package binnie.genetics.craftgui;

import binnie.core.AbstractMod;
import binnie.core.craftgui.geometry.Position;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlEnergyBar;
import binnie.core.craftgui.minecraft.control.ControlErrorState;
import binnie.core.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.core.craftgui.minecraft.control.ControlSlot;
import binnie.core.craftgui.minecraft.control.ControlSlotArray;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.StandardTexture;
import binnie.core.util.I18N;
import binnie.extrabees.core.ExtraBeeTexture;
import binnie.genetics.Genetics;
import binnie.genetics.machine.acclimatiser.Acclimatiser;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public class WindowAcclimatiser extends WindowMachine {
    protected static Texture progressBase =
            new StandardTexture(64, 0, 130, 21, ExtraBeeTexture.GUIProgress.getTexture());
    protected static Texture progress = new StandardTexture(64, 21, 130, 21, ExtraBeeTexture.GUIProgress.getTexture());

    public WindowAcclimatiser(EntityPlayer player, IInventory inventory, Side side) {
        super(280, 198, player, inventory, side);
    }

    public static Window create(EntityPlayer player, IInventory inventory, Side side) {
        return new WindowAcclimatiser(player, inventory, side);
    }

    @Override
    public void initialiseClient() {
        super.initialiseClient();
        int x = 59;
        int y = 32;
        new ControlSlotArray(this, x, y, 2, 2).create(Acclimatiser.SLOT_RESERVE);
        x += 54;
        new ControlSlot(this, x + 18, y).assign(Acclimatiser.SLOT_TARGET);
        new ControlSlotArray(this, x, y + 18 + 18, 3, 1).create(Acclimatiser.SLOT_ACCLIMATISER);
        x += 72;
        new ControlSlotArray(this, x, y, 2, 2).create(Acclimatiser.SLOT_DONE);
        new ControlEnergyBar(this, 21, 115, 16, 60, Position.BOTTOM);
        new ControlErrorState(this, 181.0f, 83.0f);
        new ControlPlayerInventory(this);
    }

    @Override
    public String getTitle() {
        return I18N.localise("genetics.machine.labMachine.acclimatiser");
    }

    @Override
    protected AbstractMod getMod() {
        return Genetics.instance;
    }

    @Override
    protected String getName() {
        return "Acclimatiser";
    }
}
