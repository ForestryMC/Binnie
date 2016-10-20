package binnie.core.machines.inventory;

import binnie.core.machines.Machine;
import binnie.core.machines.power.ITankMachine;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

public class TransferHandler {
    public static ItemStack transfer(final ItemStack item, final IInventory origin, final IInventory destination, final boolean doAdd) {
        ItemStack ret = transferItemToInventory(item, destination, doAdd);
        if (destination instanceof ITankMachine) {
            ret = transferContainerIntoTank(ret, origin, (ITankMachine) destination, doAdd);
            ret = transferTankIntoContainer(ret, origin, (ITankMachine) destination, doAdd);
        }
        return ret;
    }

    public static ItemStack transferItemToInventory(final ItemStack item, final IInventory destination, final boolean doAdd) {
        if (item == null || destination == null) {
            return item;
        }
        ItemStack addition = item.copy();
        for (int i = 0; i < destination.getSizeInventory(); ++i) {
            addition = transferToInventory(addition, destination, new int[]{i}, doAdd, false);
            if (addition == null) {
                return null;
            }
        }
        return addition;
    }

    public static ItemStack transferToInventory(ItemStack item, final IInventory destination, final int[] targetSlots, final boolean doAdd, final boolean ignoreValidation) {
        for (final int i : targetSlots) {
            if (destination.isItemValidForSlot(i, item) || ignoreValidation) {
                if (destination.getStackInSlot(i) == null) {
                    if (doAdd) {
                        destination.setInventorySlotContents(i, item.copy());
                    }
                    return null;
                }
                if (item.isStackable()) {
                    final ItemStack merged = destination.getStackInSlot(i).copy();
                    final ItemStack[] newStacks = mergeStacks(item.copy(), merged.copy());
                    item = newStacks[0];
                    if (doAdd) {
                        destination.setInventorySlotContents(i, newStacks[1]);
                    }
                    if (item == null) {
                        return null;
                    }
                }
            }
        }
        return item;
    }

    public static ItemStack[] mergeStacks(ItemStack itemstack, final ItemStack merged) {
        if (ItemStack.areItemStackTagsEqual(itemstack, merged) && itemstack.isItemEqual(merged)) {
            final int space = merged.getMaxStackSize() - merged.stackSize;
            if (space > 0) {
                if (itemstack.stackSize > space) {
                    final ItemStack itemStack = itemstack;
                    itemStack.stackSize -= space;
                    merged.stackSize += space;
                } else if (itemstack.stackSize <= space) {
                    merged.stackSize += itemstack.stackSize;
                    itemstack = null;
                }
            }
        }
        return new ItemStack[]{itemstack, merged};
    }

    public static ItemStack transferContainerIntoTank(final ItemStack item, final IInventory origin, final ITankMachine destination, final boolean doAdd) {
        if (item == null) {
            return null;
        }
        final IFluidTank[] tanks = destination.getTanks();
        ItemStack stack = item.copy();
        for (int i = 0; i < tanks.length; ++i) {
            stack = transferToTank(stack, origin, destination, i, doAdd);
        }
        return stack;
    }

    public static ItemStack transferTankIntoContainer(final ItemStack item, final IInventory origin, final ITankMachine destination, final boolean doAdd) {
        if (item == null) {
            return null;
        }
        final IFluidTank[] tanks = destination.getTanks();
        ItemStack stack = item.copy();
        for (int i = 0; i < tanks.length; ++i) {
            stack = transferFromTank(stack, origin, destination, i, doAdd);
        }
        return stack;
    }

    public static ItemStack transferToTank(final ItemStack item, final IInventory origin, final ITankMachine destination, final int tankID, final boolean doAdd) {
        if (item == null) {
            return item;
        }
        FluidStack containerLiquid = null;
        FluidContainerRegistry.FluidContainerData containerLiquidData = null;
        for (final FluidContainerRegistry.FluidContainerData data : FluidContainerRegistry.getRegisteredFluidContainerData()) {
            if (data.filledContainer.isItemEqual(item)) {
                containerLiquidData = data;
                containerLiquid = data.fluid.copy();
                break;
            }
        }
        if (containerLiquid == null) {
            return item;
        }
        final IFluidTank tank = destination.getTanks()[tankID];
        final IValidatedTankContainer validated = Machine.getInterface(IValidatedTankContainer.class, destination);
        if (validated != null && !validated.isLiquidValidForTank(containerLiquid, tankID)) {
            return item;
        }
        final FluidStack largeAmountOfLiquid = containerLiquid.copy();
        largeAmountOfLiquid.amount = tank.getCapacity();
        final int amountAdded = tank.fill(largeAmountOfLiquid, false);
        int numberOfContainersToAdd = amountAdded / containerLiquid.amount;
        if (numberOfContainersToAdd > item.stackSize) {
            numberOfContainersToAdd = item.stackSize;
        }
        final ItemStack copy;
        ItemStack leftOverContainers = copy = item.copy();
        copy.stackSize -= numberOfContainersToAdd;
        if (leftOverContainers.stackSize <= 0) {
            leftOverContainers = null;
        }
        ItemStack emptyContainers = containerLiquidData.emptyContainer.copy();
        emptyContainers.stackSize = 0;
        final ItemStack itemStack = emptyContainers;
        itemStack.stackSize += numberOfContainersToAdd;
        if (emptyContainers.stackSize <= 0) {
            emptyContainers = null;
        }
        final ItemStack containersThatCantBeDumped = transferItemToInventory(emptyContainers, origin, false);
        if (containersThatCantBeDumped != null) {
            return item;
        }
        if (doAdd) {
            final FluidStack copy2;
            final FluidStack liquidToFillTank = copy2 = containerLiquid.copy();
            copy2.amount *= numberOfContainersToAdd;
            tank.fill(liquidToFillTank, true);
            transferItemToInventory(emptyContainers, origin, true);
        }
        return leftOverContainers;
    }

    public static ItemStack transferFromTank(final ItemStack item, final IInventory origin, final ITankMachine destination, final int tankID, final boolean doAdd) {
        if (item == null) {
            return item;
        }
        final IFluidTank tank = destination.getTanks()[tankID];
        final FluidStack liquidInTank = tank.getFluid();
        if (liquidInTank == null) {
            return item;
        }
        FluidContainerRegistry.FluidContainerData containerLiquidData = null;
        for (final FluidContainerRegistry.FluidContainerData data : FluidContainerRegistry.getRegisteredFluidContainerData()) {
            if (data.emptyContainer.isItemEqual(item) && liquidInTank.isFluidEqual(data.fluid)) {
                containerLiquidData = data;
                break;
            }
        }
        if (containerLiquidData == null) {
            return item;
        }
        final int maximumExtractedLiquid = item.stackSize * containerLiquidData.fluid.amount;
        final FluidStack drainedLiquid = tank.drain(maximumExtractedLiquid, false);
        final int amountInTank = (drainedLiquid == null) ? 0 : drainedLiquid.amount;
        int numberOfContainersToFill = amountInTank / containerLiquidData.fluid.amount;
        if (numberOfContainersToFill > item.stackSize) {
            numberOfContainersToFill = item.stackSize;
        }
        final ItemStack copy;
        ItemStack leftOverContainers = copy = item.copy();
        copy.stackSize -= numberOfContainersToFill;
        if (leftOverContainers.stackSize <= 0) {
            leftOverContainers = null;
        }
        ItemStack filledContainers = containerLiquidData.filledContainer.copy();
        filledContainers.stackSize = 0;
        final ItemStack itemStack = filledContainers;
        itemStack.stackSize += numberOfContainersToFill;
        if (filledContainers.stackSize <= 0) {
            filledContainers = null;
        }
        final ItemStack containersThatCantBeDumped = transferItemToInventory(filledContainers, origin, false);
        if (containersThatCantBeDumped != null) {
            return item;
        }
        if (doAdd) {
            tank.drain(maximumExtractedLiquid, true);
            transferItemToInventory(filledContainers, origin, true);
        }
        return leftOverContainers;
    }
}
