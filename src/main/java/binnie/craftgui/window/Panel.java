package binnie.craftgui.window;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.minecraft.MinecraftGUI;
import binnie.craftgui.resource.minecraft.CraftGUITexture;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Panel extends Control {
	IPanelType type;

	public Panel(final IWidget parent, final int x, final int y, final int width, final int height, final IPanelType type) {
		super(parent, x, y, width, height);
		this.type = type;
	}

	public Panel(final IWidget parent, final IArea area, final IPanelType type) {
		this(parent, area.xPos(), area.yPos(), area.width(), area.height(), type);
	}

	public IPanelType getType() {
		return this.type;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		final IPanelType panelType = this.getType();
		if (panelType instanceof MinecraftGUI.PanelType) {
			switch ((MinecraftGUI.PanelType) panelType) {
				case Black: {
					CraftGUI.render.texture(CraftGUITexture.PanelBlack, this.getArea());
					break;
				}
				case Gray: {
					CraftGUI.render.texture(CraftGUITexture.PanelGray, this.getArea());
					break;
				}
				case Tinted: {
					CraftGUI.render.texture(CraftGUITexture.PanelTinted, this.getArea());
					break;
				}
				case Outline: {
					CraftGUI.render.texture(CraftGUITexture.Outline, this.getArea());
					break;
				}
				case TabOutline: {
					CraftGUI.render.texture(CraftGUITexture.TabOutline, this.getArea());
					break;
				}
				default: {
					break;
				}
			}
		}
	}

	public interface IPanelType {
	}
}
