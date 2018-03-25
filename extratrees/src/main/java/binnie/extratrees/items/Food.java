package binnie.extratrees.items;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.recipes.RecipeManagers;
import forestry.core.fluids.Fluids;

import binnie.core.Mods;
import binnie.core.item.IItemMiscProvider;
import binnie.core.util.I18N;
import binnie.extratrees.liquid.Juice;
import binnie.extratrees.modules.ModuleCore;

public enum Food implements IItemMiscProvider {
	CRABAPPLE(2){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Apple").registerCropOre("Crabapple").registerOre(HARVESTCRAFT_LIST_ALLFRUIT);
		}
	},
	ORANGE(4){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Orange").registerOre(HARVESTCRAFT_LIST_ALLFRUIT).registerOre(HARVESTCRAFT_LIST_ALLCITRUS);
		}
	},
	KUMQUAT(2){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Kumquat").registerOre(HARVESTCRAFT_LIST_ALLFRUIT);
		}
	},
	LIME(2){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Lime").registerOre(HARVESTCRAFT_LIST_ALLFRUIT).registerOre(HARVESTCRAFT_LIST_ALLCITRUS);
		}
	},
	WILD_CHERRY(2){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Cherry").registerCropOre("WildCherry").registerOre(HARVESTCRAFT_LIST_ALLFRUIT);
		}
	},
	SOUR_CHERRY(2){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Cherry").registerCropOre("SourCherry").registerOre(HARVESTCRAFT_LIST_ALLFRUIT);
		}
	},
	BLACK_CHERRY(2){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Cherry").registerCropOre("BlackCherry").registerOre(HARVESTCRAFT_LIST_ALLFRUIT);
		}
	},
	Blackthorn(3){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Blackthorn").registerOre(HARVESTCRAFT_LIST_ALLFRUIT).registerOre(HARVESTCRAFT_LIST_ALLBERRY);
		}
	},
	CHERRY_PLUM(3){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Plum").registerCropOre("CherryPlum").registerOre(HARVESTCRAFT_LIST_ALLFRUIT);
		}
	},
	ALMOND(1){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Almond").registerOre(HARVESTCRAFT_LIST_ALLNUT);
		}
	},
	APRICOT(4){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Apricot").registerOre(HARVESTCRAFT_LIST_ALLFRUIT);
		}
	},
	GRAPEFRUIT(4){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Grapefruit").registerOre(HARVESTCRAFT_LIST_ALLFRUIT).registerOre(HARVESTCRAFT_LIST_ALLCITRUS);
		}
	},
	PEACH(4){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Peach").registerOre(HARVESTCRAFT_LIST_ALLFRUIT);
		}
	},
	SATSUMA(3){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Satsuma").registerCropOre("Orange").registerOre(HARVESTCRAFT_LIST_ALLFRUIT).registerOre(HARVESTCRAFT_LIST_ALLCITRUS);
		}
	},
	BUDDHA_HAND(3){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("BuddhaHand").registerCropOre("Citron").registerOre(HARVESTCRAFT_LIST_ALLFRUIT).registerOre(HARVESTCRAFT_LIST_ALLCITRUS);
		}
	},
	CITRON(3){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Citron").registerOre(HARVESTCRAFT_LIST_ALLFRUIT).registerOre(HARVESTCRAFT_LIST_ALLCITRUS);
		}
	},
	FINGER_LIME(3){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Lime").registerCropOre("FingerLime").registerOre(HARVESTCRAFT_LIST_ALLFRUIT).registerOre(HARVESTCRAFT_LIST_ALLCITRUS);
		}
	},
	KEY_LIME(2){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("KeyLime").registerCropOre("Lime").registerOre(HARVESTCRAFT_LIST_ALLFRUIT).registerOre(HARVESTCRAFT_LIST_ALLCITRUS);
		}
	},
	MANDERIN(3){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Orange").registerCropOre("Manderin").registerOre(HARVESTCRAFT_LIST_ALLFRUIT).registerOre(HARVESTCRAFT_LIST_ALLCITRUS);
		}
	},
	NECTARINE(3){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Peach").registerCropOre("Nectarine").registerOre(HARVESTCRAFT_LIST_ALLFRUIT);
		}
	},
	POMELO(3){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Pomelo").registerOre(HARVESTCRAFT_LIST_ALLFRUIT);
		}
	},
	TANGERINE(3){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Tangerine").registerCropOre("Orange").registerOre(HARVESTCRAFT_LIST_ALLFRUIT).registerOre(HARVESTCRAFT_LIST_ALLCITRUS);
		}
	},
	PEAR(4){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Pear").registerOre(HARVESTCRAFT_LIST_ALLFRUIT);
		}
	},
	SAND_PEAR(2){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("SandPear").registerOre(HARVESTCRAFT_LIST_ALLFRUIT);
		}
	},
	HAZELNUT(2){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Hazelnut").registerOre(HARVESTCRAFT_LIST_ALLNUT);
		}
	},
	BUTTERNUT(1){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Butternut").registerCropOre("Walnut").registerOre(HARVESTCRAFT_LIST_ALLNUT);
		}
	},
	BEECHNUT(0){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Beechnut").registerOre(HARVESTCRAFT_LIST_ALLNUT);
		}
	},
	PECAN(0){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Pecan").registerOre(HARVESTCRAFT_LIST_ALLNUT);
		}
	},
	BANANA(4){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Banana").registerOre(HARVESTCRAFT_LIST_ALLFRUIT);
		}
	},
	RED_BANANA(4){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("RedBanana").registerCropOre("Banana").registerOre(HARVESTCRAFT_LIST_ALLFRUIT);
		}
	},
	PLANTAIN(2){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Plantain").registerOre(HARVESTCRAFT_LIST_ALLFRUIT);
		}
	},
	BRAZIL_NUT(0){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("BrazilNut").registerOre(HARVESTCRAFT_LIST_ALLNUT);
		}
	},
	FIG(2){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Fig").registerOre(HARVESTCRAFT_LIST_ALLFRUIT);
		}
	},
	ACORN(0){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Acorn").registerOre(HARVESTCRAFT_LIST_ALLNUT);
		}
	},
	ELDERBERRY(1){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Elderberry").registerOre(HARVESTCRAFT_LIST_ALLFRUIT).registerOre(HARVESTCRAFT_LIST_ALLBERRY);
		}
	},
	OLIVE(1){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Olive");
		}
	},
	GINGKO_NUT(1){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("GingkoNut").registerOre(HARVESTCRAFT_LIST_ALLSPICE);
		}
	},
	COFFEE(0){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Coffee");
		}
	},
	OSANGE_ORANGE(1){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("OsangeOrange").registerOre(HARVESTCRAFT_LIST_ALLFRUIT).registerOre(HARVESTCRAFT_LIST_ALLCITRUS);
		}
	},
	CLOVE(0){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Clove").registerOre(HARVESTCRAFT_LIST_ALLSPICE);
		}
	},
	PAPAYIMAR(8){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Papayimar").registerOre(HARVESTCRAFT_LIST_ALLFRUIT);
		}
	},
	BLACKCURRANT(2){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Blackcurrant").registerOre(HARVESTCRAFT_LIST_ALLFRUIT).registerOre(HARVESTCRAFT_LIST_ALLBERRY);
		}
	},
	REDCURRANT(2){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Redcurrant").registerOre(HARVESTCRAFT_LIST_ALLFRUIT).registerOre(HARVESTCRAFT_LIST_ALLBERRY);
		}
	},
	BLACKBERRY(2){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Blackberry").registerOre(HARVESTCRAFT_LIST_ALLFRUIT).registerOre(HARVESTCRAFT_LIST_ALLBERRY);
		}
	},
	RASPBERRY(2){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Raspberry").registerOre(HARVESTCRAFT_LIST_ALLFRUIT).registerOre(HARVESTCRAFT_LIST_ALLBERRY);
		}
	},
	BLUEBERRY(2){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Blueberry").registerOre(HARVESTCRAFT_LIST_ALLFRUIT).registerOre(HARVESTCRAFT_LIST_ALLBERRY);
		}
	},
	CRANBERRY(2){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Cranberry").registerOre(HARVESTCRAFT_LIST_ALLFRUIT).registerOre(HARVESTCRAFT_LIST_ALLBERRY);
		}
	},
	JUNIPER(0){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Juniper").registerOre(HARVESTCRAFT_LIST_ALLFRUIT);
		}
	},
	GOOSEBERRY(2){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Gooseberry").registerOre(HARVESTCRAFT_LIST_ALLFRUIT).registerOre(HARVESTCRAFT_LIST_ALLBERRY);
		}
	},
	GOLDEN_RASPBERRY(2){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("GoldenRaspberry").registerCropOre("Goldenraspberry").registerOre(HARVESTCRAFT_LIST_ALLFRUIT).registerOre(HARVESTCRAFT_LIST_ALLBERRY);
		}
	},
	COCONUT(2){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Coconut").registerOre(HARVESTCRAFT_LIST_ALLNUT);
		}
	},
	CASHEW(0){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Cashew").registerOre(HARVESTCRAFT_LIST_ALLNUT);
		}
	},
	AVACADO(2){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Avocado").registerOre(HARVESTCRAFT_LIST_ALLFRUIT);
		}
	},
	NUTMEG(0){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Nutmeg").registerOre(HARVESTCRAFT_LIST_ALLSPICE);
		}
	},
	ALLSPICE(0){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Allspice").registerOre(HARVESTCRAFT_LIST_ALLSPICE);
		}
	},
	CHILLI(2){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Chilli").registerCropOre("Chilipepper").registerOre(HARVESTCRAFT_LIST_ALLPEPPER);
		}
	},
	STAR_ANISE(0){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Staranise").registerCropOre("StarAnise").registerOre(HARVESTCRAFT_LIST_ALLSPICE);
		}
	},
	MANGO(4){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Mango").registerOre(HARVESTCRAFT_LIST_ALLFRUIT);
		}
	},
	STARFRUIT(2){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Starfruit").registerOre(HARVESTCRAFT_LIST_ALLFRUIT);
		}
	},
	CANDLENUT(0){
		@Override
		protected void registerOreDictEntries() {
			registerCropOre("Candlenut").registerCropOre("Candleberry").registerCropOre("Candle").registerOre(HARVESTCRAFT_LIST_ALLNUT);
		}
	};


	private final static String HARVESTCRAFT_LIST_ALLFRUIT = "listAllfruit";
	private final static String HARVESTCRAFT_LIST_ALLNUT = "listAllnut";
	private final static String HARVESTCRAFT_LIST_ALLSPICE = "listAllspice";
	private final static String HARVESTCRAFT_LIST_ALLCITRUS = "listAllcitrus";
	private final static String HARVESTCRAFT_LIST_ALLBERRY = "listAllberry";
	private final static String HARVESTCRAFT_LIST_ALLPEPPER = "listAllpepper";

	public static final Food[] VALUES = values();

	private final int hunger;
	private final List<String> ores;

	Food(final int hunger) {
		this.ores = new ArrayList<>();
		this.hunger = hunger;
	}

	public static void registerOreDictionary() {
		for(Food food : values()){
			food.registerOreDictEntries();
		}
	}

	protected void registerOreDictEntries(){

	}

	public boolean isEdible() {
		return this.hunger > 0;
	}

	public int getHealth() {
		return this.hunger;
	}

	@Override
	public boolean isActive() {
		return true;
	}

	@Override
	public String getDisplayName(final ItemStack stack) {
		return I18N.localise("extratrees.item.food." + this.name().toLowerCase());
	}

	@Override
	public ItemStack get(final int i) {
		return new ItemStack(ModuleCore.itemFood, i, this.ordinal());
	}

	@Override
	public String getModelPath() {
		return name().toLowerCase(Locale.ENGLISH);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(final List<String> tooltip) {
	}

	public void addJuice(final Juice juice, final int time, final int amount, final int mulch) {
		RecipeManagers.squeezerManager.addRecipe(time, this.get(1), Fluids.JUICE.getFluid(amount), Mods.Forestry.stack("mulch"), mulch);
	}

	public void addJuice(final int time, final int amount, final int mulch) {
		RecipeManagers.squeezerManager.addRecipe(time, this.get(1), Fluids.JUICE.getFluid(amount), Mods.Forestry.stack("mulch"), mulch);
	}

	public void addOil(final int time, final int amount, final int mulch) {
		RecipeManagers.squeezerManager.addRecipe(time, this.get(1), Fluids.SEED_OIL.getFluid(amount), Mods.Forestry.stack("mulch"), mulch);
	}

	protected Food registerCropOre(final String string) {
		return registerOre("crop" + string);
	}

	protected Food registerOre(final String string) {
		OreDictionary.registerOre(string, this.get(1));
		this.ores.add(string);
		return this;
	}

	public Collection<String> getOres() {
		return this.ores;
	}
}
