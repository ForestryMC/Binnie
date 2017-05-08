package binnie.extratrees.craftgui.dictionary;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.craftgui.CraftGUI;
import binnie.core.craftgui.ITooltip;
import binnie.core.craftgui.IWidget;
import binnie.core.craftgui.Tooltip;
import binnie.core.craftgui.WidgetAttribute;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.core.Control;
import binnie.core.craftgui.controls.core.IControlValue;
import binnie.core.craftgui.controls.scroll.IControlScrollable;
import binnie.core.craftgui.events.EventMouse;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.resource.minecraft.CraftGUITexture;
import binnie.core.machines.TileEntityMachine;
import binnie.extratrees.api.CarpentryManager;
import binnie.extratrees.api.IDesign;
import binnie.extratrees.api.IDesignCategory;
import binnie.extratrees.carpentry.EnumDesign;
import binnie.extratrees.machines.Designer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControlTileSelect extends Control implements IControlValue<IDesign>, IControlScrollable {
	protected IDesign value;
	protected float shownHeight;

	protected ControlTileSelect(IWidget parent, float x, float y) {
		super(parent, x, y, 102.0f, 20 * (CarpentryManager.carpentryInterface.getSortedDesigns().size() / 4) + 22);
		value = EnumDesign.Blank;
		shownHeight = 92.0f;
		refresh("");
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
		return value;
	}

	@Override
	public void setValue(IDesign value) {
		this.value = value;
	}

	@Override
	public void movePercentage(float percentage) {
	}

	@Override
	public void onUpdateClient() {
		super.onUpdateClient();
		TileEntityMachine tile = (TileEntityMachine) Window.get(this).getInventory();
		if (tile == null) {
			return;
		}

		Designer.ComponentWoodworkerRecipe recipe = tile.getMachine().getComponent(Designer.ComponentWoodworkerRecipe.class);
		setValue(recipe.getDesign());
	}

	public void refresh(String filterText) {
		deleteAllChildren();
		int cx;
		int cy = 2;
		Map<IDesignCategory, List<IDesign>> designs = new HashMap<IDesignCategory, List<IDesign>>();
		for (IDesignCategory category : CarpentryManager.carpentryInterface.getAllDesignCategories()) {
			designs.put(category, new ArrayList<>());
			for (IDesign tile : category.getDesigns()) {
				if (filterText == "" || tile.getName().toLowerCase().contains(filterText)) {
					designs.get(category).add(tile);
				}
			}
			if (designs.get(category).isEmpty()) {
				designs.remove(category);
			}
		}

		for (IDesignCategory category : designs.keySet()) {
			cx = 2;
			new ControlText(this, new IPoint(cx, cy + 3), category.getName());
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
		setSize(new IPoint(getSize().x(), height));
	}

	@Override
	public float getMovementRange() {
		return 0.0f;
	}

	public static class ControlTile extends Control implements IControlValue<IDesign>, ITooltip {
		IDesign value;

		protected ControlTile(IWidget parent, float x, float y, IDesign value) {
			super(parent, x, y, 18.0f, 18.0f);
			setValue(value);
			addAttribute(WidgetAttribute.MouseOver);
			addSelfEventHandler(new EventMouse.Down.Handler() {
				@Override
				public void onEvent(EventMouse.Down event) {
					TileEntityMachine tile = (TileEntityMachine) Window.get(getWidget()).getInventory();
					if (tile == null) {
						return;
					}
					Designer.ComponentWoodworkerRecipe recipe = tile.getMachine().getComponent(Designer.ComponentWoodworkerRecipe.class);
					NBTTagCompound nbt = new NBTTagCompound();
					nbt.setShort("d", (short) CarpentryManager.carpentryInterface.getDesignIndex(getValue()));
					Window.get(getWidget()).sendClientAction("design", nbt);
				}
			});
		}

		@Override
		public void getTooltip(Tooltip tooltip) {
			tooltip.add(Binnie.I18N.localise(BinnieCore.instance, "gui.designer.pattern", getValue().getName()));
		}

		@Override
		public IDesign getValue() {
			return value;
		}

		@Override
		public void setValue(IDesign value) {
			this.value = value;
		}

		@Override
		public void onRenderBackground() {
			CraftGUI.Render.texture(CraftGUITexture.Slot, IPoint.ZERO);
		}

		@Override
		public void onRenderForeground() {
			ItemStack image = ((WindowWoodworker) getSuperParent()).getDesignerType().getDisplayStack(getValue());
			CraftGUI.Render.item(new IPoint(1.0f, 1.0f), image);
			if (((IControlValue) getParent()).getValue() == getValue()) {
				return;
			}

			if (Window.get(this).getMousedOverWidget() == this) {
				CraftGUI.Render.gradientRect(getArea().inset(1), 1157627903, 1157627903);
			} else {
				CraftGUI.Render.gradientRect(getArea().inset(1), -1433892728, -1433892728);
			}
		}
	}
}
