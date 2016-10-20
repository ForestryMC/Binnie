package binnie.extrabees.core;

import binnie.Binnie;
import binnie.core.IInitializable;
import binnie.extrabees.ExtraBees;
import forestry.api.core.Tabs;

public class ModuleCore implements IInitializable {
    @Override
    public void preInit() {
        ExtraBees.itemMisc = Binnie.Item.registerMiscItems(ExtraBeeItems.values(), Tabs.tabApiculture);
    }

    @Override
    public void init() {
        ExtraBeeItems.init();
    }

    @Override
    public void postInit() {
        ExtraBeeItems.postInit();
    }
}
