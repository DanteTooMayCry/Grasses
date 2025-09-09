package net.night.grasses.block.otherSlabs;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.night.grasses.block.otherSlabs.superclassses.ParentSlabBlock;

public class MagmaSlabBlock extends ParentSlabBlock {
    private static final int BUBBLE_COLUMN_CHECK_DELAY = 20;
    public MagmaSlabBlock() {
        super(Properties.copy(Blocks.MAGMA_BLOCK));
    }

    @Override
    public void stepOn(Level level, BlockPos blockPos, BlockState blockState, Entity entity) {
        if (!entity.isSteppingCarefully() && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)entity)) {
            entity.hurt(level.damageSources().hotFloor(), 1.0F);
        }

        super.stepOn(level, blockPos, blockState, entity);
    }

    @Override
    public void tick(BlockState blockState, ServerLevel level, BlockPos blockPos, RandomSource randomSource) {
        BubbleColumnBlock.updateColumn(level, blockPos.above(), blockState);
    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState facingBlockState, LevelAccessor levelAccessor, BlockPos currentBlockPos, BlockPos facingBlockPos) {
        if (blockState.getValue(WATERLOGGED)) {
            levelAccessor.scheduleTick(currentBlockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        }
        if (direction == Direction.UP && facingBlockState.is(Blocks.WATER)) {
            levelAccessor.scheduleTick(currentBlockPos, this, 20);
        }

        return super.updateShape(blockState, direction, facingBlockState, levelAccessor, currentBlockPos, facingBlockPos);
    }

    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState oldBlockState, boolean pIsMoving) {
        level.scheduleTick(blockPos, this, 20);
    }
}
