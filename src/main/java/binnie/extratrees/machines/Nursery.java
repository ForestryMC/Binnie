// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.machines;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.Machine;
import binnie.extratrees.core.ExtraTreeTexture;

public class Nursery
{
	public static int slotCaterpillar;

	static {
		Nursery.slotCaterpillar = 0;
	}

	public static class PackageNursery extends ExtraTreeMachine.PackageExtraTreeMachine
	{
		public PackageNursery() {
			super("nursery", ExtraTreeTexture.Nursery.getTexture(), false);
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

		@Override
		public void renderMachine(final Machine machine, final double x, final double y, final double z, final float partialTick, final RenderBlocks renderer) {
		}
	}
}
