package net.night.grasses.block.netherrackLike;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import net.night.grasses.block.otherSlabs.superclassses.ParentSlabBlock;
import net.night.grasses.config.GrassesConfig;

import static net.minecraft.world.level.block.state.properties.SlabType.BOTTOM;

public class NetherrackSlab extends ParentSlabBlock implements BonemealableBlock {
    public NetherrackSlab() {
        super(Properties.copy(Blocks.NETHERRACK));
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState, boolean isClient) {
        if (!levelReader.getBlockState(blockPos.above()).propagatesSkylightDown(levelReader, blockPos)) {
            return false;
        } else {
            for(BlockPos blockpos : BlockPos.betweenClosed(blockPos.offset(-1, -1, -1), blockPos.offset(1, 1, 1))) {
                if (levelReader.getBlockState(blockpos).is(BlockTags.NYLIUM)) {
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        //In ModEvents
    }

    @Override
    public boolean canSustainPlant(BlockState blockState, BlockGetter world, BlockPos blockPos, Direction direction, IPlantable plantable) {

        PlantType plantType = plantable.getPlantType(world, blockPos);

        if (plantType == PlantType.NETHER && (!blockState.getValue(TYPE).equals(BOTTOM) || GrassesConfig.CommonConfig.ALLOW_PUT_PLANTS_ON_BOTTOM_SLAB.get())) {
            return true;
        }
        else
            return super.canSustainPlant(blockState, world, blockPos, direction, plantable);
    }
}
