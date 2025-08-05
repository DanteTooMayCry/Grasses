package net.night.grasses.block.leaves.superclasses;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import net.night.grasses.block.blockEntity.TintedBlockEntity;
import net.night.grasses.config.GrassesConfig;
import net.night.grasses.event.ClientEventHandler;
import net.night.grasses.data.MethodsLib;
import net.night.grasses.colorManagers.ColorType;
import net.night.grasses.item.DyeingTool;
import net.night.grasses.particle.ModParticles;

import java.util.List;

import static net.night.grasses.data.DataLib.*;
import static net.night.grasses.colorManagers.ColorType.*;
import static net.night.grasses.data.MethodsLib.*;
import static net.night.grasses.init.BlocksRegister.*;
import static net.night.grasses.init.BlocksRegisterBoP.*;

public class ParentTintedLeavesBlock extends LeavesBlock implements EntityBlock {

    public ParentTintedLeavesBlock() {
        super(Properties.copy(Blocks.OAK_LEAVES));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new TintedBlockEntity(blockPos, blockState);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemStack, @javax.annotation.Nullable BlockGetter level, List<Component> tooltip, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, tooltip, tooltipFlag);
        additionalHoverText(itemStack, tooltip);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState blockState, HitResult hitResult, BlockGetter blockGetter, BlockPos blockPos, Player player) {

        return MethodsLib.getCloneItemStackBE((Level) blockGetter, blockPos, blockState);
    }

    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockStateOld, boolean pMovedByPiston) {

        onPlaceCounterPart(level, blockPos, blockState);
        super.onPlace(blockState, level, blockPos, blockStateOld, pMovedByPiston);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {

        getStateForPlacementCounterPart(context, matchingCounterpartsLeaves);
        return super.getStateForPlacement(context);
    }

    @Override
    public List<ItemStack> getDrops(BlockState blockState, LootParams.Builder builder) {
        return prepareDropWithColor(super.getDrops(blockState, builder), builder, this.asItem());
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 60;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 30;
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
        super.animateTick(blockState, level, blockPos, randomSource);

        boolean particleBlock = false;
        SimpleParticleType simpleParticleType = null;
        int i = 0;

        if (blockState.is(JACARANDA_LEAVES_BLOCK.get())) {
            particleBlock = true;
            simpleParticleType = ModParticles.JACARANDA_LEAVES_PARTICLE.get();
            i = 15;
        } else if (blockState.is(CHERRY_LEAVES_BLOCK.get())) {
            particleBlock = true;
            simpleParticleType = ModParticles.SNOWBLOSSOM_LEAVES_PARTICLE.get();
            i = 10;
        } else if (blockState.is(MAPLE_LEAVES_BLOCK.get())) {
            particleBlock = true;
            simpleParticleType = ModParticles.MAPLE_LEAVES_PARTICLE.get();
            i = 30;
        }

        if (particleBlock) {
            if (randomSource.nextInt(i) == 0)
            {
                BlockPos blockpos = blockPos.below();
                BlockState blockstate = level.getBlockState(blockpos);
                if (!isFaceFull(blockstate.getCollisionShape(level, blockpos), Direction.UP))
                {
                    ClientEventHandler.getColor(blockState, blockPos);
                    ParticleUtils.spawnParticleBelow(level, blockpos, randomSource, simpleParticleType);
                }
            }
        }
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {

        if (interactionHand != InteractionHand.MAIN_HAND)
            return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);

        ItemStack itemStack = player.getItemInHand(interactionHand);
        boolean usedDyeingTool = itemStack.getItem() instanceof DyeingTool && hasBlockStateTag(itemStack);
        boolean hasSilkTouch = EnchantmentHelper.hasSilkTouch(itemStack);

        if (usedDyeingTool && GrassesConfig.CommonConfig.ALLOW_CHANGE_LEAVES_COLOR_SEVERALLY.get()){
            CompoundTag compoundtag = itemStack.getTag();
            ColorType colorType = PLAINS;
            ColorType currentColor = getCurrentColor(level, blockPos, 0);

            if (compoundtag != null)
                colorType = ColorType.valueOf(compoundtag.getCompound("BlockStateTag").getString("color_type").toUpperCase());

            if (colorType != currentColor){
                if (player instanceof ServerPlayer) {
                    onlyChangeColor(level, blockPos, blockState, colorType, 0);
                    itemStack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(interactionHand));
                }
                level.playSound(null, blockPos, SoundEvents.AZALEA_LEAVES_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.addDestroyBlockEffect(blockPos, blockState);
            } else {
                level.playSound(null, blockPos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        else if (itemStack.getItem() instanceof ShearsItem && !hasSilkTouch){
            if (player instanceof ServerPlayer) {
                Block block = counterpartIDMap.get(getCounterpart(level, blockPos));
                if (block != null)
                    keepData(blockPos, block, getColorType(blockPos));
                level.setBlockAndUpdate(blockPos, blockState.setValue(ALTER, !blockState.getValue(ALTER)));
            }
            level.playSound(null, blockPos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(player, GameEvent.SHEAR, blockPos);
            itemStack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(interactionHand));
            level.addDestroyBlockEffect(blockPos, blockState);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        else if (itemStack.getItem() instanceof ShearsItem && hasSilkTouch && GrassesConfig.CommonConfig.ALLOW_CHANGE_TINTED_LEAVES_INTO_NOT_GRASSES_SEVERALLY.get()) { // If shears are used on a block of mod leaves, the block will be turned back into a vanilla leaves

            if (player instanceof ServerPlayer) {

                Block leavesBlock = counterpartIDMap.get(getCounterpart(level, blockPos));
                BlockState blockStateNew = leavesBlock.defaultBlockState();
                blockStateNew = setStandardLeavesProperties(blockStateNew, blockState);

                if (leavesBlock.equals(WILLOW_LEAVES_BLOCK.get()))
                    blockStateNew = blockStateNew.setValue(MOSSY, blockState.getValue(MOSSY));

                level.setBlockAndUpdate(blockPos, blockStateNew);
            }
            level.playSound(null, blockPos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(player, GameEvent.SHEAR, blockPos);
            itemStack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(interactionHand));
            level.addDestroyBlockEffect(blockPos, blockState);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
    }
}
