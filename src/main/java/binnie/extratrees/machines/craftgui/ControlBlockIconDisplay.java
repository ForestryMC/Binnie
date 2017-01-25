package binnie.extratrees.machines.craftgui;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.renderer.RenderUtil;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class ControlBlockIconDisplay extends Control {
	TextureAtlasSprite icon;

	public ControlBlockIconDisplay(final IWidget parent, final int x, final int y, final TextureAtlasSprite icon) {
		super(parent, x, y, 18, 18);
		this.icon = icon;
	}

	@Override
	public void onRenderBackground(int guiWidth, int guiHeight) {
		RenderUtil.drawSprite(IPoint.ZERO, this.icon);
	}
}
