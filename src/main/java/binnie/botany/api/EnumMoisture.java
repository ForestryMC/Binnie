package binnie.botany.api;

import javax.annotation.Nullable;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.TextFormatting;

import binnie.core.util.I18N;

public enum EnumMoisture implements IStringSerializable {
	DRY(TextFormatting.YELLOW),
	NORMAL(null),
	DAMP(TextFormatting.DARK_BLUE);

	@Nullable
	final TextFormatting color;

	EnumMoisture(@Nullable TextFormatting color) {
		this.color = color;
	}

	@Override
	public String getName() {
		return name().toLowerCase();
	}

	public String getLocalisedName(boolean withColor) {
		String localisedName = I18N.localise("botany.moisture." + getName());
		if (withColor && color != null) {
			localisedName += color;
		}
		return localisedName;
	}
}
