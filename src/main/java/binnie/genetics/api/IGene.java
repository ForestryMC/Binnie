// 
// Decompiled by Procyon v0.5.30
// 

package binnie.genetics.api;

import net.minecraft.nbt.NBTTagCompound;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.ISpeciesRoot;
import forestry.api.genetics.IChromosomeType;
import forestry.api.core.INBTTagable;

public interface IGene extends INBTTagable
{
	IChromosomeType getChromosome();

	String getName();

	ISpeciesRoot getSpeciesRoot();

	IAllele getAllele();

	NBTTagCompound getNBTTagCompound();
}
