package net.night.grasses.block.plants;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.Shapes;
import net.night.grasses.block.plants.superclasses.ParentTintedGrowingPlantBodyBlock;
import org.jetbrains.annotations.Nullable;

import static net.night.grasses.init.BlocksRegister.KELP_TINTED;

public class TintedKelpPlant extends ParentTintedGrowingPlantBodyBlock implements LiquidBlockContainer {
    public TintedKelpPlant() {
        super(Properties.ofFullCopy(Blocks.KELP_PLANT), Direction.UP, Shapes.block(), true);
    }

    protected GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock)KELP_TINTED.get();
    }

    @Override
    public FluidState getFluidState(BlockState pState) {
        return Fluids.WATER.getSource(false);
    }

    /*protected boolean canAttachTo(BlockState pState) {
        return this.getHeadBlock().canAttachTo(pState);
    }*/


    @Override
    public boolean canPlaceLiquid(@Nullable Player player, BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, Fluid fluid) {
        return false;
    }

    @Override
    public boolean placeLiquid(LevelAccessor pLevel, BlockPos pPos, BlockState pState, FluidState pFluidState) {
        return false;
    }
}
