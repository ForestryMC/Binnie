package binnie.botany.flower;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.botany.api.EnumFlowerStage;

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
		return Binnie.I18N.localise(Botany.instance, "item.germling.tag.name");
	}
}
