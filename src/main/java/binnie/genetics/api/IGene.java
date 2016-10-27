package binnie.genetics.api;

import forestry.api.core.INbtReadable;
import forestry.api.core.INbtWritable;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.nbt.NBTTagCompound;

public interface IGene extends INbtReadable, INbtWritable {
	IChromosomeType getChromosome();

	String getName();

	ISpeciesRoot getSpeciesRoot();

	IAllele getAllele();

	NBTTagCompound getNBTTagCompound();
}
