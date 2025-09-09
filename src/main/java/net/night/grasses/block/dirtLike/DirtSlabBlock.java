package net.night.grasses.block.dirtLike;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.lighting.LightEngine;
import net.neoforged.neoforge.common.IPlantable;
import net.night.grasses.block.otherSlabs.superclassses.ParentSlabBlock;
import net.night.grasses.interfaces.CanGrowConditioner;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.world.level.block.Blocks.GRASS_BLOCK;
import static net.minecraft.world.level.block.Blocks.MYCELIUM;
import static net.minecraft.world.level.block.SnowyDirtBlock.SNOWY;
import static net.night.grasses.data.ModMethods.canSustainPlantOnDirtLike;
import static net.night.grasses.init.BlocksRegister.*;


public class DirtSlabBlock extends ParentSlabBlock implements CanGrowConditioner {

    public DirtSlabBlock() {
        super(Properties.ofFullCopy(Blocks.DIRT));
    }

    @Override
    public boolean isRandomlyTicking(@NotNull BlockState blockState) {
        return true;
    }

    ///////////////////////////////////////////////////////////

    @Override
    public boolean canSustainPlant(BlockState blockState, BlockGetter world, BlockPos blockPos, Direction facing, IPlantable plantable) {

        return canSustainPlantOnDirtLike(blockState, world, blockPos, facing, plantable);
    }


    public boolean canBeGrass(BlockState blockState, LevelReader levelReader, BlockPos targetBlockPos) {
        BlockPos blockPosAbove = targetBlockPos.above();
        BlockState blockStateAbove = levelReader.getBlockState(blockPosAbove);
        BlockState targetblockState = levelReader.getBlockState(targetBlockPos);

        if(blockStateAbove.is(Blocks.SNOW)) {
            return blockStateAbove.getValue(SnowLayerBlock.LAYERS) == 1;
        }
        else if (blockStateAbove.getFluidState().getAmount() == 8) {
            return false;
        } else if (targetblockState.getValue(WATERLOGGED) && targetblockState.getValue(TYPE) == SlabType.BOTTOM) {
            return false;
        } else {
            int i =  LightEngine.getLightBlockInto(levelReader, blockState, targetBlockPos, blockStateAbove, blockPosAbove, Direction.UP, blockStateAbove.getLightBlock(levelReader, blockPosAbove));
            return i < levelReader.getMaxLightLevel();
        }
    }

    @Override
    public boolean canSpread(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        BlockPos blockPosAbove = blockPos.above();
        return canBeGrass(blockState, levelReader, blockPos) && !levelReader.getFluidState(blockPosAbove).is(FluidTags.WATER);
    }

    ///////////////////////////////////////////////////////////

    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {

        //Reverse logic for grass spread -> if target is vanilla grass and current block is canbegrass dirtslab than change this dirtslab into grow_grass_slab
        if (!serverLevel.isAreaLoaded(blockPos, 3))
            return;
        if (serverLevel.getMaxLocalRawBrightness(blockPos.above()) >= 9) {
            BlockState defaultBlockState = this.defaultBlockState();

            for (int i = 0; i < 4; ++i) {
                BlockPos targetPos = blockPos.offset(randomSource.nextInt(3) - 1, randomSource.nextInt(5) - 3, randomSource.nextInt(3) - 1);
                BlockState targetState = serverLevel.getBlockState(targetPos);
                BlockState blockStateForSet = null;

                if (targetState.is(GRASS_BLOCK))
                    blockStateForSet = GRASS_SLAB_BLOCK.get().defaultBlockState();
                else if (targetState.is(MYCELIUM))
                    blockStateForSet = MYCELIUM_SLAB_BLOCK.get().defaultBlockState();


                if ((targetState == GRASS_BLOCK.defaultBlockState() || targetState == Blocks.MYCELIUM.defaultBlockState()) && blockState.is(DIRT_SLAB_BLOCK.get()) && canSpread(defaultBlockState, serverLevel, blockPos)) {
                    serverLevel.setBlockAndUpdate(blockPos, blockStateForSet
                            .setValue(SNOWY, serverLevel.getBlockState(blockPos.above()).is(Blocks.SNOW))
                            .setValue(FERTILE, Boolean.TRUE)
                            .setValue(TYPE, blockState.getValue(TYPE))
                            .setValue(WATERLOGGED, blockState.getValue(WATERLOGGED))
                    );
                }
            }
        }
    }
}