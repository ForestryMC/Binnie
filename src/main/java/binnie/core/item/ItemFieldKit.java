package binnie.core.item;

import binnie.core.BinnieCore;
import binnie.core.gui.BinnieCoreGUI;
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

public class ItemFieldKit extends Item {
//	private IIcon fieldKit0;
//	private IIcon fieldKit1;
//	private IIcon fieldKit2;
//	private IIcon fieldKit3;

    public ItemFieldKit() {
        this.setUnlocalizedName("fieldKit");
        setRegistryName("fieldKit");
        this.setCreativeTab(CreativeTabs.TOOLS);
        this.setMaxStackSize(1);
        this.setMaxDamage(64);
    }

//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(final IIconRegister register) {
//		this.fieldKit0 = BinnieCore.proxy.getIcon(register, "fieldKit");
//		this.fieldKit1 = BinnieCore.proxy.getIcon(register, "fieldKit1");
//		this.fieldKit2 = BinnieCore.proxy.getIcon(register, "fieldKit2");
//		this.fieldKit3 = BinnieCore.proxy.getIcon(register, "fieldKit3");
//		this.itemIcon = this.fieldKit0;
//	}
//
//	@Override
//	public IIcon getIcon(final ItemStack stack, final int pass) {
//		final int damage = stack.getItemDamage();
//		if (damage < 24) {
//			return this.fieldKit3;
//		}
//		if (damage < 48) {
//			return this.fieldKit2;
//		}
//		if (damage < 64) {
//			return this.fieldKit1;
//		}
//		return this.fieldKit0;
//	}


    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand) {
        if (!player.isSneaking() && hand == EnumHand.MAIN_HAND) {
            BinnieCore.proxy.openGui(BinnieCoreGUI.FieldKit, player, new BlockPos((int) player.posX, (int) player.posY, (int) player.posZ));
        }
        return super.onItemRightClick(itemStack, world, player, hand);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack p_77624_1_, final EntityPlayer p_77624_2_, final List p_77624_3_, final boolean p_77624_4_) {
        final int i = this.getMaxDamage() - p_77624_1_.getItemDamage();
        if (i == 0) {
            p_77624_3_.add("No paper");
        } else {
            p_77624_3_.add("" + i + " sheet" + ((i > 1) ? "s" : "") + " of paper");
        }
        super.addInformation(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
    }

    @Override
    public String getItemStackDisplayName(final ItemStack p_77653_1_) {
        return "Field Kit";
    }
}
