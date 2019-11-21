package binnie.genetics.machine.polymeriser;

import binnie.core.api.gui.Alignment;
import binnie.core.gui.minecraft.GUIIcon;
import binnie.core.gui.minecraft.control.ControlEnergyBar;
import binnie.core.gui.minecraft.control.ControlErrorState;
import binnie.core.gui.minecraft.control.ControlIconDisplay;
import binnie.core.gui.minecraft.control.ControlLiquidTank;
import binnie.core.gui.minecraft.control.ControlMachineProgress;
import binnie.core.gui.minecraft.control.ControlPlayerInventory;
import binnie.core.gui.minecraft.control.ControlSlot;
import binnie.core.gui.minecraft.control.ControlSlotArray;
import binnie.core.gui.minecraft.control.ControlSlotCharge;
import binnie.core.gui.resource.textures.StandardTexture;
import binnie.core.gui.resource.textures.Texture;
import binnie.core.gui.window.WindowMachine;
import binnie.core.util.I18N;
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsTexture;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WindowPolymeriser extends WindowMachine {
	private static final Texture PROGRESS_BASE = new StandardTexture(76, 170, 160, 79, GeneticsTexture.GUI_PROCESS);
	private static final Texture PROGRESS = new StandardTexture(76, 91, 160, 79, GeneticsTexture.GUI_PROCESS);

	public WindowPolymeriser(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(278, 212, player, inventory, side);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void initialiseClient() {
		super.initialiseClient();
		int x = 16;
		final int y = 38;
		new ControlSlotArray.Builder(this, x, y, 1, 4).create(Polymeriser.SLOT_SERUM_RESERVE);
		new ControlIconDisplay(this, x + 18, y + 1, GUIIcon.ARROW_RIGHT.getIcon().getResourceLocation());
		x += 34;
		new ControlMachineProgress(this, x + 18, y - 6, WindowPolymeriser.PROGRESS_BASE, WindowPolymeriser.PROGRESS, Alignment.LEFT);
		new ControlSlot.Builder(this, x, y).assign(0);
		new ControlLiquidTank(this, x, y + 18 + 16, Polymeriser.TANK_BACTERIA, true);
		new ControlLiquidTank(this, x, y + 18 + 16 + 18 + 8, Polymeriser.TANK_DNA, true);
		new ControlEnergyBar(this, x + 120, 96, 64, 16, Alignment.LEFT);
		x += 40;
		new ControlSlot.Builder(this, x + 30, y + 18 + 8).assign(1);
		new ControlSlotCharge(this, x + 30 + 20, y + 18 + 8, 1).setColor(16766976);
		x += 138;
		new ControlSlotArray.Builder(this, x, y + 9, 2, 2).create(Polymeriser.SLOT_SERUM_FINISHED);
		final ControlErrorState errorState = new ControlErrorState(this, 244, 97);
		new ControlPlayerInventory(this);
	}

	@Override
	public String getTitle() {
		return I18N.localise("genetics.machine.polymeriser");
	}

	@Override
	protected String getModId() {
		return Genetics.instance.getModId();
	}

	@Override
	protected String getBackgroundTextureName() {
		return "polymeriser";
	}
}
