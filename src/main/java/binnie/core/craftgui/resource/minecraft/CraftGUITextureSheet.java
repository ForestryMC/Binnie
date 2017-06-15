package binnie.core.craftgui.resource.minecraft;

import javax.annotation.Nullable;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.craftgui.CraftGUI;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;

public enum CraftGUITextureSheet implements IBinnieTexture {
	Controls2("controls"),
	Panel2("panels"),
	Slots("slots");

	private final String name;

	@SideOnly(Side.CLIENT)
	@Nullable
	private BinnieResource resource;

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
		if (resource == null) {
			resource = CraftGUI.resourceManager.getTextureSheet(this.name).getTexture();
		}
		return resource;
	}
}
