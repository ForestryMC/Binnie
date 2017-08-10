package binnie.core.machines.storage;

import net.minecraft.tileentity.TileEntity;

import binnie.core.gui.BinnieCoreGUI;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;

class StandardCompartment {
	public static class PackageCompartment extends Compartment.PackageCompartment {
		public PackageCompartment() {
			super("compartment");
		}

		@Override
		public void createMachine(final Machine machine) {
			new ComponentBinnieCoreGUI(machine, BinnieCoreGUI.COMPARTMENT);
			new ComponentCompartmentInventory(machine, 4, 25);
		}

		@Override
		public TileEntity createTileEntity() {
			return new TileEntityMachine(this);
		}

		@Override
		public void register() {
		}
	}

	public static class PackageCompartmentCopper extends Compartment.PackageCompartment {
		public PackageCompartmentCopper() {
			super("compartment_copper");
		}

		@Override
		public void createMachine(final Machine machine) {
			new ComponentBinnieCoreGUI(machine, BinnieCoreGUI.COMPARTMENT);
			new ComponentCompartmentInventory(machine, 6, 25);
		}

		@Override
		public TileEntity createTileEntity() {
			return new TileEntityMachine(this);
		}

		@Override
		public void register() {
		}
	}

	public static class PackageCompartmentBronze extends Compartment.PackageCompartment {
		public PackageCompartmentBronze() {
			super("compartment_bronze");
		}

		@Override
		public void createMachine(final Machine machine) {
			new ComponentBinnieCoreGUI(machine, BinnieCoreGUI.COMPARTMENT);
			new ComponentCompartmentInventory(machine, 8, 25);
		}

		@Override
		public TileEntity createTileEntity() {
			return new TileEntityMachine(this);
		}

		@Override
		public void register() {
		}
	}

	public static class PackageCompartmentIron extends Compartment.PackageCompartment {
		public PackageCompartmentIron() {
			super("compartment_iron");
		}

		@Override
		public void createMachine(final Machine machine) {
			new ComponentBinnieCoreGUI(machine, BinnieCoreGUI.COMPARTMENT);
			new ComponentCompartmentInventory(machine, 4, 50);
		}

		@Override
		public TileEntity createTileEntity() {
			return new TileEntityMachine(this);
		}

		@Override
		public void register() {
		}
	}

	public static class PackageCompartmentGold extends Compartment.PackageCompartment {
		public PackageCompartmentGold() {
			super("compartment_gold");
		}

		@Override
		public void createMachine(final Machine machine) {
			new ComponentBinnieCoreGUI(machine, BinnieCoreGUI.COMPARTMENT);
			new ComponentCompartmentInventory(machine, 6, 50);
		}

		@Override
		public TileEntity createTileEntity() {
			return new TileEntityMachine(this);
		}

		@Override
		public void register() {
		}
	}

	public static class PackageCompartmentDiamond extends Compartment.PackageCompartment {
		public PackageCompartmentDiamond() {
			super("compartment_diamond");
		}

		@Override
		public void createMachine(final Machine machine) {
			new ComponentBinnieCoreGUI(machine, BinnieCoreGUI.COMPARTMENT);
			new ComponentCompartmentInventory(machine, 8, 50);
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
