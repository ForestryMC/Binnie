package binnie.core.item;

import binnie.core.BinnieCore;
import binnie.core.gui.BinnieCoreGUI;
import forestry.api.core.Tabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemGenesis extends ItemCore {
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerIcons(final IIconRegister register) {
//		this.itemIcon = BinnieCore.proxy.getIcon(register, "genesis");
//	}

    public ItemGenesis() {
    	super("genesis");
        this.setCreativeTab(Tabs.tabApiculture);
        this.setUnlocalizedName("genesis");
        this.setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand) {
        BinnieCore.proxy.openGui(BinnieCoreGUI.Genesis, player, new BlockPos((int) player.posX, (int) player.posY, (int) player.posZ));
        return super.onItemRightClick(itemStack, world, player, hand);
    }


    @Override
    public String getItemStackDisplayName(final ItemStack i) {
        return "Genesis";
    }
}
