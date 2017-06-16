package binnie.core.machines.storage;

import javax.annotation.Nullable;
import java.util.Arrays;
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

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.craftgui.Attribute;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlCheckbox;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextEdit;
import binnie.core.craftgui.controls.button.ControlButton;
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
import binnie.core.craftgui.geometry.Border;
import binnie.core.craftgui.geometry.CraftGUIUtil;
import binnie.core.craftgui.geometry.Point;
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
import binnie.core.craftgui.renderer.RenderUtil;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;
import binnie.core.craftgui.window.Panel;
import binnie.core.machines.Machine;
import binnie.core.machines.transfer.TransferRequest;
import binnie.genetics.machine.craftgui.WindowMachine;

public class WindowCompartment extends WindowMachine implements IWindowAffectsShiftClick {
	private final Map<Panel, Integer> panels;
	boolean dueUpdate;
	private ControlTextEdit tabName;
	private ControlItemDisplay tabIcon;
	private ControlColourSelector tabColour;
	private int currentTab;

	public WindowCompartment(final EntityPlayer player, @Nullable final IInventory inventory, final Side side) {
		super(320, 226, player, inventory, side);
		this.panels = new HashMap<>();
		this.currentTab = 0;
	}

	public static Window create(final EntityPlayer player, final IInventory inventory, final Side side) {
		return new WindowCompartment(player, inventory, side);
	}

	@Override
	@SideOnly(Side.CLIENT)
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
		final ControlTabBar<Integer> tab = new ControlTabBar<Integer>(controlCompartment, 0, 0, 24, compartmentPageHeight, Position.LEFT, Arrays.asList(tabs1)) {
			@Override
			public ControlTab<Integer> createTab(final int x, final int y, final int w, final int h, final Integer value) {
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
		tab.addEventHandler(new EventValueChanged.Handler() {
			@Override
			public void onEvent(final EventValueChanged event) {
				final NBTTagCompound nbt = new NBTTagCompound();
				final int i = (Integer) event.getValue();
				nbt.setByte("i", (byte) i);
				Window.get(tab).sendClientAction("tab-change", nbt);
				WindowCompartment.this.currentTab = i;
			}
		}.setOrigin(EventHandler.Origin.DirectChild, tab));
		x += 24;
		final ControlPages<Integer> compartmentPages = new ControlPages<>(controlCompartment, 24, 0, compartmentPageWidth, compartmentPageHeight);
		final ControlPage[] page = new ControlPage[inv.getTabNumber()];
		for (int p = 0; p < inv.getTabNumber(); ++p) {
			page[p] = new ControlPage(compartmentPages, p);
		}
		CraftGUIUtil.linkWidgets(tab, compartmentPages);
		int i = 0;
		for (int p2 = 0; p2 < inv.getTabNumber(); ++p2) {
			final ControlPage thisPage = page[p2];
			final Panel panel = new Panel(thisPage, 0, 0, thisPage.width(), thisPage.height(), MinecraftGUI.PanelType.Black) {
				@Override
				@SideOnly(Side.CLIENT)
				public void onRenderForeground(int guiWidth, int guiHeight) {
					final Texture iTexture = CraftGUI.render.getTexture(CraftGUITexture.TabOutline);
					RenderUtil.setColour(WindowCompartment.this.getTab(WindowCompartment.this.panels.get(this)).getColor().getColour());
					CraftGUI.render.texture(iTexture, this.getArea().inset(3));
				}
			};
			this.panels.put(panel, p2);
			final int[] slotsIDs = new int[inv.getPageSize()];
			for (int k = 0; k < inv.getPageSize(); ++k) {
				slotsIDs[k] = i++;
			}
			new ControlSlotArray.Builder(thisPage, 8, 8, inv.getPageSize() / 5, 5).create(slotsIDs);
		}
		x += compartmentPageWidth;
		if (tabs2.length > 0) {
			final ControlTabBar<Integer> tab2 = new ControlTabBar<Integer>(controlCompartment, 24 + compartmentPageWidth, 0, 24, compartmentPageHeight, Position.RIGHT, Arrays.asList(tabs2)) {
				@Override
				public ControlTab<Integer> createTab(final int x, final int y, final int w, final int h, final Integer value) {
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
			tab2.addHelp(tabHelp);
			tab2.addEventHandler(new EventValueChanged.Handler() {
				@Override
				public void onEvent(final EventValueChanged event) {
					final NBTTagCompound nbt = new NBTTagCompound();
					final int i = (Integer) event.getValue();
					nbt.setByte("i", (byte) i);
					Window.get(tab).sendClientAction("tab-change", nbt);
					WindowCompartment.this.currentTab = i;
				}
			}.setOrigin(EventHandler.Origin.DirectChild, tab2));
			CraftGUIUtil.linkWidgets(tab2, compartmentPages);
			x += 24;
		}
		x += 16;
		this.setSize(new Point(Math.max(32 + compartmentWidth, 252), this.height()));
		controlCompartment.setPosition(new Point((this.width() - controlCompartment.width()) / 2, controlCompartment.yPos()));
		final ControlPlayerInventory invent = new ControlPlayerInventory(this, true);
		final ControlSlide slide = new ControlSlide(this, 0, 134, 136, 92, Position.LEFT);
		slide.setLabel("Tab Properties");
		slide.setSlide(false);
		slide.addHelp("Tab Properties");
		slide.addHelp("The label, colour and icon of the Tab can be altered here. Clicking on the icon with a held item will change it.");
		final Panel tabPropertyPanel = new Panel(slide, 16, 8, 112, 76, MinecraftGUI.PanelType.Gray);
		int y2 = 4;
		new ControlText(tabPropertyPanel, new Point(4, y2), "Tab Name:");
		final Panel parent = tabPropertyPanel;
		final int x2 = 4;
		y2 += 12;
		(this.tabName = new ControlTextEdit(parent, x2, y2, 104, 12)).addSelfEventHandler(new EventTextEdit.Handler() {
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
		new ControlText(tabPropertyPanel, new Point(4, y2), "Tab Icon: ");
		(this.tabIcon = new ControlItemDisplay(tabPropertyPanel, 58, y2 - 4)).setItemStack(new ItemStack(Items.PAPER));
		this.tabIcon.addAttribute(Attribute.MouseOver);
		this.tabIcon.addSelfEventHandler(new EventMouse.Down.Handler() {
			@Override
			public void onEvent(final EventMouse.Down event) {
				if (WindowCompartment.this.getHeldItemStack() == null) {
					return;
				}
				final CompartmentTab tab = WindowCompartment.this.getCurrentTab();
				final ItemStack stack = WindowCompartment.this.getHeldItemStack().copy();
				stack.setCount(1);
				tab.setIcon(stack);
				final NBTTagCompound nbt = new NBTTagCompound();
				tab.writeToNBT(nbt);
				WindowCompartment.this.sendClientAction("comp-change-tab", nbt);
			}
		});
		this.tabColour = new ControlColourSelector(tabPropertyPanel, 82, y2 - 4, 16, 16, EnumColor.White);
		this.tabIcon.addHelp("Icon for Current Tab");
		this.tabIcon.addHelp("Click here with an item to change");
		y2 += 20;
		new ControlText(tabPropertyPanel, new Point(4, y2), "Colour: ");
		final int cw = 8;
		final Panel panelColour = new Panel(tabPropertyPanel, 40, y2 - 4, cw * 8 + 2, cw * 2 + 1, MinecraftGUI.PanelType.Gray);
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
		final ControlButton searchButton = new ControlButton(controlCompartment, compartmentWidth - 24 - 64 - 8, compartmentPageHeight, 64, 16, "Search") {
			@Override
			@SideOnly(Side.CLIENT)
			protected void onMouseClick(final EventMouse.Down event) {
				WindowCompartment.this.createSearchDialog();
			}

			@Override
			@SideOnly(Side.CLIENT)
			public void onRenderBackground(int guiWidth, int guiHeight) {
				final Object texture = this.isMouseOver() ? CraftGUITexture.TabHighlighted : CraftGUITexture.Tab;
				CraftGUI.render.texture(CraftGUI.render.getTexture(texture).crop(Position.BOTTOM, 8), this.getArea());
			}
		};
		searchButton.addHelp("Search Button");
		searchButton.addHelp("Clicking this will open the Search dialog. This allows you to search the inventory for specific items.");
	}

	@SideOnly(Side.CLIENT)
	public void createSearchDialog() {
		new SearchDialog();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onUpdateClient() {
		super.onUpdateClient();
		this.updateTabs();
	}

	@SideOnly(Side.CLIENT)
	public void updateTabs() {
		this.tabName.setValue(this.getCurrentTab().getName());
		this.tabIcon.setItemStack(this.getCurrentTab().getIcon());
		this.tabColour.setValue(this.getCurrentTab().getColor());
	}

	@Override
	public void receiveGuiNBTOnServer(final EntityPlayer player, final String name, final NBTTagCompound nbt) {
		super.receiveGuiNBTOnServer(player, name, nbt);
		if (name.equals("tab-change")) {
			this.currentTab = nbt.getByte("i");
		}
	}

	@Override
	public String getTitle() {
		return "Compartment";
	}

	@Override
	protected AbstractMod getMod() {
		return BinnieCore.getInstance();
	}

	@Override
	protected String getBackgroundTextureName() {
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

	private class SearchDialog extends Dialog {
		Control slotGrid;
		String textSearch = "";
		boolean sortByName = false;
		boolean includeItems = true;
		boolean includeBlocks = true;

		public SearchDialog() {
			super(WindowCompartment.this, 252, 192);
		}

		@Override
		public void onClose() {
		}

		@Override
		public void initialise() {
			final ControlScrollableContent<IWidget> scroll = new ControlScrollableContent<IWidget>(this, 124, 16, 116, 92, 6) {
				@Override
				@SideOnly(Side.CLIENT)
				public void onRenderBackground(int guiWidth, int guiHeight) {
					RenderUtil.setColour(11184810);
					CraftGUI.render.texture(CraftGUITexture.Outline, this.getArea().inset(new Border(0, 6, 0, 0)));
				}
			};
			scroll.setScrollableContent(this.slotGrid = new Control(scroll, 1, 1, 108, 18));
			new ControlPlayerInventory(this, true);
			new ControlTextEdit(this, 16, 16, 100, 14).addEventHandler(new EventTextEdit.Handler() {
				@Override
				public void onEvent(final EventTextEdit event) {
					textSearch = event.value;
					updateSearch();
				}
			});
			this.includeItems = true;
			this.includeBlocks = true;
			new ControlCheckbox(this, 16, 40, 100, "Sort A-Z", this.sortByName) {
				@Override
				protected void onValueChanged(final boolean value) {
					sortByName = value;
					updateSearch();
				}
			};
			new ControlCheckbox(this, 16, 64, 100, "Include Items", this.includeItems) {
				@Override
				protected void onValueChanged(final boolean value) {
					includeItems = value;
					updateSearch();
				}
			};
			new ControlCheckbox(this, 16, 88, 100, "Include Blocks", this.includeBlocks) {
				@Override
				protected void onValueChanged(final boolean value) {
					includeBlocks = value;
					updateSearch();
				}
			};
			this.updateSearch();
		}

		private void updateSearch() {
			Map<Integer, String> slotIds = new HashMap<>();
			final IInventory inv = WindowCompartment.this.getInventory();
			for (int i = 0; i < inv.getSizeInventory(); ++i) {
				final ItemStack stack = inv.getStackInSlot(i);
				if (!stack.isEmpty()) {
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
				final List<Entry<Integer, String>> list = new LinkedList<>(slotIds.entrySet());
				list.sort((o1, o2) -> -o2.getValue().compareTo(o1.getValue()));
				final Map<Integer, String> result = new LinkedHashMap<>();
				for (final Entry<Integer, String> entry : list) {
					result.put(entry.getKey(), entry.getValue());
				}
				slotIds = result;
			}
			int y = 0;
			int x = 0;
			final int width = 108;
			final int height = 2 + 18 * (1 + (slotIds.size() - 1) / 6);
			this.slotGrid.deleteAllChildren();
			this.slotGrid.setSize(new Point(width, height));
			for (final int k : slotIds.keySet()) {
				new ControlSlot.Builder(this.slotGrid, x, y).assign(k);
				x += 18;
				if (x >= 108) {
					x = 0;
					y += 18;
				}
			}
			while (y < 108 || x != 0) {
				// TODO: what was this supposed to do?
				new ControlSlot.Builder(this.slotGrid, x, y);
				x += 18;
				if (x >= 108) {
					x = 0;
					y += 18;
				}
			}
		}
	}
}
