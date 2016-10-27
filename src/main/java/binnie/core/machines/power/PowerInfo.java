package binnie.core.machines.power;

import forestry.api.core.INbtReadable;
import forestry.api.core.INbtWritable;
import net.minecraft.nbt.NBTTagCompound;

public class PowerInfo implements INbtReadable, INbtWritable {
	private float currentEnergy;
	private float maxEnergy;

	public PowerInfo(final IPoweredMachine machine, final float currentInput) {
		this.currentEnergy = 0.0f;
		this.maxEnergy = 0.0f;
		this.currentEnergy = (float) machine.getInterface().getEnergy(PowerSystem.RF);
		this.maxEnergy = (float) machine.getInterface().getCapacity(PowerSystem.RF);
	}

	public PowerInfo() {
		this.currentEnergy = 0.0f;
		this.maxEnergy = 0.0f;
	}

	public int getStoredEnergy() {
		return (int) this.currentEnergy;
	}

	public int getMaxEnergy() {
		return (int) this.maxEnergy;
	}

	@Override
	public void readFromNBT(final NBTTagCompound nbttagcompound) {
		this.currentEnergy = nbttagcompound.getInteger("current");
		this.maxEnergy = nbttagcompound.getInteger("max");
	}

	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound nbttagcompound) {
		nbttagcompound.setInteger("current", this.getStoredEnergy());
		nbttagcompound.setInteger("max", this.getMaxEnergy());
		return nbttagcompound;
	}
}
