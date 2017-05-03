package binnie.core.craftgui.minecraft;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.events.EventHandler;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;

public abstract class Dialog extends Control
{
	public Dialog(IWidget parent, float w, float h) {
		super(parent, (parent.w() - w) / 2.0f, (parent.h() - h) / 2.0f, w, h);
		addAttribute(WidgetAttribute.MouseOver);
		addAttribute(WidgetAttribute.AlwaysOnTop);
		addAttribute(WidgetAttribute.BlockTooltip);
		initialise();
		addEventHandler(new EventMouse.Down.Handler() {
			@Override
			public void onEvent(EventMouse.Down event) {
				if (!getArea().contains(getRelativeMousePosition())) {
					onClose();
					getParent().deleteChild(Dialog.this);
				}
			}
		}.setOrigin(EventHandler.Origin.Any, this));
	}

	@Override
	public abstract void initialise();

	public abstract void onClose();

	@Override
	public void onRenderBackground() {
		CraftGUI.Render.gradientRect(getArea().outset(400), -1442840576, -1442840576);
		CraftGUI.Render.texture(CraftGUITexture.Window, getArea());
		CraftGUI.Render.texture(CraftGUITexture.TabOutline, getArea().inset(4));
	}

	@Override
	public boolean isMouseOverWidget(IPoint relativeMouse) {
		return true;
	}
}
