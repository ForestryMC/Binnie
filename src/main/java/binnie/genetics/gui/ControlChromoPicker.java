package binnie.genetics.gui;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.genetics.IChromosomeType;

import binnie.Binnie;
import binnie.core.ExtraBeeTexture;
import binnie.core.craftgui.Attribute;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.events.EventValueChanged;
import binnie.core.craftgui.events.EventWidget;
import binnie.core.craftgui.geometry.Point;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.StandardTexture;

public class ControlChromoPicker extends Control implements ITooltip {
	Texture Selected;
	Texture Texture;
	IChromosomeType type;
	ControlChromosome parent;

	public ControlChromoPicker(ControlChromosome parent, int x, int y, IChromosomeType chromo) {
		super(parent, x, y, 16, 16);
		Selected = new StandardTexture(160, 18, 16, 16, ExtraBeeTexture.GUIPunnett);
		Texture = new StandardTexture(160, 34, 16, 16, ExtraBeeTexture.GUIPunnett);
		type = chromo;
		addAttribute(Attribute.MouseOver);
		this.parent = parent;
		addSelfEventHandler(new EventWidget.StartMouseOver.Handler() {
			@Override
			public void onEvent(EventWidget.StartMouseOver event) {
				callEvent(new EventValueChanged<Object>(getWidget(), type));
			}
		});
		addSelfEventHandler(new EventWidget.EndMouseOver.Handler() {
			@Override
			public void onEvent(EventWidget.EndMouseOver event) {
				callEvent(new EventValueChanged<>(getWidget(), null));
			}
		});
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		super.onRenderBackground(guiWidth, guiHeight);
		boolean selected = isMouseOver();
		Texture text = selected ? Selected : Texture;
		CraftGUI.render.texture(text, Point.ZERO);
	}

	@Override
	public void getTooltip(Tooltip tooltip) {
		tooltip.add(Binnie.GENETICS.getSystem(parent.getRoot()).getChromosomeName(type));
	}
}
