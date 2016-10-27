package binnie.extratrees.kitchen;

import binnie.Binnie;
import binnie.core.machines.IMachineType;
import binnie.core.machines.Machine;
import binnie.core.machines.MachinePackage;
import binnie.core.machines.TileEntityMachine;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.ResourceType;
import binnie.extratrees.ExtraTrees;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public enum KitchenMachine implements IMachineType {
	Worktop(null),
	Cupboard(null),
	BottleRack(BottleRack.PackageBottleRack.class);

	Class<? extends MachinePackage> clss;

	KitchenMachine(final Class<? extends MachinePackage> clss) {
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

	public abstract static class PackageKitchenMachine extends MachinePackage {
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
		public void renderMachine(Machine machine, double x, double y, double z, float partialTicks, int destroyStage) {
			MachineRendererKitchen.instance.renderMachine(machine, textureName, x, y, z, partialTicks);
		}
	}
}
