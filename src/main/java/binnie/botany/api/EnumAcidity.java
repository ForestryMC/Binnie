package binnie.botany.api;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;

public enum EnumAcidity implements IStringSerializable {
    Acid(TextFormatting.RED),
    Neutral,
    Alkaline(TextFormatting.AQUA);
	
	final TextFormatting color;
	
	EnumAcidity() {
		this(null);
	}
	
	EnumAcidity(TextFormatting color) {
		this.color = color;
	}

    @Override
    public String getName() {
        return this.name().toLowerCase();
    }
    
    public String getTranslated(boolean withColor){
    	return (withColor && color != null ? color : "") + I18n.translateToLocal("botany.ph." + getName());
    }
}
