package binnie.botany.ceramic;

import binnie.botany.Botany;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.core.block.TileEntityMetadata;
import binnie.extratrees.api.IDesignMaterial;
import net.minecraft.item.ItemStack;

import java.util.LinkedHashMap;
import java.util.Map;

public class CeramicColor implements IDesignMaterial {
	static Map<EnumFlowerColor, CeramicColor> map;

	static {
		CeramicColor.map = new LinkedHashMap<>();
		for (EnumFlowerColor c : EnumFlowerColor.values()) {
			CeramicColor.map.put(c, new CeramicColor(c));
		}
	}

	protected EnumFlowerColor color;

	CeramicColor(EnumFlowerColor color) {
		this.color = color;
	}

	public static CeramicColor get(EnumFlowerColor c) {
		return CeramicColor.map.get(c);
	}

	@Override
	public ItemStack getStack() {
		return TileEntityMetadata.getItemStack(Botany.ceramic, color.ordinal());
	}

	@Override
	public String getName() {
		return color.getName();
	}

	@Override
	public int getColour() {
		return color.getColor(false);
	}
}
