package binnie.genetics.craftgui;

import binnie.core.AbstractMod;
import binnie.core.craftgui.geometry.CraftGUIUtil;
import binnie.core.craftgui.geometry.Position;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.craftgui.minecraft.GUIIcon;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlEnergyBar;
import binnie.core.craftgui.minecraft.control.ControlErrorState;
import binnie.core.craftgui.minecraft.control.ControlIconDisplay;
import binnie.core.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.core.craftgui.minecraft.control.ControlSlot;
import binnie.core.craftgui.minecraft.control.ControlSlotArray;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.StandardTexture;
import binnie.core.util.I18N;
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.machine.inoculator.Inoculator;
import binnie.genetics.machine.splicer.Splicer;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public class WindowSplicer extends WindowMachine {
    protected static Texture progressBase =
            new StandardTexture(0, 72, 142, 72, GeneticsTexture.GUIProcess2.getTexture());
    protected static Texture progress = new StandardTexture(0, 0, 142, 72, GeneticsTexture.GUIProcess2.getTexture());

    public WindowSplicer(EntityPlayer player, IInventory inventory, Side side) {
        super(280, 240, player, inventory, side);
    }

    public static Window create(EntityPlayer player, IInventory inventory, Side side) {
        return new WindowSplicer(player, inventory, side);
    }

    @Override
    public void initialiseClient() {
        super.initialiseClient();
        int x = 16;
        new ControlSplicerProgress(this, 84.0f, 32.0f, w() - 172.0f, 102.0f);
        CraftGUIUtil.horizontalGrid(
                x,
                62.0f,
                new ControlSlotArray(this, 0, 0, 2, 1).create(Splicer.SLOT_SERUM_RESERVE),
                new ControlIconDisplay(this, 0.0f, 0.0f, GUIIcon.ArrowRight.getIcon()),
                new ControlSlot(this, 0.0f, 0.0f).assign(Splicer.SLOT_SERUM_VIAL));
        new ControlSlotArray(this, x + 12, 84, 2, 1).create(Splicer.SLOT_SERUM_EXPENDED);
        new ControlIconDisplay(this, x + 12 + 36 + 4, 86.0f, GUIIcon.ArrowUpLeft.getIcon());
        new ControlEnergyBar(this, 196, 64, 60, 16, Position.LEFT);
        new ControlErrorState(this, 218.0f, 86.0f);
        CraftGUIUtil.verticalGrid(
                (w() - 72.0f) / 2.0f,
                32.0f,
                TextJustification.MIDDLE_CENTER,
                4.0f,
                new ControlSlotArray(this, 0, 0, 4, 1).create(Inoculator.SLOT_RESERVE),
                new ControlIconDisplay(this, 0.0f, 0.0f, GUIIcon.ArrowDown.getIcon()),
                new ControlSlot(this, 0.0f, 0.0f).assign(Splicer.SLOT_TARGET),
                new ControlIconDisplay(this, 0.0f, 0.0f, GUIIcon.ArrowDown.getIcon()),
                new ControlSlotArray(this, 0, 0, 4, 1).create(Inoculator.SLOT_FINISHED));
        new ControlPlayerInventory(this);
    }

    @Override
    public String getTitle() {
        return I18N.localise("genetics.machine.advMachine.splicer");
    }

    @Override
    protected AbstractMod getMod() {
        return Genetics.instance;
    }

    @Override
    protected String getName() {
        return "Splicer";
    }
}
