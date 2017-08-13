package binnie.extratrees.gui.database;

import java.util.stream.Collectors;

import binnie.core.api.gui.IArea;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.AbstractMod;
import binnie.extratrees.genetics.TreeBreedingSystem;
import binnie.core.gui.IWidget;
import binnie.core.gui.controls.listbox.ControlListBox;
import binnie.core.gui.database.ControlItemStackOption;
import binnie.core.gui.database.DatabaseTab;
import binnie.core.gui.database.IDatabaseMode;
import binnie.core.gui.database.PageBranchOverview;
import binnie.core.gui.database.PageBranchSpecies;
import binnie.core.gui.database.PageBreeder;
import binnie.core.gui.database.PageSpeciesClassification;
import binnie.core.gui.database.PageSpeciesMutations;
import binnie.core.gui.database.PageSpeciesOverview;
import binnie.core.gui.database.PageSpeciesResultant;
import binnie.core.gui.database.WindowAbstractDatabase;
import binnie.core.gui.minecraft.Window;
import binnie.core.util.I18N;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.api.IDesignMaterial;
import binnie.extratrees.wood.WoodManager;

public class WindowArboristDatabase extends WindowAbstractDatabase {
	ControlListBox<ItemStack> selectionBoxFruit;
	ControlListBox<ItemStack> selectionBoxWood;
	ControlListBox<ItemStack> selectionBoxPlanks;

	public WindowArboristDatabase(final EntityPlayer player, final Side side, final boolean nei) {
		super(player, side, nei, ExtraTrees.treeBreedingSystem, 120);
	}

	public static Window create(final EntityPlayer player, final Side side, final boolean nei) {
		return new WindowArboristDatabase(player, side, nei);
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected void addTabs() {
		new PageSpeciesOverview(this.getInfoPages(Mode.SPECIES), new DatabaseTab(ExtraTrees.instance, "species.overview"));
		new PageSpeciesTreeGenome(this.getInfoPages(Mode.SPECIES), new DatabaseTab(ExtraTrees.instance, "species.genome"));
		new PageSpeciesClassification(this.getInfoPages(Mode.SPECIES), new DatabaseTab(ExtraTrees.instance, "species.classification"));
		new PageSpeciesResultant(this.getInfoPages(Mode.SPECIES), new DatabaseTab(ExtraTrees.instance, "species.resultant"));
		new PageSpeciesMutations(this.getInfoPages(Mode.SPECIES), new DatabaseTab(ExtraTrees.instance, "species.further"));
		new PageBranchOverview(this.getInfoPages(Mode.BRANCHES), new DatabaseTab(ExtraTrees.instance, "branches.overview"));
		new PageBranchSpecies(this.getInfoPages(Mode.BRANCHES), new DatabaseTab(ExtraTrees.instance, "branches.species"));
		new PageBreeder(this.getInfoPages(Mode.BREEDER), this.getUsername(), new DatabaseTab(ExtraTrees.instance, "breeder"));
		this.createMode(TreeMode.FRUIT, new ModeWidgets(TreeMode.FRUIT, this) {
			@Override
			public void createListBox(final IArea area) {
				ControlListBox<ItemStack> controlListBox = new ControlListBox<ItemStack>(this.modePage, area.xPos(), area.yPos(), area.width(), area.height(), 12) {
					@Override
					@SideOnly(Side.CLIENT)
					public IWidget createOption(final ItemStack value, final int y) {
						return new ControlItemStackOption(this.getContent(), value, y);
					}
				};
				TreeBreedingSystem breedingSystem = (TreeBreedingSystem) WindowArboristDatabase.this.getBreedingSystem();
				controlListBox.setOptions(breedingSystem.allFruits);
				this.listBox = controlListBox;
			}
		});
		this.createMode(TreeMode.WOOD, new ModeWidgets(TreeMode.WOOD, this) {
			@Override
			public void createListBox(final IArea area) {
				ControlListBox<ItemStack> controlListBox = new ControlListBox<ItemStack>(this.modePage, area.xPos(), area.yPos(), area.width(), area.height(), 12) {
					@Override
					@SideOnly(Side.CLIENT)
					public IWidget createOption(final ItemStack value, final int y) {
						return new ControlItemStackOption(this.getContent(), value, y);
					}
				};
				TreeBreedingSystem breedingSystem = (TreeBreedingSystem) WindowArboristDatabase.this.getBreedingSystem();
				controlListBox.setOptions(breedingSystem.allWoods);
				this.listBox = controlListBox;
			}
		});
		this.createMode(TreeMode.PLANKS, new ModeWidgets(TreeMode.PLANKS, this) {
			@Override
			public void createListBox(final IArea area) {
				ControlListBox<ItemStack> controlListBox = new ControlListBox<ItemStack>(this.modePage, area.xPos(), area.yPos(), area.width(), area.height(), 12) {
					@Override
					@SideOnly(Side.CLIENT)
					public IWidget createOption(final ItemStack value, final int y) {
						return new ControlItemStackOption(this.getContent(), value, y);
					}
				};
				controlListBox.setOptions(WoodManager.getAllPlankTypes().stream().map(IDesignMaterial::getStack).collect(Collectors.toList()));
				this.listBox = controlListBox;
			}
		});
		new PageFruit(this.getInfoPages(TreeMode.FRUIT), new DatabaseTab(ExtraTrees.instance, "fruit.natural"), true);
		new PageFruit(this.getInfoPages(TreeMode.FRUIT), new DatabaseTab(ExtraTrees.instance, "fruit.potential"), false);
		new PageWood(this.getInfoPages(TreeMode.WOOD), new DatabaseTab(ExtraTrees.instance, "wood.natural"));
		new PagePlanksOverview(this.getInfoPages(TreeMode.PLANKS), new DatabaseTab(ExtraTrees.instance, "planks.overview"));
		new PagePlanksTrees(this.getInfoPages(TreeMode.PLANKS), new DatabaseTab(ExtraTrees.instance, "planks.natural"));
	}

	@Override
	protected AbstractMod getMod() {
		return ExtraTrees.instance;
	}

	@Override
	protected String getBackgroundTextureName() {
		return "TreeDatabase";
	}

	enum TreeMode implements IDatabaseMode {
		FRUIT,
		WOOD,
		PLANKS;

		@Override
		public String getName() {
			return I18N.localise("extratrees.gui.database.mode." + this.name().toLowerCase());
		}
	}
}
