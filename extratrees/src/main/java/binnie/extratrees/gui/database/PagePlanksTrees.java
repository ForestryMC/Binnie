package binnie.extratrees.gui.database;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.mojang.authlib.GameProfile;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.arboriculture.IAlleleTreeSpecies;
import forestry.api.arboriculture.ITreeGenome;
import forestry.api.arboriculture.ITreeRoot;
import forestry.api.arboriculture.TreeManager;
import forestry.api.genetics.IAlleleSpecies;

import binnie.core.Binnie;
import binnie.core.api.genetics.IBreedingSystem;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.ControlText;
import binnie.core.gui.database.ControlSpeciesBox;
import binnie.core.gui.database.DatabaseTab;
import binnie.core.gui.database.PageAbstract;
import binnie.core.gui.database.WindowAbstractDatabase;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.TextJustification;
import binnie.core.gui.minecraft.Window;
import binnie.extratrees.machines.lumbermill.recipes.LumbermillRecipeManager;

@SideOnly(Side.CLIENT)
public class PagePlanksTrees extends PageAbstract<ItemStack> {
	public PagePlanksTrees(final IWidget parent, final DatabaseTab tab) {
		super(parent, tab);
	}

	@Override
	public void onValueChanged(final ItemStack species) {
		this.deleteAllChildren();
		final WindowAbstractDatabase database = Window.get(this);
		new ControlText(this, new Area(0, 0, this.getSize().xPos(), 24), species.getDisplayName(), TextJustification.MIDDLE_CENTER);
		final Collection<IAlleleSpecies> trees = getTreesThatMakePlanks(species, database.isMaster(), database.getWorld(), database.getUsername());
		new ControlSpeciesBox(this, 4, 24, this.getSize().xPos() - 8, this.getSize().yPos() - 4 - 24).setOptions(trees);
	}

	private static Collection<IAlleleSpecies> getTreesThatMakePlanks(ItemStack fruit, boolean master, World world, GameProfile player) {
		if (fruit == null) {
			return new ArrayList<>();
		}
		ITreeRoot treeRoot = TreeManager.treeRoot;
		IBreedingSystem system = Binnie.GENETICS.getSystem(treeRoot);
		final Collection<IAlleleSpecies> set = master ? system.getAllSpecies() : system.getDiscoveredSpecies(world, player);
		final List<IAlleleSpecies> found = new ArrayList<>();
		for (final IAlleleSpecies species : set) {
			final IAlleleTreeSpecies tSpecies = (IAlleleTreeSpecies) species;
			ITreeGenome genome = treeRoot.templateAsGenome(treeRoot.getTemplate(tSpecies));
			IAlleleTreeSpecies treeSpecies = genome.getPrimary();
			final ItemStack woodStack = treeSpecies.getWoodProvider().getWoodStack();
			ItemStack plankProduct = LumbermillRecipeManager.getPlankProduct(woodStack, world);
			if (!plankProduct.isEmpty() && fruit.isItemEqual(plankProduct)) {
				found.add(species);
			}
		}
		return found;
	}
}
