package binnie.core.item;

import forestry.api.core.IModelManager;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemMisc extends ItemCore {
	private final IItemMiscProvider[] items;

	public ItemMisc(final CreativeTabs tab, final IItemMiscProvider[] items, String name) {
		super(name);
		this.setCreativeTab(tab);
		this.setHasSubtypes(true);
		this.setTranslationKey(name);
		this.items = items;
	}

	public ItemMisc(CreativeTabs tab, final IItemMiscProvider[] items) {
		this(tab, items, "misc");
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			for (IItemMiscProvider item : this.items) {
				if (item.isActive()) {
					items.add(this.getStack(item, 1));
				}
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
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
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
		return item.getDisplayName(stack);
	}
}
