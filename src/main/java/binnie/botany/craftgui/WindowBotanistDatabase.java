package binnie.botany.craftgui;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.botany.api.IFlowerColour;
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
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.util.I18N;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WindowBotanistDatabase extends WindowAbstractDatabase {
	private ControlListBox<EnumFlowerColor> selectionBoxColors;

	public WindowBotanistDatabase(EntityPlayer player, Side side, boolean nei) {
		super(player, side, nei, Binnie.GENETICS.flowerBreedingSystem, 130);
	}

	public static Window create(EntityPlayer player, Side side, boolean nei) {
		return new WindowBotanistDatabase(player, side, nei);
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected void addTabs() {
		new PageSpeciesOverview(getInfoPages(Mode.SPECIES), new DatabaseTab(Botany.instance, "species.overview", 0));
		new PageSpeciesFlowerGenome(getInfoPages(Mode.SPECIES), new DatabaseTab(Botany.instance, "genome", 0));
		new PageSpeciesClassification(getInfoPages(Mode.SPECIES), new DatabaseTab(Botany.instance, "species.classification", 0));
		new PageSpeciesResultant(getInfoPages(Mode.SPECIES), new DatabaseTab(Botany.instance, "species.resultant", 0));
		new PageSpeciesMutations(getInfoPages(Mode.SPECIES), new DatabaseTab(Botany.instance, "species.further", 0));
		new PageBranchOverview(getInfoPages(Mode.BRANCHES), new DatabaseTab(Botany.instance, "branches.overview", 0));
		new PageBranchSpecies(getInfoPages(Mode.BRANCHES), new DatabaseTab(Botany.instance, "branches.species", 0));
		createMode(FlowerMode.Color, new FlowerColorModeWidgets());
		new PageColorMixResultant(getInfoPages(FlowerMode.Color), new DatabaseTab(Botany.instance, "color.resultant", 0));
		new PageColorMix(getInfoPages(FlowerMode.Color), new DatabaseTab(Botany.instance, "color.further", 0));
		new PageBreeder(getInfoPages(Mode.BREEDER), getUsername(), new DatabaseTab(Botany.instance, "breeder", 0));
	}

	@Override
	protected AbstractMod getMod() {
		return Botany.instance;
	}

	@Override
	protected String getBackgroundTextureName() {
		return "FlowerDatabase";
	}

	enum FlowerMode implements IDatabaseMode {
		Color;

		@Override
		public String getName() {
			return I18N.localise("botany.gui.database.tab." + name().toLowerCase());
		}
	}

	@SideOnly(Side.CLIENT)
	private class FlowerColorModeWidgets extends ModeWidgets {
		public FlowerColorModeWidgets() {
			super(FlowerMode.Color, WindowBotanistDatabase.this);
		}

		@Override
		public void createListBox(Area area) {
			listBox = new ControlListBox<IFlowerColour>(modePage, area.xPos(), area.yPos(), area.width(), area.height(), 12) {
				@Override
				public IWidget createOption(IFlowerColour value, int y) {
					return new ControlColorOption(getContent(), value, y);
				}
			};

			List<IFlowerColour> colors = Arrays.stream(EnumFlowerColor.values()).map(c -> c.getFlowerColorAllele()).collect(Collectors.toList());
			listBox.setOptions(colors);
		}
	}
}
