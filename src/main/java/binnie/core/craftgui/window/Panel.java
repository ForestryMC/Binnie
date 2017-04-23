// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.window;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.minecraft.MinecraftGUI;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;

public class Panel extends Control
{
	IPanelType type;

	public Panel(final IWidget parent, final float x, final float y, final float width, final float height, final IPanelType type) {
		super(parent, x, y, width, height);
		this.type = type;
	}

	public Panel(final IWidget parent, final IArea area, final IPanelType type) {
		this(parent, area.x(), area.y(), area.w(), area.h(), type);
	}

	public IPanelType getType() {
		return type;
	}

	@Override
	public void onRenderBackground() {
		final IPanelType panelType = getType();
		if (panelType instanceof MinecraftGUI.PanelType) {
			switch ((MinecraftGUI.PanelType) panelType) {
			case Black: {
				CraftGUI.Render.texture(CraftGUITexture.PanelBlack, getArea());
				break;
			}
			case Gray: {
				CraftGUI.Render.texture(CraftGUITexture.PanelGray, getArea());
				break;
			}
			case Tinted: {
				CraftGUI.Render.texture(CraftGUITexture.PanelTinted, getArea());
				break;
			}
			case Outline: {
				CraftGUI.Render.texture(CraftGUITexture.Outline, getArea());
				break;
			}
			case TabOutline: {
				CraftGUI.Render.texture(CraftGUITexture.TabOutline, getArea());
				break;
			}
			}
		}
	}

	public interface IPanelType
	{
	}
}
