package binnie.extratrees.genetics.gui.analyst;

import java.util.List;

import binnie.core.Constants;
import binnie.core.api.gui.IArea;
import binnie.core.api.gui.ITitledWidget;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.ControlTextCentered;
import binnie.core.util.I18N;
import binnie.core.util.TimeUtil;
import binnie.extratrees.modules.ModuleWood;
import binnie.genetics.api.IAnalystPagePlugin;
import binnie.genetics.gui.analyst.AnalystConstants;
import binnie.genetics.gui.analyst.AnalystPageBiology;
import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.arboriculture.ITree;
import forestry.api.genetics.IIndividual;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TreeAnalystPagePlugin implements IAnalystPagePlugin<ITree>, AnalystPageBiology.IBiologyPlugin<ITree> {
	@Override
	public boolean handles(IIndividual individual) {
		return individual instanceof ITree;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addAnalystPages(ITree individual, IWidget parent, IArea pageSize, List<ITitledWidget> analystPages) {
		analystPages.add(new AnalystPageFruit(parent, pageSize, individual));
		analystPages.add(new AnalystPageWood(parent, pageSize, individual));
		analystPages.add(new AnalystPageBiology<>(parent, pageSize, individual, this));
		analystPages.add(new AnalystPageGrowth(parent, pageSize, individual));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int addBiologyPages(ITree tree, IWidget parent, int y) {
		String alleleName = ModuleWood.treeBreedingSystem.getAlleleName(EnumTreeChromosome.SAPPINESS, tree.getGenome().getActiveAllele(EnumTreeChromosome.SAPPINESS));
		new ControlTextCentered(parent, y, TextFormatting.ITALIC + I18N.localise(AnalystConstants.BIOLOGY_KEY + ".sappiness", alleleName))
				.setColor(parent.getColor());
		y += 20;

		int fertility = (int) (1.0f / tree.getGenome().getFertility());
		if (fertility > 1) {
			new ControlTextCentered(parent, y, I18N.localise(AnalystConstants.BIOLOGY_KEY + ".fertility.leaves", fertility))
					.setColor(parent.getColor());
		} else {
			new ControlTextCentered(parent, y, I18N.localise(AnalystConstants.BIOLOGY_KEY + ".fertility.leaf"))
					.setColor(parent.getColor());
		}

		y += 22;
		int butterflySpawn = Math.round(Constants.SPAWN_KOEF / (tree.getGenome().getSappiness() * tree.getGenome().getYield() * 0.5f));
		new ControlTextCentered(parent, y, I18N.localise(AnalystConstants.BIOLOGY_KEY + ".mothSpawn.perLeaf", TimeUtil.getTimeString(butterflySpawn)))
				.setColor(parent.getColor());

		y += 34;
		new ControlTextCentered(parent, y, I18N.localise(AnalystConstants.BIOLOGY_KEY + ".plantTypes")).setColor(parent.getColor());

		y += 12;
		new ControlTextCentered(parent, y, TextFormatting.ITALIC + tree.getGenome().getPrimary().getPlantType().toString())
				.setColor(parent.getColor());
		return y;
	}
}
