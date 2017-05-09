package binnie.genetics.craftgui;

import binnie.core.AbstractMod;
import binnie.core.craftgui.geometry.CraftGUIUtil;
import binnie.core.craftgui.geometry.Position;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.craftgui.minecraft.GUIIcon;
import binnie.core.craftgui.minecraft.Window;
import binnie.core.craftgui.minecraft.control.ControlEnergyBar;
import binnie.core.craftgui.minecraft.control.ControlErrorState;
import binnie.core.craftgui.minecraft.control.ControlIconDisplay;
import binnie.core.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.core.craftgui.minecraft.control.ControlSlot;
import binnie.core.craftgui.minecraft.control.ControlSlotArray;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.StandardTexture;
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.machine.Inoculator;
import binnie.genetics.machine.Splicer;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public class WindowSplicer extends WindowMachine {
	protected static Texture ProgressBase = new StandardTexture(0, 72, 142, 72, GeneticsTexture.GUIProcess2.getTexture());
	protected static Texture Progress = new StandardTexture(0, 0, 142, 72, GeneticsTexture.GUIProcess2.getTexture());

	public WindowSplicer(EntityPlayer player, IInventory inventory, Side side) {
		super(280, 240, player, inventory, side);
	}

	public static Window create(EntityPlayer player, IInventory inventory, Side side) {
		return new WindowSplicer(player, inventory, side);
	}

	@Override
	public void initialiseClient() {
		setTitle("Splicer");
		int x = 16;
		new ControlSplicerProgress(this, 84.0f, 32.0f, w() - 172.0f, 102.0f);
		CraftGUIUtil.horizontalGrid(x, 62.0f, new ControlSlotArray(this, 0, 0, 2, 1).create(Splicer.slotSerumReserve), new ControlIconDisplay(this, 0.0f, 0.0f, GUIIcon.ArrowRight.getIcon()), new ControlSlot(this, 0.0f, 0.0f).assign(0));
		new ControlSlotArray(this, x + 12, 84, 2, 1).create(Splicer.slotSerumExpended);
		new ControlIconDisplay(this, x + 12 + 36 + 4, 86.0f, GUIIcon.ArrowUpLeft.getIcon());
		new ControlEnergyBar(this, 196, 64, 60, 16, Position.Left);
		new ControlErrorState(this, 218.0f, 86.0f);
		CraftGUIUtil.verticalGrid((w() - 72.0f) / 2.0f, 32.0f, TextJustification.MiddleCenter, 4.0f, new ControlSlotArray(this, 0, 0, 4, 1).create(Inoculator.SLOT_RESERVE), new ControlIconDisplay(this, 0.0f, 0.0f, GUIIcon.ArrowDown.getIcon()), new ControlSlot(this, 0.0f, 0.0f).assign(9), new ControlIconDisplay(this, 0.0f, 0.0f, GUIIcon.ArrowDown.getIcon()), new ControlSlotArray(this, 0, 0, 4, 1).create(Inoculator.SLOT_FINISHED));
		new ControlPlayerInventory(this);
	}

	@Override
	public String getTitle() {
		return "Inoculator";
	}

	@Override
	protected AbstractMod getMod() {
		return Genetics.instance;
	}

	@Override
	protected String getName() {
		return "Inoculator";
	}
}
