package binnie.extratrees.gui.database;

import binnie.core.Binnie;
import binnie.core.api.gui.IArea;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.listbox.ControlListBox;
import binnie.core.gui.controls.page.ControlPage;
import binnie.core.gui.database.ControlItemStackOption;
import binnie.core.gui.database.DatabaseTab;
import binnie.core.gui.database.IDatabaseMode;
import binnie.core.gui.database.ModeWidgets;
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
import binnie.design.api.IDesignMaterial;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.wood.WoodManager;
import binnie.genetics.api.ITreeBreedingSystem;
import forestry.api.arboriculture.TreeManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.stream.Collectors;

public class WindowArboristDatabase extends WindowAbstractDatabase {
	public static Window create(EntityPlayer player, Side side, boolean master) {
		return new WindowArboristDatabase(player, side, master);
	}

	private WindowArboristDatabase(EntityPlayer player, Side side, boolean master) {
		super(player, side, master, Binnie.GENETICS.getSystem(TreeManager.treeRoot), 120);
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
		this.createMode(TreeMode.FRUIT, new ModeWidgets(TreeMode.FRUIT, this, (area, modePage) -> {
			ControlListBox<ItemStack> controlListBox = new ModeControlListBox(modePage, area);
			ITreeBreedingSystem breedingSystem = this.getBreedingSystem();
			controlListBox.setOptions(breedingSystem.getAllFruits());
			return controlListBox;
		}));
		this.createMode(TreeMode.WOOD, new ModeWidgets(TreeMode.WOOD, this, (area, modePage) -> {
			ControlListBox<ItemStack> controlListBox = new ModeControlListBox(modePage, area);
			ITreeBreedingSystem breedingSystem = this.getBreedingSystem();
			controlListBox.setOptions(breedingSystem.getAllWoods());
			return controlListBox;
		}));
		this.createMode(TreeMode.PLANKS, new ModeWidgets(TreeMode.PLANKS, this, (area, modePage) -> {
			ControlListBox<ItemStack> controlListBox = new ModeControlListBox(modePage, area);
			List<ItemStack> planks = WoodManager.getAllPlankTypes().stream().map(IDesignMaterial::getStack).collect(Collectors.toList());
			controlListBox.setOptions(planks);
			return controlListBox;
		}));
		new PageFruit(this.getInfoPages(TreeMode.FRUIT), new DatabaseTab(ExtraTrees.instance, "fruit.natural"), true);
		new PageFruit(this.getInfoPages(TreeMode.FRUIT), new DatabaseTab(ExtraTrees.instance, "fruit.potential"), false);
		new PageWood(this.getInfoPages(TreeMode.WOOD), new DatabaseTab(ExtraTrees.instance, "wood.natural"));
		new PagePlanksOverview(this.getInfoPages(TreeMode.PLANKS), new DatabaseTab(ExtraTrees.instance, "planks.overview"));
		new PagePlanksTrees(this.getInfoPages(TreeMode.PLANKS), new DatabaseTab(ExtraTrees.instance, "planks.natural"));
	}

	@Override
	protected String getModId() {
		return ExtraTrees.instance.getModId();
	}

	@Override
	protected String getBackgroundTextureName() {
		return "TreeDatabase";
	}

	@Override
	public ITreeBreedingSystem getBreedingSystem() {
		return (ITreeBreedingSystem) super.getBreedingSystem();
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

	private static class ModeControlListBox extends ControlListBox<ItemStack> {
		public ModeControlListBox(ControlPage<IDatabaseMode> modePage, IArea area) {
			super(modePage, area.xPos(), area.yPos(), area.width(), area.height(), 12);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public IWidget createOption(ItemStack value, int y) {
			return new ControlItemStackOption(this.getContent(), value, y);
		}
	}
}
