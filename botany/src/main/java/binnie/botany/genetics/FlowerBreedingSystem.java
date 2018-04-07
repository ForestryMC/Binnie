package binnie.botany.genetics;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IBreedingTracker;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IMutation;
import forestry.api.genetics.ISpeciesRoot;
import forestry.api.genetics.ISpeciesType;

import binnie.botany.api.BotanyAPI;
import binnie.botany.api.genetics.EnumFlowerChromosome;
import binnie.botany.api.genetics.EnumFlowerColor;
import binnie.botany.api.genetics.EnumFlowerStage;
import binnie.botany.api.genetics.IBotanistTracker;
import binnie.botany.core.BotanyCore;
import binnie.core.Binnie;
import binnie.core.api.genetics.IFieldKitPlugin;
import binnie.core.api.gui.IPoint;
import binnie.core.api.gui.ITexture;
import binnie.core.genetics.BreedingSystem;
import binnie.core.genetics.ForestryAllele;
import binnie.core.genetics.Tolerance;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.resource.textures.StandardTexture;
import binnie.core.texture.BinnieCoreTexture;
import binnie.core.util.I18N;

public class FlowerBreedingSystem extends BreedingSystem {
	public FlowerBreedingSystem() {
		iconUndiscovered = Binnie.RESOURCE.getUndiscoveredBeeSprite();
		iconDiscovered = Binnie.RESOURCE.getDiscoveredBeeSprite();
	}

	@Override
	public float getChance(final IMutation mutation, final EntityPlayer player, final IAlleleSpecies firstSpecies, final IAlleleSpecies secondSpecies) {
		return mutation.getBaseChance();
	}

	@Override
	public ISpeciesRoot getSpeciesRoot() {
		return BotanyAPI.flowerRoot;
	}

	@Override
	public int getColour() {
		return 14563127;
	}

	@Override
	public Class<? extends IBreedingTracker> getTrackerClass() {
		return IBotanistTracker.class;
	}

	@Override
	public String getAlleleName(IChromosomeType chromosome, IAllele allele) {
		if (chromosome == EnumFlowerChromosome.FERTILITY) {
			if (allele.getUID().contains("Low")) {
				return I18N.localise("binniecore.allele.fertility.low");
			}
			if (allele.getUID().contains("Normal")) {
				return I18N.localise("binniecore.allele.fertility.normal");
			}
			if (allele.getUID().contains("High")) {
				return I18N.localise("binniecore.allele.fertility.high");
			}
			if (allele.getUID().contains("Maximum")) {
				return I18N.localise("binniecore.allele.fertility.maximum");
			}
		}
		return super.getAlleleName(chromosome, allele);
	}

	@Override
	public boolean isDNAManipulable(ItemStack member) {
		ISpeciesType type = getSpeciesRoot().getType(member);
		return type != null && isDNAManipulable(type);
	}

	@Override
	public boolean isDNAManipulable(ISpeciesType type) {
		return type == EnumFlowerStage.POLLEN;
	}

	@Override
	public IIndividual getConversion(ItemStack stack) {
		return BotanyCore.getFlowerRoot().getConversion(stack);
	}

	@Override
	public ISpeciesType[] getActiveTypes() {
		return new ISpeciesType[]{EnumFlowerStage.FLOWER, EnumFlowerStage.POLLEN, EnumFlowerStage.SEED};
	}

	@Override
	public void addExtraAlleles(IChromosomeType chromosome, TreeSet<IAllele> alleles) {
		switch ((EnumFlowerChromosome) chromosome) {
			case FERTILITY:
				for (ForestryAllele.Fertility fertility : ForestryAllele.Fertility.values()) {
					alleles.add(fertility.getAllele());
				}
				break;

			case LIFESPAN:
				for (ForestryAllele.Lifespan lifespan : ForestryAllele.Lifespan.values()) {
					alleles.add(lifespan.getAllele());
				}
				break;

			case HUMIDITY_TOLERANCE:
			case PH_TOLERANCE:
			case TEMPERATURE_TOLERANCE:
				for (Tolerance tolerance : Tolerance.values()) {
					alleles.add(tolerance.getAllele());
				}
				break;

			case PRIMARY:
			case SECONDARY:
			case STEM:
				for (EnumFlowerColor color : EnumFlowerColor.values()) {
					alleles.add(color.getFlowerColorAllele());
				}
				break;

			case SAPPINESS:
				for (ForestryAllele.Sappiness sappiness : ForestryAllele.Sappiness.values()) {
					alleles.add(sappiness.getAllele());
				}
				break;

			case TERRITORY:
				for (ForestryAllele.Territory territory : ForestryAllele.Territory.values()) {
					alleles.add(territory.getAllele());
				}
				break;
		}
	}

	@Override
	public IFieldKitPlugin getFieldKitPlugin() {
		return new FieldKitPlugin();
	}

	private static class FieldKitPlugin implements IFieldKitPlugin {
		@Override
		public Map<IChromosomeType, IPoint> getChromosomePickerPositions() {
			Map<IChromosomeType, IPoint> positions = new HashMap<>();
			positions.put(EnumFlowerChromosome.SPECIES, new Point(35, 81));
			positions.put(EnumFlowerChromosome.PRIMARY, new Point(36, 28));
			positions.put(EnumFlowerChromosome.SECONDARY, new Point(27, 13));
			positions.put(EnumFlowerChromosome.FERTILITY, new Point(47, 15));
			positions.put(EnumFlowerChromosome.TERRITORY, new Point(54, 31));
			positions.put(EnumFlowerChromosome.EFFECT, new Point(15, 55));
			positions.put(EnumFlowerChromosome.LIFESPAN, new Point(23, 38));
			positions.put(EnumFlowerChromosome.TEMPERATURE_TOLERANCE, new Point(17, 77));
			positions.put(EnumFlowerChromosome.HUMIDITY_TOLERANCE, new Point(52, 51));
			positions.put(EnumFlowerChromosome.PH_TOLERANCE, new Point(54, 80));
			positions.put(EnumFlowerChromosome.SAPPINESS, new Point(41, 42));
			positions.put(EnumFlowerChromosome.STEM, new Point(37, 63));
			return positions;
		}

		@Override
		public ITexture getTypeTexture() {
			return new StandardTexture(0, 96, 96, 96, BinnieCoreTexture.GUI_BREEDING);
		}
	}
}
