package binnie.botany.genetics;

import binnie.botany.CreativeTabBotany;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemDictionary extends Item {
//	IIcon iconMaster;
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(final IIconRegister register) {
//		this.itemIcon = Botany.proxy.getIcon(register, "botanistDatabase");
//		this.iconMaster = Botany.proxy.getIcon(register, "masterBotanistDatabase");
//	}
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public IIcon getIconFromDamage(final int par1) {
//		return (par1 == 0) ? this.itemIcon : this.iconMaster;
//	}

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final List par3List, final boolean par4) {
        super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(final Item par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        super.getSubItems(par1, par2CreativeTabs, par3List);
        par3List.add(new ItemStack(par1, 1, 1));
    }

    public ItemDictionary() {
        this.setCreativeTab(CreativeTabBotany.instance);
        this.setUnlocalizedName("database");
        this.setMaxStackSize(1);
        setRegistryName("database");
    }

//	@Override
//	public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer player) {
//		if (itemstack.getItemDamage() == 0) {
//			Botany.proxy.openGui(BotanyGUI.Database, player, (int) player.posX, (int) player.posY, (int) player.posZ);
//		}
//		else {
//			Botany.proxy.openGui(BotanyGUI.DatabaseNEI, player, (int) player.posX, (int) player.posY, (int) player.posZ);
//		}
//		return itemstack;
//	}

    @Override
    public String getItemStackDisplayName(final ItemStack i) {
        return (i.getItemDamage() == 0) ? "Botanist Database" : "Master Botanist Database";
    }
}
