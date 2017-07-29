package binnie.genetics.gui;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.ISpeciesRoot;

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

public class ControlAnalystChromosome extends Control implements ITooltip {
	IAllele allele0;
	IAllele allele1;
	IChromosomeType chromosomeType;
	ISpeciesRoot root;
	Texture Homozygous;
	Texture Heterozygous;

	public ControlAnalystChromosome(IWidget parent, int x, int y, ISpeciesRoot root, IChromosomeType type, IAllele allele0, IAllele allele1) {
		super(parent, x, y, 16, 22);
		Homozygous = new StandardTexture(0, 0, 16, 22, BinnieCoreTexture.GUIAnalyst);
		Heterozygous = new StandardTexture(16, 0, 16, 22, BinnieCoreTexture.GUIAnalyst);
		addAttribute(Attribute.MouseOver);
		this.root = root;
		chromosomeType = type;
		this.allele0 = allele0;
		this.allele1 = allele1;
	}

	public boolean isHomozygous() {
		return allele0.getUID().equals(allele1.getUID());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		super.onRenderBackground(guiWidth, guiHeight);
		CraftGUI.render.texture(isHomozygous() ? Homozygous : Heterozygous, Point.ZERO);
	}

	@Override
	public void getTooltip(Tooltip tooltip) {
		BreedingSystem system = Binnie.GENETICS.getSystem(root);
		tooltip.add(system.getChromosomeName(chromosomeType));
		if (isHomozygous()) {
			tooltip.add(system.getAlleleName(chromosomeType, allele0));
		} else {
			tooltip.add("Active: " + system.getAlleleName(chromosomeType, allele0));
			tooltip.add("Inactive: " + system.getAlleleName(chromosomeType, allele1));
		}
	}
}
