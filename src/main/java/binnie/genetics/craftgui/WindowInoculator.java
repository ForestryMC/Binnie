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
import binnie.core.craftgui.minecraft.control.ControlLiquidTank;
import binnie.core.craftgui.minecraft.control.ControlMachineProgress;
import binnie.core.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.core.craftgui.minecraft.control.ControlSlot;
import binnie.core.craftgui.minecraft.control.ControlSlotArray;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.StandardTexture;
import binnie.core.util.I18N;
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.machine.inoculator.Inoculator;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public class WindowInoculator extends WindowMachine {
    static Texture ProgressBase = new StandardTexture(0, 72, 142, 72, GeneticsTexture.GUIProcess2.getTexture());
    static Texture Progress = new StandardTexture(0, 0, 142, 72, GeneticsTexture.GUIProcess2.getTexture());

    public WindowInoculator(EntityPlayer player, IInventory inventory, Side side) {
        super(266, 240, player, inventory, side);
    }

    public static Window create(EntityPlayer player, IInventory inventory, Side side) {
        return new WindowInoculator(player, inventory, side);
    }

    @Override
    public void initialiseClient() {
        super.initialiseClient();
        int x = 16;
        int y = 32;
        new ControlLiquidTank(this, x, y + 18 + 16).setTankID(0);
        CraftGUIUtil.horizontalGrid(
                x,
                y,
                new ControlSlotArray(this, 0, 0, 2, 1).create(Inoculator.SLOT_SERUM_RESERVE),
                new ControlIconDisplay(this, 0.0f, 0.0f, GUIIcon.ArrowRight.getIcon()),
                new ControlSlot(this, 0.0f, 0.0f).assign(0),
                new ControlIconDisplay(this, 0.0f, 0.0f, GUIIcon.ArrowRight.getIcon()),
                new ControlSlotArray(this, 0, 0, 2, 1).create(Inoculator.SLOT_SERUM_EXPENDED));
        x += 18;
        new ControlMachineProgress(
                this, x, y + 24, WindowInoculator.ProgressBase, WindowInoculator.Progress, Position.LEFT);
        new ControlEnergyBar(this, 91, 118, 60, 16, Position.LEFT);
        new ControlErrorState(this, 161.0f, 118.0f);
        x += 142;
        CraftGUIUtil.verticalGrid(
                x,
                y,
                TextJustification.MIDDLE_LEFT,
                8.0f,
                new ControlSlotArray(this, x, y, 4, 1).create(Inoculator.SLOT_RESERVE),
                new ControlSlot(this, x, y + 18 + 8).assign(9),
                new ControlSlotArray(this, x, y + 18 + 8 + 18 + 8, 4, 1).create(Inoculator.SLOT_FINISHED));
        new ControlIconDisplay(this, x + 18, y + 18 + 2, GUIIcon.ArrowUpLeft.getIcon());
        new ControlIconDisplay(this, x + 18, y + 18 + 18, GUIIcon.ArrowLeftDown.getIcon());
        new ControlPlayerInventory(this);
    }

    @Override
    public String getTitle() {
        return I18N.localise("genetics.machine.machine.inoculator");
    }

    @Override
    protected AbstractMod getMod() {
        return Genetics.instance;
    }

    @Override
    protected String getName() {
        return "Inoculator";
    }
}
