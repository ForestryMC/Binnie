package binnie.genetics.gui;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.util.I18N;
import java.text.DecimalFormat;

public abstract class ControlAnalystPage extends Control {
    public ControlAnalystPage(IWidget parent, IArea area) {
        super(parent, area);
        hide();
    }

    @Override
    public void onRenderBackground() {
        // ignored
    }

    public abstract String getTitle();

    protected String getTimeString(float time) {
        float seconds = time / 20.0f;
        float minutes = seconds / 60.0f;
        float hours = minutes / 60.0f;
        DecimalFormat df = new DecimalFormat("#.#");
        if (hours > 1.0f) {
            return I18N.localise("genetics.gui.analyst.page.hours", df.format(hours));
        }
        if (minutes > 1.0f) {
            return I18N.localise("genetics.gui.analyst.page.minutes", df.format(minutes));
        }
        return I18N.localise("genetics.gui.analyst.page.seconds", df.format(seconds));
    }
}
