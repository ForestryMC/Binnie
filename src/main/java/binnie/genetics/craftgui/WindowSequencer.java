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
import binnie.core.craftgui.minecraft.control.ControlSlotCharge;
import binnie.core.craftgui.geometry.IArea;
import binnie.core.craftgui.geometry.CraftGUIUtil;
import binnie.core.craftgui.minecraft.control.ControlSlot;
import binnie.core.craftgui.minecraft.control.ControlIconDisplay;
import binnie.core.craftgui.minecraft.GUIIcon;
import binnie.genetics.machine.Sequencer;
import binnie.core.craftgui.minecraft.control.ControlSlotArray;
import binnie.core.craftgui.geometry.TextJustification;
import net.minecraft.nbt.NBTTagCompound;
import binnie.core.craftgui.minecraft.Window;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.inventory.IInventory;
import net.minecraft.entity.player.EntityPlayer;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.resource.Texture;
import net.minecraft.util.EnumChatFormatting;

public class WindowSequencer extends WindowMachine
{
	static Texture ProgressBase;
	static Texture Progress;
	ControlText slotText;

	public static Window create(final EntityPlayer player, final IInventory inventory, final Side side) {
		return new WindowSequencer(player, inventory, side);
	}

	public WindowSequencer(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(226, 224, player, inventory, side);
	}

	@Override
	public void recieveGuiNBT(final Side side, final EntityPlayer player, final String name, final NBTTagCompound nbt) {
		if (side == Side.CLIENT && name.equals("username")) {
			this.slotText.setValue(EnumChatFormatting.DARK_GRAY + "Genes will be sequenced by " + nbt.getString("username"));
		}
		super.recieveGuiNBT(side, player, name, nbt);
	}

	@Override
	public void initialiseClient() {
		this.setTitle("Sequencer");
		int x = 16;
		int y = 32;
		CraftGUIUtil.horizontalGrid(x, y, TextJustification.MiddleCenter, 2.0f, new ControlSlotArray(this, 0, 0, 2, 2).create(Sequencer.slotReserve), new ControlIconDisplay(this, 0.0f, 0.0f, GUIIcon.ArrowRight.getIcon()), new ControlSequencerProgress(this, 0, 0), new ControlIconDisplay(this, 0.0f, 0.0f, GUIIcon.ArrowRight.getIcon()), new ControlSlot(this, 0.0f, 0.0f).assign(6));
		final ControlSlot slotTarget = new ControlSlot(this, x + 96, y + 16);
		slotTarget.assign(5);
		x = 34;
		y = 92;
		this.slotText = new ControlText(this, new IArea(0.0f, y, this.w(), 12.0f), EnumChatFormatting.DARK_GRAY + "Userless. Will not save sequences", TextJustification.MiddleCenter);
		y += 20;
		final ControlSlot slotDye = new ControlSlot(this, x, y);
		slotDye.assign(0);
		x += 20;
		new ControlSlotCharge(this, x, y, 0).setColor(16750848);
		x += 32;
		new ControlEnergyBar(this, x, y, 60, 16, Position.Left);
		x += 92;
		final ControlErrorState errorState = new ControlErrorState(this, x, y + 1);
		new ControlPlayerInventory(this);
	}

	@Override
	public String getTitle() {
		return "Incubator";
	}

	@Override
	protected AbstractMod getMod() {
		return Genetics.instance;
	}

	@Override
	protected String getName() {
		return "Sequencer";
	}

	static {
		WindowSequencer.ProgressBase = new StandardTexture(64, 114, 98, 9, ExtraBeeTexture.GUIProgress.getTexture());
		WindowSequencer.Progress = new StandardTexture(64, 123, 98, 9, ExtraBeeTexture.GUIProgress.getTexture());
	}
}
