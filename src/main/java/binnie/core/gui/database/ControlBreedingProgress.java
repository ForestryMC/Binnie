package binnie.core.gui.database;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.genetics.BreedingSystem;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.IWidget;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.renderer.RenderUtil;
import binnie.core.gui.resource.Texture;
import binnie.core.gui.resource.minecraft.CraftGUITexture;
import binnie.core.gui.resource.minecraft.CraftGUITextureSheet;
import binnie.core.gui.resource.minecraft.StandardTexture;

class ControlBreedingProgress extends Control {
	private static Texture Progress = new StandardTexture(80, 22, 4, 4, CraftGUITextureSheet.Controls2);
	private float percentage;
	private int colour;

	public ControlBreedingProgress(final IWidget parent, final int x, final int y, final int width, final int height, final BreedingSystem system, final float percentage) {
		super(parent, x, y, width, height);
		this.percentage = percentage;
		this.colour = system.getColour();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		CraftGUI.RENDER.texture(CraftGUITexture.PanelBlack, this.getArea());
		final Area area = this.getArea().inset(1);
		area.setSize(new Point(Math.round(area.size().xPos() * this.percentage), area.size().yPos()));
		RenderUtil.setColour(this.colour);
		CraftGUI.RENDER.texture(ControlBreedingProgress.Progress, area);
	}
}
