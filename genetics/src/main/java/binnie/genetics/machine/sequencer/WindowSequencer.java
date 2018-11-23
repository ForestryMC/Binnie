package binnie.genetics.machine.sequencer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.core.api.gui.Alignment;
import binnie.core.gui.controls.ControlText;
import binnie.core.gui.geometry.Area;
import binnie.core.gui.geometry.CraftGUIUtil;
import binnie.core.gui.geometry.TextJustification;
import binnie.core.gui.minecraft.GUIIcon;
import binnie.core.gui.minecraft.control.ControlEnergyBar;
import binnie.core.gui.minecraft.control.ControlErrorState;
import binnie.core.gui.minecraft.control.ControlIconDisplay;
import binnie.core.gui.minecraft.control.ControlPlayerInventory;
import binnie.core.gui.minecraft.control.ControlSlot;
import binnie.core.gui.minecraft.control.ControlSlotArray;
import binnie.core.gui.minecraft.control.ControlSlotCharge;
import binnie.core.gui.resource.textures.StandardTexture;
import binnie.core.gui.resource.textures.Texture;
import binnie.core.gui.window.WindowMachine;
import binnie.core.util.I18N;
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsTexture;

public class WindowSequencer extends WindowMachine {
	// TODO: why are these unused?
	private static final Texture PROGRESS_BASE = new StandardTexture(64, 114, 98, 9, GeneticsTexture.GUI_PROCESS_3);
	private static final Texture PROGRESS = new StandardTexture(64, 123, 98, 9, GeneticsTexture.GUI_PROCESS_3);

	private ControlText slotText;

	public WindowSequencer(EntityPlayer player, IInventory inventory, Side side) {
		super(226, 224, player, inventory, side);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void receiveGuiNBTOnClient(EntityPlayer player, String name, NBTTagCompound nbt) {
		if (name.equals("username")) {
			this.slotText.setValue(TextFormatting.DARK_GRAY + String.format(I18N.localise("genetics.machine.sequencer.texts.sequenced.by"), nbt.getString("username")));
		}
		super.receiveGuiNBTOnClient(player, name, nbt);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void initialiseClient() {
		super.initialiseClient();
		int x = 16;
		int y = 32;
		CraftGUIUtil.horizontalGrid(x, y, TextJustification.MIDDLE_CENTER, 2,
			new ControlSlotArray.Builder(this, 0, 0, 2, 2).create(Sequencer.SLOT_RESERVE),
			new ControlIconDisplay(this, 0, 0, GUIIcon.ARROW_RIGHT.getIcon().getResourceLocation()),
			new ControlSequencerProgress(this, 0, 0),
			new ControlIconDisplay(this, 0, 0, GUIIcon.ARROW_RIGHT.getIcon().getResourceLocation()),
			new ControlSlot.Builder(this, 0, 0).assign(6)
		);
		ControlSlot slotTarget = new ControlSlot.Builder(this, x + 96, y + 16).assign(5);
		x = 34;
		y = 92;
		this.slotText = new ControlText(this, new Area(0, y, this.getWidth(), 12), TextFormatting.DARK_GRAY + I18N.localise("genetics.machine.sequencer.texts.userless"), TextJustification.MIDDLE_CENTER);
		y += 20;
		ControlSlot slotDye = new ControlSlot.Builder(this, x, y).assign(0);
		x += 20;
		new ControlSlotCharge(this, x, y, 0).setColor(16750848);
		x += 32;
		new ControlEnergyBar(this, x, y, 60, 16, Alignment.LEFT);
		x += 92;
		ControlErrorState errorState = new ControlErrorState(this, x, y + 1);
		new ControlPlayerInventory(this);
	}

	@Override
	public String getTitle() {
		return I18N.localise("genetics.machine.sequencer");
	}

	@Override
	protected String getModId() {
		return Genetics.instance.getModId();
	}

	@Override
	protected String getBackgroundTextureName() {
		return "sequencer";
	}
}
