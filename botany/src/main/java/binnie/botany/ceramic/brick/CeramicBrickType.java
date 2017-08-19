package binnie.botany.ceramic.brick;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import binnie.botany.api.genetics.EnumFlowerColor;
import binnie.botany.items.CeramicItems;
import binnie.botany.modules.ModuleCeramic;
import binnie.core.block.TileEntityMetadata;

public enum CeramicBrickType implements IStringSerializable {
	TILE("tile") {
		@Override
		public ItemStack getRecipe(List<ItemStack> stacks) {
			if (stacks.size() != 4) {
				return ItemStack.EMPTY;
			}
			int mortars = 0;
			int blocks = 0;
			int blockColor = -1;
			for (ItemStack stack : stacks) {
				if (isMortar(stack)) {
					++mortars;
				} else {
					if (stack.getItem() != Item.getItemFromBlock(ModuleCeramic.ceramic)) {
						return ItemStack.EMPTY;
					}
					++blocks;
					int color = TileEntityMetadata.getItemDamage(stack);
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
			EnumFlowerColor c = EnumFlowerColor.get(blockColor);
			return new CeramicBrickPair(c, c, CeramicBrickType.TILE).getStack(3);
		}
	},
	BRICK("brick") {
		@Override
		public ItemStack getRecipe(List<ItemStack> stacks) {
			return getBrickRecipe(this, stacks);
		}
	},
	STRIPE_BRICK("brickstripe") {
		@Override
		public ItemStack getRecipe(List<ItemStack> stacks) {
			return getBrickRecipe(this, stacks);
		}
	},
	LARGE_BRICK("bricklarge") {
		@Override
		public ItemStack getRecipe(List<ItemStack> stacks) {
			if (stacks.size() == 1) {
				ItemStack stack3 = stacks.get(0);
				CeramicBrickPair type3 = new CeramicBrickPair(stack3);
				if (type3.type == CeramicBrickType.VERTICAL_LARGE_BRICK) {
					type3.type = this;
					return type3.getStack(1);
				}
				return ItemStack.EMPTY;
			} else {
				if (stacks.size() != 3) {
					return ItemStack.EMPTY;
				}
				int[] colors = {-1, -1};
				int a = 0;
				int b = 0;
				for (ItemStack stack4 : stacks) {
					if (stack4.getItem() != Item.getItemFromBlock(ModuleCeramic.ceramicBrick)) {
						return ItemStack.EMPTY;
					}
					CeramicBrickPair type4 = new CeramicBrickPair(stack4);
					if (type4.type != CeramicBrickType.TILE) {
						return ItemStack.EMPTY;
					}
					int color5 = type4.colorFirst.ordinal();
					if (colors[0] == -1) {
						colors[0] = color5;
						a++;
					} else if (colors[0] == color5) {
						a++;
					} else if (colors[1] == -1) {
						colors[1] = color5;
						b++;
					} else {
						if (colors[1] != color5) {
							return ItemStack.EMPTY;
						}
						b++;
					}
				}
				if (colors[1] == -1) {
					colors[1] = colors[0];
				}
				return new CeramicBrickPair(EnumFlowerColor.get(colors[0]), EnumFlowerColor.get(colors[1]), CeramicBrickType.LARGE_BRICK).getStack(3);
			}
		}
	},
	SPLIT("split") {
		@Override
		public ItemStack getRecipe(List<ItemStack> stacks) {
			if (stacks.size() != 4) {
				return ItemStack.EMPTY;
			}
			int[] colors = {-1, -1};
			int altCounter = 0;
			for (ItemStack stack2 : stacks) {
				int alt = (altCounter != 0 && altCounter != 3) ? 1 : 0;
				if (stack2.getItem() != Item.getItemFromBlock(ModuleCeramic.ceramicBrick)) {
					return ItemStack.EMPTY;
				}
				CeramicBrickPair type = new CeramicBrickPair(stack2);
				if (type.type != CeramicBrickType.TILE) {
					return ItemStack.EMPTY;
				}
				int color2 = type.colorFirst.ordinal();
				if (colors[alt] == -1) {
					colors[alt] = color2;
				} else if (colors[alt] != color2) {
					return ItemStack.EMPTY;
				}
				++altCounter;
			}
			return new CeramicBrickPair(EnumFlowerColor.get(colors[0]), EnumFlowerColor.get(colors[1]), CeramicBrickType.SPLIT).getStack(4);
		}
	},
	CHEQUERED("cheque") {
		@Override
		public ItemStack getRecipe(List<ItemStack> stacks) {
			if (stacks.size() != 4) {
				return ItemStack.EMPTY;
			}
			int[] colors = {-1, -1};
			for (ItemStack stack2 : stacks) {
				if (stack2.getItem() != Item.getItemFromBlock(ModuleCeramic.ceramicBrick)) {
					return ItemStack.EMPTY;
				}
				CeramicBrickPair type2 = new CeramicBrickPair(stack2);
				if (type2.type != CeramicBrickType.SPLIT) {
					return ItemStack.EMPTY;
				}
				int color3 = type2.colorFirst.ordinal();
				int color4 = type2.colorSecond.ordinal();
				if (colors[0] == -1) {
					colors[0] = color3;
					colors[1] = color4;
				} else {
					if (colors[0] != color3 || colors[1] != color4) {
						return ItemStack.EMPTY;
					}
				}
			}
			return new CeramicBrickPair(EnumFlowerColor.get(colors[0]), EnumFlowerColor.get(colors[1]), CeramicBrickType.CHEQUERED).getStack(4);
		}
	},
	MIXED("mixed") {
		@Override
		public ItemStack getRecipe(List<ItemStack> stacks) {
			if (stacks.size() != 4) {
				return ItemStack.EMPTY;
			}
			int[] colors = {-1, -1};
			for (ItemStack stack2 : stacks) {
				if (stack2.getItem() != Item.getItemFromBlock(ModuleCeramic.ceramicBrick)) {
					return ItemStack.EMPTY;
				}
				CeramicBrickPair type2 = new CeramicBrickPair(stack2);
				if (type2.isTwoColors()) {
					return ItemStack.EMPTY;
				}
				int color3 = type2.colorFirst.ordinal();
				if (type2.type == CeramicBrickType.SPLIT) {
					if (colors[1] != -1) {
						return ItemStack.EMPTY;
					}
					colors[1] = color3;
				} else {
					if (type2.type != CeramicBrickType.CHEQUERED) {
						return ItemStack.EMPTY;
					}
					if (colors[0] == -1) {
						colors[0] = color3;
					} else if (colors[0] != color3) {
						return ItemStack.EMPTY;
					}
				}
				int color4 = type2.colorSecond.ordinal();
			}
			if (colors[0] == -1 || colors[1] == -1) {
				return ItemStack.EMPTY;
			}
			return new CeramicBrickPair(EnumFlowerColor.get(colors[0]), EnumFlowerColor.get(colors[1]), CeramicBrickType.MIXED).getStack(4);
		}
	},
	VERTICAL_BRICK("verticalbrick") {
		@Override
		public ItemStack getRecipe(List<ItemStack> stacks) {
			return getVerticalBrickRecipe(stacks);
		}
	},
	VERTICAL_STRIPE_BRICK("verticalbrickstripe") {
		@Override
		public ItemStack getRecipe(List<ItemStack> stacks) {
			return getVerticalBrickRecipe(stacks);
		}
	},
	VERTICAL_LARGE_BRICK("verticalbricklarge") {
		@Override
		public ItemStack getRecipe(List<ItemStack> stacks) {
			return getVerticalBrickRecipe(stacks);
		}
	};

	public static final CeramicBrickType[] VALUES = values();

	public String id;

	@SideOnly(Side.CLIENT)
	@Nullable
	public TextureAtlasSprite[] sprites;

	CeramicBrickType(String id) {
		this.id = id;
	}

	public static CeramicBrickType get(int id) {
		return VALUES[id % VALUES.length];
	}

	public boolean canDouble() {
		return this != CeramicBrickType.TILE;
	}

	public abstract ItemStack getRecipe(List<ItemStack> stacks);

	protected ItemStack getVerticalBrickRecipe(List<ItemStack> stacks) {
		if (stacks.size() != 1) {
			return ItemStack.EMPTY;
		}
		ItemStack stack5 = stacks.get(0);
		CeramicBrickPair type = new CeramicBrickPair(stack5);
		if (type.type == CeramicBrickType.LARGE_BRICK) {
			type.type = CeramicBrickType.VERTICAL_LARGE_BRICK;
			return type.getStack(1);
		}

		if (type.type == CeramicBrickType.BRICK) {
			type.type = CeramicBrickType.VERTICAL_BRICK;
			return type.getStack(1);
		}

		if (type.type == CeramicBrickType.STRIPE_BRICK) {
			type.type = CeramicBrickType.VERTICAL_STRIPE_BRICK;
			return type.getStack(1);
		}
		return ItemStack.EMPTY;
	}

	protected ItemStack getBrickRecipe(CeramicBrickType ceramicType, List<ItemStack> stacks) {
		if (stacks.size() == 1) {
			ItemStack stack5 = stacks.get(0);
			CeramicBrickPair type = new CeramicBrickPair(stack5);
			if (type.type == CeramicBrickType.VERTICAL_BRICK) {
				type.type = ceramicType;
				return type.getStack(1);
			}

			if (type.type == CeramicBrickType.STRIPE_BRICK) {
				type.type = ceramicType;
				return type.getStack(1);
			}
			return ItemStack.EMPTY;
		} else if (stacks.size() == 4) {
			int[] colors = {-1, -1};
			for (int index2 = 0; index2 < stacks.size(); ++index2) {
				ItemStack stack4 = stacks.get(index2);
				if (stack4.getItem() != Item.getItemFromBlock(ModuleCeramic.ceramicBrick)) {
					return ItemStack.EMPTY;
				}

				CeramicBrickPair type4 = new CeramicBrickPair(stack4);
				if (type4.type != CeramicBrickType.LARGE_BRICK) {
					return ItemStack.EMPTY;
				}

				int color5 = type4.colorFirst.ordinal();
				int color6 = type4.colorSecond.ordinal();
				int alt2 = (index2 != 0 && index2 != 3) ? 1 : 0;
				if (ceramicType == CeramicBrickType.STRIPE_BRICK) {
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
		}
		return ItemStack.EMPTY;
	}

	protected boolean isMortar(ItemStack stack) {
		return stack.getItem() == ModuleCeramic.misc && stack.getItemDamage() == CeramicItems.MORTAR.ordinal();
	}

	@Override
	public String getName() {
		return id;
	}
}
