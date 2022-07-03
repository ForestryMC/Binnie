package binnie.botany.gardening;

import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.botany.api.EnumSoilType;
import binnie.botany.api.gardening.IBlockSoil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockSoil extends Block implements IBlockSoil {
    protected boolean weedKilled;
    protected IIcon[] iconsTop;
    protected IIcon[] iconsSide;
    protected IIcon[] iconsNoWeed;
    protected EnumSoilType type;

    public BlockSoil(EnumSoilType type, String blockName, boolean weedKilled) {
        super(Material.ground);
        this.type = type;
        this.weedKilled = weedKilled;
        iconsTop = new IIcon[9];
        iconsSide = new IIcon[9];
        iconsNoWeed = new IIcon[9];
        setCreativeTab(CreativeTabBotany.instance);
        setBlockName(blockName);
        setTickRandomly(true);
        setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.9375f, 1.0f);
        setLightOpacity(255);
        setHardness(0.5f);
        setStepSound(Block.soundTypeGravel);
    }

    public static int getMeta(EnumAcidity acid, EnumMoisture moisture) {
        return acid.ordinal() * 3 + moisture.ordinal();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < 9; ++i) {
            list.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        return getIcon(side, world.getBlockMetadata(x, y, z));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        meta = ((meta >= 9) ? 8 : meta);
        if (side == 1) {
            return weedKilled ? iconsNoWeed[meta] : iconsTop[meta];
        }
        return (side == 0) ? Blocks.dirt.getIcon(side, 0) : iconsSide[meta];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        for (EnumMoisture moisture : EnumMoisture.values()) {
            for (EnumAcidity pH : EnumAcidity.values()) {
                int index = moisture.ordinal() + pH.ordinal() * 3;
                String id = "soil/" + getType().getID() + "." + pH.getID() + "." + moisture.getID();
                iconsTop[index] = Botany.proxy.getIcon(register, id + ".0");
                iconsSide[index] = Botany.proxy.getIcon(register, id + ".1");
                iconsNoWeed[index] = Botany.proxy.getIcon(register, id + ".2");
            }
        }
    }

    public EnumSoilType getType() {
        return type;
    }

    @Override
    public int damageDropped(int damage) {
        return damage;
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
        int meta = world.getBlockMetadata(x, y, z);
        EnumMoisture moisture = EnumMoisture.values()[meta % 3];
        EnumAcidity acidity = EnumAcidity.values()[meta / 3];
        EnumMoisture desiredMoisture = Gardening.getNaturalMoisture(world, x, y, z);
        if (desiredMoisture.ordinal() > moisture.ordinal()) {
            moisture = ((moisture == EnumMoisture.DRY) ? EnumMoisture.NORMAL : EnumMoisture.DAMP);
        }
        if (desiredMoisture.ordinal() < moisture.ordinal()) {
            moisture = ((moisture == EnumMoisture.DAMP) ? EnumMoisture.NORMAL : EnumMoisture.DRY);
        }

        int meta2 = getMeta(acidity, moisture);
        if (meta != meta2) {
            world.setBlockMetadataWithNotify(x, y, z, meta2, 2);
        }
        if (!weedKilled
                && random.nextInt(5 - getType(world, x, y, z).ordinal()) == 0
                && world.getBlock(x, y + 1, z) == Blocks.air) {
            world.setBlock(x, y + 1, z, Botany.plant, BlockPlant.Type.Weeds.ordinal(), 2);
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public EnumAcidity getPH(World world, int x, int y, int z) {
        return EnumAcidity.values()[world.getBlockMetadata(x, y, z) / 3];
    }

    @Override
    public EnumMoisture getMoisture(World world, int x, int y, int z) {
        return EnumMoisture.values()[world.getBlockMetadata(x, y, z) % 3];
    }

    @Override
    public EnumSoilType getType(World world, int x, int y, int z) {
        return type;
    }

    @Override
    public boolean fertilise(World world, int x, int y, int z, EnumSoilType maxLevel) {
        EnumSoilType type = getType(world, x, y, z);
        if (type.ordinal() >= maxLevel.ordinal()) {
            return false;
        }

        int meta = world.getBlockMetadata(x, y, z);
        return world.setBlock(x, y, z, Gardening.getSoilBlock(maxLevel, weedKilled), meta, 2);
    }

    @Override
    public boolean degrade(World world, int x, int y, int z, EnumSoilType minLevel) {
        EnumSoilType type = getType(world, x, y, z);
        if (type.ordinal() <= minLevel.ordinal()) {
            return false;
        }

        int meta = world.getBlockMetadata(x, y, z);
        return world.setBlock(x, y, z, Gardening.getSoilBlock(minLevel, weedKilled), meta, 2);
    }

    @Override
    public boolean setPH(World world, int x, int y, int z, EnumAcidity pH) {
        int meta = getMeta(pH, getMoisture(world, x, y, z));
        return world.setBlockMetadataWithNotify(x, y, z, meta, 2);
    }

    @Override
    public boolean setMoisture(World world, int x, int y, int z, EnumMoisture moisture) {
        int meta = getMeta(getPH(world, x, y, z), moisture);
        return world.setBlockMetadataWithNotify(x, y, z, meta, 2);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        super.onNeighborBlockChange(world, x, y, z, block);
        if (world.isSideSolid(x, y + 1, z, ForgeDirection.DOWN, false)) {
            world.setBlock(x, y, z, Blocks.dirt, 0, 2);
        }
    }

    @Override
    public boolean canSustainPlant(
            IBlockAccess world, int x, int y, int z, ForgeDirection direction, IPlantable plantable) {
        Block plant = plantable.getPlant(world, x, y + 1, z);
        if (plant == Botany.flower) {
            return true;
        }

        if (plant == Botany.plant) {
            return !weedKilled || !BlockPlant.isWeed(world, x, y, z);
        }
        return world instanceof World && Blocks.dirt.canSustainPlant(world, x, y, z, direction, plantable);
    }

    @Override
    public boolean resistsWeeds(World world, int x, int y, int z) {
        return weedKilled;
    }
}
