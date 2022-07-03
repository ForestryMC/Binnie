package binnie.genetics.craftgui;

import binnie.core.AbstractMod;
import binnie.core.craftgui.geometry.Position;
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
import binnie.genetics.machine.incubator.Incubator;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public class WindowIncubator extends WindowMachine {
    protected static Texture progressBase = new StandardTexture(0, 91, 38, 32, GeneticsTexture.GUIProcess);
    protected static Texture progress = new StandardTexture(38, 91, 38, 32, GeneticsTexture.GUIProcess);

    public WindowIncubator(EntityPlayer player, IInventory inventory, Side side) {
        super(228, 196, player, inventory, side);
    }

    public static Window create(EntityPlayer player, IInventory inventory, Side side) {
        return new WindowIncubator(player, inventory, side);
    }

    @Override
    public void initialiseClient() {
        super.initialiseClient();
        int x = 16;
        int y = 32;
        new ControlLiquidTank(this, x, y).setTankID(Incubator.TANK_INPUT);
        x += 26;
        new ControlSlotArray(this, x, y + 3, 1, 3).create(Incubator.SLOT_QUEUE);
        x += 20;
        new ControlIconDisplay(this, x, y + 3 + 10, GUIIcon.ArrowRight.getIcon());
        x += 18;
        new ControlMachineProgress(
                this, x, y + 6, WindowIncubator.progressBase, WindowIncubator.progress, Position.LEFT);
        new ControlSlot(this, x + 10, y + 3 + 10).assign(Incubator.SLOT_INCUBATOR);
        x += 40;
        new ControlIconDisplay(this, x, y + 3 + 10, GUIIcon.ArrowRight.getIcon());
        x += 18;
        new ControlSlotArray(this, x, y + 3, 1, 3).create(Incubator.SLOT_OUTPUT);
        x += 26;
        new ControlLiquidTank(this, x, y).setTankID(Incubator.TANK_OUTPUT);
        x += 34;
        new ControlEnergyBar(this, x, y + 3, 16, 54, Position.BOTTOM);
        new ControlErrorState(this, 91.0f, 82.0f);
        new ControlPlayerInventory(this);
    }

    @Override
    public String getTitle() {
        return I18N.localise("genetics.machine.labMachine.incubator");
    }

    @Override
    protected AbstractMod getMod() {
        return Genetics.instance;
    }

    @Override
    protected String getName() {
        return "Incubator";
    }
}
