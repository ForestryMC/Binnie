package binnie.extrabees.apiary.machine;

import binnie.Binnie;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.SlotValidator;
import binnie.craftgui.minecraft.IMachineInformation;
import binnie.extrabees.apiary.ComponentBeeModifier;
import binnie.extrabees.apiary.ComponentExtraBeeGUI;
import binnie.extrabees.apiary.TileExtraBeeAlveary;
import binnie.extrabees.core.ExtraBeeGUID;
import binnie.extrabees.core.ExtraBeeTexture;
import forestry.api.apiculture.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class AlvearyFrame {
    public static int slotFrame;

    static {
        AlvearyFrame.slotFrame = 0;
    }

    public static class PackageAlvearyFrame extends AlvearyMachine.AlvearyPackage implements IMachineInformation {
        public PackageAlvearyFrame() {
            super("frame", ExtraBeeTexture.AlvearyFrame.getTexture(), false);
        }

        @Override
        public void createMachine(final Machine machine) {
            new ComponentExtraBeeGUI(machine, ExtraBeeGUID.AlvearyFrame);
            final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
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
        public boolean isValid(final ItemStack itemStack) {
            return itemStack != null && itemStack.getItem() instanceof IHiveFrame;
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
            if (this.getHiveFrame() == null) {
                return;
            }
            final World world = this.getMachine().getTileEntity().getWorld();
            final int wear = Math.round(amount * 5 * Binnie.Genetics.getBeeRoot().getBeekeepingMode(world).getWearModifier());
            this.getInventory().setInventorySlotContents(AlvearyFrame.slotFrame, this.getHiveFrame().frameUsed(((TileExtraBeeAlveary) this.getMachine().getTileEntity()).getMultiblockLogic().getController(), this.getInventory().getStackInSlot(AlvearyFrame.slotFrame), (IBee) null, wear));
        }

        public IHiveFrame getHiveFrame() {
            ItemStack stackInSlot = this.getInventory().getStackInSlot(AlvearyFrame.slotFrame);
            if (stackInSlot != null) {
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
        public float getLifespanModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
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
