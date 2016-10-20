// 
// Decompiled by Procyon v0.5.30
// 

package binnie.craftgui.minecraft;

import binnie.core.IInitializable;

public class ModuleCraftGUI implements IInitializable
{
	@Override
	public void preInit() {
	}

	@Override
	public void init() {
	}

	@Override
	public void postInit() {
		for (final GUIIcon icon : GUIIcon.values()) {
			icon.register();
		}
	}
}
