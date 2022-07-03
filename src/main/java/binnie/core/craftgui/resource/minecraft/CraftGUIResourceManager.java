package binnie.core.craftgui.resource.minecraft;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IBorder;
import binnie.core.craftgui.resource.Texture;
import binnie.core.resource.IBinnieTexture;
import com.google.common.base.Charsets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;

@SideOnly(Side.CLIENT)
public class CraftGUIResourceManager implements IResourceManagerReloadListener {
    private Map<String, ParsedTextureSheet> textureSheets;
    private Map<String, Texture> textures;

    public CraftGUIResourceManager() {
        textureSheets = new HashMap<>();
        textures = new HashMap<>();
        CraftGUI.resourceManager = this;
    }

    @Override
    public void onResourceManagerReload(IResourceManager manager) {
        textureSheets.clear();
        try {
            IResource res = manager.getResource(new ResourceLocation("binniecore", "gui/stylesheet.json"));
            JsonObject jsonobject;
            BufferedReader bufferedreader = null;

            try {
                bufferedreader = new BufferedReader(new InputStreamReader(res.getInputStream(), Charsets.UTF_8));
                jsonobject = new JsonParser().parse(bufferedreader).getAsJsonObject();
                for (JsonElement el : jsonobject.get("texture-sheets").getAsJsonArray()) {
                    if (el instanceof JsonObject) {
                        JsonObject sheet = (JsonObject) el;
                        String name = sheet.get("name").getAsString();
                        String modid = sheet.get("modid").getAsString();
                        String path = sheet.get("path").getAsString();
                        textureSheets.put(name, new ParsedTextureSheet(name, modid, path));
                    }
                }

                for (JsonElement el : jsonobject.get("textures").getAsJsonArray()) {
                    if (el instanceof JsonObject) {
                        JsonObject sheet = (JsonObject) el;
                        String name = sheet.get("name").getAsString();
                        IBinnieTexture textureSheet =
                                getTextureSheet(sheet.get("sheet").getAsString());
                        IArea uv = getArea(sheet.get("uv").getAsString());
                        IBorder border = IBorder.ZERO;
                        IBorder padding = IBorder.ZERO;
                        if (sheet.has("border")) {
                            border = getBorder(sheet.get("border").getAsString());
                        }

                        if (sheet.has("padding")) {
                            padding = getBorder(sheet.get("padding").getAsString());
                        }
                        textures.put(name, new Texture(uv, padding, border, textureSheet.getTexture()));
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

    public IArea getArea(String name) {
        String[] split = name.split(" ");
        if (split.length < 1 || split.length > 4) {
            throw new RuntimeException("Parameter must have between one and four numbers");
        }

        List<Float> f = new ArrayList<>();
        for (String string : split) {
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

    public IBorder getBorder(String name) {
        String[] split = name.split(" ");
        if (split.length < 1 || split.length > 4) {
            throw new RuntimeException("Parameter must have between one and four numbers");
        }
        List<Float> f = new ArrayList<>();
        for (String string : split) {
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

    public IBinnieTexture getTextureSheet(String name) {
        if (!textureSheets.containsKey(name)) {
            throw new RuntimeException("Missing GUI texture sheet for Binnie Mods: " + name);
        }
        return textureSheets.get(name);
    }

    public Texture getTexture(String name) {
        if (!textures.containsKey(name)) {
            throw new RuntimeException("Missing GUI texture Binnie Mods: " + name);
        }
        return textures.get(name);
    }
}
