package binnie.botany.ceramic;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.botany.Botany;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.botany.items.BotanyItems;
import binnie.core.AbstractMod;
import binnie.extratrees.api.IDesignMaterial;
import binnie.extratrees.api.IDesignSystem;
import binnie.extratrees.api.IPattern;
import binnie.extratrees.carpentry.DesignerManager;
import binnie.extratrees.carpentry.EnumPattern;

public class CeramicDesignSystem implements IDesignSystem {
	public static CeramicDesignSystem instance = new CeramicDesignSystem();
	Map<Integer, TextureAtlasSprite> primary;
	Map<Integer, TextureAtlasSprite> secondary;

	CeramicDesignSystem() {
		primary = new HashMap<>();
		secondary = new HashMap<>();
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
	public IDesignMaterial getMaterial(int id) {
		return CeramicColor.get(EnumFlowerColor.get(id));
	}

	@Override
	public int getMaterialIndex(IDesignMaterial id) {
		return ((CeramicColor) id).color.ordinal();
	}

	public String getTexturePath() {
		return "blocks/ceramic";
	}

	@Override
	@Nullable
	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getPrimarySprite(IPattern pattern) {
		if (pattern instanceof EnumPattern) {
			return primary.get(((EnumPattern) pattern).ordinal());
		}
		return null;
	}

	@Override
	@Nullable
	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getSecondarySprite(IPattern pattern) {
		if (pattern instanceof EnumPattern) {
			return secondary.get(((EnumPattern) pattern).ordinal());
		}
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerSprites() {
		TextureMap textureMap = Minecraft.getMinecraft().getTextureMapBlocks();
		for (EnumPattern pattern : EnumPattern.values()) {
			ResourceLocation primaryLocation = new ResourceLocation(getMod().getModID(), getTexturePath() + "/" + pattern.toString().toLowerCase() + ".0");
			ResourceLocation secondaryLocation = new ResourceLocation(getMod().getModID(), getTexturePath() + "/" + pattern.toString().toLowerCase() + ".1");
			primary.put(pattern.ordinal(), textureMap.registerSprite(primaryLocation));
			secondary.put(pattern.ordinal(), textureMap.registerSprite(secondaryLocation));
		}
	}

	public AbstractMod getMod() {
		return Botany.instance;
	}

	@Override
	public ItemStack getAdhesive() {
		return BotanyItems.MORTAR.get(1);
	}

	@Override
	@Nullable
	public IDesignMaterial getMaterial(ItemStack itemStack) {
		return (itemStack.getItem() == Item.getItemFromBlock(Botany.gardening().ceramic))
				? getMaterial(itemStack.getItemDamage())
				: null;
	}
}
