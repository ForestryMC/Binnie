package binnie.extratrees.machines.designer;

import net.minecraft.item.ItemStack;

import binnie.botany.Botany;
import binnie.botany.ceramic.CeramicDesignSystem;
import binnie.core.resource.IBinnieTexture;
import binnie.core.util.I18N;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.api.IDesign;
import binnie.extratrees.api.IDesignMaterial;
import binnie.extratrees.api.IDesignSystem;
import binnie.extratrees.carpentry.BlockDesign;
import binnie.extratrees.carpentry.DesignSystem;
import binnie.extratrees.carpentry.EnumDesign;
import binnie.extratrees.carpentry.ModuleCarpentry;
import binnie.extratrees.core.ExtraTreeTexture;

public enum DesignerType {
	Woodworker("woodworker", ExtraTreeTexture.CARPENTER),
	Panelworker("panelworker", ExtraTreeTexture.PANELER),
	GlassWorker("glassworker", ExtraTreeTexture.PANELER),
	// TODO why is this the Paneler texture?
	Tileworker("tileworker", ExtraTreeTexture.TILEWORKER);

	public String name;
	public IBinnieTexture texture;

	DesignerType(final String name, final IBinnieTexture texture) {
		this.name = name;
		this.texture = texture;
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
				return ExtraTrees.carpentry().blockStained;
			}
			case Panelworker: {
				return ExtraTrees.carpentry().blockPanel;
			}
			case Tileworker: {
				return Botany.gardening().ceramicTile;
			}
			default: {
				return ExtraTrees.carpentry().blockCarpentry;
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
}
