package binnie.extrabees.alveary;

import com.google.common.collect.Lists;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

import forestry.api.multiblock.IMultiblockComponent;


public class AlvearyLogicTransmitter extends AbstractAlvearyLogic implements IEnergyStorage {
	
	private final IEnergyStorage energyStorage;
	
	public AlvearyLogicTransmitter() {
		energyStorage = new EnergyStorage(2000);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		CapabilityEnergy.ENERGY.readNBT(energyStorage, null, nbt.getCompoundTag(ENERGY_NBT_KEY));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setTag(ENERGY_NBT_KEY, CapabilityEnergy.ENERGY.writeNBT(energyStorage, null));
		return nbt;
	}

	@Override
	public void updateServer(TileEntityExtraBeesAlvearyPart tile) {
		if (energyStorage.getEnergyStored() < 2) {
			return;
		}
		
		List<IEnergyStorage> esL = Lists.newArrayList();
		for (IMultiblockComponent part : tile.getConnectedComponents()) {
			if (part instanceof TileEntity) {
				if (((TileEntity) part).hasCapability(CapabilityEnergy.ENERGY, EnumFacing.UP)) {
					esL.add(((TileEntity) part).getCapability(CapabilityEnergy.ENERGY, EnumFacing.UP));
				}
			}
		}
		if (esL.isEmpty()) {
			return;
		}
		int div = esL.size();
		int maxOutput = 500;
		int output = energyStorage.getEnergyStored() / div;
		if (output > maxOutput) {
			output = maxOutput;
		}
		if (output < 1) {
			return;
		}
		for (IEnergyStorage handler : esL) {
			int recieved = handler.receiveEnergy(output, false);
			energyStorage.extractEnergy(recieved, false);
			if (energyStorage.getEnergyStored() < output) {
				return;
			}
		}
	}
	
	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		return energyStorage.receiveEnergy(maxReceive, simulate);
	}
	
	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		return 0;
	}
	
	@Override
	public int getEnergyStored() {
		return energyStorage.getEnergyStored();
	}
	
	@Override
	public int getMaxEnergyStored() {
		return energyStorage.getMaxEnergyStored();
	}
	
	@Override
	public boolean canExtract() {
		return false;
	}
	
	@Override
	public boolean canReceive() {
		return true;
	}
	
	@Override
	public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityEnergy.ENERGY;
	}
	
	@Nullable
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityEnergy.ENERGY ? (T) this : null;
	}
}
