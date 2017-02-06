package binnie.botany.ceramic.brick;

import java.util.List;
import java.util.Locale;

import binnie.botany.Botany;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.botany.items.BotanyItems;
import binnie.core.block.TileEntityMetadata;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nullable;

public enum CeramicBrickType implements IStringSerializable {
	Tile("tile", "Ceramic Tile"){
		@Nullable
		@Override
		public ItemStack getRecipe(List<ItemStack> stacks) {
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
			return new CeramicBrickPair(c, c, CeramicBrickType.Tile).getStack(3);
		}
	},
	Brick("brick", "Ceramic Bricks"){
		@Nullable
		@Override
		public ItemStack getRecipe(List<ItemStack> stacks) {
			return getBrickRecipe(this, stacks);
		}
	},
	StripeBrick("brickstripe", "Striped Ceramic Bricks"){
		@Nullable
		@Override
		public ItemStack getRecipe(List<ItemStack> stacks) {
			return getBrickRecipe(this, stacks);
		}
	},
	LargeBrick("bricklarge", "Large Ceramic Bricks"){
		@Nullable
		@Override
		public ItemStack getRecipe(List<ItemStack> stacks) {
			if (stacks.size() == 1) {
				final ItemStack stack3 = stacks.get(0);
				final CeramicBrickPair type3 = new CeramicBrickPair(stack3);
				if (type3.type == CeramicBrickType.VerticalLargeBrick) {
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
					final CeramicBrickPair type4 = new CeramicBrickPair(stack4);
					if (type4.type != CeramicBrickType.Tile) {
						return null;
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
							return null;
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
	Split("split", "Split Ceramic Tile"){
		@Nullable
		@Override
		public ItemStack getRecipe(List<ItemStack> stacks) {
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
				final CeramicBrickPair type = new CeramicBrickPair(stack2);
				if (type.type != CeramicBrickType.Tile) {
					return null;
				}
				final int color2 = type.colorFirst.ordinal();
				if (colors[alt] == -1) {
					colors[alt] = color2;
				} else if (colors[alt] != color2) {
					return null;
				}
				++altCounter;
			}
			return new CeramicBrickPair(EnumFlowerColor.get(colors[0]), EnumFlowerColor.get(colors[1]), CeramicBrickType.Split).getStack(4);
		}
	},
	Chequered("cheque", "Chequered Ceramic Tile"){
		@Nullable
		@Override
		public ItemStack getRecipe(List<ItemStack> stacks) {
			if (stacks.size() != 4) {
				return null;
			}
			final int[] colors = {-1, -1};
			for (final ItemStack stack2 : stacks) {
				if (stack2.getItem() != Item.getItemFromBlock(Botany.ceramicBrick)) {
					return null;
				}
				final CeramicBrickPair type2 = new CeramicBrickPair(stack2);
				if (type2.type != CeramicBrickType.Split) {
					return null;
				}
				final int color3 = type2.colorFirst.ordinal();
				final int color4 = type2.colorSecond.ordinal();
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
			return new CeramicBrickPair(EnumFlowerColor.get(colors[0]), EnumFlowerColor.get(colors[1]), CeramicBrickType.Chequered).getStack(4);
		}
	},
	Mixed("mixed", "Mixed Ceramic Tile"){
		@Nullable
		@Override
		public ItemStack getRecipe(List<ItemStack> stacks) {
			if (stacks.size() != 4) {
				return null;
			}
			final int[] colors = {-1, -1};
			for (final ItemStack stack2 : stacks) {
				if (stack2.getItem() != Item.getItemFromBlock(Botany.ceramicBrick)) {
					return null;
				}
				final CeramicBrickPair type2 = new CeramicBrickPair(stack2);
				if (type2.isTwoColors()) {
					return null;
				}
				final int color3 = type2.colorFirst.ordinal();
				if (type2.type == CeramicBrickType.Split) {
					if (colors[1] != -1) {
						return null;
					}
					colors[1] = color3;
				} else {
					if (type2.type != CeramicBrickType.Chequered) {
						return null;
					}
					if (colors[0] == -1) {
						colors[0] = color3;
					} else if (colors[0] != color3) {
						return null;
					}
				}
				final int color4 = type2.colorSecond.ordinal();
			}
			if (colors[0] == -1 || colors[1] == -1) {
				return null;
			}
			return new CeramicBrickPair(EnumFlowerColor.get(colors[0]), EnumFlowerColor.get(colors[1]), CeramicBrickType.Mixed).getStack(4);
		}
	},
	VerticalBrick("verticalbrick", "Vertical Ceramic Bricks"){
		@Nullable
		@Override
		public ItemStack getRecipe(List<ItemStack> stacks) {
			return getVerticalBrickRecipe(stacks);
		}
	},
	VerticalStripeBrick("verticalbrickstripe", "Vertical Striped Ceramic Bricks"){
		@Nullable
		@Override
		public ItemStack getRecipe(List<ItemStack> stacks) {
			return getVerticalBrickRecipe(stacks);
		}
	},
	VerticalLargeBrick("verticalbricklarge", "Large Vertical Ceramic Bricks"){
		@Nullable
		@Override
		public ItemStack getRecipe(List<ItemStack> stacks) {
			return getVerticalBrickRecipe(stacks);
		}
	};

	public static final CeramicBrickType[] VALUES = values();
	
	String id;
	String name;
	TextureAtlasSprite[] sprites;

	CeramicBrickType(final String id, final String name) {
		this.sprites = new TextureAtlasSprite[3];
		this.id = id;
		this.name = name;
	}

	public static CeramicBrickType get(final int id) {
		return VALUES[id % VALUES.length];
	}

	public boolean canDouble() {
		return this != CeramicBrickType.Tile;
	}

	@Nullable
	public abstract ItemStack getRecipe(List<ItemStack> stacks);

	@Nullable
	protected ItemStack getVerticalBrickRecipe(List<ItemStack> stacks){
		if (stacks.size() != 1) {
			return null;
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
		return null;
	}

	@Nullable
	protected ItemStack getBrickRecipe(CeramicBrickType ceramicType, List<ItemStack> stacks){
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
				final CeramicBrickPair type4 = new CeramicBrickPair(stack4);
				if (type4.type != CeramicBrickType.LargeBrick) {
					return null;
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
					return null;
				}
			}
			return new CeramicBrickPair(EnumFlowerColor.get(colors[0]), EnumFlowerColor.get(colors[1]), ceramicType).getStack(4);
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
