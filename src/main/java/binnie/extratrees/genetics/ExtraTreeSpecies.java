package binnie.extratrees.genetics;

import binnie.Binnie;
import binnie.core.Mods;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.ILogType;
import binnie.extratrees.gen.WorldGenAlder;
import binnie.extratrees.gen.WorldGenApple;
import binnie.extratrees.gen.WorldGenAsh;
import binnie.extratrees.gen.WorldGenBanana;
import binnie.extratrees.gen.WorldGenBeech;
import binnie.extratrees.gen.WorldGenConifer;
import binnie.extratrees.gen.WorldGenDefault;
import binnie.extratrees.gen.WorldGenEucalyptus;
import binnie.extratrees.gen.WorldGenFir;
import binnie.extratrees.gen.WorldGenHolly;
import binnie.extratrees.gen.WorldGenJungle;
import binnie.extratrees.gen.WorldGenLazy;
import binnie.extratrees.gen.WorldGenMaple;
import binnie.extratrees.gen.WorldGenPalm;
import binnie.extratrees.gen.WorldGenPoplar;
import binnie.extratrees.gen.WorldGenShrub;
import binnie.extratrees.gen.WorldGenSorbus;
import binnie.extratrees.gen.WorldGenTree;
import binnie.extratrees.gen.WorldGenTree2;
import binnie.extratrees.gen.WorldGenTree3;
import binnie.extratrees.gen.WorldGenTropical;
import binnie.extratrees.gen.WorldGenWalnut;
import binnie.genetics.genetics.AlleleHelper;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.arboriculture.EnumGermlingType;
import forestry.api.arboriculture.EnumLeafType;
import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.arboriculture.IAlleleFruit;
import forestry.api.arboriculture.IAlleleTreeSpecies;
import forestry.api.arboriculture.IGermlingIconProvider;
import forestry.api.arboriculture.ITree;
import forestry.api.arboriculture.ITreeGenerator;
import forestry.api.arboriculture.ITreeGenome;
import forestry.api.arboriculture.ITreeRoot;
import forestry.api.arboriculture.TreeManager;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.core.IIconProvider;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IClassification;
import forestry.api.genetics.IFruitFamily;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IMutation;
import forestry.api.world.ITreeGenData;
import forestry.arboriculture.blocks.BlockForestryLeaves;
import forestry.arboriculture.genetics.Tree;
import forestry.arboriculture.render.TextureLeaves;
import forestry.arboriculture.tiles.TileLeaves;
import forestry.core.config.Constants;
import forestry.core.genetics.alleles.EnumAllele;
import forestry.core.render.TextureManager;
import forestry.plugins.PluginArboriculture;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.util.ForgeDirection;

import java.awt.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public enum ExtraTreeSpecies implements IAlleleTreeSpecies, IIconProvider, IGermlingIconProvider {
	OrchardApple("malus", "domestica", 0x648830, 0xff9cf3, ILogType.ExtraTreeLog.Apple, ExtraTreeFruitGene.Apple, WorldGenApple.OrchardApple.class),
	SweetCrabapple("malus", "coronaria", 0x7a9953, 0xfc359f, ILogType.ExtraTreeLog.Apple, ExtraTreeFruitGene.Crabapple, WorldGenApple.SweetCrabapple.class),
	FloweringCrabapple("malus", "hopa", 0x7a9953, 0xfc359f, ILogType.ExtraTreeLog.Apple, ExtraTreeFruitGene.Crabapple, WorldGenApple.FloweringCrabapple.class),
	PrairieCrabapple("malus", "ioensis", 0x7a9953, 0xfc359f, ILogType.ExtraTreeLog.Apple, ExtraTreeFruitGene.Crabapple, WorldGenApple.PrairieCrabapple.class),
	Blackthorn("prunus", "spinosa ", 0x6d8f1e, 0xff87c7, ILogType.ForestryLog.PLUM, ExtraTreeFruitGene.Blackthorn, null),
	CherryPlum("prunus", "cerasifera", 0x6d8f1e, 0xff87c7, ILogType.ForestryLog.PLUM, ExtraTreeFruitGene.CherryPlum, null),
	Peach("prunus", "persica", 0x6d8f1e, 0xff269a, ILogType.ForestryLog.PLUM, ExtraTreeFruitGene.Peach, null),
	Nectarine("prunus", "nectarina", 0x6d8f1e, 0xff269a, ILogType.ForestryLog.PLUM, ExtraTreeFruitGene.Nectarine, null),
	Apricot("prunus", "armeniaca", 0x6d8f1e, 0xf5b8d8, ILogType.ForestryLog.PLUM, ExtraTreeFruitGene.Apricot, null),
	Almond("prunus", "amygdalus", 0x6d8f1e, 0xf584c0, ILogType.ForestryLog.PLUM, ExtraTreeFruitGene.Almond, null),
	WildCherry("prunus", "avium", 0x6d8f1e, 0xf7ebf6, ILogType.ExtraTreeLog.Cherry, ExtraTreeFruitGene.WildCherry, null),
	SourCherry("prunus", "cerasus", 0x6d8f1e, 0xf7ebf6, ILogType.ExtraTreeLog.Cherry, ExtraTreeFruitGene.SourCherry, null),
	BlackCherry("prunus", "serotina", 0x6d8f1e, 0xfae1f8, ILogType.ExtraTreeLog.Cherry, ExtraTreeFruitGene.BlackCherry, null),
	Orange("citrus", "sinensis", 0x88af54, 0xa3b850, ILogType.ForestryLog.CITRUS, ExtraTreeFruitGene.Orange, null),
	Manderin("citrus", "reticulata", 0x88af54, 0xa3b850, ILogType.ForestryLog.CITRUS, ExtraTreeFruitGene.Manderin, null),
	Satsuma("citrus", "unshiu", 0x88af54, 0xa3b850, ILogType.ForestryLog.CITRUS, ExtraTreeFruitGene.Satsuma, null),
	Tangerine("citrus", "tangerina", 0x88af54, 0xa3b850, ILogType.ForestryLog.CITRUS, ExtraTreeFruitGene.Tangerine, null),
	Lime("citrus", "latifolia", 0x88af54, 0xa3b850, ILogType.ForestryLog.CITRUS, ExtraTreeFruitGene.Lime, null),
	KeyLime("citrus", "aurantifolia", 0x88af54, 0xa3b850, ILogType.ForestryLog.CITRUS, ExtraTreeFruitGene.KeyLime, null),
	FingerLime("citrus", "australasica", 0x88af54, 0xa3b850, ILogType.ForestryLog.CITRUS, ExtraTreeFruitGene.FingerLime, null),
	Pomelo("citrus", "maxima", 0x88af54, 0xa3b850, ILogType.ForestryLog.CITRUS, ExtraTreeFruitGene.Pomelo, null),
	Grapefruit("citrus", "paradisi", 0x88af54, 0xa3b850, ILogType.ForestryLog.CITRUS, ExtraTreeFruitGene.Grapefruit, null),
	Kumquat("citrus", "margarita", 0x88af54, 0xa3b850, ILogType.ForestryLog.CITRUS, ExtraTreeFruitGene.Kumquat, null),
	Citron("citrus", "medica", 0x88af54, 0xa3b850, ILogType.ForestryLog.CITRUS, ExtraTreeFruitGene.Citron, null),
	BuddhaHand("citrus", "sarcodactylus", 0x88af54, 0xa3b850, ILogType.ForestryLog.CITRUS, ExtraTreeFruitGene.BuddhaHand, null),
	Banana("musa", "sinensis", 0xa1cd8e, 0x44e500, ILogType.ExtraTreeLog.Banana, ExtraTreeFruitGene.Banana, WorldGenBanana.class),
	RedBanana("musa", "rubra", 0xa1cd8e, 0x44e500, ILogType.ExtraTreeLog.Banana, ExtraTreeFruitGene.RedBanana, WorldGenBanana.class),
	Plantain("musa", "paradisiaca", 0xa1cd8e, 0x44e500, ILogType.ExtraTreeLog.Banana, ExtraTreeFruitGene.Plantain, WorldGenBanana.class),
	Butternut("juglans", "cinerea", 0x82b58c, 0x82dd8c, ILogType.ExtraTreeLog.Butternut, ExtraTreeFruitGene.Butternut, WorldGenWalnut.Butternut.class),
	Rowan("sorbus", "aucuparia", 0x9ec79b, 0x9ee8b2, ILogType.ExtraTreeLog.Rowan, null, WorldGenSorbus.Rowan.class),
	Hemlock("tsuga", "heterophylla", 0x5cac72, 0x5cd172, ILogType.ExtraTreeLog.Hemlock, null, WorldGenConifer.WesternHemlock.class),
	Ash("fraxinus", "excelsior", 0x488e2b, 0x48e42b, ILogType.ExtraTreeLog.Ash, null, WorldGenAsh.CommonAsh.class),
	Alder("alnus", "glutinosa", 0x698a33, 0x69ae33, ILogType.ExtraTreeLog.Alder, null, WorldGenAlder.CommonAlder.class),
	Beech("fagus", "sylvatica", 0x83a04c, 0x83c64c, ILogType.ExtraTreeLog.Beech, ExtraTreeFruitGene.Beechnut, WorldGenBeech.CommonBeech.class),
	CopperBeech("fagus", "purpurea", 0x801318, 0xd15b4d, ILogType.ExtraTreeLog.Beech, ExtraTreeFruitGene.Beechnut, WorldGenBeech.CopperBeech.class),
	Aspen("populus", "tremula", 0x8acc37, 0x8ae18f, ILogType.ForestryLog.POPLAR, null, WorldGenPoplar.Aspen.class),
	Yew("taxus", "baccata", 0x948a4d, 0x94ae4d, ILogType.ExtraTreeLog.Yew, null, WorldGenConifer.Yew.class),
	Cypress("chamaecyparis", "lawsoniana", 0x89c9a7, 0x89ddc6, ILogType.ExtraTreeLog.Cypress, null, WorldGenConifer.Cypress.class),
	DouglasFir("pseudotsuga", "menziesii", 0x99b582, 0x99d1aa, ILogType.ExtraTreeLog.Fir, null, WorldGenFir.DouglasFir.class),
	Hazel("Corylus", "avellana", 0x9bb552, 0x9be152, ILogType.ExtraTreeLog.Hazel, ExtraTreeFruitGene.Hazelnut, WorldGenTree3.Hazel.class),
	Sycamore("ficus", "sycomorus", 0xa0a52f, 0xb4d55c, ILogType.ExtraTreeLog.Fig, ExtraTreeFruitGene.Fig, WorldGenTree3.Sycamore.class),
	Whitebeam("sorbus", "aria", 0xbace99, 0x72863f, ILogType.ExtraTreeLog.Whitebeam, null, WorldGenSorbus.Whitebeam.class),
	Hawthorn("crataegus", "monogyna", 0x6ba84a, 0x98b77b, ILogType.ExtraTreeLog.Hawthorn, null, WorldGenTree3.Hawthorn.class),
	Pecan("carya", "illinoinensis", 0x85b674, 0x2c581b, ILogType.ExtraTreeLog.Hickory, ExtraTreeFruitGene.Pecan, WorldGenTree3.Pecan.class),
	Elm("ulmus", "procera", 0x7c9048, 0x7cbe48, ILogType.ExtraTreeLog.Elm, null, WorldGenTree3.Elm.class),
	Elder("sambucus", "nigra", 0xaeb873, 0xe0e7bd, ILogType.ExtraTreeLog.Elder, ExtraTreeFruitGene.Elderberry, WorldGenTree3.Elder.class),
	Holly("ilex", "aquifolium", 0x254b4c, 0x6e9284, ILogType.ExtraTreeLog.Holly, null, WorldGenHolly.Holly.class),
	Hornbeam("carpinus", "betulus", 0x96a71b, 0x96dd1b, ILogType.ExtraTreeLog.Hornbeam, null, WorldGenTree3.Hornbeam.class),
	Sallow("salix", "caprea", 0xaeb323, 0xb7ec25, ILogType.ForestryLog.WILLOW, null, WorldGenTree3.Sallow.class),
	AcornOak("quercus", "robur", 0x66733e, 0x9ea231, ILogType.VanillaLog.Oak, ExtraTreeFruitGene.Acorn, WorldGenTree3.AcornOak.class),
	Fir("abies", "alba", 0x6f7c20, 0x6fd120, ILogType.ExtraTreeLog.Fir, null, WorldGenFir.SilverFir.class),
	Cedar("cedrus", "libani", 0x95a370, 0x95e870, ILogType.ExtraTreeLog.Cedar, null, WorldGenConifer.Cedar.class),
	Olive("olea", "europaea", 0x3c4834, 0x3c4834, ILogType.ExtraTreeLog.Olive, ExtraTreeFruitGene.Olive, WorldGenTree2.Olive.class),
	RedMaple("acer", "ubrum", 0xe82e17, 0xe82e17, ILogType.ForestryLog.MAPLE, null, WorldGenMaple.RedMaple.class),
	BalsamFir("abies", "balsamea", 0x74a07c, 0x74a07c, ILogType.ExtraTreeLog.Fir, null, WorldGenFir.BalsamFir.class),
	LoblollyPine("pinus", "taeda", 0x6f8a47, 0x6f8a47, ILogType.ForestryLog.PINE, null, WorldGenConifer.LoblollyPine.class),
	Sweetgum("liquidambar", "styraciflua", 0x8b8762, 0x8b8762, ILogType.ExtraTreeLog.Sweetgum, null, WorldGenTree2.Sweetgum.class),
	Locust("robinia", "pseudoacacia", 0x887300, 0x887300, ILogType.ExtraTreeLog.Locust, null, WorldGenTree2.Locust.class),
	Pear("pyrus", "communis", 0x5e8826, 0x5e8826, ILogType.ExtraTreeLog.Pear, ExtraTreeFruitGene.Pear, WorldGenTree2.Pear.class),
	OsangeOsange("maclura", "pomifera", 0x687a50, 0x687a50, ILogType.ExtraTreeLog.Maclura, ExtraTreeFruitGene.OsangeOsange, WorldGenJungle.OsangeOsange.class),
	OldFustic("maclura", "tinctoria", 0x687a50, 0x687a50, ILogType.ExtraTreeLog.Maclura, null, WorldGenJungle.OldFustic.class),
	Brazilwood("caesalpinia", "echinata", 0x607459, 0x607459, ILogType.ExtraTreeLog.Brazilwood, null, WorldGenJungle.Brazilwood.class),
	Logwood("haematoxylum", "campechianum", 0x889f6b, 0x889f6b, ILogType.ExtraTreeLog.Logwood, null, WorldGenJungle.Logwood.class),
	Rosewood("dalbergia", "latifolia", 0x879b22, 0x879b22, ILogType.ExtraTreeLog.Rosewood, null, WorldGenJungle.Rosewood.class),
	Purpleheart("peltogyne", "spp.", 0x778f55, 0x778f55, ILogType.ExtraTreeLog.Purpleheart, null, WorldGenJungle.Purpleheart.class),
	Iroko("milicia", "excelsa", 0xafc86c, 0xafc86c, ILogType.ExtraTreeLog.Iroko, null, WorldGenTree2.Iroko.class),
	Gingko("ginkgo", "biloba", 0x719651, 0x719651, ILogType.ExtraTreeLog.Gingko, ExtraTreeFruitGene.GingkoNut, WorldGenTree2.Gingko.class),
	Brazilnut("bertholletia", "excelsa", 0x7c8f7b, 0x7c8f7b, ILogType.VanillaLog.Jungle, ExtraTreeFruitGene.BrazilNut, WorldGenJungle.BrazilNut.class),
	RoseGum("eucalyptus", "grandis", 0x9ca258, 0x9ca258, ILogType.ExtraTreeLog.Eucalyptus, null, WorldGenEucalyptus.RoseGum.class),
	SwampGum("eucalyptus", "grandis", 0xa2c686, 0xa2c686, ILogType.ExtraTreeLog.Eucalyptus2, null, WorldGenEucalyptus.SwampGum.class),
	Box("boxus", "sempervirens", 0x72996d, 0x72996d, ILogType.ExtraTreeLog.Box, null, WorldGenTree2.Box.class),
	Clove("syzygium", "aromaticum", 0x7a821f, 0x7a821f, ILogType.ExtraTreeLog.Syzgium, ExtraTreeFruitGene.Clove, WorldGenTree2.Clove.class),
	Coffee("coffea", "arabica", 0x6f9065, 0x6f9065, ILogType.VanillaLog.Jungle, ExtraTreeFruitGene.Coffee, WorldGenJungle.Coffee.class),
	MonkeyPuzzle("araucaria", "araucana", 0x576158, 0x576158, ILogType.ForestryLog.PINE, null, WorldGenConifer.MonkeyPuzzle.class),
	RainbowGum("eucalyptus", "deglupta", 0xb7f025, 0xb7f025, ILogType.ExtraTreeLog.Eucalyptus3, null, WorldGenEucalyptus.RainbowGum.class),
	PinkIvory("berchemia", "zeyheri", 0x7c9159, 0x7c9159, ILogType.ExtraTreeLog.PinkIvory, null, WorldGenTree.class),
	Blackcurrant("ribes", "nigrum", 0xa6da5c, 0xa6da5c, null, ExtraTreeFruitGene.Blackcurrant, WorldGenShrub.Shrub.class),
	Redcurrant("ribes", "rubrum", 0x74ac00, 0x74ac00, null, ExtraTreeFruitGene.Redcurrant, WorldGenShrub.Shrub.class),
	Blackberry("rubus", "fruticosus", 0x92c15b, 0x92c15b, null, ExtraTreeFruitGene.Blackberry, WorldGenShrub.Shrub.class),
	Raspberry("rubus", "idaeus", 0x83b96e, 0x83b96e, null, ExtraTreeFruitGene.Raspberry, WorldGenShrub.Shrub.class),
	Blueberry("vaccinium", "corymbosum", 0x72c750, 0x72c750, null, ExtraTreeFruitGene.Blueberry, WorldGenShrub.Shrub.class),
	Cranberry("vaccinium", "oxycoccos", 0x96d179, 0x96d179, null, ExtraTreeFruitGene.Cranberry, WorldGenShrub.Shrub.class),
	Juniper("juniperus", "communis", 0x90b149, 0x90b149, null, ExtraTreeFruitGene.Juniper, WorldGenShrub.Shrub.class),
	Gooseberry("ribes", "grossularia", 0x79bb00, 0x79bb00, null, ExtraTreeFruitGene.Gooseberry, WorldGenShrub.Shrub.class),
	GoldenRaspberry("rubus", "occidentalis", 0x83b96e, 0x83b96e, null, ExtraTreeFruitGene.GoldenRaspberry, WorldGenShrub.Shrub.class),
	Cinnamon("cinnamomum", "cassia", 0x738e0b, 0x738e0b, ILogType.ExtraTreeLog.Cinnamon, null, WorldGenLazy.Tree.class),
	Coconut("cocous", "nucifera", 0x649923, 0x649923, ILogType.VanillaLog.Jungle, ExtraTreeFruitGene.Coconut, WorldGenPalm.Coconut.class),
	Cashew("anacardium", "occidentale", 0xabb962, 0xabb962, ILogType.VanillaLog.Jungle, ExtraTreeFruitGene.Cashew, WorldGenLazy.Tree.class),
	Avacado("persea", "americana", 0x96a375, 0x96a375, ILogType.VanillaLog.Jungle, ExtraTreeFruitGene.Avacado, WorldGenLazy.Tree.class),
	Nutmeg("myristica", "fragrans", 0x488d4c, 0x488d4c, ILogType.VanillaLog.Jungle, ExtraTreeFruitGene.Nutmeg, WorldGenLazy.Tree.class),
	Allspice("pimenta", "dioica", 0x7c9724, 0x7c9724, ILogType.VanillaLog.Jungle, ExtraTreeFruitGene.Allspice, WorldGenLazy.Tree.class),
	Chilli("capsicum", "annuum", 0x2a9f01, 0x2a9f01, ILogType.VanillaLog.Jungle, ExtraTreeFruitGene.Chilli, WorldGenLazy.Tree.class),
	StarAnise("illicium", "verum", 0x7fc409, 0x7fc409, ILogType.VanillaLog.Jungle, ExtraTreeFruitGene.StarAnise, WorldGenLazy.Tree.class),
	Mango("mangifera", "indica", 0x87b574, 0x87b574, ILogType.VanillaLog.Jungle, ExtraTreeFruitGene.Mango, WorldGenTropical.Mango.class),
	Starfruit("averrhoa", "carambola", 0x6da92d, 0x6da92d, ILogType.VanillaLog.Jungle, ExtraTreeFruitGene.Starfruit, WorldGenLazy.Tree.class),
	Candlenut("aleurites", "moluccana", 0x8aa36c, 0x8aa36c, ILogType.VanillaLog.Jungle, ExtraTreeFruitGene.Candlenut, WorldGenLazy.Tree.class),
	DwarfHazel("Corylus", "americana", 0x9bb552, 0x9be152, ILogType.ExtraTreeLog.Hazel, ExtraTreeFruitGene.Hazelnut, WorldGenShrub.Shrub.class);

	protected ArrayList<IFruitFamily> families;
	protected int girth;
	protected Class<? extends WorldGenerator> gen;
	protected IAlleleFruit fruit;
	protected IAllele[] template;
	protected int color;
	protected String binomial;
	protected String uid;
	protected ILogType wood;
	protected String branchName;
	protected IClassification branch;
	protected int colorPollineted;

	private LeafType leafType;
	private SaplingType saplingType;

	ExtraTreeSpecies(String branch, String binomial, int color, int polColor, ILogType wood, IAlleleFruit fruit, Class<? extends WorldGenerator> gen) {
		this.color = color;
		this.wood = wood;
		this.fruit = fruit;
		this.gen = ((gen == null) ? WorldGenTree.class : gen);
		this.binomial = binomial;
		leafType = LeafType.Normal;
		saplingType = SaplingType.Default;
		families = new ArrayList<>();
		girth = 1;
		colorPollineted = new Color(color).brighter().getRGB();
		uid = toString().toLowerCase();
		branchName = branch;
	}

	public static void init() {
		String bookArborist = "Arborist Manual";
		for (ExtraTreeSpecies species : values()) {
			species.preInit();
		}

		ExtraTreeSpecies.OrchardApple.finished();
		ExtraTreeSpecies.SweetCrabapple.finished();
		ExtraTreeSpecies.FloweringCrabapple.finished();
		ExtraTreeSpecies.PrairieCrabapple.finished();
		ExtraTreeSpecies[] pruneSpecies = new ExtraTreeSpecies[]{ExtraTreeSpecies.Blackthorn, ExtraTreeSpecies.CherryPlum, ExtraTreeSpecies.Almond, ExtraTreeSpecies.Apricot, ExtraTreeSpecies.Peach, ExtraTreeSpecies.Nectarine, ExtraTreeSpecies.WildCherry, ExtraTreeSpecies.SourCherry, ExtraTreeSpecies.BlackCherry};
		for (ExtraTreeSpecies species2 : pruneSpecies) {
			IAlleleTreeSpecies citrus = (IAlleleTreeSpecies) AlleleManager.alleleRegistry.getAllele("forestry.treePlum");
			species2.setWorldGen(citrus.getGenerator().getWorldGenerator(TreeManager.treeRoot.templateAsIndividual(citrus.getRoot().getDefaultTemplate())).getClass());
			species2.saplingType = SaplingType.Fruit;
			species2.finished();
		}

		ExtraTreeSpecies[] citrusSpecies = new ExtraTreeSpecies[]{ExtraTreeSpecies.Orange, ExtraTreeSpecies.Manderin, ExtraTreeSpecies.Satsuma, ExtraTreeSpecies.Tangerine, ExtraTreeSpecies.Lime, ExtraTreeSpecies.KeyLime, ExtraTreeSpecies.FingerLime, ExtraTreeSpecies.Pomelo, ExtraTreeSpecies.Grapefruit, ExtraTreeSpecies.Kumquat, ExtraTreeSpecies.Citron, ExtraTreeSpecies.BuddhaHand};
		for (ExtraTreeSpecies species3 : citrusSpecies) {
			species3.setLeafType(LeafType.Jungle);
			species3.saplingType = SaplingType.Fruit;
			IAlleleTreeSpecies citrus2 = (IAlleleTreeSpecies) AlleleManager.alleleRegistry.getAllele("forestry.treeLemon");
			species3.setWorldGen(citrus2.getGenerator().getWorldGenerator(TreeManager.treeRoot.templateAsIndividual(citrus2.getRoot().getDefaultTemplate())).getClass());
			species3.finished();
		}

		ExtraTreeSpecies.Banana.setLeafType(LeafType.Palm);
		ExtraTreeSpecies.RedBanana.setLeafType(LeafType.Palm);
		ExtraTreeSpecies.Plantain.setLeafType(LeafType.Palm);
		ExtraTreeSpecies.Banana.finished();
		ExtraTreeSpecies.RedBanana.finished();
		ExtraTreeSpecies.Plantain.finished();
		ExtraTreeSpecies.Hemlock.setLeafType(LeafType.Conifer);
		ExtraTreeSpecies.Butternut.finished();
		ExtraTreeSpecies.Butternut.setGirth(2);
		ExtraTreeSpecies.Rowan.finished();
		ExtraTreeSpecies.Hemlock.finished();
		ExtraTreeSpecies.Hemlock.setGirth(2);
		ExtraTreeSpecies.Ash.finished();
		ExtraTreeSpecies.Alder.finished();
		ExtraTreeSpecies.Beech.finished();
		ExtraTreeSpecies.CopperBeech.finished();
		ExtraTreeSpecies.Aspen.finished();
		ExtraTreeSpecies.Yew.setLeafType(LeafType.Conifer);
		ExtraTreeSpecies.Cypress.setLeafType(LeafType.Conifer);
		ExtraTreeSpecies.DouglasFir.setLeafType(LeafType.Conifer);
		ExtraTreeSpecies.Yew.finished();
		ExtraTreeSpecies.Cypress.finished();
		ExtraTreeSpecies.Cypress.saplingType = SaplingType.Poplar;
		ExtraTreeSpecies.DouglasFir.finished();
		ExtraTreeSpecies.DouglasFir.setGirth(2);
		ExtraTreeSpecies.Hazel.finished();
		ExtraTreeSpecies.DwarfHazel.finished();
		ExtraTreeSpecies.DwarfHazel.saplingType = SaplingType.Shrub;
		ExtraTreeSpecies.Sycamore.finished();
		ExtraTreeSpecies.Whitebeam.finished();
		ExtraTreeSpecies.Hawthorn.finished();
		ExtraTreeSpecies.Pecan.finished();
		ExtraTreeSpecies.Fir.setLeafType(LeafType.Conifer);
		ExtraTreeSpecies.Cedar.setLeafType(LeafType.Conifer);
		ExtraTreeSpecies.Sallow.setLeafType(LeafType.Willow);
		ExtraTreeSpecies.Elm.finished();
		ExtraTreeSpecies.Elder.finished();
		ExtraTreeSpecies.Holly.finished();
		ExtraTreeSpecies.Hornbeam.finished();
		ExtraTreeSpecies.Sallow.finished();
		ExtraTreeSpecies.AcornOak.finished();
		ExtraTreeSpecies.AcornOak.setGirth(2);
		ExtraTreeSpecies.Fir.finished();
		ExtraTreeSpecies.Cedar.finished();
		ExtraTreeSpecies.Cedar.setGirth(2);
		ExtraTreeSpecies.RedMaple.setLeafType(LeafType.Maple);
		ExtraTreeSpecies.BalsamFir.setLeafType(LeafType.Conifer);
		ExtraTreeSpecies.LoblollyPine.setLeafType(LeafType.Conifer);
		ExtraTreeSpecies.Olive.finished();
		ExtraTreeSpecies.RedMaple.finished();
		ExtraTreeSpecies.BalsamFir.finished();
		ExtraTreeSpecies.LoblollyPine.finished();
		ExtraTreeSpecies.Sweetgum.finished();
		ExtraTreeSpecies.Locust.finished();
		ExtraTreeSpecies.Pear.finished();
		ExtraTreeSpecies.OsangeOsange.setLeafType(LeafType.Jungle);
		ExtraTreeSpecies.OldFustic.setLeafType(LeafType.Jungle);
		ExtraTreeSpecies.Brazilwood.setLeafType(LeafType.Jungle);
		ExtraTreeSpecies.Logwood.setLeafType(LeafType.Jungle);
		ExtraTreeSpecies.Rosewood.setLeafType(LeafType.Jungle);
		ExtraTreeSpecies.Purpleheart.setLeafType(LeafType.Jungle);
		ExtraTreeSpecies.OsangeOsange.finished();
		ExtraTreeSpecies.OldFustic.finished();
		ExtraTreeSpecies.Brazilwood.finished();
		ExtraTreeSpecies.Logwood.finished();
		ExtraTreeSpecies.Rosewood.finished();
		ExtraTreeSpecies.Purpleheart.finished();
		ExtraTreeSpecies.Gingko.setLeafType(LeafType.Jungle);
		ExtraTreeSpecies.Brazilnut.setLeafType(LeafType.Jungle);
		ExtraTreeSpecies.RoseGum.setLeafType(LeafType.Jungle);
		ExtraTreeSpecies.SwampGum.setLeafType(LeafType.Jungle);
		ExtraTreeSpecies.Coffee.setLeafType(LeafType.Jungle);
		ExtraTreeSpecies.MonkeyPuzzle.setLeafType(LeafType.Conifer);
		ExtraTreeSpecies.RainbowGum.setLeafType(LeafType.Jungle);
		ExtraTreeSpecies.Iroko.finished();
		ExtraTreeSpecies.Gingko.finished();
		ExtraTreeSpecies.Brazilnut.finished();
		ExtraTreeSpecies.RoseGum.finished();
		ExtraTreeSpecies.SwampGum.finished();
		ExtraTreeSpecies.SwampGum.setGirth(2);
		ExtraTreeSpecies.Box.finished();
		ExtraTreeSpecies.Clove.finished();
		ExtraTreeSpecies.Coffee.finished();
		ExtraTreeSpecies.MonkeyPuzzle.finished();
		ExtraTreeSpecies.MonkeyPuzzle.setGirth(2);
		ExtraTreeSpecies.RainbowGum.finished();
		ExtraTreeSpecies.PinkIvory.finished();
		ExtraTreeSpecies.Juniper.setLeafType(LeafType.Conifer);
		ExtraTreeSpecies.Blackcurrant.saplingType = SaplingType.Shrub;
		ExtraTreeSpecies.Redcurrant.saplingType = SaplingType.Shrub;
		ExtraTreeSpecies.Blackberry.saplingType = SaplingType.Shrub;
		ExtraTreeSpecies.Raspberry.saplingType = SaplingType.Shrub;
		ExtraTreeSpecies.Blueberry.saplingType = SaplingType.Shrub;
		ExtraTreeSpecies.Cranberry.saplingType = SaplingType.Shrub;
		ExtraTreeSpecies.Juniper.saplingType = SaplingType.Shrub;
		ExtraTreeSpecies.Gooseberry.saplingType = SaplingType.Shrub;
		ExtraTreeSpecies.GoldenRaspberry.saplingType = SaplingType.Shrub;

		citrusSpecies = values();
		for (ExtraTreeSpecies species3 : citrusSpecies) {
			String scientific = species3.branchName.substring(0, 1).toUpperCase() + species3.branchName.substring(1).toLowerCase();
			String uid = "trees." + species3.branchName.toLowerCase();
			IClassification branch = AlleleManager.alleleRegistry.getClassification("genus." + uid);
			if (branch == null) {
				branch = AlleleManager.alleleRegistry.createAndRegisterClassification(IClassification.EnumClassLevel.GENUS, uid, scientific);
			}
			(species3.branch = branch).addMemberSpecies(species3);
		}

		ExtraTreeSpecies.Cinnamon.setLeafType(LeafType.Jungle);
		ExtraTreeSpecies.Coconut.setLeafType(LeafType.Palm);
		ExtraTreeSpecies.Cashew.setLeafType(LeafType.Jungle);
		ExtraTreeSpecies.Avacado.setLeafType(LeafType.Jungle);
		ExtraTreeSpecies.Nutmeg.setLeafType(LeafType.Jungle);
		ExtraTreeSpecies.Allspice.setLeafType(LeafType.Jungle);
		ExtraTreeSpecies.Chilli.setLeafType(LeafType.Jungle);
		ExtraTreeSpecies.StarAnise.setLeafType(LeafType.Jungle);
		ExtraTreeSpecies.Mango.setLeafType(LeafType.Jungle);
		ExtraTreeSpecies.Starfruit.setLeafType(LeafType.Jungle);

		IFruitFamily familyPrune = AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes");
		IFruitFamily familyPome = AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes");
		IFruitFamily familyJungle = AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle");
		IFruitFamily familyNuts = AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts");
		IFruitFamily familyBerry = ExtraTreeFruitFamily.Berry;
		IFruitFamily familyCitrus = ExtraTreeFruitFamily.Citrus;

		ExtraTreeSpecies.OrchardApple
			.addFamily(familyPome)
			.addFamily(familyPrune)
			.addFamily(familyCitrus)
			.setYield(EnumAllele.Yield.HIGHER)
			.setSappiness(EnumAllele.Sappiness.LOW)
			.setMaturation(EnumAllele.Maturation.FASTER);

		ExtraTreeSpecies.SweetCrabapple
			.addFamily(familyPome)
			.addFamily(familyPrune)
			.addFamily(familyCitrus)
			.setYield(EnumAllele.Yield.HIGH)
			.setSappiness(EnumAllele.Sappiness.AVERAGE)
			.setMaturation(EnumAllele.Maturation.FAST);

		ExtraTreeSpecies.FloweringCrabapple
			.addFamily(familyPome)
			.addFamily(familyPrune)
			.addFamily(familyCitrus)
			.setFertility(EnumAllele.Saplings.AVERAGE)
			.setYield(EnumAllele.Yield.AVERAGE)
			.setSappiness(EnumAllele.Sappiness.LOW)
			.setMaturation(EnumAllele.Maturation.FAST);

		ExtraTreeSpecies.PrairieCrabapple
			.addFamily(familyPome)
			.addFamily(familyPrune)
			.addFamily(familyCitrus)
			.setHeight(EnumAllele.Height.SMALLEST)
			.setFertility(EnumAllele.Saplings.LOW)
			.setYield(EnumAllele.Yield.AVERAGE)
			.setSappiness(EnumAllele.Sappiness.AVERAGE);

		ExtraTreeSpecies.Blackthorn
			.addFamily(familyPome)
			.addFamily(familyPrune)
			.addFamily(familyCitrus)
			.setHeight(EnumAllele.Height.SMALLEST)
			.setYield(EnumAllele.Yield.LOW)
			.setSappiness(EnumAllele.Sappiness.AVERAGE)
			.setMaturation(EnumAllele.Maturation.FAST);

		ExtraTreeSpecies.CherryPlum
			.addFamily(familyPome)
			.addFamily(familyPrune)
			.addFamily(familyCitrus)
			.setHeight(EnumAllele.Height.SMALLEST)
			.setYield(EnumAllele.Yield.LOWER)
			.setSappiness(EnumAllele.Sappiness.AVERAGE)
			.setMaturation(EnumAllele.Maturation.FAST);

		ExtraTreeSpecies.Peach
			.addFamily(familyPome)
			.addFamily(familyPrune)
			.addFamily(familyCitrus)
			.setHeight(EnumAllele.Height.SMALLEST)
			.setFertility(EnumAllele.Saplings.LOW)
			.setYield(EnumAllele.Yield.AVERAGE)
			.setSappiness(EnumAllele.Sappiness.AVERAGE);

		ExtraTreeSpecies.Nectarine
			.addFamily(familyPome)
			.addFamily(familyPrune)
			.addFamily(familyCitrus)
			.setHeight(EnumAllele.Height.SMALLEST)
			.setYield(EnumAllele.Yield.AVERAGE)
			.setSappiness(EnumAllele.Sappiness.AVERAGE);

		ExtraTreeSpecies.Apricot
			.addFamily(familyPome)
			.addFamily(familyPrune)
			.addFamily(familyCitrus)
			.setHeight(EnumAllele.Height.SMALLEST)
			.setYield(EnumAllele.Yield.LOW)
			.setSappiness(EnumAllele.Sappiness.AVERAGE);

		ExtraTreeSpecies.Almond
			.addFamily(familyPome)
			.addFamily(familyPrune)
			.addFamily(familyCitrus)
			.setYield(EnumAllele.Yield.LOWER)
			.setSappiness(EnumAllele.Sappiness.LOWER)
			.setMaturation(EnumAllele.Maturation.FAST);

		ExtraTreeSpecies.WildCherry
			.addFamily(familyPome)
			.addFamily(familyPrune)
			.addFamily(familyCitrus)
			.setHeight(EnumAllele.Height.SMALLEST)
			.setFertility(EnumAllele.Saplings.LOW)
			.setYield(EnumAllele.Yield.LOWER)
			.setSappiness(EnumAllele.Sappiness.LOW)
			.setMaturation(EnumAllele.Maturation.FAST);

		ExtraTreeSpecies.SourCherry
			.addFamily(familyPome)
			.addFamily(familyPrune)
			.addFamily(familyCitrus)
			.setYield(EnumAllele.Yield.LOWER)
			.setSappiness(EnumAllele.Sappiness.LOW);

		ExtraTreeSpecies.BlackCherry
			.addFamily(familyPome)
			.addFamily(familyPrune)
			.addFamily(familyCitrus)
			.setHeight(EnumAllele.Height.SMALLEST)
			.setFertility(EnumAllele.Saplings.LOWEST)
			.setYield(EnumAllele.Yield.AVERAGE)
			.setSappiness(EnumAllele.Sappiness.LOWER)
			.setMaturation(EnumAllele.Maturation.FAST);

		ExtraTreeSpecies.Orange
			.addFamily(familyPome)
			.addFamily(familyPrune)
			.addFamily(familyCitrus)
			.setHeight(EnumAllele.Height.SMALLEST)
			.setYield(EnumAllele.Yield.AVERAGE)
			.setSappiness(EnumAllele.Sappiness.AVERAGE)
			.setMaturation(EnumAllele.Maturation.FAST);

		ExtraTreeSpecies.Manderin
			.addFamily(familyPome)
			.addFamily(familyPrune)
			.addFamily(familyCitrus)
			.setHeight(EnumAllele.Height.SMALLEST)
			.setFertility(EnumAllele.Saplings.LOW)
			.setYield(EnumAllele.Yield.HIGH)
			.setSappiness(EnumAllele.Sappiness.LOW);

		ExtraTreeSpecies.Satsuma
			.addFamily(familyPome)
			.addFamily(familyPrune)
			.addFamily(familyCitrus)
			.setHeight(EnumAllele.Height.SMALLEST)
			.setYield(EnumAllele.Yield.LOW)
			.setSappiness(EnumAllele.Sappiness.LOW)
			.setMaturation(EnumAllele.Maturation.FAST);

		ExtraTreeSpecies.Tangerine
			.addFamily(familyPome)
			.addFamily(familyPrune)
			.addFamily(familyCitrus)
			.setHeight(EnumAllele.Height.SMALLEST)
			.setFertility(EnumAllele.Saplings.LOW)
			.setYield(EnumAllele.Yield.AVERAGE)
			.setSappiness(EnumAllele.Sappiness.AVERAGE)
			.setMaturation(EnumAllele.Maturation.FASTER);

		ExtraTreeSpecies.Lime
			.addFamily(familyPome)
			.addFamily(familyPrune)
			.addFamily(familyCitrus)
			.setHeight(EnumAllele.Height.SMALLEST)
			.setYield(EnumAllele.Yield.AVERAGE)
			.setSappiness(EnumAllele.Sappiness.LOW);

		ExtraTreeSpecies.KeyLime
			.addFamily(familyPome)
			.addFamily(familyPrune)
			.addFamily(familyCitrus)
			.setHeight(EnumAllele.Height.SMALLEST)
			.setFertility(EnumAllele.Saplings.LOWEST)
			.setYield(EnumAllele.Yield.AVERAGE)
			.setSappiness(EnumAllele.Sappiness.LOW)
			.setMaturation(EnumAllele.Maturation.FAST);

		ExtraTreeSpecies.FingerLime
			.addFamily(familyPome)
			.addFamily(familyPrune)
			.addFamily(familyCitrus)
			.setHeight(EnumAllele.Height.SMALLEST)
			.setYield(EnumAllele.Yield.HIGH)
			.setSappiness(EnumAllele.Sappiness.LOW)
			.setMaturation(EnumAllele.Maturation.FAST);

		ExtraTreeSpecies.Pomelo
			.addFamily(familyPome)
			.addFamily(familyPrune)
			.addFamily(familyCitrus)
			.setHeight(EnumAllele.Height.SMALLEST)
			.setFertility(EnumAllele.Saplings.LOW)
			.setYield(EnumAllele.Yield.AVERAGE)
			.setSappiness(EnumAllele.Sappiness.LOWER);

		ExtraTreeSpecies.Grapefruit
			.addFamily(familyPome)
			.addFamily(familyPrune)
			.addFamily(familyCitrus)
			.setHeight(EnumAllele.Height.SMALLEST)
			.setFertility(EnumAllele.Saplings.LOW)
			.setYield(EnumAllele.Yield.AVERAGE)
			.setSappiness(EnumAllele.Sappiness.LOW)
			.setMaturation(EnumAllele.Maturation.FAST);

		ExtraTreeSpecies.Kumquat
			.addFamily(familyPome)
			.addFamily(familyPrune)
			.addFamily(familyCitrus)
			.setHeight(EnumAllele.Height.SMALLEST)
			.setYield(EnumAllele.Yield.HIGH)
			.setSappiness(EnumAllele.Sappiness.LOW);

		ExtraTreeSpecies.Citron
			.addFamily(familyPome)
			.addFamily(familyPrune)
			.addFamily(familyCitrus)
			.setHeight(EnumAllele.Height.SMALLEST)
			.setYield(EnumAllele.Yield.HIGH)
			.setSappiness(EnumAllele.Sappiness.AVERAGE);

		ExtraTreeSpecies.BuddhaHand
			.addFamily(familyPome)
			.addFamily(familyPrune)
			.addFamily(familyCitrus)
			.setYield(EnumAllele.Yield.AVERAGE)
			.setSappiness(EnumAllele.Sappiness.LOW);

		ExtraTreeSpecies.Banana
			.addFamily(familyNuts)
			.addFamily(familyJungle)
			.setYield(EnumAllele.Yield.LOW)
			.setSappiness(EnumAllele.Sappiness.LOW)
			.setMaturation(EnumAllele.Maturation.FAST);

		ExtraTreeSpecies.RedBanana
			.addFamily(familyNuts)
			.addFamily(familyJungle)
			.setFertility(EnumAllele.Saplings.LOW)
			.setYield(EnumAllele.Yield.LOWER)
			.setSappiness(EnumAllele.Sappiness.LOWER);

		ExtraTreeSpecies.Plantain
			.addFamily(familyNuts)
			.addFamily(familyJungle)
			.setHeight(EnumAllele.Height.AVERAGE)
			.setYield(EnumAllele.Yield.LOWER);

		ExtraTreeSpecies.Butternut
			.addFamily(familyPome)
			.addFamily(familyPrune)
			.addFamily(familyNuts)
			.addFamily(familyCitrus)
			.setHeight(EnumAllele.Height.SMALLER)
			.setYield(EnumAllele.Yield.LOW);

		ExtraTreeSpecies.Rowan
			.addFamily(familyNuts)
			.addFamily(familyBerry)
			.setHeight(EnumAllele.Height.LARGER)
			.setFertility(EnumAllele.Saplings.LOW)
			.setSappiness(EnumAllele.Sappiness.LOWER);

		ExtraTreeSpecies.Ash
			.addFamily(familyPome)
			.addFamily(familyPrune)
			.addFamily(familyNuts)
			.addFamily(familyCitrus)
			.setFertility(EnumAllele.Saplings.LOW)
			.setSappiness(EnumAllele.Sappiness.LOWER);

		ExtraTreeSpecies.Alder
			.addFamily(familyPome)
			.addFamily(familyPrune)
			.addFamily(familyNuts)
			.setHeight(EnumAllele.Height.AVERAGE)
			.setSappiness(EnumAllele.Sappiness.LOWER);

		ExtraTreeSpecies.Beech
			.addFamily(familyPrune)
			.addFamily(familyNuts)
			.setHeight(EnumAllele.Height.AVERAGE)
			.setYield(EnumAllele.Yield.LOWER);

		ExtraTreeSpecies.CopperBeech
			.addFamily(familyPrune)
			.addFamily(familyNuts)
			.setMaturation(EnumAllele.Maturation.SLOW);

		ExtraTreeSpecies.Aspen
			.addFamily(familyPome)
			.addFamily(familyNuts)
			.setFertility(EnumAllele.Saplings.AVERAGE)
			.setSappiness(EnumAllele.Sappiness.LOWER);

		ExtraTreeSpecies.Hazel
			.addFamily(familyPrune)
			.addFamily(familyNuts)
			.setHeight(EnumAllele.Height.AVERAGE)
			.setFertility(EnumAllele.Saplings.LOW)
			.setYield(EnumAllele.Yield.LOW)
			.setSappiness(EnumAllele.Sappiness.LOWER);

		ExtraTreeSpecies.Sycamore
			.addFamily(familyPrune)
			.addFamily(familyNuts)
			.addFamily(familyCitrus)
			.setFertility(EnumAllele.Saplings.LOWEST)
			.setSappiness(EnumAllele.Sappiness.LOWER);

		ExtraTreeSpecies.Whitebeam
			.addFamily(familyPome)
			.addFamily(familyPrune)
			.setHeight(EnumAllele.Height.SMALLER);

		ExtraTreeSpecies.Hawthorn
			.addFamily(familyPrune)
			.addFamily(familyNuts)
			.setHeight(EnumAllele.Height.AVERAGE);

		ExtraTreeSpecies.Pecan
			.addFamily(familyPome)
			.addFamily(familyPrune)
			.addFamily(familyNuts)
			.addFamily(familyCitrus)
			.setHeight(EnumAllele.Height.LARGE)
			.setSappiness(EnumAllele.Sappiness.LOWER)
			.setMaturation(EnumAllele.Maturation.SLOW);

		ExtraTreeSpecies.Elm
			.addFamily(familyPome)
			.addFamily(familyPrune)
			.setHeight(EnumAllele.Height.SMALLER)
			.setFertility(EnumAllele.Saplings.LOW)
			.setSappiness(EnumAllele.Sappiness.AVERAGE);

		ExtraTreeSpecies.Elder
			.addFamily(familyPrune)
			.addFamily(familyNuts)
			.addFamily(familyCitrus)
			.addFamily(familyBerry)
			.setHeight(EnumAllele.Height.SMALLER)
			.setSappiness(EnumAllele.Sappiness.LOW);

		ExtraTreeSpecies.Holly
			.addFamily(familyPome)
			.addFamily(familyPrune)
			.addFamily(familyCitrus)
			.setHeight(EnumAllele.Height.AVERAGE)
			.setSappiness(EnumAllele.Sappiness.LOW);

		ExtraTreeSpecies.Hornbeam
			.addFamily(familyPrune)
			.addFamily(familyNuts)
			.setHeight(EnumAllele.Height.SMALLER)
			.setFertility(EnumAllele.Saplings.LOW)
			.setYield(EnumAllele.Yield.LOWER)
			.setMaturation(EnumAllele.Maturation.SLOW);

		ExtraTreeSpecies.Sallow
			.addFamily(familyPome)
			.addFamily(familyPrune)
			.addFamily(familyCitrus)
			.setHeight(EnumAllele.Height.LARGE)
			.setFertility(EnumAllele.Saplings.LOW);

		ExtraTreeSpecies.AcornOak
			.addFamily(familyPome)
			.addFamily(familyPrune)
			.addFamily(familyNuts)
			.setHeight(EnumAllele.Height.LARGE)
			.setYield(EnumAllele.Yield.LOW)
			.setSappiness(EnumAllele.Sappiness.LOWER);

		ExtraTreeSpecies.Fir
			.setHeight(EnumAllele.Height.LARGE)
			.setSappiness(EnumAllele.Sappiness.LOWER)
			.setMaturation(EnumAllele.Maturation.SLOW);

		ExtraTreeSpecies.Olive
			.addFamily(familyPrune)
			.addFamily(familyNuts)
			.addFamily(familyCitrus)
			.setYield(EnumAllele.Yield.AVERAGE);

		ExtraTreeSpecies.Cedar
			.setHeight(EnumAllele.Height.SMALLER)
			.setSappiness(EnumAllele.Sappiness.LOWER)
			.setMaturation(EnumAllele.Maturation.SLOWER);

		ExtraTreeSpecies.RedMaple
			.addFamily(familyPome)
			.addFamily(familyPrune)
			.addFamily(familyCitrus)
			.setFertility(EnumAllele.Saplings.AVERAGE)
			.setSappiness(EnumAllele.Sappiness.HIGH);

		ExtraTreeSpecies.BalsamFir
			.setHeight(EnumAllele.Height.AVERAGE)
			.setSappiness(EnumAllele.Sappiness.LOWER)
			.setMaturation(EnumAllele.Maturation.SLOW);

		ExtraTreeSpecies.LoblollyPine
			.setHeight(EnumAllele.Height.SMALLER)
			.setSappiness(EnumAllele.Sappiness.LOWER)
			.setMaturation(EnumAllele.Maturation.SLOW);

		ExtraTreeSpecies.Sweetgum
			.addFamily(familyNuts)
			.setHeight(EnumAllele.Height.AVERAGE)
			.setFertility(EnumAllele.Saplings.HIGH)
			.setYield(EnumAllele.Yield.LOW)
			.setSappiness(EnumAllele.Sappiness.AVERAGE);

		ExtraTreeSpecies.Locust
			.addFamily(familyPrune)
			.addFamily(familyNuts)
			.addFamily(familyCitrus)
			.setHeight(EnumAllele.Height.SMALLEST);

		ExtraTreeSpecies.Pear
			.addFamily(familyPome)
			.addFamily(familyPrune)
			.addFamily(familyCitrus)
			.setHeight(EnumAllele.Height.SMALLER)
			.setFertility(EnumAllele.Saplings.LOW)
			.setYield(EnumAllele.Yield.HIGH)
			.setSappiness(EnumAllele.Sappiness.LOWER)
			.setMaturation(EnumAllele.Maturation.FAST);

		ExtraTreeSpecies.OsangeOsange
			.addFamily(familyPome)
			.addFamily(familyPrune)
			.addFamily(familyCitrus)
			.addFamily(familyJungle)
			.setYield(EnumAllele.Yield.LOWER);

		ExtraTreeSpecies.OldFustic
			.addFamily(familyJungle)
			.setHeight(EnumAllele.Height.SMALLER)
			.setSappiness(EnumAllele.Sappiness.LOWER);

		ExtraTreeSpecies.Brazilwood
			.addFamily(familyJungle)
			.setHeight(EnumAllele.Height.SMALLER)
			.setFertility(EnumAllele.Saplings.LOW)
			.setYield(EnumAllele.Yield.LOWER)
			.setSappiness(EnumAllele.Sappiness.LOWER);

		ExtraTreeSpecies.Logwood
			.addFamily(familyJungle)
			.setHeight(EnumAllele.Height.AVERAGE)
			.setFertility(EnumAllele.Saplings.LOW)
			.setSappiness(EnumAllele.Sappiness.LOWER);

		ExtraTreeSpecies.Rosewood
			.addFamily(familyJungle)
			.setHeight(EnumAllele.Height.AVERAGE)
			.setSappiness(EnumAllele.Sappiness.LOWER);

		ExtraTreeSpecies.Purpleheart
			.addFamily(familyJungle)
			.setHeight(EnumAllele.Height.LARGE)
			.setSappiness(EnumAllele.Sappiness.LOWER);

		ExtraTreeSpecies.Iroko
			.addFamily(familyNuts)
			.addFamily(familyJungle)
			.setHeight(EnumAllele.Height.AVERAGE)
			.setFertility(EnumAllele.Saplings.LOW);

		ExtraTreeSpecies.Gingko
			.addFamily(familyNuts)
			.addFamily(familyJungle)
			.setHeight(EnumAllele.Height.LARGE)
			.setYield(EnumAllele.Yield.LOWER)
			.setSappiness(EnumAllele.Sappiness.LOW);

		ExtraTreeSpecies.Brazilnut
			.addFamily(familyNuts)
			.addFamily(familyJungle)
			.setHeight(EnumAllele.Height.LARGER)
			.setYield(EnumAllele.Yield.LOW)
			.setSappiness(EnumAllele.Sappiness.LOW);

		ExtraTreeSpecies.RoseGum
			.addFamily(familyJungle)
			.setHeight(EnumAllele.Height.LARGEST)
			.setFertility(EnumAllele.Saplings.LOWEST)
			.setYield(EnumAllele.Yield.LOWER)
			.setSappiness(EnumAllele.Sappiness.LOW)
			.setMaturation(EnumAllele.Maturation.SLOWEST);

		ExtraTreeSpecies.SwampGum
			.addFamily(familyJungle)
			.setHeight(EnumAllele.Height.SMALLEST)
			.setFertility(EnumAllele.Saplings.LOWEST)
			.setMaturation(EnumAllele.Maturation.SLOWER);

		ExtraTreeSpecies.Box
			.addFamily(familyPome)
			.addFamily(familyPrune)
			.addFamily(familyNuts)
			.addFamily(familyCitrus)
			.setHeight(EnumAllele.Height.SMALLER)
			.setMaturation(EnumAllele.Maturation.FASTER);

		ExtraTreeSpecies.Clove
			.addFamily(familyNuts)
			.addFamily(familyJungle)
			.setHeight(EnumAllele.Height.SMALLER)
			.setFertility(EnumAllele.Saplings.LOW)
			.setYield(EnumAllele.Yield.HIGH)
			.setSappiness(EnumAllele.Sappiness.LOWER)
			.setMaturation(EnumAllele.Maturation.FAST);

		ExtraTreeSpecies.Coffee
			.addFamily(familyNuts)
			.addFamily(familyJungle)
			.setHeight(EnumAllele.Height.LARGE)
			.setYield(EnumAllele.Yield.AVERAGE)
			.setMaturation(EnumAllele.Maturation.FAST);

		ExtraTreeSpecies.MonkeyPuzzle
			.addFamily(familyNuts)
			.addFamily(familyJungle)
			.setHeight(EnumAllele.Height.AVERAGE)
			.setYield(EnumAllele.Yield.LOWER)
			.setSappiness(EnumAllele.Sappiness.LOW);

		ExtraTreeSpecies.RainbowGum
			.addFamily(familyNuts)
			.addFamily(familyJungle)
			.setFertility(EnumAllele.Saplings.LOW)
			.setYield(EnumAllele.Yield.LOWER);

		ExtraTreeSpecies.PinkIvory
			.addFamily(familyNuts)
			.addFamily(familyJungle)
			.setHeight(EnumAllele.Height.SMALLEST);

		ExtraTreeSpecies.Blackcurrant
			.addFamily(familyPrune)
			.addFamily(familyNuts)
			.addFamily(familyBerry)
			.setHeight(EnumAllele.Height.SMALLEST)
			.setFertility(EnumAllele.Saplings.LOW)
			.setYield(EnumAllele.Yield.AVERAGE)
			.setSappiness(EnumAllele.Sappiness.LOWER)
			.setMaturation(EnumAllele.Maturation.FASTER);

		ExtraTreeSpecies.Redcurrant
			.addFamily(familyPrune)
			.addFamily(familyNuts)
			.addFamily(familyBerry)
			.setHeight(EnumAllele.Height.SMALLEST)
			.setFertility(EnumAllele.Saplings.AVERAGE)
			.setYield(EnumAllele.Yield.AVERAGE)
			.setSappiness(EnumAllele.Sappiness.LOW)
			.setMaturation(EnumAllele.Maturation.FASTER);

		ExtraTreeSpecies.Blackberry
			.addFamily(familyPrune)
			.addFamily(familyNuts)
			.addFamily(familyBerry)
			.setHeight(EnumAllele.Height.SMALLEST)
			.setYield(EnumAllele.Yield.HIGH)
			.setSappiness(EnumAllele.Sappiness.LOWER)
			.setMaturation(EnumAllele.Maturation.FASTER);

		ExtraTreeSpecies.Raspberry
			.addFamily(familyPrune)
			.addFamily(familyNuts)
			.addFamily(familyBerry)
			.setHeight(EnumAllele.Height.SMALLEST)
			.setFertility(EnumAllele.Saplings.LOW)
			.setYield(EnumAllele.Yield.AVERAGE)
			.setSappiness(EnumAllele.Sappiness.LOWER)
			.setMaturation(EnumAllele.Maturation.FASTER);

		ExtraTreeSpecies.Blueberry
			.addFamily(familyPrune)
			.addFamily(familyNuts)
			.addFamily(familyBerry)
			.setHeight(EnumAllele.Height.SMALLEST)
			.setFertility(EnumAllele.Saplings.AVERAGE)
			.setYield(EnumAllele.Yield.AVERAGE)
			.setSappiness(EnumAllele.Sappiness.LOW)
			.setMaturation(EnumAllele.Maturation.FASTER);

		ExtraTreeSpecies.Cranberry
			.addFamily(familyPrune)
			.addFamily(familyNuts)
			.addFamily(familyBerry)
			.setHeight(EnumAllele.Height.SMALLEST)
			.setFertility(EnumAllele.Saplings.AVERAGE)
			.setYield(EnumAllele.Yield.HIGH)
			.setSappiness(EnumAllele.Sappiness.LOW)
			.setMaturation(EnumAllele.Maturation.FASTER);

		ExtraTreeSpecies.Juniper
			.addFamily(familyPrune)
			.addFamily(familyNuts)
			.addFamily(familyBerry)
			.setHeight(EnumAllele.Height.SMALLEST)
			.setFertility(EnumAllele.Saplings.LOW)
			.setYield(EnumAllele.Yield.LOW)
			.setSappiness(EnumAllele.Sappiness.LOW)
			.setMaturation(EnumAllele.Maturation.FASTER);

		ExtraTreeSpecies.Gooseberry
			.addFamily(familyPrune)
			.addFamily(familyNuts)
			.addFamily(familyBerry)
			.setHeight(EnumAllele.Height.SMALLEST)
			.setFertility(EnumAllele.Saplings.HIGH)
			.setYield(EnumAllele.Yield.HIGH)
			.setMaturation(EnumAllele.Maturation.FASTER);

		ExtraTreeSpecies.GoldenRaspberry
			.addFamily(familyPrune)
			.addFamily(familyNuts)
			.addFamily(familyBerry)
			.setHeight(EnumAllele.Height.SMALLER)
			.setFertility(EnumAllele.Saplings.LOW)
			.setYield(EnumAllele.Yield.AVERAGE)
			.setSappiness(EnumAllele.Sappiness.LOWER)
			.setMaturation(EnumAllele.Maturation.FASTEST);

		ExtraTreeSpecies.Cinnamon
			.addFamily(familyNuts)
			.addFamily(familyJungle)
			.setHeight(EnumAllele.Height.AVERAGE)
			.setYield(EnumAllele.Yield.LOWER)
			.setMaturation(EnumAllele.Maturation.FAST);

		ExtraTreeSpecies.Coconut
			.addFamily(familyJungle)
			.setHeight(EnumAllele.Height.LARGER)
			.setFertility(EnumAllele.Saplings.LOW)
			.setYield(EnumAllele.Yield.AVERAGE)
			.setMaturation(EnumAllele.Maturation.FAST);

		ExtraTreeSpecies.Cashew
			.addFamily(familyNuts)
			.addFamily(familyJungle)
			.setYield(EnumAllele.Yield.LOW);

		ExtraTreeSpecies.Avacado
			.addFamily(familyPrune)
			.addFamily(familyCitrus)
			.addFamily(familyJungle)
			.setHeight(EnumAllele.Height.SMALLEST)
			.setYield(EnumAllele.Yield.AVERAGE);

		ExtraTreeSpecies.Nutmeg
			.addFamily(familyNuts)
			.addFamily(familyJungle)
			.setHeight(EnumAllele.Height.SMALLER)
			.setYield(EnumAllele.Yield.HIGH)
			.setSappiness(EnumAllele.Sappiness.LOW);

		ExtraTreeSpecies.Allspice
			.addFamily(familyNuts)
			.addFamily(familyJungle)
			.setHeight(EnumAllele.Height.SMALLEST)
			.setFertility(EnumAllele.Saplings.LOW)
			.setYield(EnumAllele.Yield.HIGH);

		ExtraTreeSpecies.Chilli
			.addFamily(familyNuts)
			.addFamily(familyJungle)
			.setHeight(EnumAllele.Height.SMALLER)
			.setYield(EnumAllele.Yield.HIGHER)
			.setSappiness(EnumAllele.Sappiness.LOWER)
			.setMaturation(EnumAllele.Maturation.FAST);

		ExtraTreeSpecies.StarAnise
			.addFamily(familyNuts)
			.addFamily(familyJungle)
			.setHeight(EnumAllele.Height.AVERAGE)
			.setYield(EnumAllele.Yield.HIGH);

		ExtraTreeSpecies.Mango
			.addFamily(familyPome)
			.addFamily(familyPrune)
			.addFamily(familyCitrus)
			.addFamily(familyJungle)
			.setHeight(EnumAllele.Height.SMALLER)
			.setFertility(EnumAllele.Saplings.LOW)
			.setYield(EnumAllele.Yield.AVERAGE)
			.setSappiness(EnumAllele.Sappiness.LOWER)
			.setMaturation(EnumAllele.Maturation.FAST);

		ExtraTreeSpecies.Starfruit
			.addFamily(familyPrune)
			.addFamily(familyCitrus)
			.addFamily(familyJungle)
			.setYield(EnumAllele.Yield.AVERAGE)
			.setMaturation(EnumAllele.Maturation.FAST);

		ExtraTreeSpecies.Candlenut
			.addFamily(familyNuts)
			.addFamily(familyJungle)
			.setHeight(EnumAllele.Height.SMALLEST)
			.setFertility(EnumAllele.Saplings.LOWEST)
			.setYield(EnumAllele.Yield.LOW)
			.setSappiness(EnumAllele.Sappiness.LOW);

		ExtraTreeSpecies.DwarfHazel
			.addFamily(familyPrune)
			.addFamily(familyNuts)
			.setFertility(EnumAllele.Saplings.AVERAGE)
			.setSappiness(EnumAllele.Sappiness.LOWER)
			.setMaturation(EnumAllele.Maturation.FASTER);

		ExtraTreeSpecies.Yew
			.setHeight(EnumAllele.Height.LARGE)
			.setSappiness(EnumAllele.Sappiness.LOWER);

		ExtraTreeSpecies.Cypress
			.setHeight(EnumAllele.Height.LARGER)
			.setFertility(EnumAllele.Saplings.LOW)
			.setSappiness(EnumAllele.Sappiness.LOWER)
			.setMaturation(EnumAllele.Maturation.SLOW);

		ExtraTreeSpecies.DouglasFir
			.setHeight(EnumAllele.Height.SMALLER)
			.setFertility(EnumAllele.Saplings.LOW)
			.setMaturation(EnumAllele.Maturation.SLOWER);

		ExtraTreeSpecies.Hemlock
			.setHeight(EnumAllele.Height.AVERAGE)
			.setFertility(EnumAllele.Saplings.LOW)
			.setMaturation(EnumAllele.Maturation.SLOWER);
	}

	// TODO unused method?
	static ItemStack getEBXLStack(String name) {
		try {
			Class elements = Class.forName("extrabiomes.lib.Element");
			Method getElementMethod = elements.getMethod("valueOf", String.class);
			Method getItemStack = elements.getMethod("get");
			Object element = getElementMethod.invoke(null, "SAPLING_AUTUMN_YELLOW");
			return (ItemStack) getItemStack.invoke(element);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private ExtraTreeSpecies addFamily(IFruitFamily family) {
		families.add(family);
		return this;
	}

	private void setWorldGen(Class<? extends WorldGenerator> gen) {
		this.gen = gen;
	}

	private void setGirth(int i) {
		template[EnumTreeChromosome.GIRTH.ordinal()] = AlleleManager.alleleRegistry.getAllele("forestry.i" + i + "d");
	}

	public void preInit() {
		template = Binnie.Genetics.getTreeRoot().getDefaultTemplate();
		template[0] = this;
		if (fruit != null) {
			template[EnumTreeChromosome.FRUITS.ordinal()] = fruit;
		}

		template[EnumTreeChromosome.GIRTH.ordinal()] = AlleleManager.alleleRegistry.getAllele("forestry.i" + girth + "d");
		IClassification clas = AlleleManager.alleleRegistry.getClassification("trees." + branch);
		if (clas != null) {
			clas.addMemberSpecies(this);
			branch = clas;
		}
	}

	@Override
	public String getName() {
		return ExtraTrees.proxy.localise("species." + name().toLowerCase() + ".name");
	}

	@Override
	public String getDescription() {
		return ExtraTrees.proxy.localiseOrBlank("species." + name().toLowerCase() + ".desc");
	}

	@Override
	public EnumTemperature getTemperature() {
		return EnumTemperature.NORMAL;
	}

	@Override
	public EnumHumidity getHumidity() {
		return EnumHumidity.NORMAL;
	}

	@Override
	public boolean hasEffect() {
		return false;
	}

	@Override
	public boolean isSecret() {
		return false;
	}

	@Override
	public boolean isCounted() {
		return true;
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

	@Override
	public String getUID() {
		return "extratrees.species." + uid;
	}

	@Override
	public boolean isDominant() {
		return true;
	}

	@Override
	public EnumPlantType getPlantType() {
		return EnumPlantType.Plains;
	}

	@Override
	public ITreeGenerator getGenerator() {
		return new ITreeGenerator() {

			@Override
			public void setLogBlock(ITreeGenome arg0, World arg1, int arg2, int arg3, int arg4, ForgeDirection arg5) {
				if (wood != null)
					wood.placeBlock(arg1, arg2, arg3, arg4);
			}

			@Override
			public void setLeaves(ITreeGenome genome, World world, GameProfile owner, int x, int y, int z, boolean decorative) {
				boolean placed = world.setBlock(x, y, z, PluginArboriculture.blocks.leaves, 0, Constants.FLAG_BLOCK_SYNCH_AND_UPDATE);
				if (!placed) {
					return;
				}

				Block block = world.getBlock(x, y, z);
				if (PluginArboriculture.blocks.leaves != block) {
					world.setBlockToAir(x, y, z);
					return;
				}

				TileLeaves tileLeaves = BlockForestryLeaves.getLeafTile(world, x, y, z);
				if (tileLeaves == null) {
					world.setBlockToAir(x, y, z);
					return;
				}

				tileLeaves.setOwner(owner);
				if (decorative) {
					tileLeaves.setDecorative();
				}

				tileLeaves.setTree(new Tree(genome));
				world.markBlockForUpdate(x, y, z);
			}

			@Override
			public void setLogBlock(World world, int x, int y, int z, ForgeDirection direction) {
				wood.placeBlock(world, x, y, z);
			}

			@Override
			public void setLeaves(World world, GameProfile player, int x, int y, int z, boolean arg5) {

			}

			@Override
			public WorldGenerator getWorldGenerator(ITreeGenData tree) {
				return getGenerator(tree);
			}
		};
	}

	public WorldGenerator getGenerator(ITreeGenData tree) {
		if (gen != null) {
			try {
				return gen.getConstructor(ITreeGenData.class).newInstance(tree);
			} catch (Exception ex) {
				// ignored
			}
		}
		return new WorldGenDefault(tree);
	}

	protected ExtraTreeSpecies setLeafType(LeafType type) {
		leafType = type;
		if (leafType == LeafType.Conifer) {
			saplingType = SaplingType.Conifer;
		}
		if (leafType == LeafType.Jungle) {
			saplingType = SaplingType.Jungle;
		}
		if (leafType == LeafType.Palm) {
			saplingType = SaplingType.Palm;
		}
		return this;
	}

	public IAllele[] getTemplate() {
		return template;
	}

	@Override
	public ArrayList<IFruitFamily> getSuitableFruit() {
		return families;
	}

	public ILogType getLog() {
		return wood;
	}

	public ExtraTreeSpecies setHeight(EnumAllele.Height height) {
		IAllele allele = AlleleManager.alleleRegistry.getAllele(AlleleHelper.getUid(height));
		if (allele != null) {
			template[EnumTreeChromosome.HEIGHT.ordinal()] = allele;
		}
		return this;
	}

	public ExtraTreeSpecies setSappiness(EnumAllele.Sappiness sappiness) {
		IAllele allele = AlleleManager.alleleRegistry.getAllele(AlleleHelper.getUid(sappiness));
		if (allele != null) {
			template[EnumTreeChromosome.SAPPINESS.ordinal()] = allele;
		}
		return this;
	}

	public ExtraTreeSpecies setMaturation(EnumAllele.Maturation maturation) {
		IAllele allele = AlleleManager.alleleRegistry.getAllele(AlleleHelper.getUid(maturation));
		if (allele != null) {
			template[EnumTreeChromosome.MATURATION.ordinal()] = allele;
		}
		return this;
	}

	public ExtraTreeSpecies setYield(EnumAllele.Yield yield) {
		IAllele allele = AlleleManager.alleleRegistry.getAllele(AlleleHelper.getUid(yield));
		if (allele != null) {
			template[EnumTreeChromosome.YIELD.ordinal()] = allele;
		}
		return this;
	}

	public ExtraTreeSpecies setFertility(EnumAllele.Saplings saplings) {
		IAllele allele = AlleleManager.alleleRegistry.getAllele(AlleleHelper.getUid(saplings));
		if (allele != null) {
			template[EnumTreeChromosome.FERTILITY.ordinal()] = allele;
		}
		return this;
	}

	public void finished() {
	}

	public int getLeafColour(ITree tree) {
		return color;
	}

	@Override
	public int getIconColour(int renderPass) {
		return (renderPass == 0) ? color : 0xFFFFFF;// 10451021;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getGermlingIcon(EnumGermlingType type, int renderPass) {
		if (type == EnumGermlingType.POLLEN) {
			return PluginArboriculture.items.pollenFertile.getIcon(PluginArboriculture.items.pollenFertile.getItemStack(), renderPass);
		}
		return (renderPass == 0)
			? saplingType.icon[1]
			: saplingType.icon[0];
	}

	@Override
	public IIconProvider getIconProvider() {
		return this;
	}

	@Override
	public IIcon getIcon(short texUID) {
		return TextureManager.getInstance().getIcon(texUID);
	}

	@Override
	public void registerIcons(IIconRegister register) {
		for (SaplingType type : SaplingType.values()) {
			type.icon = new IIcon[2];
			type.icon[0] = ExtraTrees.proxy.getIcon(register, "saplings/" + type.toString().toLowerCase() + ".trunk");
			type.icon[1] = ExtraTrees.proxy.getIcon(register, "saplings/" + type.toString().toLowerCase() + ".leaves");
		}
	}

	@Override
	public ITreeRoot getRoot() {
		return Binnie.Genetics.getTreeRoot();
	}

	@Override
	public float getResearchSuitability(ItemStack itemstack) {
		if (itemstack == null) {
			return 0.0f;
		}

		if (template[EnumTreeChromosome.FRUITS.ordinal()] instanceof ExtraTreeFruitGene) {
			ExtraTreeFruitGene fruit = (ExtraTreeFruitGene) template[EnumTreeChromosome.FRUITS.ordinal()];
			for (ItemStack stack : fruit.products.keySet()) {
				if (stack.isItemEqual(itemstack)) {
					return 1.0f;
				}
			}
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
		ArrayList<ItemStack> bounty = new ArrayList<>();
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

		if (template[EnumTreeChromosome.FRUITS.ordinal()] instanceof ExtraTreeFruitGene) {
			ExtraTreeFruitGene fruit = (ExtraTreeFruitGene) template[EnumTreeChromosome.FRUITS.ordinal()];
			for (ItemStack stack : fruit.products.keySet()) {
				ItemStack stack2 = stack.copy();
				stack2.stackSize = world.rand.nextInt((int) (bountyLevel / 2.0f)) + 1;
				bounty.add(stack2);
			}
		}
		return bounty.toArray(new ItemStack[0]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getGermlingColour(EnumGermlingType type, int renderPass) {
		if (type == EnumGermlingType.SAPLING) {
			if (renderPass == 0) {
				return getLeafColour(null);
			}
			if (getLog() == null) {
				return 0xffffff;
			}
			return getLog().getColour();
		}
		return getLeafColour(null);
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

	public ItemStack[] getLogStacks() {
		if (wood == null) {
			return new ItemStack[0];
		}
		return new ItemStack[]{wood.getItemStack()};
	}

	@Override
	public String getUnlocalizedName() {
		return "extratrees.species." + toString().toLowerCase() + ".name";
	}

	@Override
	public int getLeafColour(boolean pollinated) {
		return pollinated ? colorPollineted : color;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getLeafIcon(boolean pollinated, boolean fancy) {
		try {
			if (pollinated)
				return TextureLeaves.get(EnumLeafType.valueOf(leafType.descript.toUpperCase())).getPollinated();
			if (fancy)
				return TextureLeaves.get(EnumLeafType.valueOf(leafType.descript.toUpperCase())).getFancy();
			return TextureLeaves.get(EnumLeafType.valueOf(leafType.descript.toUpperCase())).getPlain();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(EnumGermlingType type, int renderPass) {
		return getGermlingIcon(type, renderPass);
	}

	public enum LeafType {
		Normal((short) 10, (short) 11, (short) 12, "Deciduous"),
		Conifer((short) 15, (short) 16, (short) 17, "Conifers"),
		Jungle((short) 20, (short) 21, (short) 22, "Jungle"),
		Willow((short) 25, (short) 26, (short) 27, "Willow"),
		Maple((short) 30, (short) 31, (short) 32, "Maple"),
		Palm((short) 35, (short) 36, (short) 37, "Palm");

		public short fancyUID;
		public short plainUID;
		public short changedUID;
		public String descript;

		LeafType(short fancyUID, short plainUID, short changedUID, String descript) {
			this.fancyUID = fancyUID;
			this.plainUID = plainUID;
			this.changedUID = changedUID;
			this.descript = descript;
		}
	}

	public enum SaplingType {
		Default,
		Jungle,
		Conifer,
		Fruit,
		Poplar,
		Palm,
		Shrub;

		protected IIcon[] icon;
	}
}
