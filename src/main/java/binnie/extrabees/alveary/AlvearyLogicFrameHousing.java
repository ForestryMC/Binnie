package binnie.extrabees.alveary;

import binnie.extrabees.utils.Utils;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeRoot;
import forestry.api.apiculture.IHiveFrame;
import forestry.api.multiblock.IAlvearyController;
import forestry.api.multiblock.IMultiblockLogicAlveary;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Elec332 on 13-5-2017.
 */
public class AlvearyLogicFrameHousing extends AbstractAlvearyLogic {

	public AlvearyLogicFrameHousing(TileEntityExtraBeesAlvearyPart tile){
		inv = new ItemStackHandler(1);
	}

	private TileEntityExtraBeesAlvearyPart tile;
	private final IItemHandlerModifiable inv;

	@Override
	public void wearOutEquipment(final int amount) {
		IHiveFrame hiveFrame = this.getHiveFrame();
		if (hiveFrame != null) {
			IBeeRoot beeRoot = Utils.getBeeRoot();
			IMultiblockLogicAlveary multiblockLogic = tile.getMultiblockLogic();
			IAlvearyController alvearyController = multiblockLogic.getController();
			ItemStack queenStack = alvearyController.getBeeInventory().getQueen();
			IBee queen = beeRoot.getMember(queenStack);
			if (queen != null) {
				final int wear = Math.round(amount * 5 * beeRoot.getBeekeepingMode(tile.getWorldObj()).getWearModifier());
				ItemStack frame = inv.getStackInSlot(0);
				ItemStack frameUsed = hiveFrame.frameUsed(alvearyController, frame, queen, wear);
				inv.setStackInSlot(0, frameUsed);
			}
		}
	}

	@Nullable
	private IHiveFrame getHiveFrame() {
		ItemStack stackInSlot = inv.getStackInSlot(0);
		if (!stackInSlot.isEmpty()) {
			return (IHiveFrame) stackInSlot.getItem();
		}
		return null;
	}

	@Override
	public float getTerritoryModifier(@Nonnull final IBeeGenome genome, final float currentModifier) {
		return (this.getHiveFrame() == null) ? 1.0f : this.getHiveFrame().getBeeModifier().getTerritoryModifier(genome, currentModifier);
	}

	@Override
	public float getMutationModifier(@Nonnull final IBeeGenome genome, @Nonnull final IBeeGenome mate, final float currentModifier) {
		return (this.getHiveFrame() == null) ? 1.0f : this.getHiveFrame().getBeeModifier().getMutationModifier(genome, mate, currentModifier);
	}

	@Override
	public float getLifespanModifier(@Nonnull final IBeeGenome genome, @Nullable final IBeeGenome mate, final float currentModifier) {
		return (this.getHiveFrame() == null) ? 1.0f : this.getHiveFrame().getBeeModifier().getLifespanModifier(genome, mate, currentModifier);
	}

	@Override
	public float getProductionModifier(@Nonnull final IBeeGenome genome, final float currentModifier) {
		return (this.getHiveFrame() == null) ? 1.0f : this.getHiveFrame().getBeeModifier().getProductionModifier(genome, currentModifier);
	}

	@Override
	public float getFloweringModifier(@Nonnull final IBeeGenome genome, final float currentModifier) {
		return (this.getHiveFrame() == null) ? 1.0f : this.getHiveFrame().getBeeModifier().getFloweringModifier(genome, currentModifier);
	}

}
