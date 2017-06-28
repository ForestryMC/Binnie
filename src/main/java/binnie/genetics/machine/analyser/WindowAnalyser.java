package binnie.genetics.machine.analyser;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.AbstractMod;
import binnie.core.craftgui.geometry.Position;
import binnie.core.craftgui.minecraft.GUIIcon;
import binnie.core.craftgui.minecraft.MinecraftGUI;
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
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.machine.craftgui.WindowMachine;

public class WindowAnalyser extends WindowMachine {
	static Texture ProgressBase = new StandardTexture(0, 218, 142, 17, GeneticsTexture.GUIProcess3);
	static Texture Progress = new StandardTexture(0, 201, 142, 17, GeneticsTexture.GUIProcess3);

	public WindowAnalyser(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(220, 210, player, inventory, side);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void initialiseClient() {
		super.initialiseClient();
		WindowAnalyser.ProgressBase = new StandardTexture(0, 51, 66, 40, GeneticsTexture.GUIProcess);
		WindowAnalyser.Progress = new StandardTexture(66, 51, 66, 40, GeneticsTexture.GUIProcess);
		int x = 16;
		final int y = 32;
		new ControlSlotArray.Builder(this, x, y, 2, 3).create(Analyser.SLOT_RESERVE);
		x += 28;
		new ControlSlot.Builder(this, x, y + 54 + 8).assign(13);
		new ControlSlotCharge(this, x + 20, y + 54 + 8, 13).setColour(10040319);
		new ControlEnergyBar(this, x + 24 + 16, y + 54 + 8 + 1, 60, 16, Position.LEFT);
		new ControlErrorState(this, x + 24 + 16 + 60 + 16, y + 54 + 8 + 1);
		x -= 28;
		new ControlIconDisplay(this, x + 36 + 2, y + 18, GUIIcon.ARROW_RIGHT.getIcon().getResourceLocation());
		x += 56;
		new Panel(this, x, y, 76, 50, MinecraftGUI.PanelType.Tinted);
		new ControlProgress(this, x + 5, y + 5, WindowAnalyser.ProgressBase, WindowAnalyser.Progress, Position.LEFT);
		new ControlSlot.Builder(this, x + 38 - 9, y + 25 - 9).assign(6);
		new ControlIconDisplay(this, x + 76 + 2, y + 18, GUIIcon.ARROW_RIGHT.getIcon().getResourceLocation());
		x += 96;
		new ControlSlotArray.Builder(this, x, y, 2, 3).create(Analyser.SLOT_FINISHED);
		x += 52;
		new ControlPlayerInventory(this);
	}

	@Override
	public String getTitle() {
		return I18N.localise("genetics.lab_machine.analyser");
	}

	@Override
	protected AbstractMod getMod() {
		return Genetics.instance;
	}

	@Override
	protected String getBackgroundTextureName() {
		return "analyser";
	}
}
