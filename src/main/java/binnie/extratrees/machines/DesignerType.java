package binnie.extratrees.machines;

import binnie.botany.Botany;
import binnie.botany.ceramic.CeramicDesignSystem;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.api.IDesign;
import binnie.extratrees.api.IDesignMaterial;
import binnie.extratrees.api.IDesignSystem;
import binnie.extratrees.carpentry.BlockDesign;
import binnie.extratrees.carpentry.DesignSystem;
import binnie.extratrees.carpentry.EnumDesign;
import binnie.extratrees.carpentry.ModuleCarpentry;
import binnie.extratrees.core.ExtraTreeTexture;
import net.minecraft.item.ItemStack;

public enum DesignerType {
	Woodworker("woodworker", ExtraTreeTexture.carpenterTexture),
	Panelworker("panelworker", ExtraTreeTexture.panelerTexture),
	GlassWorker("glassworker", ExtraTreeTexture.panelerTexture),
	Tileworker("tileworker", ExtraTreeTexture.tileworkerTexture);

	public String name;
	public String texture;

	DesignerType(String name, String texture) {
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

	public ItemStack getBlock(IDesignMaterial type1, IDesignMaterial type2, IDesign design) {
		int stackSize = 2;
		if (design == EnumDesign.Blank) {
			type2 = type1;
			stackSize = 1;
		}

		ItemStack stack = ModuleCarpentry.getItemStack(getBlock(), type1, type2, design);
		stack.stackSize = stackSize;
		return stack;
	}

	private BlockDesign getBlock() {
		switch (this) {
			case GlassWorker:
				return ExtraTrees.blockStained;

			case Panelworker:
				return ExtraTrees.blockPanel;

			case Tileworker:
				return Botany.ceramicTile;
		}
		return ExtraTrees.blockCarpentry;
	}

	public ItemStack getDisplayStack(IDesign design) {
		return getBlock(getSystem().getDefaultMaterial(), getSystem().getDefaultMaterial2(), design);
	}

	public String getMaterialTooltip() {
		switch (this) {
			case GlassWorker:
				return "Glass";

			case Panelworker:
				return "Wooden Plank";

			case Tileworker:
				return "Ceramic Block";

			case Woodworker:
				return "Wooden Plank";
		}
		return "";
	}
}
