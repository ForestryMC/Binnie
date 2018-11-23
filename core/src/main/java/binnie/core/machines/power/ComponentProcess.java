package binnie.core.machines.power;

import net.minecraft.nbt.NBTTagCompound;

import binnie.core.machines.IMachine;

public abstract class ComponentProcess extends ComponentProcessIndefinate implements IProcessTimed {
	private float progressAmount;

	public ComponentProcess(IMachine machine) {
		super(machine, 0.0f);
		this.progressAmount = 0.0f;
	}

	@Override
	public float getEnergyPerTick() {
		return this.getProcessEnergy() / this.getProcessLength();
	}

	@Override
	public float getProgressPerTick() {
		return 100.0f / this.getProcessLength();
	}

	@Override
	protected void onStartTask() {
		this.progressAmount += 0.01f;
	}

	@Override
	protected void onCancelTask() {
		this.progressAmount = 0.0f;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (this.progressAmount >= 100.0f) {
			this.onFinishTask();
			this.progressAmount = 0.0f;
		}
	}

	public void alterProgress(float f) {
		this.progressAmount += f;
	}

	@Override
	protected void progressTick() {
		super.progressTick();
		this.alterProgress(this.getProgressPerTick());
	}

	@Override
	public boolean inProgress() {
		return this.progressAmount > 0.0f;
	}

	@Override
	public float getProgress() {
		return this.progressAmount;
	}

	public void setProgress(float f) {
		this.progressAmount = f;
	}

	protected void onFinishTask() {
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.progressAmount = compound.getFloat("progress");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		NBTTagCompound nbt = super.writeToNBT(compound);
		nbt.setFloat("progress", this.progressAmount);
		return nbt;
	}

	@Override
	public abstract int getProcessLength();

	@Override
	public abstract int getProcessEnergy();
}
