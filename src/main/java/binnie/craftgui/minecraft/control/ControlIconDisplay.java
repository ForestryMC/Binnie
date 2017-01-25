package binnie.craftgui.minecraft.control;

import binnie.core.BinnieCore;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.renderer.RenderUtil;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;

public class ControlIconDisplay extends Control {
	private TextureAtlasSprite icon;

	public ControlIconDisplay(final IWidget parent, final int x, final int y, final ResourceLocation icon) {
		super(parent, x, y, 16, 16);
		this.icon = BinnieCore.proxy.getTextureAtlasSprite(icon);
	}

	@Override
	public void onRenderForeground(int guiWidth, int guiHeight) {
		RenderUtil.drawSprite(IPoint.ZERO, this.icon);
	}
}
