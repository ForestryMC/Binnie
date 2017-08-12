package binnie.botany.items;

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
import binnie.botany.gui.BotanyGUI;
import binnie.core.util.I18N;

public class ItemDatabaseBotany extends ItemBotany implements IItemModelRegister {
	public ItemDatabaseBotany() {
		super("database");
		setMaxStackSize(1);
		setHasSubtypes(true);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			for(int i = 0;i < 2;i++) {
				items.add(new ItemStack(this, 1, i));
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel(Item item, IModelManager manager) {
		manager.registerItemModel(item, 0, "botanist_database");
		manager.registerItemModel(item, 1, "botanist_database_master");
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack itemStack = playerIn.getHeldItem(handIn);
		if (itemStack.getMetadata() == 0) {
			Botany.proxy.openGui(BotanyGUI.DATABASE, playerIn, playerIn.getPosition());
		} else {
			Botany.proxy.openGui(BotanyGUI.DATABASE_MASTER, playerIn, playerIn.getPosition());
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	public String getItemStackDisplayName(ItemStack i) {
		return I18N.localise("item.botany.database." + ((i.getItemDamage() == 0) ? "name" : "master.name"));
	}
}
