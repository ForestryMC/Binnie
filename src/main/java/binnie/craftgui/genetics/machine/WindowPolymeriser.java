// 
// Decompiled by Procyon v0.5.30
// 

package binnie.craftgui.genetics.machine;

import binnie.craftgui.resource.minecraft.StandardTexture;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.Genetics;
import binnie.core.AbstractMod;
import binnie.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.craftgui.minecraft.control.ControlErrorState;
import binnie.craftgui.minecraft.control.ControlSlotCharge;
import binnie.craftgui.minecraft.control.ControlEnergyBar;
import binnie.craftgui.minecraft.control.ControlLiquidTank;
import binnie.craftgui.minecraft.control.ControlSlot;
import binnie.craftgui.core.geometry.Position;
import binnie.craftgui.minecraft.control.ControlMachineProgress;
import binnie.craftgui.minecraft.GUIIcon;
import binnie.craftgui.minecraft.control.ControlIconDisplay;
import binnie.genetics.machine.Polymeriser;
import binnie.craftgui.minecraft.control.ControlSlotArray;
import binnie.craftgui.minecraft.Window;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.inventory.IInventory;
import net.minecraft.entity.player.EntityPlayer;
import binnie.craftgui.resource.Texture;

public class WindowPolymeriser extends WindowMachine
{
	static Texture ProgressBase;
	static Texture Progress;

	public static Window create(final EntityPlayer player, final IInventory inventory, final Side side) {
		return new WindowPolymeriser(player, inventory, side);
	}

	public WindowPolymeriser(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(278, 212, player, inventory, side);
	}

	@Override
	public void initialiseClient() {
		super.initialiseClient();
		int x = 16;
		final int y = 38;
		new ControlSlotArray(this, x, y, 1, 4).create(Polymeriser.slotSerumReserve);
		new ControlIconDisplay(this, x + 18, y + 1, GUIIcon.ArrowRight.getIcon());
		x += 34;
		new ControlMachineProgress(this, x + 18, y - 6, WindowPolymeriser.ProgressBase, WindowPolymeriser.Progress, Position.Left);
		new ControlSlot(this, x, y).assign(0);
		new ControlLiquidTank(this, x, y + 18 + 16, true).setTankID(0);
		new ControlLiquidTank(this, x, y + 18 + 16 + 18 + 8, true).setTankID(1);
		new ControlEnergyBar(this, x + 120, 96, 64, 16, Position.Left);
		x += 40;
		new ControlSlot(this, x + 30, y + 18 + 8).assign(1);
		new ControlSlotCharge(this, x + 30 + 20, y + 18 + 8, 1).setColour(16766976);
		x += 138;
		new ControlSlotArray(this, x, y + 9, 2, 2).create(Polymeriser.slotSerumFinished);
		final ControlErrorState errorState = new ControlErrorState(this, 244.0f, 97.0f);
		new ControlPlayerInventory(this);
	}

	@Override
	public String getTitle() {
		return "Polymeriser";
	}

	@Override
	protected AbstractMod getMod() {
		return Genetics.instance;
	}

	@Override
	protected String getName() {
		return "Polymeriser";
	}

	static {
		WindowPolymeriser.ProgressBase = new StandardTexture(76, 170, 160, 79, GeneticsTexture.GUIProcess.getTexture());
		WindowPolymeriser.Progress = new StandardTexture(76, 91, 160, 79, GeneticsTexture.GUIProcess.getTexture());
	}
}
