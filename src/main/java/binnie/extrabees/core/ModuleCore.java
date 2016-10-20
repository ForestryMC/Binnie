// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extrabees.core;

import binnie.extrabees.ExtraBees;
import forestry.api.core.Tabs;
import binnie.Binnie;
import binnie.core.IInitializable;

public class ModuleCore implements IInitializable
{
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
