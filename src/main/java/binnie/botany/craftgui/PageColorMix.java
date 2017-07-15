package binnie.botany.craftgui;

import java.util.ArrayList;
import java.util.List;

import binnie.botany.api.IColorMix;
import binnie.botany.api.IFlowerColor;
import binnie.botany.core.BotanyCore;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.database.DatabaseTab;
import binnie.core.craftgui.database.PageAbstract;
import binnie.core.util.I18N;

public class PageColorMix extends PageAbstract<IFlowerColor> {
	ControlText pageSpeciesFurther_Title;
	ControlColorMixBox pageSpeciesFurther_List;

	public PageColorMix(IWidget parent, DatabaseTab tab) {
		super(parent, tab);
		pageSpeciesFurther_Title = new ControlTextCentered(this, 8, I18N.localise("botany.gui.controls.page.color_further.title"));
		pageSpeciesFurther_List = new ControlColorMixBox(this, 4, 20, 136, 152, ControlColorMixBox.Type.FURTHER);
	}

	@Override
	public void onValueChanged(IFlowerColor colour) {
		List<IColorMix> mixes = new ArrayList<>();
		for (IColorMix mix : BotanyCore.getFlowerRoot().getColorMixes(false)) {
			if (mix.getColorFirst() == colour || mix.getColorSecond() == colour) {
				mixes.add(mix);
			}
		}
		pageSpeciesFurther_List.setOptions(mixes);
	}
}
