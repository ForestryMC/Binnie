package binnie.genetics.craftgui;

import binnie.Binnie;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.controls.core.IControlValue;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.minecraft.Window;
import binnie.genetics.api.IGene;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.genetics.Engineering;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ControlGene extends Control implements IControlValue<IGene>, ITooltip {
	protected IGene gene;

	protected ControlGene(IWidget parent, float x, float y) {
		super(parent, x, y, 16.0f, 16.0f);
		addAttribute(WidgetAttribute.MOUSE_OVER);
		addSelfEventHandler(new MouseDownHandler());
	}

	@Override
	public void getTooltip(Tooltip tooltip) {
		String cName = Binnie.Genetics.getSystem(gene.getSpeciesRoot()).getChromosomeName(gene.getChromosome());
		tooltip.add(cName + ": " + gene.getName());
		if (isMouseOver() && canFill(Window.get(this).getHeldItemStack())) {
			tooltip.add("Left click to assign gene");
			IGene existingGene = Engineering.getGene(Window.get(this).getHeldItemStack(), gene.getChromosome().ordinal());
			if (existingGene == null) {
				return;
			}

			String dName = Binnie.Genetics.getSystem(gene.getSpeciesRoot()).getChromosomeName(gene.getChromosome());
			tooltip.add("Will replace " + dName + ": " + existingGene.getName());
		}
	}

	private boolean canFill(ItemStack stack) {
		return stack != null
			&& stack.stackSize == 1
			&& Engineering.isGeneAcceptor(stack)
			&& Engineering.canAcceptGene(stack, getValue());
	}

	@Override
	public IGene getValue() {
		return gene;
	}

	@Override
	public void setValue(IGene value) {
		gene = value;
	}

	@Override
	public void onRenderForeground() {
		// ignored
	}

	@Override
	public void onRenderBackground() {
		if (isMouseOver() && canFill(Window.get(this).getHeldItemStack())) {
			CraftGUI.Render.solid(getArea(), 0xffffffff);
			CraftGUI.Render.solid(getArea().inset(1), 0xff444444);
		}

		CraftGUI.Render.color(0xffffffff);
		CraftGUI.Render.iconItem(IPoint.ZERO, GeneticsTexture.dnaIcon.getIcon());
	}

	private class MouseDownHandler extends EventMouse.Down.Handler {
		@Override
		public void onEvent(EventMouse.Down event) {
			if (!canFill(Window.get(getWidget()).getHeldItemStack())) {
				return;
			}

			NBTTagCompound action = new NBTTagCompound();
			NBTTagCompound geneNBT = new NBTTagCompound();
			getValue().writeToNBT(geneNBT);
			action.setTag("gene", geneNBT);
			Window.get(getWidget()).sendClientAction("gene-select", action);
		}
	}
}
