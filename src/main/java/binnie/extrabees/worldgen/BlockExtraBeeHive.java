package binnie.extrabees.worldgen;

import binnie.extrabees.ExtraBees;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.apiculture.IHiveDrop;
import forestry.api.core.Tabs;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockExtraBeeHive extends Block {
    protected IIcon[][] icons;

    public BlockExtraBeeHive() {
        super(ExtraBees.materialBeehive);
        setLightLevel(0.2f);
        setHardness(1.0f);
        setTickRandomly(true);
        setBlockName("hive");
        setCreativeTab(Tabs.tabApiculture);
    }

    public String getUnlocalizedName(ItemStack par1ItemStack) {
        return "extrabees.block.hive." + par1ItemStack.getItemDamage();
    }

    @Override
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List itemList) {
        for (int i = 0; i < 4; ++i) {
            itemList.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public IIcon getIcon(int side, int metadata) {
        if (metadata >= EnumHiveType.values().length) {
            return null;
        }
        if (side < 2) {
            return icons[metadata][1];
        }
        return icons[metadata][0];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        icons = new IIcon[EnumHiveType.values().length][2];
        for (EnumHiveType hive : EnumHiveType.values()) {
            icons[hive.ordinal()][0] =
                    ExtraBees.proxy.getIcon(register, "hive/" + hive.toString().toLowerCase() + ".0");
            icons[hive.ordinal()][1] =
                    ExtraBees.proxy.getIcon(register, "hive/" + hive.toString().toLowerCase() + ".1");
        }
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<>();
        List<IHiveDrop> dropList = EnumHiveType.values()[metadata].drops;
        Collections.shuffle(dropList);
        int tries = 0;

        for (boolean hasPrincess = false; tries <= 10 && !hasPrincess; hasPrincess = true) {
            tries++;
            for (IHiveDrop drop : dropList) {
                if (world.rand.nextInt(100) < drop.getChance(world, x, y, z)) {
                    ret.add(drop.getPrincess(world, x, y, z, fortune));
                    break;
                }
            }
        }

        for (IHiveDrop drop : dropList) {
            if (world.rand.nextInt(100) < drop.getChance(world, x, y, z)) {
                ret.addAll(drop.getDrones(world, x, y, z, fortune));
                break;
            }
        }

        for (IHiveDrop drop : dropList) {
            if (world.rand.nextInt(100) < drop.getChance(world, x, y, z)) {
                ret.addAll(drop.getAdditional(world, x, y, z, fortune));
                break;
            }
        }
        return ret;
    }
}
