package binnie.botany.craftgui;

import binnie.botany.api.IColourMix;
import binnie.botany.api.IFlowerColor;
import binnie.botany.core.BotanyCore;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.database.DatabaseTab;
import binnie.core.craftgui.database.PageAbstract;
import binnie.core.util.I18N;
import java.util.ArrayList;
import java.util.List;

public class PageColorMixResultant extends PageAbstract<IFlowerColor> {
    protected ControlText title;
    protected ControlColorMixBox list;

    public PageColorMixResultant(IWidget parent, DatabaseTab tab) {
        super(parent, tab);
        title = new ControlTextCentered(this, 8.0f, I18N.localise("botany.gui.controls.pageSpeciesFurtherTitle"));
        list = new ControlColorMixBox(this, 4, 20, 136, 152, ControlColorMixBox.Type.Resultant);
    }

    @Override
    public void onValueChanged(IFlowerColor color) {
        List<IColourMix> mixes = new ArrayList<>();
        for (IColourMix mix : BotanyCore.getFlowerRoot().getColourMixes(false)) {
            if (mix.getResult() == color) {
                mixes.add(mix);
            }
        }
        list.setOptions(mixes);
    }
}
