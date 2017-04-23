// 
// Decompiled by Procyon v0.5.30
// 

package binnie.botany.craftgui;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.botany.api.IFlowerColour;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.core.AbstractMod;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.listbox.ControlList;
import binnie.core.craftgui.controls.listbox.ControlListBox;
import binnie.core.craftgui.controls.scroll.ControlScrollableContent;
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
import java.util.List;

public class WindowBotanistDatabase extends WindowAbstractDatabase
{
	ControlListBox<EnumFlowerColor> selectionBoxColors;

	public WindowBotanistDatabase(final EntityPlayer player, final Side side, final boolean nei) {
		super(player, side, nei, Binnie.Genetics.flowerBreedingSystem, 130.0f);
	}

	public static Window create(final EntityPlayer player, final Side side, final boolean nei) {
		return new WindowBotanistDatabase(player, side, nei);
	}

	@Override
	protected void addTabs() {
		new PageSpeciesOverview(this.getInfoPages(Mode.Species), new DatabaseTab(Botany.instance, "species.overview", 0));
		new PageSpeciesFlowerGenome(this.getInfoPages(Mode.Species), new DatabaseTab(Botany.instance, "species.genome", 0));
		new PageSpeciesClassification(this.getInfoPages(Mode.Species), new DatabaseTab(Botany.instance, "species.classification", 0));
		new PageSpeciesResultant(this.getInfoPages(Mode.Species), new DatabaseTab(Botany.instance, "species.resultant", 0));
		new PageSpeciesMutations(this.getInfoPages(Mode.Species), new DatabaseTab(Botany.instance, "species.further", 0));
		new PageBranchOverview(this.getInfoPages(Mode.Branches), new DatabaseTab(Botany.instance, "branches.overview", 0));
		new PageBranchSpecies(this.getInfoPages(Mode.Branches), new DatabaseTab(Botany.instance, "branches.species", 0));
		this.createMode(FlowerMode.Colour, new ModeWidgets(FlowerMode.Colour, this) {
			@Override
			public void createListBox(final IArea area) {
				this.listBox = new ControlListBox<IFlowerColour>(this.modePage, area.x(), area.y(), area.w(), area.h(), 12.0f) {
					@Override
					public IWidget createOption(final IFlowerColour value, final int y) {
						return new ControlColourOption(((ControlScrollableContent<ControlList<IFlowerColour>>) this).getContent(), value, y);
					}
				};
				final List<IFlowerColour> colors = new ArrayList<IFlowerColour>();
				for (final IFlowerColour c : EnumFlowerColor.values()) {
					colors.add(c);
				}
				this.listBox.setOptions(colors);
			}
		});
		new PageColourMixResultant(this.getInfoPages(FlowerMode.Colour), new DatabaseTab(Botany.instance, "colour.resultant", 0));
		new PageColourMix(this.getInfoPages(FlowerMode.Colour), new DatabaseTab(Botany.instance, "colour.further", 0));
		new PageBreeder(this.getInfoPages(Mode.Breeder), this.getUsername(), new DatabaseTab(Botany.instance, "breeder", 0));
	}

	@Override
	protected AbstractMod getMod() {
		return Botany.instance;
	}

	@Override
	protected String getName() {
		return "FlowerDatabase";
	}

	enum FlowerMode implements IDatabaseMode
	{
		Colour;

		@Override
		public String getName() {
			return Botany.proxy.localise("gui.database.mode." + this.name().toLowerCase());
		}
	}
}
