package binnie.extratrees.craftgui;

import binnie.core.AbstractMod;
import binnie.core.craftgui.geometry.Position;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlEnergyBar;
import binnie.core.craftgui.minecraft.control.ControlErrorState;
import binnie.core.craftgui.minecraft.control.ControlLiquidTank;
import binnie.core.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.core.craftgui.minecraft.control.ControlSlot;
import binnie.core.machines.Machine;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.machines.lumbermill.Lumbermill;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public class WindowLumbermill extends Window {
    public WindowLumbermill(EntityPlayer player, IInventory inventory, Side side) {
        super(220.0f, 192.0f, player, inventory, side);
    }

    public static Window create(EntityPlayer player, IInventory inventory, Side side) {
        return new WindowLumbermill(player, inventory, side);
    }

    @Override
    protected AbstractMod getMod() {
        return ExtraTrees.instance;
    }

    @Override
    protected String getName() {
        return "Lumbermill";
    }

    @Override
    public void initialiseClient() {
        setTitle(Machine.getMachine(getInventory()).getPackage().getDisplayName());
        new ControlSlot(this, 42.0f, 43.0f).assign(Lumbermill.SLOT_WOOD);
        new ControlSlot(this, 148.0f, 43.0f).assign(Lumbermill.SLOT_PLANKS);
        new ControlSlot(this, 172.0f, 28.0f).assign(Lumbermill.SLOT_BARK);
        new ControlSlot(this, 172.0f, 58.0f).assign(Lumbermill.SLOT_SAWDUST);
        new ControlLumbermillProgress(this, 70.0f, 43.0f);
        new ControlLiquidTank(this, 16, 32);
        new ControlEnergyBar(this, 8, 112, 16, 60, Position.BOTTOM);
        new ControlPlayerInventory(this);
        new ControlErrorState(this, 95.0f, 73.0f);
    }
}
