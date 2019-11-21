package binnie.core.machines;

import binnie.core.machines.inventory.IChargedSlots;
import binnie.core.machines.power.IPoweredMachine;
import binnie.core.machines.power.IProcess;
import binnie.core.machines.power.ITankMachine;
import binnie.core.machines.power.PowerSystem;
import binnie.core.util.ItemStackSet;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MachineUtil {
	private static final Random DAMAGE_RANDOM = new Random();
	private final IMachine machine;

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
		return this.getInventory().getStackInSlot(slot).isEmpty();
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
		this.setStack(slot, ItemStack.EMPTY);
	}

	public ItemStack decreaseStack(final int slot, final int amount) {
		return this.getInventory().decrStackSize(slot, amount);
	}

	public void setStack(final int slot, final ItemStack stack) {
		this.getInventory().setInventorySlotContents(slot, stack);
	}

	public void fillTank(final int id, final FluidStack liquidStack) {
		final IFluidTank tank = this.getTank(id);
		tank.fill(liquidStack, true);
	}

	public void addStack(final int slot, final ItemStack addition) {
		final ItemStack merge = this.getStack(slot);
		if (merge.isEmpty()) {
			this.setStack(slot, addition);
		} else {
			if (merge.isItemEqual(addition) && merge.getCount() + addition.getCount() <= merge.getMaxStackSize()) {
				merge.grow(addition.getCount());
				this.setStack(slot, merge);
			}
		}
	}

	@Nullable
	public FluidStack drainTank(final int tank, final int amount) {
		return this.getTank(tank).drain(amount, true);
	}

	public boolean liquidInTank(final int tankIndex, final int amount) {
		IFluidTank tank = this.getTank(tankIndex);
		FluidStack drain = tank.drain(amount, false);
		return drain != null && drain.amount == amount;
	}

	public void damageItem(final ItemStack item, final int slot, final int damage) {
		if (damage < 0) {
			item.setItemDamage(Math.max(0, item.getItemDamage() + damage));
		} else {
			if (item.attemptDamageItem(damage, DAMAGE_RANDOM, null)) {
				this.setStack(slot, ItemStack.EMPTY);
			}
		}
		this.setStack(slot, item);
	}

	public boolean isTankEmpty(final int tankInput) {
		return this.getTank(tankInput).getFluidAmount() == 0;
	}

	@Nullable
	public FluidStack getFluid(final int tankInput) {
		return this.getTank(tankInput).getFluid();
	}

	public ItemStack[] getStacks(final int[] slots) {
		return getStacks(slots, false);
	}

	public ItemStack[] getStacks(final int[] slots, boolean copy) {
		final ItemStack[] stacks = new ItemStack[slots.length];
		for (int i = 0; i < slots.length; ++i) {
			ItemStack stack = this.getStack(slots[i]);
			if (copy) {
				stack = stack.copy();
			}
			stacks[i] = stack;
		}
		return stacks;
	}

	public boolean hasIngredients(final int[] recipe, final int[] inventory) {
		final ItemStackSet requiredStacks = new ItemStackSet();
		Collections.addAll(requiredStacks, this.getStacks(recipe));
		final ItemStackSet inventoryStacks = new ItemStackSet();
		Collections.addAll(inventoryStacks, this.getStacks(inventory));
		requiredStacks.removeAll(inventoryStacks);
		return requiredStacks.isEmpty();
	}

	public boolean removeIngredients(final int[] recipe, final int[] inventorySlots) {
		if (!hasIngredients(recipe, inventorySlots)) {
			return false;
		}

		List<ItemStack> requiredStacks = this.getNonEmptyStacks(recipe, true);
		for (ItemStack requiredStack : requiredStacks) {
			IInventory inventory = this.getInventory();
			for (int slot : inventorySlots) {
				ItemStack stackInSlot = this.getStack(slot);
				if (!stackInSlot.isEmpty() && ItemStack.areItemsEqual(requiredStack, stackInSlot) && ItemStack.areItemStackTagsEqual(requiredStack, stackInSlot)) {
					if (requiredStack.getCount() >= stackInSlot.getCount()) {
						requiredStack.shrink(stackInSlot.getCount());
						inventory.removeStackFromSlot(slot);
					} else {
						stackInSlot.shrink(requiredStack.getCount());
						requiredStack.setCount(0);
						break;
					}
				}
			}
		}

		return true;
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
		BlockPos pos = machine.getTileEntity().getPos();
		World world = machine.getWorld();
		IBlockState blockState = world.getBlockState(pos);
		machine.getWorld().notifyBlockUpdate(pos, blockState, blockState, 0);
	}

	public IProcess getProcess() {
		return this.machine.getInterface(IProcess.class);
	}

	public NonNullList<ItemStack> getNonEmptyStacks(final int[] slots) {
		return getNonEmptyStacks(slots, false);
	}

	public NonNullList<ItemStack> getNonEmptyStacks(final int[] slots, boolean copy) {
		final NonNullList<ItemStack> stacks = NonNullList.create();
		for (final ItemStack stack : this.getStacks(slots, copy)) {
			if (!stack.isEmpty()) {
				stacks.add(stack);
			}
		}
		return stacks;
	}

	public boolean isServer() {
		return !this.machine.getWorld().isRemote;
	}

	public World getWorld() {
		return this.machine.getWorld();
	}
}
