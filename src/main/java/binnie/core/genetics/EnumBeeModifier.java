package binnie.core.genetics;

import binnie.core.BinnieCore;
import binnie.core.util.I18N;

public enum EnumBeeModifier {
	TERRITORY,
	MUTATION,
	LIFESPAN,
	PRODUCTION,
	FLOWERING,
	GENETIC_DECAY;

	public String getName() {
		return I18N.localise(BinnieCore.instance, "beemodifier." + name().toLowerCase());
	}
}
