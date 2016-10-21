package binnie.genetics.item;

import binnie.core.item.ItemCore;
import binnie.genetics.CreativeTabGenetics;
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsGUI;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemMasterRegistry extends ItemCore {
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(final IIconRegister register) {
//		this.itemIcon = Genetics.proxy.getIcon(register, "masterRegistry");
//	}

    public ItemMasterRegistry() {
    	super("masterRegistry");
        this.setCreativeTab(CreativeTabGenetics.instance);
        this.setUnlocalizedName("masterRegistry");
        this.setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand) {
        Genetics.proxy.openGui(GeneticsGUI.MasterRegistry, player, new BlockPos((int) player.posX, (int) player.posY, (int) player.posZ));
        return super.onItemRightClick(itemStack, world, player, hand);
    }

    @Override
    public String getItemStackDisplayName(final ItemStack i) {
        return "Master Registry";
    }
}
