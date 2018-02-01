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
			registerOre("Apple").registerOre("Crabapple");
		}
	},
	ORANGE(4){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Orange");
		}
	},
	KUMQUAT(2){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Kumquat");
		}
	},
	LIME(2){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Lime");
		}
	},
	WILD_CHERRY(2){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Cherry").registerOre("WildCherry");
		}
	},
	SOUR_CHERRY(2){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Cherry").registerOre("SourCherry");
		}
	},
	BLACK_CHERRY(2){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Cherry").registerOre("BlackCherry");
		}
	},
	Blackthorn(3){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Blackthorn");
		}
	},
	CHERRY_PLUM(3){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Plum").registerOre("CherryPlum");
		}
	},
	ALMOND(1){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Almond");
		}
	},
	APRICOT(4){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Apricot");
		}
	},
	GRAPEFRUIT(4){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Grapefruit");
		}
	},
	PEACH(4){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Peach");
		}
	},
	SATSUMA(3){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Satsuma").registerOre("Orange");
		}
	},
	BUDDHA_HAND(3){
		@Override
		protected void registerOreDictEntries() {
			registerOre("BuddhaHand").registerOre("Citron");
		}
	},
	CITRON(3){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Citron");
		}
	},
	FINGER_LIME(3){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Lime").registerOre("FingerLime");
		}
	},
	KEY_LIME(2){
		@Override
		protected void registerOreDictEntries() {
			registerOre("KeyLime").registerOre("Lime");
		}
	},
	MANDERIN(3){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Orange").registerOre("Manderin");
		}
	},
	NECTARINE(3){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Peach").registerOre("Nectarine");
		}
	},
	POMELO(3){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Pomelo");
		}
	},
	TANGERINE(3){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Tangerine").registerOre("Orange");
		}
	},
	PEAR(4){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Pear");
		}
	},
	SAND_PEAR(2){
		@Override
		protected void registerOreDictEntries() {
			registerOre("SandPear");
		}
	},
	HAZELNUT(2){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Hazelnut");
		}
	},
	BUTTERNUT(1){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Butternut").registerOre("Walnut");
		}
	},
	BEECHNUT(0){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Beechnut");
		}
	},
	PECAN(0){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Pecan");
		}
	},
	BANANA(4){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Banana");
		}
	},
	RED_BANANA(4){
		@Override
		protected void registerOreDictEntries() {
			registerOre("RedBanana").registerOre("Banana");
		}
	},
	PLANTAIN(2){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Plantain");
		}
	},
	BRAZIL_NUT(0){
		@Override
		protected void registerOreDictEntries() {
			registerOre("BrazilNut");
		}
	},
	FIG(2){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Fig");
		}
	},
	ACORN(0){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Acorn");
		}
	},
	ELDERBERRY(1){
		@Override
		protected void registerOreDictEntries() {
			registerOre("Elderberry");
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
			registerOre("GingkoNut");
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
			registerOre("OsangeOrange");
		}
	},
	CLOVE(0){
		@Override
		protected void registerOreDictEntries() {
			Food.CLOVE.registerOre("Clove");
		}
	},
	PAPAYIMAR(8),
	BLACKCURRANT(2),
	REDCURRANT(2),
	BLACKBERRY(2),
	RASPBERRY(2),
	BLUEBERRY(2),
	CRANBERRY(2),
	JUNIPER(0),
	GOOSEBERRY(2),
	GOLDEN_RASPBERRY(2),
	COCONUT(2),
	CASHEW(0),
	AVOCADO(2),
	NUTMEG(0),
	ALLSPICE(0),
	CHILLI(2),
	STAR_ANISE(0),
	MANGO(4),
	STARFRUIT(2),
	CANDLENUT(0);

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
