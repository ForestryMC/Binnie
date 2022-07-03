package binnie.extratrees.block;

import binnie.core.block.IBlockMetadata;
import binnie.core.block.TileEntityMetadata;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemETStairs extends ItemBlock {
    public ItemETStairs(Block block) {
        super(block);
        setCreativeTab(CreativeTabs.tabBlock);
        setUnlocalizedName("stairs");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int par1) {
        return PlankType.ExtraTreePlanks.values()[par1].getIcon();
    }

    @Override
    public boolean placeBlockAt(
            ItemStack stack,
            EntityPlayer player,
            World world,
            int x,
            int y,
            int z,
            int side,
            float hitX,
            float hitY,
            float hitZ,
            int metadata) {
        boolean done = super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);
        TileEntityMetadata tile = (TileEntityMetadata) world.getTileEntity(x, y, z);
        if (tile != null) {
            tile.setTileMetadata(stack.getItemDamage(), false);
        }
        return done;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack par1ItemStack) {
        return ((IBlockMetadata) field_150939_a).getBlockName(par1ItemStack);
    }
}
