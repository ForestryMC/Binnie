package binnie.genetics.gui.database.bee;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.biome.Biome;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;

import binnie.Binnie;
import binnie.core.craftgui.Attribute;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;

public class ControlBiomes extends Control implements ITooltip {
	List<Integer> tolerated;

	public ControlBiomes(IWidget parent, int x, int y, int width, int height) {
		super(parent, x, y, width * 16, height * 16);
		tolerated = new ArrayList<>();
		addAttribute(Attribute.MouseOver);
	}

	@Override
	public void getTooltip(Tooltip list) {
		if (tolerated.isEmpty()) {
			return;
		}

		int x = (int) (getRelativeMousePosition().x() / 16.0f);
		int y = (int) (getRelativeMousePosition().y() / 16.0f);
		int i = x + y * 8;
		if (i >= tolerated.size()) {
			return;
		}

		Biome biome = Biome.getBiome(tolerated.get(i));
		if (biome != null) {
			list.add(biome.getBiomeName());
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderForeground(int guiWidth, int guiHeight) {
		for (int i = 0; i < tolerated.size(); ++i) {
			int x = i % 8 * 16;
			int y = i / 8 * 16;
			if (Biome.getBiome(i) != null) {
				//TODO FIND COLOR
				//CraftGUI.Render.colour(Biome.getBiome(i).color);
			}
			CraftGUI.render.texture(CraftGUITexture.Button, new Area(x, y, 16, 16));
		}
	}

	public void setSpecies(IAlleleBeeSpecies species) {
		tolerated.clear();
		IBeeGenome genome = Binnie.GENETICS.getBeeRoot().templateAsGenome(Binnie.GENETICS.getBeeRoot().getTemplate(species));
		IBee bee = Binnie.GENETICS.getBeeRoot().getBee(genome);
	}
}
