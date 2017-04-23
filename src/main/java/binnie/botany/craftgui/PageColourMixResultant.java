// 
// Decompiled by Procyon v0.5.30
// 

package binnie.botany.craftgui;

import java.util.List;
import binnie.botany.core.BotanyCore;
import binnie.botany.api.IColourMix;
import java.util.ArrayList;
import binnie.core.craftgui.controls.ControlTextCentered;
import binnie.core.craftgui.database.DatabaseTab;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.botany.api.IFlowerColour;
import binnie.core.craftgui.database.PageAbstract;

public class PageColourMixResultant extends PageAbstract<IFlowerColour>
{
	ControlText pageSpeciesFurther_Title;
	ControlColourMixBox pageSpeciesFurther_List;

	public PageColourMixResultant(final IWidget parent, final DatabaseTab tab) {
		super(parent, tab);
		this.pageSpeciesFurther_Title = new ControlTextCentered(this, 8.0f, "Resultant Mixes");
		this.pageSpeciesFurther_List = new ControlColourMixBox(this, 4, 20, 136, 152, ControlColourMixBox.Type.Resultant);
	}

	@Override
	public void onValueChanged(final IFlowerColour colour) {
		final List<IColourMix> mixes = new ArrayList<IColourMix>();
		for (final IColourMix mix : BotanyCore.getFlowerRoot().getColourMixes(false)) {
			if (mix.getResult() == colour) {
				mixes.add(mix);
			}
		}
		this.pageSpeciesFurther_List.setOptions(mixes);
	}
}
