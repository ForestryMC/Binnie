package binnie.genetics.gui;

import binnie.Binnie;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.util.I18N;
import forestry.api.apiculture.IAlleleBeeEffect;
import forestry.api.apiculture.IBee;
import forestry.api.genetics.IIndividual;
import forestry.api.lepidopterology.EnumButterflyChromosome;
import forestry.api.lepidopterology.IAlleleButterflyEffect;
import forestry.api.lepidopterology.IButterfly;
import forestry.plugins.PluginApiculture;
import net.minecraft.util.EnumChatFormatting;

public class AnalystPageBehaviour extends ControlAnalystPage {
	public AnalystPageBehaviour(IWidget parent, IArea area, IIndividual ind) {
		super(parent, area);
		setColor(6684723);
		int y = 4;
		new ControlTextCentered(this, y, EnumChatFormatting.UNDERLINE + "Behaviour").setColor(getColor());
		y += 12;

		if (ind instanceof IBee) {
			IBee bee = (IBee) ind;
			y += 8;
			int fertility = bee.getGenome().getFlowering();
			new ControlTextCentered(this, y, "Pollinates nearby\n" + bee.getGenome().getFlowerProvider().getDescription()).setColor(getColor());
			y += 20;
			new ControlTextCentered(this, y, EnumChatFormatting.ITALIC + "Every " + getTimeString(PluginApiculture.ticksPerBeeWorkCycle * 100.0f / fertility)).setColor(getColor());
			y += 22;
			IAlleleBeeEffect effect = bee.getGenome().getEffect();
			int[] t = bee.getGenome().getTerritory();
			if (!effect.getUID().contains("None")) {
				String effectDesc = I18N.localiseOrBlank("binniecore.allele." + effect.getUID() + ".desc");
				String loc = effectDesc.equals("") ? ("Effect: " + effect.getName()) : effectDesc;
				new ControlText(this, new IArea(4.0f, y, w() - 8.0f, 0.0f), loc, TextJustification.TOP_CENTER).setColor(getColor());
				y += (int) (CraftGUI.Render.textHeight(loc, w() - 8.0f) + 1.0f);
				new ControlTextCentered(this, y, EnumChatFormatting.ITALIC + "Within " + (int) (t[0] / 2.0f) + " blocks").setColor(getColor());
				y += 22;
			}
			new ControlTextCentered(this, y, "Territory: " + EnumChatFormatting.ITALIC + t[0] + "x" + t[1] + "x" + t[2]).setColor(getColor());
			y += 22;
		}

		if (ind instanceof IButterfly) {
			IButterfly bee2 = (IButterfly) ind;
			new ControlTextCentered(this, y, EnumChatFormatting.ITALIC + "Metabolism: " + Binnie.Genetics.mothBreedingSystem.getAlleleName(EnumButterflyChromosome.METABOLISM, ind.getGenome().getActiveAllele(EnumButterflyChromosome.METABOLISM))).setColor(getColor());
			y += 20;
			new ControlTextCentered(this, y, "Pollinates nearby\n" + bee2.getGenome().getFlowerProvider().getDescription()).setColor(getColor());
			y += 20;
			new ControlTextCentered(this, y, EnumChatFormatting.ITALIC + "Every " + getTimeString(1500.0f)).setColor(getColor());
			y += 22;
			IAlleleButterflyEffect effect2 = bee2.getGenome().getEffect();
			if (!effect2.getUID().contains("None")) {
				String effectDesc2 = I18N.localiseOrBlank("binniecore.allele." + effect2.getUID() + ".desc");
				String loc2 = effectDesc2.equals("") ? ("Effect: " + effect2.getName()) : effectDesc2;
				new ControlText(this, new IArea(4.0f, y, w() - 8.0f, 0.0f), loc2, TextJustification.TOP_CENTER).setColor(getColor());
				y += (int) (CraftGUI.Render.textHeight(loc2, w() - 8.0f) + 1.0f);
				y += 22;
			}
		}
	}

	@Override
	public String getTitle() {
		return "Behaviour";
	}
}
