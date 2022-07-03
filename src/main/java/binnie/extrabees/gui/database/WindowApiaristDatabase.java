package binnie.extrabees.gui.database;

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
import binnie.core.util.I18N;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.gui.database.product.PageSpeciesProducts;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;

public class WindowApiaristDatabase extends WindowAbstractDatabase {
    public WindowApiaristDatabase(EntityPlayer player, Side side, boolean nei) {
        super(player, side, nei, Binnie.Genetics.beeBreedingSystem, 110.0f);
    }

    public static Window create(EntityPlayer player, Side side, boolean nei) {
        return new WindowApiaristDatabase(player, side, nei);
    }

    @Override
    protected void addTabs() {
        new PageSpeciesOverview(getInfoPages(Mode.Species), new DatabaseTab(ExtraBees.instance, "species.overview"));
        new PageSpeciesClassification(
                getInfoPages(Mode.Species), new DatabaseTab(ExtraBees.instance, "species.classification"));
        new PageSpeciesGenome(getInfoPages(Mode.Species), new DatabaseTab(ExtraBees.instance, "species.genome"));
        new PageSpeciesProducts(getInfoPages(Mode.Species), new DatabaseTab(ExtraBees.instance, "species.products"));
        new PageSpeciesClimate(getInfoPages(Mode.Species), new DatabaseTab(ExtraBees.instance, "species.climate"));
        new PageSpeciesResultant(getInfoPages(Mode.Species), new DatabaseTab(ExtraBees.instance, "species.resultant"));
        new PageSpeciesMutations(getInfoPages(Mode.Species), new DatabaseTab(ExtraBees.instance, "species.further"));
        new PageBranchOverview(getInfoPages(Mode.Branches), new DatabaseTab(ExtraBees.instance, "branches.overview"));
        new PageBranchSpecies(getInfoPages(Mode.Branches), new DatabaseTab(ExtraBees.instance, "branches.species"));
        new PageBreeder(getInfoPages(Mode.Breeder), getUsername(), new DatabaseTab(ExtraBees.instance, "breeder"));
    }

    @Override
    public AbstractMod getMod() {
        return ExtraBees.instance;
    }

    @Override
    public String getName() {
        return I18N.localise("extrabees.gui.database");
    }
}
