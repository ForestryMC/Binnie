package binnie.genetics.machine.analyser;

import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentChargedSlots;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentInventoryTransfer;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.craftgui.minecraft.IMachineInformation;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.item.GeneticsItems;
import binnie.genetics.machine.ComponentGeneticGUI;
import binnie.genetics.machine.GeneticMachine;
import binnie.genetics.machine.ModuleMachine;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class PackageAnalyser extends GeneticMachine.PackageGeneticBase implements IMachineInformation {
	public PackageAnalyser() {
		super("analyser", GeneticsTexture.Analyser, 9961727, true);
	}

	@Override
	public void createMachine(final Machine machine) {
		new ComponentGeneticGUI(machine, GeneticsGUI.Analyser);
		ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
		for (InventorySlot slot : inventory.addSlotArray(Analyser.SLOT_RESERVE, "input")) {
			slot.setValidator(new SlotValidatorUnanalysed());
			slot.forbidExtraction();
		}
		InventorySlot slotTarget = inventory.addSlot(Analyser.SLOT_TARGET, "analyse");
		slotTarget.setValidator(new SlotValidatorUnanalysed());
		slotTarget.setReadOnly();
		slotTarget.forbidInteraction();
		InventorySlot slotDye = inventory.addSlot(Analyser.SLOT_DYE, "dye");
		slotDye.forbidExtraction();
		slotDye.setValidator(new SlotValidator(ModuleMachine.IconDye) {
			@Override
			public boolean isValid(final ItemStack itemStack) {
				return itemStack.isItemEqual(GeneticsItems.DNADye.get(1));
			}

			@Override
			public String getTooltip() {
				return GeneticsItems.DNADye.get(1).getDisplayName();
			}
		});
		for (InventorySlot slot : inventory.addSlotArray(Analyser.SLOT_FINISHED, "output")) {
			slot.forbidInsertion();
			slot.setReadOnly();
		}
		ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
		transfer.addRestock(Analyser.SLOT_RESERVE, 6, 1);
		transfer.addStorage(6, Analyser.SLOT_FINISHED, new ComponentInventoryTransfer.Condition() {
			@Override
			public boolean fufilled(final ItemStack stack) {
				return Analyser.isAnalysed(stack);
			}
		});
		new ComponentChargedSlots(machine).addCharge(13);
		new ComponentPowerReceptor(machine, 500);
		new AnalyserLogic(machine);
		new AnalyserFX(machine);
	}

	@Override
	public TileEntity createTileEntity() {
		return new TileEntityMachine(this);
	}

	@Override
	public void register() {
	}
}
