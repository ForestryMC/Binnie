package binnie.craftgui.extratrees.dictionary;

import binnie.core.AbstractMod;
import binnie.core.machines.Machine;
import binnie.craftgui.core.geometry.Position;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.minecraft.control.*;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.machines.Lumbermill;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;

public class WindowLumbermill extends Window {
    public WindowLumbermill(final EntityPlayer player, final IInventory inventory, final Side side) {
        super(220.0f, 192.0f, player, inventory, side);
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
        this.setTitle(Machine.getMachine(this.getInventory()).getPackage().getDisplayName());
        new ControlSlot(this, 42.0f, 43.0f).assign(Lumbermill.slotWood);
        new ControlSlot(this, 148.0f, 43.0f).assign(Lumbermill.slotPlanks);
        new ControlSlot(this, 172.0f, 28.0f).assign(Lumbermill.slotBark);
        new ControlSlot(this, 172.0f, 58.0f).assign(Lumbermill.slotSawdust);
        new ControlLumbermillProgress(this, 70.0f, 43.0f);
        new ControlLiquidTank(this, 16, 32);
        new ControlEnergyBar(this, 8, 112, 16, 60, Position.Bottom);
        new ControlPlayerInventory(this);
        new ControlErrorState(this, 95.0f, 73.0f);
    }

    public static Window create(final EntityPlayer player, final IInventory inventory, final Side side) {
        return new WindowLumbermill(player, inventory, side);
    }
}
