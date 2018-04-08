package binnie.core.gui.resource.stylesheet;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.Constants;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.resource.textures.Texture;

@SideOnly(Side.CLIENT)
public class StyleSheetManager implements IResourceManagerReloadListener {

	public static final String DEFAULT_SHEET = "default";
	public static final String PUNNETT_SHEET = "punnett";

	private static Map<String, StyleSheet> sheets;
	private static Map<String, ResourceLocation> sheetLocations;

	public StyleSheetManager() {
		sheets = new HashMap<>();
		sheetLocations = new HashMap<>();
		CraftGUI.styleSheetManager = this;
		registerLocation(DEFAULT_SHEET, new ResourceLocation(Constants.CORE_MOD_ID, "gui/stylesheet.json"));
		registerLocation(PUNNETT_SHEET, new ResourceLocation(Constants.CORE_MOD_ID, "gui/punnett_stylesheet.json"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onResourceManagerReload(final IResourceManager manager) {
		sheets.clear();
		try {
			for(Map.Entry<String, ResourceLocation> entry : sheetLocations.entrySet()){
				final ResourceLocation location = entry.getValue();
				final StyleSheet styleSheet = StyleSheetParser.parseSheet(manager, location);
				sheets.put(entry.getKey(), styleSheet);
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to load default stylesheet for Binnie's Mods.", e);
		}
	}

	@SideOnly(Side.CLIENT)
	public static Texture getDefaultTexture(Object key) {
		final StyleSheet styleSheet = getDefaultSheet();
		return styleSheet.getTexture(key);
	}

	public static StyleSheet getDefaultSheet() {
		return getSheet(DEFAULT_SHEET);
	}

	public static StyleSheet getSheet(String name){
		return sheets.get(name);
	}

	public static void registerLocation(String name, ResourceLocation location){
		sheetLocations.put(name, location);
	}
}
