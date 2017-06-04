package binnie.genetics.machine.acclimatiser;

import binnie.core.AbstractMod;
import binnie.core.ExtraBeeTexture;
import binnie.core.craftgui.geometry.Position;
import binnie.core.craftgui.minecraft.control.ControlEnergyBar;
import binnie.core.craftgui.minecraft.control.ControlErrorState;
import binnie.core.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.core.craftgui.minecraft.control.ControlSlot;
import binnie.core.craftgui.minecraft.control.ControlSlotArray;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.StandardTexture;
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.machine.craftgui.WindowMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WindowAcclimatiser extends WindowMachine {
	static Texture ProgressBase = new StandardTexture(64, 0, 130, 21, GeneticsTexture.GUIProcess3);
	static Texture Progress = new StandardTexture(64, 21, 130, 21, GeneticsTexture.GUIProcess3);

	public WindowAcclimatiser(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(280, 198, player, inventory, side);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void initialiseClient() {
		super.initialiseClient();
		int x = 16;
		final int y = 32;
		new ControlSlotArray.Builder(this, x, y, 2, 2).create(Acclimatiser.SLOT_RESERVE);
		x += 54;
		new ControlSlot.Builder(this, x + 18, y).assign(4);
		new ControlSlotArray.Builder(this, x, y + 18 + 18, 3, 1).create(Acclimatiser.SLOT_ACCLIMATISER);
		x += 72;
		new ControlSlotArray.Builder(this, x, y, 2, 2).create(Acclimatiser.SLOT_DRONE);
		new ControlEnergyBar(this, 21, 115, 16, 60, Position.BOTTOM);
		new ControlErrorState(this, 181, 83);
		new ControlPlayerInventory(this);
	}

	@Override
	public String getTitle() {
		return Genetics.proxy.localise("machine.labMachine.acclimatiser");
	}

	@Override
	protected AbstractMod getMod() {
		return Genetics.instance;
	}

	@Override
	protected String getBackgroundTextureName() {
		return "acclimatiser";
	}
}
