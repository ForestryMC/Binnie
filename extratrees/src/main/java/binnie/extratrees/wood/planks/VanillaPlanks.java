package binnie.extratrees.wood.planks;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.arboriculture.EnumVanillaWoodType;
import forestry.api.arboriculture.IWoodType;
import forestry.api.arboriculture.TreeManager;
import forestry.api.arboriculture.WoodBlockKind;

import binnie.core.util.I18N;

public enum VanillaPlanks implements IPlankType {
	OAK(11833434, EnumVanillaWoodType.OAK),
	SPRUCE(8412726, EnumVanillaWoodType.SPRUCE),
	BIRCH(14139781, EnumVanillaWoodType.BIRCH),
	JUNGLE(11632732, EnumVanillaWoodType.JUNGLE),
	ACACIA(12215095, EnumVanillaWoodType.ACACIA),
	BIG_OAK(4599061, EnumVanillaWoodType.DARK_OAK);

	private final IWoodType woodType;
	private final int color;
	@SideOnly(Side.CLIENT)
	private TextureAtlasSprite sprite;

	VanillaPlanks(final int color, IWoodType woodType) {
		this.color = color;
		this.woodType = woodType;
	}

	@Override
	public String getDesignMaterialName() {
		return I18N.localise("extratrees.block.planks.vanilla." + this.toString().toLowerCase());
	}

	@Override
	public String getDescription() {
		return I18N.localise("extratrees.block.planks.vanilla." + this.toString().toLowerCase() + ".desc");
	}

	public String getPlankTextureName() {
		return "minecraft:blocks/planks_" + name().toLowerCase();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public TextureAtlasSprite getSprite() {
		return sprite;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerSprites(TextureMap map) {
		sprite = map.registerSprite(new ResourceLocation(getPlankTextureName()));
	}

	@Override
	public int getColour() {
		return this.color;
	}

	@Override
	public ItemStack getStack() {
		return getStack(true);
	}

	@Override
	public IWoodType getWoodType() {
		return woodType;
	}

	@Override
	public ItemStack getStack(boolean fireproof) {
		return TreeManager.woodAccess.getStack(woodType, WoodBlockKind.PLANKS, fireproof);
	}
}
