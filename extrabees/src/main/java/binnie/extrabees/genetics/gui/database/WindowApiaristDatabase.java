package binnie.extrabees.genetics.gui.database;

import binnie.core.Constants;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.gui.PageSpeciesClimate;
import binnie.extrabees.gui.PageSpeciesGenome;
import binnie.extrabees.gui.PageSpeciesProducts;
import net.minecraft.entity.player.EntityPlayer;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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

public class WindowApiaristDatabase extends WindowAbstractDatabase {
	public WindowApiaristDatabase(EntityPlayer player, Side side, boolean master) {
		super(player, side, master, ExtraBees.beeBreedingSystem, 110);
	}

	public static Window create(EntityPlayer player, Side side, boolean master) {
		return new WindowApiaristDatabase(player, side, master);
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected void addTabs() {
		new PageSpeciesOverview(getInfoPages(Mode.SPECIES), new DatabaseTab(Constants.GENETICS_MOD_ID, "species.overview"));
		new PageSpeciesClassification(getInfoPages(Mode.SPECIES), new DatabaseTab(Constants.GENETICS_MOD_ID, "species.classification"));
		new PageSpeciesGenome(getInfoPages(Mode.SPECIES), new DatabaseTab(Constants.GENETICS_MOD_ID, "species.genome"));
		new PageSpeciesProducts(getInfoPages(Mode.SPECIES), new DatabaseTab(Constants.GENETICS_MOD_ID,"species.products"));
		new PageSpeciesClimate(getInfoPages(Mode.SPECIES), new DatabaseTab(Constants.GENETICS_MOD_ID, "species.climate"));
		new PageSpeciesResultant(getInfoPages(Mode.SPECIES), new DatabaseTab(Constants.GENETICS_MOD_ID, "species.resultant"));
		new PageSpeciesMutations(getInfoPages(Mode.SPECIES), new DatabaseTab(Constants.GENETICS_MOD_ID, "species.further"));
		new PageBranchOverview(getInfoPages(Mode.BRANCHES), new DatabaseTab(Constants.GENETICS_MOD_ID, "branches.overview"));
		new PageBranchSpecies(getInfoPages(Mode.BRANCHES), new DatabaseTab(Constants.GENETICS_MOD_ID, "branches.species"));
		new PageBreeder(getInfoPages(Mode.BREEDER), getUsername(), new DatabaseTab(Constants.GENETICS_MOD_ID, "breeder"));
	}

	@Override
	public String getModId() {
		return ExtraBees.MODID;
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

		private final int color;

		SpeciesTab(int color) {
			this.color = color;
		}

		public int getColor() {
			return color;
		}
	}

	enum BranchesTab {
		Overview(0x0000ff),
		Species(0xff0000);

		private final int color;

		BranchesTab(int color) {
			this.color = color;
		}

		public int getColor() {
			return color;
		}
	}
}
