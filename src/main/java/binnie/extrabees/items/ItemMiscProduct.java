package binnie.extrabees.items;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.core.IModelManager;

import binnie.extrabees.items.types.IEBEnumItem;
import binnie.extrabees.items.types.IEBItemMiscProvider;
import binnie.extrabees.utils.ExtraBeesResourceLocation;

public class ItemMiscProduct extends ItemProduct {

	public ItemMiscProduct(CreativeTabs tab, IEBItemMiscProvider[] types) {
		super(types);
		setCreativeTab(tab);
		setRegistryName("misc");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		((IEBItemMiscProvider) get(stack)).addInformation(tooltip);
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("all")
	public void registerModel(Item item, IModelManager manager) {
		for (IEBEnumItem type : types) {
			ModelLoader.setCustomModelResourceLocation(item, type.ordinal(), new ModelResourceLocation(new ExtraBeesResourceLocation("misc/" + ((IEBItemMiscProvider) type).getModelPath()), "inventory"));
		}
	}
}
