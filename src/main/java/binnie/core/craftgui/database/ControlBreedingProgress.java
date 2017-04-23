// 
// Decompiled by Procyon v0.5.30
// 

package binnie.core.craftgui.database;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;
import binnie.core.craftgui.resource.minecraft.CraftGUITextureSheet;
import binnie.core.craftgui.resource.minecraft.StandardTexture;
import binnie.core.genetics.BreedingSystem;

class ControlBreedingProgress extends Control
{
	private static Texture Progress;
	private float percentage;
	private int colour;

	public ControlBreedingProgress(final IWidget parent, final int x, final int y, final int width, final int height, final BreedingSystem system, final float percentage) {
		super(parent, x, y, width, height);
		this.percentage = percentage;
		colour = system.getColour();
	}

	@Override
	public void onRenderBackground() {
		CraftGUI.Render.texture(CraftGUITexture.PanelBlack, getArea());
		final IArea area = getArea().inset(1);
		area.setSize(new IPoint(area.size().x() * percentage, area.size().y()));
		CraftGUI.Render.colour(colour);
		CraftGUI.Render.texture(ControlBreedingProgress.Progress, area);
	}

	static {
		ControlBreedingProgress.Progress = new StandardTexture(80, 22, 4, 4, CraftGUITextureSheet.Controls2);
	}
}
