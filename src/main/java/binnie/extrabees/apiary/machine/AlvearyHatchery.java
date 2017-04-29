package binnie.extrabees.apiary.machine;

import binnie.Binnie;
import binnie.core.craftgui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.transfer.TransferRequest;
import binnie.extrabees.apiary.ComponentBeeModifier;
import binnie.extrabees.apiary.ComponentExtraBeeGUI;
import binnie.extrabees.apiary.TileExtraBeeAlveary;
import binnie.extrabees.core.ExtraBeeGUID;
import binnie.extrabees.core.ExtraBeeTexture;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.apiculture.IBeeRoot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import java.util.Random;

public class AlvearyHatchery {
	public static int[] slotLarvae = new int[]{0, 1, 2, 3, 4};

	public static class PackageAlvearyHatchery extends AlvearyMachine.AlvearyPackage implements IMachineInformation {
		public PackageAlvearyHatchery() {
			super("hatchery", ExtraBeeTexture.AlvearyHatchery.getTexture(), false);
		}

		@Override
		public void createMachine(Machine machine) {
			new ComponentExtraBeeGUI(machine, ExtraBeeGUID.AlvearyHatchery);
			ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
			for (InventorySlot slot : inventory.addSlotArray(AlvearyHatchery.slotLarvae, "hatchery")) {
				slot.setValidator(new SlotValidatorLarvae());
			}
			new ComponentFrameModifier(machine);
		}
	}

	public static class SlotValidatorLarvae extends SlotValidator {
		public SlotValidatorLarvae() {
			super(SlotValidator.IconBee);
		}

		@Override
		public boolean isValid(ItemStack itemStack) {
			return Binnie.Genetics.getBeeRoot().isMember(itemStack) && Binnie.Genetics.getBeeRoot().getType(itemStack) == EnumBeeType.LARVAE;
		}

		@Override
		public String getTooltip() {
			return "Larvae";
		}
	}

	public static class ComponentFrameModifier extends ComponentBeeModifier implements IBeeModifier, IBeeListener {
		public ComponentFrameModifier(Machine machine) {
			super(machine);
		}

		@Override
		public void onUpdate() {
			if (new Random().nextInt(2400) != 0) {
				return;
			}

			TileEntity tile = getMachine().getTileEntity();
			if (!(tile instanceof TileExtraBeeAlveary)) {
				return;
			}

			IBeeHousing house = ((TileExtraBeeAlveary) tile).getMultiblockLogic().getController();
			if (house == null || house.getErrorLogic().hasErrors()) {
				return;
			}

			ItemStack queenStack = house.getBeeInventory().getQueen();
			IBee queen = (queenStack == null) ? null : Binnie.Genetics.getBeeRoot().getMember(queenStack);
			if (queen == null) {
				return;
			}

			IBeeRoot beeRoot = Binnie.Genetics.getBeeRoot();
			ItemStack larvae = beeRoot.getMemberStack(beeRoot.getBee(getMachine().getWorld(), queen.getGenome()), EnumBeeType.LARVAE.ordinal());
			new TransferRequest(larvae, getInventory()).transfer(true);
		}
	}
}
