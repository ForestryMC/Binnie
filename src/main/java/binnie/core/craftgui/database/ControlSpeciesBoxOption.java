package binnie.core.craftgui.database;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.controls.listbox.ControlList;
import binnie.core.craftgui.controls.listbox.ControlTextOption;
import binnie.core.craftgui.geometry.CraftGUIUtil;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.util.I18N;
import forestry.api.genetics.IAlleleSpecies;

class ControlSpeciesBoxOption extends ControlTextOption<IAlleleSpecies> {
    private ControlDatabaseIndividualDisplay controlBee;

    public ControlSpeciesBoxOption(ControlList<IAlleleSpecies> controlList, IAlleleSpecies option, int y) {
        super(controlList, option, option.getName(), y);
        setSize(new IPoint(getSize().x(), 20.0f));
        (controlBee = new ControlDatabaseIndividualDisplay(this, 2.0f, 2.0f))
                .setSpecies(getValue(), EnumDiscoveryState.UNDETERMINED);
        if (controlBee.discovered == EnumDiscoveryState.DISCOVERED) {
            controlBee.discovered = EnumDiscoveryState.SHOW;
        }

        if (controlBee.discovered == EnumDiscoveryState.SHOW) {
            textWidget.setValue(option.getName());
            addAttribute(WidgetAttribute.MOUSE_OVER);
        } else {
            textWidget.setValue(I18N.localise("binniecore.gui.database.discovered.undiscovered"));
        }

        CraftGUIUtil.moveWidget(textWidget, new IPoint(22.0f, 0.0f));
        textWidget.setSize(textWidget.getSize().sub(new IPoint(24.0f, 0.0f)));
        int th = (int) CraftGUI.render.textHeight(
                textWidget.getValue(), textWidget.getSize().x());
        int height = Math.max(20, th + 6);
        setSize(new IPoint(size().x(), height));
        textWidget.setSize(new IPoint(textWidget.getSize().x(), height));
        controlBee.setPosition(new IPoint(controlBee.pos().x(), (height - 18) / 2));
    }
}
