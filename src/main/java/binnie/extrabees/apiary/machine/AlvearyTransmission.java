package binnie.extrabees.apiary.machine;


import binnie.core.machines.Machine;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.craftgui.minecraft.IMachineInformation;
import binnie.extrabees.apiary.ComponentBeeModifier;
import binnie.extrabees.apiary.TileExtraBeeAlveary;
import binnie.extrabees.core.ExtraBeeTexture;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.LinkedList;
import java.util.List;

public class AlvearyTransmission {
	public static class PackageAlvearyTransmission extends AlvearyMachine.AlvearyPackage implements IMachineInformation {
		public PackageAlvearyTransmission() {
			super("transmission", ExtraBeeTexture.AlvearyTransmission.getTexture(), false);
		}

		@Override
		public void createMachine(final Machine machine) {
			new ComponentPowerReceptor(machine, 1000);
			new ComponentTransmission(machine);
		}
	}

	public static class ComponentTransmission extends ComponentBeeModifier implements IBeeModifier, IBeeListener {
		public ComponentTransmission(final Machine machine) {
			super(machine);
		}

		@Override
		public void onUpdate() {
			super.onUpdate();

			int energy = this.getUtil().getPoweredMachine().getEnergyStored();
			if (energy == 0) {
				return;
			}
			final TileExtraBeeAlveary tile = (TileExtraBeeAlveary) this.getMachine().getTileEntity();
			final List<IEnergyStorage> handlers = new LinkedList<>();
			for (final TileEntity alvearyTile : tile.getAlvearyBlocks()) {
				if (alvearyTile != tile && alvearyTile.hasCapability(CapabilityEnergy.ENERGY, EnumFacing.NORTH)) {
					handlers.add(alvearyTile.getCapability(CapabilityEnergy.ENERGY, EnumFacing.NORTH));
				}
			}
			if (handlers.isEmpty()) {
				return;
			}
			final int maxOutput = 500;
			int output = energy / handlers.size();
			if (output > maxOutput) {
				output = maxOutput;
			}
			if (output < 1) {
				output = 1;
			}
			for (final IEnergyStorage handler : handlers) {
				final int recieved = handler.receiveEnergy(output, false);
				this.getUtil().getPoweredMachine().receiveEnergy(-recieved, false);
				energy = this.getUtil().getPoweredMachine().getEnergyStored();
				if (energy <= 0) {
					return;
				}
			}
		}
	}
}
