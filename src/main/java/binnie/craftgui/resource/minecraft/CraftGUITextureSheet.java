package binnie.craftgui.resource.minecraft;

import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;
import binnie.craftgui.core.CraftGUI;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

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
