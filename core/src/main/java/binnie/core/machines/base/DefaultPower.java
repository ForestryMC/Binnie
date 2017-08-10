package binnie.core.machines.base;

import net.minecraft.util.EnumFacing;

import net.minecraftforge.fml.common.Optional;

import binnie.core.machines.power.IPoweredMachine;
import binnie.core.machines.power.PowerInfo;
import binnie.core.machines.power.PowerInterface;
import ic2.api.energy.tile.IEnergyEmitter;

class DefaultPower implements IPoweredMachine {
	public static final DefaultPower INSTANCE = new DefaultPower();

	@Override
	public PowerInfo getPowerInfo() {
		return new PowerInfo(this, 0.0f);
	}

	@Override
	@Optional.Method(modid = "ic2")
	public double getDemandedEnergy() {
		return 0.0;
	}

	@Override
	@Optional.Method(modid = "ic2")
	public int getSinkTier() {
		return 0;
	}

	@Override
	@Optional.Method(modid = "ic2")
	public double injectEnergy(EnumFacing directionFrom, double amount, double voltage) {
		return 0.0;
	}

	@Override
	@Optional.Method(modid = "ic2")
	public boolean acceptsEnergyFrom(IEnergyEmitter emitter, EnumFacing direction) {
		return false;
	}

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
