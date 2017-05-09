package binnie.botany.api;

import binnie.botany.Botany;
import binnie.core.util.I18N;

public enum EnumMoisture {
	DRY,
	NORMAL,
	DAMP;

	public String getID() {
		return name().toLowerCase();
	}

	public String getLocalisedName() {
		return I18N.localise(Botany.instance, "moisture." + getID());
	}
}
