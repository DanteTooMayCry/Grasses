package net.night.grasses.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherrackBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import static net.night.grasses.init.BlocksRegister.*;

@Mixin(NetherrackBlock.class)
public class NetherrackBlockMixin {

    @Inject(at = @At("HEAD"), method = "performBonemeal", cancellable = true)
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState, CallbackInfo ci) {

        boolean flag = false;
        boolean flag1 = false;

        for (BlockPos blockpos : BlockPos.betweenClosed(blockPos.offset(-1, -1, -1), blockPos.offset(1, 1, 1))) {
            BlockState blockstate = serverLevel.getBlockState(blockpos);
            if (blockstate.is(WARPED_NYLIUM_SLAB_BLOCK.get()) || blockstate.is(GROW_WARPED_NYLIUM_BLOCK.get())) {
                flag1 = true;
            }

            if (blockstate.is(CRIMSON_NYLIUM_SLAB_BLOCK.get()) || blockstate.is(GROW_CRIMSON_NYLIUM_BLOCK.get())) {
                flag = true;
            }

            if (flag1 && flag) {
                break;
            }
        }

        if (flag1 && flag) {
            serverLevel.setBlock(blockPos, randomSource.nextBoolean() ? Blocks.WARPED_NYLIUM.defaultBlockState() : Blocks.CRIMSON_NYLIUM.defaultBlockState(), 3);
        } else if (flag1) {
            serverLevel.setBlock(blockPos, Blocks.WARPED_NYLIUM.defaultBlockState(), 3);
        } else if (flag) {
            serverLevel.setBlock(blockPos, Blocks.CRIMSON_NYLIUM.defaultBlockState(), 3);
        }
    }

}
