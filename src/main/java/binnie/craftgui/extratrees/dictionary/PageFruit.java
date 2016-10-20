// 
// Decompiled by Procyon v0.5.30
// 

package binnie.craftgui.extratrees.dictionary;

import forestry.api.genetics.IAlleleSpecies;
import java.util.Collection;
import binnie.craftgui.mod.database.ControlSpeciesBox;
import binnie.core.genetics.TreeBreedingSystem;
import binnie.craftgui.core.geometry.TextJustification;
import binnie.extratrees.ExtraTrees;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.controls.ControlText;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.mod.database.WindowAbstractDatabase;
import binnie.craftgui.mod.database.DatabaseTab;
import binnie.craftgui.core.IWidget;
import net.minecraft.item.ItemStack;
import binnie.craftgui.mod.database.PageAbstract;

public class PageFruit extends PageAbstract<ItemStack>
{
	boolean treesThatBearFruit;

	public PageFruit(final IWidget parent, final DatabaseTab tab, final boolean treesThatBearFruit) {
		super(parent, tab);
		this.treesThatBearFruit = treesThatBearFruit;
	}

	@Override
	public void onValueChanged(final ItemStack species) {
		this.deleteAllChildren();
		final WindowAbstractDatabase database = Window.get(this);
		new ControlText(this, new IArea(0.0f, 0.0f, this.size().x(), 24.0f), ExtraTrees.proxy.localise("gui.database.tab.fruit." + (this.treesThatBearFruit ? "natural" : "potential")), TextJustification.MiddleCenter);
		final Collection<IAlleleSpecies> trees = this.treesThatBearFruit ? ((TreeBreedingSystem) database.getBreedingSystem()).getTreesThatBearFruit(species, database.isNEI(), database.getWorld(), database.getUsername()) : ((TreeBreedingSystem) database.getBreedingSystem()).getTreesThatCanBearFruit(species, database.isNEI(), database.getWorld(), database.getUsername());
		new ControlSpeciesBox(this, 4.0f, 24.0f, this.size().x() - 8.0f, this.size().y() - 4.0f - 24.0f).setOptions(trees);
	}
}
