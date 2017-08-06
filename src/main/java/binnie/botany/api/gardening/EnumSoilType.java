package binnie.botany.api.gardening;

import java.util.Locale;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.TextFormatting;

import binnie.core.util.I18N;

public enum EnumSoilType implements IStringSerializable {
	SOIL(TextFormatting.DARK_GRAY),
	LOAM(TextFormatting.GOLD),
	FLOWERBED(TextFormatting.LIGHT_PURPLE);

	private final TextFormatting color;

	EnumSoilType(TextFormatting color) {
		this.color = color;
	}

	@Override
	public String getName() {
		return name().toLowerCase(Locale.ENGLISH);
	}

	public String getTranslated() {
		return color + I18N.localise("botany.soil." + getName());
	}
}
