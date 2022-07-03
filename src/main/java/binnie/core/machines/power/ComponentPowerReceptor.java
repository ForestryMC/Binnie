package binnie.core.machines.power;

import binnie.core.Mods;
import binnie.core.machines.IMachine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.component.IBuildcraft;
import binnie.core.machines.component.IInteraction;
import binnie.core.triggers.TriggerData;
import binnie.core.triggers.TriggerPower;
import cpw.mods.fml.common.Optional;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergyTile;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

@Optional.Interface(iface = "binnie.core.machines.component.IBuildcraft.TriggerProvider", modid = "BuildCraft|Silicon")
public class ComponentPowerReceptor extends MachineComponent
        implements IPoweredMachine, IBuildcraft.TriggerProvider, IInteraction.ChunkUnload, IInteraction.Invalidation {
    public float previousPower;
    public LinkedList<Float> inputs;

    private boolean registeredToIC2EnergyNet;
    private PowerInterface container;

    public ComponentPowerReceptor(IMachine machine) {
        this(machine, 1000);
    }

    public ComponentPowerReceptor(IMachine machine, int storage) {
        super(machine);
        registeredToIC2EnergyNet = false;
        previousPower = 0.0f;
        inputs = new LinkedList<>();
        container = new PowerInterface(storage);
        if (!registeredToIC2EnergyNet) {
            addToEnergyNet();
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        container.readFromNBT(nbttagcompound);
        if (!registeredToIC2EnergyNet) {
            addToEnergyNet();
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        container.writeToNBT(nbttagcompound);
    }

    @Override
    public void onUpdate() {
        if (!registeredToIC2EnergyNet && !getMachine().getTileEntity().isInvalid()) {
            addToEnergyNet();
        }
    }

    @Override
    public PowerInfo getPowerInfo() {
        return new PowerInfo(this, 0.0f);
    }

    @Optional.Method(modid = "BuildCraft|Silicon")
    @Override
    public void getTriggers(List<TriggerData> triggers) {
        triggers.add(TriggerPower.powerNone(this));
        triggers.add(TriggerPower.powerLow(this));
        triggers.add(TriggerPower.powerMedium(this));
        triggers.add(TriggerPower.powerHigh(this));
        triggers.add(TriggerPower.powerFull(this));
    }

    @Override
    @Optional.Method(modid = "IC2")
    public double getDemandedEnergy() {
        return container.getEnergySpace(PowerSystem.EU);
    }

    @Override
    @Optional.Method(modid = "IC2")
    public int getSinkTier() {
        return 1;
    }

    @Override
    @Optional.Method(modid = "IC2")
    public double injectEnergy(ForgeDirection directionFrom, double amount, double voltage) {
        container.addEnergy(PowerSystem.EU, amount, true);
        return 0.0;
    }

    @Override
    @Optional.Method(modid = "IC2")
    public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
        return acceptsPowerSystem(PowerSystem.EU);
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
        return (int) container.addEnergy(PowerSystem.RF, maxReceive, !simulate);
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
        return container.useEnergy(maxExtract, simulate);
    }

    @Override
    public int getEnergyStored(ForgeDirection from) {
        return (int) container.getEnergy(PowerSystem.RF);
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from) {
        return (int) container.getCapacity(PowerSystem.RF);
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
        return acceptsPowerSystem(PowerSystem.RF);
    }

    @Override
    public PowerInterface getInterface() {
        return container;
    }

    private boolean acceptsPowerSystem(PowerSystem system) {
        return true;
    }

    @Override
    public void onInvalidation() {
        removeFromEnergyNet();
    }

    @Override
    public void onChunkUnload() {
        removeFromEnergyNet();
    }

    private void addToEnergyNet() {
        if (getMachine().getWorld() == null) {
            return;
        }
        if (Mods.ic2.active()) {
            do_addToEnergyNet();
        }
    }

    private void removeFromEnergyNet() {
        if (getMachine().getWorld() == null) {
            return;
        }
        if (Mods.ic2.active()) {
            do_removeFromEnergyNet();
        }
    }

    @Optional.Method(modid = "IC2")
    private void do_addToEnergyNet() {
        MinecraftForge.EVENT_BUS.post(
                new EnergyTileLoadEvent((IEnergyTile) getMachine().getTileEntity()));
        registeredToIC2EnergyNet = true;
    }

    @Optional.Method(modid = "IC2")
    private void do_removeFromEnergyNet() {
        MinecraftForge.EVENT_BUS.post(
                new EnergyTileUnloadEvent((IEnergyTile) getMachine().getTileEntity()));
        registeredToIC2EnergyNet = false;
    }
}
