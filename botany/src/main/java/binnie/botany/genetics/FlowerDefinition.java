package binnie.botany.genetics;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import binnie.botany.api.genetics.EnumFlowerColor;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import net.minecraftforge.common.MinecraftForge;

import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.core.EnumTemperature;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.AlleleSpeciesRegisterEvent;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleRegistry;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IClassification;
import forestry.core.genetics.alleles.AlleleHelper;
import forestry.core.genetics.alleles.EnumAllele;

import binnie.core.Constants;
import binnie.botany.api.BotanyAPI;
import binnie.botany.api.gardening.EnumAcidity;
import binnie.botany.api.gardening.EnumMoisture;
import binnie.botany.api.genetics.EnumFlowerChromosome;
import binnie.botany.api.genetics.EnumFlowerStage;
import binnie.botany.api.genetics.IAlleleFlowerSpecies;
import binnie.botany.api.genetics.IAlleleFlowerSpeciesBuilder;
import binnie.botany.api.genetics.IFlower;
import binnie.botany.api.genetics.IFlowerDefinition;
import binnie.botany.api.genetics.IFlowerGenome;
import binnie.botany.api.genetics.IFlowerMutationBuilder;
import binnie.botany.api.genetics.IFlowerRoot;
import binnie.botany.api.genetics.IFlowerType;
import binnie.botany.core.BotanyCore;

public enum FlowerDefinition implements IFlowerDefinition {
	Dandelion("Dandelion", "taraxacum", "officinale", EnumFlowerType.DANDELION, EnumFlowerColor.Yellow) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.HIGH);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.SHORTENED);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.LOWER);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
		}

		@Override
		protected void registerMutations() {
			// vanilla
		}
	},
	Poppy("Poppy", "papaver", "rhoeas", EnumFlowerType.POPPY, EnumFlowerColor.Red) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
			flowerSpecies.setTemperature(EnumTemperature.WARM);
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.HIGH);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.SHORTER);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.BOTH_2);
		}

		@Override
		protected void registerMutations() {
			// vanilla
		}
	},
	Orchid("Orchid", "vanda", "coerulea", EnumFlowerType.ORCHID, EnumFlowerColor.DeepSkyBlue) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
			flowerSpecies.setPH(EnumAcidity.ACID);
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.LONG);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.LOW);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
		}

		@Override
		protected void registerMutations() {
			// vanilla
		}
	},
	Allium("Allium", "allium", "giganteum", EnumFlowerType.ALLIUM, EnumFlowerColor.MediumPurple) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
			flowerSpecies.setPH(EnumAcidity.ALKALINE);
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.LOW);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
		}

		@Override
		protected void registerMutations() {
			// vanilla
		}
	},
	Bluet("Bluet", "houstonia", "caerulea", EnumFlowerType.BLUET, EnumFlowerColor.Lavender, EnumFlowerColor.Khaki) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
			flowerSpecies.setMoisture(EnumMoisture.DAMP);
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.LOW);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.LOWER);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.OliveDrab.getFlowerColorAllele());
		}

		@Override
		protected void registerMutations() {
			// vanilla
		}
	},
	Tulip("Tulip", "tulipa", "agenensis", EnumFlowerType.TULIP, EnumFlowerColor.Violet) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.LONG);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.OliveDrab.getFlowerColorAllele());
		}

		@Override
		protected void registerMutations() {
			// vanilla
		}
	},
	Daisy("Daisy", "leucanthemum", "vulgare", EnumFlowerType.DAISY, EnumFlowerColor.White, EnumFlowerColor.Yellow) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
			flowerSpecies.setTemperature(EnumTemperature.WARM);
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.LOW);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.BOTH_2);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.OliveDrab.getFlowerColorAllele());
		}

		@Override
		protected void registerMutations() {
			// vanilla
		}
	},
	Cornflower("Cornflower", "centaurea", "cyanus", EnumFlowerType.CORNFLOWER, EnumFlowerColor.SkyBlue) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.HIGH);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.SHORTER);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.LOW);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.OliveDrab.getFlowerColorAllele());
		}

		@Override
		protected void registerMutations() {
			registerMutation(Dandelion, Tulip, 10);
		}
	},
	Pansy("Pansy", "viola", "tricolor", EnumFlowerType.PANSY, EnumFlowerColor.Pink, EnumFlowerColor.Purple) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
			flowerSpecies.setPH(EnumAcidity.ACID);
			flowerSpecies.setTemperature(EnumTemperature.WARM);
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.SHORTENED);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.SeaGreen.getFlowerColorAllele());
		}

		@Override
		protected void registerMutations() {
			registerMutation(Tulip, Viola, 5);
		}
	},
	Iris("Iris", "iris", "germanica", EnumFlowerType.IRIS, EnumFlowerColor.LightGray, EnumFlowerColor.Purple) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
			flowerSpecies.setPH(EnumAcidity.ACID);
			flowerSpecies.setTemperature(EnumTemperature.WARM);
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.HIGH);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.SeaGreen.getFlowerColorAllele());
		}

		@Override
		protected void registerMutations() {
			registerMutation(Orchid, Viola, 10);
		}
	},
	Lavender("Lavender", "Lavandula", "angustifolia", EnumFlowerType.LAVENDER, EnumFlowerColor.MediumOrchid) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
			flowerSpecies.setTemperature(EnumTemperature.WARM);
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.HIGH);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.LOW);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.UP_1);
		}

		@Override
		protected void registerMutations() {
			registerMutation(Allium, Viola, 10);
		}
	},
	Viola("Viola", "viola", "odorata", EnumFlowerType.VIOLA, EnumFlowerColor.MediumPurple, EnumFlowerColor.SlateBlue) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
			flowerSpecies.setPH(EnumAcidity.ACID);
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.SHORTENED);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.OliveDrab.getFlowerColorAllele());
		}

		@Override
		protected void registerMutations() {
			registerMutation(Orchid, Poppy, 15);
		}
	},
	Daffodil("Daffodil", "narcissus", "pseudonarcissus", EnumFlowerType.DAFFODIL, EnumFlowerColor.Yellow, EnumFlowerColor.Gold) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.ELONGATED);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.HIGH);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
		}

		@Override
		protected void registerMutations() {
			registerMutation(Dandelion, Poppy, 10);
		}
	},
	Dahlia("Dahlia", "dahlia", "variabilis", EnumFlowerType.DAHLIA, EnumFlowerColor.HotPink, EnumFlowerColor.DeepPink) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.UP_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.BOTH_2);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.OliveDrab.getFlowerColorAllele());
		}

		@Override
		protected void registerMutations() {
			registerMutation(Daisy, Allium, 15);
		}
	},
	Peony("Peony", "paeonia", "suffruticosa", EnumFlowerType.PEONY, EnumFlowerColor.Thistle) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
			flowerSpecies.setPH(EnumAcidity.ALKALINE);
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.LOW);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.LONG);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.UP_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.DarkGreen.getFlowerColorAllele());
		}

		@Override
		protected void registerMutations() {
			// vanilla
		}
	},
	Rose("ROSE", "rosa", "rubiginosa", EnumFlowerType.ROSE, EnumFlowerColor.Red) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
			flowerSpecies.setPH(EnumAcidity.ACID);
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.LONGER);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.LOW);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.HIGH);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.UP_1);
		}

		@Override
		protected void registerMutations() {
			// vanilla
		}
	},
	Lilac("Lilac", "syringa", "vulgaris", EnumFlowerType.LILAC, EnumFlowerColor.Plum) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
			flowerSpecies.setPH(EnumAcidity.ALKALINE);
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.LOW);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.LONGER);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.OliveDrab.getFlowerColorAllele());
		}

		@Override
		protected void registerMutations() {
		}
	},
	Hydrangea("Hydrangea", "hydrangea", "macrophylla", EnumFlowerType.HYDRANGEA, EnumFlowerColor.DeepSkyBlue) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
			flowerSpecies.setMoisture(EnumMoisture.DAMP);
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.LONGER);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.LOW);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.HIGH);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.DarkGreen.getFlowerColorAllele());
		}

		@Override
		protected void registerMutations() {
			registerMutation(Peony, Bluet, 10);
		}
	},
	Foxglove("Foxglove", "digitalis", "purpurea", EnumFlowerType.FOXGLOVE, EnumFlowerColor.HotPink) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.LOW);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.LOW);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.SHORTENED);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.UP_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.DarkGreen.getFlowerColorAllele());
		}

		@Override
		protected void registerMutations() {
			registerMutation(Lilac, Zinnia, 5);
			// vanilla
		}
	},
	Zinnia("Zinnia", "zinnia", "elegans", EnumFlowerType.ZINNIA, EnumFlowerColor.MediumVioletRed, EnumFlowerColor.Yellow) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.SHORTER);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.BOTH_2);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.MediumSeaGreen.getFlowerColorAllele());
		}

		@Override
		protected void registerMutations() {
			Zinnia.registerMutation(Dahlia, Marigold, 5);
		}
	},
	Chrysanthemum("Chrysanthemum", "chrysanthemum", "\u00ef?? grandiflorum", EnumFlowerType.MUMS, EnumFlowerColor.Violet) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.HIGH);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.LONG);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.HIGH);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.MediumSeaGreen.getFlowerColorAllele());
		}

		@Override
		protected void registerMutations() {
			registerMutation(Geranium, Rose, 10);
		}
	},
	Marigold("Marigold", "calendula", "officinalis", EnumFlowerType.MARIGOLD, EnumFlowerColor.Gold, EnumFlowerColor.DarkOrange) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
			flowerSpecies.setTemperature(EnumTemperature.WARM);
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.HIGH);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.SHORTER);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.BOTH_2);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.UP_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
		}

		@Override
		protected void registerMutations() {
			registerMutation(Daisy, Dandelion, 10);
		}
	},
	Geranium("Geranium", "geranium", "maderense", EnumFlowerType.GERANIUM, EnumFlowerColor.DeepPink) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
			flowerSpecies.setTemperature(EnumTemperature.WARM);
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.LOW);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.LONG);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.LOW);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.MediumSeaGreen.getFlowerColorAllele());
		}

		@Override
		protected void registerMutations() {
			registerMutation(Tulip, Orchid, 15);
		}
	},
	Azalea("Azalea", "rhododendrons", "aurigeranum", EnumFlowerType.AZALEA, EnumFlowerColor.HotPink) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
			flowerSpecies.setPH(EnumAcidity.ACID);
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
		}

		@Override
		protected void registerMutations() {
			registerMutation(Orchid, Geranium, 5);
		}
	},
	Primrose("Primrose", "primula", "vulgaris", EnumFlowerType.PRIMROSE, EnumFlowerColor.Red, EnumFlowerColor.Gold) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
			flowerSpecies.setPH(EnumAcidity.ACID);
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.LONG);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.UP_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.UP_1);
		}

		@Override
		protected void registerMutations() {
			registerMutation(Chrysanthemum, Auricula, 5);
		}
	},
	Aster("Aster", "aster", "amellus", EnumFlowerType.ASTER, EnumFlowerColor.MediumPurple, EnumFlowerColor.Goldenrod) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.HIGHER);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
		}

		@Override
		protected void registerMutations() {
			registerMutation(Daisy, Tulip, 10);
		}
	},
	Carnation("Carnation", "dianthus", "caryophyllus", EnumFlowerType.CARNATION, EnumFlowerColor.Crimson, EnumFlowerColor.White) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
			flowerSpecies.setPH(EnumAcidity.ALKALINE);
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.HIGH);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.SeaGreen.getFlowerColorAllele());
		}

		@Override
		protected void registerMutations() {
			registerMutation(Dianthus, Rose, 5);
		}
	},
	Lily("Lily", "lilium", "auratum", EnumFlowerType.LILY, EnumFlowerColor.Pink, EnumFlowerColor.Gold) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
			flowerSpecies.setTemperature(EnumTemperature.WARM);
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.LOW);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.LONG);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.LOW);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
		}

		@Override
		protected void registerMutations() {
			registerMutation(Tulip, Chrysanthemum, 5);
		}
	},
	Yarrow("Yarrow", "achillea", "millefolium", EnumFlowerType.YARROW, EnumFlowerColor.Yellow) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
			flowerSpecies.setPH(EnumAcidity.ACID);
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.HIGH);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.LOW);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.UP_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.DarkOliveGreen.getFlowerColorAllele());
		}

		@Override
		protected void registerMutations() {
			registerMutation(Dandelion, Orchid, 10);
		}
	},
	Petunia("Petunia", "petunia", "\u00ef?? atkinsiana", EnumFlowerType.Petunia, EnumFlowerColor.MediumVioletRed, EnumFlowerColor.Thistle) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
			flowerSpecies.setTemperature(EnumTemperature.WARM);
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.SHORTER);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.UP_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.UP_1);
		}

		@Override
		protected void registerMutations() {
			registerMutation(Tulip, Dahlia, 5);
		}
	},
	Agapanthus("Agapanthus", "agapanthus", "praecox", EnumFlowerType.AGAPANTHUS, EnumFlowerColor.DeepSkyBlue) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
			flowerSpecies.setTemperature(EnumTemperature.WARM);
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.LOW);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.DarkOliveGreen.getFlowerColorAllele());
		}

		@Override
		protected void registerMutations() {
			registerMutation(Allium, Geranium, 5);
		}
	},
	Fuchsia("Fuchsia", "fuchsia", "magellanica", EnumFlowerType.FUCHSIA, EnumFlowerColor.DeepPink, EnumFlowerColor.MediumOrchid) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
			flowerSpecies.setTemperature(EnumTemperature.WARM);
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.SHORTENED);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.UP_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.SeaGreen.getFlowerColorAllele());
		}

		@Override
		protected void registerMutations() {
			registerMutation(Foxglove, Dahlia, 5);
		}
	},
	Dianthus("Dianthus", "dianthus", "barbatus", EnumFlowerType.DIANTHUS, EnumFlowerColor.Crimson, EnumFlowerColor.HotPink) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
			flowerSpecies.setPH(EnumAcidity.ALKALINE);
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.SHORT);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.HIGH);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.BOTH_2);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.OliveDrab.getFlowerColorAllele());
		}

		@Override
		protected void registerMutations() {
			registerMutation(Tulip, Poppy, 15);
		}
	},
	Forget("Forget-me-nots", "myosotis", "arvensis", EnumFlowerType.FORGET, EnumFlowerColor.LightSteelBlue) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
			flowerSpecies.setPH(EnumAcidity.ACID);
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.LOWER);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.SHORT);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.UP_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.UP_1);
		}

		@Override
		protected void registerMutations() {
			registerMutation(Orchid, Bluet, 10);
		}
	},
	Anemone("Anemone", "anemone", "coronaria", EnumFlowerType.ANEMONE, EnumFlowerColor.Red, EnumFlowerColor.MistyRose) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.HIGH);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.LOW);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.UP_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.DarkOliveGreen.getFlowerColorAllele());
		}

		@Override
		protected void registerMutations() {
			FlowerDefinition.Anemone.registerMutation(FlowerDefinition.Aquilegia, FlowerDefinition.Rose, 5);
		}
	},
	Aquilegia("Aquilegia", "aquilegia", "vulgaris", EnumFlowerType.AQUILEGIA, EnumFlowerColor.SlateBlue, EnumFlowerColor.Thistle) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.UP_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.MediumSeaGreen.getFlowerColorAllele());
		}

		@Override
		protected void registerMutations() {
			registerMutation(Iris, Poppy, 5);
		}
	},
	Edelweiss("Edelweiss", "leontopodium", "alpinum", EnumFlowerType.EDELWEISS, EnumFlowerColor.White, EnumFlowerColor.Khaki) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
			flowerSpecies.setPH(EnumAcidity.ALKALINE);
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.LOWEST);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.LOW);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.DarkOliveGreen.getFlowerColorAllele());
		}

		@Override
		protected void registerMutations() {
			FlowerDefinition.Edelweiss.registerMutation(FlowerDefinition.Peony, FlowerDefinition.Bluet, 5);
		}
	},
	Scabious("Scabious", "scabiosa", "columbaria", EnumFlowerType.SCABIOUS, EnumFlowerColor.RoyalBlue) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.SHORTENED);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.LOW);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.UP_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.OliveDrab.getFlowerColorAllele());
		}

		@Override
		protected void registerMutations() {
			registerMutation(Allium, Cornflower, 5);
		}
	},
	Coneflower("Coneflower", "echinacea", "purpurea", EnumFlowerType.CONEFLOWER, EnumFlowerColor.Violet, EnumFlowerColor.DarkOrange) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.HIGHER);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.DarkOliveGreen.getFlowerColorAllele());
		}

		@Override
		protected void registerMutations() {
			registerMutation(Tulip, Cornflower, 5);
		}
	},
	Gaillardia("Gaillardia", "gaillardia", "aristata", EnumFlowerType.GAILLARDIA, EnumFlowerColor.DarkOrange, EnumFlowerColor.Yellow) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
			flowerSpecies.setMoisture(EnumMoisture.DAMP);
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.HIGH);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.LONG);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.HIGHER);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.BOTH_2);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.OliveDrab.getFlowerColorAllele());
		}

		@Override
		protected void registerMutations() {
			registerMutation(Dandelion, Marigold, 5);
		}
	},
	Auricula("Auricula", "primula", "auricula", EnumFlowerType.AURICULA, EnumFlowerColor.Red, EnumFlowerColor.Yellow) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
			flowerSpecies.setPH(EnumAcidity.ACID);
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.ELONGATED);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.HIGH);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.UP_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.UP_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.DarkOliveGreen.getFlowerColorAllele());
		}

		@Override
		protected void registerMutations() {
			registerMutation(Poppy, Geranium, 10);
		}
	},
	Camellia("Camellia", "camellia", "japonica", EnumFlowerType.CAMELLIA, EnumFlowerColor.Crimson) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
			flowerSpecies.setPH(EnumAcidity.ACID);
			flowerSpecies.setMoisture(EnumMoisture.DAMP);
			flowerSpecies.setTemperature(EnumTemperature.WARM);
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.HIGH);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.UP_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.DarkOliveGreen.getFlowerColorAllele());
		}

		@Override
		protected void registerMutations() {
			registerMutation(Hydrangea, Rose, 5);
		}
	},
	Goldenrod("Goldenrod", "solidago", "canadensis", EnumFlowerType.GOLDENROD, EnumFlowerColor.Gold) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.HIGH);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.HIGHER);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.MediumSeaGreen.getFlowerColorAllele());
		}

		@Override
		protected void registerMutations() {
			registerMutation(Lilac, Marigold, 10);
		}
	},
	Althea("Althea", "althaea", "officinalis", EnumFlowerType.ALTHEA, EnumFlowerColor.Thistle, EnumFlowerColor.MediumOrchid) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
			flowerSpecies.setTemperature(EnumTemperature.WARM);
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.LOW);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.ELONGATED);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.HIGH);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.DarkGreen.getFlowerColorAllele());
		}

		@Override
		protected void registerMutations() {
			registerMutation(Hydrangea, Iris, 5);
		}
	},
	Penstemon("Penstemon", "penstemon", "digitalis", EnumFlowerType.PENSTEMON, EnumFlowerColor.MediumOrchid, EnumFlowerColor.Thistle) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
			flowerSpecies.setMoisture(EnumMoisture.DRY);
			flowerSpecies.setTemperature(EnumTemperature.WARM);
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.LOW);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.LOW);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.LONG);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.UP_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.UP_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.OliveDrab.getFlowerColorAllele());
		}

		@Override
		protected void registerMutations() {
			registerMutation(Peony, Lilac, 5);
		}
	},
	Delphinium("Delphinium", "delphinium", "staphisagria", EnumFlowerType.DELPHINIUM, EnumFlowerColor.DarkSlateBlue) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
			flowerSpecies.setMoisture(EnumMoisture.DAMP);
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.LOW);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.LOW);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.LONGER);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.HUMIDITY_TOLERANCE, EnumAllele.Tolerance.DOWN_1);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.STEM, EnumFlowerColor.DarkSeaGreen.getFlowerColorAllele());
		}

		@Override
		protected void registerMutations() {
			registerMutation(Lilac, Bluet, 5);
		}
	},
	Hollyhock("Hollyhock", "Alcea", "rosea", EnumFlowerType.HOLLYHOCK, EnumFlowerColor.Black, EnumFlowerColor.Gold) {
		@Override
		protected void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies) {
		}

		@Override
		protected void setAlleles(IAllele[] alleles) {
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.SAPPINESS, EnumAllele.Sappiness.LOW);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.FERTILITY, EnumAllele.Fertility.LOW);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.LIFESPAN, EnumAllele.Lifespan.LONG);
			AlleleHelper.getInstance().set(alleles, EnumFlowerChromosome.PH_TOLERANCE, EnumAllele.Tolerance.UP_1);
		}

		@Override
		protected void registerMutations() {
			registerMutation(Delphinium, Lavender, 5);
		}
	};

	public static FlowerDefinition[] VALUES = values();
	private final IAlleleFlowerSpecies species;
	private final IFlowerType<EnumFlowerType> type;
	private final String name;
	private final String binomial;
	private final String branchName;
	private final List<IAllele[]> variantTemplates;
	@Nullable
	private IClassification branch;
	private final EnumFlowerColor primaryColor;
	private final EnumFlowerColor secondaryColor;
	private IAllele[] template;
	private IFlowerGenome genome;

	FlowerDefinition(String name, String branch, String binomial, IFlowerType<EnumFlowerType> type, EnumFlowerColor colour) {
		this(name, branch, binomial, type, false, colour, colour);
	}

	FlowerDefinition(String name, String branch, String binomial, IFlowerType<EnumFlowerType> type, EnumFlowerColor primaryColor, EnumFlowerColor secondaryColor) {
		this(name, branch, binomial, type, true, primaryColor, secondaryColor);
	}

	FlowerDefinition(String name, String branch, String binomial, IFlowerType<EnumFlowerType> flowerType, boolean isDominant, EnumFlowerColor primaryColor, EnumFlowerColor secondaryColor) {
		String modId = Constants.BOTANY_MOD_ID;
		String uid = modId + ".flower" + this;
		String unlocalizedDescription = "botany.description.flower" + this;
		String unlocalizedName = "botany.flowers.species." + name;

		variantTemplates = new ArrayList<>();
		this.name = name;
		this.binomial = binomial;
		branchName = branch;
		type = flowerType;
		this.primaryColor = primaryColor;
		this.secondaryColor = secondaryColor;

		IAlleleFlowerSpeciesBuilder speciesBuilder = BotanyAPI.flowerFactory.createSpecies(modId, uid, unlocalizedName, "Binnie's Mod Team", unlocalizedDescription, isDominant, getBranch(), binomial, flowerType);
		setSpeciesProperties(speciesBuilder);
		species = speciesBuilder.build();
		if (this.branch != null) {
			this.branch.addMemberSpecies(species);
		}
	}

	private static void setupVariants() {
		IFlowerRoot flowerRood = BotanyCore.getFlowerRoot();
		flowerRood.addConversion(new ItemStack(Blocks.YELLOW_FLOWER, 1, 0), Dandelion.getTemplate());
		flowerRood.addConversion(new ItemStack(Blocks.RED_FLOWER, 1, 0), Poppy.getTemplate());
		flowerRood.addConversion(new ItemStack(Blocks.RED_FLOWER, 1, 1), Orchid.getTemplate());
		flowerRood.addConversion(new ItemStack(Blocks.RED_FLOWER, 1, 2), Allium.getTemplate());
		flowerRood.addConversion(new ItemStack(Blocks.RED_FLOWER, 1, 3), Bluet.getTemplate());
		flowerRood.addConversion(new ItemStack(Blocks.RED_FLOWER, 1, 7), Tulip.getTemplate());
		flowerRood.addConversion(new ItemStack(Blocks.RED_FLOWER, 1, 8), Daisy.getTemplate());
		flowerRood.addConversion(new ItemStack(Blocks.DOUBLE_PLANT, 1, 1), Lilac.getTemplate());
		flowerRood.addConversion(new ItemStack(Blocks.DOUBLE_PLANT, 1, 4), Rose.getTemplate());
		flowerRood.addConversion(new ItemStack(Blocks.DOUBLE_PLANT, 1, 5), Peony.getTemplate());
		flowerRood.addConversion(new ItemStack(Blocks.RED_FLOWER, 1, 6), Tulip.addVariant(EnumFlowerColor.White));
		flowerRood.addConversion(new ItemStack(Blocks.RED_FLOWER, 1, 4), Tulip.addVariant(EnumFlowerColor.Crimson));
		flowerRood.addConversion(new ItemStack(Blocks.RED_FLOWER, 1, 5), Tulip.addVariant(EnumFlowerColor.DarkOrange));
	}

	public static void preInitFlowers() {
		MinecraftForge.EVENT_BUS.post(new AlleleSpeciesRegisterEvent(BotanyAPI.flowerRoot, IAlleleFlowerSpecies.class));
		for (FlowerDefinition def : values()) {
			@SuppressWarnings("unchecked")
			IFlowerType<EnumFlowerType> type = def.species.getType();
			if (EnumFlowerType.highestSection < type.getSections()) {
				EnumFlowerType.highestSection = type.getSections();
			}
		}
	}

	public static void initFlowers() {
		for (FlowerDefinition flower : values()) {
			flower.init();
		}
		setupVariants();
		for (FlowerDefinition flower : values()) {
			flower.registerMutations();
		}
	}

	private static void markAllelesAsValid(IChromosomeType existingType, IChromosomeType newType) {
		IAlleleRegistry alleleRegistry = AlleleManager.alleleRegistry;
		Collection<IAllele> alleles = alleleRegistry.getRegisteredAlleles(existingType);
		for (IAllele allele : alleles) {
			alleleRegistry.addValidAlleleTypes(allele, newType);
		}
	}

	protected abstract void setSpeciesProperties(IAlleleFlowerSpeciesBuilder flowerSpecies);

	protected abstract void setAlleles(IAllele[] alleles);

	protected abstract void registerMutations();

	private IAllele[] addVariant(EnumFlowerColor a, EnumFlowerColor b) {
		IAllele[] template = getTemplate();
		template[EnumFlowerChromosome.PRIMARY.ordinal()] = a.getFlowerColorAllele();
		template[EnumFlowerChromosome.SECONDARY.ordinal()] = b.getFlowerColorAllele();
		variantTemplates.add(template);
		return template;
	}

	private IAllele[] addVariant(EnumFlowerColor a) {
		return addVariant(a, a);
	}

	public List<IAllele[]> getVariants() {
		return variantTemplates;
	}

	public IClassification getBranch() {
		if (branch == null) {
			String scientific = branchName.substring(0, 1).toUpperCase() + branchName.substring(1).toLowerCase();
			String uid = "flowers." + branchName.toLowerCase();
			IClassification branch = AlleleManager.alleleRegistry.getClassification("genus." + uid);
			if (branch == null) {
				branch = AlleleManager.alleleRegistry.createAndRegisterClassification(IClassification.EnumClassLevel.GENUS, uid, scientific);
			}
			this.branch = branch;
		}
		return branch;
	}

	public void setBranch(IClassification branch) {
		this.branch = branch;
	}

	@Override
	public final IAllele[] getTemplate() {
		return Arrays.copyOf(template, template.length);
	}

	public IAlleleFlowerSpecies getSpecies() {
		return species;
	}

	@Override
	public IFlowerGenome getGenome() {
		return genome;
	}

	@Override
	public IFlower getIndividual() {
		return new Flower(genome, 0);
	}

	@Override
	public ItemStack getMemberStack(EnumFlowerStage flowerStage) {
		IFlower flower = getIndividual();
		return BotanyCore.getFlowerRoot().getMemberStack(flower, flowerStage);
	}

	private void init() {
		markAllelesAsValid(EnumBeeChromosome.FERTILITY, EnumFlowerChromosome.FERTILITY);
		markAllelesAsValid(EnumBeeChromosome.TERRITORY, EnumFlowerChromosome.TERRITORY);
		markAllelesAsValid(EnumBeeChromosome.LIFESPAN, EnumFlowerChromosome.LIFESPAN);
		markAllelesAsValid(EnumBeeChromosome.TEMPERATURE_TOLERANCE, EnumFlowerChromosome.TEMPERATURE_TOLERANCE);
		markAllelesAsValid(EnumBeeChromosome.HUMIDITY_TOLERANCE, EnumFlowerChromosome.HUMIDITY_TOLERANCE);
		markAllelesAsValid(EnumBeeChromosome.HUMIDITY_TOLERANCE, EnumFlowerChromosome.PH_TOLERANCE);
		markAllelesAsValid(EnumTreeChromosome.SAPPINESS, EnumFlowerChromosome.SAPPINESS);

		template = Arrays.copyOf(BotanyAPI.flowerRoot.getDefaultTemplate(), EnumFlowerChromosome.values().length);
		AlleleHelper.getInstance().set(template, EnumFlowerChromosome.SPECIES, species);
		AlleleHelper.getInstance().set(template, EnumFlowerChromosome.TEMPERATURE_TOLERANCE, EnumAllele.Tolerance.BOTH_1);
		AlleleHelper.getInstance().set(template, EnumFlowerChromosome.PRIMARY, primaryColor.getFlowerColorAllele());
		AlleleHelper.getInstance().set(template, EnumFlowerChromosome.SECONDARY, secondaryColor.getFlowerColorAllele());
		setAlleles(template);

		genome = BotanyCore.getFlowerRoot().templateAsGenome(template);

		BotanyCore.getFlowerRoot().registerTemplate(template);
		for (IAllele[] template : variantTemplates) {
			BotanyCore.getFlowerRoot().registerTemplate(template);
		}
	}

	protected final IFlowerMutationBuilder registerMutation(FlowerDefinition parent1, FlowerDefinition parent2, int chance) {
		return BotanyAPI.flowerFactory.createMutation(parent1.species, parent2.species, getTemplate(), chance);
	}
}
