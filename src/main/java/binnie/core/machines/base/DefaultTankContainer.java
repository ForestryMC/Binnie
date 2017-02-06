package binnie.core.machines.base;

import binnie.core.machines.inventory.TankSlot;
import binnie.core.machines.power.ITankMachine;
import binnie.core.machines.power.TankInfo;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;

class DefaultTankContainer implements ITankMachine {
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
	public TankSlot getTankSlot(final int index) {
		return null;
	}

	@Override
	public IFluidHandler getHandler(EnumFacing from) {
		return null;
	}

	@Override
	public IFluidTank[] getTanks() {
		return new IFluidTank[0];
	}
}
