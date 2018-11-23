package binnie.extrabees.machines.frame;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.apiculture.IBeeRoot;
import forestry.api.apiculture.IHiveFrame;
import forestry.api.multiblock.IAlvearyController;
import forestry.api.multiblock.IMultiblockLogicAlveary;

import binnie.core.machines.Machine;
import binnie.extrabees.machines.TileExtraBeeAlveary;
import binnie.extrabees.utils.ComponentBeeModifier;
import binnie.extrabees.utils.Utils;

public class ComponentFrameModifier extends ComponentBeeModifier implements IBeeModifier, IBeeListener {
	public ComponentFrameModifier(Machine machine) {
		super(machine);
	}

	@Override
	public void wearOutEquipment(int amount) {
		IHiveFrame hiveFrame = this.getHiveFrame();
		if (hiveFrame != null) {
			World world = this.getMachine().getTileEntity().getWorld();
			IBeeRoot beeRoot = Utils.getBeeRoot();
			IMultiblockLogicAlveary multiblockLogic = ((TileExtraBeeAlveary) this.getMachine().getTileEntity()).getMultiblockLogic();
			IAlvearyController alvearyController = multiblockLogic.getController();
			ItemStack queenStack = alvearyController.getBeeInventory().getQueen();
			IBee queen = beeRoot.getMember(queenStack);
			if (queen != null) {
				int wear = Math.round(amount * 5 * beeRoot.getBeekeepingMode(world).getWearModifier());
				ItemStack frame = this.getInventory().getStackInSlot(AlvearyFrame.SLOT_FRAME);
				ItemStack frameUsed = hiveFrame.frameUsed(alvearyController, frame, queen, wear);
				this.getInventory().setInventorySlotContents(AlvearyFrame.SLOT_FRAME, frameUsed);
			}
		}
	}

	@Nullable
	public IHiveFrame getHiveFrame() {
		ItemStack stackInSlot = this.getInventory().getStackInSlot(AlvearyFrame.SLOT_FRAME);
		if (!stackInSlot.isEmpty()) {
			return (IHiveFrame) stackInSlot.getItem();
		}
		return null;
	}

	@Override
	public float getTerritoryModifier(IBeeGenome genome, float currentModifier) {
		return (this.getHiveFrame() == null) ? 1.0f : this.getHiveFrame().getBeeModifier().getTerritoryModifier(genome, currentModifier);
	}

	@Override
	public float getMutationModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
		return (this.getHiveFrame() == null) ? 1.0f : this.getHiveFrame().getBeeModifier().getMutationModifier(genome, mate, currentModifier);
	}

	@Override
	public float getLifespanModifier(IBeeGenome genome, @Nullable IBeeGenome mate, float currentModifier) {
		return (this.getHiveFrame() == null) ? 1.0f : this.getHiveFrame().getBeeModifier().getLifespanModifier(genome, mate, currentModifier);
	}

	@Override
	public float getProductionModifier(IBeeGenome genome, float currentModifier) {
		return (this.getHiveFrame() == null) ? 1.0f : this.getHiveFrame().getBeeModifier().getProductionModifier(genome, currentModifier);
	}

	@Override
	public float getFloweringModifier(IBeeGenome genome, float currentModifier) {
		return (this.getHiveFrame() == null) ? 1.0f : this.getHiveFrame().getBeeModifier().getFloweringModifier(genome, currentModifier);
	}
}
