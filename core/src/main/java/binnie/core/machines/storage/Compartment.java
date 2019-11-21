package binnie.core.machines.storage;

import binnie.core.BinnieCore;
import binnie.core.machines.IMachineType;
import binnie.core.machines.MachinePackage;
import binnie.core.machines.TileEntityMachine;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import java.util.function.Supplier;

enum Compartment implements IMachineType {
	Compartment(StandardCompartment.PackageCompartment::new),
	CompartmentCopper(StandardCompartment.PackageCompartmentCopper::new),
	CompartmentBronze(StandardCompartment.PackageCompartmentBronze::new),
	CompartmentIron(StandardCompartment.PackageCompartmentIron::new),
	CompartmentGold(StandardCompartment.PackageCompartmentGold::new),
	CompartmentDiamond(StandardCompartment.PackageCompartmentDiamond::new);

	private final Supplier<MachinePackage> supplier;

	Compartment(final Supplier<MachinePackage> supplier) {
		this.supplier = supplier;
	}

	@Override
	public Supplier<MachinePackage> getSupplier() {
		return supplier;
	}

	public ItemStack get(final int i) {
		return new ItemStack(BinnieCore.getPackageCompartment().getBlock(), i, this.ordinal());
	}

	public abstract static class PackageCompartment extends MachinePackage {
		protected PackageCompartment(final String uid) {
			super(uid);
		}

		@Override
		public TileEntity createTileEntity() {
			return new TileEntityMachine(this);
		}

	}
}
