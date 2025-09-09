package net.night.grasses.block.dirtLike;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.piston.MovingPistonBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.FarmlandWaterManager;
import net.neoforged.neoforge.common.IPlantable;
import net.neoforged.neoforge.common.PlantType;
import net.night.grasses.block.otherSlabs.superclassses.ParentSlabBlock;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.world.level.block.FarmBlock.MOISTURE;
import static net.neoforged.neoforge.common.CommonHooks.onFarmlandTrample;
import static net.night.grasses.init.BlocksRegister.DIRT_SLAB_BLOCK;
import static net.night.grasses.init.BlocksRegister.FARMLAND_SLAB_BLOCK;

public class FarmlandSlabBlock extends ParentSlabBlock {

    protected static final VoxelShape BOTTOM_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D);
    protected static final VoxelShape TOP_SHAPE = Block.box(0.0D, 8.0D, 0.0D, 16.0D, 15.0D, 16.0D);
    protected static final VoxelShape DOUBLE_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D);

    public FarmlandSlabBlock() {
        super(Properties.ofFullCopy(Blocks.FARMLAND));
        this.registerDefaultState(this.defaultBlockState().setValue(MOISTURE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(MOISTURE);
    }

    ///////////////////////////////////////////////////////////

    protected boolean mayPlaceOn(BlockState blockState) {
        return blockState.is(DIRT_SLAB_BLOCK.get()) && blockState.getValue(SlabBlock.TYPE) != SlabType.BOTTOM ||
                blockState.is(FARMLAND_SLAB_BLOCK.get()) && blockState.getValue(SlabBlock.TYPE) != SlabType.BOTTOM;
    }

    @Override
    public boolean canSustainPlant(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, Direction direction, IPlantable plantable) {
        PlantType plantType = plantable.getPlantType(blockGetter, blockPos);

        if (plantable instanceof BushBlock && mayPlaceOn(blockState))
            return true;
        if (!blockState.getValue(TYPE).equals(SlabType.BOTTOM))
            return plantType == PlantType.CROP;
        return false;
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        BlockState blockStateAbove = levelReader.getBlockState(blockPos.above());
        return !blockStateAbove.isSolid() || blockStateAbove.getBlock() instanceof FenceGateBlock || blockStateAbove.getBlock() instanceof MovingPistonBlock;
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (!pState.canSurvive(pLevel, pPos)) {
            turnToDirt((Entity)null, pState, pLevel, pPos);
        }
    }

    ///////////////////////////////////////////////////////////

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return !this.defaultBlockState().canSurvive(blockPlaceContext.getLevel(), blockPlaceContext.getClickedPos()) ? DIRT_SLAB_BLOCK.get().defaultBlockState() : super.getStateForPlacement(blockPlaceContext);
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

    ///////////////////////////////////////////////////////////

    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {

        if (!serverLevel.isAreaLoaded(blockPos, 3))
            return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading

        int i = blockState.getValue(MOISTURE);
        if (!isNearWater(serverLevel, blockPos) && !serverLevel.isRainingAt(blockPos.above())) {
            if (i > 0) {
                serverLevel.setBlock(blockPos, blockState.setValue(MOISTURE, Integer.valueOf(i - 1)), 2);
            } else if (!shouldMaintainFarmland(serverLevel, blockPos)) {
                turnToDirt((Entity)null, blockState, serverLevel, blockPos);
            }
        } else if (i < 7) {
            serverLevel.setBlock(blockPos, blockState.setValue(MOISTURE, Integer.valueOf(7)), 2);
        }
    }

    ///////////////////////////////////////////////////////////

    public static void turnToDirt(@javax.annotation.Nullable Entity pEntity, BlockState pState, Level pLevel, BlockPos pPos) {
        BlockState blockstate = pushEntitiesUp(pState, DIRT_SLAB_BLOCK.get().defaultBlockState().setValue(TYPE, pState.getValue(TYPE)), pLevel, pPos);
        pLevel.setBlockAndUpdate(pPos, blockstate);
        pLevel.gameEvent(GameEvent.BLOCK_CHANGE, pPos, GameEvent.Context.of(pEntity, blockstate));
    }

    @Override
    public void fallOn(Level pLevel, BlockState pState, BlockPos pPos, Entity pEntity, float pFallDistance) {
        if (!pLevel.isClientSide && onFarmlandTrample(pLevel, pPos, Blocks.DIRT.defaultBlockState(), pFallDistance, pEntity)) { // Forge: Move logic to Entity#canTrample
            turnToDirt(pEntity, pState, pLevel, pPos);
        }
        super.fallOn(pLevel, pState, pPos, pEntity, pFallDistance);
    }

    private static boolean shouldMaintainFarmland(BlockGetter pLevel, BlockPos pPos) {
        BlockState plant = pLevel.getBlockState(pPos.above());
        BlockState blockState = pLevel.getBlockState(pPos);
        return plant.getBlock() instanceof IPlantable && blockState.canSustainPlant(pLevel, pPos, Direction.UP, (IPlantable)plant.getBlock());
    }

    private static boolean isNearWater(LevelReader pLevel, BlockPos pPos) {
        BlockState state = pLevel.getBlockState(pPos);
        for(BlockPos blockpos : BlockPos.betweenClosed(pPos.offset(-4, 0, -4), pPos.offset(4, 1, 4))) {
            if (state.canBeHydrated(pLevel, pPos, pLevel.getFluidState(blockpos), blockpos)) {
                return true;
            }
        }
        return FarmlandWaterManager.hasBlockWaterTicket(pLevel, pPos);
    }

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }
}
