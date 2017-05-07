package binnie.botany.craftgui;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.botany.api.IColourMix;
import binnie.botany.api.IFlowerColor;
import binnie.botany.core.BotanyCore;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.database.DatabaseTab;
import binnie.core.craftgui.database.PageAbstract;

import java.util.ArrayList;
import java.util.List;

public class PageColourMix extends PageAbstract<IFlowerColor> {
	ControlText pageSpeciesFurtherTitle;
	ControlColorMixBox pageSpeciesFurtherList;

	public PageColourMix(IWidget parent, DatabaseTab tab) {
		super(parent, tab);
		pageSpeciesFurtherTitle = new ControlTextCentered(this, 8.0f, Binnie.I18N.localise(Botany.instance, "gui.controls.pageColorFurtherTitle"));
		pageSpeciesFurtherList = new ControlColorMixBox(this, 4, 20, 136, 152, ControlColorMixBox.Type.Further);
	}

	@Override
	public void onValueChanged(IFlowerColor colour) {
		List<IColourMix> mixes = new ArrayList<IColourMix>();
		for (IColourMix mix : BotanyCore.getFlowerRoot().getColourMixes(false)) {
			if (mix.getColor1() == colour || mix.getColor2() == colour) {
				mixes.add(mix);
			}
		}
		pageSpeciesFurtherList.setOptions(mixes);
	}
}
