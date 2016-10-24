package binnie.craftgui.genetics.machine;

import binnie.core.AbstractMod;
import binnie.craftgui.core.geometry.CraftGUIUtil;
import binnie.craftgui.core.geometry.Position;
import binnie.craftgui.core.geometry.TextJustification;
import binnie.craftgui.minecraft.GUIIcon;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.minecraft.control.*;
import binnie.craftgui.resource.Texture;
import binnie.craftgui.resource.minecraft.StandardTexture;
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.machine.Inoculator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;

public class WindowInoculator extends WindowMachine {
    static Texture ProgressBase = new StandardTexture(0, 72, 142, 72, GeneticsTexture.GUIProcess2.getTexture());
    static Texture Progress = new StandardTexture(0, 0, 142, 72, GeneticsTexture.GUIProcess2.getTexture());

    public static Window create(final EntityPlayer player, final IInventory inventory, final Side side) {
        return new WindowInoculator(player, inventory, side);
    }

    public WindowInoculator(final EntityPlayer player, final IInventory inventory, final Side side) {
        super(266, 240, player, inventory, side);
    }

    @Override
    public void initialiseClient() {
        this.setTitle("Inoculator");
        int x = 16;
        final int y = 32;
        new ControlLiquidTank(this, x, y + 18 + 16).setTankID(0);
        CraftGUIUtil.horizontalGrid(x, y, new ControlSlotArray(this, 0, 0, 2, 1).create(Inoculator.slotSerumReserve), new ControlIconDisplay(this, 0.0f, 0.0f, GUIIcon.ArrowRight.getIcon().getResourceLocation()), new ControlSlot(this, 0.0f, 0.0f).assign(0), new ControlIconDisplay(this, 0.0f, 0.0f, GUIIcon.ArrowRight.getIcon().getResourceLocation()), new ControlSlotArray(this, 0, 0, 2, 1).create(Inoculator.slotSerumExpended));
        x += 18;
        new ControlMachineProgress(this, x, y + 24, WindowInoculator.ProgressBase, WindowInoculator.Progress, Position.Left);
        new ControlEnergyBar(this, 91, 118, 60, 16, Position.Left);
        new ControlErrorState(this, 161.0f, 118.0f);
        x += 142;
        CraftGUIUtil.verticalGrid(x, y, TextJustification.MiddleLeft, 8.0f, new ControlSlotArray(this, x, y, 4, 1).create(Inoculator.slotReserve), new ControlSlot(this, x, y + 18 + 8).assign(9), new ControlSlotArray(this, x, y + 18 + 8 + 18 + 8, 4, 1).create(Inoculator.slotFinished));
        new ControlIconDisplay(this, x + 18, y + 18 + 2, GUIIcon.ArrowUpLeft.getIcon().getResourceLocation());
        new ControlIconDisplay(this, x + 18, y + 18 + 18, GUIIcon.ArrowLeftDown.getIcon().getResourceLocation());
        new ControlPlayerInventory(this);
    }

    @Override
    public String getTitle() {
        return "Inoculator";
    }

    @Override
    protected AbstractMod getMod() {
        return Genetics.instance;
    }

    @Override
    protected String getName() {
        return "Inoculator";
    }

}
