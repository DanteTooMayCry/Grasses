package net.night.grasses.block.plants.bop;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.night.grasses.block.plants.superclasses.ParentTintedBushBlock;
import net.night.grasses.config.GrassesConfig;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.SLAB_TYPE;
import static net.night.grasses.data.ModMethods.isFertileState;
import static net.night.grasses.init.BlocksRegister.FERTILE;

public class TintedTinyCactusBOP extends ParentTintedBushBlock implements BonemealableBlock {
    protected static final VoxelShape NORMAL = Block.box(3.0, 0.0, 3.0, 13.0, 13.0, 13.0);

    public TintedTinyCactusBOP() {
        super(Properties.of().pushReaction(PushReaction.DESTROY).mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.WOOL).offsetType(OffsetType.XZ));
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext context) {
        return NORMAL;
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader level, BlockPos blockPos) {

        BlockState blockStateBelow = level.getBlockState(blockPos.below());
        if (blockStateBelow.hasProperty(SLAB_TYPE) && blockStateBelow.getValue(SLAB_TYPE) == SlabType.BOTTOM && !GrassesConfig.COMMON_CONFIG.ALLOW_PUT_PLANTS_ON_BOTTOM_SLAB.get())
            return false;

        return blockStateBelow.is(BlockTags.DIRT) || blockStateBelow.is(BlockTags.SAND);
    }

    @Override
    public void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
        if (entity instanceof Player playerEntity) {
            playerEntity.hurt(level.damageSources().cactus(), 1.0F);
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
