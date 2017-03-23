package binnie.craftgui.minecraft.control;

import binnie.core.BinnieCore;
import binnie.core.resource.BinnieSprite;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.Point;
import binnie.craftgui.core.renderer.RenderUtil;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ControlIconDisplay extends Control {
	private final TextureAtlasSprite sprite;

	public ControlIconDisplay(final IWidget parent, final int x, final int y) {
		super(parent, x, y, 16, 16);
		this.sprite = null;
	}
	
	public ControlIconDisplay(final IWidget parent, final int x, final int y, final ResourceLocation icon) {
		super(parent, x, y, 16, 16);
		this.sprite = BinnieCore.getBinnieProxy().getTextureAtlasSprite(icon);
	}
	
	public ControlIconDisplay(final IWidget parent, final int x, final int y, BinnieSprite sprite) {
		super(parent, x, y, 16, 16);
		this.sprite = sprite.getSprite();
	}
	
	public ControlIconDisplay(final IWidget parent, final int x, final int y, TextureAtlasSprite sprite) {
		super(parent, x, y, 16, 16);
		this.sprite = sprite;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderForeground(int guiWidth, int guiHeight) {
		RenderUtil.drawSprite(Point.ZERO, this.sprite);
	}
}
