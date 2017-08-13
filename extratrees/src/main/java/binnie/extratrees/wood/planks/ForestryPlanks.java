package binnie.extratrees.wood.planks;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.arboriculture.EnumForestryWoodType;
import forestry.api.arboriculture.IWoodType;
import forestry.api.arboriculture.TreeManager;
import forestry.api.arboriculture.WoodBlockKind;

import binnie.core.util.I18N;
import binnie.extratrees.wood.IFenceProvider;

public enum ForestryPlanks implements IPlankType, IFenceProvider {
	LARCH(14131085, EnumForestryWoodType.LARCH),
	TEAK(8223075, EnumForestryWoodType.TEAK),
	ACACIA(9745287, EnumForestryWoodType.ACACIA),
	LIME(13544048, EnumForestryWoodType.LIME),
	CHESTNUT(12298845, EnumForestryWoodType.CHESTNUT),
	WENGE(6182474, EnumForestryWoodType.WENGE),
	BAOBAB(9608290, EnumForestryWoodType.BAOBAB),
	SEQUOIA(10050135, EnumForestryWoodType.SEQUOIA),
	KAPOK(8156212, EnumForestryWoodType.KAPOK),
	EBONY(3946288, EnumForestryWoodType.EBONY),
	MAHOGANY(7749432, EnumForestryWoodType.MAHOGANY),
	BALSA(11117209, EnumForestryWoodType.BALSA),
	WILLOW(11710818, EnumForestryWoodType.WILLOW),
	WALNUT(6836802, EnumForestryWoodType.WALNUT),
	GREENHEART(5144156, EnumForestryWoodType.GREENHEART),
	CHERRY(11895348, EnumForestryWoodType.CHERRY),
	MAHOE(8362154, EnumForestryWoodType.MAHOE),
	POPLAR(13619074, EnumForestryWoodType.POPLAR),
	PALM(13271115, EnumForestryWoodType.PALM),
	PAPAYA(14470005, EnumForestryWoodType.PAPAYA),
	PINE(12885585, EnumForestryWoodType.PINE),
	PLUM(11364479, EnumForestryWoodType.PLUM),
	MAPLE(11431211, EnumForestryWoodType.MAPLE),
	CITRUS(10266653, EnumForestryWoodType.CITRUS),
	GIGANTEUM(5186590, EnumForestryWoodType.GIGANTEUM),
	IPE(5057822, EnumForestryWoodType.IPE),
	PADAUK(11756341, EnumForestryWoodType.PADAUK),
	COCOBOLO(7541506, EnumForestryWoodType.COCOBOLO),
	ZEBRAWOOD(10912334, EnumForestryWoodType.ZEBRAWOOD);

	private final IWoodType woodType;
	private final int color;
	@SideOnly(Side.CLIENT)
	private TextureAtlasSprite sprite;

	ForestryPlanks(final int color, IWoodType woodType) {
		this.color = color;
		this.woodType = woodType;
	}

	private String getPlankTextureName() {
		return "forestry:blocks/wood/planks." + name().toLowerCase();
	}

	@Override
	public String getDesignMaterialName() {
		return I18N.localise("extratrees.block.planks.forestry." + this.toString().toLowerCase());
	}

	@Override
	public String getDescription() {
		return I18N.localise("extratrees.block.planks.forestry." + this.toString().toLowerCase() + ".desc");
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
	public IWoodType getWoodType() {
		return woodType;
	}

	@Override
	public ItemStack getStack(boolean fireproof) {
		return TreeManager.woodAccess.getStack(woodType, WoodBlockKind.PLANKS, fireproof);
	}

	@Override
	public ItemStack getStack() {
		return getStack(true);
	}

	@Override
	public ItemStack getFence() {
		return TreeManager.woodAccess.getStack(woodType, WoodBlockKind.FENCE, false);
	}
}
