package binnie.extrabees.utils;

import net.minecraft.util.text.translation.I18n;

public enum EnumBeeModifier {
	Territory,
	Mutation,
	Lifespan,
	Production,
	Flowering,
	GeneticDecay;

	public String getName() {
		return I18n.translateToLocal("beemodifier." + this.name().toLowerCase());
	}

}
