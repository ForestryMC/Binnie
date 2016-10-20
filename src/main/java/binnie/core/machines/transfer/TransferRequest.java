package binnie.core.machines.transfer;

import binnie.core.machines.Machine;
import binnie.core.machines.inventory.IInventorySlots;
import binnie.core.machines.inventory.IValidatedTankContainer;
import binnie.core.machines.power.ITankMachine;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import net.minecraftforge.fluids.IFluidTank;

import java.util.ArrayList;
import java.util.List;

public class TransferRequest {
    private ItemStack itemToTransfer;
    private ItemStack returnItem;
    private IInventory origin;
    private IInventory destination;
    private int[] targetSlots;
    private int[] targetTanks;
    private boolean transferLiquids;
    private boolean ignoreReadOnly;
    private List<TransferSlot> insertedSlots;
    private List<Integer> insertedTanks;

    public TransferRequest(final ItemStack toTransfer, final IInventory destination) {
        this.itemToTransfer = null;
        this.returnItem = null;
        this.targetSlots = new int[0];
        this.targetTanks = new int[0];
        this.transferLiquids = true;
        this.ignoreReadOnly = false;
        this.insertedSlots = new ArrayList<TransferSlot>();
        this.insertedTanks = new ArrayList<Integer>();
        final int[] target = new int[destination.getSizeInventory()];
        for (int i = 0; i < target.length; ++i) {
            target[i] = i;
        }
        int[] targetTanks = new int[0];
        if (destination instanceof ITankMachine) {
            targetTanks = new int[((ITankMachine) destination).getTanks().length];
            for (int j = 0; j < targetTanks.length; ++j) {
                targetTanks[j] = j;
            }
        }
        if (toTransfer != null) {
            this.setItemToTransfer(toTransfer.copy());
            this.setReturnItem(toTransfer.copy());
        }
        this.setOrigin(null);
        this.setDestination(destination);
        this.setTargetSlots(target);
        this.setTargetTanks(targetTanks);
        this.transferLiquids = true;
    }

    private void setItemToTransfer(final ItemStack itemToTransfer) {
        this.itemToTransfer = itemToTransfer;
    }

    private void setReturnItem(final ItemStack returnItem) {
        this.returnItem = returnItem;
    }

    public TransferRequest setOrigin(final IInventory origin) {
        this.origin = origin;
        return this;
    }

    private void setDestination(final IInventory destination) {
        this.destination = destination;
    }

    public TransferRequest setTargetSlots(final int[] targetSlots) {
        this.targetSlots = targetSlots;
        return this;
    }

    public TransferRequest setTargetTanks(final int[] targetTanks) {
        this.targetTanks = targetTanks;
        return this;
    }

    public TransferRequest ignoreValidation() {
        this.ignoreReadOnly = true;
        return this;
    }

    public ItemStack getReturnItem() {
        return this.returnItem;
    }

    public ItemStack transfer(final boolean doAdd) {
        ItemStack item = this.returnItem;
        if (item == null || this.destination == null) {
            return null;
        }
        if (this.transferLiquids && this.destination instanceof ITankMachine) {
            for (final int tankID : this.targetTanks) {
                item = this.transferToTank(item, this.origin, (ITankMachine) this.destination, tankID, doAdd);
                if (item != null) {
                    item = this.transferFromTank(item, this.origin, (ITankMachine) this.destination, tankID, doAdd);
                }
            }
        }
        if (item != null) {
            for (final int slot : this.targetSlots) {
                if (this.destination.isItemValidForSlot(slot, item) || this.ignoreReadOnly) {
                    if (!(this.destination instanceof IInventorySlots) || ((IInventorySlots) this.destination).getSlot(slot) == null || !((IInventorySlots) this.destination).getSlot(slot).isRecipe()) {
                        if (this.destination.getStackInSlot(slot) != null) {
                            if (item.isStackable()) {
                                final ItemStack merged = this.destination.getStackInSlot(slot).copy();
                                final ItemStack[] newStacks = mergeStacks(item.copy(), merged.copy());
                                item = newStacks[0];
                                if (!areItemsEqual(merged, newStacks[1])) {
                                    this.insertedSlots.add(new TransferSlot(slot, this.destination));
                                }
                                if (doAdd) {
                                    this.destination.setInventorySlotContents(slot, newStacks[1]);
                                }
                                if (item == null) {
                                    return null;
                                }
                            }
                        }
                    }
                }
            }
        }
        if (item != null) {
            for (final int slot : this.targetSlots) {
                if (this.destination.isItemValidForSlot(slot, item) || this.ignoreReadOnly) {
                    if (!(this.destination instanceof IInventorySlots) || ((IInventorySlots) this.destination).getSlot(slot) == null || !((IInventorySlots) this.destination).getSlot(slot).isRecipe()) {
                        if (this.destination.getStackInSlot(slot) == null && item != null) {
                            this.insertedSlots.add(new TransferSlot(slot, this.destination));
                            if (doAdd) {
                                this.destination.setInventorySlotContents(slot, item.copy());
                            }
                            return null;
                        }
                    }
                }
            }
        }
        this.setReturnItem(item);
        return this.getReturnItem();
    }

    private static boolean areItemsEqual(final ItemStack merged, final ItemStack itemstack) {
        return ItemStack.areItemStackTagsEqual(itemstack, merged) && itemstack.isItemEqual(merged);
    }

    public static ItemStack[] mergeStacks(ItemStack itemstack, final ItemStack merged) {
        if (areItemsEqual(itemstack, merged)) {
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

    private ItemStack transferToTank(ItemStack item, final IInventory origin, final ITankMachine destination, final int tankID, final boolean doAdd) {
        item = this.transferToTankUsingContainerData(item, origin, destination, tankID, doAdd);
        item = this.transferToTankUsingFluidContainer(item, origin, destination, tankID, doAdd);
        return item;
    }

    private ItemStack transferToTankUsingFluidContainer(final ItemStack item, final IInventory origin, final ITankMachine destination, final int tankID, final boolean doAdd) {
        if (item == null || !(item.getItem() instanceof IFluidContainerItem)) {
            return item;
        }
        final IFluidContainerItem fluidContainer = (IFluidContainerItem) item.getItem();
        final FluidStack fluid = fluidContainer.getFluid(item);
        if (fluid == null) {
            return item;
        }
        final IFluidTank tank = destination.getTanks()[tankID];
        final IValidatedTankContainer validated = Machine.getInterface(IValidatedTankContainer.class, destination);
        if (validated != null && (!validated.isLiquidValidForTank(fluid, tankID) || validated.isTankReadOnly(tankID))) {
            return item;
        }
        final int maxFill = tank.fill(fluid, false);
        final FluidStack toTake = fluidContainer.drain(item, maxFill, true);
        if (doAdd) {
            tank.fill(toTake, true);
        }
        return item;
    }

    private ItemStack transferToTankUsingContainerData(final ItemStack item, final IInventory origin, final ITankMachine destination, final int tankID, final boolean doAdd) {
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
        if (validated != null && (!validated.isLiquidValidForTank(containerLiquid, tankID) || validated.isTankReadOnly(tankID))) {
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
        final TransferRequest containersDump = new TransferRequest(emptyContainers, origin);
        final ItemStack containersThatCantBeDumped = containersDump.transfer(false);
        if (containersThatCantBeDumped != null) {
            return item;
        }
        if (doAdd) {
            final FluidStack copy2;
            final FluidStack liquidToFillTank = copy2 = containerLiquid.copy();
            copy2.amount *= numberOfContainersToAdd;
            tank.fill(liquidToFillTank, true);
            containersDump.transfer(true);
        }
        return leftOverContainers;
    }

    private ItemStack transferFromTank(ItemStack item, final IInventory origin, final ITankMachine destination, final int tankID, final boolean doAdd) {
        item = this.transferFromTankUsingContainerData(item, origin, destination, tankID, doAdd);
        item = this.transferFromTankUsingFluidContainer(item, origin, destination, tankID, doAdd);
        return item;
    }

    private ItemStack transferFromTankUsingFluidContainer(final ItemStack item, final IInventory origin, final ITankMachine destination, final int tankID, final boolean doAdd) {
        if (item == null || !(item.getItem() instanceof IFluidContainerItem)) {
            return item;
        }
        final IFluidContainerItem fluidContainer = (IFluidContainerItem) item.getItem();
        final IFluidTank tank = destination.getTanks()[tankID];
        final FluidStack fluid = tank.getFluid();
        if (fluid == null) {
            return item;
        }
        int amount = fluidContainer.fill(item, fluid, false);
        amount = Math.min(amount, (tank.drain(amount, false) == null) ? 0 : tank.drain(amount, false).amount);
        if (amount <= 0) {
            return item;
        }
        fluidContainer.fill(item, tank.drain(amount, doAdd), doAdd);
        return item;
    }

    private ItemStack transferFromTankUsingContainerData(final ItemStack item, final IInventory origin, final ITankMachine destination, final int tankID, final boolean doAdd) {
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
        FluidStack fluid = null;
        ItemStack filled = null;
        if (containerLiquidData != null) {
            fluid = containerLiquidData.fluid;
            filled = containerLiquidData.filledContainer;
        }
        if (fluid == null || filled == null) {
            return item;
        }
        final int maximumExtractedLiquid = item.stackSize * fluid.amount;
        final FluidStack drainedLiquid = tank.drain(maximumExtractedLiquid, false);
        final int amountInTank = (drainedLiquid == null) ? 0 : drainedLiquid.amount;
        int numberOfContainersToFill = amountInTank / fluid.amount;
        if (numberOfContainersToFill > item.stackSize) {
            numberOfContainersToFill = item.stackSize;
        }
        final ItemStack copy;
        ItemStack leftOverContainers = copy = item.copy();
        copy.stackSize -= numberOfContainersToFill;
        if (leftOverContainers.stackSize <= 0) {
            leftOverContainers = null;
        }
        ItemStack filledContainers = filled.copy();
        filledContainers.stackSize = 0;
        final ItemStack itemStack = filledContainers;
        itemStack.stackSize += numberOfContainersToFill;
        if (filledContainers.stackSize <= 0) {
            filledContainers = null;
        }
        final TransferRequest containersDump = new TransferRequest(filledContainers, origin);
        final ItemStack containersThatCantBeDumped = containersDump.transfer(false);
        if (containersThatCantBeDumped != null) {
            return item;
        }
        if (doAdd) {
            tank.drain(maximumExtractedLiquid, true);
            containersDump.transfer(true);
        }
        return leftOverContainers;
    }

    public List<TransferSlot> getInsertedSlots() {
        return this.insertedSlots;
    }

    public List<Integer> getInsertedTanks() {
        return this.insertedTanks;
    }

    public IInventory getOrigin() {
        return this.origin;
    }

    public IInventory getDestination() {
        return this.destination;
    }

    public ItemStack getItemToTransfer() {
        return this.itemToTransfer;
    }

    public int[] getTargetSlots() {
        return this.targetSlots;
    }

    public int[] getTargetTanks() {
        return this.targetTanks;
    }

    public static class TransferSlot {
        public int id;
        public IInventory inventory;

        public TransferSlot(final int id, final IInventory inventory) {
            this.id = id;
            this.inventory = inventory;
        }
    }
}
