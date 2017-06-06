package binnie.genetics.item;

import binnie.genetics.Genetics;
import binnie.genetics.GeneticsCreativeTab;
import binnie.genetics.core.GeneticsGUI;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

public class ItemDatabase extends Item {
	protected IIcon iconMaster;

	public ItemDatabase() {
		setCreativeTab(GeneticsCreativeTab.instance);
		setUnlocalizedName("database");
		setMaxStackSize(1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		itemIcon = Genetics.proxy.getIcon(register, "geneDatabase");
		iconMaster = Genetics.proxy.getIcon(register, "masterGeneDatabase");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int damage) {
		return (damage == 0) ? itemIcon : iconMaster;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean advanced) {
		super.addInformation(stack, player, tooltip, advanced);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		super.getSubItems(item, tab, list);
		list.add(new ItemStack(item, 1, 1));
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemststackck, World world, EntityPlayer player) {
		if (itemststackck.getItemDamage() == 0) {
			Genetics.proxy.openGui(GeneticsGUI.Database, player, (int) player.posX, (int) player.posY, (int) player.posZ);
		} else {
			Genetics.proxy.openGui(GeneticsGUI.DatabaseNEI, player, (int) player.posX, (int) player.posY, (int) player.posZ);
		}
		return itemststackck;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return (stack.getItemDamage() == 0) ?
			"Gene Database" :
			"Master Gene Database";
	}
}
