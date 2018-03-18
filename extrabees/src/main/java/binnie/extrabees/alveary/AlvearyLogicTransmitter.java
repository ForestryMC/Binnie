package binnie.extrabees.alveary;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import forestry.api.multiblock.IMultiblockComponent;


public class AlvearyLogicTransmitter extends AlvearyLogicElectrical {

	private Set<IEnergyStorage> storages = new HashSet<>();

	public AlvearyLogicTransmitter() {
		super(2000);
	}

	@Override
	public void onMachineAssembled(TileEntityExtraBeesAlvearyPart tile) {
		super.onMachineAssembled(tile);
		for (IMultiblockComponent part : tile.getConnectedComponents()) {
			if (part instanceof TileEntity && part != tile) {
				TileEntity tileEntity = (TileEntity) part;
				if (tileEntity.hasCapability(CapabilityEnergy.ENERGY, EnumFacing.UP)) {
					storages.add(tileEntity.getCapability(CapabilityEnergy.ENERGY, EnumFacing.UP));
				}
			}
		}
	}

	@Override
	public void onMachineBroken(TileEntityExtraBeesAlvearyPart tile) {
		super.onMachineBroken(tile);
		storages.clear();
	}

	@Override
	public void updateServer(TileEntityExtraBeesAlvearyPart tile) {
		if (energyStorage.getEnergyStored() < 2) {
			return;
		}

		if (storages.isEmpty()) {
			return;
		}
		int div = storages.size();
		int maxOutput = 500;
		int output = energyStorage.getEnergyStored() / div;
		if (output > maxOutput) {
			output = maxOutput;
		}
		if (output < 1) {
			return;
		}
		for (IEnergyStorage handler :storages) {
			int recieved = handler.receiveEnergy(output, false);
			energyStorage.extractEnergy(recieved, false);
			if (energyStorage.getEnergyStored() < output) {
				return;
			}
		}
	}
}
