package binnie.craftgui.mod.database;

import binnie.craftgui.controls.listbox.ControlList;
import binnie.craftgui.controls.listbox.ControlTextOption;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.geometry.CraftGUIUtil;
import binnie.craftgui.core.geometry.IPoint;
import forestry.api.genetics.IAlleleSpecies;

class ControlSpeciexBoxOption extends ControlTextOption<IAlleleSpecies> {
    private ControlDatabaseIndividualDisplay controlBee;

    public ControlSpeciexBoxOption(final ControlList<IAlleleSpecies> controlList, final IAlleleSpecies option, final int y) {
        super(controlList, option, option.getName(), y);
        this.setSize(new IPoint(this.getSize().x(), 20.0f));
        (this.controlBee = new ControlDatabaseIndividualDisplay(this, 2.0f, 2.0f)).setSpecies(this.getValue(), EnumDiscoveryState.Undetermined);
        if (this.controlBee.discovered == EnumDiscoveryState.Discovered) {
            this.controlBee.discovered = EnumDiscoveryState.Show;
        }
        this.textWidget.setValue((this.controlBee.discovered == EnumDiscoveryState.Show) ? option.getName() : "Undiscovered");
        if (this.controlBee.discovered == EnumDiscoveryState.Show) {
            this.addAttribute(Attribute.MouseOver);
        }
        CraftGUIUtil.moveWidget(this.textWidget, new IPoint(22.0f, 0.0f));
        this.textWidget.setSize(this.textWidget.getSize().sub(new IPoint(24.0f, 0.0f)));
        final int th = (int) CraftGUI.Render.textHeight(this.textWidget.getValue(), this.textWidget.getSize().x());
        final int height = Math.max(20, th + 6);
        this.setSize(new IPoint(this.size().x(), height));
        this.textWidget.setSize(new IPoint(this.textWidget.getSize().x(), height));
        this.controlBee.setPosition(new IPoint(this.controlBee.pos().x(), (height - 18) / 2));
    }
}
