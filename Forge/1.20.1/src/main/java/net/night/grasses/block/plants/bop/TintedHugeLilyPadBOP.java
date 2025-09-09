package net.night.grasses.block.plants.bop;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.night.grasses.block.plants.superclasses.ParentTintedBushBlock;
import net.night.grasses.enums.GrassesQuarterProperty;

import javax.annotation.Nullable;

import java.util.List;

import static biomesoplenty.api.block.BOPBlocks.HUGE_LILY_PAD;
import static net.night.grasses.Grasses.isBOPLoaded;
import static net.night.grasses.data.ModMethods.*;
import static net.night.grasses.data.ModMethods.prepareDropWithColor;
import static net.night.grasses.init.BlocksRegisterBoP.*;

public class TintedHugeLilyPadBOP extends ParentTintedBushBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    protected static final VoxelShape AABB = Block.box(0.0, 0.0, 0.0, 16.0, 1.5, 16.0);

    public TintedHugeLilyPadBOP() {
        super(Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).instabreak().sound(SoundType.LILY_PAD).noOcclusion());
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(GRASSES_QUARTER, GrassesQuarterProperty.SOUTH_WEST).setValue(VARIANT_LILY, 0));
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext context) {
        return AABB;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, GRASSES_QUARTER, VARIANT_LILY);
    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState facingState, LevelAccessor level, BlockPos blockPos, BlockPos facingPos) {
        boolean lilypadSurvive = true;
        Direction facing = blockState.getValue(FACING);
        BlockPos sw = blockPos;
        BlockPos nw = blockPos.relative(facing);
        BlockPos ne = nw.relative(facing.getClockWise());
        BlockPos se = blockPos.relative(facing.getClockWise());
        if (blockState.getValue(GRASSES_QUARTER) == GrassesQuarterProperty.SOUTH_WEST) {
            sw = blockPos;
            nw = blockPos.relative(facing);
            ne = nw.relative(facing.getClockWise());
            se = blockPos.relative(facing.getClockWise());
        }

        if (blockState.getValue(GRASSES_QUARTER) == GrassesQuarterProperty.NORTH_WEST) {
            sw = blockPos.relative(facing.getOpposite());
            nw = blockPos;
            ne = blockPos.relative(facing.getClockWise());
            se = sw.relative(facing.getClockWise());
        }

        if (blockState.getValue(GRASSES_QUARTER) == GrassesQuarterProperty.NORTH_EAST) {
            nw = blockPos.relative(facing.getCounterClockWise());
            ne = blockPos;
            se = blockPos.relative(facing.getOpposite());
            sw = se.relative(facing);
        }

        if (blockState.getValue(GRASSES_QUARTER) == GrassesQuarterProperty.SOUTH_EAST) {
            sw = blockPos.relative(facing.getCounterClockWise());
            ne = blockPos.relative(facing);
            se = blockPos;
            nw = ne.relative(facing.getCounterClockWise());
        }

        if (!level.getBlockState(sw).is(this) || !level.getBlockState(nw).is(this) || !level.getBlockState(ne).is(this) || !level.getBlockState(se).is(this)) {
            lilypadSurvive = false;
        }

        if (level.getBlockState(sw).is(this) && level.getBlockState(sw).getValue(FACING) != facing && level.getBlockState(sw).getValue(GRASSES_QUARTER) != GrassesQuarterProperty.SOUTH_WEST) {
            lilypadSurvive = false;
        }

        if (level.getBlockState(nw).is(this) && level.getBlockState(nw).getValue(FACING) != facing && level.getBlockState(nw).getValue(GRASSES_QUARTER) != GrassesQuarterProperty.NORTH_WEST) {
            lilypadSurvive = false;
        }

        if (level.getBlockState(ne).is(this) && level.getBlockState(ne).getValue(FACING) != facing && level.getBlockState(ne).getValue(GRASSES_QUARTER) != GrassesQuarterProperty.NORTH_EAST) {
            lilypadSurvive = false;
        }

        if (level.getBlockState(se).is(this) && level.getBlockState(se).getValue(FACING) != facing && level.getBlockState(se).getValue(GRASSES_QUARTER) != GrassesQuarterProperty.SOUTH_EAST) {
            lilypadSurvive = false;
        }

        return !lilypadSurvive ? Blocks.AIR.defaultBlockState() : super.updateShape(blockState, direction, facingState, level, blockPos, facingPos);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getHorizontalDirection();
        BlockPos blockPosClicked = context.getClickedPos();
        BlockPos blockPos1 = blockPosClicked.relative(direction);
        BlockPos blockPos2 = blockPos1.relative(direction.getClockWise());
        BlockPos blockPos3 = blockPosClicked.relative(direction.getClockWise());
        Level level = context.getLevel();

        ItemStack itemStack = context.getItemInHand();
        if (isBOPLoaded) {
            keepData(blockPosClicked, HUGE_LILY_PAD, getColorTypeFromNBT(itemStack));
            keepData(blockPos1, HUGE_LILY_PAD, keepColorType.get(blockPosClicked));
            keepData(blockPos2, HUGE_LILY_PAD, keepColorType.get(blockPosClicked));
            keepData(blockPos3, HUGE_LILY_PAD, keepColorType.get(blockPosClicked));
        } else {
            keepColorType.put(blockPosClicked, getColorTypeFromNBT(itemStack));
            keepColorType.put(blockPos1, keepColorType.get(blockPosClicked));
            keepColorType.put(blockPos2, keepColorType.get(blockPosClicked));
            keepColorType.put(blockPos3, keepColorType.get(blockPosClicked));
        }

        return this.mayPlaceOn(level.getBlockState(blockPos1.below()), level, blockPos1.below()) && level.getBlockState(blockPos1).canBeReplaced(context) &&
                level.getWorldBorder().isWithinBounds(blockPos1) && this.mayPlaceOn(level.getBlockState(blockPos2.below()), level, blockPos2.below()) &&
                level.getBlockState(blockPos2).canBeReplaced(context) &&
                level.getWorldBorder().isWithinBounds(blockPos2) && this.mayPlaceOn(level.getBlockState(blockPos3.below()), level, blockPos3.below()) &&
                level.getBlockState(blockPos3).canBeReplaced(context) && level.getWorldBorder().isWithinBounds(blockPos3) ? this.defaultBlockState().setValue(FACING, direction) : null;
    }

    @Override
    public List<ItemStack> getDrops(BlockState blockState, LootParams.Builder builder) {

        if (blockState.getValue(GRASSES_QUARTER).equals(GrassesQuarterProperty.SOUTH_EAST))
            return prepareDropWithColor(super.getDrops(blockState, builder), builder, WATERLILY_TINTED.get().asItem());
        else
            return prepareDropWithColor(super.getDrops(blockState, builder), builder, this.asItem());
    }

    @Override
    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
        super.setPlacedBy(level, blockPos, blockState, livingEntity, itemStack);
        if (!level.isClientSide) {
            BlockPos blockpos = blockPos.relative(blockState.getValue(FACING));
            BlockPos blockpos1 = blockpos.relative((blockState.getValue(FACING)).getClockWise());
            BlockPos blockpos2 = blockPos.relative((blockState.getValue(FACING)).getClockWise());
            level.setBlock(blockpos, blockState.setValue(GRASSES_QUARTER, GrassesQuarterProperty.NORTH_WEST), 26);
            level.setBlock(blockpos1, blockState.setValue(GRASSES_QUARTER, GrassesQuarterProperty.NORTH_EAST), 26);
            level.setBlock(blockpos2, blockState.setValue(GRASSES_QUARTER, GrassesQuarterProperty.SOUTH_EAST), 26);
            level.blockUpdated(blockPos, Blocks.AIR);
            level.blockUpdated(blockpos, Blocks.AIR);
            level.blockUpdated(blockpos1, Blocks.AIR);
            level.blockUpdated(blockpos2, Blocks.AIR);
            blockState.updateNeighbourShapes(level, blockPos, 26);
            blockState.updateNeighbourShapes(level, blockpos, 26);
            blockState.updateNeighbourShapes(level, blockpos1, 26);
            blockState.updateNeighbourShapes(level, blockpos2, 26);
        }

    }
    @Override
    public void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
        super.entityInside(blockState, level, blockPos, entity);
        if (level instanceof ServerLevel && entity instanceof Boat) {
            level.destroyBlock(new BlockPos(blockPos), true, entity);
        }
    }
    @Override
    protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        FluidState fluidstate = blockGetter.getFluidState(blockPos);
        FluidState fluidstate1 = blockGetter.getFluidState(blockPos.above());
        return (fluidstate.getType() == Fluids.WATER || blockState.getBlock() instanceof IceBlock) && fluidstate1.getType() == Fluids.EMPTY;
    }
    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
    }
    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.rotate(mirror.getRotation(blockState.getValue(FACING)));
    }
    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return false;
    }
}
