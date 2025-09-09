package net.night.grasses.interfaces;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

public interface FallingBlockDust {

    default int getDustColor(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return 0;
    }
}
