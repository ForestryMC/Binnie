package binnie.core.machines.storage;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import binnie.core.BinnieCore;
import binnie.core.machines.IMachineType;
import binnie.core.machines.MachinePackage;
import binnie.core.machines.TileEntityMachine;

enum Compartment implements IMachineType {
	Compartment(StandardCompartment.PackageCompartment.class),
	CompartmentCopper(StandardCompartment.PackageCompartmentCopper.class),
	CompartmentBronze(StandardCompartment.PackageCompartmentBronze.class),
	CompartmentIron(StandardCompartment.PackageCompartmentIron.class),
	CompartmentGold(StandardCompartment.PackageCompartmentGold.class),
	CompartmentDiamond(StandardCompartment.PackageCompartmentDiamond.class);

	Class<? extends MachinePackage> clss;

	Compartment(final Class<? extends MachinePackage> clss) {
		this.clss = clss;
	}

	@Override
	public Class<? extends MachinePackage> getPackageClass() {
		return this.clss;
	}

	@Override
	public boolean isActive() {
		return true;
	}

	public ItemStack get(final int i) {
		return new ItemStack(BinnieCore.getPackageCompartment().getBlock(), i, this.ordinal());
	}

	public abstract static class PackageCompartment extends MachinePackage {
		protected PackageCompartment(final String uid) {
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
