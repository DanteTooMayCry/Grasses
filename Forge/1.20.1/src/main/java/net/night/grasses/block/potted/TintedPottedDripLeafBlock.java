package net.night.grasses.block.potted;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

import static net.minecraft.world.level.block.HorizontalDirectionalBlock.FACING;
import static net.night.grasses.init.BlocksRegister.SMALL_DRIP_LEAF_POTTED_TINTED;


public class TintedPottedDripLeafBlock extends TintedPottedPlantBlock {
    protected static final VoxelShape SHAPE_WEST = Block.box(9.0D, 0.0D, 5.0D, 15.0D, 6.0D, 11.0D);
    protected static final VoxelShape SHAPE_EAST = Block.box(1.0D, 0.0D, 5.0D, 7.0D, 6.0D, 11.0D);
    protected static final VoxelShape SHAPE_NORTH = Block.box(5.0D, 0.0D, 9.0D, 11.0D, 6.0D, 15.0D);
    protected static final VoxelShape SHAPE_SOUTH = Block.box(5.0D, 0.0D, 1.0D, 11.0D, 6.0D, 7.0D);

    public TintedPottedDripLeafBlock(@Nullable Supplier<FlowerPotBlock> emptyPot, Supplier<? extends Block> plant) {
        super(emptyPot, plant);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.EAST));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter level, BlockPos blockPos, CollisionContext context) {

        if (blockState.is(SMALL_DRIP_LEAF_POTTED_TINTED.get()))
            return super.getShape(blockState, level, blockPos, context);
        if (blockState.getValue(FACING).equals(Direction.SOUTH))
            return SHAPE_SOUTH;
        else if (blockState.getValue(FACING).equals(Direction.NORTH))
            return SHAPE_NORTH;
        else if (blockState.getValue(FACING).equals(Direction.WEST))
            return SHAPE_WEST;
        else
            return SHAPE_EAST;
    }
}
