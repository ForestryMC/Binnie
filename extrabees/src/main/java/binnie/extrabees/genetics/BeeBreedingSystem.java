package binnie.extrabees.genetics;

import javax.annotation.Nullable;
import java.util.TreeSet;

import binnie.core.genetics.BreedingSystem;
import binnie.core.genetics.ForestryAllele;
import binnie.core.genetics.Tolerance;
import binnie.core.genetics.VirtualBeeHousing;
import forestry.api.apiculture.BeeManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IApiaristTracker;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeMutation;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IBreedingTracker;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IMutation;
import forestry.api.genetics.ISpeciesRoot;
import forestry.api.genetics.ISpeciesType;

import binnie.core.Binnie;
import binnie.core.util.I18N;

public class BeeBreedingSystem extends BreedingSystem {

	public BeeBreedingSystem() {
		this.iconUndiscovered = Binnie.RESOURCE.getUndiscoveredBeeSprite();
		this.iconDiscovered = Binnie.RESOURCE.getDiscoveredBeeSprite();
	}

	@Override
	public float getChance(final IMutation mutation, final EntityPlayer player, final IAlleleSpecies firstSpecies, final IAlleleSpecies secondSpecies) {
		return ((IBeeMutation) mutation).
			getChance(new VirtualBeeHousing(player),
				(IAlleleBeeSpecies) firstSpecies,
				(IAlleleBeeSpecies) secondSpecies,
				(IBeeGenome) this.getSpeciesRoot().
					templateAsGenome(this.getSpeciesRoot().getTemplate(firstSpecies)),
				(IBeeGenome) this.getSpeciesRoot().templateAsGenome(this.getSpeciesRoot().getTemplate(secondSpecies))
			);
	}

	@Override
	public ISpeciesRoot getSpeciesRoot() {
		return BeeManager.beeRoot;
	}

	@Override
	public int getColour() {
		return 16767232;
	}

	@Override
	public Class<? extends IBreedingTracker> getTrackerClass() {
		return IApiaristTracker.class;
	}

	@Override
	public String getAlleleName(final IChromosomeType chromosome, final IAllele allele) {
		if (chromosome == EnumBeeChromosome.FERTILITY) {
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
	public boolean isDNAManipulable(final ItemStack member) {
		ISpeciesType type = this.getSpeciesRoot().getType(member);
		return isDNAManipulable(type);
	}

	@Override
	public boolean isDNAManipulable(@Nullable ISpeciesType type) {
		return type == EnumBeeType.LARVAE;
	}

	@Override
	public ISpeciesType[] getActiveTypes() {
		return new ISpeciesType[]{EnumBeeType.DRONE, EnumBeeType.PRINCESS, EnumBeeType.QUEEN, EnumBeeType.LARVAE};
	}

	@Override
	public void addExtraAlleles(final IChromosomeType chromosome, final TreeSet<IAllele> alleles) {
		switch ((EnumBeeChromosome) chromosome) {
			case FERTILITY: {
				for (final ForestryAllele.Fertility a : ForestryAllele.Fertility.values()) {
					alleles.add(a.getAllele());
				}
				break;
			}
			case FLOWERING: {
				for (final ForestryAllele.Flowering a2 : ForestryAllele.Flowering.values()) {
					alleles.add(a2.getAllele());
				}
				break;
			}
			case HUMIDITY_TOLERANCE:
			case TEMPERATURE_TOLERANCE: {
				for (final Tolerance a3 : Tolerance.values()) {
					alleles.add(a3.getAllele());
				}
				break;
			}
			case LIFESPAN: {
				for (final ForestryAllele.Lifespan a4 : ForestryAllele.Lifespan.values()) {
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
			case TERRITORY: {
				for (final ForestryAllele.Territory a6 : ForestryAllele.Territory.values()) {
					alleles.add(a6.getAllele());
				}
				break;
			}
			case NEVER_SLEEPS:
			case CAVE_DWELLING:
			case TOLERATES_RAIN: {
				for (final ForestryAllele.Bool a7 : ForestryAllele.Bool.values()) {
					alleles.add(a7.getAllele());
				}
				break;
			}
		}
	}
}
