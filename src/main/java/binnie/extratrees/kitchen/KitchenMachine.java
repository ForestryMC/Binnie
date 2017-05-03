package binnie.extratrees.kitchen;

import binnie.Binnie;
import binnie.core.machines.IMachineType;
import binnie.core.machines.Machine;
import binnie.core.machines.MachinePackage;
import binnie.core.machines.TileEntityMachine;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.ResourceType;
import binnie.extratrees.ExtraTrees;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public enum KitchenMachine implements IMachineType {
	Worktop(null),
	Cupboard(null),
	BottleRack(BottleRack.PackageBottleRack.class);

	protected Class<? extends MachinePackage> cls;

	KitchenMachine(Class<? extends MachinePackage> cls) {
		this.cls = cls;
	}

	@Override
	public Class<? extends MachinePackage> getPackageClass() {
		return cls;
	}

	@Override
	public boolean isActive() {
		return cls != null;
	}

	public ItemStack get(int i) {
		return new ItemStack(ExtraTrees.blockKitchen, i, ordinal());
	}

	public abstract static class PackageKitchenMachine extends MachinePackage {
		protected BinnieResource textureName;

		protected PackageKitchenMachine(String uid, String textureName) {
			super(uid, false);
			this.textureName = Binnie.Resource.getFile(ExtraTrees.instance, ResourceType.Tile, textureName);
		}

		protected PackageKitchenMachine(String uid, BinnieResource textureName) {
			super(uid, false);
			this.textureName = textureName;
		}

		@Override
		public TileEntity createTileEntity() {
			return new TileEntityMachine(this);
		}

		@Override
		public void renderMachine(Machine machine, double x, double y, double z, float partialTick, RenderBlocks renderer) {
			MachineRendererKitchen.instance.renderMachine(machine, textureName, x, y, z, partialTick, renderer);
		}
	}
}
