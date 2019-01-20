package binnie.extratrees.machines.designer;

import net.minecraft.item.ItemStack;

import binnie.core.util.I18N;
import binnie.design.DesignHelper;
import binnie.design.EnumDesign;
import binnie.design.api.IDesign;
import binnie.design.api.IDesignMaterial;
import binnie.design.api.IDesignSystem;
import binnie.design.api.IDesignerType;
import binnie.design.blocks.BlockDesign;
import binnie.extratrees.carpentry.DesignSystem;
import binnie.extratrees.modules.features.ExtraTreesBlocks;

public enum DesignerType implements IDesignerType {
	Woodworker("woodworker"),
	Panelworker("panelworker"),
	GlassWorker("glassworker");

	private final String name;

	DesignerType(String name) {
		this.name = name;
	}

	@Override
	public IDesignSystem getSystem() {
		switch (this) {
			case GlassWorker: {
				return DesignSystem.Glass;
			}
			default: {
				return DesignSystem.Wood;
			}
		}
	}

	@Override
	public ItemStack getBlock(IDesignMaterial type1, IDesignMaterial type2, IDesign design) {
		int stackSize = 2;
		if (design == EnumDesign.Blank) {
			type2 = type1;
			stackSize = 1;
		}
		ItemStack stack = DesignHelper.getItemStack(this.getBlock(), type1, type2, design);
		stack.setCount(stackSize);
		return stack;
	}

	private BlockDesign getBlock() {
		switch (this) {
			case GlassWorker: {
				return ExtraTreesBlocks.STAINED.cast();
			}
			case Panelworker: {
				return ExtraTreesBlocks.PANEL.cast();
			}
			default: {
				return ExtraTreesBlocks.CARPENTRY.cast();
			}
		}
	}

	@Override
	public ItemStack getDisplayStack(IDesign design) {
		return this.getBlock(this.getSystem().getDefaultMaterial(), this.getSystem().getDefaultMaterial2(), design);
	}

	@Override
	public String getMaterialTooltip() {
		switch (this) {
			case GlassWorker: {
				return I18N.localise("extratrees.machine.designer.material.glass");
			}
			case Panelworker: {
				return I18N.localise("extratrees.machine.designer.material.panel");
			}
			case Woodworker: {
				return I18N.localise("extratrees.machine.designer.material.wood");
			}
			default: {
				return "";
			}
		}
	}

	@Override
	public String getName() {
		return name;
	}
}
