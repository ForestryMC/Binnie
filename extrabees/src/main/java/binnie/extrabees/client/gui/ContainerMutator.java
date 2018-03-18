package binnie.extrabees.client.gui;

import java.awt.Dimension;

import net.minecraft.entity.player.EntityPlayer;

import net.minecraftforge.items.SlotItemHandler;

import binnie.extrabees.alveary.AlvearyLogicMutator;
import binnie.extrabees.alveary.EnumAlvearyLogicType;

public class ContainerMutator extends AlvearyContainer {

	public ContainerMutator(EntityPlayer player, AlvearyLogicMutator logic) {
		super(player, logic.getInventory(), EnumAlvearyLogicType.MUTATOR, new Dimension(176, 176));
	}

	@Override
	protected void setupContainer() {
		addPlayerInventory();
		addSlotToContainer(new SlotItemHandler(inv, 0, 81, 29));
	}
}
