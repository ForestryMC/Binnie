package binnie.extratrees.block;

import binnie.core.block.ItemMetadata;
import binnie.core.block.TileEntityMetadata;
import binnie.extratrees.ExtraTrees;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemETDoor extends ItemMetadata {
    public ItemETDoor(Block block) {
        super(block);
        maxStackSize = 8;
        setCreativeTab(CreativeTabs.tabRedstone);
    }

    public static void placeDoorBlock(
            World world, int x, int y, int z, int meta, Block block, ItemStack item, EntityPlayer player) {
        byte b0 = 0;
        byte b2 = 0;
        if (meta == 0) {
            b2 = 1;
        }
        if (meta == 1) {
            b0 = -1;
        }
        if (meta == 2) {
            b2 = -1;
        }
        if (meta == 3) {
            b0 = 1;
        }

        int i1 = (world.isBlockNormalCubeDefault(x - b0, y, z - b2, false) ? 1 : 0)
                + (world.isBlockNormalCubeDefault(x - b0, y + 1, z - b2, false) ? 1 : 0);
        int j1 = (world.isBlockNormalCubeDefault(x + b0, y, z + b2, false) ? 1 : 0)
                + (world.isBlockNormalCubeDefault(x + b0, y + 1, z + b2, false) ? 1 : 0);
        boolean flag = world.getBlock(x - b0, y, z - b2) == block || world.getBlock(x - b0, y + 1, z - b2) == block;
        boolean flag2 = world.getBlock(x + b0, y, z + b2) == block || world.getBlock(x + b0, y + 1, z + b2) == block;
        boolean flag3 = false;
        if (flag && !flag2 || j1 > i1) {
            flag3 = true;
        }

        world.setBlock(x, y, z, block, meta, 2);
        world.setBlock(x, y + 1, z, block, 0x8 | (flag3 ? 1 : 0), 2);
        if (world.getBlock(x, y, z) == block) {
            TileEntityMetadata tile = TileEntityMetadata.getTile(world, x, y, z);
            if (tile != null) {
                tile.setTileMetadata(TileEntityMetadata.getItemDamage(item), false);
            }
            block.onBlockPlacedBy(world, x, y, z, player, item);
            block.onPostBlockPlaced(world, x, y, z, meta);
        }

        world.notifyBlocksOfNeighborChange(x, y, z, block);
        world.notifyBlocksOfNeighborChange(x, y + 1, z, block);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        for (DoorType type : DoorType.values()) {
            type.iconItem = ExtraTrees.proxy.getIcon(register, "door." + type.id);
        }
    }

    @Override
    public boolean onItemUse(
            ItemStack stack,
            EntityPlayer player,
            World world,
            int x,
            int y,
            int z,
            int meta,
            float hitX,
            float hitY,
            float hitZ) {
        if (meta != 1) {
            return false;
        }

        y++;
        Block block = ExtraTrees.blockDoor;
        if (!player.canPlayerEdit(x, y, z, meta, stack) || !player.canPlayerEdit(x, y + 1, z, meta, stack)) {
            return false;
        }
        if (!block.canPlaceBlockAt(world, x, y, z)) {
            return false;
        }

        int newMeta = MathHelper.floor_double((player.rotationYaw + 180.0f) * 4.0f / 360.0f - 0.5) & 0x3;
        placeDoorBlock(world, x, y, z, newMeta, block, stack, player);
        stack.stackSize--;
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getSpriteNumber() {
        return 1;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIconFromDamage(int damage) {
        DoorType type = BlockETDoor.getDoorType(damage);
        return type.iconItem;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int meta) {
        return WoodManager.getPlankType((stack == null) ? 0 : (stack.getItemDamage() & 0xFF))
                .getColor();
    }
}
