package binnie.extrabees.client.gui2;

import binnie.extrabees.alveary.AlvearyLogicHatchery;
import binnie.extrabees.alveary.AlvearyLogicType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.items.SlotItemHandler;

/**
 * Created by Elec332 on 13-5-2017.
 */
public class ContainerHatchery extends AbstractAlvearyContainer {

	public ContainerHatchery(EntityPlayer player, AlvearyLogicHatchery logic) {
		super(player, logic.getInventory(), AlvearyLogicType.HATCHERY, DEFAULT_DIMENSION);
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
