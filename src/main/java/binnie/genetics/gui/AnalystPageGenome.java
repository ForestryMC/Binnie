package binnie.genetics.gui;

import binnie.Binnie;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.genetics.BreedingSystem;
import binnie.genetics.Genetics;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class AnalystPageGenome extends ControlAnalystPage {
	boolean active;

	public AnalystPageGenome(final IWidget parent, final Area area, final boolean active, final IIndividual ind) {
		super(parent, area);
		this.active = active;
		this.setColour(26265);
		int y = 4;
		new ControlTextCentered(this, y, TextFormatting.UNDERLINE + this.getTitle()).setColour(this.getColour());
		y += 16;
		final ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(ind);
		final BreedingSystem system = Binnie.GENETICS.getSystem(root);
		final Control scaled = new Control(this, 0, y, 0, 0) {
			@Override
			@SideOnly(Side.CLIENT)
			public void onRenderBackground(int guiWidth, int guiHeight) {
				GlStateManager.pushMatrix();
				GlStateManager.translate(10.0f, -15.0f, 0.0f);
				GlStateManager.scale(0.9f, 0.95f, 1.0f);
			}

			@Override
			@SideOnly(Side.CLIENT)
			public void onRenderForeground(int guiWidth, int guiHeight) {
				GlStateManager.popMatrix();
			}
		};
		for (final IChromosomeType chromo : system.getActiveKaryotype()) {
			final IAllele allele = active ? ind.getGenome().getActiveAllele(chromo) : ind.getGenome().getInactiveAllele(chromo);
			final String alleleName = system.getAlleleName(chromo, allele);
			final int height = CraftGUI.render.textHeight(alleleName, this.width() / 2 - 2);
			new ControlText(scaled, new Area(0, y + (height - 9) / 2, this.width() / 2 - 2, 0), system.getChromosomeShortName(chromo) + " :", TextJustification.TopRight).setColour(this.getColour());
			new ControlText(scaled, new Area(this.width() / 2 + 2, y, this.width() / 2 - 2, 0), alleleName, TextJustification.TopLeft).setColour(this.getColour());
			y += 3 + height;
		}
		this.setSize(new Point(this.width(), y + 8));
	}

	@Override
	public String getTitle() {
		return Genetics.proxy.localise("gui.analyst.genome." + (this.active ? "active" : "inactive")) + " " + Genetics.proxy.localise("gui.analyst.genome.title");
	}
}
