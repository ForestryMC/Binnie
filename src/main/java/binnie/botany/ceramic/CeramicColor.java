// 
// Decompiled by Procyon v0.5.30
// 

package binnie.botany.ceramic;

import java.util.LinkedHashMap;
import binnie.core.block.TileEntityMetadata;
import binnie.botany.Botany;
import net.minecraft.item.ItemStack;
import java.util.Map;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.extratrees.api.IDesignMaterial;

public class CeramicColor implements IDesignMaterial
{
	EnumFlowerColor color;
	static Map<EnumFlowerColor, CeramicColor> map;

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
	public String getName() {
		return this.color.getName();
	}

	@Override
	public int getColour() {
		return this.color.getColor(false);
	}

	static {
		CeramicColor.map = new LinkedHashMap<EnumFlowerColor, CeramicColor>();
		for (final EnumFlowerColor c : EnumFlowerColor.values()) {
			CeramicColor.map.put(c, new CeramicColor(c));
		}
	}
}
