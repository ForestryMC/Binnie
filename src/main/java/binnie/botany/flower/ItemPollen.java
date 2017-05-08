package binnie.botany.flower;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.botany.api.EnumFlowerStage;

public class ItemPollen extends ItemBotany {
	public ItemPollen() {
		super("pollen");
	}

	@Override
	public EnumFlowerStage getStage() {
		return EnumFlowerStage.POLLEN;
	}

	@Override
	public String getTag() {
		return Binnie.I18N.localise(Botany.instance, "item.pollen.tag.name");
	}
}
