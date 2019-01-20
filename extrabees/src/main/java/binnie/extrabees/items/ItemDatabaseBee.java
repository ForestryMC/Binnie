package binnie.extrabees.items;

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

import binnie.core.gui.IBinnieGUID;
import binnie.core.gui.IBinnieGuiItem;
import binnie.core.util.I18N;
import binnie.extrabees.gui.ExtraBeesGUID;

public class ItemDatabaseBee extends Item implements IItemModelRegister, IBinnieGuiItem {

	public ItemDatabaseBee() {
		this.setCreativeTab(Tabs.tabApiculture);
		this.setTranslationKey("dictionary");
		this.setMaxStackSize(1);
		setRegistryName("dictionary");
		setHasSubtypes(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		if (stack.getItemDamage() > 0) {
			tooltip.add(I18N.localise("extrabees.item.database.master.tooltip"));
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
		ItemStack itemStack = playerIn.getHeldItem(handIn);
		openGui(itemStack, worldIn, playerIn);
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	public IBinnieGUID getGuiID(ItemStack itemStack) {
		return isMaster(itemStack) ? ExtraBeesGUID.DATABASE_MASTER : ExtraBeesGUID.DATABASE;
	}

	@Override
	@Nonnull
	public String getItemStackDisplayName(@Nonnull ItemStack itemStack) {
		return I18N.localise("extrabees.item.database." + (isMaster(itemStack) ? "master.name" : "name"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("all")
	public void registerModel(Item item, IModelManager manager) {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
		ModelLoader.setCustomModelResourceLocation(this, 1, new ModelResourceLocation(getRegistryName().toString() + "_master", "inventory"));
	}

	protected boolean isMaster(ItemStack itemStack) {
		return itemStack.getMetadata() > 0;
	}
}
