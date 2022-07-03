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
import binnie.core.craftgui.minecraft.control.ControlSlotCharge;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.StandardTexture;
import binnie.core.util.I18N;
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.machine.polymeriser.Polymeriser;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public class WindowPolymeriser extends WindowMachine {
    protected static Texture progressBase =
            new StandardTexture(76, 170, 160, 79, GeneticsTexture.GUIProcess.getTexture());
    protected static Texture progress = new StandardTexture(76, 91, 160, 79, GeneticsTexture.GUIProcess.getTexture());

    public WindowPolymeriser(EntityPlayer player, IInventory inventory, Side side) {
        super(278, 212, player, inventory, side);
    }

    public static Window create(EntityPlayer player, IInventory inventory, Side side) {
        return new WindowPolymeriser(player, inventory, side);
    }

    @Override
    public void initialiseClient() {
        super.initialiseClient();
        int x = 16;
        int y = 38;
        new ControlSlotArray(this, x, y, 1, 4).create(Polymeriser.SLOT_SERUM_RESERVE);
        new ControlIconDisplay(this, x + 18, y + 1, GUIIcon.ArrowRight.getIcon());
        x += 34;
        new ControlMachineProgress(this, x + 18, y - 6, progressBase, progress, Position.LEFT);
        new ControlSlot(this, x, y).assign(Polymeriser.SLOT_SERUM);
        new ControlLiquidTank(this, x, y + 18 + 16, true).setTankID(Polymeriser.TANK_BACTERIA);
        new ControlLiquidTank(this, x, y + 18 + 16 + 18 + 8, true).setTankID(Polymeriser.TANK_DNA);
        new ControlEnergyBar(this, x + 120, 96, 64, 16, Position.LEFT);
        x += 40;
        new ControlSlot(this, x + 30, y + 18 + 8).assign(Polymeriser.SLOT_GOLD);
        new ControlSlotCharge(this, x + 30 + 20, y + 18 + 8, 1).setColor(0xffd800);
        x += 138;
        new ControlSlotArray(this, x, y + 9, 2, 2).create(Polymeriser.SLOT_SERUM_FINISHED);
        new ControlErrorState(this, 244.0f, 97.0f);
        new ControlPlayerInventory(this);
    }

    @Override
    public String getTitle() {
        return I18N.localise("genetics.machine.machine.polymeriser");
    }

    @Override
    protected AbstractMod getMod() {
        return Genetics.instance;
    }

    @Override
    protected String getName() {
        return "Polymeriser";
    }
}
