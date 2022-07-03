package binnie.core.machines.power;

import forestry.api.core.INBTTagable;
import net.minecraft.nbt.NBTTagCompound;

public class ProcessInfo implements INBTTagable {
    private float currentProgress;
    private int processEnergy;
    private int processTime;
    private float energyPerTick;

    public ProcessInfo(IProcess process) {
        currentProgress = 0.0f;
        processEnergy = 0;
        processTime = 0;
        energyPerTick = process.getEnergyPerTick();

        if (process instanceof IProcessTimed) {
            IProcessTimed time = (IProcessTimed) process;
            currentProgress = time.getProgress();
            processEnergy = time.getProcessEnergy();
            processTime = time.getProcessLength();
        } else {
            currentProgress = process.isInProgress() ? 100.0f : 0.0f;
        }
    }

    public ProcessInfo() {
        currentProgress = 0.0f;
        processEnergy = 0;
        processTime = 0;
        energyPerTick = 0.0f;
    }

    public float getCurrentProgress() {
        return currentProgress;
    }

    public int getProcessEnergy() {
        return processEnergy;
    }

    public int getProcessTime() {
        return processTime;
    }

    public float getEnergyPerTick() {
        return energyPerTick;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        energyPerTick = nbttagcompound.getFloat("ept");
        processEnergy = nbttagcompound.getInteger("e");
        processTime = nbttagcompound.getInteger("t");
        currentProgress = nbttagcompound.getFloat("p");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        nbttagcompound.setFloat("ept", energyPerTick);
        nbttagcompound.setFloat("p", currentProgress);
        nbttagcompound.setInteger("e", processEnergy);
        nbttagcompound.setInteger("t", processTime);
    }
}
