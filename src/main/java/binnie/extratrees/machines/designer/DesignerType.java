package binnie.extratrees.machines.designer;

import net.minecraft.item.ItemStack;

import binnie.botany.ceramic.CeramicDesignSystem;
import binnie.botany.modules.ModuleCeramic;
import binnie.core.util.I18N;
import binnie.extratrees.api.IDesign;
import binnie.extratrees.api.IDesignMaterial;
import binnie.extratrees.api.IDesignSystem;
import binnie.extratrees.carpentry.BlockDesign;
import binnie.extratrees.carpentry.DesignSystem;
import binnie.extratrees.carpentry.EnumDesign;
import binnie.extratrees.carpentry.ModuleCarpentry;

public enum DesignerType {
	Woodworker("woodworker"),
	Panelworker("panelworker"),
	GlassWorker("glassworker"),
	Tileworker("tileworker");

	private final String name;

	DesignerType(final String name) {
		this.name = name;
	}

	public IDesignSystem getSystem() {
		switch (this) {
			case GlassWorker: {
				return DesignSystem.Glass;
			}
			case Tileworker: {
				return CeramicDesignSystem.instance;
			}
			default: {
				return DesignSystem.Wood;
			}
		}
	}

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
		switch (this) {
			case GlassWorker: {
				return ModuleCarpentry.blockStained;
			}
			case Panelworker: {
				return ModuleCarpentry.blockPanel;
			}
			case Tileworker: {
				return ModuleCeramic.ceramicTile;
			}
			default: {
				return ModuleCarpentry.blockCarpentry;
			}
		}
	}

	public ItemStack getDisplayStack(final IDesign design) {
		return this.getBlock(this.getSystem().getDefaultMaterial(), this.getSystem().getDefaultMaterial2(), design);
	}

	public String getMaterialTooltip() {
		switch (this) {
			case GlassWorker: {
				return I18N.localise("extratrees.machine.machine.designer.material.glass");
			}
			case Panelworker: {
				return I18N.localise("extratrees.machine.machine.designer.material.panel");
			}
			case Tileworker: {
				return I18N.localise("extratrees.machine.machine.designer.material.tile");
			}
			case Woodworker: {
				return I18N.localise("extratrees.machine.machine.designer.material.wood");
			}
			default: {
				return "";
			}
		}
	}

	public String getName() {
		return name;
	}
}
