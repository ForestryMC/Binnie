package binnie.core.machines.base;

import binnie.core.machines.power.IPoweredMachine;
import binnie.core.machines.power.PowerInfo;
import binnie.core.machines.power.PowerInterface;
import cpw.mods.fml.common.Optional;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

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
