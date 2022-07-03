package binnie.extratrees.craftgui;

import binnie.Binnie;
import binnie.core.AbstractMod;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.listbox.ControlListBox;
import binnie.core.craftgui.database.ControlItemStackOption;
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
import binnie.core.genetics.TreeBreedingSystem;
import binnie.core.util.I18N;
import binnie.extratrees.ExtraTrees;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class WindowArboristDatabase extends WindowAbstractDatabase {
    public WindowArboristDatabase(EntityPlayer player, Side side, boolean nei) {
        super(player, side, nei, Binnie.Genetics.treeBreedingSystem, 120.0f);
    }

    public static Window create(EntityPlayer player, Side side, boolean nei) {
        return new WindowArboristDatabase(player, side, nei);
    }

    @Override
    protected void addTabs() {
        new PageSpeciesOverview(getInfoPages(Mode.Species), new DatabaseTab(ExtraTrees.instance, "species.overview"));
        new PageSpeciesTreeGenome(getInfoPages(Mode.Species), new DatabaseTab(ExtraTrees.instance, "species.genome"));
        new PageSpeciesClassification(
                getInfoPages(Mode.Species), new DatabaseTab(ExtraTrees.instance, "species.classification"));
        new PageSpeciesResultant(getInfoPages(Mode.Species), new DatabaseTab(ExtraTrees.instance, "species.resultant"));
        new PageSpeciesMutations(getInfoPages(Mode.Species), new DatabaseTab(ExtraTrees.instance, "species.further"));
        new PageBranchOverview(getInfoPages(Mode.Branches), new DatabaseTab(ExtraTrees.instance, "branches.overview"));
        new PageBranchSpecies(getInfoPages(Mode.Branches), new DatabaseTab(ExtraTrees.instance, "branches.species"));
        new PageBreeder(getInfoPages(Mode.Breeder), getUsername(), new DatabaseTab(ExtraTrees.instance, "breeder"));
        createMode(TreeMode.FRUIT, new ModeWidgets(TreeMode.FRUIT, this) {
            @Override
            public void createListBox(IArea area) {
                (listBox = new ControlListBox<ItemStack>(modePage, area.x(), area.y(), area.w(), area.h(), 12.0f) {
                            @Override
                            public IWidget createOption(ItemStack value, int y) {
                                return new ControlItemStackOption(getContent(), value, y);
                            }
                        })
                        .setOptions(((TreeBreedingSystem) getBreedingSystem()).allFruits);
            }
        });
        createMode(TreeMode.WOOD, new ModeWidgets(TreeMode.WOOD, this) {
            @Override
            public void createListBox(IArea area) {
                (listBox = new ControlListBox<ItemStack>(modePage, area.x(), area.y(), area.w(), area.h(), 12.0f) {
                            @Override
                            public IWidget createOption(ItemStack value, int y) {
                                return new ControlItemStackOption(getContent(), value, y);
                            }
                        })
                        .setOptions(((TreeBreedingSystem) getBreedingSystem()).allWoods);
            }
        });
        createMode(TreeMode.PLANKS, new ModeWidgets(TreeMode.PLANKS, this) {
            @Override
            public void createListBox(IArea area) {
                listBox = new ControlListBox<ItemStack>(modePage, area.x(), area.y(), area.w(), area.h(), 12.0f) {
                    @Override
                    public IWidget createOption(ItemStack value, int y) {
                        return new ControlItemStackOption(getContent(), value, y);
                    }
                };
            }
        });
        new PageFruit(getInfoPages(TreeMode.FRUIT), new DatabaseTab(ExtraTrees.instance, "fruit.natural"), true);
        new PageFruit(getInfoPages(TreeMode.FRUIT), new DatabaseTab(ExtraTrees.instance, "fruit.potential"), false);
        new PageWood(getInfoPages(TreeMode.WOOD), new DatabaseTab(ExtraTrees.instance, "wood.natural"));
        new PagePlanksOverview(getInfoPages(TreeMode.PLANKS), new DatabaseTab(ExtraTrees.instance, "planks.overview"));
        new PagePlanksTrees(getInfoPages(TreeMode.PLANKS), new DatabaseTab(ExtraTrees.instance, "planks.natural"));
    }

    @Override
    protected AbstractMod getMod() {
        return ExtraTrees.instance;
    }

    @Override
    protected String getName() {
        return "TreeDatabase";
    }

    enum TreeMode implements IDatabaseMode {
        FRUIT,
        WOOD,
        PLANKS;

        @Override
        public String getName() {
            return I18N.localise("extratrees.gui.database.mode." + name().toLowerCase());
        }
    }
}
