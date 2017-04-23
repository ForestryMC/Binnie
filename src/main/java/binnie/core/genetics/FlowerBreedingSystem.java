package binnie.core.genetics;

import binnie.Binnie;
import binnie.botany.api.EnumFlowerChromosome;
import binnie.botany.api.EnumFlowerStage;
import binnie.botany.api.IBotanistTracker;
import binnie.botany.api.IFlowerRoot;
import binnie.botany.core.BotanyCore;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.core.BinnieCore;
import binnie.extrabees.ExtraBees;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IBreedingTracker;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IMutation;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.TreeSet;

class FlowerBreedingSystem extends BreedingSystem {
	public FlowerBreedingSystem() {
		iconUndiscovered = Binnie.Resource.getItemIcon(ExtraBees.instance, "icon/undiscoveredBee");
		iconDiscovered = Binnie.Resource.getItemIcon(ExtraBees.instance, "icon/discoveredBee");
	}

	@Override
	public float getChance(IMutation mutation, EntityPlayer player, IAllele species1, IAllele species2) {
		return mutation.getBaseChance();
	}

	@Override
	public ISpeciesRoot getSpeciesRoot() {
		return Binnie.Genetics.getFlowerRoot();
	}

	@Override
	public int getColour() {
		return 0xde3737;
	}

	@Override
	public Class<? extends IBreedingTracker> getTrackerClass() {
		return IBotanistTracker.class;
	}

	@Override
	public String getAlleleName(IChromosomeType chromosome, IAllele allele) {
		if (chromosome == EnumFlowerChromosome.FERTILITY) {
			if (allele.getUID().contains("Low")) {
				return Binnie.Language.localise(BinnieCore.instance, "allele.fertility.low");
			}
			if (allele.getUID().contains("Normal")) {
				return Binnie.Language.localise(BinnieCore.instance, "allele.fertility.normal");
			}
			if (allele.getUID().contains("High")) {
				return Binnie.Language.localise(BinnieCore.instance, "allele.fertility.high");
			}
			if (allele.getUID().contains("Maximum")) {
				return Binnie.Language.localise(BinnieCore.instance, "allele.fertility.maximum");
			}
		}
		return super.getAlleleName(chromosome, allele);
	}

	@Override
	public boolean isDNAManipulable(ItemStack member) {
		return ((IFlowerRoot) getSpeciesRoot()).getStageType(member) == EnumFlowerStage.POLLEN;
	}

	@Override
	public IIndividual getConversion(ItemStack stack) {
		return BotanyCore.getFlowerRoot().getConversion(stack);
	}

	@Override
	public int[] getActiveTypes() {
		return new int[]{
				EnumFlowerStage.FLOWER.ordinal(),
				EnumFlowerStage.POLLEN.ordinal(),
				EnumFlowerStage.SEED.ordinal()
		};
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
					alleles.add(a4.getAllele());
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
