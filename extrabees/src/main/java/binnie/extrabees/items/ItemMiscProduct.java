package binnie.extrabees.items;

import binnie.extrabees.ExtraBees;
import binnie.extrabees.items.types.IEBItemMiscProvider;
import forestry.api.core.IModelManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemMiscProduct extends ItemProduct<IEBItemMiscProvider> {

	public ItemMiscProduct(CreativeTabs tab, IEBItemMiscProvider[] types) {
		super(types);
		setCreativeTab(tab);
		setRegistryName("misc");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		IEBItemMiscProvider provider = get(stack);
		provider.addInformation(tooltip);
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("all")
	public void registerModel(Item item, IModelManager manager) {
		for (IEBItemMiscProvider type : types) {
			ModelLoader.setCustomModelResourceLocation(item, type.ordinal(), new ModelResourceLocation(ExtraBees.MODID + ":misc/" + type.getModelPath(), "inventory"));
		}
	}
}
