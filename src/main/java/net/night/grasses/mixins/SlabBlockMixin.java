package net.night.grasses.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.night.grasses.config.GrassesConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.world.level.block.SlabBlock.TYPE;
import static net.minecraft.world.level.block.SlabBlock.WATERLOGGED;

@Mixin(SlabBlock.class)
public class SlabBlockMixin {

    @Inject(at = @At("HEAD"), method = "getStateForPlacement", cancellable = true)
    public void getStateForPlacement(BlockPlaceContext pContext, CallbackInfoReturnable<BlockState> cir) {
        boolean allowPutAnySlabFirst = GrassesConfig.COMMON_CONFIG.ALLOW_PUT_ANY_TOP_SLAB_FIRST.get();

        boolean isSprintKeyPush = Minecraft.getInstance().options.keySprint.isDown();
        if (allowPutAnySlabFirst && isSprintKeyPush) {

            SlabBlock targetInstance = SlabBlock.class.cast(this);
            BlockPos $$1 = pContext.getClickedPos();
            BlockState $$2 = pContext.getLevel().getBlockState($$1);
            if (!$$2.is(targetInstance)) {
                FluidState $$3 = pContext.getLevel().getFluidState($$1);
                BlockState $$4 = (BlockState) ((BlockState) targetInstance.defaultBlockState().setValue(TYPE, SlabType.BOTTOM)).setValue(WATERLOGGED, $$3.getType() == Fluids.WATER);
                cir.setReturnValue((BlockState) $$4.setValue(TYPE, SlabType.TOP));
            }

        }
    }

}
