package binnie.extratrees.block.wood;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.arboriculture.IWoodItemMeshDefinition;
import forestry.api.arboriculture.TreeManager;
import forestry.api.arboriculture.WoodBlockKind;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import forestry.api.core.IStateMapperRegister;
import forestry.api.core.Tabs;
import forestry.arboriculture.IWoodTyped;
import forestry.arboriculture.blocks.WoodTypeStateMapper;
import forestry.arboriculture.proxy.ProxyArboricultureClient;
import forestry.core.blocks.IColoredBlock;

import binnie.Constants;
import binnie.extratrees.block.EnumETLog;

public class BlockETDoor extends BlockDoor implements IWoodTyped, IItemModelRegister, IStateMapperRegister, IColoredBlock {
	private final EnumETLog woodType;
	
	public BlockETDoor(EnumETLog woodType) {
		super(Material.WOOD);
		this.woodType = woodType;
		
		setHarvestLevel("axe", 0);
		setCreativeTab(Tabs.tabArboriculture);
		String name = "doors." + woodType;
		setUnlocalizedName(name);
		setRegistryName(name);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel(Item item, IModelManager manager) {
		ModelBakery.registerItemVariants(item, new ResourceLocation(Constants.EXTRA_TREES_MOD_ID, "door"));
		ProxyArboricultureClient.registerWoodMeshDefinition(item, new WoodMeshDefinition());
	}
	
	@SideOnly(Side.CLIENT)
	public static class WoodMeshDefinition implements IWoodItemMeshDefinition {
		
		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack) {
			return new ModelResourceLocation(Constants.EXTRA_TREES_MOD_ID + ":door", "inventory");
		}
		
		@Override
		public ResourceLocation getDefaultModelLocation(ItemStack stack) {
			return new ResourceLocation(Constants.EXTRA_TREES_MOD_ID + ":item/door");
		}
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerStateMapper() {
		ProxyArboricultureClient.registerWoodStateMapper(this,
				new WoodTypeStateMapper(this, null).addPropertyToRemove(POWERED));
	}
	
	@Override
	public WoodBlockKind getBlockKind() {
		return WoodBlockKind.DOOR;
	}
	
	@Override
	public boolean isFireproof() {
		return false;
	}
	
	@Override
	public EnumETLog getWoodType(int meta) {
		return woodType;
	}
	
	@Override
	public Collection<EnumETLog> getWoodTypes() {
		return Collections.singleton(woodType);
	}
	
	@Override
	public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
		int meta = getMetaFromState(blockState);
		EnumETLog woodType = getWoodType(meta);
		return woodType.getHardness();
	}
	
	/**
	 * Get the Item that this Block should drop when harvested.
	 */
	/**
	 * Get the Item that this Block should drop when harvested.
	 */
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER ? Items.AIR : getItem();
	}
	
	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(getItem());
	}
	
	private Item getItem() {
		return TreeManager.woodAccess.getStack(woodType, getBlockKind(), false).getItem();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
		return woodType.getPlank().getColour();
	}
}
