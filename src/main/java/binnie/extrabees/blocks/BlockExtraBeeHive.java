package binnie.extrabees.blocks;

import binnie.core.genetics.ForestryAllele;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.blocks.type.EnumHiveType;
import binnie.extrabees.genetics.ExtraBeesSpecies;
import forestry.api.apiculture.BeeManager;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IBee;
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
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BlockExtraBeeHive extends Block {

	public static final PropertyEnum<EnumHiveType> hiveType = PropertyEnum.create("type", EnumHiveType.class);

	public BlockExtraBeeHive() {
		super(ExtraBees.materialBeehive);
		this.setLightLevel(0.2f);
		this.setHardness(1.0f);
		this.setTickRandomly(true);
		this.setRegistryName("hive");
		this.setCreativeTab(Tabs.tabApiculture);
	}

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
		List<ItemStack> drops = new ArrayList<>();

		Random random = world instanceof World ? ((World) world).rand : RANDOM;

		List<IHiveDrop> hiveDrops = getDropsForHive(getMetaFromState(state));
		Collections.shuffle(hiveDrops);

		// Grab a princess
		int tries = 0;
		boolean hasPrincess = false;
		while (tries <= 10 && !hasPrincess) {
			tries++;

			for (IHiveDrop drop : hiveDrops) {
				if (random.nextDouble() < drop.getChance(world, pos, fortune)) {
					IBee bee = drop.getBeeType(world, pos);
					if (random.nextFloat() < drop.getIgnobleChance(world, pos, fortune)) {
						bee.setIsNatural(false);
					}

					ItemStack princess = BeeManager.beeRoot.getMemberStack(bee, EnumBeeType.PRINCESS);
					drops.add(princess);
					hasPrincess = true;
					break;
				}
			}
		}

		// Grab drones
		for (IHiveDrop drop : hiveDrops) {
			if (random.nextDouble() < drop.getChance(world, pos, fortune)) {
				IBee bee = drop.getBeeType(world, pos);
				ItemStack drone = BeeManager.beeRoot.getMemberStack(bee, EnumBeeType.DRONE);
				drops.add(drone);
				break;
			}
		}

		// Grab anything else on offer
		for (IHiveDrop drop : hiveDrops) {
			if (random.nextDouble() < drop.getChance(world, pos, fortune)) {
				drops.addAll(drop.getExtraItems(world, pos, fortune));
				break;
			}
		}

		return drops;
	}

	private static List<IHiveDrop> getDropsForHive(int meta) {
		EnumHiveType hive = getHiveNameForMeta(meta);
		if (hive == null) {
			return Collections.emptyList();
		}
		return hive.drops;
	}

	@Nullable
	private static EnumHiveType getHiveNameForMeta(int meta) {
		if (meta < 0 || meta >= EnumHiveType.values().length) {
			return null;
		}
		return EnumHiveType.values()[meta];
	}

}
