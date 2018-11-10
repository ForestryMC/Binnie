package binnie.genetics.gui.analyst;

import net.minecraft.client.util.ITooltipFlag;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.ISpeciesRoot;

import binnie.core.Binnie;
import binnie.core.api.genetics.IBreedingSystem;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.Attribute;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.ITooltip;
import binnie.core.gui.Tooltip;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.resource.textures.StandardTexture;
import binnie.core.gui.resource.textures.Texture;
import binnie.core.texture.BinnieCoreTexture;

public class ControlAnalystChromosome extends Control implements ITooltip {
	private static final Texture HOMOZYGOUS = new StandardTexture(0, 0, 16, 22, BinnieCoreTexture.GUI_ANALYST);
	private static final Texture HETEROZYGOUS = new StandardTexture(16, 0, 16, 22, BinnieCoreTexture.GUI_ANALYST);

	private final IAllele allele0;
	private final IAllele allele1;
	private final IChromosomeType chromosomeType;
	private final ISpeciesRoot root;

	public ControlAnalystChromosome(IWidget parent, int x, int y, ISpeciesRoot root, IChromosomeType type, IAllele allele0, IAllele allele1) {
		super(parent, x, y, 16, 22);
		addAttribute(Attribute.MOUSE_OVER);
		this.root = root;
		this.chromosomeType = type;
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
		CraftGUI.RENDER.texture(isHomozygous() ? HOMOZYGOUS : HETEROZYGOUS, Point.ZERO);
	}

	@Override
	public void getTooltip(Tooltip tooltip, ITooltipFlag tooltipFlag) {
		IBreedingSystem system = Binnie.GENETICS.getSystem(root);
		tooltip.add(system.getChromosomeName(chromosomeType));
		if (isHomozygous()) {
			tooltip.add(system.getAlleleName(chromosomeType, allele0));
		} else {
			tooltip.add("Active: " + system.getAlleleName(chromosomeType, allele0));
			tooltip.add("Inactive: " + system.getAlleleName(chromosomeType, allele1));
		}
	}
}
