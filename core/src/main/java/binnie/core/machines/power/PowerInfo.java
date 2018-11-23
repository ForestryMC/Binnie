package binnie.core.machines.power;

import net.minecraft.nbt.NBTTagCompound;

import forestry.api.core.INbtReadable;
import forestry.api.core.INbtWritable;

public class PowerInfo implements INbtReadable, INbtWritable {
	private float currentEnergy;
	private float maxEnergy;

	public PowerInfo(IPoweredMachine machine, float currentInput) {
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
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		this.currentEnergy = nbttagcompound.getInteger("current");
		this.maxEnergy = nbttagcompound.getInteger("max");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound) {
		nbttagcompound.setInteger("current", this.getStoredEnergy());
		nbttagcompound.setInteger("max", this.getMaxEnergy());
		return nbttagcompound;
	}
}
