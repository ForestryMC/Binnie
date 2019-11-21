package binnie.genetics.machine.analyser;

import binnie.core.api.gui.Alignment;
import binnie.core.gui.minecraft.GUIIcon;
import binnie.core.gui.minecraft.MinecraftGUI;
import binnie.core.gui.minecraft.control.ControlEnergyBar;
import binnie.core.gui.minecraft.control.ControlErrorState;
import binnie.core.gui.minecraft.control.ControlIconDisplay;
import binnie.core.gui.minecraft.control.ControlPlayerInventory;
import binnie.core.gui.minecraft.control.ControlProgress;
import binnie.core.gui.minecraft.control.ControlSlot;
import binnie.core.gui.minecraft.control.ControlSlotArray;
import binnie.core.gui.minecraft.control.ControlSlotCharge;
import binnie.core.gui.resource.textures.StandardTexture;
import binnie.core.gui.resource.textures.Texture;
import binnie.core.gui.window.Panel;
import binnie.core.gui.window.WindowMachine;
import binnie.core.util.I18N;
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsTexture;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WindowAnalyser extends WindowMachine {
	private static final Texture PROGRESS_BASE = new StandardTexture(0, 51, 66, 40, GeneticsTexture.GUI_PROCESS);
	private static final Texture PROGRESS = new StandardTexture(66, 51, 66, 40, GeneticsTexture.GUI_PROCESS);

	public WindowAnalyser(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(220, 210, player, inventory, side);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void initialiseClient() {
		super.initialiseClient();
		int x = 16;
		final int y = 32;
		new ControlSlotArray.Builder(this, x, y, 2, 3).create(Analyser.SLOT_RESERVE);
		x += 28;
		new ControlSlot.Builder(this, x, y + 54 + 8).assign(13);
		new ControlSlotCharge(this, x + 20, y + 54 + 8, 13).setColor(10040319);
		new ControlEnergyBar(this, x + 24 + 16, y + 54 + 8 + 1, 60, 16, Alignment.LEFT);
		new ControlErrorState(this, x + 24 + 16 + 60 + 16, y + 54 + 8 + 1);
		x -= 28;
		new ControlIconDisplay(this, x + 36 + 2, y + 18, GUIIcon.ARROW_RIGHT.getIcon().getResourceLocation());
		x += 56;
		new Panel(this, x, y, 76, 50, MinecraftGUI.PanelType.TINTED);
		new ControlProgress(this, x + 5, y + 5, PROGRESS_BASE, PROGRESS, Alignment.LEFT);
		new ControlSlot.Builder(this, x + 38 - 9, y + 25 - 9).assign(6);
		new ControlIconDisplay(this, x + 76 + 2, y + 18, GUIIcon.ARROW_RIGHT.getIcon().getResourceLocation());
		x += 96;
		new ControlSlotArray.Builder(this, x, y, 2, 3).create(Analyser.SLOT_FINISHED);
		x += 52;
		new ControlPlayerInventory(this);
	}

	@Override
	public String getTitle() {
		return I18N.localise("genetics.machine.lab_machine.analyser");
	}

	@Override
	protected String getModId() {
		return Genetics.instance.getModId();
	}

	@Override
	protected String getBackgroundTextureName() {
		return "analyser";
	}
}
