package net.night.grasses.block.plants.bop;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.NetherVines;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.night.grasses.block.plants.superclasses.ParentTintedGrowingPlantHeadBlock;
import net.night.grasses.config.GrassesConfig;

import static biomesoplenty.api.block.BOPBlocks.HIGH_GRASS;
import static biomesoplenty.api.block.BOPBlocks.HIGH_GRASS_PLANT;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.SLAB_TYPE;
import static net.night.grasses.init.BlocksRegisterBoP.HIGH_GRASS_PLANT_TINTED;

public class TintedHighGrassBOP extends ParentTintedGrowingPlantHeadBlock {
    public static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);
    public static final int MAX_AGE = 8;
    private final double growPerTickProbability = 0.01;

    public TintedHighGrassBOP() {
        super(Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).randomTicks().replaceable().ignitedByLava().noCollission().instabreak()
                .sound(SoundType.GRASS).offsetType(OffsetType.XZ), Direction.UP, SHAPE, false, 0.01);
    }
    @Override
    public BlockState getStateForPlacement(LevelAccessor level) {

        return this.defaultBlockState().setValue(AGE, level.getRandom().nextInt(MAX_AGE));
    }
    @Override
    public boolean isRandomlyTicking(BlockState blockState) {
        return blockState.getValue(AGE) < MAX_AGE;
    }
    @Override
    public BlockState getMaxAgeState(BlockState blockState) {
        return blockState.setValue(AGE, MAX_AGE);
    }
    @Override
    public boolean isMaxAge(BlockState blockState) {
        return blockState.getValue(AGE) == MAX_AGE;
    }
    @Override
    protected int getBlocksToGrowWhenBonemealed(RandomSource randomSource) {
        return NetherVines.getBlocksToGrowWhenBonemealed(randomSource);
    }

    protected boolean canGrowInto(BlockState blockState) {
        return NetherVines.isValidGrowthState(blockState);
    }

    protected Block getBodyBlock() {
        return HIGH_GRASS_PLANT_TINTED.get();
    }
    @Override
    public boolean canSurvive(BlockState blockState, LevelReader level, BlockPos blockPos) {
        BlockPos blockPosOpposite = blockPos.relative(this.growthDirection.getOpposite());
        BlockState blockStateOpposite = level.getBlockState(blockPosOpposite);
        Block block = blockStateOpposite.getBlock();

        if (blockStateOpposite.hasProperty(SLAB_TYPE) && blockStateOpposite.getValue(SLAB_TYPE) == SlabType.BOTTOM && !GrassesConfig.CommonConfig.ALLOW_PUT_PLANTS_ON_BOTTOM_SLAB.get())
            return false;
        else if (!this.canAttachTo(blockStateOpposite)) {
            return false;
        } else {
            return block == this.getHeadBlock() || block == this.getBodyBlock() || blockStateOpposite.is(BlockTags.DIRT) || blockStateOpposite.is(HIGH_GRASS) || blockStateOpposite.is(HIGH_GRASS_PLANT);
        }
    }
}
