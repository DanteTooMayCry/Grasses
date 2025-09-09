package net.night.grasses.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.GrowingPlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.night.grasses.config.GrassesConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.SLAB_TYPE;

@Mixin(GrowingPlantBlock.class)
public abstract class GrowingPlantBlockMixin {

    @Inject(at = @At("HEAD"), method = "canSurvive", cancellable = true)
    public void canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos, CallbackInfoReturnable<Boolean> cir) {
        boolean allowPlantsOnBottomSlab = GrassesConfig.COMMON_CONFIG.ALLOW_PUT_PLANTS_ON_BOTTOM_SLAB.get();

        BlockState blockStateBelow = pLevel.getBlockState(pPos.below());
        if (blockStateBelow.hasProperty(SLAB_TYPE) && blockStateBelow.getValue(SLAB_TYPE) == SlabType.BOTTOM && !allowPlantsOnBottomSlab)
            cir.setReturnValue(false);

    }


}
