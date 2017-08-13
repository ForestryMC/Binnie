package binnie.extratrees.kitchen;

import binnie.core.gui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.ComponentTankContainer;
import binnie.extratrees.gui.ExtraTreesGUID;
import binnie.extratrees.machines.ExtraTreeMachine;

public class BottleRack {
	public static class PackageBottleRack extends KitchenMachine.PackageKitchenMachine implements IMachineInformation {
		public PackageBottleRack() {
			super("bottleRack");
		}

		@Override
		public void createMachine(final Machine machine) {
			new ExtraTreeMachine.ComponentExtraTreeGUI(machine, ExtraTreesGUID.KITCHEN_BOTTLE_RACK);
			final ComponentTankContainer inventory = new ComponentTankContainer(machine);
			for (int x = 0; x < 36; ++x) {
				inventory.addTank(x, "input", 1000);
				inventory.getTankSlot(x);
			}
		}
	}
}
