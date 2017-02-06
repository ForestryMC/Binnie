package binnie.core.genetics;

import binnie.Binnie;
import binnie.core.BinnieCore;

public enum EnumBeeModifier {
	Territory,
	Mutation,
	Lifespan,
	Production,
	Flowering,
	GeneticDecay;

	public String getName() {
		return Binnie.LANGUAGE.localise(BinnieCore.getInstance(), "beemodifier." + this.name().toLowerCase());
	}
}
