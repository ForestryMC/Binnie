package binnie.genetics.craftgui;

import binnie.core.AbstractMod;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.geometry.CraftGUIUtil;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.Position;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.craftgui.minecraft.GUIIcon;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlEnergyBar;
import binnie.core.craftgui.minecraft.control.ControlErrorState;
import binnie.core.craftgui.minecraft.control.ControlIconDisplay;
import binnie.core.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.core.craftgui.minecraft.control.ControlSlot;
import binnie.core.craftgui.minecraft.control.ControlSlotArray;
import binnie.core.craftgui.minecraft.control.ControlSlotCharge;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.StandardTexture;
import binnie.core.util.I18N;
import binnie.extrabees.core.ExtraBeeTexture;
import binnie.genetics.Genetics;
import binnie.genetics.machine.sequencer.Sequencer;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;

public class WindowSequencer extends WindowMachine {
    protected static Texture progressBase =
            new StandardTexture(64, 114, 98, 9, ExtraBeeTexture.GUIProgress.getTexture());
    protected static Texture progress = new StandardTexture(64, 123, 98, 9, ExtraBeeTexture.GUIProgress.getTexture());

    protected ControlText slotText;

    public WindowSequencer(EntityPlayer player, IInventory inventory, Side side) {
        super(226, 224, player, inventory, side);
    }

    public static Window create(EntityPlayer player, IInventory inventory, Side side) {
        return new WindowSequencer(player, inventory, side);
    }

    @Override
    public void recieveGuiNBT(Side side, EntityPlayer player, String name, NBTTagCompound nbt) {
        if (side == Side.CLIENT && name.equals("username")) {
            slotText.setValue(EnumChatFormatting.DARK_GRAY
                    + I18N.localise("genetics.machine.sequencer.gui.sequensedBy", nbt.getString("username")));
        }
        super.recieveGuiNBT(side, player, name, nbt);
    }

    @Override
    public void initialiseClient() {
        super.initialiseClient();
        int x = 16;
        int y = 32;
        CraftGUIUtil.horizontalGrid(
                x,
                y,
                TextJustification.MIDDLE_CENTER,
                2.0f,
                new ControlSlotArray(this, 0, 0, 2, 2).create(Sequencer.SLOT_RESERVE),
                new ControlIconDisplay(this, 0.0f, 0.0f, GUIIcon.ArrowRight.getIcon()),
                new ControlSequencerProgress(this, 0, 0),
                new ControlIconDisplay(this, 0.0f, 0.0f, GUIIcon.ArrowRight.getIcon()),
                new ControlSlot(this, 0.0f, 0.0f).assign(6));
        ControlSlot slotTarget = new ControlSlot(this, x + 96, y + 16);
        slotTarget.assign(5);
        x = 34;
        y = 92;
        slotText = new ControlText(
                this,
                new IArea(0.0f, y, w(), 12.0f),
                EnumChatFormatting.DARK_GRAY + I18N.localise("genetics.machine.sequencer.gui.willNotSave"),
                TextJustification.MIDDLE_CENTER);
        y += 20;
        ControlSlot slotDye = new ControlSlot(this, x, y);
        slotDye.assign(0);
        x += 20;
        new ControlSlotCharge(this, x, y, 0).setColor(16750848);
        x += 32;
        new ControlEnergyBar(this, x, y, 60, 16, Position.LEFT);
        x += 92;
        new ControlErrorState(this, x, y + 1);
        new ControlPlayerInventory(this);
    }

    @Override
    public String getTitle() {
        return I18N.localise("genetics.machine.machine.sequencer");
    }

    @Override
    protected AbstractMod getMod() {
        return Genetics.instance;
    }

    @Override
    protected String getName() {
        return "Sequencer";
    }
}
