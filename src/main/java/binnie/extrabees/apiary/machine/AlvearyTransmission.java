package binnie.extrabees.apiary.machine;

import binnie.core.craftgui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.extrabees.apiary.ComponentBeeModifier;
import binnie.extrabees.apiary.TileExtraBeeAlveary;
import binnie.extrabees.core.ExtraBeeTexture;
import cofh.api.energy.IEnergyHandler;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

public class AlvearyTransmission {
	public static class PackageAlvearyTransmission extends AlvearyMachine.AlvearyPackage implements IMachineInformation {
		public PackageAlvearyTransmission() {
			super("transmission", ExtraBeeTexture.AlvearyTransmission.getTexture(), false);
		}

		@Override
		public void createMachine(Machine machine) {
			new ComponentPowerReceptor(machine, 1000);
			new ComponentTransmission(machine);
		}
	}

	public static class ComponentTransmission extends ComponentBeeModifier implements IBeeModifier, IBeeListener {
		public ComponentTransmission(Machine machine) {
			super(machine);
		}

		@Override
		public void onUpdate() {
			super.onUpdate();
			int energy = getUtil().getPoweredMachine().getEnergyStored(ForgeDirection.NORTH);
			if (energy == 0) {
				return;
			}

			TileExtraBeeAlveary tile = (TileExtraBeeAlveary) getMachine().getTileEntity();
			List<IEnergyHandler> handlers = new ArrayList<IEnergyHandler>();
			for (TileEntity alvearyTile : tile.getAlvearyBlocks()) {
				if (alvearyTile instanceof IEnergyHandler && alvearyTile != tile) {
					handlers.add((IEnergyHandler) alvearyTile);
				}
			}

			if (handlers.isEmpty()) {
				return;
			}

			int maxOutput = 500;
			int output = energy / handlers.size();
			if (output > maxOutput) {
				output = maxOutput;
			} else if (output < 1) {
				output = 1;
			}

			for (IEnergyHandler handler : handlers) {
				int recieved = handler.receiveEnergy(ForgeDirection.NORTH, output, false);
				getUtil().getPoweredMachine().receiveEnergy(ForgeDirection.NORTH, -recieved, false);
				energy = getUtil().getPoweredMachine().getEnergyStored(ForgeDirection.NORTH);
				if (energy <= 0) {
					return;
				}
			}
		}
	}
}
