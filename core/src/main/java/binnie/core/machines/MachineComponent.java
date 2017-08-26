package binnie.core.machines;

import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;

import forestry.api.core.INbtReadable;
import forestry.api.core.INbtWritable;

import binnie.core.Binnie;

public class MachineComponent implements INbtReadable, INbtWritable {
	private IMachine machine;

	public MachineComponent(final IMachine machine) {
		this.machine = machine;
		machine.addComponent(this);
	}

	public IMachine getMachine() {
		return this.machine;
	}

	public void setMachine(final IMachine machine) {
		this.machine = machine;
	}

	@Override
	public void readFromNBT(final NBTTagCompound nbttagcompound) {
	}

	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound nbttagcompound) {
		return nbttagcompound;
	}

	public void onUpdate() {
	}

	public Class<?>[] getComponentInterfaces() {
		Class<?>[] componentInterfaces = Binnie.MACHINE.getComponentInterfaces(this.getClass());
		if (componentInterfaces == null) {
			return new Class[0];
		}
		return componentInterfaces;
	}

	public void onInventoryUpdate() {
	}

	public MachineUtil getUtil() {
		return this.getMachine().getMachineUtil();
	}

	public void onDestruction() {
	}

	public IInventory getInventory() {
		return this.getMachine().getInterface(IInventory.class);
	}
}
