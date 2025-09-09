package net.night.grasses.interfaces;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;


public interface CanGrowConditioner {

    boolean canBeGrass(BlockState blockState, LevelReader levelReader, BlockPos blockPos);
    boolean canSpread(BlockState blockState, LevelReader levelReader, BlockPos blockPos);

}
