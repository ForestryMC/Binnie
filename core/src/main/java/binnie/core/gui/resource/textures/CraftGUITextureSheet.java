package binnie.core.gui.resource.textures;

import javax.annotation.Nullable;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.Binnie;
import binnie.core.Constants;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;
import binnie.core.resource.ResourceType;

public enum CraftGUITextureSheet implements IBinnieTexture {
	CONTROLS("controls"),
	PANEL("panels"),
	SLOTS("slots");

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
			resource = Binnie.RESOURCE.getPNG(Constants.CORE_MOD_ID, ResourceType.GUI, "craftgui-" + name);
		}
		return resource;
	}
}
