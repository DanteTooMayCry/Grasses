package net.night.grasses.block.otherSlabs.superclassses;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import org.jetbrains.annotations.Nullable;

import static net.night.grasses.config.GrassesConfig.CommonConfig.ALLOW_PUT_GRASSES_TOP_SLAB_FIRST;
import static net.night.grasses.data.ModMethods.setTopSlab;

public class ParentSlabBlock extends SlabBlock {
    public ParentSlabBlock(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {

        Block block = this;
        boolean isSprintKeyPush = Minecraft.getInstance().options.keySprint.isDown();

        return ALLOW_PUT_GRASSES_TOP_SLAB_FIRST.get() ? setTopSlab(pContext, isSprintKeyPush, block) : super.getStateForPlacement(pContext);
    }

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return true;
    }
}
