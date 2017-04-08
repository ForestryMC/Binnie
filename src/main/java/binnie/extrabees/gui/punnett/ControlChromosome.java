package binnie.extrabees.gui.punnett;

import binnie.core.craftgui.Attribute;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.controls.core.IControlValue;
import binnie.core.craftgui.renderer.RenderUtil;
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
