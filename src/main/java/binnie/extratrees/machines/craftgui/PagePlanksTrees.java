package binnie.extratrees.machines.craftgui;

import java.util.Collection;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.genetics.IAlleleSpecies;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.genetics.TreeBreedingSystem;
import binnie.genetics.gui.database.ControlSpeciesBox;
import binnie.genetics.gui.database.DatabaseTab;
import binnie.genetics.gui.database.PageAbstract;
import binnie.genetics.gui.database.WindowAbstractDatabase;

@SideOnly(Side.CLIENT)
public class PagePlanksTrees extends PageAbstract<ItemStack> {
	public PagePlanksTrees(final IWidget parent, final DatabaseTab tab) {
		super(parent, tab);
	}

	@Override
	public void onValueChanged(final ItemStack species) {
		this.deleteAllChildren();
		final WindowAbstractDatabase database = Window.get(this);
		new ControlText(this, new Area(0, 0, this.size().x(), 24), species.getDisplayName(), TextJustification.MIDDLE_CENTER);
		final Collection<IAlleleSpecies> trees = ((TreeBreedingSystem) database.getBreedingSystem()).getTreesThatMakePlanks(species, database.isNEI(), database.getWorld(), database.getUsername());
		new ControlSpeciesBox(this, 4, 24, this.size().x() - 8, this.size().y() - 4 - 24).setOptions(trees);
	}
}
