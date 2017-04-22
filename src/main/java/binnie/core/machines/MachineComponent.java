package binnie.core.machines;

import binnie.*;
import binnie.core.network.packet.*;
import forestry.api.core.*;
import net.minecraft.inventory.*;
import net.minecraft.nbt.*;

public class MachineComponent implements INBTTagable {
	private IMachine machine;

	public MachineComponent(IMachine machine) {
		setMachine(machine);
		machine.addComponent(this);
	}

	public void setMachine(IMachine machine) {
		this.machine = machine;
	}

	public IMachine getMachine() {
		return machine;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
	}

	public void onUpdate() {
	}

	public Class[] getComponentInterfaces() {
		return Binnie.Machine.getComponentInterfaces(getClass());
	}

	public void onInventoryUpdate() {
	}

	public MachinePayload getPayload() {
		return new MachinePayload(Binnie.Machine.getNetworkID(getClass()));
	}

	public void recieveData(MachinePayload payload) {
	}

	public MachineUtil getUtil() {
		return getMachine().getMachineUtil();
	}

	public void onDestruction() {
		// desctruction
	}

	public IInventory getInventory() {
		return this.getMachine().getInterface(IInventory.class);
	}
}
