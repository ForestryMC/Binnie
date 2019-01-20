package binnie.genetics.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import binnie.core.api.gui.IGuiItem;
import binnie.core.item.ItemCore;
import binnie.genetics.CreativeTabGenetics;
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsGUI;

public class ItemAnalyst extends ItemCore implements IGuiItem {
	public ItemAnalyst() {
		super("analyst");
		this.setCreativeTab(CreativeTabGenetics.INSTANCE);
		this.setTranslationKey("analyst");
		this.setMaxStackSize(1);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		Genetics.proxy.openGui(GeneticsGUI.ANALYST, playerIn, playerIn.getPosition());
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	public void openGui(ItemStack itemStack, World world, EntityPlayer player) {
		Genetics.proxy.openGui(GeneticsGUI.ANALYST, player, player.getPosition());
	}

	@Override
	public String getItemStackDisplayName(ItemStack i) {
		return "Analyst";
	}
}
