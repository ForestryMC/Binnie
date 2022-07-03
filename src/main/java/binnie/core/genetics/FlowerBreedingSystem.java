package binnie.core.genetics;

import binnie.Binnie;
import binnie.botany.api.EnumFlowerChromosome;
import binnie.botany.api.EnumFlowerStage;
import binnie.botany.api.IBotanistTracker;
import binnie.botany.api.IFlowerRoot;
import binnie.botany.core.BotanyCore;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.core.util.I18N;
import binnie.extrabees.ExtraBees;
import binnie.genetics.genetics.AlleleHelper;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.EnumTolerance;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IBreedingTracker;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IMutation;
import forestry.api.genetics.ISpeciesRoot;
import forestry.core.genetics.alleles.EnumAllele;
import java.util.TreeSet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

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
    public int getColor() {
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
        return ((IFlowerRoot) getSpeciesRoot()).getStageType(member) == EnumFlowerStage.POLLEN;
    }

    @Override
    public IIndividual getConversion(ItemStack stack) {
        return BotanyCore.getFlowerRoot().getConversion(stack);
    }

    @Override
    public int[] getActiveTypes() {
        return new int[] {
            EnumFlowerStage.FLOWER.ordinal(), EnumFlowerStage.POLLEN.ordinal(), EnumFlowerStage.SEED.ordinal()
        };
    }

    @Override
    public void addExtraAlleles(IChromosomeType chromosome, TreeSet<IAllele> alleles) {
        switch ((EnumFlowerChromosome) chromosome) {
            case FERTILITY:
                for (EnumAllele.Fertility fertility : EnumAllele.Fertility.values()) {
                    alleles.add(AlleleManager.alleleRegistry.getAllele(AlleleHelper.getUid(fertility)));
                }
                break;

            case LIFESPAN:
                for (EnumAllele.Lifespan lifespan : EnumAllele.Lifespan.values()) {
                    alleles.add(AlleleManager.alleleRegistry.getAllele(AlleleHelper.getUid(lifespan)));
                }
                break;

            case HUMIDITY_TOLERANCE:
            case PH_TOLERANCE:
            case TEMPERATURE_TOLERANCE:
                for (EnumTolerance a3 : EnumTolerance.values()) {
                    alleles.add(AlleleManager.alleleRegistry.getAllele(AlleleHelper.getUid(a3)));
                }
                break;

            case PRIMARY:
            case SECONDARY:
            case STEM:
                for (EnumFlowerColor color : EnumFlowerColor.values()) {
                    alleles.add(color.getAllele());
                }
                break;

            case SAPPINESS:
                for (EnumAllele.Sappiness sappiness : EnumAllele.Sappiness.values()) {
                    alleles.add(AlleleManager.alleleRegistry.getAllele(AlleleHelper.getUid(sappiness)));
                }
                break;

            case TERRITORY:
                for (EnumAllele.Territory territory : EnumAllele.Territory.values()) {
                    alleles.add(AlleleManager.alleleRegistry.getAllele(AlleleHelper.getUid(territory)));
                }
                break;
        }
    }
}
