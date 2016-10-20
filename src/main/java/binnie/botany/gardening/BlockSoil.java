package binnie.botany.gardening;

import binnie.Binnie;
import binnie.botany.CreativeTabBotany;
import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.botany.api.EnumSoilType;
import binnie.botany.api.gardening.IBlockSoil;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockSoil extends Block implements IBlockSoil {
    boolean weedKilled;
    //	IIcon[] iconsTop;
//	IIcon[] iconsSide;
//	IIcon[] iconsNoWeed;
    EnumSoilType type;
    public static final PropertyEnum<EnumMoisture> MOISTURE = PropertyEnum.create("moisture", EnumMoisture.class);
    public static final PropertyEnum<EnumAcidity> ACIDITY = PropertyEnum.create("acidity", EnumAcidity.class);

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        for (int i = 0; i < 9; ++i) {
            par3List.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, MOISTURE, ACIDITY);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return getMeta(state.getValue(ACIDITY), state.getValue(MOISTURE));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        final EnumMoisture moisture = EnumMoisture.values()[meta % 3];
        final EnumAcidity acidity = EnumAcidity.values()[meta / 3];
        return getDefaultState().withProperty(MOISTURE, moisture).withProperty(ACIDITY, acidity);
    }

    //	@Override
//	@SideOnly(Side.CLIENT)
//	public IIcon getIcon(final IBlockAccess world, final int x, final int y, final int z, final int side) {
//		return this.getIcon(side, world.getBlockMetadata(x, y, z));
//	}
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public IIcon getIcon(final int side, int meta) {
//		meta = ((meta >= 9) ? 8 : meta);
//		final EnumMoisture moisture = EnumMoisture.values()[meta % 3];
//		final EnumAcidity acidity = EnumAcidity.values()[meta / 3];
//		return (side == 1) ? (this.weedKilled ? this.iconsNoWeed[meta] : this.iconsTop[meta]) : ((side == 0) ? Blocks.dirt.getIcon(side, 0) : this.iconsSide[meta]);
//	}
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerBlockIcons(final IIconRegister register) {
//		for (final EnumMoisture moisture : EnumMoisture.values()) {
//			for (final EnumAcidity pH : EnumAcidity.values()) {
//				this.iconsTop[moisture.ordinal() + pH.ordinal() * 3] = Botany.proxy.getIcon(register, "soil/" + this.getType().getID() + "." + pH.getID() + "." + moisture.getID() + ".0");
//				this.iconsSide[moisture.ordinal() + pH.ordinal() * 3] = Botany.proxy.getIcon(register, "soil/" + this.getType().getID() + "." + pH.getID() + "." + moisture.getID() + ".1");
//				this.iconsNoWeed[moisture.ordinal() + pH.ordinal() * 3] = Botany.proxy.getIcon(register, "soil/" + this.getType().getID() + "." + pH.getID() + "." + moisture.getID() + ".2");
//			}
//		}
//	}

    public EnumSoilType getType() {
        return this.type;
    }

    public BlockSoil(final EnumSoilType type, final String blockName, final boolean weedKilled) {
        super(Material.GROUND);
        this.weedKilled = false;
//		this.iconsTop = new IIcon[9];
//		this.iconsSide = new IIcon[9];
//		this.iconsNoWeed = new IIcon[9];
        this.weedKilled = weedKilled;
        this.setCreativeTab(CreativeTabBotany.instance);
        this.setRegistryName(blockName);
        this.setTickRandomly(true);
        //this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.9375f, 1.0f);
        this.setLightOpacity(255);
        this.setHardness(0.5f);
        this.setSoundType(SoundType.GROUND);
        this.type = type;
    }

//	@Override
//	public int damageDropped(final int p_149692_1_) {
//		return p_149692_1_;
//	}
//
//	@Override
//	public void updateTick(final World world, final int x, final int y, final int z, final Random random) {
//		final int meta = world.getBlockMetadata(x, y, z);
//		EnumMoisture moisture = EnumMoisture.values()[meta % 3];
//		final EnumAcidity acidity = EnumAcidity.values()[meta / 3];
//		final EnumMoisture desiredMoisture = Gardening.getNaturalMoisture(world, x, y, z);
//		if (desiredMoisture.ordinal() > moisture.ordinal()) {
//			moisture = ((moisture == EnumMoisture.Dry) ? EnumMoisture.Normal : EnumMoisture.Damp);
//		}
//		if (desiredMoisture.ordinal() < moisture.ordinal()) {
//			moisture = ((moisture == EnumMoisture.Damp) ? EnumMoisture.Normal : EnumMoisture.Dry);
//		}
//		final int meta2 = getMeta(acidity, moisture);
//		if (meta != meta2) {
//			world.setBlockMetadataWithNotify(x, y, z, meta2, 2);
//		}
//		if (!this.weedKilled && random.nextInt(5 - this.getType(world, x, y, z).ordinal()) == 0 && world.getBlock(x, y + 1, z) == Blocks.air) {
//			world.setBlock(x, y + 1, z, Botany.plant, BlockPlant.Type.Weeds.ordinal(), 2);
//		}
//	}
//
//	@Override
//	public AxisAlignedBB getCollisionBoundingBoxFromPool(final World p_149668_1_, final int p_149668_2_, final int p_149668_3_, final int p_149668_4_) {
//		return AxisAlignedBB.getBoundingBox(p_149668_2_ + 0, p_149668_3_ + 0, p_149668_4_ + 0, p_149668_2_ + 1, p_149668_3_ + 1, p_149668_4_ + 1);
//	}
//
//	@Override
//	public boolean isOpaqueCube() {
//		return false;
//	}
//
//	@Override
//	public boolean renderAsNormalBlock() {
//		return false;
//	}

    @Override
    public EnumAcidity getPH(final World world, final BlockPos pos) {
        return world.getBlockState(pos).getValue(ACIDITY); //EnumAcidity.values()[world.getBlockMetadata(x, y, z) / 3];
    }

    @Override
    public EnumMoisture getMoisture(final World world, final BlockPos pos) {
        return world.getBlockState(pos).getValue(MOISTURE); //EnumMoisture.values()[world.getBlockMetadata(x, y, z) % 3];
    }

    @Override
    public EnumSoilType getType(final World world, final BlockPos pos) {
        return this.type;
    }

    @Override
    public boolean fertilise(final World world, final BlockPos pos, final EnumSoilType maxLevel) {
        final EnumSoilType type = this.getType(world, pos);
        if (type.ordinal() >= maxLevel.ordinal()) {
            return false;
        }
        final IBlockState old = world.getBlockState(pos);
        return world.setBlockState(pos, Gardening.getSoilBlock(maxLevel, this.weedKilled).getDefaultState().withProperty(ACIDITY, old.getValue(ACIDITY)).withProperty(MOISTURE, old.getValue(MOISTURE)), 2);
    }

    @Override
    public boolean degrade(final World world, final BlockPos pos, final EnumSoilType minLevel) {
        final EnumSoilType type = this.getType(world, pos);
        if (type.ordinal() <= minLevel.ordinal()) {
            return false;
        }
        final IBlockState old = world.getBlockState(pos);
        return world.setBlockState(pos, Gardening.getSoilBlock(minLevel, this.weedKilled).getDefaultState().withProperty(ACIDITY, old.getValue(ACIDITY)).withProperty(MOISTURE, old.getValue(MOISTURE)), 2);
    }

    @Override
    public boolean setPH(final World world, final BlockPos pos, final EnumAcidity pH) {
        //final int meta = getMeta(pH, this.getMoisture(world, pos));
        return world.setBlockState(pos, world.getBlockState(pos).withProperty(ACIDITY, pH));//world.setBlockMetadataWithNotify(x, y, z, meta, 2);
    }

    @Override
    public boolean setMoisture(final World world, final BlockPos pos, final EnumMoisture moisture) {
        //final int meta = getMeta(this.getPH(world, x, y, z), moisture);
        return world.setBlockState(pos, world.getBlockState(pos).withProperty(MOISTURE, moisture));//return world.setBlockMetadataWithNotify(x, y, z, meta, 2);
    }

    public static int getMeta(final EnumAcidity acid, final EnumMoisture moisture) {
        return acid.ordinal() * 3 + moisture.ordinal();
    }

    @Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
        super.onNeighborChange(world, pos, neighbor);
        if (world.isSideSolid(pos.up(), EnumFacing.DOWN, false)) {
            ((World) world).setBlockState(pos, Blocks.DIRT.getDefaultState(), 2);
        }
    }

    public static String getPH(final ItemStack stack) {
        return Binnie.Language.localise(EnumAcidity.values()[stack.getItemDamage() / 3]);
    }

    public static String getMoisture(final ItemStack stack) {
        return Binnie.Language.localise(EnumMoisture.values()[stack.getItemDamage() % 3]);
    }

//	@Override
//	public boolean canSustainPlant(final IBlockAccess world, final BlockPos pos, final EnumFacing direction, final IPlantable plantable) {
//		final Block plant = plantable.getPlant(world, x, y + 1, z);
//		if (plant == Botany.flower) {
//			return true;
//		}
//		if (plant == Botany.plant) {
//			return !this.weedKilled || !BlockPlant.isWeed(world, x, y, z);
//		}
//		return world instanceof World && Blocks.dirt.canSustainPlant(world, x, y, z, direction, plantable);
//	}

    @Override
    public boolean resistsWeeds(final World world, final BlockPos pos) {
        return this.weedKilled;
    }
}
