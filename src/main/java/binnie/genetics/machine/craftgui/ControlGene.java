package binnie.genetics.machine.craftgui;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.Binnie;
import binnie.core.genetics.BreedingSystem;
import binnie.core.gui.Attribute;
import binnie.core.gui.ITooltip;
import binnie.core.gui.IWidget;
import binnie.core.gui.Tooltip;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.controls.core.IControlValue;
import binnie.core.gui.events.EventMouse;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.minecraft.Window;
import binnie.core.gui.renderer.RenderUtil;
import binnie.genetics.api.IGene;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.genetics.Engineering;

public class ControlGene extends Control implements IControlValue<IGene>, ITooltip {
	private IGene gene;

	protected ControlGene(final IWidget parent, final int x, final int y, final IGene gene) {
		super(parent, x, y, 16, 16);
		this.gene = gene;
		this.addAttribute(Attribute.MouseOver);
		this.addSelfEventHandler(new EventMouse.Down.Handler() {
			@Override
			public void onEvent(final EventMouse.Down event) {
				if (ControlGene.this.canFill(Window.get(ControlGene.this.getWidget()).getHeldItemStack())) {
					final NBTTagCompound action = new NBTTagCompound();
					final NBTTagCompound geneNBT = new NBTTagCompound();
					ControlGene.this.getValue().writeToNBT(geneNBT);
					action.setTag("gene", geneNBT);
					Window.get(ControlGene.this.getWidget()).sendClientAction("gene-select", action);
				}
			}
		});
	}

	@Override
	public void getTooltip(final Tooltip tooltip) {
		BreedingSystem system = Binnie.GENETICS.getSystem(this.gene.getSpeciesRoot());
		final String cName = system.getChromosomeName(this.gene.getChromosome());
		tooltip.add(cName + ": " + this.gene.getName());
		if (this.isMouseOver() && this.canFill(Window.get(this).getHeldItemStack())) {
			tooltip.add("Left click to assign gene");
			final IGene existingGene = Engineering.getGene(Window.get(this).getHeldItemStack(), this.gene.getChromosome().ordinal());
			if (existingGene == null) {
				return;
			}
			final String dName = system.getChromosomeName(this.gene.getChromosome());
			tooltip.add("Will replace " + dName + ": " + existingGene.getName());
		}
	}

	private boolean canFill(final ItemStack stack) {
		return !stack.isEmpty() && stack.getCount() == 1 && Engineering.isGeneAcceptor(stack) && Engineering.canAcceptGene(stack, this.getValue());
	}

	@Override
	public IGene getValue() {
		return this.gene;
	}

	@Override
	public void setValue(final IGene value) {
		this.gene = value;
	}

	@Override
	public void onRenderForeground(int guiWidth, int guiHeight) {
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onRenderBackground(int guiWidth, int guiHeight) {
		if (this.isMouseOver() && this.canFill(Window.get(this).getHeldItemStack())) {
			RenderUtil.drawSolidRect(this.getArea(), -1);
			RenderUtil.drawSolidRect(this.getArea().inset(1), -12303292);
		}
		RenderUtil.setColour(-1);
		RenderUtil.drawSprite(Point.ZERO, GeneticsTexture.dnaIcon.getSprite());
	}
}
