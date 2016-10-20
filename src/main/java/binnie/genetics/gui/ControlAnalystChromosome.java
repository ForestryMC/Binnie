package binnie.genetics.gui;

import binnie.Binnie;
import binnie.core.genetics.BreedingSystem;
import binnie.core.texture.BinnieCoreTexture;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.*;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.resource.Texture;
import binnie.craftgui.resource.minecraft.StandardTexture;
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

    public ControlAnalystChromosome(final IWidget parent, final float x, final float y, final ISpeciesRoot root, final IChromosomeType type, final IAllele allele0, final IAllele allele1) {
        super(parent, x, y, 16.0f, 22.0f);
        this.Homozygous = new StandardTexture(0, 0, 16, 22, BinnieCoreTexture.GUIAnalyst);
        this.Heterozygous = new StandardTexture(16, 0, 16, 22, BinnieCoreTexture.GUIAnalyst);
        this.addAttribute(Attribute.MouseOver);
        this.root = root;
        this.chromosomeType = type;
        this.allele0 = allele0;
        this.allele1 = allele1;
    }

    public boolean isHomozygous() {
        return this.allele0.getUID().equals(this.allele1.getUID());
    }

    @Override
    public void onRenderBackground() {
        super.onRenderBackground();
        CraftGUI.Render.texture(this.isHomozygous() ? this.Homozygous : this.Heterozygous, IPoint.ZERO);
    }

    @Override
    public void getTooltip(final Tooltip tooltip) {
        final BreedingSystem system = Binnie.Genetics.getSystem(this.root);
        tooltip.add(system.getChromosomeName(this.chromosomeType));
        if (this.isHomozygous()) {
            tooltip.add(system.getAlleleName(this.chromosomeType, this.allele0));
        } else {
            tooltip.add("Active: " + system.getAlleleName(this.chromosomeType, this.allele0));
            tooltip.add("Inactive: " + system.getAlleleName(this.chromosomeType, this.allele1));
        }
    }
}
