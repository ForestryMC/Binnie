package binnie.extrabees.blocks;

import binnie.extrabees.ExtraBees;
import binnie.extrabees.blocks.type.EnumHiveType;
import forestry.api.apiculture.IHiveDrop;
import forestry.api.core.Tabs;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlockExtraBeeHive extends Block {

	public BlockExtraBeeHive() {
		super(ExtraBees.materialBeehive);
		this.setLightLevel(0.2f);
		this.setHardness(1.0f);
		this.setTickRandomly(true);
		this.setRegistryName("hive");
		this.setCreativeTab(Tabs.tabApiculture);
	}

	public static final PropertyEnum<EnumHiveType> hiveType = PropertyEnum.create("type", EnumHiveType.class);

	public String getUnlocalizedName(final ItemStack itemStack) {
		return "extrabees.block.hive." + itemStack.getItemDamage();
	}

	@Override
	public void getSubBlocks(@Nonnull final Item itemIn, final CreativeTabs tab, final NonNullList<ItemStack> itemList) {
		for (int i = 0; i < EnumHiveType.values().length; ++i) {
			itemList.add(new ItemStack(this, 1, i));
		}
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(hiveType).ordinal();
	}

	@Override
	@Nonnull
	@SuppressWarnings("deprecation")
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(hiveType, EnumHiveType.values()[meta]);
	}

	@Override
	@Nonnull
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, hiveType);
	}

	@Override
	@Nonnull
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, @Nonnull IBlockState state, int fortune) {
		final ArrayList<ItemStack> ret = new ArrayList<>();
		final List<IHiveDrop> dropList = state.getValue(hiveType).drops;
		Collections.shuffle(dropList);
		int tries = 0;
		//TODO HIVE DROP
//		for (boolean hasPrincess = false; tries <= 10 && !hasPrincess; hasPrincess = true) {
//			++tries;
//			for (final IHiveDrop drop : dropList) {
//				if (world.rand.nextInt(100) < drop.getChance(world, x, y, z)) {
//					ret.add(drop.getPrincess(world, x, y, z, fortune));
//					break;
//				}
//			}
//		}
//		for (final IHiveDrop drop : dropList) {
//			if (world.rand.nextInt(100) < drop.getChance(world, x, y, z)) {
//				ret.addAll(drop.getDrones(world, x, y, z, fortune));
//				break;
//			}
//		}
//		for (final IHiveDrop drop : dropList) {
//			if (world.rand.nextInt(100) < drop.getChance(world, x, y, z)) {
//				ret.addAll(drop.getAdditional(world, x, y, z, fortune));
//				break;
//			}
//		}
		return ret;
	}

}
