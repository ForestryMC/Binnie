package binnie.extratrees.carpentry;

import binnie.core.AbstractMod;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.api.CarpentryManager;
import binnie.extratrees.api.IDesignMaterial;
import binnie.extratrees.api.IDesignSystem;
import binnie.extratrees.api.IPattern;
import binnie.extratrees.block.PlankType;
import binnie.extratrees.item.ExtraTreeItems;
import forestry.core.render.TextureManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public enum DesignSystem implements IDesignSystem {
    Wood,
    Glass;

	Map<Integer, TextureAtlasSprite> primary;
	Map<Integer, TextureAtlasSprite> secondary;

    DesignSystem() {
		this.primary = new HashMap<Integer, TextureAtlasSprite>();
		this.secondary = new HashMap<Integer, TextureAtlasSprite>();
        DesignerManager.instance.registerDesignSystem(this);
    }

    @Override
    public IDesignMaterial getDefaultMaterial() {
        switch (this) {
            case Glass: {
                return GlassType.get(0);
            }
            case Wood: {
                return PlankType.ExtraTreePlanks.Fir;
            }
            default: {
                return null;
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
                return PlankType.ExtraTreePlanks.Whitebeam;
            }
            default: {
                return null;
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
                return null;
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
                return 0;
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
                return "";
            }
        }
    }

    @Override
    public IDesignMaterial getMaterial(final ItemStack stack) {
        switch (this) {
            case Glass: {
                return GlassType.get(stack);
            }
            case Wood: {
                return CarpentryManager.carpentryInterface.getWoodMaterial(stack);
            }
            default: {
                return null;
            }
        }
    }

    @Override
    public ItemStack getAdhesive() {
        switch (this) {
            case Glass: {
                return ExtraTreeItems.GlassFitting.get(1);
            }
            case Wood: {
                return ExtraTreeItems.WoodWax.get(1);
            }
            default: {
                return null;
            }
        }
    }

	@Override
	public TextureAtlasSprite getPrimarySprite(final IPattern pattern) {
		if (pattern instanceof EnumPattern) {
			return this.primary.get(((EnumPattern) pattern).ordinal());
		}
		return null;
	}

	@Override
	public TextureAtlasSprite getSecondarySprite(final IPattern pattern) {
		if (pattern instanceof EnumPattern) {
			return this.secondary.get(((EnumPattern) pattern).ordinal());
		}
		return null;
	}

	@Override
	public void registerSprites() {
		for (final EnumPattern pattern : EnumPattern.values()) {
			this.primary.put(pattern.ordinal(), TextureManager.registerSprite(new ResourceLocation(getMod().getModID(), getTexturePath() + "/" + pattern.toString().toLowerCase() + ".0")));
			this.secondary.put(pattern.ordinal(), TextureManager.registerSprite(new ResourceLocation(getMod().getModID(), getTexturePath() + "/" + pattern.toString().toLowerCase() + ".1")));
		}
	}

    public AbstractMod getMod() {
        return ExtraTrees.instance;
    }
}
