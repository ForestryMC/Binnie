package binnie.genetics.genetics;

import binnie.genetics.api.IGene;
import forestry.api.genetics.ISpeciesRoot;
import java.util.List;
import net.minecraft.item.ItemStack;

public interface IGeneItem {
    ISpeciesRoot getSpeciesRoot();

    void getInfo(List tooltip);

    int getColour(int color);

    void writeToItem(ItemStack stack);

    void addGene(IGene gene);
}
