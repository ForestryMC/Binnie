package binnie.extratrees.machines.designer.window;

import javax.annotation.Nullable;
import java.util.Map;

import binnie.extratrees.machines.designer.IDesignerType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import binnie.core.machines.Machine;
import binnie.core.machines.component.ComponentRecipe;
import binnie.core.machines.component.IComponentRecipe;
import binnie.core.machines.errors.ErrorState;
import binnie.core.machines.errors.IErrorStateSource;
import binnie.core.machines.network.INetwork;
import binnie.extratrees.api.CarpentryManager;
import binnie.extratrees.api.IDesign;
import binnie.extratrees.api.IDesignMaterial;
import binnie.extratrees.carpentry.EnumDesign;
import binnie.extratrees.machines.ExtraTreesErrorCode;
import binnie.extratrees.machines.designer.Designer;

public class ComponentDesignerRecipe extends ComponentRecipe implements IComponentRecipe, INetwork.GuiNBT, IErrorStateSource {
	public IDesignerType type;
	private IDesign design;

	public ComponentDesignerRecipe(final Machine machine, final IDesignerType type) {
		super(machine);
		this.design = EnumDesign.Diamond;
		this.type = type;
	}

	@Override
	public void readFromNBT(final NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		this.setDesign(CarpentryManager.carpentryInterface.getDesign(nbttagcompound.getInteger("design")));
	}

	@Override
	public NBTTagCompound writeToNBT(final NBTTagCompound nbttagcompound1) {
		NBTTagCompound nbttagcompound = super.writeToNBT(nbttagcompound1);
		nbttagcompound.setInteger("design", CarpentryManager.carpentryInterface.getDesignIndex(this.design));
		return nbttagcompound;
	}

	@Override
	public boolean isRecipe() {
		return !this.getProduct().isEmpty();
	}

	@Override
	public ItemStack getProduct() {
		final ItemStack plank1 = this.getUtil().getStack(Designer.DESIGN_SLOT_1);
		final ItemStack plank2 = this.getUtil().getStack(Designer.DESIGN_SLOT_2);
		if (plank1.isEmpty() || plank2.isEmpty()) {
			return ItemStack.EMPTY;
		}
		final IDesignMaterial type1 = this.type.getSystem().getMaterial(plank1);
		final IDesignMaterial type2 = this.type.getSystem().getMaterial(plank2);
		final IDesign design = this.getDesign();
		return this.type.getBlock(type1, type2, design);
	}

	@Override
	public ItemStack doRecipe(final boolean takeItem) {
		if (!this.isRecipe()) {
			return ItemStack.EMPTY;
		}
		if (this.canWork() != null) {
			return ItemStack.EMPTY;
		}
		final ItemStack product = this.getProduct();
		if (takeItem) {
			final ItemStack a = this.getUtil().decreaseStack(Designer.DESIGN_SLOT_1, 1);
			if (a.isEmpty()) {
				this.getUtil().decreaseStack(Designer.DESIGN_SLOT_2, 1);
			} else if (this.design != EnumDesign.Blank) {
				this.getUtil().decreaseStack(Designer.DESIGN_SLOT_2, 1);
			}
			this.getUtil().decreaseStack(Designer.BEESWAX_SLOT, 1);
		}
		return product;
	}

	public IDesign getDesign() {
		return this.design;
	}

	private void setDesign(final IDesign design) {
		this.design = design;
	}

	@Override
	@Nullable
	public ErrorState canWork() {
		if (this.getUtil().isSlotEmpty(Designer.BEESWAX_SLOT)) {
			return new ErrorState(ExtraTreesErrorCode.DESIGNER_NO_ADHESIVE, Designer.BEESWAX_SLOT);
		}
		return null;
	}

	@Override
	@Nullable
	public ErrorState canProgress() {
		return null;
	}

	@Override
	public void sendGuiNBTToClient(final Map<String, NBTTagCompound> nbt) {
		final NBTTagCompound tag = new NBTTagCompound();
		tag.setShort("d", (short) CarpentryManager.carpentryInterface.getDesignIndex(this.getDesign()));
		nbt.put("design", tag);
	}

	@Override
	public void receiveGuiNBTOnClient(EntityPlayer player, String name, NBTTagCompound nbt) {
		if (name.equals("design")) {
			this.setDesign(CarpentryManager.carpentryInterface.getDesign(nbt.getShort("d")));
		}
	}

	@Override
	public void receiveGuiNBTOnServer(final EntityPlayer player, final String name, final NBTTagCompound nbt) {
		if (name.equals("recipe")) {
			final InventoryPlayer playerInv = player.inventory;
			final ItemStack recipe = this.doRecipe(false);
			if (!recipe.isEmpty()) {
				if (playerInv.getItemStack().isEmpty()) {
					playerInv.setItemStack(this.doRecipe(true));
				} else if (playerInv.getItemStack().isItemEqual(recipe) && ItemStack.areItemStackTagsEqual(playerInv.getItemStack(), recipe)) {
					final int fit = recipe.getMaxStackSize() - (recipe.getCount() + playerInv.getItemStack().getCount());
					if (fit >= 0) {
						this.doRecipe(true);
						recipe.grow(playerInv.getItemStack().getCount());
						playerInv.setItemStack(recipe);
					}
				}
				player.openContainer.detectAndSendChanges();
				if (player instanceof EntityPlayerMP) {
					((EntityPlayerMP) player).updateHeldItem();
				}
			}
		} else if (name.equals("design")) {
			setDesign(CarpentryManager.carpentryInterface.getDesign(nbt.getInteger("d")));
		}
	}

	public IDesignerType getDesignerType() {
		return this.type;
	}
}