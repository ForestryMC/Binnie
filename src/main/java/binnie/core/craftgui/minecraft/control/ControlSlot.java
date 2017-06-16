package binnie.core.craftgui.minecraft.control;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.minecraft.CustomSlot;
import binnie.core.craftgui.minecraft.GuiCraftGUI;
import binnie.core.craftgui.minecraft.InventoryType;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.WindowInventory;
import binnie.core.craftgui.renderer.RenderUtil;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.inventory.MachineSide;
import binnie.core.machines.inventory.SlotValidator;

public class ControlSlot extends ControlSlotBase {
	public static final Map<EnumHighlighting, List<Integer>> highlighting = new EnumMap<>(EnumHighlighting.class);
	public static boolean shiftClickActive = false;

	static {
		for (final EnumHighlighting h : EnumHighlighting.values()) {
			ControlSlot.highlighting.put(h, new ArrayList<>());
		}
	}

	public final Slot slot;

	public ControlSlot(final IWidget parent, final int x, final int y, final Slot slot) {
		super(parent, x, y);
		this.slot = slot;
		this.addSelfEventHandler(new EventMouse.Down.Handler() {
			@Override
			@SideOnly(Side.CLIENT)
			public void onEvent(final EventMouse.Down event) {
				Window superParent = (Window) ControlSlot.this.getTopParent();
				GuiCraftGUI superParentGui = superParent.getGui();
				Minecraft minecraft = superParentGui.getMinecraft();
				final PlayerControllerMP playerController = minecraft.playerController;
				final int windowId = superParent.getContainer().windowId;
				final int slotNumber = ControlSlot.this.slot.slotNumber;
				final int button = event.getButton();
				Window.get(ControlSlot.this.getWidget()).getGui();
				if (playerController != null) {
					playerController.windowClick(windowId, slotNumber, button, GuiScreen.isShiftKeyDown() ? ClickType.QUICK_MOVE : ClickType.PICKUP, minecraft.player);
				}
			}
		});
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		CraftGUI.render.texture(CraftGUITexture.Slot, Point.ZERO);
		final InventorySlot islot = this.getInventorySlot();
		if (islot != null) {
			SlotValidator validator = islot.getValidator();
			if (validator != null) {
				final TextureAtlasSprite icon = validator.getIcon(!islot.getInputSides().isEmpty());
				if (icon != null && icon != Minecraft.getMinecraft().getTextureMapBlocks().getMissingSprite()) {
					GlStateManager.enableBlend();
					RenderUtil.drawSprite(new Point(1, 1), icon);
					GlStateManager.disableBlend();
				}
			}
		}
		boolean highlighted = false;
		for (final Map.Entry<EnumHighlighting, List<Integer>> highlight : ControlSlot.highlighting.entrySet()) {
			if (highlight.getKey() == EnumHighlighting.ShiftClick && !ControlSlot.shiftClickActive) {
				continue;
			}
			if (highlighted || !highlight.getValue().contains(this.slot.slotNumber)) {
				continue;
			}
			highlighted = true;
			final int c = -1442840576 + Math.min(highlight.getKey().getColour(), 16777215);
			RenderUtil.drawGradientRect(new Area(1, 1, 16, 16), c, c);
		}
		if (!highlighted && this.getTopParent().getMousedOverWidget() == this) {
			GuiCraftGUI gui = Window.get(this).getGui();
			if (!gui.getDraggedItem().isEmpty() && !this.slot.isItemValid(gui.getDraggedItem())) {
				RenderUtil.drawGradientRect(new Area(1, 1, 16, 16), -1426089575, -1426089575);
			} else {
				RenderUtil.drawGradientRect(new Area(1, 1, 16, 16), -2130706433, -2130706433);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderOverlay() {
		boolean highlighted = false;
		for (final Map.Entry<EnumHighlighting, List<Integer>> highlight : ControlSlot.highlighting.entrySet()) {
			if (highlight.getKey() == EnumHighlighting.ShiftClick && !ControlSlot.shiftClickActive) {
				continue;
			}
			if (highlighted || !highlight.getValue().contains(this.slot.slotNumber)) {
				continue;
			}
			highlighted = true;
			final int c = highlight.getKey().getColour();
			Area area = this.getArea();
			if (this.getParent() instanceof ControlSlotArray || this.getParent() instanceof ControlPlayerInventory) {
				area = this.getParent().getArea();
				area.setPosition(Point.ZERO.sub(this.getPosition()));
			}
			RenderUtil.setColour(c);
			CraftGUI.render.texture(CraftGUITexture.Outline, area.outset(1));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onUpdateClient() {
		super.onUpdateClient();
		if (this.isMouseOver() && GuiScreen.isShiftKeyDown()) {
			Window.get(this).getContainer().setMouseOverSlot(this.slot);
			ControlSlot.shiftClickActive = true;
		}
		if (Window.get(this).getGui().isHelpMode() && this.isMouseOver()) {
			for (final ControlSlot slot2 : this.getControlSlots()) {
				ControlSlot.highlighting.get(EnumHighlighting.Help).add(slot2.slot.slotNumber);
			}
		}
	}

	private List<ControlSlot> getControlSlots() {
		final List<ControlSlot> slots = new ArrayList<>();
		if (this.getParent() instanceof ControlSlotArray || this.getParent() instanceof ControlPlayerInventory) {
			for (final IWidget child : this.getParent().getWidgets()) {
				slots.add((ControlSlot) child);
			}
		} else {
			slots.add(this);
		}
		return slots;
	}

	@Override
	public ItemStack getItemStack() {
		return this.slot.getStack();
	}

	@Override
	public void getHelpTooltip(final Tooltip tooltip) {
		final InventorySlot slot = this.getInventorySlot();
		if (slot != null) {
			tooltip.add(slot.getName());
			tooltip.add("Insert Side: " + MachineSide.asString(slot.getInputSides()));
			tooltip.add("Extract Side: " + MachineSide.asString(slot.getOutputSides()));
			if (slot.isReadOnly()) {
				tooltip.add("Pickup Only Slot");
			}
			tooltip.add("Accepts: " + ((slot.getValidator() == null) ? "Any Item" : slot.getValidator().getTooltip()));
		} else if (this.slot.inventory instanceof WindowInventory) {
			final SlotValidator s = ((WindowInventory) this.slot.inventory).getValidator(this.slot.getSlotIndex());
			tooltip.add("Accepts: " + ((s == null) ? "Any Item" : s.getTooltip()));
		} else if (this.slot.inventory instanceof InventoryPlayer) {
			tooltip.add("Player Inventory");
		}
	}

	@Nullable
	public InventorySlot getInventorySlot() {
		if (this.slot instanceof CustomSlot) {
			CustomSlot customSlot = (CustomSlot) this.slot;
			return customSlot.getInventorySlot();
		} else {
			return null;
		}
	}

	public static class Builder {
		private final IWidget parent;
		private final int x;
		private final int y;

		public Builder(final IWidget parent, final int x, final int y) {
			this.parent = parent;
			this.x = x;
			this.y = y;
		}

		public ControlSlot assign(final int index) {
			return assign(InventoryType.Machine, index);
		}

		public ControlSlot assign(final InventoryType inventory, final int index) {
			Slot slot = ((Window) parent.getTopParent()).getContainer().getOrCreateSlot(inventory, index);
			return new ControlSlot(parent, x, y, slot);
		}
	}
}
