package net.night.grasses.block.potted;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.night.grasses.block.blockEntity.TintedBlockEntity;
import net.night.grasses.enums.ColorType;
import net.night.grasses.config.GrassesConfig;
import net.night.grasses.data.ModMethods;
import net.night.grasses.item.DyeingTool;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

import static net.minecraft.world.level.block.Blocks.AIR;
import static net.minecraft.world.level.block.Blocks.POTTED_FERN;
import static net.night.grasses.data.ModData.*;
import static net.night.grasses.data.ModMethods.*;


public class TintedPottedPlantBlock extends FlowerPotBlock implements EntityBlock {

    public TintedPottedPlantBlock(@Nullable Supplier<FlowerPotBlock> emptyPot, Supplier<? extends Block> plant) {
        super(emptyPot, plant, Properties.ofFullCopy(POTTED_FERN).noOcclusion());
        this.registerDefaultState(this.defaultBlockState());
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new TintedBlockEntity(blockPos, blockState);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState blockState, HitResult hitResult, LevelReader levelReader, BlockPos blockPos, Player player) {

        final BlockState emptyPotState = levelReader.getBlockState(blockPos);
        final Block emptyPotBlock = emptyPotState.getBlock();

        if (!(emptyPotBlock instanceof FlowerPotBlock) || emptyPotState != emptyPotBlock.defaultBlockState() ||
                ((FlowerPotBlock) emptyPotBlock).getPotted() != Blocks.AIR) {

            blockState = ((FlowerPotBlock) emptyPotBlock).getPotted().defaultBlockState();
        }
        return ModMethods.getCloneItemStackBE((Level) levelReader, blockPos, blockState);
    }

    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockStateOld, boolean pMovedByPiston) {
        onPlaceCounterPart(level, blockPos, blockState);
        super.onPlace(blockState, level, blockPos, blockStateOld, pMovedByPiston);
    }

    @Override
    public List<ItemStack> getDrops(BlockState blockState, LootParams.Builder builder) {
        return prepareDropWithColor(super.getDrops(blockState, builder), builder, this.getPotted().asItem());
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        getStateForPlacementCounterPart(context, matchingCounterpartsPlants);
        return super.getStateForPlacement(context);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {

        boolean changeColorPermission = GrassesConfig.COMMON_CONFIG.ALLOW_CHANGE_POTTED_PLANT_COLOR.get();
        boolean changeIntoVanillaPermission = GrassesConfig.COMMON_CONFIG.ALLOW_CHANGE_POTTED_TINTED_PLANTS_INTO_NOT_GRASSES.get();

        ItemStack itemStackInMainHand = player.getMainHandItem();
        int interactionResult = 1;
        if (inHandIsNotUsefulItem(itemStackInMainHand))
            plantReplacement(level, blockPos, blockState, player, itemStackInMainHand);
        else
            interactionResult = ModMethods.useOnPlant(blockState, level, blockPos, player, interactionHand, blockHitResult,
                changeColorPermission, changeIntoVanillaPermission, false, SoundEvents.GRASS_BREAK);


        if (interactionResult == 0)
            return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
        else {
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
    }

    private void plantReplacement(Level level, BlockPos blockPos, BlockState blockStatePot, Player player, ItemStack itemStackInMainHand) {
        ColorType colorType = getColorType(level, blockPos);
        ItemStack itemStackContent = new ItemStack(this.getPotted());

        Block blockInMainHand = AIR;

        if (!player.getMainHandItem().isEmpty() && itemStackInMainHand.getItem() instanceof BlockItem)
            blockInMainHand = ((BlockItem) itemStackInMainHand.getItem()).getBlock();

        if (inHandIsNotPlantThatCanBePotted(itemStackInMainHand))
            takePlantFromPot(level, blockPos, player, itemStackContent, colorType);
        else {
            if (tintedPlantsThatCanBePotted.contains(itemStackInMainHand.getItem())) {
                ColorType colorTypeItemStack = getColorTypeFromNBT(itemStackInMainHand);

                if (itemStackInMainHand.getItem().equals((matchingTintedPottedWithPlant.get(blockStatePot.getBlock())).asItem())) {
                    if (colorTypeItemStack == colorType)
                        takePlantFromPot(level, blockPos, player, itemStackContent, colorType);
                    else {
                        addToInventory(level, player, itemStackContent, colorType, 1, itemStackInMainHand);
                        updateColorOnEntityBlock(level, blockStatePot, blockPos, colorTypeItemStack);
                    }
                } else {
                    itemStackInMainHand.shrink(1);
                    takePlantFromPot(level, blockPos, player, itemStackContent, colorType);
                    keepData(blockPos, getKey(matchingNotTintedPottedWithPlant, getKey(matchingCounterpartsPlants, blockInMainHand)), colorTypeItemStack);
                    level.setBlock(blockPos, getKey(matchingTintedPottedWithPlant, blockInMainHand).defaultBlockState(), 3);
                }

            } else if (notTintedPlantsThatCanBePotted.contains(itemStackInMainHand.getItem())) {
                addToInventory(level, player, itemStackContent, colorType, 1, itemStackInMainHand);
                level.setBlock(blockPos, getKey(matchingNotTintedPottedWithPlant, blockInMainHand).defaultBlockState(), 3);
            }
        }
        level.playSound(null, blockPos, SoundEvents.GRASS_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
        level.addDestroyBlockEffect(blockPos, Blocks.FLOWER_POT.defaultBlockState());
    }

    public static boolean inHandIsNotUsefulItem(ItemStack itemStack) {
        return !(itemStack.getItem() instanceof ShearsItem) && !(itemStack.getItem() instanceof DyeingTool);
    }
    public static boolean inHandIsNotPlantThatCanBePotted(ItemStack itemStack) {
        return !tintedPlantsThatCanBePotted.contains(itemStack.getItem()) && !notTintedPlantsThatCanBePotted.contains(itemStack.getItem());
    }

    private void takePlantFromPot(Level level, BlockPos blockPos, Player player, ItemStack itemStackContent, ColorType colorType) {
        if (!level.isClientSide()) {
            addToInventory(level, player, itemStackContent, colorType, 0, null);
            level.setBlock(blockPos, this.getEmptyPot().defaultBlockState(), 3);
            level.gameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);
        }
    }

    private void addToInventory(Level level, Player player, ItemStack itemStackContent, ColorType colorType, int shrink, ItemStack itemStackInMainHand) {
        if (!level.isClientSide()) {
            assert colorType != null;
            setColorOnItemStack(itemStackContent, colorType);

            if (shrink != 0)
                itemStackInMainHand.shrink(1);

            int slot = findSlotMatchingItem(player, itemStackContent);
            if (slot != -1) {
                player.getInventory().getItem(slot).shrink(-1);
            } else if (!player.addItem(itemStackContent))
                player.drop(itemStackContent, false);
        }
    }

    private int findSlotMatchingItem(Player player, ItemStack itemStack) {
        Inventory inventory = player.getInventory();

        for(int i = 0; i < inventory.items.size(); ++i) {

            if (!(inventory.items.get(i)).isEmpty() && ItemStack.isSameItemSameTags(itemStack, inventory.items.get(i)) && inventory.items.get(i).getCount() < itemStack.getMaxStackSize()) {
                return i;
            }
        }
        return -1;
    }
}
