package binnie.botany.genetics.gui.analyst;

import java.util.List;

import binnie.botany.api.genetics.IFlower;
import binnie.core.Constants;
import binnie.core.api.gui.IArea;
import binnie.core.api.gui.ITitledWidget;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.ControlTextCentered;
import binnie.core.util.I18N;
import binnie.core.util.TimeUtil;
import binnie.genetics.api.IAnalystPagePlugin;
import binnie.genetics.gui.analyst.AnalystConstants;
import binnie.genetics.gui.analyst.AnalystPageBiology;
import binnie.genetics.gui.analyst.tree.AnalystPageClimate;
import forestry.api.genetics.IIndividual;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FlowerAnalystPagePlugin implements IAnalystPagePlugin<IFlower>, AnalystPageBiology.IBiologyPlugin<IFlower> {
	@Override
	public boolean handles(IIndividual individual) {
		return individual instanceof IFlower;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addAnalystPages(IFlower individual, IWidget parent, IArea pageSize, List<ITitledWidget> analystPages) {
		analystPages.add(new AnalystPageClimate(parent, pageSize, individual));
		analystPages.add(new AnalystPageSoil(parent, pageSize, individual));
		analystPages.add(new AnalystPageBiology<>(parent, pageSize, individual, this));
		analystPages.add(new AnalystPageAppearance(parent, pageSize, individual));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int addBiologyPages(IFlower flower, IWidget parent, int y) {
		y += 10;
		int butterflySpawn2 = Math.round(Constants.SPAWN_KOEF / (flower.getGenome().getSappiness() * 0.2f));
		new ControlTextCentered(parent, y, I18N.localise(AnalystConstants.BIOLOGY_KEY + ".mothSpawn", TimeUtil.getTimeString(butterflySpawn2)))
				.setColor(parent.getColor());
		y += 30;

		int fertility = flower.getGenome().getFertility();
		float chanceDispersal = 0.8f;
		chanceDispersal += 0.2f * fertility;
		if (chanceDispersal > 1.0f) {
			chanceDispersal = 1.0f;
		}

		float chancePollinate = 0.6f;
		chancePollinate += 0.25f * fertility;
		if (chancePollinate > 1.0f) {
			chancePollinate = 1.0f;
		}

		float maxAge = flower.getMaxAge();
		float ageChance = flower.getGenome().getAgeChance();
		float dispersalTime = Constants.SPAWN_KOEF / chanceDispersal;
		float pollinateTime = Constants.SPAWN_KOEF / chancePollinate;
		float lifespan2 = maxAge * 20.0f * 68.27f / ageChance;
		float floweringLifespan = (maxAge - 1) * 20.0f * 68.27f / ageChance - Constants.SPAWN_KOEF;

		new ControlTextCentered(parent, y, I18N.localise(AnalystConstants.BIOLOGY_KEY + ".averageLifespan"))
				.setColor(parent.getColor());
		y += 12;

		new ControlTextCentered(parent, y, TextFormatting.BOLD + TimeUtil.getMCDayString(lifespan2))
				.setColor(parent.getColor());
		y += 22;

		new ControlTextCentered(parent, y, I18N.localise(AnalystConstants.BIOLOGY_KEY + ".seedDispersal"))
				.setColor(parent.getColor());
		y += 12;

		new ControlTextCentered(parent, y, TextFormatting.ITALIC + I18N.localise(AnalystConstants.BIOLOGY_KEY + ".perLifetime", (int) (floweringLifespan / dispersalTime)))
				.setColor(parent.getColor());
		y += 22;

		new ControlTextCentered(parent, y, I18N.localise(AnalystConstants.BIOLOGY_KEY + ".pollination"))
				.setColor(parent.getColor());
		y += 12;

		new ControlTextCentered(parent, y, TextFormatting.ITALIC + I18N.localise(AnalystConstants.BIOLOGY_KEY + ".perLifetime", (int) (floweringLifespan / pollinateTime)))
				.setColor(parent.getColor());
		y += 22;
		return y;
	}
}
