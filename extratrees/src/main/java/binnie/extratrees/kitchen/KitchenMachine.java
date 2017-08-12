package binnie.extratrees.kitchen;

import java.util.function.Supplier;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import binnie.core.machines.IMachineType;
import binnie.core.machines.MachinePackage;
import binnie.core.machines.TileEntityMachine;

public enum KitchenMachine implements IMachineType {
	// TODO implement
	Worktop(() -> null),
	Cupboard(() -> null),
	BottleRack(binnie.extratrees.kitchen.BottleRack.PackageBottleRack::new);

	private final Supplier<MachinePackage> supplier;

	KitchenMachine(final Supplier<MachinePackage> supplier) {
		this.supplier = supplier;
	}

	@Override
	public Supplier<MachinePackage> getSupplier() {
		return supplier;
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
