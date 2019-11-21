package binnie.core.gui;

import binnie.core.gui.renderer.TextureRenderer;
import binnie.core.gui.resource.stylesheet.StyleSheetManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CraftGUI {
	public static final TextureRenderer RENDER = new TextureRenderer();
	public static StyleSheetManager styleSheetManager;
}
