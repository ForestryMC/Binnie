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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemETFood extends ItemFood implements IItemModelRegister {
	IItemMiscProvider[] items;

	public ItemETFood() {
		super(0, 0.0f, false);
		this.setUnlocalizedName("food");
		this.setCreativeTab(Tabs.tabArboriculture);
		this.setHasSubtypes(true);
		this.items = Food.VALUES;
		setRegistryName("food");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
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
		IItemMiscProvider item = this.getItem(stack.getItemDamage());
		if (item != null) {
			item.addInformation(tooltip);
		}
	}

	@Override
	public String getItemStackDisplayName(final ItemStack stack) {
		IItemMiscProvider item = this.getItem(stack.getItemDamage());
		if(item != null){
			return item.getName(stack);
		}
		return "null";
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item, IModelManager manager) {
		for(IItemMiscProvider provider : items){
			manager.registerItemModel(item, provider.ordinal(), "foods/" + provider.getModelPath());
		}
	}

	private Food getFood(final ItemStack par1ItemStack) {
		return Food.VALUES[par1ItemStack.getItemDamage()];
	}
	
	@Override
	public int getHealAmount(ItemStack stack) {
		return getFood(stack).getHealth();
	}
	
	@Override
    public float getSaturationModifier(ItemStack stack) {
        return 3.0f;
    }
}
