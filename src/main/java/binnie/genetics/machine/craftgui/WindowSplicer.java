package binnie.genetics.machine.craftgui;

import binnie.core.AbstractMod;
import binnie.craftgui.core.geometry.CraftGUIUtil;
import binnie.craftgui.core.geometry.Position;
import binnie.craftgui.core.geometry.TextJustification;
import binnie.craftgui.minecraft.GUIIcon;
import binnie.craftgui.minecraft.control.ControlEnergyBar;
import binnie.craftgui.minecraft.control.ControlErrorState;
import binnie.craftgui.minecraft.control.ControlIconDisplay;
import binnie.craftgui.minecraft.control.ControlPlayerInventory;
import binnie.craftgui.minecraft.control.ControlSlot;
import binnie.craftgui.minecraft.control.ControlSlotArray;
import binnie.craftgui.resource.Texture;
import binnie.craftgui.resource.minecraft.StandardTexture;
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.machine.inoculator.Inoculator;
import binnie.genetics.machine.splicer.Splicer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
		new ControlSplicerProgress(this, 84, 32, this.w() - 172, 102);
		CraftGUIUtil.horizontalGrid(x, 62,
				new ControlSlotArray.Builder(this, 0, 0, 2, 1).create(Splicer.SLOT_SERUM_RESERVE),
				new ControlIconDisplay(this, 0, 0, GUIIcon.ArrowRight.getIcon().getResourceLocation()),
				new ControlSlot.Builder(this, 0, 0).assign(0)
		);
		new ControlSlotArray.Builder(this, x + 12, 84, 2, 1).create(Splicer.SLOT_SERUM_EXPENDED);
		new ControlIconDisplay(this, x + 12 + 36 + 4, 86, GUIIcon.ArrowUpLeft.getIcon().getResourceLocation());
		new ControlEnergyBar(this, 196, 64, 60, 16, Position.Left);
		new ControlErrorState(this, 218, 86);
		x += 142;
		CraftGUIUtil.verticalGrid((this.w() - 72) / 2, 32, TextJustification.MiddleCenter, 4,
				new ControlSlotArray.Builder(this, 0, 0, 4, 1).create(Inoculator.SLOT_RESERVE),
				new ControlIconDisplay(this, 0, 0, GUIIcon.ArrowDown.getIcon().getResourceLocation()),
				new ControlSlot.Builder(this, 0, 0).assign(9),
				new ControlIconDisplay(this, 0, 0, GUIIcon.ArrowDown.getIcon().getResourceLocation()),
				new ControlSlotArray.Builder(this, 0, 0, 4, 1).create(Inoculator.SLOT_FINISHED)
		);
		new ControlPlayerInventory(this);
	}

	@Override
	public String getTitle() {
		return Genetics.proxy.localise("machine.advMachine.splicer");
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
