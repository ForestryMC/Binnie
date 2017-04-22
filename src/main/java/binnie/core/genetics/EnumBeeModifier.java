package binnie.core.genetics;

import binnie.*;
import binnie.core.*;

public enum EnumBeeModifier {
	Territory,
	Mutation,
	Lifespan,
	Production,
	Flowering,
	GeneticDecay;

	public String getName() {
		return Binnie.Language.localise(BinnieCore.instance, "beemodifier." + name().toLowerCase());
	}
}
