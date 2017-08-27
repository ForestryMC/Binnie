package binnie.extratrees.gui.database;

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
import binnie.extratrees.ExtraTrees;

public class WindowLepidopteristDatabase extends WindowAbstractDatabase {
	public WindowLepidopteristDatabase(EntityPlayer player, Side side, boolean master) {
		super(player, side, master, ExtraTrees.mothBreedingSystem, 160);
	}

	public static Window create(EntityPlayer player, Side side, boolean master) {
		return new WindowLepidopteristDatabase(player, side, master);
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected void addTabs() {
		new PageSpeciesOverview(this.getInfoPages(Mode.SPECIES), new DatabaseTab(ExtraTrees.instance, "butterfly.species.overview"));
		new PageSpeciesClassification(this.getInfoPages(Mode.SPECIES), new DatabaseTab(ExtraTrees.instance, "butterfly.species.classification"));
		new PageSpeciesImage(this.getInfoPages(Mode.SPECIES), new DatabaseTab(ExtraTrees.instance, "butterfly.species.specimen"));
		new PageSpeciesResultant(this.getInfoPages(Mode.SPECIES), new DatabaseTab(ExtraTrees.instance, "butterfly.species.resultant"));
		new PageSpeciesMutations(this.getInfoPages(Mode.SPECIES), new DatabaseTab(ExtraTrees.instance, "butterfly.species.further"));
		new PageBranchOverview(this.getInfoPages(Mode.BRANCHES), new DatabaseTab(ExtraTrees.instance, "butterfly.branches.overview"));
		new PageBranchSpecies(this.getInfoPages(Mode.BRANCHES), new DatabaseTab(ExtraTrees.instance, "butterfly.branches.species"));
		new PageBreeder(this.getInfoPages(Mode.BREEDER), this.getUsername(), new DatabaseTab(ExtraTrees.instance, "butterfly.breeder"));
	}

	@Override
	protected String getModId() {
		return ExtraTrees.instance.getModId();
	}

	@Override
	protected String getBackgroundTextureName() {
		return "MothDatabase";
	}
}
