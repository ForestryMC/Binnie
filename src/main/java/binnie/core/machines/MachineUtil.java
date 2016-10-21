package binnie.core.machines;

import binnie.core.BinnieCore;
import binnie.core.machines.inventory.IChargedSlots;
import binnie.core.machines.power.IPoweredMachine;
import binnie.core.machines.power.IProcess;
import binnie.core.machines.power.ITankMachine;
import binnie.core.machines.power.PowerSystem;
import binnie.core.util.ItemStackSet;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MachineUtil {
    private IMachine machine;

    public MachineUtil(final IMachine machine) {
        this.machine = machine;
    }

    public IInventory getInventory() {
        return this.machine.getInterface(IInventory.class);
    }

    public ITankMachine getTankContainer() {
        return this.machine.getInterface(ITankMachine.class);
    }

    public IPoweredMachine getPoweredMachine() {
        return this.machine.getInterface(IPoweredMachine.class);
    }

    public boolean isSlotEmpty(final int slot) {
        return this.getInventory().getStackInSlot(slot) == null;
    }

    public IFluidTank getTank(final int id) {
        return this.getTankContainer().getTanks()[id];
    }

    public boolean spaceInTank(final int id, final int amount) {
        final IFluidTank tank = this.getTank(id);
        final int space = tank.getCapacity() - tank.getFluidAmount();
        return amount <= space;
    }

    public ItemStack getStack(final int slot) {
        return this.getInventory().getStackInSlot(slot);
    }

    public void deleteStack(final int slot) {
        this.setStack(slot, null);
    }

    public ItemStack decreaseStack(final int slotWood, final int amount) {
        return this.getInventory().decrStackSize(slotWood, amount);
    }

    public void setStack(final int slot, final ItemStack stack) {
        this.getInventory().setInventorySlotContents(slot, stack);
    }

    public void fillTank(final int id, final FluidStack liquidStack) {
        final IFluidTank tank = this.getTank(id);
        tank.fill(liquidStack, true);
    }

    public void addStack(final int slot, final ItemStack addition) {
        if (this.isSlotEmpty(slot)) {
            this.setStack(slot, addition);
        } else {
            final ItemStack merge = this.getStack(slot);
            if (merge.isItemEqual(addition) && merge.stackSize + addition.stackSize <= merge.getMaxStackSize()) {
                final ItemStack itemStack = merge;
                itemStack.stackSize += addition.stackSize;
                this.setStack(slot, merge);
            }
        }
    }

    public FluidStack drainTank(final int tank, final int amount) {
        return this.getTank(tank).drain(amount, true);
    }

    public boolean liquidInTank(final int tankIndex, final int amount) {
        IFluidTank tank = this.getTank(tankIndex);
        FluidStack drain = tank.drain(amount, false);
        return drain != null && drain.amount == amount;
    }

    public void damageItem(final int slot, final int damage) {
        final ItemStack item = this.getStack(slot);
        if (damage < 0) {
            item.setItemDamage(Math.max(0, item.getItemDamage() + damage));
        } else if (item.attemptDamageItem(damage, new Random())) {
            this.setStack(slot, null);
        }
        this.setStack(slot, item);
    }

    public boolean isTankEmpty(final int tankInput) {
        return this.getTank(tankInput).getFluidAmount() == 0;
    }

    public FluidStack getFluid(final int tankInput) {
        return (this.getTank(tankInput).getFluid() == null) ? null : this.getTank(tankInput).getFluid();
    }

    public ItemStack[] getStacks(final int[] slotGrains) {
        final ItemStack[] stacks = new ItemStack[slotGrains.length];
        for (int i = 0; i < slotGrains.length; ++i) {
            stacks[i] = this.getStack(slotGrains[i]);
        }
        return stacks;
    }

    public ItemStack hasIngredients(final int recipe, final int[] inventory) {
        return null;
    }

    public boolean hasIngredients(final int[] recipe, final int[] inventory) {
        final ItemStackSet requiredStacks = new ItemStackSet();
        Collections.addAll(requiredStacks, this.getStacks(recipe));
        final ItemStackSet inventoryStacks = new ItemStackSet();
        Collections.addAll(inventoryStacks, this.getStacks(inventory));
        requiredStacks.removeAll(inventoryStacks);
        return requiredStacks.isEmpty();
    }

    public void useEnergyMJ(final float powerUsage) {
        this.getPoweredMachine().getInterface().useEnergy(PowerSystem.MJ, powerUsage, true);
    }

    public boolean hasEnergyMJ(final float powerUsage) {
        return this.getPoweredMachine().getInterface().useEnergy(PowerSystem.MJ, powerUsage, false) >= powerUsage;
    }

    public float getSlotCharge(final int slot) {
        return this.machine.getInterface(IChargedSlots.class).getCharge(slot);
    }

    public void useCharge(final int slot, final float loss) {
        this.machine.getInterface(IChargedSlots.class).alterCharge(slot, -loss);
    }

    public Random getRandom() {
        return new Random();
    }

    public void refreshBlock() {
        //TODO renderupdate
        //this.machine.getWorld().markBlockForUpdate(this.machine.getTileEntity().xCoord, this.machine.getTileEntity().yCoord, this.machine.getTileEntity().zCoord);
    }

    public IProcess getProcess() {
        return this.machine.getInterface(IProcess.class);
    }

    public List<ItemStack> getNonNullStacks(final int[] slotacclimatiser) {
        final List<ItemStack> stacks = new ArrayList<>();
        for (final ItemStack stack : this.getStacks(slotacclimatiser)) {
            if (stack != null) {
                stacks.add(stack);
            }
        }
        return stacks;
    }

    public boolean isServer() {
        return BinnieCore.proxy.isSimulating(this.machine.getWorld());
    }
}
