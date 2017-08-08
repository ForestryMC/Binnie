package binnie.extrabees.utils;

import binnie.extrabees.ExtraBees;

public enum EnumBeeModifier {
	TERRITORY,
	MUTATION,
	LIFESPAN,
	PRODUCTION,
	FLOWERING,
	GENETIC_DECAY;

	public String getName() {
		return ExtraBees.proxy.localiseWithOutPrefix("beemodifier." + this.name().toLowerCase());
	}

}
