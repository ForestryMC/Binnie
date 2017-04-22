package binnie.core.genetics;

import binnie.*;
import binnie.core.*;
import binnie.extrabees.*;
import forestry.api.apiculture.*;
import forestry.api.genetics.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

import java.util.*;

class BeeBreedingSystem extends BreedingSystem {
	public BeeBreedingSystem() {
		iconUndiscovered = Binnie.Resource.getItemIcon(ExtraBees.instance, "icon/undiscoveredBee");
		iconDiscovered = Binnie.Resource.getItemIcon(ExtraBees.instance, "icon/discoveredBee");
	}

	@Override
	public float getChance(IMutation mutation, EntityPlayer player, IAllele species1, IAllele species2) {
		return ((IBeeMutation) mutation).getChance(
			new VirtualBeeHousing(player),
			(IAlleleBeeSpecies) species1,
			(IAlleleBeeSpecies) species2,
			(IBeeGenome) getSpeciesRoot().templateAsGenome(getSpeciesRoot().getTemplate(species1.getUID())),
			(IBeeGenome) getSpeciesRoot().templateAsGenome(getSpeciesRoot().getTemplate(species2.getUID()))
		);
	}

	@Override
	public ISpeciesRoot getSpeciesRoot() {
		return Binnie.Genetics.getBeeRoot();
	}

	@Override
	public int getColour() {
		return 0xffd900;
	}

	@Override
	public Class<? extends IBreedingTracker> getTrackerClass() {
		return IApiaristTracker.class;
	}

	@Override
	public String getAlleleName(IChromosomeType chromosome, IAllele allele) {
		if (chromosome == EnumBeeChromosome.FERTILITY) {
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
		return ((IBeeRoot) getSpeciesRoot()).getType(member) == EnumBeeType.LARVAE;
	}

	@Override
	public int[] getActiveTypes() {
		return new int[] {
			EnumBeeType.DRONE.ordinal(),
			EnumBeeType.PRINCESS.ordinal(),
			EnumBeeType.QUEEN.ordinal(),
			EnumBeeType.LARVAE.ordinal()
		};
	}

	@Override
	public void addExtraAlleles(IChromosomeType chromosome, TreeSet<IAllele> alleles) {
		switch ((EnumBeeChromosome) chromosome) {
			case FERTILITY:
				for (ForestryAllele.Fertility a : ForestryAllele.Fertility.values()) {
					alleles.add(a.getAllele());
				}
				break;

			case FLOWERING:
				for (ForestryAllele.Flowering a2 : ForestryAllele.Flowering.values()) {
					alleles.add(a2.getAllele());
				}
				break;

			case HUMIDITY_TOLERANCE:
			case TEMPERATURE_TOLERANCE:
				for (Tolerance a3 : Tolerance.values()) {
					alleles.add(a3.getAllele());
				}
				break;

			case LIFESPAN:
				for (ForestryAllele.Lifespan a4 : ForestryAllele.Lifespan.values()) {
					alleles.add(a4.getAllele());
				}
				break;

			case SPEED:
				for (ForestryAllele.Speed a5 : ForestryAllele.Speed.values()) {
					alleles.add(a5.getAllele());
				}
				break;

			case TERRITORY:
				for (ForestryAllele.Territory a6 : ForestryAllele.Territory.values()) {
					alleles.add(a6.getAllele());
				}
				break;

			case NOCTURNAL:
			case CAVE_DWELLING:
			case TOLERANT_FLYER:
				for (ForestryAllele.Bool a7 : ForestryAllele.Bool.values()) {
					alleles.add(a7.getAllele());
				}
				break;
		}
	}
}
