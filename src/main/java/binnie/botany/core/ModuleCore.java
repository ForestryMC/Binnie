package binnie.botany.core;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.botany.api.EnumSoilType;
import binnie.core.IInitializable;

public class ModuleCore implements IInitializable {
	@Override
	public void preInit() {
		for (EnumAcidity pH : EnumAcidity.values()) {
			Binnie.I18N.addObjectName(pH, Binnie.I18N.unlocalised(Botany.instance, "ph." + pH.getID()));
		}
		for (EnumMoisture pH2 : EnumMoisture.values()) {
			Binnie.I18N.addObjectName(pH2, Binnie.I18N.unlocalised(Botany.instance, "moisture." + pH2.getID()));
		}
		for (EnumSoilType pH3 : EnumSoilType.values()) {
			Binnie.I18N.addObjectName(pH3, Binnie.I18N.unlocalised(Botany.instance, "soil." + pH3.getID()));
		}
	}

	@Override
	public void init() {
		// ignored
	}

	@Override
	public void postInit() {
		// ignored
	}
}
