package net.night.grasses.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;

public class ContainerItemHandlerAdapter implements IItemHandler {

    private final Container container;

    public ContainerItemHandlerAdapter(Container container) {
        this.container = container;
    }

    @Override
    public int getSlots() {
        return container.getContainerSize();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return container.getItem(slot);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        }
        if (!container.canPlaceItem(slot, stack)) {
            return stack;
        }

        ItemStack existing = container.getItem(slot);
        int limit = Math.min(container.getMaxStackSize(), stack.getMaxStackSize());

        if (!existing.isEmpty()) {
            if (!ItemStack.isSameItemSameTags(existing, stack)) {
                return stack;
            }
            limit -= existing.getCount();
        }

        if (limit <= 0) {
            return stack;
        }

        boolean reachedLimit = stack.getCount() > limit;

        if (!simulate) {
            ItemStack toInsert = stack.copy();
            toInsert.setCount(reachedLimit ? limit : toInsert.getCount());

            if (existing.isEmpty()) {
                container.setItem(slot, toInsert);
            } else {
                existing.grow(toInsert.getCount());
                container.setItem(slot, existing);
            }
            container.setChanged();
        }

        if (reachedLimit) {
            ItemStack remainder = stack.copy();
            remainder.shrink(limit);
            return remainder;
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        ItemStack existing = container.getItem(slot);
        if (existing.isEmpty()) {
            return ItemStack.EMPTY;
        }

        int toExtract = Math.min(amount, existing.getMaxStackSize());
        if (simulate) {
            ItemStack copy = existing.copy();
            copy.setCount(toExtract);
            return copy;
        } else {
            ItemStack extracted = container.removeItem(slot, toExtract);
            container.setChanged();
            return extracted;
        }
    }

    @Override
    public int getSlotLimit(int slot) {
        return container.getMaxStackSize();
    }

    @Override
    public boolean isItemValid(int slot, ItemStack itemStack) {
        return container.canPlaceItem(slot, itemStack);
    }
}
