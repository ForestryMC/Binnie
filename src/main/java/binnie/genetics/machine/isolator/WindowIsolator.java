package binnie.genetics.machine.isolator;

import binnie.core.AbstractMod;
import binnie.core.craftgui.geometry.Position;
import binnie.core.craftgui.minecraft.GUIIcon;
import binnie.core.craftgui.minecraft.control.*;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.StandardTexture;
import binnie.core.ExtraBeeTexture;
import binnie.genetics.Genetics;
import binnie.genetics.machine.craftgui.WindowMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WindowIsolator extends WindowMachine {
	static Texture ProgressBase = new StandardTexture(0, 218, 142, 17, ExtraBeeTexture.GUIProgress);
	static Texture Progress = new StandardTexture(0, 201, 142, 17, ExtraBeeTexture.GUIProgress);

	public WindowIsolator(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(330, 208, player, inventory, side);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void initialiseClient() {
		super.initialiseClient();
		int x = 16;
		final int y = 32;
		new ControlLiquidTank(this, x, y).setTankID(0);
		x += 26;
		new ControlSlotArray.Builder(this, x, y + 3, 1, 3).create(Isolator.SLOT_RESERVE);
		x += 20;
		new ControlIconDisplay(this, x, y + 3 + 1, GUIIcon.ARROW_RIGHT.getIcon().getResourceLocation());
		x += 18;
		new ControlSlot.Builder(this, x, y + 3).assign(5);
		new ControlSlot.Builder(this, x, y + 36 + 3).assign(0);
		new ControlSlotCharge(this, x + 18 + 2, y + 36 + 3, 0).setColour(15722671);
		x += 18;
		new ControlProgress(this, x, y + 3, WindowIsolator.ProgressBase, WindowIsolator.Progress, Position.LEFT);
		x += 142;
		new ControlSlot.Builder(this, x, y + 3).assign(6);
		new ControlSlot.Builder(this, x, y + 3 + 36).assign(1);
		new ControlIconDisplay(this, x + 1, y + 3 + 19, GUIIcon.ARROW_UP.getIcon().getResourceLocation());
		x += 20;
		new ControlIconDisplay(this, x, y + 3 + 1, GUIIcon.ARROW_RIGHT.getIcon().getResourceLocation());
		x += 18;
		new ControlSlotArray.Builder(this, x, y + 3, 2, 3).create(Isolator.SLOT_FINISHED);
		new ControlEnergyBar(this, 260, 130, 16, 60, Position.BOTTOM);
		new ControlErrorState(this, 153, 81);
		new ControlPlayerInventory(this);
	}

	@Override
	public String getTitle() {
		return Genetics.proxy.localise("machine.machine.isolator");
	}

	@Override
	protected AbstractMod getMod() {
		return Genetics.instance;
	}

	@Override
	protected String getBackgroundTextureName() {
		return "isolator";
	}

}
