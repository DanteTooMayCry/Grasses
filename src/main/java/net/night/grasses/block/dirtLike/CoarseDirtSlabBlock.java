package net.night.grasses.block.dirtLike;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IPlantable;
import net.night.grasses.block.otherSlabs.superclassses.ParentSlabBlock;

import static net.night.grasses.data.MethodsLib.canSustainPlantOnDirtLike;

public class CoarseDirtSlabBlock extends ParentSlabBlock {
    public CoarseDirtSlabBlock() {
        super(Properties.copy(Blocks.COARSE_DIRT));
    }

    @Override
    public boolean canSustainPlant(BlockState blockState, BlockGetter world, BlockPos blockPos, Direction facing, IPlantable plantable) {
        return canSustainPlantOnDirtLike(blockState, world, blockPos, facing, plantable);
    }
}
