package binnie.extrabees.client.gui;

import binnie.extrabees.alveary.AlvearyLogicMutator;
import binnie.extrabees.alveary.EnumAlvearyLogicType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.SlotItemHandler;

import java.awt.Dimension;

public class ContainerMutator extends AbstractAlvearyContainer {

	public ContainerMutator(EntityPlayer player, AlvearyLogicMutator logic) {
		super(player, logic.getInventory(), EnumAlvearyLogicType.MUTATOR, new Dimension(176, 176));
	}

	@Override
	protected void setupContainer() {
		addPlayerInventory();
		addSlotToContainer(new SlotItemHandler(inv, 0, 81, 29));
	}
}
