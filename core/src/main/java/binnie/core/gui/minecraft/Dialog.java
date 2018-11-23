package binnie.core.gui.minecraft;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.api.gui.IPoint;
import binnie.core.api.gui.IWidget;
import binnie.core.api.gui.events.EventHandlerOrigin;
import binnie.core.gui.Attribute;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.events.EventMouse;
import binnie.core.gui.renderer.RenderUtil;
import binnie.core.gui.resource.textures.CraftGUITexture;

@SideOnly(Side.CLIENT)
public abstract class Dialog extends Control {
	public Dialog(IWidget parent, int w, int h) {
		super(parent, (parent.getWidth() - w) / 2, (parent.getHeight() - h) / 2, w, h);
		this.addAttribute(Attribute.MOUSE_OVER);
		this.addAttribute(Attribute.ALWAYS_ON_TOP);
		this.addAttribute(Attribute.BLOCK_TOOLTIP);
		this.addEventHandler(EventMouse.Down.class, EventHandlerOrigin.ANY, this, event -> {
			if (!this.getArea().contains(this.getRelativeMousePosition())) {
				this.getParent().deleteChild(this);
			}
		});
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		RenderUtil.drawGradientRect(this.getArea().outset(400), -1442840576, -1442840576);
		CraftGUI.RENDER.texture(CraftGUITexture.WINDOW, this.getArea());
		CraftGUI.RENDER.texture(CraftGUITexture.TAB_OUTLINE, this.getArea().inset(4));
	}

	@Override
	public boolean isMouseOverWidget(IPoint relativeMouse) {
		return true;
	}
}
