package binnie.genetics.machine.lab;

import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityTESRMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.genetics.machine.GeneticMachine;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import static binnie.core.Constants.GENETICS_MOD_ID;

public class PackageLabMachine extends GeneticMachine.PackageGeneticBase {
	public PackageLabMachine() {
		super("lab_machine", 16777215);
	}

	@Override
	public void createMachine(final Machine machine) {
		new ComponentGUIHolder(machine);
		new LabFX(machine);
		ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
		inventory.addSlot(0, new ResourceLocation(GENETICS_MOD_ID, "lab_display"));
	}

	@Override
	public TileEntity createTileEntity() {
		return new TileEntityTESRMachine(this);
	}
}
