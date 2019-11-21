package binnie.core.item;

import binnie.core.BinnieCore;
import binnie.core.api.gui.IGuiItem;
import binnie.core.gui.BinnieCoreGUI;
import forestry.api.core.Tabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemGenesis extends ItemCore implements IGuiItem {

	public ItemGenesis() {
		super("genesis");
		this.setCreativeTab(Tabs.tabApiculture);
		this.setTranslationKey("genesis");
		this.setMaxStackSize(1);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack itemStack = playerIn.getHeldItem(handIn);
		openGuiOnRightClick(itemStack, worldIn, playerIn);
		return new ActionResult<>(EnumActionResult.SUCCESS, itemStack);
	}

	@Override
	public void openGuiOnRightClick(ItemStack itemStack, World world, EntityPlayer playerIn) {
		BinnieCore.getBinnieProxy().openGui(BinnieCoreGUI.GENESIS, playerIn, playerIn.getPosition());
	}

	@Override
	public String getItemStackDisplayName(final ItemStack i) {
		return "Genesis";
	}
}
