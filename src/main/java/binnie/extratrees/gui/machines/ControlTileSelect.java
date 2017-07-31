package binnie.extratrees.gui.machines;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.gui.Attribute;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.ITooltip;
import binnie.core.gui.IWidget;
import binnie.core.gui.Tooltip;
import binnie.core.gui.controls.ControlText;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.controls.core.IControlValue;
import binnie.core.gui.controls.scroll.IControlScrollable;
import binnie.core.gui.events.EventMouse;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.minecraft.Window;
import binnie.core.gui.renderer.RenderUtil;
import binnie.core.gui.resource.minecraft.CraftGUITexture;
import binnie.core.machines.TileEntityMachine;
import binnie.core.util.I18N;
import binnie.extratrees.api.CarpentryManager;
import binnie.extratrees.api.IDesign;
import binnie.extratrees.api.IDesignCategory;
import binnie.extratrees.carpentry.EnumDesign;
import binnie.extratrees.machines.designer.ComponentDesignerRecipe;

public class ControlTileSelect extends Control implements IControlValue<IDesign>, IControlScrollable {
	IDesign value;
	float shownHeight;

	protected ControlTileSelect(final IWidget parent, final int x, final int y) {
		super(parent, x, y, 102, 20 * (CarpentryManager.carpentryInterface.getSortedDesigns().size() / 4) + 22);
		this.value = EnumDesign.Blank;
		this.shownHeight = 92.0f;
		this.refresh("");
	}

	@Override
	public float getPercentageIndex() {
		return 0.0f;
	}

	@Override
	public void setPercentageIndex(final float index) {
	}

	@Override
	public float getPercentageShown() {
		return 0.0f;
	}

	@Override
	public IDesign getValue() {
		return this.value;
	}

	@Override
	public void setValue(final IDesign value) {
		this.value = value;
	}

	@Override
	public void movePercentage(final float percentage) {
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onUpdateClient() {
		super.onUpdateClient();
		final TileEntityMachine tile = (TileEntityMachine) Window.get(this).getInventory();
		if (tile == null) {
			return;
		}
		ComponentDesignerRecipe recipe = tile.getMachine().getComponent(ComponentDesignerRecipe.class);
		this.setValue(recipe.getDesign());
	}

	public void refresh(final String filterText) {
		this.deleteAllChildren();
		int cx = 2;
		int cy = 2;
		final Map<IDesignCategory, List<IDesign>> designs = new HashMap<>();
		for (final IDesignCategory category : CarpentryManager.carpentryInterface.getAllDesignCategories()) {
			designs.put(category, new ArrayList<>());
			for (final IDesign tile : category.getDesigns()) {
				if (Objects.equals(filterText, "") || tile.getName().toLowerCase().contains(filterText)) {
					designs.get(category).add(tile);
				}
			}
			if (designs.get(category).isEmpty()) {
				designs.remove(category);
			}
		}
		for (final IDesignCategory category : designs.keySet()) {
			cx = 2;
			new ControlText(this, new Point(cx, cy + 3), category.getName());
			cy += 16;
			for (final IDesign tile : designs.get(category)) {
				if (cx > 90) {
					cx = 2;
					cy += 20;
				}
				new ControlTile(this, cx, cy, tile);
				cx += 20;
			}
			cy += 20;
		}
		final int height = cy;
		this.setSize(new Point(this.getSize().x(), height));
	}

	@Override
	public float getMovementRange() {
		return 0.0f;
	}

	public static class ControlTile extends Control implements IControlValue<IDesign>, ITooltip {
		private IDesign value;

		protected ControlTile(final IWidget parent, final int x, final int y, final IDesign value) {
			super(parent, x, y, 18, 18);
			this.value = value;
			this.addAttribute(Attribute.MouseOver);
			this.addSelfEventHandler(new EventMouse.Down.Handler() {
				@Override
				public void onEvent(final EventMouse.Down event) {
					final TileEntityMachine tile = (TileEntityMachine) Window.get(ControlTile.this.getWidget()).getInventory();
					if (tile == null) {
						return;
					}
					// TODO: why is recipe unused here?
					ComponentDesignerRecipe recipe = tile.getMachine().getComponent(ComponentDesignerRecipe.class);
					final NBTTagCompound nbt = new NBTTagCompound();
					nbt.setShort("d", (short) CarpentryManager.carpentryInterface.getDesignIndex(ControlTile.this.getValue()));
					Window.get(ControlTile.this.getWidget()).sendClientAction("design", nbt);
				}
			});
		}

		@Override
		public void getTooltip(final Tooltip tooltip) {
			tooltip.add(I18N.localise("binniecore.gui.designer.pattern", this.getValue().getName()));
		}

		@Override
		public IDesign getValue() {
			return this.value;
		}

		@Override
		public void setValue(final IDesign value) {
			this.value = value;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void onRenderBackground(int guiWidth, int guiHeight) {
			CraftGUI.render.texture(CraftGUITexture.Slot, Point.ZERO);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void onRenderForeground(int guiWidth, int guiHeight) {
			final ItemStack image = ((WindowDesigner) this.getTopParent()).getDesignerType().getDisplayStack(this.getValue());
			RenderUtil.drawItem(new Point(1, 1), image);
			GlStateManager.disableBlend();
			if (((IControlValue) this.getParent()).getValue() != this.getValue()) {
				if (Window.get(this).getMousedOverWidget() == this) {
					RenderUtil.drawGradientRect(this.getArea().inset(1), 1157627903, 1157627903);
				} else {
					RenderUtil.drawGradientRect(this.getArea().inset(1), -1433892728, -1433892728);
				}
			}
			GlStateManager.enableBlend();
		}
	}
}
