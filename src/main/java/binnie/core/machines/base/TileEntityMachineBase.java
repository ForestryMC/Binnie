package binnie.core.machines.base;

import binnie.core.machines.Machine;
import binnie.core.machines.inventory.IInventoryMachine;
import binnie.core.machines.inventory.TankSlot;
import binnie.core.machines.power.IPoweredMachine;
import binnie.core.machines.power.ITankMachine;
import binnie.core.machines.power.PowerInfo;
import binnie.core.machines.power.PowerInterface;
import binnie.core.machines.power.TankInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nullable;

public class TileEntityMachineBase extends TileEntity implements IInventoryMachine, ITankMachine, IPoweredMachine, ITickable {

	public IInventoryMachine getInventory() {
		final IInventoryMachine inv = Machine.getInterface(IInventoryMachine.class, this);
		return (inv == null || inv == this) ? new DefaultMachineInventory() : inv;
	}

	public ITankMachine getTankContainer() {
		final ITankMachine inv = Machine.getInterface(ITankMachine.class, this);
		return (inv == null || inv == this) ? new DefaultTankContainer() : inv;
	}

	public IPoweredMachine getPower() {
		final IPoweredMachine inv = Machine.getInterface(IPoweredMachine.class, this);
		return (inv == null || inv == this) ? DefaultPower.INSTANCE : inv;
	}

//	@Override
//	public ItemStack getStackInSlotOnClosing(final int var1) {
//		return this.getInventory().getStackInSlotOnClosing(var1);
//	}

	@Override
	public boolean isUsableByPlayer(final EntityPlayer entityplayer) {
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
	public boolean canInsertItem(final int i, final ItemStack itemstack, EnumFacing j) {
		return this.getInventory().canInsertItem(i, itemstack, j);
	}

	@Override
	public boolean canExtractItem(final int i, final ItemStack itemstack, EnumFacing j) {
		return this.getInventory().canExtractItem(i, itemstack, j);
	}

	@Override
	public boolean isReadOnly(final int slot) {
		return this.getInventory().isReadOnly(slot);
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
	public boolean isTankReadOnly(final int tank) {
		return this.getTankContainer().isTankReadOnly(tank);
	}

	@Override
	public boolean isLiquidValidForTank(final FluidStack liquid, final int tank) {
		return this.getTankContainer().isLiquidValidForTank(liquid, tank);
	}

	@Override
	public TankSlot addTank(final int index, final String name, final int capacity) {
		return this.getTankContainer().addTank(index, name, capacity);
	}

	@Override
	public IFluidTank getTank(final int index) {
		return this.getTankContainer().getTank(index);
	}

	@Override
	public TankSlot getTankSlot(final int index) {
		return this.getTankContainer().getTankSlot(index);
	}

	@Override
	public IFluidTank[] getTanks() {
		return this.getTankContainer().getTanks();
	}

//	@Override
//	@Optional.Method(modid = "IC2")
//	public double getDemandedEnergy() {
//		return this.getPower().getDemandedEnergy();
//	}
//
//	@Override
//	@Optional.Method(modid = "IC2")
//	public int getSinkTier() {
//		return this.getPower().getSinkTier();
//	}
//
//	@Override
//	@Optional.Method(modid = "IC2")
//	public double injectEnergy(final ForgeDirection directionFrom, final double amount, final double voltage) {
//		return this.getPower().injectEnergy(directionFrom, amount, voltage);
//	}
//
//	@Override
//	@Optional.Method(modid = "IC2")
//	public boolean acceptsEnergyFrom(final TileEntity emitter, final ForgeDirection direction) {
//		return this.getPower().acceptsEnergyFrom(emitter, direction);
//	}

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
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		return (capability == CapabilityEnergy.ENERGY && (canExtract() || canReceive())) || capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Override
	@Nullable
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == CapabilityEnergy.ENERGY) {
			return CapabilityEnergy.ENERGY.cast(this);
		} else if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(new InvWrapper(getInventory()));
		} else if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(getHandler(facing));
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

}
