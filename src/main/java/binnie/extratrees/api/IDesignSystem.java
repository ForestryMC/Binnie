package binnie.extratrees.api;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public interface IDesignSystem {
	@Nullable
	TextureAtlasSprite getPrimarySprite(final IPattern pattern);

	@Nullable
	TextureAtlasSprite getSecondarySprite(final IPattern pattern);

	void registerSprites();

	IDesignMaterial getDefaultMaterial();

	@Nullable
	IDesignMaterial getMaterial(final int p0);

	int getMaterialIndex(final IDesignMaterial p0);

	IDesignMaterial getDefaultMaterial2();

	ItemStack getAdhesive();

	IDesignMaterial getMaterial(final ItemStack p0);
}
