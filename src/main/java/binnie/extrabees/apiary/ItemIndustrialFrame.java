// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extrabees.apiary;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.ItemStack;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.Item;

public class ItemIndustrialFrame extends Item
{
	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(final Item par1, final CreativeTabs par2CreativeTabs, final List par3List) {
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
	public void addInformation(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final List par3List, final boolean par4) {
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		final IndustrialFrame frame = getFrame(par1ItemStack);
		if (frame == null) {
			par3List.add("Invalid Contents");
		}
		else {
			par3List.add(frame.getName());
		}
	}

	@Override
	public String getItemStackDisplayName(final ItemStack par1ItemStack) {
		return "Industrial Frame";
	}

	public ItemIndustrialFrame() {
		this.setCreativeTab(CreativeTabs.tabMisc);
		this.setMaxDamage(400);
		this.setMaxStackSize(1);
		this.setUnlocalizedName("industrialFrame");
	}

	public static IndustrialFrame getFrame(final ItemStack stack) {
		if (stack == null || !stack.hasTagCompound() || !stack.getTagCompound().hasKey("frame")) {
			return null;
		}
		return IndustrialFrame.values()[stack.getTagCompound().getInteger("frame")];
	}
}
