package binnie.craftgui.resource.minecraft;

import binnie.core.resource.IBinnieTexture;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.geometry.Area;
import binnie.craftgui.core.geometry.Border;
import binnie.craftgui.resource.Texture;
import com.google.common.base.Charsets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class CraftGUIResourceManager implements IResourceManagerReloadListener {
	private Map<String, ParsedTextureSheet> textureSheets;
	private Map<String, Texture> textures;

	public CraftGUIResourceManager() {
		this.textureSheets = new HashMap<>();
		this.textures = new HashMap<>();
		CraftGUI.resourceManager = this;
	}

	@Override
	public void onResourceManagerReload(final IResourceManager manager) {
		this.textureSheets.clear();
		try {
			final IResource res = manager.getResource(new ResourceLocation("binniecore", "gui/stylesheet.json"));
			JsonObject jsonobject = null;
			BufferedReader bufferedreader = null;
			try {
				bufferedreader = new BufferedReader(new InputStreamReader(res.getInputStream(), Charsets.UTF_8));
				jsonobject = new JsonParser().parse(bufferedreader).getAsJsonObject();
				for (final JsonElement el : jsonobject.get("texture-sheets").getAsJsonArray()) {
					if (el instanceof JsonObject) {
						final JsonObject sheet = (JsonObject) el;
						final String name = sheet.get("name").getAsString();
						final String modid = sheet.get("modid").getAsString();
						final String path = sheet.get("path").getAsString();
						this.textureSheets.put(name, new ParsedTextureSheet(name, modid, path));
					}
				}
				for (final JsonElement el : jsonobject.get("textures").getAsJsonArray()) {
					if (el instanceof JsonObject) {
						final JsonObject sheet = (JsonObject) el;
						final String name = sheet.get("name").getAsString();
						final IBinnieTexture textureSheet = this.getTextureSheet(sheet.get("sheet").getAsString());
						final Area uv = this.getArea(sheet.get("uv").getAsString());
						Border border = Border.ZERO;
						Border padding = Border.ZERO;
						if (sheet.has("border")) {
							border = this.getBorder(sheet.get("border").getAsString());
						}
						if (sheet.has("padding")) {
							padding = this.getBorder(sheet.get("padding").getAsString());
						}
						this.textures.put(name, new Texture(uv, padding, border, textureSheet));
					}
				}
			} catch (RuntimeException runtimeexception) {
				throw new JsonParseException("Failed to parse stylesheet for Binnie's Mods", runtimeexception);
			} finally {
				IOUtils.closeQuietly(bufferedreader);
			}
		} catch (IOException e) {
			throw new RuntimeException("Failed to load default stylesheet for Binnie's Mods.", e);
		}
	}

	public Area getArea(final String name) {
		final String[] split = name.split(" ");
		if (split.length < 1 || split.length > 4) {
			throw new RuntimeException("Parameter must have between one and four numbers");
		}
		final List<Integer> i = new ArrayList<>();
		for (final String string : split) {
			i.add(Integer.parseInt(string));
		}
		if (i.size() == 1) {
			return new Area(i.get(0));
		}
		if (i.size() == 2) {
			return new Area(i.get(0), i.get(1));
		}
		if (i.size() == 3) {
			return new Area(i.get(0), i.get(1), i.get(2));
		}
		return new Area(i.get(0), i.get(1), i.get(2), i.get(3));
	}

	public Border getBorder(final String name) {
		final String[] split = name.split(" ");
		if (split.length < 1 || split.length > 4) {
			throw new RuntimeException("Parameter must have between one and four numbers");
		}
		final List<Integer> i = new ArrayList<>();
		for (final String string : split) {
			i.add(Integer.parseInt(string));
		}
		if (i.size() == 1) {
			return new Border(i.get(0));
		}
		if (i.size() == 2) {
			return new Border(i.get(0), i.get(1));
		}
		if (i.size() == 3) {
			return new Border(i.get(0), i.get(1), i.get(2));
		}
		return new Border(i.get(0), i.get(1), i.get(2), i.get(3));
	}

	public IBinnieTexture getTextureSheet(final String name) {
		if (!this.textureSheets.containsKey(name)) {
			throw new RuntimeException("Missing GUI texture sheet for Binnie Mods: " + name);
		}
		return this.textureSheets.get(name);
	}

	public Texture getTexture(final String name) {
		if (!this.textures.containsKey(name)) {
			throw new RuntimeException("Missing GUI texture Binnie Mods: " + name);
		}
		return this.textures.get(name);
	}
}
