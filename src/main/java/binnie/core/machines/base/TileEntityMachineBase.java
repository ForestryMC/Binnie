package binnie.core.machines.base;

import binnie.core.machines.Machine;
import binnie.core.machines.inventory.IInventoryMachine;
import binnie.core.machines.inventory.TankSlot;
import binnie.core.machines.power.IPoweredMachine;
import binnie.core.machines.power.ITankMachine;
import binnie.core.machines.power.PowerInfo;
import binnie.core.machines.power.PowerInterface;
import binnie.core.machines.power.TankInfo;
import cpw.mods.fml.common.Optional;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidTank;

public class TileEntityMachineBase extends TileEntity implements IInventoryMachine, ITankMachine, IPoweredMachine {
    public IInventoryMachine getInventory() {
        IInventoryMachine inv = Machine.getInterface(IInventoryMachine.class, this);
        return (inv == null || inv == this) ? new DefaultInventory() : inv;
    }

    public ITankMachine getTankContainer() {
        ITankMachine inv = Machine.getInterface(ITankMachine.class, this);
        return (inv == null || inv == this) ? new DefaultTankContainer() : inv;
    }

    public IPoweredMachine getPower() {
        IPoweredMachine inv = Machine.getInterface(IPoweredMachine.class, this);
        return (inv == null || inv == this) ? new DefaultPower() : inv;
    }

    @Override
    public int getSizeInventory() {
        return getInventory().getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return getInventory().getStackInSlot(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int amount) {
        return getInventory().decrStackSize(index, amount);
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int var1) {
        return getInventory().getStackInSlotOnClosing(var1);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack itemStack) {
        getInventory().setInventorySlotContents(index, itemStack);
    }

    @Override
    public String getInventoryName() {
        return getInventory().getInventoryName();
    }

    @Override
    public int getInventoryStackLimit() {
        return getInventory().getInventoryStackLimit();
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return !isInvalid()
                && getWorldObj().getTileEntity(xCoord, yCoord, zCoord) == this
                && player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) <= 64.0
                && getInventory().isUseableByPlayer(player);
    }

    @Override
    public void openInventory() {
        getInventory().openInventory();
    }

    @Override
    public void closeInventory() {
        getInventory().closeInventory();
    }

    @Override
    public boolean hasCustomInventoryName() {
        return getInventory().hasCustomInventoryName();
    }

    @Override
    public void markDirty() {
        super.markDirty();
        getInventory().markDirty();
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
        return getInventory().isItemValidForSlot(slot, itemStack);
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int var1) {
        return getInventory().getAccessibleSlotsFromSide(var1);
    }

    @Override
    public boolean canInsertItem(int i, ItemStack itemstack, int j) {
        return getInventory().canInsertItem(i, itemstack, j);
    }

    @Override
    public boolean canExtractItem(int i, ItemStack itemstack, int j) {
        return getInventory().canExtractItem(i, itemstack, j);
    }

    @Override
    public boolean isReadOnly(int slot) {
        return getInventory().isReadOnly(slot);
    }

    @Override
    public PowerInfo getPowerInfo() {
        return getPower().getPowerInfo();
    }

    @Override
    public TankInfo[] getTankInfos() {
        return getTankContainer().getTankInfos();
    }

    @Override
    public boolean isTankReadOnly(int tank) {
        return getTankContainer().isTankReadOnly(tank);
    }

    @Override
    public boolean isLiquidValidForTank(FluidStack liquid, int tank) {
        return getTankContainer().isLiquidValidForTank(liquid, tank);
    }

    @Override
    public TankSlot addTank(int index, String name, int capacity) {
        return getTankContainer().addTank(index, name, capacity);
    }

    @Override
    public IFluidTank getTank(int index) {
        return getTankContainer().getTank(index);
    }

    @Override
    public TankSlot getTankSlot(int slot) {
        return getTankContainer().getTankSlot(slot);
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return getTankContainer().fill(from, resource, doFill);
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        return getTankContainer().drain(from, resource, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return getTankContainer().drain(from, maxDrain, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return getTankContainer().canFill(from, fluid);
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return getTankContainer().canDrain(from, fluid);
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return getTankContainer().getTankInfo(from);
    }

    @Override
    public IFluidTank[] getTanks() {
        return getTankContainer().getTanks();
    }

    @Override
    @Optional.Method(modid = "IC2")
    public double getDemandedEnergy() {
        return getPower().getDemandedEnergy();
    }

    @Override
    @Optional.Method(modid = "IC2")
    public int getSinkTier() {
        return getPower().getSinkTier();
    }

    @Override
    @Optional.Method(modid = "IC2")
    public double injectEnergy(ForgeDirection directionFrom, double amount, double voltage) {
        return getPower().injectEnergy(directionFrom, amount, voltage);
    }

    @Override
    @Optional.Method(modid = "IC2")
    public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
        return getPower().acceptsEnergyFrom(emitter, direction);
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
        return getPower().receiveEnergy(from, maxReceive, simulate);
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
        return getPower().extractEnergy(from, maxExtract, simulate);
    }

    @Override
    public int getEnergyStored(ForgeDirection from) {
        return getPower().getEnergyStored(from);
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from) {
        return getPower().getMaxEnergyStored(from);
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
        return getPower().canConnectEnergy(from);
    }

    @Override
    public PowerInterface getInterface() {
        return getPower().getInterface();
    }
}
