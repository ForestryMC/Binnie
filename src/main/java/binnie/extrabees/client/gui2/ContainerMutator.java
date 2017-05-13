package binnie.extrabees.client.gui2;

import binnie.extrabees.alveary.AlvearyLogicMutator;
import binnie.extrabees.alveary.AlvearyLogicType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.SlotItemHandler;

import java.awt.*;

/**
 * Created by Elec332 on 13-5-2017.
 */
public class ContainerMutator extends AbstractAlvearyContainer {

	public ContainerMutator(EntityPlayer player, AlvearyLogicMutator logic) {
		super(player, logic.getInventory(), AlvearyLogicType.MUTATOR, new Dimension(176, 176));
	}

	@Override
	protected void setupContainer() {
		addPlayerInventory();
		addSlotToContainer(new SlotItemHandler(inv, 0, 81, 29));
	}

}
