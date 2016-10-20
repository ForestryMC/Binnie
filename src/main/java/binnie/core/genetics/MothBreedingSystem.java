// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.genetics;

import java.util.TreeSet;
import forestry.api.lepidopterology.EnumFlutterType;
import forestry.api.lepidopterology.IButterflyRoot;
import net.minecraft.item.ItemStack;
import binnie.core.BinnieCore;
import forestry.api.genetics.IAlleleInteger;
import forestry.api.lepidopterology.EnumButterflyChromosome;
import forestry.api.genetics.IChromosomeType;
import forestry.api.lepidopterology.ILepidopteristTracker;
import forestry.api.genetics.IBreedingTracker;
import forestry.api.genetics.ISpeciesRoot;
import forestry.api.genetics.IAllele;
import net.minecraft.entity.player.EntityPlayer;
import forestry.api.genetics.IMutation;
import binnie.extratrees.ExtraTrees;
import binnie.Binnie;

class MothBreedingSystem extends BreedingSystem
{
	public MothBreedingSystem() {
		this.iconUndiscovered = Binnie.Resource.getItemIcon(ExtraTrees.instance, "icon/undiscoveredMoth");
		this.iconDiscovered = Binnie.Resource.getItemIcon(ExtraTrees.instance, "icon/discoveredMoth");
	}

	@Override
	public float getChance(final IMutation mutation, final EntityPlayer player, final IAllele species1, final IAllele species2) {
		return 0.0f;
	}

	@Override
	public ISpeciesRoot getSpeciesRoot() {
		return Binnie.Genetics.getButterflyRoot();
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
				return Binnie.Language.localise(BinnieCore.instance, "allele.metabolism.highest");
			}
			if (metabolism >= 16) {
				return Binnie.Language.localise(BinnieCore.instance, "allele.metabolism.higher");
			}
			if (metabolism >= 13) {
				return Binnie.Language.localise(BinnieCore.instance, "allele.metabolism.high");
			}
			if (metabolism >= 10) {
				return Binnie.Language.localise(BinnieCore.instance, "allele.metabolism.normal");
			}
			if (metabolism >= 7) {
				return Binnie.Language.localise(BinnieCore.instance, "allele.metabolism.slow");
			}
			if (metabolism >= 4) {
				return Binnie.Language.localise(BinnieCore.instance, "allele.metabolism.slower");
			}
			return Binnie.Language.localise(BinnieCore.instance, "allele.metabolism.slowest");
		}
		else {
			if (chromosome == EnumButterflyChromosome.FERTILITY) {
				final int metabolism = ((IAlleleInteger) allele).getValue();
				return metabolism + "x";
			}
			return super.getAlleleName(chromosome, allele);
		}
	}

	@Override
	public boolean isDNAManipulable(final ItemStack member) {
		return ((IButterflyRoot) this.getSpeciesRoot()).getType(member) == EnumFlutterType.SERUM;
	}

	@Override
	public int[] getActiveTypes() {
		return new int[] { EnumFlutterType.BUTTERFLY.ordinal(), EnumFlutterType.CATERPILLAR.ordinal(), EnumFlutterType.SERUM.ordinal() };
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
