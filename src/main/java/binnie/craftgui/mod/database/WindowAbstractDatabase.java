package binnie.craftgui.mod.database;

import binnie.core.BinnieCore;
import binnie.core.genetics.BreedingSystem;
import binnie.core.util.IValidator;
import binnie.craftgui.controls.ControlTextEdit;
import binnie.craftgui.controls.listbox.ControlListBox;
import binnie.craftgui.controls.listbox.ControlTextOption;
import binnie.craftgui.controls.page.ControlPage;
import binnie.craftgui.controls.page.ControlPages;
import binnie.craftgui.controls.tab.ControlTab;
import binnie.craftgui.controls.tab.ControlTabBar;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.CraftGUIUtil;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.geometry.Position;
import binnie.craftgui.events.EventHandler;
import binnie.craftgui.events.EventTextEdit;
import binnie.craftgui.events.EventValueChanged;
import binnie.craftgui.minecraft.MinecraftGUI;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.minecraft.control.ControlHelp;
import binnie.craftgui.window.Panel;
import com.mojang.authlib.GameProfile;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IBreedingTracker;
import forestry.api.genetics.IClassification;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class WindowAbstractDatabase extends Window {
	private int selectionBoxWidth;
	private final int infoBoxWidth = 144;
	private final int infoBoxHeight = 176;
	private final int infoTabWidth = 16;
	private final int modeTabWidth = 22;
	private final int searchBoxHeight = 16;
	private Map<IDatabaseMode, ModeWidgets> modes;
	boolean isNEI;
	private BreedingSystem system;
	@Nullable
	private Panel panelInformation;
	@Nullable
	private Panel panelSearch;
	@Nullable
	private ControlPages<IDatabaseMode> modePages;
	@Nullable
	private IAlleleSpecies gotoSpecies;

	public void changeMode(final IDatabaseMode mode) {
		this.modePages.setValue(mode);
	}

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

	public ControlPages<DatabaseTab> getInfoPages(final IDatabaseMode mode) {
		return this.modes.get(mode).infoPages;
	}

	public boolean isNEI() {
		return this.isNEI;
	}

	public BreedingSystem getBreedingSystem() {
		return this.system;
	}

	public WindowAbstractDatabase(final EntityPlayer player, final Side side, final boolean nei, final BreedingSystem system) {
		this(player, side, nei, system, 95);
	}

	protected ModeWidgets createMode(final IDatabaseMode mode, final ModeWidgets widgets) {
		this.modes.put(mode, widgets);
		return widgets;
	}

	@Override
	public void initialiseClient() {
		this.setSize(new IPoint(176 + this.selectionBoxWidth + 22 + 8, 208));
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
					widgets.listBox.setValidator(new IValidator<IWidget>() {
						@Override
						public boolean isValid(final IWidget object) {
							return Objects.equals(event.getValue(), "") || ((ControlTextOption) object).getText().toLowerCase().contains(event.getValue().toLowerCase());
						}
					});
				}
			}
		}.setOrigin(EventHandler.Origin.DirectChild, this));
		new ControlHelp(this, 4, 4);
		(this.panelInformation = new Panel(this, 24, 24, 144, 176, MinecraftGUI.PanelType.Black)).setColour(860416);
		(this.panelSearch = new Panel(this, 176, 24, this.selectionBoxWidth, 160, MinecraftGUI.PanelType.Black)).setColour(860416);
		this.modePages = new ControlPages<>(this, 0, 0, this.getSize().x(), this.getSize().y());
		new ControlTextEdit(this, 176, 184, this.selectionBoxWidth, 16);
		this.createMode(Mode.Species, new ModeWidgets(Mode.Species, this) {
			@Override
			public void createListBox(final IArea area) {
				final GameProfile playerName = WindowAbstractDatabase.this.getUsername();
				final Collection<IAlleleSpecies> speciesList = this.database.isNEI ? this.database.system.getAllSpecies() : this.database.system.getDiscoveredSpecies(this.database.getWorld(), playerName);
				(this.listBox = new ControlSpeciesBox(this.modePage, area.x(), area.y(), area.w(), area.h())).setOptions(speciesList);
			}
		});
		this.createMode(Mode.Branches, new ModeWidgets(Mode.Branches, this) {
			@Override
			public void createListBox(final IArea area) {
				final EntityPlayer player = this.database.getPlayer();
				final GameProfile playerName = WindowAbstractDatabase.this.getUsername();
				final Collection<IClassification> speciesList = this.database.isNEI ? this.database.system.getAllBranches() : this.database.system.getDiscoveredBranches(this.database.getWorld(), playerName);
				(this.listBox = new ControlBranchBox(this.modePage, area.x(), area.y(), area.w(), area.h())).setOptions(speciesList);
			}
		});
		this.createMode(Mode.Breeder, new ModeWidgets(Mode.Breeder, this) {
			@Override
			public void createListBox(final IArea area) {
				this.listBox = new ControlListBox(this.modePage, area.x(), area.y(), area.w(), area.h(), 12);
			}
		});
		this.addTabs();
		final ControlTabBar<IDatabaseMode> tab = new ControlTabBar<IDatabaseMode>(this, 176 + this.selectionBoxWidth, 24, 22, 176, Position.Right, this.modePages.getValues()) {
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
		this.changeMode(Mode.Species);
		for (final IDatabaseMode mode : this.modes.keySet()) {
			this.modes.get(mode).infoTabs = new ControlTabBar<>(this.modes.get(mode).modePage, 8, 24, 16, 176, Position.Left, this.modes.get(mode).infoPages.getValues());
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

	protected void addTabs() {
	}

	public void gotoSpecies(final IAlleleSpecies value) {
		if (value != null) {
			this.modePages.setValue(Mode.Species);
			this.changeMode(Mode.Species);
			this.modes.get(this.modePages.getValue()).listBox.setValue(value);
		}
	}

	public void gotoSpeciesDelayed(final IAlleleSpecies species) {
		this.gotoSpecies = species;
	}

	@Override
	public void onUpdateClient() {
		super.onUpdateClient();
		if (this.gotoSpecies != null) {
			((WindowAbstractDatabase) this.getTopParent()).gotoSpecies(this.gotoSpecies);
			this.gotoSpecies = null;
		}
	}

	public enum Mode implements IDatabaseMode {
		Species,
		Branches,
		Breeder;

		@Override
		public String getName() {
			return BinnieCore.getBinnieProxy().localise("gui.database.mode." + this.name().toLowerCase());
		}
	}

	public abstract static class ModeWidgets {
		public WindowAbstractDatabase database;
		public ControlPage<IDatabaseMode> modePage;
		private ControlPages<DatabaseTab> infoPages;
		public ControlListBox listBox;
		private ControlTabBar<DatabaseTab> infoTabs;

		public ModeWidgets(final IDatabaseMode mode, final WindowAbstractDatabase database) {
			this.database = database;
			this.modePage = new ControlPage<>(database.modePages, 0, 0, database.getSize().x(), database.getSize().y(), mode);
			final IArea listBoxArea = database.panelSearch.area().inset(2);
			this.createListBox(listBoxArea);
			CraftGUIUtil.alignToWidget(this.listBox, database.panelSearch);
			CraftGUIUtil.moveWidget(this.listBox, new IPoint(2, 2));
			CraftGUIUtil.alignToWidget(this.infoPages = new ControlPages<>(this.modePage, 0, 0, 144, 176), database.panelInformation);
		}

		public abstract void createListBox(final IArea p0);
	}
}
