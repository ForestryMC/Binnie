package binnie.botany.gui.database;

import binnie.botany.api.genetics.IColorMix;
import binnie.botany.api.genetics.IFlowerColor;
import binnie.botany.core.BotanyCore;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.controls.ControlText;
import binnie.core.gui.controls.ControlTextCentered;
import binnie.core.gui.database.DatabaseConstants;
import binnie.core.gui.database.DatabaseTab;
import binnie.core.gui.database.PageAbstract;
import binnie.core.util.I18N;

import java.util.ArrayList;
import java.util.List;

public class PageColorMixResultant extends PageAbstract<IFlowerColor> {
	private final ControlText pageSpeciesFurtherTitle;
	private final ControlColorMixBox pageSpeciesFurtherList;

	public PageColorMixResultant(IWidget parent, DatabaseTab tab) {
		super(parent, tab);
		pageSpeciesFurtherTitle = new ControlTextCentered(this, 8, I18N.localise(DatabaseConstants.BOTANY_PAGES_KEY + ".species_further.title"));
		pageSpeciesFurtherList = new ControlColorMixBox(this, 4, 20, 136, 152, ControlColorMixBox.Type.RESULTANT);
	}

	@Override
	public void onValueChanged(IFlowerColor colour) {
		List<IColorMix> mixes = new ArrayList<>();
		for (IColorMix mix : BotanyCore.getFlowerRoot().getColorMixes(false)) {
			if (mix.getResult() == colour) {
				mixes.add(mix);
			}
		}
		pageSpeciesFurtherList.setOptions(mixes);
	}
}
