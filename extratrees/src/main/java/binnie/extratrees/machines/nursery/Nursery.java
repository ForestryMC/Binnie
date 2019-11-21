package binnie.extratrees.machines.nursery;

import binnie.core.machines.Machine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.extratrees.machines.ExtraTreeMachine;
import net.minecraft.tileentity.TileEntity;

public class Nursery {
	public static final int SLOT_CATERPILLAR = 0;

	public static class PackageNursery extends ExtraTreeMachine.PackageExtraTreeMachine {
		public PackageNursery() {
			super("nursery");
		}

		@Override
		public void createMachine(final Machine machine) {
			final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
			inventory.addSlot(Nursery.SLOT_CATERPILLAR, getSlotRL("caterpillar"));
		}

		@Override
		public TileEntity createTileEntity() {
			return new TileEntityNursery(this);
		}
	}
}
