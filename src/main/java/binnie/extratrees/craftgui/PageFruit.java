package binnie.extratrees.craftgui;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.database.ControlSpeciesBox;
import binnie.core.craftgui.database.DatabaseTab;
import binnie.core.craftgui.database.PageAbstract;
import binnie.core.craftgui.database.WindowAbstractDatabase;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.genetics.TreeBreedingSystem;
import binnie.core.util.I18N;
import forestry.api.genetics.IAlleleSpecies;
import java.util.Collection;
import net.minecraft.item.ItemStack;

public class PageFruit extends PageAbstract<ItemStack> {
    boolean treesThatBearFruit;

    public PageFruit(IWidget parent, DatabaseTab tab, boolean treesThatBearFruit) {
        super(parent, tab);
        this.treesThatBearFruit = treesThatBearFruit;
    }

    @Override
    public void onValueChanged(ItemStack species) {
        deleteAllChildren();
        WindowAbstractDatabase database = Window.get(this);
        new ControlText(
                this,
                new IArea(0.0f, 0.0f, size().x(), 24.0f),
                I18N.localise("extratrees.gui.database.tab.fruit." + (treesThatBearFruit ? "natural" : "potential")),
                TextJustification.MIDDLE_CENTER);
        Collection<IAlleleSpecies> trees = treesThatBearFruit
                ? ((TreeBreedingSystem) database.getBreedingSystem())
                        .getTreesThatBearFruit(species, database.isNEI(), database.getWorld(), database.getUsername())
                : ((TreeBreedingSystem) database.getBreedingSystem())
                        .getTreesThatCanBearFruit(
                                species, database.isNEI(), database.getWorld(), database.getUsername());
        new ControlSpeciesBox(this, 4.0f, 24.0f, size().x() - 8.0f, size().y() - 4.0f - 24.0f).setOptions(trees);
    }
}
