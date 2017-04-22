package binnie.core.machines.base;

import binnie.core.machines.power.*;
import cpw.mods.fml.common.*;
import net.minecraft.tileentity.*;
import net.minecraftforge.common.util.*;

class DefaultPower implements IPoweredMachine {
	@Override
	public PowerInfo getPowerInfo() {
		return new PowerInfo(this, 0.0f);
	}

	@Override
	@Optional.Method(modid = "IC2")
	public double getDemandedEnergy() {
		return 0.0;
	}

	@Override
	@Optional.Method(modid = "IC2")
	public int getSinkTier() {
		return 0;
	}

	@Override
	@Optional.Method(modid = "IC2")
	public double injectEnergy(ForgeDirection directionFrom, double amount, double voltage) {
		return 0.0;
	}

	@Override
	@Optional.Method(modid = "IC2")
	public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
		return false;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		return 0;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		return 0;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return 0;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return 0;
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return false;
	}

	@Override
	public PowerInterface getInterface() {
		return null;
	}
}
