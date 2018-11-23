package binnie.core.item;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.core.IModelManager;

import binnie.core.BinnieCore;
import binnie.core.Constants;
import binnie.core.gui.BinnieCoreGUI;

public class ItemFieldKit extends ItemCore {
	private ModelResourceLocation fieldKit;
	private ModelResourceLocation fieldKit1;
	private ModelResourceLocation fieldKit2;
	private ModelResourceLocation fieldKit3;

	public ItemFieldKit() {
		super("field_kit");
		this.setUnlocalizedName("field_kit");
		this.setCreativeTab(CreativeTabs.TOOLS);
		this.setMaxStackSize(1);
		this.setMaxDamage(64);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item, IModelManager manager) {
		manager.registerItemModel(item, new FieldKitMeshDefinition());
		fieldKit = new ModelResourceLocation(Constants.CORE_MOD_ID + ":field_kit", "inventory");
		fieldKit1 = new ModelResourceLocation(Constants.CORE_MOD_ID + ":field_kit1", "inventory");
		fieldKit2 = new ModelResourceLocation(Constants.CORE_MOD_ID + ":field_kit2", "inventory");
		fieldKit3 = new ModelResourceLocation(Constants.CORE_MOD_ID + ":field_kit3", "inventory");
		ModelBakery.registerItemVariants(item, fieldKit, fieldKit1, fieldKit2, fieldKit3);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if (!playerIn.isSneaking() && handIn == EnumHand.MAIN_HAND) {
			BinnieCore.getBinnieProxy().openGui(BinnieCoreGUI.FIELD_KIT, playerIn, playerIn.getPosition());
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		int i = stack.getMaxDamage() - stack.getItemDamage();
		if (i == 0) {
			tooltip.add("No paper");
		} else {
			tooltip.add(i + " sheet" + ((i > 1) ? "s" : "") + " of paper");
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	@Override
	public String getItemStackDisplayName(ItemStack p_77653_1_) {
		return "Field Kit";
	}

	@SideOnly(Side.CLIENT)
	private class FieldKitMeshDefinition implements ItemMeshDefinition {
		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack) {
			int damage = stack.getItemDamage();
			if (damage < 24) {
				return fieldKit3;
			}
			if (damage < 48) {
				return fieldKit2;
			}
			if (damage < 64) {
				return fieldKit1;
			}
			return fieldKit;
		}
	}
}
