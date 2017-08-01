package binnie.core.gui.minecraft.control;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.gui.Attribute;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.ITooltip;
import binnie.core.gui.IWidget;
import binnie.core.gui.Tooltip;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.events.EventWidget;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.minecraft.Window;
import binnie.core.gui.renderer.RenderUtil;
import binnie.core.gui.resource.minecraft.CraftGUITexture;

public abstract class ControlSlotBase extends Control implements ITooltip {
	private ControlItemDisplay itemDisplay;

	public ControlSlotBase(final IWidget parent, final int x, final int y) {
		this(parent, x, y, 18);
	}

	public ControlSlotBase(final IWidget parent, final int x, final int y, final int size) {
		super(parent, x, y, size, size);
		this.addAttribute(Attribute.MOUSE_OVER);
		this.itemDisplay = new ControlItemDisplay(this, 1, 1, size - 2);
		this.addSelfEventHandler(new EventWidget.ChangeSize.Handler() {
			@Override
			public void onEvent(final EventWidget.ChangeSize event) {
				ControlSlotBase.this.itemDisplay.setSize(ControlSlotBase.this.getSize().sub(new Point(2, 2)));
			}
		});
	}

	protected void setRotating() {
		this.itemDisplay.setRotating();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		final int size = this.getSize().xPos();
		CraftGUI.RENDER.texture(CraftGUITexture.Slot, this.getArea());
		if (this.getTopParent().getMousedOverWidget() == this) {
			RenderUtil.drawGradientRect(new Area(new Point(1, 1), this.getArea().size().sub(new Point(2, 2))), -2130706433, -2130706433);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onUpdateClient() {
		super.onUpdateClient();
		this.itemDisplay.setItemStack(this.getItemStack());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getTooltip(final Tooltip tooltip) {
		final ItemStack stack = this.getItemStack();
		if (stack.isEmpty()) {
			return;
		}
		List<String> list = stack.getTooltip(((Window) this.getTopParent()).getPlayer(), false);

		for (int i = 0; i < list.size(); ++i) {
			if (i == 0) {
				list.set(i, stack.getRarity().rarityColor + list.get(i));
			} else {
				list.set(i, TextFormatting.GRAY + list.get(i));
			}
		}
		tooltip.add(list);
	}

	public abstract ItemStack getItemStack();
}
