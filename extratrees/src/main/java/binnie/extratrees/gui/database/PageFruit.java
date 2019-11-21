package binnie.extratrees.gui.database;

import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.ControlText;
import binnie.core.gui.database.ControlSpeciesBox;
import binnie.core.gui.database.DatabaseTab;
import binnie.core.gui.database.PageAbstract;
import binnie.core.gui.database.WindowAbstractDatabase;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.TextJustification;
import binnie.core.gui.minecraft.Window;
import binnie.core.util.I18N;
import binnie.genetics.api.ITreeBreedingSystem;
import forestry.api.genetics.IAlleleSpecies;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collection;

@SideOnly(Side.CLIENT)
public class PageFruit extends PageAbstract<ItemStack> {
	private final boolean treesThatBearFruit;

	public PageFruit(final IWidget parent, final DatabaseTab tab, final boolean treesThatBearFruit) {
		super(parent, tab);
		this.treesThatBearFruit = treesThatBearFruit;
	}

	@Override
	public void onValueChanged(final ItemStack species) {
		this.deleteAllChildren();
		final WindowAbstractDatabase database = Window.get(this);
		new ControlText(this, new Area(0, 0, this.getSize().xPos(), 24), I18N.localise("extratrees.gui.database.tab.fruit." + (this.treesThatBearFruit ? "natural" : "potential")), TextJustification.MIDDLE_CENTER);
		ITreeBreedingSystem breedingSystem = (ITreeBreedingSystem) database.getBreedingSystem();
		final Collection<IAlleleSpecies> trees;
		if (this.treesThatBearFruit) {
			trees = breedingSystem.getTreesThatBearFruit(species, database.isMaster(), database.getWorld(), database.getUsername());
		} else {
			trees = breedingSystem.getTreesThatCanBearFruit(species, database.isMaster(), database.getWorld(), database.getUsername());
		}
		new ControlSpeciesBox(this, 4, 24, this.getSize().xPos() - 8, this.getSize().yPos() - 4 - 24).setOptions(trees);
	}
}
