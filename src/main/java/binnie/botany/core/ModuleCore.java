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
            Binnie.Language.addObjectName(pH, Binnie.Language.unlocalised(Botany.instance, "ph." + pH.getID()));
        }
        for (final EnumMoisture pH2 : EnumMoisture.values()) {
            Binnie.Language.addObjectName(pH2, Binnie.Language.unlocalised(Botany.instance, "moisture." + pH2.getID()));
        }
        for (final EnumSoilType pH3 : EnumSoilType.values()) {
            Binnie.Language.addObjectName(pH3, Binnie.Language.unlocalised(Botany.instance, "soil." + pH3.getID()));
        }
    }

    @Override
    public void init() {
    }

    @Override
    public void postInit() {
    }
}
