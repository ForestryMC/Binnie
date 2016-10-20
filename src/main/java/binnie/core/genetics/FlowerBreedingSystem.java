package binnie.core.genetics;

import binnie.Binnie;
import binnie.botany.api.*;
import binnie.botany.core.BotanyCore;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.core.BinnieCore;
import binnie.extrabees.ExtraBees;
import forestry.api.genetics.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.TreeSet;

class FlowerBreedingSystem extends BreedingSystem {
    public FlowerBreedingSystem() {
        this.iconUndiscovered = Binnie.Resource.getItemIcon(ExtraBees.instance, "icon/undiscoveredBee");
        this.iconDiscovered = Binnie.Resource.getItemIcon(ExtraBees.instance, "icon/discoveredBee");
    }

    @Override
    public float getChance(final IMutation mutation, final EntityPlayer player, final IAllele species1, final IAllele species2) {
        return ((IFlowerMutation) mutation).getBaseChance();
    }

    @Override
    public ISpeciesRoot getSpeciesRoot() {
        return Binnie.Genetics.getFlowerRoot();
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
    public boolean isDNAManipulable(final ItemStack member) {
        return ((IFlowerRoot) this.getSpeciesRoot()).getStageType(member) == EnumFlowerStage.POLLEN;
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
        }
    }
}
