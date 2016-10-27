package binnie.botany.api;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;

public enum EnumMoisture implements IStringSerializable {
	Dry(TextFormatting.YELLOW),
	Normal,
	Damp(TextFormatting.DARK_BLUE);

	final TextFormatting color;

	EnumMoisture() {
		this(null);
	}

	EnumMoisture(TextFormatting color) {
		this.color = color;
	}

	@Override
	public String getName() {
		return name().toLowerCase();
	}

	public String getTranslated(boolean withColor) {
		return (withColor && color != null ? color : "") + I18n.translateToLocal("botany.moisture." + getName());
	}
}
