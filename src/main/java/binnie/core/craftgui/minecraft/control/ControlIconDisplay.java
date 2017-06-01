package binnie.core.craftgui.minecraft.control;

import binnie.core.BinnieCore;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.renderer.RenderUtil;
import binnie.core.resource.BinnieSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
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
