package binnie.extrabees.utils;

import net.minecraft.util.text.translation.I18n;

public enum EnumBeeModifier {
	TERRITORY,
	MUTATION,
	LIFESPAN,
	PRODUCTION,
	FLOWERING,
	GENETIC_DECAY;

	public String getName() {
		return I18n.translateToLocal("beemodifier." + this.name().toLowerCase());
	}

}
