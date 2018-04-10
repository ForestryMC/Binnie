package binnie.core.machines.storage;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import binnie.core.api.gui.IWidget;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.controls.ControlCheckbox;
import binnie.core.gui.controls.ControlTextEdit;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.controls.scroll.ControlScrollableContent;
import binnie.core.gui.events.EventTextEdit;
import binnie.core.gui.geometry.Border;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.minecraft.Dialog;
import binnie.core.gui.minecraft.control.ControlPlayerInventory;
import binnie.core.gui.minecraft.control.ControlSlot;
import binnie.core.gui.renderer.RenderUtil;
import binnie.core.gui.resource.textures.CraftGUITexture;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.StringUtils;

@SideOnly(Side.CLIENT)
public class SearchDialog extends Dialog {
	private final Control slotGrid;
	private final WindowCompartment windowCompartment;
	private String textSearch = StringUtils.EMPTY;
	private boolean sortByName;
	private boolean includeItems;
	private boolean includeBlocks;

	public SearchDialog(WindowCompartment windowCompartment) {
		super(windowCompartment, 252, 192);
		this.windowCompartment = windowCompartment;

		final ControlScrollableContent<IWidget> scroll = new SearchScrollContent(this);
		this.slotGrid = new Control(scroll, 1, 1, 108, 18);
		scroll.setScrollableContent(this.slotGrid);
		new ControlPlayerInventory(this, true);
		new ControlTextEdit(this, 16, 16, 100, 14).addEventHandler(EventTextEdit.class, event -> {
			final String value = event.getValue();
			textSearch = (value == null) ? StringUtils.EMPTY : value;
			updateSearch();
		});
		this.includeItems = true;
		this.includeBlocks = true;
		new SortAlphabeticalCheckbox(this);
		new IncludeItemsCheckbox(this);
		new IncludeBlocksCheckbox(this);
		this.updateSearch();
	}

	private Map<Integer, String> filter(final Map<Integer, String> slotIds) {
		final IInventory inv = windowCompartment.getInventory();
		for (int i = 0; i < inv.getSizeInventory(); ++i) {
			final ItemStack stack = inv.getStackInSlot(i);
			if (!stack.isEmpty()) {
				final String name = stack.getDisplayName().toLowerCase();
				if (this.textSearch.length() == 0 || name.contains(this.textSearch)) {
					if (this.includeBlocks || Block.getBlockFromItem(stack.getItem()) == Blocks.AIR) {
						if (this.includeItems || Block.getBlockFromItem(stack.getItem()) != Blocks.AIR) {
							slotIds.put(i, name);
						}
					}
				}
			}
		}
		return slotIds;
	}

	private Map<Integer, String> sort(final Map<Integer, String> slotIds) {
		if (this.sortByName) {
			final List<Map.Entry<Integer, String>> list = new LinkedList<>(slotIds.entrySet());
			list.sort((o1, o2) -> -o2.getValue().compareTo(o1.getValue()));
			slotIds.clear();
			for (final Map.Entry<Integer, String> entry : list) {
				slotIds.put(entry.getKey(), entry.getValue());
			}
		}
		return slotIds;
	}

	private void updateSearch() {
		final Map<Integer, String> slotIds = new HashMap<>();
		final Map<Integer, String> filtered = filter(slotIds);
		final Map<Integer, String> sorted = sort(filtered);
		int y = 0;
		int x = 0;
		final int width = 108;
		final int height = 2 + 18 * (1 + (sorted.size() - 1) / 6);
		this.slotGrid.deleteAllChildren();
		this.slotGrid.setSize(new Point(width, height));
		for (final int k : sorted.keySet()) {
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
		private final SearchDialog searchDialog;

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
		private final SearchDialog searchDialog;

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
		private final SearchDialog searchDialog;

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
		private final SearchDialog searchDialog;

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
