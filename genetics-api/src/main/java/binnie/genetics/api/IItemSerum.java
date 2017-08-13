package binnie.genetics.api;

import javax.annotation.Nullable;

import binnie.core.api.genetics.IGene;
import net.minecraft.item.ItemStack;

public interface IItemSerum extends IItemChargeable {
	IGene[] getGenes(ItemStack stack);

	@Nullable
	IGene getGene(ItemStack stack, int chromosome);

	ItemStack addGene(ItemStack stack, IGene gene);
}
