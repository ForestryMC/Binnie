package binnie.craftgui.mod.database;

import binnie.craftgui.controls.ControlText;
import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.core.IWidget;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IClassification;

import java.util.LinkedHashMap;
import java.util.Map;

public class PageSpeciesClassification extends PageSpecies {
    private Map<IClassification.EnumClassLevel, ControlText> levels;
    private ControlText genus;

    public PageSpeciesClassification(final IWidget parent, final DatabaseTab tab) {
        super(parent, tab);
        this.levels = new LinkedHashMap<IClassification.EnumClassLevel, ControlText>();
        int y = 16;
        for (final IClassification.EnumClassLevel level : IClassification.EnumClassLevel.values()) {
            final ControlText text = new ControlTextCentered(this, y, "");
            text.setColour(level.getColour());
            this.levels.put(level, text);
            y += 12;
        }
        (this.genus = new ControlTextCentered(this, y, "")).setColour(16759415);
    }

    @Override
    public void onValueChanged(final IAlleleSpecies species) {
        if (species != null) {
            for (final ControlText text : this.levels.values()) {
                text.setValue("- - -");
            }
            this.genus.setValue(species.getBinomial());
            for (IClassification classification = species.getBranch(); classification != null; classification = classification.getParent()) {
                final IClassification.EnumClassLevel level = classification.getLevel();
                String text2 = "";
                final int n = level.ordinal();
                text2 += classification.getScientific();
                this.levels.get(level).setValue(text2);
            }
        }
    }
}
