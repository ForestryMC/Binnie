// 
// Decompiled by Procyon v0.5.30
// 

package binnie.genetics.craftgui;

import binnie.core.craftgui.resource.minecraft.StandardTexture;
import binnie.extrabees.core.ExtraBeeTexture;
import binnie.genetics.Genetics;
import binnie.core.AbstractMod;
import binnie.core.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.core.craftgui.minecraft.control.ControlErrorState;
import binnie.core.craftgui.geometry.Position;
import binnie.core.craftgui.minecraft.control.ControlEnergyBar;
import binnie.core.craftgui.minecraft.control.ControlSlot;
import binnie.genetics.machine.Acclimatiser;
import binnie.core.craftgui.minecraft.control.ControlSlotArray;
import binnie.core.craftgui.minecraft.Window;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.inventory.IInventory;
import net.minecraft.entity.player.EntityPlayer;
import binnie.core.craftgui.resource.Texture;

public class WindowAcclimatiser extends WindowMachine
{
	static Texture ProgressBase;
	static Texture Progress;
	public static final int[] slotReserve;
	public static final int slotTarget = 4;
	public static final int[] slotAcclimatiser;
	public static final int[] slotDone;

	public static Window create(final EntityPlayer player, final IInventory inventory, final Side side) {
		return new WindowAcclimatiser(player, inventory, side);
	}

	public WindowAcclimatiser(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(280, 198, player, inventory, side);
	}

	@Override
	public void initialiseClient() {
		this.setTitle("Acclimatiser");
		int x = 16;
		final int y = 32;
		new ControlSlotArray(this, x, y, 2, 2).create(Acclimatiser.slotReserve);
		x += 54;
		new ControlSlot(this, x + 18, y).assign(4);
		new ControlSlotArray(this, x, y + 18 + 18, 3, 1).create(Acclimatiser.slotAcclimatiser);
		x += 72;
		new ControlSlotArray(this, x, y, 2, 2).create(Acclimatiser.slotDone);
		new ControlEnergyBar(this, 21, 115, 16, 60, Position.Bottom);
		new ControlErrorState(this, 181.0f, 83.0f);
		new ControlPlayerInventory(this);
	}

	@Override
	public String getTitle() {
		return "Acclimatiser";
	}

	@Override
	protected AbstractMod getMod() {
		return Genetics.instance;
	}

	@Override
	protected String getName() {
		return "Acclimatiser";
	}

	static {
		WindowAcclimatiser.ProgressBase = new StandardTexture(64, 0, 130, 21, ExtraBeeTexture.GUIProgress.getTexture());
		WindowAcclimatiser.Progress = new StandardTexture(64, 21, 130, 21, ExtraBeeTexture.GUIProgress.getTexture());
		slotReserve = new int[] { 0, 1, 2, 3 };
		slotAcclimatiser = new int[] { 5, 6, 7 };
		slotDone = new int[] { 8, 9, 10, 11 };
	}
}
