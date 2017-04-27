package binnie.genetics.genetics;

import binnie.genetics.api.IGene;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface IGeneItem {
	ISpeciesRoot getSpeciesRoot();

	void getInfo(List tooltip);

	int getColour(int color);

	void writeToItem(ItemStack stack);

	void addGene(IGene gene);
}
