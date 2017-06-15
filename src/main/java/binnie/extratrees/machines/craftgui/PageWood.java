package binnie.extratrees.machines.craftgui;

import java.util.Collection;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.genetics.IAlleleSpecies;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.database.ControlSpeciesBox;
import binnie.core.craftgui.database.DatabaseTab;
import binnie.core.craftgui.database.PageAbstract;
import binnie.core.craftgui.database.WindowAbstractDatabase;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.genetics.TreeBreedingSystem;

@SideOnly(Side.CLIENT)
public class PageWood extends PageAbstract<ItemStack> {
	public PageWood(final IWidget parent, final DatabaseTab tab) {
		super(parent, tab);
	}

	@Override
	public void onValueChanged(final ItemStack species) {
		this.deleteAllChildren();
		final WindowAbstractDatabase database = Window.get(this);
		new ControlText(this, new Area(0, 0, this.size().x(), 24), this.getValue().toString(), TextJustification.MiddleCenter);
		final Collection<IAlleleSpecies> trees = ((TreeBreedingSystem) database.getBreedingSystem()).getTreesThatHaveWood(species, database.isNEI(), database.getWorld(), database.getUsername());
		new ControlSpeciesBox(this, 4, 24, this.size().x() - 8, this.size().y() - 4 - 24).setOptions(trees);
	}
}
