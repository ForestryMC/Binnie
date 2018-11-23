package binnie.design.api;

import net.minecraft.item.ItemStack;

public interface IDesignerType {
	IDesignSystem getSystem();

	ItemStack getBlock(IDesignMaterial type1, IDesignMaterial type2, IDesign design);

	String getMaterialTooltip();

	ItemStack getDisplayStack(IDesign design);

	String getName();
}
