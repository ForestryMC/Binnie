package binnie.botany.items;

import binnie.botany.api.genetics.EnumFlowerColor;
import binnie.core.util.I18N;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import forestry.core.items.IColoredItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemClay extends ItemBotany implements IColoredItem, IItemModelRegister {
	public ItemClay() {
		super("clay");
		setTranslationKey("botany.clay");
		setHasSubtypes(true);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item, IModelManager manager) {
		for (EnumFlowerColor color : EnumFlowerColor.values()) {
			manager.registerItemModel(item, color.ordinal());
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		EnumFlowerColor color = EnumFlowerColor.get(stack.getItemDamage());
		return color.getFlowerColorAllele().getColor(false);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		EnumFlowerColor color = EnumFlowerColor.get(stack.getItemDamage());
		return I18N.localise("item.botany.clay.name", color.getFlowerColorAllele().getColorName());
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			for (EnumFlowerColor color : EnumFlowerColor.values()) {
				items.add(new ItemStack(this, 1, color.ordinal()));
			}
		}
	}
}
