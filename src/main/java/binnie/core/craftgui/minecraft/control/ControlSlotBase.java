package binnie.core.craftgui.minecraft.control;

import binnie.core.craftgui.Attribute;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.events.EventWidget;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.renderer.RenderUtil;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ControlSlotBase extends Control implements ITooltip {
	private ControlItemDisplay itemDisplay;

	public ControlSlotBase(final IWidget parent, final int x, final int y) {
		this(parent, x, y, 18);
	}

	public ControlSlotBase(final IWidget parent, final int x, final int y, final int size) {
		super(parent, x, y, size, size);
		this.addAttribute(Attribute.MouseOver);
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
		final int size = this.getSize().x();
		CraftGUI.render.texture(CraftGUITexture.Slot, this.getArea());
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
		final ItemStack item = this.getItemStack();
		if (item.isEmpty()) {
			return;
		}
		tooltip.add(item.getTooltip(((Window) this.getTopParent()).getPlayer(), false));
	}

	public abstract ItemStack getItemStack();
}
