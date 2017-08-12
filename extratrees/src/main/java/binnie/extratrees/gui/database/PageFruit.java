package binnie.extratrees.gui.database;

import java.util.Collection;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.genetics.IAlleleSpecies;

import binnie.extratrees.genetics.TreeBreedingSystem;
import binnie.core.gui.IWidget;
import binnie.core.gui.controls.ControlText;
import binnie.core.gui.database.ControlSpeciesBox;
import binnie.core.gui.database.DatabaseTab;
import binnie.core.gui.database.PageAbstract;
import binnie.core.gui.database.WindowAbstractDatabase;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.TextJustification;
import binnie.core.gui.minecraft.Window;
import binnie.core.util.I18N;

@SideOnly(Side.CLIENT)
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
		new ControlText(this, new Area(0, 0, this.getSize().xPos(), 24), I18N.localise("extratrees.gui.database.tab.fruit." + (this.treesThatBearFruit ? "natural" : "potential")), TextJustification.MIDDLE_CENTER);
		TreeBreedingSystem breedingSystem = (TreeBreedingSystem) database.getBreedingSystem();
		final Collection<IAlleleSpecies> trees;
		if (this.treesThatBearFruit) {
			trees = breedingSystem.getTreesThatBearFruit(species, database.isNEI(), database.getWorld(), database.getUsername());
		} else {
			trees = breedingSystem.getTreesThatCanBearFruit(species, database.isNEI(), database.getWorld(), database.getUsername());
		}
		new ControlSpeciesBox(this, 4, 24, this.getSize().xPos() - 8, this.getSize().yPos() - 4 - 24).setOptions(trees);
	}
}
