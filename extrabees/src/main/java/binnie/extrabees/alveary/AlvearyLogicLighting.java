package binnie.extrabees.alveary;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.util.EnumFacing;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

public class AlvearyLogicLighting extends AbstractAlvearyLogic {
	
	private final IEnergyStorage energyStorage;
	private boolean lighted;
	
	public AlvearyLogicLighting() {
		this.energyStorage = new EnergyStorage(2000);
	}
	
	@Override
	public void updateServer(TileEntityExtraBeesAlvearyPart tile) {
		lighted = energyStorage.extractEnergy(10, false) >= 10;
	}
	
	@Override
	public boolean isSelfLighted() {
		return lighted;
	}
	
	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityEnergy.ENERGY || super.hasCapability(capability, facing);
	}
	
	@Nullable
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityEnergy.ENERGY ? (T) energyStorage : super.getCapability(capability, facing);
	}
}
