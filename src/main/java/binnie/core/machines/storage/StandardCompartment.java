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
        public void createMachine(Machine machine) {
            new ComponentBinnieCoreGUI(machine, BinnieCoreGUI.Compartment);
            new ComponentCompartmentInventory(machine, 4, 25);
        }

        @Override
        public TileEntity createTileEntity() {
            return new TileEntityMachine(this);
        }
    }

    public static class PackageCompartmentCopper extends Compartment.PackageCompartment {
        public PackageCompartmentCopper() {
            super("compartmentCopper", BinnieCoreTexture.CompartmentCopper);
        }

        @Override
        public void createMachine(Machine machine) {
            new ComponentBinnieCoreGUI(machine, BinnieCoreGUI.Compartment);
            new ComponentCompartmentInventory(machine, 6, 25);
        }

        @Override
        public TileEntity createTileEntity() {
            return new TileEntityMachine(this);
        }
    }

    public static class PackageCompartmentBronze extends Compartment.PackageCompartment {
        public PackageCompartmentBronze() {
            super("compartmentBronze", BinnieCoreTexture.CompartmentBronze);
        }

        @Override
        public void createMachine(Machine machine) {
            new ComponentBinnieCoreGUI(machine, BinnieCoreGUI.Compartment);
            new ComponentCompartmentInventory(machine, 8, 25);
        }

        @Override
        public TileEntity createTileEntity() {
            return new TileEntityMachine(this);
        }
    }

    public static class PackageCompartmentIron extends Compartment.PackageCompartment {
        public PackageCompartmentIron() {
            super("compartmentIron", BinnieCoreTexture.CompartmentIron);
        }

        @Override
        public void createMachine(Machine machine) {
            new ComponentBinnieCoreGUI(machine, BinnieCoreGUI.Compartment);
            new ComponentCompartmentInventory(machine, 4, 50);
        }

        @Override
        public TileEntity createTileEntity() {
            return new TileEntityMachine(this);
        }
    }

    public static class PackageCompartmentGold extends Compartment.PackageCompartment {
        public PackageCompartmentGold() {
            super("compartmentGold", BinnieCoreTexture.CompartmentGold);
        }

        @Override
        public void createMachine(Machine machine) {
            new ComponentBinnieCoreGUI(machine, BinnieCoreGUI.Compartment);
            new ComponentCompartmentInventory(machine, 6, 50);
        }

        @Override
        public TileEntity createTileEntity() {
            return new TileEntityMachine(this);
        }
    }

    public static class PackageCompartmentDiamond extends Compartment.PackageCompartment {
        public PackageCompartmentDiamond() {
            super("compartmentDiamond", BinnieCoreTexture.CompartmentDiamond);
        }

        @Override
        public void createMachine(Machine machine) {
            new ComponentBinnieCoreGUI(machine, BinnieCoreGUI.Compartment);
            new ComponentCompartmentInventory(machine, 8, 50);
        }

        @Override
        public TileEntity createTileEntity() {
            return new TileEntityMachine(this);
        }
    }
}
