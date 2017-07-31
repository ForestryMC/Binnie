package binnie.extratrees.machines;

import net.minecraft.item.ItemStack;

import binnie.botany.Botany;
import binnie.botany.ceramic.CeramicDesignSystem;
import binnie.core.resource.IBinnieTexture;
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
	Woodworker("woodworker", ExtraTreeTexture.Carpenter),
	Panelworker("panelworker", ExtraTreeTexture.Paneler),
	GlassWorker("glassworker", ExtraTreeTexture.Paneler),
	// TODO why is this the Paneler texture?
	Tileworker("tileworker", ExtraTreeTexture.Tileworker);

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
				return "Glass";
			}
			case Panelworker: {
				return "Wooden Plank";
			}
			case Tileworker: {
				return "Ceramic Block";
			}
			case Woodworker: {
				return "Wooden Plank";
			}
			default: {
				return "";
			}
		}
	}
}
