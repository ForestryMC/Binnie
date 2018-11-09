package binnie.extrabees.client.gui;

import java.awt.Dimension;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.items.IItemHandlerModifiable;

import forestry.core.utils.SlotUtil;

import binnie.core.ModId;
import binnie.core.util.I18N;
import binnie.extrabees.alveary.EnumAlvearyLogicType;
import binnie.extrabees.utils.ExtraBeesResourceLocation;

public abstract class AlvearyContainer extends Container {

	protected static final Dimension DEFAULT_DIMENSION = new Dimension(176, 144);
	private final Dimension dimension;
	protected final EntityPlayer player;
	protected final IItemHandlerModifiable inv;
	protected final ResourceLocation background;
	protected final String tooltip;
	protected int offset;
	protected String title;

	public AlvearyContainer(EntityPlayer player, IItemHandlerModifiable inv, EnumAlvearyLogicType type, Dimension dimension) {
		this.player = player;
		this.inv = inv;
		this.dimension = dimension;
		String base = "machine.alveay." + type.getName();
		title = I18N.localise(ModId.EXTRA_BEES, base + ".name");
		tooltip = I18N.localise(ModId.EXTRA_BEES, base + ".info");
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
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		return SlotUtil.transferStackInSlot(this.inventorySlots, playerIn, index);
	}

	public Dimension getDimension() {
		return dimension;
	}
}
