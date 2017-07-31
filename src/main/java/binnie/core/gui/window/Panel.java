package binnie.core.gui.window;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.gui.CraftGUI;
import binnie.core.gui.IWidget;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.minecraft.MinecraftGUI;
import binnie.core.gui.resource.minecraft.CraftGUITexture;

public class Panel extends Control {
	IPanelType type;

	public Panel(final IWidget parent, final int x, final int y, final int width, final int height, final IPanelType type) {
		super(parent, x, y, width, height);
		this.type = type;
	}

	public Panel(final IWidget parent, final Area area, final IPanelType type) {
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
					CraftGUI.RENDER.texture(CraftGUITexture.PanelBlack, this.getArea());
					break;
				}
				case Gray: {
					CraftGUI.RENDER.texture(CraftGUITexture.PanelGray, this.getArea());
					break;
				}
				case Tinted: {
					CraftGUI.RENDER.texture(CraftGUITexture.PanelTinted, this.getArea());
					break;
				}
				case Outline: {
					CraftGUI.RENDER.texture(CraftGUITexture.Outline, this.getArea());
					break;
				}
				case TabOutline: {
					CraftGUI.RENDER.texture(CraftGUITexture.TabOutline, this.getArea());
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
