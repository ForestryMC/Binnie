package binnie.botany.ceramic;

import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;

import binnie.botany.genetics.EnumFlowerColor;
import binnie.botany.modules.ModuleCeramic;
import binnie.core.block.TileEntityMetadata;
import binnie.extratrees.api.IDesignMaterial;

public class CeramicColor implements IDesignMaterial {
	private final static Map<EnumFlowerColor, CeramicColor> map = new LinkedHashMap<>();

	static {
		for (EnumFlowerColor c : EnumFlowerColor.values()) {
			CeramicColor.map.put(c, new CeramicColor(c));
		}
	}

	EnumFlowerColor color;

	CeramicColor(EnumFlowerColor color) {
		this.color = color;
	}

	public static CeramicColor get(EnumFlowerColor c) {
		return CeramicColor.map.get(c);
	}

	@Override
	public ItemStack getStack() {
		return TileEntityMetadata.getItemStack(ModuleCeramic.ceramic, color.ordinal());
	}

	@Override
	public ItemStack getStack(boolean fireproof) {
		return getStack();
	}

	@Override
	public String getDesignMaterialName() {
		return color.getName();
	}

	@Override
	public int getColour() {
		return color.getFlowerColorAllele().getColor(false);
	}
}
