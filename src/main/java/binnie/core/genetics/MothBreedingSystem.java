package binnie.core.genetics;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.extratrees.ExtraTrees;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleInteger;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IBreedingTracker;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IMutation;
import forestry.api.genetics.ISpeciesRoot;
import forestry.api.genetics.ISpeciesType;
import forestry.api.lepidopterology.EnumButterflyChromosome;
import forestry.api.lepidopterology.EnumFlutterType;
import forestry.api.lepidopterology.ILepidopteristTracker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.TreeSet;

class MothBreedingSystem extends BreedingSystem {
	public MothBreedingSystem() {
		this.iconUndiscovered = Binnie.RESOURCE.getItemSprite(ExtraTrees.instance, "icon/undiscovered_moth");
		this.iconDiscovered = Binnie.RESOURCE.getItemSprite(ExtraTrees.instance, "icon/discovered_moth");
	}

	@Override
	public float getChance(final IMutation mutation, final EntityPlayer player, final IAlleleSpecies species1, final IAlleleSpecies species2) {
		return 0.0f;
	}

	@Override
	public ISpeciesRoot getSpeciesRoot() {
		return Binnie.GENETICS.getButterflyRoot();
	}

	@Override
	public int getColour() {
		return 62194;
	}

	@Override
	public Class<? extends IBreedingTracker> getTrackerClass() {
		return ILepidopteristTracker.class;
	}

	@Override
	public String getAlleleName(final IChromosomeType chromosome, final IAllele allele) {
		if (chromosome == EnumButterflyChromosome.METABOLISM) {
			final int metabolism = ((IAlleleInteger) allele).getValue();
			if (metabolism >= 19) {
				return Binnie.LANGUAGE.localise(BinnieCore.getInstance(), "allele.metabolism.highest");
			}
			if (metabolism >= 16) {
				return Binnie.LANGUAGE.localise(BinnieCore.getInstance(), "allele.metabolism.higher");
			}
			if (metabolism >= 13) {
				return Binnie.LANGUAGE.localise(BinnieCore.getInstance(), "allele.metabolism.high");
			}
			if (metabolism >= 10) {
				return Binnie.LANGUAGE.localise(BinnieCore.getInstance(), "allele.metabolism.normal");
			}
			if (metabolism >= 7) {
				return Binnie.LANGUAGE.localise(BinnieCore.getInstance(), "allele.metabolism.slow");
			}
			if (metabolism >= 4) {
				return Binnie.LANGUAGE.localise(BinnieCore.getInstance(), "allele.metabolism.slower");
			}
			return Binnie.LANGUAGE.localise(BinnieCore.getInstance(), "allele.metabolism.slowest");
		} else {
			if (chromosome == EnumButterflyChromosome.FERTILITY) {
				final int metabolism = ((IAlleleInteger) allele).getValue();
				return metabolism + "x";
			}
			return super.getAlleleName(chromosome, allele);
		}
	}

	@Override
	public boolean isDNAManipulable(final ItemStack member) {
		ISpeciesType type = this.getSpeciesRoot().getType(member);
		return type != null && isDNAManipulable(type);
	}

	@Override
	public boolean isDNAManipulable(ISpeciesType type) {
		return type == EnumFlutterType.SERUM;
	}

	@Override
	public ISpeciesType[] getActiveTypes() {
		return new ISpeciesType[]{EnumFlutterType.BUTTERFLY, EnumFlutterType.CATERPILLAR, EnumFlutterType.SERUM};
	}

	@Override
	public void addExtraAlleles(final IChromosomeType chromosome, final TreeSet<IAllele> alleles) {
		switch ((EnumButterflyChromosome) chromosome) {
			case FERTILITY: {
				for (final ForestryAllele.Int a : ForestryAllele.Int.values()) {
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
			case METABOLISM: {
				for (final ForestryAllele.Int a : ForestryAllele.Int.values()) {
					alleles.add(a.getAllele());
				}
				break;
			}
			case TOLERANT_FLYER:
			case FIRE_RESIST:
			case NOCTURNAL: {
				for (final ForestryAllele.Bool a3 : ForestryAllele.Bool.values()) {
					alleles.add(a3.getAllele());
				}
				break;
			}
			case SIZE: {
				for (final ForestryAllele.Size a4 : ForestryAllele.Size.values()) {
					alleles.add(a4.getAllele());
				}
				break;
			}
			case SPEED: {
				for (final ForestryAllele.Speed a5 : ForestryAllele.Speed.values()) {
					alleles.add(a5.getAllele());
				}
				break;
			}
			case HUMIDITY_TOLERANCE:
			case TEMPERATURE_TOLERANCE: {
				for (final Tolerance a6 : Tolerance.values()) {
					alleles.add(a6.getAllele());
				}
				break;
			}
		}
	}
}
