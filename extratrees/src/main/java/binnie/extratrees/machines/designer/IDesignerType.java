package binnie.extratrees.machines.designer;

import binnie.extratrees.api.IDesign;
import binnie.extratrees.api.IDesignMaterial;
import binnie.extratrees.api.IDesignSystem;
import net.minecraft.item.ItemStack;

public interface IDesignerType {
	IDesignSystem getSystem();
	ItemStack getBlock(IDesignMaterial type1, IDesignMaterial type2, IDesign design);
	String getMaterialTooltip();
	ItemStack getDisplayStack(final IDesign design);
	String getName();
}
