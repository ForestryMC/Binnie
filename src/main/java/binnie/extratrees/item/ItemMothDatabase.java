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

public class ItemMothDatabase extends Item {
	protected IIcon iconMaster;

	public ItemMothDatabase() {
		setCreativeTab(Tabs.tabLepidopterology);
		setUnlocalizedName("databaseMoth");
		setMaxStackSize(1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		itemIcon = ExtraTrees.proxy.getIcon(register, "lepiDatabase");
		iconMaster = ExtraTrees.proxy.getIcon(register, "masterLepiDatabase");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int par1) {
		return (par1 == 0) ? itemIcon : iconMaster;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean advanced) {
		super.addInformation(stack, player, tooltip, advanced);
		if (stack.getItemDamage() > 0) {
			tooltip.add("Binnie's Emporium of Lepidopterans");
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
			ExtraTrees.proxy.openGui(ExtraTreesGUID.MothDatabase, player, (int) player.posX, (int) player.posY, (int) player.posZ);
		} else {
			ExtraTrees.proxy.openGui(ExtraTreesGUID.MothDatabaseNEI, player, (int) player.posX, (int) player.posY, (int) player.posZ);
		}
		return stack;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return (stack.getItemDamage() == 0) ?
			"Lepidopterist Database" :
			"Master Lepidopterist Database";
	}
}
