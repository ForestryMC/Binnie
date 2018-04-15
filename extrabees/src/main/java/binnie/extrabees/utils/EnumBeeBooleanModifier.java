package binnie.extrabees.utils;

import binnie.core.ModId;
import binnie.core.util.I18N;

public enum EnumBeeBooleanModifier {
	Sealed,
	SelfLighted,
	SunlightStimulated,
	Hellish;

	public String getName() {
		return I18N.localise(ModId.EXTRA_BEES, "beebooleanmodifier." + this.name().toLowerCase());
	}
}
