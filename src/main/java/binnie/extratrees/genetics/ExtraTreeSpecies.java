package binnie.extratrees.genetics;

import binnie.Binnie;
import binnie.core.Mods;
import binnie.core.genetics.ForestryAllele;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.EnumExtraTreeLog;
import binnie.extratrees.gen.*;
import com.mojang.authlib.GameProfile;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.arboriculture.*;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.core.IModelManager;
import forestry.api.genetics.*;
import forestry.api.world.ITreeGenData;
import forestry.arboriculture.PluginArboriculture;
import forestry.arboriculture.genetics.Tree;
import forestry.arboriculture.tiles.TileLeaves;
import forestry.core.tiles.TileUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.List;

public class ExtraTreeSpecies implements IAlleleTreeSpecies//, IIconProvider, IGermlingIconProvider
{
    public static final ExtraTreeSpecies OrchardApple = new ExtraTreeSpecies("malus", "domestica", 6588464, 16751859, 8092283, EnumExtraTreeLog.Apple, ExtraTreeFruitGene.Apple, SaplingType.Default, WorldGenApple.OrchardApple.class);
    public static final ExtraTreeSpecies SweetCrabapple = new ExtraTreeSpecies("malus", "coronaria", 8034643, 16528799, 8092283, EnumExtraTreeLog.Apple, ExtraTreeFruitGene.Crabapple, SaplingType.Default, WorldGenApple.SweetCrabapple.class);
    public static final ExtraTreeSpecies FloweringCrabapple = new ExtraTreeSpecies("malus", "hopa", 8034643, 16528799, 8092283, EnumExtraTreeLog.Apple, ExtraTreeFruitGene.Crabapple, SaplingType.Default, WorldGenApple.FloweringCrabapple.class);
    public static final ExtraTreeSpecies PrairieCrabapple = new ExtraTreeSpecies("malus", "ioensis", 8034643, 16528799, 8092283, EnumExtraTreeLog.Apple, ExtraTreeFruitGene.Crabapple, SaplingType.Default, WorldGenApple.PrairieCrabapple.class);
    public static final ExtraTreeSpecies Blackthorn = new ExtraTreeSpecies("prunus", "spinosa ", 7180062, 16746439, 11961953, EnumForestryWoodType.PLUM, ExtraTreeFruitGene.Blackthorn, SaplingType.Fruit, (Class<? extends WorldGenerator>) null);
    public static final ExtraTreeSpecies CherryPlum = new ExtraTreeSpecies("prunus", "cerasifera", 7180062, 16746439, 11961953, EnumForestryWoodType.PLUM, ExtraTreeFruitGene.CherryPlum, SaplingType.Fruit, (Class<? extends WorldGenerator>) null);
    public static final ExtraTreeSpecies Peach = new ExtraTreeSpecies("prunus", "persica", 7180062, 16721562, 11961953, EnumForestryWoodType.PLUM, ExtraTreeFruitGene.Peach, SaplingType.Fruit, (Class<? extends WorldGenerator>) null);
    public static final ExtraTreeSpecies Nectarine = new ExtraTreeSpecies("prunus", "nectarina", 7180062, 16721562, 11961953, EnumForestryWoodType.PLUM, ExtraTreeFruitGene.Nectarine, SaplingType.Fruit, (Class<? extends WorldGenerator>) null);
    public static final ExtraTreeSpecies Apricot = new ExtraTreeSpecies("prunus", "armeniaca", 7180062, 16103640, 11961953, EnumForestryWoodType.PLUM, ExtraTreeFruitGene.Apricot, SaplingType.Fruit, (Class<? extends WorldGenerator>) null);
    public static final ExtraTreeSpecies Almond = new ExtraTreeSpecies("prunus", "amygdalus", 7180062, 16090304, 11961953, EnumForestryWoodType.PLUM, ExtraTreeFruitGene.Almond, SaplingType.Fruit, (Class<? extends WorldGenerator>) null);
    public static final ExtraTreeSpecies WildCherry = new ExtraTreeSpecies("prunus", "avium", 7180062, 16247798, 7432272, EnumExtraTreeLog.Cherry, ExtraTreeFruitGene.WildCherry, SaplingType.Fruit, (Class<? extends WorldGenerator>) null);
    public static final ExtraTreeSpecies SourCherry = new ExtraTreeSpecies("prunus", "cerasus", 7180062, 16247798, 7432272, EnumExtraTreeLog.Cherry, ExtraTreeFruitGene.SourCherry, SaplingType.Fruit, (Class<? extends WorldGenerator>) null);
    public static final ExtraTreeSpecies BlackCherry = new ExtraTreeSpecies("prunus", "serotina", 7180062, 16441848, 7432272, EnumExtraTreeLog.Cherry, ExtraTreeFruitGene.BlackCherry, SaplingType.Fruit, (Class<? extends WorldGenerator>) null);
    public static final ExtraTreeSpecies Orange = new ExtraTreeSpecies("citrus", "sinensis", 8957780, 10729552, 5983033, EnumForestryWoodType.CITRUS, ExtraTreeFruitGene.Orange, SaplingType.Fruit, (Class<? extends WorldGenerator>) null);
    public static final ExtraTreeSpecies Manderin = new ExtraTreeSpecies("citrus", "reticulata", 8957780, 10729552, 5983033, EnumForestryWoodType.CITRUS, ExtraTreeFruitGene.Manderin, SaplingType.Fruit, (Class<? extends WorldGenerator>) null);
    public static final ExtraTreeSpecies Satsuma = new ExtraTreeSpecies("citrus", "unshiu", 8957780, 10729552, 5983033, EnumForestryWoodType.CITRUS, ExtraTreeFruitGene.Satsuma, SaplingType.Fruit, (Class<? extends WorldGenerator>) null);
    public static final ExtraTreeSpecies Tangerine = new ExtraTreeSpecies("citrus", "tangerina", 8957780, 10729552, 5983033, EnumForestryWoodType.CITRUS, ExtraTreeFruitGene.Tangerine, SaplingType.Fruit, (Class<? extends WorldGenerator>) null);
    public static final ExtraTreeSpecies Lime = new ExtraTreeSpecies("citrus", "latifolia", 8957780, 10729552, 5983033, EnumForestryWoodType.CITRUS, ExtraTreeFruitGene.Lime, SaplingType.Fruit, (Class<? extends WorldGenerator>) null);
    public static final ExtraTreeSpecies KeyLime = new ExtraTreeSpecies("citrus", "aurantifolia", 8957780, 10729552, 5983033, EnumForestryWoodType.CITRUS, ExtraTreeFruitGene.KeyLime, SaplingType.Fruit, (Class<? extends WorldGenerator>) null);
    public static final ExtraTreeSpecies FingerLime = new ExtraTreeSpecies("citrus", "australasica", 8957780, 10729552, 5983033, EnumForestryWoodType.CITRUS, ExtraTreeFruitGene.FingerLime, SaplingType.Fruit, (Class<? extends WorldGenerator>) null);
    public static final ExtraTreeSpecies Pomelo = new ExtraTreeSpecies("citrus", "maxima", 8957780, 10729552, 5983033, EnumForestryWoodType.CITRUS, ExtraTreeFruitGene.Pomelo, SaplingType.Fruit, (Class<? extends WorldGenerator>) null);
    public static final ExtraTreeSpecies Grapefruit = new ExtraTreeSpecies("citrus", "paradisi", 8957780, 10729552, 5983033, EnumForestryWoodType.CITRUS, ExtraTreeFruitGene.Grapefruit, SaplingType.Fruit, (Class<? extends WorldGenerator>) null);
    public static final ExtraTreeSpecies Kumquat = new ExtraTreeSpecies("citrus", "margarita", 8957780, 10729552, 5983033, EnumForestryWoodType.CITRUS, ExtraTreeFruitGene.Kumquat, SaplingType.Fruit, (Class<? extends WorldGenerator>) null);
    public static final ExtraTreeSpecies Citron = new ExtraTreeSpecies("citrus", "medica", 8957780, 10729552, 5983033, EnumForestryWoodType.CITRUS, ExtraTreeFruitGene.Citron, SaplingType.Fruit, (Class<? extends WorldGenerator>) null);
    public static final ExtraTreeSpecies BuddhaHand = new ExtraTreeSpecies("citrus", "sarcodactylus", 8957780, 10729552, 5983033, EnumForestryWoodType.CITRUS, ExtraTreeFruitGene.BuddhaHand, SaplingType.Fruit, (Class<? extends WorldGenerator>) null);
    public static final ExtraTreeSpecies Banana = new ExtraTreeSpecies("musa", "sinensis", 10603918, 4515072, 8753743, EnumExtraTreeLog.Banana, ExtraTreeFruitGene.Banana, SaplingType.Default, WorldGenBanana.class);
    public static final ExtraTreeSpecies RedBanana = new ExtraTreeSpecies("musa", "rubra", 10603918, 4515072, 8753743, EnumExtraTreeLog.Banana, ExtraTreeFruitGene.RedBanana, SaplingType.Default, WorldGenBanana.class);
    public static final ExtraTreeSpecies Plantain = new ExtraTreeSpecies("musa", "paradisiaca", 10603918, 4515072, 8753743, EnumExtraTreeLog.Banana, ExtraTreeFruitGene.Plantain, SaplingType.Default, WorldGenBanana.class);
    public static final ExtraTreeSpecies Butternut = new ExtraTreeSpecies("juglans", "cinerea", 8566156, 8576396, 12037536, EnumExtraTreeLog.Butternut, ExtraTreeFruitGene.Butternut, SaplingType.Default, WorldGenWalnut.Butternut.class);
    public static final ExtraTreeSpecies Rowan = new ExtraTreeSpecies("sorbus", "aucuparia", 10405787, 10414258, 11972763, EnumExtraTreeLog.Rowan, (IAlleleFruit) null, SaplingType.Default, WorldGenSorbus.Rowan.class);
    public static final ExtraTreeSpecies Hemlock = new ExtraTreeSpecies("tsuga", "heterophylla", 6073458, 6082930, 11379611, EnumExtraTreeLog.Hemlock, (IAlleleFruit) null, SaplingType.Default, WorldGenConifer.WesternHemlock.class);
    public static final ExtraTreeSpecies Ash = new ExtraTreeSpecies("fraxinus", "excelsior", 4754987, 4777003, 9013634, EnumExtraTreeLog.Ash, (IAlleleFruit) null, SaplingType.Default, WorldGenAsh.CommonAsh.class);
    public static final ExtraTreeSpecies Alder = new ExtraTreeSpecies("alnus", "glutinosa", 6916659, 6925875, 13025464, EnumExtraTreeLog.Alder, (IAlleleFruit) null, SaplingType.Default, WorldGenAlder.CommonAlder.class);
    public static final ExtraTreeSpecies Beech = new ExtraTreeSpecies("fagus", "sylvatica", 8626252, 8635980, 11702654, EnumExtraTreeLog.Beech, ExtraTreeFruitGene.Beechnut, SaplingType.Default, WorldGenBeech.CommonBeech.class);
    public static final ExtraTreeSpecies CopperBeech = new ExtraTreeSpecies("fagus", "purpurea", 8393496, 13720397, 11702654, EnumExtraTreeLog.Beech, ExtraTreeFruitGene.Beechnut, SaplingType.Default, WorldGenBeech.CopperBeech.class);
    public static final ExtraTreeSpecies Aspen = new ExtraTreeSpecies("populus", "tremula", 9096247, 9101711, 9217671, EnumForestryWoodType.POPLAR, (IAlleleFruit) null, SaplingType.Default, WorldGenPoplar.Aspen.class);
    public static final ExtraTreeSpecies Yew = new ExtraTreeSpecies("taxus", "baccata", 9734733, 9743949, 13745089, EnumExtraTreeLog.Yew, (IAlleleFruit) null, SaplingType.Default, WorldGenConifer.Yew.class);
    public static final ExtraTreeSpecies Cypress = new ExtraTreeSpecies("chamaecyparis", "lawsoniana", 9030055, 9035206, 10126467, EnumExtraTreeLog.Cypress, (IAlleleFruit) null, SaplingType.Poplar, WorldGenConifer.Cypress.class);
    public static final ExtraTreeSpecies DouglasFir = new ExtraTreeSpecies("pseudotsuga", "menziesii", 10073474, 10080682, 8553346, EnumExtraTreeLog.Fir, (IAlleleFruit) null, SaplingType.Default, WorldGenFir.DouglasFir.class);
    public static final ExtraTreeSpecies Hazel = new ExtraTreeSpecies("Corylus", "avellana", 10204498, 10215762, 11180143, EnumExtraTreeLog.Hazel, ExtraTreeFruitGene.Hazelnut, SaplingType.Default, WorldGenTree3.Hazel.class);
    public static final ExtraTreeSpecies Sycamore = new ExtraTreeSpecies("ficus", "sycomorus", 10528047, 11851100, 8418135, EnumExtraTreeLog.Fig, ExtraTreeFruitGene.Fig, SaplingType.Default, WorldGenTree3.Sycamore.class);
    public static final ExtraTreeSpecies Whitebeam = new ExtraTreeSpecies("sorbus", "aria", 12242585, 7505471, 7891565, EnumExtraTreeLog.Whitebeam, (IAlleleFruit) null, SaplingType.Default, WorldGenSorbus.Whitebeam.class);
    public static final ExtraTreeSpecies Hawthorn = new ExtraTreeSpecies("crataegus", "monogyna", 7055434, 10008443, 6248261, EnumExtraTreeLog.Hawthorn, (IAlleleFruit) null, SaplingType.Default, WorldGenTree3.Hawthorn.class);
    public static final ExtraTreeSpecies Pecan = new ExtraTreeSpecies("carya", "illinoinensis", 8762996, 2906139, 4076848, EnumExtraTreeLog.Hickory, ExtraTreeFruitGene.Pecan, SaplingType.Default, WorldGenTree3.Pecan.class);
    public static final ExtraTreeSpecies Elm = new ExtraTreeSpecies("ulmus", "procera", 8163400, 8175176, 8684422, EnumExtraTreeLog.Elm, (IAlleleFruit) null, SaplingType.Default, WorldGenTree3.Elm.class);
    public static final ExtraTreeSpecies Elder = new ExtraTreeSpecies("sambucus", "nigra", 11450483, 14739389, 14202996, EnumExtraTreeLog.Elder, ExtraTreeFruitGene.Elderberry, SaplingType.Default, WorldGenTree3.Elder.class);
    public static final ExtraTreeSpecies Holly = new ExtraTreeSpecies("ilex", "aquifolium", 2444108, 7246468, 11905669, EnumExtraTreeLog.Holly, (IAlleleFruit) null, SaplingType.Default, WorldGenHolly.Holly.class);
    public static final ExtraTreeSpecies Hornbeam = new ExtraTreeSpecies("carpinus", "betulus", 9873179, 9887003, 10719862, EnumExtraTreeLog.Hornbeam, (IAlleleFruit) null, SaplingType.Default, WorldGenTree3.Hornbeam.class);
    public static final ExtraTreeSpecies Sallow = new ExtraTreeSpecies("salix", "caprea", 11449123, 12053541, 10590869, EnumForestryWoodType.WILLOW, (IAlleleFruit) null, SaplingType.Default, WorldGenTree3.Sallow.class);
    public static final ExtraTreeSpecies AcornOak = new ExtraTreeSpecies("quercus", "robur", 6714174, 10396209, 6376752, EnumVanillaWoodType.OAK, ExtraTreeFruitGene.Acorn, SaplingType.Default, WorldGenTree3.AcornOak.class);
    public static final ExtraTreeSpecies Fir = new ExtraTreeSpecies("abies", "alba", 7306272, 7328032, 8553346, EnumExtraTreeLog.Fir, (IAlleleFruit) null, SaplingType.Default, WorldGenFir.SilverFir.class);
    public static final ExtraTreeSpecies Cedar = new ExtraTreeSpecies("cedrus", "libani", 9806704, 9824368, 11368015, EnumExtraTreeLog.Cedar, (IAlleleFruit) null, SaplingType.Default, WorldGenConifer.Cedar.class);
    public static final ExtraTreeSpecies Olive = new ExtraTreeSpecies("olea", "europaea", 3950644, 3950644, 8089706, EnumExtraTreeLog.Olive, ExtraTreeFruitGene.Olive, SaplingType.Default, WorldGenTree2.Olive.class);
    public static final ExtraTreeSpecies RedMaple = new ExtraTreeSpecies("acer", "ubrum", 15216151, 15216151, 9078657, EnumForestryWoodType.MAPLE, (IAlleleFruit) null, SaplingType.Default, WorldGenMaple.RedMaple.class);
    public static final ExtraTreeSpecies BalsamFir = new ExtraTreeSpecies("abies", "balsamea", 7643260, 7643260, 8553346, EnumExtraTreeLog.Fir, (IAlleleFruit) null, SaplingType.Default, WorldGenFir.BalsamFir.class);
    public static final ExtraTreeSpecies LoblollyPine = new ExtraTreeSpecies("pinus", "taeda", 7309895, 7309895, 7558729, EnumForestryWoodType.PINE, (IAlleleFruit) null, SaplingType.Default, WorldGenConifer.LoblollyPine.class);
    public static final ExtraTreeSpecies Sweetgum = new ExtraTreeSpecies("liquidambar", "styraciflua", 9144162, 9144162, 10592668, EnumExtraTreeLog.Sweetgum, (IAlleleFruit) null, SaplingType.Default, WorldGenTree2.Sweetgum.class);
    public static final ExtraTreeSpecies Locust = new ExtraTreeSpecies("robinia", "pseudoacacia", 8942336, 8942336, 11381948, EnumExtraTreeLog.Locust, (IAlleleFruit) null, SaplingType.Default, WorldGenTree2.Locust.class);
    public static final ExtraTreeSpecies Pear = new ExtraTreeSpecies("pyrus", "communis", 6195238, 6195238, 11048825, EnumExtraTreeLog.Pear, ExtraTreeFruitGene.Pear, SaplingType.Default, WorldGenTree2.Pear.class);
    public static final ExtraTreeSpecies OsangeOsange = new ExtraTreeSpecies("maclura", "pomifera", 6847056, 6847056, 9131828, EnumExtraTreeLog.Maclura, ExtraTreeFruitGene.OsangeOsange, SaplingType.Default, WorldGenJungle.OsangeOsange.class);
    public static final ExtraTreeSpecies OldFustic = new ExtraTreeSpecies("maclura", "tinctoria", 6847056, 6847056, 9131828, EnumExtraTreeLog.Maclura, (IAlleleFruit) null, SaplingType.Default, WorldGenJungle.OldFustic.class);
    public static final ExtraTreeSpecies Brazilwood = new ExtraTreeSpecies("caesalpinia", "echinata", 6321241, 6321241, 10387560, EnumExtraTreeLog.Brazilwood, (IAlleleFruit) null, SaplingType.Default, WorldGenJungle.Brazilwood.class);
    public static final ExtraTreeSpecies Logwood = new ExtraTreeSpecies("haematoxylum", "campechianum", 8953707, 8953707, 16376530, EnumExtraTreeLog.Logwood, (IAlleleFruit) null, SaplingType.Default, WorldGenJungle.Logwood.class);
    public static final ExtraTreeSpecies Rosewood = new ExtraTreeSpecies("dalbergia", "latifolia", 8887074, 8887074, 10061414, EnumExtraTreeLog.Rosewood, (IAlleleFruit) null, SaplingType.Default, WorldGenJungle.Rosewood.class);
    public static final ExtraTreeSpecies Purpleheart = new ExtraTreeSpecies("peltogyne", "spp", 7835477, 7835477, 9671330, EnumExtraTreeLog.Purpleheart, (IAlleleFruit) null, SaplingType.Default, WorldGenJungle.Purpleheart.class);
    public static final ExtraTreeSpecies Iroko = new ExtraTreeSpecies("milicia", "excelsa", 11520108, 11520108, 6315099, EnumExtraTreeLog.Iroko, (IAlleleFruit) null, SaplingType.Default, WorldGenTree2.Iroko.class);
    public static final ExtraTreeSpecies Gingko = new ExtraTreeSpecies("ginkgo", "biloba", 7444049, 7444049, 11382428, EnumExtraTreeLog.Gingko, ExtraTreeFruitGene.GingkoNut, SaplingType.Default, WorldGenTree2.Gingko.class);
    public static final ExtraTreeSpecies Brazilnut = new ExtraTreeSpecies("bertholletia", "excelsa", 8163195, 8163195, 5456154, EnumVanillaWoodType.JUNGLE, ExtraTreeFruitGene.BrazilNut, SaplingType.Default, WorldGenJungle.BrazilNut.class);
    public static final ExtraTreeSpecies RoseGum = new ExtraTreeSpecies("eucalyptus", "grandis", 10265176, 10265176, 15392474, EnumExtraTreeLog.Eucalyptus, (IAlleleFruit) null, SaplingType.Default, WorldGenEucalyptus.RoseGum.class);
    public static final ExtraTreeSpecies SwampGum = new ExtraTreeSpecies("eucalyptus", "grandis", 10667654, 10667654, 8814181, EnumExtraTreeLog.Eucalyptus2, (IAlleleFruit) null, SaplingType.Default, WorldGenEucalyptus.SwampGum.class);
    public static final ExtraTreeSpecies Box = new ExtraTreeSpecies("boxus", "sempervirens", 7510381, 7510381, 11235159, EnumExtraTreeLog.Box, (IAlleleFruit) null, SaplingType.Default, WorldGenTree2.Box.class);
    public static final ExtraTreeSpecies Clove = new ExtraTreeSpecies("syzygium", "aromaticum", 8028703, 8028703, 11235159, EnumExtraTreeLog.Syzgium, ExtraTreeFruitGene.Clove, SaplingType.Default, WorldGenTree2.Clove.class);
    public static final ExtraTreeSpecies Coffee = new ExtraTreeSpecies("coffea", "arabica", 7311461, 7311461, 5456154, EnumVanillaWoodType.JUNGLE, ExtraTreeFruitGene.Coffee, SaplingType.Default, WorldGenJungle.Coffee.class);
    public static final ExtraTreeSpecies MonkeyPuzzle = new ExtraTreeSpecies("araucaria", "araucana", 5726552, 5726552, 7558729, EnumForestryWoodType.PINE, (IAlleleFruit) null, SaplingType.Default, WorldGenConifer.MonkeyPuzzle.class);
    public static final ExtraTreeSpecies RainbowGum = new ExtraTreeSpecies("eucalyptus", "deglupta", 12054565, 12054565, 7123007, EnumExtraTreeLog.Eucalyptus3, (IAlleleFruit) null, SaplingType.Default, WorldGenEucalyptus.RainbowGum.class);
    public static final ExtraTreeSpecies PinkIvory = new ExtraTreeSpecies("berchemia", "zeyheri", 8163673, 8163673, 8349012, EnumExtraTreeLog.PinkIvory, (IAlleleFruit) null, SaplingType.Default, WorldGenTree.class);
    public static final ExtraTreeSpecies Blackcurrant = new ExtraTreeSpecies("ribes", "nigrum", 10934876, 10934876, 16777215, EnumExtraTreeLog.EMPTY, ExtraTreeFruitGene.Blackcurrant, SaplingType.Shrub, WorldGenShrub.Shrub.class);
    public static final ExtraTreeSpecies Redcurrant = new ExtraTreeSpecies("ribes", "rubrum", 7646208, 7646208, 16777215, EnumExtraTreeLog.EMPTY, ExtraTreeFruitGene.Redcurrant, SaplingType.Shrub, WorldGenShrub.Shrub.class);
    public static final ExtraTreeSpecies Blackberry = new ExtraTreeSpecies("rubus", "fruticosus", 9617755, 9617755, 16777215, EnumExtraTreeLog.EMPTY, ExtraTreeFruitGene.Blackberry, SaplingType.Shrub, WorldGenShrub.Shrub.class);
    public static final ExtraTreeSpecies Raspberry = new ExtraTreeSpecies("rubus", "idaeus", 8632686, 8632686, 16777215, EnumExtraTreeLog.EMPTY, ExtraTreeFruitGene.Raspberry, SaplingType.Shrub, WorldGenShrub.Shrub.class);
    public static final ExtraTreeSpecies Blueberry = new ExtraTreeSpecies("vaccinium", "corymbosum", 7522128, 7522128, 16777215, EnumExtraTreeLog.EMPTY, ExtraTreeFruitGene.Blueberry, SaplingType.Shrub, WorldGenShrub.Shrub.class);
    public static final ExtraTreeSpecies Cranberry = new ExtraTreeSpecies("vaccinium", "oxycoccos", 9884025, 9884025, 16777215, EnumExtraTreeLog.EMPTY, ExtraTreeFruitGene.Cranberry, SaplingType.Shrub, WorldGenShrub.Shrub.class);
    public static final ExtraTreeSpecies Juniper = new ExtraTreeSpecies("juniperus", "communis", 9482569, 9482569, 16777215, EnumExtraTreeLog.EMPTY, ExtraTreeFruitGene.Juniper, SaplingType.Shrub, WorldGenShrub.Shrub.class);
    public static final ExtraTreeSpecies Gooseberry = new ExtraTreeSpecies("ribes", "grossularia", 7977728, 7977728, 16777215, EnumExtraTreeLog.EMPTY, ExtraTreeFruitGene.Gooseberry, SaplingType.Shrub, WorldGenShrub.Shrub.class);
    public static final ExtraTreeSpecies GoldenRaspberry = new ExtraTreeSpecies("rubus", "occidentalis", 8632686, 8632686, 16777215, EnumExtraTreeLog.EMPTY, ExtraTreeFruitGene.GoldenRaspberry, SaplingType.Shrub, WorldGenShrub.Shrub.class);
    public static final ExtraTreeSpecies Cinnamon = new ExtraTreeSpecies("cinnamomum", "cassia", 7573003, 7573003, 8804412, EnumExtraTreeLog.Cinnamon, (IAlleleFruit) null, SaplingType.Default, WorldGenLazy.Tree.class);
    public static final ExtraTreeSpecies Coconut = new ExtraTreeSpecies("cocous", "nucifera", 6592803, 6592803, 5456154, EnumVanillaWoodType.JUNGLE, ExtraTreeFruitGene.Coconut, SaplingType.Default, WorldGenPalm.Coconut.class);
    public static final ExtraTreeSpecies Cashew = new ExtraTreeSpecies("anacardium", "occidentale", 11254114, 11254114, 5456154, EnumVanillaWoodType.JUNGLE, ExtraTreeFruitGene.Cashew, SaplingType.Default, WorldGenLazy.Tree.class);
    public static final ExtraTreeSpecies Avacado = new ExtraTreeSpecies("persea", "americana", 9872245, 9872245, 5456154, EnumVanillaWoodType.JUNGLE, ExtraTreeFruitGene.Avacado, SaplingType.Default, WorldGenLazy.Tree.class);
    public static final ExtraTreeSpecies Nutmeg = new ExtraTreeSpecies("myristica", "fragrans", 4754764, 4754764, 5456154, EnumVanillaWoodType.JUNGLE, ExtraTreeFruitGene.Nutmeg, SaplingType.Default, WorldGenLazy.Tree.class);
    public static final ExtraTreeSpecies Allspice = new ExtraTreeSpecies("pimenta", "dioica", 8165156, 8165156, 5456154, EnumVanillaWoodType.JUNGLE, ExtraTreeFruitGene.Allspice, SaplingType.Default, WorldGenLazy.Tree.class);
    public static final ExtraTreeSpecies Chilli = new ExtraTreeSpecies("capsicum", "annuum", 2793217, 2793217, 5456154, EnumVanillaWoodType.JUNGLE, ExtraTreeFruitGene.Chilli, SaplingType.Default, WorldGenLazy.Tree.class);
    public static final ExtraTreeSpecies StarAnise = new ExtraTreeSpecies("illicium", "verum", 8373257, 8373257, 5456154, EnumVanillaWoodType.JUNGLE, ExtraTreeFruitGene.StarAnise, SaplingType.Default, WorldGenLazy.Tree.class);
    public static final ExtraTreeSpecies Mango = new ExtraTreeSpecies("mangifera", "indica", 8893812, 8893812, 5456154, EnumVanillaWoodType.JUNGLE, ExtraTreeFruitGene.Mango, SaplingType.Default, WorldGenTropical.Mango.class);
    public static final ExtraTreeSpecies Starfruit = new ExtraTreeSpecies("averrhoa", "carambola", 7186733, 7186733, 5456154, EnumVanillaWoodType.JUNGLE, ExtraTreeFruitGene.Starfruit, SaplingType.Default, WorldGenLazy.Tree.class);
    public static final ExtraTreeSpecies Candlenut = new ExtraTreeSpecies("aleurites", "moluccana", 9085804, 9085804, 5456154, EnumVanillaWoodType.JUNGLE, ExtraTreeFruitGene.Candlenut, SaplingType.Default, WorldGenLazy.Tree.class);
    public static final ExtraTreeSpecies DwarfHazel = new ExtraTreeSpecies("Corylus", "americana", 10204498, 10215762, 11180143, EnumExtraTreeLog.Hazel, ExtraTreeFruitGene.Hazelnut, SaplingType.Shrub, WorldGenShrub.Shrub.class);

    private LeafType leafType;
    private SaplingType saplingType;
    ArrayList<IFruitFamily> families;
    int girth;
    Class<? extends WorldGenerator> gen;
    IAlleleFruit fruit;
    IAllele[] template;
    int color;
    String binomial;
    //String uid;
    IWoodType wood;
    IWoodProvider woodProvider;
    String branchName;
    IClassification branch;

    private static LinkedList<ExtraTreeSpecies> list;
    private static HashMap<ExtraTreeSpecies, String> namesMap;

    public static List<ExtraTreeSpecies> values() {
        if (list == null) {
            list = new LinkedList<>();
            for (Field f : ExtraTreeSpecies.class.getFields()) {
                if (f.getType() == ExtraTreeSpecies.class) {
                    try {
                        list.add((ExtraTreeSpecies) f.get(ExtraTreeSpecies.AcornOak));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return list;
    }

    public String getSpeciesName() {
        if (namesMap == null) {
            namesMap = new HashMap<>();
            for (Field f : ExtraTreeSpecies.class.getFields()) {
                if (f.getType() == ExtraTreeSpecies.class) {
                    try {
                        namesMap.put((ExtraTreeSpecies) f.get(ExtraTreeSpecies.AcornOak), f.getName());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        String value = namesMap.get(this);
        if (value == null || value.isEmpty()) {
            throw new RuntimeException("Name not found for:" + value);
        }
        return namesMap.get(this);
    }

    public static void init() {
        final String bookArborist = "Arborist Manual";
        for (final ExtraTreeSpecies species : values()) {
            species.preInit();
        }
        ExtraTreeSpecies.OrchardApple.finished();
        ExtraTreeSpecies.SweetCrabapple.finished();
        ExtraTreeSpecies.FloweringCrabapple.finished();
        ExtraTreeSpecies.PrairieCrabapple.finished();
        final ExtraTreeSpecies[] arr$2;
        final ExtraTreeSpecies[] pruneSpecies = arr$2 = new ExtraTreeSpecies[]{
                ExtraTreeSpecies.Blackthorn, ExtraTreeSpecies.CherryPlum, ExtraTreeSpecies.Almond, ExtraTreeSpecies.Apricot,
                ExtraTreeSpecies.Peach, ExtraTreeSpecies.Nectarine, ExtraTreeSpecies.WildCherry, ExtraTreeSpecies.SourCherry,
                ExtraTreeSpecies.BlackCherry};
        for (final ExtraTreeSpecies species2 : arr$2) {
            final IAlleleTreeSpecies citrus = (IAlleleTreeSpecies) AlleleManager.alleleRegistry.getAllele("forestry.treePlum");
            species2.setWorldGen(citrus.getGenerator().getWorldGenerator(TreeManager.treeRoot.templateAsIndividual(citrus.getRoot().getDefaultTemplate())).getClass());
            species2.saplingType = SaplingType.Fruit;
            species2.finished();
        }
        ExtraTreeSpecies[] arr$3;
        final ExtraTreeSpecies[] citrusSpecies = arr$3 = new ExtraTreeSpecies[]{
                ExtraTreeSpecies.Orange, ExtraTreeSpecies.Manderin, ExtraTreeSpecies.Satsuma, ExtraTreeSpecies.Tangerine,
                ExtraTreeSpecies.Lime, ExtraTreeSpecies.KeyLime, ExtraTreeSpecies.FingerLime, ExtraTreeSpecies.Pomelo,
                ExtraTreeSpecies.Grapefruit, ExtraTreeSpecies.Kumquat, ExtraTreeSpecies.Citron, ExtraTreeSpecies.BuddhaHand};
        for (final ExtraTreeSpecies species3 : arr$3) {
            species3.setLeafType(LeafType.Jungle);
            species3.saplingType = SaplingType.Fruit;
            final IAlleleTreeSpecies citrus2 = (IAlleleTreeSpecies) AlleleManager.alleleRegistry.getAllele("forestry.treeLemon");
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
        for (final ExtraTreeSpecies species3 : values()) {
            final String scientific = species3.branchName.substring(0, 1).toUpperCase() + species3.branchName.substring(1).toLowerCase();
            final String uid = "trees." + species3.branchName.toLowerCase();
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
        final IFruitFamily familyPrune = AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes");
        final IFruitFamily familyPome = AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes");
        final IFruitFamily familyJungle = AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle");
        final IFruitFamily familyNuts = AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts");
        final IFruitFamily familyBerry = ExtraTreeFruitFamily.Berry;
        final IFruitFamily familyCitrus = ExtraTreeFruitFamily.Citrus;
        ExtraTreeSpecies.OrchardApple.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.SweetCrabapple.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.FloweringCrabapple.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.PrairieCrabapple.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.Blackthorn.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.CherryPlum.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.Peach.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.Nectarine.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.Apricot.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.Almond.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.WildCherry.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.SourCherry.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.BlackCherry.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.Orange.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.Manderin.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.Satsuma.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.Tangerine.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.Lime.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.KeyLime.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.FingerLime.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.Pomelo.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.Grapefruit.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.Kumquat.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.Citron.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.BuddhaHand.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.Banana.addFamily(familyNuts).addFamily(familyJungle);
        ExtraTreeSpecies.RedBanana.addFamily(familyNuts).addFamily(familyJungle);
        ExtraTreeSpecies.Plantain.addFamily(familyNuts).addFamily(familyJungle);
        ExtraTreeSpecies.Butternut.addFamily(familyPome).addFamily(familyPrune).addFamily(familyNuts).addFamily(familyCitrus);
        ExtraTreeSpecies.Rowan.addFamily(familyNuts).addFamily(familyBerry);
        ExtraTreeSpecies.Ash.addFamily(familyPome).addFamily(familyPrune).addFamily(familyNuts).addFamily(familyCitrus);
        ExtraTreeSpecies.Alder.addFamily(familyPome).addFamily(familyPrune).addFamily(familyNuts);
        ExtraTreeSpecies.Beech.addFamily(familyPrune).addFamily(familyNuts);
        ExtraTreeSpecies.CopperBeech.addFamily(familyPrune).addFamily(familyNuts);
        ExtraTreeSpecies.Aspen.addFamily(familyPome).addFamily(familyNuts);
        ExtraTreeSpecies.Hazel.addFamily(familyPrune).addFamily(familyNuts);
        ExtraTreeSpecies.Sycamore.addFamily(familyPrune).addFamily(familyNuts).addFamily(familyCitrus);
        ExtraTreeSpecies.Whitebeam.addFamily(familyPome).addFamily(familyPrune);
        ExtraTreeSpecies.Hawthorn.addFamily(familyPrune).addFamily(familyNuts);
        ExtraTreeSpecies.Pecan.addFamily(familyPome).addFamily(familyPrune).addFamily(familyNuts).addFamily(familyCitrus);
        ExtraTreeSpecies.Elm.addFamily(familyPome).addFamily(familyPrune);
        ExtraTreeSpecies.Elder.addFamily(familyPrune).addFamily(familyNuts).addFamily(familyCitrus).addFamily(familyBerry);
        ExtraTreeSpecies.Holly.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.Hornbeam.addFamily(familyPrune).addFamily(familyNuts);
        ExtraTreeSpecies.Sallow.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.AcornOak.addFamily(familyPome).addFamily(familyPrune).addFamily(familyNuts);
        ExtraTreeSpecies.Olive.addFamily(familyPrune).addFamily(familyNuts).addFamily(familyCitrus);
        ExtraTreeSpecies.RedMaple.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.Sweetgum.addFamily(familyNuts);
        ExtraTreeSpecies.Locust.addFamily(familyPrune).addFamily(familyNuts).addFamily(familyCitrus);
        ExtraTreeSpecies.Pear.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.OsangeOsange.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus).addFamily(familyJungle);
        ExtraTreeSpecies.OldFustic.addFamily(familyJungle);
        ExtraTreeSpecies.Brazilwood.addFamily(familyJungle);
        ExtraTreeSpecies.Logwood.addFamily(familyJungle);
        ExtraTreeSpecies.Rosewood.addFamily(familyJungle);
        ExtraTreeSpecies.Purpleheart.addFamily(familyJungle);
        ExtraTreeSpecies.Iroko.addFamily(familyNuts).addFamily(familyJungle);
        ExtraTreeSpecies.Gingko.addFamily(familyNuts).addFamily(familyJungle);
        ExtraTreeSpecies.Brazilnut.addFamily(familyNuts).addFamily(familyJungle);
        ExtraTreeSpecies.RoseGum.addFamily(familyJungle);
        ExtraTreeSpecies.SwampGum.addFamily(familyJungle);
        ExtraTreeSpecies.Box.addFamily(familyPome).addFamily(familyPrune).addFamily(familyNuts).addFamily(familyCitrus);
        ExtraTreeSpecies.Clove.addFamily(familyNuts).addFamily(familyJungle);
        ExtraTreeSpecies.Coffee.addFamily(familyNuts).addFamily(familyJungle);
        ExtraTreeSpecies.MonkeyPuzzle.addFamily(familyNuts).addFamily(familyJungle);
        ExtraTreeSpecies.RainbowGum.addFamily(familyNuts).addFamily(familyJungle);
        ExtraTreeSpecies.PinkIvory.addFamily(familyNuts).addFamily(familyJungle);
        ExtraTreeSpecies.Blackcurrant.addFamily(familyPrune).addFamily(familyNuts).addFamily(familyBerry);
        ExtraTreeSpecies.Redcurrant.addFamily(familyPrune).addFamily(familyNuts).addFamily(familyBerry);
        ExtraTreeSpecies.Blackberry.addFamily(familyPrune).addFamily(familyNuts).addFamily(familyBerry);
        ExtraTreeSpecies.Raspberry.addFamily(familyPrune).addFamily(familyNuts).addFamily(familyBerry);
        ExtraTreeSpecies.Blueberry.addFamily(familyPrune).addFamily(familyNuts).addFamily(familyBerry);
        ExtraTreeSpecies.Cranberry.addFamily(familyPrune).addFamily(familyNuts).addFamily(familyBerry);
        ExtraTreeSpecies.Juniper.addFamily(familyPrune).addFamily(familyNuts).addFamily(familyBerry);
        ExtraTreeSpecies.Gooseberry.addFamily(familyPrune).addFamily(familyNuts).addFamily(familyBerry);
        ExtraTreeSpecies.GoldenRaspberry.addFamily(familyPrune).addFamily(familyNuts).addFamily(familyBerry);
        ExtraTreeSpecies.Cinnamon.addFamily(familyNuts).addFamily(familyJungle);
        ExtraTreeSpecies.Coconut.addFamily(familyJungle);
        ExtraTreeSpecies.Cashew.addFamily(familyNuts).addFamily(familyJungle);
        ExtraTreeSpecies.Avacado.addFamily(familyPrune).addFamily(familyCitrus).addFamily(familyJungle);
        ExtraTreeSpecies.Nutmeg.addFamily(familyNuts).addFamily(familyJungle);
        ExtraTreeSpecies.Allspice.addFamily(familyNuts).addFamily(familyJungle);
        ExtraTreeSpecies.Chilli.addFamily(familyNuts).addFamily(familyJungle);
        ExtraTreeSpecies.StarAnise.addFamily(familyNuts).addFamily(familyJungle);
        ExtraTreeSpecies.Mango.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus).addFamily(familyJungle);
        ExtraTreeSpecies.Starfruit.addFamily(familyPrune).addFamily(familyCitrus).addFamily(familyJungle);
        ExtraTreeSpecies.Candlenut.addFamily(familyNuts).addFamily(familyJungle);
        ExtraTreeSpecies.DwarfHazel.addFamily(familyPrune).addFamily(familyNuts);
        ExtraTreeSpecies.OrchardApple.setYield(ForestryAllele.Yield.Higher).setSappiness(ForestryAllele.Sappiness.Low).setMaturation(ForestryAllele.Maturation.Faster);
        ExtraTreeSpecies.SweetCrabapple.setYield(ForestryAllele.Yield.High).setSappiness(ForestryAllele.Sappiness.Average).setMaturation(ForestryAllele.Maturation.Fast);
        ExtraTreeSpecies.FloweringCrabapple.setFertility(ForestryAllele.Saplings.Average).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Low).setMaturation(ForestryAllele.Maturation.Fast);
        ExtraTreeSpecies.PrairieCrabapple.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Average);
        ExtraTreeSpecies.Blackthorn.setHeight(ForestryAllele.TreeHeight.Smallest).setYield(ForestryAllele.Yield.Low).setSappiness(ForestryAllele.Sappiness.Average).setMaturation(ForestryAllele.Maturation.Fast);
        ExtraTreeSpecies.CherryPlum.setHeight(ForestryAllele.TreeHeight.Smallest).setYield(ForestryAllele.Yield.Lower).setSappiness(ForestryAllele.Sappiness.Average).setMaturation(ForestryAllele.Maturation.Fast);
        ExtraTreeSpecies.Peach.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Average);
        ExtraTreeSpecies.Nectarine.setHeight(ForestryAllele.TreeHeight.Smallest).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Average);
        ExtraTreeSpecies.Apricot.setHeight(ForestryAllele.TreeHeight.Smallest).setYield(ForestryAllele.Yield.Low).setSappiness(ForestryAllele.Sappiness.Average);
        ExtraTreeSpecies.Almond.setYield(ForestryAllele.Yield.Lower).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Fast);
        ExtraTreeSpecies.WildCherry.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Lower).setSappiness(ForestryAllele.Sappiness.Low).setMaturation(ForestryAllele.Maturation.Fast);
        ExtraTreeSpecies.SourCherry.setYield(ForestryAllele.Yield.Lower).setSappiness(ForestryAllele.Sappiness.Low);
        ExtraTreeSpecies.BlackCherry.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Lowest).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Fast);
        ExtraTreeSpecies.Orange.setHeight(ForestryAllele.TreeHeight.Smallest).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Average).setMaturation(ForestryAllele.Maturation.Fast);
        ExtraTreeSpecies.Manderin.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.High).setSappiness(ForestryAllele.Sappiness.Low);
        ExtraTreeSpecies.Satsuma.setHeight(ForestryAllele.TreeHeight.Smallest).setYield(ForestryAllele.Yield.Low).setSappiness(ForestryAllele.Sappiness.Low).setMaturation(ForestryAllele.Maturation.Fast);
        ExtraTreeSpecies.Tangerine.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Average).setMaturation(ForestryAllele.Maturation.Faster);
        ExtraTreeSpecies.Lime.setHeight(ForestryAllele.TreeHeight.Smallest).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Low);
        ExtraTreeSpecies.KeyLime.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Lowest).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Low).setMaturation(ForestryAllele.Maturation.Fast);
        ExtraTreeSpecies.FingerLime.setHeight(ForestryAllele.TreeHeight.Smallest).setYield(ForestryAllele.Yield.High).setSappiness(ForestryAllele.Sappiness.Low).setMaturation(ForestryAllele.Maturation.Fast);
        ExtraTreeSpecies.Pomelo.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Lower);
        ExtraTreeSpecies.Grapefruit.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Low).setMaturation(ForestryAllele.Maturation.Fast);
        ExtraTreeSpecies.Kumquat.setHeight(ForestryAllele.TreeHeight.Smallest).setYield(ForestryAllele.Yield.High).setSappiness(ForestryAllele.Sappiness.Low);
        ExtraTreeSpecies.Citron.setHeight(ForestryAllele.TreeHeight.Smallest).setYield(ForestryAllele.Yield.High).setSappiness(ForestryAllele.Sappiness.Average);
        ExtraTreeSpecies.BuddhaHand.setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Low);
        ExtraTreeSpecies.Banana.setYield(ForestryAllele.Yield.Low).setSappiness(ForestryAllele.Sappiness.Low).setMaturation(ForestryAllele.Maturation.Fast);
        ExtraTreeSpecies.RedBanana.setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Lower).setSappiness(ForestryAllele.Sappiness.Lower);
        ExtraTreeSpecies.Plantain.setHeight(ForestryAllele.TreeHeight.Average).setYield(ForestryAllele.Yield.Lower);
        ExtraTreeSpecies.Butternut.setHeight(ForestryAllele.TreeHeight.Smaller).setYield(ForestryAllele.Yield.Low);
        ExtraTreeSpecies.Rowan.setHeight(ForestryAllele.TreeHeight.Larger).setFertility(ForestryAllele.Saplings.Low).setSappiness(ForestryAllele.Sappiness.Lower);
        ExtraTreeSpecies.Hemlock.setHeight(ForestryAllele.TreeHeight.Average).setFertility(ForestryAllele.Saplings.Low).setMaturation(ForestryAllele.Maturation.Slower);
        ExtraTreeSpecies.Ash.setFertility(ForestryAllele.Saplings.Low).setSappiness(ForestryAllele.Sappiness.Lower);
        ExtraTreeSpecies.Alder.setHeight(ForestryAllele.TreeHeight.Average).setSappiness(ForestryAllele.Sappiness.Lower);
        ExtraTreeSpecies.Beech.setHeight(ForestryAllele.TreeHeight.Average).setYield(ForestryAllele.Yield.Lower);
        ExtraTreeSpecies.CopperBeech.setMaturation(ForestryAllele.Maturation.Slow);
        ExtraTreeSpecies.Aspen.setFertility(ForestryAllele.Saplings.Average).setSappiness(ForestryAllele.Sappiness.Lower);
        ExtraTreeSpecies.Yew.setHeight(ForestryAllele.TreeHeight.Large).setSappiness(ForestryAllele.Sappiness.Lower);
        ExtraTreeSpecies.Cypress.setHeight(ForestryAllele.TreeHeight.Larger).setFertility(ForestryAllele.Saplings.Low).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Slow);
        ExtraTreeSpecies.DouglasFir.setHeight(ForestryAllele.TreeHeight.Smaller).setFertility(ForestryAllele.Saplings.Low).setMaturation(ForestryAllele.Maturation.Slower);
        ExtraTreeSpecies.Hazel.setHeight(ForestryAllele.TreeHeight.Average).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Low).setSappiness(ForestryAllele.Sappiness.Lower);
        ExtraTreeSpecies.Sycamore.setFertility(ForestryAllele.Saplings.Lowest).setSappiness(ForestryAllele.Sappiness.Lower);
        ExtraTreeSpecies.Whitebeam.setHeight(ForestryAllele.TreeHeight.Smaller);
        ExtraTreeSpecies.Hawthorn.setHeight(ForestryAllele.TreeHeight.Average);
        ExtraTreeSpecies.Pecan.setHeight(ForestryAllele.TreeHeight.Large).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Slow);
        ExtraTreeSpecies.Elm.setHeight(ForestryAllele.TreeHeight.Smaller).setFertility(ForestryAllele.Saplings.Low).setSappiness(ForestryAllele.Sappiness.Average);
        ExtraTreeSpecies.Elder.setHeight(ForestryAllele.TreeHeight.Smaller).setSappiness(ForestryAllele.Sappiness.Low);
        ExtraTreeSpecies.Holly.setHeight(ForestryAllele.TreeHeight.Average).setSappiness(ForestryAllele.Sappiness.Low);
        ExtraTreeSpecies.Hornbeam.setHeight(ForestryAllele.TreeHeight.Smaller).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Lower).setMaturation(ForestryAllele.Maturation.Slow);
        ExtraTreeSpecies.Sallow.setHeight(ForestryAllele.TreeHeight.Large).setFertility(ForestryAllele.Saplings.Low);
        ExtraTreeSpecies.AcornOak.setHeight(ForestryAllele.TreeHeight.Large).setYield(ForestryAllele.Yield.Low).setSappiness(ForestryAllele.Sappiness.Lower);
        ExtraTreeSpecies.Fir.setHeight(ForestryAllele.TreeHeight.Large).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Slow);
        ExtraTreeSpecies.Cedar.setHeight(ForestryAllele.TreeHeight.Smaller).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Slower);
        ExtraTreeSpecies.Olive.setYield(ForestryAllele.Yield.Average);
        ExtraTreeSpecies.RedMaple.setFertility(ForestryAllele.Saplings.Average).setSappiness(ForestryAllele.Sappiness.High);
        ExtraTreeSpecies.BalsamFir.setHeight(ForestryAllele.TreeHeight.Average).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Slow);
        ExtraTreeSpecies.LoblollyPine.setHeight(ForestryAllele.TreeHeight.Smaller).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Slow);
        ExtraTreeSpecies.Sweetgum.setHeight(ForestryAllele.TreeHeight.Average).setFertility(ForestryAllele.Saplings.High).setYield(ForestryAllele.Yield.Low).setSappiness(ForestryAllele.Sappiness.Average);
        ExtraTreeSpecies.Locust.setHeight(ForestryAllele.TreeHeight.Smallest);
        ExtraTreeSpecies.Pear.setHeight(ForestryAllele.TreeHeight.Smaller).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.High).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Fast);
        ExtraTreeSpecies.OsangeOsange.setYield(ForestryAllele.Yield.Lower);
        ExtraTreeSpecies.OldFustic.setHeight(ForestryAllele.TreeHeight.Smaller).setSappiness(ForestryAllele.Sappiness.Lower);
        ExtraTreeSpecies.Brazilwood.setHeight(ForestryAllele.TreeHeight.Smaller).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Lower).setSappiness(ForestryAllele.Sappiness.Lower);
        ExtraTreeSpecies.Logwood.setHeight(ForestryAllele.TreeHeight.Average).setFertility(ForestryAllele.Saplings.Low).setSappiness(ForestryAllele.Sappiness.Lower);
        ExtraTreeSpecies.Rosewood.setHeight(ForestryAllele.TreeHeight.Average).setSappiness(ForestryAllele.Sappiness.Lower);
        ExtraTreeSpecies.Purpleheart.setHeight(ForestryAllele.TreeHeight.Large).setSappiness(ForestryAllele.Sappiness.Lower);
        ExtraTreeSpecies.Iroko.setHeight(ForestryAllele.TreeHeight.Average).setFertility(ForestryAllele.Saplings.Low);
        ExtraTreeSpecies.Gingko.setHeight(ForestryAllele.TreeHeight.Large).setYield(ForestryAllele.Yield.Lower).setSappiness(ForestryAllele.Sappiness.Low);
        ExtraTreeSpecies.Brazilnut.setHeight(ForestryAllele.TreeHeight.Larger).setYield(ForestryAllele.Yield.Low).setSappiness(ForestryAllele.Sappiness.Low);
        ExtraTreeSpecies.RoseGum.setHeight(ForestryAllele.TreeHeight.Largest).setFertility(ForestryAllele.Saplings.Lowest).setYield(ForestryAllele.Yield.Lower).setSappiness(ForestryAllele.Sappiness.Low).setMaturation(ForestryAllele.Maturation.Slowest);
        ExtraTreeSpecies.SwampGum.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Lowest).setMaturation(ForestryAllele.Maturation.Slower);
        ExtraTreeSpecies.Box.setHeight(ForestryAllele.TreeHeight.Smaller).setMaturation(ForestryAllele.Maturation.Faster);
        ExtraTreeSpecies.Clove.setHeight(ForestryAllele.TreeHeight.Smaller).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.High).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Fast);
        ExtraTreeSpecies.Coffee.setHeight(ForestryAllele.TreeHeight.Large).setYield(ForestryAllele.Yield.Average).setMaturation(ForestryAllele.Maturation.Fast);
        ExtraTreeSpecies.MonkeyPuzzle.setHeight(ForestryAllele.TreeHeight.Average).setYield(ForestryAllele.Yield.Lower).setSappiness(ForestryAllele.Sappiness.Low);
        ExtraTreeSpecies.RainbowGum.setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Lower);
        ExtraTreeSpecies.PinkIvory.setHeight(ForestryAllele.TreeHeight.Smallest);
        ExtraTreeSpecies.Blackcurrant.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Faster);
        ExtraTreeSpecies.Redcurrant.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Average).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Low).setMaturation(ForestryAllele.Maturation.Faster);
        ExtraTreeSpecies.Blackberry.setHeight(ForestryAllele.TreeHeight.Smallest).setYield(ForestryAllele.Yield.High).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Faster);
        ExtraTreeSpecies.Raspberry.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Faster);
        ExtraTreeSpecies.Blueberry.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Average).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Low).setMaturation(ForestryAllele.Maturation.Faster);
        ExtraTreeSpecies.Cranberry.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Average).setYield(ForestryAllele.Yield.High).setSappiness(ForestryAllele.Sappiness.Low).setMaturation(ForestryAllele.Maturation.Faster);
        ExtraTreeSpecies.Juniper.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Low).setSappiness(ForestryAllele.Sappiness.Low).setMaturation(ForestryAllele.Maturation.Faster);
        ExtraTreeSpecies.Gooseberry.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.High).setYield(ForestryAllele.Yield.High).setMaturation(ForestryAllele.Maturation.Faster);
        ExtraTreeSpecies.GoldenRaspberry.setHeight(ForestryAllele.TreeHeight.Smaller).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Fastest);
        ExtraTreeSpecies.Cinnamon.setHeight(ForestryAllele.TreeHeight.Average).setYield(ForestryAllele.Yield.Lower).setMaturation(ForestryAllele.Maturation.Fast);
        ExtraTreeSpecies.Coconut.setHeight(ForestryAllele.TreeHeight.Smaller).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Average).setMaturation(ForestryAllele.Maturation.Fast);
        ExtraTreeSpecies.Cashew.setYield(ForestryAllele.Yield.Low);
        ExtraTreeSpecies.Avacado.setHeight(ForestryAllele.TreeHeight.Smallest).setYield(ForestryAllele.Yield.Average);
        ExtraTreeSpecies.Nutmeg.setHeight(ForestryAllele.TreeHeight.Smaller).setYield(ForestryAllele.Yield.High).setSappiness(ForestryAllele.Sappiness.Low);
        ExtraTreeSpecies.Allspice.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.High);
        ExtraTreeSpecies.Chilli.setHeight(ForestryAllele.TreeHeight.Smaller).setYield(ForestryAllele.Yield.Higher).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Fast);
        ExtraTreeSpecies.StarAnise.setHeight(ForestryAllele.TreeHeight.Average).setYield(ForestryAllele.Yield.High);
        ExtraTreeSpecies.Mango.setHeight(ForestryAllele.TreeHeight.Smaller).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Fast);
        ExtraTreeSpecies.Starfruit.setYield(ForestryAllele.Yield.Average).setMaturation(ForestryAllele.Maturation.Fast);
        ExtraTreeSpecies.Candlenut.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Lowest).setYield(ForestryAllele.Yield.Low).setSappiness(ForestryAllele.Sappiness.Low);
        ExtraTreeSpecies.DwarfHazel.setFertility(ForestryAllele.Saplings.Average).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Faster);
    }

    private ExtraTreeSpecies addFamily(final IFruitFamily family) {
        this.families.add(family);
        return this;
    }

    static final ItemStack getEBXLStack(final String name) {
        try {
            final Class elements = Class.forName("extrabiomes.lib.Element");
            final Method getElementMethod = elements.getMethod("valueOf", String.class);
            final Method getItemStack = elements.getMethod("get", new Class[0]);
            final Object element = getElementMethod.invoke(null, "SAPLING_AUTUMN_YELLOW");
            return (ItemStack) getItemStack.invoke(element, new Object[0]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setWorldGen(final Class<? extends WorldGenerator> gen) {
        this.gen = gen;
    }

    private void setGirth(final int i) {
        this.template[EnumTreeChromosome.GIRTH.ordinal()] = AlleleManager.alleleRegistry.getAllele("forestry.i" + i + "d");
    }

    public void preInit() {
        this.template = Binnie.Genetics.getTreeRoot().getDefaultTemplate();
        this.template[EnumTreeChromosome.SPECIES.ordinal()] = this;
        if (this.fruit != null) {
            this.template[EnumTreeChromosome.FRUITS.ordinal()] = this.fruit;
        }
        this.template[EnumTreeChromosome.GIRTH.ordinal()] = AlleleManager.alleleRegistry.getAllele("forestry.i" + this.girth + "d");
        final IClassification clas = AlleleManager.alleleRegistry.getClassification("trees." + this.branch);
        if (clas != null) {
            clas.addMemberSpecies(this);
            this.branch = clas;
        }


    }

    int colorPollineted;
    int woodColor;

    private ExtraTreeSpecies(final String branch, final String binomial, final int color, final int polColor, final int woodColor, final IWoodType wood, final IAlleleFruit fruit, SaplingType saplingType, final Class<? extends WorldGenerator> gen) {
        this.leafType = LeafType.Normal;
        this.saplingType = saplingType;
        this.families = new ArrayList<>();
        this.girth = 1;
        this.fruit = null;
        this.color = color;
        this.colorPollineted = new Color(color).brighter().getRGB();
        this.wood = wood;
        this.fruit = fruit;
        this.gen = ((gen == null) ? WorldGenTree.class : gen);
        this.branchName = branch;
        this.binomial = binomial;
        this.woodProvider = new WoodProvider(this.wood);
        this.woodColor = woodColor;
        this.colorPollineted = polColor;
        //this.uid = getSpeciesName().toLowerCase().trim();
    }
///TODO

    @Override
    public boolean equals(Object obj) {
        return obj == this;
    }

    @Override
    public int getSpriteColour(int renderPass) {
        return color;
    }

    @Override
    public int compareTo(@Nonnull IAlleleTreeSpecies o) {
        return o == this ? 0 : -1;
    }

    @Nonnull
    @Override
    public String getModID() {
        return "extratrees";
    }

    @Override
    public IWoodProvider getWoodProvider() {
        return woodProvider;
    }

    @Nonnull
    @Override
    public ILeafSpriteProvider getLeafSpriteProvider() {
        return new ILeafSpriteProvider() {
            @Nonnull
            @Override
            public ResourceLocation getSprite(boolean pollinated, boolean fancy) {
                return new ResourceLocation("asd");
            }

            @Override
            public int getColor(boolean pollinated) {
                return colorPollineted;
            }
        };
    }

    @Nonnull
    @Override
    public ModelResourceLocation getGermlingModel(EnumGermlingType type) {
        if (type == EnumGermlingType.SAPLING) {
            return germlingModel;
        } else if (type == EnumGermlingType.POLLEN) {
            return pollenModel;
        } else {
            return germlingModel;
        }
    }


    private ModelResourceLocation germlingModel;
    private ModelResourceLocation pollenModel;

    @Override
    public void registerModels(Item item, IModelManager manager, EnumGermlingType type) {
        if (type == EnumGermlingType.SAPLING) {
            germlingModel = manager.getModelLocation("extratrees", "saplings/tree" + saplingType.name());
            ModelBakery.registerItemVariants(item, new ResourceLocation("extratrees", "saplings/tree" + saplingType.name()));
        }
        if (type == EnumGermlingType.POLLEN) {
            pollenModel = manager.getModelLocation("pollen");
            ModelBakery.registerItemVariants(item, new ResourceLocation("forestry:pollen"));
        }
    }


    ////
    @Override
    public String getName() {
        return ExtraTrees.proxy.localise("species." + this.getUID() + ".name");
    }

    @Override
    public String getDescription() {
        return ExtraTrees.proxy.localiseOrBlank("species." + this.getUID() + ".desc");
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
        return this.binomial;
    }

    @Override
    public String getAuthority() {
        return "Binnie";
    }

    @Override
    public IClassification getBranch() {
        return this.branch;
    }

    @Override
    public String getUID() {
        return getSpeciesName().toLowerCase().trim();
    }

    @Override
    public boolean isDominant() {
        return true;
    }

    @Override
    public EnumPlantType getPlantType() {
        return EnumPlantType.Plains;
    }


    WoodAccess woodAccess = new WoodAccess();
    @Override
    public ITreeGenerator getGenerator() {
        return new ITreeGenerator() {
            @Override
            public WorldGenerator getWorldGenerator(ITreeGenData tree) {
                return ExtraTreeSpecies.this.getGenerator(tree);
            }

            @Override
            public boolean setLogBlock(ITreeGenome genome, World world, BlockPos pos, EnumFacing facing) {
                if (ExtraTreeSpecies.this.wood != null) {
                    //ItemStack block = woodProvider.getWoodStack(); //TreeManager.woodAccess.getBlock(wood, WoodBlockKind.LOG, false);
                    return world.setBlockState(pos, woodAccess.getBlock(wood,WoodBlockKind.LOG,false), 2);
                }
                return false;
            }

            @Override
            public boolean setLeaves(ITreeGenome genome, World world, GameProfile owner, BlockPos pos) {
                boolean placed = world.setBlockState(pos, PluginArboriculture.blocks.leaves.getDefaultState());
                if (!placed) {
                    return false;
                }

                Block block = world.getBlockState(pos).getBlock();
                if (PluginArboriculture.blocks.leaves != block) {
                    world.setBlockToAir(pos);
                    return false;
                }

                TileLeaves tileLeaves = TileUtil.getTile(world, pos, TileLeaves.class);
                if (tileLeaves == null) {
                    world.setBlockToAir(pos);
                    return false;
                }

                tileLeaves.getOwnerHandler().setOwner(owner);
                tileLeaves.setTree(new Tree(genome));

                world.markBlockRangeForRenderUpdate(pos, pos);
                return true;
            }

        };
    }

    public WorldGenerator getGenerator(final ITreeGenData tree) {
        if (this.gen != null) {
            try {
                return this.gen.getConstructor(ITree.class).newInstance(tree);
            } catch (Exception ex) {
            }
        }
        return new WorldGenDefault(tree);
    }

    void setLeafType(final LeafType type) {
        this.leafType = type;
        if (this.leafType == LeafType.Conifer) {
            this.saplingType = SaplingType.Conifer;
        }
        if (this.leafType == LeafType.Jungle) {
            this.saplingType = SaplingType.Jungle;
        }
        if (this.leafType == LeafType.Palm) {
            this.saplingType = SaplingType.Palm;
        }
    }

    public IAllele[] getTemplate() {
        return this.template;
    }

    @Override
    public ArrayList<IFruitFamily> getSuitableFruit() {
        return this.families;
    }

    public IWoodType getLog() {
        return this.wood;
    }

    public ExtraTreeSpecies setHeight(final ForestryAllele.TreeHeight height) {
        final IAllele allele = height.getAllele();
        if (allele != null) {
            this.template[EnumTreeChromosome.HEIGHT.ordinal()] = allele;
        }
        return this;
    }

    public ExtraTreeSpecies setSappiness(final ForestryAllele.Sappiness height) {
        final IAllele allele = height.getAllele();
        if (allele != null) {
            this.template[EnumTreeChromosome.SAPPINESS.ordinal()] = allele;
        }
        return this;
    }

    public ExtraTreeSpecies setMaturation(final ForestryAllele.Maturation height) {
        final IAllele allele = height.getAllele();
        if (allele != null) {
            this.template[EnumTreeChromosome.MATURATION.ordinal()] = allele;
        }
        return this;
    }

    public ExtraTreeSpecies setYield(final ForestryAllele.Yield height) {
        final IAllele allele = height.getAllele();
        if (allele != null) {
            this.template[EnumTreeChromosome.YIELD.ordinal()] = allele;
        }
        return this;
    }

    public ExtraTreeSpecies setFertility(final ForestryAllele.Saplings height) {
        final IAllele allele = height.getAllele();
        if (allele != null) {
            this.template[EnumTreeChromosome.FERTILITY.ordinal()] = allele;
        }
        return this;
    }

    public void setGrowthConditions(final ForestryAllele.Growth growth) {
        final IAllele allele = growth.getAllele();
        if (allele != null) {
            this.template[EnumTreeChromosome.GROWTH.ordinal()] = allele;
        }
    }

    public void finished() {

    }


    public int getLeafColour(final ITree tree) {
        return this.color;
    }

    public short getLeafIconIndex(final ITree tree, final boolean fancy) {
        if (!fancy) {
            return this.leafType.plainUID;
        }
        if (tree.getMate() != null) {
            return this.leafType.changedUID;
        }
        return this.leafType.fancyUID;
    }

//	@Override
//	public int getIconColour(final int renderPass) {
//		return (renderPass == 0) ? this.color : 0xFFFFFF;// 10451021;
//	}

//	@Override
//	@SideOnly(Side.CLIENT)
//	public IIcon getGermlingIcon(final EnumGermlingType type, final int renderPass) {
//		if (type == EnumGermlingType.POLLEN) {
//			return PluginArboriculture.items.pollenFertile.getIcon(PluginArboriculture.items.pollenFertile.getItemStack(), renderPass);
//		}
//
//		return (renderPass == 0) ? this.saplingType.icon[1] : this.saplingType.icon[0];
//	}
//
//	@Override
//	public IIconProvider getIconProvider() {
//		return this;
//	}
//
//	@Override
//	public IIcon getIcon(final short texUID) {
//		return TextureManager.getInstance().getIcon(texUID);
//	}
//
//	@Override
//	public void registerIcons(final IIconRegister register) {
//		for (final SaplingType type : SaplingType.values()) {
//			(type.icon = new IIcon[2])[0] = ExtraTrees.proxy.getIcon(register, "saplings/" + type.toString().toLowerCase() + ".trunk");
//			type.icon[1] = ExtraTrees.proxy.getIcon(register, "saplings/" + type.toString().toLowerCase() + ".leaves");
//		}
//	}

    @Override
    public ITreeRoot getRoot() {
        return Binnie.Genetics.getTreeRoot();
    }

    @Override
    public float getResearchSuitability(final ItemStack itemstack) {
        if (itemstack == null) {
            return 0.0f;
        }
        if (this.template[EnumTreeChromosome.FRUITS.ordinal()] instanceof ExtraTreeFruitGene) {
            final ExtraTreeFruitGene fruit = (ExtraTreeFruitGene) this.template[EnumTreeChromosome.FRUITS.ordinal()];
            for (final ItemStack stack : fruit.products.keySet()) {
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
        for (final Map.Entry<ItemStack, Float> entry : this.getRoot().getResearchCatalysts().entrySet()) {
            if (entry.getKey().isItemEqual(itemstack)) {
                return entry.getValue();
            }
        }
        return 0.0f;
    }

    @Override
    public ItemStack[] getResearchBounty(final World world, final GameProfile researcher, final IIndividual individual, final int bountyLevel) {
        final ArrayList<ItemStack> bounty = new ArrayList<>();
        ItemStack research = null;
        if (world.rand.nextFloat() < 10.0f / bountyLevel) {
            final Collection<? extends IMutation> combinations = this.getRoot().getCombinations(this);
            if (combinations.size() > 0) {
                final IMutation[] candidates = combinations.toArray(new IMutation[0]);
                research = AlleleManager.alleleRegistry.getMutationNoteStack(researcher, candidates[world.rand.nextInt(candidates.length)]);
            }
        }
        if (research != null) {
            bounty.add(research);
        }
        if (this.template[EnumTreeChromosome.FRUITS.ordinal()] instanceof ExtraTreeFruitGene) {
            final ExtraTreeFruitGene fruit = (ExtraTreeFruitGene) this.template[EnumTreeChromosome.FRUITS.ordinal()];
            for (final ItemStack stack : fruit.products.keySet()) {
                final ItemStack stack2 = stack.copy();
                stack2.stackSize = world.rand.nextInt((int) (bountyLevel / 2.0f)) + 1;
                bounty.add(stack2);
            }
        }
        return bounty.toArray(new ItemStack[0]);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getGermlingColour(final EnumGermlingType type, int renderPass) {
        if (type == EnumGermlingType.SAPLING) {
            return (renderPass != 0) ? this.getLeafColour(null) : ((this.getLog() == null) ? 16777215 : woodColor);
        }
        return this.getLeafColour(null);
    }

    @Override
    public int getComplexity() {
        return 1 + this.getGeneticAdvancement(this, new ArrayList<>());
    }

    private int getGeneticAdvancement(final IAllele species, final ArrayList<IAllele> exclude) {
        final int own = 1;
        int highest = 0;
        exclude.add(species);
        for (final IMutation mutation : this.getRoot().getPaths(species, EnumBeeChromosome.SPECIES)) {
            if (!exclude.contains(mutation.getAllele0())) {
                final int otherAdvance = this.getGeneticAdvancement(mutation.getAllele0(), exclude);
                if (otherAdvance > highest) {
                    highest = otherAdvance;
                }
            }
            if (!exclude.contains(mutation.getAllele1())) {
                final int otherAdvance = this.getGeneticAdvancement(mutation.getAllele1(), exclude);
                if (otherAdvance <= highest) {
                    continue;
                }
                highest = otherAdvance;
            }
        }
        return own + ((highest < 0) ? 0 : highest);
    }
//never used
//	public ItemStack[] getLogStacks() {
//		if (this.wood == null) {
//			return new ItemStack[0];
//		}
//		return new ItemStack[] { this.wood.getItemStack() };
//	}

    @Override
    public String getUnlocalizedName() {
        return "extratrees.species." + this.getUID() + ".name";
    }

    public enum LeafType {
        Normal((short) 10, (short) 11, (short) 12, "Deciduous"),
        Conifer((short) 15, (short) 16, (short) 17, "Conifers"),
        Jungle((short) 20, (short) 21, (short) 22, "Jungle"),
        Willow((short) 25, (short) 26, (short) 27, "Willow"),
        Maple((short) 30, (short) 31, (short) 32, "Maple"),
        Palm((short) 35, (short) 36, (short) 37, "Palm");

        public final short fancyUID;
        public final short plainUID;
        public final short changedUID;
        public final String descript;

        LeafType(final short fancyUID, final short plainUID, final short changedUID, final String descript) {
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

        public ModelResourceLocation getModelLocation(ExtraTreeSpecies species) {
            return new ModelResourceLocation(ExtraTrees.MOD_ID + ":saplings/tree" + name());
        }

        public ResourceLocation getResourceLocation(ExtraTreeSpecies species) {
            return new ResourceLocation(ExtraTrees.MOD_ID + ":saplings/tree" + name());
        }
        //IIcon[] icon;
    }

//	@Override
//	public int getLeafColour(boolean pollinated) {
//		return pollinated ? colorPollineted : color;
//	}
//
//	@SideOnly(Side.CLIENT)
//	@Override
//	public IIcon getLeafIcon(boolean pollinated, boolean fancy) {
//		try {
//			if (pollinated)
//				return TextureLeaves.get(EnumLeafType.valueOf(leafType.descript.toUpperCase())).getPollinated();
//			if (fancy)
//				return TextureLeaves.get(EnumLeafType.valueOf(leafType.descript.toUpperCase())).getFancy();
//			return TextureLeaves.get(EnumLeafType.valueOf(leafType.descript.toUpperCase())).getPlain();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	@SideOnly(Side.CLIENT)
//	@Override
//	public IIcon getIcon(EnumGermlingType type, int renderPass) {
//		return getGermlingIcon(type, renderPass);
//	}

}
