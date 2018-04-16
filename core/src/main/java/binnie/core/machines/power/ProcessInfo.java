package binnie.core.machines.power;

import net.minecraft.nbt.NBTTagCompound;

import forestry.api.core.INbtReadable;
import forestry.api.core.INbtWritable;

public class ProcessInfo implements INbtReadable, INbtWritable {
	private float currentProgress;
	private int processEnergy;
	private int processTime;
	private float energyPerTick;

	public ProcessInfo(final IProcess process) {
		this();
		this.energyPerTick = process.getEnergyPerTick();
		if (process instanceof IProcessTimed) {
			final IProcessTimed time = (IProcessTimed) process;
			this.currentProgress = time.getProgress();
			this.processEnergy = time.getProcessEnergy();
			this.processTime = time.getProcessLength();
		} else {
			this.currentProgress = (process.isInProgress() ? 100.0f : 0.0f);
		}
	}

	public ProcessInfo() {
		this.currentProgress = 0.0f;
		this.processEnergy = 0;
		this.processTime = 0;
		this.energyPerTick = 0.0f;
	}

	public float getCurrentProgress() {
		return this.currentProgress;
	}

	public int getProcessEnergy() {
		return this.processEnergy;
	}

	public int getProcessTime() {
		return this.processTime;
	}

	public float getEnergyPerTick() {
		return this.energyPerTick;
	}

	private static final String NBT_KEY_ENERGY_PER_TICK = "ept";
	private static final String NBT_KEY_PROCESS_ENERGY = "e";
	private static final String NBT_KEY_PROCESS_TIME = "t";
	private static final String NBT_KEY_CURRENT_PROGRESS = "p";

	@Override
	public void readFromNBT(final NBTTagCompound nbttagcompound) {
		this.energyPerTick = nbttagcompound.getFloat(NBT_KEY_ENERGY_PER_TICK);
		this.processEnergy = nbttagcompound.getInteger(NBT_KEY_PROCESS_ENERGY);
		this.processTime = nbttagcompound.getInteger(NBT_KEY_PROCESS_TIME);
		this.currentProgress = nbttagcompound.getFloat(NBT_KEY_CURRENT_PROGRESS);
	}

	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound nbttagcompound) {
		nbttagcompound.setFloat(NBT_KEY_ENERGY_PER_TICK, this.energyPerTick);
		nbttagcompound.setInteger(NBT_KEY_PROCESS_ENERGY, this.processEnergy);
		nbttagcompound.setInteger(NBT_KEY_PROCESS_TIME, this.processTime);
		nbttagcompound.setFloat(NBT_KEY_CURRENT_PROGRESS, this.currentProgress);
		return nbttagcompound;
	}
}
