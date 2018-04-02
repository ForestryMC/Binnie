package binnie.extratrees.genetics;

import com.google.common.base.Preconditions;

import javax.annotation.Nullable;
import java.awt.Color;
import java.util.Arrays;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.mojang.authlib.GameProfile;

import forestry.api.arboriculture.EnumForestryWoodType;
import forestry.api.arboriculture.EnumGermlingType;
import forestry.api.arboriculture.EnumLeafType;
import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.arboriculture.EnumVanillaWoodType;
import forestry.api.arboriculture.IAlleleTreeSpecies;
import forestry.api.arboriculture.IAlleleTreeSpeciesBuilder;
import forestry.api.arboriculture.ILeafSpriteProvider;
import forestry.api.arboriculture.ITree;
import forestry.api.arboriculture.ITreeGenerator;
import forestry.api.arboriculture.ITreeGenome;
import forestry.api.arboriculture.IWoodProvider;
import forestry.api.arboriculture.IWoodType;
import forestry.api.arboriculture.TreeManager;
import forestry.api.arboriculture.WoodBlockKind;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.EnumTolerance;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IClassification;
import forestry.api.world.ITreeGenData;
import forestry.arboriculture.ModuleArboriculture;
import forestry.arboriculture.genetics.ClimateGrowthProvider;
import forestry.arboriculture.genetics.ITreeDefinition;
import forestry.arboriculture.genetics.Tree;
import forestry.arboriculture.tiles.TileLeaves;
import forestry.arboriculture.worldgen.WorldGenLemon;
import forestry.arboriculture.worldgen.WorldGenPlum;
import forestry.core.genetics.alleles.AlleleBoolean;
import forestry.core.tiles.TileUtil;

import binnie.core.Constants;
import binnie.core.genetics.ForestryAllele;
import binnie.extratrees.gen.WorldGenAlder;
import binnie.extratrees.gen.WorldGenApple;
import binnie.extratrees.gen.WorldGenAsh;
import binnie.extratrees.gen.WorldGenBanana;
import binnie.extratrees.gen.WorldGenBeech;
import binnie.extratrees.gen.WorldGenConifer;
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
import binnie.extratrees.genetics.fruits.ETFruitFamily;
import binnie.extratrees.wood.EnumETLog;
import binnie.extratrees.wood.EnumShrubLog;

public enum ETTreeDefinition implements IStringSerializable, ITreeDefinition, ITreeGenerator {
	OrchardApple("malus", "domestica", EnumLeafType.DECIDUOUS, new Color(0x09E67E), new Color(0xFF9CF3), EnumSaplingType.Default, EnumETLog.Apple, new Color(0x7B7A7B)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenApple.OrchardApple(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Apple.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Higher.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ETFruitFamily.CITRUS);
		}
	},
	SweetCrabapple("malus", "coronaria", EnumLeafType.DECIDUOUS, new Color(0x7A9953), new Color(0xFC359F), EnumSaplingType.Default, EnumETLog.Apple, new Color(0x7B7A7B)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenApple.SweetCrabapple(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Crabapple.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.High.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Average.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Fast.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ETFruitFamily.CITRUS);
		}
	},
	FloweringCrabapple("malus", "hopa", EnumLeafType.DECIDUOUS, new Color(0x7A9953), new Color(0xFC359F), EnumSaplingType.Default, EnumETLog.Apple, new Color(0x7B7A7B)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenApple.FloweringCrabapple(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Crabapple.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Average.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Average.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Low.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Fast.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ETFruitFamily.CITRUS);
		}
	},
	PrairieCrabapple("malus", "ioensis", EnumLeafType.DECIDUOUS, new Color(0x7A9953), new Color(0xFC359F), EnumSaplingType.Default, EnumETLog.Apple, new Color(0x7B7A7B)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenApple.PrairieCrabapple(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Crabapple.getAllele());
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Low.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Average.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Average.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ETFruitFamily.CITRUS);
		}
	},
	Blackthorn("prunus", "spinosa", EnumLeafType.DECIDUOUS, new Color(0x6D8F1E), new Color(0xFF87C7), EnumSaplingType.Fruit, EnumForestryWoodType.PLUM, new Color(0xB68661)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenPlum(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Blackthorn.getAllele());
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Low.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Average.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Fast.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ETFruitFamily.CITRUS);
		}
	},
	CherryPlum("prunus", "cerasifera", EnumLeafType.DECIDUOUS, new Color(0x6D8F1E), new Color(0xFF87C7), EnumSaplingType.Fruit, EnumForestryWoodType.PLUM, new Color(0xB68661)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenPlum(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.CherryPlum.getAllele());
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Lower.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Average.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Fast.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ETFruitFamily.CITRUS);
		}
	},
	Peach("prunus", "persica", EnumLeafType.DECIDUOUS, new Color(0x6D8F1E), new Color(0xFF269A), EnumSaplingType.Fruit, EnumForestryWoodType.PLUM, new Color(0xB68661)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenPlum(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Peach.getAllele());
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Low.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Average.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Average.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ETFruitFamily.CITRUS);
		}
	},
	Nectarine("prunus", "nectarina", EnumLeafType.DECIDUOUS, new Color(0x6D8F1E), new Color(0xFF269A), EnumSaplingType.Fruit, EnumForestryWoodType.PLUM, new Color(0xB68661)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenPlum(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Nectarine.getAllele());
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Average.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Average.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ETFruitFamily.CITRUS);
		}
	},
	Apricot("prunus", "armeniaca", EnumLeafType.DECIDUOUS, new Color(0x6D8F1E), new Color(0xF5B8D8), EnumSaplingType.Fruit, EnumForestryWoodType.PLUM, new Color(0xB68661)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenPlum(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Apricot.getAllele());
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Low.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Average.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ETFruitFamily.CITRUS);
		}
	},
	Almond("prunus", "amygdalus", EnumLeafType.DECIDUOUS, new Color(0x6D8F1E), new Color(0xF584C0), EnumSaplingType.Fruit, EnumForestryWoodType.PLUM, new Color(0xB68661)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenPlum(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Almond.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Lower.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Fast.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ETFruitFamily.CITRUS);
		}
	},
	WildCherry("prunus", "avium", EnumLeafType.DECIDUOUS, new Color(0x6D8F1E), new Color(0xF7EBF6), EnumSaplingType.Fruit, EnumETLog.Cherry, new Color(0x716850)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenPlum(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.WildCherry.getAllele());
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
			species.addFruitFamily(ETFruitFamily.CITRUS);
			species.setTemperature(EnumTemperature.NORMAL);
			species.setRarity(0.0015F);
		}
	},
	SourCherry("prunus", "cerasus", EnumLeafType.DECIDUOUS, new Color(0x6D8F1E), new Color(0xF7EBF6), EnumSaplingType.Fruit, EnumETLog.Cherry, new Color(0x716850)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenPlum(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.SourCherry.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Lower.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Low.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ETFruitFamily.CITRUS);
		}
	},
	BlackCherry("prunus", "serotina", EnumLeafType.DECIDUOUS, new Color(0x6D8F1E), new Color(0xFAE1F8), EnumSaplingType.Fruit, EnumETLog.Cherry, new Color(0x716850)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenPlum(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.BlackCherry.getAllele());
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
			species.addFruitFamily(ETFruitFamily.CITRUS);
		}
	},
	Orange("citrus", "sinensis", EnumLeafType.JUNGLE, new Color(0x88AF54), new Color(0xA3B850), EnumSaplingType.Fruit, EnumForestryWoodType.CITRUS, new Color(0x5B4B39)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenLemon(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Orange.getAllele());
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Average.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Average.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Fast.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ETFruitFamily.CITRUS);
		}
	},
	Manderin("citrus", "reticulata", EnumLeafType.JUNGLE, new Color(0x88AF54), new Color(0xA3B850), EnumSaplingType.Fruit, EnumForestryWoodType.CITRUS, new Color(0x5B4B39)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenLemon(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Manderin.getAllele());
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Low.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.High.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Low.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ETFruitFamily.CITRUS);
		}
	},
	Satsuma("citrus", "unshiu", EnumLeafType.JUNGLE, new Color(0x88AF54), new Color(0xA3B850), EnumSaplingType.Fruit, EnumForestryWoodType.CITRUS, new Color(0x5B4B39)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenLemon(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Satsuma.getAllele());
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Low.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Low.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Fast.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ETFruitFamily.CITRUS);
		}
	},
	Tangerine("citrus", "tangerina", EnumLeafType.JUNGLE, new Color(0x88AF54), new Color(0xA3B850), EnumSaplingType.Fruit, EnumForestryWoodType.CITRUS, new Color(0x5B4B39)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenLemon(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Tangerine.getAllele());
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
			species.addFruitFamily(ETFruitFamily.CITRUS);
		}
	},
	Lime("citrus", "latifolia", EnumLeafType.JUNGLE, new Color(0x88AF54), new Color(0xA3B850), EnumSaplingType.Fruit, EnumForestryWoodType.CITRUS, new Color(0x5B4B39)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenLemon(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Lime.getAllele());
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Average.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Low.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ETFruitFamily.CITRUS);
		}
	},
	KeyLime("citrus", "aurantifolia", EnumLeafType.JUNGLE, new Color(0x88AF54), new Color(0xA3B850), EnumSaplingType.Fruit, EnumForestryWoodType.CITRUS, new Color(0x5B4B39)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenLemon(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.KeyLime.getAllele());
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
			species.addFruitFamily(ETFruitFamily.CITRUS);
		}
	},
	FingerLime("citrus", "australasica", EnumLeafType.JUNGLE, new Color(0x88AF54), new Color(0xA3B850), EnumSaplingType.Fruit, EnumForestryWoodType.CITRUS, new Color(0x5B4B39)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenLemon(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.FingerLime.getAllele());
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.High.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Low.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Fast.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ETFruitFamily.CITRUS);
		}
	},
	Pomelo("citrus", "maxima", EnumLeafType.JUNGLE, new Color(0x88AF54), new Color(0xA3B850), EnumSaplingType.Fruit, EnumForestryWoodType.CITRUS, new Color(0x5B4B39)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenLemon(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Pomelo.getAllele());
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Low.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Average.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ETFruitFamily.CITRUS);
		}
	},
	Grapefruit("citrus", "paradisi", EnumLeafType.JUNGLE, new Color(0x88AF54), new Color(0xA3B850), EnumSaplingType.Fruit, EnumForestryWoodType.CITRUS, new Color(0x5B4B39)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenLemon(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Grapefruit.getAllele());
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
			species.addFruitFamily(ETFruitFamily.CITRUS);
		}
	},
	Kumquat("citrus", "margarita", EnumLeafType.JUNGLE, new Color(0x88AF54), new Color(0xA3B850), EnumSaplingType.Fruit, EnumForestryWoodType.CITRUS, new Color(0x5B4B39)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenLemon(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Kumquat.getAllele());
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.High.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Low.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ETFruitFamily.CITRUS);
		}
	},
	Citron("citrus", "medica", EnumLeafType.JUNGLE, new Color(0x88AF54), new Color(0xA3B850), EnumSaplingType.Fruit, EnumForestryWoodType.CITRUS, new Color(0x5B4B39)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenLemon(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Citron.getAllele());
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.High.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Average.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ETFruitFamily.CITRUS);
		}
	},
	BuddhaHand("citrus", "sarcodactylus", EnumLeafType.JUNGLE, new Color(0x88AF54), new Color(0xA3B850), EnumSaplingType.Fruit, EnumForestryWoodType.CITRUS, new Color(0x5B4B39)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenLemon(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.BuddhaHand.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Average.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Low.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ETFruitFamily.CITRUS);
		}
	},
	Banana("musa", "sinensis", EnumLeafType.PALM, new Color(0xA1CD8E), new Color(0x44E500), EnumSaplingType.Default, EnumETLog.Banana, new Color(0x85924F)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenBanana(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Banana.getAllele());
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
	RedBanana("musa", "rubra", EnumLeafType.PALM, new Color(0xA1CD8E), new Color(0x44E500), EnumSaplingType.Default, EnumETLog.Banana, new Color(0x85924F)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenBanana(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.RedBanana.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
		}
	},
	Plantain("musa", "paradisiaca", EnumLeafType.PALM, new Color(0xA1CD8E), new Color(0x44E500), EnumSaplingType.Default, EnumETLog.Banana, new Color(0x85924F)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenBanana(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Plantain.getAllele());
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Average.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Lower.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
		}
	},
	Butternut("juglans", "cinerea", EnumLeafType.DECIDUOUS, new Color(0x82B58C), new Color(0x82DD8C), EnumSaplingType.Default, EnumETLog.Butternut, new Color(0xB7ADA0)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenWalnut.Butternut(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Butternut.getAllele());
			template.set(EnumTreeChromosome.GIRTH, ForestryAllele.Int.Int2.getAllele());
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smaller.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Low.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
			species.addFruitFamily(ETFruitFamily.CITRUS);
		}
	},
	Rowan("sorbus", "aucuparia", EnumLeafType.DECIDUOUS, new Color(0x9EC79B), new Color(0x9EE8B2), EnumSaplingType.Default, EnumETLog.Rowan, new Color(0xB6B09B)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenSorbus.Rowan(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Larger.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Low.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
			species.addFruitFamily(ETFruitFamily.BERRY);
		}
	},
	Hemlock("tsuga", "heterophylla", EnumLeafType.CONIFERS, new Color(0x5CAC72), new Color(0x5CD172), EnumSaplingType.Default, EnumETLog.Hemlock, new Color(0xADA39B)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenConifer.WesternHemlock(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Average.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Low.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Slower.getAllele());
			template.set(EnumTreeChromosome.GIRTH, ForestryAllele.Int.Int2.getAllele());
		}
	},
	Ash("fraxinus", "excelsior", EnumLeafType.DECIDUOUS, new Color(0x488E2B), new Color(0x48E42B), EnumSaplingType.Default, EnumETLog.Ash, new Color(0x898982)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenAsh.CommonAsh(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Low.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
			species.addFruitFamily(ETFruitFamily.CITRUS);
		}
	},
	Alder("alnus", "glutinosa", EnumLeafType.DECIDUOUS, new Color(0x698A33), new Color(0x69AE33), EnumSaplingType.Default, EnumETLog.Alder, new Color(0xC6C0B8)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenAlder.CommonAlder(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
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
	Beech("fagus", "sylvatica", EnumLeafType.DECIDUOUS, new Color(0x83A04C), new Color(0x83C64C), EnumSaplingType.Default, EnumETLog.Beech, new Color(0xB2917E)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenBeech.CommonBeech(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Beechnut.getAllele());
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Average.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Lower.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
		}
	},
	CopperBeech("fagus", "purpurea", EnumLeafType.DECIDUOUS, new Color(0x801318), new Color(0xD15B4D), EnumSaplingType.Default, EnumETLog.Beech, new Color(0xB2917E)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenBeech.CopperBeech(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Beechnut.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Slow.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
		}
	},
	Aspen("populus", "tremula", EnumLeafType.DECIDUOUS, new Color(0x8ACC37), new Color(0x8AE18F), EnumSaplingType.Default, EnumForestryWoodType.POPLAR, new Color(0x8CA687)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenPoplar.Aspen(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Average.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
		}
	},
	Yew("taxus", "baccata", EnumLeafType.CONIFERS, new Color(0x948A4D), new Color(0x94AE4D), EnumSaplingType.Default, EnumETLog.Yew, new Color(0xD1BBC1)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenConifer.Yew(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Large.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
		}
	},
	Cypress("chamaecyparis", "lawsoniana", EnumLeafType.CONIFERS, new Color(0x89C9A7), new Color(0x89DDC6), EnumSaplingType.Poplar, EnumETLog.Cypress, new Color(0x9A8483)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenConifer.Cypress(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Larger.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Low.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Slow.getAllele());
		}
	},
	DouglasFir("pseudotsuga", "menziesii", EnumLeafType.CONIFERS, new Color(0x99B582), new Color(0x99D1AA), EnumSaplingType.Default, EnumETLog.Fir, new Color(0x828382)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenFir.DouglasFir(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.GIRTH, ForestryAllele.Int.Int2.getAllele());
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smaller.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Low.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Slower.getAllele());
		}
	},
	Hazel("Corylus", "avellana", EnumLeafType.DECIDUOUS, new Color(0x9BB552), new Color(0x9BE152), EnumSaplingType.Default, EnumETLog.Hazel, new Color(0xAA986F)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenTree3.Hazel(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Hazelnut.getAllele());
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
	Sycamore("ficus", "sycomorus", EnumLeafType.DECIDUOUS, new Color(0xA0A52F), new Color(0xB4D55C), EnumSaplingType.Default, EnumETLog.Fig, new Color(0x807357)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenTree3.Sycamore(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Fig.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Lowest.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
			species.addFruitFamily(ETFruitFamily.CITRUS);
		}
	},
	Whitebeam("sorbus", "aria", EnumLeafType.DECIDUOUS, new Color(0xBACE99), new Color(0x72863F), EnumSaplingType.Default, EnumETLog.Whitebeam, new Color(0x786A6D)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenSorbus.Whitebeam(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smaller.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
		}
	},
	Hawthorn("crataegus", "monogyna", EnumLeafType.DECIDUOUS, new Color(0x6BA84A), new Color(0x98B77B), EnumSaplingType.Default, EnumETLog.Hawthorn, new Color(0x5F5745)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenTree3.Hawthorn(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Average.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
		}
	},
	Pecan("carya", "illinoinensis", EnumLeafType.DECIDUOUS, new Color(0x85B674), new Color(0x2C581B), EnumSaplingType.Default, EnumETLog.Hickory, new Color(0x3E3530)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenTree3.Pecan(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Pecan.getAllele());
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Large.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Slow.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
			species.addFruitFamily(ETFruitFamily.CITRUS);
		}
	},
	Elm("ulmus", "procera", EnumLeafType.DECIDUOUS, new Color(0x7C9048), new Color(0x7CBE48), EnumSaplingType.Default, EnumETLog.Elm, new Color(0x848386)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenTree3.Elm(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
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
	Elder("sambucus", "nigra", EnumLeafType.DECIDUOUS, new Color(0xAEB873), new Color(0xE0E7BD), EnumSaplingType.Default, EnumETLog.Elder, new Color(0xD8B874)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenTree3.Elder(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Elderberry.getAllele());
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smaller.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Low.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
			species.addFruitFamily(ETFruitFamily.BERRY);
			species.addFruitFamily(ETFruitFamily.CITRUS);
		}
	},
	Holly("ilex", "aquifolium", EnumLeafType.DECIDUOUS, new Color(0x254B4C), new Color(0x6E9284), EnumSaplingType.Default, EnumETLog.Holly, new Color(0xB5AA85)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenHolly.Holly(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Average.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Low.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ETFruitFamily.CITRUS);
		}
	},
	Hornbeam("carpinus", "betulus", EnumLeafType.DECIDUOUS, new Color(0x96A71B), new Color(0x96DD1B), EnumSaplingType.Default, EnumETLog.Hornbeam, new Color(0xA39276)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenTree3.Hornbeam(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
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
	Sallow("salix", "caprea", EnumLeafType.WILLOW, new Color(0xAEB323), new Color(0xB7EC25), EnumSaplingType.Default, EnumForestryWoodType.WILLOW, new Color(0xA19A95)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenTree3.Sallow(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Large.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Low.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ETFruitFamily.CITRUS);
		}
	},
	AcornOak("quercus", "robur", EnumLeafType.DECIDUOUS, new Color(0x66733E), new Color(0x9EA231), EnumSaplingType.Default, EnumVanillaWoodType.OAK, new Color(0x614D30)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenTree3.AcornOak(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Acorn.getAllele());
			template.set(EnumTreeChromosome.GIRTH, ForestryAllele.Int.Int2.getAllele());
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
	Fir("abies", "alba", EnumLeafType.CONIFERS, new Color(0x6F7C20), new Color(0x6FD120), EnumSaplingType.Default, EnumETLog.Fir, new Color(0x828382)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenFir.SilverFir(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Large.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Slow.getAllele());
		}
	},
	Cedar("cedrus", "libani", EnumLeafType.CONIFERS, new Color(0x95A370), new Color(0x95E870), EnumSaplingType.Default, EnumETLog.Cedar, new Color(0xAD764F)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenConifer.Cedar(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.GIRTH, ForestryAllele.Int.Int2.getAllele());
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smaller.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Slower.getAllele());
		}
	},
	Olive("olea", "europaea", EnumLeafType.DECIDUOUS, new Color(0x3C4834), new Color(0x3C4834), EnumSaplingType.Default, EnumETLog.Olive, new Color(0x7B706A)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenTree2.Olive(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Olive.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Average.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
			species.addFruitFamily(ETFruitFamily.CITRUS);
		}
	},
	RedMaple("acer", "ubrum", EnumLeafType.MAPLE, new Color(0xE82E17), new Color(0xE82E17), EnumSaplingType.Default, EnumForestryWoodType.MAPLE, new Color(0x8A8781)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenMaple.RedMaple(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Average.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.High.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(ETFruitFamily.CITRUS);
		}
	},
	BalsamFir("abies", "balsamea", EnumLeafType.CONIFERS, new Color(0x74A07C), new Color(0x74A07C), EnumSaplingType.Default, EnumETLog.Fir, new Color(0x828382)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenFir.BalsamFir(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Average.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Slow.getAllele());
		}
	},
	LoblollyPine("pinus", "taeda", EnumLeafType.CONIFERS, new Color(0x6F8A47), new Color(0x6F8A47), EnumSaplingType.Default, EnumForestryWoodType.PINE, new Color(0x735649)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenConifer.LoblollyPine(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smaller.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Slow.getAllele());
		}
	},
	Sweetgum("liquidambar", "styraciflua", EnumLeafType.DECIDUOUS, new Color(0x8B8762), new Color(0x8B8762), EnumSaplingType.Default, EnumETLog.Sweetgum, new Color(0xA1A19C)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenTree2.Sweetgum(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
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
	Locust("robinia", "pseudoacacia", EnumLeafType.DECIDUOUS, new Color(0x887300), new Color(0x887300), EnumSaplingType.Default, EnumETLog.Locust, new Color(0xADACBC)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenTree2.Locust(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
			species.addFruitFamily(ETFruitFamily.CITRUS);
		}
	},
	Pear("pyrus", "communis", EnumLeafType.DECIDUOUS, new Color(0x5E8826), new Color(0x5E8826), EnumSaplingType.Default, EnumETLog.Pear, new Color(0xA89779)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenTree2.Pear(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Pear.getAllele());
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
			species.addFruitFamily(ETFruitFamily.CITRUS);
		}
	},
	OsangeOrange("maclura", "pomifera", EnumLeafType.JUNGLE, new Color(0x687A50), new Color(0x687A50), EnumSaplingType.Default, EnumETLog.Maclura, new Color(0x8B5734)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenJungle.OsangeOsange(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.OsangeOsange.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Lower.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
			species.addFruitFamily(ETFruitFamily.CITRUS);
		}
	},
	OldFustic("maclura", "tinctoria", EnumLeafType.JUNGLE, new Color(0x687A50), new Color(0x687A50), EnumSaplingType.Default, EnumETLog.Maclura, new Color(0x8B5734)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenJungle.OldFustic(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smaller.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
		}
	},
	Brazilwood("caesalpinia", "echinata", EnumLeafType.JUNGLE, new Color(0x607459), new Color(0x607459), EnumSaplingType.Default, EnumETLog.Brazilwood, new Color(0x9E8068)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenJungle.Brazilwood(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
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
	Logwood("haematoxylum", "campechianum", EnumLeafType.JUNGLE, new Color(0x889F6B), new Color(0x889F6B), EnumSaplingType.Default, EnumETLog.Logwood, new Color(0xF9E2D2)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenJungle.Logwood(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Average.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Low.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
		}
	},
	Rosewood("dalbergia", "latifolia", EnumLeafType.JUNGLE, new Color(0x879B22), new Color(0x879B22), EnumSaplingType.Default, EnumETLog.Rosewood, new Color(0x998666)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenJungle.Rosewood(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Average.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lowest.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
		}
	},
	Purpleheart("peltogyne", "spp", EnumLeafType.JUNGLE, new Color(0x778F55), new Color(0x778F55), EnumSaplingType.Default, EnumETLog.Purpleheart, new Color(0x9392A2)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenJungle.Purpleheart(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Large.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
		}
	},
	Iroko("milicia", "excelsa", EnumLeafType.DECIDUOUS, new Color(0xAFC86C), new Color(0xAFC86C), EnumSaplingType.Default, EnumETLog.Iroko, new Color(0x605C5B)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenTree2.Iroko(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Average.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Low.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
		}
	},
	Gingko("ginkgo", "biloba", EnumLeafType.JUNGLE, new Color(0x719651), new Color(0x719651), EnumSaplingType.Default, EnumETLog.Gingko, new Color(0xADAE9C)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenTree2.Gingko(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.GingkoNut.getAllele());
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
	Brazilnut("bertholletia", "excelsa", EnumLeafType.JUNGLE, new Color(0x7C8F7B), new Color(0x7C8F7B), EnumSaplingType.Default, EnumVanillaWoodType.JUNGLE, new Color(0x53411A)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenJungle.BrazilNut(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.BrazilNut.getAllele());
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
	RoseGum("eucalyptus", "grandis", EnumLeafType.JUNGLE, new Color(0x9CA258), new Color(0x9CA258), EnumSaplingType.Default, EnumETLog.Eucalyptus, new Color(0xEADEDA)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenEucalyptus.RoseGum(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
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
	SwampGum("eucalyptus", "grandis", EnumLeafType.JUNGLE, new Color(0xA2C686), new Color(0xA2C686), EnumSaplingType.Default, EnumETLog.Eucalyptus2, new Color(0x867E65)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenEucalyptus.SwampGum(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.GIRTH, ForestryAllele.Int.Int2.getAllele());
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Lowest.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Slower.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
		}
	},
	Box("boxus", "sempervirens", EnumLeafType.DECIDUOUS, new Color(0x72996D), new Color(0x72996D), EnumSaplingType.Default, EnumETLog.Box, new Color(0xAB6F57)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenTree2.Box(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smaller.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Faster.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
			species.addFruitFamily(ETFruitFamily.CITRUS);
		}
	},
	Clove("syzygium", "aromaticum", EnumLeafType.DECIDUOUS, new Color(0x7A821F), new Color(0x7A821F), EnumSaplingType.Default, EnumETLog.Syzgium, new Color(0xAB6F57)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenTree2.Clove(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Clove.getAllele());
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
	Coffee("coffea", "arabica", EnumLeafType.JUNGLE, new Color(0x6F9065), new Color(0x6F9065), EnumSaplingType.Default, EnumVanillaWoodType.JUNGLE, new Color(0x53411A)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenJungle.Coffee(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Coffee.getAllele());
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
	MonkeyPuzzle("araucaria", "araucana", EnumLeafType.CONIFERS, new Color(0x576158), new Color(0x576158), EnumSaplingType.Default, EnumForestryWoodType.PINE, new Color(0x735649)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenConifer.MonkeyPuzzle(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.GIRTH, ForestryAllele.Int.Int2.getAllele());
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
	RainbowGum("eucalyptus", "deglupta", EnumLeafType.JUNGLE, new Color(0xB7F025), new Color(0xB7F025), EnumSaplingType.Default, EnumETLog.Eucalyptus3, new Color(0x6CB03F)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenEucalyptus.RainbowGum(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.Low.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Lower.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
		}
	},
	PinkIvory("berchemia", "zeyheri", EnumLeafType.DECIDUOUS, new Color(0x7C9159), new Color(0x7C9159), EnumSaplingType.Default, EnumETLog.PinkIvory, new Color(0x7F6554)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenTree(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
		}
	},
	Blackcurrant("ribes", "nigrum", EnumLeafType.DECIDUOUS, new Color(0xA6DA5C), new Color(0xA6DA5C), EnumSaplingType.Shrub, EnumShrubLog.INSTANCE, new Color(0xFFFFFF)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenShrub.Shrub(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Blackcurrant.getAllele());
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
			species.addFruitFamily(ETFruitFamily.BERRY);
			species.setRarity(0.0025F);
			species.setGrowthProvider(new ClimateGrowthProvider(EnumTemperature.NORMAL, EnumTolerance.BOTH_1, EnumHumidity.NORMAL, EnumTolerance.NONE));
		}
	},
	Redcurrant("ribes", "rubrum", EnumLeafType.DECIDUOUS, new Color(0x74AC00), new Color(0x74AC00), EnumSaplingType.Shrub, EnumShrubLog.INSTANCE, new Color(0xFFFFFF)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenShrub.Shrub(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Redcurrant.getAllele());
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
			species.addFruitFamily(ETFruitFamily.BERRY);
			species.setRarity(0.0025F);
			species.setGrowthProvider(new ClimateGrowthProvider(EnumTemperature.NORMAL, EnumTolerance.BOTH_1, EnumHumidity.NORMAL, EnumTolerance.NONE));
		}
	},
	Blackberry("rubus", "fruticosus", EnumLeafType.DECIDUOUS, new Color(0x92C15B), new Color(0x92C15B), EnumSaplingType.Shrub, EnumShrubLog.INSTANCE, new Color(0xFFFFFF)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenShrub.Shrub(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Blackberry.getAllele());
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.High.getAllele());
			template.set(EnumTreeChromosome.SAPPINESS, ForestryAllele.Sappiness.Lower.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Faster.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
			species.addFruitFamily(ETFruitFamily.BERRY);
		}
	},
	Raspberry("rubus", "idaeus", EnumLeafType.DECIDUOUS, new Color(0x83B96E), new Color(0x83B96E), EnumSaplingType.Shrub, EnumShrubLog.INSTANCE, new Color(0xFFFFFF)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenShrub.Shrub(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Raspberry.getAllele());
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
			species.addFruitFamily(ETFruitFamily.BERRY);
			species.setRarity(0.0025F);
			species.setGrowthProvider(new ClimateGrowthProvider(EnumTemperature.NORMAL, EnumTolerance.BOTH_1, EnumHumidity.NORMAL, EnumTolerance.NONE));
		}
	},
	Blueberry("vaccinium", "corymbosum", EnumLeafType.DECIDUOUS, new Color(0x72C750), new Color(0x72C750), EnumSaplingType.Shrub, EnumShrubLog.INSTANCE, new Color(0xFFFFFF)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenShrub.Shrub(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Blueberry.getAllele());
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
			species.addFruitFamily(ETFruitFamily.BERRY);
			species.setRarity(0.0025F);
			species.setGrowthProvider(new ClimateGrowthProvider(EnumTemperature.NORMAL, EnumTolerance.BOTH_1, EnumHumidity.NORMAL, EnumTolerance.NONE));
		}
	},
	Cranberry("vaccinium", "oxycoccos", EnumLeafType.DECIDUOUS, new Color(0x96D179), new Color(0x96D179), EnumSaplingType.Shrub, EnumShrubLog.INSTANCE, new Color(0xFFFFFF)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenShrub.Shrub(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Cranberry.getAllele());
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
			species.addFruitFamily(ETFruitFamily.BERRY);
			species.setRarity(0.0025F);
			species.setGrowthProvider(new ClimateGrowthProvider(EnumTemperature.NORMAL, EnumTolerance.BOTH_1, EnumHumidity.NORMAL, EnumTolerance.NONE));
		}
	},
	Juniper("juniperus", "communis", EnumLeafType.CONIFERS, new Color(0x90B149), new Color(0x90B149), EnumSaplingType.Shrub, EnumShrubLog.INSTANCE, new Color(0xFFFFFF)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenShrub.Shrub(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Juniper.getAllele());
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
			species.addFruitFamily(ETFruitFamily.BERRY);
			species.setRarity(0.0025F);
			species.setGrowthProvider(new ClimateGrowthProvider(EnumTemperature.NORMAL, EnumTolerance.BOTH_1, EnumHumidity.NORMAL, EnumTolerance.NONE));
		}
	},
	Gooseberry("ribes", "grossularia", EnumLeafType.DECIDUOUS, new Color(0x79BB00), new Color(0x79BB00), EnumSaplingType.Shrub, EnumShrubLog.INSTANCE, new Color(0xFFFFFF)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenShrub.Shrub(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Gooseberry.getAllele());
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.FERTILITY, ForestryAllele.Saplings.High.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.High.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Faster.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
			species.addFruitFamily(ETFruitFamily.BERRY);
		}
	},
	GoldenRaspberry("rubus", "occidentalis", EnumLeafType.DECIDUOUS, new Color(0x83B96E), new Color(0x83B96E), EnumSaplingType.Shrub, EnumShrubLog.INSTANCE, new Color(0xFFFFFF)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenShrub.Shrub(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.GoldenRaspberry.getAllele());
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
			species.addFruitFamily(ETFruitFamily.BERRY);
			species.setRarity(0.0025F);
			species.setGrowthProvider(new ClimateGrowthProvider(EnumTemperature.NORMAL, EnumTolerance.BOTH_1, EnumHumidity.NORMAL, EnumTolerance.NONE));
		}
	},
	Cinnamon("cinnamomum", "cassia", EnumLeafType.JUNGLE, new Color(0x738E0B), new Color(0x738E0B), EnumSaplingType.Default, EnumETLog.Cinnamon, new Color(0x86583C)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenLazy.Tree(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
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
	Coconut("cocous", "nucifera", EnumLeafType.PALM, new Color(0x649923), new Color(0x649923), EnumSaplingType.Default, EnumVanillaWoodType.JUNGLE, new Color(0x53411A)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenPalm.Coconut(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Coconut.getAllele());
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
	Cashew("anacardium", "occidentale", EnumLeafType.JUNGLE, new Color(0xABB962), new Color(0xABB962), EnumSaplingType.Default, EnumVanillaWoodType.JUNGLE, new Color(0x53411A)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenLazy.Tree(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Cashew.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Low.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
		}
	},
	Avacado("persea", "americana", EnumLeafType.JUNGLE, new Color(0x96A375), new Color(0x96A375), EnumSaplingType.Default, EnumVanillaWoodType.JUNGLE, new Color(0x53411A)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenLazy.Tree(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Avacado.getAllele());
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Smallest.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Average.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
			species.addFruitFamily(ETFruitFamily.CITRUS);
		}
	},
	Nutmeg("myristica", "fragrans", EnumLeafType.JUNGLE, new Color(0x488D4C), new Color(0x488D4C), EnumSaplingType.Default, EnumVanillaWoodType.JUNGLE, new Color(0x53411A)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenLazy.Tree(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Nutmeg.getAllele());
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
	Allspice("pimenta", "dioica", EnumLeafType.JUNGLE, new Color(0x7C9724), new Color(0x7C9724), EnumSaplingType.Default, EnumVanillaWoodType.JUNGLE, new Color(0x53411A)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenLazy.Tree(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Allspice.getAllele());
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
	Chilli("capsicum", "annuum", EnumLeafType.JUNGLE, new Color(0x2A9F01), new Color(0x2A9F01), EnumSaplingType.Default, EnumVanillaWoodType.JUNGLE, new Color(0x53411A)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenLazy.Tree(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Chilli.getAllele());
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
	StarAnise("illicium", "verum", EnumLeafType.JUNGLE, new Color(0x7FC409), new Color(0x7FC409), EnumSaplingType.Default, EnumVanillaWoodType.JUNGLE, new Color(0x53411A)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenLazy.Tree(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.StarAnise.getAllele());
			template.set(EnumTreeChromosome.HEIGHT, ForestryAllele.TreeHeight.Average.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.High.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts"));
		}
	},
	Mango("mangifera", "indica", EnumLeafType.JUNGLE, new Color(0x87B574), new Color(0x87B574), EnumSaplingType.Default, EnumVanillaWoodType.JUNGLE, new Color(0x53411A)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenTropical.Mango(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Mango.getAllele());
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
			species.addFruitFamily(ETFruitFamily.CITRUS);
		}
	},
	Starfruit("averrhoa", "carambola", EnumLeafType.JUNGLE, new Color(0x6DA92D), new Color(0x6DA92D), EnumSaplingType.Default, EnumVanillaWoodType.JUNGLE, new Color(0x53411A)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenLazy.Tree(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Starfruit.getAllele());
			template.set(EnumTreeChromosome.YIELD, ForestryAllele.Yield.Average.getAllele());
			template.set(EnumTreeChromosome.MATURATION, ForestryAllele.Maturation.Fast.getAllele());
		}

		@Override
		protected void setSpeciesProperties(IAlleleTreeSpeciesBuilder species) {
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes"));
			species.addFruitFamily(AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle"));
			species.addFruitFamily(ETFruitFamily.CITRUS);
		}
	},
	Candlenut("aleurites", "moluccana", EnumLeafType.DECIDUOUS, new Color(0x8AA36C), new Color(0x8AA36C), EnumSaplingType.Default, EnumVanillaWoodType.JUNGLE, new Color(0x53411A)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenLazy.Tree(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Candlenut.getAllele());
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
	DwarfHazel("Corylus", "americana", EnumLeafType.DECIDUOUS, new Color(0x9BB552), new Color(0x9BE152), EnumSaplingType.Shrub, EnumETLog.Hazel, new Color(0xAA986F)) {
		@Override
		public WorldGenerator getWorldGenerator(ITreeGenData tree) {
			return new WorldGenShrub.Shrub(tree);
		}

		@Override
		protected void setAlleles(AlleleTemplate template) {
			template.set(EnumTreeChromosome.FRUITS, AlleleETFruitDefinition.Hazelnut.getAllele());
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

	private static final String unlocalizedName = "extratrees.species.%s.name";
	private static final String unlocalizedDesc = "extratrees.species.%s.desc";
	public static final ETTreeDefinition[] VALUES = values();
	private final String branchName;
	private final String binomial;
	private final Color leafColor;
	private final Color leafPollinatedColor;
	private final Color woodColor;
	private final EnumLeafType leafType;
	private final ILeafSpriteProvider leafSpriteProvider;
	private final EnumSaplingType saplingType;
	private final IWoodType woodType;
	private final IWoodProvider woodProvider;
	private IClassification branch;
	private IAlleleTreeSpecies species;
	private ITreeGenome genome;
	private AlleleTemplate template;

	ETTreeDefinition(String branch, String binomial, EnumLeafType leafType, Color leafColor, Color leafPollinatedColor, EnumSaplingType saplingType, IWoodType woodType, Color woodColor) {
		this.binomial = binomial;
		this.leafType = leafType;
		this.leafColor = leafColor;
		this.leafPollinatedColor = leafPollinatedColor;
		this.leafSpriteProvider = TreeManager.treeFactory.getLeafIconProvider(leafType, leafColor, leafPollinatedColor);
		this.saplingType = saplingType;
		this.woodType = woodType;
		this.woodProvider = new WoodProvider(woodType);
		this.woodColor = woodColor;
		this.branchName = branch;
	}

	public static void initTrees() {
		for (ETTreeDefinition tree : values()) {
			tree.init();
		}
		for (ETTreeDefinition tree : values()) {
			tree.registerMutations();
		}
	}

	public static void preInitTrees() {
		for (ETTreeDefinition tree : values()) {
			tree.preInit();
		}
	}

	public static ETTreeDefinition byMetadata(int meta) {
		if (meta < 0 || meta >= VALUES.length) {
			meta = 0;
		}
		return VALUES[meta];
	}

	public void preInit() {
		final String scientific = StringUtils.capitalize(branchName);
		final String uid = "trees." + branchName;
		branch = AlleleManager.alleleRegistry.getClassification("genus." + uid);
		if (branch == null) {
			branch = AlleleManager.alleleRegistry.createAndRegisterClassification(IClassification.EnumClassLevel.GENUS, uid, scientific);
		}
		IAlleleTreeSpeciesBuilder speciesBuilder = TreeManager.treeFactory.createSpecies(getUID(), String.format(unlocalizedName, getUID()), getAuthority(), String.format(unlocalizedDesc, getUID()), isDominant(),
			branch, getBinomial(), Constants.EXTRA_TREES_MOD_ID, leafSpriteProvider, saplingType.getGermlingModelProvider(leafColor, woodColor), woodProvider, this, new ETLeafProvider()
		).setRarity(0.005F);
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

	@Override
	public final ITreeGenome getGenome() {
		return genome;
	}

	@Override
	public final ITree getIndividual() {
		return new Tree(genome);
	}

	@Override
	public final ItemStack getMemberStack(EnumGermlingType treeType) {
		ITree tree = getIndividual();
		return TreeManager.treeRoot.getMemberStack(tree, treeType);
	}

	@Override
	public final IAllele[] getTemplate() {
		IAllele[] alleles = template.getAlleles();
		return Arrays.copyOf(alleles, alleles.length);
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

	public int getMetadata() {
		return ordinal();
	}

	@Override
	public String getName() {
		return name().toLowerCase(Locale.ENGLISH);
	}

	@Override
	public boolean setLogBlock(ITreeGenome genome, World world, BlockPos pos, EnumFacing facing) {
		AlleleBoolean fireproofAllele = (AlleleBoolean) genome.getActiveAllele(EnumTreeChromosome.FIREPROOF);
		boolean fireproof = fireproofAllele.getValue();
		IBlockState logBlock = TreeManager.woodAccess.getBlock(woodType, WoodBlockKind.LOG, fireproof);

		BlockLog.EnumAxis axis = BlockLog.EnumAxis.fromFacingAxis(facing.getAxis());
		if (logBlock.getBlock() instanceof BlockLog) {
			logBlock = logBlock.withProperty(BlockLog.LOG_AXIS, axis);
		}
		return world.setBlockState(pos, logBlock);
	}

	@Override
	public boolean setLeaves(ITreeGenome genome, World world, @Nullable GameProfile owner, BlockPos pos) {
		boolean placed = world.setBlockState(pos, ModuleArboriculture.getBlocks().leaves.getDefaultState());
		if (!placed) {
			return false;
		}

		Block block = world.getBlockState(pos).getBlock();
		if (ModuleArboriculture.getBlocks().leaves != block) {
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

	private static class AlleleTemplate {
		private final IAllele[] alleles;

		protected AlleleTemplate() {
			alleles = TreeManager.treeRoot.getDefaultTemplate();
			alleles[EnumTreeChromosome.FRUITS.ordinal()] = AlleleManager.alleleRegistry.getAllele("forestry.fruitNone");
		}

		protected AlleleTemplate(IAllele[] alleles) {
			this.alleles = alleles;
		}

		public <T extends Enum<T> & IChromosomeType> void set(T chromosomeType, IAllele allele) {
			Preconditions.checkNotNull(allele, "Allele must not be null");
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
