package binnie.botany.api;

import binnie.botany.Botany;
import binnie.core.util.I18N;

public enum EnumAcidity {
	ACID,
	NEUTRAL,
	ALKALINE;

	public String getID() {
		return name().toLowerCase();
	}

	public String getLocalisedName() {
		return I18N.localise(Botany.instance, "ph." + getID());
	}
}
