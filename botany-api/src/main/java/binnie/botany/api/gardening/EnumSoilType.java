package binnie.botany.api.gardening;

import binnie.botany.api.IBotanyColored;
import net.minecraft.util.text.TextFormatting;

public enum EnumSoilType implements IBotanyColored {
	SOIL(TextFormatting.DARK_GRAY),
	LOAM(TextFormatting.GOLD),
	FLOWERBED(TextFormatting.LIGHT_PURPLE);

	private final TextFormatting color;

	EnumSoilType(TextFormatting color) {
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

	@Override
	public String keyGroup() {
		return "soil";
	}
}
