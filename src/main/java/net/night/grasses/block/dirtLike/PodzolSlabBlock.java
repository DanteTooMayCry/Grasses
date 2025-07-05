package net.night.grasses.block.dirtLike;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.lighting.LightEngine;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.IPlantable;
import net.night.grasses.block.otherSlabs.superclassses.ParentSlabBlock;
import net.night.grasses.config.GrassesConfig;
import net.night.grasses.interfaces.CanGrowConditioner;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.world.level.block.Blocks.FERN;
import static net.minecraft.world.level.block.SnowyDirtBlock.SNOWY;
import static net.minecraft.world.level.block.state.properties.SlabType.BOTTOM;
import static net.night.grasses.data.MethodsLib.canSustainPlantOnDirtLike;
import static net.night.grasses.data.MethodsLib.isFertileState;
import static net.night.grasses.init.BlocksRegister.*;

public class PodzolSlabBlock extends ParentSlabBlock implements BonemealableBlock, CanGrowConditioner {
    public PodzolSlabBlock() {
        super(Properties.copy(Blocks.PODZOL).randomTicks());
        this.registerDefaultState(this.defaultBlockState().setValue(SNOWY, false).setValue(FERTILE, true));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(SNOWY, FERTILE);
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        return isFertileState(blockState) && super.canSurvive(blockState, levelReader, blockPos);
    }

    @Override
    public boolean isRandomlyTicking(@NotNull BlockState blockState) {
        return isFertileState(blockState) && super.isRandomlyTicking(blockState);
    }

    private static boolean isSnowySetting(BlockState blockState) {
        return blockState.is(BlockTags.SNOW);
    }

///////////////////////////////////////////////////////////

    @Override
    public boolean canSustainPlant(BlockState blockState, BlockGetter world, BlockPos blockPos, Direction facing, IPlantable plantable) {
        return canSustainPlantOnDirtLike(blockState, world, blockPos, facing, plantable);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState, boolean pIsClient) {

        if (isFertileState(blockState) && ((blockState.getValue(TYPE).equals(BOTTOM) && !GrassesConfig.CommonConfig.ALLOW_PUT_PLANTS_ON_BOTTOM_SLAB.get()) || !GrassesConfig.CommonConfig.ALLOW_USE_BONE_MEAL_ON_MOD_PODZOL.get()))
            return false;
        else
            return levelReader.getBlockState(blockPos.above()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        //performBonemeal
        //ModEventsMethodsLib.class => instanceof BoneMealItem: used method for vanilla blocks of podzol, mycelium & soulsand

        if(!isFertileState(blockState)) {
            serverLevel.setBlock(blockPos, blockState.setValue(FERTILE, true), 3);
            serverLevel.levelEvent(2005, blockPos, 0);
        }
    }


    ///////////////////////////////////////////////////////////

    @Override
    public boolean canBeGrass(BlockState blockState, LevelReader levelReader, BlockPos targetBlockPos) {
        BlockPos blockPosAbove = targetBlockPos.above();
        BlockState blockStateAbove = levelReader.getBlockState(blockPosAbove);
        BlockState targetblockState = levelReader.getBlockState(targetBlockPos);

        if(blockStateAbove.is(Blocks.SNOW)) { //part 2 off below bypass
            return blockStateAbove.getValue(SnowLayerBlock.LAYERS) == 1;
        }
        else if (blockStateAbove.getFluidState().getAmount() == 8) {
            return false;
        } else if (blockState.getValue(WATERLOGGED) && blockState.getValue(TYPE) == BOTTOM) {
            return false;
        } else if (targetblockState.getBlock() instanceof DirtSlabBlock && targetblockState.getValue(WATERLOGGED) && targetblockState.getValue(TYPE) == BOTTOM) {
            return false;
        } else {
            int i =  LightEngine.getLightBlockInto(levelReader, blockState, targetBlockPos, blockStateAbove, blockPosAbove, Direction.UP, blockStateAbove.getLightBlock(levelReader, blockPosAbove));
            if (blockState.getValue(TYPE) == SlabType.TOP && i==16) { // Bypass. There is some kind of problem (Vanilla bug in LightEngine?), that slab top return 16 without reason
                if (blockStateAbove.isSolidRender(levelReader, blockPosAbove)) {
                    return false;
                }
                return true;
            }

            return i < levelReader.getMaxLightLevel();
        }
    }

    @Override
    public boolean canSpread(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        BlockPos blockPosAbove = blockPos.above();
        return canBeGrass(blockState, levelReader, blockPos) && !levelReader.getFluidState(blockPosAbove).is(FluidTags.WATER) && GrassesConfig.CommonConfig.ALLOW_SPREAD_MOD_PODZOL.get();
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {

        if (!serverLevel.isAreaLoaded(blockPos, 1))
            return;
        else if (!canBeGrass(blockState, serverLevel, blockPos))
            serverLevel.setBlockAndUpdate(blockPos, DIRT_SLAB_BLOCK.get().defaultBlockState().setValue(TYPE, blockState.getValue(TYPE)));
        else {
            if (serverLevel.getMaxLocalRawBrightness(blockPos.above()) >= 9) {
                BlockState defaultBlockState = this.defaultBlockState();

                for (int i = 0; i < 4; ++i) {
                    BlockPos targetPos = blockPos.offset(randomSource.nextInt(3) - 1, randomSource.nextInt(5) - 3, randomSource.nextInt(3) - 1);
                    BlockState targetState = serverLevel.getBlockState(targetPos);

                    if (canSpread(defaultBlockState, serverLevel, targetPos)) {

                        if (targetState.is(DIRT_SLAB_BLOCK.get())) {
                            serverLevel.setBlockAndUpdate(targetPos, blockState
                                    .setValue(SNOWY, Boolean.valueOf(serverLevel.getBlockState(targetPos.above()).is(Blocks.SNOW)))
                                    .setValue(FERTILE, Boolean.TRUE)
                                    .setValue(TYPE, targetState.getValue(TYPE))
                                    .setValue(WATERLOGGED, targetState.getValue(WATERLOGGED))
                            );

                        } else if (targetState.is(Blocks.DIRT)) {

                            serverLevel.setBlockAndUpdate(targetPos, GROW_PODZOL_BLOCK.get().defaultBlockState());
                        }
                    }
                }
            }
        }
    }

    @Override
    public InteractionResult use(@NotNull BlockState blockState, Level level, @NotNull BlockPos blockPos, Player player,
                                 @NotNull InteractionHand interactionHand, @NotNull BlockHitResult hitResult) {

        if (interactionHand != InteractionHand.MAIN_HAND)
            return super.use(blockState, level, blockPos, player, interactionHand, hitResult);

        ItemStack itemStack = player.getItemInHand(interactionHand);
        boolean hasSilkTouch = EnchantmentHelper.hasSilkTouch(itemStack);

        if (itemStack.getItem() instanceof ShearsItem && !hasSilkTouch) {

            if (player instanceof ServerPlayer) {

                if (isFertileState(blockState)) {
                    level.setBlockAndUpdate(blockPos, blockState.setValue(FERTILE, Boolean.FALSE));
                }
                else {
                    level.setBlockAndUpdate(blockPos, DIRT_SLAB_BLOCK.get().defaultBlockState().setValue(TYPE, blockState.getValue(TYPE)));
                    Block.popResourceFromFace(level, blockPos, hitResult.getDirection(), new ItemStack(FERN));
                }
            }
            level.playSound(player, blockPos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0f, 1.0f);
            level.gameEvent(player, GameEvent.SHEAR, blockPos);
            itemStack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(interactionHand));
            level.addDestroyBlockEffect(blockPos, blockState);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.use(blockState, level, blockPos, player, interactionHand, hitResult);
    }

    ///////////////////////////////////////////////////////////

    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState facingBlockState, LevelAccessor levelAccessor, BlockPos currentBlockPos, BlockPos facingBlockPos) {
        if (blockState.getValue(WATERLOGGED)) {
            levelAccessor.scheduleTick(currentBlockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        }
        return direction == Direction.UP ? blockState.setValue(SNOWY, Boolean.valueOf(isSnowySetting(facingBlockState))) : super.updateShape(blockState, direction, facingBlockState, levelAccessor, currentBlockPos, facingBlockPos);
    }
}
