package binnie.genetics.machine.sequencer;

import binnie.core.AbstractMod;
import binnie.core.ExtraBeeTexture;
import binnie.core.craftgui.controls.ControlText;
import binnie.core.craftgui.geometry.Area;
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
import binnie.core.craftgui.minecraft.control.ControlSlotCharge;
import binnie.core.craftgui.resource.Texture;
import binnie.core.craftgui.resource.minecraft.StandardTexture;
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.machine.craftgui.WindowMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WindowSequencer extends WindowMachine {
	static Texture ProgressBase = new StandardTexture(64, 114, 98, 9, GeneticsTexture.GUIProcess3);
	static Texture Progress = new StandardTexture(64, 123, 98, 9, GeneticsTexture.GUIProcess3);
	ControlText slotText;

	public WindowSequencer(final EntityPlayer player, final IInventory inventory, final Side side) {
		super(226, 224, player, inventory, side);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void receiveGuiNBTOnClient(EntityPlayer player, String name, NBTTagCompound nbt) {
		if (name.equals("username")) {
			this.slotText.setValue(TextFormatting.DARK_GRAY + String.format(Genetics.proxy.localise("machine.machine.sequencer.texts.sequenced.by"), nbt.getString("username")));
		}
		super.receiveGuiNBTOnClient(player, name, nbt);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void initialiseClient() {
		super.initialiseClient();
		int x = 16;
		int y = 32;
		CraftGUIUtil.horizontalGrid(x, y, TextJustification.MiddleCenter, 2,
			new ControlSlotArray.Builder(this, 0, 0, 2, 2).create(Sequencer.SLOT_RESERVE),
			new ControlIconDisplay(this, 0, 0, GUIIcon.ARROW_RIGHT.getIcon().getResourceLocation()),
			new ControlSequencerProgress(this, 0, 0),
			new ControlIconDisplay(this, 0, 0, GUIIcon.ARROW_RIGHT.getIcon().getResourceLocation()),
			new ControlSlot.Builder(this, 0, 0).assign(6)
		);
		final ControlSlot slotTarget = new ControlSlot.Builder(this, x + 96, y + 16).assign(5);
		x = 34;
		y = 92;
		this.slotText = new ControlText(this, new Area(0, y, this.width(), 12), TextFormatting.DARK_GRAY + Genetics.proxy.localise("machine.machine.sequencer.texts.userless"), TextJustification.MiddleCenter);
		y += 20;
		final ControlSlot slotDye = new ControlSlot.Builder(this, x, y).assign(0);
		x += 20;
		new ControlSlotCharge(this, x, y, 0).setColour(16750848);
		x += 32;
		new ControlEnergyBar(this, x, y, 60, 16, Position.LEFT);
		x += 92;
		final ControlErrorState errorState = new ControlErrorState(this, x, y + 1);
		new ControlPlayerInventory(this);
	}

	@Override
	public String getTitle() {
		return Genetics.proxy.localise("machine.machine.sequencer");
	}

	@Override
	protected AbstractMod getMod() {
		return Genetics.instance;
	}

	@Override
	protected String getBackgroundTextureName() {
		return "sequencer";
	}
}
