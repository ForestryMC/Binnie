package binnie.genetics.gui.punnett;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.genetics.IChromosomeType;

import binnie.core.gui.Attribute;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.ITooltip;
import binnie.core.gui.IWidget;
import binnie.core.gui.Tooltip;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.controls.core.IControlValue;
import binnie.core.gui.renderer.RenderUtil;

public class ControlChromosome extends Control implements IControlValue<IChromosomeType>, ITooltip {
	private IChromosomeType value;

	protected ControlChromosome(IWidget parent, int x, int y, IChromosomeType type) {
		super(parent, x, y, 16, 16);
		value = type;
		addAttribute(Attribute.MouseOver);
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
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		CraftGUI.render.texture(ExtraBeeGUITexture.Chromosome, getArea());
		RenderUtil.setColour(16711680);
		CraftGUI.render.texture(ExtraBeeGUITexture.Chromosome2, getArea());
	}

	@Override
	public void getTooltip(Tooltip tooltip) {
		tooltip.add(value.getName());
	}
}
