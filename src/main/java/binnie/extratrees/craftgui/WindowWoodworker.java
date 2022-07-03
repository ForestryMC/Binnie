package binnie.extratrees.craftgui;

import binnie.core.AbstractMod;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.controls.ControlTextEdit;
import binnie.core.craftgui.controls.scroll.ControlScrollableContent;
import binnie.core.craftgui.events.EventHandler;
import binnie.core.craftgui.events.EventTextEdit;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.IPoint;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.craftgui.minecraft.MinecraftGUI;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlErrorState;
import binnie.core.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.core.craftgui.minecraft.control.ControlSlot;
import binnie.core.craftgui.window.Panel;
import binnie.core.machines.Machine;
import binnie.core.util.I18N;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.machines.designer.Designer;
import binnie.extratrees.machines.designer.DesignerType;
import binnie.extratrees.machines.designer.WoodworkerRecipeComponent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public class WindowWoodworker extends Window {
    protected ControlTextEdit textEdit;
    protected ControlTileSelect tileSelect;

    public WindowWoodworker(EntityPlayer player, IInventory inventory, Side side) {
        super(320.0f, 216.0f, player, inventory, side);
        addEventHandler(
                new EventTextEdit.Handler() {
                    @Override
                    public void onEvent(EventTextEdit event) {
                        tileSelect.refresh(event.getValue());
                    }
                }.setOrigin(EventHandler.Origin.DirectChild, this));
    }

    public static Window create(EntityPlayer player, IInventory inventory, Side side) {
        return new WindowWoodworker(player, inventory, side);
    }

    @Override
    public void initialiseClient() {
        setTitle(Machine.getMachine(getInventory()).getPackage().getDisplayName());
        new ControlText(
                        this,
                        new IArea(190.0f, 36.0f, 114.0f, 10.0f),
                        I18N.localise("binniecore.gui.design"),
                        TextJustification.TOP_CENTER)
                .setColor(4473924);
        new Panel(this, 188.0f, 48.0f, 118.0f, 126.0f, MinecraftGUI.PanelType.Gray);
        textEdit = new ControlTextEdit(this, 188.0f, 178.0f, 118.0f, 12.0f);
        ControlScrollableContent scroll = new ControlScrollableContent(this, 190.0f, 50.0f, 114.0f, 122.0f, 12.0f);
        scroll.setScrollableContent(tileSelect = new ControlTileSelect(scroll, 0.0f, 0.0f));
        new ControlPlayerInventory(this).setPosition(new IPoint(14.0f, 96.0f));
        new ControlErrorState(this, 76.0f, 65.0f);

        if (getInventory() != null) {
            ControlSlot slotWood1 = new ControlSlot(this, 22.0f, 34.0f);
            slotWood1.assign(Designer.DESIGN_1_SLOT);
            ControlSlot slotWood2 = new ControlSlot(this, 62.0f, 34.0f);
            slotWood2.assign(Designer.DESIGN_2_SLOT);
            ControlSlot slotBeeswax = new ControlSlot(this, 42.0f, 64.0f);
            slotBeeswax.assign(Designer.GLUE_SLOT);
            new ControlRecipeSlot(this, 112, 34);
        }
    }

    @Override
    protected AbstractMod getMod() {
        return ExtraTrees.instance;
    }

    @Override
    protected String getName() {
        return "Woodworker";
    }

    public DesignerType getDesignerType() {
        return Machine.getInterface(WoodworkerRecipeComponent.class, getInventory())
                .getDesignerType();
    }
}
