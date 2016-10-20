package binnie.extrabees.genetics.items;

import binnie.extrabees.ExtraBees;
import binnie.extrabees.core.ExtraBeeGUID;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemPunnettSquare extends Item {
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(final IIconRegister register) {
//		this.itemIcon = ExtraBees.proxy.getIcon(register, "");
//	}

    public ItemPunnettSquare() {
        this.setCreativeTab(CreativeTabs.TOOLS);
        this.setMaxStackSize(1);
    }

    @Override
    public String getItemStackDisplayName(final ItemStack itemstack) {
        return "Punnett Square";
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World world, EntityPlayer player, EnumHand hand) {
        if (hand == EnumHand.MAIN_HAND)
            ExtraBees.proxy.openGui(ExtraBeeGUID.PunnettSquare, player, new BlockPos((int) player.posX, (int) player.posY, (int) player.posZ));
        return new ActionResult(EnumActionResult.PASS, itemStackIn);
    }
}
