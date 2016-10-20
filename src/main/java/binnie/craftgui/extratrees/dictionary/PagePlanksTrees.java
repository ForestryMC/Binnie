package binnie.craftgui.extratrees.dictionary;

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
import forestry.api.genetics.IAlleleSpecies;
import net.minecraft.item.ItemStack;

import java.util.Collection;

public class PagePlanksTrees extends PageAbstract<ItemStack> {
    public PagePlanksTrees(final IWidget parent, final DatabaseTab tab) {
        super(parent, tab);
    }

    @Override
    public void onValueChanged(final ItemStack species) {
        this.deleteAllChildren();
        final WindowAbstractDatabase database = Window.get(this);
        new ControlText(this, new IArea(0.0f, 0.0f, this.size().x(), 24.0f), species.getDisplayName(), TextJustification.MiddleCenter);
        final Collection<IAlleleSpecies> trees = ((TreeBreedingSystem) database.getBreedingSystem()).getTreesThatMakePlanks(species, database.isNEI(), database.getWorld(), database.getUsername());
        new ControlSpeciesBox(this, 4.0f, 24.0f, this.size().x() - 8.0f, this.size().y() - 4.0f - 24.0f).setOptions(trees);
    }
}
