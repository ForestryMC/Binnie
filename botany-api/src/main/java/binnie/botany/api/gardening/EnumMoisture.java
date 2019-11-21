package binnie.botany.api.gardening;

import binnie.botany.api.IBotanyColored;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nullable;

public enum EnumMoisture implements IBotanyColored {
	DRY(TextFormatting.YELLOW),
	NORMAL(null),
	DAMP(TextFormatting.DARK_BLUE);

	@Nullable
	private final TextFormatting color;

	EnumMoisture(@Nullable TextFormatting color) {
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

	public static EnumMoisture getFromValue(float rawMoisture) {
		if (rawMoisture <= -1.0f) {
			return EnumMoisture.DRY;
		} else if (rawMoisture >= 1.0f) {
			return EnumMoisture.DAMP;
		} else {
			return EnumMoisture.NORMAL;
		}
	}
}
