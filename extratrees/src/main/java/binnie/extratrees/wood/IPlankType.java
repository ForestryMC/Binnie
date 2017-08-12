package binnie.extratrees.wood;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.arboriculture.IWoodType;

import binnie.extratrees.api.IDesignMaterial;

public interface IPlankType extends IDesignMaterial {
	String getPlankTextureName();

	String getDescription();

	IWoodType getWoodType();

	@SideOnly(Side.CLIENT)
	TextureAtlasSprite getSprite();

	@SideOnly(Side.CLIENT)
	void registerSprites(TextureMap map);
}
