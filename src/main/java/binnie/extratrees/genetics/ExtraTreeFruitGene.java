package binnie.extratrees.genetics;

import binnie.Binnie;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.FruitPod;
import binnie.extratrees.config.ConfigurationMain;
import binnie.extratrees.item.Food;
import forestry.api.arboriculture.IAlleleFruit;
import forestry.api.arboriculture.IAlleleTreeSpecies;
import forestry.api.arboriculture.IFruitProvider;
import forestry.api.arboriculture.ITreeGenome;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IFruitFamily;
import forestry.arboriculture.FruitProviderNone;
import forestry.arboriculture.genetics.alleles.AlleleTreeSpecies;
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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class ExtraTreeFruitGene implements IAlleleFruit, IFruitProvider {
	public static final ExtraTreeFruitGene Blackthorn = new ExtraTreeFruitGene("Blackthorn", 10, 7180062, 14561129, FruitSprite.Small);
	public static final ExtraTreeFruitGene CherryPlum = new ExtraTreeFruitGene("CherryPlum", 10, 7180062, 15211595, FruitSprite.Small);
	public static final ExtraTreeFruitGene Peach = new ExtraTreeFruitGene("Peach	", 10, 7180062, 16424995, FruitSprite.Average);
	public static final ExtraTreeFruitGene Nectarine = new ExtraTreeFruitGene("Nectarine", 10, 7180062, 16405795, FruitSprite.Average);
	public static final ExtraTreeFruitGene Apricot = new ExtraTreeFruitGene("Apricot", 10, 7180062, 16437027, FruitSprite.Average);
	public static final ExtraTreeFruitGene Almond = new ExtraTreeFruitGene("Almond", 10, 7180062, 9364342, FruitSprite.Small);
	public static final ExtraTreeFruitGene WildCherry = new ExtraTreeFruitGene("WildCherry", 10, 7180062, 16711680, FruitSprite.Tiny);
	public static final ExtraTreeFruitGene SourCherry = new ExtraTreeFruitGene("SourCherry", 10, 7180062, 10225963, FruitSprite.Tiny);
	public static final ExtraTreeFruitGene BlackCherry = new ExtraTreeFruitGene("BlackCherry", 10, 7180062, 4852249, FruitSprite.Tiny);
	public static final ExtraTreeFruitGene Orange = new ExtraTreeFruitGene("Orange", 10, 3665987, 16749578, FruitSprite.Average);
	public static final ExtraTreeFruitGene Manderin = new ExtraTreeFruitGene("Manderin", 10, 3665987, 16749578, FruitSprite.Average);
	public static final ExtraTreeFruitGene Tangerine = new ExtraTreeFruitGene("Tangerine", 10, 3665987, 16749578, FruitSprite.Average);
	public static final ExtraTreeFruitGene Satsuma = new ExtraTreeFruitGene("Satsuma", 10, 3665987, 16749578, FruitSprite.Average);
	public static final ExtraTreeFruitGene KeyLime = new ExtraTreeFruitGene("KeyLime", 10, 3665987, 10223428, FruitSprite.Small);
	public static final ExtraTreeFruitGene Lime = new ExtraTreeFruitGene("Lime", 10, 3665987, 10223428, FruitSprite.Average);
	public static final ExtraTreeFruitGene FingerLime = new ExtraTreeFruitGene("FingerLime", 10, 3665987, 11156280, FruitSprite.Small);
	public static final ExtraTreeFruitGene Pomelo = new ExtraTreeFruitGene("Pomelo", 10, 3665987, 6083402, FruitSprite.Larger);
	public static final ExtraTreeFruitGene Grapefruit = new ExtraTreeFruitGene("Grapefruit", 10, 3665987, 16749578, FruitSprite.Large);
	public static final ExtraTreeFruitGene Kumquat = new ExtraTreeFruitGene("Kumquat", 10, 3665987, 16749578, FruitSprite.Small);
	public static final ExtraTreeFruitGene Citron = new ExtraTreeFruitGene("Citron", 10, 3665987, 16772192, FruitSprite.Large);
	public static final ExtraTreeFruitGene BuddhaHand = new ExtraTreeFruitGene("BuddhaHand", 10, 3665987, 16772192, FruitSprite.Large);
	public static final ExtraTreeFruitGene Apple = new ExtraTreeFruitGene("Apple", 10, 7915859, 16193046, FruitSprite.Average);
	public static final ExtraTreeFruitGene Crabapple = new ExtraTreeFruitGene("Crabapple	", 10, 7915859, 16760140, FruitSprite.Average);
	public static final ExtraTreeFruitGene Banana = new ExtraTreeFruitGene("Banana", "Banana", FruitPod.Banana);
	public static final ExtraTreeFruitGene RedBanana = new ExtraTreeFruitGene("RedBanana", "Red Banana", FruitPod.RedBanana);
	public static final ExtraTreeFruitGene Plantain = new ExtraTreeFruitGene("Plantain", "Platain", FruitPod.Plantain);
	public static final ExtraTreeFruitGene Hazelnut = new ExtraTreeFruitGene("Hazelnut", 7, 8223006, 14463606, FruitSprite.Small);
	public static final ExtraTreeFruitGene Butternut = new ExtraTreeFruitGene("Butternut", 7, 11712336, 16102498, FruitSprite.Small);
	public static final ExtraTreeFruitGene Beechnut = new ExtraTreeFruitGene("Beechnut", 8, 14401148, 6241845, FruitSprite.Tiny);
	public static final ExtraTreeFruitGene Pecan = new ExtraTreeFruitGene("Pecan", 8, 10660940, 15781769, FruitSprite.Small);
	public static final ExtraTreeFruitGene BrazilNut = new ExtraTreeFruitGene("BrazilNut", 10, 5875561, 9852208, FruitSprite.Large);
	public static final ExtraTreeFruitGene Fig = new ExtraTreeFruitGene("Fig", 9, 14201186, 7094086, FruitSprite.Small);
	public static final ExtraTreeFruitGene Acorn = new ExtraTreeFruitGene("Acorn", 6, 7516710, 11364893, FruitSprite.Tiny);
	public static final ExtraTreeFruitGene Elderberry = new ExtraTreeFruitGene("Elderberry", 9, 7444317, 5331779, FruitSprite.Tiny);
	public static final ExtraTreeFruitGene Olive = new ExtraTreeFruitGene("Olive", 9, 8887861, 6444842, FruitSprite.Small);
	public static final ExtraTreeFruitGene GingkoNut = new ExtraTreeFruitGene("GingkoNut", 7, 9213787, 15063725, FruitSprite.Tiny);
	public static final ExtraTreeFruitGene Coffee = new ExtraTreeFruitGene("Coffee", 8, 7433501, 16273254, FruitSprite.Tiny);
	public static final ExtraTreeFruitGene Pear = new ExtraTreeFruitGene("Pear", 10, 10456913, 10474833, FruitSprite.Pear);
	public static final ExtraTreeFruitGene OsangeOsange = new ExtraTreeFruitGene("OsangeOsange", 10, 9934674, 10665767, FruitSprite.Larger);
	public static final ExtraTreeFruitGene Clove = new ExtraTreeFruitGene("Clove", 9, 6847532, 11224133, FruitSprite.Tiny);
	public static final ExtraTreeFruitGene Coconut = new ExtraTreeFruitGene("Coconut", "Coconut", FruitPod.Coconut);
	public static final ExtraTreeFruitGene Cashew = new ExtraTreeFruitGene("Cashew", 8, 12879132, 15289111, FruitSprite.Average);
	public static final ExtraTreeFruitGene Avacado = new ExtraTreeFruitGene("Avacado", 10, 10272370, 2170640, FruitSprite.Pear);
	public static final ExtraTreeFruitGene Nutmeg = new ExtraTreeFruitGene("Nutmeg", 9, 14861101, 11305813, FruitSprite.Tiny);
	public static final ExtraTreeFruitGene Allspice = new ExtraTreeFruitGene("Allspice", 9, 15180922, 7423542, FruitSprite.Tiny);
	public static final ExtraTreeFruitGene Chilli = new ExtraTreeFruitGene("Chilli", 10, 7430757, 15145010, FruitSprite.Small);
	public static final ExtraTreeFruitGene StarAnise = new ExtraTreeFruitGene("StarAnise", 8, 8733742, 13917189, FruitSprite.Tiny);
	public static final ExtraTreeFruitGene Mango = new ExtraTreeFruitGene("Mango", 10, 6654997, 15902262, FruitSprite.Average);
	public static final ExtraTreeFruitGene Starfruit = new ExtraTreeFruitGene("Starfruit", 10, 9814541, 15061550, FruitSprite.Average);
	public static final ExtraTreeFruitGene Candlenut = new ExtraTreeFruitGene("Candlenut", 8, 8235123, 14600882, FruitSprite.Small);
	public static final ExtraTreeFruitGene Papayimar = new ExtraTreeFruitGene("Papayimar", "Papayimar", FruitPod.Papayimar);
	public static final ExtraTreeFruitGene Blackcurrant = new ExtraTreeFruitGene("Blackcurrant", 8, 9407571, 4935251, FruitSprite.Tiny);
	public static final ExtraTreeFruitGene Redcurrant = new ExtraTreeFruitGene("Redcurrant", 8, 13008910, 15080974, FruitSprite.Tiny);
	public static final ExtraTreeFruitGene Blackberry = new ExtraTreeFruitGene("Blackberry", 8, 9399665, 4801393, FruitSprite.Tiny);
	public static final ExtraTreeFruitGene Raspberry = new ExtraTreeFruitGene("Raspberry", 8, 15520197, 14510449, FruitSprite.Tiny);
	public static final ExtraTreeFruitGene Blueberry = new ExtraTreeFruitGene("Blueberry", 8, 10203799, 6329278, FruitSprite.Tiny);
	public static final ExtraTreeFruitGene Cranberry = new ExtraTreeFruitGene("Cranberry", 8, 12232496, 14555696, FruitSprite.Tiny);
	public static final ExtraTreeFruitGene Juniper = new ExtraTreeFruitGene("Juniper", 8, 10194034, 6316914, FruitSprite.Tiny);
	public static final ExtraTreeFruitGene Gooseberry = new ExtraTreeFruitGene("Gooseberry", 8, 12164944, 12177232, FruitSprite.Tiny);
	public static final ExtraTreeFruitGene GoldenRaspberry = new ExtraTreeFruitGene("GoldenRaspberry", 8, 12496955, 15970363, FruitSprite.Tiny);

	IFruitFamily family;
	boolean isRipening;
	int diffR;
	int diffG;
	int diffB;
	FruitPod pod;
	int ripeningPeriod;
	int colourUnripe;
	int colour;
	FruitSprite index;
	HashMap<ItemStack, Float> products;


	private static LinkedList<ExtraTreeFruitGene> list;

	public static List<ExtraTreeFruitGene> values() {
		if (list == null) {
			list = new LinkedList<>();
			for (Field f : ExtraTreeFruitGene.class.getFields()) {
				if (f.getType() == ExtraTreeFruitGene.class) {
					try {
						list.add((ExtraTreeFruitGene) f.get(ExtraTreeFruitGene.Acorn));
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
		final IFruitFamily familyBerry = ExtraTreeFruitFamily.Berry;
		final IFruitFamily familyCitrus = ExtraTreeFruitFamily.Citrus;
		AlleleManager.alleleRegistry.registerFruitFamily(familyBerry);
		AlleleManager.alleleRegistry.registerFruitFamily(familyCitrus);
		ExtraTreeFruitGene.Apple.addProduct(new ItemStack(Items.APPLE), 1.0f);
		ExtraTreeFruitGene.Apple.setFamily(familyPome);
		ExtraTreeFruitGene.Crabapple.addProduct(Food.Crabapple.get(1), 1.0f);
		ExtraTreeFruitGene.Crabapple.setFamily(familyPome);
		ExtraTreeFruitGene.Orange.addProduct(Food.Orange.get(1), 1.0f);
		ExtraTreeFruitGene.Orange.setFamily(familyCitrus);
		ExtraTreeFruitGene.Manderin.addProduct(Food.Manderin.get(1), 1.0f);
		ExtraTreeFruitGene.Manderin.setFamily(familyCitrus);
		ExtraTreeFruitGene.Tangerine.addProduct(Food.Tangerine.get(1), 1.0f);
		ExtraTreeFruitGene.Tangerine.setFamily(familyCitrus);
		ExtraTreeFruitGene.Satsuma.addProduct(Food.Satsuma.get(1), 1.0f);
		ExtraTreeFruitGene.Satsuma.setFamily(familyCitrus);
		ExtraTreeFruitGene.KeyLime.addProduct(Food.KeyLime.get(1), 1.0f);
		ExtraTreeFruitGene.KeyLime.setFamily(familyCitrus);
		ExtraTreeFruitGene.Lime.addProduct(Food.Lime.get(1), 1.0f);
		ExtraTreeFruitGene.Lime.setFamily(familyCitrus);
		ExtraTreeFruitGene.FingerLime.addProduct(Food.FingerLime.get(1), 1.0f);
		ExtraTreeFruitGene.FingerLime.setFamily(familyCitrus);
		ExtraTreeFruitGene.Pomelo.addProduct(Food.Pomelo.get(1), 1.0f);
		ExtraTreeFruitGene.Pomelo.setFamily(familyCitrus);
		ExtraTreeFruitGene.Grapefruit.addProduct(Food.Grapefruit.get(1), 1.0f);
		ExtraTreeFruitGene.Grapefruit.setFamily(familyCitrus);
		ExtraTreeFruitGene.Kumquat.addProduct(Food.Kumquat.get(1), 1.0f);
		ExtraTreeFruitGene.Kumquat.setFamily(familyCitrus);
		ExtraTreeFruitGene.Citron.addProduct(Food.Citron.get(1), 1.0f);
		ExtraTreeFruitGene.Citron.setFamily(familyCitrus);
		ExtraTreeFruitGene.BuddhaHand.addProduct(Food.BuddhaHand.get(1), 1.0f);
		ExtraTreeFruitGene.BuddhaHand.setFamily(familyCitrus);
		ExtraTreeFruitGene.Blackthorn.addProduct(Food.Blackthorn.get(1), 1.0f);
		ExtraTreeFruitGene.Blackthorn.setFamily(familyPrune);
		ExtraTreeFruitGene.CherryPlum.addProduct(Food.CherryPlum.get(1), 1.0f);
		ExtraTreeFruitGene.CherryPlum.setFamily(familyPrune);
		ExtraTreeFruitGene.Peach.addProduct(Food.Peach.get(1), 1.0f);
		ExtraTreeFruitGene.Peach.setFamily(familyPrune);
		ExtraTreeFruitGene.Nectarine.addProduct(Food.Nectarine.get(1), 1.0f);
		ExtraTreeFruitGene.Nectarine.setFamily(familyPrune);
		ExtraTreeFruitGene.Apricot.addProduct(Food.Apricot.get(1), 1.0f);
		ExtraTreeFruitGene.Apricot.setFamily(familyPrune);
		ExtraTreeFruitGene.Almond.addProduct(Food.Almond.get(1), 1.0f);
		ExtraTreeFruitGene.Almond.setFamily(familyPrune);
		ExtraTreeFruitGene.WildCherry.addProduct(Food.WildCherry.get(1), 1.0f);
		ExtraTreeFruitGene.WildCherry.setFamily(familyPrune);
		ExtraTreeFruitGene.SourCherry.addProduct(Food.SourCherry.get(1), 1.0f);
		ExtraTreeFruitGene.SourCherry.setFamily(familyPrune);
		ExtraTreeFruitGene.BlackCherry.addProduct(Food.BlackCherry.get(1), 1.0f);
		ExtraTreeFruitGene.BlackCherry.setFamily(familyPrune);
		ExtraTreeFruitGene.Hazelnut.addProduct(Food.Hazelnut.get(1), 1.0f);
		ExtraTreeFruitGene.Hazelnut.setFamily(familyNuts);
		ExtraTreeFruitGene.Butternut.addProduct(Food.Butternut.get(1), 1.0f);
		ExtraTreeFruitGene.Butternut.setFamily(familyNuts);
		ExtraTreeFruitGene.Beechnut.addProduct(Food.Beechnut.get(1), 1.0f);
		ExtraTreeFruitGene.Beechnut.setFamily(familyNuts);
		ExtraTreeFruitGene.Pecan.addProduct(Food.Pecan.get(1), 1.0f);
		ExtraTreeFruitGene.Pecan.setFamily(familyNuts);
		ExtraTreeFruitGene.Banana.addProduct(Food.Banana.get(2), 1.0f);
		ExtraTreeFruitGene.Banana.setFamily(familyJungle);
		ExtraTreeFruitGene.RedBanana.addProduct(Food.RedBanana.get(2), 1.0f);
		ExtraTreeFruitGene.RedBanana.setFamily(familyJungle);
		ExtraTreeFruitGene.Plantain.addProduct(Food.Plantain.get(2), 1.0f);
		ExtraTreeFruitGene.Plantain.setFamily(familyJungle);
		ExtraTreeFruitGene.BrazilNut.addProduct(Food.BrazilNut.get(4), 1.0f);
		ExtraTreeFruitGene.BrazilNut.setFamily(familyNuts);
		ExtraTreeFruitGene.Fig.addProduct(Food.Fig.get(1), 1.0f);
		ExtraTreeFruitGene.Fig.setFamily(familyPrune);
		ExtraTreeFruitGene.Acorn.addProduct(Food.Acorn.get(1), 1.0f);
		ExtraTreeFruitGene.Acorn.setFamily(familyNuts);
		ExtraTreeFruitGene.Elderberry.addProduct(Food.Elderberry.get(1), 1.0f);
		ExtraTreeFruitGene.Elderberry.setFamily(familyPrune);
		ExtraTreeFruitGene.Olive.addProduct(Food.Olive.get(1), 1.0f);
		ExtraTreeFruitGene.Olive.setFamily(familyPrune);
		ExtraTreeFruitGene.GingkoNut.addProduct(Food.GingkoNut.get(1), 1.0f);
		ExtraTreeFruitGene.GingkoNut.setFamily(familyNuts);
		ExtraTreeFruitGene.Coffee.addProduct(Food.Coffee.get(1), 1.0f);
		ExtraTreeFruitGene.Coffee.setFamily(familyJungle);
		ExtraTreeFruitGene.Pear.addProduct(Food.Pear.get(1), 1.0f);
		ExtraTreeFruitGene.Pear.setFamily(familyPome);
		ExtraTreeFruitGene.OsangeOsange.addProduct(Food.OsangeOrange.get(1), 1.0f);
		ExtraTreeFruitGene.OsangeOsange.setFamily(familyPome);
		ExtraTreeFruitGene.Clove.addProduct(Food.Clove.get(1), 1.0f);
		ExtraTreeFruitGene.Clove.setFamily(familyNuts);
		ExtraTreeFruitGene.Blackcurrant.addProduct(Food.Blackcurrant.get(2), 1.0f);
		ExtraTreeFruitGene.Blackcurrant.setFamily(familyBerry);
		ExtraTreeFruitGene.Redcurrant.addProduct(Food.Redcurrant.get(2), 1.0f);
		ExtraTreeFruitGene.Redcurrant.setFamily(familyBerry);
		ExtraTreeFruitGene.Blackberry.addProduct(Food.Blackberry.get(2), 1.0f);
		ExtraTreeFruitGene.Blackberry.setFamily(familyBerry);
		ExtraTreeFruitGene.Raspberry.addProduct(Food.Raspberry.get(2), 1.0f);
		ExtraTreeFruitGene.Raspberry.setFamily(familyBerry);
		ExtraTreeFruitGene.Blueberry.addProduct(Food.Blueberry.get(2), 1.0f);
		ExtraTreeFruitGene.Blueberry.setFamily(familyBerry);
		ExtraTreeFruitGene.Cranberry.addProduct(Food.Cranberry.get(2), 1.0f);
		ExtraTreeFruitGene.Cranberry.setFamily(familyBerry);
		ExtraTreeFruitGene.Juniper.addProduct(Food.Juniper.get(2), 1.0f);
		ExtraTreeFruitGene.Juniper.setFamily(familyBerry);
		ExtraTreeFruitGene.Gooseberry.addProduct(Food.Gooseberry.get(2), 1.0f);
		ExtraTreeFruitGene.Gooseberry.setFamily(familyBerry);
		ExtraTreeFruitGene.GoldenRaspberry.addProduct(Food.GoldenRaspberry.get(2), 1.0f);
		ExtraTreeFruitGene.GoldenRaspberry.setFamily(familyBerry);
		ExtraTreeFruitGene.Coconut.addProduct(Food.Coconut.get(1), 1.0f);
		ExtraTreeFruitGene.Coconut.setFamily(familyJungle);
		ExtraTreeFruitGene.Cashew.addProduct(Food.Cashew.get(1), 1.0f);
		ExtraTreeFruitGene.Cashew.setFamily(familyJungle);
		ExtraTreeFruitGene.Avacado.addProduct(Food.Avacado.get(1), 1.0f);
		ExtraTreeFruitGene.Avacado.setFamily(familyJungle);
		ExtraTreeFruitGene.Nutmeg.addProduct(Food.Nutmeg.get(1), 1.0f);
		ExtraTreeFruitGene.Nutmeg.setFamily(familyJungle);
		ExtraTreeFruitGene.Allspice.addProduct(Food.Allspice.get(1), 1.0f);
		ExtraTreeFruitGene.Allspice.setFamily(familyJungle);
		ExtraTreeFruitGene.Chilli.addProduct(Food.Chilli.get(1), 1.0f);
		ExtraTreeFruitGene.Chilli.setFamily(familyJungle);
		ExtraTreeFruitGene.StarAnise.addProduct(Food.StarAnise.get(1), 1.0f);
		ExtraTreeFruitGene.StarAnise.setFamily(familyJungle);
		ExtraTreeFruitGene.Mango.addProduct(Food.Mango.get(1), 1.0f);
		ExtraTreeFruitGene.Mango.setFamily(familyPome);
		ExtraTreeFruitGene.Starfruit.addProduct(Food.Starfruit.get(1), 1.0f);
		ExtraTreeFruitGene.Starfruit.setFamily(familyJungle);
		ExtraTreeFruitGene.Candlenut.addProduct(Food.Candlenut.get(1), 1.0f);
		ExtraTreeFruitGene.Candlenut.setFamily(familyJungle);
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
				e.printStackTrace();
			}
		}
		for (final IAlleleSpecies tree : Binnie.Genetics.treeBreedingSystem.getAllSpecies()) {
			if (tree instanceof AlleleTreeSpecies && ((IAlleleTreeSpecies) tree).getSuitableFruit().contains(familyPrune)) {
				((AlleleTreeSpecies) tree).addFruitFamily(familyCitrus);
			}
		}
	}

	private void setFamily(final IFruitFamily family) {
		this.family = family;
	}

	String uuid;

	private ExtraTreeFruitGene(final String uuid, final int time, final int unripe, final int colour, final FruitSprite index) {
		this.isRipening = false;
		this.diffB = 0;
		this.pod = null;
		this.ripeningPeriod = 0;
		this.products = new HashMap<>();
		this.colour = colour;
		this.index = index;
		this.setRipening(time, unripe);
		this.uuid = uuid;
	}

	private ExtraTreeFruitGene(final String uuid, final String name, final FruitPod pod) {
		this.isRipening = false;
		this.diffB = 0;
		this.pod = null;
		this.ripeningPeriod = 0;
		this.products = new HashMap<>();
		this.pod = pod;
		this.ripeningPeriod = 2;
		this.uuid = uuid;
	}

	public void setRipening(final int time, final int unripe) {
		this.ripeningPeriod = time;
		this.colourUnripe = unripe;
		this.isRipening = true;
		this.diffR = (this.colour >> 16 & 0xFF) - (unripe >> 16 & 0xFF);
		this.diffG = (this.colour >> 8 & 0xFF) - (unripe >> 8 & 0xFF);
		this.diffB = (this.colour & 0xFF) - (unripe & 0xFF);
	}

	public void addProduct(final ItemStack product, final float chance) {
		this.products.put(product, chance);
	}

	@Override
	public String getUID() {
		return "extratrees.fruit." + this.uuid.toLowerCase().trim();
	}

	@Override
	public boolean isDominant() {
		return true;
	}

	@Override
	public IFruitProvider getProvider() {
		return this;
	}

//	@Override
//	public ItemStack[] getProducts() {
//		return this.products.keySet().toArray(new ItemStack[0]);
//	}
//
//	@Override
//	public ItemStack[] getSpecialty() {
//		return new ItemStack[0];
//	}

	//TODO implement this
	@Override
	public void registerSprites() {

	}


	//TODO implement this
	@Nullable
	@Override
	public ResourceLocation getSprite(ITreeGenome genome, IBlockAccess world, BlockPos pos, int ripeningTime) {
		return new ResourceLocation("extratrees:" + this.getUID());
	}

	//TODO implement this
	@Override
	public int compareTo(@Nonnull IAlleleFruit o) {
		return o == this ? 0 : -1;
	}

	//TODO implement this
	@Nonnull
	@Override
	public Map<ItemStack, Float> getSpecialty() {
		return new HashMap<>();
	}

	//TODO implement this
	@Override
	public int getDecorativeColor() {
		return 0;
	}

	//TODO implement this
	@Override
	public boolean trySpawnFruitBlock(ITreeGenome genome, World world, Random rand, BlockPos pos) {
		return false;
	}

	//TODO implement this
	@Nonnull
	@Override
	public List<ItemStack> getFruits(ITreeGenome genome, World world, BlockPos pos, int ripeningTime) {
		return new ArrayList<>();
	}

	//TODO implement this
	@Nullable
	@Override
	public String getModelName() {
		return "extratrees:" + getUID();
	}

	//TODO implement this
	@Override
	public boolean isFruitLeaf(ITreeGenome genome, World world, BlockPos pos) {
		return false;
	}

	//TODO implement this
	@Nonnull
	@Override
	public String getModID() {
		return "extratrees";
	}

	@Override
	public int getColour(ITreeGenome genome, IBlockAccess world, BlockPos pos, int ripeningTime) {
		if (!this.isRipening) {
			return this.colour;
		}
		final float stage = this.getRipeningStage(ripeningTime);
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
		return this.family;
	}


//	@Override
//	public boolean markAsFruitLeaf(final ITreeGenome genome, final World world, final int x, final int y, final int z) {
//		return this.pod == null;
//	}

	@Override
	public int getRipeningPeriod() {
		return this.ripeningPeriod;
	}


	//TODO producsts
	@Nonnull
	@Override
	public HashMap<ItemStack, Float> getProducts() {
		return products;
	}

	//	@Override
//	public ItemStack[] getFruits(final ITreeGenome genome, final World world, final int x, final int y, final int z, final int ripeningTime) {
//		if (this.pod != null) {
//			if (ripeningTime >= 2) {
//				final List<ItemStack> product = new ArrayList<ItemStack>();
//				for (final Map.Entry<ItemStack, Float> entry : this.products.entrySet()) {
//					final ItemStack single = entry.getKey().copy();
//					single.stackSize = 1;
//					for (int i = 0; i < entry.getKey().stackSize; ++i) {
//						if (world.rand.nextFloat() <= entry.getValue()) {
//							product.add(single.copy());
//						}
//					}
//				}
//				return product.toArray(new ItemStack[0]);
//			}
//			return new ItemStack[0];
//		}
//		else {
//			final ArrayList<ItemStack> product2 = new ArrayList<ItemStack>();
//			final float stage = this.getRipeningStage(ripeningTime);
//			if (stage < 0.5f) {
//				return new ItemStack[0];
//			}
//			final float modeYieldMod = 1.0f;
//			for (final Map.Entry<ItemStack, Float> entry2 : this.products.entrySet()) {
//				if (world.rand.nextFloat() <= genome.getYield() * modeYieldMod * entry2.getValue() * 5.0f * stage) {
//					product2.add(entry2.getKey().copy());
//				}
//			}
//			return product2.toArray(new ItemStack[0]);
//		}
//	}

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
//	public boolean trySpawnFruitBlock(final ITreeGenome genome, final World world, final int x, final int y, final int z) {
//		return this.pod != null && world.rand.nextFloat() <= genome.getSappiness() && Binnie.Genetics.getTreeRoot().setFruitBlock(world, (IAlleleFruit) genome.getActiveAllele(EnumTreeChromosome.FRUITS), genome.getSappiness(), this.pod.getTextures(), x, y, z);
//	}

	public boolean setFruitBlock(final World world, final IAlleleFruit allele, final float sappiness, final int x, final int y, final int z) {
		return true;
	}

//	public static int getDirectionalMetadata(final World world, final int x, final int y, final int z) {
//		for (int i = 0; i < 4; ++i) {
//			if (isValidPot(world, x, y, z, i)) {
//				return i;
//			}
//		}
//		return -1;
//	}

//	public static boolean isValidPot(final World world, int x, final int y, int z, final int notchDirection) {
//		x += Direction.offsetX[notchDirection];
//		z += Direction.offsetZ[notchDirection];
//		final Block block = world.getBlock(x, y, z);
//		if (block == Blocks.log || block == Blocks.log2) {
//			return BlockLog.func_150165_c(world.getBlockMetadata(x, y, z)) == 3;
//		}
//		return block != null && block.isWood(world, x, y, z);
//	}

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
		return null;
	}

	@Override
	public String getName() {
		return this.getDescription();
	}

	public String getNameOfFruit() {
		if (this == ExtraTreeFruitGene.Apple) {
			return "Apple";
		}
		for (final ItemStack stack : this.products.keySet()) {
			if (stack.getItem() == ExtraTrees.itemFood) {
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
