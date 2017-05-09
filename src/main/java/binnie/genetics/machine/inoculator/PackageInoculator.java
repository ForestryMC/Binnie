package binnie.genetics.machine.inoculator;

import binnie.core.craftgui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentInventoryTransfer;
import binnie.core.machines.inventory.ComponentTankContainer;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.inventory.Validator;
import binnie.core.machines.power.ComponentPowerReceptor;
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
		super("inoculator", GeneticsTexture.Inoculator, 0xe22235, true);
	}

	@Override
	public void createMachine(Machine machine) {
		new ComponentGeneticGUI(machine, GeneticsGUI.Inoculator);
		ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
		InventorySlot slotSerumVial = inventory.addSlot(Inoculator.SLOT_SERUM_VIAL, "serum.active");
		slotSerumVial.forbidInteraction();
		slotSerumVial.setReadOnly();

		SlotValidator serumValid = new SlotValidator(ModuleMachine.IconSerum) {
			@Override
			public boolean isValid(ItemStack itemStack) {
				return itemStack.getItem() instanceof IItemSerum;
			}

			@Override
			public String getTooltip() {
				return "Serum Vials & Arrays";
			}
		};

		slotSerumVial.setValidator(serumValid);

		inventory.addSlotArray(Inoculator.SLOT_SERUM_RESERVE, "serum.input");
		for (InventorySlot slot : inventory.getSlots(Inoculator.SLOT_SERUM_RESERVE)) {
			slot.setValidator(serumValid);
			slot.forbidExtraction();
		}

		inventory.addSlotArray(Inoculator.SLOT_SERUM_EXPENDED, "serum.output");
		for (InventorySlot slot : inventory.getSlots(Inoculator.SLOT_SERUM_EXPENDED)) {
			slot.setValidator(serumValid);
			slot.setReadOnly();
		}

		inventory.addSlotArray(Inoculator.SLOT_RESERVE, "input");
		for (InventorySlot slot : inventory.getSlots(Inoculator.SLOT_RESERVE)) {
			slot.forbidExtraction();
			slot.setValidator(new ValidatorIndividualInoculate());
		}

		inventory.addSlot(Inoculator.SLOT_TARGET, "process");
		inventory.getSlot(Inoculator.SLOT_TARGET).setValidator(new ValidatorIndividualInoculate());
		inventory.getSlot(Inoculator.SLOT_TARGET).setReadOnly();
		inventory.getSlot(Inoculator.SLOT_TARGET).forbidInteraction();
		inventory.addSlotArray(Inoculator.SLOT_FINISHED, "output");
		for (InventorySlot slot : inventory.getSlots(Inoculator.SLOT_FINISHED)) {
			slot.setReadOnly();
			slot.forbidInsertion();
			slot.setValidator(new ValidatorIndividualInoculate());
		}

		ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
		transfer.addRestock(Inoculator.SLOT_RESERVE, Inoculator.SLOT_TARGET, 1);
		transfer.addRestock(Inoculator.SLOT_SERUM_RESERVE, Inoculator.SLOT_SERUM_VIAL);
		transfer.addStorage(0, Inoculator.SLOT_SERUM_EXPENDED, new ComponentInventoryTransfer.Condition() {
			@Override
			public boolean fufilled(ItemStack stack) {
				return Engineering.getCharges(stack) == 0;
			}
		});

		transfer.addStorage(9, Inoculator.SLOT_FINISHED, new ComponentInventoryTransfer.Condition() {
			@Override
			public boolean fufilled(ItemStack stack) {
				return stack != null
						&& transfer.getMachine().getMachineUtil().getStack(Inoculator.SLOT_SERUM_VIAL) != null
						&& transfer.getMachine().getInterface(ComponentInoculatorLogic.class).isValidSerum() != null;
			}
		});

		new ComponentPowerReceptor(machine, 15000);
		new ComponentInoculatorLogic(machine);
		new ComponentInoculatorFX(machine);
		new ComponentTankContainer(machine)
			.addTank(Inoculator.TANK_VECTOR, "input", Inoculator.TANK_CAPACITY)
			.setValidator(new Validator<FluidStack>() {
				@Override
				public boolean isValid(FluidStack object) {
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
}
