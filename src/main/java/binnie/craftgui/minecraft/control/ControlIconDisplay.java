package binnie.craftgui.minecraft.control;

import binnie.core.BinnieCore;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IPoint;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;

public class ControlIconDisplay extends Control {
	private TextureAtlasSprite icon;

	public ControlIconDisplay(final IWidget parent, final float x, final float y, final ResourceLocation icon) {
		super(parent, x, y, 16.0f, 16.0f);
		this.icon = BinnieCore.proxy.getTextureAtlasSprite(icon);
	}

	@Override
	public void onRenderForeground() {
		CraftGUI.render.sprite(IPoint.ZERO, this.icon);
	}
}
