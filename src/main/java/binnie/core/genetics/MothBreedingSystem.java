package binnie.core.genetics;

import binnie.Binnie;
import binnie.core.util.I18N;
import binnie.extratrees.ExtraTrees;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleInteger;
import forestry.api.genetics.IBreedingTracker;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IMutation;
import forestry.api.genetics.ISpeciesRoot;
import forestry.api.lepidopterology.EnumButterflyChromosome;
import forestry.api.lepidopterology.EnumFlutterType;
import forestry.api.lepidopterology.IButterflyRoot;
import forestry.api.lepidopterology.ILepidopteristTracker;
import java.util.TreeSet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

class MothBreedingSystem extends BreedingSystem {
    public MothBreedingSystem() {
        iconUndiscovered = Binnie.Resource.getItemIcon(ExtraTrees.instance, "icon/undiscoveredMoth");
        iconDiscovered = Binnie.Resource.getItemIcon(ExtraTrees.instance, "icon/discoveredMoth");
    }

    @Override
    public float getChance(IMutation mutation, EntityPlayer player, IAllele species1, IAllele species2) {
        return 0.0f;
    }

    @Override
    public ISpeciesRoot getSpeciesRoot() {
        return Binnie.Genetics.getButterflyRoot();
    }

    @Override
    public int getColor() {
        return 0x00f2f2;
    }

    @Override
    public Class<? extends IBreedingTracker> getTrackerClass() {
        return ILepidopteristTracker.class;
    }

    @Override
    public String getAlleleName(IChromosomeType chromosome, IAllele allele) {
        if (chromosome == EnumButterflyChromosome.METABOLISM) {
            int metabolism = ((IAlleleInteger) allele).getValue();
            if (metabolism >= 19) {
                return I18N.localise("binniecore.allele.metabolism.highest");
            }
            if (metabolism >= 16) {
                return I18N.localise("binniecore.allele.metabolism.higher");
            }
            if (metabolism >= 13) {
                return I18N.localise("binniecore.allele.metabolism.high");
            }
            if (metabolism >= 10) {
                return I18N.localise("binniecore.allele.metabolism.average");
            }
            if (metabolism >= 7) {
                return I18N.localise("binniecore.allele.metabolism.slow");
            }
            if (metabolism >= 4) {
                return I18N.localise("binniecore.allele.metabolism.slower");
            }
            return I18N.localise("binniecore.allele.metabolism.slowest");
        }
        if (chromosome == EnumButterflyChromosome.FERTILITY) {
            return ((IAlleleInteger) allele).getValue() + "x";
        }
        return super.getAlleleName(chromosome, allele);
    }

    @Override
    public boolean isDNAManipulable(ItemStack member) {
        return ((IButterflyRoot) getSpeciesRoot()).getType(member) == EnumFlutterType.SERUM;
    }

    @Override
    public int[] getActiveTypes() {
        return new int[] {
            EnumFlutterType.BUTTERFLY.ordinal(), EnumFlutterType.CATERPILLAR.ordinal(), EnumFlutterType.SERUM.ordinal()
        };
    }

    @Override
    public void addExtraAlleles(IChromosomeType chromosome, TreeSet<IAllele> alleles) {
        switch ((EnumButterflyChromosome) chromosome) {
            case FERTILITY:
                for (ForestryAllele.Int a : ForestryAllele.Int.values()) {
                    alleles.add(a.getAllele());
                }
                break;

            case LIFESPAN:
                for (ForestryAllele.Lifespan a2 : ForestryAllele.Lifespan.values()) {
                    alleles.add(a2.getAllele());
                }
                break;

            case METABOLISM:
                for (ForestryAllele.Int a : ForestryAllele.Int.values()) {
                    alleles.add(a.getAllele());
                }
                break;

            case TOLERANT_FLYER:
            case FIRE_RESIST:
            case NOCTURNAL:
                for (ForestryAllele.Bool a3 : ForestryAllele.Bool.values()) {
                    alleles.add(a3.getAllele());
                }
                break;

            case SIZE:
                for (ForestryAllele.Size a4 : ForestryAllele.Size.values()) {
                    alleles.add(a4.getAllele());
                }
                break;

            case SPEED:
                for (ForestryAllele.Speed a5 : ForestryAllele.Speed.values()) {
                    alleles.add(a5.getAllele());
                }
                break;

            case HUMIDITY_TOLERANCE:
            case TEMPERATURE_TOLERANCE:
                for (Tolerance a6 : Tolerance.values()) {
                    alleles.add(a6.getAllele());
                }
                break;
        }
    }
}
