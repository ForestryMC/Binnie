// 
// Decompiled by Procyon v0.5.30
// 

package binnie.botany.genetics;

import forestry.api.core.EnumTemperature;
import binnie.botany.api.EnumMoisture;
import binnie.Binnie;
import binnie.botany.api.EnumAcidity;
import forestry.api.genetics.IAlleleFloat;
import forestry.api.genetics.IAlleleTolerance;
import forestry.api.genetics.EnumTolerance;
import binnie.botany.api.IFlowerType;
import forestry.api.genetics.IAlleleInteger;
import forestry.api.genetics.IChromosomeType;
import binnie.botany.core.BotanyCore;
import forestry.api.genetics.ISpeciesRoot;
import binnie.botany.api.EnumFlowerChromosome;
import binnie.botany.api.IAlleleFlowerSpecies;
import forestry.api.genetics.IChromosome;
import net.minecraft.nbt.NBTTagCompound;
import binnie.botany.api.IFlowerGenome;
import forestry.core.genetics.Genome;

public class FlowerGenome extends Genome implements IFlowerGenome
{
	public FlowerGenome(final NBTTagCompound nbttagcompound) {
		super(nbttagcompound);
	}

	public FlowerGenome(final IChromosome[] chromosomes) {
		super(chromosomes);
	}

	@Override
	public IAlleleFlowerSpecies getPrimary() {
		return (IAlleleFlowerSpecies) this.getChromosomes()[EnumFlowerChromosome.SPECIES.ordinal()].getPrimaryAllele();
	}

	@Override
	public IAlleleFlowerSpecies getSecondary() {
		return (IAlleleFlowerSpecies) this.getChromosomes()[EnumFlowerChromosome.SPECIES.ordinal()].getSecondaryAllele();
	}

	@Override
	public ISpeciesRoot getSpeciesRoot() {
		return BotanyCore.getFlowerRoot();
	}

	@Override
	public EnumFlowerColor getPrimaryColor() {
		return ((AlleleColor) this.getActiveAllele(EnumFlowerChromosome.PRIMARY)).getColor();
	}

	@Override
	public EnumFlowerColor getSecondaryColor() {
		return ((AlleleColor) this.getActiveAllele(EnumFlowerChromosome.SECONDARY)).getColor();
	}

	@Override
	public EnumFlowerColor getStemColor() {
		return ((AlleleColor) this.getActiveAllele(EnumFlowerChromosome.STEM)).getColor();
	}

	@Override
	public int getFertility() {
		return ((IAlleleInteger) this.getActiveAllele(EnumFlowerChromosome.FERTILITY)).getValue();
	}

	@Override
	public int getLifespan() {
		return ((IAlleleInteger) this.getActiveAllele(EnumFlowerChromosome.LIFESPAN)).getValue() / 5;
	}

	@Override
	public IFlowerType getType() {
		return this.getPrimary().getType();
	}

	@Override
	public EnumTolerance getToleranceTemperature() {
		return ((IAlleleTolerance) this.getActiveAllele(EnumFlowerChromosome.TEMPERATURE_TOLERANCE)).getValue();
	}

	@Override
	public EnumTolerance getToleranceMoisture() {
		return ((IAlleleTolerance) this.getActiveAllele(EnumFlowerChromosome.HUMIDITY_TOLERANCE)).getValue();
	}

	@Override
	public EnumTolerance getTolerancePH() {
		return ((IAlleleTolerance) this.getActiveAllele(EnumFlowerChromosome.PH_TOLERANCE)).getValue();
	}

	@Override
	public void readFromNBT(final NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
	}

	@Override
	public float getAgeChance() {
		return 1.0f * (float) Math.sqrt(2.0f / this.getLifespan());
	}

	@Override
	public float getSappiness() {
		return ((IAlleleFloat) this.getActiveAllele(EnumFlowerChromosome.SAPPINESS)).getValue();
	}

	@Override
	public boolean canTolerate(final EnumAcidity ePH) {
		final int pH = ePH.ordinal();
		final int[] pHTol = Binnie.Genetics.getTolerance(this.getTolerancePH());
		final int fPH = this.getPrimary().getPH().ordinal();
		return pH >= fPH + pHTol[0] && pH <= fPH + pHTol[1];
	}

	@Override
	public boolean canTolerate(final EnumMoisture eMoisture) {
		final int moisture = eMoisture.ordinal();
		final int[] moistTol = Binnie.Genetics.getTolerance(this.getToleranceMoisture());
		final int fMoisture = this.getPrimary().getMoisture().ordinal();
		return moisture >= fMoisture + moistTol[0] && moisture <= fMoisture + moistTol[1];
	}

	@Override
	public boolean canTolerate(final EnumTemperature eTemp) {
		final int temp = eTemp.ordinal();
		final int[] tempTol = Binnie.Genetics.getTolerance(this.getToleranceTemperature());
		final int fTemp = this.getPrimary().getTemperature().ordinal();
		return temp >= fTemp + tempTol[0] && temp <= fTemp + tempTol[1];
	}
}
