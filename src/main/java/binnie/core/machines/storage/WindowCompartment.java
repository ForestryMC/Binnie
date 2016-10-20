package binnie.core.machines.storage;

import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.machines.Machine;
import binnie.core.machines.transfer.TransferRequest;
import binnie.craftgui.controls.ControlCheckbox;
import binnie.craftgui.controls.ControlText;
import binnie.craftgui.controls.ControlTextEdit;
import binnie.craftgui.controls.button.ControlButton;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.controls.page.ControlPage;
import binnie.craftgui.controls.page.ControlPages;
import binnie.craftgui.controls.scroll.ControlScrollableContent;
import binnie.craftgui.controls.tab.ControlTab;
import binnie.craftgui.controls.tab.ControlTabBar;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.CraftGUIUtil;
import binnie.craftgui.core.geometry.IBorder;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.geometry.Position;
import binnie.craftgui.events.EventHandler;
import binnie.craftgui.events.EventMouse;
import binnie.craftgui.events.EventTextEdit;
import binnie.craftgui.events.EventValueChanged;
import binnie.craftgui.genetics.machine.WindowMachine;
import binnie.craftgui.minecraft.*;
import binnie.craftgui.minecraft.control.*;
import binnie.craftgui.resource.Texture;
import binnie.craftgui.resource.minecraft.CraftGUITexture;
import binnie.craftgui.window.Panel;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;

import java.util.*;
import java.util.Map.Entry;

public class WindowCompartment extends WindowMachine implements IWindowAffectsShiftClick {
    private final Map<Panel, Integer> panels;
    private ControlTextEdit tabName;
    private ControlItemDisplay tabIcon;
    private ControlColourSelector tabColour;
    boolean dueUpdate;
    private int currentTab;

    public static Window create(final EntityPlayer player, final IInventory inventory, final Side side) {
        return new WindowCompartment(player, inventory, side);
    }

    public WindowCompartment(final EntityPlayer player, final IInventory inventory, final Side side) {
        super(320, 226, player, inventory, side);
        this.panels = new HashMap<Panel, Integer>();
        this.currentTab = 0;
    }

    @Override
    public void initialiseClient() {
        this.setTitle(Machine.getMachine(this.getInventory()).getPackage().getDisplayName());
        int x = 16;
        final int y = 32;
        final ComponentCompartmentInventory inv = Machine.getMachine(this.getInventory()).getInterface(ComponentCompartmentInventory.class);
        Integer[] tabs1 = new Integer[0];
        Integer[] tabs2 = new Integer[0];
        if (inv.getTabNumber() == 4) {
            tabs1 = new Integer[]{0, 1};
            tabs2 = new Integer[]{2, 3};
        }
        if (inv.getTabNumber() == 6) {
            tabs1 = new Integer[]{0, 1, 2};
            tabs2 = new Integer[]{3, 4, 5};
        }
        if (inv.getTabNumber() == 8) {
            tabs1 = new Integer[]{0, 1, 2, 3};
            tabs2 = new Integer[]{4, 5, 6, 7};
        }
        final boolean doubleTabbed = tabs2.length > 0;
        final int compartmentPageWidth = 16 + 18 * inv.getPageSize() / 5;
        final int compartmentPageHeight = 106;
        final int compartmentWidth = compartmentPageWidth + (doubleTabbed ? 48 : 24);
        final int compartmentHeight = compartmentPageHeight;
        final Control controlCompartment = new Control(this, x, y, compartmentWidth, compartmentHeight);
        final ControlTabBar<Integer> tab = new ControlTabBar<Integer>(controlCompartment, 0.0f, 0.0f, 24.0f, compartmentPageHeight, Position.Left) {
            @Override
            public ControlTab<Integer> createTab(final float x, final float y, final float w, final float h, final Integer value) {
                return new ControlTabIcon<Integer>(this, x, y, w, h, value) {
                    @Override
                    public ItemStack getItemStack() {
                        return WindowCompartment.this.getTab(this.value).getIcon();
                    }

                    @Override
                    public String getName() {
                        return WindowCompartment.this.getTab(this.value).getName();
                    }

                    @Override
                    public int getOutlineColour() {
                        return WindowCompartment.this.getTab(this.value).getColor().getColour();
                    }

                    @Override
                    public boolean hasOutline() {
                        return true;
                    }
                };
            }
        };
        final String[] tabHelp = {"Compartment Tab", "Tabs that divide the inventory into sections. Each one can be labelled seperately."};
        tab.addHelp(tabHelp);
        tab.setValues(Arrays.asList(tabs1));
        tab.setValue(0);
        tab.addEventHandler(new EventValueChanged.Handler() {
            @Override
            public void onEvent(final EventValueChanged event) {
                final NBTTagCompound nbt = new NBTTagCompound();
                final int i = ((Integer) event.getValue()).intValue();
                nbt.setByte("i", (byte) i);
                Window.get(tab).sendClientAction("tab-change", nbt);
                WindowCompartment.this.currentTab = i;
            }
        }.setOrigin(EventHandler.Origin.DirectChild, tab));
        x += 24;
        final ControlPages<Integer> compartmentPages = new ControlPages<Integer>(controlCompartment, 24.0f, 0.0f, compartmentPageWidth, compartmentPageHeight);
        final ControlPage[] page = new ControlPage[inv.getTabNumber()];
        for (int p = 0; p < inv.getTabNumber(); ++p) {
            page[p] = new ControlPage(compartmentPages, Integer.valueOf(p));
        }
        CraftGUIUtil.linkWidgets(tab, compartmentPages);
        int i = 0;
        for (int p2 = 0; p2 < inv.getTabNumber(); ++p2) {
            final ControlPage thisPage = page[p2];
            final Panel panel = new Panel(thisPage, 0.0f, 0.0f, thisPage.w(), thisPage.h(), MinecraftGUI.PanelType.Black) {
                @Override
                public void onRenderForeground() {
                    final Texture iTexture = CraftGUI.Render.getTexture(CraftGUITexture.TabOutline);
                    CraftGUI.Render.colour(WindowCompartment.this.getTab(WindowCompartment.this.panels.get(this)).getColor().getColour());
                    CraftGUI.Render.texture(iTexture, this.getArea().inset(3));
                }
            };
            this.panels.put(panel, p2);
            final int[] slotsIDs = new int[inv.getPageSize()];
            for (int k = 0; k < inv.getPageSize(); ++k) {
                slotsIDs[k] = i++;
            }
            new ControlSlotArray(thisPage, 8, 8, inv.getPageSize() / 5, 5).create(slotsIDs);
        }
        x += compartmentPageWidth;
        if (tabs2.length > 0) {
            final ControlTabBar<Integer> tab2 = new ControlTabBar<Integer>(controlCompartment, 24 + compartmentPageWidth, 0.0f, 24.0f, compartmentPageHeight, Position.Right) {
                @Override
                public ControlTab<Integer> createTab(final float x, final float y, final float w, final float h, final Integer value) {
                    return new ControlTabIcon<Integer>(this, x, y, w, h, value) {
                        @Override
                        public ItemStack getItemStack() {
                            return WindowCompartment.this.getTab(this.value).getIcon();
                        }

                        @Override
                        public String getName() {
                            return WindowCompartment.this.getTab(this.value).getName();
                        }

                        @Override
                        public int getOutlineColour() {
                            return WindowCompartment.this.getTab(this.value).getColor().getColour();
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
            tab2.addEventHandler(new EventValueChanged.Handler() {
                @Override
                public void onEvent(final EventValueChanged event) {
                    final NBTTagCompound nbt = new NBTTagCompound();
                    final int i = ((Integer) event.getValue()).intValue();
                    nbt.setByte("i", (byte) i);
                    Window.get(tab).sendClientAction("tab-change", nbt);
                    WindowCompartment.this.currentTab = i;
                }
            }.setOrigin(EventHandler.Origin.DirectChild, tab2));
            CraftGUIUtil.linkWidgets(tab2, compartmentPages);
            x += 24;
        }
        x += 16;
        this.setSize(new IPoint(Math.max(32 + compartmentWidth, 252), this.h()));
        controlCompartment.setPosition(new IPoint((this.w() - controlCompartment.w()) / 2.0f, controlCompartment.y()));
        final ControlPlayerInventory invent = new ControlPlayerInventory(this, true);
        final ControlSlide slide = new ControlSlide(this, 0.0f, 134.0f, 136.0f, 92.0f, Position.Left);
        slide.setLabel("Tab Properties");
        slide.setSlide(false);
        slide.addHelp("Tab Properties");
        slide.addHelp("The label, colour and icon of the Tab can be altered here. Clicking on the icon with a held item will change it.");
        final Panel tabPropertyPanel = new Panel(slide, 16.0f, 8.0f, 112.0f, 76.0f, MinecraftGUI.PanelType.Gray);
        int y2 = 4;
        new ControlText(tabPropertyPanel, new IPoint(4.0f, y2), "Tab Name:");
        final Panel parent = tabPropertyPanel;
        final float x2 = 4.0f;
        y2 += 12;
        (this.tabName = new ControlTextEdit(parent, x2, y2, 104.0f, 12.0f)).addSelfEventHandler(new EventTextEdit.Handler() {
            @Override
            public void onEvent(final EventTextEdit event) {
                final CompartmentTab tab = WindowCompartment.this.getCurrentTab();
                tab.setName(event.getValue());
                final NBTTagCompound nbt = new NBTTagCompound();
                tab.writeToNBT(nbt);
                WindowCompartment.this.sendClientAction("comp-change-tab", nbt);
            }
        }.setOrigin(EventHandler.Origin.Self, this.tabName));
        y2 += 20;
        new ControlText(tabPropertyPanel, new IPoint(4.0f, y2), "Tab Icon: ");
        (this.tabIcon = new ControlItemDisplay(tabPropertyPanel, 58.0f, y2 - 4)).setItemStack(new ItemStack(Items.PAPER));
        this.tabIcon.addAttribute(Attribute.MouseOver);
        this.tabIcon.addSelfEventHandler(new EventMouse.Down.Handler() {
            @Override
            public void onEvent(final EventMouse.Down event) {
                if (WindowCompartment.this.getHeldItemStack() == null) {
                    return;
                }
                final CompartmentTab tab = WindowCompartment.this.getCurrentTab();
                final ItemStack stack = WindowCompartment.this.getHeldItemStack().copy();
                stack.stackSize = 1;
                tab.setIcon(stack);
                final NBTTagCompound nbt = new NBTTagCompound();
                tab.writeToNBT(nbt);
                WindowCompartment.this.sendClientAction("comp-change-tab", nbt);
            }
        });
        this.tabColour = new ControlColourSelector(tabPropertyPanel, 82.0f, y2 - 4, 16.0f, 16.0f, EnumColor.White);
        this.tabIcon.addHelp("Icon for Current Tab");
        this.tabIcon.addHelp("Click here with an item to change");
        y2 += 20;
        new ControlText(tabPropertyPanel, new IPoint(4.0f, y2), "Colour: ");
        final int cw = 8;
        final Panel panelColour = new Panel(tabPropertyPanel, 40.0f, y2 - 4, cw * 8 + 2, cw * 2 + 1, MinecraftGUI.PanelType.Gray);
        for (int cc = 0; cc < 16; ++cc) {
            final ControlColourSelector color = new ControlColourSelector(panelColour, 1 + cw * (cc % 8), 1 + cw * (cc / 8), cw, cw, EnumColor.values()[cc]);
            color.addSelfEventHandler(new EventMouse.Down.Handler() {
                @Override
                public void onEvent(final EventMouse.Down event) {
                    final CompartmentTab tab = WindowCompartment.this.getCurrentTab();
                    tab.setColor(color.getValue());
                    final NBTTagCompound nbt = new NBTTagCompound();
                    tab.writeToNBT(nbt);
                    WindowCompartment.this.sendClientAction("comp-change-tab", nbt);
                }
            });
            color.addHelp("Colour Selector");
            color.addHelp("Select a colour to highlight the current tab");
        }
        y2 += 20;
        final ControlButton searchButton = new ControlButton(controlCompartment, compartmentWidth - 24 - 64 - 8, compartmentPageHeight, 64.0f, 16.0f, "Search") {
            @Override
            protected void onMouseClick(final EventMouse.Down event) {
                WindowCompartment.this.createSearchDialog();
            }

            @Override
            public void onRenderBackground() {
                final Object texture = this.isMouseOver() ? CraftGUITexture.TabHighlighted : CraftGUITexture.Tab;
                CraftGUI.Render.texture(CraftGUI.Render.getTexture(texture).crop(Position.Bottom, 8.0f), this.getArea());
            }
        };
        searchButton.addHelp("Search Button");
        searchButton.addHelp("Clicking this will open the Search dialog. This allows you to search the inventory for specific items.");
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
            }

            @Override
            public void initialise() {
                final ControlScrollableContent<IWidget> scroll = new ControlScrollableContent<IWidget>(this, 124.0f, 16.0f, 116.0f, 92.0f, 6.0f) {
                    @Override
                    public void onRenderBackground() {
                        CraftGUI.Render.colour(11184810);
                        CraftGUI.Render.texture(CraftGUITexture.Outline, this.getArea().inset(new IBorder(0.0f, 6.0f, 0.0f, 0.0f)));
                    }
                };
                scroll.setScrollableContent(this.slotGrid = new Control(scroll, 1.0f, 1.0f, 108.0f, 18.0f));
                new ControlPlayerInventory(this, true);
                new ControlTextEdit(this, 16.0f, 16.0f, 100.0f, 14.0f).addEventHandler(new EventTextEdit.Handler() {
                    @Override
                    public void onEvent(final EventTextEdit event) {
                        textSearch = event.value;
                        updateSearch();
                    }
                });
                this.includeItems = true;
                this.includeBlocks = true;
                new ControlCheckbox(this, 16.0f, 40.0f, 100.0f, "Sort A-Z", this.sortByName) {
                    @Override
                    protected void onValueChanged(final boolean value) {
                        sortByName = value;
                        updateSearch();
                    }
                };
                new ControlCheckbox(this, 16.0f, 64.0f, 100.0f, "Include Items", this.includeItems) {
                    @Override
                    protected void onValueChanged(final boolean value) {
                        includeItems = value;
                        updateSearch();
                    }
                };
                new ControlCheckbox(this, 16.0f, 88.0f, 100.0f, "Include Blocks", this.includeBlocks) {
                    @Override
                    protected void onValueChanged(final boolean value) {
                        includeBlocks = value;
                        updateSearch();
                    }
                };
                this.updateSearch();
            }

            private void updateSearch() {
                Map<Integer, String> slotIds = new HashMap<Integer, String>();
                final IInventory inv = WindowCompartment.this.getInventory();
                for (int i = 0; i < inv.getSizeInventory(); ++i) {
                    final ItemStack stack = inv.getStackInSlot(i);
                    if (stack != null) {
                        final String name = stack.getDisplayName().toLowerCase();
                        if (this.textSearch == null || name.contains(this.textSearch)) {
                            if (this.includeBlocks || Block.getBlockFromItem(stack.getItem()) == Blocks.AIR) {
                                if (this.includeItems || Block.getBlockFromItem(stack.getItem()) != Blocks.AIR) {
                                    slotIds.put(i, name);
                                }
                            }
                        }
                    }
                }
                if (this.sortByName) {
                    final List<Entry<Integer, String>> list = new LinkedList(slotIds.entrySet());
                    Collections.sort(list, new Comparator<Entry<Integer, String>>() {
                        @Override
                        public int compare(Entry<Integer, String> o1, Entry<Integer, String> o2) {
                            return -o2.getValue().compareTo(o1.getValue());
                        }
                    });
                    final Map result = new LinkedHashMap();
                    for (final Map.Entry entry : list) {
                        result.put(entry.getKey(), entry.getValue());
                    }
                    slotIds = result;
                }
                int y = 0;
                int x = 0;
                final int width = 108;
                final int height = 2 + 18 * (1 + (slotIds.size() - 1) / 6);
                this.slotGrid.deleteAllChildren();
                this.slotGrid.setSize(new IPoint(width, height));
                for (final int k : slotIds.keySet()) {
                    new ControlSlot(this.slotGrid, x, y).assign(k);
                    x += 18;
                    if (x >= 108) {
                        x = 0;
                        y += 18;
                    }
                }
                while (y < 108 || x != 0) {
                    new ControlSlot(this.slotGrid, x, y);
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
        this.updateTabs();
    }

    public void updateTabs() {
        this.tabName.setValue(this.getCurrentTab().getName());
        this.tabIcon.setItemStack(this.getCurrentTab().getIcon());
        this.tabColour.setValue(this.getCurrentTab().getColor());
    }

    @Override
    public void recieveGuiNBT(final Side side, final EntityPlayer player, final String name, final NBTTagCompound action) {
        super.recieveGuiNBT(side, player, name, action);
        if (name.equals("tab-change")) {
            this.currentTab = action.getByte("i");
        }
    }

    @Override
    public String getTitle() {
        return "Compartment";
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
    public void alterRequest(final TransferRequest request) {
        if (request.getDestination() == this.getInventory()) {
            final ComponentCompartmentInventory inv = Machine.getMachine(this.getInventory()).getInterface(ComponentCompartmentInventory.class);
            request.setTargetSlots(inv.getSlotsForTab(this.currentTab));
        }
    }

    public CompartmentTab getTab(final int i) {
        return Machine.getInterface(ComponentCompartmentInventory.class, this.getInventory()).getTab(i);
    }

    public CompartmentTab getCurrentTab() {
        return this.getTab(this.currentTab);
    }
}
