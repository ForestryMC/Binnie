package binnie.extratrees.genetics.gui.analyst;

import binnie.core.Binnie;
import binnie.core.Constants;
import binnie.core.api.genetics.IBreedingSystem;
import binnie.core.api.gui.IArea;
import binnie.core.api.gui.ITitledWidget;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.ControlTextCentered;
import binnie.core.util.I18N;
import binnie.core.util.TimeUtil;
import binnie.genetics.api.analyst.AnalystConstants;
import binnie.genetics.api.analyst.IAnalystManager;
import binnie.genetics.api.analyst.IAnalystPagePlugin;
import binnie.genetics.api.analyst.IBiologyPlugin;
import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.arboriculture.ITree;
import forestry.api.arboriculture.TreeManager;
import forestry.api.genetics.IIndividual;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class TreeAnalystPagePlugin implements IAnalystPagePlugin<ITree> {
	@Override
	public boolean handles(IIndividual individual) {
		return individual instanceof ITree;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addAnalystPages(ITree individual, IWidget parent, IArea pageSize, List<ITitledWidget> analystPages, IAnalystManager analystManager) {
		analystPages.add(new AnalystPageFruit(parent, pageSize, individual, analystManager));
		analystPages.add(new AnalystPageWood(parent, pageSize, individual, analystManager));
		analystPages.add(analystManager.createBiologyPage(parent, pageSize, individual, new BiologyPlugin()));
		analystPages.add(new AnalystPageGrowth(parent, pageSize, individual));
	}

	private static class BiologyPlugin implements IBiologyPlugin<ITree> {
		@Override
		@SideOnly(Side.CLIENT)
		public int addBiologyPages(ITree tree, IWidget parent, int y, IAnalystManager analystManager) {
			IBreedingSystem treeSystem = Binnie.GENETICS.getSystem(TreeManager.treeRoot);
			String alleleName = treeSystem.getAlleleName(EnumTreeChromosome.SAPPINESS, tree.getGenome().getActiveAllele(EnumTreeChromosome.SAPPINESS));
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
}
