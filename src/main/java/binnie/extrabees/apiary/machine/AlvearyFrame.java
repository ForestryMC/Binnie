package binnie.extrabees.apiary.machine;

import binnie.Binnie;
import binnie.core.craftgui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.SlotValidator;
import binnie.extrabees.apiary.ComponentBeeModifier;
import binnie.extrabees.apiary.ComponentExtraBeeGUI;
import binnie.extrabees.apiary.TileExtraBeeAlveary;
import binnie.extrabees.core.ExtraBeeGUID;
import binnie.extrabees.core.ExtraBeeTexture;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.apiculture.IHiveFrame;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class AlvearyFrame {
	public static int slotFrame = 0;

	public static class PackageAlvearyFrame extends AlvearyMachine.AlvearyPackage implements IMachineInformation {
		public PackageAlvearyFrame() {
			super("frame", ExtraBeeTexture.AlvearyFrame.getTexture(), false);
		}

		@Override
		public void createMachine(Machine machine) {
			new ComponentExtraBeeGUI(machine, ExtraBeeGUID.AlvearyFrame);
			ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
			inventory.addSlot(AlvearyFrame.slotFrame, "frame");
			inventory.getSlot(AlvearyFrame.slotFrame).setValidator(new SlotValidatorFrame());
			new ComponentFrameModifier(machine);
		}
	}

	public static class SlotValidatorFrame extends SlotValidator {
		public SlotValidatorFrame() {
			super(SlotValidator.IconFrame);
		}

		@Override
		public boolean isValid(ItemStack itemStack) {
			return itemStack != null
				&& itemStack.getItem() instanceof IHiveFrame;
		}

		@Override
		public String getTooltip() {
			return "Hive Frames";
		}
	}

	public static class ComponentFrameModifier extends ComponentBeeModifier implements IBeeModifier, IBeeListener {
		public ComponentFrameModifier(Machine machine) {
			super(machine);
		}

		@Override
		public void wearOutEquipment(int amount) {
			if (getHiveFrame() == null) {
				return;
			}

			World world = getMachine().getTileEntity().getWorldObj();
			int wear = Math.round(amount * 5 * Binnie.Genetics.getBeeRoot().getBeekeepingMode(world).getWearModifier());
			getInventory().setInventorySlotContents(AlvearyFrame.slotFrame, getHiveFrame().frameUsed(((TileExtraBeeAlveary) getMachine().getTileEntity()).getMultiblockLogic().getController(), getInventory().getStackInSlot(AlvearyFrame.slotFrame), null, wear));
		}

		public IHiveFrame getHiveFrame() {
			if (getInventory().getStackInSlot(AlvearyFrame.slotFrame) != null) {
				return (IHiveFrame) getInventory().getStackInSlot(AlvearyFrame.slotFrame).getItem();
			}
			return null;
		}

		@Override
		public float getTerritoryModifier(IBeeGenome genome, float currentModifier) {
			IHiveFrame frame = getHiveFrame();
			if (frame == null) {
				return 1.0f;
			}
			return frame.getBeeModifier().getTerritoryModifier(genome, currentModifier);
		}

		@Override
		public float getMutationModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
			IHiveFrame frame = getHiveFrame();
			if (frame == null) {
				return 1.0f;
			}
			return frame.getBeeModifier().getMutationModifier(genome, mate, currentModifier);
		}

		@Override
		public float getLifespanModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
			IHiveFrame frame = getHiveFrame();
			if (getHiveFrame() == null) {
				return 1.0f;
			}
			return frame.getBeeModifier().getLifespanModifier(genome, mate, currentModifier);
		}

		@Override
		public float getProductionModifier(IBeeGenome genome, float currentModifier) {
			IHiveFrame frame = getHiveFrame();
			if (frame == null) {
				return 1.0f;
			}
			return frame.getBeeModifier().getProductionModifier(genome, currentModifier);
		}

		@Override
		public float getFloweringModifier(IBeeGenome genome, float currentModifier) {
			IHiveFrame frame = getHiveFrame();
			if (frame == null) {
				return 1.0f;
			}
			return frame.getBeeModifier().getFloweringModifier(genome, currentModifier);
		}
	}
}
