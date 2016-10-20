package binnie.craftgui.genetics.machine;

import binnie.core.AbstractMod;
import binnie.craftgui.core.geometry.Position;
import binnie.craftgui.minecraft.GUIIcon;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.minecraft.control.*;
import binnie.craftgui.resource.Texture;
import binnie.craftgui.resource.minecraft.StandardTexture;
import binnie.extrabees.core.ExtraBeeTexture;
import binnie.genetics.Genetics;
import binnie.genetics.machine.Genepool;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;

public class WindowGenepool extends WindowMachine {
    static Texture ProgressBase;
    static Texture Progress;

    public static Window create(final EntityPlayer player, final IInventory inventory, final Side side) {
        return new WindowGenepool(player, inventory, side);
    }

    public WindowGenepool(final EntityPlayer player, final IInventory inventory, final Side side) {
        super(280, 198, player, inventory, side);
    }

    @Override
    public void initialiseClient() {
        this.setTitle("Genepool");
        int x = 16;
        final int y = 32;
        new ControlLiquidTank(this, x, y).setTankID(1);
        x += 26;
        new ControlSlotArray(this, x, y + 3, 2, 3).create(Genepool.slotReserve);
        x += 38;
        //TODO RENDERING
        new ControlIconDisplay(this, x, y + 3 + 18 + 1, GUIIcon.ArrowRight.getIcon().getResourceLocation());
        x += 18;
        new ControlSlot(this, x, y + 3 + 18).assign(0);
        x += 18;
        new ControlMachineProgress(this, x, y + 19, WindowGenepool.ProgressBase, WindowGenepool.Progress, Position.Left);
        x += 130;
        new ControlLiquidTank(this, x, y).setTankID(0);
        new ControlEnergyBar(this, 21, 115, 16, 60, Position.Bottom);
        new ControlSlot(this, 121.0f, 82.0f).assign(7);
        new ControlSlotCharge(this, 143, 82, 7).setColour(15722671);
        new ControlErrorState(this, 181.0f, 83.0f);
        new ControlPlayerInventory(this);
    }

    @Override
    public String getTitle() {
        return "Genepool";
    }

    @Override
    protected AbstractMod getMod() {
        return Genetics.instance;
    }

    @Override
    protected String getName() {
        return "Genepool";
    }

    static {
        WindowGenepool.ProgressBase = new StandardTexture(64, 0, 130, 21, ExtraBeeTexture.GUIProgress.getTexture());
        WindowGenepool.Progress = new StandardTexture(64, 21, 130, 21, ExtraBeeTexture.GUIProgress.getTexture());
    }
}
