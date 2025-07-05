package net.night.grasses.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.night.grasses.block.potted.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.world.level.block.Blocks.FLOWER_POT;
import static net.night.grasses.block.potted.PottedPlantBlock.takePlantFromPot;
import static net.night.grasses.data.DataLib.*;
import static net.night.grasses.data.MethodsLib.*;

@Mixin(FlowerPotBlock.class)
public abstract class FlowerPotBlockMixin {

    @Unique
    boolean grasses$head$isEmptyPotAtStart;
    @Unique
    boolean grasses$head$isEmptyHandAtStart;
    @Unique
    ItemStack grasses$itemStackInHandAtStart;

    @Shadow
    public Block getContent() {
        return null;
    }


    @Inject(at = @At("HEAD"), method = "use", cancellable = true)
    public void head$use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit, CallbackInfoReturnable<InteractionResult> cir) {

        if (pState.getBlock() instanceof TintedPottedPlantBlock || pState.getBlock() instanceof PottedPlantBlock)
            return;

        if (pHand != InteractionHand.MAIN_HAND)
            return;

        if (pPlayer.getMainHandItem().isEmpty())
            grasses$head$isEmptyHandAtStart = true;
        else {
            grasses$head$isEmptyHandAtStart = false;
            grasses$itemStackInHandAtStart = pPlayer.getMainHandItem().copy();
            if (grasses$itemStackInHandAtStart.getItem() instanceof BlockItem) {
                BlockState blockStateInHandAtStart = ((BlockItem) grasses$itemStackInHandAtStart.getItem()).getBlock().defaultBlockState();
                ItemStack itemStack = pPlayer.getMainHandItem();
                if (tintedPlantsThatCanBePotted.contains(itemStack.getItem())) {
                    Block block = getKey(matchingCounterpartsPlants, blockStateInHandAtStart.getBlock());
                    keepData(pPos, getKey(matchingNotTintedPottedWithPlant, block), getColorTypeFromNBT(itemStack));
                }
            }
        }

        grasses$head$isEmptyPotAtStart = pState.is(FLOWER_POT);
    }

    @Inject(at = @At("RETURN"), method = "use", cancellable = true)
    public void use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult hitResult, CallbackInfoReturnable<InteractionResult> cir) {

        if (blockState.getBlock() instanceof TintedPottedPlantBlock || blockState.getBlock() instanceof PottedPlantBlock)
            return;

        if (interactionHand != InteractionHand.MAIN_HAND)
            return;

        ItemStack itemStackInHandAtEnd = player.getMainHandItem();
        BlockState blockStateAtEnd = level.getBlockState(blockPos);
        BlockState blockStateContent = getContent().defaultBlockState();
        ItemStack itemStackContent = blockStateContent.getBlock().asItem().getDefaultInstance();
        boolean hasSilkTouch = grasses$itemStackInHandAtStart.getItem() instanceof ShearsItem && EnchantmentHelper.hasSilkTouch(grasses$itemStackInHandAtStart);
        int handAtStart = grasses$itemStackInHandAtStart != null ? grasses$itemStackInHandAtStart.getCount() : 0;
        int handAtEnd = itemStackInHandAtEnd.getCount();

        if (!grasses$head$isEmptyHandAtStart) {

            if (tintedPlantsThatCanBePotted.contains(grasses$itemStackInHandAtStart.getItem()) && grasses$itemStackInHandAtStart.getItem() instanceof BlockItem) {
                Block blockInMainHand = ((BlockItem) grasses$itemStackInHandAtStart.getItem()).getBlock();
                if (!grasses$head$isEmptyPotAtStart) {
                    itemStackInHandAtEnd.shrink(1);
                    addToInventory(level, player, itemStackContent);
                }

                keepData(blockPos, getKey(matchingNotTintedPottedWithPlant, getKey(matchingCounterpartsPlants, blockInMainHand)), getColorTypeFromNBT(grasses$itemStackInHandAtStart));
                level.setBlock(blockPos, getKey(matchingTintedPottedWithPlant, blockInMainHand).defaultBlockState(), 3);
                level.playSound(null, blockPos, SoundEvents.GRASS_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.addDestroyBlockEffect(blockPos, Blocks.FLOWER_POT.defaultBlockState());

            } else if (notTintedPlantsThatCanBePotted.contains(grasses$itemStackInHandAtStart.getItem()) && !grasses$head$isEmptyPotAtStart) {
                if (grasses$itemStackInHandAtStart.getItem().equals((matchingNotTintedPottedWithPlant.get(blockState.getBlock())).asItem()))
                    takePlantFromPot(level, blockPos, player, itemStackContent);
                else if (grasses$itemStackInHandAtStart.getItem() instanceof BlockItem) {
                    Block blockInMainHand = ((BlockItem) grasses$itemStackInHandAtStart.getItem()).getBlock();

                    itemStackInHandAtEnd.shrink(1);
                    addToInventory(level, player, itemStackContent);

                    if (player instanceof ServerPlayer)
                        level.setBlock(blockPos, getKey(matchingNotTintedPottedWithPlant, blockInMainHand).defaultBlockState(), 3);
                }
                level.playSound(null, blockPos, SoundEvents.GRASS_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.addDestroyBlockEffect(blockPos, Blocks.FLOWER_POT.defaultBlockState());
            }
        } else if (blockStateAtEnd.is(FLOWER_POT) && !itemStackInHandAtEnd.isEmpty() && !hasSilkTouch) {
            itemStackInHandAtEnd.copyAndClear();
            addToInventory(level, player, itemStackContent);
        }


        if (player.isCreative() || handAtEnd != handAtStart || (!grasses$head$isEmptyPotAtStart && blockStateAtEnd.is(FLOWER_POT))) {
            level.playSound(null, blockPos, SoundEvents.GRASS_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.addDestroyBlockEffect(blockPos, Blocks.FLOWER_POT.defaultBlockState());
        }
    }
}
