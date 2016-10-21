package binnie.genetics.item;

import binnie.genetics.CreativeTabGenetics;
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsGUI;
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

public class ItemDatabase extends Item {
//	IIcon iconMaster;
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(final IIconRegister register) {
//		this.itemIcon = Genetics.proxy.getIcon(register, "geneDatabase");
//		this.iconMaster = Genetics.proxy.getIcon(register, "masterGeneDatabase");
//	}
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public IIcon getIconFromDamage(final int par1) {
//		return (par1 == 0) ? this.itemIcon : this.iconMaster;
//	}

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        super.getSubItems(itemIn, tab, subItems);
        subItems.add(new ItemStack(itemIn, 1, 1));
    }

    public ItemDatabase() {
        this.setCreativeTab(CreativeTabGenetics.instance);
        this.setUnlocalizedName("database");
        this.setMaxStackSize(1);
        setRegistryName("geneticdatabase");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World worldIn, EntityPlayer player, EnumHand hand) {
        if (itemStack.getItemDamage() == 0) {
            Genetics.proxy.openGui(GeneticsGUI.Database, player, new BlockPos((int) player.posX, (int) player.posY, (int) player.posZ));
        } else {
            Genetics.proxy.openGui(GeneticsGUI.DatabaseNEI, player, new BlockPos((int) player.posX, (int) player.posY, (int) player.posZ));
        }
        return super.onItemRightClick(itemStack, worldIn, player, hand);
    }

    @Override
    public String getItemStackDisplayName(final ItemStack i) {
        return (i.getItemDamage() == 0) ? "Gene Database" : "Master Gene Database";
    }
}
