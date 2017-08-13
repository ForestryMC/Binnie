package binnie.core.api.genetics;

import net.minecraft.nbt.NBTTagCompound;

import forestry.api.core.INbtReadable;
import forestry.api.core.INbtWritable;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.ISpeciesRoot;

public interface IGene extends INbtReadable, INbtWritable {
	IChromosomeType getChromosome();

	String getName();

	ISpeciesRoot getSpeciesRoot();

	IAllele getAllele();

	NBTTagCompound getNBTTagCompound();
}
