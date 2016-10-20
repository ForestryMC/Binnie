package binnie.craftgui.extratrees.dictionary;

import binnie.core.AbstractMod;
import binnie.core.machines.Machine;
import binnie.craftgui.core.geometry.Position;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.minecraft.control.ControlEnergyBar;
import binnie.craftgui.minecraft.control.ControlErrorState;
import binnie.craftgui.minecraft.control.ControlLiquidTank;
import binnie.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.machines.Distillery;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;

public class WindowDistillery extends Window {
    public WindowDistillery(final EntityPlayer player, final IInventory inventory, final Side side) {
        super(224.0f, 192.0f, player, inventory, side);
    }

    @Override
    protected AbstractMod getMod() {
        return ExtraTrees.instance;
    }

    @Override
    protected String getName() {
        return "Distillery";
    }

    @Override
    public void initialiseClient() {
        this.setTitle(Machine.getMachine(this.getInventory()).getPackage().getDisplayName());
        int x = 16;
        new ControlLiquidTank(this, x, 35).setTankID(Distillery.tankInput);
        x += 34;
        new ControlDistilleryProgress(this, x, 32.0f);
        x += 64;
        new ControlLiquidTank(this, x, 35).setTankID(Distillery.tankOutput);
        x += 34;
        new ControlEnergyBar(this, x, 36, 60, 16, Position.Left);
        new ControlPlayerInventory(this);
        new ControlErrorState(this, x + 21, 62.0f);
    }

    public static Window create(final EntityPlayer player, final IInventory inventory, final Side side) {
        return new WindowDistillery(player, inventory, side);
    }
}
