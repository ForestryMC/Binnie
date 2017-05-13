package binnie.extrabees.apiary.machine;

import binnie.core.craftgui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.transfer.TransferRequest;
import binnie.extrabees.apiary.ComponentBeeModifier;
import binnie.extrabees.apiary.ComponentExtraBeeGUI;
import binnie.extrabees.apiary.TileExtraBeeAlveary;
import binnie.extrabees.client.ExtraBeeGUID;
import binnie.extrabees.client.ExtraBeeTexture;
import binnie.extrabees.utils.Utils;
import forestry.api.apiculture.*;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import java.util.Random;

public class AlvearyHatchery {
	public static int[] slotLarvae = new int[]{0, 1, 2, 3, 4};

	public static class PackageAlvearyHatchery extends AlvearyMachine.AlvearyPackage implements IMachineInformation {
		public PackageAlvearyHatchery() {
			super("hatchery", ExtraBeeTexture.AlvearyHatchery, false);
		}

		@Override
		public void createMachine(final Machine machine) {
			new ComponentExtraBeeGUI(machine, ExtraBeeGUID.AlvearyHatchery);
			final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
			for (final InventorySlot slot : inventory.addSlotArray(AlvearyHatchery.slotLarvae, "hatchery")) {
				slot.setValidator(new SlotValidatorLarvae());
			}
			new ComponentFrameModifier(machine);
		}
	}

	public static class SlotValidatorLarvae extends SlotValidator {
		public SlotValidatorLarvae() {
			super(SlotValidator.spriteBee);
		}

		@Override
		public boolean isValid(final ItemStack itemStack) {
			IBeeRoot beeRoot = Utils.getBeeRoot();
			return beeRoot.isMember(itemStack) && beeRoot.getType(itemStack) == EnumBeeType.LARVAE;
		}

		@Override
		public String getTooltip() {
			return "Larvae";
		}
	}

	public static class ComponentFrameModifier extends ComponentBeeModifier implements IBeeModifier, IBeeListener {
		public ComponentFrameModifier(final Machine machine) {
			super(machine);
		}

		@Override
		public void onUpdate() {
			if (new Random().nextInt(2400) == 0) {
				final TileEntity tile = this.getMachine().getTileEntity();
				if (tile instanceof TileExtraBeeAlveary) {
					final IBeeHousing house = ((TileExtraBeeAlveary) tile).getMultiblockLogic().getController();
					if (house != null && !house.getErrorLogic().hasErrors()) {
						final ItemStack queenStack = house.getBeeInventory().getQueen();
						IBeeRoot beeRoot = Utils.getBeeRoot();
						final IBee queen = (queenStack.isEmpty()) ? null : beeRoot.getMember(queenStack);
						if (queen != null) {
							final ItemStack larvae = beeRoot.getMemberStack(beeRoot.getBee(queen.getGenome()), EnumBeeType.LARVAE);
							new TransferRequest(larvae, this.getInventory()).transfer(true);
						}
					}
				}
			}
		}
	}
}
