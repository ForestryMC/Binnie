package binnie.extratrees.genetics;

import binnie.Binnie;
import binnie.Constants;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.FruitPod;
import binnie.extratrees.config.ConfigurationMain;
import binnie.extratrees.item.Food;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import forestry.api.arboriculture.IAlleleFruit;
import forestry.api.arboriculture.IAlleleTreeSpecies;
import forestry.api.arboriculture.IFruitProvider;
import forestry.api.arboriculture.ITreeGenome;
import forestry.api.arboriculture.TreeManager;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IFruitFamily;
import forestry.arboriculture.FruitProviderNone;
import forestry.arboriculture.genetics.alleles.AlleleTreeSpecies;
import forestry.core.genetics.alleles.AlleleCategorized;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class AlleleETFruit extends AlleleCategorized implements IAlleleFruit, IFruitProvider {
	
	/*public static void createAlleles() {
		List<IAlleleFruit> fruitAlleles = Arrays.asList(
				fruitNone = new AlleleFruit("none", new FruitProviderNone("for.fruits.none", null)),
				fruitApple = new AlleleFruit("apple", new FruitProviderRandom("for.fruits.apple", EnumFruitFamily.POMES, new ItemStack(Items.APPLE), 1.0f)
						.setColour(new Color(0xff2e2e))
						.setOverlay("pomes")),
				fruitCocoa = new AlleleFruit("cocoa", new FruitProviderPod("for.fruits.cocoa", EnumFruitFamily.JUNGLE, FruitProviderPod.EnumPodType.COCOA, new ItemStack(Items.DYE, 1, EnumDyeColor.BROWN.getDyeDamage()))),
				// .setColours(0xecdca5, 0xc4d24a), true);
				fruitChestnut = new AlleleFruit("chestnut", new FruitProviderRipening("for.fruits.chestnut", EnumFruitFamily.NUX, ItemFruit.EnumFruit.CHESTNUT.getStack(), 1.0f)
						.setRipeningPeriod(6)
						.setColours(new Color(0x7f333d), new Color(0xc4d24a))
						.setOverlay("nuts"), true),
				fruitWalnut = new AlleleFruit("walnut", new FruitProviderRipening("for.fruits.walnut", EnumFruitFamily.NUX, ItemFruit.EnumFruit.WALNUT.getStack(), 1.0f)
						.setRipeningPeriod(8)
						.setColours(new Color(0xfba248), new Color(0xc4d24a))
						.setOverlay("nuts"), true),
				fruitCherry = new AlleleFruit("cherry", new FruitProviderRipening("for.fruits.cherry", EnumFruitFamily.PRUNES, ItemFruit.EnumFruit.CHERRY.getStack(), 1.0f)
						.setColours(new Color(0xff2e2e), new Color(0xc4d24a))
						.setOverlay("berries"), true),
				fruitDates = new AlleleFruit("dates", new FruitProviderPod("for.fruits.dates", EnumFruitFamily.JUNGLE, FruitProviderPod.EnumPodType.DATES, ItemFruit.EnumFruit.DATES.getStack(4))),
				fruitPapaya = new AlleleFruit("papaya", new FruitProviderPod("for.fruits.papaya", EnumFruitFamily.JUNGLE, FruitProviderPod.EnumPodType.PAPAYA, ItemFruit.EnumFruit.PAPAYA.getStack())),
				fruitLemon = new AlleleFruit("lemon", new FruitProviderRipening("for.fruits.lemon", EnumFruitFamily.PRUNES, ItemFruit.EnumFruit.LEMON.getStack(), 1.0f)
						.setColours(new Color(0xeeee00), new Color(0x99ff00))
						.setOverlay("citrus"), true),
				fruitPlum = new AlleleFruit("plum", new FruitProviderRipening("for.fruits.plum", EnumFruitFamily.PRUNES, ItemFruit.EnumFruit.PLUM.getStack(), 1.0f)
						.setColours(new Color(0x663446), new Color(0xeeff1a))
						.setOverlay("plums"), true)
		);

		for (IAlleleFruit fruitAllele : fruitAlleles) {
			AlleleManager.alleleRegistry.registerAllele(fruitAllele, EnumTreeChromosome.FRUITS);
		}
	}*/
	public static final AlleleETFruit Blackthorn = new AlleleETFruit("Blackthorn", 10, 7180062, 14561129, FruitSprite.SMALL);
	public static final AlleleETFruit CherryPlum = new AlleleETFruit("CherryPlum", 10, 7180062, 15211595, FruitSprite.SMALL);
	public static final AlleleETFruit Peach = new AlleleETFruit("Peach", 10, 7180062, 16424995, FruitSprite.AVERAGE);
	public static final AlleleETFruit Nectarine = new AlleleETFruit("Nectarine", 10, 7180062, 16405795, FruitSprite.AVERAGE);
	public static final AlleleETFruit Apricot = new AlleleETFruit("Apricot", 10, 7180062, 16437027, FruitSprite.AVERAGE);
	public static final AlleleETFruit Almond = new AlleleETFruit("Almond", 10, 7180062, 9364342, FruitSprite.SMALL);
	public static final AlleleETFruit WildCherry = new AlleleETFruit("WildCherry", 10, 7180062, 16711680, FruitSprite.TINY);
	public static final AlleleETFruit SourCherry = new AlleleETFruit("SourCherry", 10, 7180062, 10225963, FruitSprite.TINY);
	public static final AlleleETFruit BlackCherry = new AlleleETFruit("BlackCherry", 10, 7180062, 4852249, FruitSprite.TINY);
	public static final AlleleETFruit Orange = new AlleleETFruit("Orange", 10, 3665987, 16749578, FruitSprite.AVERAGE);
	public static final AlleleETFruit Manderin = new AlleleETFruit("Manderin", 10, 3665987, 16749578, FruitSprite.AVERAGE);
	public static final AlleleETFruit Tangerine = new AlleleETFruit("Tangerine", 10, 3665987, 16749578, FruitSprite.AVERAGE);
	public static final AlleleETFruit Satsuma = new AlleleETFruit("Satsuma", 10, 3665987, 16749578, FruitSprite.AVERAGE);
	public static final AlleleETFruit KeyLime = new AlleleETFruit("KeyLime", 10, 3665987, 10223428, FruitSprite.SMALL);
	public static final AlleleETFruit Lime = new AlleleETFruit("Lime", 10, 3665987, 10223428, FruitSprite.AVERAGE);
	public static final AlleleETFruit FingerLime = new AlleleETFruit("FingerLime", 10, 3665987, 11156280, FruitSprite.SMALL);
	public static final AlleleETFruit Pomelo = new AlleleETFruit("Pomelo", 10, 3665987, 6083402, FruitSprite.LARGER);
	public static final AlleleETFruit Grapefruit = new AlleleETFruit("Grapefruit", 10, 3665987, 16749578, FruitSprite.LARGE);
	public static final AlleleETFruit Kumquat = new AlleleETFruit("Kumquat", 10, 3665987, 16749578, FruitSprite.SMALL);
	public static final AlleleETFruit Citron = new AlleleETFruit("Citron", 10, 3665987, 16772192, FruitSprite.LARGE);
	public static final AlleleETFruit BuddhaHand = new AlleleETFruit("BuddhaHand", 10, 3665987, 16772192, FruitSprite.LARGE);
	public static final AlleleETFruit Apple = new AlleleETFruit("Apple", 10, 7915859, 16193046, FruitSprite.AVERAGE);
	public static final AlleleETFruit Crabapple = new AlleleETFruit("Crabapple", 10, 7915859, 16760140, FruitSprite.AVERAGE);
	public static final AlleleETFruit Banana = new AlleleETFruit("Banana", FruitPod.Banana);
	public static final AlleleETFruit RedBanana = new AlleleETFruit("Red Banana", FruitPod.RedBanana);
	public static final AlleleETFruit Plantain = new AlleleETFruit("Platain", FruitPod.Plantain);
	public static final AlleleETFruit Hazelnut = new AlleleETFruit("Hazelnut", 7, 8223006, 14463606, FruitSprite.SMALL);
	public static final AlleleETFruit Butternut = new AlleleETFruit("Butternut", 7, 11712336, 16102498, FruitSprite.SMALL);
	public static final AlleleETFruit Beechnut = new AlleleETFruit("Beechnut", 8, 14401148, 6241845, FruitSprite.TINY);
	public static final AlleleETFruit Pecan = new AlleleETFruit("Pecan", 8, 10660940, 15781769, FruitSprite.SMALL);
	public static final AlleleETFruit BrazilNut = new AlleleETFruit("BrazilNut", 10, 5875561, 9852208, FruitSprite.LARGE);
	public static final AlleleETFruit Fig = new AlleleETFruit("Fig", 9, 14201186, 7094086, FruitSprite.SMALL);
	public static final AlleleETFruit Acorn = new AlleleETFruit("Acorn", 6, 7516710, 11364893, FruitSprite.TINY);
	public static final AlleleETFruit Elderberry = new AlleleETFruit("Elderberry", 9, 7444317, 5331779, FruitSprite.TINY);
	public static final AlleleETFruit Olive = new AlleleETFruit("Olive", 9, 8887861, 6444842, FruitSprite.SMALL);
	public static final AlleleETFruit GingkoNut = new AlleleETFruit("GingkoNut", 7, 9213787, 15063725, FruitSprite.TINY);
	public static final AlleleETFruit Coffee = new AlleleETFruit("Coffee", 8, 7433501, 16273254, FruitSprite.TINY);
	public static final AlleleETFruit Pear = new AlleleETFruit("Pear", 10, 10456913, 10474833, FruitSprite.PEAR);
	public static final AlleleETFruit OsangeOsange = new AlleleETFruit("OsangeOsange", 10, 9934674, 10665767, FruitSprite.LARGER);
	public static final AlleleETFruit Clove = new AlleleETFruit("Clove", 9, 6847532, 11224133, FruitSprite.TINY);
	public static final AlleleETFruit Coconut = new AlleleETFruit("Coconut", FruitPod.Coconut);
	public static final AlleleETFruit Cashew = new AlleleETFruit("Cashew", 8, 12879132, 15289111, FruitSprite.AVERAGE);
	public static final AlleleETFruit Avacado = new AlleleETFruit("Avacado", 10, 10272370, 2170640, FruitSprite.PEAR);
	public static final AlleleETFruit Nutmeg = new AlleleETFruit("Nutmeg", 9, 14861101, 11305813, FruitSprite.TINY);
	public static final AlleleETFruit Allspice = new AlleleETFruit("Allspice", 9, 15180922, 7423542, FruitSprite.TINY);
	public static final AlleleETFruit Chilli = new AlleleETFruit("Chilli", 10, 7430757, 15145010, FruitSprite.SMALL);
	public static final AlleleETFruit StarAnise = new AlleleETFruit("StarAnise", 8, 8733742, 13917189, FruitSprite.TINY);
	public static final AlleleETFruit Mango = new AlleleETFruit("Mango", 10, 6654997, 15902262, FruitSprite.AVERAGE);
	public static final AlleleETFruit Starfruit = new AlleleETFruit("Starfruit", 10, 9814541, 15061550, FruitSprite.AVERAGE);
	public static final AlleleETFruit Candlenut = new AlleleETFruit("Candlenut", 8, 8235123, 14600882, FruitSprite.SMALL);
	public static final AlleleETFruit Papayimar = new AlleleETFruit("Papayimar", FruitPod.Papayimar);
	public static final AlleleETFruit Blackcurrant = new AlleleETFruit("Blackcurrant", 8, 9407571, 4935251, FruitSprite.TINY);
	public static final AlleleETFruit Redcurrant = new AlleleETFruit("Redcurrant", 8, 13008910, 15080974, FruitSprite.TINY);
	public static final AlleleETFruit Blackberry = new AlleleETFruit("Blackberry", 8, 9399665, 4801393, FruitSprite.TINY);
	public static final AlleleETFruit Raspberry = new AlleleETFruit("Raspberry", 8, 15520197, 14510449, FruitSprite.TINY);
	public static final AlleleETFruit Blueberry = new AlleleETFruit("Blueberry", 8, 10203799, 6329278, FruitSprite.TINY);
	public static final AlleleETFruit Cranberry = new AlleleETFruit("Cranberry", 8, 12232496, 14555696, FruitSprite.TINY);
	public static final AlleleETFruit Juniper = new AlleleETFruit("Juniper", 8, 10194034, 6316914, FruitSprite.TINY);
	public static final AlleleETFruit Gooseberry = new AlleleETFruit("Gooseberry", 8, 12164944, 12177232, FruitSprite.TINY);
	public static final AlleleETFruit GoldenRaspberry = new AlleleETFruit("GoldenRaspberry", 8, 12496955, 15970363, FruitSprite.TINY);

	@Nullable
	private IFruitFamily family;
	private boolean isRipening;
	private int diffR, diffG, diffB;
	private FruitPod pod;
	private int ripeningPeriod;
	private int colourUnripe;
	private int colour;
	private FruitSprite sprite;
	private Map<ItemStack, Float> products;

	@Nullable
	private static LinkedList<AlleleETFruit> list;

	public static List<AlleleETFruit> values() {
		if (list == null) {
			list = new LinkedList<>();
			for (Field f : AlleleETFruit.class.getFields()) {
				if (f.getType() == AlleleETFruit.class) {
					try {
						list.add((AlleleETFruit) f.get(AlleleETFruit.Acorn));
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}

		return list;
	}

	public static void init() {
		final IFruitFamily familyPrune = AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes");
		final IFruitFamily familyPome = AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes");
		final IFruitFamily familyJungle = AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle");
		final IFruitFamily familyNuts = AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts");
		final IFruitFamily familyBerry = ETFruitFamily.Berry;
		final IFruitFamily familyCitrus = ETFruitFamily.Citrus;
		AlleleManager.alleleRegistry.registerFruitFamily(familyBerry);
		AlleleManager.alleleRegistry.registerFruitFamily(familyCitrus);
		AlleleETFruit.Apple.addProduct(new ItemStack(Items.APPLE), 1.0f);
		AlleleETFruit.Apple.setFamily(familyPome);
		AlleleETFruit.Crabapple.addProduct(Food.CRABAPPLE.get(1), 1.0f);
		AlleleETFruit.Crabapple.setFamily(familyPome);
		AlleleETFruit.Orange.addProduct(Food.ORANGE.get(1), 1.0f);
		AlleleETFruit.Orange.setFamily(familyCitrus);
		AlleleETFruit.Manderin.addProduct(Food.MANDERIN.get(1), 1.0f);
		AlleleETFruit.Manderin.setFamily(familyCitrus);
		AlleleETFruit.Tangerine.addProduct(Food.TANGERINE.get(1), 1.0f);
		AlleleETFruit.Tangerine.setFamily(familyCitrus);
		AlleleETFruit.Satsuma.addProduct(Food.SATSUMA.get(1), 1.0f);
		AlleleETFruit.Satsuma.setFamily(familyCitrus);
		AlleleETFruit.KeyLime.addProduct(Food.KEY_LIME.get(1), 1.0f);
		AlleleETFruit.KeyLime.setFamily(familyCitrus);
		AlleleETFruit.Lime.addProduct(Food.LIME.get(1), 1.0f);
		AlleleETFruit.Lime.setFamily(familyCitrus);
		AlleleETFruit.FingerLime.addProduct(Food.FINGER_LIME.get(1), 1.0f);
		AlleleETFruit.FingerLime.setFamily(familyCitrus);
		AlleleETFruit.Pomelo.addProduct(Food.POMELO.get(1), 1.0f);
		AlleleETFruit.Pomelo.setFamily(familyCitrus);
		AlleleETFruit.Grapefruit.addProduct(Food.GRAPEFRUIT.get(1), 1.0f);
		AlleleETFruit.Grapefruit.setFamily(familyCitrus);
		AlleleETFruit.Kumquat.addProduct(Food.KUMQUAT.get(1), 1.0f);
		AlleleETFruit.Kumquat.setFamily(familyCitrus);
		AlleleETFruit.Citron.addProduct(Food.CITRON.get(1), 1.0f);
		AlleleETFruit.Citron.setFamily(familyCitrus);
		AlleleETFruit.BuddhaHand.addProduct(Food.BUDDHA_HAND.get(1), 1.0f);
		AlleleETFruit.BuddhaHand.setFamily(familyCitrus);
		AlleleETFruit.Blackthorn.addProduct(Food.Blackthorn.get(1), 1.0f);
		AlleleETFruit.Blackthorn.setFamily(familyPrune);
		AlleleETFruit.CherryPlum.addProduct(Food.CHERRY_PLUM.get(1), 1.0f);
		AlleleETFruit.CherryPlum.setFamily(familyPrune);
		AlleleETFruit.Peach.addProduct(Food.PEACH.get(1), 1.0f);
		AlleleETFruit.Peach.setFamily(familyPrune);
		AlleleETFruit.Nectarine.addProduct(Food.NECTARINE.get(1), 1.0f);
		AlleleETFruit.Nectarine.setFamily(familyPrune);
		AlleleETFruit.Apricot.addProduct(Food.APRICOT.get(1), 1.0f);
		AlleleETFruit.Apricot.setFamily(familyPrune);
		AlleleETFruit.Almond.addProduct(Food.ALMOND.get(1), 1.0f);
		AlleleETFruit.Almond.setFamily(familyPrune);
		AlleleETFruit.WildCherry.addProduct(Food.WILD_CHERRY.get(1), 1.0f);
		AlleleETFruit.WildCherry.setFamily(familyPrune);
		AlleleETFruit.SourCherry.addProduct(Food.SOUR_CHERRY.get(1), 1.0f);
		AlleleETFruit.SourCherry.setFamily(familyPrune);
		AlleleETFruit.BlackCherry.addProduct(Food.BLACK_CHERRY.get(1), 1.0f);
		AlleleETFruit.BlackCherry.setFamily(familyPrune);
		AlleleETFruit.Hazelnut.addProduct(Food.HAZELNUT.get(1), 1.0f);
		AlleleETFruit.Hazelnut.setFamily(familyNuts);
		AlleleETFruit.Butternut.addProduct(Food.BUTTERNUT.get(1), 1.0f);
		AlleleETFruit.Butternut.setFamily(familyNuts);
		AlleleETFruit.Beechnut.addProduct(Food.BEECHNUT.get(1), 1.0f);
		AlleleETFruit.Beechnut.setFamily(familyNuts);
		AlleleETFruit.Pecan.addProduct(Food.PECAN.get(1), 1.0f);
		AlleleETFruit.Pecan.setFamily(familyNuts);
		AlleleETFruit.Banana.addProduct(Food.BANANA.get(2), 1.0f);
		AlleleETFruit.Banana.setFamily(familyJungle);
		AlleleETFruit.RedBanana.addProduct(Food.RED_BANANA.get(2), 1.0f);
		AlleleETFruit.RedBanana.setFamily(familyJungle);
		AlleleETFruit.Plantain.addProduct(Food.PLANTAIN.get(2), 1.0f);
		AlleleETFruit.Plantain.setFamily(familyJungle);
		AlleleETFruit.BrazilNut.addProduct(Food.BRAZIL_NUT.get(4), 1.0f);
		AlleleETFruit.BrazilNut.setFamily(familyNuts);
		AlleleETFruit.Fig.addProduct(Food.FIG.get(1), 1.0f);
		AlleleETFruit.Fig.setFamily(familyPrune);
		AlleleETFruit.Acorn.addProduct(Food.ACORN.get(1), 1.0f);
		AlleleETFruit.Acorn.setFamily(familyNuts);
		AlleleETFruit.Elderberry.addProduct(Food.ELDERBERRY.get(1), 1.0f);
		AlleleETFruit.Elderberry.setFamily(familyPrune);
		AlleleETFruit.Olive.addProduct(Food.OLIVE.get(1), 1.0f);
		AlleleETFruit.Olive.setFamily(familyPrune);
		AlleleETFruit.GingkoNut.addProduct(Food.GINGKO_NUT.get(1), 1.0f);
		AlleleETFruit.GingkoNut.setFamily(familyNuts);
		AlleleETFruit.Coffee.addProduct(Food.COFFEE.get(1), 1.0f);
		AlleleETFruit.Coffee.setFamily(familyJungle);
		AlleleETFruit.Pear.addProduct(Food.PEAR.get(1), 1.0f);
		AlleleETFruit.Pear.setFamily(familyPome);
		AlleleETFruit.OsangeOsange.addProduct(Food.OSANGE_ORANGE.get(1), 1.0f);
		AlleleETFruit.OsangeOsange.setFamily(familyPome);
		AlleleETFruit.Clove.addProduct(Food.CLOVE.get(1), 1.0f);
		AlleleETFruit.Clove.setFamily(familyNuts);
		AlleleETFruit.Blackcurrant.addProduct(Food.BLACKCURRANT.get(2), 1.0f);
		AlleleETFruit.Blackcurrant.setFamily(familyBerry);
		AlleleETFruit.Redcurrant.addProduct(Food.REDCURRANT.get(2), 1.0f);
		AlleleETFruit.Redcurrant.setFamily(familyBerry);
		AlleleETFruit.Blackberry.addProduct(Food.BLACKBERRY.get(2), 1.0f);
		AlleleETFruit.Blackberry.setFamily(familyBerry);
		AlleleETFruit.Raspberry.addProduct(Food.RASPBERRY.get(2), 1.0f);
		AlleleETFruit.Raspberry.setFamily(familyBerry);
		AlleleETFruit.Blueberry.addProduct(Food.BLUEBERRY.get(2), 1.0f);
		AlleleETFruit.Blueberry.setFamily(familyBerry);
		AlleleETFruit.Cranberry.addProduct(Food.CRANBERRY.get(2), 1.0f);
		AlleleETFruit.Cranberry.setFamily(familyBerry);
		AlleleETFruit.Juniper.addProduct(Food.JUNIPER.get(2), 1.0f);
		AlleleETFruit.Juniper.setFamily(familyBerry);
		AlleleETFruit.Gooseberry.addProduct(Food.GOOSEBERRY.get(2), 1.0f);
		AlleleETFruit.Gooseberry.setFamily(familyBerry);
		AlleleETFruit.GoldenRaspberry.addProduct(Food.GOLDEN_RASPBERRY.get(2), 1.0f);
		AlleleETFruit.GoldenRaspberry.setFamily(familyBerry);
		AlleleETFruit.Coconut.addProduct(Food.COCONUT.get(1), 1.0f);
		AlleleETFruit.Coconut.setFamily(familyJungle);
		AlleleETFruit.Cashew.addProduct(Food.CASHEW.get(1), 1.0f);
		AlleleETFruit.Cashew.setFamily(familyJungle);
		AlleleETFruit.Avacado.addProduct(Food.AVACADO.get(1), 1.0f);
		AlleleETFruit.Avacado.setFamily(familyJungle);
		AlleleETFruit.Nutmeg.addProduct(Food.NUTMEG.get(1), 1.0f);
		AlleleETFruit.Nutmeg.setFamily(familyJungle);
		AlleleETFruit.Allspice.addProduct(Food.ALLSPICE.get(1), 1.0f);
		AlleleETFruit.Allspice.setFamily(familyJungle);
		AlleleETFruit.Chilli.addProduct(Food.CHILLI.get(1), 1.0f);
		AlleleETFruit.Chilli.setFamily(familyJungle);
		AlleleETFruit.StarAnise.addProduct(Food.STAR_ANISE.get(1), 1.0f);
		AlleleETFruit.StarAnise.setFamily(familyJungle);
		AlleleETFruit.Mango.addProduct(Food.MANGO.get(1), 1.0f);
		AlleleETFruit.Mango.setFamily(familyPome);
		AlleleETFruit.Starfruit.addProduct(Food.STARFRUIT.get(1), 1.0f);
		AlleleETFruit.Starfruit.setFamily(familyJungle);
		AlleleETFruit.Candlenut.addProduct(Food.CANDLENUT.get(1), 1.0f);
		AlleleETFruit.Candlenut.setFamily(familyJungle);
		if (ConfigurationMain.alterLemon) {
			try {
				final IAlleleFruit lemon = (IAlleleFruit) AlleleManager.alleleRegistry.getAllele("forestry.fruitLemon");
				final FruitProviderNone prov = (FruitProviderNone) lemon.getProvider();
				final Field f = FruitProviderNone.class.getDeclaredField("family");
				final Field modifiersField = Field.class.getDeclaredField("modifiers");
				f.setAccessible(true);
				modifiersField.setAccessible(true);
				modifiersField.setInt(f, f.getModifiers() & 0xFFFFFFEF);
				f.set(prov, familyCitrus);
			} catch (Exception e) {
				throw Throwables.propagate(e);
			}
		}
		for (final IAlleleSpecies tree : Binnie.GENETICS.treeBreedingSystem.getAllSpecies()) {
			if (tree instanceof AlleleTreeSpecies && ((IAlleleTreeSpecies) tree).getSuitableFruit().contains(familyPrune)) {
				((AlleleTreeSpecies) tree).addFruitFamily(familyCitrus);
			}
		}
	}

	private AlleleETFruit(String name, int time, int unripe, int colour, FruitSprite sprite) {
		super(Constants.EXTRA_TREES_MOD_ID, "fruit", name, true);
		this.isRipening = false;
		this.diffB = 0;
		this.pod = null;
		this.ripeningPeriod = 0;
		this.products = new HashMap<>();
		this.colour = colour;
		this.sprite = sprite;
		this.setRipening(time, unripe);
	}

	private AlleleETFruit(String name, FruitPod pod) {
		super(Constants.EXTRA_TREES_MOD_ID, "fruit", name, true);
		this.isRipening = false;
		this.diffB = 0;
		this.sprite = null;
		this.products = new HashMap<>();
		this.pod = pod;
		this.ripeningPeriod = 2;
	}

	public void setRipening(int time, int unripe) {
		this.ripeningPeriod = time;
		this.colourUnripe = unripe;
		this.isRipening = true;
		this.diffR = (this.colour >> 16 & 0xFF) - (unripe >> 16 & 0xFF);
		this.diffG = (this.colour >> 8 & 0xFF) - (unripe >> 8 & 0xFF);
		this.diffB = (this.colour & 0xFF) - (unripe & 0xFF);
	}

	public void addProduct(ItemStack product, float chance) {
		this.products.put(product, chance);
	}
	
	private void setFamily(IFruitFamily family) {
		this.family = family;
	}

	@Override
	public IFruitProvider getProvider() {
		return this;
	}

	@Override
	public void registerSprites() {
		if(this == Apple){
			for(FruitSprite sprite : FruitSprite.VALUES){
				sprite.registerSprites();
			}
		}
	}

	@Nullable
	@Override
	public ResourceLocation getSprite(ITreeGenome genome, IBlockAccess world, BlockPos pos, int ripeningTime) {
		if(sprite != null){
			return sprite.getLocation();
		}else{
			return null;
		}
	}

	@Override
	public int compareTo(IAlleleFruit o) {
		return o == this ? 0 : -1;
	}

	@Override
	public Map<ItemStack, Float> getSpecialty() {
		return new HashMap<>();
	}

	@Override
	public int getDecorativeColor() {
		return getColour(1.0f);
	}

	@Override
	public boolean trySpawnFruitBlock(ITreeGenome genome, World world, Random rand, BlockPos pos) {
		if(this.pod != null && world.rand.nextFloat() <= genome.getSappiness()){
			TreeManager.treeRoot.setFruitBlock(world, this, genome.getSappiness(), pos);
		}
		return false;
	}

	@Override
	public List<ItemStack> getFruits(ITreeGenome genome, World world, BlockPos pos, int ripeningTime) {
		if (this.pod != null) {
			if (ripeningTime >= 2) {
				List<ItemStack> products = new ArrayList<ItemStack>();
				for (Map.Entry<ItemStack, Float> product : this.products.entrySet()) {
					ItemStack single = product.getKey().copy();
					single.stackSize = 1;
					for (int i = 0; i < product.getKey().stackSize; ++i) {
						if (world.rand.nextFloat() <= product.getValue()) {
							products.add(single.copy());
						}
					}
				}
				return products;
			}
			return Collections.emptyList();
		} else {
			ArrayList<ItemStack> products = new ArrayList<ItemStack>();
			float stage = this.getRipeningStage(ripeningTime);
			if (stage < 0.5f) {
				return Collections.emptyList();
			}
			float modeYieldMod = 1.0f;
			for (final Map.Entry<ItemStack, Float> product : this.products.entrySet()) {
				if (world.rand.nextFloat() <= genome.getYield() * modeYieldMod * product.getValue() * 5.0f * stage) {
					products.add(product.getKey().copy());
				}
			}
			return products;
		}
	}

	@Nullable
	@Override
	public String getModelName() {
		if(pod != null){
			return pod.name();
		}else{
			return null;
		}
	}

	@Override
	public boolean isFruitLeaf(ITreeGenome genome, World world, BlockPos pos) {
		return sprite != null;
	}

	@Override
	public String getModID() {
		return Constants.EXTRA_TREES_MOD_ID;
	}
	
	@Override
	public int getColour(ITreeGenome genome, IBlockAccess world, BlockPos pos, int ripeningTime) {
		if (!this.isRipening) {
			return this.colour;
		}
		float stage = getRipeningStage(ripeningTime);
		return getColour(stage);
	}

	private int getColour(float stage) {
		final int r = (this.colourUnripe >> 16 & 0xFF) + (int) (this.diffR * stage);
		final int g = (this.colourUnripe >> 8 & 0xFF) + (int) (this.diffG * stage);
		final int b = (this.colourUnripe & 0xFF) + (int) (this.diffB * stage);
		return (r & 0xFF) << 16 | (g & 0xFF) << 8 | (b & 0xFF);
	}


	@Override
	public String getDescription() {
		return ExtraTrees.proxy.localise("item.food." + this.getClass().getTypeName().toLowerCase());
	}

	@Override
	public IFruitFamily getFamily() {
		Preconditions.checkState(this.family != null, "Family has not been set.");
		return this.family;
	}

	@Override
	public int getRipeningPeriod() {
		return this.ripeningPeriod;
	}

	@Override
	public Map<ItemStack, Float> getProducts() {
		return products;
	}

	private float getRipeningStage(final int ripeningTime) {
		if (ripeningTime >= this.ripeningPeriod) {
			return 1.0f;
		}
		return ripeningTime / this.ripeningPeriod;
	}

	@Override
	public boolean requiresFruitBlocks() {
		return this.pod != null;
	}

//	@Override
//	public short getIconIndex(final ITreeGenome genome, final IBlockAccess world, final int x, final int y, final int z, final int ripeningTime, final boolean fancy) {
//		return this.index.getIndex();
//	}
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(final IIconRegister register) {
//		if (this.ordinal() == 0) {
//			for (final FruitSprite sprite : FruitSprite.values()) {
//				sprite.registerIcons(register);
//			}
//		}
//	}


	@Nullable
	@Override
	public ResourceLocation getDecorativeSprite() {
		if(sprite != null){
			return sprite.getLocation();
		}else{
			return null;
		}
	}

	@Override
	public String getName() {
		return this.getDescription();
	}

	public String getNameOfFruit() {
		if (this == AlleleETFruit.Apple) {
			return "Apple";
		}
		for (final ItemStack stack : this.products.keySet()) {
			if (stack.getItem() == ExtraTrees.items().itemFood) {
				return Food.values()[stack.getItemDamage()].toString();
			}
		}
		return "NoFruit";
	}

	@Override
	public String getUnlocalizedName() {
		return this.getUID();
	}
}
