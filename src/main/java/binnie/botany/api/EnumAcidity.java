package binnie.botany.api;

import binnie.core.util.I18N;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nullable;

public enum EnumAcidity implements IStringSerializable {
	ACID(TextFormatting.RED),
	NEUTRAL(null),
	ALKALINE(TextFormatting.AQUA);

	@Nullable
	TextFormatting color;

	EnumAcidity(@Nullable TextFormatting color) {
		this.color = color;
	}

	@Override
	public String getName() {
		return this.name().toLowerCase();
	}

	public String getLocalisedName(boolean withColor) {
		String localisedName = I18N.localise("botany.ph." + getName());
		if (withColor && color != null) {
			localisedName += color;
		}
		return localisedName;
	}
}
