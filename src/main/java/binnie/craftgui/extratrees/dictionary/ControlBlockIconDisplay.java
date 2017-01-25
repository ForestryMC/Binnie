package binnie.craftgui.extratrees.dictionary;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IPoint;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class ControlBlockIconDisplay extends Control {
	TextureAtlasSprite icon;

	public ControlBlockIconDisplay(final IWidget parent, final int x, final int y, final TextureAtlasSprite icon) {
		super(parent, x, y, 18, 18);
		this.icon = icon;
	}

	@Override
	public void onRenderBackground(int guiWidth, int guiHeight) {
		CraftGUI.render.sprite(IPoint.ZERO, this.icon);
	}
}
