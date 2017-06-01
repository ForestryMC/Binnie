package binnie.core.craftgui;

import binnie.core.craftgui.renderer.TextureRenderer;
import binnie.core.craftgui.resource.minecraft.CraftGUIResourceManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CraftGUI {
	public static final TextureRenderer render = new TextureRenderer();
	public static CraftGUIResourceManager resourceManager;
}
