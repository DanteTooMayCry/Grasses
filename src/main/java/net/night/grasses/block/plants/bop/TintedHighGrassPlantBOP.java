package net.night.grasses.block.plants.bop;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.night.grasses.block.plants.superclasses.ParentTintedGrowingPlantBodyBlock;

import static biomesoplenty.api.block.BOPBlocks.HIGH_GRASS;
import static biomesoplenty.api.block.BOPBlocks.HIGH_GRASS_PLANT;
import static net.night.grasses.init.BlocksRegisterBoP.HIGH_GRASS_TINTED;

public class TintedHighGrassPlantBOP extends ParentTintedGrowingPlantBodyBlock {
    public static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);
    public TintedHighGrassPlantBOP() {
        super(Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).replaceable().ignitedByLava().noCollission().instabreak().sound(SoundType.GRASS).offsetType(OffsetType.XZ), Direction.UP, SHAPE, false);
    }

    protected GrowingPlantHeadBlock getHeadBlock() {
        return (GrowingPlantHeadBlock) HIGH_GRASS_TINTED.get();
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader level, BlockPos blockPos) {
        BlockPos blockPosOpposite = blockPos.relative(this.growthDirection.getOpposite());
        BlockState blockStateOpposite = level.getBlockState(blockPosOpposite);
        Block block = blockStateOpposite.getBlock();
        if (!this.canAttachTo(blockStateOpposite)) {
            return false;
        } else {
            return block == this.getHeadBlock() || block == this.getBodyBlock() || blockStateOpposite.is(BlockTags.DIRT) || blockStateOpposite.is(HIGH_GRASS) || blockStateOpposite.is(HIGH_GRASS_PLANT);
        }
    }

    @Override
    public void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
        entity.setDeltaMovement(entity.getDeltaMovement().multiply(0.5, 1.0, 0.5));
    }
}
