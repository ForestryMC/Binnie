package binnie.extrabees.utils;

import binnie.core.ModId;
import binnie.core.util.I18N;

public enum EnumBeeModifier {
	TERRITORY,
	MUTATION,
	LIFESPAN,
	PRODUCTION,
	FLOWERING,
	GENETIC_DECAY;

	public String getName() {
		return I18N.localise(ModId.EXTRA_BEES, "beemodifier." + this.name().toLowerCase());
	}
}
