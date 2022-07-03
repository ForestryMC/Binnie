package binnie.core.craftgui.database;

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
import binnie.core.craftgui.geometry.CraftGUIUtil;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.geometry.Position;
import binnie.core.craftgui.minecraft.MinecraftGUI;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlHelp;
import binnie.core.craftgui.window.Panel;
import binnie.core.genetics.BreedingSystem;
import binnie.core.util.I18N;
import binnie.core.util.IValidator;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.relauncher.Side;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IBreedingTracker;
import forestry.api.genetics.IClassification;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayer;

public abstract class WindowAbstractDatabase extends Window {
    protected boolean isNEI;

    private float selectionBoxWidth;
    private Map<IDatabaseMode, ModeWidgets> modes;
    private BreedingSystem system;
    private Panel panelInformation;
    private Panel panelSearch;
    private ControlPages<IDatabaseMode> modePages;
    private IAlleleSpecies gotoSpecies;

    public void changeMode(IDatabaseMode mode) {
        modePages.setValue(mode);
    }

    public WindowAbstractDatabase(EntityPlayer player, Side side, boolean nei, BreedingSystem system, float wid) {
        super(100.0f, 192.0f, player, null, side);
        selectionBoxWidth = 95.0f;
        modes = new HashMap<>();
        isNEI = nei;
        this.system = system;
        selectionBoxWidth = wid;
    }

    public ControlPages<DatabaseTab> getInfoPages(IDatabaseMode mode) {
        return modes.get(mode).infoPages;
    }

    public boolean isNEI() {
        return isNEI;
    }

    public BreedingSystem getBreedingSystem() {
        return system;
    }

    public WindowAbstractDatabase(EntityPlayer player, Side side, boolean nei, BreedingSystem system) {
        this(player, side, nei, system, 95.0f);
    }

    protected ModeWidgets createMode(IDatabaseMode mode, ModeWidgets widgets) {
        modes.put(mode, widgets);
        return widgets;
    }

    @Override
    public void initialiseClient() {
        setSize(new IPoint(176.0f + selectionBoxWidth + 22.0f + 8.0f, 208.0f));
        addEventHandler(new EventValueChanged.Handler() {
            @Override
            public void onEvent(EventValueChanged event) {
                if (!(event.getOrigin().getParent() instanceof ControlPage)) {
                    return;
                } else if ((event.getValue() instanceof DatabaseTab)) {
                    return;
                }

                ControlPage parent = (ControlPage) event.getOrigin().getParent();
                if (!(parent.getValue() instanceof IDatabaseMode)) {
                    return;
                }

                for (IWidget widget : parent.getWidgets()) {
                    if (widget instanceof ControlPages) {
                        if (event.getValue() == null) {
                            widget.hide();
                        } else {
                            widget.show();
                            for (IWidget widget2 : widget.getWidgets()) {
                                if (widget2 instanceof PageAbstract) {
                                    ((PageAbstract) widget2).onValueChanged(event.getValue());
                                }
                            }
                        }
                    }
                }
            }
        });

        addEventHandler(
                new EventTextEdit.Handler() {
                    @Override
                    public void onEvent(EventTextEdit event) {
                        for (ModeWidgets widgets : modes.values()) {
                            widgets.listBox.setValidator(new IValidator<IWidget>() {
                                @Override
                                public boolean isValid(IWidget object) {
                                    return event.getValue().isEmpty()
                                            || ((ControlTextOption) object)
                                                    .getText()
                                                    .toLowerCase()
                                                    .contains(event.getValue().toLowerCase());
                                }
                            });
                        }
                    }
                }.setOrigin(EventHandler.Origin.DirectChild, this));

        new ControlHelp(this, 4.0f, 4.0f);
        (panelInformation = new Panel(this, 24.0f, 24.0f, 144.0f, 176.0f, MinecraftGUI.PanelType.Black))
                .setColor(860416);
        (panelSearch = new Panel(this, 176.0f, 24.0f, selectionBoxWidth, 160.0f, MinecraftGUI.PanelType.Black))
                .setColor(860416);
        modePages =
                new ControlPages<>(this, 0.0f, 0.0f, getSize().x(), getSize().y());
        new ControlTextEdit(this, 176.0f, 184.0f, selectionBoxWidth, 16.0f);

        createMode(Mode.Species, new ModeWidgets(Mode.Species, this) {
            @Override
            public void createListBox(IArea area) {
                GameProfile playerName = getUsername();
                Collection<IAlleleSpecies> speciesList = database.isNEI
                        ? database.system.getAllSpecies()
                        : database.system.getDiscoveredSpecies(database.getWorld(), playerName);
                (listBox = new ControlSpeciesBox(modePage, area.x(), area.y(), area.w(), area.h()))
                        .setOptions(speciesList);
            }
        });

        createMode(Mode.Branches, new ModeWidgets(Mode.Branches, this) {
            @Override
            public void createListBox(IArea area) {
                GameProfile playerName = getUsername();
                Collection<IClassification> speciesList = database.isNEI
                        ? database.system.getAllBranches()
                        : database.system.getDiscoveredBranches(database.getWorld(), playerName);

                listBox = new ControlBranchBox(modePage, area.x(), area.y(), area.w(), area.h());
                listBox.setOptions(speciesList);
            }
        });

        createMode(Mode.Breeder, new ModeWidgets(Mode.Breeder, this) {
            @Override
            public void createListBox(IArea area) {
                listBox = new ControlListBox(modePage, area.x(), area.y(), area.w(), area.h(), 12.0f);
            }
        });

        addTabs();
        ControlTabBar<IDatabaseMode> tab =
                new ControlTabBar<IDatabaseMode>(
                        this, 176.0f + selectionBoxWidth, 24.0f, 22.0f, 176.0f, Position.RIGHT) {
                    @Override
                    public ControlTab<IDatabaseMode> createTab(
                            float x, float y, float w, float h, IDatabaseMode value) {
                        return new ControlTab<IDatabaseMode>(this, x, y, w, h, value) {
                            @Override
                            public String getName() {
                                return value.getName();
                            }
                        };
                    }
                };

        tab.setValues(modePages.getValues());
        CraftGUIUtil.linkWidgets(tab, modePages);
        changeMode(Mode.Species);
        for (IDatabaseMode mode : modes.keySet()) {
            modes.get(mode).infoTabs =
                    new ControlTabBar(modes.get(mode).modePage, 8.0f, 24.0f, 16.0f, 176.0f, Position.LEFT);
            modes.get(mode).infoTabs.setValues(modes.get(mode).infoPages.getValues());
            CraftGUIUtil.linkWidgets(modes.get(mode).infoTabs, modes.get(mode).infoPages);
        }
    }

    @Override
    public void initialiseServer() {
        IBreedingTracker tracker = system.getSpeciesRoot().getBreedingTracker(getWorld(), getUsername());
        if (tracker != null) {
            tracker.synchToPlayer(getPlayer());
        }
    }

    protected void addTabs() {
        // ignored
    }

    public void gotoSpecies(IAlleleSpecies value) {
        if (value == null) {
            return;
        }

        modePages.setValue(Mode.Species);
        changeMode(Mode.Species);
        modes.get(modePages.getValue()).listBox.setValue(value);
    }

    public void gotoSpeciesDelayed(IAlleleSpecies species) {
        gotoSpecies = species;
    }

    @Override
    public void onUpdateClient() {
        super.onUpdateClient();
        if (gotoSpecies == null) {
            return;
        }

        ((WindowAbstractDatabase) getSuperParent()).gotoSpecies(gotoSpecies);
        gotoSpecies = null;
    }

    public enum Mode implements IDatabaseMode {
        Species,
        Branches,
        Breeder;

        @Override
        public String getName() {
            return I18N.localise("binniecore.gui.database.mode." + name().toLowerCase());
        }
    }

    public abstract static class ModeWidgets {
        public WindowAbstractDatabase database;
        public ControlPage<IDatabaseMode> modePage;
        public ControlListBox listBox;

        private ControlPages<DatabaseTab> infoPages;
        private ControlTabBar<DatabaseTab> infoTabs;

        public ModeWidgets(IDatabaseMode mode, WindowAbstractDatabase database) {
            this.database = database;
            modePage = new ControlPage<>(
                    database.modePages,
                    0.0f,
                    0.0f,
                    database.getSize().x(),
                    database.getSize().y(),
                    mode);
            IArea listBoxArea = database.panelSearch.area().inset(2);
            createListBox(listBoxArea);
            CraftGUIUtil.alignToWidget(listBox, database.panelSearch);
            CraftGUIUtil.moveWidget(listBox, new IPoint(2.0f, 2.0f));
            CraftGUIUtil.alignToWidget(
                    infoPages = new ControlPages<>(modePage, 0.0f, 0.0f, 144.0f, 176.0f), database.panelInformation);
        }

        public abstract void createListBox(IArea area);
    }
}
