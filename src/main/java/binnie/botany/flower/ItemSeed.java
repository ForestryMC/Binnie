package binnie.botany.flower;

import binnie.botany.Botany;
import binnie.botany.api.EnumFlowerStage;
import binnie.core.util.I18N;

public class ItemSeed extends ItemBotany {
	public ItemSeed() {
		super("seed");
	}

	@Override
	public EnumFlowerStage getStage() {
		return EnumFlowerStage.SEED;
	}

	@Override
	public String getTag() {
		return I18N.localise(Botany.instance, "item.germling.tag.name");
	}
}
