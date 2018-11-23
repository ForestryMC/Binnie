package binnie.design.gui;

import javax.annotation.Nullable;
import java.util.Map;

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
import binnie.design.Design;
import binnie.design.EnumDesign;
import binnie.design.api.IDesign;
import binnie.design.api.IDesignMaterial;
import binnie.design.api.IDesignerType;

public class ComponentDesignerRecipe extends ComponentRecipe implements IComponentRecipe, INetwork.GuiNBT, IErrorStateSource {
	private final IDesignerType type;
	private IDesign design;

	public ComponentDesignerRecipe(Machine machine, IDesignerType type) {
		super(machine);
		this.design = EnumDesign.Diamond;
		this.type = type;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.setDesign(Design.getDesignManager().getDesign(compound.getInteger("design")));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		NBTTagCompound nbttagcompound = super.writeToNBT(compound);
		nbttagcompound.setInteger("design", Design.getDesignManager().getDesignIndex(this.design));
		return nbttagcompound;
	}

	@Override
	public boolean isRecipe() {
		return !this.getProduct().isEmpty();
	}

	@Override
	public ItemStack getProduct() {
		ItemStack plank1 = this.getUtil().getStack(DesignerSlots.DESIGN_SLOT_1);
		ItemStack plank2 = this.getUtil().getStack(DesignerSlots.DESIGN_SLOT_2);
		if (plank1.isEmpty() || plank2.isEmpty()) {
			return ItemStack.EMPTY;
		}
		IDesignMaterial type1 = this.type.getSystem().getMaterial(plank1);
		IDesignMaterial type2 = this.type.getSystem().getMaterial(plank2);
		IDesign design = this.getDesign();
		return this.type.getBlock(type1, type2, design);
	}

	@Override
	public ItemStack doRecipe(boolean takeItem) {
		if (!this.isRecipe()) {
			return ItemStack.EMPTY;
		}
		if (this.canWork() != null) {
			return ItemStack.EMPTY;
		}
		ItemStack product = this.getProduct();
		if (takeItem) {
			ItemStack a = this.getUtil().decreaseStack(DesignerSlots.DESIGN_SLOT_1, 1);
			if (a.isEmpty()) {
				this.getUtil().decreaseStack(DesignerSlots.DESIGN_SLOT_2, 1);
			} else if (this.design != EnumDesign.Blank) {
				this.getUtil().decreaseStack(DesignerSlots.DESIGN_SLOT_2, 1);
			}
			this.getUtil().decreaseStack(DesignerSlots.ADHESIVE_SLOT, 1);
		}
		return product;
	}

	public IDesign getDesign() {
		return this.design;
	}

	private void setDesign(IDesign design) {
		this.design = design;
	}

	@Override
	@Nullable
	public ErrorState canWork() {
		if (this.getUtil().isSlotEmpty(DesignerSlots.ADHESIVE_SLOT)) {
			return new ErrorState(DesignErrorCode.DESIGNER_NO_ADHESIVE, DesignerSlots.ADHESIVE_SLOT);
		}
		return null;
	}

	@Override
	@Nullable
	public ErrorState canProgress() {
		return null;
	}

	@Override
	public void sendGuiNBTToClient(Map<String, NBTTagCompound> data) {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setShort("d", (short) Design.getDesignManager().getDesignIndex(this.getDesign()));
		data.put("design", tag);
	}

	@Override
	public void receiveGuiNBTOnClient(EntityPlayer player, String name, NBTTagCompound nbt) {
		if (name.equals("design")) {
			this.setDesign(Design.getDesignManager().getDesign(nbt.getShort("d")));
		}
	}

	@Override
	public void receiveGuiNBTOnServer(EntityPlayer player, String name, NBTTagCompound nbt) {
		if (name.equals("recipe")) {
			InventoryPlayer playerInv = player.inventory;
			ItemStack recipe = this.doRecipe(false);
			if (!recipe.isEmpty()) {
				if (playerInv.getItemStack().isEmpty()) {
					playerInv.setItemStack(this.doRecipe(true));
				} else if (playerInv.getItemStack().isItemEqual(recipe) && ItemStack.areItemStackTagsEqual(playerInv.getItemStack(), recipe)) {
					int fit = recipe.getMaxStackSize() - (recipe.getCount() + playerInv.getItemStack().getCount());
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
			setDesign(Design.getDesignManager().getDesign(nbt.getInteger("d")));
		}
	}

	public IDesignerType getDesignerType() {
		return this.type;
	}
}