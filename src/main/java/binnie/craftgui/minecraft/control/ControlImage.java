package binnie.craftgui.minecraft.control;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.Point;
import binnie.craftgui.resource.Texture;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ControlImage extends Control {
	private Object key;

	public ControlImage(final IWidget parent, final int x, final int y, final Texture text) {
		super(parent, x, y, text.width(), text.height());
		this.key = text;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderForeground(int guiWidth, int guiHeight) {
		CraftGUI.render.texture(this.key, Point.ZERO);
	}
}
