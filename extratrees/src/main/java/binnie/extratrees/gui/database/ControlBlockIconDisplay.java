package binnie.extratrees.gui.database;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.renderer.RenderUtil;

public class ControlBlockIconDisplay extends Control {
	TextureAtlasSprite icon;

	public ControlBlockIconDisplay(final IWidget parent, final int x, final int y, final TextureAtlasSprite icon) {
		super(parent, x, y, 18, 18);
		this.icon = icon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		RenderUtil.drawSprite(Point.ZERO, this.icon);
	}
}
