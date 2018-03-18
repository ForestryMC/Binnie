package binnie.extrabees.alveary;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

import binnie.core.util.NBTUtil;

public abstract class AlvearyLogicElectrical extends AlvearyLogic {
	protected final IEnergyStorage energyStorage;

	protected AlvearyLogicElectrical(int capacity) {
		this.energyStorage = new EnergyStorage(capacity);
	}

	public IEnergyStorage getEnergyStorage() {
		return energyStorage;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		NBTUtil.readFromNBT(energyStorage, ENERGY_NBT_KEY, nbt);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		NBTUtil.writeToNBT(energyStorage, ENERGY_NBT_KEY, nbt);
		return nbt;
	}

	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityEnergy.ENERGY || super.hasCapability(capability, facing);
	}

	@Nullable
	@Override
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityEnergy.ENERGY ? CapabilityEnergy.ENERGY.cast(energyStorage) : super.getCapability(capability, facing);
	}
}
