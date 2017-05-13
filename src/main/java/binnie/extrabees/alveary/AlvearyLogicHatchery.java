package binnie.extrabees.alveary;

import binnie.extrabees.client.gui2.AbstractAlvearyContainer;
import binnie.extrabees.client.gui2.ContainerHatchery;
import binnie.extrabees.client.gui2.GuiContainerAlvearyPart;
import binnie.extrabees.utils.Utils;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeeRoot;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Elec332 on 13-5-2017.
 */
public class AlvearyLogicHatchery extends AbstractAlvearyLogic {

	public AlvearyLogicHatchery(){
		inv = new ItemStackHandler(5);
	}

	private final IItemHandlerModifiable inv;

	public IItemHandlerModifiable getInventory() {
		return inv;
	}

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

	@Nullable
	@Override
	@SideOnly(Side.CLIENT)
	public GuiContainer getGui(@Nonnull EntityPlayer player, int data) {
		return new GuiContainerAlvearyPart(getContainer(player, data));
	}

	@Nullable
	@Override
	public AbstractAlvearyContainer getContainer(@Nonnull EntityPlayer player, int data) {
		return new ContainerHatchery(player, this);
	}

	@Override
	public boolean hasGui() {
		return true;
	}

}
