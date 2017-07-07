package binnie.extrabees.client.gui;

import javax.annotation.Nonnull;
import java.awt.Dimension;

import forestry.core.utils.SlotUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.items.IItemHandlerModifiable;

import binnie.extrabees.ExtraBees;
import binnie.extrabees.alveary.EnumAlvearyLogicType;
import binnie.extrabees.utils.ExtraBeesResourceLocation;

public abstract class AbstractAlvearyContainer extends Container {

	protected static final Dimension DEFAULT_DIMENSION = new Dimension(176, 144);
	public final Dimension dimension;
	protected final EntityPlayer player;
	protected final IItemHandlerModifiable inv;
	protected ResourceLocation background;
	protected int offset = 0;
	protected String tooltip, title;

	public AbstractAlvearyContainer(EntityPlayer player, IItemHandlerModifiable inv, EnumAlvearyLogicType type, Dimension dimension) {
		this.player = player;
		this.inv = inv;
		this.dimension = dimension;
		String base = "extrabees.machine.alveay." + type.getName();
		title = ExtraBees.proxy.localiseWithOutPrefix(base + ".name");
		tooltip = ExtraBees.proxy.localiseWithOutPrefix(base + ".info");
		background = new ExtraBeesResourceLocation("textures/gui/gui" + type.getName() + ".png");
		setupContainer();
	}

	protected abstract void setupContainer();

	protected final void addPlayerInventory() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, (84 + this.offset) + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142 + this.offset));
		}
	}

	@Override
	public boolean canInteractWith(@Nonnull EntityPlayer playerIn) {
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		return SlotUtil.transferStackInSlot(this.inventorySlots, playerIn, index);
	}
}
