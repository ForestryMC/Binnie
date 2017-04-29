package binnie.extrabees.genetics;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.Mods;
import binnie.core.genetics.ForestryAllele;
import binnie.core.genetics.Tolerance;
import binnie.core.item.IItemEnum;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.genetics.effect.ExtraBeesEffect;
import binnie.extrabees.products.EnumHoneyComb;
import binnie.extrabees.products.ItemHoneyComb;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IAlleleBeeEffect;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeeRoot;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.core.IIconProvider;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleFlowers;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IClassification;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IMutation;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public enum ExtraBeesSpecies implements IAlleleBeeSpecies, IIconProvider {
	ARID("aridus", 12511316),
	BARREN("infelix", 14733923),
	DESOLATE("desolo", 13744272),
	GNAWING("apica", 15234224),
	ROTTEN("caries", 12574902),
	BONE("os", 15330792),
	CREEPER("erepo", 2942485),
	DECOMPOSING("aegrus", 5388049),
	ROCK("saxum", 11053224),
	STONE("lapis", 7697781),
	GRANITE("granum", 6903125),
	MINERAL("minerale", 7239037),
	COPPER("cuprous", 13722376),
	TIN("stannus", 12431805),
	IRON("ferrous", 11038808),
	LEAD("plumbous", 11373483),
	ZINC("spelta", 15592447),
	TITANIUM("titania", 11578083),
	BRONZE,
	BRASS,
	STEEL,
	TUNGSTATE("wolfram", 1249812),
	GOLD("aureus", 15125515),
	SILVER("argentus", 14408667),
	ELECTRUM,
	PLATINUM("platina", 14408667),
	LAPIS("lazuli", 4009179),
	SODALITE,
	PYRITE,
	BAUXITE,
	CINNABAR,
	SPHALERITE,
	EMERALD("emerala", 1900291),
	RUBY("ruba", 14024704),
	SAPPHIRE("saphhira", 673791),
	OLIVINE,
	DIAMOND("diama", 8371706),
	UNSTABLE("levis", 4099124),
	NUCLEAR("nucleus", 4312111),
	RADIOACTIVE("fervens", 2031360),
	ANCIENT("antiquus", 15915919),
	PRIMEVAL("priscus", 11773563),
	PREHISTORIC("pristinus", 7232064),
	RELIC("sapiens", 5062166),
	COAL("carbo", 8025672),
	RESIN("lacrima", 10908443),
	OIL("lubricus", 5719920),
	PEAT,
	DISTILLED("distilli", 3498838),
	FUEL("refina", 16760835),
	CREOSOTE("creosota", 9936403),
	LATEX("latex", 4803134),
	WATER("aqua", 9741055),
	RIVER("flumen", 8631252),
	OCEAN("mare", 1912493),
	INK("atramentum", 922695),
	GROWING("tyrelli", 6024152),
	THRIVING("thriva", 3466109),
	BLOOMING("blooma", 704308),
	SWEET("mellitus", 16536049),
	SUGAR("dulcis", 15127520),
	RIPENING("ripa", 11716445),
	FRUIT("pomum", 14375030),
	ALCOHOL("vinum", 15239777),
	FARM("ager", 7723872),
	MILK("lacteus", 14936296),
	COFFEE("arabica", 9199152),
	CITRUS,
	MINT,
	SWAMP("paludis", 3500339),
	BOGGY("lama", 7887913),
	FUNGAL("boletus", 13722112),
	MARBLE,
	ROMAN,
	GREEK,
	CLASSICAL,
	BASALT("aceri", 9202025),
	TEMPERED("iratus", 9062472),
	ANGRY,
	VOLCANIC("volcano", 5049356),
	MALICIOUS("acerbus", 7875191),
	INFECTIOUS("contagio", 12070581),
	VIRULENT("morbus", 15733740),
	VISCOUS("liquidus", 608014),
	GLUTINOUS("glutina", 1936423),
	STICKY("lentesco", 1565480),
	CORROSIVE("corrumpo", 4873227),
	CAUSTIC("torrens", 8691997),
	ACIDIC("acidus", 12644374),
	EXCITED("excita", 16729413),
	ENERGETIC("energia", 15218119),
	ECSTATIC("ecstatica", 11482600),
	ARTIC("artica", 11395296),
	FREEZING("glacia", 8119267),
	SHADOW("shadowa", 5855577),
	DARKENED("darka", 3354163),
	ABYSS("abyssba", 2164769),
	RED("rubra", 16711680),
	YELLOW("fulvus", 16768256),
	BLUE("caeruleus", 8959),
	GREEN("prasinus", 39168),
	BLACK("niger", 5723991),
	WHITE("albus", 16777215),
	BROWN("fuscus", 6042895),
	ORANGE("flammeus", 16751872),
	CYAN("cyana", 65509),
	PURPLE("purpureus", 11403519),
	GRAY("ravus", 12237498),
	LIGHTBLUE("aqua", 40447),
	PINK("rosaceus", 16744671),
	LIMEGREEN("lima", 65288),
	MAGENTA("fuchsia", 16711884),
	LIGHTGRAY("canus", 13224393),
	CELEBRATORY("celeba", 16386666),
	JADED("jadeca", 16386666),
	GLOWSTONE("glowia", 14730779),
	HAZARDOUS("infensus", 11562024),
	NICKEL("claro", 16768764),
	INVAR,
	QUANTUM("quanta", 3655131),
	SPATIAL("spatia", 4987872),
	UNUSUAL("daniella", 5874874),
	YELLORIUM("yellori", 14019840),
	CYANITE("cyanita", 34541),
	BLUTONIUM("caruthus", 1769702),
	MYSTICAL("mystica", 4630306);

	public HashMap<ItemStack, Float> allProducts;
	public HashMap<ItemStack, Float> allSpecialties;
	public State state;

	protected boolean nocturnal;

	private int primaryColor;
	private int secondaryColor;
	private EnumTemperature temperature;
	private EnumHumidity humidity;
	private boolean hasEffect;
	private boolean isSecret;
	private boolean isCounted;
	private String binomial;
	private IClassification branch;
	private String uid;
	private Achievement achievement;
	private boolean dominant;
	private HashMap<ItemStack, Float> products;
	private HashMap<ItemStack, Float> specialties;
	private IAllele[] template;

	@SideOnly(Side.CLIENT)
	private IIcon[][] icons;

	ExtraBeesSpecies(String binomial, int color) {
		this.binomial = binomial;
		primaryColor = color;
		secondaryColor = 0xffdc16;
		temperature = EnumTemperature.NORMAL;
		humidity = EnumHumidity.NORMAL;
		hasEffect = false;
		isSecret = true;
		isCounted = true;
		branch = null;
		achievement = null;
		dominant = true;
		products = new LinkedHashMap<>();
		specialties = new LinkedHashMap<>();
		allProducts = new LinkedHashMap<>();
		allSpecialties = new LinkedHashMap<>();
		state = State.Active;
		nocturnal = false;
		uid = toString().toLowerCase();
	}

	ExtraBeesSpecies() {
		primaryColor = 0xffffff;
		secondaryColor = 0xffdc16;
		temperature = EnumTemperature.NORMAL;
		humidity = EnumHumidity.NORMAL;
		hasEffect = false;
		isSecret = true;
		isCounted = true;
		binomial = "";
		branch = null;
		uid = "";
		achievement = null;
		dominant = true;
		products = new LinkedHashMap<>();
		specialties = new LinkedHashMap<>();
		allProducts = new LinkedHashMap<>();
		allSpecialties = new LinkedHashMap<>();
		nocturnal = false;
		state = State.Deprecated;
	}

	public static IAllele[] getDefaultTemplate() {
		return Binnie.Genetics.getBeeRoot().getDefaultTemplate();
	}

	public static void doInit() {
		for (ExtraBeesSpecies species : values()) {
			species.template = getDefaultTemplate();
		}

		int aridBody = 0xcbe374;
		int rockBody = 0x999999;

		ExtraBeesSpecies.ARID.importTemplate(ForestryAllele.BeeSpecies.Modest);
		ExtraBeesSpecies.ARID.addProduct(EnumHoneyComb.BARREN, 0.30f);
		ExtraBeesSpecies.ARID.setHumidity(EnumHumidity.ARID);
		ExtraBeesSpecies.ARID.setFlowerProvider(ExtraBeesFlowers.DEAD.getUID());
		ExtraBeesSpecies.ARID.setTemperatureTolerance(Tolerance.Up1);
		ExtraBeesSpecies.ARID.setSecondaryColor(aridBody);

		ExtraBeesSpecies.BARREN.importTemplate(ExtraBeesSpecies.ARID);
		ExtraBeesSpecies.BARREN.setFertility(ForestryAllele.Fertility.Low);
		ExtraBeesSpecies.BARREN.addProduct(EnumHoneyComb.BARREN, 0.30f);

		ExtraBeesSpecies.DESOLATE.addProduct(EnumHoneyComb.BARREN, 0.30f);
		ExtraBeesSpecies.DESOLATE.importTemplate(ExtraBeesSpecies.BARREN);
		ExtraBeesSpecies.DESOLATE.setEffect(ExtraBeesEffect.HUNGER.getUID());
		ExtraBeesSpecies.DESOLATE.recessive();
		ExtraBeesSpecies.DESOLATE.setNocturnal();
		ExtraBeesSpecies.DESOLATE.setHasEffect(true);

		ExtraBeesSpecies.GNAWING.importTemplate(ExtraBeesSpecies.BARREN);
		ExtraBeesSpecies.GNAWING.setFlowerProvider(ExtraBeesFlowers.WOOD.getUID());
		ExtraBeesSpecies.GNAWING.addProduct(EnumHoneyComb.BARREN, 0.25f);
		ExtraBeesSpecies.GNAWING.addSpecialty(EnumHoneyComb.SAWDUST, 0.25f);

		ExtraBeesSpecies.ROTTEN.importTemplate(ExtraBeesSpecies.DESOLATE);
		ExtraBeesSpecies.ROTTEN.setNocturnal();
		ExtraBeesSpecies.ROTTEN.setCaveDwelling();
		ExtraBeesSpecies.ROTTEN.setTolerantFlyer();
		ExtraBeesSpecies.ROTTEN.setEffect(ExtraBeesEffect.SPAWN_ZOMBIE.getUID());
		ExtraBeesSpecies.ROTTEN.addProduct(EnumHoneyComb.BARREN, 0.30f);
		ExtraBeesSpecies.ROTTEN.addSpecialty(EnumHoneyComb.ROTTEN, 0.10f);

		ExtraBeesSpecies.BONE.importTemplate(ExtraBeesSpecies.ROTTEN);
		ExtraBeesSpecies.BONE.addProduct(EnumHoneyComb.BARREN, 0.30f);
		ExtraBeesSpecies.BONE.addSpecialty(EnumHoneyComb.BONE, 0.10f);
		ExtraBeesSpecies.BONE.setEffect(ExtraBeesEffect.SPAWN_SKELETON.getUID());

		ExtraBeesSpecies.CREEPER.importTemplate(ExtraBeesSpecies.ROTTEN);
		ExtraBeesSpecies.CREEPER.setAllDay();
		ExtraBeesSpecies.CREEPER.addProduct(EnumHoneyComb.BARREN, 0.30f);
		ExtraBeesSpecies.CREEPER.addSpecialty(ItemHoneyComb.VanillaComb.POWDERY.get(), 0.08f);
		ExtraBeesSpecies.CREEPER.setEffect(ExtraBeesEffect.SPAWN_CREEPER.getUID());

		ExtraBeesSpecies.DECOMPOSING.importTemplate(ExtraBeesSpecies.BARREN);
		ExtraBeesSpecies.DECOMPOSING.addProduct(EnumHoneyComb.BARREN, 0.30f);
		ExtraBeesSpecies.DECOMPOSING.addSpecialty(EnumHoneyComb.COMPOST, 0.08f);

		ExtraBeesSpecies.ROCK.addProduct(EnumHoneyComb.STONE, 0.30f);
		ExtraBeesSpecies.ROCK.setIsSecret(false);
		ExtraBeesSpecies.ROCK.setAllDay();
		ExtraBeesSpecies.ROCK.setCaveDwelling();
		ExtraBeesSpecies.ROCK.setTolerantFlyer();
		ExtraBeesSpecies.ROCK.setTemperatureTolerance(Tolerance.Both1);
		ExtraBeesSpecies.ROCK.setHumidityTolerance(Tolerance.Both1);
		ExtraBeesSpecies.ROCK.setFlowerProvider(ExtraBeesFlowers.ROCK.getUID());
		ExtraBeesSpecies.ROCK.setFertility(ForestryAllele.Fertility.Low);
		ExtraBeesSpecies.ROCK.setLifespan(ForestryAllele.Lifespan.Short);
		ExtraBeesSpecies.ROCK.setSecondaryColor(rockBody);

		ExtraBeesSpecies.STONE.addProduct(EnumHoneyComb.STONE, 0.30f);
		ExtraBeesSpecies.STONE.importTemplate(ExtraBeesSpecies.ROCK);
		ExtraBeesSpecies.STONE.recessive();
		ExtraBeesSpecies.STONE.setSecondaryColor(rockBody);

		ExtraBeesSpecies.GRANITE.addProduct(EnumHoneyComb.STONE, 0.30f);
		ExtraBeesSpecies.GRANITE.importTemplate(ExtraBeesSpecies.STONE);
		ExtraBeesSpecies.GRANITE.setTemperatureTolerance(Tolerance.Both2);
		ExtraBeesSpecies.GRANITE.setHumidityTolerance(Tolerance.Both2);
		ExtraBeesSpecies.GRANITE.setSecondaryColor(rockBody);

		ExtraBeesSpecies.MINERAL.addProduct(EnumHoneyComb.STONE, 0.30f);
		ExtraBeesSpecies.MINERAL.importTemplate(ExtraBeesSpecies.GRANITE);
		ExtraBeesSpecies.MINERAL.setSecondaryColor(rockBody);

		ExtraBeesSpecies.COPPER.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeesSpecies.COPPER.addSpecialty(EnumHoneyComb.COPPER, 0.06f);
		ExtraBeesSpecies.COPPER.importTemplate(ExtraBeesSpecies.MINERAL);
		ExtraBeesSpecies.COPPER.setSecondaryColor(rockBody);

		ExtraBeesSpecies.TIN.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeesSpecies.TIN.addSpecialty(EnumHoneyComb.TIN, 0.06f);
		ExtraBeesSpecies.TIN.importTemplate(ExtraBeesSpecies.MINERAL);
		ExtraBeesSpecies.TIN.setSecondaryColor(rockBody);

		ExtraBeesSpecies.IRON.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeesSpecies.IRON.addSpecialty(EnumHoneyComb.IRON, 0.05f);
		ExtraBeesSpecies.IRON.importTemplate(ExtraBeesSpecies.MINERAL);
		ExtraBeesSpecies.IRON.recessive();
		ExtraBeesSpecies.IRON.setSecondaryColor(rockBody);

		ExtraBeesSpecies.LEAD.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeesSpecies.LEAD.addSpecialty(EnumHoneyComb.LEAD, 0.50f);
		ExtraBeesSpecies.LEAD.importTemplate(ExtraBeesSpecies.MINERAL);
		ExtraBeesSpecies.LEAD.setSecondaryColor(rockBody);

		ExtraBeesSpecies.NICKEL.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeesSpecies.NICKEL.addSpecialty(EnumHoneyComb.NICKEL, 0.05f);
		ExtraBeesSpecies.NICKEL.importTemplate(ExtraBeesSpecies.MINERAL);
		ExtraBeesSpecies.NICKEL.setSecondaryColor(rockBody);

		ExtraBeesSpecies.ZINC.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeesSpecies.ZINC.addSpecialty(EnumHoneyComb.ZINC, 0.05f);
		ExtraBeesSpecies.ZINC.importTemplate(ExtraBeesSpecies.MINERAL);
		ExtraBeesSpecies.ZINC.setSecondaryColor(rockBody);

		ExtraBeesSpecies.TITANIUM.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeesSpecies.TITANIUM.addSpecialty(EnumHoneyComb.TITANIUM, 0.02f);
		ExtraBeesSpecies.TITANIUM.importTemplate(ExtraBeesSpecies.MINERAL);
		ExtraBeesSpecies.TITANIUM.setSecondaryColor(rockBody);

		ExtraBeesSpecies.TUNGSTATE.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeesSpecies.TUNGSTATE.addSpecialty(EnumHoneyComb.TUNGSTEN, 0.01f);
		ExtraBeesSpecies.TUNGSTATE.importTemplate(ExtraBeesSpecies.MINERAL);
		ExtraBeesSpecies.TUNGSTATE.setSecondaryColor(rockBody);

		ExtraBeesSpecies.GOLD.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeesSpecies.GOLD.addSpecialty(EnumHoneyComb.GOLD, 0.02f);
		ExtraBeesSpecies.GOLD.importTemplate(ExtraBeesSpecies.MINERAL);
		ExtraBeesSpecies.GOLD.setSecondaryColor(rockBody);

		ExtraBeesSpecies.SILVER.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeesSpecies.SILVER.addSpecialty(EnumHoneyComb.SILVER, 0.02f);
		ExtraBeesSpecies.SILVER.importTemplate(ExtraBeesSpecies.MINERAL);
		ExtraBeesSpecies.SILVER.recessive();
		ExtraBeesSpecies.SILVER.recessive();
		ExtraBeesSpecies.SILVER.setSecondaryColor(rockBody);

		ExtraBeesSpecies.PLATINUM.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeesSpecies.PLATINUM.addSpecialty(EnumHoneyComb.PLATINUM, 0.01f);
		ExtraBeesSpecies.PLATINUM.importTemplate(ExtraBeesSpecies.MINERAL);
		ExtraBeesSpecies.PLATINUM.recessive();
		ExtraBeesSpecies.PLATINUM.setSecondaryColor(rockBody);

		ExtraBeesSpecies.LAPIS.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeesSpecies.LAPIS.addSpecialty(EnumHoneyComb.LAPIS, 0.05f);
		ExtraBeesSpecies.LAPIS.importTemplate(ExtraBeesSpecies.MINERAL);
		ExtraBeesSpecies.LAPIS.setSecondaryColor(rockBody);

		ExtraBeesSpecies.EMERALD.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeesSpecies.EMERALD.addSpecialty(EnumHoneyComb.EMERALD, 0.04f);
		ExtraBeesSpecies.EMERALD.importTemplate(ExtraBeesSpecies.MINERAL);
		ExtraBeesSpecies.EMERALD.setSecondaryColor(rockBody);

		ExtraBeesSpecies.RUBY.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeesSpecies.RUBY.addSpecialty(EnumHoneyComb.RUBY, 0.03f);
		ExtraBeesSpecies.RUBY.importTemplate(ExtraBeesSpecies.MINERAL);
		ExtraBeesSpecies.RUBY.setSecondaryColor(rockBody);

		ExtraBeesSpecies.SAPPHIRE.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeesSpecies.SAPPHIRE.addSpecialty(EnumHoneyComb.SAPPHIRE, 0.03f);
		ExtraBeesSpecies.SAPPHIRE.importTemplate(ExtraBeesSpecies.MINERAL);
		ExtraBeesSpecies.SAPPHIRE.setSecondaryColor(rockBody);

		ExtraBeesSpecies.DIAMOND.addProduct(EnumHoneyComb.STONE, 0.20f);
		ExtraBeesSpecies.DIAMOND.addSpecialty(EnumHoneyComb.DIAMOND, 0.01f);
		ExtraBeesSpecies.DIAMOND.importTemplate(ExtraBeesSpecies.MINERAL);
		ExtraBeesSpecies.DIAMOND.setSecondaryColor(rockBody);

		ExtraBeesSpecies.UNSTABLE.importTemplate(ExtraBeesSpecies.MINERAL);
		ExtraBeesSpecies.UNSTABLE.addProduct(EnumHoneyComb.BARREN, 0.20f);
		ExtraBeesSpecies.UNSTABLE.setEffect(ExtraBeesEffect.RADIOACTIVE.getUID());
		ExtraBeesSpecies.UNSTABLE.setFertility(ForestryAllele.Fertility.Low);
		ExtraBeesSpecies.UNSTABLE.setLifespan(ForestryAllele.Lifespan.Shortest);
		ExtraBeesSpecies.UNSTABLE.recessive();

		ExtraBeesSpecies.NUCLEAR.importTemplate(ExtraBeesSpecies.UNSTABLE);
		ExtraBeesSpecies.NUCLEAR.addProduct(EnumHoneyComb.BARREN, 0.20f);
		ExtraBeesSpecies.NUCLEAR.recessive();

		ExtraBeesSpecies.RADIOACTIVE.importTemplate(ExtraBeesSpecies.NUCLEAR);
		ExtraBeesSpecies.RADIOACTIVE.addProduct(EnumHoneyComb.BARREN, 0.20f);
		ExtraBeesSpecies.RADIOACTIVE.addSpecialty(EnumHoneyComb.URANIUM, 0.02f);
		ExtraBeesSpecies.RADIOACTIVE.setHasEffect(true);
		ExtraBeesSpecies.RADIOACTIVE.recessive();

		ExtraBeesSpecies.ANCIENT.importTemplate(ForestryAllele.BeeSpecies.Noble);
		ExtraBeesSpecies.ANCIENT.addProduct(EnumHoneyComb.OLD, 0.30f);
		ExtraBeesSpecies.ANCIENT.setLifespan(ForestryAllele.Lifespan.Elongated);

		ExtraBeesSpecies.PRIMEVAL.importTemplate(ExtraBeesSpecies.ANCIENT);
		ExtraBeesSpecies.PRIMEVAL.addProduct(EnumHoneyComb.OLD, 0.30f);
		ExtraBeesSpecies.PRIMEVAL.setLifespan(ForestryAllele.Lifespan.Long);

		ExtraBeesSpecies.PREHISTORIC.importTemplate(ExtraBeesSpecies.ANCIENT);
		ExtraBeesSpecies.PREHISTORIC.addProduct(EnumHoneyComb.OLD, 0.30f);
		ExtraBeesSpecies.PREHISTORIC.setLifespan(ForestryAllele.Lifespan.Longer);
		ExtraBeesSpecies.PREHISTORIC.setFertility(ForestryAllele.Fertility.Low);
		ExtraBeesSpecies.PREHISTORIC.recessive();

		ExtraBeesSpecies.RELIC.importTemplate(ExtraBeesSpecies.ANCIENT);
		ExtraBeesSpecies.RELIC.addProduct(EnumHoneyComb.OLD, 0.30f);
		ExtraBeesSpecies.RELIC.setHasEffect(true);
		ExtraBeesSpecies.RELIC.setLifespan(ForestryAllele.Lifespan.Longest);

		ExtraBeesSpecies.COAL.importTemplate(ExtraBeesSpecies.ANCIENT);
		ExtraBeesSpecies.COAL.setLifespan(ForestryAllele.Lifespan.Normal);
		ExtraBeesSpecies.COAL.addProduct(EnumHoneyComb.OLD, 0.20f);
		ExtraBeesSpecies.COAL.addSpecialty(EnumHoneyComb.COAL, 0.08f);

		ExtraBeesSpecies.RESIN.importTemplate(ExtraBeesSpecies.COAL);
		ExtraBeesSpecies.RESIN.addProduct(EnumHoneyComb.OLD, 0.20f);
		ExtraBeesSpecies.RESIN.addSpecialty(EnumHoneyComb.RESIN, 0.05f);
		ExtraBeesSpecies.RESIN.recessive();

		ExtraBeesSpecies.OIL.importTemplate(ExtraBeesSpecies.COAL);
		ExtraBeesSpecies.OIL.addProduct(EnumHoneyComb.OLD, 0.20f);
		ExtraBeesSpecies.OIL.addSpecialty(EnumHoneyComb.OIL, 0.05f);

		ExtraBeesSpecies.DISTILLED.importTemplate(ExtraBeesSpecies.OIL);
		ExtraBeesSpecies.DISTILLED.addProduct(EnumHoneyComb.OIL, 0.10f);
		ExtraBeesSpecies.DISTILLED.recessive();

		ExtraBeesSpecies.FUEL.importTemplate(ExtraBeesSpecies.OIL);
		ExtraBeesSpecies.FUEL.addProduct(EnumHoneyComb.OIL, 0.10f);
		ExtraBeesSpecies.FUEL.addSpecialty(EnumHoneyComb.FUEL, 0.04f);
		ExtraBeesSpecies.FUEL.setHasEffect(true);

		ExtraBeesSpecies.CREOSOTE.importTemplate(ExtraBeesSpecies.COAL);
		ExtraBeesSpecies.CREOSOTE.addProduct(EnumHoneyComb.COAL, 0.10f);
		ExtraBeesSpecies.CREOSOTE.addSpecialty(EnumHoneyComb.CREOSOTE, 0.07f);
		ExtraBeesSpecies.CREOSOTE.setHasEffect(true);

		ExtraBeesSpecies.LATEX.importTemplate(ExtraBeesSpecies.RESIN);
		ExtraBeesSpecies.LATEX.addProduct(EnumHoneyComb.RESIN, 0.10f);
		ExtraBeesSpecies.LATEX.addSpecialty(EnumHoneyComb.LATEX, 0.05f);
		ExtraBeesSpecies.LATEX.setHasEffect(true);

		ExtraBeesSpecies.WATER.addProduct(EnumHoneyComb.WATER, 0.30f);
		ExtraBeesSpecies.WATER.setIsSecret(false);
		ExtraBeesSpecies.WATER.setTolerantFlyer();
		ExtraBeesSpecies.WATER.setHumidityTolerance(Tolerance.Both1);
		ExtraBeesSpecies.WATER.setFlowerProvider(ExtraBeesFlowers.WATER.getUID());
		ExtraBeesSpecies.WATER.setFlowering(ForestryAllele.Flowering.Slow);
		ExtraBeesSpecies.WATER.setEffect(ExtraBeesEffect.WATER.getUID());
		ExtraBeesSpecies.WATER.setHumidity(EnumHumidity.DAMP);

		ExtraBeesSpecies.RIVER.importTemplate(ExtraBeesSpecies.WATER);
		ExtraBeesSpecies.RIVER.addProduct(EnumHoneyComb.WATER, 0.30f);
		ExtraBeesSpecies.RIVER.addSpecialty(EnumHoneyComb.CLAY, 0.20f);
		ExtraBeesSpecies.RIVER.importTemplate(ExtraBeesSpecies.WATER);

		ExtraBeesSpecies.OCEAN.importTemplate(ExtraBeesSpecies.WATER);
		ExtraBeesSpecies.OCEAN.addProduct(EnumHoneyComb.WATER, 0.30f);
		ExtraBeesSpecies.OCEAN.importTemplate(ExtraBeesSpecies.WATER);
		ExtraBeesSpecies.OCEAN.recessive();

		ExtraBeesSpecies.INK.importTemplate(ExtraBeesSpecies.OCEAN);
		ExtraBeesSpecies.INK.addProduct(EnumHoneyComb.WATER, 0.30f);
		ExtraBeesSpecies.INK.addSpecialty(new ItemStack(Items.dye, 1, 0), 0.10f);

		ExtraBeesSpecies.GROWING.importTemplate(ForestryAllele.BeeSpecies.Forest);
		ExtraBeesSpecies.GROWING.addProduct(ItemHoneyComb.VanillaComb.HONEY.get(), 0.35f);
		ExtraBeesSpecies.GROWING.setFlowering(ForestryAllele.Flowering.Average);
		ExtraBeesSpecies.GROWING.setFlowerProvider(ExtraBeesFlowers.LEAVES.getUID());

		ExtraBeesSpecies.THRIVING.importTemplate(ExtraBeesSpecies.GROWING);
		ExtraBeesSpecies.THRIVING.addProduct(ItemHoneyComb.VanillaComb.HONEY.get(), 0.35f);
		ExtraBeesSpecies.THRIVING.setFlowering(ForestryAllele.Flowering.Fast);

		ExtraBeesSpecies.BLOOMING.importTemplate(ExtraBeesSpecies.THRIVING);
		ExtraBeesSpecies.BLOOMING.setFlowering(ForestryAllele.Flowering.Fastest);
		ExtraBeesSpecies.BLOOMING.addProduct(ItemHoneyComb.VanillaComb.HONEY.get(), 0.35f);
		ExtraBeesSpecies.BLOOMING.setFlowerProvider(ExtraBeesFlowers.SAPLING.getUID());
		ExtraBeesSpecies.BLOOMING.setEffect(ExtraBeesEffect.BONEMEAL_SAPLING.getUID());

		ExtraBeesSpecies.SWEET.importTemplate(ForestryAllele.BeeSpecies.Rural);
		ExtraBeesSpecies.SWEET.addProduct(ItemHoneyComb.VanillaComb.HONEY.get(), 0.40f);
		ExtraBeesSpecies.SWEET.addProduct(new ItemStack(Items.sugar, 1, 0), 0.10f);
		ExtraBeesSpecies.SWEET.setFlowerProvider(ExtraBeesFlowers.SUGAR.getUID());

		ExtraBeesSpecies.SUGAR.addProduct(ItemHoneyComb.VanillaComb.HONEY.get(), 0.40f);
		ExtraBeesSpecies.SUGAR.addProduct(new ItemStack(Items.sugar, 1, 0), 0.20f);
		ExtraBeesSpecies.SUGAR.importTemplate(ExtraBeesSpecies.SWEET);

		ExtraBeesSpecies.RIPENING.addProduct(ItemHoneyComb.VanillaComb.HONEY.get(), 0.30f);
		ExtraBeesSpecies.RIPENING.addProduct(new ItemStack(Items.sugar, 1, 0), 0.10f);
		ExtraBeesSpecies.RIPENING.addSpecialty(EnumHoneyComb.FRUIT, 0.10f);
		ExtraBeesSpecies.RIPENING.setFlowerProvider(ExtraBeesFlowers.FRUIT.getUID());
		ExtraBeesSpecies.RIPENING.importTemplate(ExtraBeesSpecies.SUGAR);

		ExtraBeesSpecies.FRUIT.importTemplate(ExtraBeesSpecies.RIPENING);
		ExtraBeesSpecies.FRUIT.addProduct(ItemHoneyComb.VanillaComb.HONEY.get(), 0.30f);
		ExtraBeesSpecies.FRUIT.addProduct(new ItemStack(Items.sugar, 1, 0), 0.15f);
		ExtraBeesSpecies.FRUIT.addSpecialty(EnumHoneyComb.FRUIT, 0.20f);
		ExtraBeesSpecies.FRUIT.setEffect(ExtraBeesEffect.BONEMEAL_FRUIT.getUID());
		ExtraBeesSpecies.FRUIT.setHasEffect(true);

		ExtraBeesSpecies.ALCOHOL.importTemplate(ExtraBeesSpecies.SWEET);
		ExtraBeesSpecies.ALCOHOL.addProduct(ItemHoneyComb.VanillaComb.WHEATEN.get(), 0.30f);
		ExtraBeesSpecies.ALCOHOL.addSpecialty(EnumHoneyComb.ALCOHOL, 0.10f);
		ExtraBeesSpecies.ALCOHOL.setEffect("forestry.effectDrunkard");
		ExtraBeesSpecies.ALCOHOL.recessive();

		ExtraBeesSpecies.FARM.addProduct(ItemHoneyComb.VanillaComb.WHEATEN.get(), 0.30f);
		ExtraBeesSpecies.FARM.addSpecialty(EnumHoneyComb.SEED, 0.10f);
		ExtraBeesSpecies.FARM.importTemplate(ForestryAllele.BeeSpecies.Rural);

		ExtraBeesSpecies.MILK.addProduct(ItemHoneyComb.VanillaComb.WHEATEN.get(), 0.30f);
		ExtraBeesSpecies.MILK.addSpecialty(EnumHoneyComb.MILK, 0.10f);
		ExtraBeesSpecies.MILK.importTemplate(ForestryAllele.BeeSpecies.Rural);

		ExtraBeesSpecies.COFFEE.addProduct(ItemHoneyComb.VanillaComb.WHEATEN.get(), 0.30f);
		ExtraBeesSpecies.COFFEE.addSpecialty(EnumHoneyComb.COFFEE, 0.08f);
		ExtraBeesSpecies.COFFEE.importTemplate(ForestryAllele.BeeSpecies.Rural);

		ExtraBeesSpecies.SWAMP.addProduct(ItemHoneyComb.VanillaComb.MOSSY.get(), 0.30f);
		ExtraBeesSpecies.SWAMP.importTemplate(ForestryAllele.BeeSpecies.Marshy);
		ExtraBeesSpecies.SWAMP.setHumidity(EnumHumidity.DAMP);
		ExtraBeesSpecies.SWAMP.setEffect(ExtraBeesEffect.SLOW.getUID());

		ExtraBeesSpecies.BOGGY.importTemplate(ExtraBeesSpecies.SWAMP);
		ExtraBeesSpecies.BOGGY.addProduct(ItemHoneyComb.VanillaComb.MOSSY.get(), 0.30f);
		ExtraBeesSpecies.BOGGY.importTemplate(ExtraBeesSpecies.SWAMP);
		ExtraBeesSpecies.BOGGY.recessive();

		ExtraBeesSpecies.FUNGAL.importTemplate(ExtraBeesSpecies.BOGGY);
		ExtraBeesSpecies.FUNGAL.addProduct(ItemHoneyComb.VanillaComb.MOSSY.get(), 0.30f);
		ExtraBeesSpecies.FUNGAL.addSpecialty(EnumHoneyComb.FUNGAL, 0.15f);
		ExtraBeesSpecies.FUNGAL.importTemplate(ExtraBeesSpecies.BOGGY);
		ExtraBeesSpecies.FUNGAL.setEffect(ExtraBeesEffect.BONEMEAL_MUSHROOM.getUID());
		ExtraBeesSpecies.FUNGAL.setHasEffect(true);

		ExtraBeesSpecies.BASALT.addProduct(ItemHoneyComb.VanillaComb.SIMMERING.get(), 0.25f);
		ExtraBeesSpecies.BASALT.importTemplate(ForestryAllele.BeeSpecies.Sinister);
		ExtraBeesSpecies.BASALT.setEffect("forestry.effectAggressive");
		ExtraBeesSpecies.BASALT.setSecondaryColor(0x9a2323);
		ExtraBeesSpecies.BASALT.setHumidity(EnumHumidity.ARID);
		ExtraBeesSpecies.BASALT.setTemperature(EnumTemperature.HELLISH);

		ExtraBeesSpecies.TEMPERED.addProduct(ItemHoneyComb.VanillaComb.SIMMERING.get(), 0.25f);
		ExtraBeesSpecies.TEMPERED.importTemplate(ExtraBeesSpecies.BASALT);
		ExtraBeesSpecies.TEMPERED.setEffect(ExtraBeesEffect.METEOR.getUID());
		ExtraBeesSpecies.TEMPERED.recessive();
		ExtraBeesSpecies.TEMPERED.setSecondaryColor(0x9a2323);

		ExtraBeesSpecies.VOLCANIC.importTemplate(ExtraBeesSpecies.TEMPERED);
		ExtraBeesSpecies.VOLCANIC.addProduct(ItemHoneyComb.VanillaComb.SIMMERING.get(), 0.25f);
		ExtraBeesSpecies.VOLCANIC.addSpecialty(EnumHoneyComb.BLAZE, 0.10f);
		ExtraBeesSpecies.VOLCANIC.setHasEffect(true);
		ExtraBeesSpecies.VOLCANIC.setSecondaryColor(0x9a2323);

		ExtraBeesSpecies.MALICIOUS.importTemplate(ForestryAllele.BeeSpecies.Tropical);
		ExtraBeesSpecies.MALICIOUS.addProduct(ItemHoneyComb.VanillaComb.SILKY.get(), 0.25f);
		ExtraBeesSpecies.MALICIOUS.setSecondaryColor(0x069764);
		ExtraBeesSpecies.MALICIOUS.setHumidity(EnumHumidity.DAMP);
		ExtraBeesSpecies.MALICIOUS.setTemperature(EnumTemperature.WARM);

		ExtraBeesSpecies.INFECTIOUS.importTemplate(ExtraBeesSpecies.MALICIOUS);
		ExtraBeesSpecies.INFECTIOUS.addProduct(ItemHoneyComb.VanillaComb.SILKY.get(), 0.25f);
		ExtraBeesSpecies.INFECTIOUS.setFlowering(ForestryAllele.Flowering.Slow);
		ExtraBeesSpecies.INFECTIOUS.setSecondaryColor(0x069764);

		ExtraBeesSpecies.VIRULENT.importTemplate(ExtraBeesSpecies.INFECTIOUS);
		ExtraBeesSpecies.VIRULENT.addProduct(ItemHoneyComb.VanillaComb.SILKY.get(), 0.25f);
		ExtraBeesSpecies.VIRULENT.addSpecialty(EnumHoneyComb.VENOMOUS, 0.12f);
		ExtraBeesSpecies.VIRULENT.setFlowering(ForestryAllele.Flowering.Average);
		ExtraBeesSpecies.VIRULENT.recessive();
		ExtraBeesSpecies.VIRULENT.setHasEffect(true);
		ExtraBeesSpecies.VIRULENT.setSecondaryColor(0x069764);

		ExtraBeesSpecies.VISCOUS.importTemplate(ForestryAllele.BeeSpecies.Tropical);
		ExtraBeesSpecies.VISCOUS.setEffect(ExtraBeesEffect.ECTOPLASM.getUID());
		ExtraBeesSpecies.VISCOUS.addProduct(ItemHoneyComb.VanillaComb.SILKY.get(), 0.25f);
		ExtraBeesSpecies.VISCOUS.setSecondaryColor(0x069764);
		ExtraBeesSpecies.VISCOUS.setHumidity(EnumHumidity.DAMP);
		ExtraBeesSpecies.VISCOUS.setSpeed(ForestryAllele.Speed.Slow);
		ExtraBeesSpecies.VISCOUS.setTemperature(EnumTemperature.WARM);

		ExtraBeesSpecies.GLUTINOUS.importTemplate(ExtraBeesSpecies.VISCOUS);
		ExtraBeesSpecies.GLUTINOUS.addProduct(ItemHoneyComb.VanillaComb.SILKY.get(), 0.25f);
		ExtraBeesSpecies.GLUTINOUS.setSpeed(ForestryAllele.Speed.Norm);
		ExtraBeesSpecies.GLUTINOUS.setSecondaryColor(0x069764);

		ExtraBeesSpecies.STICKY.importTemplate(ExtraBeesSpecies.GLUTINOUS);
		ExtraBeesSpecies.STICKY.addProduct(ItemHoneyComb.VanillaComb.SILKY.get(), 0.25f);
		ExtraBeesSpecies.STICKY.addSpecialty(EnumHoneyComb.SLIME, 0.12f);
		ExtraBeesSpecies.STICKY.setSpeed(ForestryAllele.Speed.Fast);
		ExtraBeesSpecies.STICKY.setHasEffect(true);
		ExtraBeesSpecies.STICKY.setSecondaryColor(0x069764);

		ExtraBeesSpecies.CORROSIVE.importTemplate(ExtraBeesSpecies.STICKY);
		ExtraBeesSpecies.CORROSIVE.setHumidity(EnumHumidity.DAMP);
		ExtraBeesSpecies.CORROSIVE.setTemperature(EnumTemperature.WARM);
		ExtraBeesSpecies.CORROSIVE.setEffect(ExtraBeesEffect.ACID.getUID());
		ExtraBeesSpecies.CORROSIVE.setFlowering(ForestryAllele.Flowering.Average);
		ExtraBeesSpecies.CORROSIVE.addProduct(ItemHoneyComb.VanillaComb.SILKY.get(), 20f);
		ExtraBeesSpecies.CORROSIVE.recessive();
		ExtraBeesSpecies.CORROSIVE.setSecondaryColor(0x069764);

		ExtraBeesSpecies.CAUSTIC.importTemplate(ExtraBeesSpecies.CORROSIVE);
		ExtraBeesSpecies.CAUSTIC.addProduct(ItemHoneyComb.VanillaComb.SILKY.get(), 0.25f);
		ExtraBeesSpecies.CAUSTIC.addSpecialty(EnumHoneyComb.ACIDIC, 0.03f);
		ExtraBeesSpecies.CAUSTIC.setSecondaryColor(0x069764);

		ExtraBeesSpecies.ACIDIC.importTemplate(ExtraBeesSpecies.CAUSTIC);
		ExtraBeesSpecies.ACIDIC.addProduct(ItemHoneyComb.VanillaComb.SILKY.get(), 0.20f);
		ExtraBeesSpecies.ACIDIC.addSpecialty(EnumHoneyComb.ACIDIC, 0.16f);
		ExtraBeesSpecies.ACIDIC.setHasEffect(true);
		ExtraBeesSpecies.ACIDIC.setSecondaryColor(0x069764);

		ExtraBeesSpecies.EXCITED.setEffect(ExtraBeesEffect.LIGHTNING.getUID());
		ExtraBeesSpecies.EXCITED.addProduct(EnumHoneyComb.REDSTONE, 0.10f);
		ExtraBeesSpecies.EXCITED.setCaveDwelling();
		ExtraBeesSpecies.EXCITED.setFlowerProvider(ExtraBeesFlowers.REDSTONE.getUID());

		ExtraBeesSpecies.ENERGETIC.importTemplate(ExtraBeesSpecies.EXCITED);
		ExtraBeesSpecies.ENERGETIC.setEffect(ExtraBeesEffect.LIGHTNING.getUID());
		ExtraBeesSpecies.ENERGETIC.addProduct(EnumHoneyComb.REDSTONE, 0.12f);
		ExtraBeesSpecies.ENERGETIC.recessive();

		ExtraBeesSpecies.ECSTATIC.importTemplate(ExtraBeesSpecies.ENERGETIC);
		ExtraBeesSpecies.ECSTATIC.setEffect(ExtraBeesEffect.Power.getUID());
		ExtraBeesSpecies.ECSTATIC.addProduct(EnumHoneyComb.REDSTONE, 0.20f);
		ExtraBeesSpecies.ECSTATIC.addSpecialty(EnumHoneyComb.IC2ENERGY, 0.08f);
		ExtraBeesSpecies.ECSTATIC.setHasEffect(true);

		ExtraBeesSpecies.ARTIC.importTemplate(ForestryAllele.BeeSpecies.Wintry);
		ExtraBeesSpecies.ARTIC.addProduct(ItemHoneyComb.VanillaComb.FROZEN.get(), 0.25f);
		ExtraBeesSpecies.ARTIC.setTemperature(EnumTemperature.ICY);
		ExtraBeesSpecies.ARTIC.setSecondaryColor(0xdaf5f3);

		ExtraBeesSpecies.FREEZING.importTemplate(ExtraBeesSpecies.ARTIC);
		ExtraBeesSpecies.FREEZING.addProduct(ItemHoneyComb.VanillaComb.FROZEN.get(), 0.20f);
		ExtraBeesSpecies.FREEZING.addSpecialty(EnumHoneyComb.GLACIAL, 0.10f);
		ExtraBeesSpecies.FREEZING.setSecondaryColor(0xdaf5f3);

		ExtraBeesSpecies.SHADOW.importTemplate(ExtraBeesSpecies.BASALT);
		ExtraBeesSpecies.SHADOW.setNocturnal();
		ExtraBeesSpecies.SHADOW.addProduct(EnumHoneyComb.SHADOW, 0.05f);
		ExtraBeesSpecies.SHADOW.setEffect(ExtraBeesEffect.BLINDNESS.getUID());
		ExtraBeesSpecies.SHADOW.setAllDay(false);
		ExtraBeesSpecies.SHADOW.recessive();
		ExtraBeesSpecies.SHADOW.setSecondaryColor(0x333333);

		ExtraBeesSpecies.DARKENED.addProduct(EnumHoneyComb.SHADOW, 0.10f);
		ExtraBeesSpecies.DARKENED.setNocturnal();
		ExtraBeesSpecies.DARKENED.importTemplate(ExtraBeesSpecies.SHADOW);
		ExtraBeesSpecies.DARKENED.setSecondaryColor(0x333333);

		ExtraBeesSpecies.ABYSS.importTemplate(ExtraBeesSpecies.DARKENED);
		ExtraBeesSpecies.ABYSS.setNocturnal();
		ExtraBeesSpecies.ABYSS.addProduct(EnumHoneyComb.SHADOW, 0.25f);
		ExtraBeesSpecies.ABYSS.setEffect(ExtraBeesEffect.WITHER.getUID());
		ExtraBeesSpecies.ABYSS.setHasEffect(true);
		ExtraBeesSpecies.ABYSS.setSecondaryColor(0x333333);

		ExtraBeesSpecies.CELEBRATORY.importTemplate(ForestryAllele.BeeSpecies.Merry);
		ExtraBeesSpecies.CELEBRATORY.setEffect(ExtraBeesEffect.FIREWORKS.getUID());

		ExtraBeesSpecies.GLOWSTONE.importTemplate(ExtraBeesSpecies.BASALT);
		ExtraBeesSpecies.GLOWSTONE.addProduct(EnumHoneyComb.GLOWSTONE, 0.15f);
		ExtraBeesSpecies.GLOWSTONE.setSecondaryColor(0x9a2323);

		ExtraBeesSpecies.HAZARDOUS.importTemplate(ForestryAllele.BeeSpecies.Austere);
		ExtraBeesSpecies.HAZARDOUS.addProduct(EnumHoneyComb.SALTPETER, 0.12f);

		ExtraBeesSpecies.JADED.importTemplate(ForestryAllele.BeeSpecies.Imperial);
		ExtraBeesSpecies.JADED.setFertility(ForestryAllele.Fertility.Maximum);
		ExtraBeesSpecies.JADED.setFlowering(ForestryAllele.Flowering.Maximum);
		ExtraBeesSpecies.JADED.setTerritory(ForestryAllele.Territory.Largest);
		ExtraBeesSpecies.JADED.addProduct(ItemHoneyComb.VanillaComb.HONEY.get(), 0.30f);
		ExtraBeesSpecies.JADED.addSpecialty(Mods.Forestry.stack("pollen"), 0.20f);
		ExtraBeesSpecies.JADED.setHasEffect(true);
		ExtraBeesSpecies.JADED.setSecondaryColor(0xdc8aeb);
		ExtraBeesSpecies.JADED.addSpecialty(EnumHoneyComb.PURPLE, 0.15f);
		ExtraBeesSpecies.JADED.isCounted = false;

		ExtraBeesSpecies.UNUSUAL.importTemplate(ForestryAllele.BeeSpecies.Ended);
		ExtraBeesSpecies.UNUSUAL.setEffect(ExtraBeesEffect.GRAVITY.getUID());
		ExtraBeesSpecies.UNUSUAL.setSecondaryColor(0xbaa2eb);
		ExtraBeesSpecies.UNUSUAL.addProduct(ItemHoneyComb.VanillaComb.QUARTZ.get(), 0.25f);

		ExtraBeesSpecies.SPATIAL.importTemplate(ExtraBeesSpecies.UNUSUAL);
		ExtraBeesSpecies.SPATIAL.setEffect(ExtraBeesEffect.GRAVITY.getUID());
		ExtraBeesSpecies.SPATIAL.setSecondaryColor(0xa44ecc);
		ExtraBeesSpecies.SPATIAL.addProduct(ItemHoneyComb.VanillaComb.QUARTZ.get(), 0.25f);
		ExtraBeesSpecies.SPATIAL.addSpecialty(EnumHoneyComb.CERTUS, 0.05f);

		ExtraBeesSpecies.QUANTUM.importTemplate(ExtraBeesSpecies.QUANTUM);
		ExtraBeesSpecies.QUANTUM.setEffect(ExtraBeesEffect.TELEPORT.getUID());
		ExtraBeesSpecies.QUANTUM.setSecondaryColor(0xd50fdb);
		ExtraBeesSpecies.QUANTUM.addProduct(ItemHoneyComb.VanillaComb.QUARTZ.get(), 0.25f);
		ExtraBeesSpecies.QUANTUM.addSpecialty(EnumHoneyComb.CERTUS, 0.15f);
		ExtraBeesSpecies.QUANTUM.addSpecialty(EnumHoneyComb.ENDERPEARL, 0.15f);

		ExtraBeesSpecies.YELLORIUM.importTemplate(ExtraBeesSpecies.NUCLEAR);
		ExtraBeesSpecies.YELLORIUM.addProduct(EnumHoneyComb.BARREN, 0.20f);
		ExtraBeesSpecies.YELLORIUM.addSpecialty(EnumHoneyComb.YELLORIUM, 0.02f);
		ExtraBeesSpecies.YELLORIUM.setEffect(ExtraBeesEffect.RADIOACTIVE.getUID());
		ExtraBeesSpecies.YELLORIUM.setFertility(ForestryAllele.Fertility.Low);
		ExtraBeesSpecies.YELLORIUM.setLifespan(ForestryAllele.Lifespan.Shortest);

		ExtraBeesSpecies.CYANITE.importTemplate(ExtraBeesSpecies.YELLORIUM);
		ExtraBeesSpecies.CYANITE.addProduct(EnumHoneyComb.BARREN, 0.20f);
		ExtraBeesSpecies.CYANITE.addSpecialty(EnumHoneyComb.CYANITE, 0.01f);

		ExtraBeesSpecies.BLUTONIUM.importTemplate(ExtraBeesSpecies.CYANITE);
		ExtraBeesSpecies.BLUTONIUM.addProduct(EnumHoneyComb.BARREN, 0.20f);
		ExtraBeesSpecies.BLUTONIUM.addSpecialty(EnumHoneyComb.BLUTONIUM, 0.01f);

		ExtraBeesSpecies.MYSTICAL.importTemplate(ForestryAllele.BeeSpecies.Noble);
		for (Map.Entry<ItemStack, Float> entry : ForestryAllele.BeeSpecies.Noble.getAllele().getProductChances().entrySet()) {
			ExtraBeesSpecies.MYSTICAL.addProduct(entry.getKey(), entry.getValue());
		}

		ExtraBeesSpecies.MYSTICAL.setFlowerProvider(ExtraBeesFlowers.MYSTICAL.getUID());
		for (ExtraBeesSpecies species2 : values()) {
			if (species2.state != State.Active) {
				AlleleManager.alleleRegistry.blacklistAllele(species2.getUID());
			}

			for (EnumBeeChromosome chromo : EnumBeeChromosome.values()) {
				if (chromo != EnumBeeChromosome.HUMIDITY) {
					IAllele allele = species2.template[chromo.ordinal()];
					if (allele == null || !chromo.getAlleleClass().isInstance(allele)) {
						throw new RuntimeException(species2.getName() + " has an invalid " + chromo.toString() + " chromosome!");
					}
				}
			}
		}

		for (int i = 0; i < 16; ++i) {
			ExtraBeesSpecies species3 = values()[ExtraBeesSpecies.RED.ordinal() + i];
			EnumHoneyComb comb = EnumHoneyComb.values()[EnumHoneyComb.RED.ordinal() + i];
			species3.addProduct(ItemHoneyComb.VanillaComb.HONEY.get(), 0.75f);
			species3.addSpecialty(comb, 0.25f);
			species3.setSecondaryColor(9240320);
		}

		for (ExtraBeesSpecies species2 : values()) {
			species2.registerTemplate();
		}
	}

	@Override
	public String getName() {
		return ExtraBees.proxy.localise("species." + name().toLowerCase() + ".name");
	}

	@Override
	public String getDescription() {
		return ExtraBees.proxy.localiseOrBlank("species." + name().toLowerCase() + ".desc");
	}

	@Override
	public EnumTemperature getTemperature() {
		return temperature;
	}

	private void setTemperature(EnumTemperature temperature) {
		this.temperature = temperature;
	}

	@Override
	public EnumHumidity getHumidity() {
		return humidity;
	}

	private void setHumidity(EnumHumidity humidity) {
		this.humidity = humidity;
	}

	@Override
	public boolean hasEffect() {
		return hasEffect;
	}

	@Override
	public boolean isSecret() {
		return isSecret;
	}

	@Override
	public boolean isCounted() {
		return isCounted;
	}

	@Override
	public String getBinomial() {
		return binomial;
	}

	@Override
	public String getAuthority() {
		return "Binnie";
	}

	@Override
	public IClassification getBranch() {
		return branch;
	}

	void setBranch(IClassification branch) {
		this.branch = branch;
	}

	@Override
	public String getUID() {
		return "extrabees.species." + uid;
	}

	@Override
	public boolean isDominant() {
		return dominant;
	}

	public HashMap<ItemStack, Float> getProducts() {
		return products;
	}

	public HashMap<ItemStack, Float> getSpecialty() {
		return specialties;
	}

	private void setState(State state) {
		this.state = state;
	}

	public void registerTemplate() {
		Binnie.Genetics.getBeeRoot().registerTemplate(getTemplate());
		if (state != State.Active) {
			AlleleManager.alleleRegistry.blacklistAllele(getUID());
		}
	}

	public void addProduct(ItemStack product, Float chance) {
		if (product == null) {
			setState(State.Inactive);
		} else {
			products.put(product, chance);
			allProducts.put(product, chance);
		}
	}

	public void addProduct(IItemEnum product, Float chance) {
		if (product.isActive()) {
			addProduct(product.get(1), chance);
		} else {
			allProducts.put(product.get(1), chance);
			setState(State.Inactive);
		}
	}

	public void addSpecialty(ItemStack product, Float chance) {
		if (product == null) {
			setState(State.Inactive);
		} else {
			specialties.put(product, chance);
			allSpecialties.put(product, chance);
		}
	}

	private void addSpecialty(IItemEnum product, Float chance) {
		if (product.isActive()) {
			addSpecialty(product.get(1), chance);
		} else {
			setState(State.Inactive);
			allSpecialties.put(product.get(1), chance);
		}
	}

	public IAllele[] getTemplate() {
		template[EnumBeeChromosome.SPECIES.ordinal()] = this;
		return template;
	}

	public void importTemplate(ForestryAllele.BeeSpecies species) {
		importTemplate(species.getTemplate());
	}

	public void importTemplate(ExtraBeesSpecies species) {
		importTemplate(species.getTemplate());
	}

	public void importTemplate(IAllele[] template) {
		this.template = template.clone();
		setHumidity(((IAlleleSpecies) template[0]).getHumidity());
		setTemperature(((IAlleleSpecies) template[0]).getTemperature());
		setSecondaryColor(((IAlleleSpecies) template[0]).getIconColour(1));
		this.template[EnumBeeChromosome.SPECIES.ordinal()] = this;
	}

	public void recessive() {
		dominant = false;
	}

	public void setIsSecret(boolean secret) {
		isSecret = secret;
	}

	public void setHasEffect(boolean effect) {
		hasEffect = effect;
	}

	public void setSecondaryColor(int colour) {
		secondaryColor = colour;
	}

	public boolean isJubilant(World world, int biomeid, int x, int y, int z) {
		return true;
	}

	@Override
	public boolean isJubilant(IBeeGenome genome, IBeeHousing housing) {
		return true;
	}

	@Override
	public int getIconColour(int renderPass) {
		return (renderPass == 0) ? primaryColor : ((renderPass == 1) ? secondaryColor : 16777215);
	}

	@Override
	public IIconProvider getIconProvider() {
		return this;
	}

	@Override
	public IIcon getIcon(short texUID) {
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		String iconType = "default";
		String mod = "forestry";
		if (this == ExtraBeesSpecies.JADED) {
			iconType = "jaded";
			mod = "extrabees";
		}

		icons = new IIcon[EnumBeeType.values().length][3];
		IIcon body1 = BinnieCore.proxy.getIcon(register, mod, "bees/" + iconType + "/body1");
		for (int i = 0; i < EnumBeeType.values().length; ++i) {
			if (EnumBeeType.values()[i] != EnumBeeType.NONE) {
				icons[i][0] = BinnieCore.proxy.getIcon(register, mod, "bees/" + iconType + "/" + EnumBeeType.values()[i].toString().toLowerCase(Locale.ENGLISH) + ".outline");
				icons[i][1] = ((EnumBeeType.values()[i] != EnumBeeType.LARVAE) ? body1 : BinnieCore.proxy.getIcon(register, mod, "bees/" + iconType + "/" + EnumBeeType.values()[i].toString().toLowerCase(Locale.ENGLISH) + ".body"));
				icons[i][2] = BinnieCore.proxy.getIcon(register, mod, "bees/" + iconType + "/" + EnumBeeType.values()[i].toString().toLowerCase(Locale.ENGLISH) + ".body2");
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(EnumBeeType type, int renderPass) {
		if (icons == null) {
			return ExtraBeesSpecies.ARID.getIcon(type, renderPass);
		}
		return icons[type.ordinal()][renderPass];
	}

	@Override
	public IBeeRoot getRoot() {
		return Binnie.Genetics.getBeeRoot();
	}

	@Override
	public boolean isNocturnal() {
		return nocturnal;
	}

	public void setNocturnal() {
		nocturnal = true;
	}

	public void setAllDay() {
		setAllDay(true);
	}

	public void setAllDay(boolean allDay) {
		if (allDay) {
			template[EnumBeeChromosome.NOCTURNAL.ordinal()] = AlleleManager.alleleRegistry.getAllele("forestry.boolTrue");
		} else {
			template[EnumBeeChromosome.NOCTURNAL.ordinal()] = AlleleManager.alleleRegistry.getAllele("forestry.boolFalse");
		}
	}

	public void setCaveDwelling() {
		template[EnumBeeChromosome.CAVE_DWELLING.ordinal()] = AlleleManager.alleleRegistry.getAllele("forestry.boolTrue");
	}

	public void setTolerantFlyer() {
		template[EnumBeeChromosome.TOLERANT_FLYER.ordinal()] = AlleleManager.alleleRegistry.getAllele("forestry.boolTrue");
	}

	public void setFlowerProvider(String uid) {
		IAllele allele = AlleleManager.alleleRegistry.getAllele(uid);
		if (allele instanceof IAlleleFlowers) {
			template[EnumBeeChromosome.FLOWER_PROVIDER.ordinal()] = allele;
		}
	}

	public void setEffect(String uid) {
		IAllele allele = AlleleManager.alleleRegistry.getAllele(uid);
		if (allele instanceof IAlleleBeeEffect) {
			template[EnumBeeChromosome.EFFECT.ordinal()] = AlleleManager.alleleRegistry.getAllele(uid);
		}
	}

	private void setFertility(ForestryAllele.Fertility fert) {
		template[EnumBeeChromosome.FERTILITY.ordinal()] = fert.getAllele();
	}

	private void setLifespan(ForestryAllele.Lifespan fert) {
		template[EnumBeeChromosome.LIFESPAN.ordinal()] = fert.getAllele();
	}

	private void setSpeed(ForestryAllele.Speed fert) {
		template[EnumBeeChromosome.SPEED.ordinal()] = fert.getAllele();
	}

	private void setTerritory(ForestryAllele.Territory fert) {
		template[EnumBeeChromosome.TERRITORY.ordinal()] = fert.getAllele();
	}

	private void setFlowering(ForestryAllele.Flowering fert) {
		template[EnumBeeChromosome.FLOWERING.ordinal()] = fert.getAllele();
	}

	private void setHumidityTolerance(Tolerance fert) {
		template[EnumBeeChromosome.HUMIDITY_TOLERANCE.ordinal()] = fert.getAllele();
	}

	private void setTemperatureTolerance(Tolerance both1) {
		template[EnumBeeChromosome.TEMPERATURE_TOLERANCE.ordinal()] = both1.getAllele();
	}

	@Override
	public float getResearchSuitability(ItemStack itemstack) {
		if (itemstack == null) {
			return 0.0f;
		}

		for (ItemStack stack : products.keySet()) {
			if (stack.isItemEqual(itemstack)) {
				return 1.0f;
			}
		}

		for (ItemStack stack : specialties.keySet()) {
			if (stack.isItemEqual(itemstack)) {
				return 1.0f;
			}
		}

		if (itemstack.getItem() == Items.glass_bottle) {
			return 0.9f;
		}
		if (itemstack.getItem() == Mods.Forestry.item("honeyDrop")) {
			return 0.5f;
		}
		if (itemstack.getItem() == Mods.Forestry.item("honeydew")) {
			return 0.7f;
		}
		if (itemstack.getItem() == Mods.Forestry.item("beeComb")) {
			return 0.4f;
		}
		if (AlleleManager.alleleRegistry.isIndividual(itemstack)) {
			return 1.0f;
		}

		for (Map.Entry<ItemStack, Float> entry : getRoot().getResearchCatalysts().entrySet()) {
			if (entry.getKey().isItemEqual(itemstack)) {
				return entry.getValue();
			}
		}
		return 0.0f;
	}

	@Override
	public ItemStack[] getResearchBounty(World world, GameProfile researcher, IIndividual individual, int bountyLevel) {
		ArrayList<ItemStack> bounty = new ArrayList<ItemStack>();
		ItemStack research = null;
		if (world.rand.nextFloat() < 10.0f / bountyLevel) {
			Collection<? extends IMutation> combinations = getRoot().getCombinations(this);
			if (combinations.size() > 0) {
				IMutation[] candidates = combinations.toArray(new IMutation[0]);
				research = AlleleManager.alleleRegistry.getMutationNoteStack(researcher, candidates[world.rand.nextInt(candidates.length)]);
			}
		}

		if (research != null) {
			bounty.add(research);
		}

		if (bountyLevel > 10) {
			for (ItemStack stack : specialties.keySet()) {
				ItemStack stack2 = stack.copy();
				stack2.stackSize = world.rand.nextInt((int) (bountyLevel / 2.0f)) + 1;
				bounty.add(stack2);
			}
		}

		for (ItemStack stack : products.keySet()) {
			ItemStack stack2 = stack.copy();
			stack2.stackSize = world.rand.nextInt((int) (bountyLevel / 2.0f)) + 1;
			bounty.add(stack2);
		}
		return bounty.toArray(new ItemStack[0]);
	}

	@Override
	public String getEntityTexture() {
		return "/gfx/forestry/entities/bees/honeyBee.png";
	}

	@Override
	public int getComplexity() {
		return 1 + getGeneticAdvancement(this, new ArrayList<>());
	}

	private int getGeneticAdvancement(IAllele species, ArrayList<IAllele> exclude) {
		int own = 1;
		int highest = 0;
		exclude.add(species);

		for (IMutation mutation : getRoot().getPaths(species, EnumBeeChromosome.SPECIES)) {
			if (!exclude.contains(mutation.getAllele0())) {
				int otherAdvance = getGeneticAdvancement(mutation.getAllele0(), exclude);
				if (otherAdvance > highest) {
					highest = otherAdvance;
				}
			}
			if (!exclude.contains(mutation.getAllele1())) {
				int otherAdvance = getGeneticAdvancement(mutation.getAllele1(), exclude);
				if (otherAdvance <= highest) {
					continue;
				}
				highest = otherAdvance;
			}
		}
		return own + ((highest < 0) ? 0 : highest);
	}

	@Override
	public String getUnlocalizedName() {
		return getUID();
	}

	@Override
	public Map<ItemStack, Float> getProductChances() {
		return getProducts();
	}

	@Override
	public Map<ItemStack, Float> getSpecialtyChances() {
		return getSpecialty();
	}

	public enum State {
		Active,
		Inactive,
		Deprecated
	}
}
