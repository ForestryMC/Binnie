package binnie.extrabees.gui;

import binnie.core.AbstractMod;
import binnie.core.craftgui.geometry.Position;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlEnergyBar;
import binnie.core.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.core.craftgui.minecraft.control.ControlSlot;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.util.I18N;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.apiary.machine.stimulator.AlvearyStimulator;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public class WindowAlvearyStimulator extends Window {
    protected Machine machine;
    protected ControlPlayerInventory playerInventory;

    public WindowAlvearyStimulator(EntityPlayer player, IInventory inventory, Side side) {
        super(176.0f, 144.0f, player, inventory, side);
        machine = ((TileEntityMachine) inventory).getMachine();
    }

    public static Window create(EntityPlayer player, IInventory inventory, Side side) {
        if (player == null || inventory == null) {
            return null;
        }
        return new WindowAlvearyStimulator(player, inventory, side);
    }

    @Override
    public void initialiseClient() {
        setTitle(I18N.localise("extrabees.machine.alveay.stimulator"));
        new ControlEnergyBar(this, 75, 29, 60, 16, Position.LEFT);
        ControlSlot slot = new ControlSlot(this, 41.0f, 28.0f);
        slot.assign(AlvearyStimulator.SLOT_CIRCUIT);
        playerInventory = new ControlPlayerInventory(this);
    }

    @Override
    public AbstractMod getMod() {
        return ExtraBees.instance;
    }

    @Override
    public String getName() {
        return "AlvearyStimulator";
    }
}
