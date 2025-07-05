package net.night.grasses.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FungusBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.night.grasses.block.netherrackLike.NyliumSlabBlock;
import net.night.grasses.config.GrassesConfig;
import net.night.grasses.util.ModTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.SLAB_TYPE;

@Mixin(FungusBlock.class)
public abstract class FungusBlockMixin {

    @Inject(at = @At("HEAD"), method = "isValidBonemealTarget", cancellable = true)
    public void isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient, CallbackInfoReturnable<Boolean> cir) {
        BlockState blockstate = pLevel.getBlockState(pPos.below());
        BlockState fungusType = pLevel.getBlockState(pPos);

        if ((fungusType.is(Blocks.CRIMSON_FUNGUS) && blockstate.is(ModTags.Blocks.CRIMSON_NYLIUM_BLOCKS)) ||
                (fungusType.is(Blocks.WARPED_FUNGUS) && blockstate.is(ModTags.Blocks.WARPED_NYLIUM_BLOCKS))) {
            cir.setReturnValue(true);
        }
    }

    @Inject(at = @At("HEAD"), method = "mayPlaceOn", cancellable = true)
    protected void mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos, CallbackInfoReturnable<Boolean> cir) {

        if ((pState.getBlock() instanceof NyliumSlabBlock && pState.getValue(SLAB_TYPE) == SlabType.BOTTOM) && !GrassesConfig.CommonConfig.ALLOW_PUT_PLANTS_ON_BOTTOM_SLAB.get())
            cir.setReturnValue(false);
    }
}
