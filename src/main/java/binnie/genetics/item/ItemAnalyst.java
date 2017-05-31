package binnie.genetics.item;

import binnie.core.item.ItemCore;
import binnie.genetics.CreativeTabGenetics;
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsGUI;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemAnalyst extends ItemCore {
	//	@Override
	//	@SideOnly(Side.CLIENT)
	//	public void registerIcons(final IIconRegister register) {
	//		this.itemIcon = Genetics.proxy.getIcon(register, "analyst");
	//	}

	public ItemAnalyst() {
		super("analyst");
		this.setCreativeTab(CreativeTabGenetics.instance);
		this.setUnlocalizedName("analyst");
		this.setMaxStackSize(1);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		Genetics.proxy.openGui(GeneticsGUI.Analyst, playerIn, playerIn.getPosition());
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	public String getItemStackDisplayName(final ItemStack i) {
		return "Analyst";
	}
}
