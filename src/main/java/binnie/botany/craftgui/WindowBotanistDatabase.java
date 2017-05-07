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
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WindowBotanistDatabase extends WindowAbstractDatabase {
	public WindowBotanistDatabase(EntityPlayer player, Side side, boolean nei) {
		super(player, side, nei, Binnie.Genetics.flowerBreedingSystem, 130.0f);
	}

	public static Window create(EntityPlayer player, Side side, boolean nei) {
		return new WindowBotanistDatabase(player, side, nei);
	}

	@Override
	protected void addTabs() {
		new PageSpeciesOverview(getInfoPages(Mode.Species), new DatabaseTab(Botany.instance, "species.overview", 0));
		new PageSpeciesFlowerGenome(getInfoPages(Mode.Species), new DatabaseTab(Botany.instance, "species.genome", 0));
		new PageSpeciesClassification(getInfoPages(Mode.Species), new DatabaseTab(Botany.instance, "species.classification", 0));
		new PageSpeciesResultant(getInfoPages(Mode.Species), new DatabaseTab(Botany.instance, "species.resultant", 0));
		new PageSpeciesMutations(getInfoPages(Mode.Species), new DatabaseTab(Botany.instance, "species.further", 0));
		new PageBranchOverview(getInfoPages(Mode.Branches), new DatabaseTab(Botany.instance, "branches.overview", 0));
		new PageBranchSpecies(getInfoPages(Mode.Branches), new DatabaseTab(Botany.instance, "branches.species", 0));
		createMode(FlowerMode.Colour, new ColorModeWidget());
		new PageColorMixResultant(getInfoPages(FlowerMode.Colour), new DatabaseTab(Botany.instance, "colour.resultant", 0));
		new PageColourMix(getInfoPages(FlowerMode.Colour), new DatabaseTab(Botany.instance, "colour.further", 0));
		new PageBreeder(getInfoPages(Mode.Breeder), getUsername(), new DatabaseTab(Botany.instance, "breeder", 0));
	}

	@Override
	protected AbstractMod getMod() {
		return Botany.instance;
	}

	@Override
	protected String getName() {
		return Binnie.I18N.localise(Botany.instance, "gui.database.name");
	}

	enum FlowerMode implements IDatabaseMode {
		Colour;

		@Override
		public String getName() {
			return Botany.proxy.localise("gui.database.mode." + name().toLowerCase());
		}
	}

	private class ColorModeWidget extends ModeWidgets {
		public ColorModeWidget() {
			super(FlowerMode.Colour, WindowBotanistDatabase.this);
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
