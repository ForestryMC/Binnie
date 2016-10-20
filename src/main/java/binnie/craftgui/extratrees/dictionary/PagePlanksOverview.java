package binnie.craftgui.extratrees.dictionary;

import binnie.core.BinnieCore;
import binnie.craftgui.controls.ControlText;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.geometry.TextJustification;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.minecraft.control.ControlItemDisplay;
import binnie.craftgui.mod.database.DatabaseTab;
import binnie.craftgui.mod.database.PageAbstract;
import binnie.craftgui.mod.database.WindowAbstractDatabase;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.DoorType;
import binnie.extratrees.block.IPlankType;
import binnie.extratrees.block.WoodManager;
import binnie.extratrees.block.decor.FenceType;
import net.minecraft.item.ItemStack;

public class PagePlanksOverview extends PageAbstract<ItemStack> {
    public PagePlanksOverview(final IWidget parent, final DatabaseTab tab) {
        super(parent, tab);
    }

    @Override
    public void onValueChanged(final ItemStack species) {
        this.deleteAllChildren();
        final WindowAbstractDatabase database = Window.get(this);
        new ControlText(this, new IArea(0.0f, 0.0f, this.size().x(), 24.0f), species.getDisplayName(), TextJustification.MiddleCenter);
        new ControlText(this, new IArea(12.0f, 24.0f, this.size().x() - 24.0f, 24.0f), ExtraTrees.proxy.localise("gui.database.planks.use"), TextJustification.MiddleLeft);
        final IPlankType type = WoodManager.get(species);
        int x = 12;
        if (type != null) {
            final ItemStack fence = WoodManager.getFence(type, new FenceType(0), 1);
            final ItemStack gate = WoodManager.getGate(type);
            final ItemStack door = WoodManager.getDoor(type, DoorType.Standard);
            if (fence != null) {
                new ControlItemDisplay(this, x, 48.0f).setItemStack(fence);
                x += 22;
            }
            if (gate != null) {
                new ControlItemDisplay(this, x, 48.0f).setItemStack(gate);
                x += 22;
            }
            if (door != null) {
                new ControlItemDisplay(this, x, 48.0f).setItemStack(door);
                x += 22;
            }
        }
        final ControlText controlDescription = new ControlText(this, new IArea(8.0f, 84.0f, this.getSize().x() - 16.0f, 0.0f), "", TextJustification.MiddleCenter);
        final ControlText controlSignature = new ControlText(this, new IArea(8.0f, 84.0f, this.getSize().x() - 16.0f, 0.0f), "", TextJustification.BottomRight);
        String desc = "";
        if (type != null) {
            desc = type.getDescription();
        }
        String descBody = "§o";
        String descSig = "";
        if (desc == null || desc.length() == 0) {
            descBody += BinnieCore.proxy.localise("gui.database.nodescription");
        } else {
            final String[] descStrings = desc.split("\\|");
            descBody += descStrings[0];
            for (int i = 1; i < descStrings.length - 1; ++i) {
                descBody = descBody + " " + descStrings[i];
            }
            if (descStrings.length > 1) {
                descSig += descStrings[descStrings.length - 1];
            }
        }
        controlDescription.setValue(descBody + "§r");
        controlSignature.setValue(descSig + "§r");
        final float descHeight = CraftGUI.Render.textHeight(controlDescription.getValue(), controlDescription.getSize().x());
        controlSignature.setPosition(new IPoint(controlSignature.pos().x(), controlDescription.getPosition().y() + descHeight + 10.0f));
    }
}
