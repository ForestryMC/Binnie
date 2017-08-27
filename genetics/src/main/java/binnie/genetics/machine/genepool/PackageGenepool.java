package binnie.genetics.machine.genepool;

import binnie.genetics.machine.EthanolTankValidator;
import net.minecraft.tileentity.TileEntity;

import binnie.core.gui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentChargedSlots;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentInventoryTransfer;
import binnie.core.machines.inventory.ComponentTankContainer;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.inventory.TankSlot;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.item.GeneticsItems;
import binnie.genetics.machine.ComponentGeneticGUI;
import binnie.genetics.machine.GeneticMachine;
import binnie.genetics.machine.ModuleMachine;

public class PackageGenepool extends GeneticMachine.PackageGeneticBase implements IMachineInformation {
	public PackageGenepool() {
		super("genepool", 12661942);
	}

	@Override
	public void createMachine(final Machine machine) {
		new ComponentGeneticGUI(machine, GeneticsGUI.GENEPOOL);
		ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
		InventorySlot slotEnzyme = inventory.addSlot(Genepool.SLOT_ENZYME, "enzyme");
		slotEnzyme.setValidator(new SlotValidator.Item(GeneticsItems.Enzyme.get(1), ModuleMachine.getSpriteEnzyme()));
		slotEnzyme.forbidExtraction();
		InventorySlot slotProcess = inventory.addSlot(Genepool.SLOT_BEE, "process");
		slotProcess.setValidator(new SlotValidator.Individual());
		slotProcess.setReadOnly();
		slotProcess.forbidExtraction();
		for (InventorySlot slot : inventory.addSlotArray(Genepool.SLOT_RESERVE, "input")) {
			slot.setValidator(new SlotValidator.Individual());
			slot.forbidExtraction();
		}
		ComponentTankContainer tanks = new ComponentTankContainer(machine);
		TankSlot tankDNA = tanks.addTank(Genepool.TANK_DNA, "output", 2000);
		tankDNA.setReadOnly();
		TankSlot tankEthanol = tanks.addTank(Genepool.TANK_ETHANOL, "input", 1000);
		tankEthanol.forbidExtraction();
		tankEthanol.setValidator(new EthanolTankValidator());
		ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
		transfer.addRestock(Genepool.SLOT_RESERVE, Genepool.SLOT_BEE, 1);
		new ComponentPowerReceptor(machine, 1600);
		new GenepoolLogic(machine);
		ComponentChargedSlots chargedSlots = new ComponentChargedSlots(machine);
		chargedSlots.addCharge(Genepool.SLOT_ENZYME);
		new GenepoolFX(machine);
	}

	@Override
	public TileEntity createTileEntity() {
		return new TileEntityMachine(this);
	}

}
