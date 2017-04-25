// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extrabees.gui.database;

import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import binnie.core.BinnieCore;
import binnie.Binnie;
import forestry.api.apiculture.IAlleleBeeSpecies;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;
import binnie.core.craftgui.CraftGUI;
import net.minecraft.world.biome.BiomeGenBase;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.WidgetAttribute;
import java.util.ArrayList;
import binnie.core.craftgui.IWidget;
import java.util.List;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.controls.core.Control;

public class ControlBiomes extends Control implements ITooltip
{
	List<Integer> tolerated;

	public ControlBiomes(final IWidget parent, final int x, final int y, final int width, final int height) {
		super(parent, x, y, width * 16, height * 16);
		this.tolerated = new ArrayList<Integer>();
		this.addAttribute(WidgetAttribute.MouseOver);
	}

	@Override
	public void getTooltip(final Tooltip tooltip) {
		if (this.tolerated.isEmpty()) {
			return;
		}
		final int x = (int) (this.getRelativeMousePosition().x() / 16.0f);
		final int y = (int) (this.getRelativeMousePosition().y() / 16.0f);
		final int i = x + y * 8;
		if (i < this.tolerated.size()) {
			tooltip.add(BiomeGenBase.getBiome(this.tolerated.get(i)).biomeName);
		}
	}

	@Override
	public void onRenderForeground() {
		for (int i = 0; i < this.tolerated.size(); ++i) {
			final int x = i % 8 * 16;
			final int y = i / 8 * 16;
			if (BiomeGenBase.getBiome(i) != null) {
				CraftGUI.Render.colour(BiomeGenBase.getBiome(i).color);
			}
			CraftGUI.Render.texture(CraftGUITexture.Button, new IArea(x, y, 16.0f, 16.0f));
		}
	}

	public void setSpecies(final IAlleleBeeSpecies species) {
		this.tolerated.clear();
		if (species == null) {
			return;
		}
		final IBeeGenome genome = Binnie.Genetics.getBeeRoot().templateAsGenome(Binnie.Genetics.getBeeRoot().getTemplate(species.getUID()));
		final IBee bee = Binnie.Genetics.getBeeRoot().getBee(BinnieCore.proxy.getWorld(), genome);
	}
}
