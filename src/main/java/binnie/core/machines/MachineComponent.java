package binnie.core.machines;

import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;

import forestry.api.core.INbtReadable;
import forestry.api.core.INbtWritable;

import binnie.Binnie;
import binnie.core.network.packet.MachinePayload;

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
		return Binnie.MACHINE.getComponentInterfaces(this.getClass());
	}

	public void onInventoryUpdate() {
	}

	public final MachinePayload getPayload() {
		return new MachinePayload(Binnie.MACHINE.getNetworkID(this.getClass()));
	}

	public void recieveData(final MachinePayload payload) {
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
