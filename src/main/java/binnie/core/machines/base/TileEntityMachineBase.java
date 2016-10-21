package binnie.core.machines.base;

import binnie.core.machines.Machine;
import binnie.core.machines.inventory.IInventoryMachine;
import binnie.core.machines.inventory.TankSlot;
import binnie.core.machines.power.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidTank;

import javax.annotation.Nullable;

public class TileEntityMachineBase extends TileEntity implements IInventoryMachine, ITankMachine, IPoweredMachine, ITickable {

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
        return (inv == null || inv == this) ? DefaultPower.INSTANCE : inv;
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

    @Nullable
    @Override
    public ItemStack removeStackFromSlot(int index) {
        return null;
    }

//	@Override
//	public ItemStack getStackInSlotOnClosing(final int var1) {
//		return this.getInventory().getStackInSlotOnClosing(var1);
//	}

    @Override
    public void setInventorySlotContents(final int index, final ItemStack itemStack) {
        this.getInventory().setInventorySlotContents(index, itemStack);
    }

    @Override
    public String getName() {
        return this.getInventory().getName();
    }

    @Override
    public int getInventoryStackLimit() {
        return this.getInventory().getInventoryStackLimit();
    }

    @Override
    public boolean isUseableByPlayer(final EntityPlayer entityplayer) {
        return !this.isInvalid() && this.getWorld().getTileEntity(getPos()) == this && entityplayer.getDistanceSq(getPos().getX() + 0.5, getPos().getY() + 0.5, getPos().getZ() + 0.5) <= 64.0 && this.getInventory().isUseableByPlayer(entityplayer);
    }

    @Override
    public void openInventory(EntityPlayer player) {
        this.getInventory().openInventory(player);
    }

    @Override
    public void closeInventory(EntityPlayer player) {
        this.getInventory().closeInventory(player);
    }

    @Override
    public boolean hasCustomName() {
        return this.getInventory().hasCustomName();
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


    //TODO Implement
    @Override
    public int getField(int id) {
        return 0;
    }
    //TODO Implement
    @Override
    public void setField(int id, int value) {

    }
    //TODO Implement
    @Override
    public int getFieldCount() {
        return 0;
    }
    //TODO Implement
    @Override
    public void clear() {

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
    public int fill(final EnumFacing from, final FluidStack resource, final boolean doFill) {
        return this.getTankContainer().fill(from, resource, doFill);
    }

    @Override
    public FluidStack drain(final EnumFacing from, final FluidStack resource, final boolean doDrain) {
        return this.getTankContainer().drain(from, resource, doDrain);
    }

    @Override
    public FluidStack drain(final EnumFacing from, final int maxDrain, final boolean doDrain) {
        return this.getTankContainer().drain(from, maxDrain, doDrain);
    }

    @Override
    public boolean canFill(final EnumFacing from, final Fluid fluid) {
        return this.getTankContainer().canFill(from, fluid);
    }

    @Override
    public boolean canDrain(final EnumFacing from, final Fluid fluid) {
        return this.getTankContainer().canDrain(from, fluid);
    }

    @Override
    public FluidTankInfo[] getTankInfo(final EnumFacing from) {
        return this.getTankContainer().getTankInfo(from);
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
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return (capability == CapabilityEnergy.ENERGY && (canExtract() || canReceive())) || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return (T) this;
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
}
