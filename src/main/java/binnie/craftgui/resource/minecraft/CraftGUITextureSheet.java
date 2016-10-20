// 
// Decompiled by Procyon v0.5.30
// 

package binnie.craftgui.resource.minecraft;

import binnie.craftgui.core.CraftGUI;
import binnie.core.BinnieCore;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;

public enum CraftGUITextureSheet implements IBinnieTexture
{
	Controls2("controls"),
	Panel2("panels"),
	Slots("slots");

	String name;

	private CraftGUITextureSheet(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public BinnieResource getTexture() {
		if (BinnieCore.proxy.isServer()) {
			return null;
		}
		return CraftGUI.ResourceManager.getTextureSheet(this.name).getTexture();
	}
}
