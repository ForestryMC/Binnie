// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.machines.base;

import binnie.core.machines.power.PowerInterface;
import cpw.mods.fml.common.Optional;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.IFluidTank;
import binnie.core.machines.inventory.TankSlot;
import net.minecraftforge.fluids.FluidStack;
import binnie.core.machines.power.TankInfo;
import binnie.core.machines.power.PowerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import binnie.core.machines.Machine;
import binnie.core.machines.power.IPoweredMachine;
import binnie.core.machines.power.ITankMachine;
import binnie.core.machines.inventory.IInventoryMachine;
import net.minecraft.tileentity.TileEntity;

public class TileEntityMachineBase extends TileEntity implements IInventoryMachine, ITankMachine, IPoweredMachine
{
	public IInventoryMachine getInventory() {
		final IInventoryMachine inv = Machine.getInterface(IInventoryMachine.class, this);
		return (inv == null || inv == this) ? new DefaultInventory() : inv;
	}

	public ITankMachine getTankContainer() {
		final ITankMachine inv = Machine.getInterface(ITankMachine.class, this);
		return (inv == null || inv == this) ? new DefaultTankContainer() : inv;
	}

	public IPoweredMachine getPower() {
		final IPoweredMachine inv = Machine.getInterface(IPoweredMachine.class, this);
		return (inv == null || inv == this) ? new DefaultPower() : inv;
	}

	@Override
	public int getSizeInventory() {
		return this.getInventory().getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(final int index) {
		return this.getInventory().getStackInSlot(index);
	}

	@Override
	public ItemStack decrStackSize(final int index, final int amount) {
		return this.getInventory().decrStackSize(index, amount);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(final int var1) {
		return this.getInventory().getStackInSlotOnClosing(var1);
	}

	@Override
	public void setInventorySlotContents(final int index, final ItemStack itemStack) {
		this.getInventory().setInventorySlotContents(index, itemStack);
	}

	@Override
	public String getInventoryName() {
		return this.getInventory().getInventoryName();
	}

	@Override
	public int getInventoryStackLimit() {
		return this.getInventory().getInventoryStackLimit();
	}

	@Override
	public boolean isUseableByPlayer(final EntityPlayer entityplayer) {
		return !this.isInvalid() && this.getWorldObj().getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && entityplayer.getDistanceSq(this.xCoord + 0.5, this.yCoord + 0.5, this.zCoord + 0.5) <= 64.0 && this.getInventory().isUseableByPlayer(entityplayer);
	}

	@Override
	public void openInventory() {
		this.getInventory().openInventory();
	}

	@Override
	public void closeInventory() {
		this.getInventory().closeInventory();
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.getInventory().hasCustomInventoryName();
	}

	@Override
	public void markDirty() {
		super.markDirty();
		this.getInventory().markDirty();
	}

	@Override
	public boolean isItemValidForSlot(final int slot, final ItemStack itemStack) {
		return this.getInventory().isItemValidForSlot(slot, itemStack);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(final int var1) {
		return this.getInventory().getAccessibleSlotsFromSide(var1);
	}

	@Override
	public boolean canInsertItem(final int i, final ItemStack itemstack, final int j) {
		return this.getInventory().canInsertItem(i, itemstack, j);
	}

	@Override
	public boolean canExtractItem(final int i, final ItemStack itemstack, final int j) {
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
	public int fill(final ForgeDirection from, final FluidStack resource, final boolean doFill) {
		return this.getTankContainer().fill(from, resource, doFill);
	}

	@Override
	public FluidStack drain(final ForgeDirection from, final FluidStack resource, final boolean doDrain) {
		return this.getTankContainer().drain(from, resource, doDrain);
	}

	@Override
	public FluidStack drain(final ForgeDirection from, final int maxDrain, final boolean doDrain) {
		return this.getTankContainer().drain(from, maxDrain, doDrain);
	}

	@Override
	public boolean canFill(final ForgeDirection from, final Fluid fluid) {
		return this.getTankContainer().canFill(from, fluid);
	}

	@Override
	public boolean canDrain(final ForgeDirection from, final Fluid fluid) {
		return this.getTankContainer().canDrain(from, fluid);
	}

	@Override
	public FluidTankInfo[] getTankInfo(final ForgeDirection from) {
		return this.getTankContainer().getTankInfo(from);
	}

	@Override
	public IFluidTank[] getTanks() {
		return this.getTankContainer().getTanks();
	}

	@Override
	@Optional.Method(modid = "IC2")
	public double getDemandedEnergy() {
		return this.getPower().getDemandedEnergy();
	}

	@Override
	@Optional.Method(modid = "IC2")
	public int getSinkTier() {
		return this.getPower().getSinkTier();
	}

	@Override
	@Optional.Method(modid = "IC2")
	public double injectEnergy(final ForgeDirection directionFrom, final double amount, final double voltage) {
		return this.getPower().injectEnergy(directionFrom, amount, voltage);
	}

	@Override
	@Optional.Method(modid = "IC2")
	public boolean acceptsEnergyFrom(final TileEntity emitter, final ForgeDirection direction) {
		return this.getPower().acceptsEnergyFrom(emitter, direction);
	}

	@Override
	public int receiveEnergy(final ForgeDirection from, final int maxReceive, final boolean simulate) {
		return this.getPower().receiveEnergy(from, maxReceive, simulate);
	}

	@Override
	public int extractEnergy(final ForgeDirection from, final int maxExtract, final boolean simulate) {
		return this.getPower().extractEnergy(from, maxExtract, simulate);
	}

	@Override
	public int getEnergyStored(final ForgeDirection from) {
		return this.getPower().getEnergyStored(from);
	}

	@Override
	public int getMaxEnergyStored(final ForgeDirection from) {
		return this.getPower().getMaxEnergyStored(from);
	}

	@Override
	public boolean canConnectEnergy(final ForgeDirection from) {
		return this.getPower().canConnectEnergy(from);
	}

	@Override
	public PowerInterface getInterface() {
		return this.getPower().getInterface();
	}
}
