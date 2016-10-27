package binnie.extratrees.item;

import binnie.extratrees.api.IToolHammer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemHammer extends Item implements IToolHammer {
	boolean isDurableHammer;

//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(final IIconRegister register) {
//		this.itemIcon = ExtraTrees.proxy.getIcon(register, this.isDurableHammer ? "durableHammer" : "carpentryHammer");
//	}

	public ItemHammer(final boolean durable) {
		this.isDurableHammer = false;
		this.isDurableHammer = durable;
		this.setCreativeTab(CreativeTabs.TOOLS);
		this.setUnlocalizedName(durable ? "durableHammer" : "hammer");
		this.setMaxStackSize(1);
		this.setMaxDamage(durable ? 1562 : 251);
		setRegistryName(durable ? "durableHammer" : "hammer");
	}

	@Override
	public String getItemStackDisplayName(final ItemStack i) {
		return this.isDurableHammer ? "Master Carpentry Hammer" : "Carpentry Hammer";
	}

	@Override
	public boolean isActive(final ItemStack item) {
		return true;
	}

	@Override
	public void onHammerUsed(final ItemStack item, final EntityPlayer player) {
		item.damageItem(1, player);
	}
}
