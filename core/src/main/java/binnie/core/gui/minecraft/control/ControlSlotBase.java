package binnie.core.gui.minecraft.control;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.gui.Attribute;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.ITooltip;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.Tooltip;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.events.EventWidget;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.minecraft.Window;
import binnie.core.gui.renderer.RenderUtil;
import binnie.core.gui.resource.textures.CraftGUITexture;

public abstract class ControlSlotBase extends Control implements ITooltip {
	private ControlItemDisplay itemDisplay;

	public ControlSlotBase(final IWidget parent, final int x, final int y) {
		this(parent, x, y, 18);
	}

	public ControlSlotBase(final IWidget parent, final int x, final int y, final int size) {
		super(parent, x, y, size, size);
		this.addAttribute(Attribute.MOUSE_OVER);
		this.itemDisplay = new ControlItemDisplay(this, 1, 1, size - 2);
		this.addSelfEventHandler(EventWidget.ChangeSize.class, event -> {
			ControlSlotBase.this.itemDisplay.setSize(ControlSlotBase.this.getSize().sub(new Point(2, 2)));
		});
	}

	protected void setRotating() {
		this.itemDisplay.setRotating();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		CraftGUI.RENDER.texture(CraftGUITexture.SLOT, this.getArea());
		if (this.getTopParent().getMousedOverWidget() == this) {
			Area area = new Area(new Point(1, 1), this.getArea().size().sub(new Point(2, 2)));
			RenderUtil.drawGradientRect(area, -2130706433, -2130706433);
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
	public void getTooltip(final Tooltip tooltip, ITooltipFlag tooltipFlag) {
		final ItemStack stack = this.getItemStack();
		if (stack.isEmpty()) {
			return;
		}
		List<String> list = stack.getTooltip(((Window) this.getTopParent()).getPlayer(), tooltipFlag);

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
