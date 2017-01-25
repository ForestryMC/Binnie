package binnie.extratrees.machines.craftgui;

import binnie.core.genetics.TreeBreedingSystem;
import binnie.craftgui.controls.ControlText;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.TextJustification;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.mod.database.ControlSpeciesBox;
import binnie.craftgui.mod.database.DatabaseTab;
import binnie.craftgui.mod.database.PageAbstract;
import binnie.craftgui.mod.database.WindowAbstractDatabase;
import binnie.extratrees.ExtraTrees;
import forestry.api.genetics.IAlleleSpecies;
import net.minecraft.item.ItemStack;

import java.util.Collection;

public class PageFruit extends PageAbstract<ItemStack> {
	boolean treesThatBearFruit;

	public PageFruit(final IWidget parent, final DatabaseTab tab, final boolean treesThatBearFruit) {
		super(parent, tab);
		this.treesThatBearFruit = treesThatBearFruit;
	}

	@Override
	public void onValueChanged(final ItemStack species) {
		this.deleteAllChildren();
		final WindowAbstractDatabase database = Window.get(this);
		new ControlText(this, new IArea(0, 0, this.size().x(), 24), ExtraTrees.proxy.localise("gui.database.tab.fruit." + (this.treesThatBearFruit ? "natural" : "potential")), TextJustification.MiddleCenter);
		final Collection<IAlleleSpecies> trees = this.treesThatBearFruit ? ((TreeBreedingSystem) database.getBreedingSystem()).getTreesThatBearFruit(species, database.isNEI(), database.getWorld(), database.getUsername()) : ((TreeBreedingSystem) database.getBreedingSystem()).getTreesThatCanBearFruit(species, database.isNEI(), database.getWorld(), database.getUsername());
		new ControlSpeciesBox(this, 4, 24, this.size().x() - 8, this.size().y() - 4 - 24).setOptions(trees);
	}
}
