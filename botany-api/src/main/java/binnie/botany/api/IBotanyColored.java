package binnie.botany.api;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nullable;

public interface IBotanyColored extends IStringSerializable {
    @Nullable
    TextFormatting getColor();
}
