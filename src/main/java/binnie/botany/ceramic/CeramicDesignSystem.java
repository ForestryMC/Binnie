package binnie.botany.ceramic;

import java.util.HashMap;
import java.util.Map;

import binnie.botany.Botany;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.botany.items.BotanyItems;
import binnie.core.AbstractMod;
import binnie.extratrees.api.IDesignMaterial;
import binnie.extratrees.api.IDesignSystem;
import binnie.extratrees.api.IPattern;
import binnie.extratrees.carpentry.DesignerManager;
import binnie.extratrees.carpentry.EnumPattern;
import forestry.core.render.TextureManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CeramicDesignSystem implements IDesignSystem {
    public static CeramicDesignSystem instance = new CeramicDesignSystem();
	Map<Integer, TextureAtlasSprite> primary;
	Map<Integer, TextureAtlasSprite> secondary;

    CeramicDesignSystem() {
		this.primary = new HashMap<Integer, TextureAtlasSprite>();
		this.secondary = new HashMap<Integer, TextureAtlasSprite>();
        DesignerManager.instance.registerDesignSystem(this);
    }

    @Override
    public IDesignMaterial getDefaultMaterial() {
        return CeramicColor.get(EnumFlowerColor.White);
    }

    @Override
    public IDesignMaterial getDefaultMaterial2() {
        return CeramicColor.get(EnumFlowerColor.Black);
    }

    @Override
    public IDesignMaterial getMaterial(final int id) {
        return CeramicColor.get(EnumFlowerColor.get(id));
    }

    @Override
    public int getMaterialIndex(final IDesignMaterial id) {
        return ((CeramicColor) id).color.ordinal();
    }

    public String getTexturePath() {
        return "blocks/ceramic";
    }
    
    @Override
    public TextureAtlasSprite getPrimarySprite(IPattern pattern) {
		if (pattern instanceof EnumPattern) {
			return this.primary.get(((EnumPattern) pattern).ordinal());
		}
		return null;
    }
    
    @Override
    public TextureAtlasSprite getSecondarySprite(IPattern pattern) {
		if (pattern instanceof EnumPattern) {
			return this.secondary.get(((EnumPattern) pattern).ordinal());
		}
		return null;
    }
    
    @Override
    public void registerSprites() {
		for (EnumPattern pattern : EnumPattern.values()) {
			this.primary.put(pattern.ordinal(), TextureManager.registerSprite(new ResourceLocation(getMod().getModID(), getTexturePath() + "/" + pattern.toString().toLowerCase() + ".0")));
			this.secondary.put(pattern.ordinal(), TextureManager.registerSprite(new ResourceLocation(getMod().getModID(), getTexturePath() + "/" + pattern.toString().toLowerCase() + ".1")));
		}
    }

    public AbstractMod getMod() {
        return Botany.instance;
    }

    @Override
    public ItemStack getAdhesive() {
        return BotanyItems.Mortar.get(1);
    }

    @Override
    public IDesignMaterial getMaterial(final ItemStack itemStack) {
        return (itemStack.getItem() == Item.getItemFromBlock(Botany.ceramic)) ? this.getMaterial(itemStack.getItemDamage()) : null;
    }

}
