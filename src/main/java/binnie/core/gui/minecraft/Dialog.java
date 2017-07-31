package binnie.core.gui.minecraft;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.gui.Attribute;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.IWidget;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.events.EventHandler;
import binnie.core.gui.events.EventMouse;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.renderer.RenderUtil;
import binnie.core.gui.resource.minecraft.CraftGUITexture;

@SideOnly(Side.CLIENT)
public abstract class Dialog extends Control {
	public Dialog(final IWidget parent, final int w, final int h) {
		super(parent, (parent.width() - w) / 2, (parent.height() - h) / 2, w, h);
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
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		RenderUtil.drawGradientRect(this.getArea().outset(400), -1442840576, -1442840576);
		CraftGUI.RENDER.texture(CraftGUITexture.Window, this.getArea());
		CraftGUI.RENDER.texture(CraftGUITexture.TabOutline, this.getArea().inset(4));
	}

	@Override
	public boolean isMouseOverWidget(final Point relativeMouse) {
		return true;
	}
}
