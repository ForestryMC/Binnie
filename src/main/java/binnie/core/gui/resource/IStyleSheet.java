package binnie.core.gui.resource;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IStyleSheet {
	@SideOnly(Side.CLIENT)
	Texture getTexture(final Object p0);
}
