package binnie.core.machines.storage;

import binnie.core.gui.BinnieCoreGUI;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.texture.BinnieCoreTexture;
import net.minecraft.tileentity.TileEntity;

class StandardCompartment {
	public static class PackageCompartment extends Compartment.PackageCompartment {
		public PackageCompartment() {
			super("compartment", BinnieCoreTexture.Compartment);
		}

		@Override
		public void createMachine(final Machine machine) {
			new ComponentBinnieCoreGUI(machine, BinnieCoreGUI.Compartment);
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
			super("compartment_copper", BinnieCoreTexture.CompartmentCopper);
		}

		@Override
		public void createMachine(final Machine machine) {
			new ComponentBinnieCoreGUI(machine, BinnieCoreGUI.Compartment);
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
			super("compartment_bronze", BinnieCoreTexture.CompartmentBronze);
		}

		@Override
		public void createMachine(final Machine machine) {
			new ComponentBinnieCoreGUI(machine, BinnieCoreGUI.Compartment);
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
			super("compartment_iron", BinnieCoreTexture.CompartmentIron);
		}

		@Override
		public void createMachine(final Machine machine) {
			new ComponentBinnieCoreGUI(machine, BinnieCoreGUI.Compartment);
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
			super("compartment_gold", BinnieCoreTexture.CompartmentGold);
		}

		@Override
		public void createMachine(final Machine machine) {
			new ComponentBinnieCoreGUI(machine, BinnieCoreGUI.Compartment);
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
			super("compartment_diamond", BinnieCoreTexture.CompartmentDiamond);
		}

		@Override
		public void createMachine(final Machine machine) {
			new ComponentBinnieCoreGUI(machine, BinnieCoreGUI.Compartment);
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
