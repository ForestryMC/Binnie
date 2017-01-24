package binnie.extrabees.core;

import binnie.core.IInitializable;
import binnie.core.item.IItemMiscProvider;
import binnie.core.item.ItemMisc;
import binnie.extrabees.ExtraBees;
import forestry.api.core.Tabs;

public class ModuleCore implements IInitializable {
	@Override
	public void preInit() {
		ExtraBees.itemMisc = new ItemMisc(Tabs.tabApiculture, (IItemMiscProvider[]) ExtraBeeItems.values());
		ExtraBees.proxy.registerItem(ExtraBees.itemMisc);
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
