package binnie.botany.ceramic;

import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.botany.items.BotanyItems;
import binnie.core.BinnieCore;
import binnie.core.block.BlockMetadata;
import binnie.core.block.IBlockMetadata;
import binnie.core.block.IMultipassBlock;
import binnie.core.block.MultipassBlockRenderer;
import binnie.core.block.TileEntityMetadata;
import binnie.core.util.I18N;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockCeramicBrick extends Block implements IBlockMetadata, IMultipassBlock {
    public BlockCeramicBrick() {
        super(Material.rock);
        setHardness(1.0f);
        setResistance(5.0f);
        setBlockName("ceramicBrick");
        setCreativeTab(CreativeTabBotany.instance);
    }

    private static BlockType getType(int meta) {
        return new BlockType(meta);
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int blockMeta, int fortune) {
        return BlockMetadata.getBlockDropped(this, world, x, y, z, blockMeta);
    }

    @Override
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z) {
        return BlockMetadata.breakBlock(this, player, world, x, y, z);
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int i) {
        return new TileEntityMetadata();
    }

    @Override
    public boolean hasTileEntity(int meta) {
        return true;
    }

    @Override
    public boolean onBlockEventReceived(World world, int x, int y, int z, int eventId, int eventType) {
        super.onBlockEventReceived(world, x, y, z, eventId, eventType);
        TileEntity tileentity = world.getTileEntity(x, y, z);
        return tileentity != null && tileentity.receiveClientEvent(eventId, eventType);
    }

    @Override
    public int getPlacedMeta(ItemStack itemStack, World world, int x, int y, int z, ForgeDirection direction) {
        return TileEntityMetadata.getItemDamage(itemStack);
    }

    @Override
    public int getDroppedMeta(int blockMeta, int tileMeta) {
        return tileMeta;
    }

    @Override
    public String getBlockName(ItemStack itemStack) {
        int meta = TileEntityMetadata.getItemDamage(itemStack);
        return getType(meta).getName();
    }

    @Override
    public void addBlockTooltip(ItemStack itemStack, List tooltip) {
        int meta = TileEntityMetadata.getItemDamage(itemStack);
        getType(meta).addTooltip(tooltip);
    }

    @Override
    public void dropAsStack(World world, int x, int y, int z, ItemStack itemStack) {
        dropBlockAsItem(world, x, y, z, itemStack);
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List itemList) {
        for (EnumFlowerColor c : EnumFlowerColor.values()) {
            BlockType type = new BlockType(c, c, TileType.Tile);
            itemList.add(TileEntityMetadata.getItemStack(this, type.ordinal()));
        }

        for (TileType type2 : TileType.values()) {
            if (type2.canDouble()) {
                itemList.add(new BlockType(EnumFlowerColor.BROWN, EnumFlowerColor.GOLD, type2).getStack(1));
            }
        }

        itemList.add(new BlockType(EnumFlowerColor.GOLD, EnumFlowerColor.GOLD, TileType.Split).getStack(1));
        itemList.add(new BlockType(EnumFlowerColor.BROWN, EnumFlowerColor.BROWN, TileType.Chequered).getStack(1));
        itemList.add(new BlockType(EnumFlowerColor.GOLD, EnumFlowerColor.BROWN, TileType.LargeBrick).getStack(1));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        TileEntityMetadata tile = TileEntityMetadata.getTile(world, x, y, z);
        if (tile != null) {
            return getIcon(side, tile.getTileMetadata());
        }
        return super.getIcon(world, x, y, z, side);
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return getType(meta).getIcon(MultipassBlockRenderer.getLayer());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        for (TileType type : TileType.values()) {
            for (int i = 0; i < 3; ++i) {
                type.icons[i] = Botany.proxy.getIcon(register, "ceramic." + type.id + "." + i);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
        TileEntityMetadata tile = TileEntityMetadata.getTile(world, x, y, z);
        if (tile != null) {
            return getRenderColor(tile.getTileMetadata());
        }
        return 0xffffff;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int side) {
        super.breakBlock(world, x, y, z, block, side);
        world.removeTileEntity(x, y, z);
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
        return BlockMetadata.getPickBlock(world, x, y, z);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderColor(int meta) {
        return colorMultiplier(meta);
    }

    @Override
    public int getNumberOfPasses() {
        return 3;
    }

    @Override
    public int colorMultiplier(int meta) {
        BlockType type = getType(meta);
        if (MultipassBlockRenderer.getLayer() == 0) {
            return 0xffffff;
        }
        if (MultipassBlockRenderer.getLayer() == 1) {
            return type.color1.getColor(false);
        }
        return type.color2.getColor(false);
    }

    @Override
    public int getRenderType() {
        return BinnieCore.multipassRenderID;
    }

    public enum TileType {
        Tile("tile", "ceramicTile"),
        Brick("brick", "ceramicBricks"),
        StripeBrick("brickstripe", "strippedCeramicBricks"),
        LargeBrick("bricklarge", "largeCeramicBricks"),
        Split("split", "splitCeramicTile"),
        Chequered("cheque", "chequeredCeramicTile"),
        Mixed("mixed", "mixedCeramicTile"),
        VerticalBrick("verticalbrick", "verticalCeramicBricks"),
        VerticalStripeBrick("verticalbrickstripe", "verticalStripedCeramicBricks"),
        VerticalLargeBrick("verticalbricklarge", "largeVerticalCeramicBricks");

        protected String id;
        protected String name;
        protected IIcon[] icons;

        TileType(String id, String name) {
            icons = new IIcon[3];
            this.id = id;
            this.name = name;
        }

        public static TileType get(int id) {
            return values()[id % values().length];
        }

        public boolean canDouble() {
            return this != TileType.Tile;
        }

        public ItemStack getRecipe(List<ItemStack> stacks) {
            switch (this) {
                case Tile:
                    return getTileRecipe(stacks);

                case Split:
                    return getSplitRecipe(stacks);

                case Chequered:
                    return getChequeredRecipe(stacks);

                case Mixed:
                    return getMixedRecipe(stacks);

                case LargeBrick:
                    return getLargeBrickRecipe(stacks);

                case Brick:
                case StripeBrick:
                    return getBrickRecipe(stacks);

                case VerticalLargeBrick:
                case VerticalBrick:
                case VerticalStripeBrick:
                    return getVerticalBrickRecipe(stacks);
            }
            return null;
        }

        private ItemStack getTileRecipe(List<ItemStack> stacks) {
            if (stacks.size() != 4) {
                return null;
            }

            int mortars = 0;
            int blockColor = -1;
            for (ItemStack stack : stacks) {
                if (isMortar(stack)) {
                    mortars++;
                } else {
                    if (stack.getItem() != Item.getItemFromBlock(Botany.ceramic)) {
                        return null;
                    }

                    int color = TileEntityMetadata.getItemDamage(stack);
                    if (blockColor == -1) {
                        blockColor = color;
                    } else if (blockColor != color) {
                        return null;
                    }
                }
            }

            if (mortars != 1) {
                return null;
            }
            EnumFlowerColor c = EnumFlowerColor.get(blockColor);
            return new BlockType(c, c, TileType.Tile).getStack(3);
        }

        private ItemStack getSplitRecipe(List<ItemStack> stacks) {
            if (stacks.size() != 4) {
                return null;
            }

            int[] colors = {-1, -1};
            int altCounter = 0;
            for (ItemStack stack : stacks) {
                int alt = (altCounter != 0 && altCounter != 3) ? 1 : 0;
                if (stack.getItem() != Item.getItemFromBlock(Botany.ceramicBrick)) {
                    return null;
                }

                BlockType type = new BlockType(stack);
                if (type.type != TileType.Tile) {
                    return null;
                }

                int color2 = type.color1.ordinal();
                if (colors[alt] == -1) {
                    colors[alt] = color2;
                } else if (colors[alt] != color2) {
                    return null;
                }
                altCounter++;
            }
            return new BlockType(EnumFlowerColor.get(colors[0]), EnumFlowerColor.get(colors[1]), TileType.Split)
                    .getStack(4);
        }

        private ItemStack getChequeredRecipe(List<ItemStack> stacks) {
            if (stacks.size() != 4) {
                return null;
            }

            int[] colors = {-1, -1};
            for (ItemStack stack : stacks) {
                if (stack.getItem() != Item.getItemFromBlock(Botany.ceramicBrick)) {
                    return null;
                }

                BlockType type2 = new BlockType(stack);
                if (type2.type != TileType.Split) {
                    return null;
                }

                int color1 = type2.color1.ordinal();
                int color2 = type2.color2.ordinal();
                if (colors[0] == -1) {
                    colors[0] = color1;
                    colors[1] = color2;
                } else if (colors[0] != color1 || colors[1] != color2) {
                    return null;
                }
            }
            return new BlockType(EnumFlowerColor.get(colors[0]), EnumFlowerColor.get(colors[1]), TileType.Chequered)
                    .getStack(4);
        }

        private ItemStack getMixedRecipe(List<ItemStack> stacks) {
            if (stacks.size() != 4) {
                return null;
            }

            int[] colors = {-1, -1};
            for (ItemStack stack : stacks) {
                if (stack.getItem() != Item.getItemFromBlock(Botany.ceramicBrick)) {
                    return null;
                }

                BlockType type2 = new BlockType(stack);
                if (type2.isTwoColors()) {
                    return null;
                }

                int color3 = type2.color1.ordinal();
                if (type2.type == TileType.Split) {
                    if (colors[1] != -1) {
                        return null;
                    }
                    colors[1] = color3;
                } else {
                    if (type2.type != TileType.Chequered) {
                        return null;
                    }

                    if (colors[0] == -1) {
                        colors[0] = color3;
                    } else if (colors[0] != color3) {
                        return null;
                    }
                }
            }

            if (colors[0] == -1 || colors[1] == -1) {
                return null;
            }
            return new BlockType(EnumFlowerColor.get(colors[0]), EnumFlowerColor.get(colors[1]), TileType.Mixed)
                    .getStack(4);
        }

        private ItemStack getLargeBrickRecipe(List<ItemStack> stacks) {
            if (stacks.size() == 1) {
                ItemStack stack3 = stacks.get(0);
                BlockType type3 = new BlockType(stack3);
                if (type3.type == TileType.VerticalLargeBrick) {
                    type3.type = this;
                    return type3.getStack(1);
                }
                return null;
            }

            if (stacks.size() != 3) {
                return null;
            }

            int[] colors = {-1, -1};
            for (ItemStack stack4 : stacks) {
                if (stack4.getItem() != Item.getItemFromBlock(Botany.ceramicBrick)) {
                    return null;
                }

                BlockType type4 = new BlockType(stack4);
                if (type4.type != TileType.Tile) {
                    return null;
                }

                int color5 = type4.color1.ordinal();
                if (colors[0] == -1) {
                    colors[0] = color5;
                } else if (colors[1] == -1) {
                    colors[1] = color5;
                } else if (colors[1] != color5) {
                    return null;
                }
            }

            if (colors[1] == -1) {
                colors[1] = colors[0];
            }
            return new BlockType(EnumFlowerColor.get(colors[0]), EnumFlowerColor.get(colors[1]), TileType.LargeBrick)
                    .getStack(3);
        }

        private ItemStack getBrickRecipe(List<ItemStack> stacks) {
            if (stacks.size() == 1) {
                ItemStack stack = stacks.get(0);
                BlockType type = new BlockType(stack);
                if (type.type == TileType.VerticalBrick) {
                    type.type = this;
                    return type.getStack(1);
                }

                if (type.type == TileType.StripeBrick) {
                    type.type = this;
                    return type.getStack(1);
                }
                return null;
            }

            if (stacks.size() != 4) {
                return null;
            }

            int[] colors = {-1, -1};
            for (int index2 = 0; index2 < stacks.size(); ++index2) {
                ItemStack stack = stacks.get(index2);
                if (stack.getItem() != Item.getItemFromBlock(Botany.ceramicBrick)) {
                    return null;
                }

                BlockType type = new BlockType(stack);
                if (type.type != TileType.LargeBrick) {
                    return null;
                }

                int color5 = type.color1.ordinal();
                int color6 = type.color2.ordinal();
                int alt2 = (index2 != 0 && index2 != 3) ? 1 : 0;
                if (this == TileType.StripeBrick) {
                    alt2 = 0;
                }

                if (colors[alt2] == -1) {
                    colors[alt2] = color5;
                    colors[1 - alt2] = color6;
                } else if (colors[alt2] != color5 || colors[1 - alt2] != color6) {
                    return null;
                }
            }
            return new BlockType(EnumFlowerColor.get(colors[0]), EnumFlowerColor.get(colors[1]), this).getStack(4);
        }

        private ItemStack getVerticalBrickRecipe(List<ItemStack> stacks) {
            if (stacks.size() != 1) {
                return null;
            }

            ItemStack stack5 = stacks.get(0);
            BlockType type = new BlockType(stack5);
            if (type.type == TileType.LargeBrick) {
                type.type = TileType.VerticalLargeBrick;
                return type.getStack(1);
            }

            if (type.type == TileType.Brick) {
                type.type = TileType.VerticalBrick;
                return type.getStack(1);
            }

            if (type.type == TileType.StripeBrick) {
                type.type = TileType.VerticalStripeBrick;
                return type.getStack(1);
            }
            return null;
        }

        private boolean isMortar(ItemStack stack) {
            return stack.getItem() == Botany.misc && stack.getItemDamage() == BotanyItems.Mortar.ordinal();
        }
    }

    public static class BlockType {
        protected EnumFlowerColor color1;
        protected EnumFlowerColor color2;
        protected TileType type;

        private BlockType(EnumFlowerColor color1, EnumFlowerColor color2, TileType type) {
            this.color1 = color1;
            this.color2 = color2;
            this.type = type;
        }

        public boolean isTwoColors() {
            return type.canDouble() && color2 != color1;
        }

        public BlockType(ItemStack stack) {
            this(TileEntityMetadata.getItemDamage(stack));
        }

        public ItemStack getStack(int stackSize) {
            ItemStack stack = TileEntityMetadata.getItemStack(Botany.ceramicBrick, ordinal());
            stack.stackSize = stackSize;
            return stack;
        }

        public BlockType(int id) {
            color1 = EnumFlowerColor.get(id & 0xFF);
            color2 = EnumFlowerColor.get(id >> 8 & 0xFF);
            type = TileType.get(id >> 16 & 0xFF);
        }

        public String getName() {
            return I18N.localise("botany." + type.name);
        }

        public void addTooltip(List tooltip) {
            String name = color1.getName();
            if (type.canDouble() && color2 != color1) {
                name = I18N.localise("botany.colour.double", name, color2.getName());
            }
            tooltip.add(EnumChatFormatting.GRAY + name);
        }

        public int ordinal() {
            return color1.ordinal() + color2.ordinal() * 256 + type.ordinal() * 65536;
        }

        public IIcon getIcon(int layer) {
            return type.icons[layer];
        }
    }
}
