// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.proxy;

import forestry.api.core.IIconProvider;
import binnie.extratrees.genetics.FruitSprite;
import forestry.api.core.ForestryAPI;

public class ProxyClient extends Proxy implements IExtraTreeProxy
{
	@Override
	public void init() {
		ForestryAPI.textureManager.registerIconProvider(FruitSprite.Average);
	}
}
