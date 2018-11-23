package binnie.extrabees.machines.transmission;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.multiblock.IMultiblockComponent;

import binnie.core.machines.Machine;
import binnie.extrabees.machines.TileExtraBeeAlveary;
import binnie.extrabees.utils.ComponentBeeModifier;

public class ComponentTransmission extends ComponentBeeModifier implements IBeeModifier, IBeeListener {
	public ComponentTransmission(Machine machine) {
		super(machine);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		int energy = this.getUtil().getPoweredMachine().getEnergyStored();
		if (energy == 0) {
			return;
		}
		TileExtraBeeAlveary tile = (TileExtraBeeAlveary) this.getMachine().getTileEntity();
		List<IEnergyStorage> handlers = new LinkedList<>();
		for (IMultiblockComponent component : tile.getAlvearyBlocks()) {
			if (!(component instanceof TileEntity)) {
				continue;
			}
			TileEntity alvearyTile = (TileEntity) component;
			if (alvearyTile != tile && alvearyTile.hasCapability(CapabilityEnergy.ENERGY, EnumFacing.NORTH)) {
				handlers.add(alvearyTile.getCapability(CapabilityEnergy.ENERGY, EnumFacing.NORTH));
			}
		}
		if (handlers.isEmpty()) {
			return;
		}
		int maxOutput = 500;
		int output = energy / handlers.size();
		if (output > maxOutput) {
			output = maxOutput;
		}
		if (output < 1) {
			output = 1;
		}
		for (IEnergyStorage handler : handlers) {
			int recieved = handler.receiveEnergy(output, false);
			this.getUtil().getPoweredMachine().receiveEnergy(-recieved, false);
			energy = this.getUtil().getPoweredMachine().getEnergyStored();
			if (energy <= 0) {
				return;
			}
		}
	}
}
