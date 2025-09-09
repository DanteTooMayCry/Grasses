package net.night.grasses.block.falling;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.material.Fluids;
import net.night.grasses.block.otherSlabs.superclassses.ParentSlabBlock;
import net.night.grasses.interfaces.FallingBlockDust;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static net.minecraft.world.level.block.Blocks.AIR;

public class FallingSlabBlock extends ParentSlabBlock implements Fallable, FallingBlockDust {
    private final int dustColor;
    public FallingSlabBlock(int pDustColor, Properties pProperties) {
        super(pProperties);
        this.dustColor = pDustColor;
        this.registerDefaultState(this.defaultBlockState().setValue(TYPE, SlabType.BOTTOM).setValue(WATERLOGGED, Boolean.valueOf(false)));
    }

    @Override
    public int getDustColor(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return this.dustColor;
    }
    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState oldBlockState, boolean isMoving) {

        BlockState blockStateBelow = level.getBlockState(blockPos.below());

        if (oldBlockState.is(AIR)) {
            if (blockStateBelow.is(blockState.getBlock()) && blockStateBelow.getValue(TYPE).equals(SlabType.BOTTOM)) {
                level.setBlock(blockPos, AIR.defaultBlockState(), 3);
                level.setBlockAndUpdate(blockPos.below(), blockStateBelow.setValue(TYPE, SlabType.DOUBLE));
            } else if (blockStateBelow.is(blockState.getBlock()) && blockStateBelow.getValue(TYPE).equals(SlabType.DOUBLE)) {
                level.setBlock(blockPos, blockState.setValue(TYPE, SlabType.BOTTOM), 3);
            }
        }
        level.scheduleTick(blockPos, this, this.getDelayAfterPlace());
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pState.getValue(WATERLOGGED))
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        else
            pLevel.scheduleTick(pCurrentPos, this, this.getDelayAfterPlace());

        return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    @Override
    public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {

        if (isFree(serverLevel.getBlockState(blockPos.below())) && blockPos.getY() >= serverLevel.getMinBuildHeight()) {

            FallingBlockEntity fallingBlockEntity = FallingBlockEntity.fall(serverLevel, blockPos, blockState);
            fallingBlockEntity.disableDrop();
            // Because falling block always broken when fall on bottom slab, disable drop and made bypass in "onBrokenAfterFall" method for connecting slabs and broke only in specific case

        }
    }

    @Override
    public void onBrokenAfterFall(Level level, @NotNull BlockPos blockPos, FallingBlockEntity fallingBlockEntity) {

        BlockState blockState = level.getBlockState(blockPos);
        BlockState fallingBlockState = fallingBlockEntity.getBlockState();

        if (fallingBlockState.is(blockState.getBlock()) && blockState.getValue(TYPE).equals(SlabType.BOTTOM))
            level.setBlock(blockPos, blockState.setValue(TYPE, SlabType.DOUBLE), 3);
        else if (blockState.is(AIR)) {
            if (fallingBlockEntity.getBlockState().getValue(TYPE).equals(SlabType.DOUBLE))
                level.setBlock(blockPos, fallingBlockState.setValue(TYPE, SlabType.DOUBLE), 3);
            else
                level.setBlock(blockPos, fallingBlockState.setValue(TYPE, SlabType.BOTTOM), 3);
        } else
            Block.popResourceFromFace(level, blockPos, Objects.requireNonNull(Direction.UP), new ItemStack(fallingBlockState.getBlock()));
    }

    protected void falling(FallingBlockEntity pEntity) {
    }

    protected int getDelayAfterPlace() {
        return 2;
    }

    public static boolean isFree(BlockState pState) {
        return pState.isAir() || pState.is(BlockTags.FIRE) || pState.liquid() || pState.canBeReplaced();
    }
    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pRandom.nextInt(16) == 0) {
            BlockPos blockpos = pPos.below();
            if (isFree(pLevel.getBlockState(blockpos))) {
                ParticleUtils.spawnParticleBelow(pLevel, pPos, pRandom, new BlockParticleOption(ParticleTypes.FALLING_DUST, pState));
            }
        }
    }
}
