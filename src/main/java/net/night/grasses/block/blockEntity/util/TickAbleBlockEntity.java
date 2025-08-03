package net.night.grasses.block.blockEntity.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;

public interface TickAbleBlockEntity {
    void tick(Level level, BlockPos blockPos, BlockState blockState, BlockEntity blockEntity);

    public static <T extends BlockEntity> BlockEntityTicker<T> getTickerHelper(Level level) {
        return level.isClientSide() ? null : (level1, blockPos, blockState1, blockEntity)
                -> ((TickAbleBlockEntity)blockEntity).tick(level1, blockPos, blockState1, blockEntity);
    }
}