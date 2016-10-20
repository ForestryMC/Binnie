// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core;

import binnie.Binnie;

public abstract class ManagerBase implements IInitializable
{
	public ManagerBase() {
		Binnie.Managers.add(this);
	}

	@Override
	public void preInit() {
	}

	@Override
	public void init() {
	}

	@Override
	public void postInit() {
	}
}
