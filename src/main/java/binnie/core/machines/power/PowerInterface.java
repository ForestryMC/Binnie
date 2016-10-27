package binnie.core.machines.power;

import forestry.api.core.INbtReadable;
import forestry.api.core.INbtWritable;
import net.minecraft.nbt.NBTTagCompound;

public class PowerInterface implements INbtReadable, INbtWritable {
	private int capacity;
	private int energy;

	public PowerInterface(final int capacity) {
		this.capacity = capacity * 100;
		this.energy = 0;
	}

	public int getCapacity() {
		return this.capacity;
	}

	public int getEnergy() {
		return this.energy;
	}

	public int addEnergy(final int amount, final boolean shouldDo) {
		final int added = Math.min(this.getEnergySpace(), amount);
		if (shouldDo) {
			this.energy += added;
		}
		return added;
	}

	public int useEnergy(final int amount, final boolean simulate) {
		final int added = Math.min(this.getEnergy(), amount);
		if (simulate) {
			this.energy -= added;
		}
		return added;
	}

	public int getEnergySpace() {
		return this.getCapacity() - this.getEnergy();
	}

	public double addEnergy(final PowerSystem unit, final double amount, final boolean simulate) {
		return unit.convertTo(this.addEnergy(unit.convertFrom(amount), simulate));
	}

	public double useEnergy(final PowerSystem unit, final double amount, final boolean simulate) {
		return unit.convertTo(this.useEnergy(unit.convertFrom(amount), simulate));
	}

	public double getEnergy(final PowerSystem unit) {
		return unit.convertTo(this.getEnergy());
	}

	public double getCapacity(final PowerSystem unit) {
		return unit.convertTo(this.getCapacity());
	}

	public double getEnergySpace(final PowerSystem unit) {
		return unit.convertTo(this.getEnergySpace());
	}

	@Override
	public void readFromNBT(final NBTTagCompound nbt) {
		this.energy = nbt.getInteger("Energy");
		if (this.energy > this.capacity) {
			this.energy = this.capacity;
		} else if (this.energy < 0) {
			this.energy = 0;
		}
	}

	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound nbt) {
		nbt.setInteger("Energy", this.getEnergy());
		return nbt;
	}
}
