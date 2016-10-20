// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.machines.power;

import net.minecraft.nbt.NBTTagCompound;
import forestry.api.core.INBTTagable;

public class PowerInfo implements INBTTagable
{
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
	public void writeToNBT(final NBTTagCompound nbttagcompound) {
		nbttagcompound.setInteger("current", this.getStoredEnergy());
		nbttagcompound.setInteger("max", this.getMaxEnergy());
	}
}
