// 
// Decompiled by Procyon v0.5.30
// 

package binnie.extratrees.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import binnie.extratrees.ExtraTrees;
import net.minecraft.client.renderer.texture.IIconRegister;
import binnie.extratrees.api.IToolHammer;
import net.minecraft.item.Item;

public class ItemSetSquare extends Item implements IToolHammer
{
	EnumSetSquareMode mode;

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(final IIconRegister register) {
		this.itemIcon = ExtraTrees.proxy.getIcon(register, "setSquare" + this.mode.ordinal());
	}

	public ItemSetSquare(final EnumSetSquareMode mode) {
		this.mode = EnumSetSquareMode.Rotate;
		this.mode = mode;
		this.setCreativeTab(CreativeTabs.tabTools);
		this.setUnlocalizedName("setSquare" + mode);
		this.setMaxStackSize(1);
	}

	@Override
	public String getItemStackDisplayName(final ItemStack i) {
		return "Set Square";
	}

	@Override
	public boolean isActive(final ItemStack item) {
		return this.mode == EnumSetSquareMode.Rotate;
	}

	@Override
	public void onHammerUsed(final ItemStack item, final EntityPlayer player) {
	}

	public enum EnumSetSquareMode
	{
		Rotate,
		Edit,
		Swap;
	}
}
