package binnie.genetics.machine.lab;

import binnie.core.machines.IMachine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.component.IInteraction;
import binnie.core.api.gui.IGuiItem;
import binnie.core.machines.network.INetwork;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ComponentGUIHolder extends MachineComponent implements INetwork.TilePacketSync, IInteraction.RightClick {
	public ComponentGUIHolder(final IMachine machine) {
		super(machine);
	}

	public ItemStack getStack() {
		IInventory inventory = getMachine().getMachineUtil().getInventory();
		return inventory.getStackInSlot(0);
	}

	private void setStack(ItemStack itemStack) {
		IInventory inventory = getMachine().getMachineUtil().getInventory();
		inventory.setInventorySlotContents(0, itemStack);
	}

	@Override
	public void onDestruction() {
		super.onDestruction();
		ItemStack stack = getStack();
		if (!stack.isEmpty()) {
			final float f = this.getMachine().getWorld().rand.nextFloat() * 0.8f + 0.1f;
			final float f2 = this.getMachine().getWorld().rand.nextFloat() * 0.8f + 0.1f;
			final float f3 = this.getMachine().getWorld().rand.nextFloat() * 0.8f + 0.1f;
			BlockPos pos = this.getMachine().getTileEntity().getPos();
			final EntityItem entityitem = new EntityItem(this.getMachine().getWorld(), pos.getX() + f, pos.getY() + f2, pos.getZ() + f3, stack.copy());
			final float accel = 0.05f;
			entityitem.motionX = (float) this.getMachine().getWorld().rand.nextGaussian() * accel;
			entityitem.motionY = (float) this.getMachine().getWorld().rand.nextGaussian() * accel + 0.2f;
			entityitem.motionZ = (float) this.getMachine().getWorld().rand.nextGaussian() * accel;
			this.getMachine().getWorld().spawnEntity(entityitem);
		}
	}

	@Override
	public void onRightClick(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack heldItem = player.getHeldItem(hand);
		ItemStack stack = this.getStack();
		if (stack.isEmpty()) {
			if (!heldItem.isEmpty()) {
				setStack(heldItem.splitStack(1));
				world.markBlockRangeForRenderUpdate(pos, pos);
			}
		} else {
			if (player.isSneaking()) {
				if (heldItem.isEmpty()) {
					player.setHeldItem(hand, stack);
					setStack(ItemStack.EMPTY);
					world.markBlockRangeForRenderUpdate(pos, pos);
				}
			} else if (stack.getItem() instanceof IGuiItem) {
				IGuiItem labStandItem = (IGuiItem) stack.getItem();
				labStandItem.openGuiOnRightClick(stack, world, player);
			}
		}
	}

	@Override
	public void syncToNBT(NBTTagCompound nbt) {
		ItemStack stack = getStack();
		nbt.setTag("Item", stack.serializeNBT());
	}

	@Override
	public void syncFromNBT(NBTTagCompound nbt) {
		ItemStack stack = new ItemStack(nbt.getCompoundTag("Item"));
		setStack(stack);
	}
}
