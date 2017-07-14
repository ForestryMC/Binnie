package binnie.botany.api;

import binnie.core.util.I18N;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nullable;
import java.util.Locale;

public enum EnumSoilType implements IStringSerializable {
	SOIL(TextFormatting.DARK_GRAY),
	LOAM(TextFormatting.GOLD),
	FLOWERBED(TextFormatting.LIGHT_PURPLE);

	@Nullable
	final TextFormatting color;

	EnumSoilType(@Nullable TextFormatting color) {
		this.color = color;
	}

	@Override
	public String getName() {
		return this.name().toLowerCase(Locale.ENGLISH);
	}

	public String getTranslated(boolean withColor) {
		return (withColor && color != null ? color : "") + I18N.localise("botany.soil." + getName());
	}
}
