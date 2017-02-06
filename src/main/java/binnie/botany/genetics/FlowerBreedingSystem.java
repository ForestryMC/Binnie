package binnie.botany.genetics;

import binnie.Binnie;
import binnie.botany.api.EnumFlowerChromosome;
import binnie.botany.api.EnumFlowerStage;
import binnie.botany.api.IBotanistTracker;
import binnie.botany.core.BotanyCore;
import binnie.core.BinnieCore;
import binnie.core.genetics.BreedingSystem;
import binnie.core.genetics.ForestryAllele;
import binnie.core.genetics.Tolerance;
import binnie.extrabees.ExtraBees;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IBreedingTracker;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IMutation;
import forestry.api.genetics.ISpeciesRoot;
import forestry.api.genetics.ISpeciesType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.TreeSet;

public class FlowerBreedingSystem extends BreedingSystem {
	public FlowerBreedingSystem() {
		this.iconUndiscovered = Binnie.RESOURCE.getItemSprite(ExtraBees.instance, "icon/undiscoveredBee");
		this.iconDiscovered = Binnie.RESOURCE.getItemSprite(ExtraBees.instance, "icon/discoveredBee");
	}

	@Override
	public float getChance(final IMutation mutation, final EntityPlayer player, final IAllele species1, final IAllele species2) {
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
	public String getAlleleName(final IChromosomeType chromosome, final IAllele allele) {
		if (chromosome == EnumFlowerChromosome.FERTILITY) {
			if (allele.getUID().contains("Low")) {
				return Binnie.LANGUAGE.localise(BinnieCore.getInstance(), "allele.fertility.low");
			}
			if (allele.getUID().contains("Normal")) {
				return Binnie.LANGUAGE.localise(BinnieCore.getInstance(), "allele.fertility.normal");
			}
			if (allele.getUID().contains("High")) {
				return Binnie.LANGUAGE.localise(BinnieCore.getInstance(), "allele.fertility.high");
			}
			if (allele.getUID().contains("Maximum")) {
				return Binnie.LANGUAGE.localise(BinnieCore.getInstance(), "allele.fertility.maximum");
			}
		}
		return super.getAlleleName(chromosome, allele);
	}

	@Override
	public boolean isDNAManipulable(final ItemStack member) {
		ISpeciesType type = this.getSpeciesRoot().getType(member);
		return isDNAManipulable(type);
	}

	@Override
	public boolean isDNAManipulable(ISpeciesType type) {
		return type == EnumFlowerStage.POLLEN;
	}

	@Override
	public IIndividual getConversion(final ItemStack stack) {
		return BotanyCore.getFlowerRoot().getConversion(stack);
	}

	@Override
	public ISpeciesType[] getActiveTypes() {
		return new ISpeciesType[]{EnumFlowerStage.FLOWER, EnumFlowerStage.POLLEN, EnumFlowerStage.SEED};
	}

	@Override
	public void addExtraAlleles(final IChromosomeType chromosome, final TreeSet<IAllele> alleles) {
		switch ((EnumFlowerChromosome) chromosome) {
			case FERTILITY: {
				for (final ForestryAllele.Fertility a : ForestryAllele.Fertility.values()) {
					alleles.add(a.getAllele());
				}
				break;
			}
			case LIFESPAN: {
				for (final ForestryAllele.Lifespan a2 : ForestryAllele.Lifespan.values()) {
					alleles.add(a2.getAllele());
				}
				break;
			}
			case HUMIDITY_TOLERANCE:
			case PH_TOLERANCE:
			case TEMPERATURE_TOLERANCE: {
				for (final Tolerance a3 : Tolerance.values()) {
					alleles.add(a3.getAllele());
				}
				break;
			}
			case PRIMARY:
			case SECONDARY:
			case STEM: {
				for (final EnumFlowerColor a4 : EnumFlowerColor.values()) {
					alleles.add(a4.getAllele());
				}
				break;
			}
			case SAPPINESS: {
				for (final ForestryAllele.Sappiness a5 : ForestryAllele.Sappiness.values()) {
					alleles.add(a5.getAllele());
				}
				break;
			}
			case TERRITORY: {
				for (final ForestryAllele.Territory a6 : ForestryAllele.Territory.values()) {
					alleles.add(a6.getAllele());
				}
				break;
			}
			default: {
				break;
			}
		}
	}
}
