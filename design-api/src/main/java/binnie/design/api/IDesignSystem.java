package binnie.design.api;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public interface IDesignSystem {
	@Nullable
	@SideOnly(Side.CLIENT)
	TextureAtlasSprite getPrimarySprite(final IPattern pattern);

	@Nullable
	@SideOnly(Side.CLIENT)
	TextureAtlasSprite getSecondarySprite(final IPattern pattern);

	@SideOnly(Side.CLIENT)
	void registerSprites();

	IDesignMaterial getDefaultMaterial();

	@Nullable
	IDesignMaterial getMaterial(final int p0);

	int getMaterialIndex(final IDesignMaterial p0);

	IDesignMaterial getDefaultMaterial2();

	ItemStack getAdhesive();

	@Nullable
	IDesignMaterial getMaterial(final ItemStack p0);
}
