package binnie.botany.api;

import java.util.Locale;

import net.minecraft.util.IStringSerializable;

public enum EnumSoilType implements IStringSerializable {
    SOIL,
    LOAM,
    FLOWERBED;

	@Override
	public String getName() {
		return this.name().toLowerCase(Locale.ENGLISH);
	}
}
