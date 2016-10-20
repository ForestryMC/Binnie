// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extrabees.liquids;

import binnie.core.liquid.ItemFluidContainer;
import binnie.Binnie;
import binnie.core.IInitializable;

public class ModuleLiquids implements IInitializable
{
	@Override
	public void preInit() {
		Binnie.Liquid.createLiquids(ExtraBeeLiquid.values(), ItemFluidContainer.LiquidExtraBee);
	}

	@Override
	public void init() {
	}

	@Override
	public void postInit() {
	}
}
