package binnie.extrabees.liquids;

import binnie.Binnie;
import binnie.core.IInitializable;
import binnie.core.liquid.ItemFluidContainer;

public class ModuleLiquids implements IInitializable {
    @Override
    public void preInit() {
        Binnie.Liquid.createLiquids(ExtraBeeLiquid.values(), ItemFluidContainer.LiquidExtraBee);
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
