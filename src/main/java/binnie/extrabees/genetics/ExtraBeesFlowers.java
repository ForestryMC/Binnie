package binnie.extrabees.genetics;

import binnie.extrabees.ExtraBees;
import binnie.extrabees.utils.Utils;
import forestry.api.apiculture.FlowerManager;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleFlowers;
import forestry.api.genetics.ICheckPollinatable;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IFlowerProvider;
import forestry.api.genetics.IFruitBearer;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;

import java.util.Collections;
import java.util.List;

public enum ExtraBeesFlowers implements IFlowerProvider, IAlleleFlowers, IChromosomeType {
	WATER,
	SUGAR,
	ROCK,
	BOOK,
	DEAD,
	REDSTONE,
	WOOD,
	LEAVES,
	Sapling,
	Fruit,
	Mystical;

	boolean dominant;

	ExtraBeesFlowers() {
		this.dominant = true;
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
		return ExtraBees.proxy.localise("flowers." + this.name().toLowerCase() + ".name");
	}

	public void register() {
		//AlleleManager.alleleRegistry.registerAllele(this);

		for (Block block : getAcceptableBlocks()) {
			FlowerManager.flowerRegistry.registerAcceptableFlower(block, getUID());
		}

		AlleleManager.alleleRegistry.registerAllele(this, this);
	}

	public static void doInit() {
		for (final ExtraBeesFlowers effect : values()) {
			effect.register();
		}
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
				return Collections.singletonList(Blocks.COBBLESTONE);
			}
			case BOOK: {
				return Collections.singletonList(Blocks.BOOKSHELF);
			}
			case REDSTONE: {
				return Collections.singletonList(Blocks.REDSTONE_TORCH);
			}
			case DEAD: {
				return Collections.singletonList(Blocks.DEADBUSH);
			}
			case Fruit: {
				return Collections.emptyList(); // TODO: what is this supposed to be? It was Items.APPLE before.
			}
			case LEAVES: {
				return Collections.singletonList(Blocks.LEAVES);
			}
			case Sapling: {
				return Collections.singletonList(Blocks.SAPLING);
			}
			case WOOD: {
				return Collections.singletonList(Blocks.LOG);
			}
			default: {
				return Collections.emptyList();
			}
		}
	}

	public boolean isAcceptedFlower(final World world, final BlockPos pos) {
		final Block block = world.getBlockState(pos).getBlock();
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
			case Fruit: {
				return world.getTileEntity(pos) instanceof IFruitBearer;
			}
			case LEAVES: {
				return block.isLeaves(world.getBlockState(pos), world, pos);
			}
			case Sapling: {
				return block.getClass().getName().toLowerCase().contains("sapling");
			}
			case Mystical: {
				return block == Utils.getBotaniaBlock("flower");
			}
			default: {
				return false;
			}
		}
	}


//	@Override
//	public boolean growFlower(final World world, final IIndividual individual, final BlockPos pos) {
//		switch (this) {
//		case WATER: {
//			return world.isAirBlock(x, y, z) && world.getBlock(x, y - 1, z) == Blocks.water && world.setBlock(x, y, z, Blocks.waterlily, 0, 2);
//		}
//		case SUGAR: {
//			return world.getBlock(x, y - 1, z) == Blocks.reeds && world.isAirBlock(x, y, z) && world.setBlock(x, y, z, Blocks.reeds, 0, 0);
//		}
//		default: {
//			return false;
//		}
//		}
//	}

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

//	@Override
//	public Set<IFlower> getFlowers() {
//		return new HashSet<IFlower>();
//	}

	@Override
	public Class<? extends IAllele> getAlleleClass() {
		return getClass();
	}

	@Override
	public ISpeciesRoot getSpeciesRoot() {
		return AlleleManager.alleleRegistry.getSpeciesRoot(getUID());
	}

	@Override
	public boolean isAcceptedPollinatable(World world, ICheckPollinatable pollinatable) {
		return pollinatable.getPlantType() != EnumPlantType.Nether;
	}

	@Override
	public NonNullList<ItemStack> affectProducts(World world, IIndividual individual, BlockPos pos, NonNullList<ItemStack> products) {
		if (this == ExtraBeesFlowers.Mystical) {
			final NonNullList<ItemStack> prods = NonNullList.create();
			prods.addAll(products);
			for (int k = 0; k < 50; ++k) {
				final int tX = 7;
				final int tY = 7;
				final int tZ = 3;
				final int x2 = pos.getX() - tX + world.rand.nextInt(1 + 2 * tX);
				final int y2 = pos.getY() - tY + world.rand.nextInt(1 + 2 * tY);
				final int z2 = pos.getZ() - tZ + world.rand.nextInt(1 + 2 * tZ);
				final BlockPos pos2 = new BlockPos(x2, y2, z2);
				final Block block = world.getBlockState(pos2).getBlock();
				if (block == Utils.getBotaniaBlock("flower")) {
					final int meta = world.getBlockState(pos2).getBlock().getMetaFromState(world.getBlockState(pos2));
					final Item item = Utils.getBotaniaItem("petal");
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
