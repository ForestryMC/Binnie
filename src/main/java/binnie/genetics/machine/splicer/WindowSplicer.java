package binnie.genetics.machine.splicer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.AbstractMod;
import binnie.core.craftgui.geometry.CraftGUIUtil;
import binnie.core.craftgui.geometry.Position;
import binnie.core.craftgui.geometry.TextJustification;
import binnie.core.craftgui.minecraft.GUIIcon;
import binnie.core.craftgui.minecraft.control.ControlEnergyBar;
import binnie.core.craftgui.minecraft.control.ControlErrorState;
import binnie.core.craftgui.minecraft.control.ControlIconDisplay;
import binnie.core.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.core.craftgui.minecraft.control.ControlSlot;
import binnie.core.craftgui.minecraft.control.ControlSlotArray;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.StandardTexture;
import binnie.core.util.I18N;
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.machine.craftgui.WindowMachine;
import binnie.genetics.machine.inoculator.Inoculator;

public class WindowSplicer extends WindowMachine {
	static Texture ProgressBase = new StandardTexture(0, 72, 142, 72, GeneticsTexture.GUIProcess2);
	static Texture Progress = new StandardTexture(0, 0, 142, 72, GeneticsTexture.GUIProcess2);

	public WindowSplicer(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(280, 240, player, inventory, side);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void initialiseClient() {
		super.initialiseClient();
		int x = 16;
		new ControlSplicerProgress(this, 84, 32, this.width() - 172, 102);
		CraftGUIUtil.horizontalGrid(x, 62,
			new ControlSlotArray.Builder(this, 0, 0, 2, 1).create(Splicer.SLOT_SERUM_RESERVE),
			new ControlIconDisplay(this, 0, 0, GUIIcon.ARROW_RIGHT.getIcon().getResourceLocation()),
			new ControlSlot.Builder(this, 0, 0).assign(0)
		);
		new ControlSlotArray.Builder(this, x + 12, 84, 2, 1).create(Splicer.SLOT_SERUM_EXPENDED);
		new ControlIconDisplay(this, x + 12 + 36 + 4, 86, GUIIcon.ARROW_UP_LEFT.getIcon().getResourceLocation());
		new ControlEnergyBar(this, 196, 64, 60, 16, Position.LEFT);
		new ControlErrorState(this, 218, 86);
		x += 142;
		CraftGUIUtil.verticalGrid((this.width() - 72) / 2, 32, TextJustification.MiddleCenter, 4,
			new ControlSlotArray.Builder(this, 0, 0, 4, 1).create(Inoculator.SLOT_RESERVE),
			new ControlIconDisplay(this, 0, 0, GUIIcon.ARROW_DOWN.getIcon().getResourceLocation()),
			new ControlSlot.Builder(this, 0, 0).assign(9),
			new ControlIconDisplay(this, 0, 0, GUIIcon.ARROW_DOWN.getIcon().getResourceLocation()),
			new ControlSlotArray.Builder(this, 0, 0, 4, 1).create(Inoculator.SLOT_FINISHED)
		);
		new ControlPlayerInventory(this);
	}

	@Override
	public String getTitle() {
		return I18N.localise("genetics.machine.advMachine.splicer");
	}

	@Override
	protected AbstractMod getMod() {
		return Genetics.instance;
	}

	@Override
	protected String getBackgroundTextureName() {
		return "inoculator";
	}
}
