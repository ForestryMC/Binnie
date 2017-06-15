package binnie.botany.ceramic.brick;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.botany.Botany;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.botany.items.BotanyItems;
import binnie.core.block.TileEntityMetadata;

public enum CeramicBrickType implements IStringSerializable {
	Tile("tile", "Ceramic Tile") {
		@Override
		public ItemStack getRecipe(List<ItemStack> stacks) {
			if (stacks.size() != 4) {
				return ItemStack.EMPTY;
			}
			int mortars = 0;
			int blocks = 0;
			int blockColor = -1;
			for (final ItemStack stack : stacks) {
				if (this.isMortar(stack)) {
					++mortars;
				} else {
					if (stack.getItem() != Item.getItemFromBlock(Botany.ceramic)) {
						return ItemStack.EMPTY;
					}
					++blocks;
					final int color = TileEntityMetadata.getItemDamage(stack);
					if (blockColor == -1) {
						blockColor = color;
					} else {
						if (blockColor != color) {
							return ItemStack.EMPTY;
						}
					}
				}
			}
			if (mortars != 1) {
				return ItemStack.EMPTY;
			}
			final EnumFlowerColor c = EnumFlowerColor.get(blockColor);
			return new CeramicBrickPair(c, c, CeramicBrickType.Tile).getStack(3);
		}
	},
	Brick("brick", "Ceramic Bricks") {
		@Override
		public ItemStack getRecipe(List<ItemStack> stacks) {
			return getBrickRecipe(this, stacks);
		}
	},
	StripeBrick("brickstripe", "Striped Ceramic Bricks") {
		@Override
		public ItemStack getRecipe(List<ItemStack> stacks) {
			return getBrickRecipe(this, stacks);
		}
	},
	LargeBrick("bricklarge", "Large Ceramic Bricks") {
		@Override
		public ItemStack getRecipe(List<ItemStack> stacks) {
			if (stacks.size() == 1) {
				final ItemStack stack3 = stacks.get(0);
				final CeramicBrickPair type3 = new CeramicBrickPair(stack3);
				if (type3.type == CeramicBrickType.VerticalLargeBrick) {
					type3.type = this;
					return type3.getStack(1);
				}
				return ItemStack.EMPTY;
			} else {
				if (stacks.size() != 3) {
					return ItemStack.EMPTY;
				}
				final int[] colors = {-1, -1};
				int a = 0;
				int b = 0;
				for (final ItemStack stack4 : stacks) {
					if (stack4.getItem() != Item.getItemFromBlock(Botany.ceramicBrick)) {
						return ItemStack.EMPTY;
					}
					final CeramicBrickPair type4 = new CeramicBrickPair(stack4);
					if (type4.type != CeramicBrickType.Tile) {
						return ItemStack.EMPTY;
					}
					final int color5 = type4.colorFirst.ordinal();
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
							return ItemStack.EMPTY;
						}
						++b;
					}
				}
				if (colors[1] == -1) {
					colors[1] = colors[0];
				}
				return new CeramicBrickPair(EnumFlowerColor.get(colors[0]), EnumFlowerColor.get(colors[1]), CeramicBrickType.LargeBrick).getStack(3);
			}
		}
	},
	Split("split", "Split Ceramic Tile") {
		@Override
		public ItemStack getRecipe(List<ItemStack> stacks) {
			if (stacks.size() != 4) {
				return ItemStack.EMPTY;
			}
			final int[] colors = {-1, -1};
			int altCounter = 0;
			for (final ItemStack stack2 : stacks) {
				final int alt = (altCounter != 0 && altCounter != 3) ? 1 : 0;
				if (stack2.getItem() != Item.getItemFromBlock(Botany.ceramicBrick)) {
					return ItemStack.EMPTY;
				}
				final CeramicBrickPair type = new CeramicBrickPair(stack2);
				if (type.type != CeramicBrickType.Tile) {
					return ItemStack.EMPTY;
				}
				final int color2 = type.colorFirst.ordinal();
				if (colors[alt] == -1) {
					colors[alt] = color2;
				} else if (colors[alt] != color2) {
					return ItemStack.EMPTY;
				}
				++altCounter;
			}
			return new CeramicBrickPair(EnumFlowerColor.get(colors[0]), EnumFlowerColor.get(colors[1]), CeramicBrickType.Split).getStack(4);
		}
	},
	Chequered("cheque", "Chequered Ceramic Tile") {
		@Override
		public ItemStack getRecipe(List<ItemStack> stacks) {
			if (stacks.size() != 4) {
				return ItemStack.EMPTY;
			}
			final int[] colors = {-1, -1};
			for (final ItemStack stack2 : stacks) {
				if (stack2.getItem() != Item.getItemFromBlock(Botany.ceramicBrick)) {
					return ItemStack.EMPTY;
				}
				final CeramicBrickPair type2 = new CeramicBrickPair(stack2);
				if (type2.type != CeramicBrickType.Split) {
					return ItemStack.EMPTY;
				}
				final int color3 = type2.colorFirst.ordinal();
				final int color4 = type2.colorSecond.ordinal();
				if (colors[0] == -1) {
					colors[0] = color3;
					colors[1] = color4;
				} else {
					if (colors[0] != color3 || colors[1] != color4) {
						return ItemStack.EMPTY;
					}
				}
			}
			return new CeramicBrickPair(EnumFlowerColor.get(colors[0]), EnumFlowerColor.get(colors[1]), CeramicBrickType.Chequered).getStack(4);
		}
	},
	Mixed("mixed", "Mixed Ceramic Tile") {
		@Override
		public ItemStack getRecipe(List<ItemStack> stacks) {
			if (stacks.size() != 4) {
				return ItemStack.EMPTY;
			}
			final int[] colors = {-1, -1};
			for (final ItemStack stack2 : stacks) {
				if (stack2.getItem() != Item.getItemFromBlock(Botany.ceramicBrick)) {
					return ItemStack.EMPTY;
				}
				final CeramicBrickPair type2 = new CeramicBrickPair(stack2);
				if (type2.isTwoColors()) {
					return ItemStack.EMPTY;
				}
				final int color3 = type2.colorFirst.ordinal();
				if (type2.type == CeramicBrickType.Split) {
					if (colors[1] != -1) {
						return ItemStack.EMPTY;
					}
					colors[1] = color3;
				} else {
					if (type2.type != CeramicBrickType.Chequered) {
						return ItemStack.EMPTY;
					}
					if (colors[0] == -1) {
						colors[0] = color3;
					} else if (colors[0] != color3) {
						return ItemStack.EMPTY;
					}
				}
				final int color4 = type2.colorSecond.ordinal();
			}
			if (colors[0] == -1 || colors[1] == -1) {
				return ItemStack.EMPTY;
			}
			return new CeramicBrickPair(EnumFlowerColor.get(colors[0]), EnumFlowerColor.get(colors[1]), CeramicBrickType.Mixed).getStack(4);
		}
	},
	VerticalBrick("verticalbrick", "Vertical Ceramic Bricks") {
		@Override
		public ItemStack getRecipe(List<ItemStack> stacks) {
			return getVerticalBrickRecipe(stacks);
		}
	},
	VerticalStripeBrick("verticalbrickstripe", "Vertical Striped Ceramic Bricks") {
		@Override
		public ItemStack getRecipe(List<ItemStack> stacks) {
			return getVerticalBrickRecipe(stacks);
		}
	},
	VerticalLargeBrick("verticalbricklarge", "Large Vertical Ceramic Bricks") {
		@Override
		public ItemStack getRecipe(List<ItemStack> stacks) {
			return getVerticalBrickRecipe(stacks);
		}
	};

	public static final CeramicBrickType[] VALUES = values();

	String id;
	String name;

	@SideOnly(Side.CLIENT)
	@Nullable
	TextureAtlasSprite[] sprites;

	CeramicBrickType(final String id, final String name) {
		this.id = id;
		this.name = name;
	}

	public static CeramicBrickType get(final int id) {
		return VALUES[id % VALUES.length];
	}

	public boolean canDouble() {
		return this != CeramicBrickType.Tile;
	}

	public abstract ItemStack getRecipe(List<ItemStack> stacks);

	protected ItemStack getVerticalBrickRecipe(List<ItemStack> stacks) {
		if (stacks.size() != 1) {
			return ItemStack.EMPTY;
		}
		final ItemStack stack5 = stacks.get(0);
		final CeramicBrickPair type = new CeramicBrickPair(stack5);
		if (type.type == CeramicBrickType.LargeBrick) {
			type.type = CeramicBrickType.VerticalLargeBrick;
			return type.getStack(1);
		}
		if (type.type == CeramicBrickType.Brick) {
			type.type = CeramicBrickType.VerticalBrick;
			return type.getStack(1);
		}
		if (type.type == CeramicBrickType.StripeBrick) {
			type.type = CeramicBrickType.VerticalStripeBrick;
			return type.getStack(1);
		}
		return ItemStack.EMPTY;
	}

	protected ItemStack getBrickRecipe(CeramicBrickType ceramicType, List<ItemStack> stacks) {
		if (stacks.size() == 1) {
			final ItemStack stack5 = stacks.get(0);
			final CeramicBrickPair type = new CeramicBrickPair(stack5);
			if (type.type == CeramicBrickType.VerticalBrick) {
				type.type = ceramicType;
				return type.getStack(1);
			}
			if (type.type == CeramicBrickType.StripeBrick) {
				type.type = ceramicType;
				return type.getStack(1);
			}
			return ItemStack.EMPTY;
		} else if (stacks.size() == 4) {
			final int[] colors = {-1, -1};
			for (int index2 = 0; index2 < stacks.size(); ++index2) {
				final ItemStack stack4 = stacks.get(index2);
				if (stack4.getItem() != Item.getItemFromBlock(Botany.ceramicBrick)) {
					return ItemStack.EMPTY;
				}
				final CeramicBrickPair type4 = new CeramicBrickPair(stack4);
				if (type4.type != CeramicBrickType.LargeBrick) {
					return ItemStack.EMPTY;
				}
				final int color5 = type4.colorFirst.ordinal();
				final int color6 = type4.colorSecond.ordinal();
				int alt2 = (index2 != 0 && index2 != 3) ? 1 : 0;
				if (ceramicType == CeramicBrickType.StripeBrick) {
					alt2 = 0;
				}
				if (colors[alt2] == -1) {
					colors[alt2] = color5;
					colors[1 - alt2] = color6;
				} else if (colors[alt2] != color5 || colors[1 - alt2] != color6) {
					return ItemStack.EMPTY;
				}
			}
			return new CeramicBrickPair(EnumFlowerColor.get(colors[0]), EnumFlowerColor.get(colors[1]), ceramicType).getStack(4);
		} else {
			return ItemStack.EMPTY;
		}
	}

	protected boolean isMortar(final ItemStack stack) {
		return stack.getItem() == Botany.misc && stack.getItemDamage() == BotanyItems.Mortar.ordinal();
	}

	@Override
	public String getName() {
		return name().toLowerCase(Locale.ENGLISH);
	}
}
