package binnie.core.gui.minecraft.control;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.api.gui.IWidget;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.resource.textures.Texture;

public class ControlImage extends Control {
	private final Object key;

	public ControlImage(IWidget parent, int x, int y, Texture text) {
		super(parent, x, y, text.width(), text.height());
		this.key = text;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderForeground(int guiWidth, int guiHeight) {
		CraftGUI.RENDER.texture(this.key, Point.ZERO);
	}
}
