package net.night.grasses.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.AttachedStemBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.neoforged.neoforge.common.IPlantable;
import net.night.grasses.config.GrassesConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static net.minecraft.world.level.block.Blocks.AIR;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.SLAB_TYPE;
import static net.neoforged.neoforge.common.CommonHooks.onCropsGrowPost;
import static net.neoforged.neoforge.common.CommonHooks.onCropsGrowPre;
import static net.night.grasses.init.BlocksRegister.FARMLAND_SLAB_BLOCK;

@Mixin(StemBlock.class)
public abstract class StemBlockMixin {

    @Shadow
    @Final
    private ResourceKey<Block> fruit;
    @Shadow
    @Final
    private ResourceKey<Block> attachedStem;
    @Shadow @Final public static IntegerProperty AGE;
    @Unique
    private static Map<Direction, BlockState> grasses$matchingDirectionBlockState = new HashMap<>();

    //With Vanilla not grow, when slab farm is next to, and grow when dirt like slabs are with bottom properties
    @Inject(at = @At("HEAD"), method = "randomTick", cancellable = true)
    public void grasses$randomTickHead(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom, CallbackInfo ci) {
        if (!pLevel.isAreaLoaded(pPos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (pLevel.getRawBrightness(pPos, 0) >= 9) {
            if (pState.getValue(AGE) == 7) {

                grasses$matchingDirectionBlockState.put(Direction.NORTH, pLevel.getBlockState(pPos.north()));
                grasses$matchingDirectionBlockState.put(Direction.SOUTH, pLevel.getBlockState(pPos.south()));
                grasses$matchingDirectionBlockState.put(Direction.WEST, pLevel.getBlockState(pPos.west()));
                grasses$matchingDirectionBlockState.put(Direction.EAST, pLevel.getBlockState(pPos.east()));
            }

            float f = grasses$getGrowthSpeed(pState.getBlock(), pLevel, pPos);
            if (onCropsGrowPre(pLevel, pPos, pState, pRandom.nextInt((int)(25.0F / f) + 1) == 0)) {
                boolean allowPlantsOnBottomSlab = GrassesConfig.COMMON_CONFIG.ALLOW_PUT_PLANTS_ON_BOTTOM_SLAB.get();

                if (pState.getValue(AGE) == 7) {
                    Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(pRandom);
                    BlockPos blockpos = pPos.relative(direction);
                    BlockState blockstate = pLevel.getBlockState(blockpos.below());
                    if (pLevel.isEmptyBlock(blockpos) && (blockstate.is(FARMLAND_SLAB_BLOCK.get()) && (blockstate.getValue(SLAB_TYPE) != SlabType.BOTTOM || allowPlantsOnBottomSlab))) {
                        Registry<Block> registry = pLevel.registryAccess().registryOrThrow(Registries.BLOCK);
                        Optional<Block> optional = registry.getOptional(this.fruit);
                        Optional<Block> optional1 = registry.getOptional(this.attachedStem);

                        if (optional.isPresent() && optional1.isPresent()) {
                            pLevel.setBlockAndUpdate(blockpos, ((Block)optional.get()).defaultBlockState());
                            pLevel.setBlockAndUpdate(pPos, (BlockState)((Block)optional1.get()).defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, direction));
                        }
                    }
                }
                onCropsGrowPost(pLevel, pPos, pState);
            }
        }
    }

    @Inject(at = @At("TAIL"), method = "randomTick", cancellable = true)
    public void grasses$randomTickTAIL(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom, CallbackInfo ci) {

        BlockState blockStateOnPosStem = pLevel.getBlockState(pPos);

        if (blockStateOnPosStem.getBlock() instanceof AttachedStemBlock) {

            for (Map.Entry<Direction, BlockState> blockStateEntry : grasses$matchingDirectionBlockState.entrySet()) {
                if (blockStateEntry.getValue().is(AIR)) {
                    BlockPos blockPosRelative = pPos.relative(blockStateEntry.getKey());
                    boolean isStillAir = pLevel.getBlockState(blockPosRelative).isAir();
                    if (!isStillAir) {
                        BlockState blockStateBelow = pLevel.getBlockState(blockPosRelative.below());
                        if (blockStateBelow.is(BlockTags.DIRT) && blockStateBelow.hasProperty(SLAB_TYPE) && blockStateBelow.getValue(SLAB_TYPE).equals(SlabType.BOTTOM)) {
                            pLevel.setBlockAndUpdate(blockPosRelative, AIR.defaultBlockState());
                            pLevel.setBlockAndUpdate(pPos, pState);
                        }
                    }
                }
            }
        }
    }

    @Unique
    private static float grasses$getGrowthSpeed(Block pBlock, BlockGetter pLevel, BlockPos pPos) {
        float f = 1.0F;
        BlockPos blockpos = pPos.below();

        for(int i = -1; i <= 1; ++i) {
            for(int j = -1; j <= 1; ++j) {
                float f1 = 0.0F;
                BlockState blockstate = pLevel.getBlockState(blockpos.offset(i, 0, j));
                if (blockstate.canSustainPlant(pLevel, blockpos.offset(i, 0, j), Direction.UP, (IPlantable) pBlock)) {
                    f1 = 1.0F;
                    if (blockstate.isFertile(pLevel, pPos.offset(i, 0, j))) {
                        f1 = 3.0F;
                    }
                }

                if (i != 0 || j != 0) {
                    f1 /= 4.0F;
                }

                f += f1;
            }
        }

        BlockPos blockpos1 = pPos.north();
        BlockPos blockpos2 = pPos.south();
        BlockPos blockpos3 = pPos.west();
        BlockPos blockpos4 = pPos.east();
        boolean flag = pLevel.getBlockState(blockpos3).is(pBlock) || pLevel.getBlockState(blockpos4).is(pBlock);
        boolean flag1 = pLevel.getBlockState(blockpos1).is(pBlock) || pLevel.getBlockState(blockpos2).is(pBlock);
        if (flag && flag1) {
            f /= 2.0F;
        } else {
            boolean flag2 = pLevel.getBlockState(blockpos3.north()).is(pBlock) || pLevel.getBlockState(blockpos4.north()).is(pBlock) || pLevel.getBlockState(blockpos4.south()).is(pBlock) || pLevel.getBlockState(blockpos3.south()).is(pBlock);
            if (flag2) {
                f /= 2.0F;
            }
        }

        return f;
    }
}
