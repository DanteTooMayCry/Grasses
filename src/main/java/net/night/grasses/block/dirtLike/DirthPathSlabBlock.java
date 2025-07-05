package net.night.grasses.block.dirtLike;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.night.grasses.block.otherSlabs.superclassses.ParentSlabBlock;
import org.jetbrains.annotations.Nullable;

import static net.night.grasses.block.dirtLike.FarmlandSlabBlock.*;
import static net.night.grasses.init.BlocksRegister.DIRT_SLAB_BLOCK;

public class DirthPathSlabBlock extends ParentSlabBlock {

    public DirthPathSlabBlock() {
        super(Properties.copy(Blocks.DIRT_PATH));
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        BlockState blockStateAbove = levelReader.getBlockState(blockPos.above());
        return !blockStateAbove.isSolid() || blockStateAbove.getBlock() instanceof FenceGateBlock;
    }

    @Override
    public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        FarmlandSlabBlock.turnToDirt((Entity)null, blockState, serverLevel, blockPos);
    }

    ///////////////////////////////////////////////////////////

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return !this.defaultBlockState().canSurvive(blockPlaceContext.getLevel(), blockPlaceContext.getClickedPos()) ?
                Block.pushEntitiesUp(this.defaultBlockState(), DIRT_SLAB_BLOCK.get().defaultBlockState(), blockPlaceContext.getLevel(), blockPlaceContext.getClickedPos()) :
                super.getStateForPlacement(blockPlaceContext);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        SlabType slabtype = blockState.getValue(TYPE);
        switch (slabtype) {
            case DOUBLE:
                return DOUBLE_SHAPE;
            case TOP:
                return TOP_SHAPE;
            default:
                return BOTTOM_SHAPE;
        }
    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState directionState, LevelAccessor levelAccessor, BlockPos currentPos, BlockPos directionPos) {
        if (blockState.getValue(WATERLOGGED)) {
            levelAccessor.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        }
        if (direction == Direction.UP && !blockState.canSurvive(levelAccessor, currentPos)) {
            levelAccessor.scheduleTick(currentPos, this, 1);
        }
        return super.updateShape(blockState, direction, directionState, levelAccessor, currentPos, directionPos);
    }

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }
}
