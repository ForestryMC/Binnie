// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.machines.power;

import ic2.api.energy.event.EnergyTileUnloadEvent;
import cpw.mods.fml.common.eventhandler.Event;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.tile.IEnergyTile;
import net.minecraftforge.common.MinecraftForge;
import binnie.core.Mods;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import binnie.core.triggers.TriggerPower;
import binnie.core.triggers.TriggerData;
import java.util.List;
import net.minecraft.nbt.NBTTagCompound;
import binnie.core.machines.IMachine;
import java.util.LinkedList;
import cpw.mods.fml.common.Optional;
import binnie.core.machines.component.IInteraction;
import binnie.core.machines.component.IBuildcraft;
import binnie.core.machines.MachineComponent;

@Optional.Interface(iface = "binnie.core.machines.component.IBuildcraft.TriggerProvider", modid = "BuildCraft|Silicon")
public class ComponentPowerReceptor extends MachineComponent implements IPoweredMachine, IBuildcraft.TriggerProvider, IInteraction.ChunkUnload, IInteraction.Invalidation
{
	private boolean registeredToIC2EnergyNet;
	float previousPower;
	LinkedList<Float> inputs;
	static final int inputAverageTicks = 20;
	private PowerInterface container;

	public ComponentPowerReceptor(final IMachine machine) {
		this(machine, 1000);
	}

	public ComponentPowerReceptor(final IMachine machine, final int storage) {
		super(machine);
		this.registeredToIC2EnergyNet = false;
		this.previousPower = 0.0f;
		this.inputs = new LinkedList<Float>();
		this.container = new PowerInterface(storage);
		if (!this.registeredToIC2EnergyNet) {
			this.addToEnergyNet();
		}
	}

	@Override
	public void readFromNBT(final NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		this.container.readFromNBT(nbttagcompound);
		if (!this.registeredToIC2EnergyNet) {
			this.addToEnergyNet();
		}
	}

	@Override
	public void writeToNBT(final NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		this.container.writeToNBT(nbttagcompound);
	}

	@Override
	public void onUpdate() {
		if (!this.registeredToIC2EnergyNet && !this.getMachine().getTileEntity().isInvalid()) {
			this.addToEnergyNet();
		}
	}

	@Override
	public PowerInfo getPowerInfo() {
		return new PowerInfo(this, 0.0f);
	}

	@Optional.Method(modid = "BuildCraft|Silicon")
	@Override
	public final void getTriggers(final List<TriggerData> triggers) {
		triggers.add(TriggerPower.powerNone(this));
		triggers.add(TriggerPower.powerLow(this));
		triggers.add(TriggerPower.powerMedium(this));
		triggers.add(TriggerPower.powerHigh(this));
		triggers.add(TriggerPower.powerFull(this));
	}

	@Override
	@Optional.Method(modid = "IC2")
	public double getDemandedEnergy() {
		return this.container.getEnergySpace(PowerSystem.EU);
	}

	@Override
	@Optional.Method(modid = "IC2")
	public int getSinkTier() {
		return 1;
	}

	@Override
	@Optional.Method(modid = "IC2")
	public double injectEnergy(final ForgeDirection directionFrom, final double amount, final double voltage) {
		this.container.addEnergy(PowerSystem.EU, amount, true);
		return 0.0;
	}

	@Override
	@Optional.Method(modid = "IC2")
	public boolean acceptsEnergyFrom(final TileEntity emitter, final ForgeDirection direction) {
		return this.acceptsPowerSystem(PowerSystem.EU);
	}

	@Override
	public int receiveEnergy(final ForgeDirection from, final int maxReceive, final boolean simulate) {
		return (int) this.container.addEnergy(PowerSystem.RF, maxReceive, !simulate);
	}

	@Override
	public int extractEnergy(final ForgeDirection from, final int maxExtract, final boolean simulate) {
		return this.container.useEnergy(maxExtract, simulate);
	}

	@Override
	public int getEnergyStored(final ForgeDirection from) {
		return (int) this.container.getEnergy(PowerSystem.RF);
	}

	@Override
	public int getMaxEnergyStored(final ForgeDirection from) {
		return (int) this.container.getCapacity(PowerSystem.RF);
	}

	@Override
	public boolean canConnectEnergy(final ForgeDirection from) {
		final boolean can = this.acceptsPowerSystem(PowerSystem.RF);
		return can;
	}

	@Override
	public PowerInterface getInterface() {
		return this.container;
	}

	private boolean acceptsPowerSystem(final PowerSystem system) {
		return true;
	}

	@Override
	public void onInvalidation() {
		this.removeFromEnergyNet();
	}

	@Override
	public void onChunkUnload() {
		this.removeFromEnergyNet();
	}

	private void addToEnergyNet() {
		if (this.getMachine().getWorld() == null) {
			return;
		}
		if (Mods.IC2.active()) {
			this.do_addToEnergyNet();
		}
	}

	private void removeFromEnergyNet() {
		if (this.getMachine().getWorld() == null) {
			return;
		}
		if (Mods.IC2.active()) {
			this.do_removeFromEnergyNet();
		}
	}

	@Optional.Method(modid = "IC2")
	private void do_addToEnergyNet() {
		MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent((IEnergyTile) this.getMachine().getTileEntity()));
		this.registeredToIC2EnergyNet = true;
	}

	@Optional.Method(modid = "IC2")
	private void do_removeFromEnergyNet() {
		MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent((IEnergyTile) this.getMachine().getTileEntity()));
		this.registeredToIC2EnergyNet = false;
	}
}
