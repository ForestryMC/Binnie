package binnie.botany.api.gardening;

import javax.annotation.Nullable;

import net.minecraft.util.text.TextFormatting;

import binnie.botany.api.IBotanyColored;

public enum EnumAcidity implements IBotanyColored {
	ACID(TextFormatting.RED),
	NEUTRAL(null),
	ALKALINE(TextFormatting.AQUA);

	@Nullable
	private final TextFormatting color;

	EnumAcidity(@Nullable TextFormatting color) {
		this.color = color;
	}

	@Override
	public String getName() {
		return name().toLowerCase();
	}

	@Override
	public TextFormatting getColor() {
		return color;
	}

	public static EnumAcidity getFromValue(float rawAcidity) {
		if (rawAcidity <= -1.0f) {
			return EnumAcidity.ACID;
		} else if (rawAcidity >= 1.0f) {
			return EnumAcidity.ALKALINE;
		} else {
			return EnumAcidity.NEUTRAL;
		}
	}
}
