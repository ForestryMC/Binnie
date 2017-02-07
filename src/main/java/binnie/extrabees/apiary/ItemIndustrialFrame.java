package binnie.extrabees.apiary;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemIndustrialFrame extends Item {
//	@Override
//	@SideOnly(Side.CLIENT)
//	public boolean requiresMultipleRenderPasses() {
//		return true;
//	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(final Item par1, final CreativeTabs par2CreativeTabs, final List<ItemStack> par3List) {
		for (final IndustrialFrame frame : IndustrialFrame.values()) {
			final ItemStack stack = new ItemStack(this);
			final NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("frame", frame.ordinal());
			stack.setTagCompound(nbt);
			par3List.add(stack);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		super.addInformation(stack, playerIn, tooltip, advanced);
		final IndustrialFrame frame = getFrame(stack);
		if (frame == null) {
			tooltip.add("Invalid Contents");
		} else {
			tooltip.add(frame.getName());
		}
	}

	@Override
	public String getItemStackDisplayName(final ItemStack par1ItemStack) {
		return "Industrial Frame";
	}

	public ItemIndustrialFrame() {
		this.setCreativeTab(CreativeTabs.MISC);
		this.setMaxDamage(400);
		this.setMaxStackSize(1);
		this.setUnlocalizedName("industrialFrame");
	}

	@Nullable
	public static IndustrialFrame getFrame(final ItemStack stack) {
		NBTTagCompound tagCompound = stack.getTagCompound();
		if (tagCompound == null || !tagCompound.hasKey("frame")) {
			return null;
		}
		return IndustrialFrame.values()[tagCompound.getInteger("frame")];
	}
}
