package binnie.core.gui.fieldkit;

import javax.annotation.Nullable;

import binnie.core.Binnie;
import binnie.core.gui.Attribute;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.ITooltip;
import binnie.core.gui.Tooltip;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.events.EventValueChanged;
import binnie.core.gui.events.EventWidget;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.resource.textures.Texture;
import binnie.core.gui.resource.textures.StandardTexture;
import binnie.core.texture.BinnieCoreTexture;
import forestry.api.genetics.IChromosomeType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ControlChromoPicker extends Control implements ITooltip {
	private final Texture selected;
	private final Texture texture;
	private final IChromosomeType type;
	private final ControlChromosome parent;

	public ControlChromoPicker(ControlChromosome parent, int x, int y, IChromosomeType chromo) {
		super(parent, x, y, 16, 16);
		selected = new StandardTexture(160, 18, 16, 16, BinnieCoreTexture.GUI_PUNNETT);
		texture = new StandardTexture(160, 34, 16, 16, BinnieCoreTexture.GUI_PUNNETT);
		type = chromo;
		addAttribute(Attribute.MOUSE_OVER);
		this.parent = parent;
		addSelfEventHandler(EventWidget.StartMouseOver.class, event -> {
			callEvent(new EventValueChanged<Object>(getWidget(), type));
		});
		addSelfEventHandler(EventWidget.EndMouseOver.class, event -> {
			callEvent(new EventValueChanged<>(getWidget(), null));
		});
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		super.onRenderBackground(guiWidth, guiHeight);
		boolean selected = isMouseOver();
		Texture text = selected ? this.selected : texture;
		CraftGUI.RENDER.texture(text, Point.ZERO);
	}

	@Override
	public void getTooltip(Tooltip tooltip, ITooltipFlag tooltipFlag) {
		tooltip.add(Binnie.GENETICS.getSystem(parent.getRoot()).getChromosomeName(type));
	}

	@Nullable
	@Override
	public ControlChromosome getParent() {
		return parent;
	}
}
