package binnie.genetics.gui.database.bee;

import binnie.extrabees.api.ExtraBeesAPI;
import net.minecraft.entity.player.EntityPlayer;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.AbstractMod;
import binnie.core.gui.database.DatabaseTab;
import binnie.core.gui.database.PageBranchOverview;
import binnie.core.gui.database.PageBranchSpecies;
import binnie.core.gui.database.PageBreeder;
import binnie.core.gui.database.PageSpeciesClassification;
import binnie.core.gui.database.PageSpeciesMutations;
import binnie.core.gui.database.PageSpeciesOverview;
import binnie.core.gui.database.PageSpeciesResultant;
import binnie.core.gui.database.WindowAbstractDatabase;
import binnie.core.gui.minecraft.Window;
import binnie.genetics.Genetics;

public class WindowApiaristDatabase extends WindowAbstractDatabase {
	public WindowApiaristDatabase(EntityPlayer player, Side side, boolean nei) {
		super(player, side, nei, ExtraBeesAPI.beeBreedingSystem, 110);
	}

	public static Window create(EntityPlayer player, Side side, boolean nei) {
		return new WindowApiaristDatabase(player, side, nei);
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected void addTabs() {
		new PageSpeciesOverview(getInfoPages(Mode.SPECIES), new DatabaseTab(Genetics.instance, "species.overview"));
		new PageSpeciesClassification(getInfoPages(Mode.SPECIES), new DatabaseTab(Genetics.instance, "species.classification"));
		new PageSpeciesGenome(getInfoPages(Mode.SPECIES), new DatabaseTab(Genetics.instance, "species.genome"));
		new PageSpeciesProducts(getInfoPages(Mode.SPECIES), new DatabaseTab(Genetics.instance, "species.products"));
		new PageSpeciesClimate(getInfoPages(Mode.SPECIES), new DatabaseTab(Genetics.instance, "species.climate"));
		new PageSpeciesResultant(getInfoPages(Mode.SPECIES), new DatabaseTab(Genetics.instance, "species.resultant"));
		new PageSpeciesMutations(getInfoPages(Mode.SPECIES), new DatabaseTab(Genetics.instance, "species.further"));
		new PageBranchOverview(getInfoPages(Mode.BRANCHES), new DatabaseTab(Genetics.instance, "branches.overview"));
		new PageBranchSpecies(getInfoPages(Mode.BRANCHES), new DatabaseTab(Genetics.instance, "branches.species"));
		new PageBreeder(getInfoPages(Mode.BREEDER), getUsername(), new DatabaseTab(Genetics.instance, "breeder"));
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
