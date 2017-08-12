package binnie.extratrees.api;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.core.ISpriteRegister;

public interface IPattern extends ISpriteRegister {
	@SideOnly(Side.CLIENT)
	TextureAtlasSprite getPrimarySprite(IDesignSystem system);

	@SideOnly(Side.CLIENT)
	TextureAtlasSprite getSecondarySprite(IDesignSystem system);

	ILayout getRotation();

	ILayout getHorizontalFlip();

	int getIndex();
}
