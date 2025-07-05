package net.night.grasses.mixins;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.HugeFungusConfiguration;
import net.minecraft.world.level.levelgen.feature.HugeFungusFeature;
import net.night.grasses.util.ModTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HugeFungusFeature.class)
public abstract class HugeFungusFeatureMixin extends Feature<HugeFungusConfiguration> {

    public HugeFungusFeatureMixin(Codec<HugeFungusConfiguration> pCodec) {
        super(pCodec);
    }

    @Inject(at = @At("HEAD"), method = "place", cancellable = true)
    public void grasses$place(FeaturePlaceContext<HugeFungusConfiguration> pContext, CallbackInfoReturnable<Boolean> cir) {
        WorldGenLevel worldgenlevel = pContext.level();
        BlockPos blockpos = pContext.origin();
        RandomSource randomsource = pContext.random();
        ChunkGenerator chunkgenerator = pContext.chunkGenerator();
        HugeFungusConfiguration hugefungusconfiguration = pContext.config();

        BlockPos blockpos1 = null;
        BlockState blockstate = worldgenlevel.getBlockState(blockpos.below());
        BlockState fungusType =  worldgenlevel.getBlockState(blockpos);

        if ((fungusType.is(Blocks.CRIMSON_FUNGUS) && blockstate.is(ModTags.Blocks.CRIMSON_NYLIUM_BLOCKS)) ||
                                    (fungusType.is(Blocks.WARPED_FUNGUS) && blockstate.is(ModTags.Blocks.WARPED_NYLIUM_BLOCKS))) {
            blockpos1 = blockpos;
        }

        if (blockpos1 != null) {
            int i = Mth.nextInt(randomsource, 4, 13);
            if (randomsource.nextInt(12) == 0) {
                i *= 2;
            }

            if (!hugefungusconfiguration.planted) {
                int j = chunkgenerator.getGenDepth();
                if (blockpos1.getY() + i + 1 >= j) {
                    cir.setReturnValue(false);
                }
            }

            boolean flag = !hugefungusconfiguration.planted && randomsource.nextFloat() < 0.06F;
            worldgenlevel.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 4);
            this.placeStem(worldgenlevel, randomsource, hugefungusconfiguration, blockpos1, i, flag);
            this.placeHat(worldgenlevel, randomsource, hugefungusconfiguration, blockpos1, i, flag);
            cir.setReturnValue(true);
        }
    }


    @Shadow
    private void placeStem(WorldGenLevel pLevel, RandomSource pRandom, HugeFungusConfiguration pConfig, BlockPos pPos, int pHeight, boolean p_285355_) {
    }
    @Shadow
    private void placeHat(WorldGenLevel pLevel, RandomSource pRandom, HugeFungusConfiguration pConfig, BlockPos pPos, int p_285156_, boolean p_285265_) {
    }
}
