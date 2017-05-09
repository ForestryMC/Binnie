package binnie.botany.flower;

import binnie.botany.Botany;
import binnie.botany.api.EnumFlowerStage;
import binnie.core.util.I18N;

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
		return I18N.localise(Botany.instance, "item.pollen.tag.name");
	}
}
