package binnie.extratrees.machines;

import binnie.core.machines.Machine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.extratrees.core.ExtraTreeTexture;
import net.minecraft.tileentity.TileEntity;

public class Nursery {
	public static int slotCaterpillar = 0;

	public static class PackageNursery extends ExtraTreeMachine.PackageExtraTreeMachine {
		public PackageNursery() {
			super("nursery", ExtraTreeTexture.Nursery, false);
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

//		@Override
//		public void renderMachine(final Machine machine, final double x, final double y, final double z, final float var8, final RenderBlocks renderer) {
//		}
	}
}
