package binnie.genetics.machine.polymeriser;

import binnie.core.machines.inventory.TankValidator;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.fluids.FluidStack;

import binnie.core.gui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentChargedSlots;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentInventoryTransfer;
import binnie.core.machines.inventory.ComponentTankContainer;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.item.GeneticLiquid;
import binnie.genetics.machine.ComponentGeneticGUI;
import binnie.genetics.machine.GeneticMachine;
import binnie.genetics.machine.ModuleMachine;

public class PackagePolymeriser extends GeneticMachine.PackageGeneticBase implements IMachineInformation {

	public PackagePolymeriser() {
		super("polymeriser", 58819, true);
	}

	@Override
	public void createMachine(Machine machine) {
		new ComponentGeneticGUI(machine, GeneticsGUI.POLYMERISER);
		ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
		//Slot Gold
		InventorySlot slotGold = inventory.addSlot(Polymeriser.SLOT_GOLD, "catalyst");
		slotGold.setValidator(new SlotValidator.Item(new ItemStack(Items.GOLD_NUGGET, 1), ModuleMachine.spriteNugget));
		slotGold.forbidExtraction();
		//Slot Serum
		InventorySlot slotSerum = inventory.addSlot(Polymeriser.SLOT_SERUM, "process");
		slotSerum.setValidator(new SlotValidatorUnfilledSerum());
		slotSerum.forbidInteraction();
		slotSerum.setReadOnly();
		for (InventorySlot slot : inventory.addSlotArray(Polymeriser.SLOT_SERUM_RESERVE, "input")) {
			slot.setValidator(new SlotValidatorUnfilledSerum());
			slot.forbidExtraction();
		}
		for (InventorySlot slot : inventory.addSlotArray(Polymeriser.SLOT_SERUM_FINISHED, "output")) {
			slot.setReadOnly();
		}
		ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
		transfer.addRestock(Polymeriser.SLOT_SERUM_RESERVE, Polymeriser.SLOT_SERUM, 1);
		transfer.addStorage(Polymeriser.SLOT_SERUM, Polymeriser.SLOT_SERUM_FINISHED, (stack) -> !stack.isItemDamaged());
		ComponentTankContainer tank = new ComponentTankContainer(machine);
		tank.addTank(Polymeriser.TANK_BACTERIA, "input", 1000).setValidator(new BacteriaTankValidator());
		tank.addTank(Polymeriser.TANK_DNA, "input", 1000).setValidator(new DnaTankValidator());
		new ComponentChargedSlots(machine).addCharge(1);
		new ComponentPowerReceptor(machine, 8000);
		new PolymeriserLogic(machine);
		new PolymeriserFX(machine);
	}

	@Override
	public TileEntity createTileEntity() {
		return new TileEntityMachine(this);
	}

	@Override
	public void register() {
	}

	private static class BacteriaTankValidator extends TankValidator {
		@Override
		public boolean isValid(final FluidStack itemStack) {
			return GeneticLiquid.BacteriaPoly.get(1).isFluidEqual(itemStack);
		}

		@Override
		public String getTooltip() {
			return GeneticLiquid.BacteriaPoly.get(1).getLocalizedName();
		}
	}

	private static class DnaTankValidator extends TankValidator {
		@Override
		public boolean isValid(final FluidStack itemStack) {
			return GeneticLiquid.RawDNA.get(1).isFluidEqual(itemStack);
		}

		@Override
		public String getTooltip() {
			return GeneticLiquid.RawDNA.get(1).getLocalizedName();
		}
	}
}