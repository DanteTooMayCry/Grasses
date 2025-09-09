package net.night.grasses.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.world.level.block.BubbleColumnBlock.DRAG_DOWN;
import static net.night.grasses.init.BlocksRegister.MAGMA_SLAB_BLOCK;
import static net.night.grasses.init.BlocksRegister.SOUL_SAND_SLAB_BLOCK;

@Mixin(BubbleColumnBlock.class)
public abstract class BubbleColumnMixin {


    @Inject(at = @At("HEAD"), method = "canSurvive", cancellable = true)
    public void canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos, CallbackInfoReturnable<Boolean> cir) {
        BlockState blockstate = pLevel.getBlockState(pPos.below());
        if(blockstate.is(MAGMA_SLAB_BLOCK.get()) || blockstate.is(SOUL_SAND_SLAB_BLOCK.get()))
            cir.setReturnValue(true);
    }

    @Inject(at = @At("HEAD"), method = "getColumnState", cancellable = true)
    private static void getColumnState(BlockState pBlockState, CallbackInfoReturnable<BlockState> cir) {

        if (pBlockState.is(SOUL_SAND_SLAB_BLOCK.get())) {
            cir.setReturnValue((BlockState) Blocks.BUBBLE_COLUMN.defaultBlockState().setValue(DRAG_DOWN, Boolean.valueOf(false)));
        } else if (pBlockState.is(MAGMA_SLAB_BLOCK.get())) {
            cir.setReturnValue((BlockState) Blocks.BUBBLE_COLUMN.defaultBlockState().setValue(DRAG_DOWN, Boolean.valueOf(true)));
        }
    }
}