package binnie.extrabees.apiary.machine.frame;

import binnie.Binnie;
import binnie.core.machines.Machine;
import binnie.extrabees.apiary.ComponentBeeModifier;
import binnie.extrabees.apiary.TileExtraBeeAlveary;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.apiculture.IHiveFrame;
import net.minecraft.world.World;

public class FrameComponentModifier extends ComponentBeeModifier implements IBeeModifier, IBeeListener {
    public FrameComponentModifier(Machine machine) {
        super(machine);
    }

    @Override
    public void wearOutEquipment(int amount) {
        if (getHiveFrame() == null) {
            return;
        }

        World world = getMachine().getTileEntity().getWorldObj();
        int wear = Math.round(amount
                * 5
                * Binnie.Genetics.getBeeRoot().getBeekeepingMode(world).getWearModifier());
        getInventory()
                .setInventorySlotContents(
                        AlvearyFrame.SLOT_FRAME,
                        getHiveFrame()
                                .frameUsed(
                                        ((TileExtraBeeAlveary) getMachine().getTileEntity())
                                                .getMultiblockLogic()
                                                .getController(),
                                        getInventory().getStackInSlot(AlvearyFrame.SLOT_FRAME),
                                        null,
                                        wear));
    }

    public IHiveFrame getHiveFrame() {
        if (getInventory().getStackInSlot(AlvearyFrame.SLOT_FRAME) == null) {
            return null;
        }
        return (IHiveFrame)
                getInventory().getStackInSlot(AlvearyFrame.SLOT_FRAME).getItem();
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
