package net.night.grasses.block.plants.bop;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.night.grasses.block.plants.superclasses.ParentTintedBushBlock;

public class TintedWaterlilyBOP extends ParentTintedBushBlock {
    protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);

    public TintedWaterlilyBOP() {
        super(Properties.of().pushReaction(PushReaction.DESTROY).mapColor(MapColor.COLOR_PINK).noOcclusion().noCollission().instabreak().sound(SoundType.LILY_PAD).offsetType(OffsetType.XZ));
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter level, BlockPos blockPos, CollisionContext context) {
        return SHAPE;
    }
    @Override
    public void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
        super.entityInside(blockState, level, blockPos, entity);
        if (level instanceof ServerLevel && entity instanceof Boat) {
            level.destroyBlock(new BlockPos(blockPos), true, entity);
        }
    }
    @Override
    public boolean canSurvive(BlockState blockState, LevelReader level, BlockPos blockPos) {
        BlockPos blockPosBelow = blockPos.below();
        return this.mayPlaceOn(level.getBlockState(blockPosBelow), level, blockPosBelow);
    }
    @Override
    protected boolean mayPlaceOn(BlockState blockState, BlockGetter level, BlockPos blockPos) {
        FluidState fluidState = level.getFluidState(blockPos);
        FluidState fluidStateAbove = level.getFluidState(blockPos.above());
        return (fluidState.getType() == Fluids.WATER || blockState.getBlock() instanceof IceBlock) && fluidStateAbove.getType() == Fluids.EMPTY;
    }
}
