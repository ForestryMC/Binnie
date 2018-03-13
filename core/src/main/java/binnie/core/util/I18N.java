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
import net.minecraft.client.resources.I18n;
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

	@SideOnly(Side.CLIENT)
	public static String localiseOrBlank(String key) {
		String trans = localise(key);
		return trans.equals(key) ? "" : trans;
	}

	@SideOnly(Side.CLIENT)
	public static String localise(String key) {
		if (I18n.hasKey(key)) {
			return I18n.format(key);
		} else {
			return key;
		}
	}

	@SideOnly(Side.CLIENT)
	public static String localise(ModId modId, String path, Object... format) {
		return localise(modId.getDomain() + "." + path, format);
	}

	@SideOnly(Side.CLIENT)
	public static String localise(ResourceLocation key) {
		return localise(key.getResourceDomain() + "." + key.getResourcePath());
	}

	@SideOnly(Side.CLIENT)
	public static boolean canLocalise(String key) {
		return I18n.hasKey(key);
	}

	@SideOnly(Side.CLIENT)
	public static String localise(String key, Object... format) {
		try {
			return I18n.format(key, format);
		} catch (IllegalFormatException e) {
			String errorMessage = "Format error: " + key;
			Log.error(errorMessage, e);
			return errorMessage;
		}
	}

	@SideOnly(Side.CLIENT)
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
