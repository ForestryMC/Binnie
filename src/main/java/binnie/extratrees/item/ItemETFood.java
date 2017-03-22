package binnie.extratrees.item;

import binnie.core.item.IItemMiscProvider;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import forestry.api.core.Tabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemETFood extends ItemFood implements IItemModelRegister {
	static final IItemMiscProvider[] items = Food.VALUES;

	public ItemETFood() {
		super(0, 0.0f, false);
		this.setUnlocalizedName("food");
		this.setCreativeTab(Tabs.tabArboriculture);
		this.setHasSubtypes(true);
		setRegistryName("food");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
		for (IItemMiscProvider item : ItemETFood.items) {
			if (item.isActive()) {
				subItems.add(this.getStack(item, 1));
			}
		}
	}

	private IItemMiscProvider getItem(int damage) {
		return (damage >= ItemETFood.items.length) ? ItemETFood.items[0] : ItemETFood.items[damage];
	}

	public ItemStack getStack(IItemMiscProvider type, int size) {
		return new ItemStack(this, size, type.ordinal());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
		IItemMiscProvider item = this.getItem(stack.getItemDamage());
		item.addInformation(tooltip);
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemStack) {
		IItemMiscProvider item = this.getItem(itemStack.getItemDamage());
		return item.getName(itemStack);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item, IModelManager manager) {
		for (IItemMiscProvider provider : items) {
			manager.registerItemModel(item, provider.ordinal(), "foods/" + provider.getModelPath());
		}
	}

	private Food getFood(ItemStack itemStack) {
		return Food.VALUES[itemStack.getItemDamage()];
	}

	@Override
	public int getHealAmount(ItemStack itemStack) {
		return getFood(itemStack).getHealth();
	}

	@Override
	public float getSaturationModifier(ItemStack itemStack) {
		return 3.0f;
	}
}
