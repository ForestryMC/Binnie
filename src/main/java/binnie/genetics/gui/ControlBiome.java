// 
// Decompiled by Procyon v0.5.30
// 

package binnie.genetics.gui;

import binnie.core.craftgui.Tooltip;
import net.minecraft.util.IIcon;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.CraftGUI;
import forestry.core.render.TextureManager;
import net.minecraftforge.common.BiomeDictionary;
import binnie.core.craftgui.IWidget;
import net.minecraft.world.biome.BiomeGenBase;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.controls.core.Control;

public class ControlBiome extends Control implements ITooltip
{
	BiomeGenBase biome;
	String iconCategory;

	public ControlBiome(final IWidget parent, final float x, final float y, final float w, final float h, final BiomeGenBase biome) {
		super(parent, x, y, w, h);
		this.biome = null;
		this.iconCategory = "plains";
		this.biome = biome;
	}

	@Override
	public void onRenderBackground() {
		if (BiomeDictionary.isBiomeOfType(this.biome, BiomeDictionary.Type.MOUNTAIN)) {
			this.iconCategory = "hills";
		}
		if (BiomeDictionary.isBiomeOfType(this.biome, BiomeDictionary.Type.HILLS)) {
			this.iconCategory = "hills";
		}
		if (BiomeDictionary.isBiomeOfType(this.biome, BiomeDictionary.Type.SANDY)) {
			this.iconCategory = "desert";
		}
		if (BiomeDictionary.isBiomeOfType(this.biome, BiomeDictionary.Type.SNOWY)) {
			this.iconCategory = "snow";
		}
		if (BiomeDictionary.isBiomeOfType(this.biome, BiomeDictionary.Type.FOREST)) {
			this.iconCategory = "forest";
		}
		if (BiomeDictionary.isBiomeOfType(this.biome, BiomeDictionary.Type.SWAMP)) {
			this.iconCategory = "swamp";
		}
		if (BiomeDictionary.isBiomeOfType(this.biome, BiomeDictionary.Type.JUNGLE)) {
			this.iconCategory = "jungle";
		}
		if (BiomeDictionary.isBiomeOfType(this.biome, BiomeDictionary.Type.COLD) && BiomeDictionary.isBiomeOfType(this.biome, BiomeDictionary.Type.FOREST)) {
			this.iconCategory = "taiga";
		}
		if (BiomeDictionary.isBiomeOfType(this.biome, BiomeDictionary.Type.MUSHROOM)) {
			this.iconCategory = "mushroom";
		}
		if (BiomeDictionary.isBiomeOfType(this.biome, BiomeDictionary.Type.OCEAN)) {
			this.iconCategory = "ocean";
		}
		if (BiomeDictionary.isBiomeOfType(this.biome, BiomeDictionary.Type.NETHER)) {
			this.iconCategory = "nether";
		}
		if (BiomeDictionary.isBiomeOfType(this.biome, BiomeDictionary.Type.END)) {
			this.iconCategory = "end";
		}
		final IIcon icon = TextureManager.getInstance().getDefault("habitats/" + this.iconCategory);
		CraftGUI.Render.iconItem(IPoint.ZERO, icon);
	}

	@Override
	public void getTooltip(final Tooltip tooltip) {
		tooltip.add(this.biome.biomeName.replaceAll("(\\p{Ll})(\\p{Lu})", "$1 $2"));
	}
}
