package binnie.botany.api;

import javax.annotation.Nullable;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;

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
		String localisedName = I18n.translateToLocal("botany.ph." + getName());
		if(withColor && color != null){
			localisedName +=color;
		}
		return localisedName;
	}
}
