package binnie.core.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import forestry.api.core.Tabs;

import binnie.core.gui.BinnieCoreGUI;
import binnie.core.gui.IBinnieGUID;
import binnie.core.gui.IBinnieGuiItem;

public class ItemGenesis extends ItemCore implements IBinnieGuiItem {

	public ItemGenesis() {
		super("genesis");
		this.setCreativeTab(Tabs.tabApiculture);
		this.setTranslationKey("genesis");
		this.setMaxStackSize(1);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack itemStack = playerIn.getHeldItem(handIn);
		openGui(itemStack, worldIn, playerIn);
		return new ActionResult<>(EnumActionResult.SUCCESS, itemStack);
	}

	@Override
	public IBinnieGUID getGuiID(ItemStack itemStack) {
		return BinnieCoreGUI.GENESIS;
	}

	@Override
	public String getItemStackDisplayName(ItemStack i) {
		return "Genesis";
	}
}
