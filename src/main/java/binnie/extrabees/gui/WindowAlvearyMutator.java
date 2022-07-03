package binnie.extrabees.gui;

import binnie.core.AbstractMod;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlItemDisplay;
import binnie.core.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.core.craftgui.minecraft.control.ControlSlot;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.util.I18N;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.apiary.machine.mutator.AlvearyMutator;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class WindowAlvearyMutator extends Window {
    protected Machine machine;
    protected ControlPlayerInventory playerInventory;

    public WindowAlvearyMutator(EntityPlayer player, IInventory inventory, Side side) {
        super(176.0f, 176.0f, player, inventory, side);
        machine = ((TileEntityMachine) inventory).getMachine();
    }

    public static Window create(EntityPlayer player, IInventory inventory, Side side) {
        if (player == null || inventory == null) {
            return null;
        }
        return new WindowAlvearyMutator(player, inventory, side);
    }

    @Override
    public void initialiseClient() {
        setTitle(I18N.localise("extrabees.machine.alveay.mutator"));
        playerInventory = new ControlPlayerInventory(this);
        ControlSlot slot = new ControlSlot(this, 79.0f, 30.0f);
        slot.assign(AlvearyMutator.SLOT_MUTATOR);
        new ControlText(
                        this,
                        new IArea(0.0f, 52.0f, w(), 16.0f),
                        I18N.localise("extrabees.machine.alveay.mutator.mutagens"),
                        TextJustification.MIDDLE_CENTER)
                .setColor(0x555555);
        int size = AlvearyMutator.getMutagens().size();
        int w = size * 18;

        if (size <= 0) {
            return;
        }

        float x = (w() - w) / 2.0f;
        for (ItemStack stack : AlvearyMutator.getMutagens()) {
            ControlItemDisplay display = new ControlItemDisplay(this, x, 66.0f);
            display.setItemStack(stack);
            display.hastooltip = true;
            x += 18.0f;
        }
    }

    @Override
    public AbstractMod getMod() {
        return ExtraBees.instance;
    }

    @Override
    public String getName() {
        return "AlvearyMutator";
    }
}
