package binnie.core.genetics;

import binnie.Binnie;
import binnie.core.util.I18N;
import binnie.extrabees.ExtraBees;
import binnie.genetics.genetics.AlleleHelper;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IApiaristTracker;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeMutation;
import forestry.api.apiculture.IBeeRoot;
import forestry.api.genetics.EnumTolerance;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IBreedingTracker;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IMutation;
import forestry.api.genetics.ISpeciesRoot;
import forestry.core.genetics.alleles.EnumAllele;
import java.util.TreeSet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

class BeeBreedingSystem extends BreedingSystem {
    public BeeBreedingSystem() {
        iconUndiscovered = Binnie.Resource.getItemIcon(ExtraBees.instance, "icon/undiscoveredBee");
        iconDiscovered = Binnie.Resource.getItemIcon(ExtraBees.instance, "icon/discoveredBee");
    }

    @Override
    public float getChance(IMutation mutation, EntityPlayer player, IAllele species1, IAllele species2) {
        return ((IBeeMutation) mutation)
                .getChance(
                        new VirtualBeeHousing(player),
                        (IAlleleBeeSpecies) species1,
                        (IAlleleBeeSpecies) species2,
                        (IBeeGenome) getSpeciesRoot()
                                .templateAsGenome(getSpeciesRoot().getTemplate(species1.getUID())),
                        (IBeeGenome) getSpeciesRoot()
                                .templateAsGenome(getSpeciesRoot().getTemplate(species2.getUID())));
    }

    @Override
    public ISpeciesRoot getSpeciesRoot() {
        return Binnie.Genetics.getBeeRoot();
    }

    @Override
    public int getColor() {
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
                for (EnumAllele.Fertility fertility : EnumAllele.Fertility.values()) {
                    alleles.add(AlleleHelper.getAllele(fertility));
                }
                break;

            case FLOWERING:
                for (EnumAllele.Flowering flowering : EnumAllele.Flowering.values()) {
                    alleles.add(AlleleHelper.getAllele(flowering));
                }
                break;

            case HUMIDITY_TOLERANCE:
            case TEMPERATURE_TOLERANCE:
                for (EnumTolerance tolerance : EnumTolerance.values()) {
                    alleles.add(AlleleHelper.getAllele(tolerance));
                }
                break;

            case LIFESPAN:
                for (EnumAllele.Lifespan lifespan : EnumAllele.Lifespan.values()) {
                    alleles.add(AlleleHelper.getAllele(lifespan));
                }
                break;

            case SPEED:
                for (EnumAllele.Speed speed : EnumAllele.Speed.values()) {
                    alleles.add(AlleleHelper.getAllele(speed));
                }
                break;

            case TERRITORY:
                for (EnumAllele.Territory territory : EnumAllele.Territory.values()) {
                    alleles.add(AlleleHelper.getAllele(territory));
                }
                break;

            case NOCTURNAL:
            case CAVE_DWELLING:
            case TOLERANT_FLYER:
                for (ForestryAllele.Bool bool : ForestryAllele.Bool.values()) {
                    alleles.add(bool.getAllele());
                }
                break;
        }
    }
}
