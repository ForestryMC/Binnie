package binnie.core.machines;

import binnie.core.BinnieCore;
import binnie.core.machines.inventory.IChargedSlots;
import binnie.core.machines.power.IPoweredMachine;
import binnie.core.machines.power.IProcess;
import binnie.core.machines.power.ITankMachine;
import binnie.core.machines.power.PowerSystem;
import binnie.core.util.ItemStackSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

public class MachineUtil {
    private IMachine machine;

    public MachineUtil(IMachine machine) {
        this.machine = machine;
    }

    public IInventory getInventory() {
        return machine.getInterface(IInventory.class);
    }

    public ITankMachine getTankContainer() {
        return machine.getInterface(ITankMachine.class);
    }

    public IPoweredMachine getPoweredMachine() {
        return machine.getInterface(IPoweredMachine.class);
    }

    public boolean isSlotEmpty(int slot) {
        return getInventory().getStackInSlot(slot) == null;
    }

    public IFluidTank getTank(int id) {
        return getTankContainer().getTanks()[id];
    }

    public boolean spaceInTank(int id, int amount) {
        IFluidTank tank = getTank(id);
        int space = tank.getCapacity() - tank.getFluidAmount();
        return amount <= space;
    }

    public ItemStack getStack(int slot) {
        return getInventory().getStackInSlot(slot);
    }

    public void deleteStack(int slot) {
        setStack(slot, null);
    }

    public ItemStack decreaseStack(int slot, int amount) {
        return getInventory().decrStackSize(slot, amount);
    }

    public void setStack(int slot, ItemStack stack) {
        getInventory().setInventorySlotContents(slot, stack);
    }

    public void fillTank(int id, FluidStack liquidStack) {
        IFluidTank tank = getTank(id);
        tank.fill(liquidStack, true);
    }

    public void addStack(int slot, ItemStack addition) {
        if (isSlotEmpty(slot)) {
            setStack(slot, addition);
        } else {
            ItemStack merge = getStack(slot);
            if (merge.isItemEqual(addition) && merge.stackSize + addition.stackSize <= merge.getMaxStackSize()) {
                ItemStack itemStack = merge;
                itemStack.stackSize += addition.stackSize;
                setStack(slot, merge);
            }
        }
    }

    public FluidStack drainTank(int tank, int amount) {
        return getTank(tank).drain(amount, true);
    }

    public boolean liquidInTank(int tank, int amount) {
        FluidStack fluidStack = getTank(tank).drain(amount, false);
        return fluidStack != null && fluidStack.amount == amount;
    }

    public void damageItem(int slot, int damage) {
        ItemStack item = getStack(slot);
        if (damage < 0) {
            item.setItemDamage(Math.max(0, item.getItemDamage() + damage));
        } else if (item.attemptDamageItem(damage, new Random())) {
            setStack(slot, null);
        }
        setStack(slot, item);
    }

    public boolean isTankEmpty(int tankInput) {
        return getTank(tankInput).getFluidAmount() == 0;
    }

    public FluidStack getFluid(int tankInput) {
        return getTank(tankInput).getFluid();
    }

    public ItemStack[] getStacks(int[] slotGrains) {
        ItemStack[] stacks = new ItemStack[slotGrains.length];
        for (int i = 0; i < slotGrains.length; ++i) {
            stacks[i] = getStack(slotGrains[i]);
        }
        return stacks;
    }

    public ItemStack hasIngredients(int recipe, int[] inventory) {
        return null;
    }

    public boolean hasIngredients(int[] recipe, int[] inventory) {
        ItemStackSet requiredStacks = new ItemStackSet();
        Collections.addAll(requiredStacks, getStacks(recipe));

        ItemStackSet inventoryStacks = new ItemStackSet();
        Collections.addAll(inventoryStacks, getStacks(inventory));

        requiredStacks.removeAll(inventoryStacks);
        return requiredStacks.isEmpty();
    }

    public void useEnergyMJ(float powerUsage) {
        getPoweredMachine().getInterface().useEnergy(PowerSystem.MJ, powerUsage, true);
    }

    public boolean hasEnergyMJ(float powerUsage) {
        return getPoweredMachine().getInterface().useEnergy(PowerSystem.MJ, powerUsage, false) >= powerUsage;
    }

    public float getSlotCharge(int slot) {
        return machine.getInterface(IChargedSlots.class).getCharge(slot);
    }

    public void useCharge(int slot, float loss) {
        machine.getInterface(IChargedSlots.class).alterCharge(slot, -loss);
    }

    public Random getRandom() {
        return new Random();
    }

    public void refreshBlock() {
        TileEntity tileEntity = machine.getTileEntity();
        machine.getWorld().markBlockForUpdate(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
    }

    public IProcess getProcess() {
        return machine.getInterface(IProcess.class);
    }

    public List<ItemStack> getNonNullStacks(int[] slot) {
        List<ItemStack> stacks = new ArrayList<>();
        for (ItemStack stack : getStacks(slot)) {
            if (stack != null) {
                stacks.add(stack);
            }
        }
        return stacks;
    }

    public boolean isServer() {
        return BinnieCore.proxy.isSimulating(machine.getWorld());
    }
}
