package binnie.genetics.gui;

import binnie.Binnie;
import binnie.core.genetics.BreedingSystem;
import binnie.craftgui.controls.ControlText;
import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.geometry.TextJustification;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.client.renderer.GlStateManager;

public class AnalystPageGenome extends ControlAnalystPage {
	boolean active;

	public AnalystPageGenome(final IWidget parent, final IArea area, final boolean active, final IIndividual ind) {
		super(parent, area);
		this.active = active;
		this.setColour(26265);
		int y = 4;
		new ControlTextCentered(this, y, "§n" + this.getTitle()).setColour(this.getColour());
		y += 16;
		final ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(ind.getClass());
		final BreedingSystem system = Binnie.GENETICS.getSystem(root);
		final Control scaled = new Control(this, 0, y, 0, 0) {
			@Override
			public void onRenderBackground(int guiWidth, int guiHeight) {
				GlStateManager.pushMatrix();
				GlStateManager.translate(10.0f, -15.0f, 0.0f);
				GlStateManager.scale(0.9f, 0.95f, 1.0f);
			}

			@Override
			public void onRenderForeground(int guiWidth, int guiHeight) {
				GlStateManager.popMatrix();
			}
		};
		for (final IChromosomeType chromo : system.getActiveKaryotype()) {
			final IAllele allele = active ? ind.getGenome().getActiveAllele(chromo) : ind.getGenome().getInactiveAllele(chromo);
			final String alleleName = system.getAlleleName(chromo, allele);
			final int height = CraftGUI.render.textHeight(alleleName, this.w() / 2 - 2);
			new ControlText(scaled, new IArea(0, y + (height - 9) / 2, this.w() / 2 - 2, 0), system.getChromosomeShortName(chromo) + " :", TextJustification.TopRight).setColour(this.getColour());
			new ControlText(scaled, new IArea(this.w() / 2 + 2, y, this.w() / 2 - 2, 0), alleleName, TextJustification.TopLeft).setColour(this.getColour());
			y += 3 + height;
		}
		this.setSize(new IPoint(this.w(), y + 8));
	}

	@Override
	public String getTitle() {
		return (this.active ? "Active" : "Inactive") + " Genome";
	}
}
