package binnie.genetics.gui.analyst;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.ISpeciesRoot;

import binnie.Binnie;
import binnie.core.genetics.BreedingSystem;
import binnie.core.gui.Attribute;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.ITooltip;
import binnie.core.gui.IWidget;
import binnie.core.gui.Tooltip;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.resource.Texture;
import binnie.core.gui.resource.minecraft.StandardTexture;
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
		Homozygous = new StandardTexture(0, 0, 16, 22, BinnieCoreTexture.GUI_ANALYST);
		Heterozygous = new StandardTexture(16, 0, 16, 22, BinnieCoreTexture.GUI_ANALYST);
		addAttribute(Attribute.MOUSE_OVER);
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
		CraftGUI.RENDER.texture(isHomozygous() ? Homozygous : Heterozygous, Point.ZERO);
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
