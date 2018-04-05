package binnie.extratrees.carpentry;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

import binnie.design.api.DesignAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.extratrees.ExtraTrees;
import binnie.extratrees.api.CarpentryManager;
import binnie.design.api.IDesignMaterial;
import binnie.design.api.IDesignSystem;
import binnie.design.api.IPattern;
import binnie.design.EnumPattern;
import binnie.extratrees.items.ExtraTreeItems;
import binnie.extratrees.wood.planks.ExtraTreePlanks;

public enum DesignSystem implements IDesignSystem {
	Wood,
	Glass;

	private final Map<Integer, TextureAtlasSprite> primary;
	private final Map<Integer, TextureAtlasSprite> secondary;

	DesignSystem() {
		this.primary = new HashMap<>();
		this.secondary = new HashMap<>();
		DesignAPI.manager.registerDesignSystem(this);
	}

	@Override
	public IDesignMaterial getDefaultMaterial() {
		switch (this) {
			case Glass: {
				return GlassType.get(0);
			}
			case Wood: {
				return ExtraTreePlanks.Fir;
			}
			default: {
				throw new IllegalStateException("Unknown design system: " + this);
			}
		}
	}

	@Override
	public IDesignMaterial getDefaultMaterial2() {
		switch (this) {
			case Glass: {
				return GlassType.get(1);
			}
			case Wood: {
				return ExtraTreePlanks.Whitebeam;
			}
			default: {
				throw new IllegalStateException("Unknown design system: " + this);
			}
		}
	}

	@Override
	public IDesignMaterial getMaterial(final int id) {
		switch (this) {
			case Glass: {
				return GlassType.get(id);
			}
			case Wood: {
				return CarpentryManager.carpentryInterface.getWoodMaterial(id);
			}
			default: {
				throw new IllegalStateException("Unknown design system: " + this);
			}
		}
	}

	@Override
	public int getMaterialIndex(final IDesignMaterial id) {
		switch (this) {
			case Glass: {
				return GlassType.getIndex(id);
			}
			case Wood: {
				return CarpentryManager.carpentryInterface.getCarpentryWoodIndex(id);
			}
			default: {
				throw new IllegalStateException("Unknown design system: " + this);
			}
		}
	}

	public String getTexturePath() {
		switch (this) {
			case Glass: {
				return "glass";
			}
			case Wood: {
				return "patterns";
			}
			default: {
				throw new IllegalStateException("Unknown design system: " + this);
			}
		}
	}

	@Override
	@Nullable
	public IDesignMaterial getMaterial(final ItemStack stack) {
		switch (this) {
			case Glass: {
				return GlassType.get(stack);
			}
			case Wood: {
				return CarpentryManager.carpentryInterface.getWoodMaterial(stack);
			}
			default: {
				throw new IllegalStateException("Unknown design system: " + this);
			}
		}
	}

	@Override
	public ItemStack getAdhesive() {
		switch (this) {
			case Glass: {
				return ExtraTreeItems.GLASS_FITTING.get(1);
			}
			case Wood: {
				return ExtraTreeItems.WOOD_WAX.get(1);
			}
			default: {
				throw new IllegalStateException("Unknown design system: " + this);
			}
		}
	}

	@Override
	@Nullable
	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getPrimarySprite(final IPattern pattern) {
		if (pattern instanceof EnumPattern) {
			return this.primary.get(((EnumPattern) pattern).ordinal());
		}
		return null;
	}

	@Override
	@Nullable
	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getSecondarySprite(final IPattern pattern) {
		if (pattern instanceof EnumPattern) {
			return this.secondary.get(((EnumPattern) pattern).ordinal());
		}
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerSprites() {
		TextureMap textureMap = Minecraft.getMinecraft().getTextureMapBlocks();
		for (final EnumPattern pattern : EnumPattern.values()) {
			ResourceLocation primaryLocation = new ResourceLocation(getModId(), "blocks/" + getTexturePath() + '/' + pattern.toString().toLowerCase() + ".0");
			ResourceLocation secondaryLocation = new ResourceLocation(getModId(), "blocks/" + getTexturePath() + '/' + pattern.toString().toLowerCase() + ".1");
			this.primary.put(pattern.ordinal(), textureMap.registerSprite(primaryLocation));
			this.secondary.put(pattern.ordinal(), textureMap.registerSprite(secondaryLocation));
		}
	}

	public String getModId() {
		return ExtraTrees.instance.getModId();
	}
}
