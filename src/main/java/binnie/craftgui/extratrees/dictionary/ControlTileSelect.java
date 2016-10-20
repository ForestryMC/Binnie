package binnie.craftgui.extratrees.dictionary;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.machines.TileEntityMachine;
import binnie.craftgui.controls.ControlText;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.controls.core.IControlValue;
import binnie.craftgui.controls.scroll.IControlScrollable;
import binnie.craftgui.core.*;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.events.EventMouse;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.resource.minecraft.CraftGUITexture;
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
    IDesign value;
    float shownHeight;

    protected ControlTileSelect(final IWidget parent, final float x, final float y) {
        super(parent, x, y, 102.0f, 20 * (CarpentryManager.carpentryInterface.getSortedDesigns().size() / 4) + 22);
        this.value = EnumDesign.Blank;
        this.shownHeight = 92.0f;
        this.refresh("");
    }

    @Override
    public float getPercentageIndex() {
        return 0.0f;
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
    public void movePercentage(final float percentage) {
    }

    @Override
    public void onUpdateClient() {
        super.onUpdateClient();
        final TileEntityMachine tile = (TileEntityMachine) Window.get(this).getInventory();
        if (tile == null) {
            return;
        }
        final Designer.ComponentWoodworkerRecipe recipe = tile.getMachine().getComponent(Designer.ComponentWoodworkerRecipe.class);
        this.setValue(recipe.getDesign());
    }

    public void refresh(final String filterText) {
        this.deleteAllChildren();
        int cx = 2;
        int cy = 2;
        final Map<IDesignCategory, List<IDesign>> designs = new HashMap<IDesignCategory, List<IDesign>>();
        for (final IDesignCategory category : CarpentryManager.carpentryInterface.getAllDesignCategories()) {
            designs.put(category, new ArrayList<IDesign>());
            for (final IDesign tile : category.getDesigns()) {
                if (filterText == "" || tile.getName().toLowerCase().contains(filterText)) {
                    designs.get(category).add(tile);
                }
            }
            if (designs.get(category).isEmpty()) {
                designs.remove(category);
            }
        }
        for (final IDesignCategory category : designs.keySet()) {
            cx = 2;
            new ControlText(this, new IPoint(cx, cy + 3), category.getName());
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
        this.setSize(new IPoint(this.getSize().x(), height));
    }

    @Override
    public void setPercentageIndex(final float index) {
    }

    @Override
    public void setValue(final IDesign value) {
        this.value = value;
    }

    @Override
    public float getMovementRange() {
        return 0.0f;
    }

    public static class ControlTile extends Control implements IControlValue<IDesign>, ITooltip {
        IDesign value;

        protected ControlTile(final IWidget parent, final float x, final float y, final IDesign value) {
            super(parent, x, y, 18.0f, 18.0f);
            this.setValue(value);
            this.addAttribute(Attribute.MouseOver);
            this.addSelfEventHandler(new EventMouse.Down.Handler() {
                @Override
                public void onEvent(final EventMouse.Down event) {
                    final TileEntityMachine tile = (TileEntityMachine) Window.get(ControlTile.this.getWidget()).getInventory();
                    if (tile == null) {
                        return;
                    }
                    final Designer.ComponentWoodworkerRecipe recipe = tile.getMachine().getComponent(Designer.ComponentWoodworkerRecipe.class);
                    final NBTTagCompound nbt = new NBTTagCompound();
                    nbt.setShort("d", (short) CarpentryManager.carpentryInterface.getDesignIndex(ControlTile.this.getValue()));
                    Window.get(ControlTile.this.getWidget()).sendClientAction("design", nbt);
                }
            });
        }

        @Override
        public void getTooltip(final Tooltip tooltip) {
            tooltip.add(Binnie.Language.localise(BinnieCore.instance, "gui.designer.pattern", this.getValue().getName()));
        }

        @Override
        public IDesign getValue() {
            return this.value;
        }

        @Override
        public void onRenderBackground() {
            CraftGUI.Render.texture(CraftGUITexture.Slot, IPoint.ZERO);
        }

        @Override
        public void onRenderForeground() {
            final ItemStack image = ((WindowWoodworker) this.getSuperParent()).getDesignerType().getDisplayStack(this.getValue());
            CraftGUI.Render.item(new IPoint(1.0f, 1.0f), image);
            if (((IControlValue) this.getParent()).getValue() != this.getValue()) {
                if (Window.get(this).getMousedOverWidget() == this) {
                    CraftGUI.Render.gradientRect(this.getArea().inset(1), 1157627903, 1157627903);
                } else {
                    CraftGUI.Render.gradientRect(this.getArea().inset(1), -1433892728, -1433892728);
                }
            }
        }

        @Override
        public void setValue(final IDesign value) {
            this.value = value;
        }
    }
}
