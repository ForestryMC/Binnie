package binnie.genetics.item;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
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

import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import forestry.api.core.Tabs;

import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsGUI;

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
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		if (stack.getItemDamage() > 0) {
			tooltip.add("Flora-in-a-box");
		}
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		super.getSubItems(tab, items);
		if (this.isInCreativeTab(tab)) {
			items.add(new ItemStack(this, 1, 1));
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		final ItemStack itemStack = playerIn.getHeldItem(handIn);
		final GeneticsGUI id;
		if (itemStack.getItemDamage() == 0) {
			id = GeneticsGUI.BEE_DATABASE;
		} else {
			id = GeneticsGUI.BEE_DATABASE_NEI;
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
