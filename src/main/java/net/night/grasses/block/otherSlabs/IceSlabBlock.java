package net.night.grasses.block.otherSlabs;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.night.grasses.block.otherSlabs.superclassses.ParentSlabBlock;

import javax.annotation.Nullable;

public class IceSlabBlock extends ParentSlabBlock {
    public IceSlabBlock() {
        super(Properties.ofFullCopy(Blocks.ICE).noOcclusion());
    }

    @Override
    public boolean skipRendering(BlockState pState, BlockState pAdjacentBlockState, Direction pSide) {

        if (pAdjacentBlockState.is(Blocks.ICE) && ((pState.getValue(TYPE) == SlabType.BOTTOM && pSide != Direction.UP) || (pState.getValue(TYPE) == SlabType.TOP && pSide != Direction.DOWN) || pState.getValue(TYPE) == SlabType.DOUBLE))
            return true;
        else if (pState.getValue(TYPE) == SlabType.DOUBLE && pAdjacentBlockState.hasProperty(TYPE) && pAdjacentBlockState.getValue(TYPE) != SlabType.DOUBLE)
            return false;
        else if (pState.getValue(TYPE) == SlabType.BOTTOM && pAdjacentBlockState.hasProperty(TYPE) && ( pAdjacentBlockState.getValue(TYPE) == SlabType.TOP || (pAdjacentBlockState.getValue(TYPE) == SlabType.BOTTOM && (pSide == Direction.DOWN || pSide == Direction.UP))))
            return false;
        else if (pState.getValue(TYPE) == SlabType.TOP && pAdjacentBlockState.hasProperty(TYPE) && (pAdjacentBlockState.getValue(TYPE) == SlabType.BOTTOM || (pAdjacentBlockState.getValue(TYPE) == SlabType.TOP && (pSide == Direction.DOWN || pSide == Direction.UP))))
            return false;

        return pAdjacentBlockState.is(this) ? true : super.skipRendering(pState, pAdjacentBlockState, pSide);
    }

    public static BlockState meltsInto() {
        return Blocks.WATER.defaultBlockState();
    }

    protected void melt(BlockState blockState, Level level, BlockPos blockPos) {
        if (level.dimensionType().ultraWarm()) {
            level.removeBlock(blockPos, false);
        } else {
            level.setBlockAndUpdate(blockPos, meltsInto());
            level.neighborChanged(blockPos, meltsInto().getBlock(), blockPos);
        }
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos blockPos, BlockState blockState, @Nullable BlockEntity blockEntity, ItemStack itemStack) {
        super.playerDestroy(level, player, blockPos, blockState, blockEntity, itemStack);
        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, itemStack) == 0) {
            if (level.dimensionType().ultraWarm()) {
                level.removeBlock(blockPos, false);
                return;
            }

            BlockState blockstate = level.getBlockState(blockPos.below());
            if (blockstate.blocksMotion() || blockstate.liquid()) {
                level.setBlockAndUpdate(blockPos, meltsInto());
            }
        }
    }

    @Override
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {

        if (serverLevel.getBrightness(LightLayer.BLOCK, blockPos) > 11 - blockState.getLightBlock(serverLevel, blockPos)) {
            this.melt(blockState, serverLevel, blockPos);
        }

    }
}
