package net.night.grasses.block.leaves;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

import static net.night.grasses.init.BlocksRegisterBoP.MOSSY;

public class TintedWillowLeavesBlock extends TintedLeavesBlock {

    public TintedWillowLeavesBlock() {
        super();
        this.registerDefaultState(this.defaultBlockState().setValue(MOSSY, Boolean.FALSE));

    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(MOSSY);
    }
}
