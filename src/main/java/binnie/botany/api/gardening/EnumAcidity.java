package binnie.botany.api.gardening;

import javax.annotation.Nullable;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.TextFormatting;

import binnie.core.util.I18N;

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
		return name().toLowerCase();
	}

	public String getLocalisedName(boolean withColor) {
		String localisedName = I18N.localise("botany.ph." + getName());
		if (withColor && color != null) {
			localisedName = color + localisedName;
		}
		return localisedName;
	}

	public static EnumAcidity getFromValue(float rawAcidity) {
		if(rawAcidity <= -1.0f){
			return EnumAcidity.ACID;
		}else if(rawAcidity >= 1.0f){
			return EnumAcidity.ALKALINE;
		}else{
			return EnumAcidity.NEUTRAL;
		}
	}
}
