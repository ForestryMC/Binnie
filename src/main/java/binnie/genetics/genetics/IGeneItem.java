package binnie.genetics.genetics;

import binnie.genetics.api.IGene;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface IGeneItem {
    ISpeciesRoot getSpeciesRoot();

    void getInfo(final List p0);

    int getColour(final int p0);

    void writeToItem(final ItemStack p0);

    void addGene(final IGene p0);
}
