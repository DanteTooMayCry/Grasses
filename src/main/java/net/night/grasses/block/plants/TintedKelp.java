package net.night.grasses.block.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.night.grasses.block.plants.superclasses.ParentTintedGrowingPlantHeadBlock;
import net.night.grasses.colorManagers.ColorType;

import javax.annotation.Nullable;

import static net.night.grasses.data.MethodsLib.getColorTypeFromNBT;
import static net.night.grasses.init.BlocksRegister.*;

public class TintedKelp extends ParentTintedGrowingPlantHeadBlock implements LiquidBlockContainer {

    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D);
    public TintedKelp() {
        super(Properties.copy(Blocks.KELP), Direction.UP, SHAPE, true, 0.14D);
    }

    @Override
    protected boolean canGrowInto(BlockState pState) {
        return pState.is(Blocks.WATER);
    }

    protected Block getBodyBlock() {
        return KELP_PLANT_TINTED.get();
    }

    protected boolean canAttachTo(BlockState pState) {
        return !pState.is(Blocks.MAGMA_BLOCK);
    }

    @Override
    public boolean canPlaceLiquid(BlockGetter pLevel, BlockPos pPos, BlockState pState, Fluid pFluid) {
        return false;
    }

    @Override
    public boolean placeLiquid(LevelAccessor pLevel, BlockPos pPos, BlockState pState, FluidState pFluidState) {
        return false;
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(RandomSource pRandom) {
        return 1;
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {

        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return fluidstate.is(FluidTags.WATER) && fluidstate.getAmount() == 8 ? super.getStateForPlacement(context) : null;
    }

    @Override
    public FluidState getFluidState(BlockState pState) {
        return Fluids.WATER.getSource(false);
    }
}
