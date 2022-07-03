package binnie.core.machines.power;

import binnie.core.machines.IMachine;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ComponentProcess extends ComponentProcessIndefinate implements IProcessTimed {
    private float progressAmount;

    public ComponentProcess(IMachine machine) {
        super(machine, 0.0f);
        progressAmount = 0.0f;
    }

    @Override
    public float getEnergyPerTick() {
        return getProcessEnergy() / getProcessLength();
    }

    @Override
    public float getProgressPerTick() {
        return 100.0f / getProcessLength();
    }

    @Override
    protected void onStartTask() {
        progressAmount += 0.01f;
    }

    @Override
    protected void onCancelTask() {
        progressAmount = 0.0f;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (progressAmount >= 100.0f) {
            onFinishTask();
            progressAmount = 0.0f;
        }
    }

    public void alterProgress(float value) {
        progressAmount += value;
    }

    public void setProgress(float value) {
        progressAmount = value;
    }

    @Override
    protected void progressTick() {
        super.progressTick();
        alterProgress(getProgressPerTick());
    }

    @Override
    public boolean inProgress() {
        return progressAmount > 0.0f;
    }

    @Override
    public float getProgress() {
        return progressAmount;
    }

    protected void onFinishTask() {
        // ignored
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        progressAmount = nbt.getFloat("progress");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setFloat("progress", progressAmount);
    }

    @Override
    public abstract int getProcessLength();

    @Override
    public abstract int getProcessEnergy();
}
