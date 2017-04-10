package binnie.extratrees.block;

import binnie.extratrees.api.IDesignMaterial;
import forestry.api.arboriculture.IWoodType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IPlankType extends IDesignMaterial {
	String getPlankTextureName();

	String getDescription();

	IWoodType getWoodType();
	
	@SideOnly(Side.CLIENT)
	TextureAtlasSprite getSprite();
	
	@SideOnly(Side.CLIENT)
	void registerSprites(TextureMap map);
}
