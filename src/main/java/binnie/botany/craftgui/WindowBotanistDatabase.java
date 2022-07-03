package binnie.botany.craftgui;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.botany.api.IFlowerColor;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.core.AbstractMod;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.listbox.ControlListBox;
import binnie.core.craftgui.database.DatabaseTab;
import binnie.core.craftgui.database.IDatabaseMode;
import binnie.core.craftgui.database.PageBranchOverview;
import binnie.core.craftgui.database.PageBranchSpecies;
import binnie.core.craftgui.database.PageBreeder;
import binnie.core.craftgui.database.PageSpeciesClassification;
import binnie.core.craftgui.database.PageSpeciesMutations;
import binnie.core.craftgui.database.PageSpeciesOverview;
import binnie.core.craftgui.database.PageSpeciesResultant;
import binnie.core.craftgui.database.WindowAbstractDatabase;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.util.I18N;
import cpw.mods.fml.relauncher.Side;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;

public class WindowBotanistDatabase extends WindowAbstractDatabase {
    public WindowBotanistDatabase(EntityPlayer player, Side side, boolean nei) {
        super(player, side, nei, Binnie.Genetics.flowerBreedingSystem, 130.0f);
    }

    public static Window create(EntityPlayer player, Side side, boolean nei) {
        return new WindowBotanistDatabase(player, side, nei);
    }

    @Override
    protected void addTabs() {
        new PageSpeciesOverview(getInfoPages(Mode.Species), new DatabaseTab(Botany.instance, "species.overview"));
        new PageSpeciesFlowerGenome(getInfoPages(Mode.Species), new DatabaseTab(Botany.instance, "genome"));
        new PageSpeciesClassification(
                getInfoPages(Mode.Species), new DatabaseTab(Botany.instance, "species.classification"));
        new PageSpeciesResultant(getInfoPages(Mode.Species), new DatabaseTab(Botany.instance, "species.resultant"));
        new PageSpeciesMutations(getInfoPages(Mode.Species), new DatabaseTab(Botany.instance, "species.further"));
        new PageBranchOverview(getInfoPages(Mode.Branches), new DatabaseTab(Botany.instance, "branches.overview"));
        new PageBranchSpecies(getInfoPages(Mode.Branches), new DatabaseTab(Botany.instance, "branches.species"));
        createMode(FlowerMode.COLOR, new ColorModeWidget());
        new PageColorMixResultant(getInfoPages(FlowerMode.COLOR), new DatabaseTab(Botany.instance, "colour.resultant"));
        new PageColorMix(getInfoPages(FlowerMode.COLOR), new DatabaseTab(Botany.instance, "colour.further"));
        new PageBreeder(getInfoPages(Mode.Breeder), getUsername(), new DatabaseTab(Botany.instance, "breeder"));
    }

    @Override
    protected AbstractMod getMod() {
        return Botany.instance;
    }

    @Override
    protected String getName() {
        return I18N.localise("botany.gui.database.name");
    }

    enum FlowerMode implements IDatabaseMode {
        COLOR;

        @Override
        public String getName() {
            return I18N.localise("botany.gui.database.tab." + name().toLowerCase());
        }
    }

    private class ColorModeWidget extends ModeWidgets {
        public ColorModeWidget() {
            super(FlowerMode.COLOR, WindowBotanistDatabase.this);
        }

        @Override
        public void createListBox(IArea area) {
            listBox = new ControlListBox<IFlowerColor>(modePage, area.x(), area.y(), area.w(), area.h(), 12.0f) {
                @Override
                public IWidget createOption(IFlowerColor value, int y) {
                    return new ControlColorOption(getContent(), value, y);
                }
            };
            List<IFlowerColor> colors = new ArrayList<>();
            Collections.addAll(colors, EnumFlowerColor.values());
            listBox.setOptions(colors);
        }
    }
}
