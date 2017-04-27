// 
// Decompiled by Procyon v0.5.30
// 

package binnie.genetics.gui;

import forestry.api.genetics.IAllele;
import binnie.core.genetics.BreedingSystem;
import forestry.api.genetics.ISpeciesRoot;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.craftgui.CraftGUI;
import forestry.api.genetics.IChromosomeType;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;
import binnie.core.craftgui.controls.core.Control;
import binnie.Binnie;
import forestry.api.genetics.AlleleManager;
import binnie.core.craftgui.controls.ControlTextCentered;
import forestry.api.genetics.IIndividual;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.IWidget;

public class AnalystPageGenome extends ControlAnalystPage
{
	boolean active;

	public AnalystPageGenome(final IWidget parent, final IArea area, final boolean active, final IIndividual ind) {
		super(parent, area);
		this.active = active;
		this.setColor(26265);
		int y = 4;
		new ControlTextCentered(this, y, EnumChatFormatting.UNDERLINE + this.getTitle()).setColor(this.getColor());
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
			new ControlText(scaled, new IArea(0.0f, y + (height - 9.0f) / 2.0f, this.w() / 2.0f - 2.0f, 0.0f), system.getChromosomeShortName(chromo) + " :", TextJustification.TopRight).setColor(this.getColor());
			new ControlText(scaled, new IArea(this.w() / 2.0f + 2.0f, y, this.w() / 2.0f - 2.0f, 0.0f), alleleName, TextJustification.TopLeft).setColor(this.getColor());
			y += (int) (3.0f + height);
		}
		this.setSize(new IPoint(this.w(), y + 8));
	}

	@Override
	public String getTitle() {
		return (this.active ? "Active" : "Inactive") + " Genome";
	}
}
