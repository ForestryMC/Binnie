package binnie.core.gui;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.gui.renderer.TextureRenderer;
import binnie.core.gui.resource.minecraft.CraftGUIResourceManager;

@SideOnly(Side.CLIENT)
public class CraftGUI {
	public static final TextureRenderer render = new TextureRenderer();
	public static CraftGUIResourceManager resourceManager;
}
