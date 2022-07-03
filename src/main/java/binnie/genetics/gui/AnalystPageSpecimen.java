package binnie.genetics.gui;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.util.I18N;
import forestry.api.lepidopterology.IButterfly;
import net.minecraft.util.EnumChatFormatting;

public class AnalystPageSpecimen extends ControlAnalystPage {
    public AnalystPageSpecimen(IWidget parent, IArea area, IButterfly ind) {
        super(parent, area);
        setColor(0x333333);
        int y = 4;
        new ControlTextCentered(this, y, EnumChatFormatting.UNDERLINE + getTitle()).setColor(getColor());

        y += 12;
        float w = (w() - 16.0f) * ind.getSize();
        new ControlIndividualDisplay(this, (w() - w) / 2.0f, y + (w() - w) / 2.0f, w, ind);
    }

    @Override
    public String getTitle() {
        return I18N.localise("genetics.gui.analyst.specimen");
    }
}
