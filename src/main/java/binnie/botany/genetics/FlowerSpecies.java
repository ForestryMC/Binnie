// 
// Decompiled by Procyon v0.5.30
// 

package binnie.botany.genetics;

import forestry.api.genetics.IIndividual;
import com.mojang.authlib.GameProfile;
import net.minecraft.world.World;
import forestry.api.genetics.ISpeciesRoot;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.IIconProvider;
import binnie.Binnie;
import forestry.api.core.EnumHumidity;
import binnie.botany.api.EnumFlowerChromosome;
import java.util.ArrayList;
import forestry.api.genetics.IMutation;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.AlleleManager;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Blocks;
import binnie.botany.core.BotanyCore;
import forestry.api.genetics.IClassification;
import forestry.api.genetics.IAllele;
import java.util.List;
import forestry.api.genetics.EnumTolerance;
import binnie.botany.api.EnumMoisture;
import binnie.botany.api.EnumAcidity;
import forestry.api.core.EnumTemperature;
import binnie.botany.api.IFlowerType;
import binnie.core.genetics.ForestryAllele;
import binnie.botany.api.IAlleleFlowerSpecies;

public enum FlowerSpecies implements IAlleleFlowerSpecies
{
	Dandelion("Dandelion", "taraxacum", "officinale", EnumFlowerType.Dandelion, EnumFlowerColor.Yellow),
	Poppy("Poppy", "papaver", "rhoeas", EnumFlowerType.Poppy, EnumFlowerColor.Red),
	Orchid("Orchid", "vanda", "coerulea", EnumFlowerType.Orchid, EnumFlowerColor.DeepSkyBlue),
	Allium("Allium", "allium", "giganteum", EnumFlowerType.Allium, EnumFlowerColor.MediumPurple),
	Bluet("Bluet", "houstonia", "caerulea", EnumFlowerType.Bluet, EnumFlowerColor.Lavender, EnumFlowerColor.Khaki),
	Tulip("Tulip", "tulipa", "agenensis", EnumFlowerType.Tulip, EnumFlowerColor.Violet),
	Daisy("Daisy", "leucanthemum", "vulgare", EnumFlowerType.Daisy, EnumFlowerColor.White, EnumFlowerColor.Yellow),
	Cornflower("Cornflower", "centaurea", "cyanus", EnumFlowerType.Cornflower, EnumFlowerColor.SkyBlue),
	Pansy("Pansy", "viola", "tricolor", EnumFlowerType.Pansy, EnumFlowerColor.Pink, EnumFlowerColor.Purple),
	Iris("Iris", "iris", "germanica", EnumFlowerType.Iris, EnumFlowerColor.LightGray, EnumFlowerColor.Purple),
	Lavender("Lavender", "Lavandula", "angustifolia", EnumFlowerType.Lavender, EnumFlowerColor.MediumOrchid),
	Viola("Viola", "viola", "odorata", EnumFlowerType.Viola, EnumFlowerColor.MediumPurple, EnumFlowerColor.SlateBlue),
	Daffodil("Daffodil", "narcissus", "pseudonarcissus", EnumFlowerType.Daffodil, EnumFlowerColor.Yellow, EnumFlowerColor.Gold),
	Dahlia("Dahlia", "dahlia", "variabilis", EnumFlowerType.Dahlia, EnumFlowerColor.HotPink, EnumFlowerColor.DeepPink),
	Peony("Peony", "paeonia", "suffruticosa", EnumFlowerType.Peony, EnumFlowerColor.Thistle),
	Rose("Rose", "rosa", "rubiginosa", EnumFlowerType.Rose, EnumFlowerColor.Red),
	Lilac("Lilac", "syringa", "vulgaris", EnumFlowerType.Lilac, EnumFlowerColor.Plum),
	Hydrangea("Hydrangea", "hydrangea", "macrophylla", EnumFlowerType.Hydrangea, EnumFlowerColor.DeepSkyBlue),
	Foxglove("Foxglove", "digitalis", "purpurea", EnumFlowerType.Foxglove, EnumFlowerColor.HotPink),
	Zinnia("Zinnia", "zinnia", "elegans", EnumFlowerType.Zinnia, EnumFlowerColor.MediumVioletRed, EnumFlowerColor.Yellow),
	Chrysanthemum("Chrysanthemum", "chrysanthemum", "\u00ef?? grandiflorum", EnumFlowerType.Mums, EnumFlowerColor.Violet),
	Marigold("Marigold", "calendula", "officinalis", EnumFlowerType.Marigold, EnumFlowerColor.Gold, EnumFlowerColor.DarkOrange),
	Geranium("Geranium", "geranium", "maderense", EnumFlowerType.Geranium, EnumFlowerColor.DeepPink),
	Azalea("Azalea", "rhododendrons", "aurigeranum", EnumFlowerType.Azalea, EnumFlowerColor.HotPink),
	Primrose("Primrose", "primula", "vulgaris", EnumFlowerType.Primrose, EnumFlowerColor.Red, EnumFlowerColor.Gold),
	Aster("Aster", "aster", "amellus", EnumFlowerType.Aster, EnumFlowerColor.MediumPurple, EnumFlowerColor.Goldenrod),
	Carnation("Carnation", "dianthus", "caryophyllus", EnumFlowerType.Carnation, EnumFlowerColor.Crimson, EnumFlowerColor.White),
	Lily("Lily", "lilium", "auratum", EnumFlowerType.Lily, EnumFlowerColor.Pink, EnumFlowerColor.Gold),
	Yarrow("Yarrow", "achillea", "millefolium", EnumFlowerType.Yarrow, EnumFlowerColor.Yellow),
	Petunia("Petunia", "petunia", "\u00ef?? atkinsiana", EnumFlowerType.Petunia, EnumFlowerColor.MediumVioletRed, EnumFlowerColor.Thistle),
	Agapanthus("Agapanthus", "agapanthus", "praecox", EnumFlowerType.Agapanthus, EnumFlowerColor.DeepSkyBlue),
	Fuchsia("Fuchsia", "fuchsia", "magellanica", EnumFlowerType.Fuchsia, EnumFlowerColor.DeepPink, EnumFlowerColor.MediumOrchid),
	Dianthus("Dianthus", "dianthus", "barbatus", EnumFlowerType.Dianthus, EnumFlowerColor.Crimson, EnumFlowerColor.HotPink),
	Forget("Forget-me-nots", "myosotis", "arvensis", EnumFlowerType.Forget, EnumFlowerColor.LightSteelBlue),
	Anemone("Anemone", "anemone", "coronaria", EnumFlowerType.Anemone, EnumFlowerColor.Red, EnumFlowerColor.MistyRose),
	Aquilegia("Aquilegia", "aquilegia", "vulgaris", EnumFlowerType.Aquilegia, EnumFlowerColor.SlateBlue, EnumFlowerColor.Thistle),
	Edelweiss("Edelweiss", "leontopodium", "alpinum", EnumFlowerType.Edelweiss, EnumFlowerColor.White, EnumFlowerColor.Khaki),
	Scabious("Scabious", "scabiosa", "columbaria", EnumFlowerType.Scabious, EnumFlowerColor.RoyalBlue),
	Coneflower("Coneflower", "echinacea", "purpurea", EnumFlowerType.Coneflower, EnumFlowerColor.Violet, EnumFlowerColor.DarkOrange),
	Gaillardia("Gaillardia", "gaillardia", "aristata", EnumFlowerType.Gaillardia, EnumFlowerColor.DarkOrange, EnumFlowerColor.Yellow),
	Auricula("Auricula", "primula", "auricula", EnumFlowerType.Auricula, EnumFlowerColor.Red, EnumFlowerColor.Yellow),
	Camellia("Camellia", "camellia", "japonica", EnumFlowerType.Camellia, EnumFlowerColor.Crimson),
	Goldenrod("Goldenrod", "solidago", "canadensis", EnumFlowerType.Goldenrod, EnumFlowerColor.Gold),
	Althea("Althea", "althaea", "officinalis", EnumFlowerType.Althea, EnumFlowerColor.Thistle, EnumFlowerColor.MediumOrchid),
	Penstemon("Penstemon", "penstemon", "digitalis", EnumFlowerType.Penstemon, EnumFlowerColor.MediumOrchid, EnumFlowerColor.Thistle),
	Delphinium("Delphinium", "delphinium", "staphisagria", EnumFlowerType.Delphinium, EnumFlowerColor.DarkSlateBlue),
	Hollyhock("Hollyhock", "Alcea", "rosea", EnumFlowerType.Hollyhock, EnumFlowerColor.Black, EnumFlowerColor.Gold);

	EnumFlowerColor stemColor;
	ForestryAllele.Fertility fert;
	ForestryAllele.Lifespan life;
	ForestryAllele.Sappiness sap;
	IFlowerType type;
	String name;
	String binomial;
	String branchName;
	EnumFlowerColor primaryColor;
	EnumFlowerColor secondaryColor;
	EnumTemperature temperature;
	EnumAcidity pH;
	EnumMoisture moisture;
	EnumTolerance tempTolerance;
	EnumTolerance pHTolerance;
	EnumTolerance moistureTolerance;
	List<IAllele[]> variantTemplates;
	IClassification branch;

	public static void setupVariants() {
		FlowerSpecies.Dandelion.setTraits(ForestryAllele.Fertility.High, ForestryAllele.Lifespan.Shortened, ForestryAllele.Sappiness.Lower);
		FlowerSpecies.Poppy.setTraits(ForestryAllele.Fertility.High, ForestryAllele.Lifespan.Shorter, ForestryAllele.Sappiness.Average);
		FlowerSpecies.Orchid.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Long, ForestryAllele.Sappiness.Low);
		FlowerSpecies.Allium.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Normal, ForestryAllele.Sappiness.Low);
		FlowerSpecies.Bluet.setTraits(ForestryAllele.Fertility.Low, ForestryAllele.Lifespan.Normal, ForestryAllele.Sappiness.Lower);
		FlowerSpecies.Tulip.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Long, ForestryAllele.Sappiness.Average);
		FlowerSpecies.Daisy.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Normal, ForestryAllele.Sappiness.Low);
		FlowerSpecies.Cornflower.setTraits(ForestryAllele.Fertility.High, ForestryAllele.Lifespan.Shorter, ForestryAllele.Sappiness.Low);
		FlowerSpecies.Pansy.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Shortened, ForestryAllele.Sappiness.Average);
		FlowerSpecies.Iris.setTraits(ForestryAllele.Fertility.High, ForestryAllele.Lifespan.Normal, ForestryAllele.Sappiness.Average);
		FlowerSpecies.Lavender.setTraits(ForestryAllele.Fertility.High, ForestryAllele.Lifespan.Normal, ForestryAllele.Sappiness.Low);
		FlowerSpecies.Viola.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Shortened, ForestryAllele.Sappiness.Average);
		FlowerSpecies.Daffodil.setTraits(ForestryAllele.Fertility.High, ForestryAllele.Lifespan.Elongated, ForestryAllele.Sappiness.Average);
		FlowerSpecies.Aster.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Normal, ForestryAllele.Sappiness.Higher);
		FlowerSpecies.Lilac.setTraits(ForestryAllele.Fertility.Low, ForestryAllele.Lifespan.Longer, ForestryAllele.Sappiness.Average);
		FlowerSpecies.Rose.setTraits(ForestryAllele.Fertility.Low, ForestryAllele.Lifespan.Longer, ForestryAllele.Sappiness.High);
		FlowerSpecies.Peony.setTraits(ForestryAllele.Fertility.Low, ForestryAllele.Lifespan.Long, ForestryAllele.Sappiness.Average);
		FlowerSpecies.Marigold.setTraits(ForestryAllele.Fertility.High, ForestryAllele.Lifespan.Shorter, ForestryAllele.Sappiness.Average);
		FlowerSpecies.Hydrangea.setTraits(ForestryAllele.Fertility.Low, ForestryAllele.Lifespan.Longer, ForestryAllele.Sappiness.High);
		FlowerSpecies.Foxglove.setTraits(ForestryAllele.Fertility.Low, ForestryAllele.Lifespan.Shortened, ForestryAllele.Sappiness.Low);
		FlowerSpecies.Dahlia.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Normal, ForestryAllele.Sappiness.Average);
		FlowerSpecies.Chrysanthemum.setTraits(ForestryAllele.Fertility.High, ForestryAllele.Lifespan.Long, ForestryAllele.Sappiness.High);
		FlowerSpecies.Carnation.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Normal, ForestryAllele.Sappiness.High);
		FlowerSpecies.Zinnia.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Shorter, ForestryAllele.Sappiness.Average);
		FlowerSpecies.Primrose.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Long, ForestryAllele.Sappiness.Average);
		FlowerSpecies.Azalea.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Normal, ForestryAllele.Sappiness.Average);
		FlowerSpecies.Geranium.setTraits(ForestryAllele.Fertility.Low, ForestryAllele.Lifespan.Long, ForestryAllele.Sappiness.Low);
		FlowerSpecies.Lily.setTraits(ForestryAllele.Fertility.Low, ForestryAllele.Lifespan.Long, ForestryAllele.Sappiness.Low);
		FlowerSpecies.Yarrow.setTraits(ForestryAllele.Fertility.High, ForestryAllele.Lifespan.Normal, ForestryAllele.Sappiness.Low);
		FlowerSpecies.Petunia.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Shorter, ForestryAllele.Sappiness.Average);
		FlowerSpecies.Agapanthus.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Normal, ForestryAllele.Sappiness.Low);
		FlowerSpecies.Fuchsia.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Shortened, ForestryAllele.Sappiness.Average);
		FlowerSpecies.Dianthus.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Short, ForestryAllele.Sappiness.High);
		FlowerSpecies.Forget.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Short, ForestryAllele.Sappiness.Lower);
		FlowerSpecies.Anemone.setTraits(ForestryAllele.Fertility.High, ForestryAllele.Lifespan.Normal, ForestryAllele.Sappiness.Low);
		FlowerSpecies.Aquilegia.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Normal, ForestryAllele.Sappiness.Average);
		FlowerSpecies.Edelweiss.setTraits(ForestryAllele.Fertility.Low, ForestryAllele.Lifespan.Normal, ForestryAllele.Sappiness.Lowest);
		FlowerSpecies.Scabious.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Shortened, ForestryAllele.Sappiness.Low);
		FlowerSpecies.Coneflower.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Normal, ForestryAllele.Sappiness.Higher);
		FlowerSpecies.Gaillardia.setTraits(ForestryAllele.Fertility.High, ForestryAllele.Lifespan.Long, ForestryAllele.Sappiness.Higher);
		FlowerSpecies.Auricula.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Elongated, ForestryAllele.Sappiness.High);
		FlowerSpecies.Camellia.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Normal, ForestryAllele.Sappiness.High);
		FlowerSpecies.Goldenrod.setTraits(ForestryAllele.Fertility.High, ForestryAllele.Lifespan.Normal, ForestryAllele.Sappiness.Higher);
		FlowerSpecies.Althea.setTraits(ForestryAllele.Fertility.Low, ForestryAllele.Lifespan.Elongated, ForestryAllele.Sappiness.High);
		FlowerSpecies.Penstemon.setTraits(ForestryAllele.Fertility.Low, ForestryAllele.Lifespan.Long, ForestryAllele.Sappiness.Low);
		FlowerSpecies.Delphinium.setTraits(ForestryAllele.Fertility.Low, ForestryAllele.Lifespan.Longer, ForestryAllele.Sappiness.Low);
		FlowerSpecies.Hollyhock.setTraits(ForestryAllele.Fertility.Low, ForestryAllele.Lifespan.Long, ForestryAllele.Sappiness.High);
		FlowerSpecies.Dandelion.setPH(EnumAcidity.Neutral, EnumTolerance.BOTH_1);
		FlowerSpecies.Dandelion.setMoisture(EnumMoisture.Normal, EnumTolerance.BOTH_1);
		FlowerSpecies.Poppy.setPH(EnumAcidity.Neutral, EnumTolerance.DOWN_1);
		FlowerSpecies.Poppy.setMoisture(EnumMoisture.Normal, EnumTolerance.DOWN_1);
		FlowerSpecies.Poppy.setTemperature(EnumTemperature.WARM, EnumTolerance.BOTH_2);
		FlowerSpecies.Orchid.setPH(EnumAcidity.Acid, EnumTolerance.NONE);
		FlowerSpecies.Orchid.setMoisture(EnumMoisture.Normal, EnumTolerance.BOTH_1);
		FlowerSpecies.Allium.setPH(EnumAcidity.Alkaline, EnumTolerance.DOWN_1);
		FlowerSpecies.Allium.setMoisture(EnumMoisture.Normal, EnumTolerance.DOWN_1);
		FlowerSpecies.Bluet.setPH(EnumAcidity.Neutral, EnumTolerance.DOWN_1);
		FlowerSpecies.Bluet.setMoisture(EnumMoisture.Damp, EnumTolerance.NONE);
		FlowerSpecies.Tulip.setMoisture(EnumMoisture.Normal, EnumTolerance.BOTH_1);
		FlowerSpecies.Daisy.setPH(EnumAcidity.Neutral, EnumTolerance.DOWN_1);
		FlowerSpecies.Daisy.setMoisture(EnumMoisture.Normal, EnumTolerance.DOWN_1);
		FlowerSpecies.Daisy.setTemperature(EnumTemperature.WARM, EnumTolerance.BOTH_2);
		FlowerSpecies.Cornflower.setMutation(FlowerSpecies.Dandelion, FlowerSpecies.Tulip, 10);
		FlowerSpecies.Cornflower.setPH(EnumAcidity.Neutral, EnumTolerance.BOTH_1);
		FlowerSpecies.Pansy.setMutation(FlowerSpecies.Tulip, FlowerSpecies.Viola, 5);
		FlowerSpecies.Pansy.setPH(EnumAcidity.Acid, EnumTolerance.NONE);
		FlowerSpecies.Pansy.setTemperature(EnumTemperature.WARM, EnumTolerance.DOWN_1);
		FlowerSpecies.Iris.setMutation(FlowerSpecies.Orchid, FlowerSpecies.Viola, 10);
		FlowerSpecies.Iris.setPH(EnumAcidity.Acid, EnumTolerance.NONE);
		FlowerSpecies.Iris.setTemperature(EnumTemperature.WARM, EnumTolerance.DOWN_1);
		FlowerSpecies.Lavender.setMutation(FlowerSpecies.Allium, FlowerSpecies.Viola, 10);
		FlowerSpecies.Lavender.setPH(EnumAcidity.Neutral, EnumTolerance.UP_1);
		FlowerSpecies.Lavender.setMoisture(EnumMoisture.Normal, EnumTolerance.DOWN_1);
		FlowerSpecies.Lavender.setTemperature(EnumTemperature.WARM, EnumTolerance.DOWN_1);
		FlowerSpecies.Viola.setMutation(FlowerSpecies.Orchid, FlowerSpecies.Poppy, 15);
		FlowerSpecies.Viola.setPH(EnumAcidity.Acid, EnumTolerance.NONE);
		FlowerSpecies.Viola.setMoisture(EnumMoisture.Normal, EnumTolerance.DOWN_1);
		FlowerSpecies.Daffodil.setMutation(FlowerSpecies.Dandelion, FlowerSpecies.Poppy, 10);
		FlowerSpecies.Daffodil.setPH(EnumAcidity.Neutral, EnumTolerance.DOWN_1);
		FlowerSpecies.Aster.setMutation(FlowerSpecies.Daisy, FlowerSpecies.Tulip, 10);
		FlowerSpecies.Aster.setPH(EnumAcidity.Neutral, EnumTolerance.BOTH_1);
		FlowerSpecies.Lilac.setPH(EnumAcidity.Alkaline, EnumTolerance.DOWN_1);
		FlowerSpecies.Rose.setPH(EnumAcidity.Acid, EnumTolerance.UP_1);
		FlowerSpecies.Peony.setPH(EnumAcidity.Alkaline, EnumTolerance.DOWN_1);
		FlowerSpecies.Peony.setMoisture(EnumMoisture.Normal, EnumTolerance.UP_1);
		FlowerSpecies.Marigold.setMutation(FlowerSpecies.Daisy, FlowerSpecies.Dandelion, 10);
		FlowerSpecies.Marigold.setPH(EnumAcidity.Neutral, EnumTolerance.DOWN_1);
		FlowerSpecies.Marigold.setMoisture(EnumMoisture.Normal, EnumTolerance.UP_1);
		FlowerSpecies.Marigold.setTemperature(EnumTemperature.WARM, EnumTolerance.BOTH_2);
		FlowerSpecies.Hydrangea.setMutation(FlowerSpecies.Peony, FlowerSpecies.Bluet, 10);
		FlowerSpecies.Hydrangea.setPH(EnumAcidity.Neutral, EnumTolerance.BOTH_1);
		FlowerSpecies.Hydrangea.setMoisture(EnumMoisture.Damp, EnumTolerance.NONE);
		FlowerSpecies.Foxglove.setMutation(FlowerSpecies.Lilac, FlowerSpecies.Zinnia, 5);
		FlowerSpecies.Foxglove.setPH(EnumAcidity.Neutral, EnumTolerance.DOWN_1);
		FlowerSpecies.Foxglove.setMoisture(EnumMoisture.Normal, EnumTolerance.UP_1);
		FlowerSpecies.Dahlia.setMutation(FlowerSpecies.Daisy, FlowerSpecies.Allium, 15);
		FlowerSpecies.Dahlia.setPH(EnumAcidity.Neutral, EnumTolerance.DOWN_1);
		FlowerSpecies.Dahlia.setMoisture(EnumMoisture.Normal, EnumTolerance.UP_1);
		FlowerSpecies.Dahlia.setTemperature(EnumTemperature.NORMAL, EnumTolerance.BOTH_2);
		FlowerSpecies.Chrysanthemum.setMutation(FlowerSpecies.Geranium, FlowerSpecies.Rose, 10);
		FlowerSpecies.Chrysanthemum.setPH(EnumAcidity.Neutral, EnumTolerance.DOWN_1);
		FlowerSpecies.Chrysanthemum.setMoisture(EnumMoisture.Normal, EnumTolerance.DOWN_1);
		FlowerSpecies.Carnation.setMutation(FlowerSpecies.Dianthus, FlowerSpecies.Rose, 5);
		FlowerSpecies.Carnation.setPH(EnumAcidity.Alkaline, EnumTolerance.DOWN_1);
		FlowerSpecies.Zinnia.setMutation(FlowerSpecies.Dahlia, FlowerSpecies.Marigold, 5);
		FlowerSpecies.Zinnia.setPH(EnumAcidity.Neutral, EnumTolerance.BOTH_1);
		FlowerSpecies.Zinnia.setMoisture(EnumMoisture.Normal, EnumTolerance.DOWN_1);
		FlowerSpecies.Zinnia.setTemperature(EnumTemperature.NORMAL, EnumTolerance.BOTH_2);
		FlowerSpecies.Primrose.setMutation(FlowerSpecies.Chrysanthemum, FlowerSpecies.Auricula, 5);
		FlowerSpecies.Primrose.setPH(EnumAcidity.Acid, EnumTolerance.UP_1);
		FlowerSpecies.Primrose.setMoisture(EnumMoisture.Normal, EnumTolerance.UP_1);
		FlowerSpecies.Azalea.setMutation(FlowerSpecies.Orchid, FlowerSpecies.Geranium, 5);
		FlowerSpecies.Azalea.setPH(EnumAcidity.Acid, EnumTolerance.NONE);
		FlowerSpecies.Geranium.setMutation(FlowerSpecies.Tulip, FlowerSpecies.Orchid, 15);
		FlowerSpecies.Geranium.setPH(EnumAcidity.Neutral, EnumTolerance.BOTH_1);
		FlowerSpecies.Geranium.setTemperature(EnumTemperature.WARM, EnumTolerance.BOTH_1);
		FlowerSpecies.Lily.setMutation(FlowerSpecies.Tulip, FlowerSpecies.Chrysanthemum, 5);
		FlowerSpecies.Lily.setPH(EnumAcidity.Neutral, EnumTolerance.DOWN_1);
		FlowerSpecies.Lily.setTemperature(EnumTemperature.WARM, EnumTolerance.BOTH_1);
		FlowerSpecies.Yarrow.setMutation(FlowerSpecies.Dandelion, FlowerSpecies.Orchid, 10);
		FlowerSpecies.Yarrow.setPH(EnumAcidity.Acid, EnumTolerance.UP_1);
		FlowerSpecies.Yarrow.setMoisture(EnumMoisture.Normal, EnumTolerance.DOWN_1);
		FlowerSpecies.Petunia.setMutation(FlowerSpecies.Tulip, FlowerSpecies.Dahlia, 5);
		FlowerSpecies.Petunia.setPH(EnumAcidity.Neutral, EnumTolerance.DOWN_1);
		FlowerSpecies.Petunia.setMoisture(EnumMoisture.Normal, EnumTolerance.UP_1);
		FlowerSpecies.Petunia.setTemperature(EnumTemperature.WARM, EnumTolerance.UP_1);
		FlowerSpecies.Agapanthus.setMutation(FlowerSpecies.Allium, FlowerSpecies.Geranium, 5);
		FlowerSpecies.Agapanthus.setPH(EnumAcidity.Neutral, EnumTolerance.BOTH_1);
		FlowerSpecies.Agapanthus.setMoisture(EnumMoisture.Normal, EnumTolerance.DOWN_1);
		FlowerSpecies.Agapanthus.setTemperature(EnumTemperature.WARM, EnumTolerance.BOTH_1);
		FlowerSpecies.Fuchsia.setMutation(FlowerSpecies.Foxglove, FlowerSpecies.Dahlia, 5);
		FlowerSpecies.Fuchsia.setPH(EnumAcidity.Neutral, EnumTolerance.BOTH_1);
		FlowerSpecies.Fuchsia.setMoisture(EnumMoisture.Normal, EnumTolerance.UP_1);
		FlowerSpecies.Fuchsia.setTemperature(EnumTemperature.WARM, EnumTolerance.BOTH_1);
		FlowerSpecies.Dianthus.setMutation(FlowerSpecies.Tulip, FlowerSpecies.Poppy, 15);
		FlowerSpecies.Dianthus.setPH(EnumAcidity.Alkaline, EnumTolerance.DOWN_1);
		FlowerSpecies.Dianthus.setMoisture(EnumMoisture.Normal, EnumTolerance.DOWN_1);
		FlowerSpecies.Dianthus.setTemperature(EnumTemperature.NORMAL, EnumTolerance.BOTH_2);
		FlowerSpecies.Forget.setMutation(FlowerSpecies.Orchid, FlowerSpecies.Bluet, 10);
		FlowerSpecies.Forget.setPH(EnumAcidity.Acid, EnumTolerance.NONE);
		FlowerSpecies.Forget.setMoisture(EnumMoisture.Normal, EnumTolerance.UP_1);
		FlowerSpecies.Forget.setTemperature(EnumTemperature.NORMAL, EnumTolerance.UP_1);
		FlowerSpecies.Anemone.setMutation(FlowerSpecies.Aquilegia, FlowerSpecies.Rose, 5);
		FlowerSpecies.Anemone.setPH(EnumAcidity.Neutral, EnumTolerance.BOTH_1);
		FlowerSpecies.Anemone.setMoisture(EnumMoisture.Normal, EnumTolerance.UP_1);
		FlowerSpecies.Aquilegia.setMutation(FlowerSpecies.Iris, FlowerSpecies.Poppy, 5);
		FlowerSpecies.Aquilegia.setMoisture(EnumMoisture.Normal, EnumTolerance.UP_1);
		FlowerSpecies.Edelweiss.setMutation(FlowerSpecies.Peony, FlowerSpecies.Bluet, 5);
		FlowerSpecies.Edelweiss.setPH(EnumAcidity.Alkaline, EnumTolerance.DOWN_1);
		FlowerSpecies.Edelweiss.setMoisture(EnumMoisture.Normal, EnumTolerance.DOWN_1);
		FlowerSpecies.Edelweiss.setTemperature(EnumTemperature.NORMAL, EnumTolerance.DOWN_1);
		FlowerSpecies.Scabious.setMutation(FlowerSpecies.Allium, FlowerSpecies.Cornflower, 5);
		FlowerSpecies.Scabious.setPH(EnumAcidity.Neutral, EnumTolerance.UP_1);
		FlowerSpecies.Scabious.setTemperature(EnumTemperature.NORMAL, EnumTolerance.DOWN_1);
		FlowerSpecies.Coneflower.setMutation(FlowerSpecies.Tulip, FlowerSpecies.Cornflower, 5);
		FlowerSpecies.Coneflower.setPH(EnumAcidity.Neutral, EnumTolerance.BOTH_1);
		FlowerSpecies.Gaillardia.setMutation(FlowerSpecies.Dandelion, FlowerSpecies.Marigold, 5);
		FlowerSpecies.Gaillardia.setPH(EnumAcidity.Neutral, EnumTolerance.BOTH_1);
		FlowerSpecies.Gaillardia.setMoisture(EnumMoisture.Damp, EnumTolerance.DOWN_1);
		FlowerSpecies.Gaillardia.setTemperature(EnumTemperature.NORMAL, EnumTolerance.BOTH_2);
		FlowerSpecies.Auricula.setMutation(FlowerSpecies.Poppy, FlowerSpecies.Geranium, 10);
		FlowerSpecies.Auricula.setPH(EnumAcidity.Acid, EnumTolerance.UP_1);
		FlowerSpecies.Auricula.setMoisture(EnumMoisture.Normal, EnumTolerance.UP_1);
		FlowerSpecies.Camellia.setMutation(FlowerSpecies.Hydrangea, FlowerSpecies.Rose, 5);
		FlowerSpecies.Camellia.setPH(EnumAcidity.Acid, EnumTolerance.NONE);
		FlowerSpecies.Camellia.setMoisture(EnumMoisture.Damp, EnumTolerance.NONE);
		FlowerSpecies.Camellia.setTemperature(EnumTemperature.WARM, EnumTolerance.UP_1);
		FlowerSpecies.Goldenrod.setMutation(FlowerSpecies.Lilac, FlowerSpecies.Marigold, 10);
		FlowerSpecies.Goldenrod.setPH(EnumAcidity.Neutral, EnumTolerance.BOTH_1);
		FlowerSpecies.Althea.setMutation(FlowerSpecies.Hydrangea, FlowerSpecies.Iris, 5);
		FlowerSpecies.Althea.setPH(EnumAcidity.Neutral, EnumTolerance.DOWN_1);
		FlowerSpecies.Althea.setTemperature(EnumTemperature.WARM, EnumTolerance.BOTH_1);
		FlowerSpecies.Penstemon.setMutation(FlowerSpecies.Peony, FlowerSpecies.Lilac, 5);
		FlowerSpecies.Penstemon.setMoisture(EnumMoisture.Dry, EnumTolerance.UP_1);
		FlowerSpecies.Penstemon.setTemperature(EnumTemperature.WARM, EnumTolerance.UP_1);
		FlowerSpecies.Delphinium.setMutation(FlowerSpecies.Lilac, FlowerSpecies.Bluet, 5);
		FlowerSpecies.Delphinium.setMoisture(EnumMoisture.Damp, EnumTolerance.DOWN_1);
		FlowerSpecies.Delphinium.setTemperature(EnumTemperature.NORMAL, EnumTolerance.DOWN_1);
		FlowerSpecies.Hollyhock.setMutation(FlowerSpecies.Delphinium, FlowerSpecies.Lavender, 5);
		FlowerSpecies.Hollyhock.setPH(EnumAcidity.Neutral, EnumTolerance.UP_1);
		FlowerSpecies.Dandelion.setStemColor(EnumFlowerColor.Green);
		FlowerSpecies.Poppy.setStemColor(EnumFlowerColor.Green);
		FlowerSpecies.Orchid.setStemColor(EnumFlowerColor.Green);
		FlowerSpecies.Allium.setStemColor(EnumFlowerColor.Green);
		FlowerSpecies.Bluet.setStemColor(EnumFlowerColor.OliveDrab);
		FlowerSpecies.Tulip.setStemColor(EnumFlowerColor.OliveDrab);
		FlowerSpecies.Daisy.setStemColor(EnumFlowerColor.OliveDrab);
		FlowerSpecies.Cornflower.setStemColor(EnumFlowerColor.OliveDrab);
		FlowerSpecies.Pansy.setStemColor(EnumFlowerColor.SeaGreen);
		FlowerSpecies.Iris.setStemColor(EnumFlowerColor.SeaGreen);
		FlowerSpecies.Lavender.setStemColor(EnumFlowerColor.Green);
		FlowerSpecies.Viola.setStemColor(EnumFlowerColor.OliveDrab);
		FlowerSpecies.Daffodil.setStemColor(EnumFlowerColor.Green);
		FlowerSpecies.Dahlia.setStemColor(EnumFlowerColor.OliveDrab);
		FlowerSpecies.Peony.setStemColor(EnumFlowerColor.DarkGreen);
		FlowerSpecies.Rose.setStemColor(EnumFlowerColor.Green);
		FlowerSpecies.Lilac.setStemColor(EnumFlowerColor.OliveDrab);
		FlowerSpecies.Hydrangea.setStemColor(EnumFlowerColor.DarkGreen);
		FlowerSpecies.Foxglove.setStemColor(EnumFlowerColor.DarkGreen);
		FlowerSpecies.Zinnia.setStemColor(EnumFlowerColor.MediumSeaGreen);
		FlowerSpecies.Chrysanthemum.setStemColor(EnumFlowerColor.MediumSeaGreen);
		FlowerSpecies.Marigold.setStemColor(EnumFlowerColor.Green);
		FlowerSpecies.Geranium.setStemColor(EnumFlowerColor.MediumSeaGreen);
		FlowerSpecies.Azalea.setStemColor(EnumFlowerColor.Green);
		FlowerSpecies.Primrose.setStemColor(EnumFlowerColor.Green);
		FlowerSpecies.Aster.setStemColor(EnumFlowerColor.Green);
		FlowerSpecies.Carnation.setStemColor(EnumFlowerColor.SeaGreen);
		FlowerSpecies.Lily.setStemColor(EnumFlowerColor.Green);
		FlowerSpecies.Yarrow.setStemColor(EnumFlowerColor.DarkOliveGreen);
		FlowerSpecies.Petunia.setStemColor(EnumFlowerColor.Green);
		FlowerSpecies.Agapanthus.setStemColor(EnumFlowerColor.DarkOliveGreen);
		FlowerSpecies.Fuchsia.setStemColor(EnumFlowerColor.SeaGreen);
		FlowerSpecies.Dianthus.setStemColor(EnumFlowerColor.OliveDrab);
		FlowerSpecies.Forget.setStemColor(EnumFlowerColor.Green);
		FlowerSpecies.Anemone.setStemColor(EnumFlowerColor.DarkOliveGreen);
		FlowerSpecies.Aquilegia.setStemColor(EnumFlowerColor.MediumSeaGreen);
		FlowerSpecies.Edelweiss.setStemColor(EnumFlowerColor.DarkOliveGreen);
		FlowerSpecies.Scabious.setStemColor(EnumFlowerColor.OliveDrab);
		FlowerSpecies.Coneflower.setStemColor(EnumFlowerColor.DarkOliveGreen);
		FlowerSpecies.Gaillardia.setStemColor(EnumFlowerColor.OliveDrab);
		FlowerSpecies.Auricula.setStemColor(EnumFlowerColor.DarkOliveGreen);
		FlowerSpecies.Camellia.setStemColor(EnumFlowerColor.DarkOliveGreen);
		FlowerSpecies.Goldenrod.setStemColor(EnumFlowerColor.MediumSeaGreen);
		FlowerSpecies.Althea.setStemColor(EnumFlowerColor.DarkGreen);
		FlowerSpecies.Penstemon.setStemColor(EnumFlowerColor.OliveDrab);
		FlowerSpecies.Delphinium.setStemColor(EnumFlowerColor.DarkSeaGreen);
		FlowerSpecies.Hollyhock.setStemColor(EnumFlowerColor.Green);
		BotanyCore.getFlowerRoot().addConversion(new ItemStack(Blocks.yellow_flower, 1, 0), FlowerSpecies.Dandelion.getTemplate());
		BotanyCore.getFlowerRoot().addConversion(new ItemStack(Blocks.red_flower, 1, 0), FlowerSpecies.Poppy.getTemplate());
		BotanyCore.getFlowerRoot().addConversion(new ItemStack(Blocks.red_flower, 1, 1), FlowerSpecies.Orchid.getTemplate());
		BotanyCore.getFlowerRoot().addConversion(new ItemStack(Blocks.red_flower, 1, 2), FlowerSpecies.Allium.getTemplate());
		BotanyCore.getFlowerRoot().addConversion(new ItemStack(Blocks.red_flower, 1, 3), FlowerSpecies.Bluet.getTemplate());
		BotanyCore.getFlowerRoot().addConversion(new ItemStack(Blocks.red_flower, 1, 7), FlowerSpecies.Tulip.getTemplate());
		BotanyCore.getFlowerRoot().addConversion(new ItemStack(Blocks.red_flower, 1, 8), FlowerSpecies.Daisy.getTemplate());
		BotanyCore.getFlowerRoot().addConversion(new ItemStack(Blocks.double_plant, 1, 1), FlowerSpecies.Lilac.getTemplate());
		BotanyCore.getFlowerRoot().addConversion(new ItemStack(Blocks.double_plant, 1, 4), FlowerSpecies.Rose.getTemplate());
		BotanyCore.getFlowerRoot().addConversion(new ItemStack(Blocks.double_plant, 1, 5), FlowerSpecies.Peony.getTemplate());
		BotanyCore.getFlowerRoot().addConversion(new ItemStack(Blocks.red_flower, 1, 6), FlowerSpecies.Tulip.AddVariant(EnumFlowerColor.White));
		BotanyCore.getFlowerRoot().addConversion(new ItemStack(Blocks.red_flower, 1, 4), FlowerSpecies.Tulip.AddVariant(EnumFlowerColor.Crimson));
		BotanyCore.getFlowerRoot().addConversion(new ItemStack(Blocks.red_flower, 1, 5), FlowerSpecies.Tulip.AddVariant(EnumFlowerColor.DarkOrange));
		for (final FlowerSpecies species : values()) {
			final String scientific = species.branchName.substring(0, 1).toUpperCase() + species.branchName.substring(1).toLowerCase();
			final String uid = "flowers." + species.branchName.toLowerCase();
			IClassification branch = AlleleManager.alleleRegistry.getClassification("genus." + uid);
			if (branch == null) {
				branch = AlleleManager.alleleRegistry.createAndRegisterClassification(IClassification.EnumClassLevel.GENUS, uid, scientific);
			}
			(species.branch = branch).addMemberSpecies(species);
		}
	}

	private void setStemColor(final EnumFlowerColor green) {
		this.stemColor = green;
	}

	private void setTraits(final ForestryAllele.Fertility high, final ForestryAllele.Lifespan shortened, final ForestryAllele.Sappiness lower) {
		this.fert = high;
		this.life = shortened;
		this.sap = lower;
	}

	private void setMutation(final FlowerSpecies dandelion2, final FlowerSpecies tulip2, final int i) {
		BotanyCore.getFlowerRoot().registerMutation(new FlowerMutation(dandelion2, tulip2, this.getTemplate(), i));
	}

	private FlowerSpecies(final String name, final String branch, final String binomial, final IFlowerType type, final EnumFlowerColor colour) {
		this(name, branch, binomial, type, colour, colour);
	}

	private FlowerSpecies(final String name, final String branch, final String binomial, final IFlowerType type, final EnumFlowerColor primaryColor, final EnumFlowerColor secondaryColor) {
		this.stemColor = EnumFlowerColor.Green;
		this.temperature = EnumTemperature.NORMAL;
		this.pH = EnumAcidity.Neutral;
		this.moisture = EnumMoisture.Normal;
		this.tempTolerance = EnumTolerance.BOTH_1;
		this.pHTolerance = EnumTolerance.NONE;
		this.moistureTolerance = EnumTolerance.NONE;
		this.variantTemplates = new ArrayList<IAllele[]>();
		this.name = name;
		this.binomial = binomial;
		this.branchName = branch;
		this.type = type;
		this.primaryColor = primaryColor;
		this.secondaryColor = secondaryColor;
	}

	private IAllele[] AddVariant(final EnumFlowerColor a, final EnumFlowerColor b) {
		final IAllele[] template = this.getTemplate();
		template[EnumFlowerChromosome.PRIMARY.ordinal()] = a.getAllele();
		template[EnumFlowerChromosome.SECONDARY.ordinal()] = b.getAllele();
		this.variantTemplates.add(template);
		return template;
	}

	private void setTemperature(final EnumTemperature temperature, final EnumTolerance tolerance) {
		this.temperature = temperature;
		this.tempTolerance = tolerance;
	}

	private void setPH(final EnumAcidity temperature, final EnumTolerance tolerance) {
		this.pH = temperature;
		this.pHTolerance = tolerance;
	}

	private void setMoisture(final EnumMoisture temperature, final EnumTolerance tolerance) {
		this.moisture = temperature;
		this.moistureTolerance = tolerance;
	}

	private IAllele[] AddVariant(final EnumFlowerColor a) {
		return this.AddVariant(a, a);
	}

	public List<IAllele[]> getVariants() {
		return this.variantTemplates;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getDescription() {
		return "";
	}

	@Override
	public EnumTemperature getTemperature() {
		return this.temperature;
	}

	@Override
	public EnumHumidity getHumidity() {
		return EnumHumidity.values()[this.getMoisture().ordinal()];
	}

	@Override
	public EnumAcidity getPH() {
		return this.pH;
	}

	@Override
	public EnumMoisture getMoisture() {
		return this.moisture;
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

	public void setBranch(final IClassification branch) {
		this.branch = branch;
	}

	@Override
	public IClassification getBranch() {
		return this.branch;
	}

	@Override
	public String getUID() {
		return "botany.flowers.species." + this.toString().toLowerCase();
	}

	@Override
	public boolean isDominant() {
		return false;
	}

	public IAllele[] getTemplate() {
		final IAllele[] template = FlowerTemplates.getDefaultTemplate();
		template[0] = this;
		template[EnumFlowerChromosome.PRIMARY.ordinal()] = this.primaryColor.getAllele();
		template[EnumFlowerChromosome.SECONDARY.ordinal()] = this.secondaryColor.getAllele();
		template[EnumFlowerChromosome.TEMPERATURE_TOLERANCE.ordinal()] = Binnie.Genetics.getToleranceAllele(this.tempTolerance);
		template[EnumFlowerChromosome.PH_TOLERANCE.ordinal()] = Binnie.Genetics.getToleranceAllele(this.pHTolerance);
		template[EnumFlowerChromosome.HUMIDITY_TOLERANCE.ordinal()] = Binnie.Genetics.getToleranceAllele(this.moistureTolerance);
		template[EnumFlowerChromosome.FERTILITY.ordinal()] = this.fert.getAllele();
		template[EnumFlowerChromosome.LIFESPAN.ordinal()] = this.life.getAllele();
		template[EnumFlowerChromosome.SAPPINESS.ordinal()] = this.sap.getAllele();
		template[EnumFlowerChromosome.STEM.ordinal()] = this.stemColor.getAllele();
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
	public int getIconColour(final int renderPass) {
		return 0;
	}

	@Override
	public int getComplexity() {
		return 0;
	}

	@Override
	public float getResearchSuitability(final ItemStack itemstack) {
		return 0.0f;
	}

	@Override
	public ItemStack[] getResearchBounty(final World world, final GameProfile researcher, final IIndividual individual, final int bountyLevel) {
		return null;
	}

	public static void init() {
	}

	@Override
	public IFlowerType getType() {
		return this.type;
	}

	@Override
	public String getUnlocalizedName() {
		return this.getUID();
	}
}
