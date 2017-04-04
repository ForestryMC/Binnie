package binnie.genetics.gui;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.ITooltip;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.Tooltip;
import binnie.craftgui.core.geometry.Point;
import binnie.craftgui.core.renderer.RenderUtil;
import forestry.api.core.ForestryAPI;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ControlBiome extends Control implements ITooltip {
	private final Biome biome;
	private String iconCategory;

	public ControlBiome(final IWidget parent, final int x, final int y, final int w, final int h, final Biome biome) {
		super(parent, x, y, w, h);
		this.iconCategory = "plains";
		this.biome = biome;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		if (BiomeDictionary.hasType(this.biome, BiomeDictionary.Type.MOUNTAIN) || BiomeDictionary.hasType(this.biome, BiomeDictionary.Type.HILLS)) {
			this.iconCategory = "hills";
		}
		if (BiomeDictionary.hasType(this.biome, BiomeDictionary.Type.SANDY)) {
			this.iconCategory = "desert";
		}
		if (BiomeDictionary.hasType(this.biome, BiomeDictionary.Type.SNOWY)) {
			this.iconCategory = "snow";
		}
		if (BiomeDictionary.hasType(this.biome, BiomeDictionary.Type.FOREST)) {
			this.iconCategory = "forest";
		}
		if (BiomeDictionary.hasType(this.biome, BiomeDictionary.Type.SWAMP)) {
			this.iconCategory = "swamp";
		}
		if (BiomeDictionary.hasType(this.biome, BiomeDictionary.Type.JUNGLE)) {
			this.iconCategory = "jungle";
		}
		if (BiomeDictionary.hasType(this.biome, BiomeDictionary.Type.COLD) && BiomeDictionary.hasType(this.biome, BiomeDictionary.Type.FOREST)) {
			this.iconCategory = "taiga";
		}
		if (BiomeDictionary.hasType(this.biome, BiomeDictionary.Type.MUSHROOM)) {
			this.iconCategory = "mushroom";
		}
		if (BiomeDictionary.hasType(this.biome, BiomeDictionary.Type.OCEAN)) {
			this.iconCategory = "ocean";
		}
		if (BiomeDictionary.hasType(this.biome, BiomeDictionary.Type.NETHER)) {
			this.iconCategory = "nether";
		}
		if (BiomeDictionary.hasType(this.biome, BiomeDictionary.Type.END)) {
			this.iconCategory = "end";
		}
		TextureAtlasSprite sprite = ForestryAPI.textureManager.getDefault("habitats/" + this.iconCategory);
		RenderUtil.drawGuiSprite(Point.ZERO, sprite);
	}

	@Override
	public void getTooltip(final Tooltip tooltip) {
		tooltip.add(this.biome.getBiomeName().replaceAll("(\\p{Ll})(\\p{Lu})", "$1 $2"));
	}
}
