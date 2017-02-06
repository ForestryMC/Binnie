package binnie.core.item;

import forestry.api.core.IModelManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

public class ItemMisc extends ItemCore {
	private IItemMiscProvider[] items;

	public ItemMisc(final CreativeTabs tab, final IItemMiscProvider[] items) {
		super("misc");
		this.setCreativeTab(tab);
		this.setHasSubtypes(true);
		this.setUnlocalizedName("misc");
		this.items = items;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(final Item itemIn, final CreativeTabs creativeTabs, List<ItemStack> subItems) {
		for (IItemMiscProvider item : this.items) {
			if (item.isActive()) {
				subItems.add(this.getStack(item, 1));
			}
		}
	}

	private IItemMiscProvider getItem(int damage) {
		return (damage >= this.items.length) ? this.items[0] : this.items[damage];
	}

	public ItemStack getStack(IItemMiscProvider type, int size) {
		return new ItemStack(this, size, type.ordinal());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		super.addInformation(stack, playerIn, tooltip, advanced);
		IItemMiscProvider item = this.getItem(stack.getItemDamage());
		item.addInformation(tooltip);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item, IModelManager manager) {
		for (IItemMiscProvider type : items) {
			manager.registerItemModel(item, type.ordinal(), "misc/" + type.getModelPath());
		}
	}

	@Override
	public String getItemStackDisplayName(final ItemStack stack) {
		IItemMiscProvider item = this.getItem(stack.getItemDamage());
		return item.getName(stack);
	}
}
