package binnie.extrabees.gui.punnett;

import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.controls.core.IControlValue;
import forestry.api.genetics.IChromosomeType;

// TODO unused class?
public class ControlChromosome extends Control implements IControlValue<IChromosomeType>, ITooltip {
	protected IChromosomeType value;

	protected ControlChromosome(IWidget parent, float x, float y, IChromosomeType type) {
		super(parent, x, y, 16.0f, 16.0f);
		setValue(type);
		addAttribute(WidgetAttribute.MouseOver);
	}

	@Override
	public IChromosomeType getValue() {
		return value;
	}

	@Override
	public void setValue(IChromosomeType value) {
		this.value = value;
	}

	@Override
	public void onRenderBackground() {
		CraftGUI.Render.texture(ExtraBeeGUITexture.Chromosome, getArea());
		CraftGUI.Render.colour(0xff0000);
		CraftGUI.Render.texture(ExtraBeeGUITexture.Chromosome2, getArea());
	}

	@Override
	public void getTooltip(Tooltip tooltip) {
		if (value != null) {
			tooltip.add(value.getName());
		}
	}
}
