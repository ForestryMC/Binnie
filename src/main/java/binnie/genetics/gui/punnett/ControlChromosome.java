package binnie.genetics.gui.punnett;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.genetics.IChromosomeType;

import binnie.core.craftgui.Attribute;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.controls.core.IControlValue;
import binnie.core.craftgui.renderer.RenderUtil;

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
