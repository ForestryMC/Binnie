package binnie.extratrees.items;

import binnie.core.Mods;
import binnie.core.item.IItemMiscProvider;
import binnie.core.util.I18N;
import binnie.extratrees.liquid.Juice;
import binnie.extratrees.modules.ModuleCore;
import forestry.api.recipes.RecipeManagers;
import forestry.core.fluids.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public enum Food implements IItemMiscProvider {
	CRABAPPLE(2) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Apple").registerCrop("Crabapple").registerOre(HARVESTCRAFT_LIST_ALLFRUIT);
		}
	},
	ORANGE(4) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Orange").registerOre(HARVESTCRAFT_LIST_ALLFRUIT).registerOre(HARVESTCRAFT_LIST_ALLCITRUS);
		}
	},
	KUMQUAT(2) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Kumquat").registerOre(HARVESTCRAFT_LIST_ALLFRUIT);
		}
	},
	LIME(2) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Lime").registerOre(HARVESTCRAFT_LIST_ALLFRUIT).registerOre(HARVESTCRAFT_LIST_ALLCITRUS);
		}
	},
	WILD_CHERRY(2) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Cherry").registerCrop("WildCherry").registerOre(HARVESTCRAFT_LIST_ALLFRUIT);
		}
	},
	SOUR_CHERRY(2) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Cherry").registerCrop("SourCherry").registerOre(HARVESTCRAFT_LIST_ALLFRUIT);
		}
	},
	BLACK_CHERRY(2) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Cherry").registerCrop("BlackCherry").registerOre(HARVESTCRAFT_LIST_ALLFRUIT);
		}
	},
	Blackthorn(3) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Blackthorn").registerOre(HARVESTCRAFT_LIST_ALLFRUIT).registerOre(HARVESTCRAFT_LIST_ALLBERRY);
		}
	},
	CHERRY_PLUM(3) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Plum").registerCrop("CherryPlum").registerOre(HARVESTCRAFT_LIST_ALLFRUIT);
		}
	},
	ALMOND(1) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Almond").registerOre(HARVESTCRAFT_LIST_ALLNUT);
		}
	},
	APRICOT(4) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Apricot").registerOre(HARVESTCRAFT_LIST_ALLFRUIT);
		}
	},
	GRAPEFRUIT(4) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Grapefruit").registerOre(HARVESTCRAFT_LIST_ALLFRUIT).registerOre(HARVESTCRAFT_LIST_ALLCITRUS);
		}
	},
	PEACH(4) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Peach").registerOre(HARVESTCRAFT_LIST_ALLFRUIT);
		}
	},
	SATSUMA(3) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Satsuma").registerCrop("Orange").registerOre(HARVESTCRAFT_LIST_ALLFRUIT).registerOre(HARVESTCRAFT_LIST_ALLCITRUS);
		}
	},
	BUDDHA_HAND(3) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("BuddhaHand").registerCrop("Citron").registerOre(HARVESTCRAFT_LIST_ALLFRUIT).registerOre(HARVESTCRAFT_LIST_ALLCITRUS);
		}
	},
	CITRON(3) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Citron").registerOre(HARVESTCRAFT_LIST_ALLFRUIT).registerOre(HARVESTCRAFT_LIST_ALLCITRUS);
		}
	},
	FINGER_LIME(3) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Lime").registerCrop("FingerLime").registerOre(HARVESTCRAFT_LIST_ALLFRUIT).registerOre(HARVESTCRAFT_LIST_ALLCITRUS);
		}
	},
	KEY_LIME(2) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("KeyLime").registerCrop("Lime").registerOre(HARVESTCRAFT_LIST_ALLFRUIT).registerOre(HARVESTCRAFT_LIST_ALLCITRUS);
		}
	},
	MANDERIN(3) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Orange").registerCrop("Manderin").registerOre(HARVESTCRAFT_LIST_ALLFRUIT).registerOre(HARVESTCRAFT_LIST_ALLCITRUS);
		}
	},
	NECTARINE(3) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Peach").registerCrop("Nectarine").registerOre(HARVESTCRAFT_LIST_ALLFRUIT);
		}
	},
	POMELO(3) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Pomelo").registerOre(HARVESTCRAFT_LIST_ALLFRUIT);
		}
	},
	TANGERINE(3) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Tangerine").registerCrop("Orange").registerOre(HARVESTCRAFT_LIST_ALLFRUIT).registerOre(HARVESTCRAFT_LIST_ALLCITRUS);
		}
	},
	PEAR(4) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Pear").registerOre(HARVESTCRAFT_LIST_ALLFRUIT);
		}
	},
	SAND_PEAR(2) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("SandPear").registerOre(HARVESTCRAFT_LIST_ALLFRUIT);
		}
	},
	HAZELNUT(2) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Hazelnut").registerOre(HARVESTCRAFT_LIST_ALLNUT);
		}
	},
	BUTTERNUT(1) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Butternut").registerCrop("Walnut").registerOre(HARVESTCRAFT_LIST_ALLNUT);
		}
	},
	BEECHNUT(0) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Beechnut").registerOre(HARVESTCRAFT_LIST_ALLNUT);
		}
	},
	PECAN(0) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Pecan").registerOre(HARVESTCRAFT_LIST_ALLNUT);
		}
	},
	BANANA(4) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Banana").registerOre(HARVESTCRAFT_LIST_ALLFRUIT);
		}
	},
	RED_BANANA(4) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("RedBanana").registerCrop("Banana").registerOre(HARVESTCRAFT_LIST_ALLFRUIT);
		}
	},
	PLANTAIN(2) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Plantain").registerOre(HARVESTCRAFT_LIST_ALLFRUIT);
		}
	},
	BRAZIL_NUT(0) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("BrazilNut").registerOre(HARVESTCRAFT_LIST_ALLNUT);
		}
	},
	FIG(2) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Fig").registerOre(HARVESTCRAFT_LIST_ALLFRUIT);
		}
	},
	ACORN(0) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Acorn").registerOre(HARVESTCRAFT_LIST_ALLNUT);
		}
	},
	ELDERBERRY(1) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Elderberry").registerOre(HARVESTCRAFT_LIST_ALLFRUIT).registerOre(HARVESTCRAFT_LIST_ALLBERRY);
		}
	},
	OLIVE(1) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Olive");
		}
	},
	GINGKO_NUT(1) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("GingkoNut").registerOre(HARVESTCRAFT_LIST_ALLSPICE);
		}
	},
	COFFEE(0) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Coffee");
		}
	},
	OSANGE_ORANGE(1) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("OsangeOrange").registerOre(HARVESTCRAFT_LIST_ALLFRUIT).registerOre(HARVESTCRAFT_LIST_ALLCITRUS);
		}
	},
	CLOVE(0) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Clove").registerOre(HARVESTCRAFT_LIST_ALLSPICE);
		}
	},
	PAPAYIMAR(8) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Papayimar")/*.registerOre(HARVESTCRAFT_LIST_ALLFRUIT)*/;
		}
	},
	BLACKCURRANT(2) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Blackcurrant").registerOre(HARVESTCRAFT_LIST_ALLFRUIT).registerOre(HARVESTCRAFT_LIST_ALLBERRY);
		}
	},
	REDCURRANT(2) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Redcurrant").registerOre(HARVESTCRAFT_LIST_ALLFRUIT).registerOre(HARVESTCRAFT_LIST_ALLBERRY);
		}
	},
	BLACKBERRY(2) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Blackberry").registerOre(HARVESTCRAFT_LIST_ALLFRUIT).registerOre(HARVESTCRAFT_LIST_ALLBERRY);
		}
	},
	RASPBERRY(2) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Raspberry").registerOre(HARVESTCRAFT_LIST_ALLFRUIT).registerOre(HARVESTCRAFT_LIST_ALLBERRY);
		}
	},
	BLUEBERRY(2) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Blueberry").registerOre(HARVESTCRAFT_LIST_ALLFRUIT).registerOre(HARVESTCRAFT_LIST_ALLBERRY);
		}
	},
	CRANBERRY(2) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Cranberry").registerOre(HARVESTCRAFT_LIST_ALLFRUIT).registerOre(HARVESTCRAFT_LIST_ALLBERRY);
		}
	},
	JUNIPER(0) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Juniper").registerOre(HARVESTCRAFT_LIST_ALLFRUIT);
		}
	},
	GOOSEBERRY(2) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Gooseberry").registerOre(HARVESTCRAFT_LIST_ALLFRUIT).registerOre(HARVESTCRAFT_LIST_ALLBERRY);
		}
	},
	GOLDEN_RASPBERRY(2) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("GoldenRaspberry").registerCrop("Goldenraspberry").registerOre(HARVESTCRAFT_LIST_ALLFRUIT).registerOre(HARVESTCRAFT_LIST_ALLBERRY);
		}
	},
	COCONUT(2) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Coconut").registerOre(HARVESTCRAFT_LIST_ALLNUT);
		}
	},
	CASHEW(0) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Cashew").registerOre(HARVESTCRAFT_LIST_ALLNUT);
		}
	},
	AVOCADO(2) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Avocado").registerOre(HARVESTCRAFT_LIST_ALLFRUIT);
		}
	},
	NUTMEG(0) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Nutmeg").registerOre(HARVESTCRAFT_LIST_ALLSPICE);
		}
	},
	ALLSPICE(0) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Allspice").registerOre(HARVESTCRAFT_LIST_ALLSPICE);
		}
	},
	CHILLI(2) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Chilli").registerCrop("Chilipepper").registerOre(HARVESTCRAFT_LIST_ALLPEPPER);
		}
	},
	STAR_ANISE(0) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Staranise").registerCrop("StarAnise").registerOre(HARVESTCRAFT_LIST_ALLSPICE);
		}
	},
	MANGO(4) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Mango").registerOre(HARVESTCRAFT_LIST_ALLFRUIT);
		}
	},
	STARFRUIT(2) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Starfruit").registerOre(HARVESTCRAFT_LIST_ALLFRUIT);
		}
	},
	CANDLENUT(0) {
		@Override
		protected void registerOreDictEntries() {
			registerCrop("Candlenut").registerCrop("Candleberry").registerCrop("Candle").registerOre(HARVESTCRAFT_LIST_ALLNUT);
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
		for (Food food : values()) {
			food.registerOreDictEntries();
		}
	}

	abstract void registerOreDictEntries();

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

	protected Food registerCrop(final String string) {
		final String name = "crop" + string;
		this.ores.add(name);
		return registerOre(name);
	}

	protected Food registerOre(final String string) {
		OreDictionary.registerOre(string, this.get(1));
		return this;
	}

	public Collection<String> getOres() {
		return this.ores;
	}
}
