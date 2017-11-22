package binnie.core.util;

import javax.annotation.Nullable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.IllegalFormatException;
import java.util.Locale;

import binnie.core.ModId;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.Language;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class I18N {
	@Nullable
	private static NumberFormat percentFormat;
	@Nullable
	private static NumberFormat numberFormat;

	public static void onResourceReload() {
		percentFormat = null;
		numberFormat = null;
	}

	@SideOnly(Side.CLIENT)
	public static NumberFormat getPercentFormat() {
		if (percentFormat == null) {
			percentFormat = DecimalFormat.getPercentInstance(getLocale());
		}
		return percentFormat;
	}

	@SideOnly(Side.CLIENT)
	public static NumberFormat getNumberFormat() {
		if (numberFormat == null) {
			numberFormat = DecimalFormat.getNumberInstance(I18N.getLocale());
		}
		return numberFormat;
	}

	public static String localiseOrBlank(String key) {
		String trans = localise(key);
		return trans.equals(key) ? "" : trans;
	}
	
	@SuppressWarnings("deprecation")
	public static String localise(String key) {
		if (I18n.canTranslate(key)) {
			return I18n.translateToLocal(key);
		} else {
			return I18n.translateToFallback(key);
		}
	}

	public static String localise(ModId modId, String path, Object... format) {
		return localise(modId.getDomain() + "." + path, format);
	}

	public static String localise(ResourceLocation key) {
		return localise(key.getResourceDomain() + "." + key.getResourcePath());
	}
	
	@SuppressWarnings("deprecation")
	public static boolean canLocalise(String key) {
		return I18n.canTranslate(key);
	}
	
	public static String localise(String key, Object... format) {
		String s = localise(key);
		try {
			return String.format(s, format);
		} catch (IllegalFormatException e) {
			String errorMessage = "Format error: " + s;
			Log.error(errorMessage, e);
			return errorMessage;
		}
	}

	public static String localise(ResourceLocation key, Object... format) {
		return localise(key.getResourceDomain() + "." + key.getResourcePath(), format);
	}

	@SuppressWarnings("ConstantConditions")
	@SideOnly(Side.CLIENT)
	public static Locale getLocale() {
		Minecraft minecraft = Minecraft.getMinecraft();
		if (minecraft != null) {
			LanguageManager languageManager = minecraft.getLanguageManager();
			if (languageManager != null) {
				Language currentLanguage = languageManager.getCurrentLanguage();
				if (currentLanguage != null) {
					return currentLanguage.getJavaLocale();
				}
			}
		}
		return Locale.getDefault();
	}
}
