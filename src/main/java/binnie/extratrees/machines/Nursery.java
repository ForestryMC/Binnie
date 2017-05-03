package binnie.extratrees.machines;

import binnie.core.machines.Machine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.extratrees.core.ExtraTreeTexture;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;

public class Nursery {
	public static int slotCaterpillar = 0;

	public static class PackageNursery extends ExtraTreeMachine.PackageExtraTreeMachine {
		public PackageNursery() {
			super("nursery", ExtraTreeTexture.Nursery.getTexture(), false);
		}

		@Override
		public void createMachine(Machine machine) {
			ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
			inventory.addSlot(Nursery.slotCaterpillar, "caterpillar");
		}

		@Override
		public TileEntity createTileEntity() {
			return new TileEntityNursery(this);
		}

		@Override
		public void renderMachine(Machine machine, double x, double y, double z, float partialTick, RenderBlocks renderer) {
			// ignored
		}
	}
}
