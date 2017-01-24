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
		for (final EnumAcidity pH : EnumAcidity.values()) {
			Binnie.LANGUAGE.addObjectName(pH, Binnie.LANGUAGE.unlocalised(Botany.instance, "ph." + pH.getName()));
		}
		for (final EnumMoisture pH2 : EnumMoisture.values()) {
			Binnie.LANGUAGE.addObjectName(pH2, Binnie.LANGUAGE.unlocalised(Botany.instance, "moisture." + pH2.getName()));
		}
		for (final EnumSoilType pH3 : EnumSoilType.values()) {
			Binnie.LANGUAGE.addObjectName(pH3, Binnie.LANGUAGE.unlocalised(Botany.instance, "soil." + pH3.getName()));
		}
	}

	@Override
	public void init() {
	}

	@Override
	public void postInit() {
	}
}
