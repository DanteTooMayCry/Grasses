package net.night.grasses.block.dirtLike;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IPlantable;
import net.night.grasses.block.otherSlabs.superclassses.ParentSlabBlock;

import static net.night.grasses.data.MethodsLib.canSustainPlantOnDirtLike;

public class RootedDirtSlabBlock extends ParentSlabBlock implements BonemealableBlock {
    public RootedDirtSlabBlock() {
        super(Properties.copy(Blocks.ROOTED_DIRT));
    }

    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState, boolean isClient) {
        return levelReader.getBlockState(blockPos.below()).isAir();
    }

    public boolean isBonemealSuccess(Level levelp_221979_, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        serverLevel.setBlockAndUpdate(blockPos.below(), Blocks.HANGING_ROOTS.defaultBlockState());
    }

    @Override
    public boolean canSustainPlant(BlockState blockState, BlockGetter world, BlockPos blockPos, Direction facing, IPlantable plantable) {
        return canSustainPlantOnDirtLike(blockState, world, blockPos, facing, plantable);
    }
}
