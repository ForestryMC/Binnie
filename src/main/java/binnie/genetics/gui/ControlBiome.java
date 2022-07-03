package binnie.genetics.gui;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.IPoint;
import forestry.core.render.TextureManager;
import net.minecraft.util.IIcon;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

public class ControlBiome extends Control implements ITooltip {
    BiomeGenBase biome;
    String iconCategory;

    public ControlBiome(IWidget parent, float x, float y, float w, float h, BiomeGenBase biome) {
        super(parent, x, y, w, h);
        this.biome = null;
        iconCategory = "plains";
        this.biome = biome;
    }

    @Override
    public void onRenderBackground() {
        if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.MOUNTAIN)
                || BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.HILLS)) {
            iconCategory = "hills";
        }
        if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.SANDY)) {
            iconCategory = "desert";
        }
        if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.SNOWY)) {
            iconCategory = "snow";
        }
        if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.FOREST)) {
            iconCategory = "forest";
        }
        if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.SWAMP)) {
            iconCategory = "swamp";
        }
        if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.JUNGLE)) {
            iconCategory = "jungle";
        }
        if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.COLD)
                && BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.FOREST)) {
            iconCategory = "taiga";
        }
        if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.MUSHROOM)) {
            iconCategory = "mushroom";
        }
        if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.OCEAN)) {
            iconCategory = "ocean";
        }
        if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.NETHER)) {
            iconCategory = "nether";
        }
        if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.END)) {
            iconCategory = "end";
        }
        IIcon icon = TextureManager.getInstance().getDefault("habitats/" + iconCategory);
        CraftGUI.render.iconItem(IPoint.ZERO, icon);
    }

    @Override
    public void getTooltip(Tooltip tooltip) {
        tooltip.add(biome.biomeName.replaceAll("(\\p{Ll})(\\p{Lu})", "$1 $2"));
    }
}
