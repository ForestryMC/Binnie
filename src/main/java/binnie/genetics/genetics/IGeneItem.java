// 
// Decompiled by Procyon v0.5.30
// 

package binnie.genetics.genetics;

import binnie.genetics.api.IGene;
import net.minecraft.item.ItemStack;
import java.util.List;
import forestry.api.genetics.ISpeciesRoot;

public interface IGeneItem
{
	ISpeciesRoot getSpeciesRoot();

	void getInfo(final List p0);

	int getColour(final int p0);

	void writeToItem(final ItemStack p0);

	void addGene(final IGene p0);
}
