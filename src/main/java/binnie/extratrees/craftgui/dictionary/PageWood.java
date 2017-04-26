// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.craftgui.dictionary;

import forestry.api.genetics.IAlleleSpecies;
import java.util.Collection;
import binnie.core.craftgui.database.ControlSpeciesBox;
import binnie.core.genetics.TreeBreedingSystem;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.database.WindowAbstractDatabase;
import binnie.core.craftgui.database.DatabaseTab;
import binnie.core.craftgui.IWidget;
import net.minecraft.item.ItemStack;
import binnie.core.craftgui.database.PageAbstract;

public class PageWood extends PageAbstract<ItemStack>
{
	public PageWood(final IWidget parent, final DatabaseTab tab) {
		super(parent, tab);
	}

	@Override
	public void onValueChanged(final ItemStack species) {
		this.deleteAllChildren();
		final WindowAbstractDatabase database = Window.get(this);
		new ControlText(this, new IArea(0.0f, 0.0f, this.size().x(), 24.0f), this.getValue().toString(), TextJustification.MiddleCenter);
		final Collection<IAlleleSpecies> trees = ((TreeBreedingSystem) database.getBreedingSystem()).getTreesThatHaveWood(species, database.isNEI(), database.getWorld(), database.getUsername());
		new ControlSpeciesBox(this, 4.0f, 24.0f, this.size().x() - 8.0f, this.size().y() - 4.0f - 24.0f).setOptions(trees);
	}
}
