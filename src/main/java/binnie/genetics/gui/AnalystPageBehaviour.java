// 
// Decompiled by Procyon v0.5.30
// 

package binnie.genetics.gui;

import forestry.api.lepidopterology.IAlleleButterflyEffect;
import forestry.api.apiculture.IAlleleBeeEffect;
import forestry.api.lepidopterology.EnumButterflyChromosome;
import binnie.Binnie;
import forestry.api.lepidopterology.IButterfly;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.BinnieCore;
import forestry.plugins.PluginApiculture;
import forestry.api.apiculture.IBee;
import binnie.core.craftgui.controls.ControlTextCentered;
import forestry.api.genetics.IIndividual;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.IWidget;

public class AnalystPageBehaviour extends ControlAnalystPage
{
	public AnalystPageBehaviour(final IWidget parent, final IArea area, final IIndividual ind) {
		super(parent, area);
		this.setColor(6684723);
		int y = 4;
		new ControlTextCentered(this, y, "§nBehaviour").setColor(this.getColor());
		y += 12;
		if (ind instanceof IBee) {
			final IBee bee = (IBee) ind;
			y += 8;
			final int fertility = bee.getGenome().getFlowering();
			new ControlTextCentered(this, y, "Pollinates nearby\n" + bee.getGenome().getFlowerProvider().getDescription()).setColor(this.getColor());
			y += 20;
			new ControlTextCentered(this, y, "§oEvery " + this.getTimeString(PluginApiculture.ticksPerBeeWorkCycle * 100.0f / fertility)).setColor(this.getColor());
			y += 22;
			final IAlleleBeeEffect effect = bee.getGenome().getEffect();
			final int[] t = bee.getGenome().getTerritory();
			if (!effect.getUID().contains("None")) {
				final String effectDesc = BinnieCore.proxy.localiseOrBlank("allele." + effect.getUID() + ".desc");
				final String loc = effectDesc.equals("") ? ("Effect: " + effect.getName()) : effectDesc;
				new ControlText(this, new IArea(4.0f, y, this.w() - 8.0f, 0.0f), loc, TextJustification.TopCenter).setColor(this.getColor());
				y += (int) (CraftGUI.Render.textHeight(loc, this.w() - 8.0f) + 1.0f);
				new ControlTextCentered(this, y, "§oWithin " + (int) (t[0] / 2.0f) + " blocks").setColor(this.getColor());
				y += 22;
			}
			new ControlTextCentered(this, y, "Territory: §o" + t[0] + "x" + t[1] + "x" + t[2]).setColor(this.getColor());
			y += 22;
		}
		if (ind instanceof IButterfly) {
			final IButterfly bee2 = (IButterfly) ind;
			new ControlTextCentered(this, y, "§oMetabolism: " + Binnie.Genetics.mothBreedingSystem.getAlleleName(EnumButterflyChromosome.METABOLISM, ind.getGenome().getActiveAllele(EnumButterflyChromosome.METABOLISM))).setColor(this.getColor());
			y += 20;
			new ControlTextCentered(this, y, "Pollinates nearby\n" + bee2.getGenome().getFlowerProvider().getDescription()).setColor(this.getColor());
			y += 20;
			new ControlTextCentered(this, y, "§oEvery " + this.getTimeString(1500.0f)).setColor(this.getColor());
			y += 22;
			final IAlleleButterflyEffect effect2 = bee2.getGenome().getEffect();
			if (!effect2.getUID().contains("None")) {
				final String effectDesc2 = BinnieCore.proxy.localiseOrBlank("allele." + effect2.getUID() + ".desc");
				final String loc2 = effectDesc2.equals("") ? ("Effect: " + effect2.getName()) : effectDesc2;
				new ControlText(this, new IArea(4.0f, y, this.w() - 8.0f, 0.0f), loc2, TextJustification.TopCenter).setColor(this.getColor());
				y += (int) (CraftGUI.Render.textHeight(loc2, this.w() - 8.0f) + 1.0f);
				y += 22;
			}
		}
	}

	@Override
	public String getTitle() {
		return "Behaviour";
	}
}
