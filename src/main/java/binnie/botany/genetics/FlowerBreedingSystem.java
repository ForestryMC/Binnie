package binnie.botany.genetics;

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

import binnie.Binnie;
import binnie.botany.api.EnumFlowerChromosome;
import binnie.botany.api.EnumFlowerStage;
import binnie.botany.api.IBotanistTracker;
import binnie.botany.core.BotanyCore;
import binnie.core.genetics.BreedingSystem;
import binnie.core.genetics.ForestryAllele;
import binnie.core.genetics.Tolerance;
import binnie.core.util.I18N;

public class FlowerBreedingSystem extends BreedingSystem {
	public FlowerBreedingSystem() {
		iconUndiscovered = Binnie.RESOURCE.getUndiscoveredBeeSprite();
		iconDiscovered = Binnie.RESOURCE.getDiscoveredBeeSprite();
	}

	@Override
	public float getChance(IMutation mutation, EntityPlayer player, IAlleleSpecies firstSpecies, IAlleleSpecies secondSpecies) {
		return mutation.getBaseChance();
	}

	@Override
	public ISpeciesRoot getSpeciesRoot() {
		return Binnie.GENETICS.getFlowerRoot();
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
				for (ForestryAllele.Fertility a : ForestryAllele.Fertility.values()) {
					alleles.add(a.getAllele());
				}
				break;

			case LIFESPAN:
				for (ForestryAllele.Lifespan a2 : ForestryAllele.Lifespan.values()) {
					alleles.add(a2.getAllele());
				}
				break;

			case HUMIDITY_TOLERANCE:
			case PH_TOLERANCE:
			case TEMPERATURE_TOLERANCE:
				for (Tolerance a3 : Tolerance.values()) {
					alleles.add(a3.getAllele());
				}
				break;

			case PRIMARY:
			case SECONDARY:
			case STEM:
				for (EnumFlowerColor a4 : EnumFlowerColor.values()) {
					alleles.add(a4.getFlowerColorAllele());
				}
				break;

			case SAPPINESS:
				for (ForestryAllele.Sappiness a5 : ForestryAllele.Sappiness.values()) {
					alleles.add(a5.getAllele());
				}
				break;

			case TERRITORY:
				for (ForestryAllele.Territory a6 : ForestryAllele.Territory.values()) {
					alleles.add(a6.getAllele());
				}
				break;
		}
	}
}
