// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extrabees.apiary.machine;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.common.util.ForgeDirection;

import cofh.api.energy.IEnergyHandler;

import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;
import binnie.core.machines.Machine;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.core.craftgui.minecraft.IMachineInformation;
import binnie.extrabees.apiary.ComponentBeeModifier;
import binnie.extrabees.apiary.TileExtraBeeAlveary;
import binnie.extrabees.core.ExtraBeeTexture;

public class AlvearyTransmission
{
	public static class PackageAlvearyTransmission extends AlvearyMachine.AlvearyPackage implements IMachineInformation
	{
		public PackageAlvearyTransmission() {
			super("transmission", ExtraBeeTexture.AlvearyTransmission.getTexture(), false);
		}

		@Override
		public void createMachine(final Machine machine) {
			new ComponentPowerReceptor(machine, 1000);
			new ComponentTransmission(machine);
		}
	}

	public static class ComponentTransmission extends ComponentBeeModifier implements IBeeModifier, IBeeListener
	{
		public ComponentTransmission(final Machine machine) {
			super(machine);
		}

		@Override
		public void onUpdate() {
			super.onUpdate();
			int energy = this.getUtil().getPoweredMachine().getEnergyStored(ForgeDirection.NORTH);
			if (energy == 0) {
				return;
			}
			final TileExtraBeeAlveary tile = (TileExtraBeeAlveary) this.getMachine().getTileEntity();
			final List<IEnergyHandler> handlers = new ArrayList<IEnergyHandler>();
			for (final TileEntity alvearyTile : tile.getAlvearyBlocks()) {
				if (alvearyTile instanceof IEnergyHandler && alvearyTile != tile) {
					handlers.add((IEnergyHandler) alvearyTile);
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
			for (final IEnergyHandler handler : handlers) {
				final int recieved = handler.receiveEnergy(ForgeDirection.NORTH, output, false);
				this.getUtil().getPoweredMachine().receiveEnergy(ForgeDirection.NORTH, -recieved, false);
				energy = this.getUtil().getPoweredMachine().getEnergyStored(ForgeDirection.NORTH);
				if (energy <= 0) {
					return;
				}
			}
		}
	}
}
