package binnie.craftgui.genetics.machine;

import binnie.core.AbstractMod;
import binnie.craftgui.core.geometry.Position;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.minecraft.control.ControlEnergyBar;
import binnie.craftgui.minecraft.control.ControlErrorState;
import binnie.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.craftgui.minecraft.control.ControlSlot;
import binnie.craftgui.minecraft.control.ControlSlotArray;
import binnie.craftgui.resource.Texture;
import binnie.craftgui.resource.minecraft.StandardTexture;
import binnie.extrabees.core.ExtraBeeTexture;
import binnie.genetics.Genetics;
import binnie.genetics.machine.acclimatiser.Acclimatiser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;

public class WindowAcclimatiser extends WindowMachine {
	static Texture ProgressBase = new StandardTexture(64, 0, 130, 21, ExtraBeeTexture.GUIProgress.getTexture());
	static Texture Progress = new StandardTexture(64, 21, 130, 21, ExtraBeeTexture.GUIProgress.getTexture());
	public static final int[] slotReserve = new int[]{0, 1, 2, 3};
	public static final int slotTarget = 4;
	public static final int[] slotAcclimatiser = new int[]{5, 6, 7};
	public static final int[] slotDone = new int[]{8, 9, 10, 11};

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
		new ControlSlotArray(this, x, y, 2, 2).create(Acclimatiser.SLOT_RESERVE);
		x += 54;
		new ControlSlot(this, x + 18, y).assign(4);
		new ControlSlotArray(this, x, y + 18 + 18, 3, 1).create(Acclimatiser.SLOT_ACCLIMATISER);
		x += 72;
		new ControlSlotArray(this, x, y, 2, 2).create(Acclimatiser.SLOT_DRONE);
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
