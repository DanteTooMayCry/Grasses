package net.night.grasses.block.otherSlabs;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IPlantable;
import net.night.grasses.block.otherSlabs.superclassses.ParentSlabBlock;

import static net.minecraft.world.level.block.PointedDripstoneBlock.TIP_DIRECTION;
import static net.minecraft.world.level.block.state.properties.SlabType.BOTTOM;
import static net.night.grasses.data.ModMethods.canSustainPlantOnDirtLike;
import static net.night.grasses.init.BlocksRegister.CLAY_SLAB_BLOCK;

public class MudSlabBlock extends ParentSlabBlock {
    protected static final VoxelShape BOTTOM_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D);
    protected static final VoxelShape TOP_SHAPE = Block.box(0.0D, 8.0D, 0.0D, 16.0D, 14.0D, 16.0D);
    protected static final VoxelShape DOUBLE_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D);

    public MudSlabBlock() {
        super(Properties.copy(Blocks.MUD));
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return true;
    }

    ///////////////////////////////////////////////////////////

    @Override
    public boolean canSustainPlant(BlockState blockState, BlockGetter world, BlockPos blockPos, Direction facing, IPlantable plantable) {
        return canSustainPlantOnDirtLike(blockState, world, blockPos, facing, plantable);
    }

    ///////////////////////////////////////////////////////////


    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {

        BlockState blockStateTwoBelow = serverLevel.getBlockState(blockPos.offset(0, -2, 0));

        if (blockStateTwoBelow.is(Blocks.POINTED_DRIPSTONE) && blockStateTwoBelow.getValue(TIP_DIRECTION).equals(Direction.DOWN)) {

            float random = randomSource.nextFloat();

            if (random < 0.17578125F)
                serverLevel.setBlockAndUpdate(blockPos, CLAY_SLAB_BLOCK.get().defaultBlockState().setValue(TYPE, blockState.getValue(TYPE)));

        }


        super.tick(blockState, serverLevel, blockPos, randomSource);
    }

    ///////////////////////////////////////////////////////////

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

    public VoxelShape getBlockSupportShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return Shapes.block(); // => ??
    }

    public VoxelShape getVisualShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext context) {
        return Shapes.block(); // => ??
    }

    public boolean isPathfindable(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, PathComputationType pathComputationType) {
        return false;
    }

    public float getShadeBrightness(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        if (!blockState.getValue(TYPE).equals(BOTTOM)) {
            return 0.2F;
        }
        return 1.0f;
    }
}
