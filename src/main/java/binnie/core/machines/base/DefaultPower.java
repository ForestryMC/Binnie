package binnie.core.machines.base;

import binnie.core.machines.power.IPoweredMachine;
import binnie.core.machines.power.PowerInfo;
import binnie.core.machines.power.PowerInterface;

class DefaultPower implements IPoweredMachine {
	public static final DefaultPower INSTANCE = new DefaultPower();

	@Override
	public PowerInfo getPowerInfo() {
		return new PowerInfo(this, 0.0f);
	}

//	@Override
//	@Optional.Method(modid = "IC2")
//	public double getDemandedEnergy() {
//		return 0.0;
//	}
//
//	@Override
//	@Optional.Method(modid = "IC2")
//	public int getSinkTier() {
//		return 0;
//	}

//	@Override
//	@Optional.Method(modid = "IC2")
//	public double injectEnergy(final EnumFacing directionFrom, final double amount, final double voltage) {
//		return 0.0;
//	}
//
//	@Override
//	@Optional.Method(modid = "IC2")
//	public boolean acceptsEnergyFrom(final TileEntity emitter, final EnumFacing direction) {
//		return false;
//	}

	@Override
	public PowerInterface getInterface() {
		return null;
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		return 0;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		return 0;
	}

	@Override
	public int getEnergyStored() {
		return 0;
	}

	@Override
	public int getMaxEnergyStored() {
		return 0;
	}

	@Override
	public boolean canExtract() {
		return false;
	}

	@Override
	public boolean canReceive() {
		return false;
	}

}
