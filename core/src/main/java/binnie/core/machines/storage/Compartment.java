package binnie.core.machines.storage;

import java.util.function.Supplier;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import binnie.core.machines.IMachineType;
import binnie.core.machines.MachinePackage;
import binnie.core.machines.TileEntityMachine;
import binnie.core.modules.features.StorageMachines;

public enum Compartment implements IMachineType {
	COMPARTMENT(StandardCompartment.PackageCompartment::new),
	COMPARTMENT_COPPER(StandardCompartment.PackageCompartmentCopper::new),
	COMPARTMENT_BRONZE(StandardCompartment.PackageCompartmentBronze::new),
	COMPARTMENT_IRON(StandardCompartment.PackageCompartmentIron::new),
	COMPARTMENT_GOLD(StandardCompartment.PackageCompartmentGold::new),
	COMPARTMENT_DIAMOND(StandardCompartment.PackageCompartmentDiamond::new);

	private final Supplier<MachinePackage> supplier;

	Compartment(Supplier<MachinePackage> supplier) {
		this.supplier = supplier;
	}

	@Override
	public Supplier<MachinePackage> getSupplier() {
		return supplier;
	}

	public ItemStack get(int i) {
		return new ItemStack(StorageMachines.COMPARTMENT.block(), i, this.ordinal());
	}

	public abstract static class PackageCompartment extends MachinePackage {
		protected PackageCompartment(String uid) {
			super(uid);
		}

		@Override
		public TileEntity createTileEntity() {
			return new TileEntityMachine(this);
		}

	}
}
