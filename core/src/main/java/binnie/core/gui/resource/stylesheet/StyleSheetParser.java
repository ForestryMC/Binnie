package binnie.core.gui.resource.stylesheet;

import com.google.common.base.Charsets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.io.IOUtils;

import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Border;
import binnie.core.gui.resource.textures.Texture;
import binnie.core.resource.IBinnieTexture;
import binnie.core.util.Log;

public class StyleSheetParser {
	public static final String SHEETS_KEY = "texture-sheets";
	public static final String NAME_KEY = "name";
	public static final String MODID_KEY = "modid";
	public static final String PATH_KEY = "path";
	public static final String SHEET_KEY = "sheet";
	public static final String UV_KEY = "uv";
	public static final String BORDER_KEY = "border";
	public static final String PADDING_KEY = "padding";
	public static final String TEXTURES_KEY = "textures";

	@SideOnly(Side.CLIENT)
	public static StyleSheet parseSheet(IResourceManager manager, ResourceLocation location) {
		try {
			final IResource res = manager.getResource(location);
			final BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(res.getInputStream(), Charsets.UTF_8));

			final JsonParser jsonParser = new JsonParser();
			final JsonObject jsonObject = jsonParser.parse(bufferedreader).getAsJsonObject();

			final Map<String, ParsedTextureSheet> textureSheets = parseTextureSheets(jsonObject);
			final Map<String, Texture> textures = parseTextures(jsonObject, textureSheets);

			IOUtils.closeQuietly(bufferedreader);
			return new StyleSheet(textures);
		} catch (IOException e) {
			throw new RuntimeException("Failed to load default stylesheet for Binnie's Mods.", e);
		}
	}

	private static Map<String, ParsedTextureSheet> parseTextureSheets(JsonObject jsonObject){
		final JsonArray textureSheetsArray = JsonUtils.getJsonArray(jsonObject, SHEETS_KEY);
		if(textureSheetsArray == null){
			return Collections.emptyMap();
		}
		final Map<String, ParsedTextureSheet> textureSheets = new HashMap<>();
		for (final JsonElement element : textureSheetsArray) {
			if(!element.isJsonObject()){
				continue;
			}
			try {
				final JsonObject sheetJson = (JsonObject) element;
				final String name = JsonUtils.getString(sheetJson, NAME_KEY);
				final String modid = JsonUtils.getString(sheetJson, MODID_KEY);
				final String path = JsonUtils.getString(sheetJson, PATH_KEY);
				final ParsedTextureSheet textureSheet = new ParsedTextureSheet(modid, path);
				textureSheets.put(name, textureSheet);
			}catch(Exception e){
				Log.warning("Failed to load stylesheet for Binnie's Mods.", e);
			}
		}
		return textureSheets;
	}

	private static Map<String, Texture> parseTextures(JsonObject jsonObject, Map<String, ParsedTextureSheet> textureSheets){
		final JsonArray texturesJson = JsonUtils.getJsonArray(jsonObject, TEXTURES_KEY);
		if(texturesJson == null){
			return Collections.emptyMap();
		}
		final Map<String, Texture> textures = new HashMap<>();
		for (JsonElement element : texturesJson) {
			if(!element.isJsonObject()){
				continue;
			}
			try {
				final JsonObject sheetJson = element.getAsJsonObject();
				final String name = JsonUtils.getString(sheetJson, NAME_KEY);
				final String sheet = JsonUtils.getString(sheetJson, SHEET_KEY);
				final JsonArray uvArray = JsonUtils.getJsonArray(sheetJson, UV_KEY);
				final IBinnieTexture textureSheet = textureSheets.get(sheet);
				final Area uv = parseArea(uvArray);
				Border border = Border.ZERO;
				Border padding = Border.ZERO;
				if (sheetJson.has(BORDER_KEY)) {
					JsonArray array = JsonUtils.getJsonArray(sheetJson, BORDER_KEY);
					border = parseBorder(array);
				}
				if (sheetJson.has(PADDING_KEY)) {
					JsonArray array = JsonUtils.getJsonArray(sheetJson, PADDING_KEY);
					padding = parseBorder(array);
				}
				final Texture texture = new Texture(uv, padding, border, textureSheet);
				textures.put(name, texture);
			}catch(Exception e){
				Log.warning("Failed to load stylesheet for Binnie's Mods.", e);
			}
		}
		return textures;
	}

	private static Area parseArea(JsonArray uvArray) {
		final int[] ints = new int[uvArray.size()];
		if (ints.length < 1 || ints.length > 4) {
			throw new JsonParseException("Parameter must have between one and four numbers");
		}
		for(int i = 0;i < uvArray.size();i++){
			final JsonElement object = uvArray.get(i);
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

	private static Border parseBorder(JsonArray array) {
		final int[] ints = new int[array.size()];
		if (ints.length < 1 || ints.length > 4) {
			throw new JsonParseException("Parameter must have between one and four numbers");
		}
		for(int i = 0;i < array.size();i++){
			final JsonElement object = array.get(i);
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
}
