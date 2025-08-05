package net.night.grasses.block.blockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.night.grasses.block.blockEntity.screen.DyeingStationMenu;
import net.night.grasses.block.blockEntity.util.TickAbleBlockEntity;
import net.night.grasses.colorManagers.ColorType;
import net.night.grasses.config.GrassesConfig;
import net.night.grasses.init.ItemsRegister;
import net.night.grasses.item.DyeingBoneMealItem;
import net.night.grasses.item.DyeingItem;
import net.night.grasses.item.DyeingTool;
import net.night.grasses.recipe.DyeingStationRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static net.minecraft.world.item.Items.AIR;
import static net.minecraft.world.item.Items.BUCKET;
import static net.night.grasses.data.ModData.colorTypeList;
import static net.night.grasses.data.ModData.ingredientsList;
import static net.night.grasses.block.station.DyeingStationBlock.FACING;
import static net.night.grasses.data.ModMethods.*;
import static net.night.grasses.init.BlockEntitiesRegister.DYEING_STATION_BE;

public class DyeingStationBlockEntity extends BlockEntity implements TickAbleBlockEntity, MenuProvider {
    private static final int SLOT = 0;
    boolean clearColor = false;
    private ColorType colorTypeSlot2 = ColorType.valueOf("PLAINS");
    private ColorType colorTypeSlot3 = ColorType.valueOf("PLAINS");
    private Item itemSlot1Cache = null;
    private Item itemSlot2Cache = null;
    private Item itemSlot3Cache = null;
    protected final ContainerData containerData;
    private int progress = 0;
    private int maxProgress = 78;
    private int countDyeToMaxRefill = 0;
    private boolean changeColorCaseAndRefillAble = false;

    private final ItemStackHandler itemStackHandlerInputSlot1 = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isItemValid(int slot, ItemStack itemStack) {
            return itemStack.getItem().equals(Items.WATER_BUCKET) || itemStack.getItem().equals(BUCKET);
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack itemStack, boolean simulate) {
            if (!isItemValid(slot, itemStack))
                return itemStack;
            return super.insertItem(slot, itemStack, simulate);
        }
    };

    private LazyOptional<ItemStackHandler> lazyItemHandlerSlot1 = LazyOptional.of(() -> itemStackHandlerInputSlot1);
    public LazyOptional<ItemStackHandler> getLazyItemHandlerSlot1() {
        return lazyItemHandlerSlot1;
    }

    private final ItemStackHandler itemStackHandlerInputSlot2 = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isItemValid(int slot, ItemStack itemStack) {
            return itemStack.getItem() instanceof BoneMealItem || itemStack.getItem() instanceof DyeingTool || itemStack.getItem().equals(Items.PHANTOM_MEMBRANE);
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack itemStack, boolean simulate) {
            if (!isItemValid(slot, itemStack))
                return itemStack;
            return super.insertItem(slot, itemStack, simulate);
        }
    };

    private LazyOptional<ItemStackHandler> lazyItemHandlerSlot2 = LazyOptional.of(() -> itemStackHandlerInputSlot2);
    public LazyOptional<ItemStackHandler> getLazyItemHandlerSlot2() {
        return lazyItemHandlerSlot2;
    }

    private final ItemStackHandler itemStackHandlerInputSlot3 = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
        @Override
        public boolean isItemValid(int slot, ItemStack itemStack) {
            return ingredientsList.contains(itemStack.getItem()) || itemStack.getItem().equals(Items.WATER_BUCKET) || itemStack.getItem().equals(BUCKET) || itemStack.getItem().equals(ItemsRegister.GRASSES_DYE.get());
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack itemStack, boolean simulate) {
            if (!isItemValid(slot, itemStack))
                return itemStack;
            return super.insertItem(slot, itemStack, simulate);
        }
    };

    private LazyOptional<ItemStackHandler> lazyItemHandlerSlot3 = LazyOptional.of(() -> itemStackHandlerInputSlot3);
    public LazyOptional<ItemStackHandler> getLazyItemHandlerSlot3() {
        return lazyItemHandlerSlot3;
    }

    private final ItemStackHandler itemStackHandlerOutputSlot0 = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
        @Override
        public boolean isItemValid(int slot, ItemStack itemStack) {
            return false;
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack itemStack, boolean simulate) {
            if (!isItemValid(slot, itemStack))
                return itemStack;
            return super.insertItem(slot, itemStack, simulate);
        }
    };

    private LazyOptional<ItemStackHandler> lazyItemHandlerSlot0 = LazyOptional.of(() -> itemStackHandlerOutputSlot0);
    public LazyOptional<ItemStackHandler> getLazyItemHandlerSlot0() {
        return lazyItemHandlerSlot0;
    }

    private float rotation;

    public DyeingStationBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(DYEING_STATION_BE.get(), pPos, pBlockState);
        this.containerData = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> DyeingStationBlockEntity.this.progress;
                    case 1 -> DyeingStationBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> DyeingStationBlockEntity.this.progress = pValue;
                    case 1 -> DyeingStationBlockEntity.this.maxProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    public ItemStack getRenderStackInSlot0() {
        return itemStackHandlerOutputSlot0.getStackInSlot(SLOT);
    }
    public ItemStack getRenderStackInSlot1() {
        return itemStackHandlerInputSlot1.getStackInSlot(SLOT);
    }
    public ItemStack getRenderStackInSlot2() {
        return itemStackHandlerInputSlot2.getStackInSlot(SLOT);
    }
    public ItemStack getRenderStackInSlot3() {
        return itemStackHandlerInputSlot3.getStackInSlot(SLOT);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(4);

        inventory.setItem(0, itemStackHandlerOutputSlot0.getStackInSlot(SLOT));
        inventory.setItem(1, itemStackHandlerInputSlot1.getStackInSlot(SLOT));
        inventory.setItem(2, itemStackHandlerInputSlot2.getStackInSlot(SLOT));
        inventory.setItem(3, itemStackHandlerInputSlot3.getStackInSlot(SLOT));

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public float getRenderingRotation() {
        rotation += 0.1f;
        if (rotation >= 360)
            rotation = 0;

        return rotation;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {

        BlockState blockState = this.getBlockState();
        Direction direction = blockState.getValue(FACING);
        Direction left = direction.getClockWise();
        Direction opposite = direction.getOpposite();
        Direction right =  opposite.getClockWise();

        ItemStack slot1 = this.itemStackHandlerInputSlot1.getStackInSlot(SLOT);
        ItemStack slot3 = this.itemStackHandlerInputSlot3.getStackInSlot(SLOT);

        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == opposite)
                return this.lazyItemHandlerSlot2.cast();
            else if (side == Direction.DOWN && slot1.getItem().equals(BUCKET))
                return this.lazyItemHandlerSlot1.cast();
            else if (side == Direction.DOWN && slot3.getItem().equals(BUCKET))
                return this.lazyItemHandlerSlot3.cast();
            else if (side == Direction.DOWN)
                return this.lazyItemHandlerSlot0.cast();
            else if (side == left)
                return this.lazyItemHandlerSlot1.cast();
            else if (side == right)
                return this.lazyItemHandlerSlot3.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandlerSlot0 = LazyOptional.of(() -> itemStackHandlerOutputSlot0);
        lazyItemHandlerSlot1 = LazyOptional.of(() -> itemStackHandlerInputSlot1);
        lazyItemHandlerSlot2 = LazyOptional.of(() -> itemStackHandlerInputSlot2);
        lazyItemHandlerSlot3 = LazyOptional.of(() -> itemStackHandlerInputSlot3);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandlerSlot0.invalidate();
        lazyItemHandlerSlot1.invalidate();
        lazyItemHandlerSlot2.invalidate();
        lazyItemHandlerSlot3.invalidate();
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.grasses.dyeing_station");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new DyeingStationMenu(containerId, playerInventory, this, this.containerData);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {

        pTag.put("ItemStackHandlerSlot0", itemStackHandlerOutputSlot0.serializeNBT());
        pTag.put("ItemStackHandlerSlot1", itemStackHandlerInputSlot1.serializeNBT());
        pTag.put("ItemStackHandlerSlot2", itemStackHandlerInputSlot2.serializeNBT());
        pTag.put("ItemStackHandlerSlot3", itemStackHandlerInputSlot3.serializeNBT());
        pTag.putInt("dyeing_station_progress", progress);
        pTag.putInt("dyeing_station_max_progress", maxProgress);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        if (pTag.contains("ItemStackHandlerSlot0", Tag.TAG_COMPOUND)) {
            itemStackHandlerOutputSlot0.deserializeNBT(pTag.getCompound("ItemStackHandlerSlot0"));
        }

        if (pTag.contains("ItemStackHandlerSlot1", Tag.TAG_COMPOUND)) {
            itemStackHandlerInputSlot1.deserializeNBT(pTag.getCompound("ItemStackHandlerSlot1"));
        }

        if (pTag.contains("ItemStackHandlerSlot2", Tag.TAG_COMPOUND)) {
            itemStackHandlerInputSlot2.deserializeNBT(pTag.getCompound("ItemStackHandlerSlot2"));
        }

        if (pTag.contains("ItemStackHandlerSlot3", Tag.TAG_COMPOUND)) {
            itemStackHandlerInputSlot3.deserializeNBT(pTag.getCompound("ItemStackHandlerSlot3"));
        }


        progress = pTag.getInt("dyeing_station_progress");
        maxProgress = pTag.getInt("dyeing_station_max_progress");
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState, BlockEntity blockEntity) {

        if (this.level == null || this.level.isClientSide() || !(blockEntity instanceof DyeingStationBlockEntity dyeingStationBlockEntity))
            return;
        if (hasRecipe()) {

            if (allowIncreaseProgress()) {

                increaseCraftingProgress();
                setChanged(level, blockPos, blockState);

                if (hasPrograssFinished()) {
                    craftItem();
                    resetProgress();
                }
            } else {
                resetProgress();
            }
        }
        else {
            resetProgress();

            if (!itemStackHandlerOutputSlot0.getStackInSlot(SLOT).getItem().equals(AIR))
                extractFullStackAtOnceFromSlot0(blockPos, dyeingStationBlockEntity, blockEntity);

            if (!(itemStackHandlerInputSlot2.getStackInSlot(SLOT).getItem() instanceof DyeingTool))
                insertFullStackAtOnce(blockPos, dyeingStationBlockEntity, blockEntity, this.getBlockState().getValue(FACING).getOpposite(), itemStackHandlerInputSlot2);

            if (!itemStackHandlerInputSlot3.getStackInSlot(SLOT).getItem().equals(AIR))
                insertFullStackAtOnce(blockPos, dyeingStationBlockEntity, blockEntity, this.getBlockState().getValue(FACING).getOpposite().getClockWise(), itemStackHandlerInputSlot3);

        }
    }

    private boolean allowIncreaseProgress() {

        ItemStack itemStackInputSlot2 = this.itemStackHandlerInputSlot2.getStackInSlot(SLOT);
        ItemStack itemStackInputSlot3 = this.itemStackHandlerInputSlot3.getStackInSlot(SLOT);
        ItemStack itemStackOutputSlot0 = this.itemStackHandlerOutputSlot0.getStackInSlot(SLOT);

        Item itemSlot1 = this.itemStackHandlerInputSlot1.getStackInSlot(SLOT).getItem();
        Item itemSlot2 = this.itemStackHandlerInputSlot2.getStackInSlot(SLOT).getItem();
        Item itemSlot3 = this.itemStackHandlerInputSlot3.getStackInSlot(SLOT).getItem();
        int itemSlot3Count = this.itemStackHandlerInputSlot3.getStackInSlot(SLOT).getCount();

        clearColor = itemSlot3.equals((Items.WATER_BUCKET));

        ColorType colorTypeSlot0 = getColorTypeFromNBT(this.itemStackHandlerOutputSlot0.getStackInSlot(SLOT));

        if (itemSlot1Cache != null && (!itemSlot1.equals(itemSlot1Cache) || !itemSlot2.equals(itemSlot2Cache) || !itemSlot3.equals(itemSlot3Cache)))
            return false;
        else {
            itemSlot1Cache = itemSlot1;
            itemSlot2Cache = itemSlot2;
            itemSlot3Cache = itemSlot3;
        }

        if (!clearColor) {
            colorTypeSlot2 = getColorTypeFromNBT(itemStackInputSlot2);

            if (itemSlot3 instanceof DyeingItem)
                colorTypeSlot3 = getColorTypeFromNBT(itemStackInputSlot3);
            else
                colorTypeSlot3 = colorTypeList.get(ingredientsList.indexOf(this.itemStackHandlerInputSlot3.getStackInSlot(SLOT).getItem()));
        }

        if (ingredientsList.contains(itemSlot3) || itemSlot3 instanceof DyeingItem || itemSlot3.equals((Items.WATER_BUCKET))) {

            if (itemSlot2 instanceof DyeingTool) {
                int durability = this.itemStackHandlerInputSlot2.getStackInSlot(SLOT).getDamageValue();
                if (durability > 0) {
                    countDyeToMaxRefill = (int) Math.ceil((double) durability / 64);
                    if (countDyeToMaxRefill > itemSlot3Count)
                        countDyeToMaxRefill = itemSlot3Count;

                }
            }

            if (!clearColor && hasBlockStateTag(itemStackInputSlot2) && colorTypeSlot2.equals(colorTypeSlot3) && countDyeToMaxRefill == 0) {
                return false;
            } else if (hasBlockStateTag(itemStackOutputSlot0) && !colorTypeSlot0.equals(colorTypeSlot3)) {
                return false;
            } else if (itemSlot2 instanceof DyeingTool && !itemSlot3.equals(Items.WATER_BUCKET) && (!colorTypeSlot2.equals(colorTypeSlot3) || !hasBlockStateTag(itemStackInputSlot2))) {

                if (itemSlot3Count <32 || !GrassesConfig.CommonConfig.ALLOW_CHANGE_COLOR_OF_DYEING_TOOL.get())
                    return false;
                else {
                    changeColorCaseAndRefillAble = true;
                    return true;
                }
            } else if (itemSlot2 instanceof DyeingBoneMealItem && hasBlockStateTag(itemStackInputSlot2) && !itemSlot3.equals((Items.WATER_BUCKET)) && !GrassesConfig.CommonConfig.ALLOW_CHANGE_COLOR_OF_DYEING_BONEMEAL.get()) {
                return false;
            } else if (itemSlot2 instanceof BoneMealItem && !(itemSlot2 instanceof DyeingBoneMealItem) && !GrassesConfig.CommonConfig.ALLOW_CRAFT_DYEING_BONEMEAL.get()) {
                return false;
            } else if (itemSlot2 instanceof DyeingItem && !GrassesConfig.CommonConfig.ALLOW_CRAFT_DYE.get()) {
                return false;
            } else
                return true;
        }
        return false;
    }

    private void resetProgress() {
        progress = 0;
        itemSlot1Cache = null;
        itemSlot2Cache = null;
        itemSlot3Cache = null;
        clearColor = false;
        countDyeToMaxRefill = 0;
        changeColorCaseAndRefillAble = false;
    }

    private void extractItem (ItemStackHandler handler, int amount) {
        handler.extractItem(SLOT, amount,false);
    }
    private void insertItem (ItemStackHandler handler, ItemStack itemStack) {
        handler.insertItem(SLOT, itemStack,false);
    }
    private void setStack (ItemStackHandler handler, int count, ItemStack itemStack) {
        handler.setStackInSlot(SLOT, new ItemStack(itemStack.getItem(),
                count + itemStack.getCount()));
    }

    private void craftItem() {

        ItemStackHandler outputSlot0 = this.itemStackHandlerOutputSlot0;
        ItemStackHandler inputSlot1 = this.itemStackHandlerInputSlot1;
        ItemStackHandler inputSlot2 = this.itemStackHandlerInputSlot2;
        ItemStackHandler inputSlot3 = this.itemStackHandlerInputSlot3;
        ItemStack inputSlot2Stack =  inputSlot2.getStackInSlot(SLOT);
        ItemStack inputSlot3Stack =  inputSlot3.getStackInSlot(SLOT);
        int slot2Count = inputSlot2Stack.getCount();

        Optional<DyeingStationRecipe> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().getResultItem(null);

        int durability = inputSlot2Stack.getDamageValue();
        boolean changeAmount = false;

        //Slot 1
        extractItem(inputSlot1, 1);
        insertItem(inputSlot1, new ItemStack(BUCKET));

        //Slot 3
        if (inputSlot2Stack.getItem() instanceof DyeingTool) {

            if (clearColor)
                extractItem(inputSlot3, 1);
            else if (changeColorCaseAndRefillAble) {
                countDyeToMaxRefill = Math.min(inputSlot3Stack.getCount() - 32, countDyeToMaxRefill);
                extractItem(inputSlot3, countDyeToMaxRefill+32);
            } else
                extractItem(inputSlot3, countDyeToMaxRefill);

            durability = durability - countDyeToMaxRefill * 64;
        } else
            extractItem(inputSlot3, 1);

        //Slot2
        if (inputSlot2Stack.getItem() instanceof BoneMealItem) {
            extractItem(inputSlot2, inputSlot2Stack.getCount());
            changeAmount = true;
        } else
            extractItem(inputSlot2, 1);

        //Output Slot
        ItemStack outputSlot0Stack = outputSlot0.getStackInSlot(SLOT);
        setStack(outputSlot0, outputSlot0Stack.getCount(), result);



        if (!clearColor) {
            if (changeAmount)
                setStack(outputSlot0, slot2Count-1, result);
            outputSlot0Stack = outputSlot0.getStackInSlot(SLOT);
            setColorOnItemStack(outputSlot0Stack, colorTypeSlot3);
        }
        else {
            insertItem(inputSlot3, new ItemStack(BUCKET));
            setStack(outputSlot0, slot2Count-1, result);
        }

        if (inputSlot2Stack.getItem() instanceof DyeingTool) {
            outputSlot0Stack = outputSlot0.getStackInSlot(SLOT);
            outputSlot0Stack.setDamageValue(durability);
        }
    }

    private boolean hasPrograssFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftingProgress() {
        progress++;
    }

    private boolean hasRecipe() {
        Optional<DyeingStationRecipe> recipe = getCurrentRecipe();
        if(recipe.isEmpty()) {
            return false;
        }
        ItemStack result = recipe.get().getResultItem(getLevel().registryAccess()); //!! null

        return canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private Optional<DyeingStationRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(4);
            inventory.setItem(0, this.itemStackHandlerOutputSlot0.getStackInSlot(SLOT));
            inventory.setItem(1, this.itemStackHandlerInputSlot1.getStackInSlot(SLOT));
            inventory.setItem(2, this.itemStackHandlerInputSlot2.getStackInSlot(SLOT));
            inventory.setItem(3, this.itemStackHandlerInputSlot3.getStackInSlot(SLOT));


        return this.level.getRecipeManager().getRecipeFor(DyeingStationRecipe.Type.INSTANCE, inventory, level);
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemStackHandlerOutputSlot0.getStackInSlot(SLOT).isEmpty() || this.itemStackHandlerOutputSlot0.getStackInSlot(SLOT).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemStackHandlerOutputSlot0.getStackInSlot(SLOT).getCount() + count <= this.itemStackHandlerOutputSlot0.getStackInSlot(SLOT).getMaxStackSize();
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithFullMetadata();
    }

    private void extractFullStackAtOnceFromSlot0(BlockPos blockPos, DyeingStationBlockEntity dyeingStationBlockEntity, BlockEntity blockEntity) {
        Direction stationExportSide = Direction.DOWN;
        BlockPos extractHopperBlockPos = blockPos.relative(stationExportSide);

        BlockEntity extractHopperBE = level.getBlockEntity(extractHopperBlockPos);
        if (extractHopperBE == null) return;

        LazyOptional<IItemHandler> hopperHandlerOptional = extractHopperBE.getCapability(ForgeCapabilities.ITEM_HANDLER, stationExportSide.getOpposite());
        if (!hopperHandlerOptional.isPresent()) return;

        IItemHandler hopperHandler = hopperHandlerOptional.resolve().orElse(null);
        if (hopperHandler == null) return;

        ItemStack stackToExtract = dyeingStationBlockEntity.extractFullStack(SLOT, true, itemStackHandlerOutputSlot0);
        if (stackToExtract.isEmpty()) return;

        int slot = -1;
        for (int hopperSlot = 0; hopperSlot < hopperHandler.getSlots(); hopperSlot++) {
            ItemStack hopperStack = hopperHandler.getStackInSlot(hopperSlot);
            ItemStack stationStack = dyeingStationBlockEntity.itemStackHandlerOutputSlot0.getStackInSlot(SLOT);

            ColorType colorInHopper = getColorTypeFromNBT(hopperStack);
            ColorType colorInStation = getColorTypeFromNBT(stationStack);

            boolean same = colorInHopper.equals(colorInStation) && hopperStack.getItem().equals(stationStack.getItem());

            if (hopperStack.isEmpty() || (same && (hopperStack.getCount() + stationStack.getCount()) <= hopperStack.getMaxStackSize())) {
                slot = hopperSlot;
                break;
            }
        }

        if (slot != -1) {
            ItemStack remainder = hopperHandler.insertItem(slot, stackToExtract, true);
            if (remainder.getCount() < stackToExtract.getCount()) {
                ItemStack extracted = dyeingStationBlockEntity.extractFullStack(SLOT, false, itemStackHandlerOutputSlot0);
                hopperHandler.insertItem(slot, extracted, false);
                blockEntity.setChanged();
            }
        }
    }

    private ItemStack extractFullStack(int slot, boolean simulate, ItemStackHandler itemStackHandler) {
        ItemStack stack = itemStackHandler.getStackInSlot(slot);
        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        }
        return itemStackHandler.extractItem(slot, stack.getCount(), simulate);
    }

    private void insertFullStackAtOnce(BlockPos blockPos, DyeingStationBlockEntity dyeingStationBlockEntity, BlockEntity blockEntity, Direction stationImportSide, ItemStackHandler itemStackHandler) {
        BlockPos importHopperBlockPos = blockPos.relative(stationImportSide);

        BlockEntity importHopperBE = level.getBlockEntity(importHopperBlockPos);
        if (importHopperBE == null || blockEntity == null) return;

        LazyOptional<IItemHandler> hopperHandlerOptional = importHopperBE.getCapability(ForgeCapabilities.ITEM_HANDLER, stationImportSide.getOpposite());
        if (!hopperHandlerOptional.isPresent()) return;

        IItemHandler hopperHandler = hopperHandlerOptional.resolve().orElse(null);
        if (hopperHandler == null) return;

        for (int hopperSlot = 0; hopperSlot < hopperHandler.getSlots(); hopperSlot++) {
            ItemStack hopperStack = hopperHandler.getStackInSlot(hopperSlot);
            if (hopperStack.isEmpty()) continue;

            ItemStack importStack = hopperHandler.extractItem(hopperSlot, hopperStack.getCount(), true);
            if (importStack.isEmpty()) continue;

            ItemStack remainder = dyeingStationBlockEntity.insertStack(0, importStack, true, itemStackHandler);
            int transferred = importStack.getCount() - remainder.getCount();
            if (transferred > 0) {
                ItemStack actuallyImported = hopperHandler.extractItem(hopperSlot, transferred, false);
                dyeingStationBlockEntity.insertStack(0, actuallyImported, false, itemStackHandler);
            }
        }
    }

    private ItemStack insertStack(int slot, ItemStack stack, boolean simulate, ItemStackHandler itemStackHandler) {
        return itemStackHandler.insertItem(slot, stack, simulate);
    }
}
