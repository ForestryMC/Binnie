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
			registerOre("Apple").registerOre("Crabapple").registerOre("listAllfruit");
		}
	},
	ORANGE(4){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Orange").registerOre("listAllfruit").registerOre("listAllcitrus");
		}
	},
	KUMQUAT(2){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Kumquat").registerOre("listAllfruit");
		}
	},
	LIME(2){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Lime").registerOre("listAllfruit").registerOre("listAllcitrus");
		}
	},
	WILD_CHERRY(2){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Cherry").registerOre("WildCherry").registerOre("listAllfruit");
		}
	},
	SOUR_CHERRY(2){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Cherry").registerOre("SourCherry").registerOre("listAllfruit");
		}
	},
	BLACK_CHERRY(2){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Cherry").registerOre("BlackCherry").registerOre("listAllfruit");
		}
	},
	Blackthorn(3){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Blackthorn").registerOre("listAllfruit").registerOre("listAllberry");
		}
	},
	CHERRY_PLUM(3){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Plum").registerOre("CherryPlum").registerOre("listAllfruit");
		}
	},
	ALMOND(1){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Almond").registerOre("listAllnut");
		}
	},
	APRICOT(4){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Apricot").registerOre("listAllfruit");
		}
	},
	GRAPEFRUIT(4){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Grapefruit").registerOre("listAllfruit").registerOre("listAllcitrus");
		}
	},
	PEACH(4){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Peach").registerOre("listAllfruit");
		}
	},
	SATSUMA(3){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Satsuma").registerOre("Orange").registerOre("listAllfruit").registerOre("listAllcitrus");
		}
	},
	BUDDHA_HAND(3){
		@Override
		protected void registerOreDictEntries() {
			registerOre("BuddhaHand").registerOre("Citron").registerOre("listAllfruit").registerOre("listAllcitrus");
		}
	},
	CITRON(3){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Citron").registerOre("listAllfruit").registerOre("listAllcitrus");
		}
	},
	FINGER_LIME(3){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Lime").registerOre("FingerLime").registerOre("listAllfruit").registerOre("listAllcitrus");
		}
	},
	KEY_LIME(2){
		@Override
		protected void registerOreDictEntries() {
			registerOre("KeyLime").registerOre("Lime").registerOre("listAllfruit").registerOre("listAllcitrus");
		}
	},
	MANDERIN(3){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Orange").registerOre("Manderin").registerOre("listAllfruit").registerOre("listAllcitrus");
		}
	},
	NECTARINE(3){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Peach").registerOre("Nectarine").registerOre("listAllfruit");
		}
	},
	POMELO(3){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Pomelo").registerOre("listAllfruit");
		}
	},
	TANGERINE(3){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Tangerine").registerOre("Orange").registerOre("listAllfruit").registerOre("listAllcitrus");
		}
	},
	PEAR(4){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Pear").registerOre("listAllfruit");
		}
	},
	SAND_PEAR(2){
		@Override
		protected void registerOreDictEntries() {
			registerOre("SandPear").registerOre("listAllfruit");
		}
	},
	HAZELNUT(2){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Hazelnut").registerOre("listAllnut");
		}
	},
	BUTTERNUT(1){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Butternut").registerOre("Walnut").registerOre("listAllnut");
		}
	},
	BEECHNUT(0){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Beechnut").registerOre("listAllnut");
		}
	},
	PECAN(0){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Pecan").registerOre("listAllnut");
		}
	},
	BANANA(4){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Banana").registerOre("listAllfruit");
		}
	},
	RED_BANANA(4){
		@Override
		protected void registerOreDictEntries() {
			registerOre("RedBanana").registerOre("Banana").registerOre("listAllfruit");
		}
	},
	PLANTAIN(2){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Plantain").registerOre("listAllfruit");
		}
	},
	BRAZIL_NUT(0){
		@Override
		protected void registerOreDictEntries() {
			registerOre("BrazilNut").registerOre("listAllnut");
		}
	},
	FIG(2){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Fig").registerOre("listAllfruit");
		}
	},
	ACORN(0){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Acorn").registerOre("listAllnut");
		}
	},
	ELDERBERRY(1){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Elderberry").registerOre("listAllfruit").registerOre("listAllberry");
		}
	},
	OLIVE(1){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Olive");
		}
	},
	GINGKO_NUT(1){
		@Override
		protected void registerOreDictEntries() {
			registerOre("GingkoNut").registerOre("listAllspice");
		}
	},
	COFFEE(0){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Coffee");
		}
	},
	OSANGE_ORANGE(1){
		@Override
		protected void registerOreDictEntries() {
			registerOre("OsangeOrange").registerOre("listAllfruit").registerOre("listAllcitrus");
		}
	},
	CLOVE(0){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Clove").registerOre("listAllspice");
		}
	},
	PAPAYIMAR(8){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Papayimar").registerOre("listAllfruit");
		}
	},
	BLACKCURRANT(2){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Blackcurrant").registerOre("listAllfruit").registerOre("listAllberry");
		}
	},
	REDCURRANT(2){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Redcurrant").registerOre("listAllfruit").registerOre("listAllberry");
		}
	},
	BLACKBERRY(2){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Blackberry").registerOre("listAllfruit").registerOre("listAllberry");
		}
	},
	RASPBERRY(2){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Raspberry").registerOre("listAllfruit").registerOre("listAllberry");
		}
	},
	BLUEBERRY(2){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Blueberry").registerOre("listAllfruit").registerOre("listAllberry");
		}
	},
	CRANBERRY(2){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Cranberry").registerOre("listAllfruit").registerOre("listAllberry");
		}
	},
	JUNIPER(0){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Juniper").registerOre("listAllfruit");
		}
	},
	GOOSEBERRY(2){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Gooseberry").registerOre("listAllfruit").registerOre("listAllberry");
		}
	},
	GOLDEN_RASPBERRY(2){
		@Override
		protected void registerOreDictEntries() {
			registerOre("GoldenRaspberry").registerOre("Goldenraspberry").registerOre("listAllfruit").registerOre("listAllberry");
		}
	},
	COCONUT(2){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Coconut").registerOre("listAllnut");
		}
	},
	CASHEW(0){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Cashew").registerOre("listAllnut");
		}
	},
	AVACADO(2){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Avocado").registerOre("listAllfruit");
		}
	},
	NUTMEG(0){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Nutmeg").registerOre("listAllnut");
		}
	},
	ALLSPICE(0){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Allspice").registerOre("listAllspice");
		}
	},
	CHILLI(2){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Chilli").registerOre("Chilipepper").registerOre("listAllpepper");
		}
	},
	STAR_ANISE(0){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Staranise").registerOre("StarAnise").registerOre("listAllspice");
		}
	},
	MANGO(4){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Mango").registerOre("listAllfruit");
		}
	},
	STARFRUIT(2){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Starfruit").registerOre("listAllfruit");
		}
	},
	CANDLENUT(0){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Candlenut").registerOre("Candleberry").registerOre("Candle").registerOre("listAllnut");
		}
	};

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

	protected Food registerOre(String string) {
		OreDictionary.registerOre("crop" + string, this.get(1));
		this.ores.add("crop" + string);
		return this;
	}

	public Collection<String> getOres() {
		return this.ores;
	}
}
