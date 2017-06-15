package binnie.genetics.genetics;

import javax.annotation.Nullable;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import binnie.genetics.Genetics;
import binnie.genetics.api.IGene;
import binnie.genetics.api.IItemChargeable;
import binnie.genetics.api.IItemSerum;
import binnie.genetics.item.GeneticsItems;
import binnie.genetics.item.ItemSerum;
import binnie.genetics.item.ItemSerumArray;

public class Engineering {
	public static boolean isGeneAcceptor(final ItemStack stack) {
		if (stack.isEmpty()) {
			return false;
		}
		Item item = stack.getItem();
		if (item instanceof IItemSerum) {
			return ((IItemSerum) item).getCharges(stack) == 0;
		}
		int metadata = stack.getMetadata();
		return item == Genetics.items().getItemGenetics() && (metadata == GeneticsItems.EMPTY_SERUM.ordinal() || metadata == GeneticsItems.EMPTY_GENOME.ordinal());
	}

	public static boolean canAcceptGene(final ItemStack stack, final IGene gene) {
		Item item = stack.getItem();
		if (item instanceof ItemSerum) {
			return true;
		}
		if (item instanceof IItemSerum) {
			return ((IItemSerum) item).getSpeciesRoot(stack) == gene.getSpeciesRoot();
		}
		return isGeneAcceptor(stack);
	}

	@Nullable
	public static IGene getGene(final ItemStack stack, final int chromosome) {
		if (stack.getItem() instanceof IItemSerum) {
			return ((IItemSerum) stack.getItem()).getGene(stack, chromosome);
		}
		return null;
	}

	public static ItemStack addGene(final ItemStack stack, final IGene gene) {
		Item item = stack.getItem();
		int metadata = stack.getMetadata();
		if (item instanceof IItemSerum) {
			((IItemSerum) item).addGene(stack, gene);
		}
		if (item == Genetics.items().getItemGenetics()) {
			if (metadata == GeneticsItems.EMPTY_SERUM.ordinal()) {
				return ItemSerum.create(gene);
			} else if (metadata == GeneticsItems.EMPTY_GENOME.ordinal()) {
				return ItemSerumArray.create(gene);
			}
		}
		return stack;
	}

	public static IGene[] getGenes(final ItemStack serum) {
		if (!serum.isEmpty()) {
			Item item = serum.getItem();
			if (item instanceof IItemSerum) {
				return ((IItemSerum) item).getGenes(serum);
			}
			if (item == Genetics.items().itemSequencer) {
				SequencerItem sequencerItem = SequencerItem.create(serum);
				if (sequencerItem != null) {
					return new IGene[]{sequencerItem.getGene()};
				}
			}
		}
		return new IGene[0];
	}

	public static int getCharges(final ItemStack serum) {
		return ((IItemChargeable) serum.getItem()).getCharges(serum);
	}
}
