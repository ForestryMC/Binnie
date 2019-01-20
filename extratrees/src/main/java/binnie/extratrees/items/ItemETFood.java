package binnie.extratrees.items;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import forestry.api.core.Tabs;

import binnie.core.item.IItemSubtypeMisc;

public class ItemETFood extends ItemFood implements IItemModelRegister {
	static final IItemSubtypeMisc[] items = Food.VALUES;

	public ItemETFood() {
		super(0, 0.0f, false);
		this.setTranslationKey("food");
		this.setCreativeTab(Tabs.tabArboriculture);
		this.setHasSubtypes(true);
		setRegistryName("food");
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			for (IItemSubtypeMisc item : ItemETFood.items) {
				if (item.isActive()) {
					items.add(this.getStack(item, 1));
				}
			}
		}
	}

	private IItemSubtypeMisc getItem(int damage) {
		return (damage >= ItemETFood.items.length) ? ItemETFood.items[0] : ItemETFood.items[damage];
	}

	public ItemStack getStack(IItemSubtypeMisc type, int size) {
		return new ItemStack(this, size, type.ordinal());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		IItemSubtypeMisc item = this.getItem(stack.getItemDamage());
		item.addInformation(tooltip);
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemStack) {
		IItemSubtypeMisc item = this.getItem(itemStack.getItemDamage());
		return item.getDisplayName(itemStack);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item, IModelManager manager) {
		for (IItemSubtypeMisc provider : items) {
			manager.registerItemModel(item, provider.ordinal(), "foods/" + provider.getModelPath());
		}
	}

	private Food getFood(ItemStack itemStack) {
		return Food.VALUES[itemStack.getItemDamage()];
	}

	private static final int MINIMUM_USE_DURATION = 8;

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return MINIMUM_USE_DURATION + getFood(stack).getHealth() * 2;
	}

	@Override
	public int getHealAmount(ItemStack itemStack) {
		return getFood(itemStack).getHealth();
	}

	@Override
	public float getSaturationModifier(ItemStack itemStack) {
		return getFood(itemStack).getHealth() / 10.0f;
	}
}
