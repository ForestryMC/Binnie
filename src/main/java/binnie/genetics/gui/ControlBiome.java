package binnie.genetics.gui;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.biome.Biome;

import net.minecraftforge.common.BiomeDictionary;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.core.ForestryAPI;

import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.renderer.RenderUtil;

public class ControlBiome extends Control implements ITooltip {
	private final Biome biome;
	private String iconCategory;

	public ControlBiome(IWidget parent, int x, int y, int w, int h, Biome biome) {
		super(parent, x, y, w, h);
		iconCategory = "plains";
		this.biome = biome;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.MOUNTAIN) || BiomeDictionary.hasType(biome, BiomeDictionary.Type.HILLS)) {
			iconCategory = "hills";
		}
		if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.SANDY)) {
			iconCategory = "desert";
		}
		if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.SNOWY)) {
			iconCategory = "snow";
		}
		if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.FOREST)) {
			iconCategory = "forest";
		}
		if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.SWAMP)) {
			iconCategory = "swamp";
		}
		if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.JUNGLE)) {
			iconCategory = "jungle";
		}
		if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.COLD) && BiomeDictionary.hasType(biome, BiomeDictionary.Type.FOREST)) {
			iconCategory = "taiga";
		}
		if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.MUSHROOM)) {
			iconCategory = "mushroom";
		}
		if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.OCEAN)) {
			iconCategory = "ocean";
		}
		if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.NETHER)) {
			iconCategory = "nether";
		}
		if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.END)) {
			iconCategory = "end";
		}
		TextureAtlasSprite sprite = ForestryAPI.textureManager.getDefault("habitats/" + iconCategory);
		RenderUtil.drawGuiSprite(Point.ZERO, sprite);
	}

	@Override
	public void getTooltip(Tooltip tooltip) {
		tooltip.add(biome.getBiomeName().replaceAll("(\\p{Ll})(\\p{Lu})", "$1 $2"));
	}
}
