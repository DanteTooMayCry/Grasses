package net.night.grasses.block.plants.superclasses;

import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.night.grasses.block.blockEntity.TintedBlockEntity;
import net.night.grasses.config.GrassesConfig;
import net.night.grasses.data.MethodsLib;


import java.util.List;
import java.util.Optional;

import static biomesoplenty.api.block.BOPBlocks.HIGH_GRASS;
import static biomesoplenty.api.block.BOPBlocks.HIGH_GRASS_PLANT;
import static net.minecraft.world.level.block.Blocks.*;
import static net.night.grasses.Grasses.isBOPLoaded;
import static net.night.grasses.data.MethodsLib.*;
import static net.night.grasses.init.BlocksRegister.*;
import static net.night.grasses.init.BlocksRegisterBoP.HIGH_GRASS_PLANT_TINTED;
import static net.night.grasses.init.BlocksRegisterBoP.HIGH_GRASS_TINTED;
import static net.night.grasses.init.BlocksRegisterBoP.tintedBOPPlantBlockList;

public class ParentTintedGrowingPlantBodyBlock extends GrowingPlantBodyBlock implements EntityBlock {

    protected ParentTintedGrowingPlantBodyBlock(Properties pProperties, Direction pGrowthDirection, VoxelShape pShape, boolean pScheduleFluidTicks) {
        super(pProperties, pGrowthDirection, pShape, pScheduleFluidTicks);
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
    public ItemStack getCloneItemStack(BlockState blockState, HitResult hitResult, BlockGetter blockGetter, BlockPos blockPos, Player player) {

        BlockState blockStateHead = getHeadBlock().defaultBlockState();

        return MethodsLib.getCloneItemStackBE((Level) blockGetter, blockPos, blockStateHead);
    }

    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockStateOld, boolean pMovedByPiston) {
        onPlaceCounterPart(level, blockPos, blockState);
        super.onPlace(blockState, level, blockPos, blockStateOld, pMovedByPiston);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {

        Level level = context.getLevel();
        ItemStack itemStack = context.getItemInHand();
        BlockPos blockPosClicked = context.getClickedPos();
        BlockPos clickedBelow = blockPosClicked.below();

        Block blockFromHand = ((BlockItem) context.getItemInHand().getItem()).getBlock(); //

        if (isBOPLoaded || !tintedBOPPlantBlockList.contains(blockFromHand)) {
            Block blockToKeep = AIR;
            Block blockToKeepBelow = AIR;
            if (blockFromHand.equals(KELP_PLANT_TINTED.get())) {
                blockToKeep = KELP_PLANT;
                blockToKeepBelow = KELP_PLANT;
            } else if (blockFromHand.equals(HIGH_GRASS_PLANT_TINTED.get())) {
                blockToKeep = HIGH_GRASS_PLANT;
                blockToKeepBelow = HIGH_GRASS_PLANT;
            }

            if (itemStack.is(this.getBodyBlock().asItem())) {
                Block blockBelow = level.getBlockState(blockPosClicked.below()).getBlock();
                if (blockBelow.equals(this.getHeadBlock())) {
                    keepData(clickedBelow, blockToKeepBelow, getColorType(level, clickedBelow));
                }
                keepData(blockPosClicked, blockToKeep, getColorTypeFromNBT(itemStack));
            }
        }
        else {
            if (itemStack.is(this.getBodyBlock().asItem())) {
                Block blockBelow = level.getBlockState(blockPosClicked.below()).getBlock();
                if (blockBelow.equals(this.getHeadBlock())) {
                    keepColorType.put(clickedBelow, getColorTypeFromNBT(context.getItemInHand()));
                }
                keepColorType.put(blockPosClicked, getColorTypeFromNBT(context.getItemInHand()));
            }
        }

        return super.getStateForPlacement(context);
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState pNewState, boolean pMovedByPiston) {

        BlockPos blockPosBelow = blockPos.below();
        Block blockStateBelow = level.getBlockState(blockPosBelow).getBlock();
        Block blockToKeep = AIR;

        if (blockStateBelow.equals(KELP_PLANT_TINTED.get())) {
            blockToKeep = KELP_PLANT;
        } else if (blockStateBelow.equals(HIGH_GRASS_TINTED.get())) {
            blockToKeep = HIGH_GRASS_PLANT;
        }

        if (blockStateBelow.equals(this.getBodyBlock())) {
            keepData(blockPosBelow, blockToKeep, getColorType(level, blockPosBelow));
        }
        super.onRemove(blockState, level, blockPos, pNewState, pMovedByPiston);
    }

    @Override
    public List<ItemStack> getDrops(BlockState blockState, LootParams.Builder builder) {
        return prepareDropWithColor(super.getDrops(blockState, builder), builder, this.getHeadBlock().asItem());
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {

        boolean changeColorPermission = GrassesConfig.CommonConfig.ALLOW_CHANGE_PLANTS_COLOR.get();
        boolean changeIntoVanillaPermission = GrassesConfig.CommonConfig.ALLOW_CHANGE_TINTED_PLANTS_INTO_NOT_GRASSES.get();

        int interactionResult = MethodsLib.useOnPlant(blockState, level, blockPos, player, interactionHand, blockHitResult,
                changeColorPermission, changeIntoVanillaPermission, false, SoundEvents.WET_GRASS_BREAK);

        if (interactionResult == 0)
            return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
        else
            return InteractionResult.sidedSuccess(level.isClientSide);
    }

    //=================================================================

    @Override
    protected GrowingPlantHeadBlock getHeadBlock() {
        return null;
    }

    private Optional<BlockPos> getHeadPos(BlockGetter pLevel, BlockPos pPos, Block pBlock) {
        return BlockUtil.getTopConnectedBlock(pLevel, pPos, pBlock, this.growthDirection, this.getHeadBlock());
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        if(!isFertileState(blockState)) {
            serverLevel.setBlock(blockPos, blockState.setValue(FERTILE, true), 3);
        }
        else {
            Optional<BlockPos> $$4 = this.getHeadPos(serverLevel, blockPos, blockState.getBlock());
            if ($$4.isPresent()) {
                BlockState $$5 = serverLevel.getBlockState($$4.get());

                Block blockToKeepAbove = AIR;
                if (isBOPLoaded || !tintedBOPPlantBlockList.contains(blockState.getBlock())) {

                    if (blockState.getBlock().equals(KELP_TINTED.get())) {
                        blockToKeepAbove = KELP;
                    } else if (blockState.getBlock().equals(HIGH_GRASS_PLANT_TINTED.get())) {
                        blockToKeepAbove = HIGH_GRASS;
                    }
                }

                keepData($$4.get().above(), blockToKeepAbove , getColorType(serverLevel, blockPos));
                keepColorType.put($$4.get().above(), getColorType(serverLevel, blockPos));
                ((ParentTintedGrowingPlantHeadBlock)$$5.getBlock()).performBonemeal(serverLevel, randomSource, $$4.get(), $$5);
            }
        }
    }
}
