package binnie.genetics.api;

import binnie.core.api.genetics.IGene;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public interface IItemSerum extends IItemChargeable {
	IGene[] getGenes(ItemStack stack);

	@Nullable
	IGene getGene(ItemStack stack, int chromosome);

	ItemStack addGene(ItemStack stack, IGene gene);
}
