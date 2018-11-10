package binnie.extrabees.utils;

import binnie.core.ModId;
import binnie.core.util.I18N;

public enum EnumBeeBooleanModifier {
	SEALED,
	SELF_LIGHTED,
	SUNLIGHT_STIMULATED,
	HELLISH;

	public String getName() {
		return I18N.localise(ModId.EXTRA_BEES, "beebooleanmodifier." + this.name().toLowerCase());
	}
}
