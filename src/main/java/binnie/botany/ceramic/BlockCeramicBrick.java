package binnie.botany.ceramic;

import binnie.Constants;
import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.botany.items.BotanyItems;
import binnie.botany.ceramic.BlockCeramicBrick.BlockType;
import binnie.core.block.BlockMetadata;
import binnie.core.block.IBlockMetadata;
import binnie.core.block.IMultipassBlock;
import binnie.core.block.TileEntityMetadata;
import forestry.api.core.ISpriteRegister;
import forestry.api.core.ITextureManager;
import forestry.core.blocks.IColoredBlock;
import forestry.core.render.TextureManager;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockCeramicBrick extends Block implements IBlockMetadata, IMultipassBlock<BlockType>, IColoredBlock, ISpriteRegister {
    public BlockCeramicBrick() {
        super(Material.ROCK);
        this.setHardness(1.0f);
        this.setResistance(5.0f);
        this.setRegistryName("ceramicBrick");
        this.setCreativeTab(CreativeTabBotany.instance);
    }

    private static BlockType getType(final int meta) {
        return new BlockType(meta);
    }
    
    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
    	return BlockMetadata.getBlockDropped(this, world, pos);
    }
    
    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
    	return BlockMetadata.breakBlock(this, player, world, pos);
    }

    @Override
    public TileEntity createNewTileEntity(final World var1, final int i) {
        return new TileEntityMetadata();
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
    	return true;
    }
    
   @Override
   public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param) {
       super.eventReceived(state, worldIn, pos, id, param);
       TileEntity tileentity = worldIn.getTileEntity(pos);
       return tileentity == null ? false : tileentity.receiveClientEvent(id, param);
   }
   
    @Override
    public int getPlacedMeta(final ItemStack stack, final World world, final BlockPos pos, final EnumFacing clickedBlock) {
        return TileEntityMetadata.getItemDamage(stack);
    }

    @Override
    public int getDroppedMeta(final int blockMeta, final int tileMeta) {
        return tileMeta;
    }

    @Override
    public String getBlockName(final ItemStack par1ItemStack) {
        final int meta = TileEntityMetadata.getItemDamage(par1ItemStack);
        return getType(meta).getName();
    }

    @Override
    public void getBlockTooltip(final ItemStack par1ItemStack, final List par3List) {
    }

    @Override
    public void dropAsStack(World world, BlockPos pos, ItemStack itemStack) {
    	spawnAsEntity(world, pos, itemStack);
    }

    @Override
    public void getSubBlocks(final Item itemIn, final CreativeTabs par2CreativeTabs, final List<ItemStack> itemList) {
        for (final EnumFlowerColor c : EnumFlowerColor.values()) {
            final BlockType type = new BlockType(c, c, TileType.Tile);
            itemList.add(TileEntityMetadata.getItemStack(this, type.ordinal()));
        }
        for (final TileType type2 : TileType.values()) {
            if (type2.canDouble()) {
                itemList.add(new BlockType(EnumFlowerColor.Brown, EnumFlowerColor.Gold, type2).getStack(1));
            }
        }
        itemList.add(new BlockType(EnumFlowerColor.Gold, EnumFlowerColor.Gold, TileType.Split).getStack(1));
        itemList.add(new BlockType(EnumFlowerColor.Brown, EnumFlowerColor.Brown, TileType.Chequered).getStack(1));
        itemList.add(new BlockType(EnumFlowerColor.Gold, EnumFlowerColor.Brown, TileType.LargeBrick).getStack(1));
    }

//	@Override
//	@SideOnly(Side.CLIENT)
//	public IIcon getIcon(final IBlockAccess world, final int x, final int y, final int z, final int side) {
//		final TileEntityMetadata tile = TileEntityMetadata.getTile(world, x, y, z);
//		if (tile != null) {
//			return this.getIcon(side, tile.getTileMetadata());
//		}
//		return super.getIcon(world, x, y, z, side);
//	}
//
//	@Override
//	public IIcon getIcon(final int side, final int meta) {
//		return getType(meta).getIcon(MultipassBlockRenderer.getLayer());
//	}
//
//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerBlockIcons(final IIconRegister register) {
//		for (final TileType type : TileType.values()) {
//			for (int i = 0; i < 3; ++i) {
//				type.icons[i] = Botany.proxy.getIcon(register, "ceramic." + type.id + "." + i);
//			}
//		}
//	}
    
    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
    	return BlockMetadata.getPickBlock(world, pos);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public int colorMultiplier(IBlockState state, IBlockAccess world, BlockPos pos, int tintIndex) {
    	BlockType type = getType(getMetaFromState(state));
    	if(tintIndex == 0){
    		return 16777215;
    	}if(tintIndex == 1){
    		return type.color1.getColor(false);
    	}
    	return type.color2.getColor(false);
    }

    public enum TileType {
        Tile("tile", "Ceramic Tile"),
        Brick("brick", "Ceramic Bricks"),
        StripeBrick("brickstripe", "Striped Ceramic Bricks"),
        LargeBrick("bricklarge", "Large Ceramic Bricks"),
        Split("split", "Split Ceramic Tile"),
        Chequered("cheque", "Chequered Ceramic Tile"),
        Mixed("mixed", "Mixed Ceramic Tile"),
        VerticalBrick("verticalbrick", "Vertical Ceramic Bricks"),
        VerticalStripeBrick("verticalbrickstripe", "Vertical Striped Ceramic Bricks"),
        VerticalLargeBrick("verticalbricklarge", "Large Vertical Ceramic Bricks");

        String id;
        String name;
        TextureAtlasSprite[] sprites;

        TileType(final String id, final String name) {
            this.sprites = new TextureAtlasSprite[3];
            this.id = id;
            this.name = name;
        }

        public static TileType get(final int id) {
            return values()[id % values().length];
        }

        public boolean canDouble() {
            return this != TileType.Tile;
        }

        public ItemStack getRecipe(List<ItemStack> stacks) {
            switch (this) {
                case Tile: {
                    if (stacks.size() != 4) {
                        return null;
                    }
                    int mortars = 0;
                    int blocks = 0;
                    int blockColor = -1;
                    for (final ItemStack stack : stacks) {
                        if (this.isMortar(stack)) {
                            ++mortars;
                        } else {
                            if (stack.getItem() != Item.getItemFromBlock(Botany.ceramic)) {
                                return null;
                            }
                            ++blocks;
                            final int color = TileEntityMetadata.getItemDamage(stack);
                            if (blockColor == -1) {
                                blockColor = color;
                            } else {
                                if (blockColor != color) {
                                    return null;
                                }
                                continue;
                            }
                        }
                    }
                    if (mortars != 1) {
                        return null;
                    }
                    final EnumFlowerColor c = EnumFlowerColor.get(blockColor);
                    return new BlockType(c, c, TileType.Tile).getStack(3);
                }
                case Split: {
                    if (stacks.size() != 4) {
                        return null;
                    }
                    final int[] colors = {-1, -1};
                    int altCounter = 0;
                    for (final ItemStack stack2 : stacks) {
                        final int alt = (altCounter != 0 && altCounter != 3) ? 1 : 0;
                        if (stack2.getItem() != Item.getItemFromBlock(Botany.ceramicBrick)) {
                            return null;
                        }
                        final BlockType type = new BlockType(stack2);
                        if (type.type != TileType.Tile) {
                            return null;
                        }
                        final int color2 = type.color1.ordinal();
                        if (colors[alt] == -1) {
                            colors[alt] = color2;
                        } else if (colors[alt] != color2) {
                            return null;
                        }
                        ++altCounter;
                    }
                    return new BlockType(EnumFlowerColor.get(colors[0]), EnumFlowerColor.get(colors[1]), TileType.Split).getStack(4);
                }
                case Chequered: {
                    if (stacks.size() != 4) {
                        return null;
                    }
                    final int[] colors = {-1, -1};
                    for (final ItemStack stack2 : stacks) {
                        if (stack2.getItem() != Item.getItemFromBlock(Botany.ceramicBrick)) {
                            return null;
                        }
                        final BlockType type2 = new BlockType(stack2);
                        if (type2.type != TileType.Split) {
                            return null;
                        }
                        final int color3 = type2.color1.ordinal();
                        final int color4 = type2.color2.ordinal();
                        if (colors[0] == -1) {
                            colors[0] = color3;
                            colors[1] = color4;
                        } else {
                            if (colors[0] != color3 || colors[1] != color4) {
                                return null;
                            }
                            continue;
                        }
                    }
                    return new BlockType(EnumFlowerColor.get(colors[0]), EnumFlowerColor.get(colors[1]), TileType.Chequered).getStack(4);
                }
                case Mixed: {
                    if (stacks.size() != 4) {
                        return null;
                    }
                    final int[] colors = {-1, -1};
                    for (final ItemStack stack2 : stacks) {
                        if (stack2.getItem() != Item.getItemFromBlock(Botany.ceramicBrick)) {
                            return null;
                        }
                        final BlockType type2 = new BlockType(stack2);
                        if (type2.isTwoColors()) {
                            return null;
                        }
                        final int color3 = type2.color1.ordinal();
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
                        final int color4 = type2.color2.ordinal();
                    }
                    if (colors[0] == -1 || colors[1] == -1) {
                        return null;
                    }
                    return new BlockType(EnumFlowerColor.get(colors[0]), EnumFlowerColor.get(colors[1]), TileType.Mixed).getStack(4);
                }
                case LargeBrick: {
                    if (stacks.size() == 1) {
                        final ItemStack stack3 = stacks.get(0);
                        final BlockType type3 = new BlockType(stack3);
                        if (type3.type == TileType.VerticalLargeBrick) {
                            type3.type = this;
                            return type3.getStack(1);
                        }
                        return null;
                    } else {
                        if (stacks.size() != 3) {
                            return null;
                        }
                        final int[] colors = {-1, -1};
                        int a = 0;
                        int b = 0;
                        for (final ItemStack stack4 : stacks) {
                            if (stack4.getItem() != Item.getItemFromBlock(Botany.ceramicBrick)) {
                                return null;
                            }
                            final BlockType type4 = new BlockType(stack4);
                            if (type4.type != TileType.Tile) {
                                return null;
                            }
                            final int color5 = type4.color1.ordinal();
                            if (colors[0] == -1) {
                                colors[0] = color5;
                                ++a;
                            } else if (colors[0] == color5) {
                                ++a;
                            } else if (colors[1] == -1) {
                                colors[1] = color5;
                                ++b;
                            } else {
                                if (colors[1] != color5) {
                                    return null;
                                }
                                ++b;
                            }
                        }
                        if (colors[1] == -1) {
                            colors[1] = colors[0];
                        }
                        return new BlockType(EnumFlowerColor.get(colors[0]), EnumFlowerColor.get(colors[1]), TileType.LargeBrick).getStack(3);
                    }
                }
                case Brick:
                case StripeBrick: {
                    if (stacks.size() == 1) {
                        final ItemStack stack5 = stacks.get(0);
                        final BlockType type = new BlockType(stack5);
                        if (type.type == TileType.VerticalBrick) {
                            type.type = this;
                            return type.getStack(1);
                        }
                        if (type.type == TileType.StripeBrick) {
                            type.type = this;
                            return type.getStack(1);
                        }
                        return null;
                    } else {
                        if (stacks.size() != 4) {
                            return null;
                        }
                        final int[] colors = {-1, -1};
                        for (int index2 = 0; index2 < stacks.size(); ++index2) {
                            final ItemStack stack4 = stacks.get(index2);
                            if (stack4.getItem() != Item.getItemFromBlock(Botany.ceramicBrick)) {
                                return null;
                            }
                            final BlockType type4 = new BlockType(stack4);
                            if (type4.type != TileType.LargeBrick) {
                                return null;
                            }
                            final int color5 = type4.color1.ordinal();
                            final int color6 = type4.color2.ordinal();
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
                }
                case VerticalLargeBrick:
                case VerticalBrick:
                case VerticalStripeBrick: {
                    if (stacks.size() != 1) {
                        return null;
                    }
                    final ItemStack stack5 = stacks.get(0);
                    final BlockType type = new BlockType(stack5);
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
                default: {
                    return null;
                }
            }
        }

        private boolean isMortar(final ItemStack stack) {
            return stack.getItem() == Botany.misc && stack.getItemDamage() == BotanyItems.Mortar.ordinal();
        }
    }

    public static class BlockType {
        EnumFlowerColor color1;
        EnumFlowerColor color2;
        TileType type;

        private BlockType(final EnumFlowerColor color1, final EnumFlowerColor color2, final TileType type) {
            this.color1 = color1;
            this.color2 = color2;
            this.type = type;
        }

        public boolean isTwoColors() {
            return this.type.canDouble() && this.color2 != this.color1;
        }

        public BlockType(final ItemStack stack) {
            this(TileEntityMetadata.getItemDamage(stack));
        }

        public ItemStack getStack(final int i) {
            final ItemStack s = TileEntityMetadata.getItemStack(Botany.ceramicBrick, this.ordinal());
            s.stackSize = i;
            return s;
        }

        public BlockType(final int id) {
            this.color1 = EnumFlowerColor.get(id & 0xFF);
            this.color2 = EnumFlowerColor.get(id >> 8 & 0xFF);
            this.type = TileType.get(id >> 16 & 0xFF);
        }

        public String getName() {
            String name = this.color1.getColourName();
            if (this.type.canDouble() && this.color2 != this.color1) {
                name = name + " & " + this.color2.getColourName();
            }
            return name + " " + this.type.name;
        }

        public int ordinal() {
            return this.color1.ordinal() + this.color2.ordinal() * 256 + this.type.ordinal() * 256 * 256;
        }

		public TextureAtlasSprite getSprite(int pass) {
			return this.type.sprites[pass];
		}
    }

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderPasses() {
		return 3;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockType getInventoryKey(ItemStack stack) {
		return getType(stack.getItemDamage());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockType getWorldKey(IBlockState state) {
		return getType(getMetaFromState(state));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getSprite(BlockType type, int pass) {
		return type.getSprite(pass);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerSprites(ITextureManager manager) {
		for (final TileType type : TileType.values()) {
			for (int i = 0; i < 3; ++i) {
				type.sprites[i] = TextureManager.registerSprite(new ResourceLocation(Constants.BOTANY_MOD_ID + ":blocks/ceramic." + type.id + "." + i));
			}
		}
	}
}
