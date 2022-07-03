package binnie.core.machines.base;

import binnie.core.machines.inventory.TankSlot;
import binnie.core.machines.power.ITankMachine;
import binnie.core.machines.power.TankInfo;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidTank;

class DefaultTankContainer implements ITankMachine {
    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return null;
    }

    @Override
    public TankInfo[] getTankInfos() {
        return new TankInfo[0];
    }

    @Override
    public boolean isTankReadOnly(int tank) {
        return false;
    }

    @Override
    public boolean isLiquidValidForTank(FluidStack liquid, int tank) {
        return false;
    }

    @Override
    public TankSlot addTank(int index, String name, int capacity) {
        return null;
    }

    @Override
    public IFluidTank getTank(int index) {
        return null;
    }

    @Override
    public TankSlot getTankSlot(int slot) {
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[0];
    }

    @Override
    public IFluidTank[] getTanks() {
        return new IFluidTank[0];
    }
}
