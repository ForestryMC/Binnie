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

	public ControlChromoPicker(final ControlChromosome parent, final int x, final int y, final IChromosomeType chromo) {
		super(parent, x, y, 16, 16);
		this.Selected = new StandardTexture(160, 18, 16, 16, ExtraBeeTexture.GUIPunnett);
		this.Texture = new StandardTexture(160, 34, 16, 16, ExtraBeeTexture.GUIPunnett);
		this.type = chromo;
		this.addAttribute(Attribute.MouseOver);
		this.parent = parent;
		this.addSelfEventHandler(new EventWidget.StartMouseOver.Handler() {
			@Override
			public void onEvent(final EventWidget.StartMouseOver event) {
				ControlChromoPicker.this.callEvent(new EventValueChanged<Object>(ControlChromoPicker.this.getWidget(), ControlChromoPicker.this.type));
			}
		});
		this.addSelfEventHandler(new EventWidget.EndMouseOver.Handler() {
			@Override
			public void onEvent(final EventWidget.EndMouseOver event) {
				ControlChromoPicker.this.callEvent(new EventValueChanged<>(ControlChromoPicker.this.getWidget(), null));
			}
		});
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		super.onRenderBackground(guiWidth, guiHeight);
		final boolean selected = this.isMouseOver();
		final Texture text = selected ? this.Selected : this.Texture;
		CraftGUI.render.texture(text, Point.ZERO);
	}

	@Override
	public void getTooltip(final Tooltip tooltip) {
		tooltip.add(Binnie.GENETICS.getSystem(this.parent.getRoot()).getChromosomeName(this.type));
	}
}
