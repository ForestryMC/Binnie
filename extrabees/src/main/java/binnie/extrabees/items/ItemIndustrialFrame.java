package binnie.extrabees.items;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.extrabees.items.types.EnumIndustrialFrame;

public class ItemIndustrialFrame extends Item {

	public ItemIndustrialFrame() {
		this.setCreativeTab(CreativeTabs.MISC);
		this.setMaxDamage(400);
		this.setMaxStackSize(1);
		this.setUnlocalizedName("industrialFrame");
	}

	@Nullable
	public static EnumIndustrialFrame getFrame(final ItemStack stack) {
		NBTTagCompound tagCompound = stack.getTagCompound();
		if (tagCompound == null || !tagCompound.hasKey("frame")) {
			return null;
		}
		return EnumIndustrialFrame.values()[tagCompound.getInteger("frame")];
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			for (EnumIndustrialFrame frame : EnumIndustrialFrame.values()) {
				ItemStack stack = new ItemStack(this);
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setInteger("frame", frame.ordinal());
				stack.setTagCompound(nbt);
				items.add(stack);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		final EnumIndustrialFrame frame = getFrame(stack);
		if (frame == null) {
			tooltip.add("Invalid Contents");
		} else {
			tooltip.add(frame.getName());
		}
	}

	@Override
	public String getItemStackDisplayName(final ItemStack itemStack) {
		return "Industrial Frame";
	}
}
