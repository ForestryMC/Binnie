// 
// Decompiled by Procyon v0.5.30
// 

package binnie.genetics.gui;

import forestry.api.genetics.IAllele;
import binnie.core.genetics.BreedingSystem;
import forestry.api.genetics.ISpeciesRoot;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.controls.ControlText;
import binnie.craftgui.core.geometry.TextJustification;
import binnie.craftgui.core.CraftGUI;
import forestry.api.genetics.IChromosomeType;
import org.lwjgl.opengl.GL11;
import binnie.craftgui.controls.core.Control;
import binnie.Binnie;
import forestry.api.genetics.AlleleManager;
import binnie.craftgui.controls.ControlTextCentered;
import forestry.api.genetics.IIndividual;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.IWidget;

public class AnalystPageGenome extends ControlAnalystPage
{
	boolean active;

	public AnalystPageGenome(final IWidget parent, final IArea area, final boolean active, final IIndividual ind) {
		super(parent, area);
		this.active = active;
		this.setColour(26265);
		int y = 4;
		new ControlTextCentered(this, y, "§n" + this.getTitle()).setColour(this.getColour());
		y += 16;
		final ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(ind.getClass());
		final BreedingSystem system = Binnie.Genetics.getSystem(root);
		final Control scaled = new Control(this, 0.0f, y, 0.0f, 0.0f) {
			@Override
			public void onRenderBackground() {
				GL11.glPushMatrix();
				GL11.glTranslatef(10.0f, -15.0f, 0.0f);
				GL11.glScalef(0.9f, 0.95f, 1.0f);
			}

			@Override
			public void onRenderForeground() {
				GL11.glPopMatrix();
			}
		};
		for (final IChromosomeType chromo : system.getActiveKaryotype()) {
			final IAllele allele = active ? ind.getGenome().getActiveAllele(chromo) : ind.getGenome().getInactiveAllele(chromo);
			final String alleleName = system.getAlleleName(chromo, allele);
			final float height = CraftGUI.Render.textHeight(alleleName, this.w() / 2.0f - 2.0f);
			new ControlText(scaled, new IArea(0.0f, y + (height - 9.0f) / 2.0f, this.w() / 2.0f - 2.0f, 0.0f), system.getChromosomeShortName(chromo) + " :", TextJustification.TopRight).setColour(this.getColour());
			new ControlText(scaled, new IArea(this.w() / 2.0f + 2.0f, y, this.w() / 2.0f - 2.0f, 0.0f), alleleName, TextJustification.TopLeft).setColour(this.getColour());
			y += (int) (3.0f + height);
		}
		this.setSize(new IPoint(this.w(), y + 8));
	}

	@Override
	public String getTitle() {
		return (this.active ? "Active" : "Inactive") + " Genome";
	}
}
