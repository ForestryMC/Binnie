package binnie.extratrees.machines.craftgui;

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
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.genetics.TreeBreedingSystem;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.WoodManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.stream.Collectors;

public class WindowArboristDatabase extends WindowAbstractDatabase {
	ControlListBox<ItemStack> selectionBoxFruit;
	ControlListBox<ItemStack> selectionBoxWood;
	ControlListBox<ItemStack> selectionBoxPlanks;

	public WindowArboristDatabase(final EntityPlayer player, final Side side, final boolean nei) {
		super(player, side, nei, Binnie.GENETICS.treeBreedingSystem, 120);
	}

	public static Window create(final EntityPlayer player, final Side side, final boolean nei) {
		return new WindowArboristDatabase(player, side, nei);
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected void addTabs() {
		new PageSpeciesOverview(this.getInfoPages(Mode.SPECIES), new DatabaseTab(ExtraTrees.instance, "species.overview", 0));
		new PageSpeciesTreeGenome(this.getInfoPages(Mode.SPECIES), new DatabaseTab(ExtraTrees.instance, "species.genome", 0));
		new PageSpeciesClassification(this.getInfoPages(Mode.SPECIES), new DatabaseTab(ExtraTrees.instance, "species.classification", 0));
		new PageSpeciesResultant(this.getInfoPages(Mode.SPECIES), new DatabaseTab(ExtraTrees.instance, "species.resultant", 0));
		new PageSpeciesMutations(this.getInfoPages(Mode.SPECIES), new DatabaseTab(ExtraTrees.instance, "species.further", 0));
		new PageBranchOverview(this.getInfoPages(Mode.BRANCHES), new DatabaseTab(ExtraTrees.instance, "branches.overview", 0));
		new PageBranchSpecies(this.getInfoPages(Mode.BRANCHES), new DatabaseTab(ExtraTrees.instance, "branches.species", 0));
		new PageBreeder(this.getInfoPages(Mode.BREEDER), this.getUsername(), new DatabaseTab(ExtraTrees.instance, "breeder", 0));
		this.createMode(TreeMode.FRUIT, new ModeWidgets(TreeMode.FRUIT, this) {
			@Override
			public void createListBox(final Area area) {
				(this.listBox = new ControlListBox<ItemStack>(this.modePage, area.xPos(), area.yPos(), area.width(), area.height(), 12) {
					@Override
					@SideOnly(Side.CLIENT)
					public IWidget createOption(final ItemStack value, final int y) {
						return new ControlItemStackOption(this.getContent(), value, y);
					}
				}).setOptions(((TreeBreedingSystem) WindowArboristDatabase.this.getBreedingSystem()).allFruits);
			}
		});
		this.createMode(TreeMode.WOOD, new ModeWidgets(TreeMode.WOOD, this) {
			@Override
			public void createListBox(final Area area) {
				(this.listBox = new ControlListBox<ItemStack>(this.modePage, area.xPos(), area.yPos(), area.width(), area.height(), 12) {
					@Override
					@SideOnly(Side.CLIENT)
					public IWidget createOption(final ItemStack value, final int y) {
						return new ControlItemStackOption(this.getContent(), value, y);
					}
				}).setOptions(((TreeBreedingSystem) WindowArboristDatabase.this.getBreedingSystem()).allWoods);
			}
		});
		this.createMode(TreeMode.PLANKS, new ModeWidgets(TreeMode.PLANKS, this) {
			@Override
			public void createListBox(final Area area) {
				(this.listBox = new ControlListBox<ItemStack>(this.modePage, area.xPos(), area.yPos(), area.width(), area.height(), 12) {
					@Override
					@SideOnly(Side.CLIENT)
					public IWidget createOption(final ItemStack value, final int y) {
						return new ControlItemStackOption(this.getContent(), value, y);
					}
				}).setOptions(WoodManager.getAllPlankTypes().stream().<ItemStack>map(type -> type.getStack()).collect(Collectors.toList()));
			}
		});
		new PageFruit(this.getInfoPages(TreeMode.FRUIT), new DatabaseTab(ExtraTrees.instance, "fruit.natural", 0), true);
		new PageFruit(this.getInfoPages(TreeMode.FRUIT), new DatabaseTab(ExtraTrees.instance, "fruit.potential", 0), false);
		new PageWood(this.getInfoPages(TreeMode.WOOD), new DatabaseTab(ExtraTrees.instance, "wood.natural", 0));
		new PagePlanksOverview(this.getInfoPages(TreeMode.PLANKS), new DatabaseTab(ExtraTrees.instance, "planks.overview", 0));
		new PagePlanksTrees(this.getInfoPages(TreeMode.PLANKS), new DatabaseTab(ExtraTrees.instance, "planks.natural", 1));
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
			return ExtraTrees.proxy.localise("gui.database.mode." + this.name().toLowerCase());
		}
	}
}
