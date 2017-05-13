package binnie.genetics.item;

import binnie.botany.Botany;
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsGUI;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import forestry.api.core.Tabs;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

public class ItemBeeDictionary extends Item implements IItemModelRegister {

	public ItemBeeDictionary() {
		this.setCreativeTab(Tabs.tabApiculture);
		this.setUnlocalizedName("dictionary");
		this.setMaxStackSize(1);
		setRegistryName("dictionary");
		setHasSubtypes(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		super.addInformation(stack, playerIn, tooltip, advanced);
		if (stack.getItemDamage() > 0) {
			tooltip.add("Flora-in-a-box");
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(final Item itemIn, final CreativeTabs tab, final NonNullList<ItemStack> subItems) {
		super.getSubItems(itemIn, tab, subItems);
		subItems.add(new ItemStack(itemIn, 1, 1));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		final ItemStack itemStack = playerIn.getHeldItem(handIn);
		final GeneticsGUI id;
		if (itemStack.getItemDamage() == 0) {
			id = GeneticsGUI.BeeDatabase;
		} else {
			id = GeneticsGUI.BeeDatabaseNEI;
		}
		Genetics.proxy.openGui(id, playerIn, playerIn.getPosition());

		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	@Nonnull
	public String getItemStackDisplayName(@Nonnull ItemStack i) {
		return (i.getItemDamage() == 0) ? "Apiarist Database" : "Master Apiarist Database";
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("all")
	public void registerModel(Item item, IModelManager manager) {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(this, 1, new ModelResourceLocation(getRegistryName().toString() + "_master", "inventory"));
	}

}
