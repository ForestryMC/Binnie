package binnie.core.craftgui;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.craftgui.renderer.TextureRenderer;
import binnie.core.craftgui.resource.minecraft.CraftGUIResourceManager;

@SideOnly(Side.CLIENT)
public class CraftGUI {
	public static final TextureRenderer render = new TextureRenderer();
	public static CraftGUIResourceManager resourceManager;
}
