// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.machines;

import java.util.Map;
import binnie.core.machines.power.ErrorState;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.relauncher.Side;
import binnie.extratrees.api.IDesignMaterial;
import net.minecraft.item.ItemStack;
import binnie.extratrees.api.CarpentryManager;
import net.minecraft.nbt.NBTTagCompound;
import binnie.extratrees.carpentry.EnumDesign;
import binnie.extratrees.api.IDesign;
import binnie.core.machines.power.IErrorStateSource;
import binnie.core.machines.network.INetwork;
import binnie.core.machines.component.IComponentRecipe;
import binnie.core.machines.component.ComponentRecipe;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.extratrees.core.ExtraTreesGUID;
import binnie.core.machines.Machine;
import binnie.craftgui.minecraft.IMachineInformation;

public abstract class Designer
{
	public static int beeswaxSlot;
	public static int design1Slot;
	public static int design2Slot;

	static {
		Designer.beeswaxSlot = 0;
		Designer.design1Slot = 1;
		Designer.design2Slot = 2;
	}

	public static class PackageWoodworker extends PackageCarpenter
	{
		public PackageWoodworker() {
			super(DesignerType.Woodworker);
		}
	}

	public static class PackagePanelworker extends PackageCarpenter
	{
		public PackagePanelworker() {
			super(DesignerType.Panelworker);
		}
	}

	public static class PackageGlassworker extends PackageCarpenter
	{
		public PackageGlassworker() {
			super(DesignerType.GlassWorker);
		}
	}

	public static class PackageTileworker extends PackageCarpenter
	{
		public PackageTileworker() {
			super(DesignerType.Tileworker);
		}
	}

	public abstract static class PackageCarpenter extends ExtraTreeMachine.PackageExtraTreeMachine implements IMachineInformation
	{
		DesignerType type;

		public PackageCarpenter(final DesignerType type) {
			super(type.name, type.texture, false);
			this.type = type;
		}

		@Override
		public void createMachine(final Machine machine) {
			new ExtraTreeMachine.ComponentExtraTreeGUI(machine, ExtraTreesGUID.Woodworker);
			final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
			inventory.addSlot(Designer.beeswaxSlot, "polish");
			inventory.addSlot(Designer.design1Slot, "wood");
			inventory.addSlot(Designer.design2Slot, "wood");
			inventory.getSlot(Designer.beeswaxSlot).setValidator(new Validators.SlotValidatorBeeswax(this.type));
			inventory.getSlot(Designer.design1Slot).setValidator(new Validators.SlotValidatorPlanks(this.type));
			inventory.getSlot(Designer.design2Slot).setValidator(new Validators.SlotValidatorPlanks(this.type));
			new ComponentWoodworkerRecipe(machine, this.type);
		}
	}

	public static class ComponentWoodworkerRecipe extends ComponentRecipe implements IComponentRecipe, INetwork.GuiNBT, IErrorStateSource
	{
		public DesignerType type;
		private IDesign design;

		public ComponentWoodworkerRecipe(final Machine machine, final DesignerType type) {
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
		public void writeToNBT(final NBTTagCompound nbttagcompound) {
			super.writeToNBT(nbttagcompound);
			nbttagcompound.setInteger("design", CarpentryManager.carpentryInterface.getDesignIndex(this.design));
		}

		@Override
		public boolean isRecipe() {
			return this.getProduct() != null;
		}

		@Override
		public ItemStack getProduct() {
			final ItemStack plank1 = this.getUtil().getStack(Designer.design1Slot);
			final ItemStack plank2 = this.getUtil().getStack(Designer.design2Slot);
			if (plank1 == null || plank2 == null) {
				return null;
			}
			final IDesignMaterial type1 = this.type.getSystem().getMaterial(plank1);
			final IDesignMaterial type2 = this.type.getSystem().getMaterial(plank2);
			final IDesign design = this.getDesign();
			final ItemStack stack = this.type.getBlock(type1, type2, design);
			return stack;
		}

		@Override
		public ItemStack doRecipe(final boolean takeItem) {
			if (!this.isRecipe()) {
				return null;
			}
			if (this.canWork() != null) {
				return null;
			}
			final ItemStack product = this.getProduct();
			if (takeItem) {
				final ItemStack a = this.getUtil().decreaseStack(Designer.design1Slot, 1);
				if (a == null) {
					this.getUtil().decreaseStack(Designer.design2Slot, 1);
				}
				else if (this.design != EnumDesign.Blank) {
					this.getUtil().decreaseStack(Designer.design2Slot, 1);
				}
				this.getUtil().decreaseStack(Designer.beeswaxSlot, 1);
			}
			return product;
		}

		public IDesign getDesign() {
			return this.design;
		}

		@Override
		public void recieveGuiNBT(final Side side, final EntityPlayer player, final String name, final NBTTagCompound nbt) {
			if (name.equals("recipe")) {
				final InventoryPlayer playerInv = player.inventory;
				final ItemStack recipe = this.doRecipe(false);
				if (recipe == null) {
					return;
				}
				if (playerInv.getItemStack() == null) {
					playerInv.setItemStack(this.doRecipe(true));
				}
				else if (playerInv.getItemStack().isItemEqual(recipe) && ItemStack.areItemStackTagsEqual(playerInv.getItemStack(), recipe)) {
					final int fit = recipe.getMaxStackSize() - (recipe.stackSize + playerInv.getItemStack().stackSize);
					if (fit >= 0) {
						final ItemStack doRecipe;
						final ItemStack rec = doRecipe = this.doRecipe(true);
						doRecipe.stackSize += playerInv.getItemStack().stackSize;
						playerInv.setItemStack(rec);
					}
				}
				player.openContainer.detectAndSendChanges();
				if (player instanceof EntityPlayerMP) {
					((EntityPlayerMP) player).updateHeldItem();
				}
			}
			else if (name.equals("design")) {
				this.setDesign(CarpentryManager.carpentryInterface.getDesign(nbt.getShort("d")));
			}
		}

		private void setDesign(final IDesign design) {
			this.design = design;
		}

		@Override
		public ErrorState canWork() {
			if (this.getUtil().isSlotEmpty(Designer.beeswaxSlot)) {
				return new ErrorState.NoItem("Requires Adhesive to Function", Designer.beeswaxSlot);
			}
			return null;
		}

		@Override
		public ErrorState canProgress() {
			return null;
		}

		@Override
		public void sendGuiNBT(final Map<String, NBTTagCompound> nbts) {
			final NBTTagCompound tag = new NBTTagCompound();
			tag.setShort("d", (short) CarpentryManager.carpentryInterface.getDesignIndex(this.getDesign()));
			nbts.put("design", tag);
		}

		public DesignerType getDesignerType() {
			return this.type;
		}
	}
}
