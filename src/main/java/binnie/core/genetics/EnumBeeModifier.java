// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.genetics;

import binnie.core.BinnieCore;
import binnie.Binnie;

public enum EnumBeeModifier
{
	Territory,
	Mutation,
	Lifespan,
	Production,
	Flowering,
	GeneticDecay;

	public String getName() {
		return Binnie.Language.localise(BinnieCore.instance, "beemodifier." + this.name().toLowerCase());
	}
}
