package binnie.extrabees.client.gui;

import net.minecraft.entity.player.EntityPlayer;

import net.minecraftforge.items.SlotItemHandler;

import binnie.extrabees.alveary.AlvearyLogicHatchery;
import binnie.extrabees.alveary.EnumAlvearyLogicType;

public class ContainerHatchery extends AbstractAlvearyContainer {

	public ContainerHatchery(EntityPlayer player, AlvearyLogicHatchery logic) {
		super(player, logic.getInventory(), EnumAlvearyLogicType.HATCHERY, DEFAULT_DIMENSION);
	}

	@Override
	protected void setupContainer() {
		offset = -21;
		addPlayerInventory();
		for (int i = 0; i < 5; i++) {
			addSlotToContainer(new SlotItemHandler(inv, i, 44 + i * 18, 37));
		}
	}
}
