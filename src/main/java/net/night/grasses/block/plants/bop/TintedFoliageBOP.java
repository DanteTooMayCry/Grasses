package net.night.grasses.block.plants.bop;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.night.grasses.block.plants.superclasses.ParentTintedBushBlock;
import net.night.grasses.config.GrassesConfig;

import static net.night.grasses.data.ModMethods.isFertileState;
import static net.night.grasses.init.BlocksRegister.FERTILE;
import static net.night.grasses.init.BlocksRegisterBoP.SPROUT_TINTED;

public class TintedFoliageBOP extends ParentTintedBushBlock implements BonemealableBlock {
    protected static final VoxelShape NORMAL = Block.box(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);

    public TintedFoliageBOP() {
        super(Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).replaceable().ignitedByLava().noCollission().instabreak().sound(SoundType.GRASS).offsetType(OffsetType.XYZ));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext selectionContext) {
        return NORMAL;
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader level, BlockPos blockPos) {
        BlockState BlockStateBelow = level.getBlockState(blockPos.below());

        if (blockState.is(SPROUT_TINTED.get())) {
            return BlockStateBelow.isFaceSturdy(level, blockPos.below(), Direction.UP) || super.canSurvive(blockState, level, blockPos);
        } else {
            return super.canSurvive(blockState, level, blockPos);
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        if (!GrassesConfig.COMMON_CONFIG.ALLOW_USE_BONE_MEAL_ON_BOP_PLANTS.get())
            return false;
        else
            return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        if(!isFertileState(blockState)) {
            serverLevel.setBlock(blockPos, blockState.setValue(FERTILE, true), 3);
        }
    }
}