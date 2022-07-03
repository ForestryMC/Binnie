package binnie.extratrees.craftgui;

import binnie.Binnie;
import binnie.core.AbstractMod;
import binnie.core.craftgui.database.DatabaseTab;
import binnie.core.craftgui.database.PageBranchOverview;
import binnie.core.craftgui.database.PageBranchSpecies;
import binnie.core.craftgui.database.PageBreeder;
import binnie.core.craftgui.database.PageSpeciesClassification;
import binnie.core.craftgui.database.PageSpeciesMutations;
import binnie.core.craftgui.database.PageSpeciesOverview;
import binnie.core.craftgui.database.PageSpeciesResultant;
import binnie.core.craftgui.database.WindowAbstractDatabase;
import binnie.core.craftgui.minecraft.Window;
import binnie.extratrees.ExtraTrees;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;

public class WindowLepidopteristDatabase extends WindowAbstractDatabase {
    public WindowLepidopteristDatabase(EntityPlayer player, Side side, boolean nei) {
        super(player, side, nei, Binnie.Genetics.mothBreedingSystem, 160.0f);
    }

    public static Window create(EntityPlayer player, Side side, boolean nei) {
        return new WindowLepidopteristDatabase(player, side, nei);
    }

    @Override
    protected void addTabs() {
        new PageSpeciesOverview(
                getInfoPages(Mode.Species), new DatabaseTab(ExtraTrees.instance, "butterfly.species.overview"));
        new PageSpeciesClassification(
                getInfoPages(Mode.Species), new DatabaseTab(ExtraTrees.instance, "butterfly.species.classification"));
        new PageSpeciesImage(
                getInfoPages(Mode.Species), new DatabaseTab(ExtraTrees.instance, "butterfly.species.specimen"));
        new PageSpeciesResultant(
                getInfoPages(Mode.Species), new DatabaseTab(ExtraTrees.instance, "butterfly.species.resultant"));
        new PageSpeciesMutations(
                getInfoPages(Mode.Species), new DatabaseTab(ExtraTrees.instance, "butterfly.species.further"));
        new PageBranchOverview(
                getInfoPages(Mode.Branches), new DatabaseTab(ExtraTrees.instance, "butterfly.branches.overview"));
        new PageBranchSpecies(
                getInfoPages(Mode.Branches), new DatabaseTab(ExtraTrees.instance, "butterfly.branches.species"));
        new PageBreeder(
                getInfoPages(Mode.Breeder), getUsername(), new DatabaseTab(ExtraTrees.instance, "butterfly.breeder"));
    }

    @Override
    protected AbstractMod getMod() {
        return ExtraTrees.instance;
    }

    @Override
    protected String getName() {
        return "MothDatabase";
    }
}
