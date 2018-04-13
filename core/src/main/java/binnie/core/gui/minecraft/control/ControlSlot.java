package binnie.core.gui.minecraft.control;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import binnie.core.ModId;
import binnie.core.api.gui.IArea;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.Tooltip;
import binnie.core.gui.events.EventMouse;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.minecraft.CustomSlot;
import binnie.core.gui.minecraft.GuiCraftGUI;
import binnie.core.gui.minecraft.InventoryType;
import binnie.core.gui.minecraft.Window;
import binnie.core.gui.minecraft.WindowInventory;
import binnie.core.gui.renderer.RenderUtil;
import binnie.core.gui.resource.textures.CraftGUITexture;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.inventory.MachineSide;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.util.I18N;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ControlSlot extends ControlSlotBase {
	public static final Map<EnumHighlighting, List<Integer>> highlighting = new EnumMap<>(EnumHighlighting.class);
	public static boolean shiftClickActive;

	static {
		for (final EnumHighlighting h : EnumHighlighting.values()) {
			ControlSlot.highlighting.put(h, new ArrayList<>());
		}
	}

	public final Slot slot;

	public ControlSlot(final IWidget parent, final int x, final int y, final Slot slot) {
		super(parent, x, y);
		this.slot = slot;
		this.addSelfEventHandler(EventMouse.Down.class, event -> {
			Window superParent = (Window) ControlSlot.this.getTopParent();
			GuiCraftGUI superParentGui = superParent.getGui();
			Minecraft mc = superParentGui.getMinecraft();
			final PlayerControllerMP playerController = mc.playerController;
			final int windowId = superParent.getContainer().windowId;
			final int slotNumber = this.slot.slotNumber;
			final int button = event.getButton();
			Window.get(this).getGui();
			if (playerController != null) {
				boolean clone = mc.gameSettings.keyBindPickBlock.isActiveAndMatches(button - 100);
				ClickType clickType;
				if (clone) {
					clickType = ClickType.CLONE;
				} else if (GuiScreen.isShiftKeyDown()) {
					clickType = ClickType.QUICK_MOVE;
				} else {
					clickType = ClickType.PICKUP;
				}
				playerController.windowClick(windowId, slotNumber, button, clickType, mc.player);
			}
		});
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		CraftGUI.RENDER.texture(CraftGUITexture.SLOT, Point.ZERO);
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
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderForeground(int guiWidth, int guiHeight) {
		boolean highlighted = false;
		for (final Map.Entry<EnumHighlighting, List<Integer>> highlight : ControlSlot.highlighting.entrySet()) {
			if (highlight.getKey() == EnumHighlighting.SHIFT_CLICK && !ControlSlot.shiftClickActive) {
				continue;
			}
			if (highlighted || !highlight.getValue().contains(this.slot.slotNumber)) {
				continue;
			}
			highlighted = true;
			final int c = -1442840576 + Math.min(highlight.getKey().getColour(), 16777215);
			GlStateManager.disableBlend();
			GlStateManager.disableDepth();
			RenderUtil.drawGradientRect(new Area(1, 1, 16, 16), c, c);
			GlStateManager.enableBlend();
			GlStateManager.enableDepth();
		}
		if (!highlighted && this.getTopParent().getMousedOverWidget() == this) {
			GuiCraftGUI gui = Window.get(this).getGui();
			GlStateManager.disableBlend();
			GlStateManager.disableDepth();
			if (!gui.getDraggedItem().isEmpty() && !this.slot.isItemValid(gui.getDraggedItem())) {
				RenderUtil.drawGradientRect(new Area(1, 1, 16, 16), -1426089575, -1426089575);
			} else {
				RenderUtil.drawGradientRect(new Area(1, 1, 16, 16), -2130706433, -2130706433);
			}
			GlStateManager.enableBlend();
			GlStateManager.enableDepth();
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderOverlay() {
		boolean highlighted = false;
		for (final Map.Entry<EnumHighlighting, List<Integer>> highlight : ControlSlot.highlighting.entrySet()) {
			if (highlight.getKey() == EnumHighlighting.SHIFT_CLICK && !ControlSlot.shiftClickActive) {
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
				area.setPosition(Point.ZERO.sub(this.getPosition()));
			}
			RenderUtil.setColour(c);
			CraftGUI.RENDER.texture(CraftGUITexture.OUTLINE, area.outset(1));
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
				ControlSlot.highlighting.get(EnumHighlighting.HELP).add(slot2.slot.slotNumber);
			}
		}
	}

	private List<ControlSlot> getControlSlots() {
		final List<ControlSlot> slots = new ArrayList<>();
		if (this.getParent() instanceof ControlSlotArray || this.getParent() instanceof ControlPlayerInventory) {
			for (final IWidget child : this.getParent().getChildren()) {
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
	public void getHelpTooltip(final Tooltip tooltip, ITooltipFlag tooltipFlag) {
		final InventorySlot slot = this.getInventorySlot();
		if (slot != null) {
			tooltip.add(slot.getName());
			if (tooltipFlag.isAdvanced()) {
                Collection<EnumFacing> inputSides = slot.getInputSides();
				if (inputSides.size() > 0) {
					tooltip.add(TextFormatting.GRAY + I18N.localise(ModId.CORE, "gui.side.insert", MachineSide.asString(inputSides)));
				}
                Collection<EnumFacing> outputSides = slot.getOutputSides();
				if (outputSides.size() > 0) {
					tooltip.add(TextFormatting.GRAY + I18N.localise(ModId.CORE, "gui.side.extract", MachineSide.asString(outputSides)));
				}
				if (slot.isReadOnly()) {
					tooltip.add(TextFormatting.GRAY + I18N.localise(ModId.CORE, "gui.slot.pickup.only"));
				}
			}
		} else if (this.slot.inventory instanceof WindowInventory) {
			if (tooltipFlag.isAdvanced()) {
				final SlotValidator s = ((WindowInventory) this.slot.inventory).getValidator(this.slot.getSlotIndex());
				final String itemDescription = (s == null) ? I18N.localise(ModId.CORE, "gui.slot.window.inventory.any") : s.getTooltip();
				tooltip.add(I18N.localise(ModId.CORE, "gui.slot.window.inventory", itemDescription));
			}
		} else if (this.slot.inventory instanceof InventoryPlayer) {
			if (tooltipFlag.isAdvanced()) {
				tooltip.add(I18N.localise(ModId.CORE, "gui.slot.player.inventory"));
			}
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
			return assign(InventoryType.MACHINE, index);
		}

		public ControlSlot assign(final InventoryType inventory, final int index) {
			Slot slot = ((Window) parent.getTopParent()).getContainer().createClientSlot(inventory, index);
			return new ControlSlot(parent, x, y, slot);
		}
	}
}
