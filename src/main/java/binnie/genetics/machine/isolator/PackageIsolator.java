package binnie.genetics.machine.isolator;

import binnie.core.craftgui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentChargedSlots;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentInventoryTransfer;
import binnie.core.machines.inventory.ComponentTankContainer;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.inventory.TankValidator;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.item.GeneticsItems;
import binnie.genetics.machine.ComponentGeneticGUI;
import binnie.genetics.machine.GeneticMachine;
import binnie.genetics.machine.ModuleMachine;
import forestry.core.fluids.Fluids;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.Objects;

public class PackageIsolator extends GeneticMachine.PackageGeneticBase implements IMachineInformation {
	public PackageIsolator() {
		super("isolator", GeneticsTexture.Isolator, 16740111, true);
	}

	@Override
	public void createMachine(final Machine machine) {
		new ComponentGeneticGUI(machine, GeneticsGUI.Isolator);
		final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
		InventorySlot slotEnzyme = inventory.addSlot(Isolator.SLOT_ENZYME, "enzyme");
		slotEnzyme.setValidator(new SlotValidator.Item(GeneticsItems.Enzyme.get(1), ModuleMachine.spriteEnzyme));
		slotEnzyme.forbidExtraction();
		InventorySlot slotSequencer = inventory.addSlot(Isolator.SLOT_SEQUENCER_VIAL, "sequencervial");
		slotSequencer.setValidator(new SlotValidator.Item(GeneticsItems.EmptySequencer.get(1), ModuleMachine.spriteSequencer));
		slotSequencer.forbidExtraction();
		for (InventorySlot slot : inventory.addSlotArray(Isolator.SLOT_RESERVE, "input")) {
			slot.setValidator(new SlotValidator.Individual());
			slot.forbidExtraction();
		}
		InventorySlot slotTarget = inventory.addSlot(Isolator.SLOT_TARGET, "process");
		slotTarget.setValidator(new SlotValidator.Individual());
		slotTarget.setReadOnly();
		slotTarget.forbidInteraction();
		InventorySlot slotResulut = inventory.addSlot(Isolator.SLOT_RESULUT, "output");
		slotResulut.setReadOnly();
		slotResulut.forbidInteraction();
		for (final InventorySlot slot : inventory.addSlotArray(Isolator.SLOT_FINISHED, "output")) {
			slot.setReadOnly();
			slot.forbidInsertion();
		}
		final ComponentTankContainer tanks = new ComponentTankContainer(machine);
		tanks.addTank(Isolator.TANK_ETHANOL, "input", 1000).setValidator(new TankValidator() {
			@Override
			public String getTooltip() {
				return FluidRegistry.getFluidStack(Fluids.BIO_ETHANOL.getTag(), 1).getLocalizedName();
			}

			@Override
			public boolean isValid(final FluidStack stack) {
				return Objects.equals(stack.getFluid().getName(), Fluids.BIO_ETHANOL.getTag());
			}
		});
		final ComponentChargedSlots chargedSlots = new ComponentChargedSlots(machine);
		chargedSlots.addCharge(Isolator.SLOT_ENZYME);
		final ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
		transfer.addRestock(Isolator.SLOT_RESERVE, Isolator.SLOT_TARGET, 1);
		transfer.addStorage(Isolator.SLOT_RESULUT, Isolator.SLOT_FINISHED);
		new ComponentPowerReceptor(machine, 20000);
		new IsolatorLogic(machine);
		new IsolaterFX(machine);
	}

	@Override
	public TileEntity createTileEntity() {
		return new TileEntityMachine(this);
	}

	@Override
	public void register() {
	}
}
