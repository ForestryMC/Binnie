package binnie.extrabees.gui.punnett;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.controls.core.IControlValue;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.ITooltip;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.Tooltip;
import binnie.craftgui.core.renderer.RenderUtil;
import forestry.api.genetics.IChromosomeType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ControlChromosome extends Control implements IControlValue<IChromosomeType>, ITooltip {
	private IChromosomeType value;

	protected ControlChromosome(final IWidget parent, final int x, final int y, final IChromosomeType type) {
		super(parent, x, y, 16, 16);
		this.value = type;
		this.addAttribute(Attribute.MouseOver);
	}

	@Override
	public IChromosomeType getValue() {
		return this.value;
	}

	@Override
	public void setValue(final IChromosomeType value) {
		this.value = value;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		CraftGUI.render.texture(ExtraBeeGUITexture.Chromosome, this.getArea());
		RenderUtil.setColour(16711680);
		CraftGUI.render.texture(ExtraBeeGUITexture.Chromosome2, this.getArea());
	}

	@Override
	public void getTooltip(final Tooltip tooltip) {
		tooltip.add(this.value.getName());
	}
}
