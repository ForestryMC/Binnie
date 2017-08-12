package binnie.botany.machines.designer;

import binnie.extratrees.machines.designer.IDesignerType;
import net.minecraft.item.ItemStack;

import binnie.botany.ceramic.CeramicDesignSystem;
import binnie.botany.modules.ModuleCeramic;
import binnie.core.util.I18N;
import binnie.extratrees.api.IDesign;
import binnie.extratrees.api.IDesignMaterial;
import binnie.extratrees.api.IDesignSystem;
import binnie.extratrees.carpentry.BlockDesign;
import binnie.extratrees.carpentry.EnumDesign;
import binnie.extratrees.carpentry.ModuleCarpentry;

public class Tileworker implements IDesignerType {
	private static final String name = "tileworker";

	@Override
	public IDesignSystem getSystem() {
		return CeramicDesignSystem.instance;
	}

	@Override
	public ItemStack getBlock(final IDesignMaterial type1, IDesignMaterial type2, final IDesign design) {
		int stackSize = 2;
		if (design == EnumDesign.Blank) {
			type2 = type1;
			stackSize = 1;
		}
		final ItemStack stack = ModuleCarpentry.getItemStack(this.getBlock(), type1, type2, design);
		stack.setCount(stackSize);
		return stack;
	}

	private BlockDesign getBlock() {
		return ModuleCeramic.ceramicTile;
	}

	@Override
	public ItemStack getDisplayStack(final IDesign design) {
		return this.getBlock(this.getSystem().getDefaultMaterial(), this.getSystem().getDefaultMaterial2(), design);
	}

	@Override
	public String getMaterialTooltip() {
		return I18N.localise("extratrees.machine.machine.designer.material.tile");
	}

	@Override
	public String getName() {
		return name;
	}
}
