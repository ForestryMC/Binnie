package binnie.extrabees.alveary;

import forestry.apiculture.MaterialBeehive;
import forestry.core.gui.GuiHandler;
import forestry.core.tiles.TileUtil;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class BlockAlveary extends Block implements ITileEntityProvider {

	private static final PropertyEnum<EnumAlvearyLogicType> TYPE = PropertyEnum.create("type", EnumAlvearyLogicType.class);

	public BlockAlveary() {
		super(new MaterialBeehive(false));
		setRegistryName("alveary");
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntityExtraBeesAlvearyPart tile = TileUtil.getTile(worldIn, pos, TileEntityExtraBeesAlvearyPart.class);
		if (tile != null && tile.hasGui()) {
			GuiHandler.openGui(playerIn, tile);
		}
		return true;
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
		return new TileEntityExtraBeesAlvearyPart(getType(meta));
	}

	private EnumAlvearyLogicType getType(int meta) {
		IBlockState state = getStateFromMeta(meta);
		return state.getValue(TYPE);
	}

	@Override
	public void getSubBlocks(@Nonnull Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list) {
		for (int i = 0; i < EnumAlvearyLogicType.VALUES.length; i++) {
			list.add(new ItemStack(itemIn, 1, i));
		}
	}

	@Override
	@Nonnull
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, TYPE);
	}

	@Override
	@Nonnull
	public String getUnlocalizedName() {
		return getUnlocalizedName(0);
	}

	@Nonnull
	public String getUnlocalizedName(int meta) {
		return "extrabees.machine.alveay." + getType(meta).getName();
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TYPE).ordinal();
	}

	@Override
	@Nonnull
	public IBlockState getStateFromMeta(int meta) {
		meta = meta >= 0 && meta < EnumAlvearyLogicType.VALUES.length ? meta : 0;
		return getDefaultState().withProperty(TYPE, EnumAlvearyLogicType.VALUES[meta]);
	}
}
