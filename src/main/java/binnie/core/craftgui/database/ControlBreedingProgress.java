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

class ControlBreedingProgress extends Control {
    private static Texture Progress = new StandardTexture(80, 22, 4, 4, CraftGUITextureSheet.Controls2);
    private float percentage;
    private int colour;

    public ControlBreedingProgress(
            IWidget parent, int x, int y, int width, int height, BreedingSystem system, float percentage) {
        super(parent, x, y, width, height);
        this.percentage = percentage;
        colour = system.getColor();
    }

    @Override
    public void onRenderBackground() {
        CraftGUI.render.texture(CraftGUITexture.PanelBlack, getArea());
        IArea area = getArea().inset(1);
        area.setSize(new IPoint(area.size().x() * percentage, area.size().y()));
        CraftGUI.render.color(colour);
        CraftGUI.render.texture(ControlBreedingProgress.Progress, area);
    }
}
