package binnie.extrabees.items;

import binnie.extrabees.items.types.IEBEnumItem;
import binnie.extrabees.items.types.IEBItemMiscProvider;
import binnie.extrabees.utils.ExtraBeesResourceLocation;
import forestry.api.core.IModelManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * Created by Elec332 on 13-5-2017.
 */
public class ItemMiscProduct extends ItemProduct {

	public ItemMiscProduct(CreativeTabs tab, IEBItemMiscProvider[] types) {
		super(types);
		setCreativeTab(tab);
		setRegistryName("misc");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		super.addInformation(stack, playerIn, tooltip, advanced);
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
