package binnie.genetics.gui.analyst;

import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.TextFormatting;

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
import binnie.core.gui.CraftGUI;
import binnie.core.gui.IWidget;
import binnie.core.gui.controls.ControlText;
import binnie.core.gui.controls.ControlTextCentered;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.TextJustification;
import binnie.core.util.I18N;

@SideOnly(Side.CLIENT)
public class AnalystPageBehaviour extends ControlAnalystPage {
	public AnalystPageBehaviour(IWidget parent, Area area, IIndividual ind) {
		super(parent, area);
		setColor(0x660033);
		int y = 4;
		new ControlTextCentered(this, y, TextFormatting.UNDERLINE + getTitle())
			.setColor(getColor());
		y += 12;

		if (ind instanceof IBee) {
			IBee bee = (IBee) ind;
			y += 8;
			int fertility = bee.getGenome().getFlowering();
			new ControlTextCentered(this, y, I18N.localise(AnalystConstants.BEHAVIOUR_KEY + ".pollinatesNearby") + "\n" + bee.getGenome().getFlowerProvider().getDescription())
				.setColor(getColor());
			y += 20;

			new ControlTextCentered(this, y, I18N.localise(AnalystConstants.BEHAVIOUR_KEY + ".everyTime", getTimeString(PluginApiculture.ticksPerBeeWorkCycle * 100 / fertility)))
				.setColor(getColor());
			y += 22;

			IAlleleBeeEffect effect = bee.getGenome().getEffect();
			Vec3i t = bee.getGenome().getTerritory();
			if (!effect.getUID().contains("None")) {
				String effectDesc = I18N.localiseOrBlank("binniecore.allele." + effect.getUID() + ".desc");
				String loc = effectDesc.isEmpty()
					? I18N.localise(AnalystConstants.BEHAVIOUR_KEY + ".effect", effect.getAlleleName())
					: effectDesc;

				new ControlText(this, new Area(4, y, width() - 8, 0), loc, TextJustification.TOP_CENTER)
					.setColor(getColor());
				y += (int) (CraftGUI.render.textHeight(loc, width() - 8) + 1.0f);

				new ControlTextCentered(this, y, I18N.localise(AnalystConstants.BEHAVIOUR_KEY + ".withinBlocks", (int) (t.getX() / 2.0f)))
					.setColor(getColor());
				y += 22;
			}

			new ControlTextCentered(this, y, I18N.localise(AnalystConstants.BEHAVIOUR_KEY + ".territory", t.getX(), t.getY(), t.getZ()))
				.setColor(getColor());
			y += 22;
		}

		if (ind instanceof IButterfly) {
			IButterfly bee2 = (IButterfly) ind;
			new ControlTextCentered(this, y, I18N.localise(AnalystConstants.BEHAVIOUR_KEY + ".metabolism", Binnie.GENETICS.mothBreedingSystem.getAlleleName(EnumButterflyChromosome.METABOLISM, ind.getGenome().getActiveAllele(EnumButterflyChromosome.METABOLISM))))
				.setColor(getColor());
			y += 20;

			new ControlTextCentered(this, y, I18N.localise(AnalystConstants.BEHAVIOUR_KEY + ".pollinatesNearby") + "\n" + bee2.getGenome().getFlowerProvider().getDescription())
				.setColor(getColor());
			y += 20;

			new ControlTextCentered(this, y, I18N.localise(AnalystConstants.BEHAVIOUR_KEY + ".everyTime", getTimeString(1500)))
				.setColor(getColor());
			y += 22;

			IAlleleButterflyEffect effect2 = bee2.getGenome().getEffect();
			if (!effect2.getUID().contains("None")) {
				String effectDesc2 = I18N.localiseOrBlank("binniecore.allele." + effect2.getUID() + ".desc");
				String loc2 = effectDesc2.isEmpty()
					? I18N.localise(AnalystConstants.BEHAVIOUR_KEY + ".effect", effect2.getAlleleName())
					: effectDesc2;

				new ControlText(this, new Area(4, y, width() - 8, 0), loc2, TextJustification.TOP_CENTER)
					.setColor(getColor());
			}
		}
	}

	@Override
	public String getTitle() {
		return I18N.localise(AnalystConstants.BEHAVIOUR_KEY + "");
	}
}
