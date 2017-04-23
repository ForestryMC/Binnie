// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.kitchen;

import binnie.core.machines.inventory.ComponentTankContainer;
import binnie.extratrees.core.ExtraTreesGUID;
import binnie.extratrees.machines.ExtraTreeMachine;
import binnie.core.machines.Machine;
import binnie.extratrees.core.ExtraTreeTexture;
import binnie.core.craftgui.minecraft.IMachineInformation;

public class BottleRack
{
	public static class PackageBottleRack extends KitchenMachine.PackageKitchenMachine implements IMachineInformation
	{
		public PackageBottleRack() {
			super("bottleRack", ExtraTreeTexture.lumbermillTexture);
		}

		@Override
		public void createMachine(final Machine machine) {
			new ExtraTreeMachine.ComponentExtraTreeGUI(machine, ExtraTreesGUID.KitchenBottleRack);
			final ComponentTankContainer inventory = new ComponentTankContainer(machine);
			for (int x = 0; x < 36; ++x) {
				inventory.addTank(x, "input", 1000);
				inventory.getTankSlot(x);
			}
		}
	}
}
