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

import binnie.core.item.ItemCore;
import binnie.genetics.CreativeTabGenetics;
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsGUI;

public class ItemDatabase extends ItemCore {

	public ItemDatabase() {
		super("geneticdatabase");
		this.setCreativeTab(CreativeTabGenetics.instance);
		this.setMaxStackSize(1);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item, IModelManager manager) {
		manager.registerItemModel(item, 0);
		manager.registerItemModel(item, 1, "geneticdatabase_master");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
		super.getSubItems(itemIn, tab, subItems);
		subItems.add(new ItemStack(itemIn, 1, 1));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack itemStack = player.getHeldItem(hand);
		final GeneticsGUI id;
		if (isMaster(itemStack)) {
			id = GeneticsGUI.DatabaseNEI;
		} else {
			id = GeneticsGUI.Database;
		}

		Genetics.proxy.openGui(id, player, player.getPosition());

		return new ActionResult(EnumActionResult.PASS, itemStack);
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemStack) {
		return Genetics.proxy.localise(isMaster(itemStack) ? "item.database.master.name" : "item.database.name");
	}

	protected boolean isMaster(ItemStack itemStack) {
		return itemStack.getItemDamage() > 0;
	}
}
