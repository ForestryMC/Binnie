package binnie.core.craftgui.database;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import net.minecraft.entity.player.EntityPlayer;

import com.mojang.authlib.GameProfile;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IBreedingTracker;
import forestry.api.genetics.IClassification;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlTextEdit;
import binnie.core.craftgui.controls.listbox.ControlListBox;
import binnie.core.craftgui.controls.listbox.ControlTextOption;
import binnie.core.craftgui.controls.page.ControlPage;
import binnie.core.craftgui.controls.page.ControlPages;
import binnie.core.craftgui.controls.tab.ControlTab;
import binnie.core.craftgui.controls.tab.ControlTabBar;
import binnie.core.craftgui.events.EventHandler;
import binnie.core.craftgui.events.EventTextEdit;
import binnie.core.craftgui.events.EventValueChanged;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.geometry.CraftGUIUtil;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.geometry.Position;
import binnie.core.craftgui.minecraft.MinecraftGUI;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlHelp;
import binnie.core.craftgui.window.Panel;
import binnie.core.genetics.BreedingSystem;
import binnie.core.util.I18N;

public abstract class WindowAbstractDatabase extends Window {
	private final int infoBoxWidth = 144;
	private final int infoBoxHeight = 176;
	private final int infoTabWidth = 16;
	private final int modeTabWidth = 22;
	private final int searchBoxHeight = 16;
	boolean isNEI;
	private int selectionBoxWidth;
	private Map<IDatabaseMode, ModeWidgets> modes;
	private BreedingSystem system;
	@Nullable
	private Panel panelInformation;
	@Nullable
	private Panel panelSearch;
	@Nullable
	private ControlPages<IDatabaseMode> modePages;
	@Nullable
	private IAlleleSpecies gotoSpecies;

	public WindowAbstractDatabase(final EntityPlayer player, final Side side, final boolean nei, final BreedingSystem system, final int wid) {
		super(100, 192, player, null, side);
		this.selectionBoxWidth = 95;
		this.modes = new HashMap<>();
		this.panelInformation = null;
		this.panelSearch = null;
		this.modePages = null;
		this.gotoSpecies = null;
		this.isNEI = nei;
		this.system = system;
		this.selectionBoxWidth = wid;
	}

	public WindowAbstractDatabase(final EntityPlayer player, final Side side, final boolean nei, final BreedingSystem system) {
		this(player, side, nei, system, 95);
	}

	public void changeMode(final IDatabaseMode mode) {
		this.modePages.setValue(mode);
	}

	public ControlPages<DatabaseTab> getInfoPages(final IDatabaseMode mode) {
		return this.modes.get(mode).infoPages;
	}

	public boolean isNEI() {
		return this.isNEI;
	}

	public BreedingSystem getBreedingSystem() {
		return this.system;
	}

	protected ModeWidgets createMode(final IDatabaseMode mode, final ModeWidgets widgets) {
		this.modes.put(mode, widgets);
		return widgets;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void initialiseClient() {
		this.setSize(new Point(176 + this.selectionBoxWidth + 22 + 8, 208));
		this.addEventHandler(new EventValueChanged.Handler() {
			@Override
			public void onEvent(final EventValueChanged event) {
				if (event.getOrigin().getParent() instanceof ControlPage && !(event.getValue() instanceof DatabaseTab)) {
					final ControlPage parent = (ControlPage) event.getOrigin().getParent();
					if (parent.getValue() instanceof IDatabaseMode) {
						for (final IWidget widget : parent.getWidgets()) {
							if (widget instanceof ControlPages) {
								if (event.getValue() == null) {
									widget.hide();
								} else {
									widget.show();
									for (final IWidget widget2 : widget.getWidgets()) {
										if (widget2 instanceof PageAbstract) {
											((PageAbstract) widget2).onValueChanged(event.getValue());
										}
									}
								}
							}
						}
					}
				}
			}
		});
		this.addEventHandler(new EventTextEdit.Handler() {
			@Override
			public void onEvent(final EventTextEdit event) {
				for (final ModeWidgets widgets : WindowAbstractDatabase.this.modes.values()) {
					widgets.listBox.setValidator(object -> Objects.equals(event.getValue(), "") || ((ControlTextOption) object).getText().toLowerCase().contains(event.getValue().toLowerCase()));
				}
			}
		}.setOrigin(EventHandler.Origin.DirectChild, this));
		new ControlHelp(this, 4, 4);
		(this.panelInformation = new Panel(this, 24, 24, 144, 176, MinecraftGUI.PanelType.Black)).setColour(860416);
		(this.panelSearch = new Panel(this, 176, 24, this.selectionBoxWidth, 160, MinecraftGUI.PanelType.Black)).setColour(860416);
		this.modePages = new ControlPages<>(this, 0, 0, this.getSize().x(), this.getSize().y());
		new ControlTextEdit(this, 176, 184, this.selectionBoxWidth, 16);
		this.createMode(Mode.SPECIES, new SpeciesModeWidgets());
		this.createMode(Mode.BRANCHES, new BranchesModeWidgets());
		this.createMode(Mode.BREEDER, new BreederModeWidgets());
		this.addTabs();
		final ControlTabBar<IDatabaseMode> tab = new ControlTabBar<IDatabaseMode>(this, 176 + this.selectionBoxWidth, 24, 22, 176, Position.RIGHT, this.modePages.getValues()) {
			@Override
			public ControlTab<IDatabaseMode> createTab(final int x, final int y, final int w, final int h, final IDatabaseMode value) {
				return new ControlTab<IDatabaseMode>(this, x, y, w, h, value) {
					@Override
					public String getName() {
						return this.value.getName();
					}
				};
			}
		};
		CraftGUIUtil.linkWidgets(tab, this.modePages);
		this.changeMode(Mode.SPECIES);
		for (final IDatabaseMode mode : this.modes.keySet()) {
			this.modes.get(mode).infoTabs = new ControlTabBar<>(this.modes.get(mode).modePage, 8, 24, 16, 176, Position.LEFT, this.modes.get(mode).infoPages.getValues());
			CraftGUIUtil.linkWidgets(this.modes.get(mode).infoTabs, this.modes.get(mode).infoPages);
		}
	}

	@Override
	public void initialiseServer() {
		final IBreedingTracker tracker = this.system.getSpeciesRoot().getBreedingTracker(this.getWorld(), this.getUsername());
		if (tracker != null) {
			tracker.synchToPlayer(this.getPlayer());
		}
	}

	@SideOnly(Side.CLIENT)
	protected void addTabs() {
	}

	public void gotoSpecies(final IAlleleSpecies value) {
		if (value != null) {
			this.modePages.setValue(Mode.SPECIES);
			this.changeMode(Mode.SPECIES);
			this.modes.get(this.modePages.getValue()).listBox.setValue(value);
		}
	}

	public void gotoSpeciesDelayed(final IAlleleSpecies species) {
		this.gotoSpecies = species;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onUpdateClient() {
		super.onUpdateClient();
		if (this.gotoSpecies != null) {
			((WindowAbstractDatabase) this.getTopParent()).gotoSpecies(this.gotoSpecies);
			this.gotoSpecies = null;
		}
	}

	public enum Mode implements IDatabaseMode {
		SPECIES,
		BRANCHES,
		BREEDER;

		@Override
		public String getName() {
			return I18N.localise("binniecore.gui.database.mode." + this.name().toLowerCase());
		}
	}

	public abstract static class ModeWidgets {
		public WindowAbstractDatabase database;
		public ControlPage<IDatabaseMode> modePage;
		public ControlListBox listBox;
		private ControlPages<DatabaseTab> infoPages;
		private ControlTabBar<DatabaseTab> infoTabs;

		public ModeWidgets(final IDatabaseMode mode, final WindowAbstractDatabase database) {
			this.database = database;
			this.modePage = new ControlPage<>(database.modePages, 0, 0, database.getSize().x(), database.getSize().y(), mode);
			final Area listBoxArea = database.panelSearch.area().inset(2);
			this.createListBox(listBoxArea);
			CraftGUIUtil.alignToWidget(this.listBox, database.panelSearch);
			CraftGUIUtil.moveWidget(this.listBox, new Point(2, 2));
			CraftGUIUtil.alignToWidget(this.infoPages = new ControlPages<>(this.modePage, 0, 0, 144, 176), database.panelInformation);
		}

		public abstract void createListBox(final Area p0);
	}

	@SideOnly(Side.CLIENT)
	private class SpeciesModeWidgets extends ModeWidgets {
		public SpeciesModeWidgets() {
			super(Mode.SPECIES, WindowAbstractDatabase.this);
		}

		@Override
		public void createListBox(final Area area) {
			final GameProfile playerName = WindowAbstractDatabase.this.getUsername();
			final Collection<IAlleleSpecies> speciesList = this.database.isNEI ? this.database.system.getAllSpecies() : this.database.system.getDiscoveredSpecies(this.database.getWorld(), playerName);
			ControlSpeciesBox controlSpeciesBox = new ControlSpeciesBox(this.modePage, area.xPos(), area.yPos(), area.width(), area.height());
			controlSpeciesBox.setOptions(speciesList);
			this.listBox = controlSpeciesBox;
		}
	}

	@SideOnly(Side.CLIENT)
	private class BranchesModeWidgets extends ModeWidgets {
		public BranchesModeWidgets() {
			super(Mode.BRANCHES, WindowAbstractDatabase.this);
		}

		@Override
		public void createListBox(final Area area) {
			final EntityPlayer player = this.database.getPlayer();
			final GameProfile playerName = WindowAbstractDatabase.this.getUsername();
			final Collection<IClassification> speciesList = this.database.isNEI ? this.database.system.getAllBranches() : this.database.system.getDiscoveredBranches(this.database.getWorld(), playerName);
			ControlBranchBox controlBranchBox = new ControlBranchBox(this.modePage, area.xPos(), area.yPos(), area.width(), area.height());
			controlBranchBox.setOptions(speciesList);
			this.listBox = controlBranchBox;
		}
	}

	@SideOnly(Side.CLIENT)
	private class BreederModeWidgets extends ModeWidgets {
		public BreederModeWidgets() {
			super(Mode.BREEDER, WindowAbstractDatabase.this);
		}

		@Override
		public void createListBox(final Area area) {
			this.listBox = new ControlListBox(this.modePage, area.xPos(), area.yPos(), area.width(), area.height(), 12);
		}
	}
}
