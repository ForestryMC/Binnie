package binnie.core.proxy;

import binnie.core.ModId;
import binnie.core.util.Log;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.StringUtils;

import java.util.IllegalFormatException;

@SideOnly(Side.SERVER)
public class I18NServer implements I18NProxy {

    public String localiseOrBlank(String key) {
        String trans = localise(key);
        return trans.equals(key) ? StringUtils.EMPTY : trans;
    }

    public String localise(String key) {
        if (I18n.canTranslate(key)) {
            return I18n.translateToLocal(key);
        } else {
            return I18n.translateToFallback(key);
        }
    }

    public String localise(ModId modId, String path, Object... format) {
        return localise(modId.getDomain() + '.' + path, format);
    }

    public String localise(ResourceLocation key) {
        return this.localise(key.getResourceDomain() + '.' + key.getResourcePath());
    }

    public boolean canLocalise(String key) {
        return I18n.canTranslate(key);
    }

    public String localise(String key, Object... format) {
        String s = localise(key);
        try {
            return String.format(s, format);
        } catch (IllegalFormatException e) {
            String errorMessage = "Format error: " + s;
            Log.error(errorMessage, e);
            return errorMessage;
        }
    }

    public String localise(ResourceLocation key, Object... format) {
        return this.localise(key.getResourceDomain() + '.' + key.getResourcePath(), format);
    }
}
