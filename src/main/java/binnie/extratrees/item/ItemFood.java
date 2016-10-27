package binnie.extratrees.item;

import binnie.core.item.IItemMiscProvider;
import forestry.api.core.Tabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemFood extends net.minecraft.item.ItemFood {
	IItemMiscProvider[] items;

	public ItemFood() {
		super(0, 0.0f, false);
		this.setUnlocalizedName("food");
		this.setCreativeTab(Tabs.tabArboriculture);
		this.setHasSubtypes(true);
		this.items = Food.values();
		setRegistryName("food");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(final Item par1, final CreativeTabs par2CreativeTabs, final List par3List) {
		for (final IItemMiscProvider item : this.items) {
			if (item.isActive()) {
				par3List.add(this.getStack(item, 1));
			}
		}
	}

	private IItemMiscProvider getItem(final int damage) {
		return (damage >= this.items.length) ? this.items[0] : this.items[damage];
	}

	public ItemStack getStack(final IItemMiscProvider type, final int size) {
		return new ItemStack(this, size, type.ordinal());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		super.addInformation(stack, playerIn, tooltip, advanced);
		final IItemMiscProvider item = this.getItem(stack.getItemDamage());
		if (item != null) {
			item.addInformation(tooltip);
		}
	}

	@Override
	public String getItemStackDisplayName(final ItemStack stack) {
		final IItemMiscProvider item = this.getItem(stack.getItemDamage());
		return (item != null) ? item.getName(stack) : "null";
	}

//	@Override
//	public IIcon getIcon(final ItemStack stack, final int pass) {
//		final IItemMisc item = this.getItem(stack.getItemDamage());
//		return (item != null) ? item.getIcon(stack) : null;
//	}
//
//	@Override
//	public IIcon getIconFromDamage(final int damage) {
//		final IItemMisc item = this.getItem(damage);
//		return (item != null) ? item.getIcon(null) : null;
//	}
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(final IIconRegister register) {
//		for (final IItemMisc item : this.items) {
//			if (item.isActive()) {
//				item.registerIcons(register);
//			}
//		}
//	}

	private Food getFood(final ItemStack par1ItemStack) {
		return Food.values()[par1ItemStack.getItemDamage()];
	}

//	@Override
//	public int func_150905_g(final ItemStack p_150905_1_) {
//		return this.getFood(p_150905_1_).getHealth();
//	}
//
//	@Override
//	public float func_150906_h(final ItemStack p_150906_1_) {
//		return 3.0f;
//	}
}
