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
import binnie.core.util.I18N;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ControlSlot extends ControlSlotBase {
    public static Map<EnumHighlighting, List<Integer>> highlighting = new HashMap<>();
    public static boolean shiftClickActive = false;

    static {
        for (EnumHighlighting h : EnumHighlighting.values()) {
            ControlSlot.highlighting.put(h, new ArrayList<>());
        }
    }

    public Slot slot;

    public ControlSlot(IWidget parent, float x, float y) {
        super(parent, x, y);
        slot = null;
        addSelfEventHandler(new EventMouse.Down.Handler() {
            @Override
            public void onEvent(EventMouse.Down event) {
                if (slot == null) {
                    return;
                }

                PlayerControllerMP playerController =
                        ((Window) getSuperParent()).getGui().getMinecraft().playerController;
                int windowId = ((Window) getSuperParent()).getContainer().windowId;
                int slotNumber = slot.slotNumber;
                int button = event.getButton();
                Window.get(getWidget()).getGui();
                playerController.windowClick(
                        windowId,
                        slotNumber,
                        button,
                        GuiScreen.isShiftKeyDown() ? 1 : 0,
                        ((Window) getSuperParent()).getGui().getMinecraft().thePlayer);
            }
        });
    }

    public ControlSlot(IWidget parent, int x, int y, Slot slot) {
        super(parent, x, y);
        this.slot = slot;
    }

    @Override
    public void onRenderBackground() {
        CraftGUI.render.texture(CraftGUITexture.Slot, IPoint.ZERO);
        if (slot == null) {
            return;
        }

        InventorySlot islot = getInventorySlot();
        if (islot != null && islot.getValidator() != null) {
            IIcon icon = islot.getValidator().getIcon(!islot.getInputSides().isEmpty());
            if (icon != null) {
                CraftGUI.render.iconItem(new IPoint(1.0f, 1.0f), icon);
            }
        }

        boolean highlighted = false;
        for (Map.Entry<EnumHighlighting, List<Integer>> highlight : ControlSlot.highlighting.entrySet()) {
            if (highlight.getKey() == EnumHighlighting.SHIFT_CLICK && !ControlSlot.shiftClickActive) {
                continue;
            }
            if (highlighted || !highlight.getValue().contains(slot.slotNumber)) {
                continue;
            }

            highlighted = true;
            int c = 0xaa000000 + Math.min(highlight.getKey().getColour(), 0xffffff);
            CraftGUI.render.gradientRect(new IArea(1.0f, 1.0f, 16.0f, 16.0f), c, c);
        }
        if (!highlighted && getSuperParent().getMousedOverWidget() == this) {
            if (Window.get(this).getGui().getDraggedItem() != null
                    && !slot.isItemValid(Window.get(this).getGui().getDraggedItem())) {
                CraftGUI.render.gradientRect(new IArea(1.0f, 1.0f, 16.0f, 16.0f), 0xaaff9999, 0xaaff9999);
            } else {
                CraftGUI.render.gradientRect(new IArea(1.0f, 1.0f, 16.0f, 16.0f), 0x80ffffff, 0x80ffffff);
            }
        }
    }

    @Override
    public void onRenderOverlay() {
        if (slot == null) {
            return;
        }

        boolean highlighted = false;
        for (Map.Entry<EnumHighlighting, List<Integer>> highlight : ControlSlot.highlighting.entrySet()) {
            if (highlight.getKey() == EnumHighlighting.SHIFT_CLICK && !ControlSlot.shiftClickActive) {
                continue;
            }
            if (highlighted || !highlight.getValue().contains(slot.slotNumber)) {
                continue;
            }

            highlighted = true;
            int c = highlight.getKey().getColour();
            IArea area = getArea();
            if (getParent() instanceof ControlSlotArray || getParent() instanceof ControlPlayerInventory) {
                area = getParent().getArea();
                area.setPosition(IPoint.ZERO.sub(getPosition()));
            }
            CraftGUI.render.color(c);
            CraftGUI.render.texture(CraftGUITexture.Outline, area.outset(1));
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
            for (ControlSlot slot2 : getControlSlots()) {
                if (slot2.slot != null) {
                    ControlSlot.highlighting.get(EnumHighlighting.HELP).add(slot2.slot.slotNumber);
                }
            }
        }
    }

    private List<ControlSlot> getControlSlots() {
        List<ControlSlot> slots = new ArrayList<>();
        if (getParent() instanceof ControlSlotArray || getParent() instanceof ControlPlayerInventory) {
            for (IWidget child : getParent().getWidgets()) {
                slots.add((ControlSlot) child);
            }
        } else {
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

    public ControlSlot assign(int index) {
        return assign(InventoryType.Machine, index);
    }

    public ControlSlot assign(InventoryType inventory, int index) {
        if (slot == null) {
            slot = ((Window) getSuperParent()).getContainer().getOrCreateSlot(inventory, index);
        }
        return this;
    }

    @Override
    public void getHelpTooltip(Tooltip tooltip) {
        if (slot == null) {
            return;
        }

        InventorySlot slot = getInventorySlot();
        if (getInventorySlot() != null) {
            tooltip.add(slot.getName());
            tooltip.add(I18N.localise("binniecore.gui.side.insertSide", MachineSide.asString(slot.getInputSides())));
            tooltip.add(I18N.localise("binniecore.gui.side.extractSide", MachineSide.asString(slot.getOutputSides())));

            if (slot.isReadOnly()) {
                tooltip.add(I18N.localise("binniecore.gui.slot.pickupOnly"));
            }

            if (slot.getValidator() == null) {
                tooltip.add(I18N.localise("binniecore.gui.slot.accepts", I18N.localise("binniecore.gui.slot.anyItem")));
            } else {
                tooltip.add(I18N.localise(
                        "binniecore.gui.slot.accepts", slot.getValidator().getTooltip()));
            }
        } else if (this.slot.inventory instanceof WindowInventory) {
            SlotValidator s = ((WindowInventory) this.slot.inventory).getValidator(this.slot.getSlotIndex());
            if (s == null) {
                tooltip.add(I18N.localise("binniecore.gui.slot.accepts", I18N.localise("binniecore.gui.slot.anyItem")));
            } else {
                tooltip.add(I18N.localise("binniecore.gui.slot.accepts", s.getTooltip()));
            }
        } else if (this.slot.inventory instanceof InventoryPlayer) {
            tooltip.add(I18N.localise("binniecore.gui.slot.playerInventory"));
        }
    }

    public InventorySlot getInventorySlot() {
        return (slot instanceof CustomSlot) ? ((CustomSlot) slot).getInventorySlot() : null;
    }
}
