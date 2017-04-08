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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WindowBotanistDatabase extends WindowAbstractDatabase {
	ControlListBox<EnumFlowerColor> selectionBoxColors;

	public WindowBotanistDatabase(final EntityPlayer player, final Side side, final boolean nei) {
		super(player, side, nei, Binnie.GENETICS.flowerBreedingSystem, 130);
	}

	public static Window create(final EntityPlayer player, final Side side, final boolean nei) {
		return new WindowBotanistDatabase(player, side, nei);
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected void addTabs() {
		new PageSpeciesOverview(this.getInfoPages(Mode.SPECIES), new DatabaseTab(Botany.instance, "species.overview", 0));
		new PageSpeciesFlowerGenome(this.getInfoPages(Mode.SPECIES), new DatabaseTab(Botany.instance, "species.genome", 0));
		new PageSpeciesClassification(this.getInfoPages(Mode.SPECIES), new DatabaseTab(Botany.instance, "species.classification", 0));
		new PageSpeciesResultant(this.getInfoPages(Mode.SPECIES), new DatabaseTab(Botany.instance, "species.resultant", 0));
		new PageSpeciesMutations(this.getInfoPages(Mode.SPECIES), new DatabaseTab(Botany.instance, "species.further", 0));
		new PageBranchOverview(this.getInfoPages(Mode.BRANCHES), new DatabaseTab(Botany.instance, "branches.overview", 0));
		new PageBranchSpecies(this.getInfoPages(Mode.BRANCHES), new DatabaseTab(Botany.instance, "branches.species", 0));
		this.createMode(FlowerMode.Colour, new ModeWidgets(FlowerMode.Colour, this) {
			@Override
			public void createListBox(final Area area) {
				this.listBox = new ControlListBox<IFlowerColour>(this.modePage, area.xPos(), area.yPos(), area.width(), area.height(), 12) {
					@Override
					public IWidget createOption(final IFlowerColour value, final int y) {
						return new ControlColourOption(this.getContent(), value, y);
					}
				};
				final List<IFlowerColour> colors = new ArrayList<>();
				Collections.addAll(colors, EnumFlowerColor.values());
				this.listBox.setOptions(colors);
			}
		});
		new PageColourMixResultant(this.getInfoPages(FlowerMode.Colour), new DatabaseTab(Botany.instance, "colour.resultant", 0));
		new PageColourMix(this.getInfoPages(FlowerMode.Colour), new DatabaseTab(Botany.instance, "colour.further", 0));
		new PageBreeder(this.getInfoPages(Mode.BREEDER), this.getUsername(), new DatabaseTab(Botany.instance, "breeder", 0));
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
		Colour;

		@Override
		public String getName() {
			return Botany.proxy.localise("gui.database.mode." + this.name().toLowerCase());
		}
	}
}
