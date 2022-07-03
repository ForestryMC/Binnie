package binnie.core.machines.storage;

import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.controls.ControlButton;
import binnie.core.craftgui.controls.ControlCheckbox;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextEdit;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.controls.page.ControlPage;
import binnie.core.craftgui.controls.page.ControlPages;
import binnie.core.craftgui.controls.scroll.ControlScrollableContent;
import binnie.core.craftgui.controls.tab.ControlTab;
import binnie.core.craftgui.controls.tab.ControlTabBar;
import binnie.core.craftgui.events.EventHandler;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.events.EventTextEdit;
import binnie.core.craftgui.events.EventValueChanged;
import binnie.core.craftgui.geometry.CraftGUIUtil;
import binnie.core.craftgui.geometry.IBorder;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.geometry.Position;
import binnie.core.craftgui.minecraft.Dialog;
import binnie.core.craftgui.minecraft.EnumColor;
import binnie.core.craftgui.minecraft.IWindowAffectsShiftClick;
import binnie.core.craftgui.minecraft.MinecraftGUI;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlItemDisplay;
import binnie.core.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.core.craftgui.minecraft.control.ControlSlide;
import binnie.core.craftgui.minecraft.control.ControlSlot;
import binnie.core.craftgui.minecraft.control.ControlSlotArray;
import binnie.core.craftgui.minecraft.control.ControlTabIcon;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;
import binnie.core.craftgui.window.Panel;
import binnie.core.machines.Machine;
import binnie.core.machines.transfer.TransferRequest;
import binnie.core.util.I18N;
import binnie.genetics.craftgui.WindowMachine;
import cpw.mods.fml.relauncher.Side;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

@SuppressWarnings("Duplicates")
public class WindowCompartment extends WindowMachine implements IWindowAffectsShiftClick {
    private Map<Panel, Integer> panels;
    private ControlTextEdit tabName;
    private ControlItemDisplay tabIcon;
    private ControlColourSelector tabColour;
    private int currentTab;

    public static Window create(EntityPlayer player, IInventory inventory, Side side) {
        return new WindowCompartment(player, inventory, side);
    }

    public WindowCompartment(EntityPlayer player, IInventory inventory, Side side) {
        super(320, 226, player, inventory, side);
        panels = new HashMap<>();
        currentTab = 0;
    }

    @Override
    public void initialiseClient() {
        setTitle(Machine.getMachine(getInventory()).getPackage().getDisplayName());
        int x = 16;
        int y = 32;
        ComponentCompartmentInventory inv =
                Machine.getMachine(getInventory()).getInterface(ComponentCompartmentInventory.class);
        Integer[] tabs1 = new Integer[0];
        Integer[] tabs2 = new Integer[0];
        if (inv.getTabNumber() == 4) {
            tabs1 = new Integer[] {0, 1};
            tabs2 = new Integer[] {2, 3};
        }
        if (inv.getTabNumber() == 6) {
            tabs1 = new Integer[] {0, 1, 2};
            tabs2 = new Integer[] {3, 4, 5};
        }
        if (inv.getTabNumber() == 8) {
            tabs1 = new Integer[] {0, 1, 2, 3};
            tabs2 = new Integer[] {4, 5, 6, 7};
        }
        boolean doubleTabbed = tabs2.length > 0;
        int compartmentPageWidth = 16 + 18 * inv.getPageSize() / 5;
        int compartmentPageHeight = 106;
        int compartmentWidth = compartmentPageWidth + (doubleTabbed ? 48 : 24);

        Control controlCompartment = new Control(this, x, y, compartmentWidth, compartmentPageHeight);
        ControlTabBar<Integer> tab =
                new ControlTabBar<Integer>(
                        controlCompartment, 0.0f, 0.0f, 24.0f, compartmentPageHeight, Position.LEFT) {
                    @Override
                    public ControlTab<Integer> createTab(float x, float y, float w, float h, Integer value) {
                        return new ControlTabIcon<Integer>(this, x, y, w, h, value) {
                            @Override
                            public ItemStack getItemStack() {
                                return getTab(value).getIcon();
                            }

                            @Override
                            public String getName() {
                                return getTab(value).getName();
                            }

                            @Override
                            public int getOutlineColor() {
                                return getTab(value).getColor().getColor();
                            }

                            @Override
                            public boolean hasOutline() {
                                return true;
                            }
                        };
                    }
                };

        String[] tabHelp = {
            I18N.localise("binniecore.machine.storage.tab"), I18N.localise("binniecore.machine.storage.tab.desc")
        };
        tab.addHelp(tabHelp);
        tab.setValues(Arrays.asList(tabs1));
        tab.setValue(0);
        tab.addEventHandler(
                new EventValueChanged.Handler() {
                    @Override
                    public void onEvent(EventValueChanged event) {
                        NBTTagCompound nbt = new NBTTagCompound();
                        int i = (Integer) event.getValue();
                        nbt.setByte("i", (byte) i);
                        Window.get(tab).sendClientAction("tab-change", nbt);
                        currentTab = i;
                    }
                }.setOrigin(EventHandler.Origin.DirectChild, tab));
        x += 24;

        ControlPages<Integer> compartmentPages =
                new ControlPages<>(controlCompartment, 24.0f, 0.0f, compartmentPageWidth, compartmentPageHeight);
        ControlPage[] page = new ControlPage[inv.getTabNumber()];
        for (int p = 0; p < inv.getTabNumber(); ++p) {
            page[p] = new ControlPage(compartmentPages, p);
        }
        CraftGUIUtil.linkWidgets(tab, compartmentPages);
        int i = 0;
        for (int p2 = 0; p2 < inv.getTabNumber(); ++p2) {
            ControlPage thisPage = page[p2];
            Panel panel = new Panel(thisPage, 0.0f, 0.0f, thisPage.w(), thisPage.h(), MinecraftGUI.PanelType.Black) {
                @Override
                public void onRenderForeground() {
                    Texture iTexture = CraftGUI.render.getTexture(CraftGUITexture.TabOutline);
                    CraftGUI.render.color(getTab(panels.get(this)).getColor().getColor());
                    CraftGUI.render.texture(iTexture, getArea().inset(3));
                }
            };

            panels.put(panel, p2);
            int[] slotsIDs = new int[inv.getPageSize()];
            for (int k = 0; k < inv.getPageSize(); ++k) {
                slotsIDs[k] = i++;
            }
            new ControlSlotArray(thisPage, 8, 8, inv.getPageSize() / 5, 5).create(slotsIDs);
        }
        x += compartmentPageWidth;
        if (tabs2.length > 0) {
            ControlTabBar<Integer> tab2 =
                    new ControlTabBar<Integer>(
                            controlCompartment,
                            24 + compartmentPageWidth,
                            0.0f,
                            24.0f,
                            compartmentPageHeight,
                            Position.RIGHT) {
                        @Override
                        public ControlTab<Integer> createTab(float x, float y, float w, float h, Integer value) {
                            return new ControlTabIcon<Integer>(this, x, y, w, h, value) {
                                @Override
                                public ItemStack getItemStack() {
                                    return getTab(value).getIcon();
                                }

                                @Override
                                public String getName() {
                                    return getTab(value).getName();
                                }

                                @Override
                                public int getOutlineColor() {
                                    return getTab(value).getColor().getColor();
                                }

                                @Override
                                public boolean hasOutline() {
                                    return true;
                                }
                            };
                        }
                    };
            tab2.setValues(Arrays.asList(tabs2));
            tab2.setValue(0);
            tab2.addHelp(tabHelp);
            tab2.addEventHandler(
                    new EventValueChanged.Handler() {
                        @Override
                        public void onEvent(EventValueChanged event) {
                            NBTTagCompound nbt = new NBTTagCompound();
                            int i = (Integer) event.getValue();
                            nbt.setByte("i", (byte) i);
                            Window.get(tab).sendClientAction("tab-change", nbt);
                            currentTab = i;
                        }
                    }.setOrigin(EventHandler.Origin.DirectChild, tab2));
            CraftGUIUtil.linkWidgets(tab2, compartmentPages);
            x += 24;
        }

        setSize(new IPoint(Math.max(32 + compartmentWidth, 252), h()));
        controlCompartment.setPosition(new IPoint((w() - controlCompartment.w()) / 2.0f, controlCompartment.y()));
        new ControlPlayerInventory(this, true);
        ControlSlide slide = new ControlSlide(this, 0.0f, 134.0f, 136.0f, 92.0f, Position.LEFT);
        slide.setLabel(I18N.localise("binniecore.machine.storage.tab.properties"));
        slide.setSlide(false);
        slide.addHelp(I18N.localise("binniecore.machine.storage.tooltip.properties"));
        slide.addHelp(I18N.localise("binniecore.machine.storage.tooltip.properties.desc"));
        Panel tabPropertyPanel = new Panel(slide, 16.0f, 8.0f, 112.0f, 76.0f, MinecraftGUI.PanelType.Gray);
        int y2 = 4;
        new ControlText(tabPropertyPanel, new IPoint(4.0f, y2), I18N.localise("binniecore.machine.storage.tab.name"));
        float x2 = 4.0f;
        y2 += 12;

        tabName = new ControlTextEdit(tabPropertyPanel, x2, y2, 104.0f, 12.0f);
        tabName.addSelfEventHandler(
                new EventTextEdit.Handler() {
                    @Override
                    public void onEvent(EventTextEdit event) {
                        CompartmentTab tab = getCurrentTab();
                        tab.setName(event.getValue());
                        NBTTagCompound nbt = new NBTTagCompound();
                        tab.writeToNBT(nbt);
                        sendClientAction("comp-change-tab", nbt);
                    }
                }.setOrigin(EventHandler.Origin.Self, tabName));
        y2 += 20;
        new ControlText(tabPropertyPanel, new IPoint(4.0f, y2), I18N.localise("binniecore.machine.storage.tab.icon"));

        tabIcon = new ControlItemDisplay(tabPropertyPanel, 58.0f, y2 - 4);
        tabIcon.setItemStack(new ItemStack(Items.paper));
        tabIcon.addAttribute(WidgetAttribute.MOUSE_OVER);
        tabIcon.addSelfEventHandler(new EventMouse.Down.Handler() {
            @Override
            public void onEvent(EventMouse.Down event) {
                if (getHeldItemStack() == null) {
                    return;
                }
                CompartmentTab tab = getCurrentTab();
                ItemStack stack = getHeldItemStack().copy();
                stack.stackSize = 1;
                tab.setIcon(stack);
                NBTTagCompound nbt = new NBTTagCompound();
                tab.writeToNBT(nbt);
                sendClientAction("comp-change-tab", nbt);
            }
        });

        tabColour = new ControlColourSelector(tabPropertyPanel, 82.0f, y2 - 4, 16.0f, 16.0f, EnumColor.White);
        tabIcon.addHelp(I18N.localise("binniecore.machine.storage.tooltip.icon"));
        tabIcon.addHelp(I18N.localise("binniecore.machine.storage.tooltip.icon.desc"));
        y2 += 20;

        new ControlText(tabPropertyPanel, new IPoint(4.0f, y2), I18N.localise("binniecore.machine.storage.tab.color"));
        int cw = 8;
        Panel panelColour =
                new Panel(tabPropertyPanel, 40.0f, y2 - 4, cw * 8 + 2, cw * 2 + 1, MinecraftGUI.PanelType.Gray);
        for (int cc = 0; cc < 16; ++cc) {
            ControlColourSelector color = new ControlColourSelector(
                    panelColour, 1 + cw * (cc % 8), 1 + cw * (cc / 8), cw, cw, EnumColor.values()[cc]);
            color.addSelfEventHandler(new EventMouse.Down.Handler() {
                @Override
                public void onEvent(EventMouse.Down event) {
                    CompartmentTab tab = getCurrentTab();
                    tab.setColor(color.getValue());
                    NBTTagCompound nbt = new NBTTagCompound();
                    tab.writeToNBT(nbt);
                    sendClientAction("comp-change-tab", nbt);
                }
            });
            color.addHelp(I18N.localise("binniecore.machine.storage.tooltip.color"));
            color.addHelp(I18N.localise("binniecore.machine.storage.tooltip.color.desc"));
        }

        ControlButton searchButton = new SearchButton(controlCompartment, compartmentWidth, compartmentPageHeight);
        searchButton.addHelp(I18N.localise("binniecore.machine.storage.tooltip.search"));
        searchButton.addHelp(I18N.localise("binniecore.machine.storage.tooltip.search.desc"));
    }

    public void createSearchDialog() {
        new Dialog(this, 252.0f, 192.0f) {
            Control slotGrid;
            String textSearch = "";
            boolean sortByName = false;
            boolean includeItems = true;
            boolean includeBlocks = true;

            @Override
            public void onClose() {
                // ignored
            }

            @Override
            public void initialise() {
                ControlScrollableContent<IWidget> scroll =
                        new ControlScrollableContent<IWidget>(this, 124.0f, 16.0f, 116.0f, 92.0f, 6.0f) {
                            @Override
                            public void onRenderBackground() {
                                CraftGUI.render.color(11184810);
                                CraftGUI.render.texture(
                                        CraftGUITexture.Outline, getArea().inset(new IBorder(0.0f, 6.0f, 0.0f, 0.0f)));
                            }
                        };
                slotGrid = new Control(scroll, 1.0f, 1.0f, 108.0f, 18.0f);
                scroll.setScrollableContent(slotGrid);
                new ControlPlayerInventory(this, true);
                new ControlTextEdit(this, 16.0f, 16.0f, 100.0f, 14.0f).addEventHandler(new EventTextEdit.Handler() {
                    @Override
                    public void onEvent(EventTextEdit event) {
                        textSearch = event.value;
                        updateSearch();
                    }
                });
                includeItems = true;
                includeBlocks = true;
                new ControlCheckbox(
                        this, 16.0f, 40.0f, 100.0f, I18N.localise("binniecore.machine.storage.sort"), sortByName) {
                    @Override
                    protected void onValueChanged(boolean value) {
                        sortByName = value;
                        updateSearch();
                    }
                };
                new ControlCheckbox(
                        this,
                        16.0f,
                        64.0f,
                        100.0f,
                        I18N.localise("binniecore.machine.storage.filterItems"),
                        includeItems) {
                    @Override
                    protected void onValueChanged(boolean value) {
                        includeItems = value;
                        updateSearch();
                    }
                };
                new ControlCheckbox(
                        this,
                        16.0f,
                        88.0f,
                        100.0f,
                        I18N.localise("binniecore.machine.storage.filterBlocks"),
                        includeBlocks) {
                    @Override
                    protected void onValueChanged(boolean value) {
                        includeBlocks = value;
                        updateSearch();
                    }
                };
                updateSearch();
            }

            private void updateSearch() {
                Map<Integer, String> slotIds = new HashMap<>();
                IInventory inv = getInventory();
                for (int i = 0; i < inv.getSizeInventory(); ++i) {
                    ItemStack stack = inv.getStackInSlot(i);
                    if (stack == null) {
                        continue;
                    }

                    String name = stack.getDisplayName().toLowerCase();
                    if (textSearch == null || name.contains(textSearch)) {
                        Block block = Block.getBlockFromItem(stack.getItem());
                        if ((includeBlocks || block == Blocks.air) && (includeItems || block != Blocks.air)) {
                            slotIds.put(i, name);
                        }
                    }
                }

                if (sortByName) {
                    List<Entry<Integer, String>> list = new LinkedList(slotIds.entrySet());
                    list.sort(new Comparator<Entry<Integer, String>>() {
                        @Override
                        public int compare(Entry<Integer, String> o1, Entry<Integer, String> o2) {
                            return -o2.getValue().compareTo(o1.getValue());
                        }
                    });

                    Map result = new LinkedHashMap();
                    for (Map.Entry entry : list) {
                        result.put(entry.getKey(), entry.getValue());
                    }
                    slotIds = result;
                }

                int y = 0;
                int x = 0;
                int width = 108;
                int height = 2 + 18 * (1 + (slotIds.size() - 1) / 6);
                slotGrid.deleteAllChildren();
                slotGrid.setSize(new IPoint(width, height));

                for (int k : slotIds.keySet()) {
                    new ControlSlot(slotGrid, x, y).assign(k);
                    x += 18;
                    if (x >= 108) {
                        x = 0;
                        y += 18;
                    }
                }

                while (y < 108 || x != 0) {
                    new ControlSlot(slotGrid, x, y);
                    x += 18;
                    if (x >= 108) {
                        x = 0;
                        y += 18;
                    }
                }
            }
        };
    }

    @Override
    public void onUpdateClient() {
        super.onUpdateClient();
        updateTabs();
    }

    public void updateTabs() {
        CompartmentTab tab = getCurrentTab();
        tabName.setValue(tab.getName());
        tabIcon.setItemStack(tab.getIcon());
        tabColour.setValue(tab.getColor());
    }

    @Override
    public void recieveGuiNBT(Side side, EntityPlayer player, String name, NBTTagCompound nbt) {
        super.recieveGuiNBT(side, player, name, nbt);
        if (name.equals("tab-change")) {
            currentTab = nbt.getByte("i");
        }
    }

    @Override
    public String getTitle() {
        return I18N.localise("binniecore.machine.storage.compartment");
    }

    @Override
    protected AbstractMod getMod() {
        return BinnieCore.instance;
    }

    @Override
    protected String getName() {
        return "compartment";
    }

    @Override
    public void alterRequest(TransferRequest request) {
        IInventory inventory = getInventory();
        if (request.getDestination() == inventory) {
            ComponentCompartmentInventory inv =
                    Machine.getMachine(inventory).getInterface(ComponentCompartmentInventory.class);
            request.setTargetSlots(inv.getSlotsForTab(currentTab));
        }
    }

    public CompartmentTab getTab(int i) {
        return Machine.getInterface(ComponentCompartmentInventory.class, getInventory())
                .getTab(i);
    }

    public CompartmentTab getCurrentTab() {
        return getTab(currentTab);
    }

    private class SearchButton extends ControlButton {
        public SearchButton(Control controlCompartment, int compartmentWidth, int compartmentPageHeight) {
            super(
                    controlCompartment,
                    compartmentWidth - 24 - 64 - 8,
                    compartmentPageHeight,
                    64.0f,
                    16.0f,
                    I18N.localise("binniecore.machine.storage.search"));
        }

        @Override
        protected void onMouseClick(EventMouse.Down event) {
            createSearchDialog();
        }

        @Override
        public void onRenderBackground() {
            Object texture = isMouseOver() ? CraftGUITexture.TabHighlighted : CraftGUITexture.Tab;
            CraftGUI.render.texture(CraftGUI.render.getTexture(texture).crop(Position.BOTTOM, 8.0f), getArea());
        }
    }
}
