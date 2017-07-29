package binnie.genetics.gui;

import net.minecraft.util.math.Vec3i;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.apiculture.IAlleleBeeEffect;
import forestry.api.apiculture.IBee;
import forestry.api.genetics.IIndividual;
import forestry.api.lepidopterology.EnumButterflyChromosome;
import forestry.api.lepidopterology.IAlleleButterflyEffect;
import forestry.api.lepidopterology.IButterfly;
import forestry.apiculture.PluginApiculture;

import binnie.Binnie;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.util.I18N;

//TODO:localise
@SideOnly(Side.CLIENT)
public class AnalystPageBehaviour extends ControlAnalystPage {
	public AnalystPageBehaviour(IWidget parent, Area area, IIndividual ind) {
		super(parent, area);
		setColor(6684723);
		int y = 4;
		new ControlTextCentered(this, y, "§nBehaviour").setColor(getColor());
		y += 12;
		if (ind instanceof IBee) {
			IBee bee = (IBee) ind;
			y += 8;
			int fertility = bee.getGenome().getFlowering();
			new ControlTextCentered(this, y, "Pollinates nearby\n" + bee.getGenome().getFlowerProvider().getDescription()).setColor(getColor());
			y += 20;
			new ControlTextCentered(this, y, "§oEvery " + getTimeString(PluginApiculture.ticksPerBeeWorkCycle * 100 / fertility)).setColor(getColor());
			y += 22;
			IAlleleBeeEffect effect = bee.getGenome().getEffect();
			Vec3i t = bee.getGenome().getTerritory();
			if (!effect.getUID().contains("None")) {
				String effectDesc = I18N.localiseOrBlank("binniecore.allele." + effect.getUID() + ".desc");
				String loc = effectDesc.equals("") ? ("Effect: " + effect.getName()) : effectDesc;
				new ControlText(this, new Area(4, y, width() - 8, 0), loc, TextJustification.TOP_CENTER).setColor(getColor());
				y += CraftGUI.render.textHeight(loc, width() - 8) + 1;
				new ControlTextCentered(this, y, "§oWithin " + t.getX() / 2 + " blocks").setColor(getColor());
				y += 22;
			}
			new ControlTextCentered(this, y, "Territory: §o" + t.getX() + "x" + t.getY() + "x" + t.getZ()).setColor(getColor());
			y += 22;
		}
		if (ind instanceof IButterfly) {
			IButterfly bee2 = (IButterfly) ind;
			new ControlTextCentered(this, y, "§oMetabolism: " + Binnie.GENETICS.mothBreedingSystem.getAlleleName(EnumButterflyChromosome.METABOLISM, ind.getGenome().getActiveAllele(EnumButterflyChromosome.METABOLISM))).setColor(getColor());
			y += 20;
			new ControlTextCentered(this, y, "Pollinates nearby\n" + bee2.getGenome().getFlowerProvider().getDescription()).setColor(getColor());
			y += 20;
			new ControlTextCentered(this, y, "§oEvery " + getTimeString(1500)).setColor(getColor());
			y += 22;
			IAlleleButterflyEffect effect2 = bee2.getGenome().getEffect();
			if (!effect2.getUID().contains("None")) {
				String effectDesc2 = I18N.localiseOrBlank("binniecore.allele." + effect2.getUID() + ".desc");
				String loc2 = effectDesc2.equals("") ? ("Effect: " + effect2.getName()) : effectDesc2;
				new ControlText(this, new Area(4, y, width() - 8, 0), loc2, TextJustification.TOP_CENTER).setColor(getColor());
				y += CraftGUI.render.textHeight(loc2, width() - 8) + 1;
				y += 22;
			}
		}
	}

	@Override
	public String getTitle() {
		return "Behaviour";
	}
}
