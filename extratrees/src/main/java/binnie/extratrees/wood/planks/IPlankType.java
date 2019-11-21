package binnie.extratrees.wood.planks;

import binnie.design.api.IDesignMaterial;
import forestry.api.arboriculture.IWoodType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IPlankType extends IDesignMaterial {
	String getDescription();

	IWoodType getWoodType();

	String getPlankTextureName();

	@SideOnly(Side.CLIENT)
	TextureAtlasSprite getSprite();

	@SideOnly(Side.CLIENT)
	void registerSprites(TextureMap map);
}
