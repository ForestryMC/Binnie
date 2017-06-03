package binnie.botany.ceramic;

import binnie.botany.Botany;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.core.block.TileEntityMetadata;
import binnie.extratrees.api.IDesignMaterial;
import net.minecraft.item.ItemStack;

import java.util.LinkedHashMap;
import java.util.Map;

public class CeramicColor implements IDesignMaterial {
	private final static Map<EnumFlowerColor, CeramicColor> map = new LinkedHashMap<>();

	static {
		for (final EnumFlowerColor c : EnumFlowerColor.values()) {
			CeramicColor.map.put(c, new CeramicColor(c));
		}
	}

	EnumFlowerColor color;

	CeramicColor(final EnumFlowerColor color) {
		this.color = color;
	}

	public static CeramicColor get(final EnumFlowerColor c) {
		return CeramicColor.map.get(c);
	}

	@Override
	public ItemStack getStack() {
		return TileEntityMetadata.getItemStack(Botany.ceramic, this.color.ordinal());
	}

	@Override
	public ItemStack getStack(boolean fireproof) {
		return getStack();
	}

	@Override
	public String getDesignMaterialName() {
		return this.color.getName();
	}

	@Override
	public int getColour() {
		return this.color.getFlowerColorAllele().getColor(false);
	}
}
