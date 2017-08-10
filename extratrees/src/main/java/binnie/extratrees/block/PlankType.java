package binnie.extratrees.block;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.arboriculture.EnumForestryWoodType;
import forestry.api.arboriculture.EnumVanillaWoodType;
import forestry.api.arboriculture.IWoodType;
import forestry.api.arboriculture.TreeManager;
import forestry.api.arboriculture.WoodBlockKind;

import binnie.core.Constants;
import binnie.core.util.I18N;
import binnie.extratrees.api.CarpentryManager;

public class PlankType {
	public static final int MAX_PLANKS = 256;

	public static void setup() {
		for (final VanillaPlanks plank : VanillaPlanks.values()) {
			CarpentryManager.carpentryInterface.registerCarpentryWood(plank.ordinal(), plank);
		}
		for (final ExtraTreePlanks plank2 : ExtraTreePlanks.values()) {
			CarpentryManager.carpentryInterface.registerCarpentryWood(plank2.ordinal() + 32, plank2);
		}
		for (final ForestryPlanks plank3 : ForestryPlanks.values()) {
			CarpentryManager.carpentryInterface.registerCarpentryWood(plank3.ordinal() + 128, plank3);
		}
		/*for (final ExtraBiomesPlank plank4 : ExtraBiomesPlank.values()) {
			CarpentryManager.carpentryInterface.registerCarpentryWood(plank4.ordinal() + 192, plank4);
		}*/
	}

	public enum ExtraTreePlanks implements IPlankType, IFenceProvider, IStringSerializable {
		Fir(12815444),
		Cedar(14181940),
		Hemlock(13088108),
		Cypress(16169052),
		Fig(13142058),
		Beech(14784849),
		Alder(12092755),
		Hazel(13480341),
		Hornbeam(12818528),
		Box(16511430),
		Butternut(15510138),
		Hickory(14333070),
		Whitebeam(13222585),
		Elm(15772004),
		Apple(6305064),
		Yew(14722426),
		Pear(12093805),
		Hawthorn(13402978),
		Rowan(13610394),
		Elder(12489337),
		Maclura(15970862),
		Syzgium(15123393),
		Brazilwood(7487063),
		Logwood(10762028),
		Iroko(7681024),
		Locust(12816736),
		Eucalyptus(16165771),
		Purpleheart(5970991),
		Ash(16107368),
		Holly(16512743),
		Olive(11578760),
		Sweetgum(13997656),
		Rosewood(7738624),
		Gingko(16050106),
		PinkIvory(15502496),
		Banana(9466691);

		public static final ExtraTreePlanks[] VALUES = values();
		private final int color;
		private EnumETLog woodType;
		@SideOnly(Side.CLIENT)
		private TextureAtlasSprite sprite;

		ExtraTreePlanks(final int color) {
			this.color = color;
		}

		public static void initWoodTypes() {
			Fir.setWoodType(EnumETLog.Fir);
			Cedar.setWoodType(EnumETLog.Cedar);
			Hemlock.setWoodType(EnumETLog.Hemlock);
			Cypress.setWoodType(EnumETLog.Cypress);
			Fig.setWoodType(EnumETLog.Fig);
			Beech.setWoodType(EnumETLog.Beech);
			Alder.setWoodType(EnumETLog.Alder);
			Hazel.setWoodType(EnumETLog.Hazel);
			Hornbeam.setWoodType(EnumETLog.Hornbeam);
			Box.setWoodType(EnumETLog.Box);
			Butternut.setWoodType(EnumETLog.Butternut);
			Hickory.setWoodType(EnumETLog.Hickory);
			Whitebeam.setWoodType(EnumETLog.Whitebeam);
			Elm.setWoodType(EnumETLog.Elm);
			Apple.setWoodType(EnumETLog.Apple);
			Yew.setWoodType(EnumETLog.Yew);
			Pear.setWoodType(EnumETLog.Pear);
			Hawthorn.setWoodType(EnumETLog.Hawthorn);
			Rowan.setWoodType(EnumETLog.Rowan);
			Elder.setWoodType(EnumETLog.Elder);
			Maclura.setWoodType(EnumETLog.Maclura);
			Syzgium.setWoodType(EnumETLog.Syzgium);
			Brazilwood.setWoodType(EnumETLog.Brazilwood);
			Logwood.setWoodType(EnumETLog.Logwood);
			Iroko.setWoodType(EnumETLog.Iroko);
			Locust.setWoodType(EnumETLog.Locust);
			Eucalyptus.setWoodType(EnumETLog.Eucalyptus);
			Purpleheart.setWoodType(EnumETLog.Purpleheart);
			Ash.setWoodType(EnumETLog.Ash);
			Holly.setWoodType(EnumETLog.Holly);
			Olive.setWoodType(EnumETLog.Olive);
			Sweetgum.setWoodType(EnumETLog.Sweetgum);
			Rosewood.setWoodType(EnumETLog.Rosewood);
			Gingko.setWoodType(EnumETLog.Gingko);
			PinkIvory.setWoodType(EnumETLog.PinkIvory);
			Banana.setWoodType(EnumETLog.Banana);
		}

		@Override
		public EnumETLog getWoodType() {
			return woodType;
		}

		public void setWoodType(EnumETLog woodType) {
			this.woodType = woodType;
		}

		@Override
		public String getName() {
			return "block.planks." + this.toString().toLowerCase();
		}

		@Override
		public String getDesignMaterialName() {
			return I18N.localise("extratrees.block.planks." + this.toString().toLowerCase());
		}

		@Override
		public String getPlankTextureName() {
			return Constants.EXTRA_TREES_MOD_ID + ":blocks/planks/" + name();
		}

		@Override
		public String getDescription() {
			return I18N.localise("extratrees.block.planks." + this.toString().toLowerCase() + ".desc");
		}

		@Override
		public int getColour() {
			return this.color;
		}

		@Override
		public ItemStack getStack(boolean fireproof) {
			return TreeManager.woodAccess.getStack(woodType, WoodBlockKind.PLANKS, fireproof);
		}

		@Override
		public ItemStack getStack() {
			return getStack(true);
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
		public ItemStack getFence() {
			return TreeManager.woodAccess.getStack(woodType, WoodBlockKind.FENCE, false);
		}
	}

	public enum VanillaPlanks implements IPlankType {
		OAK(11833434, EnumVanillaWoodType.OAK),
		SPRUCE(8412726, EnumVanillaWoodType.SPRUCE),
		BIRCH(14139781, EnumVanillaWoodType.BIRCH),
		JUNGLE(11632732, EnumVanillaWoodType.JUNGLE),
		ACACIA(12215095, EnumVanillaWoodType.ACACIA),
		BIG_OAK(4599061, EnumVanillaWoodType.DARK_OAK);

		private final IWoodType woodType;
		private int color;
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

		@Override
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

		@Override
		public String getPlankTextureName() {
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


	//TODO: extrabiomes 1.10.2
	/*public enum ExtraBiomesPlank implements IPlankType {
		Redwood(10185538),
		Fir(8288074),
		Acacia(12561022);

		int color;

		ExtraBiomesPlank(final int color) {
			this.color = color;
		}

		@Override
		public String getDisplayName() {
			return I18N.localise("extratrees.block.planks.ebxl." + this.toString().toLowerCase());
		}


		@Override
		public String getPlankTextureName() {
			return "";
		}

		@Override
		public String getDescription() {
			return I18N.localise("extratrees.block.planks.ebxl." + this.toString().toLowerCase() + ".desc");
		}

		@Override
		public int getColor() {
			return this.color;
		}
		
		@Override
		public ItemStack getStack() {
			return getStack(true);
		}

		@Override
		public ItemStack getStack(boolean fireproof) {
			try {
				final Class clss = Class.forName("extrabiomes.api.Stuff");
				Optional planks = (Optional) clss.getField("planks").get(null);
				if (planks.isPresent()) {
					final Block block = (Block) planks.get();
					return new ItemStack(block, 1, this.ordinal());
				} else {
					return null;
				}
			} catch (Exception e) {
				return null;
			}
		}

		@Override
		public IIcon getIcon() {
			if (this.getStack() != null) {
				final int meta = this.getStack().getItemDamage();
				final Block block = ((ItemBlock) this.getStack().getItem()).field_150939_a;
				return block.getIcon(2, meta);
			}
			return null;
		}
	}*/
}
