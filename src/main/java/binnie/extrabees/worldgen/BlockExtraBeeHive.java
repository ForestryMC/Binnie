package binnie.extrabees.worldgen;

import binnie.extrabees.ExtraBees;
import forestry.api.apiculture.IHiveDrop;
import forestry.api.core.Tabs;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlockExtraBeeHive extends Block {
    //	IIcon[][] icons;
    public static final PropertyEnum<EnumHiveType> hiveType = PropertyEnum.create("hive_type", EnumHiveType.class);

    public BlockExtraBeeHive() {
        super(ExtraBees.materialBeehive);
        this.setLightLevel(0.2f);
        this.setHardness(1.0f);
        this.setTickRandomly(true);
        this.setRegistryName("hive");
        this.setCreativeTab(Tabs.tabApiculture);
    }

    public String getUnlocalizedName(final ItemStack par1ItemStack) {
        return "extrabees.block.hive." + par1ItemStack.getItemDamage();
    }

    @Override
    public void getSubBlocks(final Item par1, final CreativeTabs par2CreativeTabs, final List itemList) {
        for (int i = 0; i < EnumHiveType.values().length; ++i) {
            itemList.add(new ItemStack(this, 1, i));
        }
    }

//	@Override
//	public IIcon getIcon(final int side, final int metadata) {
//		if (metadata >= EnumHiveType.values().length) {
//			return null;
//		}
//		if (side < 2) {
//			return this.icons[metadata][1];
//		}
//		return this.icons[metadata][0];
//	}
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerBlockIcons(final IIconRegister register) {
//		this.icons = new IIcon[EnumHiveType.values().length][2];
//		for (final EnumHiveType hive : EnumHiveType.values()) {
//			this.icons[hive.ordinal()][0] = ExtraBees.proxy.getIcon(register, "hive/" + hive.toString().toLowerCase() + ".0");
//			this.icons[hive.ordinal()][1] = ExtraBees.proxy.getIcon(register, "hive/" + hive.toString().toLowerCase() + ".1");
//		}
//	}


    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        final ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
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
