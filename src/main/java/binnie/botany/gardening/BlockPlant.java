package binnie.botany.gardening;

import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import binnie.botany.api.EnumSoilType;
import binnie.botany.api.gardening.IBlockSoil;
import binnie.core.util.I18N;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPlant extends BlockBush {
    public BlockPlant() {
        setBlockName("plant");
        setCreativeTab(CreativeTabBotany.instance);
        setTickRandomly(true);
        setStepSound(Block.soundTypeGrass);
    }

    public static boolean isWeed(IBlockAccess world, int x, int y, int z) {
        if (!(world.getBlock(x, y, z) instanceof BlockPlant)) {
            return false;
        }

        Type type = Type.get(world.getBlockMetadata(x, y, z));
        return type == Type.Weeds || type == Type.WeedsLong || type == Type.WeedsVeryLong;
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        return new ArrayList<>();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        for (Type t : Type.values()) {
            t.icon = Botany.proxy.getIcon(register, t.name().toLowerCase());
        }
    }

    @Override
    protected boolean canPlaceBlockOn(Block block) {
        return super.canPlaceBlockOn(block) || Gardening.isSoil(block);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return Type.values()[meta % Type.values().length].icon;
    }

    @Override
    public int damageDropped(int damage) {
        return damage;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for (Type type : Type.values()) {
            list.add(type.get());
        }
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
        Type type = Type.get(world.getBlockMetadata(x, y, z));
        if (random.nextInt(4) == 0) {
            if (type == Type.Weeds) {
                world.setBlockMetadataWithNotify(x, y, z, Type.WeedsLong.ordinal(), 2);
            } else if (type == Type.WeedsLong) {
                world.setBlockMetadataWithNotify(x, y, z, Type.WeedsVeryLong.ordinal(), 2);
            } else if (type == Type.DeadFlower) {
                world.setBlockMetadataWithNotify(x, y, z, Type.DecayingFlower.ordinal(), 2);
            } else if (type == Type.DecayingFlower) {
                world.setBlockToAir(x, y, z);
                return;
            }
        }

        if (random.nextInt(6) == 0) {
            if (type == Type.Weeds) {
                world.setBlockToAir(x, y, z);
            } else if (type == Type.WeedsLong) {
                world.setBlockMetadataWithNotify(x, y, z, Type.Weeds.ordinal(), 2);
            } else if (type == Type.WeedsVeryLong) {
                world.setBlockMetadataWithNotify(x, y, z, Type.WeedsLong.ordinal(), 2);
            }
        }

        Block below = world.getBlock(x, y - 1, z);
        if (!Gardening.isSoil(below)) {
            return;
        }

        IBlockSoil soil = (IBlockSoil) below;
        if (random.nextInt(3) != 0) {
            return;
        }

        if (type == Type.Weeds || type == Type.WeedsLong || type == Type.WeedsVeryLong) {
            if (!soil.degrade(world, x, y - 1, z, EnumSoilType.LOAM)) {
                soil.degrade(world, x, y - 1, z, EnumSoilType.SOIL);
            }
        } else if (type == Type.DecayingFlower && !soil.fertilise(world, x, y - 1, z, EnumSoilType.LOAM)) {
            soil.fertilise(world, x, y - 1, z, EnumSoilType.FLOWERBED);
        }
    }

    @Override
    public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
        return true;
    }

    public enum Type {
        Weeds("weeds"),
        WeedsLong("longWeeds"),
        WeedsVeryLong("veryLongWeeds"),
        DeadFlower("deadFlower"),
        DecayingFlower("decayingFlower");

        public IIcon icon;

        protected String name;

        Type(String name) {
            this.name = name;
        }

        public static Type get(int id) {
            return values()[id % values().length];
        }

        public ItemStack get() {
            return new ItemStack(Botany.plant, 1, ordinal());
        }

        public String getName() {
            return I18N.localise("botany.plant." + name);
        }
    }
}
