package binnie.extratrees.kitchen;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import binnie.core.machines.IMachineType;
import binnie.core.machines.MachinePackage;
import binnie.core.machines.TileEntityMachine;

public enum KitchenMachine implements IMachineType {
	// TODO implement
	Worktop(null),
	Cupboard(null),
	BottleRack(BottleRack.PackageBottleRack.class);

	@Nullable
	Class<? extends MachinePackage> clss;

	KitchenMachine(@Nullable final Class<? extends MachinePackage> clss) {
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
		return new ItemStack(ModuleKitchen.blockKitchen, i, this.ordinal());
	}

	public abstract static class PackageKitchenMachine extends MachinePackage {

		protected PackageKitchenMachine(final String uid) {
			super(uid, false);
		}

		@Override
		public TileEntity createTileEntity() {
			return new TileEntityMachine(this);
		}

		@Override
		public void register() {
		}
	}
}
