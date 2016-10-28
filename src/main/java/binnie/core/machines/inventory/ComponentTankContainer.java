package binnie.core.machines.inventory;

import binnie.core.machines.IMachine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.power.ITankMachine;
import binnie.core.machines.power.TankInfo;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.FluidTankPropertiesWrapper;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ComponentTankContainer extends MachineComponent implements ITankMachine {
	private Map<Integer, TankSlot> tanks;
	private EnumMap<EnumFacing, IFluidHandler> handlers;

	public ComponentTankContainer(final IMachine machine) {
		super(machine);
		this.tanks = new LinkedHashMap<>();
		this.handlers = new EnumMap<>(EnumFacing.class);
		for(EnumFacing facing : EnumFacing.VALUES){
			handlers.put(facing, new TankContainer(facing));
		}
	}

	@Override
	public final TankSlot addTank(final int index, final String name, final int capacity) {
		TankSlot tank = new TankSlot(index, name, capacity);
		this.tanks.put(index, tank);
		return tank;
	}

	private final int fill(final int tankIndex, final FluidStack resource, final boolean doFill) {
		if (!this.tanks.containsKey(tankIndex)) {
			return 0;
		}
		if (!this.isLiquidValidForTank(resource, tankIndex)) {
			return 0;
		}
		final TankSlot tank = this.tanks.get(tankIndex);
		final int filled = tank.getTank().fill(resource, doFill);
		if (filled > 0) {
			this.markDirty();
		}
		return filled;
	}

	private final FluidStack drain(final int tankIndex, final int maxDrain, final boolean doDrain) {
		if (!this.tanks.containsKey(tankIndex)) {
			return null;
		}
		final TankSlot tank = this.tanks.get(tankIndex);
		final FluidStack drained = tank.getTank().drain(maxDrain, doDrain);
		if (drained != null) {
			this.markDirty();
		}
		return drained;
	}

	private int getTankIndexToFill(final EnumFacing from, final FluidStack resource) {
		for (final TankSlot tank : this.tanks.values()) {
			if (tank.isValid(resource) && tank.canInsert(from) && (tank.getContent() == null || tank.getContent().isFluidEqual(resource))) {
				return tank.getIndex();
			}
		}
		return -1;
	}

	private int getTankIndexToDrain(final EnumFacing from, final FluidStack resource) {
		for (final TankSlot tank : this.tanks.values()) {
			if (tank.getContent() != null && tank.canExtract(from) && (resource == null || resource.isFluidEqual(tank.getContent()))) {
				return tank.getIndex();
			}
		}
		return -1;
	}

	@Override
	public void readFromNBT(final NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		if (nbttagcompound.hasKey("liquidTanks")) {
			final NBTTagList tanksNBT = nbttagcompound.getTagList("liquidTanks", 10);
			for (int i = 0; i < tanksNBT.tagCount(); ++i) {
				final NBTTagCompound tankNBT = tanksNBT.getCompoundTagAt(i);
				final int index = tankNBT.getInteger("index");
				if (this.tanks.containsKey(index)) {
					this.tanks.get(index).readFromNBT(tankNBT);
				}
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound nbttagcompound2) {
		NBTTagCompound nbttagcompound = super.writeToNBT(nbttagcompound2);
		final NBTTagList tanksNBT = new NBTTagList();
		for (final Map.Entry<Integer, TankSlot> entry : this.tanks.entrySet()) {
			final NBTTagCompound tankNBT = new NBTTagCompound();
			tankNBT.setInteger("index", entry.getKey());
			entry.getValue().writeToNBT(tankNBT);
			tanksNBT.appendTag(tankNBT);
		}
		nbttagcompound.setTag("liquidTanks", tanksNBT);
		return nbttagcompound;
	}

	@Override
	public boolean isTankReadOnly(final int tank) {
		return this.tanks.get(tank).isReadOnly();
	}

	@Override
	public boolean isLiquidValidForTank(final FluidStack liquid, final int tank) {
		final TankSlot slot = this.getTankSlot(tank);
		return slot != null && (slot.isValid(liquid) && !slot.isReadOnly());
	}

	@Override
	public TankInfo[] getTankInfos() {
		return TankInfo.get(this);
	}

	@Override
	public IFluidTank getTank(final int index) {
		return this.getTanks()[index];
	}

	@Override
	public IFluidTank[] getTanks() {
		final List<IFluidTank> ltanks = new ArrayList<>();
		for (final TankSlot tank : this.tanks.values()) {
			ltanks.add(tank.getTank());
		}
		return ltanks.toArray(new IFluidTank[0]);
	}

	@Override
	public TankSlot getTankSlot(final int index) {
		return this.tanks.get(index);
	}

	public void markDirty() {
		if (this.getMachine() != null) {
			this.getMachine().markDirty();
		}
	}
	
	@Override
	public IFluidHandler getHandler(EnumFacing from) {
		return handlers.get(from);
	}
	
	private class TankContainer implements IFluidHandler{
		EnumFacing from;
		
		public TankContainer(EnumFacing from) {
			this.from = from;
		}
		
		@Override
		public final int fill(final FluidStack resource, final boolean doFill) {
			final int index = getTankIndexToFill(from, resource);
			if (tanks.containsKey(index)) {
				return ComponentTankContainer.this.fill(index, resource, doFill);
			}
			return 0;
		}
		
		@Override
		public FluidStack drain(final FluidStack resource, final boolean doDrain) {
			final int index = getTankIndexToDrain(from, null);
			if (tanks.containsKey(index)) {
				return ComponentTankContainer.this.drain(index, resource.amount, doDrain);
			}
			return null;
		}

		@Override
		public final FluidStack drain(final int maxDrain, final boolean doDrain) {
			final int index = getTankIndexToDrain(from, null);
			if (tanks.containsKey(index)) {
				return ComponentTankContainer.this.drain(index, maxDrain, doDrain);
			}
			return null;
		}
		
		@Override
		public IFluidTankProperties[] getTankProperties() {
			final IFluidTankProperties[] properties = new IFluidTankProperties[getTanks().length];
			for (int i = 0; i < properties.length; ++i) {
				IFluidTank tank = getTanks()[i];
				properties[i] = new FluidTankProperties(tank.getFluid(), tank.getCapacity());
			}
			return properties;
		}
	}
}
