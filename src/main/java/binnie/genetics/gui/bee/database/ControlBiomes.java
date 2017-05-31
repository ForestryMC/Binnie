package binnie.genetics.gui.bee.database;

import binnie.Binnie;
import binnie.core.craftgui.Attribute;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class ControlBiomes extends Control implements ITooltip {
	List<Integer> tolerated;

	public ControlBiomes(final IWidget parent, final int x, final int y, final int width, final int height) {
		super(parent, x, y, width * 16, height * 16);
		this.tolerated = new ArrayList<>();
		this.addAttribute(Attribute.MouseOver);
	}

	@Override
	public void getTooltip(final Tooltip list) {
		if (this.tolerated.isEmpty()) {
			return;
		}
		final int x = (int) (this.getRelativeMousePosition().x() / 16.0f);
		final int y = (int) (this.getRelativeMousePosition().y() / 16.0f);
		final int i = x + y * 8;
		if (i < this.tolerated.size()) {
			Biome biome = Biome.getBiome(this.tolerated.get(i));
			if (biome != null) {
				list.add(biome.getBiomeName());
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderForeground(int guiWidth, int guiHeight) {
		for (int i = 0; i < this.tolerated.size(); ++i) {
			final int x = i % 8 * 16;
			final int y = i / 8 * 16;
			if (Biome.getBiome(i) != null) {
				//TODO FIND COLOR
				//CraftGUI.Render.colour(Biome.getBiome(i).color);

			}
			CraftGUI.render.texture(CraftGUITexture.Button, new Area(x, y, 16, 16));
		}
	}

	public void setSpecies(final IAlleleBeeSpecies species) {
		this.tolerated.clear();
		if (species == null) {
			return;
		}
		final IBeeGenome genome = Binnie.GENETICS.getBeeRoot().templateAsGenome(Binnie.GENETICS.getBeeRoot().getTemplate(species));
		final IBee bee = Binnie.GENETICS.getBeeRoot().getBee(genome);
	}
}
