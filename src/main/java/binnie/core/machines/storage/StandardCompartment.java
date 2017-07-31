package binnie.core.machines.storage;

import net.minecraft.tileentity.TileEntity;

import binnie.core.gui.BinnieCoreGUI;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.texture.BinnieCoreTexture;

class StandardCompartment {
	public static class PackageCompartment extends Compartment.PackageCompartment {
		public PackageCompartment() {
			super("compartment", BinnieCoreTexture.COMPARTMENT);
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
			super("compartment_copper", BinnieCoreTexture.COMPARTMENT_COPPER);
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
			super("compartment_bronze", BinnieCoreTexture.COMPARTMENT_BRONZE);
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
			super("compartment_iron", BinnieCoreTexture.COMPARTMENT_IRON);
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
			super("compartment_gold", BinnieCoreTexture.COMPARTMENT_GOLD);
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
			super("compartment_diamond", BinnieCoreTexture.COMPARTMENT_DIAMOND);
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
