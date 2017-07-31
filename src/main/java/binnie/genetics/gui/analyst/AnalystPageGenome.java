package binnie.genetics.gui.analyst;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.text.TextFormatting;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;

import binnie.Binnie;
import binnie.core.genetics.BreedingSystem;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.IWidget;
import binnie.core.gui.controls.ControlText;
import binnie.core.gui.controls.ControlTextCentered;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.geometry.TextJustification;
import binnie.core.util.I18N;

@SideOnly(Side.CLIENT)
public class AnalystPageGenome extends ControlAnalystPage {
	boolean active;

	public AnalystPageGenome(IWidget parent, Area area, boolean active, IIndividual ind) {
		super(parent, area);
		this.active = active;
		setColor(26265);
		int y = 4;
		int width = width();
		int center = width / 2;
		new ControlTextCentered(this, y, TextFormatting.UNDERLINE + getTitle()).setColor(getColor());
		y += 16;
		ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(ind);
		BreedingSystem system = Binnie.GENETICS.getSystem(root);
		Control scaled = new Control(this, 0, y, 0, 0) {
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
		for (IChromosomeType chromo : system.getActiveKaryotype()) {
			IAllele allele = active ? ind.getGenome().getActiveAllele(chromo) : ind.getGenome().getInactiveAllele(chromo);
			String alleleName = system.getAlleleName(chromo, allele);
			int height = CraftGUI.render.textHeight(alleleName, center - 2);
			new ControlText(scaled, new Area(0, y + (height - 9) / 2, center - 2, 0), system.getChromosomeShortName(chromo) + " :", TextJustification.TOP_RIGHT).setColor(getColor());
			new ControlText(scaled, new Area(center + 2, y, center - 2, 0), alleleName, TextJustification.TOP_LEFT).setColor(getColor());
			y += 3 + height;
		}
		setSize(new Point(width, y + 8));
	}

	@Override
	public String getTitle() {
		String stateKey = AnalystConstants.GENOME_KEY;
		if(active){
			stateKey+=".active";
		}else{
			stateKey+=".inactive";
		}
		return I18N.localise(AnalystConstants.GENOME_KEY + ".title", I18N.localise(stateKey));
	}
}
