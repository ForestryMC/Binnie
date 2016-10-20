package binnie.core.machines.base;

import binnie.core.machines.inventory.TankSlot;
import binnie.core.machines.power.ITankMachine;
import binnie.core.machines.power.TankInfo;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidTank;

class DefaultTankContainer implements ITankMachine {
    @Override
    public int fill(final EnumFacing from, final FluidStack resource, final boolean doFill) {
        return 0;
    }

    @Override
    public FluidStack drain(final EnumFacing from, final int maxDrain, final boolean doDrain) {
        return null;
    }

    @Override
    public TankInfo[] getTankInfos() {
        return new TankInfo[0];
    }

    @Override
    public boolean isTankReadOnly(final int tank) {
        return false;
    }

    @Override
    public boolean isLiquidValidForTank(final FluidStack liquid, final int tank) {
        return false;
    }

    @Override
    public TankSlot addTank(final int index, final String name, final int capacity) {
        return null;
    }

    @Override
    public IFluidTank getTank(final int index) {
        return null;
    }

    @Override
    public TankSlot getTankSlot(final int slot) {
        return null;
    }

    @Override
    public FluidStack drain(final EnumFacing from, final FluidStack resource, final boolean doDrain) {
        return null;
    }

    @Override
    public boolean canFill(final EnumFacing from, final Fluid fluid) {
        return false;
    }

    @Override
    public boolean canDrain(final EnumFacing from, final Fluid fluid) {
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(final EnumFacing from) {
        return new FluidTankInfo[0];
    }

    @Override
    public IFluidTank[] getTanks() {
        return new IFluidTank[0];
    }
}
