package binnie.extratrees.craftgui.dictionary;

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
import binnie.extratrees.ExtraTrees;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class WindowArboristDatabase extends WindowAbstractDatabase {
	protected ControlListBox<ItemStack> selectionBoxFruit;
	protected ControlListBox<ItemStack> selectionBoxWood;
	protected ControlListBox<ItemStack> selectionBoxPlanks;

	public WindowArboristDatabase(EntityPlayer player, Side side, boolean nei) {
		super(player, side, nei, Binnie.Genetics.treeBreedingSystem, 120.0f);
	}

	public static Window create(EntityPlayer player, Side side, boolean nei) {
		return new WindowArboristDatabase(player, side, nei);
	}

	@Override
	protected void addTabs() {
		new PageSpeciesOverview(getInfoPages(Mode.Species), new DatabaseTab(ExtraTrees.instance, "species.overview", 0));
		new PageSpeciesTreeGenome(getInfoPages(Mode.Species), new DatabaseTab(ExtraTrees.instance, "species.genome", 0));
		new PageSpeciesClassification(getInfoPages(Mode.Species), new DatabaseTab(ExtraTrees.instance, "species.classification", 0));
		new PageSpeciesResultant(getInfoPages(Mode.Species), new DatabaseTab(ExtraTrees.instance, "species.resultant", 0));
		new PageSpeciesMutations(getInfoPages(Mode.Species), new DatabaseTab(ExtraTrees.instance, "species.further", 0));
		new PageBranchOverview(getInfoPages(Mode.Branches), new DatabaseTab(ExtraTrees.instance, "branches.overview", 0));
		new PageBranchSpecies(getInfoPages(Mode.Branches), new DatabaseTab(ExtraTrees.instance, "branches.species", 0));
		new PageBreeder(getInfoPages(Mode.Breeder), getUsername(), new DatabaseTab(ExtraTrees.instance, "breeder", 0));
		createMode(TreeMode.Fruit, new ModeWidgets(TreeMode.Fruit, this) {
			@Override
			public void createListBox(IArea area) {
				(listBox = new ControlListBox<ItemStack>(modePage, area.x(), area.y(), area.w(), area.h(), 12.0f) {
					@Override
					public IWidget createOption(ItemStack value, int y) {
						return new ControlItemStackOption(getContent(), value, y);
					}
				}).setOptions(((TreeBreedingSystem) getBreedingSystem()).allFruits);
			}
		});
		createMode(TreeMode.Wood, new ModeWidgets(TreeMode.Wood, this) {
			@Override
			public void createListBox(IArea area) {
				(listBox = new ControlListBox<ItemStack>(modePage, area.x(), area.y(), area.w(), area.h(), 12.0f) {
					@Override
					public IWidget createOption(ItemStack value, int y) {
						return new ControlItemStackOption(getContent(), value, y);
					}
				}).setOptions(((TreeBreedingSystem) getBreedingSystem()).allWoods);
			}
		});
		createMode(TreeMode.Planks, new ModeWidgets(TreeMode.Planks, this) {
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
		new PageFruit(getInfoPages(TreeMode.Fruit), new DatabaseTab(ExtraTrees.instance, "fruit.natural", 0), true);
		new PageFruit(getInfoPages(TreeMode.Fruit), new DatabaseTab(ExtraTrees.instance, "fruit.potential", 0), false);
		new PageWood(getInfoPages(TreeMode.Wood), new DatabaseTab(ExtraTrees.instance, "wood.natural", 0));
		new PagePlanksOverview(getInfoPages(TreeMode.Planks), new DatabaseTab(ExtraTrees.instance, "planks.overview", 0));
		new PagePlanksTrees(getInfoPages(TreeMode.Planks), new DatabaseTab(ExtraTrees.instance, "planks.natural", 1));
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
			return ExtraTrees.proxy.localise("gui.database.mode." + name().toLowerCase());
		}
	}
}
