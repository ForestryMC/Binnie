package binnie.botany.api;

import javax.annotation.Nullable;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.TextFormatting;

public interface IBotanyColored extends IStringSerializable {
	@Nullable
	TextFormatting getColor();
}
