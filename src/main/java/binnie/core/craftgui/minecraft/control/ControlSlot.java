// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.minecraft.control;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.minecraft.CustomSlot;
import binnie.core.craftgui.minecraft.InventoryType;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.WindowInventory;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.inventory.MachineSide;
import binnie.core.machines.inventory.SlotValidator;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControlSlot extends ControlSlotBase
{
	public static Map<EnumHighlighting, List<Integer>> highlighting;
	public static boolean shiftClickActive;
	public Slot slot;

	public ControlSlot(final IWidget parent, final float x, final float y) {
		super(parent, x, y);
		slot = null;
		addSelfEventHandler(new EventMouse.Down.Handler() {
			@Override
			public void onEvent(final EventMouse.Down event) {
				if (slot != null) {
					final PlayerControllerMP playerController = ((Window) getSuperParent()).getGui().getMinecraft().playerController;
					final int windowId = ((Window) getSuperParent()).getContainer().windowId;
					final int slotNumber = slot.slotNumber;
					final int button = event.getButton();
					Window.get(getWidget()).getGui();
					playerController.windowClick(windowId, slotNumber, button, GuiScreen.isShiftKeyDown() ? 1 : 0, ((Window) getSuperParent()).getGui().getMinecraft().thePlayer);
				}
			}
		});
	}

	public ControlSlot(final IWidget parent, final int x, final int y, final Slot slot) {
		super(parent, x, y);
		this.slot = null;
		this.slot = slot;
	}

	@Override
	public void onRenderBackground() {
		CraftGUI.Render.texture(CraftGUITexture.Slot, IPoint.ZERO);
		if (slot == null) {
			return;
		}
		final InventorySlot islot = getInventorySlot();
		if (islot != null && islot.getValidator() != null) {
			final IIcon icon = islot.getValidator().getIcon(!islot.getInputSides().isEmpty());
			if (icon != null) {
				CraftGUI.Render.iconItem(new IPoint(1.0f, 1.0f), icon);
			}
		}
		boolean highlighted = false;
		for (final Map.Entry<EnumHighlighting, List<Integer>> highlight : ControlSlot.highlighting.entrySet()) {
			if (highlight.getKey() == EnumHighlighting.ShiftClick && !ControlSlot.shiftClickActive) {
				continue;
			}
			if (highlighted || !highlight.getValue().contains(slot.slotNumber)) {
				continue;
			}
			highlighted = true;
			final int c = -1442840576 + Math.min(highlight.getKey().getColour(), 16777215);
			CraftGUI.Render.gradientRect(new IArea(1.0f, 1.0f, 16.0f, 16.0f), c, c);
		}
		if (!highlighted && getSuperParent().getMousedOverWidget() == this) {
			if (Window.get(this).getGui().getDraggedItem() != null && !slot.isItemValid(Window.get(this).getGui().getDraggedItem())) {
				CraftGUI.Render.gradientRect(new IArea(1.0f, 1.0f, 16.0f, 16.0f), -1426089575, -1426089575);
			}
			else {
				CraftGUI.Render.gradientRect(new IArea(1.0f, 1.0f, 16.0f, 16.0f), -2130706433, -2130706433);
			}
		}
	}

	@Override
	public void onRenderOverlay() {
		if (slot == null) {
			return;
		}
		boolean highlighted = false;
		for (final Map.Entry<EnumHighlighting, List<Integer>> highlight : ControlSlot.highlighting.entrySet()) {
			if (highlight.getKey() == EnumHighlighting.ShiftClick && !ControlSlot.shiftClickActive) {
				continue;
			}
			if (highlighted || !highlight.getValue().contains(slot.slotNumber)) {
				continue;
			}
			highlighted = true;
			final int c = highlight.getKey().getColour();
			IArea area = getArea();
			if (getParent() instanceof ControlSlotArray || getParent() instanceof ControlPlayerInventory) {
				area = getParent().getArea();
				area.setPosition(IPoint.ZERO.sub(getPosition()));
			}
			CraftGUI.Render.colour(c);
			CraftGUI.Render.texture(CraftGUITexture.Outline, area.outset(1));
		}
	}

	@Override
	public void onUpdateClient() {
		super.onUpdateClient();
		if (slot == null) {
			return;
		}
		if (isMouseOver() && GuiScreen.isShiftKeyDown()) {
			Window.get(this).getContainer().setMouseOverSlot(slot);
			ControlSlot.shiftClickActive = true;
		}
		if (Window.get(this).getGui().isHelpMode() && isMouseOver()) {
			for (final ControlSlot slot2 : getControlSlots()) {
				if (slot2.slot != null) {
					ControlSlot.highlighting.get(EnumHighlighting.Help).add(slot2.slot.slotNumber);
				}
			}
		}
	}

	private List<ControlSlot> getControlSlots() {
		final List<ControlSlot> slots = new ArrayList<ControlSlot>();
		if (getParent() instanceof ControlSlotArray || getParent() instanceof ControlPlayerInventory) {
			for (final IWidget child : getParent().getWidgets()) {
				slots.add((ControlSlot) child);
			}
		}
		else {
			slots.add(this);
		}
		return slots;
	}

	@Override
	public ItemStack getItemStack() {
		if (slot != null) {
			return slot.getStack();
		}
		return null;
	}

	public ControlSlot assign(final int index) {
		return assign(InventoryType.Machine, index);
	}

	public ControlSlot assign(final InventoryType inventory, final int index) {
		if (slot != null) {
			return this;
		}
		slot = ((Window) getSuperParent()).getContainer().getOrCreateSlot(inventory, index);
		return this;
	}

	@Override
	public void getHelpTooltip(final Tooltip tooltip) {
		if (slot == null) {
			return;
		}
		final InventorySlot slot = getInventorySlot();
		if (getInventorySlot() != null) {
			tooltip.add(slot.getName());
			tooltip.add("Insert Side: " + MachineSide.asString(slot.getInputSides()));
			tooltip.add("Extract Side: " + MachineSide.asString(slot.getOutputSides()));
			if (slot.isReadOnly()) {
				tooltip.add("Pickup Only Slot");
			}
			tooltip.add("Accepts: " + ((slot.getValidator() == null) ? "Any Item" : slot.getValidator().getTooltip()));
		}
		else if (this.slot.inventory instanceof WindowInventory) {
			final SlotValidator s = ((WindowInventory) this.slot.inventory).getValidator(this.slot.getSlotIndex());
			tooltip.add("Accepts: " + ((s == null) ? "Any Item" : s.getTooltip()));
		}
		else if (this.slot.inventory instanceof InventoryPlayer) {
			tooltip.add("Player Inventory");
		}
	}

	public InventorySlot getInventorySlot() {
		return (slot instanceof CustomSlot) ? ((CustomSlot) slot).getInventorySlot() : null;
	}

	static {
		ControlSlot.highlighting = new HashMap<EnumHighlighting, List<Integer>>();
		ControlSlot.shiftClickActive = false;
		for (final EnumHighlighting h : EnumHighlighting.values()) {
			ControlSlot.highlighting.put(h, new ArrayList<Integer>());
		}
	}
}
