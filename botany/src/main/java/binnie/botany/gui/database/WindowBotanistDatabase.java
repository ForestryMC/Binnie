package binnie.botany.gui.database;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.entity.player.EntityPlayer;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.botany.Botany;
import binnie.botany.api.genetics.EnumFlowerColor;
import binnie.botany.api.genetics.IFlowerColor;
import binnie.botany.modules.ModuleFlowers;
import binnie.core.api.gui.IArea;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.listbox.ControlListBox;
import binnie.core.gui.controls.page.ControlPage;
import binnie.core.gui.database.DatabaseConstants;
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

public class WindowBotanistDatabase extends WindowAbstractDatabase {
	public WindowBotanistDatabase(EntityPlayer player, Side side, boolean nei) {
		super(player, side, nei, ModuleFlowers.flowerBreedingSystem, 130);
	}

	public static Window create(EntityPlayer player, Side side, boolean nei) {
		return new WindowBotanistDatabase(player, side, nei);
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected void addTabs() {
		new PageSpeciesOverview(getInfoPages(Mode.SPECIES), new DatabaseTab(Botany.instance, "species.overview"));
		new PageSpeciesFlowerGenome(getInfoPages(Mode.SPECIES), new DatabaseTab(Botany.instance, "genome.title"));
		new PageSpeciesClassification(getInfoPages(Mode.SPECIES), new DatabaseTab(Botany.instance, "species.classification"));
		new PageSpeciesResultant(getInfoPages(Mode.SPECIES), new DatabaseTab(Botany.instance, "species.resultant"));
		new PageSpeciesMutations(getInfoPages(Mode.SPECIES), new DatabaseTab(Botany.instance, "species.further"));
		new PageBranchOverview(getInfoPages(Mode.BRANCHES), new DatabaseTab(Botany.instance, "branches.overview"));
		new PageBranchSpecies(getInfoPages(Mode.BRANCHES), new DatabaseTab(Botany.instance, "branches.species"));
		createMode(FlowerMode.Color, new ModeWidgets(FlowerMode.Color, this, (area, modePage) -> {
			FlowerColorControlListBox listBox = new FlowerColorControlListBox(modePage, area);
			List<IFlowerColor> colors = Arrays.stream(EnumFlowerColor.values()).map(EnumFlowerColor::getFlowerColorAllele).collect(Collectors.toList());
			listBox.setOptions(colors);
			return listBox;
		}));
		new PageColorMixResultant(getInfoPages(FlowerMode.Color), new DatabaseTab(Botany.instance, "color.resultant"));
		new PageColorMix(getInfoPages(FlowerMode.Color), new DatabaseTab(Botany.instance, "color.further"));
		new PageBreeder(getInfoPages(Mode.BREEDER), getUsername(), new DatabaseTab(Botany.instance, "breeder"));
	}

	@Override
	protected String getModId() {
		return Botany.instance.getModId();
	}

	@Override
	protected String getBackgroundTextureName() {
		return "FlowerDatabase";
	}

	enum FlowerMode implements IDatabaseMode {
		Color;

		@Override
		public String getName() {
			return I18N.localise(DatabaseConstants.BOTANY_TAB_KEY + '.' + name().toLowerCase());
		}
	}

	@SideOnly(Side.CLIENT)
	private static class FlowerColorControlListBox extends ControlListBox<IFlowerColor> {
		public FlowerColorControlListBox(ControlPage<IDatabaseMode> modePage, IArea area) {
			super(modePage, area.xPos(), area.yPos(), area.width(), area.height(), 12);
		}

		@Override
		public IWidget createOption(IFlowerColor value, int y) {
			return new ControlColorOption(getContent(), value, y);
		}
	}
}
