package binnie.core.gui.resource.minecraft;

import com.google.common.base.Charsets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.Constants;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Border;
import binnie.core.gui.resource.Texture;
import binnie.core.resource.IBinnieTexture;
import binnie.core.util.Log;

@SideOnly(Side.CLIENT)
public class CraftGUIResourceManager implements IResourceManagerReloadListener {
	public static final ResourceLocation SHEET_LOCATION = new ResourceLocation(Constants.CORE_MOD_ID, "gui/stylesheet.json");
	public static final String SHEETS_KEY = "texture-sheets";
	public static final String NAME_KEY = "name";
	public static final String MODID_KEY = "modid";
	public static final String PATH_KEY = "path";
	public static final String SHEET_KEY = "sheet";
	public static final String UV_KEY = "uv";
	public static final String BORDER_KEY = "border";
	public static final String PADDING_KEY = "padding";
	public static final String TEXTURES_KEY = "textures";

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
			IResource res = manager.getResource(SHEET_LOCATION);
			BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(res.getInputStream(), Charsets.UTF_8));

			JsonParser jsonParser = new JsonParser();
			JsonObject jsonObject = jsonParser.parse(bufferedreader).getAsJsonObject();
			parseTextureSheets(jsonObject);
			parseTextures(jsonObject);

			IOUtils.closeQuietly(bufferedreader);
		} catch (IOException e) {
			throw new RuntimeException("Failed to load default stylesheet for Binnie's Mods.", e);
		}
	}

	private void parseTextureSheets(JsonObject jsonObject){
		JsonArray textureSheets = JsonUtils.getJsonArray(jsonObject, SHEETS_KEY);
		if(textureSheets == null){
			return;
		}
		for (JsonElement element : textureSheets) {
			if(!element.isJsonObject()){
				continue;
			}
			try {
				JsonObject sheetJson = (JsonObject) element;
				String name = JsonUtils.getString(sheetJson, NAME_KEY);
				String modid = JsonUtils.getString(sheetJson, MODID_KEY);
				String path = JsonUtils.getString(sheetJson, PATH_KEY);
				ParsedTextureSheet textureSheet = new ParsedTextureSheet(name, modid, path);
				this.textureSheets.put(name, textureSheet);
			}catch(Exception e){
				Log.warning("Failed to load stylesheet for Binnie's Mods.", e);
			}
		}
	}

	private void parseTextures(JsonObject jsonObject){
		JsonArray textures = JsonUtils.getJsonArray(jsonObject, TEXTURES_KEY);
		if(textures == null){
			return;
		}
		for (JsonElement element : textures) {
			if(!element.isJsonObject()){
				continue;
			}
			try {
				JsonObject sheetJson = element.getAsJsonObject();
				parseTexture(sheetJson);
			}catch(Exception e){
				Log.warning("Failed to load stylesheet for Binnie's Mods.", e);
			}
		}
	}

	private void parseTexture(JsonObject sheetJson){
		String name = JsonUtils.getString(sheetJson, NAME_KEY);
		String sheet = JsonUtils.getString(sheetJson, SHEET_KEY);
		JsonArray uvArray = JsonUtils.getJsonArray(sheetJson, UV_KEY);
		IBinnieTexture textureSheet = this.getTextureSheet(sheet);
		Area uv = this.parseArea(uvArray);
		Border border = Border.ZERO;
		Border padding = Border.ZERO;
		if (sheetJson.has(BORDER_KEY)) {
			JsonArray array = JsonUtils.getJsonArray(sheetJson, BORDER_KEY);
			border = this.parseBorder(array);
		}
		if (sheetJson.has(PADDING_KEY)) {
			JsonArray array = JsonUtils.getJsonArray(sheetJson, PADDING_KEY);
			padding = this.parseBorder(array);
		}
		Texture texture = new Texture(uv, padding, border, textureSheet);
		this.textures.put(name, texture);
	}

	public Area parseArea(JsonArray uvArray) {
		int[] ints = new int[uvArray.size()];
		if (ints.length < 1 || ints.length > 4) {
			throw new JsonParseException("Parameter must have between one and four numbers");
		}
		for(int i = 0;i < uvArray.size();i++){
			JsonElement object = uvArray.get(i);
			ints[i] = JsonUtils.getInt(object, "uv" + i);
		}
		if (ints.length == 1) {
			return new Area(ints[0]);
		}
		if (ints.length == 2) {
			return new Area(ints[0], ints[1]);
		}
		if (ints.length == 3) {
			return new Area(ints[0], ints[1], ints[2]);
		}
		return new Area(ints[0], ints[1], ints[2], ints[3]);
	}

	public Border parseBorder(JsonArray array) {
		int[] ints = new int[array.size()];
		if (ints.length < 1 || ints.length > 4) {
			throw new JsonParseException("Parameter must have between one and four numbers");
		}
		for(int i = 0;i < array.size();i++){
			JsonElement object = array.get(i);
			ints[i] = JsonUtils.getInt(object, UV_KEY + i);
		}
		if (ints.length == 1) {
			return new Border(ints[0]);
		}
		if (ints.length == 2) {
			return new Border(ints[0], ints[1]);
		}
		if (ints.length == 3) {
			return new Border(ints[0], ints[1], ints[2]);
		}
		return new Border(ints[0], ints[1], ints[2], ints[3]);
	}

	public IBinnieTexture getTextureSheet(String name) {
		if (!this.textureSheets.containsKey(name)) {
			throw new RuntimeException("Missing GUI texture sheet for Binnie Mods: " + name);
		}
		return this.textureSheets.get(name);
	}

	public Texture getTexture(String name) {
		if (!this.textures.containsKey(name)) {
			throw new RuntimeException("Missing GUI texture Binnie Mods: " + name);
		}
		return this.textures.get(name);
	}
}
