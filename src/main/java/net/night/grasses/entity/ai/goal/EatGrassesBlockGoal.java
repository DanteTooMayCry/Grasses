package net.night.grasses.entity.ai.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.EatBlockGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;

import java.util.EnumSet;
import java.util.function.Predicate;

import static net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent;
import static net.night.grasses.data.MethodsLib.isFertileState;
import static net.night.grasses.init.BlocksRegister.*;
import static net.night.grasses.util.ModTags.Blocks.*;

public class EatGrassesBlockGoal extends EatBlockGoal {
    private static final Predicate<BlockState> IS_TALL_GRASS = BlockStatePredicate.forBlock(Blocks.GRASS)
                                                                .or(BlockStatePredicate.forBlock(GRASS_TINTED.get()));
    private static final int EAT_ANIMATION_TICKS = 40;
    private final Mob mob;
    private final Level level;
    private int  eatAnimationTick;

    public EatGrassesBlockGoal(Mob mob) {
        super(mob);
        this.mob = mob;
        this.level = mob.level();
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        if (this.mob.getRandom().nextInt(this.mob.isBaby() ? 50 : 1000) != 0) {
            return false;
        } else {
            BlockPos blockpos = this.mob.blockPosition();
            BlockState blockState = this.level.getBlockState(blockpos);
            if (IS_TALL_GRASS.test(blockState) && isFertileState(blockState)) {
                return true;
            } else {
                return this.level.getBlockState(blockpos.below()).is(ALL_MOD_GRASS);
            }
        }
    }

    @Override
    public void start() {
        this.eatAnimationTick = this.adjustedTickDelay(EAT_ANIMATION_TICKS);
        this.level.broadcastEntityEvent(this.mob, (byte)10);
        this.mob.getNavigation().stop();
    }
    @Override
    public void stop() {
        this.eatAnimationTick = 0;
    }
    @Override
    public boolean canContinueToUse() {
        return this.eatAnimationTick > 0;
    }
    @Override
    public int getEatAnimationTick() {
        return this.eatAnimationTick;
    }

    @Override
    public void tick() {
        this.eatAnimationTick = Math.max(0, this.eatAnimationTick - 1);
        if (this.eatAnimationTick == 4) {
            BlockPos blockpos = this.mob.blockPosition();
            BlockState blockState = this.level.getBlockState(blockpos);
            if (IS_TALL_GRASS.test(blockState) && isFertileState(blockState)) {
                if (getMobGriefingEvent(this.level, this.mob)) {
                    this.level.destroyBlock(blockpos, false);
                }
                this.mob.ate();

            } else {
                BlockPos blockPosBelow = blockpos.below();
                BlockState blockStateBelow = level.getBlockState(blockPosBelow);
                if (this.level.getBlockState(blockPosBelow).is(ALL_MOD_GRASS_BLOCKS) && blockStateBelow.getValue(FERTILE)) {
                    if (getMobGriefingEvent(this.level, this.mob)) {
                        this.level.levelEvent(2001, blockPosBelow, Block.getId(blockStateBelow.getBlock().defaultBlockState()));
                        this.level.setBlock(blockPosBelow, Blocks.DIRT.defaultBlockState(), 3);

                    }
                    this.mob.ate();
                }
                else if (this.level.getBlockState(blockPosBelow).is(ALL_MOD_GRASS_SLABS) && blockStateBelow.getValue(FERTILE)) {
                    if (getMobGriefingEvent(this.level, this.mob)) {
                        this.level.levelEvent(2001, blockPosBelow, Block.getId(blockStateBelow.getBlock().defaultBlockState()));
                        this.level.setBlock(blockPosBelow, DIRT_SLAB_BLOCK.get().defaultBlockState(), 3);

                    }
                    this.mob.ate();
                }
            }
        }
    }


}
