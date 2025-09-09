package net.night.grasses.block.plants.bop;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.night.grasses.block.plants.superclasses.ParentTintedBushBlock;

import javax.annotation.Nullable;

public class TintedLeafPileBOP extends ParentTintedBushBlock {
    protected static final VoxelShape NORMAL = Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0);

    public TintedLeafPileBOP() {
        super(Properties.of().noOcclusion().pushReaction(PushReaction.DESTROY).replaceable().noCollission().instabreak().sound(SoundType.CHERRY_LEAVES));
    }

    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext selectionContext) {
        return NORMAL;
    }

    public void playerDestroy(Level worldIn, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity te, ItemStack stack) {
        if (!worldIn.isClientSide && stack.getItem() == Items.SHEARS) {
            player.awardStat(Stats.BLOCK_MINED.get(this));
            player.causeFoodExhaustion(0.005F);
            popResource(worldIn, pos, new ItemStack(this));
        } else {
            super.playerDestroy(worldIn, player, pos, state, te, stack);
        }

    }

    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        BlockState groundState = worldIn.getBlockState(pos.below());
        return groundState.isFaceSturdy(worldIn, pos.below(), Direction.UP) || groundState.is(BlockTags.LEAVES) || super.canSurvive(state, worldIn, pos);
    }

}
