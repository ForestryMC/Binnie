package binnie.core.gui.resource.stylesheet;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.gui.resource.textures.Texture;

public interface IStyleSheet {
	@SideOnly(Side.CLIENT)
	boolean hasTexture(Object key);

	@SideOnly(Side.CLIENT)
	Texture getTexture(Object key);
}
