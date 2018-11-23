package binnie.extratrees.genetics;

import java.awt.Color;
import java.lang.reflect.Field;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.arboriculture.IAlleleFruit;
import forestry.api.arboriculture.IAlleleTreeSpecies;
import forestry.api.arboriculture.TreeManager;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IFruitFamily;
import forestry.arboriculture.FruitProviderNone;
import forestry.arboriculture.genetics.alleles.AlleleTreeSpecies;

import binnie.core.Binnie;
import binnie.core.api.genetics.IBreedingSystem;
import binnie.extratrees.config.ConfigurationMain;
import binnie.extratrees.genetics.fruits.ETFruitProviderNone;
import binnie.extratrees.genetics.fruits.ETFruitProviderPod;
import binnie.extratrees.genetics.fruits.ETFruitProviderRipening;
import binnie.extratrees.genetics.fruits.FruitPod;
import binnie.extratrees.genetics.fruits.FruitSprite;
import binnie.extratrees.items.Food;

import static binnie.extratrees.genetics.fruits.ETFruitFamily.BERRY;
import static binnie.extratrees.genetics.fruits.ETFruitFamily.CITRUS;
import static forestry.api.arboriculture.EnumFruitFamily.JUNGLE;
import static forestry.api.arboriculture.EnumFruitFamily.NUX;
import static forestry.api.arboriculture.EnumFruitFamily.POMES;
import static forestry.api.arboriculture.EnumFruitFamily.PRUNES;

public enum AlleleETFruitDefinition {
	Blackthorn("blackthorn", 10, 7180062, 14561129, FruitSprite.SMALL, PRUNES) {
		@Override
		protected void addProducts() {
			addProduct(Food.Blackthorn.get(1), 1.0f);
		}
	},
	CherryPlum("cherry_plum", 10, 7180062, 15211595, FruitSprite.SMALL, PRUNES) {
		@Override
		protected void addProducts() {
			addProduct(Food.CHERRY_PLUM.get(1), 1.0f);
		}
	},
	Peach("peach", 10, 7180062, 16424995, FruitSprite.AVERAGE, PRUNES) {
		@Override
		protected void addProducts() {
			addProduct(Food.PEACH.get(1), 1.0f);
		}
	},
	Nectarine("nectarine", 10, 7180062, 16405795, FruitSprite.AVERAGE, PRUNES) {
		@Override
		protected void addProducts() {
			addProduct(Food.NECTARINE.get(1), 1.0f);
		}
	},
	Apricot("apricot", 10, 7180062, 16437027, FruitSprite.AVERAGE, PRUNES) {
		@Override
		protected void addProducts() {
			addProduct(Food.APRICOT.get(1), 1.0f);
		}
	},
	Almond("almond", 10, 7180062, 9364342, FruitSprite.SMALL, PRUNES) {
		@Override
		protected void addProducts() {
			addProduct(Food.ALMOND.get(1), 1.0f);
		}
	},
	WildCherry("wild_cherry", 10, 7180062, 16711680, FruitSprite.TINY, PRUNES) {
		@Override
		protected void addProducts() {
			addProduct(Food.WILD_CHERRY.get(1), 1.0f);
		}
	},
	SourCherry("sour_cherry", 10, 7180062, 10225963, FruitSprite.TINY, PRUNES) {
		@Override
		protected void addProducts() {
			addProduct(Food.SOUR_CHERRY.get(1), 1.0f);
		}
	},
	BlackCherry("black_cherry", 10, 7180062, 4852249, FruitSprite.TINY, PRUNES) {
		@Override
		protected void addProducts() {
			addProduct(Food.BLACK_CHERRY.get(1), 1.0f);
		}
	},
	Fig("fig", 9, 14201186, 7094086, FruitSprite.SMALL, PRUNES) {
		@Override
		protected void addProducts() {
			addProduct(Food.FIG.get(1), 1.0f);
		}
	},
	Elderberry("elderberry", 9, 7444317, 5331779, FruitSprite.TINY, PRUNES) {
		@Override
		protected void addProducts() {
			addProduct(Food.ELDERBERRY.get(1), 1.0f);
		}
	},
	Olive("olive", 9, 8887861, 6444842, FruitSprite.SMALL, PRUNES) {
		@Override
		protected void addProducts() {
			addProduct(Food.OLIVE.get(1), 1.0f);
		}
	},
	Orange("orange", 10, 3665987, 16749578, FruitSprite.AVERAGE, CITRUS) {
		@Override
		protected void addProducts() {
			addProduct(Food.ORANGE.get(1), 1.0f);
		}
	},
	Manderin("manderin", 10, 3665987, 16749578, FruitSprite.AVERAGE, CITRUS) {
		@Override
		protected void addProducts() {
			addProduct(Food.MANDERIN.get(1), 1.0f);
		}
	},
	Tangerine("tangerine", 10, 3665987, 16749578, FruitSprite.AVERAGE, CITRUS) {
		@Override
		protected void addProducts() {
			addProduct(Food.TANGERINE.get(1), 1.0f);
		}
	},
	Satsuma("satsuma", 10, 3665987, 16749578, FruitSprite.AVERAGE, CITRUS) {
		@Override
		protected void addProducts() {
			addProduct(Food.SATSUMA.get(1), 1.0f);
		}
	},
	KeyLime("key_lime", 10, 3665987, 10223428, FruitSprite.SMALL, CITRUS) {
		@Override
		protected void addProducts() {
			addProduct(Food.KEY_LIME.get(1), 1.0f);
		}
	},
	Lime("lime", 10, 3665987, 10223428, FruitSprite.AVERAGE, CITRUS) {
		@Override
		protected void addProducts() {
			addProduct(Food.LIME.get(1), 1.0f);
		}
	},
	FingerLime("finger_lime", 10, 3665987, 11156280, FruitSprite.SMALL, CITRUS) {
		@Override
		protected void addProducts() {
			addProduct(Food.FINGER_LIME.get(1), 1.0f);
		}
	},
	Pomelo("pomelo", 10, 3665987, 6083402, FruitSprite.LARGER, CITRUS) {
		@Override
		protected void addProducts() {
			addProduct(Food.POMELO.get(1), 1.0f);
		}
	},
	Grapefruit("grapefruit", 10, 3665987, 16749578, FruitSprite.LARGE, CITRUS) {
		@Override
		protected void addProducts() {
			addProduct(Food.GRAPEFRUIT.get(1), 1.0f);
		}
	},
	Kumquat("kumquat", 10, 3665987, 16749578, FruitSprite.SMALL, CITRUS) {
		@Override
		protected void addProducts() {
			addProduct(Food.KUMQUAT.get(1), 1.0f);
		}
	},
	Citron("citron", 10, 3665987, 16772192, FruitSprite.LARGE, CITRUS) {
		@Override
		protected void addProducts() {
			addProduct(Food.CITRON.get(1), 1.0f);
		}
	},
	BuddhaHand("buddha_hand", 10, 3665987, 16772192, FruitSprite.LARGE, CITRUS) {
		@Override
		protected void addProducts() {
			addProduct(Food.BUDDHA_HAND.get(1), 1.0f);
		}
	},
	Apple("apple", 10, 7915859, 16193046, FruitSprite.AVERAGE, POMES) {
		@Override
		protected void addProducts() {
			addProduct(new ItemStack(Items.APPLE), 1.0f);
		}
	},
	Crabapple("crabapple", 10, 7915859, 16760140, FruitSprite.AVERAGE, POMES) {
		@Override
		protected void addProducts() {
			addProduct(Food.CRABAPPLE.get(1), 1.0f);
		}
	},
	Mango("mango", 10, 6654997, 15902262, FruitSprite.AVERAGE, POMES) {
		@Override
		protected void addProducts() {
			addProduct(Food.MANGO.get(1), 1.0f);
		}
	},
	Pear("pear", 10, 10456913, 10474833, FruitSprite.PEAR, POMES) {
		@Override
		protected void addProducts() {
			addProduct(Food.PEAR.get(1), 1.0f);
		}
	},
	OsangeOsange("osange_osange", 10, 9934674, 10665767, FruitSprite.LARGER, POMES) {
		@Override
		protected void addProducts() {
			addProduct(Food.OSANGE_ORANGE.get(1), 1.0f);
		}
	},
	Banana("banana", FruitPod.BANANA, JUNGLE) {
		@Override
		protected void addProducts() {
			addProduct(Food.BANANA.get(2), 1.0f);
		}
	},
	RedBanana("red_banana", FruitPod.RED_BANANA, JUNGLE) {
		@Override
		protected void addProducts() {
			addProduct(Food.RED_BANANA.get(2), 1.0f);
		}
	},
	Plantain("platain", FruitPod.PLANTAIN, JUNGLE) {
		@Override
		protected void addProducts() {
			addProduct(Food.PLANTAIN.get(2), 1.0f);
		}
	},
	Coffee("coffee", 8, 7433501, 16273254, FruitSprite.TINY, JUNGLE) {
		@Override
		protected void addProducts() {
			addProduct(Food.COFFEE.get(1), 1.0f);
		}
	},
	Coconut("coconut", FruitPod.COCONUT, JUNGLE) {
		@Override
		protected void addProducts() {
			addProduct(Food.COCONUT.get(1), 1.0f);
		}
	},
	Cashew("cashew", 8, 12879132, 15289111, FruitSprite.AVERAGE, JUNGLE) {
		@Override
		protected void addProducts() {
			addProduct(Food.CASHEW.get(1), 1.0f);
		}
	},
	Avacado("avacado", 10, 10272370, 2170640, FruitSprite.PEAR, JUNGLE) {
		@Override
		protected void addProducts() {
			addProduct(Food.AVACADO.get(1), 1.0f);
		}
	},
	Nutmeg("nutmeg", 9, 14861101, 11305813, FruitSprite.TINY, JUNGLE) {
		@Override
		protected void addProducts() {
			addProduct(Food.NUTMEG.get(1), 1.0f);
		}
	},
	Allspice("allspice", 9, 15180922, 7423542, FruitSprite.TINY, JUNGLE) {
		@Override
		protected void addProducts() {
			addProduct(Food.ALLSPICE.get(1), 1.0f);
		}
	},
	Chilli("chilli", 10, 7430757, 15145010, FruitSprite.SMALL, JUNGLE) {
		@Override
		protected void addProducts() {
			addProduct(Food.CHILLI.get(1), 1.0f);
		}
	},
	StarAnise("star_anise", 8, 8733742, 13917189, FruitSprite.TINY, JUNGLE) {
		@Override
		protected void addProducts() {
			addProduct(Food.STAR_ANISE.get(1), 1.0f);
		}
	},
	Starfruit("starfruit", 10, 9814541, 15061550, FruitSprite.AVERAGE, JUNGLE) {
		@Override
		protected void addProducts() {
			addProduct(Food.STARFRUIT.get(1), 1.0f);
		}
	},
	Candlenut("candlenut", 8, 8235123, 14600882, FruitSprite.SMALL, JUNGLE) {
		@Override
		protected void addProducts() {
			addProduct(Food.CANDLENUT.get(1), 1.0f);
		}
	},
	Hazelnut("hazelnut", 7, 8223006, 14463606, FruitSprite.SMALL, NUX) {
		@Override
		protected void addProducts() {
			addProduct(Food.HAZELNUT.get(1), 1.0f);
		}
	},
	Butternut("butternut", 7, 11712336, 16102498, FruitSprite.SMALL, NUX) {
		@Override
		protected void addProducts() {
			addProduct(Food.BUTTERNUT.get(1), 1.0f);
		}
	},
	Beechnut("beechnut", 8, 14401148, 6241845, FruitSprite.TINY, NUX) {
		@Override
		protected void addProducts() {
			addProduct(Food.BEECHNUT.get(1), 1.0f);
		}
	},
	Pecan("pecan", 8, 10660940, 15781769, FruitSprite.SMALL, NUX) {
		@Override
		protected void addProducts() {
			addProduct(Food.PECAN.get(1), 1.0f);
		}
	},
	BrazilNut("brazil_nut", 10, 5875561, 9852208, FruitSprite.LARGE, NUX) {
		@Override
		protected void addProducts() {
			addProduct(Food.BRAZIL_NUT.get(4), 1.0f);
		}
	},
	Acorn("acorn", 6, 7516710, 11364893, FruitSprite.TINY, NUX) {
		@Override
		protected void addProducts() {
			addProduct(Food.ACORN.get(1), 1.0f);
		}
	},
	GingkoNut("gingko_nut", 7, 9213787, 15063725, FruitSprite.TINY, NUX) {
		@Override
		protected void addProducts() {
			addProduct(Food.GINGKO_NUT.get(1), 1.0f);
		}
	},
	Clove("clove", 9, 6847532, 11224133, FruitSprite.TINY, NUX) {
		@Override
		protected void addProducts() {
			addProduct(Food.CLOVE.get(1), 1.0f);
		}
	},
	Blackcurrant("blackcurrant", 8, 9407571, 4935251, FruitSprite.TINY, BERRY) {
		@Override
		protected void addProducts() {
			addProduct(Food.BLACKCURRANT.get(2), 1.0f);
		}
	},
	Redcurrant("redcurrant", 8, 13008910, 15080974, FruitSprite.TINY, BERRY) {
		@Override
		protected void addProducts() {
			addProduct(Food.REDCURRANT.get(2), 1.0f);
		}
	},
	Blackberry("blackberry", 8, 9399665, 4801393, FruitSprite.TINY, BERRY) {
		@Override
		protected void addProducts() {
			addProduct(Food.BLACKBERRY.get(2), 1.0f);
		}
	},
	Raspberry("raspberry", 8, 15520197, 14510449, FruitSprite.TINY, BERRY) {
		@Override
		protected void addProducts() {
			addProduct(Food.RASPBERRY.get(2), 1.0f);
		}
	},
	Blueberry("blueberry", 8, 10203799, 6329278, FruitSprite.TINY, BERRY) {
		@Override
		protected void addProducts() {
			addProduct(Food.BLUEBERRY.get(2), 1.0f);
		}
	},
	Cranberry("cranberry", 8, 12232496, 14555696, FruitSprite.TINY, BERRY) {
		@Override
		protected void addProducts() {
			addProduct(Food.CRANBERRY.get(2), 1.0f);
		}
	},
	Juniper("juniper", 8, 10194034, 6316914, FruitSprite.TINY, BERRY) {
		@Override
		protected void addProducts() {
			addProduct(Food.JUNIPER.get(2), 1.0f);
		}
	},
	Gooseberry("gooseberry", 8, 12164944, 12177232, FruitSprite.TINY, BERRY) {
		@Override
		protected void addProducts() {
			addProduct(Food.GOOSEBERRY.get(2), 1.0f);
		}
	},
	GoldenRaspberry("golden_raspberry", 8, 12496955, 15970363, FruitSprite.TINY, BERRY) {
		@Override
		protected void addProducts() {
			addProduct(Food.GOLDEN_RASPBERRY.get(2), 1.0f);
		}
	}
	//	, Papayimar("papayimar", FruitPod.PAPAYIMAR)
	;

	private final IAlleleFruit alleleFruit;
	private final ETFruitProviderNone fruitProvider;

	AlleleETFruitDefinition(String name, int time, int unripe, int color, FruitSprite sprite, IFruitFamily family) {
		alleleFruit = new AlleleETFruit(name, fruitProvider = new ETFruitProviderRipening(name, family, sprite).setColours(new Color(color), new Color(unripe)).setRipeningPeriod(time));
	}

	AlleleETFruitDefinition(String name, FruitPod fruitPod, IFruitFamily family) {
		alleleFruit = new AlleleETFruit(name, fruitProvider = new ETFruitProviderPod(name, family, fruitPod));
	}

	public static void preInit() {
		for (AlleleETFruitDefinition definition : values()) {
			AlleleManager.alleleRegistry.registerAllele(definition.alleleFruit, EnumTreeChromosome.FRUITS);
		}
	}

	public static void init() {
		for (AlleleETFruitDefinition definition : values()) {
			definition.addProducts();
		}

		AlleleManager.alleleRegistry.registerFruitFamily(BERRY);
		AlleleManager.alleleRegistry.registerFruitFamily(CITRUS);

		if (ConfigurationMain.alterLemon) {
			try {
				IAlleleFruit lemon = (IAlleleFruit) AlleleManager.alleleRegistry.getAllele("forestry.fruitLemon");
				FruitProviderNone prov = (FruitProviderNone) lemon.getProvider();
				Field familyField = FruitProviderNone.class.getDeclaredField("family");
				Field modifiersField = Field.class.getDeclaredField("modifiers");
				familyField.setAccessible(true);
				modifiersField.setAccessible(true);
				modifiersField.setInt(familyField, familyField.getModifiers() & 0xFFFFFFEF);
				familyField.set(prov, CITRUS);
			} catch (IllegalAccessException | NoSuchFieldException e) {
				throw new RuntimeException(e);
			}
		}
		IBreedingSystem treeSystem = Binnie.GENETICS.getSystem(TreeManager.treeRoot);
		for (IAlleleSpecies tree : treeSystem.getAllSpecies()) {
			if (tree instanceof AlleleTreeSpecies && ((IAlleleTreeSpecies) tree).getSuitableFruit().contains(PRUNES)) {
				((AlleleTreeSpecies) tree).addFruitFamily(CITRUS);
			}
		}
	}

	protected void addProducts() {
	}

	protected void addProduct(ItemStack product, float chance) {
		fruitProvider.addDrop(product, chance);
	}

	public IAlleleFruit getAllele() {
		return alleleFruit;
	}
}
