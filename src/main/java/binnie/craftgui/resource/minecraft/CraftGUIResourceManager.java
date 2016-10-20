// 
// Decompiled by Procyon v0.5.30
// 

package binnie.craftgui.resource.minecraft;

import java.util.List;
import java.util.ArrayList;
import binnie.craftgui.core.geometry.IArea;
import binnie.core.resource.IBinnieTexture;
import net.minecraft.client.resources.IResource;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import com.google.gson.JsonParseException;
import binnie.craftgui.core.geometry.IBorder;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.google.common.base.Charsets;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.resources.IResourceManager;
import binnie.craftgui.core.CraftGUI;
import java.util.HashMap;
import binnie.craftgui.resource.Texture;
import java.util.Map;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.IResourceManagerReloadListener;

@SideOnly(Side.CLIENT)
public class CraftGUIResourceManager implements IResourceManagerReloadListener
{
	private Map<String, ParsedTextureSheet> textureSheets;
	private Map<String, Texture> textures;

	public CraftGUIResourceManager() {
		this.textureSheets = new HashMap<String, ParsedTextureSheet>();
		this.textures = new HashMap<String, Texture>();
		CraftGUI.ResourceManager = this;
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
						final IArea uv = this.getArea(sheet.get("uv").getAsString());
						IBorder border = IBorder.ZERO;
						IBorder padding = IBorder.ZERO;
						if (sheet.has("border")) {
							border = this.getBorder(sheet.get("border").getAsString());
						}
						if (sheet.has("padding")) {
							padding = this.getBorder(sheet.get("padding").getAsString());
						}
						this.textures.put(name, new Texture(uv, padding, border, textureSheet.getTexture()));
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

	public IArea getArea(final String name) {
		final String[] split = name.split(" ");
		if (split.length < 1 || split.length > 4) {
			throw new RuntimeException("Parameter must have between one and four numbers");
		}
		final List<Float> f = new ArrayList<Float>();
		for (final String string : split) {
			f.add(Float.parseFloat(string));
		}
		if (f.size() == 1) {
			return new IArea(f.get(0));
		}
		if (f.size() == 2) {
			return new IArea(f.get(0), f.get(1));
		}
		if (f.size() == 3) {
			return new IArea(f.get(0), f.get(1), f.get(2));
		}
		return new IArea(f.get(0), f.get(1), f.get(2), f.get(3));
	}

	public IBorder getBorder(final String name) {
		final String[] split = name.split(" ");
		if (split.length < 1 || split.length > 4) {
			throw new RuntimeException("Parameter must have between one and four numbers");
		}
		final List<Float> f = new ArrayList<Float>();
		for (final String string : split) {
			f.add(Float.parseFloat(string));
		}
		if (f.size() == 1) {
			return new IBorder(f.get(0));
		}
		if (f.size() == 2) {
			return new IBorder(f.get(0), f.get(1));
		}
		if (f.size() == 3) {
			return new IBorder(f.get(0), f.get(1), f.get(2));
		}
		return new IBorder(f.get(0), f.get(1), f.get(2), f.get(3));
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
