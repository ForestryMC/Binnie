package binnie.extrabees.gui.database;

import binnie.Binnie;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.*;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.resource.minecraft.CraftGUITexture;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.List;

public class ControlBiomes extends Control implements ITooltip {
    List<Integer> tolerated;

    public ControlBiomes(final IWidget parent, final int x, final int y, final int width, final int height) {
        super(parent, x, y, width * 16, height * 16);
        this.tolerated = new ArrayList<Integer>();
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
            list.add(Biome.getBiome(this.tolerated.get(i)).getBiomeName());
        }
    }

    @Override
    public void onRenderForeground() {
        for (int i = 0; i < this.tolerated.size(); ++i) {
            final int x = i % 8 * 16;
            final int y = i / 8 * 16;
            if (Biome.getBiome(i) != null) {
                //TODO FIND COLOR
                //CraftGUI.Render.colour(Biome.getBiome(i).color);
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
        final IBee bee = Binnie.Genetics.getBeeRoot().getBee(genome);
    }
}
