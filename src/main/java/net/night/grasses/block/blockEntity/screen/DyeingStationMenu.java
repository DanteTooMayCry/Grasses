package net.night.grasses.block.blockEntity.screen;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.SlotItemHandler;
import net.night.grasses.block.blockEntity.DyeingStationBlockEntity;
import net.night.grasses.init.ItemsRegister;
import net.night.grasses.item.DyeingTool;
import org.jetbrains.annotations.NotNull;

import static net.night.grasses.data.ModData.ingredientsList;
import static net.night.grasses.init.BlocksRegister.DYEING_STATION;

public class DyeingStationMenu extends AbstractContainerMenu {
    public final DyeingStationBlockEntity blockEntity;
    public final Level level;
    private final ContainerData containerData;

    // Client Constructor
    protected DyeingStationMenu(int containerId, Inventory playerInventory, FriendlyByteBuf friendlyByteBuf) {
        this(containerId, playerInventory, playerInventory.player.level().getBlockEntity(friendlyByteBuf.readBlockPos()), new SimpleContainerData(4));
    }
    // Server Constructor
    public DyeingStationMenu(int containerId, Inventory inventory, BlockEntity blockEntity, ContainerData containerData) {
        super(MenuTypesRegister.DYEING_STATION_MENU.get(), containerId);
        checkContainerSize(inventory, 4);
        this.blockEntity = ((DyeingStationBlockEntity) blockEntity);
        this.level = inventory.player.level();
        this.containerData = containerData;

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);

        this.blockEntity.getLazyItemHandlerSlot0().ifPresent(iItemHandler -> {
            this.addSlot(new SlotItemHandler(iItemHandler, 0, 80, 59) {
                @Override
                public boolean mayPlace(@NotNull ItemStack itemStack) {
                    return false;
                }
            });
        });

        this.blockEntity.getLazyItemHandlerSlot1().ifPresent(iItemHandler -> {
            this.addSlot(new SlotItemHandler(iItemHandler,0, 61, 21) {
                @Override
                public boolean mayPlace(@NotNull ItemStack itemStack) {
                    return itemStack.getItem().equals(Items.WATER_BUCKET);
                }
            });
        });

        this.blockEntity.getLazyItemHandlerSlot2().ifPresent(iItemHandler -> {
            this.addSlot(new SlotItemHandler(iItemHandler, 0, 80, 11) {
                @Override
                public boolean mayPlace(@NotNull ItemStack itemStack) {
                    return itemStack.getItem() instanceof BoneMealItem || itemStack.getItem() instanceof DyeingTool || itemStack.getItem().equals(Items.PHANTOM_MEMBRANE);
                }
            });
        });

        this.blockEntity.getLazyItemHandlerSlot3().ifPresent(iItemHandler -> {
            this.addSlot(new SlotItemHandler(iItemHandler, 0, 99, 21) {
                @Override
                public boolean mayPlace(@NotNull ItemStack itemStack) {
                    return ingredientsList.contains(itemStack.getItem()) || itemStack.getItem().equals(Items.WATER_BUCKET) || itemStack.getItem().equals(ItemsRegister.GRASSES_DYE.get());
                }
            });
        });
        addDataSlots(containerData);
    }

    public boolean isCrafting() {
        return containerData.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = this.containerData.get(0);
        int maxProgress = this.containerData.get(1);
        int progressArrowSize = 26;

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = 4;  // must be the number of slots you have!
    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, DYEING_STATION.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
