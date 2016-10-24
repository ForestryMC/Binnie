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
import binnie.genetics.machine.Splicer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;

public class WindowSplicer extends WindowMachine {
    static Texture ProgressBase = new StandardTexture(0, 72, 142, 72, GeneticsTexture.GUIProcess2.getTexture());
    static Texture Progress = new StandardTexture(0, 0, 142, 72, GeneticsTexture.GUIProcess2.getTexture());

    public static Window create(final EntityPlayer player, final IInventory inventory, final Side side) {
        return new WindowSplicer(player, inventory, side);
    }

    public WindowSplicer(final EntityPlayer player, final IInventory inventory, final Side side) {
        super(280, 240, player, inventory, side);
    }

    @Override
    public void initialiseClient() {
        this.setTitle("Splicer");
        int x = 16;
        new ControlSplicerProgress(this, 84.0f, 32.0f, this.w() - 172.0f, 102.0f);
        CraftGUIUtil.horizontalGrid(x, 62.0f, new ControlSlotArray(this, 0, 0, 2, 1).create(Splicer.slotSerumReserve), new ControlIconDisplay(this, 0.0f, 0.0f, GUIIcon.ArrowRight.getIcon().getResourceLocation()), new ControlSlot(this, 0.0f, 0.0f).assign(0));
        new ControlSlotArray(this, x + 12, 84, 2, 1).create(Splicer.slotSerumExpended);
        new ControlIconDisplay(this, x + 12 + 36 + 4, 86.0f, GUIIcon.ArrowUpLeft.getIcon().getResourceLocation());
        new ControlEnergyBar(this, 196, 64, 60, 16, Position.Left);
        new ControlErrorState(this, 218.0f, 86.0f);
        x += 142;
        CraftGUIUtil.verticalGrid((this.w() - 72.0f) / 2.0f, 32.0f, TextJustification.MiddleCenter, 4.0f, new ControlSlotArray(this, 0, 0, 4, 1).create(Inoculator.slotReserve), new ControlIconDisplay(this, 0.0f, 0.0f, GUIIcon.ArrowDown.getIcon().getResourceLocation()), new ControlSlot(this, 0.0f, 0.0f).assign(9), new ControlIconDisplay(this, 0.0f, 0.0f, GUIIcon.ArrowDown.getIcon().getResourceLocation()), new ControlSlotArray(this, 0, 0, 4, 1).create(Inoculator.slotFinished));
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
