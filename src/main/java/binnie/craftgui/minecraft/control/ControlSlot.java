package binnie.craftgui.minecraft.control;

import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.inventory.MachineSide;
import binnie.core.machines.inventory.SlotValidator;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.Tooltip;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.events.EventMouse;
import binnie.craftgui.minecraft.CustomSlot;
import binnie.craftgui.minecraft.InventoryType;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.minecraft.WindowInventory;
import binnie.craftgui.resource.minecraft.CraftGUITexture;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControlSlot extends ControlSlotBase {
    public static Map<EnumHighlighting, List<Integer>> highlighting = new HashMap<>();
    public static boolean shiftClickActive = false;
    public Slot slot;

    public ControlSlot(final IWidget parent, final float x, final float y) {
        super(parent, x, y);
        this.slot = null;
        this.addSelfEventHandler(new EventMouse.Down.Handler() {
            @Override
            public void onEvent(final EventMouse.Down event) {
                if (ControlSlot.this.slot != null) {
                    final PlayerControllerMP playerController = ((Window) ControlSlot.this.getSuperParent()).getGui().getMinecraft().playerController;
                    final int windowId = ((Window) ControlSlot.this.getSuperParent()).getContainer().windowId;
                    final int slotNumber = ControlSlot.this.slot.slotNumber;
                    final int button = event.getButton();
                    Window.get(ControlSlot.this.getWidget()).getGui();
                    playerController.windowClick(windowId, slotNumber, button, GuiScreen.isShiftKeyDown() ? ClickType.QUICK_MOVE : ClickType.PICKUP, ((Window) ControlSlot.this.getSuperParent()).getGui().getMinecraft().thePlayer);
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
        if (this.slot == null) {
            return;
        }
        final InventorySlot islot = this.getInventorySlot();
        //TODO RENDERING
        if (islot != null && islot.getValidator() != null) {
            final TextureAtlasSprite icon = islot.getValidator().getIcon(!islot.getInputSides().isEmpty());
            if (icon != null) {
                CraftGUI.Render.iconItem(new IPoint(1.0f, 1.0f), icon);
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
            CraftGUI.Render.gradientRect(new IArea(1.0f, 1.0f, 16.0f, 16.0f), c, c);
        }
        if (!highlighted && this.getSuperParent().getMousedOverWidget() == this) {
            if (Window.get(this).getGui().getDraggedItem() != null && !this.slot.isItemValid(Window.get(this).getGui().getDraggedItem())) {
                CraftGUI.Render.gradientRect(new IArea(1.0f, 1.0f, 16.0f, 16.0f), -1426089575, -1426089575);
            } else {
                CraftGUI.Render.gradientRect(new IArea(1.0f, 1.0f, 16.0f, 16.0f), -2130706433, -2130706433);
            }
        }
    }

    @Override
    public void onRenderOverlay() {
        if (this.slot == null) {
            return;
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
            final int c = highlight.getKey().getColour();
            IArea area = this.getArea();
            if (this.getParent() instanceof ControlSlotArray || this.getParent() instanceof ControlPlayerInventory) {
                area = this.getParent().getArea();
                area.setPosition(IPoint.ZERO.sub(this.getPosition()));
            }
            CraftGUI.Render.colour(c);
            CraftGUI.Render.texture(CraftGUITexture.Outline, area.outset(1));
        }
    }

    @Override
    public void onUpdateClient() {
        super.onUpdateClient();
        if (this.slot == null) {
            return;
        }
        if (this.isMouseOver() && GuiScreen.isShiftKeyDown()) {
            Window.get(this).getContainer().setMouseOverSlot(this.slot);
            ControlSlot.shiftClickActive = true;
        }
        if (Window.get(this).getGui().isHelpMode() && this.isMouseOver()) {
            for (final ControlSlot slot2 : this.getControlSlots()) {
                if (slot2.slot != null) {
                    ControlSlot.highlighting.get(EnumHighlighting.Help).add(slot2.slot.slotNumber);
                }
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
        if (this.slot != null) {
            return this.slot.getStack();
        }
        return null;
    }

    public ControlSlot assign(final int index) {
        return this.assign(InventoryType.Machine, index);
    }

    public ControlSlot assign(final InventoryType inventory, final int index) {
        if (this.slot != null) {
            return this;
        }
        this.slot = ((Window) this.getSuperParent()).getContainer().getOrCreateSlot(inventory, index);
        return this;
    }

    @Override
    public void getHelpTooltip(final Tooltip tooltip) {
        if (this.slot == null) {
            return;
        }
        final InventorySlot slot = this.getInventorySlot();
        if (this.getInventorySlot() != null) {
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

    public InventorySlot getInventorySlot() {
        return (this.slot instanceof CustomSlot) ? ((CustomSlot) this.slot).getInventorySlot() : null;
    }

    static {
        for (final EnumHighlighting h : EnumHighlighting.values()) {
            ControlSlot.highlighting.put(h, new ArrayList<>());
        }
    }
}
