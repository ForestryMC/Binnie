package binnie.extrabees.liquids;

import binnie.Binnie;
import binnie.core.IInitializable;
import binnie.core.liquid.ItemFluidContainer;

public class ModuleLiquids implements IInitializable {
	@Override
	public void preInit() {
		Binnie.LIQUID.createLiquids(ExtraBeeLiquid.values());
	}

	@Override
	public void init() {
	}

	@Override
	public void postInit() {
	}
}
