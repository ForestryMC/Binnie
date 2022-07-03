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

public class PageColorMix extends PageAbstract<IFlowerColor> {
    ControlText title;
    ControlColorMixBox list;

    public PageColorMix(IWidget parent, DatabaseTab tab) {
        super(parent, tab);
        title = new ControlTextCentered(this, 8.0f, I18N.localise("botany.gui.controls.pageColorFurtherTitle"));
        list = new ControlColorMixBox(this, 4, 20, 136, 152, ControlColorMixBox.Type.Further);
    }

    @Override
    public void onValueChanged(IFlowerColor colour) {
        List<IColourMix> mixes = new ArrayList<>();
        for (IColourMix mix : BotanyCore.getFlowerRoot().getColourMixes(false)) {
            if (mix.getColor1() == colour || mix.getColor2() == colour) {
                mixes.add(mix);
            }
        }
        list.setOptions(mixes);
    }
}
