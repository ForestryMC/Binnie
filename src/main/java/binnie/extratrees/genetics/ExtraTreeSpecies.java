package binnie.extratrees.genetics;

import binnie.Constants;
import binnie.core.genetics.ForestryAllele;
import binnie.extratrees.block.EnumExtraTreeLog;
import binnie.extratrees.gen.*;
import forestry.api.arboriculture.*;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IClassification;
import forestry.arboriculture.worldgen.WorldGenLemon;
import forestry.arboriculture.worldgen.WorldGenPlum;
import net.minecraft.world.gen.feature.WorldGenerator;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;

public enum ExtraTreeSpecies {
	OrchardApple("malus", "domestica", EnumLeafType.DECIDUOUS, new Color(0x09E67E), new Color(0xFF9CF3),EnumSaplingType.Default, EnumExtraTreeLog.Apple, new Color(0x7B7A7B), WorldGenApple.OrchardApple.class){
		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Apple);
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Higher.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ExtraTreeFruitFamily.Citrus);
		}
	},
	SweetCrabapple("malus", "coronaria", EnumLeafType.DECIDUOUS, new Color(0x7A9953), new Color(0xFC359F),EnumSaplingType.Default, EnumExtraTreeLog.Apple, new Color(0x7B7A7B), WorldGenApple.SweetCrabapple.class){
		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Crabapple);
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.High.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Average.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Fast.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ExtraTreeFruitFamily.Citrus);
		}
	},
	FloweringCrabapple("malus", "hopa", EnumLeafType.DECIDUOUS, new Color(0x7A9953), new Color(0xFC359F),EnumSaplingType.Default, EnumExtraTreeLog.Apple, new Color(0x7B7A7B),  WorldGenApple.FloweringCrabapple.class){
		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Crabapple);
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Average.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Average.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Low.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Fast.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ExtraTreeFruitFamily.Citrus);
		}
	},
	PrairieCrabapple("malus", "ioensis", EnumLeafType.DECIDUOUS, new Color(0x7A9953), new Color(0xFC359F), EnumSaplingType.Default, EnumExtraTreeLog.Apple, new Color(0x7B7A7B),  WorldGenApple.PrairieCrabapple.class){
		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Crabapple);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Low.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Average.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Average.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ExtraTreeFruitFamily.Citrus);
		}
	},
	Blackthorn("prunus", "spinosa", EnumLeafType.DECIDUOUS, new Color(0x6D8F1E), new Color(0xFF87C7), EnumSaplingType.Fruit, EnumForestryWoodType.PLUM, new Color(0xB68661),  WorldGenPlum.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Blackthorn);
			template.set(EnumTreeChromosome.HEIGHT,ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.YIELD,ForestryAllele.Yield.Low.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS,ForestryAllele.Sappiness.Average.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Fast.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ExtraTreeFruitFamily.Citrus);
		}
	},
	CherryPlum("prunus", "cerasifera", EnumLeafType.DECIDUOUS, new Color(0x6D8F1E), new Color(0xFF87C7), EnumSaplingType.Fruit, EnumForestryWoodType.PLUM, new Color(0xB68661),  WorldGenPlum.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.CherryPlum);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Lower.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS,ForestryAllele.Sappiness.Average.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Fast.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ExtraTreeFruitFamily.Citrus);
		}
	},
	Peach("prunus", "persica", EnumLeafType.DECIDUOUS, new Color(0x6D8F1E), new Color(0xFF269A), EnumSaplingType.Fruit, EnumForestryWoodType.PLUM, new Color(0xB68661),  WorldGenPlum.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Peach);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Low.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Average.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Average.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ExtraTreeFruitFamily.Citrus);
		}
	},
	Nectarine("prunus", "nectarina", EnumLeafType.DECIDUOUS, new Color(0x6D8F1E), new Color(0xFF269A), EnumSaplingType.Fruit, EnumForestryWoodType.PLUM, new Color(0xB68661),  WorldGenPlum.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Nectarine);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Average.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Average.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ExtraTreeFruitFamily.Citrus);
		}
	},
	Apricot("prunus", "armeniaca", EnumLeafType.DECIDUOUS, new Color(0x6D8F1E), new Color(0xF5B8D8), EnumSaplingType.Fruit, EnumForestryWoodType.PLUM, new Color(0xB68661),  WorldGenPlum.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Apricot);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Low.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Average.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ExtraTreeFruitFamily.Citrus);
		}
	},
	Almond("prunus", "amygdalus", EnumLeafType.DECIDUOUS, new Color(0x6D8F1E), new Color(0xF584C0), EnumSaplingType.Fruit, EnumForestryWoodType.PLUM, new Color(0xB68661),  WorldGenPlum.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Almond);
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Lower.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Fast.getAllele());;
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ExtraTreeFruitFamily.Citrus);
		}
	},
	WildCherry("prunus", "avium", EnumLeafType.DECIDUOUS, new Color(0x6D8F1E), new Color(0xF7EBF6), EnumSaplingType.Fruit, EnumExtraTreeLog.Cherry, new Color(0x716850),  WorldGenPlum.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.WildCherry);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Low.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Lower.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Low.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Fast.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ExtraTreeFruitFamily.Citrus);
		}
	},
	SourCherry("prunus", "cerasus", EnumLeafType.DECIDUOUS, new Color(0x6D8F1E), new Color(0xF7EBF6), EnumSaplingType.Fruit, EnumExtraTreeLog.Cherry, new Color(0x716850),  WorldGenPlum.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.SourCherry);
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Lower.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Low.getAllele());;
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ExtraTreeFruitFamily.Citrus);
		}
	},
	BlackCherry("prunus", "serotina", EnumLeafType.DECIDUOUS, new Color(0x6D8F1E), new Color(0xFAE1F8), EnumSaplingType.Fruit, EnumExtraTreeLog.Cherry, new Color(0x716850),  WorldGenPlum.class){
		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.BlackCherry);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Lowest.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Average.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Fast.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ExtraTreeFruitFamily.Citrus);
		}
	},
	Orange("citrus", "sinensis", EnumLeafType.JUNGLE, new Color(0x88AF54), new Color(0xA3B850), EnumSaplingType.Fruit, EnumForestryWoodType.CITRUS, new Color(0x5B4B39),  WorldGenLemon.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Orange);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Average.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Average.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Fast.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ExtraTreeFruitFamily.Citrus);
		}
	},
	Manderin("citrus", "reticulata", EnumLeafType.JUNGLE, new Color(0x88AF54), new Color(0xA3B850), EnumSaplingType.Fruit, EnumForestryWoodType.CITRUS, new Color(0x5B4B39),  WorldGenLemon.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Manderin);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Low.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.High.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Low.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ExtraTreeFruitFamily.Citrus);
		}
	},
	Satsuma("citrus", "unshiu", EnumLeafType.JUNGLE, new Color(0x88AF54), new Color(0xA3B850), EnumSaplingType.Fruit, EnumForestryWoodType.CITRUS, new Color(0x5B4B39),  WorldGenLemon.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Satsuma);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Low.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Low.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Fast.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ExtraTreeFruitFamily.Citrus);
		}
	},
	Tangerine("citrus", "tangerina", EnumLeafType.JUNGLE, new Color(0x88AF54), new Color(0xA3B850), EnumSaplingType.Fruit, EnumForestryWoodType.CITRUS, new Color(0x5B4B39),  WorldGenLemon.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Tangerine);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Low.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Average.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Average.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Faster.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ExtraTreeFruitFamily.Citrus);
		}
	},
	Lime("citrus", "latifolia", EnumLeafType.JUNGLE, new Color(0x88AF54), new Color(0xA3B850), EnumSaplingType.Fruit, EnumForestryWoodType.CITRUS, new Color(0x5B4B39),  WorldGenLemon.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Lime);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Average.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Low.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ExtraTreeFruitFamily.Citrus);
		}
	},
	KeyLime("citrus", "aurantifolia", EnumLeafType.JUNGLE, new Color(0x88AF54), new Color(0xA3B850), EnumSaplingType.Fruit, EnumForestryWoodType.CITRUS, new Color(0x5B4B39),  WorldGenLemon.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.KeyLime);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Lowest.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Average.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Low.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Fast.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ExtraTreeFruitFamily.Citrus);
		}
	},
	FingerLime("citrus", "australasica", EnumLeafType.JUNGLE, new Color(0x88AF54), new Color(0xA3B850), EnumSaplingType.Fruit, EnumForestryWoodType.CITRUS, new Color(0x5B4B39),  WorldGenLemon.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.FingerLime);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.High.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Low.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Fast.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ExtraTreeFruitFamily.Citrus);
		}
	},
	Pomelo("citrus", "maxima", EnumLeafType.JUNGLE, new Color(0x88AF54), new Color(0xA3B850), EnumSaplingType.Fruit, EnumForestryWoodType.CITRUS, new Color(0x5B4B39),  WorldGenLemon.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Pomelo);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Low.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Average.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ExtraTreeFruitFamily.Citrus);
		}
	},
	Grapefruit("citrus", "paradisi", EnumLeafType.JUNGLE, new Color(0x88AF54), new Color(0xA3B850), EnumSaplingType.Fruit, EnumForestryWoodType.CITRUS, new Color(0x5B4B39),  WorldGenLemon.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Grapefruit);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Low.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Average.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Low.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Fast.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ExtraTreeFruitFamily.Citrus);
		}
	},
	Kumquat("citrus", "margarita", EnumLeafType.JUNGLE, new Color(0x88AF54), new Color(0xA3B850), EnumSaplingType.Fruit, EnumForestryWoodType.CITRUS, new Color(0x5B4B39),  WorldGenLemon.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Kumquat);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.High.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Low.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ExtraTreeFruitFamily.Citrus);
		}
	},
	Citron("citrus", "medica", EnumLeafType.JUNGLE, new Color(0x88AF54), new Color(0xA3B850), EnumSaplingType.Fruit, EnumForestryWoodType.CITRUS, new Color(0x5B4B39),  WorldGenLemon.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Citron);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.High.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Average.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ExtraTreeFruitFamily.Citrus);
		}
	},
	BuddhaHand("citrus", "sarcodactylus", EnumLeafType.JUNGLE, new Color(0x88AF54), new Color(0xA3B850), EnumSaplingType.Fruit, EnumForestryWoodType.CITRUS, new Color(0x5B4B39),  WorldGenLemon.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.BuddhaHand);
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Average.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Low.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ExtraTreeFruitFamily.Citrus);
		}
	},
	Banana("musa", "sinensis", EnumLeafType.PALM, new Color(0xA1CD8E), new Color(0x44E500), EnumSaplingType.Default, EnumExtraTreeLog.Banana, new Color(0x85924F),  WorldGenBanana.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Banana);
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Low.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Low.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Fast.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
		}
	},
	RedBanana("musa", "rubra", EnumLeafType.PALM, new Color(0xA1CD8E), new Color(0x44E500), EnumSaplingType.Default, EnumExtraTreeLog.Banana, new Color(0x85924F),  WorldGenBanana.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.RedBanana);
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
		}
	},
	Plantain("musa", "paradisiaca", EnumLeafType.PALM, new Color(0xA1CD8E), new Color(0x44E500), EnumSaplingType.Default, EnumExtraTreeLog.Banana, new Color(0x85924F),  WorldGenBanana.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Plantain);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Average.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Lower.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
		}
	},
	Butternut("juglans", "cinerea", EnumLeafType.DECIDUOUS, new Color(0x82B58C), new Color(0x82DD8C), EnumSaplingType.Default, EnumExtraTreeLog.Butternut, new Color(0xB7ADA0),  WorldGenWalnut.Butternut.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Butternut);
			template.set(EnumTreeChromosome.GIRTH,  ForestryAllele.Int.Int2.getAllele());
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smaller.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Low.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
			species.addFruitFamily(ExtraTreeFruitFamily.Citrus);
		}
	},
	Rowan("sorbus", "aucuparia", EnumLeafType.DECIDUOUS, new Color(0x9EC79B), new Color(0x9EE8B2), EnumSaplingType.Default, EnumExtraTreeLog.Rowan, new Color(0xB6B09B),  WorldGenSorbus.Rowan.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Larger.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Low.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
			species.addFruitFamily(ExtraTreeFruitFamily.Berry);
		}
	},
	Hemlock("tsuga", "heterophylla", EnumLeafType.CONIFERS, new Color(0x5CAC72), new Color(0x5CD172), EnumSaplingType.Default, EnumExtraTreeLog.Hemlock, new Color(0xADA39B),  WorldGenConifer.WesternHemlock.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Average.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Low.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Slower.getAllele());
			template.set(EnumTreeChromosome.GIRTH, ForestryAllele.Int.Int2.getAllele());
		}
	},
	Ash("fraxinus", "excelsior", EnumLeafType.DECIDUOUS, new Color(0x488E2B), new Color(0x48E42B), EnumSaplingType.Default, EnumExtraTreeLog.Ash, new Color(0x898982),  WorldGenAsh.CommonAsh.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Low.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
			species.addFruitFamily(ExtraTreeFruitFamily.Citrus);
		}
	},
	Alder("alnus", "glutinosa", EnumLeafType.DECIDUOUS, new Color(0x698A33), new Color(0x69AE33), EnumSaplingType.Default, EnumExtraTreeLog.Alder, new Color(0xC6C0B8),  WorldGenAlder.CommonAlder.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Average.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
		}
	},
	Beech("fagus", "sylvatica", EnumLeafType.DECIDUOUS, new Color(0x83A04C), new Color(0x83C64C), EnumSaplingType.Default, EnumExtraTreeLog.Beech, new Color(0xB2917E),  WorldGenBeech.CommonBeech.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Beechnut);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Average.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Lower.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
		}
	},
	CopperBeech("fagus", "purpurea", EnumLeafType.DECIDUOUS, new Color(0x801318), new Color(0xD15B4D), EnumSaplingType.Default, EnumExtraTreeLog.Beech, new Color(0xB2917E),  WorldGenBeech.CopperBeech.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Beechnut);
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Slow.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
		}
	},
	Aspen("populus", "tremula", EnumLeafType.DECIDUOUS, new Color(0x8ACC37), new Color(0x8AE18F), EnumSaplingType.Default, EnumForestryWoodType.POPLAR, new Color(0x8CA687),  WorldGenPoplar.Aspen.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Average.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
		}
	},
	Yew("taxus", "baccata", EnumLeafType.CONIFERS, new Color(0x948A4D), new Color(0x94AE4D), EnumSaplingType.Default, EnumExtraTreeLog.Yew, new Color(0xD1BBC1),  WorldGenConifer.Yew.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Large.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
		}
	},
	Cypress("chamaecyparis", "lawsoniana", EnumLeafType.CONIFERS, new Color(0x89C9A7), new Color(0x89DDC6), EnumSaplingType.Poplar, EnumExtraTreeLog.Cypress, new Color(0x9A8483),  WorldGenConifer.Cypress.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Larger.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Low.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Slow.getAllele());
		}
	},
	DouglasFir("pseudotsuga", "menziesii", EnumLeafType.CONIFERS, new Color(0x99B582), new Color(0x99D1AA), EnumSaplingType.Default, EnumExtraTreeLog.Fir, new Color(0x828382),  WorldGenFir.DouglasFir.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.GIRTH,  ForestryAllele.Int.Int2.getAllele());
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smaller.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Low.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Slower.getAllele());
		}
	},
	Hazel("Corylus", "avellana", EnumLeafType.DECIDUOUS, new Color(0x9BB552), new Color(0x9BE152), EnumSaplingType.Default, EnumExtraTreeLog.Hazel, new Color(0xAA986F),  WorldGenTree3.Hazel.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Hazelnut);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Average.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Low.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Low.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
		}
	},
	Sycamore("ficus", "sycomorus", EnumLeafType.DECIDUOUS, new Color(0xA0A52F), new Color(0xB4D55C), EnumSaplingType.Default, EnumExtraTreeLog.Fig, new Color(0x807357),  WorldGenTree3.Sycamore.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Fig);
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Lowest.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
			species.addFruitFamily(ExtraTreeFruitFamily.Citrus);
		}
	},
	Whitebeam("sorbus", "aria", EnumLeafType.DECIDUOUS, new Color(0xBACE99), new Color(0x72863F), EnumSaplingType.Default, EnumExtraTreeLog.Whitebeam, new Color(0x786A6D),  WorldGenSorbus.Whitebeam.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smaller.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
		}
	},
	Hawthorn("crataegus", "monogyna", EnumLeafType.DECIDUOUS, new Color(0x6BA84A), new Color(0x98B77B), EnumSaplingType.Default, EnumExtraTreeLog.Hawthorn, new Color(0x5F5745),  WorldGenTree3.Hawthorn.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Average.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
		}
	},
	Pecan("carya", "illinoinensis", EnumLeafType.DECIDUOUS, new Color(0x85B674), new Color(0x2C581B), EnumSaplingType.Default, EnumExtraTreeLog.Hickory, new Color(0x3E3530),  WorldGenTree3.Pecan.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Pecan);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Large.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Slow.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
			species.addFruitFamily(ExtraTreeFruitFamily.Citrus);
		}
	},
	Elm("ulmus", "procera", EnumLeafType.DECIDUOUS, new Color(0x7C9048), new Color(0x7CBE48), EnumSaplingType.Default, EnumExtraTreeLog.Elm, new Color(0x848386),  WorldGenTree3.Elm.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smaller.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Low.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Average.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
		}
	},
	Elder("sambucus", "nigra", EnumLeafType.DECIDUOUS, new Color(0xAEB873), new Color(0xE0E7BD), EnumSaplingType.Default, EnumExtraTreeLog.Elder, new Color(0xD8B874),  WorldGenTree3.Elder.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Elderberry);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smaller.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Low.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
			species.addFruitFamily(ExtraTreeFruitFamily.Berry);
			species.addFruitFamily(ExtraTreeFruitFamily.Citrus);
		}
	},
	Holly("ilex", "aquifolium", EnumLeafType.DECIDUOUS, new Color(0x254B4C), new Color(0x6E9284), EnumSaplingType.Default, EnumExtraTreeLog.Holly, new Color(0xB5AA85),  WorldGenHolly.Holly.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Average.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Low.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ExtraTreeFruitFamily.Citrus);
		}
	},
	Hornbeam("carpinus", "betulus", EnumLeafType.DECIDUOUS, new Color(0x96A71B), new Color(0x96DD1B), EnumSaplingType.Default, EnumExtraTreeLog.Hornbeam, new Color(0xA39276),  WorldGenTree3.Hornbeam.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smaller.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Low.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Lower.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Slow.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
		}
	},
	Sallow("salix", "caprea", EnumLeafType.WILLOW, new Color(0xAEB323), new Color(0xB7EC25), EnumSaplingType.Default, EnumForestryWoodType.WILLOW, new Color(0xA19A95),  WorldGenTree3.Sallow.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Large.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Low.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ExtraTreeFruitFamily.Citrus);
		}
	},
	AcornOak("quercus", "robur", EnumLeafType.DECIDUOUS, new Color(0x66733E), new Color(0x9EA231), EnumSaplingType.Default, EnumVanillaWoodType.OAK, new Color(0x614D30),  WorldGenTree3.AcornOak.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Acorn);
			template.set(EnumTreeChromosome.GIRTH,  ForestryAllele.Int.Int2.getAllele());
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Large.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Low.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
		}
	},
	Fir("abies", "alba", EnumLeafType.CONIFERS, new Color(0x6F7C20), new Color(0x6FD120), EnumSaplingType.Default, EnumExtraTreeLog.Fir, new Color(0x828382),  WorldGenFir.SilverFir.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Large.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Slow.getAllele());
		}
	},
	Cedar("cedrus", "libani", EnumLeafType.CONIFERS, new Color(0x95A370), new Color(0x95E870), EnumSaplingType.Default, EnumExtraTreeLog.Cedar, new Color(0xAD764F),  WorldGenConifer.Cedar.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.GIRTH,  ForestryAllele.Int.Int2.getAllele());
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smaller.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Slower.getAllele());
		}
	},
	Olive("olea", "europaea", EnumLeafType.DECIDUOUS, new Color(0x3C4834), new Color(0x3C4834), EnumSaplingType.Default, EnumExtraTreeLog.Olive, new Color(0x7B706A),  WorldGenTree2.Olive.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Olive);
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Average.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
			species.addFruitFamily(ExtraTreeFruitFamily.Citrus);
		}
	},
	RedMaple("acer", "ubrum", EnumLeafType.MAPLE, new Color(0xE82E17), new Color(0xE82E17), EnumSaplingType.Default, EnumForestryWoodType.MAPLE, new Color(0x8A8781),  WorldGenMaple.RedMaple.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Average.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.High.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ExtraTreeFruitFamily.Citrus);
		}
	},
	BalsamFir("abies", "balsamea", EnumLeafType.CONIFERS, new Color(0x74A07C), new Color(0x74A07C), EnumSaplingType.Default, EnumExtraTreeLog.Fir, new Color(0x828382),  WorldGenFir.BalsamFir.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Average.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Slow.getAllele());
		}
	},
	LoblollyPine("pinus", "taeda", EnumLeafType.CONIFERS, new Color(0x6F8A47), new Color(0x6F8A47), EnumSaplingType.Default, EnumForestryWoodType.PINE, new Color(0x735649),  WorldGenConifer.LoblollyPine.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smaller.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Slow.getAllele());
		}
	},
	Sweetgum("liquidambar", "styraciflua", EnumLeafType.DECIDUOUS, new Color(0x8B8762), new Color(0x8B8762), EnumSaplingType.Default, EnumExtraTreeLog.Sweetgum, new Color(0xA1A19C),  WorldGenTree2.Sweetgum.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Average.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.High.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Low.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Average.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
		}
	},
	Locust("robinia", "pseudoacacia", EnumLeafType.DECIDUOUS, new Color(0x887300), new Color(0x887300), EnumSaplingType.Default, EnumExtraTreeLog.Locust, new Color(0xADACBC),  WorldGenTree2.Locust.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
			species.addFruitFamily(ExtraTreeFruitFamily.Citrus);
		}
	},
	Pear("pyrus", "communis", EnumLeafType.DECIDUOUS, new Color(0x5E8826), new Color(0x5E8826), EnumSaplingType.Default, EnumExtraTreeLog.Pear, new Color(0xA89779),  WorldGenTree2.Pear.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Pear);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smaller.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Low.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.High.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Fast.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ExtraTreeFruitFamily.Citrus);
		}
	},
	OsangeOsange("maclura", "pomifera", EnumLeafType.JUNGLE, new Color(0x687A50), new Color(0x687A50), EnumSaplingType.Default, EnumExtraTreeLog.Maclura, new Color(0x8B5734),  WorldGenJungle.OsangeOsange.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.OsangeOsange);
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Lower.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
			species.addFruitFamily(ExtraTreeFruitFamily.Citrus);
		}
	},
	OldFustic("maclura", "tinctoria", EnumLeafType.JUNGLE, new Color(0x687A50), new Color(0x687A50), EnumSaplingType.Default, EnumExtraTreeLog.Maclura, new Color(0x8B5734),  WorldGenJungle.OldFustic.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smaller.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
		}
	},
	Brazilwood("caesalpinia", "echinata", EnumLeafType.JUNGLE, new Color(0x607459), new Color(0x607459), EnumSaplingType.Default, EnumExtraTreeLog.Brazilwood, new Color(0x9E8068),  WorldGenJungle.Brazilwood.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smaller.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Low.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Lower.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
		}
	},
	Logwood("haematoxylum", "campechianum", EnumLeafType.JUNGLE, new Color(0x889F6B), new Color(0x889F6B), EnumSaplingType.Default, EnumExtraTreeLog.Logwood, new Color(0xF9E2D2),  WorldGenJungle.Logwood.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Average.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Low.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
		}
	},
	Rosewood("dalbergia", "latifolia", EnumLeafType.JUNGLE, new Color(0x879B22), new Color(0x879B22), EnumSaplingType.Default, EnumExtraTreeLog.Rosewood, new Color(0x998666),  WorldGenJungle.Rosewood.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Average.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lowest.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
		}
	},
	Purpleheart("peltogyne", "spp", EnumLeafType.JUNGLE, new Color(0x778F55), new Color(0x778F55), EnumSaplingType.Default, EnumExtraTreeLog.Purpleheart, new Color(0x9392A2),  WorldGenJungle.Purpleheart.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Large.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
		}
	},
	Iroko("milicia", "excelsa", EnumLeafType.DECIDUOUS, new Color(0xAFC86C), new Color(0xAFC86C), EnumSaplingType.Default, EnumExtraTreeLog.Iroko, new Color(0x605C5B),  WorldGenTree2.Iroko.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Average.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Low.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
		}
	},
	Gingko("ginkgo", "biloba", EnumLeafType.JUNGLE, new Color(0x719651), new Color(0x719651), EnumSaplingType.Default, EnumExtraTreeLog.Gingko, new Color(0xADAE9C),  WorldGenTree2.Gingko.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.GingkoNut);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Large.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Lower.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Low.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
		}
	},
	Brazilnut("bertholletia", "excelsa", EnumLeafType.JUNGLE, new Color(0x7C8F7B), new Color(0x7C8F7B), EnumSaplingType.Default, EnumVanillaWoodType.JUNGLE, new Color(0x53411A),  WorldGenJungle.BrazilNut.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.BrazilNut);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Larger.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Low.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Low.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
		}
	},
	RoseGum("eucalyptus", "grandis", EnumLeafType.JUNGLE, new Color(0x9CA258), new Color(0x9CA258), EnumSaplingType.Default, EnumExtraTreeLog.Eucalyptus, new Color(0xEADEDA), WorldGenEucalyptus.RoseGum.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Largest.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Low.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Lower.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Low.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Slowest.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
		}
	},
	SwampGum("eucalyptus", "grandis", EnumLeafType.JUNGLE, new Color(0xA2C686), new Color(0xA2C686), EnumSaplingType.Default, EnumExtraTreeLog.Eucalyptus2, new Color(0x867E65), WorldGenEucalyptus.SwampGum.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.GIRTH,  ForestryAllele.Int.Int2.getAllele());
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Lowest.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Slower.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
		}
	},
	Box("boxus", "sempervirens", EnumLeafType.DECIDUOUS, new Color(0x72996D), new Color(0x72996D), EnumSaplingType.Default, EnumExtraTreeLog.Box, new Color(0xAB6F57), WorldGenTree2.Box.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smaller.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Faster.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
			species.addFruitFamily(ExtraTreeFruitFamily.Citrus);
		}
	},
	Clove("syzygium", "aromaticum", EnumLeafType.DECIDUOUS, new Color(0x7A821F), new Color(0x7A821F), EnumSaplingType.Default, EnumExtraTreeLog.Syzgium, new Color(0xAB6F57), WorldGenTree2.Clove.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Clove);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smaller.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Low.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.High.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Fast.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
		}
	},
	Coffee("coffea", "arabica", EnumLeafType.JUNGLE, new Color(0x6F9065), new Color(0x6F9065), EnumSaplingType.Default, EnumVanillaWoodType.JUNGLE, new Color(0x53411A), WorldGenJungle.Coffee.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Coffee);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Large.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Average.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Fast.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
		}
	},
	MonkeyPuzzle("araucaria", "araucana", EnumLeafType.CONIFERS, new Color(0x576158), new Color(0x576158), EnumSaplingType.Default, EnumForestryWoodType.PINE, new Color(0x735649), WorldGenConifer.MonkeyPuzzle.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.GIRTH,  ForestryAllele.Int.Int2.getAllele());
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Average.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Lower.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Low.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
		}
	},
	RainbowGum("eucalyptus", "deglupta", EnumLeafType.JUNGLE, new Color(0xB7F025), new Color(0xB7F025), EnumSaplingType.Default, EnumExtraTreeLog.Eucalyptus3, new Color(0x6CB03F),  WorldGenEucalyptus.RainbowGum.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Low.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Lower.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
		}
	},
	PinkIvory("berchemia", "zeyheri", EnumLeafType.DECIDUOUS, new Color(0x7C9159), new Color(0x7C9159), EnumSaplingType.Default, EnumExtraTreeLog.PinkIvory, new Color(0x7F6554),  WorldGenTree.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
		}
	},
	Blackcurrant("ribes", "nigrum", EnumLeafType.DECIDUOUS, new Color(0xA6DA5C), new Color(0xA6DA5C), EnumSaplingType.Shrub, EnumExtraTreeLog.EMPTY, new Color(0xFFFFFF),  WorldGenShrub.Shrub.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Blackcurrant);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Low.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Average.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Faster.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
			species.addFruitFamily(ExtraTreeFruitFamily.Berry);
		}
	},
	Redcurrant("ribes", "rubrum", EnumLeafType.DECIDUOUS, new Color(0x74AC00), new Color(0x74AC00), EnumSaplingType.Shrub, EnumExtraTreeLog.EMPTY, new Color(0xFFFFFF),  WorldGenShrub.Shrub.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Redcurrant);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Average.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Average.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Low.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Faster.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
			species.addFruitFamily(ExtraTreeFruitFamily.Berry);
		}
	},
	Blackberry("rubus", "fruticosus", EnumLeafType.DECIDUOUS, new Color(0x92C15B), new Color(0x92C15B), EnumSaplingType.Shrub, EnumExtraTreeLog.EMPTY, new Color(0xFFFFFF),  WorldGenShrub.Shrub.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Blackberry);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.High.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Faster.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
			species.addFruitFamily(ExtraTreeFruitFamily.Berry);
		}
	},
	Raspberry("rubus", "idaeus", EnumLeafType.DECIDUOUS, new Color(0x83B96E), new Color(0x83B96E), EnumSaplingType.Shrub, EnumExtraTreeLog.EMPTY, new Color(0xFFFFFF),  WorldGenShrub.Shrub.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Raspberry);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Low.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Average.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Faster.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
			species.addFruitFamily(ExtraTreeFruitFamily.Berry);
		}
	},
	Blueberry("vaccinium", "corymbosum", EnumLeafType.DECIDUOUS, new Color(0x72C750), new Color(0x72C750), EnumSaplingType.Shrub, EnumExtraTreeLog.EMPTY, new Color(0xFFFFFF),  WorldGenShrub.Shrub.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Blueberry);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Average.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Average.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Low.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Faster.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
			species.addFruitFamily(ExtraTreeFruitFamily.Berry);
		}
	},
	Cranberry("vaccinium", "oxycoccos", EnumLeafType.DECIDUOUS, new Color(0x96D179), new Color(0x96D179), EnumSaplingType.Shrub, EnumExtraTreeLog.EMPTY, new Color(0xFFFFFF),  WorldGenShrub.Shrub.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Cranberry);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Average.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.High.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Low.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Faster.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
			species.addFruitFamily(ExtraTreeFruitFamily.Berry);
		}
	},
	Juniper("juniperus", "communis", EnumLeafType.CONIFERS, new Color(0x90B149), new Color(0x90B149), EnumSaplingType.Shrub, EnumExtraTreeLog.EMPTY, new Color(0xFFFFFF),  WorldGenShrub.Shrub.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Juniper);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Low.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Low.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Low.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Faster.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
			species.addFruitFamily(ExtraTreeFruitFamily.Berry);
		}
	},
	Gooseberry("ribes", "grossularia", EnumLeafType.DECIDUOUS, new Color(0x79BB00), new Color(0x79BB00), EnumSaplingType.Shrub, EnumExtraTreeLog.EMPTY, new Color(0xFFFFFF),  WorldGenShrub.Shrub.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Gooseberry);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.High.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.High.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Faster.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
			species.addFruitFamily(ExtraTreeFruitFamily.Berry);
		}
	},
	GoldenRaspberry("rubus", "occidentalis", EnumLeafType.DECIDUOUS, new Color(0x83B96E), new Color(0x83B96E), EnumSaplingType.Shrub, EnumExtraTreeLog.EMPTY, new Color(0xFFFFFF),  WorldGenShrub.Shrub.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.GoldenRaspberry);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smaller.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Low.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Average.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Fastest.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
			species.addFruitFamily(ExtraTreeFruitFamily.Berry);
		}
	},
	Cinnamon("cinnamomum", "cassia", EnumLeafType.JUNGLE, new Color(0x738E0B), new Color(0x738E0B), EnumSaplingType.Default, EnumExtraTreeLog.Cinnamon, new Color(0x86583C),  WorldGenLazy.Tree.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Average.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Lower.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Fast.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
		}
	},
	Coconut("cocous", "nucifera", EnumLeafType.PALM, new Color(0x649923), new Color(0x649923), EnumSaplingType.Default, EnumVanillaWoodType.JUNGLE, new Color(0x53411A),  WorldGenPalm.Coconut.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Coconut);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smaller.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Low.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Average.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Fast.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
		}
	},
	Cashew("anacardium", "occidentale", EnumLeafType.JUNGLE, new Color(0xABB962), new Color(0xABB962), EnumSaplingType.Default, EnumVanillaWoodType.JUNGLE, new Color(0x53411A),  WorldGenLazy.Tree.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Cashew);
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Low.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
		}
	},
	Avacado("persea", "americana", EnumLeafType.JUNGLE, new Color(0x96A375), new Color(0x96A375), EnumSaplingType.Default, EnumVanillaWoodType.JUNGLE, new Color(0x53411A),  WorldGenLazy.Tree.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Avacado);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Average.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
			species.addFruitFamily(ExtraTreeFruitFamily.Citrus);
		}
	},
	Nutmeg("myristica", "fragrans", EnumLeafType.JUNGLE, new Color(0x488D4C), new Color(0x488D4C), EnumSaplingType.Default, EnumVanillaWoodType.JUNGLE, new Color(0x53411A),  WorldGenLazy.Tree.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Nutmeg);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smaller.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.High.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Low.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
		}
	},
	Allspice("pimenta", "dioica", EnumLeafType.JUNGLE, new Color(0x7C9724), new Color(0x7C9724), EnumSaplingType.Default, EnumVanillaWoodType.JUNGLE, new Color(0x53411A),  WorldGenLazy.Tree.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Allspice);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Low.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.High.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
		}
	},
	Chilli("capsicum", "annuum", EnumLeafType.JUNGLE, new Color(0x2A9F01), new Color(0x2A9F01), EnumSaplingType.Default, EnumVanillaWoodType.JUNGLE, new Color(0x53411A),  WorldGenLazy.Tree.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Chilli);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smaller.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Higher.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Fast.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
		}
	},
	StarAnise("illicium", "verum", EnumLeafType.JUNGLE, new Color(0x7FC409), new Color(0x7FC409), EnumSaplingType.Default, EnumVanillaWoodType.JUNGLE, new Color(0x53411A),  WorldGenLazy.Tree.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.StarAnise);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Average.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.High.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
		}
	},
	Mango("mangifera", "indica", EnumLeafType.JUNGLE, new Color(0x87B574), new Color(0x87B574), EnumSaplingType.Default, EnumVanillaWoodType.JUNGLE, new Color(0x53411A),  WorldGenTropical.Mango.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Mango);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smaller.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Low.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Average.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Fast.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
			species.addFruitFamily(ExtraTreeFruitFamily.Citrus);
		}
	},
	Starfruit("averrhoa", "carambola", EnumLeafType.JUNGLE,  new Color(0x6DA92D), new Color(0x6DA92D), EnumSaplingType.Default, EnumVanillaWoodType.JUNGLE, new Color(0x53411A),  WorldGenLazy.Tree.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Starfruit);
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Average.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Fast.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
			species.addFruitFamily(ExtraTreeFruitFamily.Citrus);
		}
	},
	Candlenut("aleurites", "moluccana", EnumLeafType.DECIDUOUS, new Color(0x8AA36C), new Color(0x8AA36C), EnumSaplingType.Default, EnumVanillaWoodType.JUNGLE, new Color(0x53411A),  WorldGenLazy.Tree.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Candlenut);
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Lowest.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Low.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Low.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
		}
	},
	DwarfHazel("Corylus", "americana", EnumLeafType.DECIDUOUS, new Color(0x9BB552), new Color(0x9BE152), EnumSaplingType.Shrub, EnumExtraTreeLog.Hazel, new Color(0xAA986F),  WorldGenShrub.Shrub.class){
		@Override
		protected void setAlleles(AlleleTemplate template)  {
			template.set(EnumTreeChromosome.FRUITS, ExtraTreeFruitGene.Hazelnut);
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Average.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Faster.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
		}
	};

	private String branchName;
	private String binomial;
	private Color leafColor;
	private Color leafPollinatedColor;
	private Color woodColor;
	private EnumLeafType leafType;
	private ILeafSpriteProvider leafSpriteProvider;
	private EnumSaplingType saplingType;
	private IWoodType woodType;
	private IWoodProvider woodProvider;
	private ITreeGenerator treeGenerator;
	private IClassification branch;
	private IAlleleTreeSpecies species;
	private ITreeGenome genome;
	private AlleleTemplate template;
	private static final String unlocalizedName = "extratrees.species.%s.name";
	private static final String unlocalizedDesc = "extratrees.species.%s.desc";

	ExtraTreeSpecies(String branch, String binomial, EnumLeafType leafType, Color leafColor, Color leafPollinatedColor, EnumSaplingType saplingType, IWoodType woodType, Color woodColor, Class<? extends WorldGenerator> treeGenerator) {
		this.binomial = binomial;
		this.leafType = leafType;
		this.leafColor = leafColor;
		this.leafPollinatedColor = leafPollinatedColor;
		this.leafSpriteProvider = TreeManager.treeFactory.getLeafIconProvider(leafType, leafColor, leafPollinatedColor);
		this.saplingType = saplingType;
		this.woodType = woodType;
		this.woodProvider = new WoodProvider(woodType);
		this.treeGenerator = new TreeGenerator(treeGenerator, woodType);
		this.woodColor = woodColor;
		this.branchName = branch;


	}

	public static void initTrees() {
		for (ExtraTreeSpecies tree : values()) {
			tree.init();
		}
		for (ExtraTreeSpecies tree : values()) {
			tree.registerMutations();
		}
	}

	public static void preInitTrees() {
		for (ExtraTreeSpecies tree : values()) {
			tree.preInit();
		}
	}

	public void preInit() {
		final String scientific = StringUtils.capitalize(branchName);
		final String uid = "trees." + branchName;
		branch = AlleleManager.alleleRegistry.getClassification("genus." + uid);
		if (branch == null) {
			branch = AlleleManager.alleleRegistry.createAndRegisterClassification(IClassification.EnumClassLevel.GENUS, uid, scientific);
		}
		IAlleleTreeSpeciesBuilder speciesBuilder = TreeManager.treeFactory.createSpecies(getUID(), String.format(unlocalizedName, getUID()), getAuthority(), String.format(unlocalizedDesc, getUID()), isDominant(),
				branch, getBinomial(), Constants.EXTRA_TREES_MOD_ID, leafSpriteProvider, saplingType.getGermlingModelProvider(leafColor, woodColor), woodProvider, treeGenerator);
		setSpeciesProperties(speciesBuilder);
		species = speciesBuilder.build();
		branch.addMemberSpecies(species);
	}

	public void init() {
		template = new AlleleTemplate();
		template.set(EnumTreeChromosome.SPECIES, species);
		setAlleles(template);
		genome = TreeManager.treeRoot.templateAsGenome(template.getAlleles());
		TreeManager.treeRoot.registerTemplate(template.getAlleles());
	}

	protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {

	}

	protected void setAlleles(AlleleTemplate template) {

	}

	protected void registerMutations() {

	}

	public String getUID() {
		return name().toLowerCase();
	}

	public boolean isDominant() {
		return true;
	}

	public String getBinomial() {
		return binomial;
	}

	public String getAuthority() {
		return "Binnie";
	}

	public Color getLeafColor() {
		return leafColor;
	}

	public Color getWoodColor() {
		return woodColor;
	}

	public String getBranchName() {
		return branchName;
	}

	public IAlleleTreeSpecies getSpecies() {
		return species;
	}

	private static class AlleleTemplate {
		IAllele[] alleles;

		protected AlleleTemplate() {
			alleles = TreeManager.treeRoot.getDefaultTemplate();
		}

		protected AlleleTemplate(IAllele[] alleles) {
			this.alleles = alleles;
		}

		public <T extends Enum<T> & IChromosomeType> void set(T chromosomeType, IAllele allele) {
			if (allele == null) {
				throw new NullPointerException("Allele must not be null");
			}
			if (!chromosomeType.getAlleleClass().isInstance(allele)) {
				throw new IllegalArgumentException("Allele is the wrong type. Expected: " + chromosomeType + " Got: " + allele);
			}
			alleles[chromosomeType.ordinal()] = allele;
		}

		public IAllele[] getAlleles() {
			return alleles;
		}
	}
}
