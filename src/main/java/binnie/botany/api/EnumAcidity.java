package binnie.botany.api;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;

import javax.annotation.Nullable;

public enum EnumAcidity implements IStringSerializable {
	Acid(TextFormatting.RED),
	Neutral(null),
	Alkaline(TextFormatting.AQUA);

	@Nullable
	final TextFormatting color;

	EnumAcidity(@Nullable TextFormatting color) {
		this.color = color;
	}

	@Override
	public String getName() {
		return this.name().toLowerCase();
	}

	public String getTranslated(boolean withColor) {
		return (withColor && color != null ? color : "") + I18n.translateToLocal("botany.ph." + getName());
	}
}
