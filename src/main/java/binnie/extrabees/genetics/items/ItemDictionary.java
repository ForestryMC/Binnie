package binnie.extrabees.genetics.items;

import binnie.extrabees.ExtraBees;
import binnie.extrabees.core.ExtraBeeGUID;
import forestry.api.core.Tabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemDictionary extends Item {
    public ItemDictionary() {
        this.setCreativeTab(Tabs.tabApiculture);
        this.setUnlocalizedName("dictionary");
        this.setMaxStackSize(1);
        setRegistryName("dictionary");
    }

//	IIcon iconMaster;

//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(final IIconRegister register) {
//		this.itemIcon = ExtraBees.proxy.getIcon(register, "apiaristDatabase");
//		this.iconMaster = ExtraBees.proxy.getIcon(register, "masterApiaristDatabase");
//	}
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public IIcon getIconFromDamage(final int par1) {
//		return (par1 == 0) ? this.itemIcon : this.iconMaster;
//	}

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, playerIn, tooltip, advanced);
        if (stack.getItemDamage() > 0) {
            tooltip.add("Flora-in-a-box");
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(final Item par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        super.getSubItems(par1, par2CreativeTabs, par3List);
        par3List.add(new ItemStack(par1, 1, 1));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand) {
        if (itemStack.getItemDamage() == 0) {
            ExtraBees.proxy.openGui(ExtraBeeGUID.Database, player, new BlockPos((int) player.posX, (int) player.posY, (int) player.posZ));
        } else {
            ExtraBees.proxy.openGui(ExtraBeeGUID.DatabaseNEI, player, new BlockPos((int) player.posX, (int) player.posY, (int) player.posZ));
        }

        return super.onItemRightClick(itemStack, world, player, hand);
    }

    @Override
    public String getItemStackDisplayName(final ItemStack i) {
        return (i.getItemDamage() == 0) ? "Apiarist Database" : "Master Apiarist Database";
    }
}
