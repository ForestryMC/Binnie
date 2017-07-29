package binnie.genetics.api;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;

public interface IItemSerum extends IItemChargeable {
	IGene[] getGenes(ItemStack stack);

	@Nullable
	IGene getGene(ItemStack stack, int chromosome);

	ItemStack addGene(ItemStack stack, IGene gene);
}
