package net.night.grasses.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.Hopper;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.night.grasses.block.blockEntity.DyeingStationBlockEntity;
import net.night.grasses.colorManagers.ColorType;
import net.night.grasses.inventory.ContainerItemHandlerAdapter;
import net.night.grasses.item.DyeingItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.world.level.block.HopperBlock.FACING;
import static net.night.grasses.data.MethodsLib.getColorTypeFromNBT;
import static net.night.grasses.data.MethodsLib.hasBlockStateTag;

@Mixin(HopperBlockEntity.class)
public abstract class HopperBlockEntityMixin {

    @Shadow
    private static Container getSourceContainer(Level level, Hopper hopper) {
        return null;
    }

    @Shadow
    private static Container getAttachedContainer(Level level, BlockPos pos, BlockState state) {
        return null;
    }

    @Shadow
    private static boolean isFullContainer(Container container, Direction direction) {
        return false;
    }

    @Inject(method = "suckInItems", at = @At("HEAD"), cancellable = true)
    private static void suckInItems(Level level, Hopper hopper, CallbackInfoReturnable<Boolean> cir) {
        BlockPos hopperPos = ((BlockEntity)(Object) hopper).getBlockPos();
        BlockState hopperBlockState = level.getBlockState(hopperPos);
        Direction hopperFacing = hopperBlockState.getValue(FACING);
        BlockEntity blockNextTo = level.getBlockEntity(hopperPos.relative(hopperFacing));

        if (!(blockNextTo instanceof DyeingStationBlockEntity blockEntity))
            return;

        Container container = getSourceContainer(level, hopper);
        if (container == null)
            return;

        BlockState stationBlockState = level.getBlockState(hopperPos.relative(hopperFacing));
        Direction stationFacing = stationBlockState.getValue(HorizontalDirectionalBlock.FACING);
        Direction left = stationFacing.getClockWise();
        Direction opposite = stationFacing.getOpposite();
        Direction right = opposite.getClockWise();
        LazyOptional<ItemStackHandler> optionalHandler = null;

        Direction hopperOpposite = hopperFacing.getOpposite();

        if (hopperOpposite.equals(left))
            return; // Added in case there is more to this slot than just a bucket of water in the future.
        else if (hopperOpposite.equals(right))
            optionalHandler = blockEntity.getLazyItemHandlerSlot3();
        else if (hopperOpposite.equals(opposite))
            optionalHandler = blockEntity.getLazyItemHandlerSlot2();

        if (optionalHandler == null || !optionalHandler.isPresent())
            return;

        ItemStackHandler itemStackHandler = optionalHandler.resolve().orElse(null);
        if (itemStackHandler == null)
            return;

        LazyOptional<IItemHandler> hopperHandlerOptional = ((BlockEntity)(Object) hopper).getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.UP);
        if (!hopperHandlerOptional.isPresent())
            return;

        IItemHandler hopperHandler = hopperHandlerOptional.resolve().orElse(null);

        if (hopperHandler == null)
            return;

        for (int containerSlot = 0; containerSlot < container.getContainerSize(); containerSlot++) {
            ItemStack containerStack = container.getItem(containerSlot);
            if (containerStack.isEmpty() || !itemStackHandler.isItemValid(0, containerStack)) continue;

            int hopperSlotIndex = -1;

            for (int hopperSlot = 0; hopperSlot < hopper.getContainerSize(); hopperSlot++) {
                ItemStack hopperStack = hopper.getItem(hopperSlot);

                if (hopperStack.isEmpty()) {
                    hopperSlotIndex = hopperSlot;
                    break;
                }

                boolean same;

                if (hasBlockStateTag(hopperStack) && hasBlockStateTag(containerStack)) {
                    ColorType colorInHopper = getColorTypeFromNBT(hopperStack);
                    ColorType colorInContainer = getColorTypeFromNBT(containerStack);

                    same = colorInHopper.equals(colorInContainer) && hopperStack.getItem().equals(containerStack.getItem());
                } else
                    same = hopperStack.getItem().equals(containerStack.getItem());

                if (same && hopperStack.getCount() < hopperStack.getMaxStackSize()) {
                    hopperSlotIndex = hopperSlot;
                    break;
                }
            }

            if (hopperSlotIndex != -1) {
                ItemStack toInsert = containerStack.copy();
                ItemStack remainder = hopperHandler.insertItem(hopperSlotIndex, toInsert, true);
                int insertedCount = containerStack.getCount() - remainder.getCount();

                if (insertedCount > 0) {
                    ItemStack extracted = container.removeItem(containerSlot, insertedCount);
                    hopperHandler.insertItem(hopperSlotIndex, extracted, false);

                    blockEntity.setChanged();
                    cir.setReturnValue(true);
                    return;
                }
            }
        }
    }

    @Inject(method = "pushItemsTick", at = @At("HEAD"), cancellable = true)
    private static void pushItemsTick(Level level, BlockPos hopperBlockPos, BlockState hopperBlockState, HopperBlockEntity hopperBlockEntity, CallbackInfo cir) {
        Direction hopperFacing = hopperBlockState.getValue(FACING);
        BlockEntity blockAbove = level.getBlockEntity(hopperBlockPos.above());

        if (!(blockAbove instanceof DyeingStationBlockEntity dyeingStationBlockEntity))
            return;

        Container container = getAttachedContainer(level, hopperBlockPos, hopperBlockState);
        if (container == null)
            return;

        if (isFullContainer(container, hopperFacing.getOpposite())) {
            cir.cancel();
            return;
        }

        ContainerItemHandlerAdapter containerHandler = new ContainerItemHandlerAdapter(container);

        outerLoop:
        for (int containerSlot = 0; containerSlot < container.getContainerSize(); containerSlot++) {
            ItemStack containerStack = container.getItem(containerSlot);
            if (!containerStack.isEmpty() && containerStack.getCount() >= containerStack.getMaxStackSize()) continue;

            for (int hopperSlot = 0; hopperSlot < hopperBlockEntity.getContainerSize(); hopperSlot++) {
                ItemStack hopperStack = hopperBlockEntity.getItem(hopperSlot);
                if (hopperStack.isEmpty()) continue;

                boolean outputStackedItemsInDyeingStation = hopperStack.getItem() instanceof BoneMealItem || hopperStack.getItem() instanceof DyeingItem;
                if (!outputStackedItemsInDyeingStation) continue;

                boolean same;
                if (hasBlockStateTag(hopperStack) && hasBlockStateTag(containerStack)) {
                    ColorType colorInHopper = getColorTypeFromNBT(hopperStack);
                    ColorType colorInContainer = getColorTypeFromNBT(containerStack);
                    same = colorInHopper.equals(colorInContainer) && hopperStack.getItem().equals(containerStack.getItem());
                } else
                    same = hopperStack.getItem().equals(containerStack.getItem());

                if (containerStack.isEmpty() || (same && containerStack.getCount() < containerStack.getMaxStackSize())) {
                    ItemStack toInsert = hopperStack.copy();
                    ItemStack remainder = containerHandler.insertItem(containerSlot, toInsert, true);
                    int insertedCount = hopperStack.getCount() - remainder.getCount();

                    if (insertedCount > 0) {
                        ItemStack extracted = hopperBlockEntity.removeItem(hopperSlot, insertedCount);
                        containerHandler.insertItem(containerSlot, extracted, false);

                        dyeingStationBlockEntity.setChanged();
                        cir.cancel();
                        break outerLoop;
                    }
                }
            }
        }
    }
}