package net.night.grasses.block.netherrackLike;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.NetherFeatures;
import net.minecraft.resources.ResourceKey;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.lighting.LightEngine;
import net.minecraft.world.phys.BlockHitResult;
import net.night.grasses.block.otherSlabs.superclassses.ParentSlabBlock;
import net.night.grasses.config.GrassesConfig;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.world.level.block.Blocks.NETHERRACK;
import static net.minecraft.world.level.block.state.properties.SlabType.BOTTOM;
import static net.night.grasses.data.ModMethods.isFertileState;
import static net.night.grasses.init.BlocksRegister.*;

public class NyliumSlabBlock extends ParentSlabBlock implements BonemealableBlock {
    public NyliumSlabBlock(Properties pProperties) {
        super(pProperties);
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
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState, boolean isClient) {
        if (blockState.getValue(TYPE).equals(BOTTOM) && !GrassesConfig.CommonConfig.ALLOW_PUT_PLANTS_ON_BOTTOM_SLAB.get())
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
        if(!isFertileState(blockState)) {
            serverLevel.setBlock(blockPos, blockState.setValue(FERTILE, true), 3);
            serverLevel.levelEvent(2005, blockPos, 0);
        }

        BlockState blockstate = serverLevel.getBlockState(blockPos);
        BlockPos blockpos = blockPos.above();
        ChunkGenerator chunkgenerator = serverLevel.getChunkSource().getGenerator();
        Registry<ConfiguredFeature<?, ?>> registry = serverLevel.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);
        if (blockstate.is(CRIMSON_NYLIUM_SLAB_BLOCK.get())) {
            this.place(registry, NetherFeatures.CRIMSON_FOREST_VEGETATION_BONEMEAL, serverLevel, chunkgenerator, randomSource, blockpos);
        } else if (blockstate.is(WARPED_NYLIUM_SLAB_BLOCK.get())) {
            this.place(registry, NetherFeatures.WARPED_FOREST_VEGETATION_BONEMEAL, serverLevel, chunkgenerator, randomSource, blockpos);
            this.place(registry, NetherFeatures.NETHER_SPROUTS_BONEMEAL, serverLevel, chunkgenerator, randomSource, blockpos);
            if (randomSource.nextInt(8) == 0) {
                this.place(registry, NetherFeatures.TWISTING_VINES_BONEMEAL, serverLevel, chunkgenerator, randomSource, blockpos);
            }
        }

    }

    private void place(Registry<ConfiguredFeature<?, ?>> pFeatureRegistry, ResourceKey<ConfiguredFeature<?, ?>> pFeatureKey, ServerLevel pLevel, ChunkGenerator pChunkGenerator, RandomSource pRandom, BlockPos pPos) {
        pFeatureRegistry.getHolder(pFeatureKey).ifPresent((p_255920_) -> {
            p_255920_.value().place(pLevel, pChunkGenerator, pRandom, pPos);
        });
    }

    private static boolean canBeNylium(BlockState blockState, LevelReader levelReader, BlockPos targetBlockPos) {
        BlockPos blockPosAbove = targetBlockPos.above();
        BlockState blockStateAbove = levelReader.getBlockState(blockPosAbove);
        int i = LightEngine.getLightBlockInto(levelReader, blockState, targetBlockPos, blockStateAbove, blockPosAbove, Direction.UP, blockStateAbove.getLightBlock(levelReader, blockPosAbove));
        if (blockState.getValue(TYPE) == SlabType.TOP && i==16) { // Bypass. There is some kind of problem (Vanilla bug in LightEngine?), that slab top return 16 without reason
            if (blockStateAbove.isSolidRender(levelReader, blockPosAbove)) {
                return false;
            }
            return true;
        }
        return i < levelReader.getMaxLightLevel();
    }

    private boolean canSpread(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        BlockPos blockPosAbove = blockPos.above();
        return canBeNylium(blockState, levelReader, blockPos) && !levelReader.getFluidState(blockPosAbove).is(FluidTags.WATER) && GrassesConfig.CommonConfig.ALLOW_SPREAD_MOD_NYLIUM.get();
    }

    ///////////////////////////////////////////////////////////

    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {

        if (!serverLevel.isAreaLoaded(blockPos, 1))
            return;
        else if (!canBeNylium(blockState, serverLevel, blockPos)) {
            serverLevel.setBlockAndUpdate(blockPos, NETHERRACK_SLAB_BLOCK.get().defaultBlockState().setValue(TYPE, blockState.getValue(TYPE)));
        } else {
            if (serverLevel.getMaxLocalRawBrightness(blockPos.above()) >= 9) {
                BlockState defaultBlockState = this.defaultBlockState();

                for (int i = 0; i < 4; ++i) {
                    BlockPos targetPos = blockPos.offset(randomSource.nextInt(3) - 1, randomSource.nextInt(5) - 3, randomSource.nextInt(3) - 1);
                    BlockState targetState = serverLevel.getBlockState(targetPos);

                    if (canSpread(defaultBlockState, serverLevel, targetPos)) {

                        if (targetState.is(NETHERRACK_SLAB_BLOCK.get())) {
                            BlockState blockStateNyliumType;
                            if (blockState.is(CRIMSON_NYLIUM_SLAB_BLOCK.get()))
                                blockStateNyliumType = CRIMSON_NYLIUM_SLAB_BLOCK.get().defaultBlockState();
                            else
                                blockStateNyliumType = WARPED_NYLIUM_SLAB_BLOCK.get().defaultBlockState();

                            serverLevel.setBlockAndUpdate(targetPos, blockStateNyliumType
                                    .setValue(FERTILE, Boolean.TRUE)
                                    .setValue(TYPE, targetState.getValue(TYPE))
                                    .setValue(WATERLOGGED, targetState.getValue(WATERLOGGED))
                            );
                        } else if (targetState.is(NETHERRACK)) {
                            BlockState blockStateNyliumType;
                            if (blockState.is(CRIMSON_NYLIUM_SLAB_BLOCK.get()))
                                blockStateNyliumType = GROW_CRIMSON_NYLIUM_BLOCK.get().defaultBlockState();
                            else
                                blockStateNyliumType = GROW_WARPED_NYLIUM_BLOCK.get().defaultBlockState();

                            serverLevel.setBlockAndUpdate(targetPos, blockStateNyliumType
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

        if (itemStack.getItem() instanceof ShearsItem && !hasSilkTouch) {

            if (player instanceof ServerPlayer) {

                if (isFertileState(blockState)) {
                    level.setBlockAndUpdate(blockPos, blockState.setValue(FERTILE, Boolean.FALSE));
                }
                else {
                level.setBlockAndUpdate(blockPos, NETHERRACK_SLAB_BLOCK.get().defaultBlockState().setValue(TYPE, blockState.getValue(TYPE)));
                if (blockState.is(CRIMSON_NYLIUM_SLAB_BLOCK.get()))
                    Block.popResourceFromFace(level, blockPos, hitResult.getDirection(), new ItemStack(Items.CRIMSON_ROOTS));
                if (blockState.is(WARPED_NYLIUM_SLAB_BLOCK.get()))
                    Block.popResourceFromFace(level, blockPos, hitResult.getDirection(), new ItemStack(Items.WARPED_ROOTS));
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
