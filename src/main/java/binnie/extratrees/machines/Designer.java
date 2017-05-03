package binnie.extratrees.machines;

import binnie.core.craftgui.minecraft.IMachineInformation;
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
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Map;

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

		public PackageCarpenter(DesignerType type) {
			super(type.name, type.texture, false);
			this.type = type;
		}

		@Override
		public void createMachine(Machine machine) {
			new ExtraTreeMachine.ComponentExtraTreeGUI(machine, ExtraTreesGUID.Woodworker);
			ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
			inventory.addSlot(Designer.beeswaxSlot, "polish");
			inventory.addSlot(Designer.design1Slot, "wood");
			inventory.addSlot(Designer.design2Slot, "wood");
			inventory.getSlot(Designer.beeswaxSlot).setValidator(new Validators.SlotValidatorBeeswax(type));
			inventory.getSlot(Designer.design1Slot).setValidator(new Validators.SlotValidatorPlanks(type));
			inventory.getSlot(Designer.design2Slot).setValidator(new Validators.SlotValidatorPlanks(type));
			new ComponentWoodworkerRecipe(machine, type);
		}
	}

	public static class ComponentWoodworkerRecipe extends ComponentRecipe implements
		IComponentRecipe,
		INetwork.GuiNBT,
		IErrorStateSource {
		public DesignerType type;

		private IDesign design;

		public ComponentWoodworkerRecipe(Machine machine, DesignerType type) {
			super(machine);
			design = EnumDesign.Diamond;
			this.type = type;
		}

		@Override
		public void readFromNBT(NBTTagCompound nbttagcompound) {
			super.readFromNBT(nbttagcompound);
			setDesign(CarpentryManager.carpentryInterface.getDesign(nbttagcompound.getInteger("design")));
		}

		@Override
		public void writeToNBT(NBTTagCompound nbttagcompound) {
			super.writeToNBT(nbttagcompound);
			nbttagcompound.setInteger("design", CarpentryManager.carpentryInterface.getDesignIndex(design));
		}

		@Override
		public boolean isRecipe() {
			return getProduct() != null;
		}

		@Override
		public ItemStack getProduct() {
			ItemStack plank1 = getUtil().getStack(Designer.design1Slot);
			ItemStack plank2 = getUtil().getStack(Designer.design2Slot);
			if (plank1 == null || plank2 == null) {
				return null;
			}

			IDesignMaterial type1 = type.getSystem().getMaterial(plank1);
			IDesignMaterial type2 = type.getSystem().getMaterial(plank2);
			IDesign design = getDesign();
			ItemStack stack = type.getBlock(type1, type2, design);
			return stack;
		}

		@Override
		public ItemStack doRecipe(boolean takeItem) {
			if (!isRecipe() || canWork() != null) {
				return null;
			}

			ItemStack product = getProduct();
			if (takeItem) {
				ItemStack a = getUtil().decreaseStack(Designer.design1Slot, 1);
				if (a == null) {
					getUtil().decreaseStack(Designer.design2Slot, 1);
				} else if (design != EnumDesign.Blank) {
					getUtil().decreaseStack(Designer.design2Slot, 1);
				}
				getUtil().decreaseStack(Designer.beeswaxSlot, 1);
			}
			return product;
		}

		public IDesign getDesign() {
			return design;
		}

		private void setDesign(IDesign design) {
			this.design = design;
		}

		@Override
		public void recieveGuiNBT(Side side, EntityPlayer player, String name, NBTTagCompound nbt) {
			if (name.equals("recipe")) {
				InventoryPlayer playerInv = player.inventory;
				ItemStack recipe = doRecipe(false);
				if (recipe == null) {
					return;
				}

				if (playerInv.getItemStack() == null) {
					playerInv.setItemStack(doRecipe(true));
				} else if (playerInv.getItemStack().isItemEqual(recipe) && ItemStack.areItemStackTagsEqual(playerInv.getItemStack(), recipe)) {
					int fit = recipe.getMaxStackSize() - (recipe.stackSize + playerInv.getItemStack().stackSize);
					if (fit >= 0) {
						ItemStack doRecipe;
						ItemStack rec = doRecipe = doRecipe(true);
						doRecipe.stackSize += playerInv.getItemStack().stackSize;
						playerInv.setItemStack(rec);
					}
				}

				player.openContainer.detectAndSendChanges();
				if (player instanceof EntityPlayerMP) {
					((EntityPlayerMP) player).updateHeldItem();
				}
			} else if (name.equals("design")) {
				setDesign(CarpentryManager.carpentryInterface.getDesign(nbt.getShort("d")));
			}
		}

		@Override
		public ErrorState canWork() {
			if (getUtil().isSlotEmpty(Designer.beeswaxSlot)) {
				return new ErrorState.NoItem("Requires Adhesive to Function", Designer.beeswaxSlot);
			}
			return null;
		}

		@Override
		public ErrorState canProgress() {
			return null;
		}

		@Override
		public void sendGuiNBT(Map<String, NBTTagCompound> nbts) {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setShort("d", (short) CarpentryManager.carpentryInterface.getDesignIndex(getDesign()));
			nbts.put("design", tag);
		}

		public DesignerType getDesignerType() {
			return type;
		}
	}
}
