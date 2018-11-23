package binnie.core.gui.window;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.api.gui.IArea;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.minecraft.MinecraftGUI;
import binnie.core.gui.resource.textures.CraftGUITexture;

public class Panel extends Control {
	private final IPanelType type;

	public Panel(IWidget parent, int x, int y, int width, int height, IPanelType type) {
		super(parent, x, y, width, height);
		this.type = type;
	}

	public Panel(IWidget parent, IArea area, IPanelType type) {
		this(parent, area.xPos(), area.yPos(), area.width(), area.height(), type);
	}

	public IPanelType getType() {
		return this.type;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		IPanelType panelType = this.getType();
		if (panelType instanceof MinecraftGUI.PanelType) {
			switch ((MinecraftGUI.PanelType) panelType) {
				case BLACK: {
					CraftGUI.RENDER.texture(CraftGUITexture.PANEL_BLACK, this.getArea());
					break;
				}
				case GRAY: {
					CraftGUI.RENDER.texture(CraftGUITexture.PANEL_GRAY, this.getArea());
					break;
				}
				case TINTED: {
					CraftGUI.RENDER.texture(CraftGUITexture.PANEL_TINTED, this.getArea());
					break;
				}
				case OUTLINE: {
					CraftGUI.RENDER.texture(CraftGUITexture.OUTLINE, this.getArea());
					break;
				}
				case TAB_OUTLINE: {
					CraftGUI.RENDER.texture(CraftGUITexture.TAB_OUTLINE, this.getArea());
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
