package binnie.design.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.Constants;
import binnie.core.api.gui.IWidget;
import binnie.core.gui.Attribute;
import binnie.core.gui.CraftGUI;
import binnie.core.gui.ITooltip;
import binnie.core.gui.Tooltip;
import binnie.core.gui.controls.ControlText;
import binnie.core.gui.controls.core.Control;
import binnie.core.gui.controls.core.IControlValue;
import binnie.core.gui.controls.scroll.IControlScrollable;
import binnie.core.gui.events.EventMouse;
import binnie.core.gui.geometry.Point;
import binnie.core.gui.minecraft.Window;
import binnie.core.gui.renderer.RenderUtil;
import binnie.core.gui.resource.textures.CraftGUITexture;
import binnie.core.machines.TileEntityMachine;
import binnie.core.util.I18N;
import binnie.design.EnumDesign;
import binnie.design.api.DesignAPI;
import binnie.design.api.IDesign;
import binnie.design.api.IDesignCategory;

public class ControlTileSelect extends Control implements IControlValue<IDesign>, IControlScrollable {
	private IDesign value;
	private final float shownHeight;

	protected ControlTileSelect(IWidget parent, int x, int y) {
		super(parent, x, y, 102, 20 * (DesignAPI.manager.getSortedDesigns().size() / 4) + 22);
		this.value = EnumDesign.Blank;
		this.shownHeight = 92.0f;
		this.refresh("");
	}

	@Override
	public float getPercentageIndex() {
		return 0.0f;
	}

	@Override
	public void setPercentageIndex(float index) {
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
	public void setValue(IDesign value) {
		this.value = value;
	}

	@Override
	public void movePercentage(float percentage) {
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onUpdateClient() {
		super.onUpdateClient();
		TileEntityMachine tile = (TileEntityMachine) Window.get(this).getInventory();
		if (tile == null) {
			return;
		}
		ComponentDesignerRecipe recipe = tile.getMachine().getComponent(ComponentDesignerRecipe.class);
		this.setValue(recipe.getDesign());
	}

	public void refresh(String filterText) {
		this.deleteAllChildren();
		int cx = 2;
		int cy = 2;
		Map<IDesignCategory, List<IDesign>> designs = new HashMap<>();
		for (IDesignCategory category : DesignAPI.manager.getAllDesignCategories()) {
			designs.put(category, new ArrayList<>());
			for (IDesign tile : category.getDesigns()) {
				if (Objects.equals(filterText, "") || tile.getName().toLowerCase().contains(filterText)) {
					designs.get(category).add(tile);
				}
			}
			if (designs.get(category).isEmpty()) {
				designs.remove(category);
			}
		}
		for (IDesignCategory category : designs.keySet()) {
			cx = 2;
			new ControlText(this, new Point(cx, cy + 3), category.getName());
			cy += 16;
			for (IDesign tile : designs.get(category)) {
				if (cx > 90) {
					cx = 2;
					cy += 20;
				}
				new ControlTile(this, cx, cy, tile);
				cx += 20;
			}
			cy += 20;
		}
		int height = cy;
		this.setSize(new Point(this.getSize().xPos(), height));
	}

	@Override
	public float getMovementRange() {
		return 0.0f;
	}

	public static class ControlTile extends Control implements IControlValue<IDesign>, ITooltip {
		private IDesign value;

		protected ControlTile(IWidget parent, int x, int y, IDesign value) {
			super(parent, x, y, 18, 18);
			this.value = value;
			this.addAttribute(Attribute.MOUSE_OVER);
			this.addSelfEventHandler(EventMouse.Down.class, event -> {
				TileEntityMachine tile = (TileEntityMachine) Window.get(ControlTile.this.getWidget()).getInventory();
				if (tile == null) {
					return;
				}
				// TODO: why is recipe unused here?
				ComponentDesignerRecipe recipe = tile.getMachine().getComponent(ComponentDesignerRecipe.class);
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setShort("d", (short) DesignAPI.manager.getDesignIndex(ControlTile.this.getValue()));
				Window.get(ControlTile.this.getWidget()).sendClientAction("design", nbt);
			});
		}

		@Override
		public void getTooltip(Tooltip tooltip, ITooltipFlag tooltipFlag) {
			tooltip.add(I18N.localise(new ResourceLocation(Constants.DESIGN_MOD_ID, "gui.designer.pattern"), this.getValue().getName()));
		}

		@Override
		public IDesign getValue() {
			return this.value;
		}

		@Override
		public void setValue(IDesign value) {
			this.value = value;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void onRenderBackground(int guiWidth, int guiHeight) {
			CraftGUI.RENDER.texture(CraftGUITexture.SLOT, Point.ZERO);
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void onRenderForeground(int guiWidth, int guiHeight) {
			ItemStack image = ((WindowDesigner) this.getTopParent()).getDesignerType().getDisplayStack(this.getValue());
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
