package net.night.grasses.block.leaves;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.night.grasses.block.leaves.superclasses.ParentTintedLeavesBlock;
import net.night.grasses.colorManagers.ColorType;
import net.night.grasses.config.GrassesConfig;
import net.night.grasses.item.DyeingTool;

import static net.night.grasses.colorManagers.ColorType.PLAINS;
import static net.night.grasses.data.ModData.counterpartIDMap;
import static net.night.grasses.data.ModMethods.*;
import static net.night.grasses.init.BlocksRegister.DOUBLE_ALTER;

public class TintedJungleLeavesBlock extends ParentTintedLeavesBlock implements EntityBlock {
    public TintedJungleLeavesBlock() {
        this.registerDefaultState(this.defaultBlockState().setValue(DOUBLE_ALTER, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(DOUBLE_ALTER);
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
                int nextVersion = blockState.getValue(DOUBLE_ALTER)+1;
                if (nextVersion == 4)
                    nextVersion = 0;
                level.setBlockAndUpdate(blockPos, blockState.setValue(DOUBLE_ALTER, nextVersion));
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
