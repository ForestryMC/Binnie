package binnie.extratrees.machines.nursery;

import net.minecraft.tileentity.TileEntity;

import binnie.core.machines.Machine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.extratrees.machines.ExtraTreeMachine;

public class Nursery {
	public static int slotCaterpillar = 0;

	public static class PackageNursery extends ExtraTreeMachine.PackageExtraTreeMachine {
		public PackageNursery() {
			super("nursery", false);
		}

		@Override
		public void createMachine(final Machine machine) {
			final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
			inventory.addSlot(Nursery.slotCaterpillar, "caterpillar");
		}

		@Override
		public TileEntity createTileEntity() {
			return new TileEntityNursery(this);
		}
	}
}
