// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.kitchen;

import net.minecraft.client.renderer.RenderBlocks;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import net.minecraft.tileentity.TileEntity;
import binnie.core.resource.ResourceType;
import binnie.Binnie;
import binnie.core.resource.BinnieResource;
import binnie.extratrees.ExtraTrees;
import net.minecraft.item.ItemStack;
import binnie.core.machines.MachinePackage;
import binnie.core.machines.IMachineType;

public enum KitchenMachine implements IMachineType
{
	Worktop((Class<? extends MachinePackage>) null),
	Cupboard((Class<? extends MachinePackage>) null),
	BottleRack(BottleRack.PackageBottleRack.class);

	Class<? extends MachinePackage> clss;

	private KitchenMachine(final Class<? extends MachinePackage> clss) {
		this.clss = clss;
	}

	@Override
	public Class<? extends MachinePackage> getPackageClass() {
		return this.clss;
	}

	@Override
	public boolean isActive() {
		return this.clss != null;
	}

	public ItemStack get(final int i) {
		return new ItemStack(ExtraTrees.blockKitchen, i, this.ordinal());
	}

	public abstract static class PackageKitchenMachine extends MachinePackage
	{
		BinnieResource textureName;

		protected PackageKitchenMachine(final String uid, final String textureName) {
			super(uid, false);
			this.textureName = Binnie.Resource.getFile(ExtraTrees.instance, ResourceType.Tile, textureName);
		}

		protected PackageKitchenMachine(final String uid, final BinnieResource textureName) {
			super(uid, false);
			this.textureName = textureName;
		}

		@Override
		public TileEntity createTileEntity() {
			return new TileEntityMachine(this);
		}

		@Override
		public void register() {
		}

		@Override
		public void renderMachine(final Machine machine, final double x, final double y, final double z, final float var8, final RenderBlocks renderer) {
			MachineRendererKitchen.instance.renderMachine(machine, this.textureName, x, y, z, var8, renderer);
		}
	}
}
