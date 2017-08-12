package binnie.genetics.integration.jei.inoculator;

import net.minecraft.item.ItemStack;

public class SplicerRecipeWrapper extends InoculatorRecipeWrapper {
	public SplicerRecipeWrapper(ItemStack inputSerum, ItemStack wildcardTarget) {
		super(inputSerum, wildcardTarget, true);
	}
}
