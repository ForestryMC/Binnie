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
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemMothDatabase extends Item implements IItemModelRegister {

	public ItemMothDatabase() {
		this.setCreativeTab(Tabs.tabLepidopterology);
		this.setUnlocalizedName("databaseMoth");
		this.setMaxStackSize(1);
		setRegistryName("databaseMoth");
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
		super.addInformation(stack, playerIn, tooltip, advanced);
		if (stack.getItemDamage() > 0) {
			tooltip.add(TextFormatting.DARK_PURPLE + "Binnie's Emporium of Lepidopterans");
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(final Item par1, final CreativeTabs par2CreativeTabs, final NonNullList<ItemStack> par3List) {
		super.getSubItems(par1, par2CreativeTabs, par3List);
		par3List.add(new ItemStack(par1, 1, 1));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		final ItemStack itemStack = playerIn.getHeldItem(handIn);
		final ExtraTreesGUID id;
		if (itemStack.getMetadata() == 0) {
			id = ExtraTreesGUID.MothDatabase;
		} else {
			id = ExtraTreesGUID.MothDatabaseNEI;
		}

		ExtraTrees.proxy.openGui(id, playerIn, playerIn.getPosition());

		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	public String getItemStackDisplayName(final ItemStack i) {
		return (i.getItemDamage() == 0) ? "Lepidopterist Database" : "Master Lepidopterist Database";
	}
}
