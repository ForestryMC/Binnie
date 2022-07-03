package binnie.genetics.gui;

import binnie.Binnie;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.StandardTexture;
import binnie.core.genetics.BreedingSystem;
import binnie.core.texture.BinnieCoreTexture;
import binnie.core.util.I18N;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.ISpeciesRoot;

public class ControlAnalystChromosome extends Control implements ITooltip {
    IAllele allele0;
    IAllele allele1;
    IChromosomeType chromosomeType;
    ISpeciesRoot root;
    Texture Homozygous;
    Texture Heterozygous;

    public ControlAnalystChromosome(
            IWidget parent,
            float x,
            float y,
            ISpeciesRoot root,
            IChromosomeType type,
            IAllele allele0,
            IAllele allele1) {
        super(parent, x, y, 16.0f, 22.0f);
        Homozygous = new StandardTexture(0, 0, 16, 22, BinnieCoreTexture.GUIAnalyst);
        Heterozygous = new StandardTexture(16, 0, 16, 22, BinnieCoreTexture.GUIAnalyst);
        addAttribute(WidgetAttribute.MOUSE_OVER);
        this.root = root;
        chromosomeType = type;
        this.allele0 = allele0;
        this.allele1 = allele1;
    }

    public boolean isHomozygous() {
        return allele0.getUID().equals(allele1.getUID());
    }

    @Override
    public void onRenderBackground() {
        super.onRenderBackground();
        CraftGUI.render.texture(isHomozygous() ? Homozygous : Heterozygous, IPoint.ZERO);
    }

    @Override
    public void getTooltip(Tooltip tooltip) {
        BreedingSystem system = Binnie.Genetics.getSystem(root);
        tooltip.add(system.getChromosomeName(chromosomeType));
        if (isHomozygous()) {
            tooltip.add(system.getAlleleName(chromosomeType, allele0));
        } else {
            tooltip.add(I18N.localise(
                    "genetics.gui.analyst.chromosome.active", system.getAlleleName(chromosomeType, allele0)));
            tooltip.add(I18N.localise(
                    "genetics.gui.analyst.chromosome.inactive", system.getAlleleName(chromosomeType, allele1)));
        }
    }
}
