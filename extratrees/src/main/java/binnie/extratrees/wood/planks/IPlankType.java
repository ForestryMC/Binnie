package binnie.extratrees.wood.planks;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.arboriculture.IWoodType;

import binnie.design.api.IDesignMaterial;

public interface IPlankType extends IDesignMaterial {
	String getDescription();

	IWoodType getWoodType();

	String getPlankTextureName();

	@SideOnly(Side.CLIENT)
	TextureAtlasSprite getSprite();

	@SideOnly(Side.CLIENT)
	void registerSprites(TextureMap map);
}
