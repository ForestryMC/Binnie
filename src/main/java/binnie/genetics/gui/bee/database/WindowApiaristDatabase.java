package binnie.genetics.gui.bee.database;

import binnie.Binnie;
import binnie.core.AbstractMod;
import binnie.core.craftgui.database.*;
import binnie.core.craftgui.minecraft.Window;
import binnie.genetics.Genetics;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WindowApiaristDatabase extends WindowAbstractDatabase {
	@Override
	@SideOnly(Side.CLIENT)
	protected void addTabs() {
		new PageSpeciesOverview(this.getInfoPages(Mode.SPECIES), new DatabaseTab(Genetics.instance, "species.overview", 0));
		new PageSpeciesClassification(this.getInfoPages(Mode.SPECIES), new DatabaseTab(Genetics.instance, "species.classification", 0));
		new PageSpeciesGenome(this.getInfoPages(Mode.SPECIES), new DatabaseTab(Genetics.instance, "species.genome", 0));
		new PageSpeciesProducts(this.getInfoPages(Mode.SPECIES), new DatabaseTab(Genetics.instance, "species.products", 0));
		new PageSpeciesClimate(this.getInfoPages(Mode.SPECIES), new DatabaseTab(Genetics.instance, "species.climate", 0));
		new PageSpeciesResultant(this.getInfoPages(Mode.SPECIES), new DatabaseTab(Genetics.instance, "species.resultant", 0));
		new PageSpeciesMutations(this.getInfoPages(Mode.SPECIES), new DatabaseTab(Genetics.instance, "species.further", 0));
		new PageBranchOverview(this.getInfoPages(Mode.BRANCHES), new DatabaseTab(Genetics.instance, "branches.overview", 0));
		new PageBranchSpecies(this.getInfoPages(Mode.BRANCHES), new DatabaseTab(Genetics.instance, "branches.species", 0));
		new PageBreeder(this.getInfoPages(Mode.BREEDER), this.getUsername(), new DatabaseTab(Genetics.instance, "breeder", 0));
	}

	public WindowApiaristDatabase(final EntityPlayer player, final Side side, final boolean nei) {
		super(player, side, nei, Binnie.GENETICS.beeBreedingSystem, 110);
	}

	public static Window create(final EntityPlayer player, final Side side, final boolean nei) {
		return new WindowApiaristDatabase(player, side, nei);
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
		Overview(255),
		Genome(16776960),
		Productivity(65535),
		Climate(16711680),
		ResultantMutations(16711935),
		FurtherMutations(65280);

		public int colour;

		SpeciesTab(final int colour) {
			this.colour = colour;
		}
	}

	enum BranchesTab {
		Overview(255),
		Species(16711680);

		public int colour;

		BranchesTab(final int colour) {
			this.colour = colour;
		}
	}
}
