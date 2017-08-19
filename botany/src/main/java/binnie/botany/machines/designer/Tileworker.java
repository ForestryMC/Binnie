package binnie.botany.machines.designer;

import binnie.design.DesignHelper;
import binnie.design.EnumDesign;
import binnie.design.api.IDesignerType;
import binnie.design.blocks.BlockDesign;
import net.minecraft.item.ItemStack;

import binnie.botany.ceramic.CeramicDesignSystem;
import binnie.botany.modules.ModuleCeramic;
import binnie.core.util.I18N;
import binnie.design.api.IDesign;
import binnie.design.api.IDesignMaterial;
import binnie.design.api.IDesignSystem;

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
		final ItemStack stack = DesignHelper.getItemStack(this.getBlock(), type1, type2, design);
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
		return I18N.localise("botany.machine.machine.designer.material.tile");
	}

	@Override
	public String getName() {
		return name;
	}
}
