package binnie.genetics.machine.inoculator;

import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentInventoryTransfer;
import binnie.core.machines.inventory.ComponentTankContainer;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.inventory.Validator;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.craftgui.minecraft.IMachineInformation;
import binnie.genetics.api.IItemSerum;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.genetics.Engineering;
import binnie.genetics.item.GeneticLiquid;
import binnie.genetics.machine.ComponentGeneticGUI;
import binnie.genetics.machine.GeneticMachine;
import binnie.genetics.machine.ModuleMachine;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;

public class PackageInoculator extends GeneticMachine.PackageGeneticBase implements IMachineInformation {
	public PackageInoculator() {
		super("inoculator", GeneticsTexture.Inoculator, 14819893, true);
	}

	@Override
	public void createMachine(final Machine machine) {
		new ComponentGeneticGUI(machine, GeneticsGUI.Inoculator);
		final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
		InventorySlot slotSerumVial = inventory.addSlot(Inoculator.SLOT_SERUM_VIAL, "serum.active");
		slotSerumVial.forbidInteraction();
		slotSerumVial.setReadOnly();
		final SlotValidator serumValid = new SlotValidator(ModuleMachine.spriteSerum) {
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
		for (InventorySlot slot : inventory.addSlotArray(Inoculator.SLOT_SERUM_RESERVE, "serum.input")) {
			slot.setValidator(serumValid);
			slot.forbidExtraction();
		}
		for (InventorySlot slot : inventory.addSlotArray(Inoculator.SLOT_SERUM_EXPENDED, "serum.output")) {
			slot.setValidator(serumValid);
			slot.setReadOnly();
		}
		for (InventorySlot slot : inventory.addSlotArray(Inoculator.SLOT_RESERVE, "input")) {
			slot.forbidExtraction();
			slot.setValidator(new ValidatorIndividualInoculate());
		}
		InventorySlot slotTarget = inventory.addSlot(Inoculator.SLOT_TARGET, "process");
		slotTarget.setValidator(new ValidatorIndividualInoculate());
		slotTarget.setReadOnly();
		slotTarget.forbidInteraction();
		for (final InventorySlot slot : inventory.addSlotArray(Inoculator.SLOT_FINISHED, "output")) {
			slot.setReadOnly();
			slot.forbidInsertion();
			slot.setValidator(new ValidatorIndividualInoculate());
		}
		final ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
		transfer.addRestock(Inoculator.SLOT_RESERVE, 9, 1);
		transfer.addRestock(Inoculator.SLOT_SERUM_RESERVE, 0);
		transfer.addStorage(Inoculator.SLOT_SERUM_VIAL, Inoculator.SLOT_SERUM_EXPENDED, new ComponentInventoryTransfer.Condition() {
			@Override
			public boolean fufilled(final ItemStack stack) {
				return Engineering.getCharges(stack) == 0;
			}
		});
		transfer.addStorage(Inoculator.SLOT_TARGET, Inoculator.SLOT_FINISHED, new ComponentInventoryTransfer.Condition() {
			@Override
			public boolean fufilled(final ItemStack stack) {
				return stack != null && this.transfer.getMachine().getMachineUtil().getStack(Inoculator.SLOT_SERUM_VIAL) != null && this.transfer.getMachine().getInterface(InoculatorLogic.class).isValidSerum() != null;
			}
		});
		new ComponentPowerReceptor(machine, 15000);
		new InoculatorLogic(machine);
		new InoculatorFX(machine);
		new ComponentTankContainer(machine).addTank(Inoculator.TANK_VEKTOR, "input", 1000).setValidator(new Validator<FluidStack>() {
			@Override
			public boolean isValid(final FluidStack object) {
				return GeneticLiquid.BacteriaVector.get(1).isFluidEqual(object);
			}

			@Override
			public String getTooltip() {
				return GeneticLiquid.BacteriaVector.getName();
			}
		});
	}

	@Override
	public TileEntity createTileEntity() {
		return new TileEntityMachine(this);
	}

	@Override
	public void register() {
	}
}
