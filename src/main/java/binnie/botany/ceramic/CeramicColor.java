package binnie.botany.ceramic;

import binnie.botany.Botany;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.core.block.TileEntityMetadata;
import binnie.extratrees.api.IDesignMaterial;
import net.minecraft.item.ItemStack;

import java.util.LinkedHashMap;
import java.util.Map;

public class CeramicColor implements IDesignMaterial {
	EnumFlowerColor color;
	private final static Map<EnumFlowerColor, CeramicColor> map = new LinkedHashMap<>();

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
	public String getName() {
		return this.color.getName();
	}

	@Override
	public int getColour() {
		return this.color.getColor(false);
	}

	static {
		for (final EnumFlowerColor c : EnumFlowerColor.values()) {
			CeramicColor.map.put(c, new CeramicColor(c));
		}
	}
}
