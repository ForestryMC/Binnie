package binnie.extratrees.item;

import binnie.extratrees.ExtraTrees;
import binnie.extratrees.core.ExtraTreesGUID;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import forestry.api.core.Tabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemMothDatabase extends Item implements IItemModelRegister {

	public ItemMothDatabase() {
		setCreativeTab(Tabs.tabLepidopterology);
		setUnlocalizedName("databaseLepi");
		setRegistryName("databaseLepi");
		setMaxStackSize(1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel(Item item, IModelManager manager) {
		manager.registerItemModel(item, 0, "lepi_database");
		manager.registerItemModel(item, 1, "master_lepi_database");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		if (isMaster(stack)) {
			tooltip.add(TextFormatting.DARK_PURPLE + ExtraTrees.proxy.localise("item.database.lepi.master.tooltip"));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, NonNullList<ItemStack> subItems) {
		super.getSubItems(item, tab, subItems);
		subItems.add(new ItemStack(item, 1, 1));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack itemStack = player.getHeldItem(hand);
		final ExtraTreesGUID id;
		if (isMaster(itemStack)) {
			id = ExtraTreesGUID.MothDatabaseNEI;
		} else {
			id = ExtraTreesGUID.MothDatabase;
		}

		ExtraTrees.proxy.openGui(id, player, player.getPosition());

		return new ActionResult(EnumActionResult.PASS, itemStack);
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemStack) {
		return ExtraTrees.proxy.localise(isMaster(itemStack) ? "item.database.lepi.master.name" : "item.database.lepi.name");
	}
	
	protected boolean isMaster(ItemStack itemStack){
		return itemStack.getMetadata() > 0;
	}
}
