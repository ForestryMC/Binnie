package binnie.botany.genetics;

import binnie.Binnie;
import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumFlowerChromosome;
import binnie.botany.api.EnumMoisture;
import binnie.botany.api.IAlleleFlowerSpecies;
import binnie.botany.api.IFlowerGenome;
import binnie.botany.api.IFlowerType;
import binnie.botany.core.BotanyCore;
import forestry.api.core.EnumTemperature;
import forestry.api.genetics.EnumTolerance;
import forestry.api.genetics.IAlleleFloat;
import forestry.api.genetics.IAlleleInteger;
import forestry.api.genetics.IAlleleTolerance;
import forestry.api.genetics.IChromosome;
import forestry.api.genetics.ISpeciesRoot;
import forestry.core.genetics.Genome;
import net.minecraft.nbt.NBTTagCompound;

public class FlowerGenome extends Genome implements IFlowerGenome {
    public FlowerGenome(NBTTagCompound nbttagcompound) {
        super(nbttagcompound);
    }

    public FlowerGenome(IChromosome[] chromosomes) {
        super(chromosomes);
    }

    @Override
    public IAlleleFlowerSpecies getPrimary() {
        return (IAlleleFlowerSpecies) getChromosomes()[EnumFlowerChromosome.SPECIES.ordinal()].getPrimaryAllele();
    }

    @Override
    public IAlleleFlowerSpecies getSecondary() {
        return (IAlleleFlowerSpecies) getChromosomes()[EnumFlowerChromosome.SPECIES.ordinal()].getSecondaryAllele();
    }

    @Override
    public ISpeciesRoot getSpeciesRoot() {
        return BotanyCore.getFlowerRoot();
    }

    @Override
    public EnumFlowerColor getPrimaryColor() {
        return ((AlleleColor) getActiveAllele(EnumFlowerChromosome.PRIMARY)).getColor();
    }

    @Override
    public EnumFlowerColor getSecondaryColor() {
        return ((AlleleColor) getActiveAllele(EnumFlowerChromosome.SECONDARY)).getColor();
    }

    @Override
    public EnumFlowerColor getStemColor() {
        return ((AlleleColor) getActiveAllele(EnumFlowerChromosome.STEM)).getColor();
    }

    @Override
    public int getFertility() {
        return ((IAlleleInteger) getActiveAllele(EnumFlowerChromosome.FERTILITY)).getValue();
    }

    @Override
    public int getLifespan() {
        return ((IAlleleInteger) getActiveAllele(EnumFlowerChromosome.LIFESPAN)).getValue() / 5;
    }

    @Override
    public IFlowerType getType() {
        return getPrimary().getType();
    }

    @Override
    public EnumTolerance getToleranceTemperature() {
        return ((IAlleleTolerance) getActiveAllele(EnumFlowerChromosome.TEMPERATURE_TOLERANCE)).getValue();
    }

    @Override
    public EnumTolerance getToleranceMoisture() {
        return ((IAlleleTolerance) getActiveAllele(EnumFlowerChromosome.HUMIDITY_TOLERANCE)).getValue();
    }

    @Override
    public EnumTolerance getTolerancePH() {
        return ((IAlleleTolerance) getActiveAllele(EnumFlowerChromosome.PH_TOLERANCE)).getValue();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
    }

    @Override
    public float getAgeChance() {
        return 1.0f * (float) Math.sqrt(2.0f / getLifespan());
    }

    @Override
    public float getSappiness() {
        return ((IAlleleFloat) getActiveAllele(EnumFlowerChromosome.SAPPINESS)).getValue();
    }

    @Override
    public boolean canTolerate(EnumAcidity ePH) {
        int pH = ePH.ordinal();
        int[] pHTol = Binnie.Genetics.getTolerance(getTolerancePH());
        int fPH = getPrimary().getPH().ordinal();
        return pH >= fPH + pHTol[0] && pH <= fPH + pHTol[1];
    }

    @Override
    public boolean canTolerate(EnumMoisture eMoisture) {
        int moisture = eMoisture.ordinal();
        int[] moistTol = Binnie.Genetics.getTolerance(getToleranceMoisture());
        int fMoisture = getPrimary().getMoisture().ordinal();
        return moisture >= fMoisture + moistTol[0] && moisture <= fMoisture + moistTol[1];
    }

    @Override
    public boolean canTolerate(EnumTemperature eTemp) {
        int temp = eTemp.ordinal();
        int[] tempTol = Binnie.Genetics.getTolerance(getToleranceTemperature());
        int fTemp = getPrimary().getTemperature().ordinal();
        return temp >= fTemp + tempTol[0] && temp <= fTemp + tempTol[1];
    }
}
