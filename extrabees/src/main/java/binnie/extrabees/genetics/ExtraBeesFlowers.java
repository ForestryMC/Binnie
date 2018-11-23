package binnie.extrabees.genetics;

import com.google.common.collect.ImmutableList;

import java.util.Collections;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.common.EnumPlantType;

import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.FlowerManager;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAlleleFlowers;
import forestry.api.genetics.ICheckPollinatable;
import forestry.api.genetics.IFlowerProvider;
import forestry.api.genetics.IFlowerRegistry;
import forestry.api.genetics.IFruitBearer;
import forestry.api.genetics.IIndividual;

import binnie.core.util.I18N;
import binnie.extrabees.utils.Utils;

public enum ExtraBeesFlowers implements IFlowerProvider, IAlleleFlowers {
	WATER,
	SUGAR,
	ROCK,
	BOOK,
	DEAD,
	REDSTONE,
	WOOD,
	LEAVES,
	SAPLING,
	FRUIT,
	MYSTICAL;

	private final boolean dominant;

	ExtraBeesFlowers() {
		this.dominant = true;
	}

	public static void doInit() {
		for (ExtraBeesFlowers effect : values()) {
			effect.register();
		}
	}

	@Override
	public String getUID() {
		return "extrabees.flower." + this.toString().toLowerCase();
	}

	@Override
	public boolean isDominant() {
		return this.dominant;
	}

	@Override
	public IFlowerProvider getProvider() {
		return this;
	}

	@Override
	public String getDescription() {
		return I18N.localise("extrabees.flowers." + this.name().toLowerCase() + ".name");
	}

	public void register() {
		//AlleleManager.alleleRegistry.registerAllele(this);

		IFlowerRegistry flowerRegistry = FlowerManager.flowerRegistry;
		for (Block block : getAcceptableBlocks()) {
			flowerRegistry.registerAcceptableFlower(block, getUID());
		}

		switch (this) {
			case ROCK:
				flowerRegistry.registerAcceptableFlowerRule((blockState, world, pos, flowerType) -> blockState.getMaterial() == Material.ROCK, getUID());
				break;
			case LEAVES:
				flowerRegistry.registerAcceptableFlowerRule((blockState, world, pos, flowerType) -> blockState.getBlock().isLeaves(blockState, world, pos), getUID());
				break;
			case WOOD:
				flowerRegistry.registerAcceptableFlowerRule((blockState, world, pos, flowerType) -> blockState.getBlock().isWood(world, pos), getUID());
				break;
		}

		AlleleManager.alleleRegistry.registerAllele(this, EnumBeeChromosome.FLOWER_PROVIDER);
	}

	public List<Block> getAcceptableBlocks() {
		switch (this) {
			case WATER: {
				return Collections.singletonList(Blocks.WATERLILY);
			}
			case SUGAR: {
				return Collections.singletonList(Blocks.REEDS);
			}
			case ROCK: {
				return ImmutableList.of(Blocks.COBBLESTONE, Blocks.STONE);
			}
			case BOOK: {
				return Collections.singletonList(Blocks.BOOKSHELF);
			}
			case REDSTONE: {
				return ImmutableList.of(Blocks.REDSTONE_TORCH, Blocks.REDSTONE_BLOCK, Blocks.REDSTONE_ORE);
			}
			case DEAD: {
				return Collections.singletonList(Blocks.DEADBUSH);
			}
			case FRUIT: {
				return Collections.emptyList(); // TODO: what is this supposed to be? It was Items.APPLE before.
			}
			case SAPLING: {
				return Collections.singletonList(Blocks.SAPLING);
			}
			case MYSTICAL:
				Block flower = Utils.getBotaniaBlock("flower");
				return flower == null ? Collections.emptyList() : Collections.singletonList(flower);
			default: {
				return Collections.emptyList();
			}
		}
	}

	public boolean isAcceptedFlower(World world, BlockPos pos) {
		Block block = world.getBlockState(pos).getBlock();
		switch (this) {
			case WATER: {
				return block == Blocks.WATERLILY;
			}
			case ROCK: {
				return world.getBlockState(pos).getMaterial() == Material.ROCK;
			}
			case SUGAR: {
				return block == Blocks.REEDS;
			}
			case BOOK: {
				return block == Blocks.BOOKSHELF;
			}
			case REDSTONE: {
				return block == Blocks.REDSTONE_TORCH;
			}
			case DEAD: {
				return block == Blocks.DEADBUSH;
			}
			case WOOD: {
				return block.isWood(world, pos);
			}
			case FRUIT: {
				return world.getTileEntity(pos) instanceof IFruitBearer;
			}
			case LEAVES: {
				return block.isLeaves(world.getBlockState(pos), world, pos);
			}
			case SAPLING: {
				return block.getClass().getName().toLowerCase().contains("sapling");
			}
			case MYSTICAL: {
				return block == Utils.getBotaniaBlock("flower");
			}
			default: {
				return false;
			}
		}
	}

	/*@Override
	public boolean growFlower(final World world, final IIndividual individual, final BlockPos pos) {
		switch (this) {
		case WATER: {
			return world.isAirBlock(x, y, z) && world.getBlock(x, y - 1, z) == Blocks.water && world.setBlock(x, y, z, Blocks.waterlily, 0, 2);
		}
		case SUGAR: {
			return world.getBlock(x, y - 1, z) == Blocks.reeds && world.isAirBlock(x, y, z) && world.setBlock(x, y, z, Blocks.reeds, 0, 0);
		}
		default: {
			return false;
		}
		}
	}*/

	@Override
	public String getName() {
		return this.getDescription();
	}

	@Override
	public String getUnlocalizedName() {
		return this.getUID();
	}

	@Override
	public String getFlowerType() {
		return this.getUID();
	}

	@Override
	public boolean isAcceptedPollinatable(World world, ICheckPollinatable pollinatable) {
		return pollinatable.getPlantType() != EnumPlantType.Nether;
	}

	@Override
	public NonNullList<ItemStack> affectProducts(World world, IIndividual individual, BlockPos pos, NonNullList<ItemStack> products) {
		if (this == ExtraBeesFlowers.MYSTICAL) {
			NonNullList<ItemStack> prods = NonNullList.create();
			prods.addAll(products);
			for (int k = 0; k < 50; ++k) {
				int tX = 7;
				int tY = 7;
				int tZ = 3;
				int x2 = pos.getX() - tX + world.rand.nextInt(1 + 2 * tX);
				int y2 = pos.getY() - tY + world.rand.nextInt(1 + 2 * tY);
				int z2 = pos.getZ() - tZ + world.rand.nextInt(1 + 2 * tZ);
				BlockPos pos2 = new BlockPos(x2, y2, z2);
				Block block = world.getBlockState(pos2).getBlock();
				if (block == Utils.getBotaniaBlock("flower")) {
					int meta = world.getBlockState(pos2).getBlock().getMetaFromState(world.getBlockState(pos2));
					Item item = Utils.getBotaniaItem("petal");
					if (item != null) {
						prods.add(new ItemStack(item, 1, meta));
					}
				}
			}
			return prods;
		}
		return products;
	}
}
