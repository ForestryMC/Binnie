package binnie.design.api;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ILayout {
	IPattern getPattern();

	boolean isInverted();

	ILayout rotateRight();

	ILayout rotateLeft();

	ILayout flipHorizontal();

	ILayout flipVertical();

	ILayout invert();

	@SideOnly(Side.CLIENT)
	TextureAtlasSprite getPrimarySprite(IDesignSystem system);

	@SideOnly(Side.CLIENT)
	TextureAtlasSprite getSecondarySprite(IDesignSystem system);
}
