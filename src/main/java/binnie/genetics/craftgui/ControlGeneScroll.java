package binnie.genetics.craftgui;

import binnie.Binnie;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.controls.core.IControlValue;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.genetics.BreedingSystem;
import binnie.core.genetics.Gene;
import binnie.genetics.genetics.GeneTracker;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ControlGeneScroll extends Control implements IControlValue<BreedingSystem> {
    private String filter;
    private BreedingSystem system;

    protected ControlGeneScroll(IWidget parent, float x, float y, float w, float h) {
        super(parent, x, y, w, h);
        filter = "";
        system = null;
    }

    public void setFilter(String filter) {
        this.filter = filter.toLowerCase();
        refresh();
    }

    public void setGenes(BreedingSystem system) {
        this.system = system;
        refresh();
    }

    public void refresh() {
        deleteAllChildren();
        GeneTracker tracker = GeneTracker.getTracker(
                Window.get(this).getWorld(), Window.get(this).getUsername());
        Map<IChromosomeType, List<IAllele>> genes = Binnie.Genetics.getChromosomeMap(system.getSpeciesRoot());
        int x;
        int y = 0;
        boolean isNEI = ((WindowGeneBank) Window.get(this)).isNei;

        for (Map.Entry<IChromosomeType, List<IAllele>> entry : genes.entrySet()) {
            List<IAllele> discovered = new ArrayList<>();
            for (IAllele allele : entry.getValue()) {
                Gene gene = new Gene(allele, entry.getKey(), system.getSpeciesRoot());
                if ((isNEI || tracker.isSequenced(new Gene(allele, entry.getKey(), system.getSpeciesRoot())))
                        && gene.getName().toLowerCase().contains(filter)) {
                    discovered.add(allele);
                }
            }

            if (discovered.size() == 0) {
                continue;
            }

            x = 0;
            new ControlText(this, new IPoint(x, y), system.getChromosomeName(entry.getKey()));
            y += 12;
            for (IAllele allele : discovered) {
                if (x + 18 > getSize().x()) {
                    y += 20;
                    x = 0;
                }
                new ControlGene(this, x, y).setValue(new Gene(allele, entry.getKey(), system.getSpeciesRoot()));
                x += 18;
            }
            y += 24;
        }
        setSize(new IPoint(getSize().x(), y));
    }

    @Override
    public BreedingSystem getValue() {
        return system;
    }

    @Override
    public void setValue(BreedingSystem system) {
        setGenes(system);
    }
}
