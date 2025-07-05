package net.night.grasses.block.leaves;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.night.grasses.block.leaves.superclasses.ParentTintedLeavesBlock;

import static net.night.grasses.init.BlocksRegister.ALTER;

public class TintedLeavesBlock extends ParentTintedLeavesBlock implements EntityBlock {

    public TintedLeavesBlock() {
        this.registerDefaultState(this.defaultBlockState().setValue(ALTER, Boolean.FALSE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(ALTER);
    }
}
