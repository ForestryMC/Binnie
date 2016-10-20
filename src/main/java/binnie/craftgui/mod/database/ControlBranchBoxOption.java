// 
// Decompiled by Procyon v0.5.30
// 

package binnie.craftgui.mod.database;

import binnie.craftgui.controls.listbox.ControlList;
import forestry.api.genetics.IClassification;
import binnie.craftgui.controls.listbox.ControlTextOption;

class ControlBranchBoxOption extends ControlTextOption<IClassification>
{
	public ControlBranchBoxOption(final ControlList<IClassification> controlList, final IClassification option, final int y) {
		super(controlList, option, (option.getName() == null) ? option.getScientific() : (option.getName().contains(".") ? option.getScientific() : option.getName()), y);
	}
}
