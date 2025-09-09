package net.night.grasses.block.potted;

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
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.night.grasses.enums.ColorType;
import net.night.grasses.config.GrassesConfig;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

import static net.minecraft.world.level.block.Blocks.*;
import static net.night.grasses.block.potted.TintedPottedPlantBlock.inHandIsNotPlantThatCanBePotted;
import static net.night.grasses.block.potted.TintedPottedPlantBlock.inHandIsNotUsefulItem;
import static net.night.grasses.data.ModData.*;
import static net.night.grasses.data.ModMethods.*;
import static net.night.grasses.init.BlocksRegister.KELP_POTTED;


public class PottedPlantBlock extends FlowerPotBlock {

    public PottedPlantBlock(@Nullable Supplier<FlowerPotBlock> emptyPot, Supplier<? extends Block> plant) {
        super(emptyPot, plant, Properties.ofFullCopy(Blocks.POTTED_FERN).noOcclusion());
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {

        if (blockState.is(KELP_POTTED.get()))
            return new ItemStack(KELP.asItem());
        else
            return super.getCloneItemStack(levelReader, blockPos, blockState);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (interactionHand != InteractionHand.MAIN_HAND)
            return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);

        ItemStack itemStackInMainHand = player.getMainHandItem();

        boolean hasSilkTouch = EnchantmentHelper.hasSilkTouch(itemStackInMainHand);

        if (inHandIsNotUsefulItem(itemStackInMainHand)) {
            plantReplacement(level, blockPos, blockState, player, itemStackInMainHand);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        else if (itemStackInMainHand.getItem() instanceof ShearsItem && hasSilkTouch && GrassesConfig.COMMON_CONFIG.ALLOW_CHANGE_POTTED_NOT_GRASSES_PLANTS_INTO_TINTED.get()) {
            if (player instanceof ServerPlayer) {
                Block blockContent = getPotted();
                Block plantsBlock = getKey(matchingTintedPottedWithPlant, matchingCounterpartsPlants.get(blockContent));
                if (plantsBlock == null)
                    return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);

                keepData(blockPos, blockState.getBlock(), ColorType.PLAINS);
                level.setBlock(blockPos, plantsBlock.withPropertiesOf(blockState), 3);
            }
            level.playSound(null, blockPos, SoundEvents.AZALEA_LEAVES_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.playSound(null, blockPos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.addDestroyBlockEffect(blockPos, blockState);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
    }

    private void plantReplacement(Level level, BlockPos blockPos, BlockState blockStatePot, Player player, ItemStack itemStackInMainHand) {
        ItemStack itemStackContent = new ItemStack(this.getPotted());
        Block blockInMainHand = AIR;
        if (!player.getMainHandItem().isEmpty() && itemStackInMainHand.getItem() instanceof BlockItem)
            blockInMainHand = ((BlockItem) itemStackInMainHand.getItem()).getBlock();

        if (inHandIsNotPlantThatCanBePotted(itemStackInMainHand)) {
            takePlantFromPot(level, blockPos, player, itemStackContent);
        } else {
            if (tintedPlantsThatCanBePotted.contains(itemStackInMainHand.getItem())) {
                ColorType colorTypeItemStack = getColorTypeFromNBT(itemStackInMainHand);
                itemStackInMainHand.shrink(1);
                addToInventory(level, player, itemStackContent);
                keepData(blockPos, getKey(matchingNotTintedPottedWithPlant, getKey(matchingCounterpartsPlants, blockInMainHand)), colorTypeItemStack);
                level.setBlock(blockPos, getKey(matchingTintedPottedWithPlant, blockInMainHand).defaultBlockState(), 3);

            } else if (notTintedPlantsThatCanBePotted.contains(itemStackInMainHand.getItem())) {
                if (itemStackInMainHand.getItem().equals((matchingNotTintedPottedWithPlant.get(blockStatePot.getBlock())).asItem())) {
                    takePlantFromPot(level, blockPos, player, itemStackContent);
                } else {
                    itemStackInMainHand.shrink(1);
                    addToInventory(level, player, itemStackContent);
                    if (player instanceof ServerPlayer)
                        level.setBlock(blockPos, getKey(matchingNotTintedPottedWithPlant, blockInMainHand).defaultBlockState(), 3);
                }
            }
        }
        level.playSound(null, blockPos, SoundEvents.GRASS_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
        level.addDestroyBlockEffect(blockPos, blockStatePot);
    }

    public static void takePlantFromPot(Level level, BlockPos blockPos, Player player, ItemStack itemStackContent) {
        if (!level.isClientSide()) {
            addToInventory(level, player, itemStackContent);
            level.setBlock(blockPos, FLOWER_POT.defaultBlockState(), 3);
            level.gameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);
            level.playSound(null, blockPos, SoundEvents.GRASS_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
        }
    }
}
