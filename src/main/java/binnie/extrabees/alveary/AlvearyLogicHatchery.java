package binnie.extrabees.alveary;

import binnie.extrabees.utils.Utils;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeeRoot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

/**
 * Created by Elec332 on 13-5-2017.
 */
public class AlvearyLogicHatchery extends AbstractAlvearyLogic {

	public AlvearyLogicHatchery(){
		inv = new ItemStackHandler(5);
	}

	private final IItemHandlerModifiable inv;

	@Override
	public void updateServer(TileEntityExtraBeesAlvearyPart tile) {
		if (tile.getWorldObj().rand.nextInt(2400) == 0) {
			final IBeeHousing house = tile.getMultiblockLogic().getController();
			if (!house.getErrorLogic().hasErrors()) {
				final ItemStack queenStack = house.getBeeInventory().getQueen();
				IBeeRoot beeRoot = Utils.getBeeRoot();
				final IBee queen = (queenStack.isEmpty()) ? null : beeRoot.getMember(queenStack);
				if (queen != null) {
					ItemStack larvae = beeRoot.getMemberStack(beeRoot.getBee(queen.getGenome()), EnumBeeType.LARVAE);
					for (int i = 0; i < 5; i++) {
						if (inv.insertItem(i, larvae, false).isEmpty()){
							return;
						}
					}
				}
			}
		}
	}

}
