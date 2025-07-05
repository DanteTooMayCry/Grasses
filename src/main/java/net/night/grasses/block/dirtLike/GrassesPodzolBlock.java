package net.night.grasses.block.dirtLike;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.lighting.LightEngine;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.IPlantable;
import net.night.grasses.config.GrassesConfig;
import net.night.grasses.interfaces.CanGrowConditioner;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.world.level.block.Blocks.FERN;
import static net.minecraft.world.level.block.SlabBlock.TYPE;
import static net.minecraft.world.level.block.SlabBlock.WATERLOGGED;
import static net.night.grasses.data.MethodsLib.canSustainPlantOnDirtLike;
import static net.night.grasses.data.MethodsLib.isFertileState;
import static net.night.grasses.init.BlocksRegister.*;

public class GrassesPodzolBlock extends SnowyDirtBlock implements BonemealableBlock, CanGrowConditioner {
    public GrassesPodzolBlock() {
        super(Properties.copy(Blocks.PODZOL).randomTicks());
        this.registerDefaultState(this.defaultBlockState().setValue(FERTILE, true));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FERTILE);
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        return isFertileState(blockState) && super.canSurvive(blockState, levelReader, blockPos);
    }

    @Override
    public boolean isRandomlyTicking(@NotNull BlockState blockState) {
        return isFertileState(blockState) && super.isRandomlyTicking(blockState);
    }

    ///////////////////////////////////////////////////////////

    @Override
    public boolean canSustainPlant(BlockState blockState, BlockGetter world, BlockPos blockPos, Direction facing, IPlantable plantable) {
        return canSustainPlantOnDirtLike(blockState, world, blockPos, facing, plantable);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState, boolean isClient) {

        if (isFertileState(blockState) && !GrassesConfig.CommonConfig.ALLOW_USE_BONE_MEAL_ON_MOD_PODZOL.get())
            return false;
        else
            return levelReader.getBlockState(blockPos.above()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, @NotNull RandomSource randomSource, BlockPos blockPos, @NotNull BlockState blockState) {

        //performBonemeal
        // ModEventsMethodsLib.class => instanceof BoneMealItem: used method for vanilla blocks of podzol, mycelium & soulsand

        if(!isFertileState(blockState))
            serverLevel.setBlock(blockPos, blockState.setValue(FERTILE, true), 3);
    }

    @Override
    public boolean canBeGrass(BlockState blockState, LevelReader levelReader, BlockPos targetBlockPos) {
        BlockPos blockPosAbove = targetBlockPos.above();
        BlockState blockStateAbove = levelReader.getBlockState(blockPosAbove);
        BlockState targetblockState = levelReader.getBlockState(targetBlockPos);

        if(blockStateAbove.is(Blocks.SNOW)) {
            return blockStateAbove.getValue(SnowLayerBlock.LAYERS) == 1;
        }
        else if (blockStateAbove.getFluidState().getAmount() == 8) {
            return false;
        } else if (targetblockState.getBlock() instanceof DirtSlabBlock && targetblockState.getValue(WATERLOGGED) && targetblockState.getValue(TYPE) == SlabType.BOTTOM) {
            return false;
        } else {
            int i =  LightEngine.getLightBlockInto(levelReader, blockState, targetBlockPos, blockStateAbove, blockPosAbove, Direction.UP, blockStateAbove.getLightBlock(levelReader, blockPosAbove));
            return i < levelReader.getMaxLightLevel();
        }
    }

    @Override
    public boolean canSpread(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        BlockPos blockPosAbove = blockPos.above();
        return canBeGrass(blockState, levelReader, blockPos) && !levelReader.getFluidState(blockPosAbove).is(FluidTags.WATER) && GrassesConfig.CommonConfig.ALLOW_SPREAD_MOD_PODZOL.get();
    }

    ///////////////////////////////////////////////////////////

    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (!serverLevel.isAreaLoaded(blockPos, 1))
            return;
        else if (!canBeGrass(blockState, serverLevel, blockPos))
            serverLevel.setBlockAndUpdate(blockPos, Blocks.DIRT.defaultBlockState());
        else {
            if (serverLevel.getMaxLocalRawBrightness(blockPos.above()) >= 9) {
                BlockState defaultBlockState = this.defaultBlockState();

                for (int i = 0; i < 4; ++i) {
                    BlockPos targetPos = blockPos.offset(randomSource.nextInt(3) - 1, randomSource.nextInt(5) - 3, randomSource.nextInt(3) - 1);
                    BlockState targetState = serverLevel.getBlockState(targetPos);

                    if (canSpread(defaultBlockState, serverLevel, targetPos)) {

                        if (targetState.is(DIRT_SLAB_BLOCK.get())) {

                            serverLevel.setBlockAndUpdate(targetPos, PODZOL_SLAB_BLOCK.get().defaultBlockState()
                                    .setValue(SNOWY, Boolean.valueOf(serverLevel.getBlockState(targetPos.above()).is(Blocks.SNOW)))
                                    .setValue(FERTILE, Boolean.TRUE)
                                    .setValue(TYPE, targetState.getValue(TYPE))
                                    .setValue(WATERLOGGED, targetState.getValue(WATERLOGGED))
                            );
                        } else if (targetState.is(Blocks.DIRT)) {

                            serverLevel.setBlockAndUpdate(targetPos, blockState.getBlock().defaultBlockState()
                                    .setValue(SNOWY, Boolean.valueOf(serverLevel.getBlockState(targetPos.above()).is(Blocks.SNOW)))
                                    .setValue(FERTILE, Boolean.TRUE));
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

        if (itemStack.getItem() instanceof ShearsItem) {
            if (hasSilkTouch) {

                if (player instanceof ServerPlayer) {
                    level.setBlock(blockPos, Blocks.PODZOL.defaultBlockState(), 3);
                }
            }
            else {

                if (player instanceof ServerPlayer) {

                    if (isFertileState(blockState)) {
                        level.setBlockAndUpdate(blockPos, blockState.setValue(FERTILE, Boolean.FALSE));
                    }
                    else {
                        level.setBlock(blockPos, Blocks.DIRT.defaultBlockState(), 3);
                        Block.popResourceFromFace(level, blockPos, hitResult.getDirection(), new ItemStack(FERN));
                    }
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
}
