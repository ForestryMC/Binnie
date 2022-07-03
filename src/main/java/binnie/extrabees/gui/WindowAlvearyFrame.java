package binnie.extrabees.gui;

import binnie.core.AbstractMod;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.core.craftgui.minecraft.control.ControlSlot;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.util.I18N;
import binnie.extrabees.ExtraBees;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public class WindowAlvearyFrame extends Window {
    protected Machine machine;
    protected ControlPlayerInventory playerInventory;

    public WindowAlvearyFrame(EntityPlayer player, IInventory inventory, Side side) {
        super(176.0f, 144.0f, player, inventory, side);
        machine = ((TileEntityMachine) inventory).getMachine();
    }

    public static Window create(EntityPlayer player, IInventory inventory, Side side) {
        if (player == null || inventory == null) {
            return null;
        }
        return new WindowAlvearyFrame(player, inventory, side);
    }

    @Override
    public void initialiseClient() {
        setTitle(I18N.localise("extrabees.machine.alveay.frame"));
        playerInventory = new ControlPlayerInventory(this);
        ControlSlot slot = new ControlSlot(this, 79.0f, 30.0f);
        slot.assign(0);
    }

    @Override
    public AbstractMod getMod() {
        return ExtraBees.instance;
    }

    @Override
    public String getName() {
        return "AlvearyFrame";
    }
}
