package binnie.extrabees.alveary;

import forestry.apiculture.MaterialBeehive;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Elec332 on 13-5-2017.
 */
@SuppressWarnings("deprecation")
public class BlockAlveary extends Block implements ITileEntityProvider {

	public BlockAlveary() {
		super(new MaterialBeehive(false));
		setRegistryName("alveary");
	}

	private static final PropertyEnum<AlvearyLogicType> TYPE = PropertyEnum.create("type", AlvearyLogicType.class);

	@Nullable
	@Override
	public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
		return new TileEntityExtraBeesAlvearyPart(getType(meta));
	}

	private AlvearyLogicType getType(int meta){
		IBlockState state = getStateFromMeta(meta);
		return state.getValue(TYPE);
	}

	@Override
	public void getSubBlocks(@Nonnull Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list) {
		for (int i = 0; i < AlvearyLogicType.VALUES.length; i++) {
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
		return "extrabees.machine.alveay."+getType(meta).getName();
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TYPE).ordinal();
	}

	@Override
	@Nonnull
	public IBlockState getStateFromMeta(int meta) {
		meta = meta >= 0 && meta < AlvearyLogicType.VALUES.length ? meta : 0;
		return getDefaultState().withProperty(TYPE, AlvearyLogicType.VALUES[meta]);
	}

}
