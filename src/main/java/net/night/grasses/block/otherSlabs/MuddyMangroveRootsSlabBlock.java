package net.night.grasses.block.otherSlabs;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.IPlantable;

import javax.annotation.Nullable;

import static net.night.grasses.data.ModMethods.canSustainPlantOnDirtLike;
import static net.night.grasses.init.BlocksRegister.ROTATE_MUDDY;

public class MuddyMangroveRootsSlabBlock extends SlabBlock {
    public MuddyMangroveRootsSlabBlock() {
        super(Properties.ofFullCopy(Blocks.MUDDY_MANGROVE_ROOTS));
        this.registerDefaultState(this.defaultBlockState().setValue(ROTATE_MUDDY, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(ROTATE_MUDDY);
    }

    ///////////////////////////////////////////////////////////

    @Override
    public boolean canSustainPlant(BlockState blockState, BlockGetter world, BlockPos blockPos, Direction facing, IPlantable plantable) {
        return canSustainPlantOnDirtLike(blockState, world, blockPos, facing, plantable);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {

        Direction.Axis rotation = context.getClickedFace().getAxis();
        boolean isSprintKeyPush = Minecraft.getInstance().options.keySprint.isDown();
        int rotate_muddy = 0;

        if (rotation == Direction.Axis.X)
            rotate_muddy = 1;
        else if (rotation == Direction.Axis.Z)
            rotate_muddy = 2;


        BlockPos $$1 = context.getClickedPos();
        BlockState $$2 = context.getLevel().getBlockState($$1);
        if ($$2.is(this)) {
            return (BlockState)((BlockState)$$2.setValue(TYPE, SlabType.DOUBLE)).setValue(WATERLOGGED, false).setValue(ROTATE_MUDDY, rotate_muddy);
        } else {
            FluidState $$3 = context.getLevel().getFluidState($$1);
            BlockState $$4 = (BlockState)((BlockState)this.defaultBlockState().setValue(TYPE, SlabType.BOTTOM)).setValue(WATERLOGGED, $$3.getType() == Fluids.WATER).setValue(ROTATE_MUDDY, rotate_muddy);
            Direction $$5 = context.getClickedFace();
            return !isSprintKeyPush && $$5 != Direction.DOWN && ($$5 == Direction.UP || !(context.getClickLocation().y - (double)$$1.getY() > 0.5)) ? $$4 : (BlockState)$$4.setValue(TYPE, SlabType.TOP).setValue(ROTATE_MUDDY, rotate_muddy);
        }
    }


}
