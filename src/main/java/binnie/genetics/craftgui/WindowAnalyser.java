package binnie.genetics.craftgui;

import binnie.core.AbstractMod;
import binnie.core.craftgui.geometry.Position;
import binnie.core.craftgui.minecraft.GUIIcon;
import binnie.core.craftgui.minecraft.MinecraftGUI;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlEnergyBar;
import binnie.core.craftgui.minecraft.control.ControlErrorState;
import binnie.core.craftgui.minecraft.control.ControlIconDisplay;
import binnie.core.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.core.craftgui.minecraft.control.ControlProgress;
import binnie.core.craftgui.minecraft.control.ControlSlot;
import binnie.core.craftgui.minecraft.control.ControlSlotArray;
import binnie.core.craftgui.minecraft.control.ControlSlotCharge;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.StandardTexture;
import binnie.core.craftgui.window.Panel;
import binnie.core.util.I18N;
import binnie.extrabees.core.ExtraBeeTexture;
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.machine.analyser.Analyser;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public class WindowAnalyser extends WindowMachine {
    protected static Texture progressBase =
            new StandardTexture(0, 218, 142, 17, ExtraBeeTexture.GUIProgress.getTexture());
    protected static Texture progress = new StandardTexture(0, 201, 142, 17, ExtraBeeTexture.GUIProgress.getTexture());

    public WindowAnalyser(EntityPlayer player, IInventory inventory, Side side) {
        super(220, 210, player, inventory, side);
    }

    public static Window create(EntityPlayer player, IInventory inventory, Side side) {
        return new WindowAnalyser(player, inventory, side);
    }

    @Override
    public void initialiseClient() {
        super.initialiseClient();
        WindowAnalyser.progressBase = new StandardTexture(0, 51, 66, 40, GeneticsTexture.GUIProcess.getTexture());
        WindowAnalyser.progress = new StandardTexture(66, 51, 66, 40, GeneticsTexture.GUIProcess.getTexture());
        int x = 16;
        int y = 32;
        new ControlSlotArray(this, x, y, 2, 3).create(Analyser.SLOT_RESERVE);
        x += 28;
        new ControlSlot(this, x, y + 54 + 8).assign(13);
        new ControlSlotCharge(this, x + 20, y + 54 + 8, 13).setColor(10040319);
        new ControlEnergyBar(this, x + 24 + 16, y + 54 + 8 + 1, 60, 16, Position.LEFT);
        new ControlErrorState(this, x + 24 + 16 + 60 + 16, y + 54 + 8 + 1);
        x -= 28;
        new ControlIconDisplay(this, x + 36 + 2, y + 18, GUIIcon.ArrowRight.getIcon());
        x += 56;
        new Panel(this, x, y, 76.0f, 50.0f, MinecraftGUI.PanelType.Tinted);
        new ControlProgress(this, x + 5, y + 5, WindowAnalyser.progressBase, WindowAnalyser.progress, Position.LEFT);
        new ControlSlot(this, x + 38 - 9, y + 25 - 9).assign(Analyser.SLOT_TARGET);
        new ControlIconDisplay(this, x + 76 + 2, y + 18, GUIIcon.ArrowRight.getIcon());
        x += 96;
        new ControlSlotArray(this, x, y, 2, 3).create(Analyser.SLOT_FINISHED);
        new ControlPlayerInventory(this);
    }

    @Override
    public String getTitle() {
        return I18N.localise("genetics.machine.labMachine.analyser");
    }

    @Override
    protected AbstractMod getMod() {
        return Genetics.instance;
    }

    @Override
    protected String getName() {
        return "Analyser";
    }
}
