package binnie.core.machines.base;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import net.minecraftforge.fml.common.Optional;

import binnie.core.Constants;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.IInventoryMachine;
import binnie.core.machines.inventory.TankSlot;
import binnie.core.machines.power.IPoweredMachine;
import binnie.core.machines.power.ITankMachine;
import binnie.core.machines.power.PowerInfo;
import binnie.core.machines.power.PowerInterface;
import binnie.core.machines.power.TankInfo;
import binnie.core.util.MjHelper;

import buildcraft.api.mj.IMjConnector;
import buildcraft.api.mj.IMjPassiveProvider;
import buildcraft.api.mj.IMjReadable;
import buildcraft.api.mj.IMjReceiver;
import buildcraft.api.mj.IMjRedstoneReceiver;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;

@Optional.Interface(iface = "ic2.api.energy.tile.IEnergySink", modid = "ic2")
public class TileEntityMachineBase extends TileEntity implements IInventoryMachine, ITankMachine, IPoweredMachine, ITickable, IEnergySink {

	@Override
	public IInventoryMachine getInventory() {
		IInventoryMachine inv = Machine.getInterface(IInventoryMachine.class, this);
		return (inv == null || inv == this) ? new DefaultMachineInventory() : inv;
	}

	public ITankMachine getTankContainer() {
		ITankMachine inv = Machine.getInterface(ITankMachine.class, this);
		return (inv == null || inv == this) ? DefaultTankContainer.INSTANCE : inv;
	}

	public IPoweredMachine getPower() {
		IPoweredMachine inv = Machine.getInterface(IPoweredMachine.class, this);
		return (inv == null || inv == this) ? DefaultPower.INSTANCE : inv;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return this.getInventory().removeStackFromSlot(index);
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer entityplayer) {
		return !this.isInvalid() &&
			this.getWorld().getTileEntity(getPos()) == this &&
			entityplayer.getDistanceSqToCenter(getPos()) <= 64.0 &&
			this.getInventory().isUsableByPlayer(entityplayer);
	}

	@Override
	public void markDirty() {
		super.markDirty();
		this.getInventory().markDirty();
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return this.getInventory().getSlotsForFace(side);
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, EnumFacing j) {
		return this.getInventory().canInsertItem(i, itemstack, j);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, EnumFacing j) {
		return this.getInventory().canExtractItem(i, itemstack, j);
	}

	@Override
	public PowerInfo getPowerInfo() {
		return this.getPower().getPowerInfo();
	}

	@Override
	public TankInfo[] getTankInfos() {
		return this.getTankContainer().getTankInfos();
	}

	@Override
	public boolean isTankReadOnly(int index) {
		return this.getTankContainer().isTankReadOnly(index);
	}

	@Override
	public boolean isLiquidValidForTank(FluidStack liquid, int index) {
		return this.getTankContainer().isLiquidValidForTank(liquid, index);
	}

	@Override
	public TankSlot addTank(int index, String name, int capacity) {
		return this.getTankContainer().addTank(index, name, capacity);
	}

	@Override
	public TankSlot addTank(int index, ResourceLocation name, int capacity) {
		return this.getTankContainer().addTank(index, name, capacity);
	}

	@Override
	public IFluidTank getTank(int index) {
		return this.getTankContainer().getTank(index);
	}

	@Override
	public TankSlot getTankSlot(int index) {
		return this.getTankContainer().getTankSlot(index);
	}

	@Override
	public IFluidTank[] getTanks() {
		return this.getTankContainer().getTanks();
	}

	@Override
	@Optional.Method(modid = "ic2")
	public double getDemandedEnergy() {
		return this.getPower().getDemandedEnergy();
	}

	@Override
	@Optional.Method(modid = "ic2")
	public int getSinkTier() {
		return this.getPower().getSinkTier();
	}

	@Override
	@Optional.Method(modid = "ic2")
	public double injectEnergy(EnumFacing directionFrom, double amount, double voltage) {
		return this.getPower().injectEnergy(directionFrom, amount, voltage);
	}

	@Override
	@Optional.Method(modid = "ic2")
	public boolean acceptsEnergyFrom(IEnergyEmitter emitter, EnumFacing direction) {
		return this.getPower().acceptsEnergyFrom(emitter, direction);
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		return this.getPower().receiveEnergy(maxReceive, simulate);
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		return this.getPower().extractEnergy(maxExtract, simulate);
	}

	@Override
	public int getEnergyStored() {
		return this.getPower().getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored() {
		return this.getPower().getMaxEnergyStored();
	}

	@Override
	public boolean canExtract() {
		return this.getPower().canExtract();
	}

	@Override
	public boolean canReceive() {
		return this.getPower().canReceive();
	}

	@Override
	@Optional.Method(modid = Constants.BCLIB_MOD_ID)
	public long extractPower(long min, long max, boolean simulate) {
		return this.getPower().extractPower(min, max, simulate);
	}

	@Override
	@Optional.Method(modid = Constants.BCLIB_MOD_ID)
	public long getStored() {
		return this.getPower().getStored();
	}

	@Override
	@Optional.Method(modid = Constants.BCLIB_MOD_ID)
	public long getCapacity() {
		return this.getPower().getCapacity();
	}

	@Override
	@Optional.Method(modid = Constants.BCLIB_MOD_ID)
	public long getPowerRequested() {
		return this.getPower().getPowerRequested();
	}

	@Override
	@Optional.Method(modid = Constants.BCLIB_MOD_ID)
	public long receivePower(long microJoules, boolean simulate) {
		return this.getPower().receivePower(microJoules, simulate);
	}

	@Override
	@Optional.Method(modid = Constants.BCLIB_MOD_ID)
	public boolean canConnect(@Nonnull IMjConnector other) {
		return this.getPower().canConnect(other);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		return (capability == CapabilityEnergy.ENERGY && (canExtract() || canReceive())) || capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && getTankContainer() != DefaultTankContainer.INSTANCE) || super.hasCapability(capability, facing);
	}

	@Override
	@Nullable
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == CapabilityEnergy.ENERGY) {
			return CapabilityEnergy.ENERGY.cast(this);
		} else if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if (facing != null) {
				SidedInvWrapper sidedInvWrapper = new SidedInvWrapper(getInventory(), facing);
				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(sidedInvWrapper);
			} else {
				InvWrapper invWrapper = new InvWrapper(getInventory());
				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(invWrapper);
			}
		} else if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(getHandler(facing));
		} else if (MjHelper.isMjCapability(capability)) {
			Capability<IMjConnector> mjConnector = MjHelper.CAP_CONNECTOR;
			Capability<IMjPassiveProvider> mjPassiveProvider = MjHelper.CAP_PASSIVE_PROVIDER;
			Capability<IMjReadable> mjReadable = MjHelper.CAP_READABLE;
			Capability<IMjReceiver> mjReceiver = MjHelper.CAP_RECEIVER;
			Capability<IMjRedstoneReceiver> mjRedstoneReceiver = MjHelper.CAP_REDSTONE_RECEIVER;

			if (capability == mjPassiveProvider) {
				return mjPassiveProvider.cast(this);
			} else if (capability == mjReceiver) {
				return mjReceiver.cast(this);
			} else if (capability == mjRedstoneReceiver) {
				return mjRedstoneReceiver.cast(this);
			} else if (capability == mjReadable) {
				return mjReadable.cast(this);
			} else if (capability == mjConnector) {
				return mjConnector.cast(this);
			}
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public PowerInterface getInterface() {
		return this.getPower().getInterface();
	}

	@Override
	public void update() {

	}

	@Override
	@Nullable
	public IFluidHandler getHandler(@Nullable EnumFacing from) {
		return getTankContainer().getHandler(from);
	}

	@Nullable
	@Override
	public IFluidHandler getHandler(int[] targetTanks) {
		return getTankContainer().getHandler(targetTanks);
	}
}
