package binnie.core.machines.power;

import net.minecraft.nbt.NBTTagCompound;

import forestry.api.core.INbtReadable;
import forestry.api.core.INbtWritable;

public class PowerInterface implements INbtReadable, INbtWritable {
	private final int capacity;
	private int energy;

	public PowerInterface(int capacity) {
		this.capacity = capacity * 100;
		this.energy = 0;
	}

	public int getCapacity() {
		return this.capacity;
	}

	public int getEnergy() {
		return this.energy;
	}

	public int addEnergy(int amount, boolean shouldDo) {
		int added = Math.min(this.getEnergySpace(), amount);
		if (shouldDo) {
			this.energy += added;
		}
		return added;
	}

	public int useEnergy(int amount, boolean simulate) {
		int added = Math.min(this.getEnergy(), amount);
		if (simulate) {
			this.energy -= added;
		}
		return added;
	}

	public int getEnergySpace() {
		return this.getCapacity() - this.getEnergy();
	}

	public double addEnergy(PowerSystem unit, double amount, boolean simulate) {
		return unit.convertTo(this.addEnergy(unit.convertFrom(amount), simulate));
	}

	public double useEnergy(PowerSystem unit, double amount, boolean simulate) {
		return unit.convertTo(this.useEnergy(unit.convertFrom(amount), simulate));
	}

	public double getEnergy(PowerSystem unit) {
		return unit.convertTo(this.getEnergy());
	}

	public double getCapacity(PowerSystem unit) {
		return unit.convertTo(this.getCapacity());
	}

	public double getEnergySpace(PowerSystem unit) {
		return unit.convertTo(this.getEnergySpace());
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		this.energy = nbt.getInteger("Energy");
		if (this.energy > this.capacity) {
			this.energy = this.capacity;
		} else if (this.energy < 0) {
			this.energy = 0;
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("Energy", this.getEnergy());
		return nbt;
	}
}
