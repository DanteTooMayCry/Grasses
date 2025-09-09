package net.night.grasses.mixins;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.night.grasses.init.BlocksRegister;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.world.level.block.SlabBlock.TYPE;

@Mixin(HalfTransparentBlock.class)
public abstract class HalfTransparentBlockMixin {

    @Inject(at = @At("HEAD"), method = "skipRendering", cancellable = true)
    public void skipRendering(BlockState pState, BlockState pAdjacentBlockState, Direction pSide, CallbackInfoReturnable<Boolean> cir) {

        if (pState.is(Blocks.ICE) && pAdjacentBlockState.is(BlocksRegister.ICE_SLAB_BLOCK.get())) {
            if (pAdjacentBlockState.getValue(TYPE) == SlabType.DOUBLE)
                cir.setReturnValue(true);
            if (pAdjacentBlockState.getValue(TYPE) == SlabType.BOTTOM && pSide == Direction.UP)
                cir.setReturnValue(true);
            if (pAdjacentBlockState.getValue(TYPE) == SlabType.TOP && pSide == Direction.DOWN)
                cir.setReturnValue(true);
        }

    }
}
