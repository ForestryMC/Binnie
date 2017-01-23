package binnie.craftgui.minecraft;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.events.EventHandler;
import binnie.craftgui.events.EventMouse;
import binnie.craftgui.resource.minecraft.CraftGUITexture;

public abstract class Dialog extends Control {
	public Dialog(final IWidget parent, final float w, final float h) {
		super(parent, (parent.w() - w) / 2.0f, (parent.h() - h) / 2.0f, w, h);
		this.addAttribute(Attribute.MouseOver);
		this.addAttribute(Attribute.AlwaysOnTop);
		this.addAttribute(Attribute.BlockTooltip);
		this.initialise();
		this.addEventHandler(new EventMouse.Down.Handler() {
			@Override
			public void onEvent(final EventMouse.Down event) {
				if (!Dialog.this.getArea().contains(Dialog.this.getRelativeMousePosition())) {
					Dialog.this.onClose();
					Dialog.this.getParent().deleteChild(Dialog.this);
				}
			}
		}.setOrigin(EventHandler.Origin.Any, this));
	}

	@Override
	public abstract void initialise();

	public abstract void onClose();

	@Override
	public void onRenderBackground() {
		CraftGUI.render.gradientRect(this.getArea().outset(400), -1442840576, -1442840576);
		CraftGUI.render.texture(CraftGUITexture.Window, this.getArea());
		CraftGUI.render.texture(CraftGUITexture.TabOutline, this.getArea().inset(4));
	}

	@Override
	public boolean isMouseOverWidget(final IPoint relativeMouse) {
		return true;
	}
}
