package binnie.extrabees.gui;

import binnie.core.AbstractMod;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.core.craftgui.minecraft.control.ControlSlotArray;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.util.I18N;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.apiary.machine.hatchery.AlvearyHatchery;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public class WindowAlvearyHatchery extends Window {
    protected Machine machine;
    protected ControlPlayerInventory playerInventory;

    public WindowAlvearyHatchery(EntityPlayer player, IInventory inventory, Side side) {
        super(176.0f, 144.0f, player, inventory, side);
        machine = ((TileEntityMachine) inventory).getMachine();
    }

    public static Window create(EntityPlayer player, IInventory inventory, Side side) {
        if (player == null || inventory == null) {
            return null;
        }
        return new WindowAlvearyHatchery(player, inventory, side);
    }

    @Override
    public void initialiseClient() {
        setTitle(I18N.localise("extrabees.machine.alveay.hatchery"));
        playerInventory = new ControlPlayerInventory(this);
        ControlSlotArray slot = new ControlSlotArray(this, 43, 30, 5, 1);
        slot.create(AlvearyHatchery.SLOT_LARVAE);
    }

    @Override
    public AbstractMod getMod() {
        return ExtraBees.instance;
    }

    @Override
    public String getName() {
        return "AlvearyHatchery";
    }
}
