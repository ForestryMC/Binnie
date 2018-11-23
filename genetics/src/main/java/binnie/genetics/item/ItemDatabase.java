package binnie.genetics.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.core.IModelManager;

import binnie.core.gui.IBinnieGUID;
import binnie.core.gui.IBinnieGuiItem;
import binnie.core.item.ItemCore;
import binnie.core.util.I18N;
import binnie.genetics.CreativeTabGenetics;
import binnie.genetics.core.GeneticsGUI;

public class ItemDatabase extends ItemCore implements IBinnieGuiItem {

	public ItemDatabase() {
		super("geneticdatabase");
		this.setCreativeTab(CreativeTabGenetics.INSTANCE);
		this.setMaxStackSize(1);
		setHasSubtypes(true);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item, IModelManager manager) {
		manager.registerItemModel(item, 0);
		manager.registerItemModel(item, 1, "geneticdatabase_master");
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		super.getSubItems(tab, items);
		if (this.isInCreativeTab(tab)) {
			items.add(new ItemStack(this, 1, 1));
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack itemStack = player.getHeldItem(hand);
		openGui(itemStack, world, player);
		return new ActionResult<>(EnumActionResult.PASS, itemStack);
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemStack) {
		return I18N.localise("genetics." + (isMaster(itemStack) ? "item.database.master.name" : "item.database.name"));
	}

	protected boolean isMaster(ItemStack itemStack) {
		return itemStack.getItemDamage() > 0;
	}

	@Override
	public IBinnieGUID getGuiID(ItemStack itemStack) {
		return isMaster(itemStack) ? GeneticsGUI.DATABASE_MASTER : GeneticsGUI.DATABASE;
	}
}
