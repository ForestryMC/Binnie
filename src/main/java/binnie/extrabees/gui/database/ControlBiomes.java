package binnie.extrabees.gui.database;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.ArrayList;
import java.util.List;

public class ControlBiomes extends Control implements ITooltip {
	protected List<Integer> tolerated;

	public ControlBiomes(IWidget parent, int x, int y, int width, int height) {
		super(parent, x, y, width * 16, height * 16);
		tolerated = new ArrayList<>();
		addAttribute(WidgetAttribute.MouseOver);
	}

	@Override
	public void getTooltip(Tooltip tooltip) {
		if (tolerated.isEmpty()) {
			return;
		}

		int x = (int) (getRelativeMousePosition().x() / 16.0f);
		int y = (int) (getRelativeMousePosition().y() / 16.0f);
		int i = x + y * 8;
		if (i < tolerated.size()) {
			tooltip.add(BiomeGenBase.getBiome(tolerated.get(i)).biomeName);
		}
	}

	@Override
	public void onRenderForeground() {
		for (int i = 0; i < tolerated.size(); ++i) {
			int x = i % 8 * 16;
			int y = i / 8 * 16;
			if (BiomeGenBase.getBiome(i) != null) {
				CraftGUI.Render.colour(BiomeGenBase.getBiome(i).color);
			}
			CraftGUI.Render.texture(CraftGUITexture.Button, new IArea(x, y, 16.0f, 16.0f));
		}
	}

	public void setSpecies(IAlleleBeeSpecies species) {
		tolerated.clear();
		if (species == null) {
			return;
		}

		// TODO unused code?
		IBeeGenome genome = Binnie.Genetics.getBeeRoot().templateAsGenome(Binnie.Genetics.getBeeRoot().getTemplate(species.getUID()));
		IBee bee = Binnie.Genetics.getBeeRoot().getBee(BinnieCore.proxy.getWorld(), genome);
	}
}
