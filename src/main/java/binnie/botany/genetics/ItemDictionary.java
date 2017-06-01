package binnie.botany.genetics;

import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import binnie.botany.core.BotanyGUI;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
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

public class ItemDictionary extends Item implements IItemModelRegister {
	public ItemDictionary() {
		this.setCreativeTab(CreativeTabBotany.instance);
		this.setUnlocalizedName("database");
		this.setMaxStackSize(1);
		setRegistryName("database");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(final Item itemIn, final CreativeTabs tab, final NonNullList<ItemStack> subItems) {
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
			Botany.proxy.openGui(BotanyGUI.Database, playerIn, playerIn.getPosition());
		} else {
			Botany.proxy.openGui(BotanyGUI.DatabaseNEI, playerIn, playerIn.getPosition());
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	public String getItemStackDisplayName(final ItemStack i) {
		return (i.getItemDamage() == 0) ? "Botanist Database" : "Master Botanist Database";
	}
}
