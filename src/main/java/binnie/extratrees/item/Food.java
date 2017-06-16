package binnie.extratrees.item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

import forestry.api.recipes.RecipeManagers;
import forestry.core.fluids.Fluids;

import binnie.core.Mods;
import binnie.core.item.IItemMiscProvider;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.alcohol.Juice;

public enum Food implements IItemMiscProvider {
	CRABAPPLE(2),
	ORANGE(4),
	KUMQUAT(2),
	LIME(2),
	WILD_CHERRY(2),
	SOUR_CHERRY(2),
	BLACK_CHERRY(2),
	Blackthorn(3),
	CHERRY_PLUM(3),
	ALMOND(1),
	APRICOT(4),
	GRAPEFRUIT(4),
	PEACH(4),
	SATSUMA(3),
	BUDDHA_HAND(3),
	CITRON(3),
	FINGER_LIME(3),
	KEY_LIME(2),
	MANDERIN(3),
	NECTARINE(3),
	POMELO(3),
	TANGERINE(3),
	PEAR(4),
	SAND_PEAR(2),
	HAZELNUT(2),
	BUTTERNUT(1),
	BEECHNUT(0),
	PECAN(0),
	BANANA(4),
	RED_BANANA(4),
	PLANTAIN(2),
	BRAZIL_NUT(0),
	FIG(2),
	ACORN(0),
	ELDERBERRY(1),
	OLIVE(1),
	GINGKO_NUT(1),
	COFFEE(0),
	OSANGE_ORANGE(1),
	CLOVE(0),
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
	AVACADO(2),
	NUTMEG(0),
	ALLSPICE(0),
	CHILLI(2),
	STAR_ANISE(0),
	MANGO(4),
	STARFRUIT(2),
	CANDLENUT(0);

	public static Food[] VALUES = values();

	int hunger;
	private List<String> ores;

	Food() {
		this(0);
	}

	Food(final int hunger) {
		this.ores = new ArrayList<>();
		this.hunger = hunger;
	}

	public static void registerOreDictionary() {
		Food.CRABAPPLE.ore("Apple").ore("Crabapple");
		Food.ORANGE.ore("Orange");
		Food.KUMQUAT.ore("Kumquat");
		Food.LIME.ore("Lime");
		Food.WILD_CHERRY.ore("Cherry").ore("WildCherry");
		Food.SOUR_CHERRY.ore("Cherry").ore("SourCherry");
		Food.BLACK_CHERRY.ore("Cherry").ore("BlackCherry");
		Food.Blackthorn.ore("Blackthorn");
		Food.CHERRY_PLUM.ore("Plum").ore("CherryPlum");
		Food.ALMOND.ore("Almond");
		Food.APRICOT.ore("Apricot");
		Food.GRAPEFRUIT.ore("Grapefruit");
		Food.PEACH.ore("Peach");
		Food.SATSUMA.ore("Satsuma").ore("Orange");
		Food.BUDDHA_HAND.ore("BuddhaHand").ore("Citron");
		Food.CITRON.ore("Citron");
		Food.FINGER_LIME.ore("Lime").ore("FingerLime");
		Food.KEY_LIME.ore("KeyLime").ore("Lime");
		Food.MANDERIN.ore("Orange").ore("Manderin");
		Food.NECTARINE.ore("Peach").ore("Nectarine");
		Food.POMELO.ore("Pomelo");
		Food.TANGERINE.ore("Tangerine").ore("Orange");
		Food.PEAR.ore("Pear");
		Food.SAND_PEAR.ore("SandPear");
		Food.HAZELNUT.ore("Hazelnut");
		Food.BUTTERNUT.ore("Butternut").ore("Walnut");
		Food.BEECHNUT.ore("Beechnut");
		Food.PECAN.ore("Pecan");
		Food.BANANA.ore("Banana");
		Food.RED_BANANA.ore("RedBanana").ore("Banana");
		Food.PLANTAIN.ore("Plantain");
		Food.BRAZIL_NUT.ore("BrazilNut");
		Food.FIG.ore("Fig");
		Food.ACORN.ore("Acorn");
		Food.ELDERBERRY.ore("Elderberry");
		Food.OLIVE.ore("Olive");
		Food.GINGKO_NUT.ore("GingkoNut");
		Food.COFFEE.ore("Coffee");
		Food.OSANGE_ORANGE.ore("OsangeOrange");
		Food.CLOVE.ore("Clove");
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
	public String getName(final ItemStack stack) {
		return ExtraTrees.proxy.localise("item.food." + this.name().toLowerCase());
	}

	@Override
	public ItemStack get(final int i) {
		return new ItemStack(ExtraTrees.items().itemFood, i, this.ordinal());
	}

	@Override
	public String getModelPath() {
		return name().toLowerCase(Locale.ENGLISH);
	}

	@Override
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

	private Food ore(final String string) {
		OreDictionary.registerOre("crop" + string, this.get(1));
		this.ores.add("crop" + string);
		return this;
	}

	public Collection<String> getOres() {
		return this.ores;
	}
}
