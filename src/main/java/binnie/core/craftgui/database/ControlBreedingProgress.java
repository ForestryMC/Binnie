package binnie.core.craftgui.database;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.Area;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.renderer.RenderUtil;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;
import binnie.core.craftgui.resource.minecraft.CraftGUITextureSheet;
import binnie.core.craftgui.resource.minecraft.StandardTexture;
import binnie.core.genetics.BreedingSystem;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
		CraftGUI.render.texture(CraftGUITexture.PanelBlack, this.getArea());
		final Area area = this.getArea().inset(1);
		area.setSize(new Point(Math.round(area.size().x() * this.percentage), area.size().y()));
		RenderUtil.setColour(this.colour);
		CraftGUI.render.texture(ControlBreedingProgress.Progress, area);
	}
}
