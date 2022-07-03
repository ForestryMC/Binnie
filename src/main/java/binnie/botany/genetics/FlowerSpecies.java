package binnie.botany.genetics;

import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumFlowerChromosome;
import binnie.botany.api.EnumMoisture;
import binnie.botany.api.IAlleleFlowerSpecies;
import binnie.botany.api.IFlowerRoot;
import binnie.botany.api.IFlowerType;
import binnie.botany.core.BotanyCore;
import binnie.core.util.I18N;
import binnie.genetics.genetics.AlleleHelper;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.core.IIconProvider;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.EnumTolerance;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IClassification;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;
import forestry.core.genetics.alleles.EnumAllele;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public enum FlowerSpecies implements IAlleleFlowerSpecies {
    DANDELION("dandelion", "taraxacum", "officinale", EnumFlowerType.DANDELION, EnumFlowerColor.YELLOW),
    POPPY("poppy", "papaver", "rhoeas", EnumFlowerType.POPPY, EnumFlowerColor.RED),
    ORCHID("orchid", "vanda", "coerulea", EnumFlowerType.ORCHID, EnumFlowerColor.DEEP_SKY_BLUE),
    ALLIUM("allium", "allium", "giganteum", EnumFlowerType.ALLIUM, EnumFlowerColor.MEDIUM_PURPLE),
    BLUET("bluet", "houstonia", "caerulea", EnumFlowerType.BLUET, EnumFlowerColor.LAVENDER, EnumFlowerColor.KHAKI),
    TULIP("tulip", "tulipa", "agenensis", EnumFlowerType.TULIP, EnumFlowerColor.VIOLET),
    DAISY("daisy", "leucanthemum", "vulgare", EnumFlowerType.DAISY, EnumFlowerColor.WHITE, EnumFlowerColor.YELLOW),
    CORNFLOWER("cornflower", "centaurea", "cyanus", EnumFlowerType.CORNFLOWER, EnumFlowerColor.SKY_BLUE),
    PANSY("pansy", "viola", "tricolor", EnumFlowerType.PANSY, EnumFlowerColor.PINK, EnumFlowerColor.PURPLE),
    IRIS("iris", "iris", "germanica", EnumFlowerType.IRIS, EnumFlowerColor.LIGHT_GRAY, EnumFlowerColor.PURPLE),
    LAVENDER("lavender", "Lavandula", "angustifolia", EnumFlowerType.LAVENDER, EnumFlowerColor.MEDIUM_ORCHID),
    VIOLA("viola", "viola", "odorata", EnumFlowerType.VIOLA, EnumFlowerColor.MEDIUM_PURPLE, EnumFlowerColor.SLATE_BLUE),
    DAFFODIL(
            "daffodil",
            "narcissus",
            "pseudonarcissus",
            EnumFlowerType.DAFFODIL,
            EnumFlowerColor.YELLOW,
            EnumFlowerColor.GOLD),
    DAHLIA(
            "dahlia",
            "dahlia",
            "variabilis",
            EnumFlowerType.DAHLIA,
            EnumFlowerColor.HOT_PINK,
            EnumFlowerColor.DEEP_PINK),
    PEONY("peony", "paeonia", "suffruticosa", EnumFlowerType.PEONY, EnumFlowerColor.THISTLE),
    ROSE("rose", "rosa", "rubiginosa", EnumFlowerType.ROSE, EnumFlowerColor.RED),
    LILAC("lilac", "syringa", "vulgaris", EnumFlowerType.LILAC, EnumFlowerColor.PLUM),
    HYDRANGEA("hydrangea", "hydrangea", "macrophylla", EnumFlowerType.HYDRANGEA, EnumFlowerColor.DEEP_SKY_BLUE),
    FOXGLOVE("foxglove", "digitalis", "purpurea", EnumFlowerType.FOXGLOVE, EnumFlowerColor.HOT_PINK),
    ZINNIA(
            "zinnia",
            "zinnia",
            "elegans",
            EnumFlowerType.ZINNIA,
            EnumFlowerColor.MEDIUM_VIOLET_RED,
            EnumFlowerColor.YELLOW),
    CHRYSANTHEMUM(
            "chrysanthemum", "chrysanthemum", "\u00ef?? grandiflorum", EnumFlowerType.MUMS, EnumFlowerColor.VIOLET),
    MARIGOLD(
            "marigold",
            "calendula",
            "officinalis",
            EnumFlowerType.MARIGOLD,
            EnumFlowerColor.GOLD,
            EnumFlowerColor.DARK_ORANGE),
    GERANIUM("geranium", "geranium", "maderense", EnumFlowerType.GERANIUM, EnumFlowerColor.DEEP_PINK),
    AZALEA("azalea", "rhododendrons", "aurigeranum", EnumFlowerType.AZALEA, EnumFlowerColor.HOT_PINK),
    PRIMROSE("primrose", "primula", "vulgaris", EnumFlowerType.PRIMROSE, EnumFlowerColor.RED, EnumFlowerColor.GOLD),
    ASTER("aster", "aster", "amellus", EnumFlowerType.ASTER, EnumFlowerColor.MEDIUM_PURPLE, EnumFlowerColor.GOLDENROD),
    CARNATION(
            "carnation",
            "dianthus",
            "caryophyllus",
            EnumFlowerType.CARNATION,
            EnumFlowerColor.CRIMSON,
            EnumFlowerColor.WHITE),
    LILY("lily", "lilium", "auratum", EnumFlowerType.LILY, EnumFlowerColor.PINK, EnumFlowerColor.GOLD),
    YARROW("yarrow", "achillea", "millefolium", EnumFlowerType.YARROW, EnumFlowerColor.YELLOW),
    PETUNIA(
            "petunia",
            "petunia",
            "\u00ef?? atkinsiana",
            EnumFlowerType.PETUNIA,
            EnumFlowerColor.MEDIUM_VIOLET_RED,
            EnumFlowerColor.THISTLE),
    AGAPANTHUS("agapanthus", "agapanthus", "praecox", EnumFlowerType.AGAPANTHUS, EnumFlowerColor.DEEP_SKY_BLUE),
    FUCHSIA(
            "fuchsia",
            "fuchsia",
            "magellanica",
            EnumFlowerType.FUCHSIA,
            EnumFlowerColor.DEEP_PINK,
            EnumFlowerColor.MEDIUM_ORCHID),
    DIANTHUS(
            "dianthus",
            "dianthus",
            "barbatus",
            EnumFlowerType.DIANTHUS,
            EnumFlowerColor.CRIMSON,
            EnumFlowerColor.HOT_PINK),
    FORGET("forget-me-nots", "myosotis", "arvensis", EnumFlowerType.FORGET, EnumFlowerColor.LIGHT_STEEL_BLUE),
    ANEMONE("anemone", "anemone", "coronaria", EnumFlowerType.ANEMONE, EnumFlowerColor.RED, EnumFlowerColor.MISTY_ROSE),
    AQUILEGIA(
            "aquilegia",
            "aquilegia",
            "vulgaris",
            EnumFlowerType.AQUILEGIA,
            EnumFlowerColor.SLATE_BLUE,
            EnumFlowerColor.THISTLE),
    EDELWEISS(
            "edelweiss",
            "leontopodium",
            "alpinum",
            EnumFlowerType.EDELWEISS,
            EnumFlowerColor.WHITE,
            EnumFlowerColor.KHAKI),
    SCABIOUS("scabious", "scabiosa", "columbaria", EnumFlowerType.SCABIOUS, EnumFlowerColor.ROYAL_BLUE),
    CONEFLOWER(
            "coneflower",
            "echinacea",
            "purpurea",
            EnumFlowerType.CONEFLOWER,
            EnumFlowerColor.VIOLET,
            EnumFlowerColor.DARK_ORANGE),
    GAILLARDIA(
            "gaillardia",
            "gaillardia",
            "aristata",
            EnumFlowerType.GAILLARDIA,
            EnumFlowerColor.DARK_ORANGE,
            EnumFlowerColor.YELLOW),
    AURICULA("auricula", "primula", "auricula", EnumFlowerType.AURICULA, EnumFlowerColor.RED, EnumFlowerColor.YELLOW),
    CAMELLIA("camellia", "camellia", "japonica", EnumFlowerType.CAMELLIA, EnumFlowerColor.CRIMSON),
    GOLDENROD("goldenrod", "solidago", "canadensis", EnumFlowerType.GOLDENROD, EnumFlowerColor.GOLD),
    ALTHEA(
            "althea",
            "althaea",
            "officinalis",
            EnumFlowerType.ALTHEA,
            EnumFlowerColor.THISTLE,
            EnumFlowerColor.MEDIUM_ORCHID),
    PENSTEMON(
            "penstemon",
            "penstemon",
            "digitalis",
            EnumFlowerType.PENSTEMON,
            EnumFlowerColor.MEDIUM_ORCHID,
            EnumFlowerColor.THISTLE),
    DELPHINIUM("delphinium", "delphinium", "staphisagria", EnumFlowerType.DELPHINIUM, EnumFlowerColor.DARK_SLATE_BLUE),
    HOLLYHOCK("hollyhock", "Alcea", "rosea", EnumFlowerType.HOLLYHOCK, EnumFlowerColor.BLACK, EnumFlowerColor.GOLD);

    protected EnumFlowerColor stemColor;
    protected EnumAllele.Fertility fert;
    protected EnumAllele.Lifespan life;
    protected EnumAllele.Sappiness sap;
    protected IFlowerType type;
    protected String name;
    protected String binomial;
    protected String branchName;
    protected EnumFlowerColor primaryColor;
    protected EnumFlowerColor secondaryColor;
    protected EnumTemperature temperature;
    protected EnumAcidity pH;
    protected EnumMoisture moisture;
    protected EnumTolerance tempTolerance;
    protected EnumTolerance pHTolerance;
    protected EnumTolerance moistureTolerance;
    protected List<IAllele[]> variantTemplates;
    protected IClassification branch;

    FlowerSpecies(String name, String branch, String binomial, IFlowerType type, EnumFlowerColor colour) {
        this(name, branch, binomial, type, colour, colour);
    }

    FlowerSpecies(
            String name,
            String branch,
            String binomial,
            IFlowerType type,
            EnumFlowerColor primaryColor,
            EnumFlowerColor secondaryColor) {
        this.name = name;
        this.binomial = binomial;
        this.type = type;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        stemColor = EnumFlowerColor.GREEN;
        temperature = EnumTemperature.NORMAL;
        pH = EnumAcidity.NEUTRAL;
        moisture = EnumMoisture.NORMAL;
        tempTolerance = EnumTolerance.BOTH_1;
        pHTolerance = EnumTolerance.NONE;
        moistureTolerance = EnumTolerance.NONE;
        variantTemplates = new ArrayList<>();
        branchName = branch;
    }

    public static void setupVariants() {
        FlowerSpecies.DANDELION
                .setTraits(EnumAllele.Fertility.HIGH, EnumAllele.Lifespan.SHORTENED, EnumAllele.Sappiness.LOWER)
                .setPH(EnumAcidity.NEUTRAL, EnumTolerance.BOTH_1)
                .setMoisture(EnumMoisture.NORMAL, EnumTolerance.BOTH_1)
                .setStemColor(EnumFlowerColor.GREEN);

        FlowerSpecies.POPPY
                .setTraits(EnumAllele.Fertility.HIGH, EnumAllele.Lifespan.SHORTER, EnumAllele.Sappiness.AVERAGE)
                .setPH(EnumAcidity.NEUTRAL, EnumTolerance.DOWN_1)
                .setMoisture(EnumMoisture.NORMAL, EnumTolerance.DOWN_1)
                .setTemperature(EnumTemperature.WARM, EnumTolerance.BOTH_2)
                .setStemColor(EnumFlowerColor.GREEN);

        FlowerSpecies.ORCHID
                .setTraits(EnumAllele.Fertility.NORMAL, EnumAllele.Lifespan.LONG, EnumAllele.Sappiness.LOW)
                .setPH(EnumAcidity.ACID, EnumTolerance.NONE)
                .setMoisture(EnumMoisture.NORMAL, EnumTolerance.BOTH_1)
                .setStemColor(EnumFlowerColor.GREEN);

        FlowerSpecies.ALLIUM
                .setTraits(EnumAllele.Fertility.NORMAL, EnumAllele.Lifespan.NORMAL, EnumAllele.Sappiness.LOW)
                .setPH(EnumAcidity.ALKALINE, EnumTolerance.DOWN_1)
                .setMoisture(EnumMoisture.NORMAL, EnumTolerance.DOWN_1);

        FlowerSpecies.BLUET
                .setTraits(EnumAllele.Fertility.LOW, EnumAllele.Lifespan.NORMAL, EnumAllele.Sappiness.LOWER)
                .setPH(EnumAcidity.NEUTRAL, EnumTolerance.DOWN_1)
                .setMoisture(EnumMoisture.DAMP, EnumTolerance.NONE)
                .setStemColor(EnumFlowerColor.OLIVE_DRAB);

        FlowerSpecies.TULIP
                .setTraits(EnumAllele.Fertility.NORMAL, EnumAllele.Lifespan.LONG, EnumAllele.Sappiness.AVERAGE)
                .setMoisture(EnumMoisture.NORMAL, EnumTolerance.BOTH_1)
                .setStemColor(EnumFlowerColor.OLIVE_DRAB);

        FlowerSpecies.DAISY
                .setTraits(EnumAllele.Fertility.NORMAL, EnumAllele.Lifespan.NORMAL, EnumAllele.Sappiness.LOW)
                .setPH(EnumAcidity.NEUTRAL, EnumTolerance.DOWN_1)
                .setMoisture(EnumMoisture.NORMAL, EnumTolerance.DOWN_1)
                .setTemperature(EnumTemperature.WARM, EnumTolerance.BOTH_2)
                .setStemColor(EnumFlowerColor.OLIVE_DRAB);

        FlowerSpecies.CORNFLOWER
                .setTraits(EnumAllele.Fertility.HIGH, EnumAllele.Lifespan.SHORTER, EnumAllele.Sappiness.LOW)
                .setMutation(FlowerSpecies.DANDELION, FlowerSpecies.TULIP, 10)
                .setPH(EnumAcidity.NEUTRAL, EnumTolerance.BOTH_1)
                .setStemColor(EnumFlowerColor.OLIVE_DRAB);

        FlowerSpecies.PANSY
                .setTraits(EnumAllele.Fertility.NORMAL, EnumAllele.Lifespan.SHORTENED, EnumAllele.Sappiness.AVERAGE)
                .setMutation(FlowerSpecies.TULIP, FlowerSpecies.VIOLA, 5)
                .setPH(EnumAcidity.ACID, EnumTolerance.NONE)
                .setTemperature(EnumTemperature.WARM, EnumTolerance.DOWN_1)
                .setStemColor(EnumFlowerColor.SEA_GREEN);

        FlowerSpecies.IRIS
                .setTraits(EnumAllele.Fertility.HIGH, EnumAllele.Lifespan.NORMAL, EnumAllele.Sappiness.AVERAGE)
                .setMutation(FlowerSpecies.ORCHID, FlowerSpecies.VIOLA, 10)
                .setPH(EnumAcidity.ACID, EnumTolerance.NONE)
                .setTemperature(EnumTemperature.WARM, EnumTolerance.DOWN_1)
                .setStemColor(EnumFlowerColor.SEA_GREEN);

        FlowerSpecies.LAVENDER
                .setTraits(EnumAllele.Fertility.HIGH, EnumAllele.Lifespan.NORMAL, EnumAllele.Sappiness.LOW)
                .setMutation(FlowerSpecies.ALLIUM, FlowerSpecies.VIOLA, 10)
                .setPH(EnumAcidity.NEUTRAL, EnumTolerance.UP_1)
                .setMoisture(EnumMoisture.NORMAL, EnumTolerance.DOWN_1)
                .setTemperature(EnumTemperature.WARM, EnumTolerance.DOWN_1)
                .setStemColor(EnumFlowerColor.GREEN);

        FlowerSpecies.VIOLA
                .setTraits(EnumAllele.Fertility.NORMAL, EnumAllele.Lifespan.SHORTENED, EnumAllele.Sappiness.AVERAGE)
                .setMutation(FlowerSpecies.ORCHID, FlowerSpecies.POPPY, 15)
                .setPH(EnumAcidity.ACID, EnumTolerance.NONE)
                .setMoisture(EnumMoisture.NORMAL, EnumTolerance.DOWN_1)
                .setStemColor(EnumFlowerColor.OLIVE_DRAB);

        FlowerSpecies.DAFFODIL
                .setTraits(EnumAllele.Fertility.HIGH, EnumAllele.Lifespan.ELONGATED, EnumAllele.Sappiness.AVERAGE)
                .setMutation(FlowerSpecies.DANDELION, FlowerSpecies.POPPY, 10)
                .setPH(EnumAcidity.NEUTRAL, EnumTolerance.DOWN_1)
                .setStemColor(EnumFlowerColor.GREEN);

        FlowerSpecies.ASTER
                .setTraits(EnumAllele.Fertility.NORMAL, EnumAllele.Lifespan.NORMAL, EnumAllele.Sappiness.HIGHER)
                .setMutation(FlowerSpecies.DAISY, FlowerSpecies.TULIP, 10)
                .setPH(EnumAcidity.NEUTRAL, EnumTolerance.BOTH_1)
                .setStemColor(EnumFlowerColor.GREEN);

        FlowerSpecies.LILAC
                .setTraits(EnumAllele.Fertility.LOW, EnumAllele.Lifespan.LONGER, EnumAllele.Sappiness.AVERAGE)
                .setPH(EnumAcidity.ALKALINE, EnumTolerance.DOWN_1)
                .setStemColor(EnumFlowerColor.OLIVE_DRAB);

        FlowerSpecies.ROSE
                .setTraits(EnumAllele.Fertility.LOW, EnumAllele.Lifespan.LONGER, EnumAllele.Sappiness.HIGH)
                .setPH(EnumAcidity.ACID, EnumTolerance.UP_1)
                .setStemColor(EnumFlowerColor.GREEN);

        FlowerSpecies.PEONY
                .setTraits(EnumAllele.Fertility.LOW, EnumAllele.Lifespan.LONG, EnumAllele.Sappiness.AVERAGE)
                .setPH(EnumAcidity.ALKALINE, EnumTolerance.DOWN_1)
                .setMoisture(EnumMoisture.NORMAL, EnumTolerance.UP_1)
                .setStemColor(EnumFlowerColor.DARK_GREEN);

        FlowerSpecies.MARIGOLD
                .setTraits(EnumAllele.Fertility.HIGH, EnumAllele.Lifespan.SHORTER, EnumAllele.Sappiness.AVERAGE)
                .setMutation(FlowerSpecies.DAISY, FlowerSpecies.DANDELION, 10)
                .setPH(EnumAcidity.NEUTRAL, EnumTolerance.DOWN_1)
                .setMoisture(EnumMoisture.NORMAL, EnumTolerance.UP_1)
                .setTemperature(EnumTemperature.WARM, EnumTolerance.BOTH_2)
                .setStemColor(EnumFlowerColor.GREEN);

        FlowerSpecies.HYDRANGEA
                .setTraits(EnumAllele.Fertility.LOW, EnumAllele.Lifespan.LONGER, EnumAllele.Sappiness.HIGH)
                .setMutation(FlowerSpecies.PEONY, FlowerSpecies.BLUET, 10)
                .setPH(EnumAcidity.NEUTRAL, EnumTolerance.BOTH_1)
                .setMoisture(EnumMoisture.DAMP, EnumTolerance.NONE)
                .setStemColor(EnumFlowerColor.DARK_GREEN);

        FlowerSpecies.FOXGLOVE
                .setTraits(EnumAllele.Fertility.LOW, EnumAllele.Lifespan.SHORTENED, EnumAllele.Sappiness.LOW)
                .setMutation(FlowerSpecies.LILAC, FlowerSpecies.ZINNIA, 5)
                .setPH(EnumAcidity.NEUTRAL, EnumTolerance.DOWN_1)
                .setMoisture(EnumMoisture.NORMAL, EnumTolerance.UP_1)
                .setStemColor(EnumFlowerColor.DARK_GREEN);

        FlowerSpecies.DAHLIA
                .setTraits(EnumAllele.Fertility.NORMAL, EnumAllele.Lifespan.NORMAL, EnumAllele.Sappiness.AVERAGE)
                .setMutation(FlowerSpecies.DAISY, FlowerSpecies.ALLIUM, 15)
                .setPH(EnumAcidity.NEUTRAL, EnumTolerance.DOWN_1)
                .setMoisture(EnumMoisture.NORMAL, EnumTolerance.UP_1)
                .setTemperature(EnumTemperature.NORMAL, EnumTolerance.BOTH_2)
                .setStemColor(EnumFlowerColor.OLIVE_DRAB);

        FlowerSpecies.CHRYSANTHEMUM
                .setTraits(EnumAllele.Fertility.HIGH, EnumAllele.Lifespan.LONG, EnumAllele.Sappiness.HIGH)
                .setMutation(FlowerSpecies.GERANIUM, FlowerSpecies.ROSE, 10)
                .setPH(EnumAcidity.NEUTRAL, EnumTolerance.DOWN_1)
                .setMoisture(EnumMoisture.NORMAL, EnumTolerance.DOWN_1)
                .setStemColor(EnumFlowerColor.MEDIUM_SEA_GREEN);

        FlowerSpecies.CARNATION
                .setTraits(EnumAllele.Fertility.NORMAL, EnumAllele.Lifespan.NORMAL, EnumAllele.Sappiness.HIGH)
                .setMutation(FlowerSpecies.DIANTHUS, FlowerSpecies.ROSE, 5)
                .setPH(EnumAcidity.ALKALINE, EnumTolerance.DOWN_1)
                .setStemColor(EnumFlowerColor.SEA_GREEN);

        FlowerSpecies.ZINNIA
                .setTraits(EnumAllele.Fertility.NORMAL, EnumAllele.Lifespan.SHORTER, EnumAllele.Sappiness.AVERAGE)
                .setMutation(FlowerSpecies.DAHLIA, FlowerSpecies.MARIGOLD, 5)
                .setPH(EnumAcidity.NEUTRAL, EnumTolerance.BOTH_1)
                .setMoisture(EnumMoisture.NORMAL, EnumTolerance.DOWN_1)
                .setTemperature(EnumTemperature.NORMAL, EnumTolerance.BOTH_2)
                .setStemColor(EnumFlowerColor.MEDIUM_SEA_GREEN);

        FlowerSpecies.PRIMROSE
                .setTraits(EnumAllele.Fertility.NORMAL, EnumAllele.Lifespan.LONG, EnumAllele.Sappiness.AVERAGE)
                .setMutation(FlowerSpecies.CHRYSANTHEMUM, FlowerSpecies.AURICULA, 5)
                .setPH(EnumAcidity.ACID, EnumTolerance.UP_1)
                .setMoisture(EnumMoisture.NORMAL, EnumTolerance.UP_1)
                .setStemColor(EnumFlowerColor.GREEN);

        FlowerSpecies.AZALEA
                .setTraits(EnumAllele.Fertility.NORMAL, EnumAllele.Lifespan.NORMAL, EnumAllele.Sappiness.AVERAGE)
                .setMutation(FlowerSpecies.ORCHID, FlowerSpecies.GERANIUM, 5)
                .setPH(EnumAcidity.ACID, EnumTolerance.NONE)
                .setStemColor(EnumFlowerColor.GREEN);

        FlowerSpecies.GERANIUM
                .setTraits(EnumAllele.Fertility.LOW, EnumAllele.Lifespan.LONG, EnumAllele.Sappiness.LOW)
                .setMutation(FlowerSpecies.TULIP, FlowerSpecies.ORCHID, 15)
                .setPH(EnumAcidity.NEUTRAL, EnumTolerance.BOTH_1)
                .setTemperature(EnumTemperature.WARM, EnumTolerance.BOTH_1)
                .setStemColor(EnumFlowerColor.MEDIUM_SEA_GREEN);

        FlowerSpecies.LILY
                .setTraits(EnumAllele.Fertility.LOW, EnumAllele.Lifespan.LONG, EnumAllele.Sappiness.LOW)
                .setMutation(FlowerSpecies.TULIP, FlowerSpecies.CHRYSANTHEMUM, 5)
                .setPH(EnumAcidity.NEUTRAL, EnumTolerance.DOWN_1)
                .setTemperature(EnumTemperature.WARM, EnumTolerance.BOTH_1)
                .setStemColor(EnumFlowerColor.GREEN);

        FlowerSpecies.YARROW
                .setTraits(EnumAllele.Fertility.HIGH, EnumAllele.Lifespan.NORMAL, EnumAllele.Sappiness.LOW)
                .setMutation(FlowerSpecies.DANDELION, FlowerSpecies.ORCHID, 10)
                .setPH(EnumAcidity.ACID, EnumTolerance.UP_1)
                .setMoisture(EnumMoisture.NORMAL, EnumTolerance.DOWN_1)
                .setStemColor(EnumFlowerColor.DARK_OLIVE_GREEN);

        FlowerSpecies.PETUNIA
                .setTraits(EnumAllele.Fertility.NORMAL, EnumAllele.Lifespan.SHORTER, EnumAllele.Sappiness.AVERAGE)
                .setMutation(FlowerSpecies.TULIP, FlowerSpecies.DAHLIA, 5)
                .setPH(EnumAcidity.NEUTRAL, EnumTolerance.DOWN_1)
                .setMoisture(EnumMoisture.NORMAL, EnumTolerance.UP_1)
                .setTemperature(EnumTemperature.WARM, EnumTolerance.UP_1)
                .setStemColor(EnumFlowerColor.GREEN);

        FlowerSpecies.AGAPANTHUS
                .setTraits(EnumAllele.Fertility.NORMAL, EnumAllele.Lifespan.NORMAL, EnumAllele.Sappiness.LOW)
                .setMutation(FlowerSpecies.ALLIUM, FlowerSpecies.GERANIUM, 5)
                .setPH(EnumAcidity.NEUTRAL, EnumTolerance.BOTH_1)
                .setMoisture(EnumMoisture.NORMAL, EnumTolerance.DOWN_1)
                .setTemperature(EnumTemperature.WARM, EnumTolerance.BOTH_1)
                .setStemColor(EnumFlowerColor.DARK_OLIVE_GREEN);

        FlowerSpecies.FUCHSIA
                .setTraits(EnumAllele.Fertility.NORMAL, EnumAllele.Lifespan.SHORTENED, EnumAllele.Sappiness.AVERAGE)
                .setMutation(FlowerSpecies.FOXGLOVE, FlowerSpecies.DAHLIA, 5)
                .setPH(EnumAcidity.NEUTRAL, EnumTolerance.BOTH_1)
                .setMoisture(EnumMoisture.NORMAL, EnumTolerance.UP_1)
                .setTemperature(EnumTemperature.WARM, EnumTolerance.BOTH_1)
                .setStemColor(EnumFlowerColor.SEA_GREEN);

        FlowerSpecies.DIANTHUS
                .setTraits(EnumAllele.Fertility.NORMAL, EnumAllele.Lifespan.SHORT, EnumAllele.Sappiness.HIGH)
                .setMutation(FlowerSpecies.TULIP, FlowerSpecies.POPPY, 15)
                .setPH(EnumAcidity.ALKALINE, EnumTolerance.DOWN_1)
                .setMoisture(EnumMoisture.NORMAL, EnumTolerance.DOWN_1)
                .setTemperature(EnumTemperature.NORMAL, EnumTolerance.BOTH_2)
                .setStemColor(EnumFlowerColor.OLIVE_DRAB);

        FlowerSpecies.FORGET
                .setTraits(EnumAllele.Fertility.NORMAL, EnumAllele.Lifespan.SHORT, EnumAllele.Sappiness.LOWER)
                .setMutation(FlowerSpecies.ORCHID, FlowerSpecies.BLUET, 10)
                .setPH(EnumAcidity.ACID, EnumTolerance.NONE)
                .setMoisture(EnumMoisture.NORMAL, EnumTolerance.UP_1)
                .setTemperature(EnumTemperature.NORMAL, EnumTolerance.UP_1)
                .setStemColor(EnumFlowerColor.GREEN);

        FlowerSpecies.ANEMONE
                .setTraits(EnumAllele.Fertility.HIGH, EnumAllele.Lifespan.NORMAL, EnumAllele.Sappiness.LOW)
                .setMutation(FlowerSpecies.AQUILEGIA, FlowerSpecies.ROSE, 5)
                .setPH(EnumAcidity.NEUTRAL, EnumTolerance.BOTH_1)
                .setMoisture(EnumMoisture.NORMAL, EnumTolerance.UP_1)
                .setStemColor(EnumFlowerColor.DARK_OLIVE_GREEN);

        FlowerSpecies.AQUILEGIA
                .setTraits(EnumAllele.Fertility.NORMAL, EnumAllele.Lifespan.NORMAL, EnumAllele.Sappiness.AVERAGE)
                .setMutation(FlowerSpecies.IRIS, FlowerSpecies.POPPY, 5)
                .setMoisture(EnumMoisture.NORMAL, EnumTolerance.UP_1)
                .setStemColor(EnumFlowerColor.MEDIUM_SEA_GREEN);

        FlowerSpecies.EDELWEISS
                .setTraits(EnumAllele.Fertility.LOW, EnumAllele.Lifespan.NORMAL, EnumAllele.Sappiness.LOWEST)
                .setMutation(FlowerSpecies.PEONY, FlowerSpecies.BLUET, 5)
                .setPH(EnumAcidity.ALKALINE, EnumTolerance.DOWN_1)
                .setMoisture(EnumMoisture.NORMAL, EnumTolerance.DOWN_1)
                .setTemperature(EnumTemperature.NORMAL, EnumTolerance.DOWN_1)
                .setStemColor(EnumFlowerColor.DARK_OLIVE_GREEN);

        FlowerSpecies.SCABIOUS
                .setTraits(EnumAllele.Fertility.NORMAL, EnumAllele.Lifespan.SHORTENED, EnumAllele.Sappiness.LOW)
                .setMutation(FlowerSpecies.ALLIUM, FlowerSpecies.CORNFLOWER, 5)
                .setPH(EnumAcidity.NEUTRAL, EnumTolerance.UP_1)
                .setTemperature(EnumTemperature.NORMAL, EnumTolerance.DOWN_1)
                .setStemColor(EnumFlowerColor.OLIVE_DRAB);

        FlowerSpecies.CONEFLOWER
                .setTraits(EnumAllele.Fertility.NORMAL, EnumAllele.Lifespan.NORMAL, EnumAllele.Sappiness.HIGHER)
                .setMutation(FlowerSpecies.TULIP, FlowerSpecies.CORNFLOWER, 5)
                .setPH(EnumAcidity.NEUTRAL, EnumTolerance.BOTH_1)
                .setStemColor(EnumFlowerColor.DARK_OLIVE_GREEN);

        FlowerSpecies.GAILLARDIA
                .setTraits(EnumAllele.Fertility.HIGH, EnumAllele.Lifespan.LONG, EnumAllele.Sappiness.HIGHER)
                .setMutation(FlowerSpecies.DANDELION, FlowerSpecies.MARIGOLD, 5)
                .setPH(EnumAcidity.NEUTRAL, EnumTolerance.BOTH_1)
                .setMoisture(EnumMoisture.DAMP, EnumTolerance.DOWN_1)
                .setTemperature(EnumTemperature.NORMAL, EnumTolerance.BOTH_2)
                .setStemColor(EnumFlowerColor.OLIVE_DRAB);

        FlowerSpecies.AURICULA
                .setTraits(EnumAllele.Fertility.NORMAL, EnumAllele.Lifespan.ELONGATED, EnumAllele.Sappiness.HIGH)
                .setMutation(FlowerSpecies.POPPY, FlowerSpecies.GERANIUM, 10)
                .setPH(EnumAcidity.ACID, EnumTolerance.UP_1)
                .setMoisture(EnumMoisture.NORMAL, EnumTolerance.UP_1)
                .setStemColor(EnumFlowerColor.DARK_OLIVE_GREEN);

        FlowerSpecies.CAMELLIA
                .setTraits(EnumAllele.Fertility.NORMAL, EnumAllele.Lifespan.NORMAL, EnumAllele.Sappiness.HIGH)
                .setMutation(FlowerSpecies.HYDRANGEA, FlowerSpecies.ROSE, 5)
                .setPH(EnumAcidity.ACID, EnumTolerance.NONE)
                .setMoisture(EnumMoisture.DAMP, EnumTolerance.NONE)
                .setTemperature(EnumTemperature.WARM, EnumTolerance.UP_1)
                .setStemColor(EnumFlowerColor.DARK_OLIVE_GREEN);

        FlowerSpecies.GOLDENROD
                .setTraits(EnumAllele.Fertility.HIGH, EnumAllele.Lifespan.NORMAL, EnumAllele.Sappiness.HIGHER)
                .setMutation(FlowerSpecies.LILAC, FlowerSpecies.MARIGOLD, 10)
                .setPH(EnumAcidity.NEUTRAL, EnumTolerance.BOTH_1)
                .setStemColor(EnumFlowerColor.MEDIUM_SEA_GREEN);

        FlowerSpecies.ALTHEA
                .setTraits(EnumAllele.Fertility.LOW, EnumAllele.Lifespan.ELONGATED, EnumAllele.Sappiness.HIGH)
                .setMutation(FlowerSpecies.HYDRANGEA, FlowerSpecies.IRIS, 5)
                .setPH(EnumAcidity.NEUTRAL, EnumTolerance.DOWN_1)
                .setTemperature(EnumTemperature.WARM, EnumTolerance.BOTH_1)
                .setStemColor(EnumFlowerColor.DARK_GREEN);

        FlowerSpecies.PENSTEMON
                .setTraits(EnumAllele.Fertility.LOW, EnumAllele.Lifespan.LONG, EnumAllele.Sappiness.LOW)
                .setMutation(FlowerSpecies.PEONY, FlowerSpecies.LILAC, 5)
                .setMoisture(EnumMoisture.DRY, EnumTolerance.UP_1)
                .setTemperature(EnumTemperature.WARM, EnumTolerance.UP_1)
                .setStemColor(EnumFlowerColor.OLIVE_DRAB);

        FlowerSpecies.DELPHINIUM
                .setTraits(EnumAllele.Fertility.LOW, EnumAllele.Lifespan.LONGER, EnumAllele.Sappiness.LOW)
                .setMutation(FlowerSpecies.LILAC, FlowerSpecies.BLUET, 5)
                .setMoisture(EnumMoisture.DAMP, EnumTolerance.DOWN_1)
                .setTemperature(EnumTemperature.NORMAL, EnumTolerance.DOWN_1)
                .setStemColor(EnumFlowerColor.DARK_SEA_GREEN);

        FlowerSpecies.HOLLYHOCK
                .setTraits(EnumAllele.Fertility.LOW, EnumAllele.Lifespan.LONG, EnumAllele.Sappiness.HIGH)
                .setMutation(FlowerSpecies.DELPHINIUM, FlowerSpecies.LAVENDER, 5)
                .setPH(EnumAcidity.NEUTRAL, EnumTolerance.UP_1)
                .setStemColor(EnumFlowerColor.GREEN);

        IFlowerRoot flowerRoot = BotanyCore.getFlowerRoot();
        flowerRoot.addConversion(new ItemStack(Blocks.yellow_flower, 1, 0), FlowerSpecies.DANDELION.getTemplate());
        flowerRoot.addConversion(new ItemStack(Blocks.red_flower, 1, 0), FlowerSpecies.POPPY.getTemplate());
        flowerRoot.addConversion(new ItemStack(Blocks.red_flower, 1, 1), FlowerSpecies.ORCHID.getTemplate());
        flowerRoot.addConversion(new ItemStack(Blocks.red_flower, 1, 2), FlowerSpecies.ALLIUM.getTemplate());
        flowerRoot.addConversion(new ItemStack(Blocks.red_flower, 1, 3), FlowerSpecies.BLUET.getTemplate());
        flowerRoot.addConversion(new ItemStack(Blocks.red_flower, 1, 7), FlowerSpecies.TULIP.getTemplate());
        flowerRoot.addConversion(new ItemStack(Blocks.red_flower, 1, 8), FlowerSpecies.DAISY.getTemplate());
        flowerRoot.addConversion(new ItemStack(Blocks.double_plant, 1, 1), FlowerSpecies.LILAC.getTemplate());
        flowerRoot.addConversion(new ItemStack(Blocks.double_plant, 1, 4), FlowerSpecies.ROSE.getTemplate());
        flowerRoot.addConversion(new ItemStack(Blocks.double_plant, 1, 5), FlowerSpecies.PEONY.getTemplate());
        flowerRoot.addConversion(
                new ItemStack(Blocks.red_flower, 1, 6), FlowerSpecies.TULIP.addVariant(EnumFlowerColor.WHITE));
        flowerRoot.addConversion(
                new ItemStack(Blocks.red_flower, 1, 4), FlowerSpecies.TULIP.addVariant(EnumFlowerColor.CRIMSON));
        flowerRoot.addConversion(
                new ItemStack(Blocks.red_flower, 1, 5), FlowerSpecies.TULIP.addVariant(EnumFlowerColor.DARK_ORANGE));

        for (FlowerSpecies species : values()) {
            String scientific = species.branchName.substring(0, 1).toUpperCase()
                    + species.branchName.substring(1).toLowerCase();
            String uid = "flowers." + species.branchName.toLowerCase();
            IClassification branch = AlleleManager.alleleRegistry.getClassification("genus." + uid);
            if (branch == null) {
                branch = AlleleManager.alleleRegistry.createAndRegisterClassification(
                        IClassification.EnumClassLevel.GENUS, uid, scientific);
            }

            species.branch = branch;
            branch.addMemberSpecies(species);
        }
    }

    private FlowerSpecies setStemColor(EnumFlowerColor green) {
        stemColor = green;
        return this;
    }

    private FlowerSpecies setTraits(
            EnumAllele.Fertility fertility, EnumAllele.Lifespan shortened, EnumAllele.Sappiness lower) {
        fert = fertility;
        life = shortened;
        sap = lower;
        return this;
    }

    private FlowerSpecies setMutation(FlowerSpecies dandelion2, FlowerSpecies tulip2, int chance) {
        BotanyCore.getFlowerRoot().registerMutation(new FlowerMutation(dandelion2, tulip2, getTemplate(), chance));
        return this;
    }

    private IAllele[] addVariant(EnumFlowerColor a, EnumFlowerColor b) {
        IAllele[] template = getTemplate();
        template[EnumFlowerChromosome.PRIMARY.ordinal()] = a.getAllele();
        template[EnumFlowerChromosome.SECONDARY.ordinal()] = b.getAllele();
        variantTemplates.add(template);
        return template;
    }

    private FlowerSpecies setTemperature(EnumTemperature temperature, EnumTolerance tolerance) {
        this.temperature = temperature;
        tempTolerance = tolerance;
        return this;
    }

    private FlowerSpecies setPH(EnumAcidity temperature, EnumTolerance tolerance) {
        pH = temperature;
        pHTolerance = tolerance;
        return this;
    }

    private FlowerSpecies setMoisture(EnumMoisture temperature, EnumTolerance tolerance) {
        moisture = temperature;
        moistureTolerance = tolerance;
        return this;
    }

    private IAllele[] addVariant(EnumFlowerColor a) {
        return addVariant(a, a);
    }

    public List<IAllele[]> getVariants() {
        return variantTemplates;
    }

    @Override
    public String getName() {
        return I18N.localise("botany.flower." + name);
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public EnumTemperature getTemperature() {
        return temperature;
    }

    @Override
    public EnumHumidity getHumidity() {
        return EnumHumidity.values()[getMoisture().ordinal()];
    }

    @Override
    public EnumAcidity getPH() {
        return pH;
    }

    @Override
    public EnumMoisture getMoisture() {
        return moisture;
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

    public void setBranch(IClassification branch) {
        this.branch = branch;
    }

    @Override
    public String getUID() {
        return "botany.flowers.species." + toString().toLowerCase();
    }

    @Override
    public boolean isDominant() {
        return false;
    }

    public IAllele[] getTemplate() {
        forestry.core.genetics.alleles.AlleleHelper alleleHelper = forestry.core.genetics.alleles.AlleleHelper.instance;
        IAllele[] template = FlowerTemplates.getDefaultTemplate();
        template[0] = this;
        alleleHelper.set(template, EnumFlowerChromosome.PRIMARY, AlleleHelper.getAllele(primaryColor));
        alleleHelper.set(template, EnumFlowerChromosome.SECONDARY, AlleleHelper.getAllele(secondaryColor));
        alleleHelper.set(template, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, AlleleHelper.getAllele(tempTolerance));
        alleleHelper.set(template, EnumFlowerChromosome.PH_TOLERANCE, AlleleHelper.getAllele(pHTolerance));
        alleleHelper.set(template, EnumFlowerChromosome.HUMIDITY_TOLERANCE, AlleleHelper.getAllele(moistureTolerance));
        alleleHelper.set(template, EnumFlowerChromosome.FERTILITY, AlleleHelper.getAllele(fert));
        alleleHelper.set(template, EnumFlowerChromosome.LIFESPAN, AlleleHelper.getAllele(life));
        alleleHelper.set(template, EnumFlowerChromosome.SAPPINESS, AlleleHelper.getAllele(sap));
        alleleHelper.set(template, EnumFlowerChromosome.STEM, AlleleHelper.getAllele(stemColor));
        return template;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIconProvider getIconProvider() {
        return null;
    }

    @Override
    public ISpeciesRoot getRoot() {
        return BotanyCore.getFlowerRoot();
    }

    @Override
    public int getIconColour(int renderPass) {
        return 0;
    }

    @Override
    public int getComplexity() {
        return 0;
    }

    @Override
    public float getResearchSuitability(ItemStack itemstack) {
        return 0.0f;
    }

    @Override
    public ItemStack[] getResearchBounty(World world, GameProfile researcher, IIndividual individual, int bountyLevel) {
        return null;
    }

    @Override
    public IFlowerType getType() {
        return type;
    }

    @Override
    public String getUnlocalizedName() {
        return getUID();
    }
}
