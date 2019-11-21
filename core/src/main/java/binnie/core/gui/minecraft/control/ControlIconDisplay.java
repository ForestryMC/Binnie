package binnie.core.gui.minecraft.control;

import binnie.core.BinnieCore;
import binnie.core.api.gui.IBinnieSprite;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.renderer.RenderUtil;
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

	public ControlIconDisplay(final IWidget parent, final int x, final int y, IBinnieSprite sprite) {
		super(parent, x, y, 16, 16);
		this.sprite = sprite.getSprite();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderForeground(int guiWidth, int guiHeight) {
		RenderUtil.drawSprite(Point.ZERO, this.sprite);
	}
}
