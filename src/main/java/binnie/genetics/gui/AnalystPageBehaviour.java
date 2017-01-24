package binnie.genetics.gui;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.craftgui.controls.ControlText;
import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.TextJustification;
import forestry.api.apiculture.IAlleleBeeEffect;
import forestry.api.apiculture.IBee;
import forestry.api.genetics.IIndividual;
import forestry.api.lepidopterology.EnumButterflyChromosome;
import forestry.api.lepidopterology.IAlleleButterflyEffect;
import forestry.api.lepidopterology.IButterfly;
import forestry.apiculture.PluginApiculture;
import net.minecraft.util.math.Vec3i;

public class AnalystPageBehaviour extends ControlAnalystPage {
	public AnalystPageBehaviour(final IWidget parent, final IArea area, final IIndividual ind) {
		super(parent, area);
		this.setColour(6684723);
		int y = 4;
		new ControlTextCentered(this, y, "§nBehaviour").setColour(this.getColour());
		y += 12;
		if (ind instanceof IBee) {
			final IBee bee = (IBee) ind;
			y += 8;
			final int fertility = bee.getGenome().getFlowering();
			new ControlTextCentered(this, y, "Pollinates nearby\n" + bee.getGenome().getFlowerProvider().getDescription()).setColour(this.getColour());
			y += 20;
			new ControlTextCentered(this, y, "§oEvery " + this.getTimeString(PluginApiculture.ticksPerBeeWorkCycle * 100.0f / fertility)).setColour(this.getColour());
			y += 22;
			final IAlleleBeeEffect effect = bee.getGenome().getEffect();
			final Vec3i t = bee.getGenome().getTerritory();
			if (!effect.getUID().contains("None")) {
				final String effectDesc = BinnieCore.proxy.localiseOrBlank("allele." + effect.getUID() + ".desc");
				final String loc = effectDesc.equals("") ? ("Effect: " + effect.getName()) : effectDesc;
				new ControlText(this, new IArea(4.0f, y, this.w() - 8.0f, 0.0f), loc, TextJustification.TopCenter).setColour(this.getColour());
				y += (int) (CraftGUI.render.textHeight(loc, this.w() - 8.0f) + 1.0f);
				new ControlTextCentered(this, y, "§oWithin " + (int) (t.getX() / 2.0f) + " blocks").setColour(this.getColour());
				y += 22;
			}
			new ControlTextCentered(this, y, "Territory: §o" + t.getX() + "x" + t.getY() + "x" + t.getZ()).setColour(this.getColour());
			y += 22;
		}
		if (ind instanceof IButterfly) {
			final IButterfly bee2 = (IButterfly) ind;
			new ControlTextCentered(this, y, "§oMetabolism: " + Binnie.GENETICS.mothBreedingSystem.getAlleleName(EnumButterflyChromosome.METABOLISM, ind.getGenome().getActiveAllele(EnumButterflyChromosome.METABOLISM))).setColour(this.getColour());
			y += 20;
			new ControlTextCentered(this, y, "Pollinates nearby\n" + bee2.getGenome().getFlowerProvider().getDescription()).setColour(this.getColour());
			y += 20;
			new ControlTextCentered(this, y, "§oEvery " + this.getTimeString(1500.0f)).setColour(this.getColour());
			y += 22;
			final IAlleleButterflyEffect effect2 = bee2.getGenome().getEffect();
			if (!effect2.getUID().contains("None")) {
				final String effectDesc2 = BinnieCore.proxy.localiseOrBlank("allele." + effect2.getUID() + ".desc");
				final String loc2 = effectDesc2.equals("") ? ("Effect: " + effect2.getName()) : effectDesc2;
				new ControlText(this, new IArea(4.0f, y, this.w() - 8.0f, 0.0f), loc2, TextJustification.TopCenter).setColour(this.getColour());
				y += (int) (CraftGUI.render.textHeight(loc2, this.w() - 8.0f) + 1.0f);
				y += 22;
			}
		}
	}

	@Override
	public String getTitle() {
		return "Behaviour";
	}
}
