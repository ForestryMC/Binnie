package binnie.extratrees.machines.craftgui;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.renderer.RenderUtil;

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
