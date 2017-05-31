package binnie.extrabees.client.gui;

import binnie.extrabees.alveary.AlvearyLogicFrameHousing;
import binnie.extrabees.alveary.EnumAlvearyLogicType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerFrameHousing extends AbstractAlvearyContainer {

	public ContainerFrameHousing(EntityPlayer player, AlvearyLogicFrameHousing logic) {
		super(player, logic.getInventory(), EnumAlvearyLogicType.FRAME, DEFAULT_DIMENSION);
	}

	@Override
	protected void setupContainer() {
		offset = -21;
		addPlayerInventory();
		addSlotToContainer(new SlotItemHandler(inv, 0, 80, 37));
	}
}
