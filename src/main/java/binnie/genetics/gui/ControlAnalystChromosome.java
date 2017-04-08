package binnie.genetics.gui;

import binnie.Binnie;
import binnie.core.craftgui.Attribute;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.StandardTexture;
import binnie.core.genetics.BreedingSystem;
import binnie.core.texture.BinnieCoreTexture;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ControlAnalystChromosome extends Control implements ITooltip {
	IAllele allele0;
	IAllele allele1;
	IChromosomeType chromosomeType;
	ISpeciesRoot root;
	Texture Homozygous;
	Texture Heterozygous;

	public ControlAnalystChromosome(final IWidget parent, final int x, final int y, final ISpeciesRoot root, final IChromosomeType type, final IAllele allele0, final IAllele allele1) {
		super(parent, x, y, 16, 22);
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
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		super.onRenderBackground(guiWidth, guiHeight);
		CraftGUI.render.texture(this.isHomozygous() ? this.Homozygous : this.Heterozygous, Point.ZERO);
	}

	@Override
	public void getTooltip(final Tooltip tooltip) {
		final BreedingSystem system = Binnie.GENETICS.getSystem(this.root);
		tooltip.add(system.getChromosomeName(this.chromosomeType));
		if (this.isHomozygous()) {
			tooltip.add(system.getAlleleName(this.chromosomeType, this.allele0));
		} else {
			tooltip.add("Active: " + system.getAlleleName(this.chromosomeType, this.allele0));
			tooltip.add("Inactive: " + system.getAlleleName(this.chromosomeType, this.allele1));
		}
	}
}
