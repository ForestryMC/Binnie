package binnie.craftgui.resource.minecraft;

import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;
import binnie.craftgui.core.CraftGUI;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public enum CraftGUITextureSheet implements IBinnieTexture {
	Controls2("controls"),
	Panel2("panels"),
	Slots("slots");

	String name;

	CraftGUITextureSheet(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BinnieResource getTexture() {
		return CraftGUI.resourceManager.getTextureSheet(this.name).getTexture();
	}
}
