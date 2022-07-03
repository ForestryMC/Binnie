package binnie.core.craftgui.database;

import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextCentered;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IClassification;
import java.util.LinkedHashMap;
import java.util.Map;

public class PageSpeciesClassification extends PageSpecies {
    private Map<IClassification.EnumClassLevel, ControlText> levels;
    private ControlText genus;

    public PageSpeciesClassification(IWidget parent, DatabaseTab tab) {
        super(parent, tab);
        levels = new LinkedHashMap<>();
        int y = 16;
        for (IClassification.EnumClassLevel level : IClassification.EnumClassLevel.values()) {
            ControlText text = new ControlTextCentered(this, y, "");
            text.setColor(level.getColour());
            levels.put(level, text);
            y += 12;
        }

        genus = new ControlTextCentered(this, y, "");
        genus.setColor(0xffba77);
    }

    @Override
    public void onValueChanged(IAlleleSpecies species) {
        if (species == null) {
            return;
        }

        for (ControlText text : levels.values()) {
            text.setValue("- - -");
        }

        genus.setValue(species.getBinomial());
        for (IClassification classification = species.getBranch();
                classification != null;
                classification = classification.getParent()) {
            IClassification.EnumClassLevel level = classification.getLevel();
            String text2 = "";
            text2 += classification.getScientific();
            levels.get(level).setValue(text2);
        }
    }
}
