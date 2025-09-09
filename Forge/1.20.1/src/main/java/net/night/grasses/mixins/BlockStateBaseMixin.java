package net.night.grasses.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.Vec3;
import net.night.grasses.config.GrassesConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({BlockBehaviour.BlockStateBase.class})
public class BlockStateBaseMixin {

    @Inject(at = @At("HEAD"), method = "getOffset", cancellable = true)
    private void getOffset(BlockGetter level, BlockPos blockPos, CallbackInfoReturnable<Vec3> cir) {

        if (GrassesConfig.ClientConfig.DISABLE_VANILLA_PLANTS_OFFSET.get())
            cir.setReturnValue(Vec3.ZERO);
    }
}
