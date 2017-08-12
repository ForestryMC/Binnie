package binnie.core.gui.database;

import java.util.LinkedHashMap;
import java.util.Map;

import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IClassification;

import binnie.core.gui.IWidget;
import binnie.core.gui.controls.ControlText;
import binnie.core.gui.controls.ControlTextCentered;

public class PageSpeciesClassification extends PageSpecies {
	private Map<IClassification.EnumClassLevel, ControlText> levels;
	private ControlText genus;

	public PageSpeciesClassification(final IWidget parent, final DatabaseTab tab) {
		super(parent, tab);
		this.levels = new LinkedHashMap<>();
		int y = 16;
		for (final IClassification.EnumClassLevel level : IClassification.EnumClassLevel.values()) {
			final ControlText text = new ControlTextCentered(this, y, "");
			text.setColor(level.getColour());
			this.levels.put(level, text);
			y += 12;
		}
		(this.genus = new ControlTextCentered(this, y, "")).setColor(16759415);
	}

	@Override
	public void onValueChanged(final IAlleleSpecies species) {
		if (species != null) {
			for (ControlText control : this.levels.values()) {
				control.setValue("- - -");
			}
			this.genus.setValue(species.getBinomial());
			for (IClassification classification = species.getBranch(); classification != null; classification = classification.getParent()) {
				IClassification.EnumClassLevel level = classification.getLevel();
				String text = "";
				text += classification.getScientific();
				this.levels.get(level).setValue(text);
			}
		}
	}
}
