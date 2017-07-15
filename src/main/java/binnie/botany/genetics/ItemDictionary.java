package binnie.botany.genetics;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;

import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import binnie.botany.core.BotanyGUI;
import binnie.core.util.I18N;

public class ItemDictionary extends Item implements IItemModelRegister {
	public ItemDictionary() {
		setCreativeTab(CreativeTabBotany.instance);
		setUnlocalizedName("database");
		setMaxStackSize(1);
		setRegistryName("database");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
		super.getSubItems(itemIn, tab, subItems);
		subItems.add(new ItemStack(itemIn, 1, 1));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel(Item item, IModelManager manager) {
		manager.registerItemModel(item, 0, "botanist_database");
		manager.registerItemModel(item, 1, "master_botanist_database");
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack itemStack = playerIn.getHeldItem(handIn);
		if (itemStack.getMetadata() == 0) {
			Botany.proxy.openGui(BotanyGUI.DATABASE, playerIn, playerIn.getPosition());
		} else {
			Botany.proxy.openGui(BotanyGUI.DATABASE_NEI, playerIn, playerIn.getPosition());
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	public String getItemStackDisplayName(ItemStack i) {
		return I18N.localise("item.botany.database." + ((i.getItemDamage() == 0) ? "name" : "master.name"));
	}
}
