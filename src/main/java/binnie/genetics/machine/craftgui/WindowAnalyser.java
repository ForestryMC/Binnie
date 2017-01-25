package binnie.genetics.machine.craftgui;

import binnie.core.AbstractMod;
import binnie.craftgui.core.geometry.Position;
import binnie.craftgui.minecraft.GUIIcon;
import binnie.craftgui.minecraft.MinecraftGUI;
import binnie.craftgui.minecraft.control.ControlEnergyBar;
import binnie.craftgui.minecraft.control.ControlErrorState;
import binnie.craftgui.minecraft.control.ControlIconDisplay;
import binnie.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.craftgui.minecraft.control.ControlProgress;
import binnie.craftgui.minecraft.control.ControlSlot;
import binnie.craftgui.minecraft.control.ControlSlotArray;
import binnie.craftgui.minecraft.control.ControlSlotCharge;
import binnie.craftgui.resource.Texture;
import binnie.craftgui.resource.minecraft.StandardTexture;
import binnie.craftgui.window.Panel;
import binnie.extrabees.core.ExtraBeeTexture;
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.machine.analyser.Analyser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;

public class WindowAnalyser extends WindowMachine {
	static Texture ProgressBase = new StandardTexture(0, 218, 142, 17, ExtraBeeTexture.GUIProgress.getTexture());
	static Texture Progress = new StandardTexture(0, 201, 142, 17, ExtraBeeTexture.GUIProgress.getTexture());

	public WindowAnalyser(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(220, 210, player, inventory, side);
	}

	@Override
	public void initialiseClient() {
		super.initialiseClient();
		WindowAnalyser.ProgressBase = new StandardTexture(0, 51, 66, 40, GeneticsTexture.GUIProcess.getTexture());
		WindowAnalyser.Progress = new StandardTexture(66, 51, 66, 40, GeneticsTexture.GUIProcess.getTexture());
		int x = 16;
		final int y = 32;
		new ControlSlotArray(this, x, y, 2, 3).create(Analyser.SLOT_RESERVE);
		x += 28;
		new ControlSlot(this, x, y + 54 + 8).assign(13);
		new ControlSlotCharge(this, x + 20, y + 54 + 8, 13).setColour(10040319);
		new ControlEnergyBar(this, x + 24 + 16, y + 54 + 8 + 1, 60, 16, Position.Left);
		new ControlErrorState(this, x + 24 + 16 + 60 + 16, y + 54 + 8 + 1);
		x -= 28;
		new ControlIconDisplay(this, x + 36 + 2, y + 18, GUIIcon.ArrowRight.getIcon().getResourceLocation());
		x += 56;
		new Panel(this, x, y, 76, 50, MinecraftGUI.PanelType.Tinted);
		new ControlProgress(this, x + 5, y + 5, WindowAnalyser.ProgressBase, WindowAnalyser.Progress, Position.Left);
		new ControlSlot(this, x + 38 - 9, y + 25 - 9).assign(6);
		new ControlIconDisplay(this, x + 76 + 2, y + 18, GUIIcon.ArrowRight.getIcon().getResourceLocation());
		x += 96;
		new ControlSlotArray(this, x, y, 2, 3).create(Analyser.SLOT_FINISHED);
		x += 52;
		new ControlPlayerInventory(this);
	}

	@Override
	public String getTitle() {
		return Genetics.proxy.localise("machine.labMachine.analyser");
	}

	@Override
	protected AbstractMod getMod() {
		return Genetics.instance;
	}

	@Override
	protected String getBackgroundTextureName() {
		return "Analyser";
	}

}
