package binnie.core.machines.inventory;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import binnie.core.machines.IMachine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.power.ITankMachine;
import binnie.core.machines.power.TankInfo;
import binnie.core.util.EmptyHelper;
import binnie.core.util.NBTUtil;

public class ComponentTankContainer extends MachineComponent implements ITankMachine {
	private static final String TANKS_KEY = "liquidTanks";
	private static final String TANK_INDEX_KEY = "index";

	private final Map<Integer, TankSlot> tanks;
	private final EnumMap<EnumFacing, IFluidHandler> handlers;
	private final IFluidHandler noFacingHandler;

	public ComponentTankContainer(final IMachine machine) {
		super(machine);
		this.tanks = new LinkedHashMap<>();
		this.handlers = new EnumMap<>(EnumFacing.class);
		for (EnumFacing facing : EnumFacing.VALUES) {
			handlers.put(facing, new TankContainer(this, this.tanks, facing));
		}
		this.noFacingHandler = new TankContainer(this, this.tanks, null);
	}

	@Override
	public final TankSlot addTank(final int index, final String name, final int capacity) {
		final TankSlot tank = new TankSlot(index, name, capacity);
		this.tanks.put(index, tank);
		return tank;
	}

	@Override
	public final TankSlot addTank(final int index, final ResourceLocation name, final int capacity) {
		final TankSlot tank = new TankSlot(index, name, capacity);
		this.tanks.put(index, tank);
		return tank;
	}

	private int fill(final int tankIndex, final FluidStack resource, final boolean doFill) {
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

	@Nullable
	private FluidStack drain(final int tankIndex, final int maxDrain, final boolean doDrain) {
		if (!this.tanks.containsKey(tankIndex)) {
			return null;
		}
		final TankSlot tank = this.tanks.get(tankIndex);
		final FluidStack drained = tank.getTank().drain(maxDrain, doDrain);
		if (drained != null && doDrain) {
			this.markDirty();
		}
		return drained;
	}

	@Override
	public void readFromNBT(final NBTTagCompound compound) {
		super.readFromNBT(compound);
		NBTUtil.readFromList(compound, TANKS_KEY, (tankNBT)->{
			final int index = tankNBT.getInteger(TANK_INDEX_KEY);
			if (this.tanks.containsKey(index)) {
				TankSlot tank = tanks.get(index);
				tank.readFromNBT(tankNBT);
			}
		});
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound = super.writeToNBT(compound);
		NBTUtil.writeToList(compound, TANKS_KEY, tanks, NBTUtil.writeToListConsumer(TANK_INDEX_KEY));
		return compound;
	}

	@Override
	public boolean isTankReadOnly(final int index) {
		return this.tanks.get(index).isReadOnly();
	}

	@Override
	public boolean isLiquidValidForTank(final FluidStack liquid, final int index) {
		final TankSlot slot = this.getTankSlot(index);
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
		final List<IFluidTank> tankList = new ArrayList<>();
		for (final TankSlot tank : this.tanks.values()) {
			tankList.add(tank.getTank());
		}
		return tankList.toArray(EmptyHelper.FLUID_TANKS_EMPTY);
	}

	@Override
	@Nullable
	public TankSlot getTankSlot(final int index) {
		return this.tanks.get(index);
	}

	public void markDirty() {
		getMachine().markDirty();
	}

	@Override
	@Nullable
	public IFluidHandler getHandler(@Nullable final EnumFacing from) {
		if (from == null) {
			return noFacingHandler;
		}
		return handlers.get(from);
	}

	@Nullable
	@Override
	public IFluidHandler getHandler(final int[] targetTanks) {
		final Map<Integer, TankSlot> tanks = new HashMap<>();
		for (final int index : targetTanks) {
			final TankSlot tankSlot = this.tanks.get(index);
			if (tankSlot != null) {
				tanks.put(index, tankSlot);
			}
		}
		if (tanks.isEmpty()) {
			return null;
		}
		return new TankContainer(this, tanks, null);
	}

	private static class TankContainer implements IFluidHandler {
		private final ComponentTankContainer tankContainer;
		private final Map<Integer, TankSlot> tanks;
		@Nullable
		private final EnumFacing from;

		private TankContainer(final ComponentTankContainer tankContainer, final Map<Integer, TankSlot> tanks, @Nullable final EnumFacing from) {
			this.tankContainer = tankContainer;
			this.tanks = tanks;
			this.from = from;
		}

		@Override
		public final int fill(final FluidStack resource, final boolean doFill) {
			final int index = getTankIndexToFill(tanks, from, resource);
			if (tanks.containsKey(index)) {
				return this.tankContainer.fill(index, resource, doFill);
			}
			return 0;
		}

		@Override
		@Nullable
		public FluidStack drain(final FluidStack resource, final boolean doDrain) {
			final int index = getTankIndexToDrain(tanks, from, null);
			if (tanks.containsKey(index)) {
				return this.tankContainer.drain(index, resource.amount, doDrain);
			}
			return null;
		}

		@Override
		@Nullable
		public final FluidStack drain(final int maxDrain, final boolean doDrain) {
			final int index = getTankIndexToDrain(tanks, from, null);
			if (tanks.containsKey(index)) {
				return this.tankContainer.drain(index, maxDrain, doDrain);
			}
			return null;
		}

		@Override
		public IFluidTankProperties[] getTankProperties() {
			final IFluidTankProperties[] properties = new IFluidTankProperties[getTanks().length];
			for (int i = 0; i < properties.length; ++i) {
				final IFluidTank tank = getTanks()[i];
				properties[i] = new FluidTankProperties(tank.getFluid(), tank.getCapacity());
			}
			return properties;
		}

		private IFluidTank[] getTanks() {
			final List<IFluidTank> ltanks = new ArrayList<>();
			for (final TankSlot tank : this.tanks.values()) {
				ltanks.add(tank.getTank());
			}
			return ltanks.toArray(EmptyHelper.FLUID_TANKS_EMPTY);
		}

		private static int getTankIndexToFill(final Map<Integer, TankSlot> tanks, @Nullable final EnumFacing from, final FluidStack resource) {
			for (final TankSlot tank : tanks.values()) {
				if (tank.isValid(resource) && tank.canInsert(from) && (tank.getContent() == null || tank.getContent().isFluidEqual(resource))) {
					return tank.getIndex();
				}
			}
			return -1;
		}

		private static int getTankIndexToDrain(final Map<Integer, TankSlot> tanks, @Nullable final EnumFacing from, @Nullable final FluidStack resource) {
			for (final TankSlot tank : tanks.values()) {
				if (tank.getContent() != null && tank.canExtract(from) && (resource == null || resource.isFluidEqual(tank.getContent()))) {
					return tank.getIndex();
				}
			}
			return -1;
		}
	}
}
