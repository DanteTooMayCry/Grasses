package net.night.grasses.block.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.night.grasses.block.blockEntity.TintedBlockEntity;
import net.night.grasses.enums.ColorType;
import net.night.grasses.config.GrassesConfig;
import net.night.grasses.data.ModMethods;

import java.util.List;

import static biomesoplenty.api.block.BOPBlocks.WILLOW_VINE;
import static net.minecraft.world.level.block.Blocks.VINE;
import static net.night.grasses.data.ModData.matchingCounterpartsPlants;
import static net.night.grasses.data.ModMethods.*;
import static net.night.grasses.init.BlocksRegister.FERTILE;
import static net.night.grasses.init.BlocksRegister.VINE_TINTED;

public class TintedVine extends VineBlock implements BonemealableBlock, EntityBlock {

    public TintedVine() {
        super(Properties.ofFullCopy(VINE));
        this.registerDefaultState(this.defaultBlockState().setValue(FERTILE, Boolean.TRUE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FERTILE);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new TintedBlockEntity(blockPos, blockState);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemStack, @javax.annotation.Nullable BlockGetter level, List<Component> tooltip, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, tooltip, tooltipFlag);
        additionalHoverText(itemStack, tooltip);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState blockState, HitResult hitResult, LevelReader levelReader, BlockPos blockPos, Player player) {
        return ModMethods.getCloneItemStackBE((Level) levelReader, blockPos, blockState);
    }

    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockStateOld, boolean pMovedByPiston) {

        onPlaceCounterPart(level, blockPos, blockState);
        super.onPlace(blockState, level, blockPos, blockStateOld, pMovedByPiston);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {

        getStateForPlacementCounterPart(context, matchingCounterpartsPlants);

        return super.getStateForPlacement(context);
    }

    @Override
    public List<ItemStack> getDrops(BlockState blockState, LootParams.Builder builder) {
        return prepareDropWithColor(super.getDrops(blockState, builder), builder, this.asItem());
    }

    private boolean canSpread(BlockGetter pBlockReader, BlockPos pPos) {
        int i = 4;
        Iterable<BlockPos> iterable = BlockPos.betweenClosed(pPos.getX() - 4, pPos.getY() - 1, pPos.getZ() - 4, pPos.getX() + 4, pPos.getY() + 1, pPos.getZ() + 4);
        int j = 5;

        for(BlockPos blockPosAbove : iterable) {
            if (pBlockReader.getBlockState(blockPosAbove).is(this)) {
                --j;
                if (j <= 0) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean canSupportAtFace(BlockGetter pLevel, BlockPos pPos, Direction pDirection) {
        if (pDirection == Direction.DOWN) {
            return false;
        } else {
            BlockPos blockPosAbove = pPos.relative(pDirection);
            if (isAcceptableNeighbour(pLevel, blockPosAbove, pDirection)) {
                return true;
            } else if (pDirection.getAxis() == Direction.Axis.Y) {
                return false;
            } else {
                BooleanProperty booleanproperty = PROPERTY_BY_DIRECTION.get(pDirection);
                BlockState blockstate = pLevel.getBlockState(pPos.above());
                return blockstate.is(this) && blockstate.getValue(booleanproperty);
            }
        }
    }

    private boolean hasHorizontalConnection(BlockState pState) {
        return pState.getValue(NORTH) || pState.getValue(EAST) || pState.getValue(SOUTH) || pState.getValue(WEST);
    }

    private BlockState copyRandomFaces(BlockState blockState, BlockState blockState1, RandomSource randomSource) {
        for(Direction direction : Direction.Plane.HORIZONTAL) {
            if (randomSource.nextBoolean()) {
                BooleanProperty booleanproperty = getPropertyForFace(direction);
                if (blockState.getValue(booleanproperty)) {
                    blockState1 = blockState1.setValue(booleanproperty, Boolean.valueOf(true));
                }
            }
        }

        return blockState1;
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if(isFertileState(blockState)) {
            if (serverLevel.random.nextInt(4) == 0 && serverLevel.isAreaLoaded(blockPos, 4)) { // Forge: check area to prevent loading unloaded chunks
                Direction direction = Direction.getRandom(randomSource);
                BlockPos blockPosAbove = blockPos.above();

                ColorType colorType = getColorType(blockPos);
                Block plantBlock;
                if (blockState.is(VINE_TINTED.get()))
                    plantBlock = VINE;
                else
                    plantBlock = WILLOW_VINE;


                if (direction.getAxis().isHorizontal() && !blockState.getValue(getPropertyForFace(direction))) {
                    if (this.canSpread(serverLevel, blockPos)) {
                        BlockPos blockpos4 = blockPos.relative(direction);
                        BlockState blockstate4 = serverLevel.getBlockState(blockpos4);
                        if (blockstate4.isAir()) {
                            Direction direction3 = direction.getClockWise();
                            Direction direction4 = direction.getCounterClockWise();
                            boolean flag = blockState.getValue(getPropertyForFace(direction3));
                            boolean flag1 = blockState.getValue(getPropertyForFace(direction4));
                            BlockPos blockpos2 = blockpos4.relative(direction3);
                            BlockPos blockpos3 = blockpos4.relative(direction4);
                            if (flag && isAcceptableNeighbour(serverLevel, blockpos2, direction3)) {
                                keepData(blockpos4, plantBlock, colorType);
                                serverLevel.setBlock(blockpos4, this.defaultBlockState().setValue(getPropertyForFace(direction3), Boolean.valueOf(true)), 2);
                            } else if (flag1 && isAcceptableNeighbour(serverLevel, blockpos3, direction4)) {
                                keepData(blockpos4, plantBlock, colorType);
                                serverLevel.setBlock(blockpos4, this.defaultBlockState().setValue(getPropertyForFace(direction4), Boolean.valueOf(true)), 2);
                            } else {
                                Direction direction1 = direction.getOpposite();
                                if (flag && serverLevel.isEmptyBlock(blockpos2) && isAcceptableNeighbour(serverLevel, blockPos.relative(direction3), direction1)) {
                                    keepData(blockpos2, plantBlock, colorType);
                                    serverLevel.setBlock(blockpos2, this.defaultBlockState().setValue(getPropertyForFace(direction1), Boolean.valueOf(true)), 2);
                                } else if (flag1 && serverLevel.isEmptyBlock(blockpos3) && isAcceptableNeighbour(serverLevel, blockPos.relative(direction4), direction1)) {
                                    keepData(blockpos3, plantBlock, colorType);
                                    serverLevel.setBlock(blockpos3, this.defaultBlockState().setValue(getPropertyForFace(direction1), Boolean.valueOf(true)), 2);
                                } else if ((double)randomSource.nextFloat() < 0.05D && isAcceptableNeighbour(serverLevel, blockpos4.above(), Direction.UP)) {
                                    keepData(blockpos4, plantBlock, colorType);
                                    serverLevel.setBlock(blockpos4, this.defaultBlockState().setValue(UP, Boolean.valueOf(true)), 2);
                                }
                            }
                        } else if (isAcceptableNeighbour(serverLevel, blockpos4, direction)) {
                            keepData(blockPos, plantBlock, colorType);
                            serverLevel.setBlock(blockPos, blockState.setValue(getPropertyForFace(direction), Boolean.valueOf(true)), 2);
                        }

                    }
                } else {
                    if (direction == Direction.UP && blockPos.getY() < serverLevel.getMaxBuildHeight() - 1) {
                        if (this.canSupportAtFace(serverLevel, blockPos, direction)) {
                            keepData(blockPos, plantBlock, colorType);
                            serverLevel.setBlock(blockPos, blockState.setValue(UP, Boolean.valueOf(true)), 2);
                            return;
                        }

                        if (serverLevel.isEmptyBlock(blockPosAbove)) {
                            if (!this.canSpread(serverLevel, blockPos)) {
                                return;
                            }

                            BlockState blockstate3 = blockState;

                            for(Direction direction2 : Direction.Plane.HORIZONTAL) {
                                if (randomSource.nextBoolean() || !isAcceptableNeighbour(serverLevel, blockPosAbove.relative(direction2), direction2)) {
                                    blockstate3 = blockstate3.setValue(getPropertyForFace(direction2), Boolean.valueOf(false));
                                }
                            }

                            if (this.hasHorizontalConnection(blockstate3)) {
                                keepData(blockPosAbove, plantBlock, colorType);
                                serverLevel.setBlock(blockPosAbove, blockstate3, 2);
                            }

                            return;
                        }
                    }

                    if (blockPos.getY() > serverLevel.getMinBuildHeight()) {
                        BlockPos blockPosBelow = blockPos.below();
                        BlockState blockStateBelow = serverLevel.getBlockState(blockPosBelow);
                        if (blockStateBelow.isAir() || blockStateBelow.is(this)) {
                            BlockState blockstate1 = blockStateBelow.isAir() ? this.defaultBlockState() : blockStateBelow;
                            BlockState blockstate2 = this.copyRandomFaces(blockState, blockstate1, randomSource);
                            if (blockstate1 != blockstate2 && this.hasHorizontalConnection(blockstate2)) {
                                keepData(blockPosBelow, plantBlock, colorType);
                                serverLevel.setBlock(blockPosBelow, blockstate2, 2);
                            }
                        }
                    }

                }
            }
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState) {
        if (!GrassesConfig.COMMON_CONFIG.ALLOW_USE_BONE_MEAL_ON_MOD_VINES.get())
            return false;
        else
            return true;
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        if(!isFertileState(blockState)) {
            serverLevel.setBlock(blockPos, blockState.setValue(FERTILE, true), 3);
        }
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {

        boolean changeColorPermission = GrassesConfig.COMMON_CONFIG.ALLOW_CHANGE_VINES_COLOR_SEVERALLY.get();
        boolean changeIntoVanillaPermission = GrassesConfig.COMMON_CONFIG.ALLOW_CHANGE_TINTED_VINES_INTO_NOT_GRASSES_SEVERALLY.get();

        int interactionResult = ModMethods.useOnPlant(blockState, level, blockPos, player, interactionHand, blockHitResult,
                changeColorPermission, changeIntoVanillaPermission, false, SoundEvents.VINE_BREAK);

        if (interactionResult == 0)
            return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
        else
            return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 100;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 15;
    }
}
