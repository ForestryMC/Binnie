package binnie.genetics.gui;

import binnie.Binnie;
import binnie.botany.api.IFlower;
import binnie.core.AbstractMod;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.Widget;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.controls.scroll.ControlScrollBar;
import binnie.core.craftgui.controls.scroll.ControlScrollableContent;
import binnie.core.craftgui.events.EventKey;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.geometry.Position;
import binnie.core.craftgui.minecraft.InventoryType;
import binnie.core.craftgui.minecraft.MinecraftGUI;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.core.craftgui.minecraft.control.ControlSlide;
import binnie.core.craftgui.minecraft.control.ControlSlot;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;
import binnie.core.craftgui.window.Panel;
import binnie.core.genetics.BreedingSystem;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.util.I18N;
import binnie.genetics.Genetics;
import binnie.genetics.item.GeneticsItems;
import binnie.genetics.machine.ModuleMachine;
import binnie.genetics.machine.analyser.Analyser;
import cpw.mods.fml.relauncher.Side;
import forestry.api.apiculture.IBee;
import forestry.api.arboriculture.ITree;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IBreedingTracker;
import forestry.api.genetics.IIndividual;
import forestry.api.lepidopterology.IButterfly;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class WindowAnalyst extends Window {
    protected IWidget baseWidget;
    protected ControlScrollableContent leftPage;
    protected ControlScrollableContent rightPage;
    protected Control tabBar;
    protected Panel analystPanel;
    protected List<ControlAnalystPage> analystPages;
    protected IArea analystPageSize;
    protected boolean isDatabase;
    protected boolean isMaster;
    protected boolean lockedSearch;
    protected IIndividual current;
    protected BreedingSystem currentSystem;

    private Control analystNone;
    private ControlSlide slideUpInv;

    public WindowAnalyst(EntityPlayer player, IInventory inventory, Side side, boolean database, boolean master) {
        super(312.0f, 230.0f, player, inventory, side);
        baseWidget = null;
        tabBar = null;
        analystPages = new ArrayList<>();
        analystPageSize = null;
        isDatabase = false;
        isMaster = false;
        lockedSearch = false;
        current = null;
        currentSystem = null;
        isDatabase = database;
        isMaster = master;
        lockedSearch = isDatabase;
    }

    @Override
    protected AbstractMod getMod() {
        return Genetics.instance;
    }

    @Override
    protected String getName() {
        return "Analyst";
    }

    private void setupValidators() {
        if (!isDatabase) {
            getWindowInventory().setValidator(0, new SlotValidator.Individual() {
                @Override
                public boolean isValid(ItemStack itemStack) {
                    return Analyser.isAnalysed(itemStack)
                            || (Analyser.isAnalysable(itemStack)
                                    && getWindowInventory().getStackInSlot(1) != null);
                }
            });
            getWindowInventory()
                    .setValidator(1, new SlotValidator.Item(GeneticsItems.DNADye.get(1), ModuleMachine.IconDye));
        }
    }

    @Override
    public void initialiseServer() {
        for (BreedingSystem system : Binnie.Genetics.getActiveSystems()) {
            IBreedingTracker tracker = system.getSpeciesRoot().getBreedingTracker(getWorld(), getUsername());
            if (tracker != null) {
                tracker.synchToPlayer(getPlayer());
            }
        }
        setupValidators();
    }

    @Override
    public void initialiseClient() {
        if (isDatabase) {
            if (isMaster) {
                setTitle(I18N.localise("genetics.item.registry.1.name"));
            } else {
                setTitle(I18N.localise("genetics.item.registry.0.name"));
            }
        } else {
            setTitle(I18N.localise("genetics.item.analyst.name"));
        }

        getWindowInventory().createSlot(0);
        baseWidget = new Widget(this);
        int x = 16;
        int y = 28;

        if (isDatabase) {
            for (BreedingSystem syst : Binnie.Genetics.getActiveSystems()) {
                new Control(this, x, y, 20.0f, 20.0f) {
                    @Override
                    public void initialise() {
                        addAttribute(WidgetAttribute.MOUSE_OVER);
                        addSelfEventHandler(new EventMouse.Down.Handler() {
                            @Override
                            public void onEvent(EventMouse.Down event) {
                                setSystem(syst);
                            }
                        });
                    }

                    @Override
                    public void getTooltip(Tooltip tooltip) {
                        tooltip.add(syst.getName());
                    }

                    @Override
                    public void onRenderBackground() {
                        CraftGUI.render.color(syst.getColor());
                        int outset = (getSystem() == syst) ? 1 : 0;
                        CraftGUI.render.texture(
                                CraftGUITexture.TabOutline, getArea().outset(outset));
                        if (getSystem() == syst) {
                            CraftGUI.render.color(1140850688 + syst.getColor());
                            CraftGUI.render.texture(
                                    CraftGUITexture.TabSolid, getArea().outset(outset));
                        }
                        CraftGUI.render.item(new IPoint(2.0f, 2.0f), syst.getItemStackRepresentative());
                    }
                };
                x += 22;
            }
        } else {
            new ControlSlot(this, x, y + 1).assign(InventoryType.Window, 0);
            x += 22;
            new ControlSlot(this, x, y + 1).assign(InventoryType.Window, 1);
            x += 26;
            setupValidators();
        }
        tabBar = new Control(this, x, 28.0f, w() - 16.0f - x, 20.0f);
        analystPanel = new Panel(this, 16.0f, 54.0f, 280.0f, 164.0f, MinecraftGUI.PanelType.Outline) {
            @Override
            public void onRenderBackground() {
                CraftGUI.render.gradientRect(getArea(), 0x44ffffff, 0x66ffffff);
                super.onRenderBackground();
            }

            @Override
            public void initialise() {
                setColor(0x444444);
                float sectionWidth = (w() - 8.0f - 4.0f) / 2.0f;
                leftPage =
                        new ControlScrollableContent<IWidget>(
                                this, 3.0f, 3.0f, sectionWidth + 2.0f, h() - 8.0f + 2.0f, 0.0f) {
                            @Override
                            public void onRenderBackground() {
                                if (getContent() == null) {
                                    return;
                                }
                                CraftGUI.render.color(getContent().getColor());
                                CraftGUI.render.texture(CraftGUITexture.TabOutline, getArea());
                            }
                        };
                new ControlScrollBar(this, sectionWidth + 2.0f - 3.0f, 6.0f, 3.0f, h() - 8.0f + 2.0f - 6.0f, leftPage) {
                    @Override
                    public void onRenderBackground() {
                        if (!isEnabled()) {
                            return;
                        }
                        if (leftPage.getContent() == null) {
                            return;
                        }
                        CraftGUI.render.gradientRect(
                                getArea(),
                                1140850688 + leftPage.getContent().getColor(),
                                0x44000000 + leftPage.getContent().getColor());
                        CraftGUI.render.solid(
                                getRenderArea(), leftPage.getContent().getColor());
                    }
                };
                rightPage =
                        new ControlScrollableContent<IWidget>(
                                this, 3.0f + sectionWidth + 4.0f, 3.0f, sectionWidth + 2.0f, h() - 8.0f + 2.0f, 0.0f) {
                            @Override
                            public void onRenderBackground() {
                                if (getContent() == null) {
                                    return;
                                }
                                CraftGUI.render.color(getContent().getColor());
                                CraftGUI.render.texture(CraftGUITexture.TabOutline, getArea());
                            }
                        };
                new ControlScrollBar(
                        this,
                        sectionWidth + 2.0f - 3.0f + sectionWidth + 4.0f,
                        6.0f,
                        3.0f,
                        h() - 8.0f + 2.0f - 6.0f,
                        rightPage) {
                    @Override
                    public void onRenderBackground() {
                        if (!isEnabled()) {
                            return;
                        }
                        if (rightPage.getContent() == null) {
                            return;
                        }
                        CraftGUI.render.gradientRect(
                                getArea(),
                                1140850688 + rightPage.getContent().getColor(),
                                0x44000000 + rightPage.getContent().getColor());
                        CraftGUI.render.solid(
                                getRenderArea(), rightPage.getContent().getColor());
                    }
                };
                analystPageSize = new IArea(1.0f, 1.0f, sectionWidth, h() - 8.0f);
            }
        };

        if (!isDatabase) {
            slideUpInv = new ControlSlide(
                    this,
                    (getSize().x() - 244.0f) / 2.0f,
                    getSize().y() - 80.0f + 1.0f,
                    244.0f,
                    80.0f,
                    Position.BOTTOM);
            new ControlPlayerInventory(slideUpInv, true);
            slideUpInv.setSlide(false);
        }
        addEventHandler(new EventKey.Down.Handler() {
            @Override
            public void onEvent(EventKey.Down event) {
                if (event.getKey() == 205) {
                    shiftPages(true);
                }
                if (event.getKey() == 203) {
                    shiftPages(false);
                }
            }
        });

        if (!isDatabase) {
            analystNone = new Control(analystPanel, 0.0f, 0.0f, analystPanel.w(), analystPanel.h()) {
                @Override
                public void initialise() {
                    new ControlTextCentered(this, 20.0f, I18N.localise("genetics.gui.analyst.info")).setColor(0x444444);
                    new ControlPlayerInventory(this);
                }
            };
        }
        setIndividual(null);
        setSystem(Binnie.Genetics.beeBreedingSystem);
    }

    public void updatePages(boolean systemChange) {
        int oldLeft = -1;
        int oldRight = -1;
        if (!systemChange) {
            oldLeft = analystPages.indexOf(leftPage.getContent());
            oldRight = analystPages.indexOf(rightPage.getContent());
        }

        ControlAnalystPage databasePage = null;
        if (isDatabase && !systemChange) {
            databasePage = ((analystPages.size() > 0) ? analystPages.get(0) : null);
        }

        analystPages.clear();
        setPage(leftPage, null);
        setPage(rightPage, null);
        if (isDatabase) {
            analystPages.add(
                    (databasePage != null)
                            ? databasePage
                            : new AnalystPageDatabase(analystPanel, analystPageSize, currentSystem, isMaster));
        }

        if (current != null) {
            analystPages.add(new AnalystPageDescription(analystPanel, analystPageSize, current));
            analystPages.add(new AnalystPageGenome(analystPanel, analystPageSize, true, current));
            if (!isDatabase) {
                analystPages.add(new AnalystPageGenome(analystPanel, analystPageSize, false, current));
                analystPages.add(new AnalystPageKaryogram(analystPanel, analystPageSize, current));
            }

            if (!(current instanceof ITree)) {
                analystPages.add(new AnalystPageClimate(analystPanel, analystPageSize, current));
            }

            if (current instanceof IBee) {
                analystPages.add(new AnalystPageProducts(analystPanel, analystPageSize, (IBee) current));
            } else if (current instanceof ITree) {
                analystPages.add(new AnalystPageFruit(analystPanel, analystPageSize, (ITree) current));
                analystPages.add(new AnalystPageWood(analystPanel, analystPageSize, (ITree) current));
            } else if (current instanceof IFlower) {
                analystPages.add(new AnalystPageSoil(analystPanel, analystPageSize, (IFlower) current));
            } else if (current instanceof IButterfly) {
                analystPages.add(new AnalystPageSpecimen(analystPanel, analystPageSize, (IButterfly) current));
            }

            analystPages.add(new AnalystPageBiology(analystPanel, analystPageSize, current));
            if (current instanceof IBee || current instanceof IButterfly) {
                analystPages.add(new AnalystPageBehaviour(analystPanel, analystPageSize, current));
            } else if (current instanceof ITree) {
                analystPages.add(new AnalystPageGrowth(analystPanel, analystPageSize, current));
            } else if (current instanceof IFlower) {
                analystPages.add(new AnalystPageAppearance(analystPanel, analystPageSize, (IFlower) current));
            }
            analystPages.add(new AnalystPageMutations(analystPanel, analystPageSize, current, isMaster));
        }

        tabBar.deleteAllChildren();
        float width = tabBar.w() / analystPages.size();
        float x = 0.0f;

        for (ControlAnalystPage page : analystPages) {
            new ControlTooltip(tabBar, x, 0.0f, width, tabBar.h()) {
                ControlAnalystPage value;

                @Override
                public void getTooltip(Tooltip tooltip) {
                    tooltip.add(value.getTitle());
                }

                @Override
                protected void initialise() {
                    super.initialise();
                    addAttribute(WidgetAttribute.MOUSE_OVER);
                    value = page;
                    addSelfEventHandler(new EventMouse.Down.Handler() {
                        @Override
                        public void onEvent(EventMouse.Down event) {
                            int currentIndex = analystPages.indexOf(rightPage.getContent());
                            int clickedIndex = analystPages.indexOf(value);
                            if (isDatabase) {
                                if (clickedIndex != 0 && clickedIndex != currentIndex) {
                                    setPage(rightPage, value);
                                }
                            } else {
                                if (clickedIndex < 0) {
                                    clickedIndex = 0;
                                }
                                if (clickedIndex < currentIndex) {
                                    ++clickedIndex;
                                }
                                setPage(rightPage, null);
                                setPage(leftPage, null);
                                setPage(rightPage, analystPages.get(clickedIndex));
                                setPage(leftPage, analystPages.get(clickedIndex - 1));
                            }
                        }
                    });
                }

                @Override
                public void onRenderBackground() {
                    boolean active = value == leftPage.getContent() || value == rightPage.getContent();
                    CraftGUI.render.color((active ? 0xff000000 : 0x44000000) + value.getColor());
                    CraftGUI.render.texture(CraftGUITexture.TabSolid, getArea().inset(1));
                    CraftGUI.render.color(value.getColor());
                    CraftGUI.render.texture(
                            CraftGUITexture.TabOutline, getArea().inset(1));
                    super.onRenderBackground();
                }
            };
            x += width;
        }

        if (analystPages.size() > 0) {
            setPage(leftPage, analystPages.get((oldLeft >= 0) ? oldLeft : 0));
        }
        if (analystPages.size() > 1) {
            setPage(rightPage, analystPages.get((oldRight >= 0) ? oldRight : 1));
        }
    }

    public void shiftPages(boolean right) {
        if (analystPages.size() < 2) {
            return;
        }
        int leftIndex = analystPages.indexOf(leftPage.getContent());
        int rightIndex = analystPages.indexOf(rightPage.getContent());
        if (right && rightIndex + 1 >= analystPages.size()) {
            return;
        }
        if (!lockedSearch && !right && leftIndex <= 0) {
            return;
        }
        if (!lockedSearch && !right && rightIndex <= 1) {
            return;
        }
        int newRightIndex = rightIndex + (right ? 1 : -1);
        int newLeftIndex = lockedSearch ? 0 : (newRightIndex - 1);
        float oldRightPercent = 0.0f;
        float oldLeftPercent = 0.0f;
        if (newLeftIndex == rightIndex) {
            oldRightPercent = rightPage.getPercentageIndex();
        }
        if (newRightIndex == leftIndex) {
            oldLeftPercent = leftPage.getPercentageIndex();
        }
        setPage(leftPage, null);
        setPage(rightPage, null);
        setPage(leftPage, analystPages.get(newLeftIndex));
        setPage(rightPage, analystPages.get(newRightIndex));
        analystPages.get(newLeftIndex).show();
        if (oldRightPercent != 0.0f) {
            leftPage.setPercentageIndex(oldRightPercent);
        }
        if (oldLeftPercent != 0.0f) {
            rightPage.setPercentageIndex(oldLeftPercent);
        }
    }

    public void setPage(ControlScrollableContent side, ControlAnalystPage page) {
        ControlAnalystPage existingPage = (ControlAnalystPage) side.getContent();
        if (existingPage != null) {
            existingPage.hide();
            side.setScrollableContent(null);
        }
        if (page != null) {
            page.show();
            side.setScrollableContent(page);
            side.setPercentageIndex(0.0f);
            page.setPosition(side.pos().add(1.0f, 1.0f));
        }
    }

    @Override
    public void onWindowInventoryChanged() {
        super.onWindowInventoryChanged();
        if (getWindowInventory().getStackInSlot(0) != null
                && !Analyser.isAnalysed(getWindowInventory().getStackInSlot(0))) {
            getWindowInventory()
                    .setInventorySlotContents(
                            0, Analyser.analyse(getWindowInventory().getStackInSlot(0)));
            getWindowInventory().decrStackSize(1, 1);
        }

        IIndividual ind =
                AlleleManager.alleleRegistry.getIndividual(getWindowInventory().getStackInSlot(0));
        if (ind != null) {
            ind.getGenome()
                    .getSpeciesRoot()
                    .getBreedingTracker(getWorld(), getUsername())
                    .registerBirth(ind);
        }

        if (isClient()) {
            setStack(getWindowInventory().getStackInSlot(0));
        }
    }

    public void setStack(ItemStack stack) {
        IIndividual ind = AlleleManager.alleleRegistry.getIndividual(stack);
        setIndividual(ind);
    }

    public IIndividual getIndividual() {
        return current;
    }

    public void setIndividual(IIndividual ind) {
        if (!isDatabase) {
            if (ind == null) {
                analystNone.show();
                slideUpInv.hide();
            } else {
                analystNone.hide();
                slideUpInv.show();
            }
        }

        if (ind == current || (ind != null && current != null && ind.isGeneticEqual(current))) {
            return;
        }

        boolean systemChange = (current = ind) != null
                && ind.getGenome().getSpeciesRoot() != getSystem().getSpeciesRoot();
        if (systemChange) {
            currentSystem = Binnie.Genetics.getSystem(ind.getGenome().getSpeciesRoot());
        }
        updatePages(systemChange);
    }

    public BreedingSystem getSystem() {
        return currentSystem;
    }

    public void setSystem(BreedingSystem system) {
        if (system == currentSystem) {
            return;
        }

        currentSystem = system;
        current = null;
        updatePages(true);
    }
}
