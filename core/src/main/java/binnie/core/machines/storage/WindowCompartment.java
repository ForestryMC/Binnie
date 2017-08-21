package binnie.core.machines.storage;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import binnie.core.api.gui.ITexture;
import binnie.core.api.gui.events.EventHandlerOrigin;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.window.WindowMachine;
import binnie.core.machines.IMachine;
import binnie.core.machines.MachinePackage;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.BinnieCore;
import binnie.core.gui.Attribute;
import binnie.core.gui.CraftGUI;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.ControlCheckbox;
import binnie.core.gui.controls.ControlText;
import binnie.core.gui.controls.ControlTextEdit;
import binnie.core.gui.controls.button.ControlButton;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.controls.page.ControlPage;
import binnie.core.gui.controls.page.ControlPages;
import binnie.core.gui.controls.scroll.ControlScrollableContent;
import binnie.core.gui.controls.tab.ControlTab;
import binnie.core.gui.controls.tab.ControlTabBar;
import binnie.core.gui.events.EventMouse;
import binnie.core.gui.events.EventTextEdit;
import binnie.core.gui.events.EventValueChanged;
import binnie.core.gui.geometry.Border;
import binnie.core.gui.geometry.CraftGUIUtil;
import binnie.core.api.gui.Alignment;
import binnie.core.gui.minecraft.Dialog;
import binnie.core.gui.minecraft.EnumColor;
import binnie.core.gui.minecraft.IWindowAffectsShiftClick;
import binnie.core.gui.minecraft.MinecraftGUI;
import binnie.core.gui.minecraft.Window;
import binnie.core.gui.minecraft.control.ControlItemDisplay;
import binnie.core.gui.minecraft.control.ControlPlayerInventory;
import binnie.core.gui.minecraft.control.ControlSlide;
import binnie.core.gui.minecraft.control.ControlSlot;
import binnie.core.gui.minecraft.control.ControlSlotArray;
import binnie.core.gui.minecraft.control.ControlTabIcon;
import binnie.core.gui.renderer.RenderUtil;
import binnie.core.gui.resource.textures.CraftGUITexture;
import binnie.core.gui.window.Panel;
import binnie.core.machines.Machine;
import binnie.core.machines.transfer.TransferRequest;

public class WindowCompartment extends WindowMachine implements IWindowAffectsShiftClick {
	private final Map<Panel, Integer> panels;
	private ControlTextEdit tabName;
	private ControlItemDisplay tabIcon;
	private ControlColourSelector tabColour;
	private int currentTab;

	public WindowCompartment(final EntityPlayer player, @Nullable final IInventory inventory, final Side side) {
		super(320, 226, player, inventory, side);
		this.panels = new HashMap<>();
		this.currentTab = 0;
	}

	//TODO: Clean Up, Localise
	@Override
	@SideOnly(Side.CLIENT)
	public void initialiseClient() {
		IInventory inventory = this.getInventory();
		IMachine machine = Machine.getMachine(inventory);
		MachinePackage machinePackage = machine.getPackage();
		this.setTitle(machinePackage.getDisplayName());
		int x = 16;
		final int y = 32;
		final ComponentCompartmentInventory inv = machine.getInterface(ComponentCompartmentInventory.class);
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
		final ControlTabBar<Integer> tab = new CompartmentTabBar1(this, controlCompartment, compartmentPageHeight, tabs1);
		final String[] tabHelp = {"Compartment Tab", "Tabs that divide the inventory into sections. Each one can be labelled seperately."};
		tab.addHelp(tabHelp);
		tab.addEventHandler(EventValueChanged.class, EventHandlerOrigin.DIRECT_CHILD, tab, event -> {
			if (event.getValue() == null) {
				return;
			}
			final NBTTagCompound nbt = new NBTTagCompound();
			final int i = (Integer) event.getValue();
			nbt.setByte("i", (byte) i);
			Window.get(tab).sendClientAction("tab-change", nbt);
			WindowCompartment.this.currentTab = i;
		});
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
			final Panel panel = new CompartmentPanel(this, thisPage);
			this.panels.put(panel, p2);
			final int[] slotsIDs = new int[inv.getPageSize()];
			for (int k = 0; k < inv.getPageSize(); ++k) {
				slotsIDs[k] = i++;
			}
			new ControlSlotArray.Builder(thisPage, 8, 8, inv.getPageSize() / 5, 5).create(slotsIDs);
		}
		x += compartmentPageWidth;
		if (tabs2.length > 0) {
			final ControlTabBar<Integer> tab2 = new CompartmentTabBar2(this, controlCompartment, compartmentPageWidth, compartmentPageHeight, tabs2);
			tab2.addHelp(tabHelp);
			tab2.addEventHandler(EventValueChanged.class, EventHandlerOrigin.DIRECT_CHILD, tab2, event -> {
				if (event.getValue() == null) {
					return;
				}
				final NBTTagCompound nbt = new NBTTagCompound();
				final int iVal = (Integer) event.getValue();
				nbt.setByte("i", (byte) iVal);
				Window.get(tab).sendClientAction("tab-change", nbt);
				WindowCompartment.this.currentTab = iVal;
			});
			CraftGUIUtil.linkWidgets(tab2, compartmentPages);
			x += 24;
		}
		x += 16;
		this.setSize(new Point(Math.max(32 + compartmentWidth, 252), this.getHeight()));
		controlCompartment.setPosition(new Point((this.getWidth() - controlCompartment.getWidth()) / 2, controlCompartment.getYPos()));
		final ControlPlayerInventory invent = new ControlPlayerInventory(this, true);
		final ControlSlide slide = new ControlSlide(this, 0, 134, 136, 92, Alignment.LEFT);
		slide.setLabel("Tab Properties");
		slide.setSlide(false);
		slide.addHelp("Tab Properties");
		slide.addHelp("The label, colour and icon of the Tab can be altered here. Clicking on the icon with a held item will change it.");
		final Panel tabPropertyPanel = new Panel(slide, 16, 8, 112, 76, MinecraftGUI.PanelType.GRAY);
		int y2 = 4;
		new ControlText(tabPropertyPanel, new Point(4, y2), "Tab Name:");
		final Panel parent = tabPropertyPanel;
		final int x2 = 4;
		y2 += 12;
		(this.tabName = new ControlTextEdit(parent, x2, y2, 104, 12)).addEventHandler(EventTextEdit.class, EventHandlerOrigin.SELF, this.tabName, event -> {
			final CompartmentTab currentTab = WindowCompartment.this.getCurrentTab();
			currentTab.setName(event.getValue());
			final NBTTagCompound nbt = new NBTTagCompound();
			currentTab.writeToNBT(nbt);
			WindowCompartment.this.sendClientAction("comp-change-tab", nbt);
		});
		y2 += 20;
		new ControlText(tabPropertyPanel, new Point(4, y2), "Tab Icon: ");
		(this.tabIcon = new ControlItemDisplay(tabPropertyPanel, 58, y2 - 4)).setItemStack(new ItemStack(Items.PAPER));
		this.tabIcon.addAttribute(Attribute.MOUSE_OVER);
		this.tabIcon.addSelfEventHandler(EventMouse.Down.class, event -> {
			if (WindowCompartment.this.getHeldItemStack().isEmpty()) {
				return;
			}
			final CompartmentTab currentTab = WindowCompartment.this.getCurrentTab();
			final ItemStack stack = WindowCompartment.this.getHeldItemStack().copy();
			stack.setCount(1);
			currentTab.setIcon(stack);
			final NBTTagCompound nbt = new NBTTagCompound();
			currentTab.writeToNBT(nbt);
			WindowCompartment.this.sendClientAction("comp-change-tab", nbt);
		});
		this.tabColour = new ControlColourSelector(tabPropertyPanel, 82, y2 - 4, 16, 16, EnumColor.WHITE);
		this.tabIcon.addHelp("Icon for Current Tab");
		this.tabIcon.addHelp("Click here with an item to change");
		y2 += 20;
		new ControlText(tabPropertyPanel, new Point(4, y2), "Colour: ");
		final int cw = 8;
		final Panel panelColour = new Panel(tabPropertyPanel, 40, y2 - 4, cw * 8 + 2, cw * 2 + 1, MinecraftGUI.PanelType.GRAY);
		for (int cc = 0; cc < 16; ++cc) {
			final ControlColourSelector color = new ControlColourSelector(panelColour, 1 + cw * (cc % 8), 1 + cw * (cc / 8), cw, cw, EnumColor.values()[cc]);
			color.addSelfEventHandler(EventMouse.Down.class, event -> {
				final CompartmentTab currentTab = WindowCompartment.this.getCurrentTab();
				currentTab.setColor(color.getValue());
				final NBTTagCompound nbt = new NBTTagCompound();
				currentTab.writeToNBT(nbt);
				WindowCompartment.this.sendClientAction("comp-change-tab", nbt);
			});
			color.addHelp("Colour Selector");
			color.addHelp("Select a colour to highlight the current tab");
		}
		y2 += 20;
		final ControlButton searchButton = new SearchButton(this, controlCompartment, compartmentWidth, compartmentPageHeight);
		searchButton.addHelp("Search Button");
		searchButton.addHelp("Clicking this will open the Search dialog. This allows you to search the inventory for specific items.");
	}

	@SideOnly(Side.CLIENT)
	public void createSearchDialog() {
		new SearchDialog(this);
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
	protected String getModId() {
		return BinnieCore.getInstance().getModId();
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

	private static class SearchDialog extends Dialog {
		Control slotGrid;
		String textSearch = "";
		boolean sortByName = false;
		boolean includeItems = true;
		boolean includeBlocks = true;
		private WindowCompartment windowCompartment;

		public SearchDialog(WindowCompartment windowCompartment) {
			super(windowCompartment, 252, 192);
			this.windowCompartment = windowCompartment;
		}

		@Override
		public void onClose() {
		}

		@Override
		public void initialise() {
			final ControlScrollableContent<IWidget> scroll = new SearchScrollContent(this);
			scroll.setScrollableContent(this.slotGrid = new Control(scroll, 1, 1, 108, 18));
			new ControlPlayerInventory(this, true);
			new ControlTextEdit(this, 16, 16, 100, 14).addEventHandler(EventTextEdit.class, event -> {
				textSearch = event.getValue();
				updateSearch();
			});
			this.includeItems = true;
			this.includeBlocks = true;
			new SortAlphabeticalCheckbox(this);
			new IncludeItemsCheckbox(this);
			new IncludeBlocksCheckbox(this);
			this.updateSearch();
		}

		private void updateSearch() {
			Map<Integer, String> slotIds = new HashMap<>();
			final IInventory inv = windowCompartment.getInventory();
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

		private static class SearchScrollContent extends ControlScrollableContent<IWidget> {
			private SearchDialog searchDialog;

			public SearchScrollContent(SearchDialog searchDialog) {
				super(searchDialog, 124, 16, 116, 92, 6);
				this.searchDialog = searchDialog;
			}

			@Override
			@SideOnly(Side.CLIENT)
			public void onRenderBackground(int guiWidth, int guiHeight) {
				RenderUtil.setColour(11184810);
				CraftGUI.RENDER.texture(CraftGUITexture.OUTLINE, searchDialog.windowCompartment.getArea().inset(new Border(0, 6, 0, 0)));
			}
		}

		private static class SortAlphabeticalCheckbox extends ControlCheckbox {
			private SearchDialog searchDialog;

			public SortAlphabeticalCheckbox(SearchDialog searchDialog) {
				super(searchDialog, 16, 40, 100, "Sort A-Z", searchDialog.sortByName);
				this.searchDialog = searchDialog;
			}

			@Override
			protected void onValueChanged(final boolean value) {
				searchDialog.sortByName = value;
				searchDialog.updateSearch();
			}
		}

		private static class IncludeItemsCheckbox extends ControlCheckbox {
			private SearchDialog searchDialog;

			public IncludeItemsCheckbox(SearchDialog searchDialog) {
				super(searchDialog, 16, 64, 100, "Include Items", searchDialog.includeItems);
				this.searchDialog = searchDialog;
			}

			@Override
			protected void onValueChanged(final boolean value) {
				searchDialog.includeItems = value;
				searchDialog.updateSearch();
			}
		}

		private static class IncludeBlocksCheckbox extends ControlCheckbox {
			private SearchDialog searchDialog;

			public IncludeBlocksCheckbox(SearchDialog searchDialog) {
				super(searchDialog, 16, 88, 100, "Include Blocks", searchDialog.includeBlocks);
				this.searchDialog = searchDialog;
			}

			@Override
			protected void onValueChanged(final boolean value) {
				searchDialog.includeBlocks = value;
				searchDialog.updateSearch();
			}
		}
	}

	private static class CompartmentTabBar1 extends ControlTabBar<Integer> {
		private WindowCompartment windowCompartment;

		public CompartmentTabBar1(final WindowCompartment windowCompartment, Control controlCompartment, int compartmentPageHeight, Integer[] tabs1) {
			super(controlCompartment, 0, 0, 24, compartmentPageHeight, Alignment.LEFT, Arrays.asList(tabs1));
			this.windowCompartment = windowCompartment;
		}

		@Override
		public ControlTab<Integer> createTab(final int x, final int y, final int w, final int h, final Integer value) {
			return new CompartmentTabIcon(this, this.windowCompartment, x, y, w, h, value);
		}
	}

	private static class CompartmentTabBar2 extends ControlTabBar<Integer> {
		private WindowCompartment windowCompartment;

		public CompartmentTabBar2(WindowCompartment windowCompartment, Control controlCompartment, int compartmentPageWidth, int compartmentPageHeight, Integer[] tabs2) {
			super(controlCompartment, 24 + compartmentPageWidth, 0, 24, compartmentPageHeight, Alignment.RIGHT, Arrays.asList(tabs2));
			this.windowCompartment = windowCompartment;
		}

		@Override
		public ControlTab<Integer> createTab(final int x, final int y, final int w, final int h, final Integer value) {
			return new CompartmentTabIcon(this, this.windowCompartment, x, y, w, h, value);
		}
	}

	private static class CompartmentTabIcon extends ControlTabIcon<Integer> {
		private WindowCompartment windowCompartment;

		public CompartmentTabIcon(ControlTabBar<Integer> compartmentTabBar, WindowCompartment windowCompartment, int x, int y, int w, int h, Integer value) {
			super(compartmentTabBar, x, y, w, h, value);
			this.windowCompartment = windowCompartment;
		}

		@Override
		public ItemStack getItemStack() {
			return windowCompartment.getTab(this.value).getIcon();
		}

		@Override
		public String getName() {
			return windowCompartment.getTab(this.value).getName();
		}

		@Override
		public int getOutlineColour() {
			return windowCompartment.getTab(this.value).getColor().getColor();
		}

		@Override
		public boolean hasOutline() {
			return true;
		}
	}

	private static class CompartmentPanel extends Panel {
		private WindowCompartment windowCompartment;

		public CompartmentPanel(WindowCompartment windowCompartment, ControlPage thisPage) {
			super(thisPage, 0, 0, thisPage.getWidth(), thisPage.getHeight(), MinecraftGUI.PanelType.BLACK);
			this.windowCompartment = windowCompartment;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void onRenderForeground(int guiWidth, int guiHeight) {
			final ITexture iTexture = CraftGUI.RENDER.getTexture(CraftGUITexture.TAB_OUTLINE);
			CompartmentTab tab = windowCompartment.getTab(windowCompartment.panels.get(this));
			RenderUtil.setColour(tab.getColor().getColor());
			CraftGUI.RENDER.texture(iTexture, this.getArea().inset(3));
		}
	}

	private static class SearchButton extends ControlButton {
		private WindowCompartment windowCompartment;

		public SearchButton(WindowCompartment windowCompartment, Control controlCompartment, int compartmentWidth, int compartmentPageHeight) {
			super(controlCompartment, compartmentWidth - 24 - 64 - 8, compartmentPageHeight, 64, 16, "Search");
			this.windowCompartment = windowCompartment;
		}

		@Override
		@SideOnly(Side.CLIENT)
		protected void onMouseClick(final EventMouse.Down event) {
			windowCompartment.createSearchDialog();
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void onRenderBackground(int guiWidth, int guiHeight) {
			final Object texture = this.isMouseOver() ? CraftGUITexture.TAB_HIGHLIGHTED : CraftGUITexture.TAB;
			CraftGUI.RENDER.texture(CraftGUI.RENDER.getTexture(texture).crop(Alignment.BOTTOM, 8), this.getArea());
		}
	}
}
