package binnie.extratrees.items;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.EnumPlantType;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import forestry.api.core.IItemModelRegister;
import forestry.api.core.IModelManager;
import forestry.api.core.Tabs;

import binnie.core.util.I18N;

public class ItemHops extends Item implements net.minecraftforge.common.IPlantable, IItemModelRegister {
	private final Block crops;
	private final Block soil;

	public ItemHops(Block crops, Block soil) {
		this.crops = crops;
		this.soil = soil;
		setUnlocalizedName("hops");
		setRegistryName("hops");
		this.setCreativeTab(Tabs.tabArboriculture);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel(Item item, IModelManager manager) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}

	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		IBlockState state = worldIn.getBlockState(pos);
		Block block = state.getBlock();

		ItemStack itemstack = player.getHeldItem(hand);

		if (!itemstack.isEmpty() && facing == EnumFacing.UP && player.canPlayerEdit(pos, facing, itemstack) && state.getBlock().canSustainPlant(state, worldIn, pos, EnumFacing.UP, this) && worldIn.isAirBlock(pos.up())) {
			pos = pos.up();
			IBlockState blockState = crops.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, 0, player, hand);

			if (!worldIn.setBlockState(pos, blockState, 11)) {
				return EnumActionResult.FAIL;
			} else {
				blockState = worldIn.getBlockState(pos);

				if (blockState.getBlock() == crops) {
					blockState.getBlock().onBlockPlacedBy(worldIn, pos, blockState, player, itemstack);
				}

				SoundType soundtype = blockState.getBlock().getSoundType(blockState, worldIn, pos, player);
				worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
				itemstack.shrink(1);
				return EnumActionResult.SUCCESS;
			}
		} else {
			return EnumActionResult.FAIL;
		}
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemStack) {
		return I18N.localise("extratrees.item.hops.name");
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
		return EnumPlantType.Crop;
	}

	@Override
	public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
		return crops.getDefaultState();
	}
}
