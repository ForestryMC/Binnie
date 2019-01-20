package binnie.genetics.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import binnie.core.Binnie;
import binnie.core.api.gui.IGuiItem;
import binnie.core.item.ItemCore;
import binnie.genetics.CreativeTabGenetics;
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsGUI;

public class ItemMasterRegistry extends ItemCore implements IGuiItem {
	/*@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(final IIconRegister register) {
		this.itemIcon = Genetics.proxy.getIcon(register, "masterRegistry");
	}*/

	public ItemMasterRegistry() {
		super("master_registry");
		this.setCreativeTab(CreativeTabGenetics.INSTANCE);
		this.setTranslationKey("master_registry");
		this.setMaxStackSize(1);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if (!Binnie.GENETICS.getActiveSystems().isEmpty()) {
			Genetics.proxy.openGui(GeneticsGUI.MASTER_REGISTRY, playerIn, playerIn.getPosition());
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	public void openGui(ItemStack itemStack, World world, EntityPlayer player) {
		if (!Binnie.GENETICS.getActiveSystems().isEmpty()) {
			Genetics.proxy.openGui(GeneticsGUI.MASTER_REGISTRY, player, player.getPosition());
		}
	}

	@Override
	public String getItemStackDisplayName(ItemStack i) {
		return "Master Registry";
	}
}
