package binnie.core.gui.database;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.genetics.IAlleleSpecies;

import binnie.core.gui.Attribute;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.controls.listbox.ControlList;
import binnie.core.gui.controls.listbox.ControlTextOption;
import binnie.core.gui.geometry.CraftGUIUtil;
import binnie.core.gui.geometry.Point;

@SideOnly(Side.CLIENT)
class ControlSpeciesBoxOption extends ControlTextOption<IAlleleSpecies> {
	private ControlDatabaseIndividualDisplay controlBee;

	public ControlSpeciesBoxOption(final ControlList<IAlleleSpecies> controlList, final IAlleleSpecies option, final int y) {
		super(controlList, option, option.getAlleleName(), y);
		this.setSize(new Point(this.getSize().x(), 20));
		(this.controlBee = new ControlDatabaseIndividualDisplay(this, 2, 2)).setSpecies(this.getValue(), EnumDiscoveryState.Undetermined);
		if (this.controlBee.discovered == EnumDiscoveryState.Discovered) {
			this.controlBee.discovered = EnumDiscoveryState.Show;
		}
		this.textWidget.setValue((this.controlBee.discovered == EnumDiscoveryState.Show) ? option.getAlleleName() : DatabaseConstants.CONTROL_KEY + ".undiscovered");
		if (this.controlBee.discovered == EnumDiscoveryState.Show) {
			this.addAttribute(Attribute.MouseOver);
		}
		CraftGUIUtil.moveWidget(this.textWidget, new Point(22, 0));
		this.textWidget.setSize(this.textWidget.getSize().sub(new Point(24, 0)));
		final int th = CraftGUI.RENDER.textHeight(this.textWidget.getValue(), this.textWidget.getSize().x());
		final int height = Math.max(20, th + 6);
		this.setSize(new Point(this.size().x(), height));
		this.textWidget.setSize(new Point(this.textWidget.getSize().x(), height));
		this.controlBee.setPosition(new Point(this.getPosition().x(), (height - 18) / 2));
	}
}
