package binnie.core.craftgui.minecraft.control;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.resource.Texture;
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
