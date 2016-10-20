package binnie.craftgui.extratrees.dictionary;

import binnie.Binnie;
import binnie.core.AbstractMod;
import binnie.core.genetics.TreeBreedingSystem;
import binnie.craftgui.controls.listbox.ControlList;
import binnie.craftgui.controls.listbox.ControlListBox;
import binnie.craftgui.controls.scroll.ControlScrollableContent;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.mod.database.*;
import binnie.extratrees.ExtraTrees;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;

public class WindowArboristDatabase extends WindowAbstractDatabase {
    ControlListBox<ItemStack> selectionBoxFruit;
    ControlListBox<ItemStack> selectionBoxWood;
    ControlListBox<ItemStack> selectionBoxPlanks;

    public WindowArboristDatabase(final EntityPlayer player, final Side side, final boolean nei) {
        super(player, side, nei, Binnie.Genetics.treeBreedingSystem, 120.0f);
    }

    public static Window create(final EntityPlayer player, final Side side, final boolean nei) {
        return new WindowArboristDatabase(player, side, nei);
    }

    @Override
    protected void addTabs() {
        new PageSpeciesOverview(this.getInfoPages(Mode.Species), new DatabaseTab(ExtraTrees.instance, "species.overview", 0));
        new PageSpeciesTreeGenome(this.getInfoPages(Mode.Species), new DatabaseTab(ExtraTrees.instance, "species.genome", 0));
        new PageSpeciesClassification(this.getInfoPages(Mode.Species), new DatabaseTab(ExtraTrees.instance, "species.classification", 0));
        new PageSpeciesResultant(this.getInfoPages(Mode.Species), new DatabaseTab(ExtraTrees.instance, "species.resultant", 0));
        new PageSpeciesMutations(this.getInfoPages(Mode.Species), new DatabaseTab(ExtraTrees.instance, "species.further", 0));
        new PageBranchOverview(this.getInfoPages(Mode.Branches), new DatabaseTab(ExtraTrees.instance, "branches.overview", 0));
        new PageBranchSpecies(this.getInfoPages(Mode.Branches), new DatabaseTab(ExtraTrees.instance, "branches.species", 0));
        new PageBreeder(this.getInfoPages(Mode.Breeder), this.getUsername(), new DatabaseTab(ExtraTrees.instance, "breeder", 0));
        this.createMode(TreeMode.Fruit, new ModeWidgets(TreeMode.Fruit, this) {
            @Override
            public void createListBox(final IArea area) {
                (this.listBox = new ControlListBox<ItemStack>(this.modePage, area.x(), area.y(), area.w(), area.h(), 12.0f) {
                    @Override
                    public IWidget createOption(final ItemStack value, final int y) {
                        return new ControlItemStackOption(((ControlScrollableContent<ControlList<ItemStack>>) this).getContent(), value, y);
                    }
                }).setOptions(((TreeBreedingSystem) WindowArboristDatabase.this.getBreedingSystem()).allFruits);
            }
        });
        this.createMode(TreeMode.Wood, new ModeWidgets(TreeMode.Wood, this) {
            @Override
            public void createListBox(final IArea area) {
                (this.listBox = new ControlListBox<ItemStack>(this.modePage, area.x(), area.y(), area.w(), area.h(), 12.0f) {
                    @Override
                    public IWidget createOption(final ItemStack value, final int y) {
                        return new ControlItemStackOption(((ControlScrollableContent<ControlList<ItemStack>>) this).getContent(), value, y);
                    }
                }).setOptions(((TreeBreedingSystem) WindowArboristDatabase.this.getBreedingSystem()).allWoods);
            }
        });
        this.createMode(TreeMode.Planks, new ModeWidgets(TreeMode.Planks, this) {
            @Override
            public void createListBox(final IArea area) {
                this.listBox = new ControlListBox<ItemStack>(this.modePage, area.x(), area.y(), area.w(), area.h(), 12.0f) {
                    @Override
                    public IWidget createOption(final ItemStack value, final int y) {
                        return new ControlItemStackOption(((ControlScrollableContent<ControlList<ItemStack>>) this).getContent(), value, y);
                    }
                };
            }
        });
        new PageFruit(this.getInfoPages(TreeMode.Fruit), new DatabaseTab(ExtraTrees.instance, "fruit.natural", 0), true);
        new PageFruit(this.getInfoPages(TreeMode.Fruit), new DatabaseTab(ExtraTrees.instance, "fruit.potential", 0), false);
        new PageWood(this.getInfoPages(TreeMode.Wood), new DatabaseTab(ExtraTrees.instance, "wood.natural", 0));
        new PagePlanksOverview(this.getInfoPages(TreeMode.Planks), new DatabaseTab(ExtraTrees.instance, "planks.overview", 0));
        new PagePlanksTrees(this.getInfoPages(TreeMode.Planks), new DatabaseTab(ExtraTrees.instance, "planks.natural", 1));
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
        Fruit,
        Wood,
        Planks;

        @Override
        public String getName() {
            return ExtraTrees.proxy.localise("gui.database.mode." + this.name().toLowerCase());
        }
    }
}
