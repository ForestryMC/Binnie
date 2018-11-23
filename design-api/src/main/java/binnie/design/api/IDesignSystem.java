package binnie.design.api;

import javax.annotation.Nullable;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IDesignSystem {
	@Nullable
	@SideOnly(Side.CLIENT)
	TextureAtlasSprite getPrimarySprite(IPattern pattern);

	@Nullable
	@SideOnly(Side.CLIENT)
	TextureAtlasSprite getSecondarySprite(IPattern pattern);

	@SideOnly(Side.CLIENT)
	void registerSprites();

	IDesignMaterial getDefaultMaterial();

	@Nullable
	IDesignMaterial getMaterial(int p0);

	int getMaterialIndex(IDesignMaterial p0);

	IDesignMaterial getDefaultMaterial2();

	ItemStack getAdhesive();

	@Nullable
	IDesignMaterial getMaterial(ItemStack p0);
}
