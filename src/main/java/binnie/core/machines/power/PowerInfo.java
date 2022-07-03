package binnie.core.machines.power;

import forestry.api.core.INBTTagable;
import net.minecraft.nbt.NBTTagCompound;

public class PowerInfo implements INBTTagable {
    private float currentEnergy;
    private float maxEnergy;

    public PowerInfo(IPoweredMachine machine, float currentInput) {
        currentEnergy = (float) machine.getInterface().getEnergy(PowerSystem.RF);
        maxEnergy = (float) machine.getInterface().getCapacity(PowerSystem.RF);
    }

    public PowerInfo() {
        currentEnergy = 0.0f;
        maxEnergy = 0.0f;
    }

    public int getStoredEnergy() {
        return (int) currentEnergy;
    }

    public int getMaxEnergy() {
        return (int) maxEnergy;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        currentEnergy = nbttagcompound.getInteger("current");
        maxEnergy = nbttagcompound.getInteger("max");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        nbttagcompound.setInteger("current", getStoredEnergy());
        nbttagcompound.setInteger("max", getMaxEnergy());
    }
}
