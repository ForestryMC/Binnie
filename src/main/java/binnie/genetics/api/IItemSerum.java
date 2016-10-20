package binnie.genetics.api;

import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.item.ItemStack;

public interface IItemSerum extends IItemChargable {
    IGene[] getGenes(final ItemStack p0);

    ISpeciesRoot getSpeciesRoot(final ItemStack p0);

    IGene getGene(final ItemStack p0, final int p1);

    ItemStack addGene(final ItemStack p0, final IGene p1);
}
