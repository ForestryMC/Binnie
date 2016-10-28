package binnie.genetics.machine.splicer;

import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentInventoryTransfer;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.craftgui.minecraft.IMachineInformation;
import binnie.genetics.api.IItemSerum;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.genetics.Engineering;
import binnie.genetics.machine.AdvGeneticMachine;
import binnie.genetics.machine.ComponentGeneticGUI;
import binnie.genetics.machine.ModuleMachine;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class PackageSplicer extends AdvGeneticMachine.PackageAdvGeneticBase implements IMachineInformation {
	public PackageSplicer() {
		super("splicer", GeneticsTexture.Splicer, 14819893, true);
	}

	@Override
	public void createMachine(final Machine machine) {
		new ComponentGeneticGUI(machine, GeneticsGUI.Splicer);
		final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
		InventorySlot slotSerumVial = inventory.addSlot(Splicer.SLOT_SERUM_VIAL, "serum.active");
		slotSerumVial.forbidInteraction();
		slotSerumVial.setReadOnly();
		final SlotValidator serumValid = new SlotValidator(ModuleMachine.iconSerum) {
			@Override
			public boolean isValid(final ItemStack itemStack) {
				return itemStack.getItem() instanceof IItemSerum;
			}

			@Override
			public String getTooltip() {
				return "Serum Vials & Arrays";
			}
		};
		slotSerumVial.setValidator(serumValid);
		for (InventorySlot slot : inventory.addSlotArray(Splicer.SLOT_SERUM_RESERVE, "serum.input")) {
			slot.setValidator(serumValid);
			slot.forbidExtraction();
		}
		for (InventorySlot slot : inventory.addSlotArray(Splicer.SLOT_SERUM_EXPENDED, "serum.output")) {
			slot.setValidator(serumValid);
			slot.setReadOnly();
		}
		for (InventorySlot slot : inventory.addSlotArray(Splicer.SLOT_RESERVE, "input")) {
			slot.forbidExtraction();
			slot.setValidator(new ValidatorIndividualInoculate());
		}
		InventorySlot slotTarget = inventory.addSlot(Splicer.SLOT_TARGET, "process");
		slotTarget.setValidator(new ValidatorIndividualInoculate());
		slotTarget.setReadOnly();
		slotTarget.forbidInteraction();
		for (InventorySlot slot : inventory.addSlotArray(Splicer.SLOT_FINISHED, "output")) {
			slot.setReadOnly();
			slot.forbidInsertion();
			slot.setValidator(new ValidatorIndividualInoculate());
		}
		final ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
		transfer.addRestock(Splicer.SLOT_RESERVE, 9, 1);
		transfer.addRestock(Splicer.SLOT_SERUM_RESERVE, 0);
		transfer.addStorage(Splicer.SLOT_SERUM_VIAL, Splicer.SLOT_SERUM_EXPENDED, new ComponentInventoryTransfer.Condition() {
			@Override
			public boolean fufilled(final ItemStack stack) {
				return Engineering.getCharges(stack) == 0;
			}
		});
		transfer.addStorage(Splicer.SLOT_TARGET, Splicer.SLOT_FINISHED, new ComponentInventoryTransfer.Condition() {
			@Override
			public boolean fufilled(final ItemStack stack) {
				return stack != null && this.transfer.getMachine().getMachineUtil().getStack(0) != null && this.transfer.getMachine().getInterface(SplicerLogic.class).isValidSerum() != null;
			}
		});
		new ComponentPowerReceptor(machine, 20000);
		new SplicerLogic(machine);
		new SplicerFX(machine);
	}

	@Override
	public TileEntity createTileEntity() {
		return new TileEntityMachine(this);
	}

	@Override
	public void register() {
	}
}
