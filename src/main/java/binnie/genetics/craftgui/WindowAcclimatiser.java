package binnie.genetics.craftgui;

import binnie.core.AbstractMod;
import binnie.core.craftgui.geometry.Position;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlEnergyBar;
import binnie.core.craftgui.minecraft.control.ControlErrorState;
import binnie.core.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.core.craftgui.minecraft.control.ControlSlot;
import binnie.core.craftgui.minecraft.control.ControlSlotArray;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.StandardTexture;
import binnie.extrabees.core.ExtraBeeTexture;
import binnie.genetics.Genetics;
import binnie.genetics.machine.acclimatiser.Acclimatiser;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public class WindowAcclimatiser extends WindowMachine {
	public static int[] slotReserve = new int[]{0, 1, 2, 3};
	public static int slotTarget = 4;
	public static int[] slotAcclimatiser = new int[]{5, 6, 7};
	public static int[] slotDone = new int[]{8, 9, 10, 11};

	protected static Texture ProgressBase = new StandardTexture(64, 0, 130, 21, ExtraBeeTexture.GUIProgress.getTexture());
	protected static Texture Progress = new StandardTexture(64, 21, 130, 21, ExtraBeeTexture.GUIProgress.getTexture());

	public WindowAcclimatiser(EntityPlayer player, IInventory inventory, Side side) {
		super(280, 198, player, inventory, side);
	}

	public static Window create(EntityPlayer player, IInventory inventory, Side side) {
		return new WindowAcclimatiser(player, inventory, side);
	}

	@Override
	public void initialiseClient() {
		setTitle("Acclimatiser");
		int x = 16;
		int y = 32;
		new ControlSlotArray(this, x, y, 2, 2).create(Acclimatiser.SLOT_RESERVE);
		x += 54;
		new ControlSlot(this, x + 18, y).assign(4);
		new ControlSlotArray(this, x, y + 18 + 18, 3, 1).create(Acclimatiser.SLOT_ACCLIMATISER);
		x += 72;
		new ControlSlotArray(this, x, y, 2, 2).create(Acclimatiser.SLOT_DONE);
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
}
