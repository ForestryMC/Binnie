package binnie.genetics.genetics;

import binnie.genetics.Genetics;
import binnie.genetics.api.IGene;
import binnie.genetics.api.IItemChargable;
import binnie.genetics.api.IItemSerum;
import binnie.genetics.item.GeneticsItems;
import binnie.genetics.item.ItemSerum;
import binnie.genetics.item.ItemSerumArray;
import net.minecraft.item.ItemStack;

public class Engineering {
    public static boolean isGeneAcceptor(ItemStack stack) {
        if (stack == null) {
            return false;
        }
        if (stack.getItem() instanceof IItemSerum) {
            return ((IItemSerum) stack.getItem()).getCharges(stack) == 0;
        }
        return stack.getItem() == Genetics.itemGenetics
                && (stack.getItemDamage() == GeneticsItems.EmptySerum.ordinal()
                        || stack.getItemDamage() == GeneticsItems.EmptyGenome.ordinal());
    }

    public static boolean canAcceptGene(ItemStack stack, IGene gene) {
        if (stack.getItem() instanceof ItemSerum) {
            return true;
        }
        if (stack.getItem() instanceof IItemSerum) {
            return ((IItemSerum) stack.getItem()).getSpeciesRoot(stack) == gene.getSpeciesRoot();
        }
        return isGeneAcceptor(stack);
    }

    public static IGene getGene(ItemStack stack, int chromosome) {
        if (stack.getItem() instanceof IItemSerum) {
            return ((IItemSerum) stack.getItem()).getGene(stack, chromosome);
        }
        return null;
    }

    public static ItemStack addGene(ItemStack stack, IGene gene) {
        if (stack.getItem() instanceof IItemSerum) {
            ((IItemSerum) stack.getItem()).addGene(stack, gene);
        }
        if (stack.getItem() == Genetics.itemGenetics && stack.getItemDamage() == GeneticsItems.EmptySerum.ordinal()) {
            return ItemSerum.create(gene);
        }
        if (stack.getItem() == Genetics.itemGenetics && stack.getItemDamage() == GeneticsItems.EmptyGenome.ordinal()) {
            return ItemSerumArray.create(gene);
        }
        return stack;
    }

    public static IGene[] getGenes(ItemStack serum) {
        if (serum.getItem() instanceof IItemSerum) {
            return ((IItemSerum) serum.getItem()).getGenes(serum);
        }
        if (serum.getItem() == Genetics.itemSequencer) {
            return new IGene[] {new SequencerItem(serum).gene};
        }
        return new IGene[0];
    }

    public static int getCharges(ItemStack serum) {
        return ((IItemChargable) serum.getItem()).getCharges(serum);
    }
}
