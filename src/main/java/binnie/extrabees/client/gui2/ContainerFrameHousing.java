package binnie.extrabees.client.gui2;

import binnie.extrabees.alveary.AlvearyLogicFrameHousing;
import binnie.extrabees.alveary.AlvearyLogicType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.SlotItemHandler;

/**
 * Created by Elec332 on 13-5-2017.
 */
public class ContainerFrameHousing extends AbstractAlvearyContainer {

	public ContainerFrameHousing(EntityPlayer player, AlvearyLogicFrameHousing logic) {
		super(player, logic.getInventory(), AlvearyLogicType.FRAME, DEFAULT_DIMENSION);
	}

	@Override
	protected void setupContainer() {
		offset = -21;
		addPlayerInventory();
		addSlotToContainer(new SlotItemHandler(inv, 0, 80, 37));
	}

}
