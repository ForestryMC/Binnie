package binnie.core.item;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.core.IModelManager;

public class ItemMisc<T extends IItemSubtypeMisc> extends ItemCore {
	private final T[] items;

	public ItemMisc(CreativeTabs tab, T[] items, String name) {
		super(name);
		this.setCreativeTab(tab);
		this.setHasSubtypes(true);
		this.setUnlocalizedName(name);
		this.items = items;
	}

	public ItemMisc(CreativeTabs tab, T[] items) {
		this(tab, items, "misc");
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			for (T item : this.items) {
				if (item.isActive()) {
					items.add(this.getStack(item, 1));
				}
			}
		}
	}

	public T getProvider(ItemStack stack) {
		int damage = stack.getItemDamage();
		if (damage >= 0 && damage < this.items.length) {
			return items[damage];
		}
		return items[0];
	}

	protected T getProvider(int damage) {
		return (damage >= this.items.length) ? this.items[0] : this.items[damage];
	}

	public ItemStack getStack(T type, int size) {
		return new ItemStack(this, size, type.ordinal());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		T item = this.getProvider(stack.getItemDamage());
		item.addInformation(tooltip);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item, IModelManager manager) {
		for (T type : items) {
			manager.registerItemModel(item, type.ordinal(), "misc/" + type.getModelPath());
		}
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		T item = this.getProvider(stack.getItemDamage());
		return item.getDisplayName(stack);
	}
}
