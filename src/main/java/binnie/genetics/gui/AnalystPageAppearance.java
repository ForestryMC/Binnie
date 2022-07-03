package binnie.genetics.gui;

import binnie.botany.api.EnumFlowerStage;
import binnie.botany.api.IFlower;
import binnie.botany.craftgui.ControlColorDisplay;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.minecraft.control.ControlIconDisplay;
import binnie.core.util.I18N;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;

public class AnalystPageAppearance extends ControlAnalystPage {
    public AnalystPageAppearance(IWidget parent, IArea area, IFlower ind) {
        super(parent, area);
        setColor(0x333333);
        int y = 4;
        new ControlTextCentered(this, y, EnumChatFormatting.UNDERLINE + getTitle()).setColor(getColor());
        y += 12;

        ControlColorDisplay a = new ControlColorDisplay(this, w() / 2.0f - 28.0f, y);
        a.setValue(ind.getGenome().getPrimaryColor());
        a.addTooltip(I18N.localise("genetics.gui.analyst.primaryColor"));

        ControlColorDisplay b = new ControlColorDisplay(this, w() / 2.0f - 8.0f, y);
        b.setValue(ind.getGenome().getSecondaryColor());
        b.addTooltip(I18N.localise("genetics.gui.analyst.secondaryColor"));

        ControlColorDisplay c = new ControlColorDisplay(this, w() / 2.0f + 12.0f, y);
        c.setValue(ind.getGenome().getStemColor());
        c.addTooltip(I18N.localise("genetics.gui.analyst.stemColor"));
        y += 26;

        int sections = ind.getGenome().getType().getSections();
        int w = (sections > 1) ? 50 : 100;

        new ControlIconDisplay(this, (w() - w) / 2.0f, y - ((sections == 1) ? 0 : 0), null) {
            @Override
            public void onRenderForeground() {
                GL11.glPushMatrix();
                float scale = w / 16.0f;
                float dy = (sections > 1) ? 16.0f : 0.0f;
                GL11.glScalef(scale, scale, 1.0f);
                CraftGUI.render.color(ind.getGenome().getStemColor().getColor(false));
                if (sections > 1) {
                    CraftGUI.render.iconBlock(
                            new IPoint(0.0f, 0.0f), ind.getGenome().getType().getStem(EnumFlowerStage.FLOWER, true, 1));
                }

                CraftGUI.render.iconBlock(
                        new IPoint(0.0f, dy), ind.getGenome().getType().getStem(EnumFlowerStage.FLOWER, true, 0));
                CraftGUI.render.color(ind.getGenome().getPrimaryColor().getColor(false));
                if (sections > 1) {
                    CraftGUI.render.iconBlock(
                            new IPoint(0.0f, 0.0f),
                            ind.getGenome().getType().getPetalIcon(EnumFlowerStage.FLOWER, true, 1));
                }

                CraftGUI.render.iconBlock(
                        new IPoint(0.0f, dy), ind.getGenome().getType().getPetalIcon(EnumFlowerStage.FLOWER, true, 0));
                CraftGUI.render.color(ind.getGenome().getSecondaryColor().getColor(false));
                if (sections > 1) {
                    CraftGUI.render.iconBlock(
                            new IPoint(0.0f, 0.0f),
                            ind.getGenome().getType().getVariantIcon(EnumFlowerStage.FLOWER, true, 1));
                }

                CraftGUI.render.iconBlock(
                        new IPoint(0.0f, dy),
                        ind.getGenome().getType().getVariantIcon(EnumFlowerStage.FLOWER, true, 0));
                GL11.glPopMatrix();
            }
        };
    }

    @Override
    public String getTitle() {
        return I18N.localise("genetics.gui.analyst.appearance");
    }
}
