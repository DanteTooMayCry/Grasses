package net.night.grasses.data;

import biomesoplenty.block.HugeCloverPetalBlock;
import biomesoplenty.block.HugeLilyPadBlock;
import biomesoplenty.block.properties.QuarterProperty;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import net.night.grasses.block.bars.TintedPlantInBars;
import net.night.grasses.block.blockEntity.TintedBlockEntity;
import net.night.grasses.block.plants.TintedVine;
import net.night.grasses.block.plants.bop.TintedHugeLilyPadBOP;
import net.night.grasses.block.potted.TintedPottedPlantBlock;
import net.night.grasses.enums.ColorType;
import net.night.grasses.colorManagers.ColorsDefinition;
import net.night.grasses.config.GrassesConfig;
import net.night.grasses.datagen.loot.ModSaplingData;
import net.night.grasses.item.DyeingTool;
import net.night.grasses.util.ClientPlayerHelper;
import net.night.grasses.enums.GrassesQuarterProperty;

import java.util.*;

import static biomesoplenty.api.block.BOPBlocks.*;
import static biomesoplenty.block.HugeLilyPadBlock.QUARTER;
import static net.minecraft.tags.BlockTags.LEAVES;
import static net.minecraft.world.level.block.Blocks.*;
import static net.minecraft.world.level.block.CrossCollisionBlock.*;
import static net.minecraft.world.level.block.DoublePlantBlock.HALF;
import static net.minecraft.world.level.block.HorizontalDirectionalBlock.FACING;
import static net.minecraft.world.level.block.LeavesBlock.DISTANCE;
import static net.minecraft.world.level.block.LeavesBlock.PERSISTENT;
import static net.minecraft.world.level.block.SlabBlock.TYPE;
import static net.minecraft.world.level.block.SlabBlock.WATERLOGGED;
import static net.minecraft.world.level.block.state.properties.DoubleBlockHalf.LOWER;
import static net.minecraft.world.level.block.state.properties.DoubleBlockHalf.UPPER;
import static net.minecraft.world.level.block.state.properties.SlabType.BOTTOM;
import static net.night.grasses.Grasses.isBOPLoaded;
import static net.night.grasses.block.plants.TintedSugarCane.biomesColorSourcePropertiesUpdate;
import static net.night.grasses.block.plants.bop.TintedHugeLilyPadBOP.GRASSES_QUARTER;
import static net.night.grasses.enums.ColorType.*;
import static net.night.grasses.data.ModData.*;
import static net.night.grasses.datagen.loot.SaplingDropHelper.knownMods;
import static net.night.grasses.init.BlocksRegister.*;
import static net.night.grasses.init.BlocksRegisterBoP.*;
import static net.night.grasses.enums.GrassesQuarterProperty.*;

public final class ModMethods {

    private  ModMethods() {

    }

    public static Map<BlockPos, ColorType> keepColorType = new HashMap<>();
    public static Map<BlockPos, BlockState> keepCounterPartType = new HashMap<>();

    //main methods:

    public static Block getKey(Map<Block, Block> map, Block block) {
        for (Map.Entry<Block, Block> blockEntry : map.entrySet()) {
            if (block.equals(blockEntry.getValue())) {
                return blockEntry.getKey();
            }
        }
        return AIR;
    }
    public static void addToInventoryWithColor(Level level, BlockPos blockPos, Player player, ItemStack itemStackContent, ColorType colorType, int shrink, ItemStack itemStackInMainHand) {
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
    public static void addToInventory(Level level, Player player, ItemStack itemStackContent) {
        if (!level.isClientSide()) {

            int slot = findSlotMatchingItem(player, itemStackContent);
            if (slot != -1) {
                player.getInventory().getItem(slot).shrink(-1);
            } else if (!player.addItem(itemStackContent))
                player.drop(itemStackContent, false);
        }
    }
    public static int findSlotMatchingItem(Player player, ItemStack itemStack) {
        Inventory inventory = player.getInventory();

        for(int i = 0; i < inventory.items.size(); ++i) {

            if (!(inventory.items.get(i)).isEmpty() && ItemStack.isSameItemSameTags(itemStack, inventory.items.get(i)) && inventory.items.get(i).getCount() < itemStack.getMaxStackSize()) {
                return i;
            }
        }
        return -1;
    }
    public static boolean compareOnMap(BlockState blockState, ItemStack itemStack, Map<Block, Block> map){
        if (itemStack.is(map.get(blockState.getBlock()).asItem()))
            return true;
        else
            return false;
    }
    public static BlockState setTopSlab(BlockPlaceContext context, boolean isSprintKeyPush, Block block) {
        BlockPos $$1 = context.getClickedPos();
        BlockState $$2 = context.getLevel().getBlockState($$1);
        if ($$2.is(block)) {
            return (BlockState)((BlockState)$$2.setValue(TYPE, SlabType.DOUBLE)).setValue(WATERLOGGED, false);
        } else {
            FluidState $$3 = context.getLevel().getFluidState($$1);
            BlockState $$4 = (BlockState)((BlockState)block.defaultBlockState().setValue(TYPE, SlabType.BOTTOM)).setValue(WATERLOGGED, $$3.getType() == Fluids.WATER);
            Direction $$5 = context.getClickedFace();
            return !isSprintKeyPush && $$5 != Direction.DOWN && ($$5 == Direction.UP || !(context.getClickLocation().y - (double)$$1.getY() > 0.5)) ? $$4 : (BlockState)$$4.setValue(TYPE, SlabType.TOP);
        }
    }

    //plants methods

    public static boolean canSustainPlantOnDirtLike(BlockState blockState, BlockGetter world, BlockPos blockPos, Direction facing, IPlantable plantable) {

        if (!blockState.hasProperty(TYPE) || !blockState.getValue(TYPE).equals(BOTTOM) || GrassesConfig.CommonConfig.ALLOW_PUT_PLANTS_ON_BOTTOM_SLAB.get()) {

            PlantType plantType = plantable.getPlantType(world, blockPos);
            BlockState east = world.getBlockState(blockPos.east());
            BlockState west = world.getBlockState(blockPos.west());
            BlockState north = world.getBlockState(blockPos.north());
            BlockState south = world.getBlockState(blockPos.south());

            if (plantType == PlantType.PLAINS || plantType == PlantType.CAVE || plantType == PlantType.DESERT)
                return true;
            else if (plantType == PlantType.BEACH)
                return ((east.getBlock() == WATER || (east.hasProperty(WATERLOGGED) && east.getValue(WATERLOGGED))) ||
                        (west.getBlock() == WATER || (west.hasProperty(WATERLOGGED) && west.getValue(WATERLOGGED))) ||
                        (north.getBlock() == WATER || (north.hasProperty(WATERLOGGED) && north.getValue(WATERLOGGED))) ||
                        (south.getBlock() == WATER || (south.hasProperty(WATERLOGGED) && south.getValue(WATERLOGGED))) ||
                        (blockState.hasProperty(WATERLOGGED) && blockState.getValue(WATERLOGGED))
                );
            else
                return false;
        }
        return false;
    }
    public static boolean isFertileState(BlockState blockState){
        return blockState.hasProperty(FERTILE) && blockState.getValue(FERTILE);
    }
    public static BlockPos getSecondPartOfPlant(BlockState hitBlockState, BlockPos firstPartBlockPos) {

        DoubleBlockHalf doubleblockhalf = hitBlockState.getValue(HALF);

        if (hitBlockState.getBlock().equals(TALL_SEAGRASS))
            doubleblockhalf = hitBlockState.getValue(TallSeagrassBlock.HALF);

        BlockPos secondPartBlockPos = null;

        if (doubleblockhalf == UPPER) {
            secondPartBlockPos = firstPartBlockPos.below();
        }
        else {
            secondPartBlockPos = firstPartBlockPos.above();
        }
        return secondPartBlockPos;
    }
    public static boolean isTintedBarsBlock(BlockState blockStateToSet) {
        return blockStateToSet.getBlock() instanceof TintedVine || blockStateToSet.getBlock() instanceof TintedPlantInBars;
    }
    public static BlockState setStandardLeavesProperties(BlockState blockState, BlockState leavesOnTreeBlockState) {

        blockState = blockState
                .setValue(PERSISTENT, leavesOnTreeBlockState.getValue(PERSISTENT))
                .setValue(LeavesBlock.WATERLOGGED, leavesOnTreeBlockState.getValue(LeavesBlock.WATERLOGGED))
                .setValue(DISTANCE, leavesOnTreeBlockState.getValue(DISTANCE));
        return blockState;
    }

    //related to nbtTag and/or color

    public static boolean hasBlockStateTag(ItemStack itemStack){
        if (!itemStack.hasTag()) return false;
        assert itemStack.getTag() != null;
        return itemStack.getTag().contains("BlockStateTag");
    }
    public static String nbtTagToName (ItemStack itemStack) {

        CompoundTag compoundtag = itemStack.getTag();
        return "grasses.color_state_".concat(compoundtag.getCompound("BlockStateTag").getString("color_type"));
    }
    public static void additionalHoverText (ItemStack itemStack, List<Component> tooltip) {
        if (hasBlockStateTag(itemStack)) {
            tooltip.add(Component.literal("  ").append(Component.translatable(nbtTagToName(itemStack)).withStyle(ChatFormatting.GRAY)));
        }
    }
    public static void getColorTypeAndShowOnActionBar(ItemStack itemStack) {

        if (hasBlockStateTag(itemStack)) {
            MutableComponent colorType = Component.translatable(nbtTagToName(itemStack));
            ClientPlayerHelper.sendClientMessage(colorType, true);
        }
    }
    public static ItemStack setEnchantmentBoolean(ItemStack itemStack){

        CompoundTag compoundNBT = itemStack.getTag();

        if (EnchantmentHelper.hasSilkTouch(itemStack))
            compoundNBT.putBoolean("Enchanted", true);
        else
            compoundNBT.putBoolean("Enchanted", false);

        itemStack.setTag(compoundNBT);
        return itemStack;
    }
    public static ColorType getColorTypeFromNBT(ItemStack itemStack) {

        if (hasBlockStateTag(itemStack)) {
            CompoundTag compoundtag = itemStack.getTag();

            if (compoundtag != null)
                return ColorType.valueOf(compoundtag.getCompound("BlockStateTag").getString("color_type").toUpperCase());
            else
                return PLAINS;
        }
        else
            return PLAINS;
    }
    public static ItemStack setColorOnItemStack(ItemStack itemStack, ColorType colorType) {

        CompoundTag compoundNBT1 = new CompoundTag();
        CompoundTag compoundNBT = itemStack.getOrCreateTagElement("BlockStateTag");
        compoundNBT.putString("color_type", colorType.toString());
        compoundNBT1.put("BlockStateTag", compoundNBT);
        itemStack.setTag(compoundNBT1);

        if (itemStack.getItem().equals(SUGAR_CANE_TINTED.get().asItem()) || itemStack.getItem().equals(SUGAR_CANE_POTTED_TINTED.get().asItem())) {
            compoundNBT.putString("biomes_color_source", String.valueOf(biomesColorSourcePropertiesUpdate(colorType)));
            compoundNBT1.put("BlockStateTag", compoundNBT);
            itemStack.setTag(compoundNBT1);
        }

        return itemStack;
    }
    public static boolean compareColors (BlockPos blockPos, ItemStack itemStack) {

        if (getColorType(blockPos).equals(getColorTypeFromNBT(itemStack)))
            return true;
        else
            return false;
    }

    //methods related to entityBlock Color/Counterpart

    public static void onlyChangeColor(Level level, BlockPos blockPos, BlockState blockState, ColorType colorType, int type) {

        if (!level.isClientSide()) {
            BlockEntity be = level.getBlockEntity(blockPos);
            if ((type == 0 && be instanceof TintedBlockEntity blockEntity)) {
                blockEntity.setColorType(colorType, ColorsDefinition.takeColor(colorType, blockState, null));
            }
        }
    }
    public static ColorType getCurrentColor(Level level, BlockPos blockPos, int type) {

        if (!level.isClientSide()) {
            BlockEntity be = level.getBlockEntity(blockPos);
            if ((type == 0 && be instanceof TintedBlockEntity blockEntity)) {
                return  blockEntity.getColorType();
            }
            else return PLAINS;
        }
        else return PLAINS;
    }
    public static ItemStack getCloneItemStackBE(Level level, BlockPos blockPos, BlockState blockState) {

        ColorType colorType;
        BlockEntity be = level.getBlockEntity(blockPos);
        if (be instanceof TintedBlockEntity blockEntity) {
            colorType = blockEntity.getColorType();
        } else colorType = PLAINS;

        ItemStack itemStack = new ItemStack(blockState.getBlock());
        if (colorType == null)
            colorType = PLAINS;
        setColorOnItemStack(itemStack, colorType);

        return itemStack;
    }
    public static ColorType getColorType(BlockPos blockPos) {
        Level level = Minecraft.getInstance().level;
        ColorType colorType;

        assert level != null;
        BlockEntity be = level.getBlockEntity(blockPos);

        if (be instanceof TintedBlockEntity blockEntity) {
            colorType = blockEntity.getColorType();
            if (colorType == null)
                colorType = PLAINS;
        } else colorType = PLAINS;

        return colorType;
    }
    public static ColorType getColorType(Level level, BlockPos blockPos) {

        if(!level.isClientSide()) {
            BlockEntity be = level.getBlockEntity(blockPos);
            if (be instanceof TintedBlockEntity blockEntity) {
                return blockEntity.getColorType();
            } else return PLAINS;
        }
        return null;
    }
    public static String getCounterpart(Level level, BlockPos blockPos) {

        if(!level.isClientSide()) {
            BlockEntity be = level.getBlockEntity(blockPos);
            if (be instanceof TintedBlockEntity blockEntity) {
                return blockEntity.getCounterpart();
            } else return null;
        }
        return null;
    }
    public static void onPlaceCounterPart (Level level, BlockPos blockPos, BlockState blockState) {
        if (keepCounterPartType.containsKey(blockPos)) {
            setColorAndCounterpartOnEntityBlock(level, blockState, blockPos, keepCounterPartType.get(blockPos), keepColorType.get(blockPos));
            keepCounterPartType.remove(blockPos);
            keepColorType.get(blockPos);
        }
        else if (keepColorType.containsKey(blockPos)) {
            setColorAndColorTypeInBlockEntity(level, blockPos, blockState, keepColorType.get(blockPos));
            keepColorType.remove(blockPos);
        }
    }
    public static void getStateForPlacementCounterPart(BlockPlaceContext context, Map<Block, Block> map) {
        Block block = ((BlockItem) context.getItemInHand().getItem()).getBlock();
        BlockPos blockPosClicked = context.getClickedPos();

        if (isBOPLoaded || !tintedBOPPlantBlockList.contains(block)) {
            BlockState blockToKeep = getKey(map, block).defaultBlockState();
            if (!blockToKeep.is(AIR))
                keepCounterPartType.put(blockPosClicked, blockToKeep);
        }
        keepColorType.put(blockPosClicked, getColorTypeFromNBT(context.getItemInHand()));
    }
    public static void getStateForPlacementCounterPartDouble(BlockPlaceContext context, Map<Block, Block> map) {
        Block block = ((BlockItem) context.getItemInHand().getItem()).getBlock();
        BlockPos blockPosClicked = context.getClickedPos();

        if (isBOPLoaded || !tintedBOPPlantBlockList.contains(block)) {
            BlockState blockToKeep = getKey(map, block).defaultBlockState();
            if (!blockToKeep.is(AIR)) {
                keepCounterPartType.put(blockPosClicked, blockToKeep.setValue(HALF, LOWER));
                keepCounterPartType.put(blockPosClicked.above(), blockToKeep.setValue(HALF, UPPER));
            }
        }
        keepColorType.put(blockPosClicked, getColorTypeFromNBT(context.getItemInHand()));
        keepColorType.put(blockPosClicked.above(), keepColorType.get(blockPosClicked));
    }
    public static void keepData(BlockPos blockPos, Block block, ColorType colorType) {
        keepColorType.put(blockPos, colorType);
        keepCounterPartType.put(blockPos, block.defaultBlockState());
    }
    public static void setColorAndColorTypeInBlockEntity(Level level, BlockPos blockPos, BlockState blockState, ColorType colorType) {

        if (!level.isClientSide()) {
            BlockEntity be = level.getBlockEntity(blockPos);
            if (be instanceof TintedBlockEntity blockEntity && colorType != null) {
                blockEntity.setColorType(colorType, ColorsDefinition.takeColor(colorType, blockState, null));
            }
        }
    }
    public static void setColorAndCounterpartOnEntityBlock(Level level, BlockState blockStateNew, BlockPos blockPos, BlockState blockStateOld, ColorType colorType) {
        if (!level.isClientSide()) {
            BlockEntity be = level.getBlockEntity(blockPos);
            if (be instanceof TintedBlockEntity blockEntity) {
                if (blockStateOld.is(LEAVES) || matchingCounterpartsPlants.containsKey(blockStateOld.getBlock()) || blockStateOld.getBlock() instanceof FlowerPotBlock) {
                    String counterpart = "";
                    for (Map.Entry<String, Block> blockEntry : counterpartIDMap.entrySet())
                        if (blockEntry.getValue().equals(blockStateOld.getBlock())) {
                            counterpart = blockEntry.getKey();
                            break;
                        }
                    blockEntity.setColorType(colorType, ColorsDefinition.takeColor(colorType, blockStateNew, null), counterpart);
                }
                else
                    blockEntity.setColorType(colorType, ColorsDefinition.takeColor(colorType, blockStateNew, null));
            }
        }
    }
    public static void updateColorOnEntityBlock(Level level, BlockState blockState, BlockPos blockPos, ColorType colorType) {
        if (!level.isClientSide()) {
            BlockEntity be = level.getBlockEntity(blockPos);
            if (be instanceof TintedBlockEntity blockEntity) {
                blockEntity.setColorType(colorType, ColorsDefinition.takeColor(colorType, blockState, null));

            }
        }
    }
    public static int getColorTypeForColorManager(BlockPos blockPos) {
        Level level = Minecraft.getInstance().level;
        int numberColor;

        assert level != null;
        BlockEntity be = level.getBlockEntity(blockPos);

        if (be instanceof TintedBlockEntity blockEntity) {
            numberColor = blockEntity.getColorInt();
        } else {
            numberColor = 0;
        }

        return numberColor;
    }
    public static List<ItemStack> prepareDropWithColor(List<ItemStack> drops, LootParams.Builder builder, Item thisItem) {

        Optional<ItemStack> base = drops.stream().filter(item -> item.is(thisItem)).findFirst();

        if (base.isPresent()) {
            BlockEntity be = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
            if (be instanceof TintedBlockEntity blockEntity) {
                ColorType colorType = blockEntity.getColorType();
                if (colorType == null)
                    colorType = PLAINS;
                ItemStack itemStack = base.get();
                setColorOnItemStack(itemStack, colorType);
                drops.remove(base.get());
                drops.add(itemStack);
            }
        }
        return drops;
    }

    public static List<ItemStack> prepareDropWithColorAndAdditionalItems(List<ItemStack> drops, LootParams.Builder builder, Item thisLeavesItem) {
        Optional<ItemStack> base = drops.stream().filter(itemStack -> itemStack.is(thisLeavesItem)).findFirst();

        if (base.isPresent()) {
            BlockEntity be = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
            if (be instanceof TintedBlockEntity blockEntity) {
                ColorType colorType = blockEntity.getColorType();
                if (colorType == null)
                    colorType = PLAINS;
                ItemStack itemStack = base.get();
                setColorOnItemStack(itemStack, colorType);
                drops.remove(base.get());
                drops.add(itemStack);
            }
            return drops;
        }

        ItemStack itemStackInHand = builder.getOptionalParameter(LootContextParams.TOOL);

        int fortuneLevel = itemStackInHand != null ? EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, itemStackInHand) : 0;

        RandomSource random = builder.getLevel().random;

        for (ModSaplingData modSaplingData : knownMods) {
            if (!modSaplingData.isModLoaded()) continue;

            float[] chancesToUse = modSaplingData.normalLeavesSaplingChances();
            if (modSaplingData.unNormalLeavesSaplingChances() != null && modSaplingData.unNormalLeavesSaplingChances().containsKey(thisLeavesItem))
                chancesToUse = modSaplingData.unNormalLeavesSaplingChances().get(thisLeavesItem);

            int chanceIndex = Math.min(fortuneLevel, chancesToUse.length - 1);
            float chance = chancesToUse[chanceIndex];

            if (modSaplingData.leavesWithMoreThanOneSapling() != null && modSaplingData.leavesWithMoreThanOneSapling().containsKey(thisLeavesItem)) {
                List<Item> additionalSaplings = modSaplingData.leavesWithMoreThanOneSapling().get(thisLeavesItem);

                if (random.nextFloat() < chance) {
                    int selectedIndex = random.nextInt(additionalSaplings.size());
                    Item selectedSapling = additionalSaplings.get(selectedIndex);
                    if (selectedSapling != null)
                        drops.add(new ItemStack(selectedSapling));
                }
            } else {
                Item sapling = modSaplingData.saplingForLeaves().get(thisLeavesItem);
                if (sapling != null && random.nextFloat() < chance)
                    drops.add(new ItemStack(sapling));
            }
        }

        return drops;
    }

    //Methods related to "InteractionResult use" method

    public static int useOnPlant(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult,
                                 boolean changeColorPermission, boolean changeIntoVanillaPermission, boolean doublePlant, SoundEvent soundEvent) {

        if (interactionHand != InteractionHand.MAIN_HAND)
            return 0;

        ItemStack itemStack = player.getMainHandItem();
        boolean usedDyeingTool = itemStack.getItem() instanceof DyeingTool && hasBlockStateTag(itemStack);
        boolean isShears = itemStack.getItem() instanceof ShearsItem;
        boolean hasSilkTouch = EnchantmentHelper.hasSilkTouch(itemStack);
        boolean hasInfinity = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, itemStack) > 0;
        boolean pass = false;
        List<BlockPos> blockPosList = new ArrayList<>();

        if (usedDyeingTool && changeColorPermission) {
            CompoundTag compoundtag = itemStack.getTag();
            ColorType colorType = PLAINS;
            ColorType currentColor = getCurrentColor(level, blockPos, 0);

            if (compoundtag != null)
                colorType = getColorTypeFromNBT(itemStack);

            if (colorType != currentColor) {
                if (player instanceof ServerPlayer) {
                    if (doublePlant) {
                        BlockPos secondPart = getSecondPartOfPlant(blockState, blockPos);
                        BlockState secondHalf = level.getBlockState(secondPart);

                        onlyChangeColor(level, blockPos, blockState, colorType, 0);
                        onlyChangeColor(level, secondPart, secondHalf, colorType, 0);
                    }
                    else if (sugarCaneTintedPlantsList.contains(blockState.getBlock())) {
                        boolean biomesColorSource = biomesColorSourcePropertiesUpdate(colorType);
                        if (blockState.getValue(BIOMES_COLOR_SOURCE).equals(biomesColorSource))
                            onlyChangeColor(level, blockPos, blockState, colorType, 0);
                        else {
                            keepData(blockPos, getKey(matchingCounterpartsPlants, blockState.getBlock()), colorType);
                            level.setBlockAndUpdate(blockPos, blockState.setValue(BIOMES_COLOR_SOURCE, biomesColorSource));
                        }
                    }
                    else if (blockState.getBlock() instanceof TintedHugeLilyPadBOP) {

                        blockPosList =  checkTintedHugeLily(blockPos, blockState);
                        for (BlockPos blockPosToSet : blockPosList)
                            setColorAndColorTypeInBlockEntity(level, blockPosToSet, blockState, colorType);
                    }
                    else
                        onlyChangeColor(level, blockPos, blockState, colorType, 0);

                    if (!hasInfinity)
                        itemStack.hurtAndBreak(1, player, (l) -> l.broadcastBreakEvent(interactionHand));
                }
                level.playSound(null, blockPos, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.addDestroyBlockEffect(blockPos, blockState);
            } else {
                level.playSound(null, blockPos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            return 1;
        }
        else if (isShears && hasSilkTouch && changeIntoVanillaPermission) { // If shears are used on a mod plant block , the block will be turned back into a vanilla plant

            if (blockState.getBlock() instanceof TintedPottedPlantBlock) {
                if (player instanceof ServerPlayer) {
                    Block plantsBlock = counterpartIDMap.get(getCounterpart(level, blockPos));
                    if (plantsBlock == null)
                        return 0;
                    level.setBlock(blockPos, plantsBlock.withPropertiesOf(blockState), 3);
                }
                level.playSound(null, blockPos, SoundEvents.AZALEA_LEAVES_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.playSound(null, blockPos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.addDestroyBlockEffect(blockPos, blockState);
                return 1;

            } else {
                if (player instanceof ServerPlayer) { // mod -> vanilla

                    Block plantsBlock = counterpartIDMap.get(getCounterpart(level, blockPos));
                    ColorType colorType = getColorType(level, blockPos);
                    if (plantsBlock == null)
                        return 0;

                    if (isBOPLoaded && plantsBlock instanceof HugeLilyPadBlock)
                        blockPosList = hugeLilyPadHandle(level, blockPos, blockState, plantsBlock, blockHitResult, colorType);
                    else {

                        level.setBlock(blockPos, plantsBlock.withPropertiesOf(blockState), plantsChangedIntoTintedWithFlag.get(plantsBlock));

                        if (doublePlant) {
                            BlockPos blockPosSecondPart = blockPos;
                            blockPosSecondPart = blockState.getValue(HALF).equals(LOWER) ? blockPosSecondPart.above() : blockPosSecondPart.below();
                            level.setBlock(blockPosSecondPart, plantsBlock.withPropertiesOf(level.getBlockState(blockPosSecondPart)), plantsChangedIntoTintedWithFlag.get(plantsBlock));
                        } else if (isBOPLoaded && plantsBlock instanceof HugeCloverPetalBlock) {
                            blockPosList = hugeCloverHandle(level, blockPos, blockState, plantsBlock);
                        }
                    }
                }
                pass = true;
            }

        } else if (isShears && isFertileState(blockState) && !hasSilkTouch) {
            if (player instanceof ServerPlayer)
                level.setBlockAndUpdate(blockPos, blockState.setValue(FERTILE, Boolean.FALSE));
            pass = true;
        }

        if (pass) {
            level.playSound(null, blockPos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(player, GameEvent.SHEAR, blockPos);
            level.addDestroyBlockEffect(blockPos, blockState);
            int hurtAndBreak = 1;

            for (BlockPos blockPosOnList : blockPosList) {
                level.addDestroyBlockEffect(blockPosOnList, level.getBlockState(blockPosOnList));
                hurtAndBreak++;
            }

            itemStack.hurtAndBreak(hurtAndBreak, player, (l) -> l.broadcastBreakEvent(interactionHand));
            player.swing(player.getUsedItemHand(), true);
        }
        return 0;
    }

    public static int useOnBarsPlant(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {

        Direction hitDirection = blockHitResult.getDirection();
        int clicked_side =  blockState.getValue(CLICKED_SIDE);
        Block block = blockState.getBlock();
        ItemStack itemStack = player.getMainHandItem();
        boolean hasSilkTouch = EnchantmentHelper.hasSilkTouch(itemStack);
        boolean isSprintKeyPush = Minecraft.getInstance().options.keySprint.isDown();
        boolean north = blockState.getValue(NORTH);
        boolean east = blockState.getValue(EAST);
        boolean south = blockState.getValue(SOUTH);
        boolean west = blockState.getValue(WEST);

        Direction facing = blockState.getValue(FACING);
        boolean fNorth = facing.equals(Direction.NORTH);
        boolean fEast = facing.equals(Direction.EAST);
        boolean fSouth = facing.equals(Direction.SOUTH);
        boolean fWest = facing.equals(Direction.WEST);

        boolean anyFace = fNorth || fEast || fWest || fSouth;

        boolean fourSides = north && east && south && west;
        boolean straight = ((fNorth || fSouth) && (west || east) && !north && !south) || ((fEast || fWest ) && ((south || north)) && !east && !west);
        boolean outer = ((fNorth || fWest) && north && west && !south && !east) || ((fNorth || fEast) && north && east && !south && !west) || ((fSouth || fEast) && south && east && !north && !west) || ((fSouth || fWest) && south && west && !north && !east);
        boolean inner = ((fNorth || fWest) && south && east && !north && !west) || ((fNorth || fEast) && south && west && !north && !east) || ((fSouth || fEast) && north && west && !south && !east) || ((fSouth || fWest) && north && east && !south && !west);
        boolean innerT = (anyFace && south && east && !north && west) || (anyFace && south && east && north && !west) || (anyFace && south && !east && north && west) || (anyFace && north && west && !south && east);

        if ((fourSides || straight || outer || inner || innerT) && compareOnMap(blockState, itemStack, matchingBarsWithPlant) && isSprintKeyPush && (!(block instanceof TintedPlantInBars) || (block instanceof TintedPlantInBars && compareColors(blockPos, itemStack)))) {
            if ((fourSides && (clicked_side == 3 || clicked_side == 4)) || ((straight || outer || inner || innerT) && clicked_side == 1)) {
                level.playSound(null, blockPos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
                return 0;
            }
            if (player instanceof ServerPlayer)
                if (fourSides && hitDirection.equals(Direction.UP) && clicked_side != 0) {
                    level.playSound(null, blockPos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
                    return 0;
                }
                else if (fourSides && hitDirection.equals(Direction.UP))
                    level.setBlockAndUpdate(blockPos, blockState.setValue(CLICKED_SIDE, 4));
                else
                    level.setBlockAndUpdate(blockPos, blockState.setValue(CLICKED_SIDE, clicked_side+1));

            if (isTintedBarsBlock(blockState)) {
                Block blockCounterpart = getKey(matchingCounterpartsPlants, ((BlockItem) itemStack.getItem()).getBlock());
                keepData(blockPos, getKey(matchingBarsWithPlant, blockCounterpart), getColorTypeFromNBT(itemStack));
            }

            if (!player.isCreative())
                itemStack.shrink(1);
            level.playSound(null, blockPos, SoundEvents.GRASS_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
            return 1;
        } else if (itemStack.getItem() instanceof ShearsItem && !hasSilkTouch) {

            if (player instanceof ServerPlayer) {
                ItemStack itemStackContent = new ItemStack(matchingBarsWithPlant.get(blockState.getBlock()));
                if (block instanceof TintedPlantInBars)
                    setColorOnItemStack(itemStackContent, Objects.requireNonNull(getColorType(level, blockPos)));

                if (clicked_side == 0)
                    blockState = IRON_BARS.defaultBlockState().setValue(NORTH, north).setValue(EAST, east).setValue(SOUTH, south).setValue(WEST, west).setValue(WATERLOGGED, blockState.getValue(WATERLOGGED));
                else if (clicked_side == 4)
                    blockState = blockState.setValue(CLICKED_SIDE, 0);
                else
                    blockState = blockState.setValue(CLICKED_SIDE, clicked_side - 1);

                level.setBlockAndUpdate(blockPos, blockState);
                Block.popResourceFromFace(level, blockPos, Direction.UP,  itemStackContent);
                itemStack.hurtAndBreak(1, player, (e) -> {
                    e.broadcastBreakEvent(interactionHand);
                });
                level.playSound(null, blockPos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
                return 1;
            }
        }
        return 0;
    }




    private static List<BlockPos> hugeCloverHandle(Level level, BlockPos blockPos, BlockState blockState, Block plantsBlock) {

        List<BlockPos> blockPosList = new ArrayList<>();

        Direction direction = blockState.getValue(FACING);
        BlockPos blockPos2 = blockPos.offset(direction.getOpposite().getNormal());
        BlockPos blockPos3 = blockPos.offset(direction.getClockWise().getNormal());
        BlockPos blockPos4 = blockPos3.offset(direction.getOpposite().getNormal());

        if (setHugeClover(level, blockPos2, blockState.getBlock(), plantsBlock)) blockPosList.add(blockPos2);
        if (setHugeClover(level, blockPos3, blockState.getBlock(), plantsBlock)) blockPosList.add(blockPos3);
        if (setHugeClover(level, blockPos4, blockState.getBlock(), plantsBlock)) blockPosList.add(blockPos4);

        return blockPosList;
    }
    private static List<BlockPos> hugeLilyPadHandle(Level level, BlockPos blockPos, BlockState blockState, Block plantsBlock, BlockHitResult blockHitResult, ColorType colorType) {

        List<BlockPos> blockPosList = checkTintedHugeLily(blockPos, blockState);

        if (blockState.getValue(VARIANT_LILY).equals(0)) {
            for (BlockPos blockPosToSet : blockPosList) {
                BlockState blockStateCurrent = level.getBlockState(blockPosToSet);
                keepData(blockPosToSet, blockState.getBlock(), ColorType.PLAINS);
                GrassesQuarterProperty oldValue = blockStateCurrent.getValue(GRASSES_QUARTER);
                QuarterProperty newValue = mapFromGrassesToQuarterProperty(oldValue);
                level.setBlock(blockPosToSet, plantsBlock.withPropertiesOf(blockStateCurrent).setValue(QUARTER, newValue), 19);
            }
        }  else {
            for (BlockPos blockPosToSet : blockPosList) {
                BlockState blockStateCurrent = level.getBlockState(blockPosToSet);
                keepData(blockPosToSet, blockState.getBlock(), getColorType(level, blockPosToSet));
                level.setBlock(blockPosToSet, blockStateCurrent.getBlock().withPropertiesOf(blockStateCurrent).setValue(VARIANT_LILY, 0), 19);
            }
            if (blockState.getValue(VARIANT_LILY).equals(1)) {
                ItemStack itemStackToPop = new ItemStack(WATERLILY_TINTED.get());
                assert colorType != null;
                setColorOnItemStack(itemStackToPop, colorType);
                Block.popResourceFromFace(level, blockPos, blockHitResult.getDirection(), itemStackToPop);
            } else if (blockState.getValue(VARIANT_LILY).equals(2)) {
                Block.popResourceFromFace(level, blockPos, blockHitResult.getDirection(), new ItemStack(WATERLILY));
            }
        }
        return blockPosList;
    }
    public static boolean setHugeClover(Level level, BlockPos blockPos, Block blockCurrent, Block blockToSet) {

        if (level.getBlockState(blockPos).is(blockCurrent)) {
            if (!level.isClientSide) {
                keepData(blockPos, blockCurrent, PLAINS);
                level.setBlockAndUpdate(blockPos, blockToSet.defaultBlockState().setValue(FACING, level.getBlockState(blockPos).getValue(FACING)));
            }
            return true;
        }
        return false;
    }
    public static List<BlockPos> checkHugeLily(BlockPos blockPos, BlockState blockStateCurrent) {

        List<BlockPos> blockPosList = new ArrayList<>();
        QuarterProperty quarterProperty = blockStateCurrent.getValue(HugeLilyPadBlock.QUARTER);
        Direction direction = blockStateCurrent.getValue(FACING);

        BlockPos blockPos2;
        Vec3i ClockWise = direction.getClockWise().getNormal();
        Vec3i CClockWise = direction.getCounterClockWise().getNormal();
        Vec3i CCWClockWise = direction.getCounterClockWise().getClockWise().getNormal();
        Vec3i ClockWiseCCW = direction.getClockWise().getCounterClockWise().getNormal();
        Vec3i Opposite = direction.getOpposite().getNormal();

        blockPosList.add(blockPos);

        if(quarterProperty.equals(QuarterProperty.SOUTH_EAST)) {
            blockPos2 = blockPos.offset(CCWClockWise);
            blockPosList.add(blockPos2);
            blockPosList.add(blockPos.offset(CClockWise));
            blockPosList.add(blockPos2.offset(CClockWise));

        } else if (quarterProperty.equals(QuarterProperty.SOUTH_WEST)) {
            blockPos2 = blockPos.offset(ClockWiseCCW);
            blockPosList.add(blockPos2);
            blockPosList.add(blockPos.offset(ClockWise));
            blockPosList.add(blockPos2.offset(ClockWise));

        } else if (quarterProperty.equals(QuarterProperty.NORTH_EAST)) {
            blockPos2 = blockPos.offset(Opposite);
            blockPosList.add(blockPos2);
            blockPosList.add(blockPos.offset(CClockWise));
            blockPosList.add(blockPos2.offset(CClockWise));

        } else if (quarterProperty.equals(QuarterProperty.NORTH_WEST)) {
            blockPos2 = blockPos.offset(Opposite);
            blockPosList.add(blockPos2);
            blockPosList.add(blockPos.offset(ClockWise));
            blockPosList.add(blockPos2.offset(ClockWise));
        }
        return blockPosList;
    }

    public static List<BlockPos> checkTintedHugeLily(BlockPos blockPos, BlockState blockStateCurrent) {

        List<BlockPos> blockPosList = new ArrayList<>();
        GrassesQuarterProperty quarterProperty = blockStateCurrent.getValue(GRASSES_QUARTER);
        Direction direction = blockStateCurrent.getValue(FACING);

        BlockPos blockPos2;
        Vec3i ClockWise = direction.getClockWise().getNormal();
        Vec3i CClockWise = direction.getCounterClockWise().getNormal();
        Vec3i CCWClockWise = direction.getCounterClockWise().getClockWise().getNormal();
        Vec3i ClockWiseCCW = direction.getClockWise().getCounterClockWise().getNormal();
        Vec3i Opposite = direction.getOpposite().getNormal();

        blockPosList.add(blockPos);

        if(quarterProperty.equals(SOUTH_EAST)) {
            blockPos2 = blockPos.offset(CCWClockWise);
            blockPosList.add(blockPos2);
            blockPosList.add(blockPos.offset(CClockWise));
            blockPosList.add(blockPos2.offset(CClockWise));

        } else if (quarterProperty.equals(SOUTH_WEST)) {
            blockPos2 = blockPos.offset(ClockWiseCCW);
            blockPosList.add(blockPos2);
            blockPosList.add(blockPos.offset(ClockWise));
            blockPosList.add(blockPos2.offset(ClockWise));

        } else if (quarterProperty.equals(NORTH_EAST)) {
            blockPos2 = blockPos.offset(Opposite);
            blockPosList.add(blockPos2);
            blockPosList.add(blockPos.offset(CClockWise));
            blockPosList.add(blockPos2.offset(CClockWise));

        } else if (quarterProperty.equals(NORTH_WEST)) {
            blockPos2 = blockPos.offset(Opposite);
            blockPosList.add(blockPos2);
            blockPosList.add(blockPos.offset(ClockWise));
            blockPosList.add(blockPos2.offset(ClockWise));
        }
        return blockPosList;
    }

    public static GrassesQuarterProperty mapFromQuarterPropertyToGrasses(QuarterProperty oldValue) {
        return switch (oldValue) {
            case SOUTH_EAST -> GrassesQuarterProperty.SOUTH_EAST;
            case SOUTH_WEST -> GrassesQuarterProperty.SOUTH_WEST;
            case NORTH_WEST -> GrassesQuarterProperty.NORTH_WEST;
            case NORTH_EAST -> GrassesQuarterProperty.NORTH_EAST;
        };
    }

    public static QuarterProperty mapFromGrassesToQuarterProperty(GrassesQuarterProperty oldValue) {
        return switch (oldValue) {
            case SOUTH_EAST -> QuarterProperty.SOUTH_EAST;
            case SOUTH_WEST -> QuarterProperty.SOUTH_WEST;
            case NORTH_WEST -> QuarterProperty.NORTH_WEST;
            case NORTH_EAST -> QuarterProperty.NORTH_EAST;
        };
    }
}
