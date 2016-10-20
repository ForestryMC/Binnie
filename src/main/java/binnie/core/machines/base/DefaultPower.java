// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.machines.base;

import binnie.core.machines.power.PowerInterface;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.common.Optional;
import binnie.core.machines.power.PowerInfo;
import binnie.core.machines.power.IPoweredMachine;

class DefaultPower implements IPoweredMachine
{
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
	public double injectEnergy(final ForgeDirection directionFrom, final double amount, final double voltage) {
		return 0.0;
	}

	@Override
	@Optional.Method(modid = "IC2")
	public boolean acceptsEnergyFrom(final TileEntity emitter, final ForgeDirection direction) {
		return false;
	}

	@Override
	public int receiveEnergy(final ForgeDirection from, final int maxReceive, final boolean simulate) {
		return 0;
	}

	@Override
	public int extractEnergy(final ForgeDirection from, final int maxExtract, final boolean simulate) {
		return 0;
	}

	@Override
	public int getEnergyStored(final ForgeDirection from) {
		return 0;
	}

	@Override
	public int getMaxEnergyStored(final ForgeDirection from) {
		return 0;
	}

	@Override
	public boolean canConnectEnergy(final ForgeDirection from) {
		return false;
	}

	@Override
	public PowerInterface getInterface() {
		return null;
	}
}
