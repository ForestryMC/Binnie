package binnie.extratrees.item;

import binnie.extratrees.ExtraTrees;
import binnie.extratrees.core.ExtraTreesGUID;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

public class ItemDictionary extends Item {
	protected IIcon iconMaster;

	public ItemDictionary() {
		setCreativeTab(Tabs.tabArboriculture);
		setUnlocalizedName("database");
		setMaxStackSize(1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		itemIcon = ExtraTrees.proxy.getIcon(register, "arboristDatabase");
		iconMaster = ExtraTrees.proxy.getIcon(register, "masterArboristDatabase");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int par1) {
		return (par1 == 0) ? itemIcon : iconMaster;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		if (par1ItemStack.getItemDamage() > 0) {
			par3List.add("Sengir-in-a-can");
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		super.getSubItems(item, tab, list);
		list.add(new ItemStack(item, 1, 1));
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (stack.getItemDamage() == 0) {
			ExtraTrees.proxy.openGui(ExtraTreesGUID.Database, player, (int) player.posX, (int) player.posY, (int) player.posZ);
		} else {
			ExtraTrees.proxy.openGui(ExtraTreesGUID.DatabaseNEI, player, (int) player.posX, (int) player.posY, (int) player.posZ);
		}
		return stack;
	}

	@Override
	public String getItemStackDisplayName(ItemStack i) {
		return (i.getItemDamage() == 0) ?
			"Arborist Database" :
			"Master Arborist Database";
	}
}
