package net.night.grasses.block.otherSlabs;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.IPlantable;
import net.neoforged.neoforge.common.PlantType;
import net.night.grasses.block.otherSlabs.superclassses.ParentSlabBlock;
import net.night.grasses.config.GrassesConfig;

import static net.minecraft.world.level.block.state.properties.SlabType.BOTTOM;

public class SoulSandSlabBlock extends ParentSlabBlock implements BonemealableBlock {
    protected static final VoxelShape BOTTOM_SHAPE  = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D);
    protected static final VoxelShape TOP_SHAPE     = Block.box(0.0D, 8.0D, 0.0D, 16.0D, 14.0D, 16.0D);
    protected static final VoxelShape DOUBLE_SHAPE  = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D);
    private static final int BUBBLE_COLUMN_CHECK_DELAY = 20;

    public SoulSandSlabBlock() {
        super(Properties.ofFullCopy(Blocks.SOUL_SAND));
    }

    ///////////////////////////////////////////////////////////

    @Override
    public boolean canSustainPlant(BlockState blockState, BlockGetter world, BlockPos blockPos, Direction facing, IPlantable plantable) {
        if (!blockState.getValue(TYPE).equals(BOTTOM) || GrassesConfig.COMMON_CONFIG.ALLOW_PUT_PLANTS_ON_BOTTOM_SLAB.get()) {

            PlantType plantType = plantable.getPlantType(world, blockPos);

            if (plantType == PlantType.NETHER)
                return true;
            else
                return false;
        }
        return false;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        if ((blockState.getValue(TYPE).equals(BOTTOM) && !GrassesConfig.COMMON_CONFIG.ALLOW_PUT_PLANTS_ON_BOTTOM_SLAB.get()) || !GrassesConfig.COMMON_CONFIG.ALLOW_USE_BONE_MEAL_ON_MOD_SOUL_SAND.get())
            return false;
        else
            return levelReader.getBlockState(blockPos.above()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        return (!blockState.getValue(TYPE).equals(BOTTOM) || GrassesConfig.COMMON_CONFIG.ALLOW_PUT_PLANTS_ON_BOTTOM_SLAB.get()) && GrassesConfig.COMMON_CONFIG.ALLOW_USE_BONE_MEAL_ON_MOD_SOUL_SAND.get();

    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {

        //performBonemeal
        // ModEventsMethodsLib.class => instanceof BoneMealItem: used method for vanilla blocks of podzol, mycelium & soulsand
    }

    @Override
    public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        BubbleColumnBlock.updateColumn(serverLevel, blockPos.above(), blockState);
    }

    ///////////////////////////////////////////////////////////

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        pLevel.scheduleTick(pPos, this, 20);
    }

    @Override
    public float getShadeBrightness(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        if (!blockState.getValue(TYPE).equals(BOTTOM)) {
            return 0.2F;
        }
        return 1.0f;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext context) {
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
    public VoxelShape getBlockSupportShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return Shapes.block(); // => ??
    }

    @Override
    public VoxelShape getVisualShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext context) {
        return Shapes.block(); // => ??
    }

    public BlockState updateShape(BlockState blockState, Direction direction, BlockState facingBlockState, LevelAccessor levelAccessor, BlockPos currentBlockPos, BlockPos facingBlockPos) {
        if (blockState.getValue(WATERLOGGED)) {
            levelAccessor.scheduleTick(currentBlockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        }
        if (direction == Direction.UP && facingBlockState.is(Blocks.WATER)) {
            levelAccessor.scheduleTick(currentBlockPos, this, 20);
        }

        return super.updateShape(blockState, direction, facingBlockState, levelAccessor, currentBlockPos, facingBlockPos);
    }

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }
}
