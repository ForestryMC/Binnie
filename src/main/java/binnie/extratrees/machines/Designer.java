package binnie.extratrees.machines;

import javax.annotation.Nullable;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import binnie.core.gui.minecraft.IMachineInformation;
import binnie.core.machines.Machine;
import binnie.core.machines.component.ComponentRecipe;
import binnie.core.machines.component.IComponentRecipe;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.network.INetwork;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IErrorStateSource;
import binnie.extratrees.api.CarpentryManager;
import binnie.extratrees.api.IDesign;
import binnie.extratrees.api.IDesignMaterial;
import binnie.extratrees.carpentry.EnumDesign;
import binnie.extratrees.core.ExtraTreesGUID;

public abstract class Designer {
	public static int beeswaxSlot = 0;
	public static int design1Slot = 1;
	public static int design2Slot = 2;

	public static class PackageWoodworker extends PackageCarpenter {
		public PackageWoodworker() {
			super(DesignerType.Woodworker);
		}
	}

	public static class PackagePanelworker extends PackageCarpenter {
		public PackagePanelworker() {
			super(DesignerType.Panelworker);
		}
	}

	public static class PackageGlassworker extends PackageCarpenter {
		public PackageGlassworker() {
			super(DesignerType.GlassWorker);
		}
	}

	public static class PackageTileworker extends PackageCarpenter {
		public PackageTileworker() {
			super(DesignerType.Tileworker);
		}
	}

	public abstract static class PackageCarpenter extends ExtraTreeMachine.PackageExtraTreeMachine implements IMachineInformation {
		DesignerType type;

		public PackageCarpenter(final DesignerType type) {
			super(type.name, type.texture, false);
			this.type = type;
		}

		@Override
		public void createMachine(final Machine machine) {
			new ExtraTreeMachine.ComponentExtraTreeGUI(machine, ExtraTreesGUID.Woodworker);
			final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
			inventory.addSlot(Designer.beeswaxSlot, "polish").setValidator(new Validators.SlotValidatorBeeswax(this.type));
			inventory.addSlot(Designer.design1Slot, "wood").setValidator(new Validators.SlotValidatorPlanks(this.type));
			inventory.addSlot(Designer.design2Slot, "wood").setValidator(new Validators.SlotValidatorPlanks(this.type));
			new ComponentWoodworkerRecipe(machine, this.type);
		}
	}

	public static class ComponentWoodworkerRecipe extends ComponentRecipe implements IComponentRecipe, INetwork.GuiNBT, IErrorStateSource {
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
			final ItemStack plank1 = this.getUtil().getStack(Designer.design1Slot);
			final ItemStack plank2 = this.getUtil().getStack(Designer.design2Slot);
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
				final ItemStack a = this.getUtil().decreaseStack(Designer.design1Slot, 1);
				if (a.isEmpty()) {
					this.getUtil().decreaseStack(Designer.design2Slot, 1);
				} else if (this.design != EnumDesign.Blank) {
					this.getUtil().decreaseStack(Designer.design2Slot, 1);
				}
				this.getUtil().decreaseStack(Designer.beeswaxSlot, 1);
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
			if (this.getUtil().isSlotEmpty(Designer.beeswaxSlot)) {
				return new ErrorState.NoItem("Requires Adhesive to Function", Designer.beeswaxSlot);
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

		public DesignerType getDesignerType() {
			return this.type;
		}
	}
}
