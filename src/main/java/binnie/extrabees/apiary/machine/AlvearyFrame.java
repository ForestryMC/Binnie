package binnie.extrabees.apiary.machine;

import binnie.core.craftgui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.SlotValidator;
import binnie.extrabees.apiary.ComponentBeeModifier;
import binnie.extrabees.apiary.ComponentExtraBeeGUI;
import binnie.extrabees.apiary.TileExtraBeeAlveary;
import binnie.extrabees.core.ExtraBeeGUID;
import binnie.extrabees.core.ExtraBeeTexture;
import binnie.extrabees.utils.Utils;
import forestry.api.apiculture.*;
import forestry.api.multiblock.IAlvearyController;
import forestry.api.multiblock.IMultiblockLogicAlveary;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class AlvearyFrame {
	public static int slotFrame = 0;

	public static class PackageAlvearyFrame extends AlvearyMachine.AlvearyPackage implements IMachineInformation {
		public PackageAlvearyFrame() {
			super("frame", ExtraBeeTexture.AlvearyFrame, false);
		}

		@Override
		public void createMachine(final Machine machine) {
			new ComponentExtraBeeGUI(machine, ExtraBeeGUID.AlvearyFrame);
			final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
			inventory.addSlot(AlvearyFrame.slotFrame, "frame").setValidator(new SlotValidatorFrame());
			new ComponentFrameModifier(machine);
		}
	}

	public static class SlotValidatorFrame extends SlotValidator {
		public SlotValidatorFrame() {
			super(SlotValidator.spriteFrame);
		}

		@Override
		public boolean isValid(final ItemStack itemStack) {
			return !itemStack.isEmpty() && itemStack.getItem() instanceof IHiveFrame;
		}

		@Override
		public String getTooltip() {
			return "Hive Frames";
		}
	}

	public static class ComponentFrameModifier extends ComponentBeeModifier implements IBeeModifier, IBeeListener {
		public ComponentFrameModifier(final Machine machine) {
			super(machine);
		}

		@Override
		public void wearOutEquipment(final int amount) {
			IHiveFrame hiveFrame = this.getHiveFrame();
			if (hiveFrame != null) {
				final World world = this.getMachine().getTileEntity().getWorld();
				IBeeRoot beeRoot = Utils.getBeeRoot();
				IMultiblockLogicAlveary multiblockLogic = ((TileExtraBeeAlveary) this.getMachine().getTileEntity()).getMultiblockLogic();
				IAlvearyController alvearyController = multiblockLogic.getController();
				ItemStack queenStack = alvearyController.getBeeInventory().getQueen();
				IBee queen = beeRoot.getMember(queenStack);
				if (queen != null) {
					final int wear = Math.round(amount * 5 * beeRoot.getBeekeepingMode(world).getWearModifier());
					ItemStack frame = this.getInventory().getStackInSlot(AlvearyFrame.slotFrame);
					ItemStack frameUsed = hiveFrame.frameUsed(alvearyController, frame, queen, wear);
					this.getInventory().setInventorySlotContents(AlvearyFrame.slotFrame, frameUsed);
				}
			}
		}

		@Nullable
		public IHiveFrame getHiveFrame() {
			ItemStack stackInSlot = this.getInventory().getStackInSlot(AlvearyFrame.slotFrame);
			if (!stackInSlot.isEmpty()) {
				return (IHiveFrame) stackInSlot.getItem();
			}
			return null;
		}

		@Override
		public float getTerritoryModifier(final IBeeGenome genome, final float currentModifier) {
			return (this.getHiveFrame() == null) ? 1.0f : this.getHiveFrame().getBeeModifier().getTerritoryModifier(genome, currentModifier);
		}

		@Override
		public float getMutationModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
			return (this.getHiveFrame() == null) ? 1.0f : this.getHiveFrame().getBeeModifier().getMutationModifier(genome, mate, currentModifier);
		}

		@Override
		public float getLifespanModifier(final IBeeGenome genome, @Nullable final IBeeGenome mate, final float currentModifier) {
			return (this.getHiveFrame() == null) ? 1.0f : this.getHiveFrame().getBeeModifier().getLifespanModifier(genome, mate, currentModifier);
		}

		@Override
		public float getProductionModifier(final IBeeGenome genome, final float currentModifier) {
			return (this.getHiveFrame() == null) ? 1.0f : this.getHiveFrame().getBeeModifier().getProductionModifier(genome, currentModifier);
		}

		@Override
		public float getFloweringModifier(final IBeeGenome genome, final float currentModifier) {
			return (this.getHiveFrame() == null) ? 1.0f : this.getHiveFrame().getBeeModifier().getFloweringModifier(genome, currentModifier);
		}
	}
}
