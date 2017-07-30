package binnie.genetics.gui.database.bee;

import net.minecraft.entity.player.EntityPlayer;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.Binnie;
import binnie.core.AbstractMod;
import binnie.core.craftgui.minecraft.Window;
import binnie.genetics.Genetics;
import binnie.genetics.gui.database.DatabaseTab;
import binnie.genetics.gui.database.PageBranchOverview;
import binnie.genetics.gui.database.PageBranchSpecies;
import binnie.genetics.gui.database.PageBreeder;
import binnie.genetics.gui.database.PageSpeciesClassification;
import binnie.genetics.gui.database.PageSpeciesMutations;
import binnie.genetics.gui.database.PageSpeciesOverview;
import binnie.genetics.gui.database.PageSpeciesResultant;
import binnie.genetics.gui.database.WindowAbstractDatabase;

public class WindowApiaristDatabase extends WindowAbstractDatabase {
	public WindowApiaristDatabase(EntityPlayer player, Side side, boolean nei) {
		super(player, side, nei, Binnie.GENETICS.beeBreedingSystem, 110);
	}

	public static Window create(EntityPlayer player, Side side, boolean nei) {
		return new WindowApiaristDatabase(player, side, nei);
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected void addTabs() {
		new PageSpeciesOverview(getInfoPages(Mode.SPECIES), new DatabaseTab(Genetics.instance, "species.overview", 0));
		new PageSpeciesClassification(getInfoPages(Mode.SPECIES), new DatabaseTab(Genetics.instance, "species.classification", 0));
		new PageSpeciesGenome(getInfoPages(Mode.SPECIES), new DatabaseTab(Genetics.instance, "species.genome", 0));
		new PageSpeciesProducts(getInfoPages(Mode.SPECIES), new DatabaseTab(Genetics.instance, "species.products", 0));
		new PageSpeciesClimate(getInfoPages(Mode.SPECIES), new DatabaseTab(Genetics.instance, "species.climate", 0));
		new PageSpeciesResultant(getInfoPages(Mode.SPECIES), new DatabaseTab(Genetics.instance, "species.resultant", 0));
		new PageSpeciesMutations(getInfoPages(Mode.SPECIES), new DatabaseTab(Genetics.instance, "species.further", 0));
		new PageBranchOverview(getInfoPages(Mode.BRANCHES), new DatabaseTab(Genetics.instance, "branches.overview", 0));
		new PageBranchSpecies(getInfoPages(Mode.BRANCHES), new DatabaseTab(Genetics.instance, "branches.species", 0));
		new PageBreeder(getInfoPages(Mode.BREEDER), getUsername(), new DatabaseTab(Genetics.instance, "breeder", 0));
	}

	@Override
	public AbstractMod getMod() {
		return Genetics.instance;
	}

	@Override
	public String getBackgroundTextureName() {
		return "Database";
	}

	enum SpeciesTab {
		Overview(0x00ff),
		Genome(0xffff00),
		Productivity(0x00ffff),
		Climate(0xff0000),
		ResultantMutations(0xff00ff),
		FurtherMutations(0xff00);

		public int color;

		SpeciesTab(int color) {
			this.color = color;
		}
	}

	enum BranchesTab {
		Overview(0x0000ff),
		Species(0xff0000);

		public int color;

		BranchesTab(int color) {
			this.color = color;
		}
	}
}
