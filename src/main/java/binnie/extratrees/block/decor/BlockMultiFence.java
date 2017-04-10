package binnie.extratrees.block.decor;

import javax.annotation.Nullable;

import binnie.Binnie;
import binnie.core.block.IBlockMetadata;
import binnie.core.block.TileEntityMetadata;
import binnie.core.models.DefaultStateMapper;
import binnie.core.models.ModelManager;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.IPlankType;
import binnie.extratrees.block.PlankType;
import binnie.extratrees.block.WoodManager;
import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import forestry.api.core.IStateMapperRegister;
import forestry.core.blocks.properties.UnlistedBlockAccess;
import forestry.core.blocks.properties.UnlistedBlockPos;
import forestry.core.models.BlockModelEntry;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMultiFence extends BlockFence implements IBlockMetadata, IStateMapperRegister, IItemModelRegister {
	public BlockMultiFence() {
		super("multifence");
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new ExtendedBlockState(this, new IProperty[]{NORTH, EAST, WEST, SOUTH}, new IUnlistedProperty[]{UnlistedBlockPos.POS, UnlistedBlockAccess.BLOCKACCESS});
	}

	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
		return ((IExtendedBlockState) state).withProperty(UnlistedBlockPos.POS, pos).withProperty(UnlistedBlockAccess.BLOCKACCESS, world);
	}

	@Override
	public void getSubBlocks(final Item itemIn, final CreativeTabs par2CreativeTabs, final NonNullList<ItemStack> itemList) {
		for (final FenceType type : FenceType.values()) {
			itemList.add(WoodManager.getFence(PlankType.VanillaPlanks.SPRUCE, PlankType.VanillaPlanks.BIRCH, type, 1));
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Nullable
	public TextureAtlasSprite getSprite(int meta, boolean secondary){
		FenceDescription desc = getDescription(meta);
		if(desc != null){
			IPlankType type = secondary ? desc.getSecondaryPlankType() : desc.getPlankType();
			return type.getSprite();
		}
		return null;
	}


	@Override
	public String getDisplayName(final ItemStack par1ItemStack) {
		final int meta = TileEntityMetadata.getItemDamage(par1ItemStack);
		final IPlankType type1 = this.getDescription(meta).getPlankType();
		final IPlankType type2 = this.getDescription(meta).getSecondaryPlankType();
		final boolean twoTypes = type1 != type2;
		final FenceType type3 = this.getDescription(meta).getFenceType();
		return Binnie.LANGUAGE.localise(ExtraTrees.instance, "block.woodslab.name" + (twoTypes ? "2" : ""), type3.getPrefix(), type1.getName(), type2.getName());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerStateMapper() {
		ResourceLocation registryName = getRegistryName();
		ModelLoader.setCustomStateMapper(this, new DefaultStateMapper(registryName));
		ModelManager.registerCustomBlockModel(new BlockModelEntry(
				new ModelResourceLocation(registryName, "normal"),
				new ModelResourceLocation(registryName, "inventory"),
				new ModelMultiFence(), this));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item, IModelManager manager) {
		manager.registerItemModel(item, new FenceMeshDefinition());
	}

	private class FenceMeshDefinition implements ItemMeshDefinition {

		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack) {
			return new ModelResourceLocation(getRegistryName(), "inventory");
		}

	}
}
